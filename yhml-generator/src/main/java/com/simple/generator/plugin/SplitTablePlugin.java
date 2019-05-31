package com.simple.generator.plugin;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 分表代码生成插件
 * Created by wanghonghui on 15/4/27.
 */
public class SplitTablePlugin extends PluginAdapter {

    private String hasSplitTable;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        hasSplitTable = introspectedTable.getTableConfigurationProperty("hasSplitTable");
        if (hasSplitTable!=null && hasSplitTable.equalsIgnoreCase("true")) {
            //追加分表id后缀
            introspectedTable.setSqlMapAliasedFullyQualifiedRuntimeTableName(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() + "_#{tableId}");
            introspectedTable.setSqlMapFullyQualifiedRuntimeTableName(introspectedTable.getFullyQualifiedTableNameAtRuntime()+"_#{tableId}");
        }
        super.initialized(introspectedTable);
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        if (hasSplitTable!=null && hasSplitTable.equalsIgnoreCase("true")) {
            List<Attribute> attributes = element.getAttributes();
            for(Attribute attribute : attributes){
                if(attribute.getName().equals("parameterType")){
                    attributes.remove(attribute);
                    attributes.add(new Attribute("parameterType","map"));
                }
            }
        }
        return super.sqlMapDeleteByPrimaryKeyElementGenerated(element,introspectedTable);
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        if (hasSplitTable!=null && hasSplitTable.equalsIgnoreCase("true")) {
            method.getParameters().remove(0);
            StringBuilder sb = new StringBuilder();

            FullyQualifiedJavaType type0 = new FullyQualifiedJavaType("Long");
            Parameter parameter0 = new Parameter(type0, "id");
            sb.setLength(0);
            sb.append("@Param(\""); //$NON-NLS-1$
            sb.append("id");
            sb.append("\")"); //$NON-NLS-1$
            parameter0.addAnnotation(sb.toString());
            method.addParameter(parameter0);

            FullyQualifiedJavaType type = new FullyQualifiedJavaType("Integer");
            Parameter parameter = new Parameter(type, "tableId");
            sb.setLength(0);
            sb.append("@Param(\""); //$NON-NLS-1$
            sb.append("tableId");
            sb.append("\")"); //$NON-NLS-1$
            parameter.addAnnotation(sb.toString());
            method.addParameter(parameter);
        }
        return super.clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        if (hasSplitTable!=null && hasSplitTable.equalsIgnoreCase("true")) {
            List<Attribute> attributes = element.getAttributes();
            for(Attribute attribute : attributes){
                if(attribute.getName().equals("parameterType")){
                    attributes.remove(attribute);
                    attributes.add(new Attribute("parameterType","map"));
                }
            }
        }
        return super.sqlMapSelectByPrimaryKeyElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        if (hasSplitTable!=null && hasSplitTable.equalsIgnoreCase("true")) {
            method.getParameters().remove(0);
            StringBuilder sb = new StringBuilder();

            FullyQualifiedJavaType type0 = new FullyQualifiedJavaType("Long");
            Parameter parameter0 = new Parameter(type0, "id");
            sb.setLength(0);
            sb.append("@Param(\""); //$NON-NLS-1$
            sb.append("id");
            sb.append("\")"); //$NON-NLS-1$
            parameter0.addAnnotation(sb.toString());
            method.addParameter(parameter0);

            FullyQualifiedJavaType type = new FullyQualifiedJavaType("Integer");
            Parameter parameter = new Parameter(type, "tableId");
            sb.setLength(0);
            sb.append("@Param(\""); //$NON-NLS-1$
            sb.append("tableId");
            sb.append("\")"); //$NON-NLS-1$
            parameter.addAnnotation(sb.toString());
            method.addParameter(parameter);
        }
        return super.clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        if (hasSplitTable!=null && hasSplitTable.equalsIgnoreCase("true")) {
            StringBuilder sb = new StringBuilder();
            FullyQualifiedJavaType type = new FullyQualifiedJavaType("Integer");
            Parameter parameter = new Parameter(type, "tableId");
            sb.setLength(0);
            sb.append("@Param(\""); //$NON-NLS-1$
            sb.append("tableId");
            sb.append("\")"); //$NON-NLS-1$
            parameter.addAnnotation(sb.toString());
            method.addParameter(parameter);
        }
        return super.clientUpdateByExampleSelectiveMethodGenerated(method,interfaze,introspectedTable);
    }

    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        if (hasSplitTable!=null && hasSplitTable.equalsIgnoreCase("true")) {
            StringBuilder sb = new StringBuilder();
            FullyQualifiedJavaType type = new FullyQualifiedJavaType("Integer");
            Parameter parameter = new Parameter(type, "tableId");
            sb.setLength(0);
            sb.append("@Param(\""); //$NON-NLS-1$
            sb.append("tableId");
            sb.append("\")"); //$NON-NLS-1$
            parameter.addAnnotation(sb.toString());
            method.addParameter(parameter);
        }
        return super.clientUpdateByExampleWithBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }

    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,  Interface interfaze, IntrospectedTable introspectedTable) {
        if (hasSplitTable!=null && hasSplitTable.equalsIgnoreCase("true")) {
            StringBuilder sb = new StringBuilder();
            FullyQualifiedJavaType type = new FullyQualifiedJavaType("Integer");
            Parameter parameter = new Parameter(type, "tableId");
            sb.setLength(0);
            sb.append("@Param(\""); //$NON-NLS-1$
            sb.append("tableId");
            sb.append("\")"); //$NON-NLS-1$
            parameter.addAnnotation(sb.toString());
            method.addParameter(parameter);
        }
        return super.clientUpdateByExampleWithoutBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }


    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (hasSplitTable!=null && hasSplitTable.equalsIgnoreCase("true")){
            //添加tableId字段
            addField(topLevelClass,introspectedTable, "tableId");
        }
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (hasSplitTable!=null && hasSplitTable.equalsIgnoreCase("true")){
            //添加tableId字段
            addField(topLevelClass,introspectedTable, "tableId");
        }
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    private void addField(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String fieldName) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(PrimitiveTypeWrapper.getIntegerInstance());
        field.setName(fieldName);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(creatSetterMethodName(fieldName));
        method.addParameter(new Parameter(PrimitiveTypeWrapper.getIntegerInstance(), fieldName));
        method.addBodyLine("this." + fieldName + "=" + fieldName + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(PrimitiveTypeWrapper.getIntegerInstance());
        method.setName(creatGetterMethodName(fieldName));
        method.addBodyLine("return " + fieldName + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    private String creatSetterMethodName(String field) {
        String camel = upperCaseFirst(field);
        return "set" + camel;
    }

    private String creatGetterMethodName(String field) {
        return "get" + upperCaseFirst(field);
    }

    private String upperCaseFirst(String field) {
        char c = field.charAt(0);
        String camel = Character.toUpperCase(c) + field.substring(1);
        return camel;
    }
}
