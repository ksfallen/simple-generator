package com.simple.generator.plugin.model;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import static com.simple.generator.util.FullyJavaTypeUtil.*;

/**
 * User: Jfeng
 * Date: 2017/5/24
 */
public class ModelPlugin extends PluginAdapter {

    public boolean validate(List<String> warnings) {
        return true;
    }


    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    protected void makeSerializable(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String rootClass = gc.getSuperEntityClass();

        if (rootClass != null) {
            FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(rootClass);
            topLevelClass.addImportedType(parameterType);
            topLevelClass.setSuperClass(parameterType);
        }

        addLombok(topLevelClass);
    }

    private void addLombok(TopLevelClass topLevelClass) {
        topLevelClass.getMethods().clear();
        topLevelClass.addAnnotation(_Getter);
        topLevelClass.addAnnotation(_Setter);

        topLevelClass.addImportedType(getter);
        topLevelClass.addImportedType(setter);

        if (topLevelClass.getSuperClass() == null) {
            topLevelClass.addAnnotation(_Tosirng);
            topLevelClass.addImportedType(tostring);
        }

    }
}
