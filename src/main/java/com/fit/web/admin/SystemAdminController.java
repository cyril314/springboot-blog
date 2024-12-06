package com.fit.web.admin;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.fit.base.BaseController;
import com.fit.entity.TBlog;
import com.fit.entity.TBlogger;
import com.fit.entity.TBlogtype;
import com.fit.entity.TLink;
import com.fit.service.TBlogService;
import com.fit.service.TBloggerService;
import com.fit.service.TBlogtypeService;
import com.fit.service.TLinkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员系统Controller层
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController extends BaseController {

    @Resource
    private TBloggerService bloggerService;

    @Resource
    private TBlogtypeService blogTypeService;

    @Resource
    private TBlogService blogService;

    @Resource
    private TLinkService linkService;

    /**
     * 刷新系统缓存
     */
    @RequestMapping("/refreshSystem")
    public String refreshSystem(HttpServletResponse response, HttpServletRequest request) throws Exception {
        TBlogger blogger = bloggerService.get(1); // 查询博主信息
        blogger.setPassword(null);
        request.setAttribute("blogger", blogger);
        Map<String, Object> map = new HashMap<String, Object>();
        List<TBlogtype> blogTypeCountList = blogTypeService.findList(map); // 查询博客类别以及博客的数量
        request.setAttribute("blogTypeCountList", blogTypeCountList);

        List<TBlog> blogCountList = blogService.findList(map); // 根据日期分组查询博客
        request.setAttribute("blogCountList", blogCountList);

        List<TLink> linkList = linkService.findList(map); // 获取所有友情链接
        request.setAttribute("linkList", linkList);

        JSONObject result = new JSONObject();
        result.put("success", true);
        writeJson(response, result.toJSONString());
        return null;
    }
}