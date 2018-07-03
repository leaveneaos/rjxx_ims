package com.rjxx.taxeasy.controller.jx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.dao.leshui.FpcyJpaDao;
import com.rjxx.taxeasy.dao.leshui.FpcyMapper;
import com.rjxx.taxeasy.dao.leshui.FpcyjlJpaDao;
import com.rjxx.taxeasy.dao.leshui.FpcymxJpaDao;
import com.rjxx.taxeasy.domains.leshui.Fpcy;
import com.rjxx.taxeasy.domains.leshui.Fpcyjl;
import com.rjxx.taxeasy.domains.leshui.Fpcymx;
import com.rjxx.taxeasy.service.leshui.LeshuiService;
import com.rjxx.taxeasy.vo.FpcyVo;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.utils.ChinaNumber;
import com.rjxx.utils.ExcelUtil;
import com.rjxx.utils.StringUtils;
import com.rjxx.utils.TimeUtil;
import jdk.nashorn.internal.objects.NativeNumber;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018-01-22.
 *  发票查验
 */
@Controller
@RequestMapping("/income")
@CrossOrigin
public class IncomeController extends BaseController {

    @Autowired
    private LeshuiService leshuiService;
    @Autowired
    private FpcyJpaDao fpcyJpaDao;
    @Autowired
    private FpcyjlJpaDao fpcyjlJpaDao;
    @Autowired
    private FpcyMapper fpcyMapper;
    @Autowired
    private FpcymxJpaDao fpcymxJpaDao;


    @RequestMapping
    public String index() throws Exception {
        return "jx/fpcy/index";
    }


