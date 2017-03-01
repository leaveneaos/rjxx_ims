package com.rjxx.taxeasy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjxx.taxeasy.domains.Gfxx;
import com.rjxx.taxeasy.web.BaseController;

/**
 * 通过apache solr 查询出所有的购方信息
 *
 * @author kk
 */
@Controller
@RequestMapping("/gfxxhq")
public class GfxxSelectController extends BaseController {

	private HttpSolrClient solrClient;

	 @Value("${apache_solr_url:}")
	 private String solrUrl;
	// 初始化客户端
	public void before() {
		solrClient = new HttpSolrClient(solrUrl);// 由于目前只有一个Core，就直接卸载url上拉，不然CRUD操作前都要声明被操作的Core
	}

	// 提交，关闭会话
	public void after() throws IOException, SolrServerException {
		solrClient.commit();
		solrClient.close();

	}

	public List<Gfxx> query(String mcszmsx) throws IOException, SolrServerException {
		StringBuilder csz = new StringBuilder();
		if (mcszmsx.equals("")) {
			csz.append("mcszmsx:0000") ;
		} else {
			csz.append("mcszmsx:*").append(mcszmsx).append("*").append(" or gfmc:*").append(mcszmsx).append("*");
		}

		// 构造查询参数
		SolrQuery query = new SolrQuery();
		query.setQuery(csz.toString());
		query.setHighlight(true); // 开启高亮
		query.setHighlightFragsize(10); // 返回的字符个数
		query.setHighlightRequireFieldMatch(true);
		query.setHighlightSimplePre("<font color=\"red\">"); // 前缀
		query.setHighlightSimplePost("</font>"); // 后缀
		// query.setParam("hl.fl", "name"); //高亮字段
		query.setStart(0); // 分页参数
		query.setRows(15); // 分页参数
		List<Gfxx> gfxxList = new ArrayList();
		// 获得查询结果
		try {
			solrClient.setConnectionTimeout(1000*10);
			solrClient.setSoTimeout(1000*10);
			QueryResponse response = solrClient.query(query);
			List<SolrDocument> t = response.getResults();
			SolrDocument t2 = new SolrDocument();
			
			for (int i = 0; i < t.size(); i++) {
				Gfxx gfxx = new Gfxx();
				t2 = t.get(i);
				gfxx.setGfsh(t2.get("gfsh").toString());
				gfxx.setGfmc(t2.get("gfmc").toString());
				gfxx.setGfdz(t2.get("gfdz").toString());
				gfxx.setGfdh(t2.get("gfdh").toString());
				gfxx.setGfyh(t2.get("gfyh").toString());
				gfxx.setGfyhzh(t2.get("gfyhzh").toString());
				gfxx.setMcszmsx(t2.get("mcszmsx").toString());
				gfxx.setId(Integer.valueOf(t2.get("id").toString()));
				gfxxList.add(gfxx);
			}
		} catch (Exception e) {
			return gfxxList;
		}

		System.out.println("记录条数：" + gfxxList.size());
		return gfxxList;
	}
	@RequestMapping(value = "/getGfList")
	@ResponseBody
	public Map getGfList(){
		String mcszmsx = request.getParameter("mcszmsx");
		List<Gfxx> resultList = new ArrayList();
		boolean success = false;
		Map resultMap = new HashMap();
		try {
			before();
			resultList = query(mcszmsx);
			after();
			success=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMap.put("success", success) ;
		resultMap.put("resultList", resultList) ;
		
		return resultMap;
	}
	
	
	//测试方法
	public static void main(String args[]) {
		GfxxSelectController t = new GfxxSelectController();
		try {
			t.before();
			t.query("shjy");
			t.after();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
