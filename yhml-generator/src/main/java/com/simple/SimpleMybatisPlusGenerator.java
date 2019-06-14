package com.simple;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.FileSystemUtils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.simple.generator.mybatisplus.SimplAutoGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleMybatisPlusGenerator {

    private String url;
    private String driverName;
    private String userName;
    private String password;
    private String packageName;
    private String entityPackage;
    private String mapperPackage;
    private String outputDir;
    private String xmlOutputDir;


    @Before
    public void before() {
        driverName = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://172.31.254.147:3306/sit_site?characterEncoding=UTF-8&tinyInt1isBit=false";
        userName = "opfm";
        password = "Allcityg0-_";

        // userName = "root";
        // password = "root";
        // url = "jdbc:mysql://127.0.0.1:3306/simple?characterEncoding=utf-8&useSSL=false";

        outputDir = "code";
        xmlOutputDir = "code";
        packageName = "com.citytsm.operationplatform.site.hangzhoutong";
        entityPackage = "domain";  // 父包名如果为空，子包名必须写全部， 否则就只需写子包名
        mapperPackage = "dao";

        // packageName = "com.yhml.pay.core";
        // outputDir = "src/main/java";
        // xmlOutputDir = "src/main/resources";

        File file = new File(outputDir);
        FileSystemUtils.deleteRecursively(file);
        file.mkdirs();
    }

    @Test
    public void generatorAllTables() {
        String[] tableName = {"op_shop%", "op_site%"};
        generatorTables(tableName);
    }

    @Test
    public void generatorSingle() {
        generatorTables("op_hangzhoutong%");
    }

    private void generatorTables(String... tables) {
        AutoGenerator generator = new SimplAutoGenerator();

        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir);
        gc.setFileOverride(true); //FIXME true不是很合理，应该有一种merge操作
        // gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
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
        gc.setSwagger2(true); // 实体属性 Swagger2 注解
        generator.setGlobalConfig(gc);

        // 数据源配置
        datasource(generator);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName);  // 父包名。如果为空，子包名必须写全部， 否则就只需写子包名
        pc.setEntity(entityPackage);
        // pc.setXml("dao");
        pc.setMapper(mapperPackage);
        // pc.setServiceImpl("service");
        generator.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名映射生成Entity名策略
        strategy.setInclude(tables); // 需要生成的表
        strategy.setTablePrefix(new String[]{"t_", "t_sys_", "op_"});// 此处可以修改为您的表前缀
        strategy.setFieldPrefix(); // 字段前缀
        strategy.setEntityLombokModel(true);
        // strategy.setSuperEntityClass(BaseParamBean.class);
        // strategy.setSuperControllerClass("");
        strategy.setRestControllerStyle(true);
        // strategy.setCapitalMode(true);// 是否大写命名 全局大写命名 ORACLE 注意
        strategy.setSkipView(true); // skipView 是否跳过视图
        // strategy.setLogicDeleteFieldName("deleted"); // 逻辑删除属性名称
        strategy.setEntityTableFieldAnnotationEnable(true);
        generator.setStrategy(strategy);

        // 注入 injectionConfig 配置
        injectionConfig(generator);

        // 自定义代码生成的模板
        templateConfig(generator);

        generator.execute();
    }

    private void datasource(AutoGenerator mpg) {
        DataSourceConfig ds = new DataSourceConfig();
        ds.setUrl(url);
        ds.setUsername(userName);
        ds.setPassword(password);
        ds.setDriverName(driverName);
        ds.setTypeConvert(new TypeConvert());
        ds.setDbType(DbType.MYSQL);
        mpg.setDataSource(ds);
    }
    private void templateConfig(AutoGenerator mpg) {
        //  null 不生成文件

        TemplateConfig tc = new TemplateConfig();
        // tc.setMapper(null);
        tc.setXml(null); // disable generator controller
        // tc.setService(null);
        // tc.setServiceImpl(null);
        tc.setServiceImpl("/generator/serviceImpl.java.vm");
        tc.setController(null);                             //disable generator controller
        // tc.setController("/generator/controller.java.vm");
        tc.setEntity("/generator/entity.java.vm");
        mpg.setTemplate(tc);
    }

    private void injectionConfig(AutoGenerator generator) {
        // 自定义返回配置 Map 对象
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
        generator.setCfg(cfg);

        // 自定义输出文件
        List<FileOutConfig> focList = new ArrayList<>();
        cfg.setFileOutConfigList(focList);

        // 调整 mapper  生成目录
        // focList.add(new FileOutConfig("/templates/mapper.java.vm") {
        //     @Override
        //     public String outputFile(TableInfo tableInfo) {
        //         return "src/main/java/" + packageName + "/mapper/" + tableInfo.getEntityName() + "Mapper.java";
        //     }
        // });

        // 调整 xml 生成目录
        focList.add(new FileOutConfig("/generator/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return xmlOutputDir + "/xml/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });

        // String substring = packageName.replaceAll("\\.", "/");
        // String path = outputDir + "/" + substring.substring(0, substring.lastIndexOf("/")) + "/web/controller";
        // focList.add(new FileOutConfig("/generator/controller.java.vm") {
        //     @Override
        //     public String outputFile(TableInfo tableInfo) {
        //         return  path + "/" + tableInfo.getEntityName() + "Controller.java";
        //     }
        // });
    }


    /**
     * 自定义数据库Java类型转换
     */
    class TypeConvert extends MySqlTypeConvert {
        @Override
        public IColumnType processTypeConvert(GlobalConfig gc, String fieldType) {
            String t = fieldType.toLowerCase();

            if (t.contains("date") || t.contains("time") || t.contains("year")) {
                DbColumnType date = DbColumnType.DATE;
                log.info("转换类型：{} -> {}", t, date);
                return date;
            }

            return super.processTypeConvert(gc, fieldType);
        }
    }
}
