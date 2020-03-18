package com.simple.generator.plugin.model;

import com.simple.generator.util.StringTool;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.simple.generator.util.FullyJavaTypeUtil.*;

/**
 * User: Jfeng
 * Date: 2017/5/24
 */
@Slf4j
public class EntityPlugin extends PluginAdapter {

    public EntityPlugin() {
        super();
        initPath();
    }

    public boolean validate(List<String> warnings) {
        return true;
    }

    private void initPath() {
        String directory = gc.getOutputDir();

        // File file = new File(directory);
        // FileSystemUtils.deleteRecursively(file);
        // file.mkdirs();

        // context.getJavaModelGeneratorConfiguration().setTargetPackage(pc.getEntity());
        // context.getJavaClientGeneratorConfiguration().setTargetPackage(pc.getMapper());
        // context.getSqlMapGeneratorConfiguration().setTargetPackage(pc.getXml());

        // log.info("delete dir            ===== {}", directory);
        log.info("output dir            ===== {}", directory);
        log.info("package               ===== {}", pc.getPackageName());
        log.info("entityPackage         ===== {}", pc.getEntity());
        log.info("mapperPackage         ===== {}", pc.getMapper());
        log.info("servicePackage        ===== {}", pc.getService());
        log.info("controllerPackage     ===== {}", pc.getController());
        log.info("xmlPackage            ===== {}", pc.getXml());
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

        addAnnotation(topLevelClass);

        if (gc.isSwagger2()) {
            // 表字段注释
            Map<String, String> map = introspectedTable.getAllColumns().stream().collect(Collectors.toMap(c -> c.getJavaProperty(),
                    c -> c.getRemarks().trim()));

            List<IntrospectedColumn> primaryKey= introspectedTable.getPrimaryKeyColumns();
            for (IntrospectedColumn column : primaryKey) {
                map.put(column.getJavaProperty(), "主键");
            }

            topLevelClass.getFields().forEach(field -> {
                String value = map.get(field.getName());
                if (StringTool.hasValue(value)) {
                    field.addAnnotation(StringTool.format(_ApiModelProperty, value));
                }
            });

            topLevelClass.addImportedType(apiModelProperty);
        }
    }

    private void addAnnotation(TopLevelClass topLevelClass) {
        topLevelClass.getMethods().clear();

        topLevelClass.addAnnotation(_Data);
        topLevelClass.addImportedType(data);

        topLevelClass.addAnnotation(_Accessors);
        topLevelClass.addImportedType(accessors);

        if (topLevelClass.getSuperClass() != null) {
            topLevelClass.addAnnotation(_EqualsAndHashCode);
            topLevelClass.addImportedType(equalsAndHashCodeta);
        }
    }
}
