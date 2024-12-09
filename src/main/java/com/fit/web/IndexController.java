package com.fit.web;

import com.fit.base.BaseController;
import com.fit.base.PageBean;
import com.fit.base.PatternAndView;
import com.fit.entity.TBlog;
import com.fit.service.TBlogService;
import com.fit.util.PageUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页Controller
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private TBlogService blogService;

    /**
     * 请求主页
     */
    @RequestMapping({"/", "/index", "/index.html"})
    public ModelAndView index(String page, String typeId, String releaseDateStr, HttpServletRequest request) throws Exception {
        ModelAndView mav = new PatternAndView("mainTemp");
        if (isEmpty(page)) {
            page = "1";
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("typeId", typeId);
        map.put("releaseDateStr", releaseDateStr);
        List<TBlog> blogList = blogService.findList(map);
        for (TBlog blog : blogList) {
            List<String> imagesList = blog.getImagesList();
            String blogInfo = blog.getContent();
            Document doc = Jsoup.parse(blogInfo);
            Elements jpgs = doc.select("img[src$=.jpg]"); //　查找扩展名是jpg的图片
            for (int i = 0; i < jpgs.size(); i++) {
                Element jpg = jpgs.get(i);
                imagesList.add(jpg.toString());
                if (i == 2) {
                    break;
                }
            }
        }
        mav.addObject("blogList", blogList);
        StringBuffer param = new StringBuffer(); // 查询参数
        if (isNotEmpty(typeId)) {
            param.append("typeId=" + typeId + "&");
        }
        if (isNotEmpty(releaseDateStr)) {
            param.append("releaseDateStr=" + releaseDateStr + "&");
        }
        mav.addObject("pageCode", PageUtil.genPagination(request.getContextPath() + "/index.html", blogService.findCount(map),
                Integer.parseInt(page), 10, param.toString()));
        mav.addObject("mainPage", "foreground/blog/list.jsp");
        mav.addObject("pageTitle", "Java开源博客系统");
        return mav;
    }

    /**
     * 源码下载
     *
     * @return
     * @throws Exception
     */
    @RequestMapping({"/download", "/download.html"})
    public ModelAndView download() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage", "foreground/system/download.jsp");
        mav.addObject("pageTitle", "本站源码下载页面_Java开源博客系统");
        mav.setViewName("mainTemp");
        return mav;
    }
}
