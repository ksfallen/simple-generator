package com.simple.generator.plugin.xml.common;

import com.simple.generator.util.StringTool;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.GeneratedKey;

import java.util.Iterator;

import static com.simple.generator.util.StringTool.isTimestamp;
import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.*;

/**
 * User: Jfeng
 * Date: 2017/5/25
 */
public class InsertBatchElementGenerator extends AbstractXmlElementGenerator {


    /*
      <insert id="insertBatch" parameterType="List">
       <selectKey resultType ="java.lang.Integer" keyProperty= "id" order= "AFTER">
         SELECT LAST_INSERT_ID()
       </selectKey>
        insert into tableName () values
        <foreach collection="list" item="item" index="index" open="" close="" separator=",">
        </foreach>
       </insert>
    */
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", "insertBatch"));
        String parameterType = "java.util.List";
        answer.addAttribute(new Attribute("parameterType", parameterType));

        // createSelectKey(answer);
        createInsertContent(answer);
        createForeach(answer);

        if (this.context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    private void createSelectKey(XmlElement answer) {
        // 主键策略
        IntrospectedColumn introspectedColumn = StringTool.getGeneratedKey(introspectedTable);

        // if the column is null, then it's a configuration error. The warning has already been reported
        if (introspectedColumn != null) {
            // set 主键对应的 identity 为true
            introspectedColumn.setIdentity(true);

            GeneratedKey gk = introspectedTable.getGeneratedKey();
            if (gk.isJdbcStandard()) {
                answer.addAttribute(new Attribute("useGeneratedKeys", "true"));  //$NON-NLS-2$
                answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
            } else {
                answer.addElement(getSelectKey(introspectedColumn, gk));
            }
        }
    }

    private void createInsertContent(XmlElement answer) {
        StringBuilder insertClause = new StringBuilder();
        insertClause.append("insert into ");
        insertClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" (");

        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
        while (iter.hasNext()) {
            IntrospectedColumn column = iter.next();

            // 主键不需要 insert 过滤有默认值的 更新时间戳
            if (column.isIdentity() || isTimestamp(column)) {
                continue;
            }
            OutputUtilities.xmlLineAndIndent(insertClause, 4);
            insertClause.append(getEscapedColumnName(column));
            if (iter.hasNext()) {
                insertClause.append(",");
            }
        }

        OutputUtilities.xmlLineAndIndent(insertClause, 2);
        insertClause.append(") values ");
        answer.addElement(new TextElement(insertClause.toString()));
    }

    protected void createForeach(XmlElement answer) {
        XmlElement foreach = new XmlElement("foreach");
        foreach.addAttribute(new Attribute("collection", "list"));
        foreach.addAttribute(new Attribute("item", "item"));
        // foreach.addAttribute(new Attribute("index", "index"));
        // foreach.addAttribute(new Attribute("open", "("));
        // foreach.addAttribute(new Attribute("close", ")"));
        foreach.addAttribute(new Attribute("separator", ","));
        addValueElement(foreach);
        answer.addElement(foreach);
    }

    public void addValueElement(XmlElement xmlElement) {
        StringBuilder valueClause = new StringBuilder();
        OutputUtilities.xmlIndent(valueClause, 1);
        valueClause.append("(");
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
        while (iter.hasNext()) {
            IntrospectedColumn column = iter.next();

            // 过滤有默认值的 更新时间戳
            if (column.isIdentity() || isTimestamp(column)) {
                continue;
            }
            OutputUtilities.xmlLineAndIndent(valueClause, 6);
            valueClause.append(getParameterForEach(column));

            if (iter.hasNext()) {
                valueClause.append(",");
            }
        }

        OutputUtilities.xmlLineAndIndent(valueClause, 4);
        valueClause.append(")");
        xmlElement.addElement(new TextElement(valueClause.toString()));
    }
}
