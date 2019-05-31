/*
 *  Copyright 2009 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.simple.generator.xml.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.GeneratedKey;

import static com.simple.generator.util.StringTool.*;

/**
 * @author Jfeng
 */
public class InsertElementGenerator extends AbstractXmlElementGenerator {

    private boolean isSimple;

    public InsertElementGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", introspectedTable.getInsertStatementId()));

        FullyQualifiedJavaType parameterType;
        if (isSimple) {
            parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        } else {
            parameterType = introspectedTable.getRules().calculateAllFieldsClass();
        }

        answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        context.getCommentGenerator().addComment(answer);

        // 主键策略
        IntrospectedColumn introspectedColumn = getGeneratedKey(introspectedTable);

        // if the column is null, then it's a configuration error. The warning has already been reported
        if (introspectedColumn != null) {
            // set 主键对应的 identity 为true
            introspectedColumn.setIdentity(true);

            GeneratedKey gk = introspectedTable.getGeneratedKey();
            if (gk.isJdbcStandard()) {
                answer.addAttribute(new Attribute("useGeneratedKeys", "true"));
                answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
            } else {
                answer.addElement(getSelectKey(introspectedColumn, gk));
            }
        }

        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into ");
        insertClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());         // 表名
        insertClause.append(" (");
        valuesClause.append("values (\n");
        // OutputUtilities.xmlIndent(valuesClause, 4); // 空格

        List<String> valuesClauses = new ArrayList<>();
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();

        boolean isFirstColumn = true;

        while (iter.hasNext()) {
            IntrospectedColumn column = iter.next();

            // 主键不需要 insert
            if (column.isIdentity()) {
                continue;
            }

            // 过滤有默认值的 更新时间戳 stampDate
            if (hasDefalutValue(column)) {
                continue;
            }

            // 去末尾的逗号和换行符
            if (isFirstColumn) {
                isFirstColumn = false;
                OutputUtilities.xmlIndent(valuesClause, 4);
            } else {
                insertClause.append(", ");
                valuesClause.append(",\n");
                OutputUtilities.xmlIndent(valuesClause, 4);
            }

            insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(column));
            valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(column));
        }


        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));

        valuesClause.append("\n");
        OutputUtilities.xmlIndent(valuesClause, 3);
        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }

        if (context.getPlugins().sqlMapInsertElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
