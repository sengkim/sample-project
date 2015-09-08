package com.sengkim.study.sample.dao;

import java.util.Collection;
import java.util.Collections;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sengkim.study.sample.domain.AbstractLongDomainEntity;
import com.sengkim.study.sample.util.ResponseList;

/**
 * @author sengkim
 * @Date Sep 8, 2015
 * @Email sengkim.it@gmail.com
 * @param <T>
 * @param <D>
 */
public abstract class BaseDaoImpl<T extends AbstractLongDomainEntity, D extends BaseDao<T>> implements BaseDao<T> {

    protected static final Logger LOG = LoggerFactory.getLogger(BaseDaoImpl.class);

    @Autowired
    private SqlSession sqlSession;

    private Class<T> dao;

    public BaseDaoImpl(Class dao) {
        this.dao = dao;
    }

    protected SqlSession getSqlSession() {
        return sqlSession;
    }

    @SuppressWarnings("unchecked")
    public void add(T domain) {
        ((BaseDao<T>) sqlSession.getMapper(dao)).add(domain);
    }

    public void add(Iterable<T> domains) {
        for (T domain : domains) {
            add(domain);
        }
    }

    public void update(Iterable<T> domians) {
        for (T t : domians) {
            update(t);
        }
    }

    public void update(T domain) {
        ((BaseDao<T>) sqlSession.getMapper(dao)).update(domain);
    }

    public void remove(Iterable<T> domains) {
        for (T t : domains) {
            remove(t.getId());
        }
    }

    public void remove(Long id) {
        ((BaseDao<T>) sqlSession.getMapper(dao)).remove(id);
    }

    public ResponseList<T> getPage(int pageSize, String cursorKey) {
        LOG.debug(">> getResponsePage with limit {}, cursor {}", pageSize, cursorKey);

        if (null == cursorKey || cursorKey.isEmpty()) {
            cursorKey = "0";
        }

        ResponseList<T> responseList = ((D) sqlSession.getMapper(dao)).getPage(pageSize, cursorKey);
        if (responseList == null) {
            return new ResponseList(Collections.EMPTY_LIST);
        }

        Collection<T> data = responseList.getItems();
        if (data.isEmpty()) {
            return new ResponseList(data);
        }

        int total = count();

        String nextCursorKey = null;

        if (Integer.parseInt(cursorKey) + data.size() <= total - 1) {
            nextCursorKey = String.format("%s", Integer.parseInt(cursorKey) + data.size());
        } else {
            nextCursorKey = null;
        }

        final ResponseList<T> page = new ResponseList(data, nextCursorKey);
        page.withTotal(total).withLimit(data.size());

        // populate previouse
        if (!"0".equals(cursorKey)) {
            int previouseCursor = Math.abs(Integer.parseInt(cursorKey) - pageSize);
            page.withReverseCursor(String.format("%s", previouseCursor));
        }

        return page;
    }

    public Collection<T> getAll() {
        return ((BaseDao<T>) sqlSession.getMapper(dao)).getAll();
    }

    public T findById(Object id) {
        final T domain = ((BaseDao<T>) sqlSession.getMapper(dao)).findById(id);
        return domain;
    }

    public Collection<T> findByIds(Collection<Long> ids) {
        LOG.debug(">>findByIds : {}", ids);
        if (null == ids || ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return ((BaseDao<T>) sqlSession.getMapper(dao)).findByIds(ids);
    }

    public int count() {
        return ((BaseDao<T>) sqlSession.getMapper(dao)).count();
    }

    public Integer countWithFilters(T domain) {
        return ((BaseDao<T>) sqlSession.getMapper(dao)).countWithFilters(domain);
    }

    protected ResponseList<T> populatePages(Collection<T> items, int pageSize, String cursorKey) {
        return populatePages(items, pageSize, cursorKey, null);
    }

    protected ResponseList<T> populatePages(Collection<T> items, int pageSize, String cursorKey, Integer totalCount) {
        if (items == null || items.isEmpty()) {
            return new ResponseList(Collections.EMPTY_LIST);
        }
        int total = 0;
        if (totalCount == null) {
            total = count();
        } else {
            total = totalCount;
        }

        if ("0".equals(cursorKey) && items.size() < pageSize) {
            total = items.size();
        }

        LOG.debug(">> Total records count : {} : Integer.parseInt(cursorKey) + items.size() : {}", total,
                Integer.parseInt(cursorKey) + items.size());
        String nextCursorKey = null;

        if (items.size() == pageSize && Integer.parseInt(cursorKey) + items.size() < total) {
            nextCursorKey = String.format("%s", Integer.parseInt(cursorKey) + items.size());
        }
        LOG.debug(">> next cursorKey {}", nextCursorKey);

        ResponseList<T> page = new ResponseList<T>(items, nextCursorKey);
        page.withTotal(total).withLimit(items.size());

        // populate previouse
        if (!"0".equals(cursorKey)) {
            int previouseCursor = Math.abs(Integer.parseInt(cursorKey) - pageSize);
            page.withReverseCursor(String.format("%s", previouseCursor));
        }

        return page;

    }

    public Collection<T> findByFields(T domain) {
        return ((BaseDao<T>) sqlSession.getMapper(dao)).findByFields(domain);
    }

    public ResponseList<T> getPageWithFields(T domain, int limit, String cursorKey) {
        LOG.debug(">> getResponsePage with limit {}, cursor {}", limit, cursorKey);

        if (null == cursorKey || cursorKey.isEmpty()) {
            cursorKey = "0";
        }

        ResponseList<T> responseList = ((BaseDao<T>) sqlSession.getMapper(dao)).getPageWithFields(domain, limit, cursorKey);

        if (responseList == null) {
            return new ResponseList(Collections.EMPTY_LIST);
        }
        int totalCount = countWithFilters(domain);
        return populatePages(responseList.getItems(), limit, cursorKey, totalCount);
    }

}
