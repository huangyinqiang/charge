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
