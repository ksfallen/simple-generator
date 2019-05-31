package com.simple.generator.base.tk;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.MySqlMapper;


public interface BaseCommonMapper<T> extends Mapper<T>, MySqlMapper, Marker {

}
