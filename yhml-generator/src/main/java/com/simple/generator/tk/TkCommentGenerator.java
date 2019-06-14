package com.simple.generator.tk;

import java.text.MessageFormat;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.internal.util.StringUtility;

import tk.mybatis.mapper.generator.MapperCommentGenerator;

import static com.yhml.core.util.StringUtil.toLowerCaseFirst;


/**
 * 功能说明: <br>
 * 系统版本: v1.0<br>
 * 开发人员: @author HuJianfeng<br>
 * 开发时间: 2016年7月21日<br>
 */
public class TkCommentGenerator extends MapperCommentGenerator {

    @Override
    public void addComment(XmlElement xmlElement) { }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) { }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) { }

    /**
     * 字段注塑和注解
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks().trim());
            field.addJavaDocLine(" */");
        }
        // 添加注解
        if (field.isTransient()) {
            field.addAnnotation("@Transient");
        }

        for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
            if (introspectedColumn == column) {
                field.addAnnotation("@Id");
                break;
            }
        }


        String column = introspectedColumn.getActualColumnName();

        if (StringUtility.stringContainsSpace(column) || introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled()) {
            column = introspectedColumn.getContext().getBeginningDelimiter() + column + introspectedColumn.getContext().getEndingDelimiter();
        }
        if (!column.equals(introspectedColumn.getJavaProperty())) {
            // @Column
            field.addAnnotation("@Column(name = \"" + column + "\")");
        }

        //  <generatedKey column="{0}Id" sqlStatement="Mysql" identity="true"/>
        GeneratedKey generatedKey = introspectedTable.getTableConfiguration().getGeneratedKey();
        if (generatedKey == null) {
            return;
        }

        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();

        // 获取主键字段
        String generatedKeyColumn = MessageFormat.format(generatedKey.getColumn(), toLowerCaseFirst(tableName), tableName);

        // 设置主键 isIdentity = true
        if (generatedKey.isIdentity() & column.equals(generatedKeyColumn)) {
            introspectedColumn.setIdentity(true);
        }

        String sqlStatement = generatedKey.getRuntimeSqlStatement();

        if (introspectedColumn.isIdentity()) {
            switch (sqlStatement.toUpperCase()) {
                case "JDBC":
                    field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
                    break;
                default:
                    field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
                    break;
            }
        }

        if (introspectedColumn.isSequenceColumn()) {
            switch (sqlStatement.toUpperCase()) {
                case "ORACLE":
                    field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY, "
                            + "generator = " + getKeyGeneratorForOracle(introspectedTable) + ")");
                    break;
                case "POSTGRE":
                    field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY, "
                            + "generator = " + getKeyGeneratorForPostgre(introspectedTable) + ")");
                    break;
                default:
                    field.addAnnotation("@SequenceGenerator(name=\"\","
                            + "sequenceName = \"" + generatedKey.getRuntimeSqlStatement() + "\")");
                    break;
            }
        }
    }

    /**
     * @return select tablename_SEQ.nextval from dual
     */
    private String getKeyGeneratorForOracle(IntrospectedTable table) {
        return "\"select " + table.getFullyQualifiedTable() + "_SEQ.nextval" + " from dual\"";

        //在 Oracle 中，如果需要是 SEQ_TABLENAME，那么可以配置为 select SEQ_{1} from dual
        // String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        // String sql = MessageFormat.format(introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement(), tableName, tableName.toUpperCase());
        // return sql;
    }


    private String getKeyGeneratorForPostgre(IntrospectedTable table) {
        return  "\"selecet nextval(" + "'" + table.getFullyQualifiedTable() + "_id_seq'" + ")\"";
    }

}
