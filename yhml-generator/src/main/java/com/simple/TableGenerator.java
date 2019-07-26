package com.simple;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.SimpleValue;
import org.hibernate.mapping.Table;
import org.junit.Before;
import org.junit.Test;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.simple.generator.db.SimplMysqlDialect;
import com.simple.generator.db.SimpleColumn;
import com.simple.generator.db.SimpleTable;
import com.simple.generator.db.SimpleTableExporter;
import com.simple.generator.util.JavaDocReader;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.yhml.core.util.ClassUtil;
import com.yhml.core.util.StringUtil;

import cn.hutool.core.date.DateTime;

public class TableGenerator extends SimplePlusGenerator {

    SimpleTableExporter exporter = new SimpleTableExporter(SimplMysqlDialect.INSTANCE);
    Map<String, ClassDoc> docMap;
    Set<Class> classSet;
    String templatePath;

    @Before
    public void before() {
        outputDir = "code/sql";
        packageName = "com.simple.entity";
        templatePath = "/generator/mysql.sql.vm";
        super.delete();

        docMap = JavaDocReader.getClassDoc(packageName);
        classSet = ClassUtil.getAllClass(packageName);
        tablePrefix = new String[]{"t_"};
    }

    @Test
    public void generator() {
        List<Table> list = getTableList();
        list.forEach(table -> {
            System.out.println();
            System.out.println(exporter.getSqlDropStrings(table));
            System.out.println(exporter.getSqlCreateStrings(table));
        });
    }

    @Test
    public void generatorFile() throws Exception {
        List<Table> list = getTableList();
        list.forEach(table -> exporter.getSqlCreateStrings(table));

        SimpleTable table = (SimpleTable) list.get(0);
        Map<String, Object> map = new HashMap<>();
        map.put("schema", "simple");
        map.put("tables", list);
        map.put("date", DateTime.now());

        new VelocityTemplateEngine().init(null).writer(map, templatePath, outputDir + "/init.sql");
    }

    private List<Table> getTableList() {
        List<Table> list = new ArrayList<>();
        for (Class cc : classSet) {
            Table table = getTable(cc);
            list.add(table);

            for (Field field : cc.getDeclaredFields()) {
                String fieldName = field.getName();

                if (StringUtil.equals(fieldName, "serialVersionUID")) {
                    continue;
                }

                SimpleColumn column = new SimpleColumn(StringUtil.camelToUnderline(fieldName).toLowerCase());

                if (isPrimaryKey(field)) {
                    PrimaryKey ppk = new PrimaryKey(table);
                    ppk.addColumn(column.setPrimaryKey(true));
                    table.setPrimaryKey(ppk);
                }

                SimpleValue simpleValue = new SimpleValue(SimpleTableExporter.metadata, table);
                simpleValue.setTypeName(field.getType().getTypeName());
                simpleValue.addColumn(column);

                applyFiledComment(cc, field, column);

                table.addColumn(column);
            }
        }

        return list;
    }


    private boolean isPrimaryKey(Field field) {
        Optional<Annotation> optional = Arrays.stream(field.getAnnotations()).filter(annotation -> annotation instanceof TableId).findAny();
        return optional.isPresent();
    }

    private Table getTable(Class cc) {
        String name = tablePrefix[0] + StringUtil.camelToUnderline(cc.getSimpleName()).toLowerCase();

        TableName annotation = (TableName) cc.getAnnotation(TableName.class);

        if (annotation != null) {
            name = annotation.value();
        }

        Table table = new SimpleTable(name);

        applyTableComment(cc, table);

        return table;
    }

    private void applyTableComment(Class cc, Table table) {
        if (docMap.containsKey(cc.getName())) {
            ClassDoc doc = docMap.get(cc.getName());
            String text = doc.commentText().replace("\n", ",").trim();
            table.setComment(text);
        }
    }

    private void applyFiledComment(Class cc, Field field, Column column) {
        if (docMap.containsKey(cc.getName())) {
            ClassDoc doc = docMap.get(cc.getName());
            FieldDoc[] fields = doc.fields(false);
            Optional<FieldDoc> optional = Arrays.stream(fields).filter(f -> f.name().equals(field.getName())).findFirst();

            if (optional.isPresent()) {
                String text = optional.get().commentText().replace("\n", ",").trim();
                column.setComment(text);
            }
        }
    }

}
