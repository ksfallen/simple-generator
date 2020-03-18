package com.simple.generator.plugin.xml.common;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import static com.simple.generator.plugin.xml.common.XmlStatementId.DELETE_BATCH_IDS;
import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.*;

/**
 * User: Jfeng
 * Date: 2017/5/25
 */
public class DeleteBatchElementGenerator extends AbstractXmlElementGenerator {


    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("delete");
        answer.addAttribute(new Attribute("id", DELETE_BATCH_IDS));

        String parameterType = "java.util.List";
        answer.addAttribute(new Attribute("parameterType", parameterType));

        // 单个主键
        createForeach(answer);
        // createForeach2(answer);

        if (this.context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    /*
        单个主键
        delete from tableName  where id IN
        <foreach collection="list" item="item" index="index" open="(" close=")" separator=",">
           #{item}
        </foreach>
     */
    private void createForeach(XmlElement answer) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from ");
        sb.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" where ");
        IntrospectedColumn column = this.introspectedTable.getPrimaryKeyColumns().get(0);
        sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(column));
        sb.append(" in ");

        answer.addElement(new TextElement(sb.toString()));

        XmlElement foreach = createForeachXmlElement(",");
        foreach.addElement(new TextElement(getParameterIn(column)));
        answer.addElement(foreach);
    }

    /*
       多个主键
      <foreach close="" collection="list" index="index" item="item" open="" separator=";">
        delete from tableName where id = #{item}
      </foreach>
    */
    protected void createForeach2(XmlElement answer) {
        XmlElement foreach = createForeachXmlElement(";");
        foreach.addElement(getContent());
        answer.addElement(foreach);
    }

    private TextElement getContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from ");
        sb.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" ");

        boolean and = false;

        for (IntrospectedColumn introspectedColumn : this.introspectedTable.getPrimaryKeyColumns()) {
            if (and) {
                sb.append("  and ");
            } else {
                sb.append("where ");
                and = true;
            }
            sb.append(getEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(getParameterIn(introspectedColumn));
        }
        return new TextElement(sb.toString());
    }

    private XmlElement createForeachXmlElement(String separator) {
        XmlElement foreach = new XmlElement("foreach");
        foreach.addAttribute(new Attribute("collection", "list"));
        foreach.addAttribute(new Attribute("item", "item"));
        // foreach.addAttribute(new Attribute("index", "index"));
        foreach.addAttribute(new Attribute("open", "("));
        foreach.addAttribute(new Attribute("close", ")"));
        foreach.addAttribute(new Attribute("separator", separator));
        return foreach;
    }

}
