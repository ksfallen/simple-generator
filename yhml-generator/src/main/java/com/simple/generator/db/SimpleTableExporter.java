package com.simple.generator.db;

import java.util.Iterator;

import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Constraint;
import org.hibernate.mapping.Table;
import org.hibernate.mapping.UniqueKey;
import org.hibernate.tool.schema.internal.StandardTableExporter;

import com.yhml.core.util.StringUtil;

/**
 * @author: Jfeng
 * @date: 2019-07-26
 */
public class SimpleTableExporter extends StandardTableExporter {

    public static SimpleMetadata metadata = SimpleMetadata.INSTANCE;

    public SimpleTableExporter(Dialect dialect) {
        super(dialect);
    }

    public String getSqlCreateStrings(Table table) {
        StringBuilder buf = new StringBuilder(tableCreateString(table.hasPrimaryKey())).append(' ');
        buf.append(table.getQuotedName(dialect)).append(" (\n");

        boolean isPrimaryKeyIdentity = table.hasPrimaryKey();

        String pkColName = null;
        if (table.hasPrimaryKey()) {
            Column pkColumn = (Column) table.getPrimaryKey().getColumns().iterator().next();
            pkColName = pkColumn.getQuotedName(dialect);
        }

        final Iterator columnItr = table.getColumnIterator();
        boolean isFirst = true;

        while (columnItr.hasNext()) {
            if (isFirst) {
                isFirst = false;
            } else {
                buf.append(",\n");
            }

            final Column col = (Column) columnItr.next();

            String colName = col.getQuotedName(dialect);

            buf.append("  ").append(colName).append(' ');

            if (isPrimaryKeyIdentity && colName.equals(pkColName)) {
                if (dialect.getIdentityColumnSupport().hasDataTypeInIdentityColumn()) {
                    buf.append(col.getSqlType(dialect, metadata));
                }
                buf.append(" ").append(dialect.getIdentityColumnSupport().getIdentityColumnString(col.getSqlTypeCode(metadata)));
            } else {

                buf.append(col.getSqlType(dialect, metadata));

                String defaultValue = col.getDefaultValue();
                if (defaultValue != null) {
                    buf.append(" default ").append(defaultValue);
                }

                buf.append(col.isNullable() ? dialect.getNullColumnString() : " not null");
            }

            if (col.isUnique()) {
                String keyName = Constraint.generateName("UK_", table, col);
                UniqueKey uk = table.getOrCreateUniqueKey(keyName);
                uk.addColumn(col);
                buf.append(dialect.getUniqueDelegate().getColumnDefinitionUniquenessFragment(col));
            }

            if (col.getCheckConstraint() != null && dialect.supportsColumnCheck()) {
                buf.append(" check (").append(col.getCheckConstraint()).append(")");
            }

            if (StringUtil.hasText(col.getComment())) {
                buf.append(dialect.getColumnComment(col.getComment()));
            }
        }

        if (table.hasPrimaryKey()) {
            buf.append(",\n  ").append(table.getPrimaryKey().sqlConstraintString(dialect));
        }

        buf.append("\n)");

        applyTableTypeString(buf);

        if (StringUtil.hasText(table.getComment())) {
            buf.append(dialect.getTableComment(table.getComment()));
        }

        buf.append(";");

        return buf.toString();
    }

    public String getSqlDropStrings(Table table) {
        StringBuilder buf = new StringBuilder("drop table ");
        if (dialect.supportsIfExistsBeforeTableName()) {
            buf.append("if exists ");
        }

        buf.append(table.getQuotedName(dialect));

        if (dialect.supportsIfExistsAfterTableName()) {
            buf.append(" if exists");
        }

        buf.append(";");
        return buf.toString();
    }
}
