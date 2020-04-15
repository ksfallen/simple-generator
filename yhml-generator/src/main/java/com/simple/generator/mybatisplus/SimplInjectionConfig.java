package com.simple.generator.mybatisplus;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.google.common.collect.Maps;
import com.yhml.core.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置自定义变量
 */
public class SimplInjectionConfig extends InjectionConfig {

    Map<String, Object> map = new HashMap<>();

    @Override
    public void initMap() {
        // 设置 在Service类中注入mapper对象的变量名
        map.put("mapper", Maps.newHashMap());
        // 设置 在Controller类中注入mapper对象的变量名
        map.put("service", Maps.newHashMap());

        ConfigBuilder config = this.getConfig();
        config.getTableInfoList().forEach(info -> {
            ((Map) map.get("mapper")).put(info.getEntityName(), StringUtil.toLowerCaseFirst(info.getMapperName()));
            ((Map) map.get("service")).put(info.getEntityName(), StringUtil.toLowerCaseFirst(info.getServiceName()));
        });
        this.setMap(map);
    }
}
