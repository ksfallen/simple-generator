package com.simple.generator.plugin;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.util.Lists;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import com.simple.SimpleGenerator;
import com.simple.generator.util.StringTool;
import com.yhml.core.util.PinyinUtil;
import com.yhml.core.util.PropertiesUtil;
import com.yhml.core.util.StringUtil;
import com.yhml.core.util.ValidateUtil;

import lombok.extern.slf4j.Slf4j;

import static com.simple.generator.util.FullyJavaTypeUtil.*;

/**
 * 生成 字典类
 */
@Slf4j
public class DicEntryPlugin extends PluginAdapter {

    private static List<String> dics = Lists.newArrayList();

    static {
        dics.add("flowControl");
        dics.add("status");
        dics.add("state");
        dics.add("type");
    }

    private String targetProject;
    private String targetPackage;
    private String fileEncoding = "utf-8";

    // 生成的文件
    private FullyQualifiedJavaType targetType = new FullyQualifiedJavaType(targetPackage + ".DicCons");

    public static void main(String[] args) {
        String str = "用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定";
    }

    @Override
    public boolean validate(List<String> warnings) {
        targetProject = context.getJavaModelGeneratorConfiguration().getTargetProject();
        targetPackage = PropertiesUtil.getString("package.name", "");

        if (StringTool.hasNotValue(targetPackage)) {
            return false;
        }

        targetPackage = targetPackage + ".entity.dic";
        log.info("dicPackageName \t{}", targetPackage);

        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> files = new ArrayList<>();

        // 基本字段, 不包含主键
        for (IntrospectedColumn column : introspectedTable.getBaseColumns()) {
            String columnName = column.getActualColumnName();

            if (isDicType(columnName)) {
                // 字段注释
                String table = introspectedTable.getFullyQualifiedTableNameAtRuntime();
                String remark = column.getRemarks();
                log.info("Dic: Table = {}, columnName = {}, remark = {}", table, columnName, remark);
                String str = StringUtil.camelToUnderline(table) + "_" + columnName;

                List<GeneratedJavaFile> list = SimpleGenerator.myBatisGenerator.getGeneratedJavaFiles();
                Optional<GeneratedJavaFile> javaFile = list.stream().filter(file -> file.getFileName().equals("DicCons.java")).findFirst();


                if (javaFile.isPresent()) {
                    GeneratedJavaFile file = javaFile.get();
                    CompilationUnit unit = file.getCompilationUnit();

                } else {
                    TopLevelEnumeration topLevelEnum = new TopLevelEnumeration(targetType);
                    topLevelEnum.setVisibility(JavaVisibility.PUBLIC);
                    topLevelEnum.addImportedType(stringutils);
                    topLevelEnum.addImportedType(getter);
                    topLevelEnum.addImportedType(allargsconstructor);
                    topLevelEnum.addAnnotation(_Getter);
                    topLevelEnum.addAnnotation(_AllArgsConstructor);

                    GeneratedJavaFile file = build(targetType, topLevelEnum, remark);
                    if (file != null) {
                        files.add(file);
                    }
                }
            }
        }

        return files;

    }

    private GeneratedJavaFile getGeneratedJavaFile() {
        List<GeneratedJavaFile> list = SimpleGenerator.myBatisGenerator.getGeneratedJavaFiles();
        Optional<GeneratedJavaFile> javaFile = list.stream().filter(file -> file.getFileName().equals("DicCons")).findFirst();
        return javaFile.orElse(null);

    }

    private GeneratedJavaFile build(FullyQualifiedJavaType targetType, TopLevelEnumeration topLevelEnum, String remark) {
        // remark = "开启控制 on: 开启 , off:关闭";
        // remark = "开启控制 on,off;";
        // remark = "开启控制 开启,关闭";

        GeneratedJavaFile file = null;

        // 开启控制 on: 开启 , off:关闭;
        Map<String, String> map = praseRemarkAsMap(remark);
        file = buildFile(targetType, topLevelEnum, map);

        if (file != null) {
            return file;
        }

        List<String> list = praseRemarkAsList(remark);

        boolean matches = false;
        for (String temp : list) {
            matches = ValidateUtil.isWords(temp);
        }

        // 开启控制 on,off; (英文)
        if (matches) {
            file = buildFile(targetType, topLevelEnum, list);
        } else {
            // 开启控制 开启,关闭; (中文)
            Map<String, String> finalMap = map;
            list.forEach(s -> finalMap.put(PinyinUtil.getFirstLetters(s), s));
            file = buildFile(targetType, topLevelEnum, finalMap);
        }


        return file;
    }

    /**
     * 单个 Fild 枚举
     *
     * @return
     */
    private GeneratedJavaFile buildFile(FullyQualifiedJavaType targetType, TopLevelEnumeration topLevelEnum, List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        addEnumConstant(topLevelEnum, list);
        addMethods(targetType, topLevelEnum);
        addFilds(topLevelEnum, true);

        return new GeneratedJavaFile(topLevelEnum, targetProject, context.getJavaFormatter());
    }

