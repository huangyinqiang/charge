CREATE TABLE `charge_pile` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `province` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `detail_location` varchar(50) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `socket_number` int(11) NOT NULL,
  `total_intensity` double NOT NULL,
  `total_voltage` double NOT NULL,
  `is_online` tinyint(1) NOT NULL,
  `online_date` date DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `charge_socket` (
  `id` bigint(20) NOT NULL,
  `charge_pile_id` bigint(20) NOT NULL,
  `is_used` tinyint(1) NOT NULL,
  `start_power` double NOT NULL,
  `charge_intensity` double NOT NULL,
  `charge_time` bigint(20) DEFAULT NULL,
  `charge_state` int(11) DEFAULT NULL COMMENT '充电状态， 1：充电中  0：充电截止',
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `charge_socket_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `charge_socket_id` bigint(20) NOT NULL,
  `start_power` double DEFAULT NULL,
  `charge_intensity` double DEFAULT NULL,
  `charge_time` bigint(20) DEFAULT NULL,
  `charge_state` int(11) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8




/*2018 8 - 5 数据库的扩展  开始              */
CREATE TABLE `yc_charge_pile` (
  `id` bigint(20) NOT NULL,
  `sn` bigint(20) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `province` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `detail_location` varchar(50) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `power_max` bigint(20) NOT NULL,
  `socket_sum` int(11) NOT NULL,
  `company_id` bigint(20) DEFAULT NULL COMMENT '设备所属公司外键',
  `total_intensity` bigint(20) NOT NULL,
  `total_voltage` bigint(20) NOT NULL,
  `is_online` tinyint(1) NOT NULL COMMENT '设备是否已经启用',
  `online_date` date DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL COMMENT '1,正常，2，离线，3，故障。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `yc_charge_socket` (
  `id` bigint(20) NOT NULL,
  `charge_pile_id` bigint(20) NOT NULL,
  `is_used` tinyint(1) NOT NULL,
  `start_power` bigint(20) NOT NULL,
  `charge_power` bigint(20) DEFAULT NULL,
  `charge_intensity` bigint(20) NOT NULL,
  `charge_time` bigint(20) DEFAULT NULL,
  `charge_state` int(11) DEFAULT NULL COMMENT '充电状态， 1：充电中  0：充电截止',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `yc_charge_socket_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `charge_socket_id` bigint(20) NOT NULL,
  `start_power` double DEFAULT NULL,
  `charge_intensity` double DEFAULT NULL,
  `charge_time` bigint(20) DEFAULT NULL,
  `charge_state` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `yc_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(200) DEFAULT NULL COMMENT '公司名称',
  `taxpayer_id` varchar(200) DEFAULT NULL COMMENT '纳税人号',
  `province` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `detail_location` varchar(200) DEFAULT NULL,
  `legal_person` varchar(50) DEFAULT NULL,
  `admin_id` bigint(20) DEFAULT NULL COMMENT '管理员id',
  `balance_rate` double DEFAULT NULL COMMENT '代理公司和总公司的结算比例',
  `balance_type` int(3) DEFAULT NULL COMMENT '代理公司和总公司结算的类型：1，按照用户实缴，2，用户消费。',
  `weixin_account` varchar(200) DEFAULT NULL COMMENT '各公司微信收款账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `yc_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `virtual_money` int(8) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `expiry_time` datetime DEFAULT NULL,
  `sum` int(5) DEFAULT NULL COMMENT '发行优惠券的总数',
  `balance_rate` double DEFAULT NULL COMMENT '代理公司承担的比例',
  `condition` int(5) NOT NULL DEFAULT '0' COMMENT '使用优惠券的条件，超过该金额可以使用的该优惠券',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `yc_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `introduce` varchar(500) DEFAULT NULL,
  `admin_id` bigint(50) DEFAULT NULL COMMENT '项目负责人外键',
  `admin_name` varchar(50) DEFAULT NULL,
  `admin_tel` varchar(20) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL COMMENT '项目所属id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `yc_tuser_and_coupon` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `tuser_id` bigint(8) DEFAULT NULL,
  `coupon_id` bigint(8) DEFAULT NULL,
  `coupon_amount` int(5) DEFAULT NULL COMMENT '该用户拥有此类型优惠券的个数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


/*2018 8 - 16 数据库的扩展  开始              */
alter table yc_charge_socket add `charge_socket_sn` int(2) DEFAULT NULL COMMENT '充电插座的sn';


/*2018 8 - 28 数据库的修改              */
CREATE TABLE `sysuser_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sysuser_id` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sysuser_company_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8

update yc_company set id=3  where company_name='昊方';
insert into yc_company(id,pid,company_name) values(1,0,'总部');
update yc_company set pid=1  where company_name='云创';
update yc_company set pid=100000  where company_name='昊方';
update yc_company set pid=100000  where company_name='旺座';
update yc_company set pid=100000  where company_name='测试公司';


/*新增项目活动*/
-- auto-generated definition
create table yc_project_activity
(
  id          int auto_increment
    primary key,
  project_id  bigint       null,
  name        varchar(200) null,
  type        varchar(8)   not null
  comment '类型(CH:充电)',
  money       int(8)       not null
  comment '充值金额',
  chargeNum   int(8)       null,
  coupon      int(4)       null
  comment '送优惠金额',
  status      char         not null
  comment '状态(有效:Y 无效N)',
  remark      varchar(128) null,
  start_time  datetime     null,
  expiry_time datetime     null,
  sum         int(5)       null,
  province    varchar(32)  null,
  city        varchar(32)  null,
  location    varchar(100) null,
  actNum      int          null
);

/* 新增对运营商付款的数据记录  */
-- auto-generated definition
create table yc_pay_agent_history
(
  id                  int auto_increment
    primary key,
  openId              varchar(64) charset utf8mb4 not null
  comment '微信用户的唯一识别',
  nickName            varchar(32) charset utf8mb4 not null
  comment '昵称',
  tel                 varchar(15)                 null
  comment '手机号码',
  company_id          bigint                      null,
  start_time          datetime                    null,
  end_time            datetime                    null,
  recharge_money      int(8)                      not null
  comment '本期充值金额',
  recharge_money_real int(8)                      not null
  comment '本期充值实际金额',
  charge_money        int(8)                      not null
  comment '本期充电消费金额',
  charge_money_real   int(8)                      not null
  comment '本期充电消费实际金额',
  temp_money          int(8)                      not null
  comment '本期临时充电消费金额',
  balance_rate        double                      null
  comment '代理公司和总公司的结算比例',
  pay_sum             int(8)                      not null
  comment '结算给运营商的总金额',
  pay_time            datetime                    null,
  last_surplus        int(8)                      not null
  comment '上期结余',
  surplus             int(8)                      not null
  comment '本期结余',
  pay_type            int(2)                      null
  comment '付款方式 1：微信 2：银行卡 3：现金',
  weixin_account      varchar(200)                null
  comment '微信账号',
  bank_account        varchar(200)                null
  comment '银行账号',
  bank_name           varchar(200)                null
  comment '开户行',
  operator_name       varchar(50)                 null
  comment '操作员姓名',
  operator_time       datetime                    null
  comment '操作员时间'
);




