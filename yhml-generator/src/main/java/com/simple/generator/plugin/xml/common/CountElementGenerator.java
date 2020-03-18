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
package com.simple.generator.plugin.xml.common;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * @author Jeff Butler
 */
public class CountElementGenerator extends AbstractXmlElementGenerator {

    public CountElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");

        String parameterType = introspectedTable.getBaseRecordType();

        answer.addAttribute(new Attribute("id", XmlStatementId.COUNT));
        answer.addAttribute(new Attribute("resultType", "java.lang.Integer"));
        answer.addAttribute(new Attribute("parameterType", parameterType));

        // context.getCommentGenerator().addComment(answer);

        IntrospectedColumn column = introspectedTable.getAllColumns().get(0);
        String name = column.getActualColumnName();
        String sb = "select count(" + name + ") from " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        answer.addElement(new TextElement(sb));
        answer.addElement(getWhereIncludeElement());

        if (context.getPlugins().sqlMapCountByExampleElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    protected XmlElement getWhereIncludeElement() {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", XmlStatementId.BASE_WHREE_SQL));
        return answer;
    }
}
