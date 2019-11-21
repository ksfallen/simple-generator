package com.simple.generator.mybatisplus;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义数据库Java类型转换
 *
 * @author: Jfeng
 * @date: 2019/11/21
 */
@Slf4j
public class JavaTypeConvert extends MySqlTypeConvert {
    @Override
    public IColumnType processTypeConvert(GlobalConfig gc, String fieldType) {
        String t = fieldType.toLowerCase();

        if (t.contains("date") || t.contains("time") || t.contains("year")) {
            DbColumnType date = DbColumnType.LOCAL_DATE_TIME;
            log.info("转换类型：{} -> {}", t, date);
            return date;
        }

        return super.processTypeConvert(gc, fieldType);
    }
}
