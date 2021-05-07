package com.citytsm;

import com.citytsm.model.CsvModel;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.db.ds.DSFactory;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Cleanup;
import lombok.SneakyThrows;

/**
 * @author Jfeng
 * @date 2020/8/24
 */
public class BaseCitytsmService {
    @SneakyThrows
    public Connection getConn(String datasource) {
        @Cleanup Connection conn = DSFactory.get(datasource).getConnection();
        return conn;
    }

    void writeFile(List<? extends CsvModel> list, File file) {
        CsvWriter writer = CsvUtil.getWriter(file, CharsetUtil.CHARSET_GBK);
        List<String> data = list.stream().map(t -> t.toCsvData()).collect(Collectors.toList());
        String[] header = list.get(0).toHeader();
        if (ArrayUtil.isNotEmpty(header)) {
            writer.write(header);
        }
        writer.write(data);
        writer.close();
    }
}
