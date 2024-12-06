package com.fit.entity;

import com.fit.base.BaseEntity;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @AUTO 
 * @Author AIM
 * @DATE 2024-12-06 11:29:29
 */
@Data
@Builder
@NoArgsConstructor //无参数的构造方法
@AllArgsConstructor //包含所有变量构造方法
public class TBlog extends BaseEntity<TBlog> {
    private static final long serialVersionUID = 1L;

    /**  (无默认值) */
    private String title;

    /**  (无默认值) */
    private String summary;

    /**  (无默认值) */
    private Date releasedate;

    /**  (无默认值) */
    private Integer clickhit;

    /**  (无默认值) */
    private Integer replyhit;

    /**  (无默认值) */
    private String content;

    /**  (无默认值) */
    private String contentnotag;

    /**  (无默认值) */
    private Integer blogcount;

    /**  (无默认值) */
    private String releasedatestr;

    /**  (无默认值) */
    private Integer typeid;

    /**  (无默认值) */
    private String keyword;

    private List<String> imagesList=new LinkedList<String>(); // 博客里存在的图片 主要用于列表展示显示缩略图
}