package com.simple;

import com.simple.generator.util.StringTool;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.init.ContextConfig;
import org.mybatis.generator.config.init.GlobalConfig;
import org.mybatis.generator.config.init.PackageConfig;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.springframework.util.Assert;

/**
 * mybatis Generator
 */
public class SimpleGenerator extends SimplePlusGenerator {

    @Before
    public void before() {
        super.before();
    }

    @Test
    public void generatorByTKXml() {
        String xml = "generatorConfig-tk.xml";
        initConfig();
        GlobalConfig gc = ContextConfig.getGlobalConfig();
        gc.setSuperServiceClass("com.simple.generator.base.thin.BaseService");
        gc.setSuperEntityClass("com.simple.common.base.bean.BaseParamBean");
        gc.setSuperMapperClass("com.simple.common.base.BaseMapper");
        run(xml);
    }

    @Test
    public void generatorByExample() {
        String xml = "generatorConfig-example.xml";
        initConfig();
        run(xml);
    }
    /**
     * 生成带有sql的xml文件
     */
    @Test
    public void generatorByXml() {
        String xml = "generatorConfig.xml";
        initConfig();
        run(xml);
    }

    protected void initConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir);
        // gc.setSuportServiceInterface(false);
        // gc.setGenericTypeAutowired(true);
        ContextConfig.setGlobalConfig(gc);

        PackageConfig pc = new PackageConfig();
        pc.setPackageName(packageName);
        pc.setEntity(packageName + "." + entityPackage);
        pc.setMapper(packageName + "." + mapperPackage);
        pc.setService(packageName + "." + servicePackage);
        ContextConfig.setPackageConfig(pc);

        customConfig();
    }

    private void run(String xml) {
        try {
            ConfigurationParser cp = new ConfigurationParser();
            Configuration config = cp.parseConfiguration(SimpleGenerator.class.getClassLoader().getResourceAsStream(xml));
            Context context = config.getContext("app");
            context.getJdbcConnectionConfiguration().setUserId(userName);
            context.getJdbcConnectionConfiguration().setPassword(password);
            context.getJdbcConnectionConfiguration().setDriverClass(driverName);
            context.getJdbcConnectionConfiguration().setConnectionURL(url);
            context.getJdbcConnectionConfiguration().addProperty("remarksReporting", "true");

            GlobalConfig gc = ContextConfig.getGlobalConfig();
            PackageConfig pc = ContextConfig.getPackageConfig();
            initContext(gc, pc, context);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config);
            ContextConfig.setMyBatisGenerator(myBatisGenerator);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新配置信息
     */
    protected void customConfig() {
        // GlobalConfig globalConfig = ContextConfig.getGlobalConfig();
        // PackageConfig packageConfig = ContextConfig.getPackageConfig();
    }

    public static void initContext(GlobalConfig gc, PackageConfig pc, Context context) {
        String targetProject = gc.getOutputDir();

        if (StringTool.isNotBlank(targetProject)) {
            Assert.hasLength(targetProject, "output dir 不能为空");
        }

        if (StringTool.isNotBlank(pc.getPackageName())) {
            Assert.hasLength(pc.getPackageName(), "packageName 不能为空");
        }

        context.getJavaModelGeneratorConfiguration().setTargetProject(targetProject);
        context.getSqlMapGeneratorConfiguration().setTargetProject(targetProject);
        context.getJavaClientGeneratorConfiguration().setTargetProject(targetProject);
        context.getJavaModelGeneratorConfiguration().setTargetPackage(pc.getEntity());
        context.getJavaClientGeneratorConfiguration().setTargetPackage(pc.getMapper());
        context.getSqlMapGeneratorConfiguration().setTargetPackage(pc.getXml());
    }

}
