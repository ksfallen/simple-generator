package org.mybatis.generator.config.init;

/**
 * @author: Jfeng
 * @date: 2019-05-30
 */
public class ContextConfig {

    private GlobalConfig globalConfig;

    private PackageConfig packageConfig;

    public static GlobalConfig getGlobalConfig() {
        return ThreadContext.get("gc");
    }

    public static void setGlobalConfig(GlobalConfig config) {
        ThreadContext.put("gc", config);
    }

    public static PackageConfig getPackageConfig() {
        return ThreadContext.get("pc");
    }

    public static void setPackageConfig(PackageConfig config) {
        ThreadContext.put("pc", config);
    }
}
