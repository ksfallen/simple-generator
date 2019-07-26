package com.simple.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 账户表
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("account_info")
public class AccountInfo extends Model<AccountInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "pk_id", type = IdType.AUTO)
    private Long pkId;

    /**
     * 账户id
     */
    private String acctId;

    /**
     * 用户唯一标识
     */
    private String userUuid;

    /**
     * 卡号
     */
    private String acctNo;

    /**
     * 城市代码
     */
    private String cityId;

    /**
     * 第三方账号关联id
     */
    private Integer openRelId;

    /**
     * 关联卡公司号
     */
    private String merchantNo;

    /**
     * 账户开通来源
     */
    private String accountSource;

    /**
     * 账户子类型
     */
    private Integer subType;

    private Integer acctType;

    /**
     * 区分学生卡--2、老年卡--3、普通卡--1
     */
    private Integer cardType;

    /**
     * 信用额度
     */
    private Integer creditLimit;

    /**
     * 账户状态
     */
    private Integer accountStatus;

    /**
     * 账户余额
     */
    private Integer balance;

    /**
     * 入金总额
     */
    private Integer totalIncome;

    /**
     * 出金总额
     */
    private Integer totalOutcome;

    private String ver;

    /**
     * 资金生效日期
     */
    private Integer gmtValidDate;

    /**
     * 资金失效日期
     */
    private Integer gmtExpireDate;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 冻结金额
     */
    private Integer frozenAmt;

    @Override
    protected Serializable pkVal(){
        return this.pkId;
    }
}
