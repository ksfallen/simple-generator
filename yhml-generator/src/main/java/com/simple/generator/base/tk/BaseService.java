package com.simple.generator.base.tk;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.yhml.core.base.bean.BaseParamBean;

import tk.mybatis.mapper.common.Mapper;

/**
 * 功能说明: <br>
 * 系统版本: v1.0<br>
 * 开发人员: @author HuJianfeng<br>
 */
@Service
public abstract class BaseService<T extends BaseParamBean> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Mapper<T> mapper;

    public int insert(T entity) {
        return mapper.insertSelective(entity);
    }

    public int update(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    public int deleteById(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int count(T record) {
        return mapper.selectCount(record);
    }

    public T getById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    public T getOne(T record) {
        return mapper.selectOne(record);
    }

    public List<T> listAll() {
        return mapper.selectAll();
    }

    public List<T> listByCriteria(T record) {
        return mapper.select(record);
    }

    public List<T> listByPage(int pageNum, int pageSize) {
        startPage(pageNum, pageSize);
        return mapper.selectAll();
    }

    public List<T> listByPage(T record, int pageNum, int pageSize) {
        startPage(pageNum, pageSize, record.getOrderBy());
        return mapper.select(record);
    }


    public List<T> listByExample(Object example, int pageNum, int pageSize) {
        startPage(pageNum, pageSize);
        return mapper.selectByExample(example);
    }

    public List<T> listByExample(Object example) {
        return mapper.selectByExample(example);
    }

    private void startPage(int pageNum, int pageSize) {
        startPage(pageNum, pageSize, null);
    }

    private void startPage(int pageNum, int pageSize, String orderBy) {
        if (pageNum != 0 && pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
        }

        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy( orderBy);
        }
    }

}
