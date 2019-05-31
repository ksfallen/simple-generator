package com.simple.generator.plugin.mapper;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;


/**
 * 使用于 Mybatis3 生成的 example 模式
 */
public class MapperGeneratorPlugin extends PluginAdapter {


    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 生成dao
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable table) {
        String rootClass = gc.getSuperMapperClass();

        if (rootClass != null) {
            // interfaze.getImportedTypes().clear();

            String baseName = rootClass.substring(rootClass.lastIndexOf(".") + 1);
            FullyQualifiedJavaType parameterType = table.getRules().calculateAllFieldsClass();

            // 主键默认采用java.lang.Integer
            FullyQualifiedJavaType javaType = new FullyQualifiedJavaType(baseName + "<" + parameterType.getShortName() + ">");
            // interfaze.addImportedType(parameterType);

            boolean hasExample = Boolean.valueOf(this.properties.getProperty("hasExample"));
            // 主键类型
            if (hasExample) {
                String pkJavaType = this.properties.getProperty("primaryKeyJavaType", "java.lang.Integer");
                javaType =
                        new FullyQualifiedJavaType(baseName + "<" + table.getBaseRecordType() + "," + table.getExampleType() + "," + pkJavaType + ">");
            }


            // 添加 extends BaseMapper
            interfaze.addSuperInterface(javaType);

            // 添加 import BaseMapper;
            interfaze.addImportedType(new FullyQualifiedJavaType(rootClass));

            // 删除多于方法不需要
            interfaze.getMethods().clear();
        }

        // @mapper 注解
        // interfaze.addAnnotation("@Mapper");

        // 添加 import org.apache.ibatis.annotations.Mapper;
        // className = "org.apache.ibatis.annotations.Mapper";
        // interfaze.addImportedType(new FullyQualifiedJavaType(className));

        // interfaze.getAnnotations().clear();

        return true;
    }

}
