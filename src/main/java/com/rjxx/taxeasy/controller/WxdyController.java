package com.rjxx.taxeasy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rjxx.taxeasy.bizcomm.utils.WeixinCommon;
import com.rjxx.taxeasy.domains.DyYhlxfs;
import com.rjxx.taxeasy.service.DyYhlxfsService;
import com.rjxx.taxeasy.web.BaseController;


@Controller
@RequestMapping("/wxdy")
public class WxdyController extends BaseController{
	
	@Autowired
	private DyYhlxfsService yhlxfsService;
	
	/**
	 * 二维码请求的url
	 * */
	@RequestMapping(value = "/getUrl")
	@ResponseBody
	public Map<String, Object> getUrl(String url){
		Map<String, Object> result = new HashMap<String, Object>();
		Integer yhid = this.getYhid();
		result.put("yhid", yhid);
		url = url.substring(0, url.lastIndexOf("/")+1)+"wxdy/saveYhdyxx?yhid="+yhid;
		result.put("url", url);
		result.put("success", true);
		return result;
	}
	
	/**
	 * 扫描二维码跳转页面
	 * */
	@RequestMapping(value = "/saveYhdyxx")
	@ResponseBody
	public void saveFpj(Integer yhid) throws IOException{
		session.setAttribute("yhid", yhid);
		response.sendRedirect(request.getContextPath() + "/sccg.html?_t=" + System.currentTimeMillis());
		System.out.println(request.getContextPath());
	}
	
	/**
	 * 生成带参数的二维码
	 * 
	 * */
	@RequestMapping(value = "/getEwm")
	@ResponseBody
	public Map<String,Object> getEwm(){
		int yhid = this.getYhid();
		//临时二维码
		String jsonMsg = "{\"expire_seconds\": 3600, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+yhid+"}}}";
		//永久二维码
		//String jsonMsg = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\":yhid="+yhid+"}}}";
		Map<String,Object> result = new HashMap<String,Object>();
		WeixinCommon cwt = new WeixinCommon();
		String ewmImg = cwt.getQr(jsonMsg);
		result.put("success", true);
		result.put("id", ewmImg);
		return result;
	}
	
	/**
	 * 微信回调方法推送微信消息
	 * */
	@RequestMapping(value = "/wxCallBack",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void wxCallBack(String xml,HttpServletRequest request,HttpServletResponse response){
		System.out.println(xml);
		WeixinCommon wxc = new WeixinCommon();
		Map<String,Object> result = wxc.wxCallBack(xml);
		if(result !=null && !result.isEmpty()){
			String openid = (String) result.get("openid");
			Integer yhid = (Integer)result.get("value");
			Map params = new HashMap<>();
			params.put("yhid", yhid);
			DyYhlxfs lxfs = yhlxfsService.findOneByParams(params);
			if(lxfs==null){
				DyYhlxfs item = new DyYhlxfs();
				item.setYhid(yhid);
				item.setWxOpenid(openid);
				item.setYxbz("1");
				yhlxfsService.save(item);
			}else{
				lxfs.setWxOpenid(openid);
				yhlxfsService.save(lxfs);
			}
			//订阅成功，推送微信消息
			Map<Object, Object> data1 = new HashMap<>();
			Map<Object, Object> data2 = new HashMap<>();		
			data2.put("value", "恭喜你微信订阅成功！");
			data1.put("first", data2);
			String template_id = "PrajnuAdIL_icjPZA5TgdqcNZUoOavCDyH1B29TstzY";
			String url = null;
			wxc.sentWxMsg(data1, openid, template_id, url);     //微信消息推送方法
		}else{
			System.out.println("没有读取到微信回调信息！");
		}
        String echostr = request.getParameter("echostr");                   
        PrintWriter out; 
        try { 
            out = response.getWriter(); 
            out.println(echostr); 
            out.close(); 
            response.flushBuffer(); 
        } catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        }
	}
	
	
	
	/*@RequestMapping(value = "/getToken")
    @ResponseBody
    public Map hqtk(String apiurl, String appid, String code) {
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取token
        String turl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx9abc729e2b4637ee&secret=6415ee7a53601b6a0e8b4ac194b382eb&code=" + code + "&grant_type=authorization_code";
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(turl);
        ObjectMapper jsonparer = new ObjectMapper();// 初始化解析json格式的对象
        try {
            HttpResponse res = client.execute(get);
            String responseContent = null; // 响应内容
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            Map map = jsonparer.readValue(responseContent, Map.class);
            // 将json字符串转换为json对象
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (map.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid
                    result.put("success", false);
                    result.put("msg", "获取微信token失败,错误代码为" + map.get("errcode"));
                    return result;
                } else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                    session.setAttribute("access_token", map.get("access_token"));
                    session.setAttribute("openid", map.get("openid"));
                    map.put("success", true);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "获取微信token失败" + e.getMessage());
        } finally {
            // 关闭连接 ,释放资源
            client.getConnectionManager().shutdown();
        }
        return result;
    }*/

}
