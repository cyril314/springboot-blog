package com.fit.web;

import com.fit.base.BaseController;
import com.fit.entity.TBlog;
import com.fit.lucene.BlogIndex;
import com.fit.service.TBlogService;
import com.fit.service.TCommentService;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 博客Controller层
 */
@Controller
@RequestMapping("/blog")
public class BlogController extends BaseController {

    @Resource
    private TBlogService blogService;

    @Resource
    private TCommentService commentService;

    // 博客索引
    private BlogIndex blogIndex = new BlogIndex();

    /**
     * 请求博客详细信息
     */
    @RequestMapping("/articles/{id}")
    public ModelAndView details(@PathVariable("id") Integer id, HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        TBlog blog = blogService.get(id);
        String keyWords = blog.getKeyword();
        if (isNotEmpty(keyWords)) {
            String arr[] = keyWords.split(" ");
            mav.addObject("keyWords", filterWhite(Arrays.asList(arr)));
        } else {
            mav.addObject("keyWords", null);
        }
        mav.addObject("blog", blog);
        blog.setClickhit(blog.getClickhit() + 1); // 博客点击次数加1
        blogService.update(blog);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("blogId", blog.getId());
        map.put("state", 1); // 查询审核通过的评论
        mav.addObject("commentList", commentService.findList(map));
//        mav.addObject("pageCode", this.genUpAndDownPageCode(blogService.getLastBlog(id), blogService.getNextBlog(id), request.getServletContext().getContextPath()));
        mav.addObject("mainPage", "foreground/blog/view.jsp");
        mav.addObject("pageTitle", blog.getTitle() + "_Java开源博客系统");
        mav.setViewName("mainTemp");
        return mav;
    }

    /**
     * 根据关键字查询相关博客信息
     *
     * @param q
     * @return
     * @throws Exception
     */
    @RequestMapping("/q")
    public ModelAndView search(@RequestParam(value = "q", required = false) String q, @RequestParam(value = "page", required = false) String page, HttpServletRequest request) throws Exception {
        if (isEmpty(page)) {
            page = "1";
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage", "foreground/blog/result.jsp");
        List<TBlog> blogList = blogIndex.searchBlog(q.trim());
        Integer toIndex = blogList.size() >= Integer.parseInt(page) * 10 ? Integer.parseInt(page) * 10 : blogList.size();
        mav.addObject("blogList", blogList.subList((Integer.parseInt(page) - 1) * 10, toIndex));
        mav.addObject("pageCode", this.genUpAndDownPageCode(Integer.parseInt(page), blogList.size(), q, 10, request.getServletContext().getContextPath()));
        mav.addObject("q", q);
        mav.addObject("resultTotal", blogList.size());
        mav.addObject("pageTitle", "搜索关键字'" + q + "'结果页面_Java开源博客系统");
        mav.setViewName("mainTemp");
        return mav;
    }

    /**
     * 获取下一篇博客和下一篇博客代码
     *
     * @param lastBlog
     * @param nextBlog
     * @return
     */
    private String genUpAndDownPageCode(TBlog lastBlog, TBlog nextBlog, String projectContext) {
        StringBuffer pageCode = new StringBuffer();
        if (lastBlog == null || lastBlog.getId() == null) {
            pageCode.append("<p>上一篇：没有了</p>");
        } else {
            pageCode.append("<p>上一篇：<a href='" + projectContext + "/blog/articles/" + lastBlog.getId() + ".html'>" + lastBlog.getTitle() + "</a></p>");
        }
        if (nextBlog == null || nextBlog.getId() == null) {
            pageCode.append("<p>下一篇：没有了</p>");
        } else {
            pageCode.append("<p>下一篇：<a href='" + projectContext + "/blog/articles/" + nextBlog.getId() + ".html'>" + nextBlog.getTitle() + "</a></p>");
        }
        return pageCode.toString();
    }

    /**
     * 获取上一页，下一页代码 查询博客用到
     *
     * @param page           当前页
     * @param totalNum       总记录数
     * @param q              查询关键字
     * @param pageSize       每页大小
     * @param projectContext
     * @return
     */
    private String genUpAndDownPageCode(Integer page, Integer totalNum, String q, Integer pageSize, String projectContext) {
        long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        StringBuffer pageCode = new StringBuffer();
        if (totalPage == 0) {
            return "";
        } else {
            pageCode.append("<nav>");
            pageCode.append("<ul class='pager' >");
            if (page > 1) {
                pageCode.append("<li><a href='" + projectContext + "/blog/q.html?page=" + (page - 1) + "&q=" + q + "'>上一页</a></li>");
            } else {
                pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
            }
            if (page < totalPage) {
                pageCode.append("<li><a href='" + projectContext + "/blog/q.html?page=" + (page + 1) + "&q=" + q + "'>下一页</a></li>");
            } else {
                pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
            }
            pageCode.append("</ul>");
            pageCode.append("</nav>");
        }
        return pageCode.toString();
    }
}