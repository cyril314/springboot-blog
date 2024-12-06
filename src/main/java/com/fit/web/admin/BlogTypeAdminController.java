package com.fit.web.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fit.base.BaseController;
import com.fit.base.PageBean;
import com.fit.entity.TBlogtype;
import com.fit.service.TBlogtypeService;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 管理员博客类别Controller层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController extends BaseController {

    @Resource
    private TBlogtypeService blogTypeService;

    /**
     * 分页查询博客类别信息
     *
     * @param page
     * @param rows
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows, HttpServletResponse response) throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<TBlogtype> blogTypeList = blogTypeService.findList(map);
        int total = blogTypeService.findCount(map);
        JSONObject result = new JSONObject();
        result.put("rows", blogTypeList);
        result.put("total", total);
        writeJson(response, result.toJSONString());
        return null;
    }

    /**
     * 添加或者修改博客类别信息
     *
     * @param blogType
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(TBlogtype blogType, HttpServletResponse response) throws Exception {
        int resultTotal = blogTypeService.save(blogType);
        JSONObject result = new JSONObject();
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        writeJson(response, result.toJSONString());
        return null;
    }

    /**
     * 删除博客类别信息
     *
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "ids") String ids, HttpServletResponse response) throws Exception {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            blogTypeService.delete(Integer.parseInt(idsStr[i]));
        }
        JSONObject result = new JSONObject();
        result.put("success", true);
        writeJson(response, result.toJSONString());
        return null;
    }
}