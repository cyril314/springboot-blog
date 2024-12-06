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
public class TLink extends BaseEntity<TLink> {
    private static final long serialVersionUID = 1L;

    /**  (无默认值) */
    private String linkname;

    /**  (无默认值) */
    private String linkurl;

    /**  (无默认值) */
    private Integer orderno;
}