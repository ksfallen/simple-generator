package com.simple;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;


public class TklcGenerator extends SimplePlusGenerator {

    @Before
    public void before() {
        super.before();

        // ------------ account ----------------- //
        userName = "tdw";
        password = "happy";
        url = "jdbc:mysql://10.0.0.103:3306/accounts?characterEncoding=UTF-8&tinyInt1isBit=false";

        packageName = "com.citytsm.ace.account";
        entityPackage = "api.entity";  // 父包名如果为空，子包名必须写全部， 否则就只需写子包名
        mapperPackage = "core.mapper";
        servicePackage = "core.service";


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
        list.add("account_activity");
        // list.add("account_frozen_record");
        list.add("account_info");
        // list.add("user_info");
        // list.add("open_account_info");
        // list.add("merchant_daily_cash");

        generator(list);
    }

    @Override
    protected PackageConfig packageConfig(AutoGenerator generator) {
        PackageConfig config = super.packageConfig(generator);
        // config.setService("core.service");
        return config;
    }


    @Override
    protected InjectionConfig injectionConfig(AutoGenerator generator) {
        InjectionConfig config = super.injectionConfig(generator);
        // List<FileOutConfig> focList = config.getFileOutConfigList();
        // focList.add(SimpleFileOutConfig.entityQuery(packageName, outputDir, entityPackage + ".query"));
        return config;
    }
}
