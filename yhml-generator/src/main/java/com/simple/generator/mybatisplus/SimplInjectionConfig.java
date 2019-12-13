package com.simple.generator.mybatisplus;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
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
        Map<String, String> mapperNameMap = new HashMap<>();
        map.put("mapperName", mapperNameMap);

        ConfigBuilder config = this.getConfig();
        config.getTableInfoList().forEach(info -> {
            // map.put("entityQuery", info.getEntityName() + "Query");
            mapperNameMap.put(info.getEntityName(), StringUtil.toLowerCaseFirst(info.getEntityName()) + "Mapper");
        });
        this.setMap(map);
    }
}
