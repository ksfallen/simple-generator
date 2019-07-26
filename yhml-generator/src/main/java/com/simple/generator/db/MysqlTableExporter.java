package com.simple.generator.db;

/**
 * @author: Jfeng
 * @date: 2019-07-26
 */
public class MysqlTableExporter extends SimpleTableExporter {

    public MysqlTableExporter() {
        super(SimplMysqlDialect.INSTANCE);
    }
}
