package com.simple.generator.plugin;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import com.simple.generator.util.StringTool;
import com.yhml.core.util.PropertiesUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 生成 DTO 类
 */
@Slf4j
public class DtoPlugin extends PluginAdapter {

    private String targetPackage;
    private String targetProject;
    private String fileEncoding = "utf-8";
    private String modelPostfix = "DTO"; // DTO 后缀
    private String dtoRootClass = "com.simple.common.base.bean.BaseBean";

    @Override
    public boolean validate(List<String> warnings) {
        targetProject = context.getJavaModelGeneratorConfiguration().getTargetProject();
        targetPackage = PropertiesUtil.getString("package.name", "");
        dtoRootClass = PropertiesUtil.getString("dto.rootClass", "");

        if (StringTool.hasNotValue(targetPackage)) {
            targetPackage = "";
            return false;
        }

        targetPackage = targetPackage + ".entity.dto";
        log.info("dtoPackageName \t{}", targetPackage);

        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> files = new ArrayList<>();

        // 继承类
        FullyQualifiedJavaType superClassType = null;
        if (StringTool.hasValue(dtoRootClass)) {
            superClassType = new FullyQualifiedJavaType(dtoRootClass);
        }

        //  model 类
        FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        FullyQualifiedJavaType targetType = new FullyQualifiedJavaType(targetPackage + "." + modelType.getShortName() + modelPostfix);

        TopLevelClass topLevelClass = new TopLevelClass(targetType);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addImportedType(superClassType);
        topLevelClass.setSuperClass(superClassType);

        // lombok
        topLevelClass.addImportedType("lombok.Getter");
        topLevelClass.addImportedType("lombok.Setter");
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");

        files.add(new GeneratedJavaFile(topLevelClass, targetProject, fileEncoding, context.getJavaFormatter()));

        return files;
    }

}
