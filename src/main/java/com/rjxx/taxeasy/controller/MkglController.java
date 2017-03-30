package com.rjxx.taxeasy.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rjxx.taxeasy.domains.XxXtxx;
import com.rjxx.taxeasy.service.XxXtxxService;
import com.rjxx.taxeasy.vo.Xtxxvo;
import com.rjxx.taxeasy.web.BaseController;

@Controller
@RequestMapping("/main")
public class MkglController extends BaseController {

	@Autowired
	private XxXtxxService xtxxService;


	@RequestMapping
	public String index() throws Exception {
		Integer yhid = getYhid();
		Map params = new HashMap<>();
		params.put("yhid", yhid);
		params.put("ydbz", "0");
		List<Xtxxvo> list1 = xtxxService.findAllByParams(params);
		if(list1 !=null && list1.size()>0){
			request.setAttribute("xxsl", list1.size());
		}else{
			request.setAttribute("xxsl", null);
		}
		params.put("limit", 2);
		List<Xtxxvo> xxList = xtxxService.findAllByParams(params);
		if(xxList !=null && xxList.size()>0){
			for(Xtxxvo item:xxList){
				String content = item.getContent();
				content = content.substring(0, 9)+"....";
				item.setContent(content);
			}
		}else{
			params.put("ydbz", "1");
			xxList = xtxxService.findAllByParams(params);
			if(xxList !=null && xxList.size()>0){
				for(Xtxxvo item:xxList){
					String content = item.getContent();
					content = content.substring(0, 9)+"....";
					item.setContent(content);
				}
			}			
		}
		request.setAttribute("xxList", xxList);
		return "main/index";
	}
	
	@RequestMapping(value = "/getXxmx")
	@ResponseBody
	public Map<String,Object> getContent(Integer id){
		Map<String,Object> result = new HashMap<>();
		Map params = new HashMap();
		params.put("id", id);
		XxXtxx item = xtxxService.findOneByParams(params);
		String title = item.getTitle();
		String content = item.getContent();
		SimpleDateFormat sdf= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lrsj = sdf.format(item.getLrsj());
		item.setYdbz("1");
		xtxxService.save(item);
		result.put("title", title);
		result.put("content", content);
		result.put("lrsj", lrsj);
		return result;
	}
  
}
