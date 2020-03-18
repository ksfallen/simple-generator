package com.simple;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import org.junit.Before;
import org.junit.Test;


public class TklcGenerator extends SimpleGenerator {

    @Before
    public void before() {
        super.before();

        // ------------ ace-account ----------------- //
        url = "jdbc:mysql://10.0.0.103:3306/nanjing_settlement?characterEncoding=UTF-8&tinyInt1isBit=false";
        userName = "tdw";
        password = "happy";

        // ------------ ace-benefit ----------------- //
        // url = "jdbc:mysql://10.0.0.10:3306/ace_benefit?characterEncoding=UTF-8&tinyInt1isBit=false";
        // userName = "bm";
        // password = "Citytsm0";

        // ------------ site ----------------- //
        // url = "jdbc:mysql://172.31.254.147:3306/sit_site?characterEncoding=UTF-8&tinyInt1isBit=false";
        // userName = "opfm";
        // password = "Allcityg0-_";
        // packageName = "com.citytsm.operationplatform.site.activity";
        // entityPackage = "entity.model";  // 父包名如果为空，子包名必须写全部， 否则就只需写子包名
        // mapperPackage = "dao";

        packageName = "com.citytsm.psc.biz";
        entityPackage = "dal.entity";  // 父包名如果为空，子包名必须写全部， 否则就只需写子包名
        mapperPackage = "dal.mapper";
        servicePackage = "dal.manager";
    }

    @Test
    public void generatorAll() {
        // List<String> list = new ArrayList<>();
        // list.add("check_batch");
        // list.add("check_mistake");
        // list.add("dwd_channel_order");
        // list.add("dwd_trans_info");
        generator();
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
