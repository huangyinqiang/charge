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