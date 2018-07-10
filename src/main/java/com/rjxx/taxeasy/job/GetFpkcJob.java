package com.rjxx.taxeasy.job;


import com.rjxx.taxeasy.bizcomm.utils.HttpUtils;
import com.rjxx.taxeasy.bizcomm.utils.XmlMapUtils;
import com.rjxx.taxeasy.domains.Cszb;
import com.rjxx.taxeasy.domains.Fpkc;
import com.rjxx.taxeasy.domains.FpkcMx;
import com.rjxx.taxeasy.domains.Skp;
import com.rjxx.taxeasy.dto.*;
import com.rjxx.taxeasy.service.CszbService;
import com.rjxx.taxeasy.service.FpkcMxService;
import com.rjxx.taxeasy.service.FpkcService;
import com.rjxx.taxeasy.service.SkpService;
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
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by kzx on 2018/04/27.
 */
@DisallowConcurrentExecution
@Service
public class GetFpkcJob implements Job {

    @Autowired
    private CszbService cszbService;

    @Autowired
    private SkpService skpService;

    @Autowired
    private FpkcService fpkcService;

    @Autowired
    private FpkcMxService fpkcMxService;

    private static Logger logger = LoggerFactory.getLogger(GetFpkcJob.class);

    /**
     * 服务器获取发票库存定时任务
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String ws_url = "";
        try {
            logger.info("-------进入发票库存定时任务开始---------"+context.getNextFireTime());
            getFpkc(ws_url);
            logger.info("-------进入发票库存定时任务结束---------"+context.getNextFireTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取服务器库存
     * @param ws_url
     */
    public void getFpkc(String ws_url){
        //获取IIS服务对应的url地址。
        Cszb cszb = cszbService.getSpbmbbh(null,null,null,"getskkcUrl");
        if(null!=cszb && null != cszb.getCsz() && !cszb.getCsz().equals("")){
            ws_url = cszb.getCsz();
            Map map = new HashMap();
            map.put("skurl2","03");//保证取值skurl不为空的数据
            map.put("csz","03");
            List<KpfsVo> kpfsList = cszbService.findAllKpfs(map);
            if(!kpfsList.isEmpty()){
                Map<String,String> skurlMap = new HashMap<>();//定义无用变量用来分组。
                List<String> skurlList = new ArrayList<>();//服务器地址list（说明有几台服务器）
                for(int i=0;i<kpfsList.size();i++){
                    KpfsVo kpfsVo = kpfsList.get(i);
                    if (!skurlMap.containsKey(kpfsVo.getSkurl())) {
                        skurlMap.put(kpfsVo.getSkurl(),kpfsVo.getSkurl());
                        skurlList.add(kpfsVo.getSkurl());
                    }
                }
                for(int a=0;a<skurlList.size();a++){
                    String url = skurlList.get(a);
                    String result = netSetParameter(ws_url,url);//税控服务器设置参数
                    //判断设置参数是否成功，0表示设置参数成功。
                    if(null != result && getParam(result).equals("0")){
                        for(int t=0;t<kpfsList.size();t++){
                            KpfsVo kpfsVo = kpfsList.get(t);
                            //遍历需获取库存的url与当前已设置参数的url进行比较，并获取库存。
                            if(kpfsVo.getSkurl().equals(url)){
                                Skp skp = skpService.findOne(kpfsVo.getKpdid());
                                String kplx[] = skp.getKplx().split(",");
                                for(int j=0;j<kplx.length;j++){
                                    String fwqkplx = getSkKplx(kplx[j]);
                                    String result2 = HttpUtils.netFPLGXXCX(ws_url,"FPLGXXCX",null,skp.getKpdip(),fwqkplx,"1","1");
                                    Map map2 = getFplgxx(kpfsVo.getGsdm(),kpfsVo.getXfid(),kpfsVo.getKpdid(),kplx[j],result2);
                                    if(map2.get("returncode").equals("0")){
                                        Map params = new HashMap();
                                        params.put("gsdm",kpfsVo.getGsdm());
                                        params.put("xfid",kpfsVo.getXfid());
                                        params.put("skpid",kpfsVo.getKpdid());
                                        params.put("fpzldm",kplx[j]);
                                        Fpkc yfpkc = fpkcService.findOneByParams(params);
                                        Fpkc fpkc = (Fpkc)map2.get("fpkc");
                                        Integer kcid = null;
                                        if(yfpkc!=null){
                                            if(fpkc.getFpkcl().compareTo(yfpkc.getFpkcl())!=0) {
                                                yfpkc.setFpkcl(fpkc.getFpkcl());
                                                yfpkc.setXgsj(new Date());
                                                kcid = yfpkc.getId();
                                                Map params2 = new HashMap();
                                                //如果库存已存在，则删除原来的发票号段明细
                                                params2.put("kcid", kcid);
                                                fpkcMxService.deleteMxByKcid(params2);
                                                fpkcService.save(yfpkc);
                                            }
                                        }else{
                                            fpkcService.save(fpkc);
                                            kcid = fpkc.getId();
                                        }
                                        List<FpkcMx> fpkcMxList = (List)map2.get("fpkcMxList");
                                        if(!fpkcMxList.isEmpty()){
                                            for(int m=0;m<fpkcMxList.size();m++){
                                                FpkcMx fpkcMx = fpkcMxList.get(m);
                                                fpkcMx.setKcid(kcid);
                                            }
                                            fpkcMxService.save(fpkcMxList);
                                        }
                                    }

                                }
                            }
                        }
                    }

                }
            }

        }
    }

    /**
     *  对服务器进行参数设置
     * @param ws_url
     * @param skUrl
     * @return
     */
   private static String netSetParameter(String ws_url,String skUrl){
        return  HttpUtils.netSetParameter(ws_url,"SetParameter",
                skUrl.split("/")[2].split(":")[0],skUrl.split("/")[2].split(":")[1],"00000000",null);
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
        //getFplgxx("1",null,1,"02",tmp);
        System.out.println(netSetParameter("http://datarj.imwork.net:52472/Service.asmx?wsdl","http://116.228.105.246:9002/SKServer/SKDo"));
    }
}
