package com.simple.generator.db;

import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.Column;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author: Jfeng
 * @date: 2019-07-26
 */
@SuppressWarnings("serial")
@Getter
@Setter
@Accessors(chain = true)
public class SimpleColumn extends Column {
    private boolean isPrimaryKey;

    public SimpleColumn(String columnName) {
        super(columnName);
    }

    @Override
    public String getQuotedName(Dialect d) {
        return d.openQuote() + getName() + d.closeQuote();
    }
}
