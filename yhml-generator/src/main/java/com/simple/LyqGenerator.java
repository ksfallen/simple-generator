package com.simple;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import org.junit.Before;
import org.junit.Test;

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
        generator(tableName);
    }

    @Override
    protected AutoGenerator customGenerator(AutoGenerator generator) {
        // generator.getDataSource().setTypeConvert(new JavaTypeConvert());
        generator.getTemplate().setController("/generator/controller.java.vm");
        return generator;
    }

}