    /**
     * 多个 Fild 枚举
     *
     * @param targetType
     * @param topLevelEnum
     * @param list
     * @return
     */
    private GeneratedJavaFile buildFile(FullyQualifiedJavaType targetType, TopLevelEnumeration topLevelEnum, Map<String, String> map) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }

        addEnumConstant(topLevelEnum, map);
        addMethods(targetType, topLevelEnum);
        addFilds(topLevelEnum, false);

        return new GeneratedJavaFile(topLevelEnum, targetProject, context.getJavaFormatter());
    }

    /**
     * @param topLevel
     * @param isSingle 是否单字段
     */
    private void addFilds(TopLevelEnumeration topLevel, boolean isSingle) {
        Field key = new Field();
        key.setVisibility(JavaVisibility.PRIVATE);
        key.setType(new FullyQualifiedJavaType("String"));
        key.setName("key");

        Field name = new Field();
        name.setVisibility(JavaVisibility.PRIVATE);
        name.setType(new FullyQualifiedJavaType("String"));
        name.setName("name");

        if (isSingle) {
            topLevel.addField(name);
            return;
        }

        topLevel.addField(key);
        topLevel.addField(name);
    }

    private void addMethods(FullyQualifiedJavaType type, TopLevelEnumeration topLevel) {
        FullyQualifiedJavaType stringType = new FullyQualifiedJavaType("String");
        String shortName = type.getShortName(); // 类名

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(stringType);
        method.setName("getNameByKey");
        method.addParameter(new Parameter(stringType, "key"));

        method.addBodyLine("String result = \"\";");
        method.addBodyLine("for (" + shortName + " temp: " + shortName + ".values()) {");
        method.addBodyLine("if (StringUtils.equals(name, temp.name)) {");
        method.addBodyLine("result = temp.getName();");
        method.addBodyLine("break;");
        method.addBodyLine("}");
        method.addBodyLine("}");
        method.addBodyLine("return result;");

        topLevel.addMethod(method);
    }

    /**
     * @param topLevel
     * @param type
     * @param isSingle 是否单个字段
     */
    private void addConstructor(FullyQualifiedJavaType type, TopLevelEnumeration topLevel, boolean isSingle) {
        FullyQualifiedJavaType stringType = new FullyQualifiedJavaType("String");

        Method constructor = new Method();
        constructor.setName(type.getShortName());
        constructor.setConstructor(true);

        if (isSingle) {
            constructor.addParameter(0, new Parameter(stringType, "name"));
            constructor.addBodyLine("this.name = name;");
        } else {
            constructor.addParameter(0, new Parameter(stringType, "key"));
            constructor.addParameter(1, new Parameter(stringType, "name"));
            constructor.addBodyLine("this.key = key;");
            constructor.addBodyLine("this.name = name;");
        }

        topLevel.addMethod(constructor);
    }

    /**
     * key(key, name);
     *
     * @param levelClass
     */
    private void addEnumConstant(TopLevelEnumeration topLevelEnum, Map<String, String> map) {
        if (MapUtils.isEmpty(map)) {
            return;
        }

        map.forEach((k, v) -> {
            String sb = (NumberUtils.isDigits(k) ? "_" : "") + k.toUpperCase() + "(\"" + k + "\", \"" + v + "\")";
            topLevelEnum.addEnumConstant(sb);
        });
    }

    /**
     * name(name);
     *
     * @param levelClass
     */
    private void addEnumConstant(TopLevelEnumeration topLevelEnum, List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        list.forEach(k -> {
            String sb = (NumberUtils.isDigits(k) ? "_" : "") + k.toUpperCase() + "(\"" + k + "\")";
            topLevelEnum.addEnumConstant(sb);
        });
    }

    /**
     * 开启控制 on: 开启 , off:关闭
     * 解析为 map = on:开启, off:关闭
     */
    private Map<String, String> praseRemarkAsMap(String remark) {
        // key value 分割
        String split = "[:：-]";
        String regex = "[\\w]+\\s*" + split + "\\s*[\\u4e00-\\u9fa5]+";

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(remark);

        Map<String, String> map = new HashMap<>();

        while (matcher.find()) {
            String group = matcher.group();
            String[] ss = group.split(split);
            if (ss.length == 2) {
                map.put(ss[0].trim(), ss[1].trim());
            }
        }

        return map;
    }

    /**
     * 开启控制  开启,关闭
     */
    private List<String> praseRemarkAsList(String remark) {
        String regex = "[\\w\\u4e00-\\u9fa5]+(,[\\w\\u4e00-\\u9fa5]+)";

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(remark);

        List<String> list = new ArrayList<>();

        while (matcher.find()) {
            String group = matcher.group();
            String[] ss = group.split(",");
            Arrays.stream(ss).forEach(s -> list.add(s.trim()));
        }

        return list;
    }


    private boolean isDicType(String name) {
        for (String dic : dics) {
            if (dic.toUpperCase().contains(name.toUpperCase())) {
                return true;
            }
        }

        return false;
    }

}
