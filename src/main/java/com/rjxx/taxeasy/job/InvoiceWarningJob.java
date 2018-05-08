package com.rjxx.taxeasy.job;


import com.rjxx.taxeasy.bizcomm.utils.HttpUtils;
import com.rjxx.taxeasy.bizcomm.utils.MailService;
import com.rjxx.taxeasy.bizcomm.utils.XmlMapUtils;
import com.rjxx.taxeasy.domains.Fpkc;
import com.rjxx.taxeasy.domains.FpkcMx;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.dto.*;
import com.rjxx.taxeasy.service.*;
import com.rjxx.taxeasy.vo.FpkcYjtzVo;
import com.rjxx.taxeasy.vo.KpfsVo;
import com.rjxx.utils.XmlJaxbUtils;
import org.apache.axiom.om.OMElement;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by kzx on 2018/05/04.
 */
@DisallowConcurrentExecution
public class InvoiceWarningJob implements Job {

    @Autowired
    private CszbService cszbService;

    @Autowired
    private FpkcYztzService fpkcYztzService;

    @Autowired
    private MailService mailService;


    private static Logger logger = LoggerFactory.getLogger(InvoiceWarningJob.class);

    /**
     * 服务器发票预警定时任务
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
                logger.info("-------进入库存预警定时任务开始---------"+context.getNextFireTime());
                List<FpkcYjtzVo> tzList = fpkcYztzService.findAllTzList(new HashMap());
                for (int i= 0;i<tzList.size();i++){
                    FpkcYjtzVo fpkcYjtzVo = tzList.get(i);
                    if(fpkcYjtzVo.getTzfs().contains("02") && fpkcYjtzVo.getTzfs().contains("03")){
                        String [] to=new String[1];
                        to[0]=fpkcYjtzVo.getEmail();
                        mailService.sendSimpleMail(to,"发票库存预警",fpkcYjtzVo.getTzy());
                    }else if(fpkcYjtzVo.getTzfs().contains("02")){
                        //发邮件
                        String [] to=new String[1];
                        to[0]=fpkcYjtzVo.getEmail();
                        mailService.sendSimpleMail(to,"发票库存预警",fpkcYjtzVo.getTzy());
                    }else if(fpkcYjtzVo.getTzfs().contains("03")){
                        //短信
                    }
                }

            logger.info("-------进入库存预警定时任务结束---------"+context.getNextFireTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private String getSkKplx(String kplx){
        if(kplx.equals("12")){
            return "026";
        }else if(kplx.equals("01")){
            return "004";
        }else if(kplx.equals("02")){
            return "007";
        }
        return "000";
    }

    /**
     * 解析参数设置接口返回的报文
     * @param xmlStr
     * @return
     */
    private String getParam(String xmlStr){
        /*<?xml version = "1.0" encoding = "gbk" ?>
          <business id = "20001" comment = "参数设置" >
            <body yylxdm = "1" >
                <returncode>0</returncode>
                <returnmsg>成功</returnmsg>
            </body >
          </business >*/
        OMElement root = null;
        String returncode = "-1";
        try {
            root = XmlMapUtils.xml2OMElement(xmlStr);
            Map rootMap = XmlMapUtils.xml2Map(root, "business");
            Map bodyMap = (Map) rootMap.get("body");
            returncode = String.valueOf(bodyMap.get("returncode"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return returncode;
    }


    /**
     * 解析发票领购信息接口返回的报文
     * @param xmlStr
     * @return
     */
    private static Map getFplgxx(String gsdm,Integer xfid,Integer skpid,String fpzldm,String xmlStr){
        String returncode = "-1";
        Fpkc fpkc = new Fpkc();
        List<FpkcMx> fpkcMxList = new ArrayList<>();
        Map result = new HashMap();
        try {
            Fplgxx fplgxx = XmlJaxbUtils.convertXmlStrToObject(Fplgxx.class,xmlStr);
            FplgxxBody body = fplgxx.getBody();
            returncode = body.getReturncode();
            if(returncode.equals("0")){
                Returndata returndata = body.getReturndata();
                Integer zsyfs = Integer.valueOf(returndata.getZsyfs());
                fpkc.setFpkcl(zsyfs);
                fpkc.setGsdm(gsdm);
                fpkc.setXfid(xfid);
                fpkc.setSkpid(skpid);
                fpkc.setFpzldm(fpzldm);
                fpkc.setYxbz("1");
                fpkc.setLrsj(new Date());
                fpkc.setXgsj(new Date());
                Lgxx lgxx = returndata.getLgxx();
                List<Group> lgxxList = lgxx.getGroup();
                if(!lgxxList.isEmpty()){
                    for (int i=0;i<lgxxList.size();i++){
                        FpkcMx fpkcMx = new FpkcMx();
                        Group group = lgxxList.get(i);
                        fpkcMx.setFpdm(group.getFpdm());
                        fpkcMx.setQshm(group.getQshm());
                        fpkcMx.setZzhm(group.getZzhm());
                        fpkcMx.setFpfs(group.getFpfs());
                        fpkcMx.setSyfs(group.getSyfs());
                        fpkcMx.setLgrq(group.getLgrq());
                        fpkcMx.setYxbz("1");
                        fpkcMxList.add(fpkcMx);
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result.put("returncode",returncode);
        result.put("fpkc",fpkc);
        result.put("fpkcMxList",fpkcMxList);
        return result;
    }

    public static  void main(String args[]){
        String tmp="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<business id=\"10005\" comment=\"发票领购信息查询\">\n" +
                "  <body yylxdm=\"1\">\n" +
                "    <returncode>0</returncode>\n" +
                "    <returnmsg>成功</returnmsg>\n" +
                "    <returndata>\n" +
                "      <dqfpdm>031001700311</dqfpdm>\n" +
                "      <dqfphm>55769061</dqfphm>\n" +
                "      <zsyfs>65440</zsyfs>\n" +
                "      <lgxx count=\"1\">\n" +
                "        <group xh=\"1\">\n" +
                "          <fpdm>031001700311</fpdm>\n" +
                "          <qshm>55734501</qshm>\n" +
                "          <zzhm>55784500</zzhm>\n" +
                "          <fpfs>50000</fpfs>\n" +
                "          <syfs>15440</syfs>\n" +
                "          <lgrq>20180403</lgrq>\n" +
                "          <lgry>网上</lgry>\n" +
                "        </group>\n" +
                "      </lgxx>\n" +
                "    </returndata>\n" +
                "  </body>\n" +
                "</business>";
        getFplgxx("1",1,1,"02",tmp);
    }
}
