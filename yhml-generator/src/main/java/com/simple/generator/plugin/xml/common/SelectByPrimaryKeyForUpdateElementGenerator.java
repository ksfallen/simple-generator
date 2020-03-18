package com.simple.generator.plugin.xml.common;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class SelectByPrimaryKeyForUpdateElementGenerator extends AbstractXmlElementGenerator {
    public SelectByPrimaryKeyForUpdateElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        String hasSplitTable = introspectedTable.getTableConfigurationProperty("hasSplitTable");
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", "selectByPrimaryKeyForUpdate")); //$NON-NLS-1$
        if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
            answer.addAttribute(new Attribute("resultMap", introspectedTable.getResultMapWithBLOBsId()));//$NON-NLS-1$
        } else {
            answer.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));//$NON-NLS-1$
        }

        String parameterType;
        if (hasSplitTable!=null && hasSplitTable.equalsIgnoreCase("true")) {
            parameterType = "map";
        }else{
            if (introspectedTable.getRules().generatePrimaryKeyClass()) {
                parameterType = introspectedTable.getPrimaryKeyType();
            } else {
                // PK fields are in the base class. If more than on PK
                // field, then they are coming in a map.
                if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
                    parameterType = "map"; //$NON-NLS-1$
                } else {
                    parameterType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().toString();
                }
            }
        }

        answer.addAttribute(new Attribute("parameterType", parameterType));//$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select "); //$NON-NLS-1$

        if (stringHasValue(introspectedTable.getSelectByPrimaryKeyQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
            sb.append("' as QUERYID,"); //$NON-NLS-1$
        }
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());
        if (introspectedTable.hasBLOBColumns()) {
            answer.addElement(new TextElement(",")); //$NON-NLS-1$
            answer.addElement(getBlobColumnListElement());
        }
        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append("where "); //$NON-NLS-1$
                and = true;
            }
            sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }
        answer.addElement(new TextElement(" for update"));
        parentElement.addElement(answer);
    }

}
