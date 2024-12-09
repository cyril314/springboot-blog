package com.fit.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2024/12/9
 */
@Controller
@RequestMapping("/admin")
public class PageController {

    @RequestMapping("/writeBlog")
    public String writeBlog() {
        return "admin/writeBlog";
    }

    @RequestMapping("/modifyInfo")
    public String modifyInfo() {
        return "admin/modifyInfo";
    }

    @RequestMapping("/modifyBlog")
    public String modifyBlog() {
        return "admin/modifyBlog";
    }

    @RequestMapping("/linkManage")
    public String linkManage() {
        return "admin/linkManage";
    }

    @RequestMapping("/commentReview")
    public String commentReview() {
        return "admin/commentReview";
    }

    @RequestMapping("/commentManage")
    public String commentManage() {
        return "admin/commentManage";
    }

    @RequestMapping("/blogTypeManage")
    public String blogTypeManage() {
        return "admin/blogTypeManage";
    }

    @RequestMapping("/blogManage")
    public String blogManage() {
        return "admin/blogManage";
    }

}
