package com.rjxx.taxeasy.controller;


import com.rjxx.taxeasy.config.ServiceException;
import com.rjxx.taxeasy.domains.TaskInfo;
import com.rjxx.taxeasy.service.TaskService;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quartz")
public class QuartzController extends BaseController {

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
	@RequestMapping(value = "/getTaskList")
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

	@RequestMapping(value="pause/{jobName}/{jobGroup}")
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
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */

	@RequestMapping(value="resume/{jobName}/{jobGroup}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Map resume(@PathVariable String jobName, @PathVariable String jobGroup){
		  Map map =new HashMap();
		try {
			taskService.resume(jobName, jobGroup);
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
	 * 保存任务
	 * @param info
	 * @return
	 */

	@RequestMapping(value="save")
	@ResponseBody
	public Map save(String bz,String jobName,String jobGroup,String JobDescription,String CronExpression ){
		Map map =new HashMap();
		TaskInfo info=new TaskInfo();
		info.setJobName(jobName);
		info.setJobGroup(jobGroup);
		info.setJobDescription(JobDescription);
		info.setCronExpression(CronExpression);
		try {
			if(bz.equals("0")) {
				taskService.addJob(info);
			}else{
				taskService.edit(info);
			}
			map.put("code","0");
		} catch (ServiceException e) {
			map.put("code","1");
			map.put("errortext",e.getMessage());
		}
		return map;
	}
}
