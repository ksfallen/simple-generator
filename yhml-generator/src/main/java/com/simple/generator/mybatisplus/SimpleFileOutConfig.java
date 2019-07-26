package com.simple.generator.mybatisplus;

import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.yhml.core.util.StringUtil;

import cn.hutool.core.util.StrUtil;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author: Jfeng
 * @date: 2019-07-25
 */
@Setter
@Accessors(chain = true, fluent = true)
public class SimpleFileOutConfig extends FileOutConfig {

    private static final String template_entityquery = "/generator/entity_query.java.vm";
    private static final String template_mapper = "/generator/mapper.xml.vm";
    private static final String entity_column = "/generator/entity_column.java.vm";

    protected String packageName;
    protected String outputDir;
    protected String path;
    protected String suffix = "";
    protected String prefx = "";
    protected String ext = "java";

    public SimpleFileOutConfig(String templatePath, String packeageName, String outputDir, String path) {
        super(templatePath);
        this.packageName = packeageName;
        this.outputDir = outputDir;
        this.path = path;
    }

    public static void main(String[] args) {
        String dir = StrUtil.join(".", "", "").replace(".", "/");
        System.out.println("dir = " + dir);
    }

    public static SimpleFileOutConfig entityQuery(String packeageName, String outputDir, String path) {
        return new SimpleFileOutConfig(template_entityquery, packeageName, outputDir, path).suffix("Query");
    }

    public static SimpleFileOutConfig entityColumn(String packeageName, String outputDir, String path) {
        return new SimpleFileOutConfig(entity_column, packeageName, outputDir, path).prefx("Q").suffix("Filed");
    }

    public static SimpleFileOutConfig xmlFile(String outputDir) {
        return new SimpleFileOutConfig(template_mapper, "", outputDir, "").suffix("Mapper").ext("xml");
    }

    @Override
    public String outputFile(TableInfo tableInfo) {
        String dir = "";

        if (StringUtil.isNoneBlank(packageName, path)) {
            dir = StrUtil.join(".", packageName, path).replace(".", "/");
        }

        String temp = outputDir + "/";
        if (StringUtil.hasText(dir)) {
            temp = temp + dir + "/";
        }

        return temp + prefx + tableInfo.getEntityName() + suffix + "." + ext;
    }
}
