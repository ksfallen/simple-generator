package com.simple.generator.xml.common;

import java.sql.Types;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * 基本 where 条件
 * User: Jfeng
 * Date: 2017/5/25
 */
public class BaseWhereElementGenerator extends AbstractXmlElementGenerator {

    private boolean isAlreadyCreatePrimaryKey = false;

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", XmlStatementId.BASE_WHREE_SQL));

        StringBuilder sb = new StringBuilder();

        XmlElement dynamicElement = new XmlElement("where");
        answer.addElement(dynamicElement);

        // 自增长主键
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            createPrimaryKeyElement(sb, dynamicElement, introspectedColumn);
        }

        for (IntrospectedColumn column : introspectedTable.getNonPrimaryKeyColumns()) {

            // 自定义主键
            // String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn);
            // String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
            //
            // if (!isAlreadyCreatePrimaryKey && isPrimaryKey(tableName, columnName)) {
            //     createPrimaryKeyElement(sb, dynamicElement, introspectedColumn);
            //     isAlreadyCreatePrimaryKey = true;
            //     continue;
            // }

            XmlElement isNotNullElement = new XmlElement("if");
            sb.setLength(0);
            sb.append(column.getJavaProperty());
            sb.append(" != null");

            // 字符串需要判断长度为0的空串
            if (column.getJdbcType() == Types.VARCHAR){
                sb.append(" and " );
                sb.append(column.getJavaProperty());
                sb.append(" != '' ");
            }
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(column));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(column));

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    private void createPrimaryKeyElement(StringBuilder sb, XmlElement dynamicElement, IntrospectedColumn introspectedColumn) {
        XmlElement isNotNullElement = new XmlElement("if");
        sb.setLength(0);
        sb.append(introspectedColumn.getJavaProperty());
        sb.append(" != null");
        isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
        dynamicElement.addElement(isNotNullElement);

        sb.setLength(0);
        sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
        sb.append(" = ");
        sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));

        isNotNullElement.addElement(new TextElement(sb.toString()));
    }
}
