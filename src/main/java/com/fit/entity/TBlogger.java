package com.fit.entity;

import com.fit.base.BaseEntity;
import java.util.Date;
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
public class TBlogger extends BaseEntity<TBlogger> {
    private static final long serialVersionUID = 1L;

    /**  (无默认值) */
    private String username;

    /**  (无默认值) */
    private String password;

    /**  (无默认值) */
    private String profile;

    /**  (无默认值) */
    private String nickname;

    /**  (无默认值) */
    private String sign;

    /**  (无默认值) */
    private String imagename;
}