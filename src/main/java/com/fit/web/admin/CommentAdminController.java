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
import com.fit.entity.TComment;
import com.fit.service.TCommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 管理员评论Controller层
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController extends BaseController {

    @Resource
    private TCommentService commentService;

    /**
     * 分页查询评论信息
     *
     * @param page
     * @param rows
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows, @RequestParam(value = "state", required = false) String state, HttpServletResponse response) throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("state", state); // 评论状态
        List<TComment> commentList = commentService.findList(map);
        int total = commentService.findCount(map);
        JSONObject result = new JSONObject();
        result.put("rows", commentList);
        result.put("total", total);
        writeJson(response, result.toJSONString());
        return null;
    }

    /**
     * 删除评论信息
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
            commentService.delete(Integer.parseInt(idsStr[i]));
        }
        JSONObject result = new JSONObject();
        result.put("success", true);
        writeJson(response, result.toJSONString());
        return null;
    }

    /**
     * 评论审核
     *
     * @param ids
     * @param state
     * @return
     * @throws Exception
     */
    @RequestMapping("/review")
    public String review(@RequestParam(value = "ids") String ids, @RequestParam(value = "state") Integer state, HttpServletResponse response) throws Exception {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            TComment comment = new TComment();
            comment.setState(state);
            comment.setId(Integer.parseInt(idsStr[i]));
            commentService.update(comment);
        }
        JSONObject result = new JSONObject();
        result.put("success", true);
        writeJson(response, result.toJSONString());
        return null;
    }
}