    /**
     * 查询列表
     * @param length
     * @param start
     * @param draw
     * @param fpdm
     * @param fphm
     * @param kprqq
     * @param gfsh
     * @param fpzldm
     * @param loaddata
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFpcyList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getFpcyList(int length, int start, int draw, String fpdm, String fphm, String kprqq,
                                         String gfsh, String fpzldm,boolean loaddata) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        if(loaddata){
            Pagination pagination = new Pagination();
            pagination.setPageNo(start / length + 1);
            pagination.setPageSize(length);
            String gsdm = getGsdm();
            if ("".equals(fpzldm) || fpzldm == null) {
                pagination.addParam("fpzldm",null);
            } else {
                pagination.addParam("fpzldm",fpzldm);
            }
            pagination.addParam("fpdm",fpdm);
            pagination.addParam("fphm",fphm);
            pagination.addParam("gfs",getXfList());
            pagination.addParam("kprqq",kprqq);
            pagination.addParam("gsdm",gsdm);
            if (null != gfsh && !"".equals(gfsh) && !"-1".equals(gfsh)) {
                pagination.addParam("xfsh", gfsh);
            }
            List<FpcyVo> dataList = new ArrayList();
            List<Fpcy> fpcyList = fpcyMapper.findByPage(pagination);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Fpcy fpcy : fpcyList) {
                Fpcyjl fpcyjl = fpcyjlJpaDao.findOneByFpcyIdAndGsdm(fpcy.getId(), getGsdm());
                Fpcyjl fpcyjlsj = fpcyjlJpaDao.findLastSuccessLrsj(fpcy.getId(), getGsdm());
                Integer cycsTotal = fpcyjlJpaDao.findCountByFpcyId(fpcyjl.getFpcyid());
                FpcyVo fpcyVo = new FpcyVo();
                fpcyVo.setId(fpcy.getId());
                fpcyVo.setFpdm(fpcy.getFpdm());
                fpcyVo.setFphm(fpcy.getFphm());
                fpcyVo.setXfmc(fpcy.getXfmc());
                fpcyVo.setBxry(fpcy.getBxry());
                fpcyVo.setKprq(sdf.format(fpcy.getKprq()));
                fpcyVo.setFpzldm(fpcy.getFpzldm());
                fpcyVo.setCycs(fpcy.getCycs());
                fpcyVo.setCycsTotal(cycsTotal);
                fpcyVo.setSjly(fpcy.getSjly());
                fpcyVo.setFpzt(fpcy.getFpzt());
                fpcyVo.setLrsj(sdf1.format(fpcyjlsj.getLrsj()));
                dataList.add(fpcyVo);
            }
            //logger.info("查询结果"+JSON.toJSONString(dataList));
            int total = pagination.getTotalRecord();
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("draw", draw);
            result.put("data", dataList);
        }else{
            int total = 0;
            result.put("recordsTotal", total);
            result.put("recordsFiltered", total);
            result.put("draw", draw);
            result.put("data", new ArrayList<>());
        }
        return result;
    }

    /**
     * 发票查验
     * @param sglr_fpzl
     * @param sglr_jym
     * @param sglr_fpdm
     * @param sglr_fphm
     * @param sglr_kprq
     * @param sglr_je
     * @return
     */
    @RequestMapping(value = "/invoiceCheck" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> invoiceCheck(String sglr_fpzl,String sglr_jym ,String sglr_fpdm,
                                    String sglr_fphm,String sglr_kprq,String sglr_je ,String sglr_bxr) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //校验是否报销
            String gsdm = getGsdm();
            String msg="";
            Fpcy fpcy = fpcyJpaDao.findOneByFpdmAndFphm(sglr_fpdm, sglr_fphm);
                if(fpcy !=null){
                    Fpcyjl fpcyjl = fpcyjlJpaDao.findOneBy1FpcyId(fpcy.getId());
                    List<Fpcymx> fpcymxList = fpcymxJpaDao.findOneByFpcyId(fpcy.getId());
                    session.setAttribute("fpcy",fpcy);
                    if(fpcyjl != null){
                        session.setAttribute("fpcyjlList",fpcyjl);
                    }
                    if(fpcymxList.size()>0){
                        session.setAttribute("fpcymxList",fpcymxList);
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String fpzt ="";
                    if("00".equals(fpcyjl.getReturncode())){
                        if("1000".equals(fpcyjl.getResultcode())){
                            if(fpcy.getFpzt()!=null && fpcy.getFpzt().equals("0")){
                                fpzt = "正常";
                            }else if(fpcy.getFpzt()!=null && fpcy.getFpzt().equals("1")){
                                fpzt = "作废";
                            }
                        }else {
                            fpzt = fpcyjl.getResultmsg();
                        }
                    }else{
                        fpzt = fpcyjl.getResultmsg();
                    }

                    result.put("status", true);
                    if(StringUtils.isBlank(fpcyjl.getBxry())){
                        if(fpcy.getCyrq()==null){
                            result.put("msg","该发票已存在"+"\r\n查验状态为："+fpzt);
                        }else {
                            result.put("msg","该发票已存在"+"，上次查验日期："+sdf.format(fpcy.getCyrq())+"\r\n查验状态为："+fpzt);
                        }
                    }else {
                        if(fpcy.getCyrq()==null){
                            result.put("msg","该发票已存在，报销人："+fpcyjl.getBxry()+"\r\n查验状态为："+fpzt);
                        }else {
                            result.put("msg","该发票已存在，报销人："+fpcyjl.getBxry()+"\r\n上次查验日期："+sdf.format(fpcy.getCyrq())+"\r\n查验状态为："+fpzt);

                        }
                    }
                    result.put("requery","1");
                    return result;
                }
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String res = leshuiService.fpcy(sglr_fpdm, sglr_fphm,sdf1.parse(sglr_kprq) , sglr_jym, sglr_je,"01",getGsdm(),sglr_bxr);
            logger.info(JSON.toJSONString(res));
            JSONObject resultJson = JSON.parseObject(res);
            String resultMsg_r = resultJson.getString("resultMsg");//查验结果
            result.put("msg",resultMsg_r);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("msg", "发票查验出现错误");
        }
        return result;
    }



