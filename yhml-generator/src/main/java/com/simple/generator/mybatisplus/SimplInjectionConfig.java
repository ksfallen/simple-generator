package com.simple.generator.mybatisplus;

import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.generator.InjectionConfig;

/**
 * @author: Jfeng
 * @date: 2019-07-09
 */
public class SimplInjectionConfig extends InjectionConfig {

    Map<String, Object> map = new HashMap<>();


    @Override
    public void initMap() {
        // ConfigBuilder config = this.getConfig();
        // config.getTableInfoList().forEach(info -> map.put("entityQuery", info.getEntityName() + "Query"));
        // this.setMap(map);
    }
}
