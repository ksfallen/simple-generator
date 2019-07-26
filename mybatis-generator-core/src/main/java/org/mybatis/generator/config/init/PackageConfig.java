package org.mybatis.generator.config.init;


import lombok.Data;

/**
 * @author: Jfeng
 * @date: 2019-05-30
 */
@Data
public class PackageConfig {

    /**
     * 路径配置信息
     */
    // private String pathInfo;

    /**
     * 父包模块名
     */
    private String packageName;

    /**
     * Entity包名
     */
    private String entity;

    /**
     * Service包名
     */
    private String service;

    /**
     * controller包名
     */
    private String controller;

    /**
     * Service Impl包名
     */
    private String serviceImpl;

    /**
     * Mapper包名
     */
    private String mapper;

    /**
     * Mapper XML包名
     */
    private String xml = "xml";


    public String getEntity() {
        if (entity == null) {
            return this.packageName + ".entity";
        }

        return entity;
    }

    public String getService() {
        if (service == null) {
            return this.packageName + ".service";
        }

        return service;
    }

    public String getServiceImpl() {
        if (serviceImpl == null) {
            return this.packageName + ".service.impl";
        }


        return serviceImpl;
    }

    public String getMapper() {
        if (mapper == null) {
            return this.packageName + ".mapper";
        }

        return mapper;
    }

    public String getController() {
        if (controller == null) {
            return this.packageName + ".controller";
        }

        return mapper;
    }
}
