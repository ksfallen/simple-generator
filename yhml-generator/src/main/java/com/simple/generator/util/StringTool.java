package com.simple.generator.util;

import com.yhml.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.internal.util.StringUtility;

import java.sql.Types;
import java.text.MessageFormat;
import java.util.List;


/**
 * User: Jfeng
 * Date:  2017/6/8
 */
@Slf4j
public class StringTool {


    public static boolean isProeritestKey(String value) {
        if (value == null) {
            return false;
        }
        return value.matches("\\$\\{(\\w+\\.)+\\w+}");
    }

    public static boolean hasValue(String value) {
        return !isProeritestKey(value) && StringUtility.stringHasValue(value);
    }

    public static boolean hasNotValue(String value) {
        return !hasValue(value);
    }

    public static boolean isPrimaryKey(String tableName, String columnName) {
        return columnName.equals(StringUtil.toLowerCaseFirst(tableName) + "Id");
    }

    public static String upperCaseFirst(String field) {
        return StringUtil.toUpperCaseFirst(field);
    }

    public static String lowCaseFirst(String field) {
        return StringUtil.toLowerCaseFirst(field);
    }

    public static boolean isNotBlank(String string) {
        return StringUtils.isNotBlank(string);
    }

    /**
     * timestamp 字段并且有默认值
     *
     * @param column
     * @return
     */
    public static boolean isTimestamp(IntrospectedColumn column) {
        String value = column.getDefaultValue();
        return column.getJdbcType() == Types.TIMESTAMP && StringUtils.equals("CURRENT_TIMESTAMP", value);
    }

    /**
     * @param type
     * @return
     */
    public static String getShortName(String type) {
        return type.substring(type.lastIndexOf("."));
    }

    /**
     * 获取 主键字段
     *
     * @param table
     * @return
     */
    public static IntrospectedColumn getGeneratedKey(IntrospectedTable table) {
        IntrospectedColumn column = null;
        GeneratedKey gk = table.getGeneratedKey();

        if (gk != null) {
            // 格式化主键 {0}Id {0}:tableName {1}: TableName
            String tableName = table.getFullyQualifiedTableNameAtRuntime();
            String generatedKeyColumn = MessageFormat.format(gk.getColumn(), StringUtil.toLowerCaseFirst(tableName), tableName);
            column = table.getColumn(generatedKeyColumn);

            // 没有符合的主键策略 单一主键
            if (column == null) {
                List<IntrospectedColumn> columns = table.getPrimaryKeyColumns();
                column = columns.isEmpty() ? null : columns.get(0);
            }
        }

        return column;
    }

    public static String format(String text, Object... value) {
        return MessageFormat.format(text, value);
    }

}
