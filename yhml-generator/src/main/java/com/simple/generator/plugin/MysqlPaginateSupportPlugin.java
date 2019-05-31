package com.simple.generator.plugin;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 分页插件
 */
public class MysqlPaginateSupportPlugin extends PluginAdapter {

    private final String LIMIT = "limit";
    private final String LIMIT_START = "limitStart";

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addField(topLevelClass, introspectedTable, LIMIT);
        addField(topLevelClass, introspectedTable, LIMIT_START);
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        createPaginateXml(element);
        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        createPaginateXml(element);
        return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }

    private void createPaginateXml(XmlElement element) {
        XmlElement ifElement = new XmlElement("if");
        ifElement.addAttribute(new Attribute("test", "limit!=null and limitStart!=null and limit gte 0 and limitStart gte 0 "));
        ifElement.addElement(new TextElement("limit #{limitStart} , #{limit}"));
        element.addElement(ifElement);
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

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }
}
