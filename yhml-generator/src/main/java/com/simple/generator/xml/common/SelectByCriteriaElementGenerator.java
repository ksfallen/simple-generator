package com.simple.generator.xml.common;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import static com.simple.generator.xml.common.XmlStatementId.LIST_BY_CRITERIA;

/**
 * list 结果查询
 * User: Jfeng
 * Date: 2017/5/25
 */
public class SelectByCriteriaElementGenerator extends AbstractXmlElementGenerator {

    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", LIST_BY_CRITERIA));
        // answer.addAttribute(new Attribute("resultType", this.introspectedTable.getFullyQualifiedTable().getDomainObjectName()));
        answer.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        String parameterType = introspectedTable.getBaseRecordType();

        answer.addAttribute(new Attribute("parameterType", parameterType));
        answer.addElement(this.getBaseSelectSqllement());
        if (this.context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }
    }

    protected XmlElement getBaseSelectSqllement() {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", XmlStatementId.BASE_SELECT_SQL));
        return answer;
    }
}
