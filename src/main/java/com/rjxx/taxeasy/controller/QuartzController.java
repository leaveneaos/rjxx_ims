package com.rjxx.taxeasy.controller;


import com.rjxx.taxeasy.configuration.ServiceException;
import com.rjxx.taxeasy.configuration.TaskService;
import com.rjxx.taxeasy.domains.TaskInfo;
import com.rjxx.taxeasy.web.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quartz")
public class QuartzController extends BaseController{

	@Autowired
	private TaskService taskService;
	@RequestMapping
	public String index() throws Exception {

		return "quartz/index";
	}

	/**
	 * 获取列表
	 * @param length
	 * @param start
	 * @param draw
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTaskList" ,produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getItems(int length, int start, int draw) throws Exception {
		Map result = new HashMap<>();
		List<TaskInfo> infos = taskService.list();
		result.put("recordsTotal", infos.size());
		result.put("recordsFiltered", infos.size());
		result.put("draw", draw);
		result.put("data", infos);
		/*String json= JSON.toJSON(result).toString();
		System.out.println(json);*/
		return result;
	}

	/**
	 * 暂停任务
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */

	@RequestMapping(value="pause/{jobName}/{jobGroup}" ,produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map pause( String jobName,  String jobGroup){
		Map map=new HashMap();
		try {
			taskService.pause(jobName, jobGroup);
			map.put("code","0");
		} catch (ServiceException e) {
			map.put("code","1");
			map.put("errortext",e.getMessage());
		}
		return map;
	}

	/**
	 * 开始任务
	 * @param
	 * @param
	 * @return
	 */

	@RequestMapping(value="/resume", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map resume(){
		  Map map =new HashMap();
		try {
			String jobName="com.rjxx.taxeasy.job.JsapiticketJob";
			String jobName1="com.rjxx.taxeasy.job.SuiteTokenGenerateJob";
			taskService.resume(jobName, "dingding");
			taskService.resume(jobName1, "dingding");

			map.put("code","0");
		} catch (ServiceException e) {
			map.put("code","1");
			map.put("errortext",e.getMessage());

		}
		return map;

	}

	/**
	 * 删除任务
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */

	@RequestMapping(value="delete/{jobName}/{jobGroup}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map delete(@PathVariable String jobName, @PathVariable String jobGroup){

		Map map =new HashMap();

		try {
			taskService.delete(jobName, jobGroup);
			map.put("code","0");
		} catch (ServiceException e) {
			map.put("code","1");
			map.put("errortext",e.getMessage());
		}
		return map;
	}
	/**
	 * 更新任务
	 * @param
	 * @return
	 */

	@RequestMapping(value="edit",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map edit(){
		Map map =new HashMap();
		TaskInfo info=new TaskInfo();
		info.setJobName("com.rjxx.taxeasy.job.JsapiticketJob");
		info.setJobGroup("dingding");
		info.setJobDescription("生成或者更新JSapiticket");
		info.setCronExpression("0 */30 * * * ?");
		TaskInfo info1=new TaskInfo();
		info1.setJobName("com.rjxx.taxeasy.job.SuiteTokenGenerateJob");
		info1.setJobGroup("dingding");
		info1.setJobDescription("生成或者更新套件token");
		info1.setCronExpression("0 */30 * * * ?");
		try {

			taskService.edit(info);
			taskService.edit(info1);
			map.put("code","0");
		} catch (ServiceException e) {
			map.put("code","1");
			map.put("errortext",e.getMessage());
		}
		return map;
	}
	/**
	 * 保存任务
	 * @param
	 * @return
	 */
	@RequestMapping(value="save",produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map save(){
		Map map =new HashMap();
		TaskInfo info=new TaskInfo();
		info.setJobName("com.rjxx.taxeasy.job.JsapiticketJob");
		info.setJobGroup("dingding");
		info.setJobDescription("生成或者更新JSapiticket");
		info.setCronExpression("0 0 0/1 * * ? ");
		TaskInfo info1=new TaskInfo();
		info1.setJobName("com.rjxx.taxeasy.job.SuiteTokenGenerateJob");
		info1.setJobGroup("dingding");
		info1.setJobDescription("生成或者更新套件token");
		info1.setCronExpression("0 */30 * * * ?");
		TaskInfo info2=new TaskInfo();
		info2.setJobName("com.rjxx.taxeasy.job.DingTskpInfoJob");
		info2.setJobGroup("dingding");
		info2.setJobDescription("推送开票信息任务");
		info2.setCronExpression("0 0/5 * * * ? ");
		try {
				//taskService.addJob(info);
			    //taskService.addJob(info1);
			    taskService.addJob(info2);
			map.put("code","0");
		} catch (ServiceException e) {
			map.put("code","1");
			map.put("errortext",e.getMessage());
		}
		return map;
	}
	
	public static void main(String []args){
		TaskInfo info=new TaskInfo();
		info.setJobName("com.rjxx.taxeasy.job.JsapiticketJob");
		info.setJobGroup("dingding");
		info.setJobDescription("生成或者更新JSapiticket");
		info.setCronExpression("0 0 0/1 * * ?");
		TaskService taskService=new TaskService();
		taskService.addJob(info);
	}
}
