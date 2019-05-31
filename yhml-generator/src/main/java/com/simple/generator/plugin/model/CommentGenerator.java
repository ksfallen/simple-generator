package com.simple.generator.plugin.model;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import com.simple.generator.util.StringTool;

public class CommentGenerator extends DefaultCommentGenerator {


	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn column) {
		if (StringTool.hasNotValue(column.getRemarks())) {
			return;
		}

		if (field.getName().equalsIgnoreCase("serialVersionUID")) {
		    return;
        }

		field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + column.getRemarks().trim());
		field.addJavaDocLine(" */");
	}

}
