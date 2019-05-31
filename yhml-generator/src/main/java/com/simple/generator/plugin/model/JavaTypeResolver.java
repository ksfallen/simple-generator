package com.simple.generator.plugin.model;

import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.util.StringUtility;

public class JavaTypeResolver extends JavaTypeResolverDefaultImpl {

    private boolean forceDateTime2String = false;

    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.forceDateTime2String = StringUtility.isTrue(properties.getProperty("forceDateTime2String"));

        //  强制 DECIMAL 转 double
        // typeMap.put(Types.DECIMAL, new JdbcTypeInformation("DOUBLE", new FullyQualifiedJavaType(Double.class.getName())));

        //  强制 date 转 String
        if (forceDateTime2String) {
            typeMap.put(Types.TIME, new JdbcTypeInformation("DATE", new FullyQualifiedJavaType(String.class.getName())));
            typeMap.put(Types.DATE, new JdbcTypeInformation("DATE", new FullyQualifiedJavaType(String.class.getName())));
            typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP", new FullyQualifiedJavaType(String.class.getName())));
        }
    }

    @Override
    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn column) {
        JdbcTypeInformation jdbcTypeInformation = typeMap.get(column.getJdbcType());
        Objects.requireNonNull(jdbcTypeInformation, "jdbcType:" + column.getJdbcType());

        FullyQualifiedJavaType answer = null;

        switch (column.getJdbcType()) {
            case Types.DECIMAL:
            case Types.NUMERIC:
                if (column.getScale() > 0 || column.getLength() > 18 || forceBigDecimals) {
                    answer = new FullyQualifiedJavaType(Double.class.getName());
                } else if (column.getLength() > 9) {
                    answer = new FullyQualifiedJavaType(Long.class.getName());
                } else {
                    answer = new FullyQualifiedJavaType(Integer.class.getName());
                }
                break;
            case Types.BIGINT:
                answer = new FullyQualifiedJavaType(Integer.class.getName());
                break;
            default:
                answer = jdbcTypeInformation.getFullyQualifiedJavaType();
                break;
        }

        return answer;
    }
}
