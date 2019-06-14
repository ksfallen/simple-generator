package com.simple.generator.plugin.codegen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.springframework.util.FileSystemUtils;

import lombok.extern.slf4j.Slf4j;

import static com.simple.generator.util.FullyJavaTypeUtil.*;
import static com.yhml.core.util.StringUtil.toLowerCaseFirst;

/**
 * 生成service类
 */
@Slf4j
public class ServicePlugin extends PluginAdapter {

    private FullyQualifiedJavaType mapperType;
    private FullyQualifiedJavaType modelType;

    private String rootClass;
    private String targetPackage;
    private String targetProject;


    public ServicePlugin() {
        super();
    }



    @Override
    public boolean validate(List<String> warnings) {
        // 文件生成路径
        this.targetProject = gc.getOutputDir();
        // service 包名
        this.targetPackage = pc.getService();
        this.rootClass = gc.getSuperServiceClass();

        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        mapperType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());

        // final String targetPackage = this.targetPackage + ".iml";

        List<GeneratedJavaFile> files = new ArrayList<>();

        String serviceName = "";
        if (gc.isSuportServiceInterface()) {
            serviceName = pc.getServiceImpl() + "." + modelType.getShortName() + gc.getServiceImplSuffix();
        } else {
            serviceName = pc.getService() + "." + modelType.getShortName() + gc.getServiceSuffix();
        }

        TopLevelClass topLevelClass = new TopLevelClass(new FullyQualifiedJavaType(serviceName));
        addService(topLevelClass, introspectedTable, files);

        if (gc.isSuportServiceInterface()) {
            addServceInterface(topLevelClass, files);
        }


        return files;
    }

    /**
     * add service 接口
     *
     * @return
     */
    private FullyQualifiedJavaType addServceInterface(TopLevelClass topLevelClass ,List<GeneratedJavaFile>files) {
        String serviceName = targetPackage + "." + modelType.getShortName() + gc.getServiceSuffix();

        FullyQualifiedJavaType serviceInterfacType = new FullyQualifiedJavaType(serviceName);
        Interface serviceInterface = new Interface(serviceInterfacType);
        serviceInterface.setVisibility(JavaVisibility.PUBLIC);

        topLevelClass.addSuperInterface(serviceInterfacType);
        topLevelClass.addImportedType(serviceInterfacType);

        files.add(new GeneratedJavaFile(serviceInterface, targetProject, fileEncoding, context.getJavaFormatter()));

        return serviceInterfacType;
    }

    /**
     * 添加实现类
     */
    protected void addService(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, List<GeneratedJavaFile> files) {
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        addImport(topLevelClass);

        addMapper(topLevelClass);

        addLogger(topLevelClass);

        // 生成文件
        files.add(new GeneratedJavaFile(topLevelClass, targetProject, fileEncoding, context.getJavaFormatter()));
    }

    /**
     * 添加字段
     */
    protected void addMapper(TopLevelClass topLevelClass) {
        String fieldName = toLowerCaseFirst(mapperType.getShortName());
        FullyQualifiedJavaType returnType = mapperType;

        // 泛型注入 spring 4 支持泛型注解,  mapper 返回类型可以不带<model>泛型
        if (gc.isGenericTypeAutowired()) {
            returnType = new FullyQualifiedJavaType(mapperType.getShortName() + "<" + modelType.getShortName() + ">");
            topLevelClass.addImportedType(modelType);
        }

        Field field = new Field();
        field.addAnnotation("@Autowired");
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(returnType);            // 类型
        field.setName(fieldName);            // 设置变量名

        topLevelClass.addField(field);
        topLevelClass.addImportedType(mapperType);
    }

    /**
     * 添加 抽象类实现
     *
     * @param topLevelClass
     * @deprecated
     */
    protected void addAbstractMethodImpl(TopLevelClass topLevelClass) {
        if (!gc.isGenericTypeAutowired())
            return;

        Method method = new Method();
        method.addAnnotation("@Override");
        method.setVisibility(JavaVisibility.PROTECTED);

        String mapperTargetPackage = properties.getProperty("packageName") + ".mapper";
        FullyQualifiedJavaType returnType =
                new FullyQualifiedJavaType(mapperTargetPackage + ".BaseCommonMapper<" + modelType.getShortName() + ">");

        method.setReturnType(returnType);
        method.setName("getMapper");
        method.addBodyLine("return " + toLowerCaseFirst(mapperType.getShortName()) + ";");

        topLevelClass.addImportedType(returnType);
        topLevelClass.addMethod(method);
    }


    /**
     * 导入需要的类
     */
    private void addImport(TopLevelClass topLevelClass) {
        // if (enableLogger) {
        //     loggerClass = properties.getProperty("loggerClass", "org.slf4j.Logger");
        //     loggerFactoryClass = properties.getProperty("loggerFactoryClass", "org.slf4j.LoggerFactory");
        //
        //     topLevelClass.addImportedType(loggerClass);
        //     topLevelClass.addImportedType(loggerFactoryClass);
        // }

        // if (enableAnnotation) {
        FullyQualifiedJavaType autowired = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
        FullyQualifiedJavaType service = new FullyQualifiedJavaType("org.springframework.stereotype.Service");

        topLevelClass.addImportedType(service);
        topLevelClass.addImportedType(autowired);
        topLevelClass.addAnnotation("@Service");
        // }

        topLevelClass.addImportedType(mapperType);

        if (rootClass != null) {
            topLevelClass.addImportedType(rootClass);
            topLevelClass.setSuperClass(rootClass + "<" + modelType.getShortName() + ">");
        }
    }

    /**
     * 导入logger
     */
    private void addLogger(TopLevelClass topLevelClass) {
        topLevelClass.addAnnotation(_Slf4j);
        topLevelClass.addImportedType(slf4j);
    }
}
