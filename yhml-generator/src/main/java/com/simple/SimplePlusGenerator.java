package com.simple;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.simple.generator.mybatisplus.JavaTypeConvert;
import com.simple.generator.mybatisplus.SimplAutoGenerator;
import com.simple.generator.mybatisplus.SimplInjectionConfig;
import com.simple.generator.mybatisplus.SimpleFileOutConfig;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis plus Generator
 */
public class SimplePlusGenerator {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    String driverName = "com.mysql.jdbc.Driver";
    String url;
    String userName;
    String password;
    String packageName;
    String entityPackage;
    String mapperPackage;
    String servicePackage;
    String outputDir;
    String xmlOutputDir;
    String[] tablePrefix;


    @Before
    public void before() {
        userName = "root";
        password = "root";
        url = "jdbc:mysql://127.0.0.1:3306/lyq?characterEncoding=utf-8&useSSL=false&tinyInt1isBit=false";

        outputDir = "code";
        xmlOutputDir = "code/xml";
        // outputDir = "src/main/java";
        // xmlOutputDir = "src/main/resources";

        packageName = "com.yhml.core";
        entityPackage = "model.entity";  // 父包名如果为空，子包名必须写全部， 否则就只需写子包名
        mapperPackage = "mapper";
        servicePackage = "service";

        tablePrefix = new String[]{"t_", "t_sys_", "op_", "sys_"};

        delete();
    }

    private void delete() {
        File file = new File(outputDir);
        FileSystemUtils.deleteRecursively(file);
        file.mkdirs();
    }

    protected void generator(List<String> list) {
        generator(list.toArray(new String[0]));
    }


    protected void generator(String... tables) {
        AutoGenerator generator = new SimplAutoGenerator();

        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setKotlin(false); // 是否生成 kotlin 代码
        gc.setOpen(false); // 生成之后不需要打开文件夹
        gc.setAuthor("Jfeng");

        // 自定义命名方式，注意 %s 会自动填充表实体属性！
        // gc.setEntityName("%sEntity");
        gc.setMapperName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        // gc.setXmlName("%sMapper");
        // 实体属性 Swagger2 注解
        // gc.setSwagger2(false);
        // 时间戳 默认转 LocalDateTime
        // gc.setDateType(DateType.TIME_PACK);
        generator.setGlobalConfig(gc);

        // 数据源配置
        datasource(generator);

        // 包配置
        packageConfig(generator);

        // 策略配置
        strategyConfig(generator, tables);

        // 注入 injectionConfig 配置
        injectionConfig(generator);

        // 自定义代码生成的模板
        templateConfig(generator);

        customGenerator(generator);

        generator.execute();
    }

    private StrategyConfig strategyConfig(AutoGenerator generator, String[] tables) {
        StrategyConfig config = new StrategyConfig();
        config.setNaming(NamingStrategy.underline_to_camel);// 表名映射生成Entity名策略
        config.setInclude(tables); // 需要生成的表
        config.setTablePrefix(tablePrefix);// 表前缀
        config.setFieldPrefix(); // 字段前缀
        config.setEntityLombokModel(true);
        // strategy.setSuperEntityClass(BaseParamBean.class);
        // strategy.setSuperControllerClass("");
        config.setRestControllerStyle(true);
        // strategy.setCapitalMode(true);// 是否大写命名 全局大写命名 ORACLE 注意
        config.setSkipView(true); // skipView 是否跳过视图
        // strategy.setLogicDeleteFieldName("deleted"); // 逻辑删除属性名称
        config.setEntityTableFieldAnnotationEnable(false); // TableField
        generator.setStrategy(config);
        return config;
    }

    protected AutoGenerator customGenerator(AutoGenerator generator) {
        return generator;
    }

    protected PackageConfig packageConfig(AutoGenerator generator) {
        PackageConfig config = new PackageConfig();
        config.setParent(packageName);  // 父包名。如果为空，子包名必须写全部， 否则就只需写子包名
        config.setEntity(entityPackage);
        // pc.setXml("dao");
        config.setMapper(mapperPackage);
        config.setService(servicePackage);
        // pc.setServiceImpl("service");
        generator.setPackageInfo(config);
        return config;
    }

    protected DataSourceConfig datasource(AutoGenerator mpg) {
        DataSourceConfig ds = new DataSourceConfig();
        ds.setUrl(url);
        ds.setUsername(userName);
        ds.setPassword(password);
        ds.setDriverName(driverName);
        ds.setTypeConvert(new JavaTypeConvert()); // 类型转换
        ds.setDbType(DbType.MYSQL);
        mpg.setDataSource(ds);
        return ds;
    }

    /**
     * 配置代码模板
     * 设置 null 不生成文件
     */
    protected TemplateConfig templateConfig(AutoGenerator generator) {
        TemplateConfig config = new TemplateConfig();
        config.setXml(null);
        config.setMapper(null);
        config.setMapper("/generator/mapper.java.vm");
        // config.setService(null);
        config.setService("/generator/service.java.vm");
        config.setServiceImpl(null);
        // config.setServiceImpl("/generator/serviceImpl.java.vm");
        config.setController(null);
        // config.setController("/generator/controller.java.vm");
        config.setEntity("/generator/entity.java.vm");
        generator.setTemplate(config);
        return config;
    }

    protected InjectionConfig injectionConfig(AutoGenerator generator) {
        // 自定义返回配置 Map 对象
        InjectionConfig cfg = new SimplInjectionConfig();

        // 自定义输出文件
        List<FileOutConfig> focList = new ArrayList<>();

        // 调整 xml 生成目录
        focList.add(SimpleFileOutConfig.xmlFile(xmlOutputDir));
        focList.add(SimpleFileOutConfig.entityQuery(packageName, outputDir, entityPackage + ".query"));
        focList.add(SimpleFileOutConfig.entityColumn(packageName, outputDir, entityPackage + ".query"));

        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);

        return cfg;
    }
}
