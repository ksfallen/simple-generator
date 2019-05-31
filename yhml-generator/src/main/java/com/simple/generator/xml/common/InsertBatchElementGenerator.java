package com.simple.generator.xml.common;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.internal.util.StringUtility;

import com.simple.generator.util.StringTool;

import static com.simple.generator.util.StringTool.hasDefalutValue;

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

        createSelectKey(answer);
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

        boolean isFirstColumn = true;
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            // 主键不需要 insert
            // cannot set values on identity fields
            if (column.isIdentity()) {
                continue;
            }

            // 过滤有默认值的 更新时间戳
            if (hasDefalutValue(column)) {
                continue;
            }

            // 去末尾的逗号和换行符
            if (isFirstColumn) {
                isFirstColumn = false;
            } else {
                insertClause.append(", ");
            }

            insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(column));
        }

        insertClause.append(") values ");
        OutputUtilities.xmlIndent(insertClause, 4);
        answer.addElement(new TextElement(insertClause.toString()));
    }

    protected void createForeach(XmlElement answer) {
        XmlElement foreach = createForeachXmlElement();
        addValueElement(foreach);
        answer.addElement(foreach);
    }

    private XmlElement createForeachXmlElement() {
        XmlElement foreach = new XmlElement("foreach");
        foreach.addAttribute(new Attribute("collection", "list"));
        foreach.addAttribute(new Attribute("item", "item"));
        foreach.addAttribute(new Attribute("index", "index"));
        foreach.addAttribute(new Attribute("open", "("));
        foreach.addAttribute(new Attribute("close", ")"));
        foreach.addAttribute(new Attribute("separator", ","));

        return foreach;
    }

    public void addValueElement(XmlElement xmlElement) {
        StringBuilder valueClause = new StringBuilder();
        OutputUtilities.xmlIndent(valueClause, 1);

        List<String> list = new ArrayList<>();

        boolean isFirstColumn = true;

        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            // cannot set values on identity fields
            if (column.isIdentity()) {
                continue;
            }

            // 过滤有默认值的 更新时间戳
            if (hasDefalutValue(column)) {
                continue;
            }

            // 去末尾的逗号和换行符
            if (isFirstColumn) {
                isFirstColumn = false;
            } else {
                valueClause.append(",\n");
                OutputUtilities.xmlIndent(valueClause, 4);

            }

            valueClause.append(getParameterClause(column));

            // if (iter.hasNext()) {
            //     value.append(",\n");
            //     OutputUtilities.xmlIndent(value, 4);
            // }
        }

        list.add(valueClause.toString());

        for (String clause : list) {
            xmlElement.addElement(new TextElement(clause));
        }
    }

    private String getParameterClause(IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();
        sb.append("#{item.");
        sb.append(introspectedColumn.getJavaProperty());
        sb.append(",jdbcType=");
        sb.append(introspectedColumn.getJdbcTypeName());
        if (StringUtility.stringHasValue(introspectedColumn.getTypeHandler())) {
            sb.append(",typeHandler=");
            sb.append(introspectedColumn.getTypeHandler());
        }

        sb.append('}');
        return sb.toString();
    }
}
