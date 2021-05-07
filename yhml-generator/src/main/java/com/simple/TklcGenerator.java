package com.simple;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class TklcGenerator extends SimpleGenerator {

    @Before
    public void before() {
        super.before();

        // ------------ 10.0.0.103 ----------------- //
        url = "jdbc:mysql://10.0.0.103:3306/ebus_bms?characterEncoding=UTF-8&tinyInt1isBit=false";
        userName = "tdw";
        password = "happy";

        // ------------ 10.0.0.106 ----------------- //
        // url = "jdbc:mysql://10.0.0.106:3306/ace_voucher?characterEncoding=UTF-8&tinyInt1isBit=false";
        // userName = "bm";
        // password = "Citytsm0";


        // ------------ 10.0.0.147 ----------------- //
        // url = "jdbc:mysql://10.0.0.147:3306/sit_site?characterEncoding=UTF-8&tinyInt1isBit=false";
        // userName = "ceshi";
        // password = "Allcityg0-_";
        // packageName = "com.citytsm.operationplatform.site.activity";
        // entityPackage = "entity.model";  // 父包名如果为空，子包名必须写全部， 否则就只需写子包名
        // mapperPackage = "dao";

        packageName = "com.citytsm.ace.voucher";
        entityPackage = "dal.entity";  // 父包名如果为空，子包名必须写全部， 否则就只需写子包名
        mapperPackage = "dal.mapper";
        servicePackage = "dal.manager";
    }

    @Test
    public void generatorAll() {
        List<String> list = new ArrayList<>();
        list.add("line");
        list.add("line_batch");
        list.add("station");
        list.add("train");
        generator(list);
        // generatorByXml();
    }


    @Override
    protected AutoGenerator customGenerator(AutoGenerator generator) {
        generator.getGlobalConfig().setServiceName("%sManager");
        // generator.getTemplate().setService("/generator/service2.java.vm");
        // PackageConfig config = generator.getPackageInfo();
        // config.setService("core.service");
        return generator;
    }

}
