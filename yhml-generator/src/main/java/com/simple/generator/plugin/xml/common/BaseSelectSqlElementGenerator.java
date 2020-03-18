package com.simple.generator.plugin.xml.common;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByPrimaryKeyElementGenerator;

import static com.simple.generator.plugin.xml.common.XmlStatementId.BASE_SELECT_SQL;

/**
 * 查询条件
 * User: Jfeng
 * Date: 2017/5/25
 */
public class BaseSelectSqlElementGenerator extends SelectByPrimaryKeyElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", BASE_SELECT_SQL));

        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());

        // 列出各个字段
        // Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
        // while (iter.hasNext()) {
        //     sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter.next()));
        //
        //     if (iter.hasNext()) {
        //         sb.append(", "); //$NON-NLS-1$
        //     }
        //
        //     if (sb.length() > 120) {
        //         answer.addElement(new TextElement(sb.toString()));
        //         sb.setLength(0);
        //     }
        // }
        // answer.addElement(new TextElement(sb.toString()));

        sb.setLength(0);
        sb.append("from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getWhereIncludeElement());

        if (context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    protected XmlElement getWhereIncludeElement() {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", XmlStatementId.BASE_WHREE_SQL));
        return answer;
    }
}
