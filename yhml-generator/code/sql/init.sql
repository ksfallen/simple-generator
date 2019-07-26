/*
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Schema         : simple

 Date: 2019-07-26 16:47:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `t_user_info`
-- ----------------------------
drop table if exists `t_user_info`;
create table `t_user_info` (
  user_id  integer  not null auto_increment   comment '用户id',
  user_uuid  varchar(255)   comment '用户唯一标识',
  user_name  varchar(255)   comment '用户名',
  password  varchar(255)   comment '支付渠道id，关联pay_channel表',
  random_string  varchar(255)   comment '随机串',
  status  integer   comment '用户状态',
  cert_no  varchar(255)   comment '证件号码',
  cert_type  integer   comment '证件类型',
  mobile  varchar(255)   comment '手机号码',
  user_level  integer   comment '用户级别',
  from_system  integer   comment '用户来源',
  gmt_create  integer   comment '',
  gmt_modified  integer   comment '',
  primary key (`user_id`)
) engine=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8  comment='';

-- ----------------------------
-- Table structure for `account_info`
-- ----------------------------
drop table if exists `account_info`;
create table `account_info` (
  pk_id  bigint  not null auto_increment   comment '主键',
  acct_id  varchar(255)   comment '账户id',
  user_uuid  varchar(255)   comment '用户唯一标识',
  acct_no  varchar(255)   comment '卡号',
  city_id  varchar(255)   comment '城市代码',
  open_rel_id  integer   comment '第三方账号关联id',
  merchant_no  varchar(255)   comment '关联卡公司号',
  account_source  varchar(255)   comment '账户开通来源',
  sub_type  integer   comment '账户子类型',
  acct_type  integer   comment '',
  card_type  integer   comment '区分学生卡--2、老年卡--3、普通卡--1',
  credit_limit  integer   comment '信用额度',
  account_status  integer   comment '账户状态',
  balance  integer   comment '账户余额',
  total_income  integer   comment '入金总额',
  total_outcome  integer   comment '出金总额',
  ver  varchar(255)   comment '',
  gmt_valid_date  integer   comment '资金生效日期',
  gmt_expire_date  integer   comment '资金失效日期',
  gmt_create  datetime   comment '',
  gmt_modified  datetime   comment '',
  frozen_amt  integer   comment '冻结金额',
  primary key (`pk_id`)
) engine=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8  comment='账户表';

