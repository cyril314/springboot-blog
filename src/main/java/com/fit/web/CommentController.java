package com.fit.web;

import com.alibaba.fastjson.JSONObject;
import com.fit.base.BaseController;
import com.fit.entity.TBlog;
import com.fit.entity.TComment;
import com.fit.service.TBlogService;
import com.fit.service.TCommentService;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 评论Controller层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Resource
    private TCommentService commentService;

    @Resource
    private TBlogService blogService;

    /**
     * 添加或者修改评论
     *
     * @param comment
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(TComment comment, @RequestParam("imageCode") String imageCode, HttpServletRequest request,
                       HttpServletResponse response, HttpSession session) throws Exception {
        String sRand = (String) session.getAttribute("sRand"); // 获取系统生成的验证码
        JSONObject result = new JSONObject();
        int resultTotal = 0; // 操作的记录条数
        if (!imageCode.equals(sRand)) {
            result.put("success", false);
            result.put("errorInfo", "验证码填写错误！");
        } else {
            String userIp = request.getRemoteAddr(); // 获取用户IP
            comment.setUserip(userIp);
            if (comment.getId() == null) {
                resultTotal = commentService.save(comment);
                // 该博客的回复次数加1
                TBlog blog = blogService.get(comment.getBlogid());
                blog.setReplyhit(blog.getReplyhit() + 1);
                blogService.update(blog);
            }
            if (resultTotal > 0) {
                result.put("success", true);
            } else {
                result.put("success", false);
            }
        }
        writeJson(response, result.toJSONString());
        return null;
    }
}
