package com.simple;

import org.junit.Before;
import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

public class LyqGenerator extends SimplePlusGenerator {

    @Before
    public void before() {
        super.before();
        url = "jdbc:mysql://127.0.0.1:3306/lyq?characterEncoding=utf-8&useSSL=false&tinyInt1isBit=false";

        packageName = "com.lyq.core";
        entityPackage = "entity";  // 父包名如果为空，子包名必须写全部， 否则就只需写子包名
        mapperPackage = "mapper";

        tablePrefix = new String[]{"t_"};
    }

    @Test
    public void generatorAll() {
        // String[] tableName = {"sys_%"};
        String[] tableName = {"sys_user"};
        generatorTables(tableName);
    }

    @Override
    protected void customGenerator(AutoGenerator generator) {
        generator.getDataSource().setTypeConvert(new TypeConvert());
        generator.getTemplate().setController("/generator/controller.java.vm");
    }


    /**
     * 自定义数据库Java类型转换
     */
    private class TypeConvert extends MySqlTypeConvert {
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
}
