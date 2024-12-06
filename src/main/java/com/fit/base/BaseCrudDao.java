package com.fit.base;

import java.util.List;
import java.util.Map;

/**
 * @AUTO DAO支持类接口
 * @FILE BaseCrudDao.java
 * @DATE 2018-3-23 下午2:38:38
 * @Author AIM
 */
public interface BaseCrudDao<T> {

    /**
     * 获取单条数据
     */
    T getById(int id);

    /**
     * 获取单条数据
     */
    T get(T entity);

    /**
     * 查询数据列表
     */
    List<T> findList(Object id);

    /**
     * 查询数据列表
     */
    List<T> findList(Map<String, Object> map);

    /**
     * 列表数量
     */
    int findCount(Map<String, Object> map);

    /**
     * 插入数据
     */
    int save(T entity);

    /**
     * 更新数据
     */
    int update(T entity);

    /**
     * 删除数据
     */
    int delete(int id);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     */
    int delete(T entity);

    /**
     * 批量删除
     */
    int batchDelete(String[] ids);
}