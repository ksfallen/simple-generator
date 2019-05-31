package com.simple.generator.base.thin;

import java.io.Serializable;
import java.util.List;


public interface BaseMapper<T> {
    int insert(T record);

    int update(T record);

    int deleteById(Serializable id);

    int count(T record);

    T selectById(Serializable id);

    List<T> listByCriteria(T record);

    int updateBatch(List<T> list);

    int insertBatch(List<T> list);

    int deleteByIdList(List<Serializable> list);
}
