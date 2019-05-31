package com.simple.generator.xml.common;

import java.sql.Types;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import com.simple.generator.util.StringTool;

/**
 * User: Jfeng
 * Date: 2017/5/25
 */
public class UpdateBatchElementGenerator extends AbstractXmlElementGenerator {

    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", "updateBatch"));

        String parameterType = "java.util.List";
        answer.addAttribute(new Attribute("parameterType", parameterType));

        createForeach(answer);

        if (this.context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    protected void createForeach(XmlElement answer) {
        XmlElement foreach = createForeachXmlElement();
        foreach.addElement(getUpdateContent());
        foreach.addElement(getSetElement());
        foreach.addElement(getWhereContent());

        answer.addElement(foreach);
    }

    private TextElement getWhereContent() {
        boolean and = false;

        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : this.introspectedTable.getPrimaryKeyColumns()) {
            if (and) {
                sb.append("  and ");
            } else {
                sb.append("where ");
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
        }

        return new TextElement(sb.toString());
    }

    private TextElement getUpdateContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
        return new TextElement(sb.toString());
    }


    public XmlElement getSetElement() {
        StringBuilder sb = new StringBuilder();
        XmlElement dynamicElement = new XmlElement("set");

        for (IntrospectedColumn column : introspectedTable.getNonPrimaryKeyColumns()) {

            // 过滤有默认值的 更新时间戳
            if (column.getJdbcType() == Types.TIMESTAMP && StringTool.isNotBlank(column.getDefaultValue())) {
                continue;
            }

            sb.setLength(0);
            XmlElement isNotNullElement = new XmlElement("if");
            sb.append(column.getJavaProperty());
            sb.append(" != null");
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(column));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(column));

            isNotNullElement.addElement(new TextElement(sb.toString()));
            dynamicElement.addElement(isNotNullElement);
        }

        return dynamicElement;
    }


    private XmlElement createForeachXmlElement() {
        XmlElement foreach = new XmlElement("foreach");
        foreach.addAttribute(new Attribute("collection", "list"));
        foreach.addAttribute(new Attribute("item", "item"));
        foreach.addAttribute(new Attribute("index", "index"));
        foreach.addAttribute(new Attribute("open", ""));
        foreach.addAttribute(new Attribute("close", ""));
        foreach.addAttribute(new Attribute("separator", ";"));

        return foreach;
    }
}