    @RequestMapping(value = "/requery" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> requery(String sglr_fpzl,String sglr_jym ,String sglr_fpdm,
                                            String sglr_fphm,String sglr_kprq,String sglr_je,String sglr_bxr) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String gsdm = getGsdm();
            String res = leshuiService.fpcy(sglr_fpdm, sglr_fphm,sdf1.parse(sglr_kprq), sglr_jym, sglr_je,"01",getGsdm(),sglr_bxr);
//            logger.info(JSON.toJSONString(res));
            JSONObject resultJson = JSON.parseObject(res);
            String resultMsg_r = resultJson.getString("resultMsg");//查验结果
            result.put("msg",resultMsg_r);
            result.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("msg", "发票查验出现错误");
        }
        return result;
    }


    /**
     * 发票查验删除
     * @param fpcyIds
     * @return
     */
    @ResponseBody
    @RequestMapping("/sc")
    public Map<String, Object> sc(String fpcyIds) {
        Map<String, Object> result = new HashMap<String, Object>();
        String[] ids = fpcyIds.split(",");
        for (String id : ids) {
            Fpcy fpcy = fpcyJpaDao.findOne(Integer.valueOf(id));
            fpcy.setYxbz("0");
            fpcy.setXgsj(new Date());
            fpcyJpaDao.save(fpcy);
        }
        result.put("msg", "删除成功");
        return result;
    }

    /**
     * 发票查验预览
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping( value= "/fpcyyl")
    public String fpcyyl(String id)   {
        try {
            Fpcy fpcy = fpcyJpaDao.findOne(Integer.valueOf(id));
            List<Fpcymx> fpcymxList = fpcymxJpaDao.findOneByFpcyId(fpcy.getId());
            List<Fpcyjl> fpcyjlList = fpcyjlJpaDao.findByFpcyId(fpcy.getId());
            List dxlist = new ArrayList();
            ChinaNumber cn = new ChinaNumber();
            if(fpcy.getJshj() !=null && !fpcy.getJshj().equals("")){
                String jshjstr = fpcy.getJshj().toString();
                dxlist.add(cn.getCHSNumber(jshjstr));
            }
            session.setAttribute("zwlist", dxlist);
            session.setAttribute("fpcy",fpcy);
            session.setAttribute("fpcymxList",fpcymxList);
            session.setAttribute("fpcyjlList",fpcyjlList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            //result.put("msg","程序出错，请联系开发人员;");
        }
        return "jx/fpcy/fapiao";
    }

    /**
     * 发票查验-保存报销人
     * @param fpbq
     * @param bxr
     * @param id
     * @return
     */
    @RequestMapping( value= "/saveBc")
    @ResponseBody
    public Map saveBc(String fpbq,String bxr,String id){
        Map<String, Object> result = null;
        try {
            result = new HashMap<>();
            Fpcy fpcy = fpcyJpaDao.findOne(Integer.valueOf(id));
                if(StringUtils.isNotBlank(bxr)){
                    //Fpcyjl fpcyjl = fpcyjlJpaDao.findOneBy1FpcyId(Integer.valueOf(fpcy.getId()));
                    //fpcyjl.setBxry(bxr);
                    //fpcyjlJpaDao.save(fpcyjl);
                    fpcy.setBxry(bxr);
                    fpcy.setXgsj(new Date());
                    fpcyJpaDao.save(fpcy);
                }
            result.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status",false);
            result.put("msg","程序出错，请联系开发人员;");
        }
        return result;
    }

    @RequestMapping(value = "cycs")
    @ResponseBody
    public Map<String,Object> cycs(int length, int start, int draw,String id,boolean loaddata){
        Map<String, Object> result = new HashMap<String, Object>();
        List<FpcyVo> resultList = new ArrayList<>();
        try {
            if(loaddata){
                Fpcy fpcy = fpcyJpaDao.findOne(Integer.valueOf(id));
                List<Fpcyjl> fpcyjlList = fpcyjlJpaDao.findByFpcyId(fpcy.getId());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                int i=1;
                    for (Fpcyjl fpcyjl : fpcyjlList) {
                        FpcyVo fpcyVo = new FpcyVo();
                        if(fpcyjl.getFpzt()!=null && fpcyjl.getFpzt().equals("0")){
                            fpcyVo.setFpzt("正常");
                        }else if(fpcyjl.getFpzt()!=null && fpcyjl.getFpzt().equals("1")){
                            fpcyVo.setFpzt("作废");
                        }else if(fpcyjl.getFpzt()!=null && fpcyjl.getFpzt().equals("9")){
                            fpcyVo.setFpzt("--");
                        }
                        if(fpcyjl.getLrsj()!=null&&!fpcyjl.getLrsj().equals("")){
                            String format = sdf.format(fpcyjl.getLrsj());
                            fpcyVo.setCyrq(format);
                        }else {
                            fpcyVo.setCyrq("");
                        }
                        fpcyVo.setResultMsg(fpcyjl.getResultmsg());
                        fpcyVo.setCycs(i);
                        resultList.add(fpcyVo);
                        i++;
                    }
                int total;
                if(0 == start){
                    total = fpcyjlJpaDao.findCountByFpcyId(Integer.valueOf(id));
                    //total = pagination.getTotalRecord();
                    request.getSession().setAttribute("total",total);
                }else{
                    total =  (Integer)request.getSession().getAttribute("total");
                    //request.getSession().getAttribute("total");
                }
                result.put("recordsTotal", total);
                result.put("recordsFiltered", total);
                result.put("draw", draw);
                result.put("data", resultList);
            }else{
                int total = 0;
                result.put("recordsTotal", total);
                result.put("recordsFiltered", total);
                result.put("draw", draw);
                result.put("data", new ArrayList<>());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put("status",false);
        }
        return result;
    }

    //发票查验模板
    @RequestMapping(value = "/downloadDefaultImportTemplate")
    @ResponseBody
    public void exportDefaultExcel() throws Exception {
        try {
            InputStream inputStream = this.getClass().getClassLoader()
                    .getResourceAsStream("/template/importCYTemplate.xls");
            // 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=importCYTemplate.xls");
            ServletOutputStream out = response.getOutputStream();
            IOUtils.copy(inputStream, out);
            inputStream.close();
            out.flush();
            out.close();
//            String[] headers = { "发票类型(01：专票、02：普票、12：电票)", "发票代码", "发票号码", "开票日期(yyyy-mm-dd)", "金额(增值税专用发票必填)","校验码后6位(增值税普通发票必填)", "报销人"};
//            // 第一步，创建一个webbook，对应一个Excel文件
//            HSSFWorkbook wb = new HSSFWorkbook();
//            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
//            HSSFSheet sheet = wb.createSheet("发票查验");
//            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
//            HSSFRow row = sheet.createRow(0);
//            // 第四步，创建单元格，并设置值表头 设置表头居中
//            HSSFCellStyle style = wb.createCellStyle();
//            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//            HSSFCell cell = null;
//            for (int i = 0; i < headers.length; i++) {
//                cell = row.createCell(i);
//                cell.setCellValue(headers[i]);
//                cell.setCellStyle(style);
//            }
//            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//            String filename = timeFormat.format(new Date()) + ".xls";
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=".concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
//            OutputStream out = response.getOutputStream();
//            wb.write(out);
//            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //导入excel
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public Map importExcel(MultipartFile importFile) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        if (importFile == null || importFile.isEmpty()) {
            result.put("success", false);
            result.put("message", "请选择要导入的文件");
            return result;
        }
        List<List> resultList = ExcelUtil.exportListFromExcel(importFile.getInputStream(),
                FilenameUtils.getExtension(importFile.getOriginalFilename()), 0);
        if (resultList.size() < 2) {
            result.put("success", false);
            result.put("message", "行数少于2行，没有数据");
            return result;
        }
        try {
            String msg = processExcelList(resultList);
            if (!"".equals(msg)) {
                result.put("success", false);
                result.put("message", msg);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", e);
            return result;
        }

        result.put("success", true);
        result.put("count", resultList.size() - 1);
        return result;
    }

    //查验导入excel映射
    private static Map<String, String> importColumnMapping = new HashMap<String, String>() {
        {
            put("fplx", "发票类型");
            put("fpdm", "发票代码");
            put("fphm", "发票号码");
            put("kprq", "开票日期");
            put("je", "不含税金额");
            put("jym", "校验码后6位");
            put(" bxr", "报销人");
        }
    };

    private String processExcelList(List<List> dataList) throws Exception {
        int yhid = this.getYhid();
        Map<String, String> enCnExcelColumnMap = new HashMap<>();
        enCnExcelColumnMap.putAll(importColumnMapping);
        // 转换成key为中文，value为英文的map
        Map<String, String> cnEnExcelColumnMap = new HashMap<>();
        for (Map.Entry<String, String> entry : enCnExcelColumnMap.entrySet()) {
            cnEnExcelColumnMap.put(entry.getValue(), entry.getKey());
        }
        // 找到excel中有效的字段
        List titleList = dataList.get(0);
        // 字段的顺序
        int columnIndex = 0;
        Map<String, Integer> columnIndexMap = new HashMap<>();
        String gsdm = this.getGsdm();
        for (Object obj : titleList) {
            String columnName = (String) obj;
            if (cnEnExcelColumnMap.containsKey(columnName)) {
                columnIndexMap.put(columnName, columnIndex);
            }
            columnIndex++;
        }
        // 数据的校验
        String msgg = "";
        String msg = "";
        FpcyVo fpcyVo = new FpcyVo();
        List<FpcyVo> cyList = new ArrayList<>();
        for (int i = 1; i < dataList.size(); i++) {
            List row = dataList.get(i);
            try {
                fpcyVo.setFplx(row.get(0) == null ? null : row.get(0).toString());
            } catch (Exception e) {
                fpcyVo.setFplx(null);
            }
            try {
                fpcyVo.setFpdm(row.get(1) == null ? null : row.get(1).toString());
            } catch (Exception e) {
                fpcyVo.setFpdm(null);
            }
            try {
                fpcyVo.setFphm(row.get(2) == null ? null : row.get(2).toString());
            } catch (Exception e) {
                fpcyVo.setFphm(null);
            }
            try {
                fpcyVo.setKprq(row.get(3) == null ? null : row.get(3).toString());
            } catch (Exception e) {
                fpcyVo.setKprq(null);
            }
            try {
                fpcyVo.setJe(row.get(4) == null ? null : row.get(4).toString());
            } catch (Exception e) {
                fpcyVo.setJe(null);
            }
            try {
                fpcyVo.setJym(row.get(5) == null ? null : row.get(5).toString());
            } catch (Exception e) {
                fpcyVo.setJym(null);
            }
            try {
                fpcyVo.setBxry(row.get(6) == null ? null : row.get(6).toString());
            } catch (Exception e) {
                fpcyVo.setBxry(null);
            }
            cyList.add(fpcyVo);
        }
        for (int i = 0; i < cyList.size(); i++) {
            FpcyVo fpcy = cyList.get(i);
            String fplx = fpcy.getFplx();
            if (fplx == null || "".equals(fplx)) {
                msgg = "第" + (i + 2) + "行发票类型不能为空，请重新填写！";
                msg += msgg;
            } else if (!fplx.equals("01")&&!fplx.equals("02")&&!fplx.equals("12")) {
                msgg = "第" + (i + 2) + "行发票类型填写错误，请按格式填写！";
                msg += msgg;
            }
            String fpdm = fpcy.getFpdm();
            if (fpdm == null || "".equals(fpdm)) {
                msgg = "第" + (i + 2) + "行发票代码不能为空，请重新填写！";
                msg += msgg;
            } else if (fpdm.length()!= 12) {
                msgg = "第" + (i + 2) + "行发票代码长度有误！";
                msg += msgg;
            }
            String fphm = fpcy.getFphm();
            if (fphm == null || "".equals(fphm)) {
                msgg = "第" + (i + 2) + "行发票号码不能为空，请重新填写！";
                msg += msgg;
            } else if (fphm.length()!=8) {
                msgg = "第" + (i + 2) + "行发票号码长度有误！";
                msg += msgg;
            }
            String kprq = fpcy.getKprq();
            if (kprq == null || "".equals(kprq)) {
                msgg = "第" + (i + 2) + "行开票日期不能为空，请重新填写！";
                msg += msgg;
            } else {
                String[] s = kprq.split("-");
                if (s.length !=3) {
                    msgg = "第" + (i + 2) + "行开票日期格式有误！";
                    msg += msgg;
                }
            }
            String je = fpcy.getJe();
            if(fplx !=null && fplx.equals("01")){
                if (je == null || "".equals(je)) {
                    msgg = "第" + (i + 2) + "行发票类型为专票，不含税金额不能为空，请重新填写！";
                    msg += msgg;
                }
            }
            String jym = fpcy.getJym();
            if(fplx!= null &&(fplx.equals("02")||fplx.equals("12"))){
                if (jym == null || "".equals(jym)) {
                    msgg = "第" + (i + 2) + "行发票类型为普票，校验码后6位不能为空，请重新填写！";
                    msg += msgg;
                }
            }
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        // 没有异常，查验
        if ("".equals(msg)) {
            try {
                for (FpcyVo vo : cyList) {
                    String res = leshuiService.fpcy(vo.getFpdm(), vo.getFphm(),sdf1.parse(vo.getKprq()), vo.getJym(), vo.getJe(),"01",getGsdm(),vo.getBxry());
                    logger.info(JSON.toJSONString(res));
                    JSONObject resultJson = JSON.parseObject(res);
                   msg = resultJson.getString("resultMsg");//查验结果
                }
            } catch (ParseException e) {
                e.printStackTrace();
                msg="批量导入查验失败！";
            }

        }
        return msg;
    }
}
