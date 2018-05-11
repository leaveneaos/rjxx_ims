package com.rjxx.taxeasy.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjxx.taxeasy.domains.Fpyltj;
import com.rjxx.taxeasy.domains.Fpzl;
import com.rjxx.taxeasy.domains.Xf;
import com.rjxx.taxeasy.service.FpyltjService;
import com.rjxx.taxeasy.service.FpzlService;
import com.rjxx.taxeasy.service.KplsvoService;
import com.rjxx.taxeasy.vo.Cxtjvo;
import com.rjxx.taxeasy.vo.KplsVO;
import com.rjxx.taxeasy.web.BaseController;
import com.rjxx.time.TimeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @author: zsq
 * @date: 2018/5/3 14:05
 * @describe: 发票用量统计优化--1
 */
@Controller
@RequestMapping("/sjdtjbb")
public class SjdtjbbController extends BaseController {
	@Autowired
	private KplsvoService ks;
	@Autowired
	private FpzlService fpzlService;
	@Autowired
	private FpyltjService fpyltjService;

	@RequestMapping
	public String index() {
		List<Fpzl> fpzlList = fpzlService.findAllByParams(new HashMap<>());
		request.setAttribute("fpzlList", fpzlList);
		List<Xf> xfs = getXfList();
		request.setAttribute("xfs", xfs);
		return "sjdtjbb/index";
	}
    
	@RequestMapping(value = "/getList")
	@ResponseBody
	public Map<String,Object> getItems(Integer xfid,Integer skpid,String kprqq,String kprqz)throws Exception{		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		Map<String,Object> yplResult = getYypl(xfid,skpid,kprqq,kprqz);
		//Map<String,Object> tqlResult = getYtql(xfid,skpid,kprqq,kprqz);
		List<Cxtjvo> tjList = new ArrayList<Cxtjvo>();
		if(kprqq==null||"".equals(kprqq)){
			result.put("data", tjList);
			return result;
		}
		/*for(String key:yplResult.keySet()){
			Cxtjvo item = new Cxtjvo();
			Integer kpl = (Integer) yplResult.get(key);
			Integer tql = 0;
			item.setKpny(key);
			item.setFpsl(kpl);
			item.setTqsl(tql);
			tjList.add(item);			
		}*/
		tjList = (List<Cxtjvo>) yplResult.get("list");
		if(null !=tjList && !tjList.isEmpty()){
			for(int i=0;i<tjList.size();i++){
				Cxtjvo cxtjvo = tjList.get(i);
				if(null ==cxtjvo.getSkpid() ||cxtjvo.getSkpid().equals("")){
					cxtjvo.setKpny("月汇总");
					cxtjvo.setFpzldm("99");
					cxtjvo.setFpzlmc("月合计");
					cxtjvo.setKpdmc("");
				}
			}
			Cxtjvo tmp = tjList.get(tjList.size()-1);
			tmp.setKpny("汇总");
			tmp.setFpzldm("99");
			tmp.setFpzlmc("合计");
		}
		result.put("data", tjList);
		return result;
	}


