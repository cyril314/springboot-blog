package com.fit.web.admin;

import com.alibaba.fastjson.JSONObject;
import com.fit.base.BaseController;
import com.fit.base.PageBean;
import com.fit.entity.TBlog;
import com.fit.lucene.BlogIndex;
import com.fit.service.TBlogService;
import com.fit.util.FastJsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 管理员博客Controller层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController extends BaseController {

    @Resource
    private TBlogService blogService;

    // 博客索引
    private BlogIndex blogIndex = new BlogIndex();

    /**
     * 添加或者修改博客信息
     *
     * @param blog
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(TBlog blog, HttpServletResponse response) throws Exception {
        int resultTotal = 0; // 操作的记录条数
        if (blog.getId() == null) {
            resultTotal = blogService.save(blog);
            blogIndex.addIndex(blog); // 添加博客索引
        } else {
            resultTotal = blogService.update(blog);
            blogIndex.updateIndex(blog); // 更新博客索引
        }
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
     * 分页查询博客信息
     *
     * @param page
     * @param rows
     * @param s_blog
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows,
                       TBlog s_blog, HttpServletResponse response) throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", formatLike(s_blog.getTitle()));
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<TBlog> blogList = blogService.findList(map);
        int total = blogService.findCount(map);
        JSONObject result = new JSONObject();
        result.put("rows", blogList);
        result.put("total", total);
        writeJson(response, result.toJSONString());
        return null;
    }

    /**
     * 删除博客信息
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
            blogService.delete(Integer.parseInt(idsStr[i]));
            blogIndex.deleteIndex(idsStr[i]); // 删除对应博客的索引
        }
        JSONObject result = new JSONObject();
        result.put("success", true);
        writeJson(response, result.toJSONString());
        return null;
    }

    /**
     * 通过ID查找实体
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/findById")
    public String findById(@RequestParam(value = "id") String id, HttpServletResponse response) throws Exception {
        TBlog blog = blogService.get(Integer.parseInt(id));
        writeJson(response, FastJsonUtil.toJSONString(blog));
        return null;
    }
}