package com.simple.generator.db;

import org.hibernate.dialect.InnoDBStorageEngine;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.MySQLStorageEngine;

/**
 * @author: Jfeng
 * @date: 2019-07-26
 */
public class SimplMysqlDialect extends MySQL5Dialect {

    public static final SimplMysqlDialect INSTANCE = new SimplMysqlDialect();

    public static void main(String[] args) {
        SimplMysqlDialect dialect = new SimplMysqlDialect();
    }

    @Override
    protected MySQLStorageEngine getDefaultMySQLStorageEngine() {
        return InnoDBStorageEngine.INSTANCE;
    }

    @Override
    public String getTableTypeString() {
        return super.getTableTypeString() + " AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 ";
    }
}
