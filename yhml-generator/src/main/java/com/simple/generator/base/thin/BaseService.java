package com.simple.generator.base.thin;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;

/**
 * @author Jfeng
 */
@Service
public abstract class BaseService<T> {

    @Autowired
    protected BaseMapper<T> mapper;

    public int insert(T record) {
        return mapper.insert(record);
    }

    public int update(T record) {
        return mapper.update(record);
    }

    public int deleteById(Integer id) {
        return mapper.deleteById(id);
    }

    public int count(T record) {
        return mapper.count(record);
    }

    // public int countAll() {
    //     return mapper.count(null);
    // }

    public T getById(Integer id) {
        return mapper.selectById(id);
    }

    public T getOne(T record) {
        List<T> list = mapper.listByCriteria(record);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    public List<T> listAll() {
        return mapper.listByCriteria(null);
    }

    public List<T> listByCriteria(T record) {
        return mapper.listByCriteria(record);
    }

    /**
     * 无条件分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<T> listByPage(int pageNum, int pageSize) {
        startPage(pageNum, pageSize);
        return mapper.listByCriteria(null);
    }

    /**
     * 按条件分页查询
     * @param record
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<T> listByPage(T record, int pageNum, int pageSize) {
        startPage(pageNum, pageSize);
        return mapper.listByCriteria(record);
    }


    /**
     * 批量更新
     * @param list
     * @return
     */
    public int insertBatch(List<T> list) {
        return mapper.insertBatch(list);
    }

    /**
     * 批量插入
     * @param list
     * @return
     */
    public int updateBatch(List<T> list) {
        return mapper.updateBatch(list);
    }

    /**
     * 根据ID批量删除
     * @param list
     */
    public void deleteByIds(List<Serializable> list) {
        mapper.deleteByIdList(list);
    }

    private void startPage(int pageNum, int pageSize) {
        startPage(pageNum, pageSize, null);
    }

    private void startPage(int pageNum, int pageSize, String orderBy) {
        if (pageNum != 0 && pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
        }

        PageHelper.orderBy(StringUtils.isNotBlank(orderBy) ? orderBy : null);
    }

}
