package com.citytsm;

import com.citytsm.model.AbnomalModel;
import com.yhml.core.util.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import cn.hutool.db.handler.BeanListHandler;
import cn.hutool.db.handler.NumberHandler;
import cn.hutool.db.sql.SqlExecutor;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jfeng
 * @date 2020/8/4
 */
@Slf4j
public class ReceiptService extends BaseCitytsmService {

    public static void main(String[] args) throws SQLException {
        ReceiptService service = new ReceiptService();
        service.abnormal();
    }

    //  导出异常交易
    public void abnormal() throws SQLException {
        String cityCode = "411300";
        String tradeName = "南阳公交公司";
        String mainMerchantName = "南阳电子公交卡";
        String date = DateUtils.nowDate();
        //@@formatter:off
        String countSql = "select count(*) from receipt_order_offline_pay_fail where city_id=? and gmt_create between ? and ?";
        String sql = "SELECT " +
                " '南阳公交公司'                                      AS 交易商户, " +
                " concat(\"'\", req_seq_no)                         AS 商户订单号, " +
                " '南阳电子公交卡'                                    AS 发卡主体, " +
                " asset_name                                        AS 渠道, " +
                " concat(\"'\", card_no)                            AS 卡号, " +
                " FROM_UNIXTIME(gmt_create)                         AS 上送日期, " +
                " biz_time                                          AS 交易日期, " +
                " FORMAT(amt/100,2)                                 AS 交易金额, " +
                " concat(\"code:\", code, \";message:\", message)   AS 异常交易原因 " +
                " FROM receipt_order_offline_pay_fail " +
                " WHERE city_id=? AND gmt_create BETWEEN ? AND ? LIMIT ?,?";
        // @formatter:on

        long start = DateUtils.toTimestamp("2020-07-01");
        long end = DateUtils.toTimestamp("2020-08-01") - 1;

        int index = 1;
        int pageNum = 0;
        int pageSize = 30000;
        int total = 0;

        @Cleanup Connection conn = getConn("rs0");
        BeanListHandler<AbnomalModel> handler = BeanListHandler.create(AbnomalModel.class);

        Long count = (Long) SqlExecutor.query(conn, countSql, NumberHandler.create(), cityCode, start, end);
        log.info("数据总数:" + count);

        if (count.equals(0)) {
            return;
        }

        while (true) {
            List<AbnomalModel> list = SqlExecutor.query(conn, sql, handler, cityCode, start, end, (pageNum++ * pageSize), pageSize);
            if (CollectionUtils.isEmpty(list)) {
                break;
            }
            String path = "./异常交易";
            String fileName = "异常交易-" + (index++) + ".csv";
            File file = new File(path, fileName);
            writeFile(list, file);
            total += list.size();
            if (list.size() < pageSize) {
                break;
            }
        }
        log.info("导出总数:" + total);
    }


}
