package com.fit.web;

import com.fit.base.BaseController;
import com.fit.entity.TBlogger;
import com.fit.service.TBloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 博主Controller层
 *
 * @author java1234_小锋
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController extends BaseController {

    @Resource
    private TBloggerService bloggerService;

    /**
     * 用户登录
     *
     * @param blogger
     * @param request
     * @return
     */
    @RequestMapping({"/login", "/login.do", "/login.html"})
    public String login(TBlogger blogger, HttpServletRequest request) {
        if (blogger.getUsername() != null) {
            try {
                UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUsername(),
                        new Md5Hash(blogger.getPassword(), "java1234").toString());
                Subject subject = SecurityUtils.getSubject();
                subject.login(token); // 登录验证
                return "/admin/main";
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("blogger", blogger);
                request.setAttribute("errorInfo", "用户名或密码错误！");
            }
        }
        return "login";
    }

    /**
     * 查找博主信息
     */
    @RequestMapping({"/aboutMe", "/aboutMe.html"})
    public ModelAndView aboutMe() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("blogger", bloggerService.get(1));
        mav.addObject("mainPage", "foreground/blogger/info.jsp");
        mav.addObject("pageTitle", "关于博主_Java开源博客系统");
        mav.setViewName("mainTemp");
        return mav;
    }
}