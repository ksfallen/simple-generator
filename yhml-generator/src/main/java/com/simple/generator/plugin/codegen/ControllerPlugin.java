package com.simple.generator.plugin.codegen;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import lombok.extern.slf4j.Slf4j;

import static com.simple.generator.util.FullyJavaTypeUtil.*;
import static com.yhml.core.util.StringUtil.toLowerCaseFirst;

/**
 * 生成service类
 */
@Slf4j
public class ControllerPlugin extends PluginAdapter {

    private FullyQualifiedJavaType mapperType;
    private FullyQualifiedJavaType modelType;

    private String rootClass;
    private String targetPackage;
    private String targetProject;
    private boolean isThintargetRuntime = false;


    public ControllerPlugin() {
        super();

        // 文件生成路径
        targetProject = gc.getOutputDir();

        // service 包名
        targetPackage = pc.getController();
    }


    @Override
    public boolean validate(List<String> warnings) {
        this.rootClass = gc.getSuperControllerClass();
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        FullyQualifiedJavaType controller =
                new FullyQualifiedJavaType(targetPackage + "." + modelType.getShortName() + gc.getControllerSuffix());

        TopLevelClass topLevelClass = new TopLevelClass(controller);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        addAnnotation(topLevelClass);
        addFiled(topLevelClass);

        List<GeneratedJavaFile> files = new ArrayList<>();

        files.add(new GeneratedJavaFile(topLevelClass, targetProject, fileEncoding, context.getJavaFormatter()));

        return files;
    }

    /**
     * 添加字段
     */
    protected void addFiled(TopLevelClass topLevelClass) {
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(targetPackage + "." + modelType.getShortName() + gc.getServiceSuffix());

        Field field = new Field();
        field.addAnnotation(_Autowired);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(service);            // 类型
        field.setName(toLowerCaseFirst(service.getShortName()));            // 设置变量名

        topLevelClass.addField(field);
        topLevelClass.addImportedType(service);
        topLevelClass.addImportedType(autowired);
    }

    private void addAnnotation(TopLevelClass topLevelClass) {
        topLevelClass.addAnnotation(_Slf4j);
        topLevelClass.addImportedType(slf4j);

        topLevelClass.addAnnotation(_RestController);
        topLevelClass.addImportedType(restController);
    }

}
