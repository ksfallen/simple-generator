package com.simple.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户唯一标识
     */
    private String userUuid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 支付渠道id，关联pay_channel表
     */
    private String password;

    /**
     * 随机串
     */
    private String randomString;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 证件号码
     */
    private String certNo;

    /**
     * 证件类型
     */
    private Integer certType;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户级别
     */
    private Integer userLevel;

    /**
     * 用户来源
     */
    private Integer fromSystem;

    private Integer gmtCreate;

    private Integer gmtModified;

    @Override
    protected Serializable pkVal(){
        return this.userId;
    }
}
