package com.citytsm.model;


import cn.hutool.core.annotation.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AbnomalModel extends CsvModel {
    @Alias(value = "交易商户")
    private String tradeName;
    @Alias(value = "商户订单号")
    private String orderNum;
    @Alias(value = "渠道")
    private String channelName;
    @Alias(value = "发卡主体")
    private String issuerName;
    @Alias(value = "卡号")
    private String cardNo;
    @Alias(value = "上送日期")
    private String createTime;
    @Alias(value = "交易日期")
    private String bizTime;
    @Alias(value = "交易金额")
    private String amt;
    @Alias(value = "异常交易原因")
    private String reason;     //异常原因
}
