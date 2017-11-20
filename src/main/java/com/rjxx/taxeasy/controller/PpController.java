package com.rjxx.taxeasy.controller;

import com.rjxx.comm.mybatis.Pagination;
import com.rjxx.taxeasy.domains.Pp;
import com.rjxx.taxeasy.filter.SystemControllerLog;
import com.rjxx.taxeasy.service.PpService;
import com.rjxx.taxeasy.vo.SkpVo;
import com.rjxx.taxeasy.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017-11-16.
 */
@Controller
@RequestMapping("/pp")
public class PpController  extends BaseController {

    @Autowired
    private PpService ppService;


    @RequestMapping
    @SystemControllerLog(description = "品牌管理", key = "")
    public String index() throws Exception {
        session.setAttribute("xfs", getXfList());
        session.setAttribute("xf", getXfList().get(0));
        return "pp/index";
    }

    /**
     * 分页查询税控设备信息
     *
     * @param length
     * @param start
     * @param draw
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getpplist")
    @ResponseBody
    public Map getsksblist(int length, int start, int draw, String ppmc,  String txt) throws Exception {
        Pagination pagination = new Pagination();
        pagination.setPageNo(start / length + 1);
        pagination.setPageSize(length);

        pagination.addParam("gsdm", getGsdm());
        pagination.addParam("ppmc", ppmc);
        pagination.addParam("orderBy", "lrsj");
        pagination.addParam("xfs", getXfList());
        List<Pp> list = ppService.findByPage(pagination);
        int total = pagination.getTotalRecord();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("recordsTotal", total);
        result.put("recordsFiltered", total);
        result.put("draw", draw);
        result.put("data", list);
        return result;
    }

}
