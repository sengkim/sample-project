package com.sengkim.study.sample.dao;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;

import com.sengkim.study.sample.util.ResponseList;

/**
 * @author sengkim
 * @Date Sep 8, 2015
 * @Email sengkim.it@gmail.com
 * @param <T>
 */
public interface BaseDao<T extends Object> {

    T findById(@Param("id") Object id);

    Collection<T> findByIds(@Param("list") Collection<Long> ids);

    void add(T domain);

    void add(Iterable<T> domains);

    void update(T domain);

    void update(Iterable<T> domains);

    Collection<T> getAll();

    void remove(@Param("id") Long id);

    void remove(Iterable<T> domains);

    ResponseList<T> getPage(@Param("limit") int limit, @Param("offset") String offset);

    int count();

    Integer countWithFilters(@Param("domain") T domain);

    Collection<T> findByFields(@Param("domain") T domain);

    ResponseList<T> getPageWithFields(@Param("domain") T domain, @Param("limit") int pageSize, @Param("offset") String cursorKey);
}
