package com.simple;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.init.ContextConfig;
import org.mybatis.generator.config.init.GlobalConfig;
import org.mybatis.generator.config.init.PackageConfig;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.springframework.util.Assert;

import com.simple.generator.util.StringTool;


public class SimpleGenerator {

    public static MyBatisGenerator myBatisGenerator;


    public static void main(String[] args) throws Exception {
        String url ="jdbc:mysql://127.0.0.1:3306/simple?characterEncoding=utf-8&useSSL=false";
        String dirver = "com.mysql.jdbc.Driver";
        String user = "root";
        String passwd = "root";

        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("code/demo");
        // gc.setSuperServiceClass("com.simple.generator.base.thin.BaseService");
        // gc.setSuperEntityClass("com.simple.common.base.bean.BaseParamBean");
        // gc.setSuperMapperClass("com.simple.common.base.BaseMapper");
        gc.setSuportServiceInterface(true);
        // gc.setGenericTypeAutowired(true);
        gc.setMapperSuffix("Dao");

        PackageConfig pc = new PackageConfig();
        pc.setPackageName("com.yhml.demo");

        ContextConfig.setGlobalConfig(gc);
        ContextConfig.setPackageConfig(pc);

        String generatorConfigXml = "generatorConfig.xml";
        // String generatorConfigXml = "generatorConfig-tk.xml";
        // String generatorConfigXml = "generatorConfig-example.xml";

        ConfigurationParser cp = new ConfigurationParser();
        Configuration config = cp.parseConfiguration(SimpleGenerator.class.getClassLoader().getResourceAsStream(generatorConfigXml));
        Context context = config.getContext("app");
        context.getJdbcConnectionConfiguration().setUserId(user);
        context.getJdbcConnectionConfiguration().setPassword(passwd);
        context.getJdbcConnectionConfiguration().setDriverClass(dirver);
        context.getJdbcConnectionConfiguration().setConnectionURL(url);
        context.getJdbcConnectionConfiguration().addProperty("remarksReporting", "true");

        initContext(gc, pc, context);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config);
        myBatisGenerator.generate(null);
    }



    private static void initContext(GlobalConfig gc, PackageConfig pc, Context context) {
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