	/**
	 * 每月用票量的查询
	 * */
	@RequestMapping(value = "/getYplPlot")
	@ResponseBody
	public Map<String,Object> getYypl(Integer xfid,Integer skpid,String kprqq,String kprqz) throws Exception{
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		Map<String,Object> zpresult = new LinkedHashMap<String,Object>();	//专票map
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		ObjectMapper mapper = new ObjectMapper();

		if(kprqq==null||"".equals(kprqq)){
			result.put("dateArray",mapper.writeValueAsString(""));
			result.put("zpArray",mapper.writeValueAsString(""));
			result.put("zpjeArray",mapper.writeValueAsString(""));
			result.put("ppArray",mapper.writeValueAsString(""));
			result.put("ppjeArray",mapper.writeValueAsString(""));
			result.put("dpArray",mapper.writeValueAsString(""));
			result.put("dpjeArray",mapper.writeValueAsString(""));
			return result;
		}
		Map params = new HashMap<>();
		String gsdm = getGsdm();
		params.put("gsdm", gsdm);
		params.put("xfid", xfid);
		params.put("skpid", skpid);
		params.put("kprqq", kprqq);
		/*if(kprqz !=null && !"".equals(kprqz)){
			Date date = sdf.parse(kprqz);
			Calendar calender = Calendar.getInstance();
	        calender.setTime(date);
	        calender.add(Calendar.MONTH, 1);
	        kprqz = sdf.format(calender.getTime());
		}*/
		params.put("kprqz", kprqz);
		List<Cxtjvo> list = ks.findYypl(params);
//		List<Cxtjvo> list = fpyltjService.findYplByParams(params);
//		logger.info(JSON.toJSONString(list));
		List<Cxtjvo> zplist = new ArrayList<Cxtjvo>();
		List<Cxtjvo> pplist = new ArrayList<Cxtjvo>();
		List<Cxtjvo> dplist = new ArrayList<Cxtjvo>();
		Cxtjvo cxtjvo = null;

		String dateStr = kprqq;
		Long start = Long.valueOf(kprqq.replace("-",""));
		Long end = Long.valueOf(kprqz.replace("-",""));
		Long time = Long.valueOf(kprqq.replace("-",""));

		String dateArray =dateStr;

		while(start <= time&& time<end){
			Date date = sdf.parse(dateStr);
			Calendar calender = Calendar.getInstance();
			calender.setTime(date);
			calender.add(Calendar.MONTH, 1);
			dateStr = sdf.format(calender.getTime());
			dateArray = dateArray+"," + dateStr;
			time = Long.valueOf(dateStr.replaceAll("-", ""));
		}

		String dateArray2[] = dateArray.split(",");
		String zpArray[] = new String[dateArray2.length];
		String ppArray[] =new String[dateArray2.length];
		String dpArray[] =new String[dateArray2.length];
		String zpjeArray[] =new String[dateArray2.length];
		String ppjeArray[] =new String[dateArray2.length];
		String dpjeArray[] =new String[dateArray2.length];
		if(list !=null && list.size()>0){
			for(int j=0;j<dateArray2.length;j++){
				for(int t=0;t<list.size();t++){
					cxtjvo = new Cxtjvo(list.get(t));
					if(dateArray2[j].equals(cxtjvo.getKpny())){
						if(cxtjvo.getFpzldm().equals("01")){
							zpArray[j] = String.valueOf(cxtjvo.getFpsl());
							zpjeArray[j] = String.valueOf(cxtjvo.getJshj());
						}else if(cxtjvo.getFpzldm().equals("02")){
							ppArray[j] =String.valueOf(cxtjvo.getFpsl());
							ppjeArray[j] = String.valueOf(cxtjvo.getJshj());
						}else if(cxtjvo.getFpzldm().equals("12")){
							dpArray[j] =String.valueOf(cxtjvo.getFpsl());
							dpjeArray[j] = String.valueOf(cxtjvo.getJshj());
						}
					}
				}
				if(null == zpArray[j] || zpArray[j].equals("")){
					zpArray[j] = "0";
				}
				if(null == zpjeArray[j] || zpjeArray[j].equals("")){
					zpjeArray[j] = "0";
				}
				if(null == ppArray[j] || ppArray[j].equals("")){
					ppArray[j] = "0";
				}
				if(null == ppjeArray[j] || ppjeArray[j].equals("")){
					ppjeArray[j] = "0";
				}
				if(null == dpArray[j] || dpArray[j].equals("")){
					dpArray[j] = "0";
				}
				if(null == dpjeArray[j] || dpjeArray[j].equals("")){
					dpjeArray[j] = "0";
				}
			}
		}
		result.put("dateArray",dateArray2);
		result.put("zpArray",zpArray);
		result.put("zpjeArray",zpjeArray);
		result.put("ppArray",ppArray);
		result.put("ppjeArray",ppjeArray);
		result.put("dpArray",dpArray);
		result.put("dpjeArray",dpjeArray);
		result.put("list",list);
		return result;
	}


}
