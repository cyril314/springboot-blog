package com.fit.web.admin;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.fit.base.BaseController;
import com.fit.entity.TBlogger;
import com.fit.service.TBloggerService;
import com.fit.util.DateUtils;
import com.fit.util.FastJsonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 管理员博主Controller层
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController extends BaseController {

    @Resource
    private TBloggerService bloggerService;

    /**
     * 修改博主信息
     *
     * @param imageFile
     * @param blogger
     * @param request
     * @param response
     */
    @RequestMapping("/save")
    public void save(@RequestParam("imageFile") MultipartFile imageFile, TBlogger blogger, HttpServletRequest request,
                     HttpServletResponse response) throws Exception {
        if (!imageFile.isEmpty()) {
            String filePath = request.getServletContext().getRealPath("/");
            String imageName = DateUtils.getOrderNum() + "." + imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(filePath + "static/userImages/" + imageName));
            blogger.setImagename(imageName);
        }
        int resultTotal = bloggerService.update(blogger);
        StringBuffer result = new StringBuffer();
        if (resultTotal > 0) {
            result.append("<script language='javascript'>alert('修改成功！');</script>");
        } else {
            result.append("<script language='javascript'>alert('修改失败！');</script>");
        }
        writeJson(response, result);
    }

    /**
     * 查询博主信息
     */
    @RequestMapping({"/find", "/find.do"})
    public String find(HttpServletResponse response) {
        TBlogger blogger = bloggerService.get(1);
        writeJson(response, FastJsonUtil.toJSONString(blogger));
        return null;
    }

    /**
     * 修改博主密码
     *
     * @param newPassword
     * @param response
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(String newPassword, HttpServletResponse response) {
        TBlogger blogger = new TBlogger();
        blogger.setPassword(new Md5Hash(newPassword, "java1234").toString());
        int resultTotal = bloggerService.update(blogger);
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
     * 注销
     */
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/login.jsp";
    }
}