package com.simple;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class TklcGenerator extends SimplePlusGenerator {

    @Before
    public void before() {
        super.before();

        // ------------ ace-account ----------------- //
        // url = "jdbc:mysql://10.0.0.103:3306/accounts?characterEncoding=UTF-8&tinyInt1isBit=false";
        // userName = "tdw";
        // password = "happy";

        // ------------ ace-benefit ----------------- //
        url = "jdbc:mysql://10.0.0.106:3306/ace_benefit?characterEncoding=UTF-8&tinyInt1isBit=false";
        userName = "bm";
        password = "Citytsm0";

        packageName = "com.citytsm.ace.benefit";
        entityPackage = "dal.entity";  // 父包名如果为空，子包名必须写全部， 否则就只需写子包名
        mapperPackage = "dal.mapper";
        servicePackage = "core.manager";

        // ------------ site ----------------- //
        // url = "jdbc:mysql://172.31.254.147:3306/sit_site?characterEncoding=UTF-8&tinyInt1isBit=false";
        // userName = "opfm";
        // password = "Allcityg0-_";
        // packageName = "com.citytsm.operationplatform.site.activity";
        // entityPackage = "entity.model";  // 父包名如果为空，子包名必须写全部， 否则就只需写子包名
        // mapperPackage = "dao";
    }

    @Test
    public void generatorAll() {
        List<String> list = new ArrayList<>();
        list.add("benefit_product");
        list.add("benefit_rule");
        list.add("benefit_useage_record");
        list.add("user_benefit");
        generator(list);
    }


    @Override
    protected void customGenerator(AutoGenerator generator) {
        generator.getGlobalConfig().setServiceName("%sManager");
        // generator.getTemplate().setService("/generator/service2.java.vm");

        // PackageConfig config = generator.getPackageInfo();
        // config.setService("core.service");
    }

    @Override
    protected InjectionConfig injectionConfig(AutoGenerator generator) {
        InjectionConfig config = super.injectionConfig(generator);
        // List<FileOutConfig> focList = config.getFileOutConfigList();
        // focList.add(SimpleFileOutConfig.entityQuery(packageName, outputDir, entityPackage + ".query"));
        return config;
    }
}
