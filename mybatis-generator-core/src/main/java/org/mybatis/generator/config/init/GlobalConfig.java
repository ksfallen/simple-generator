package org.mybatis.generator.config.init;

import lombok.Data;

/**
 * @author: Jfeng
 * @date: 2019-05-30
 */
@Data
public class GlobalConfig {
    private String outputDir;


    /**
     * 自定义继承的Entity类全称，带包名
     *
     */
    private String superEntityClass;

    /**
     * 自定义继承的Mapper类全称，带包名
     */
    private String superMapperClass;

    /**
     * 自定义继承的Service类全称，带包名
     */
    private String superServiceClass;

    /**
     *  泛型注入 Mapper<T> mapper
     */
    private boolean genericTypeAutowired;

    /**
     * 自定义继承的Controller类全称，带包名
     */
    private String superControllerClass;

    /**
     * 使用Service接口
     */
    private boolean suportServiceInterface;

    /**
     * 实体命名方式
     */
    private String entitySuffix;


    /**
     * mapper 命名方式
     */
    private String mapperSuffix = "Mapper";

    /**
     * Mapper xml 命名方式
     */
    private String xmlSuffix = "Mapper";

    /**
     * service 命名方式
     */
    private String serviceSuffix = "Service";

    /**
     * service impl 命名方式
     */
    private String serviceImplSuffix = "ServiceImpl";

    /**
     * controller 命名方式
     */
    private String controllerSuffix = "Controller";

    private boolean swagger2;

}
