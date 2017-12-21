-- MySQL 5.7
-- 西郊国际移动交易支付系统
-- 2017-12-01

use `xjgjpay`;

drop table if exists `member_info`;
-- 会员信息(对接结算系统的会员信息)
create table `member_info`(
  `id` bigint not null auto_increment comment '会员id',
  `member_id` varchar(50) default '' comment '会员编号',
  `member_type` int default '0' comment '会员类型',
  `member_name` varchar(50) not null comment '会员名',
  `card_id` varchar(50) default '' comment '会员卡号',
  `mobile` varchar(20) not null comment '银行卡绑定手机号',
  `password` varchar(100) default '' comment '密码',
  `email` varchar(50) default '' comment '电子邮箱',
  `id_card` varchar(50) default '' comment '身份证号',
  `id_card_type` int default '1' comment '身份证类型(默认1为身份证)',
  `is_available` tinyint(1) default '0' comment '可用标识(0-不可用;1-可用)',
  `remark` varchar(100) default '' comment '备注',
  `member_address` varchar(100) default '' comment '会员地址',
  `to_corp_address` varchar(100) default '' comment '商品去向地址',
  `gmt_create` datetime comment '创建时间',
  `gmt_modified` datetime comment '修改时间',
  primary key pk_id(`id`),
  unique key uk_card_code(`member_id`),
  index idx_card_type(`member_type`)
) engine=InnoDB default charset=utf8 comment '会员信息';

drop table if exists `member_bankcard`;
-- 会员绑定银行卡信息
create table `member_bankcard`(
	`id` bigint not null auto_increment comment 'id',
	`bank_acc_name` varchar(50) default '' comment '银行账户姓名',
	`bank_acc_card` varchar(50) default '' comment '银行卡号',
	`bank_code` varchar(50) default '' comment '发卡行编号',
	`bf_bind_id` varchar(100) default '' comment '宝付绑定标识号',
	`member_info_id` bigint not null comment '会员id',
	`remark` varchar(100) comment '备注',
	`gmt_create` datetime comment '创建时间',
	`gmt_modified` datetime comment '修改时间',
	primary key pk_id(`id`),
	index idx_member_info_id(`member_info_id`)
) engine=InnoDB default charset=utf8 comment '会员绑定银行卡信息';

drop table if exists `trade_log`;
-- 交易记录
create table `trade_log`(
	`id` bigint not null auto_increment comment '交易记录id',
	`trans_sn` varchar(50) not null comment '交易流水号',
	`trans_type` int default '0' comment '交易类型(1-充值;2-提款;3-消费;4-转账;5-收款)',
	`seller_order_id` varchar(50) default '' comment '商户订单号',
	`amt_money` decimal(18,2) not null comment '交易金额',
	`pay_mode_id` int default '0' comment '支付方式(1-宝付;2-西郊结算系统)',
	`bank_acc_name` varchar(50) default '' comment '银行账户姓名',
	`bank_acc_card` varchar(50) default '' comment '银行卡号',
	`bank_code` varchar(50) default '' comment '发卡行编号',
	`bf_bind_id` varchar(100) default '' comment '宝付绑定标识号',
	`remark` varchar(100) comment '备注',
	`gmt_create` datetime comment '交易时间',
	primary key pk_id(`id`),
	unique key uk_trans_sn(`trans_sn`),
	index idx_gmt_create(`gmt_create`,`trans_type`)
) engine=InnoDB default charset=utf8 comment '交易记录';
