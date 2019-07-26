package com.simple.generator.db;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Jfeng
 * @date: 2019-07-26
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class SimpleTable extends Table {

    private LinkedHashSet<Column> columns = new LinkedHashSet<>();

    // private boolean isPrimaryKey;

    public SimpleTable(String name) {
        super(null, null, Identifier.toIdentifier(name, true), false);
    }

    public Set<Column> getColumns(){
        Iterator iterator = getColumnIterator();
        while (iterator.hasNext()) {
            Object next =  iterator.next();
            columns.add((Column) next);
        }
        return columns;
    }

}
