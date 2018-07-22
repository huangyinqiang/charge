/*
 Navicat Premium Data Transfer

 Source Server         : 111.230.148.108-so1
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 111.230.148.108:3306
 Source Schema         : zcurd_base

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 24/06/2018 15:30:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for common_file
-- ----------------------------
DROP TABLE IF EXISTS `common_file`;
CREATE TABLE `common_file`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL COMMENT '分类（1：图片，2：文件）',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图片地址',
  `sys_user_id` int(11) NOT NULL COMMENT '系统用户',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类型',
  `dict_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '键',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '值',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单名称',
  `menu_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单地址',
  `parent_id` int(11) DEFAULT NULL COMMENT '所属菜单',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标',
  `order_num` int(11) DEFAULT NULL COMMENT '顺序',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', NULL, 0, 'glyphicon-cog', 11, '2016-01-06 19:37:31');
INSERT INTO `sys_menu` VALUES (2, '在线表单', '/zcurdHead/list', 1, 'glyphicon-cloud', 1, '2016-01-07 21:41:21');
INSERT INTO `sys_menu` VALUES (3, '菜单管理', '/menu/list', 1, 'glyphicon-menu-hamburger', 2, '2016-01-06 19:37:38');
INSERT INTO `sys_menu` VALUES (4, '字典管理', '/zcurd/7/listPage', 1, 'glyphicon-book', 3, '2016-02-29 11:44:07');
INSERT INTO `sys_menu` VALUES (5, '用户管理', NULL, 0, 'glyphicon-user', 10, '2016-01-06 19:37:31');
INSERT INTO `sys_menu` VALUES (6, '角色管理', '/zcurd/8/listPage', 5, 'glyphicon-user', 1, '2016-01-07 03:32:08');
INSERT INTO `sys_menu` VALUES (7, '系统用户', '/zcurd/12/listPage', 5, 'glyphicon-king', 2, '2016-02-16 03:59:22');
INSERT INTO `sys_menu` VALUES (14, 'i易充管理平台', '/customer/listPage', 0, 'glyphicon-tower', 1, '2017-01-25 18:18:37');
INSERT INTO `sys_menu` VALUES (15, '微信充值', '/money/listPage', 14, 'glyphicon-off', 5, '2017-01-25 18:19:33');
INSERT INTO `sys_menu` VALUES (18, '设备管理', '/device/listPage', 22, 'glyphicon-print', 6, '2017-01-25 18:27:24');
INSERT INTO `sys_menu` VALUES (19, '用户管理', '/customer/listPage', 14, 'glyphicon-home', 1, '2017-03-30 23:41:46');
INSERT INTO `sys_menu` VALUES (20, '充卡记录', '/charge/listPage', 14, 'glyphicon-list-alt', 2, '2017-04-04 13:41:55');
INSERT INTO `sys_menu` VALUES (21, '挂失管理', '/lostcard/listPage', 14, 'glyphicon-glass', 3, '2017-04-04 17:03:49');
INSERT INTO `sys_menu` VALUES (22, '设备管理', NULL, 0, 'glyphicon-cog', 2, '2017-04-06 22:26:01');
INSERT INTO `sys_menu` VALUES (23, '秘钥管理', '/config/listPage', 22, 'glyphicon-indent-left', 7, '2017-04-06 22:27:29');
INSERT INTO `sys_menu` VALUES (24, '微信用户', '/tuser/listPage', 14, 'glyphicon-user', 8, '2017-04-06 23:00:22');
INSERT INTO `sys_menu` VALUES (25, '刷卡记录', '/cardlog/listPage', 14, 'glyphicon-inbox', 7, '2017-04-06 23:06:03');
INSERT INTO `sys_menu` VALUES (26, '内部管理', NULL, 0, 'glyphicon-eye-open', 5, '2017-04-06 23:07:26');
INSERT INTO `sys_menu` VALUES (27, '网络日志', '/zcurd/32/listPage', 26, 'glyphicon-plane', 1, '2017-04-06 23:08:06');
INSERT INTO `sys_menu` VALUES (28, '充电日志', '/zcurd/33/listPage', 26, 'glyphicon-off', 2, '2017-04-06 23:13:16');
INSERT INTO `sys_menu` VALUES (29, '微信消费', '/weixin/listPage', 14, 'glyphicon-hdd', 4, '2017-04-06 23:21:38');
INSERT INTO `sys_menu` VALUES (30, '优惠设置', '/zcurd/36/listPage', 26, 'glyphicon-star-empty', 4, '2017-04-06 23:24:18');
INSERT INTO `sys_menu` VALUES (31, '支付日志', '/zcurd/37/listPage', 26, 'glyphicon-qrcode', 3, '2017-04-06 23:27:22');
INSERT INTO `sys_menu` VALUES (33, '消费统计', '/weixinsum/listSumPage', 14, 'glyphicon-yen', 5, '2017-04-14 23:24:06');
INSERT INTO `sys_menu` VALUES (34, '充值统计', '/moneysum/listSumPage', 14, 'glyphicon-usd', 6, '2017-04-16 22:50:33');
INSERT INTO `sys_menu` VALUES (35, '功率日志', '/zcurd/38/listPage', 22, 'glyphicon-baby-formula', 8, '2017-04-22 18:45:34');
INSERT INTO `sys_menu` VALUES (38, '掉线日志', '/zcurd/39/listPage', 26, 'glyphicon-sort-by-attributes-alt', 6, '2017-08-31 21:50:30');
INSERT INTO `sys_menu` VALUES (39, '端口状态', '/zcurd/40/listPage', 26, 'glyphicon-hourglass', 7, '2017-08-31 21:51:30');
INSERT INTO `sys_menu` VALUES (40, '企业支付', NULL, 0, 'glyphicon-usd', 3, '2017-09-26 21:39:05');
INSERT INTO `sys_menu` VALUES (41, '代理付款', '/pay/listSumPage', 40, 'glyphicon-ruble', NULL, '2017-09-26 21:39:58');
INSERT INTO `sys_menu` VALUES (42, '付款记录', '/paylog/listPage', 40, 'glyphicon-time', 1, '2017-09-26 21:43:07');
INSERT INTO `sys_menu` VALUES (43, '代理设置', '/zcurd/41/listPage', 40, 'glyphicon-th-list', 3, '2017-09-26 21:43:53');

-- ----------------------------
-- Table structure for sys_menu_btn
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_btn`;
CREATE TABLE `sys_menu_btn`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_id` int(11) DEFAULT NULL COMMENT '所属菜单',
  `btn_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '按钮名称',
  `class_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '页面class名称',
  `method_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '后台method名称',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_Reference_1`(`menu_id`) USING BTREE,
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单按钮' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu_btn
-- ----------------------------
INSERT INTO `sys_menu_btn` VALUES (17, 2, '删除', 'delBtn', 'delete', '2016-10-25 09:02:54');
INSERT INTO `sys_menu_btn` VALUES (19, 7, '增加', 'addBtn', 'add,addPage', '2016-10-25 09:09:15');
INSERT INTO `sys_menu_btn` VALUES (20, 7, '修改', 'updateBtn', 'update,updatePage', '2016-10-25 09:09:15');
INSERT INTO `sys_menu_btn` VALUES (21, 7, '删除', 'delBtn', 'delete', '2016-10-25 09:09:15');
INSERT INTO `sys_menu_btn` VALUES (22, 7, '导出', 'exportBtn', 'exportCsv', '2016-10-25 09:09:15');
INSERT INTO `sys_menu_btn` VALUES (25, 6, '删除', 'delBtn', 'delete', '2016-10-25 09:11:01');
INSERT INTO `sys_menu_btn` VALUES (26, 18, '增加', 'addBtn', 'add,addPage', '2017-01-25 18:49:18');
INSERT INTO `sys_menu_btn` VALUES (27, 18, '修改', 'updateBtn', 'update,updatePage', '2017-01-25 18:49:18');
INSERT INTO `sys_menu_btn` VALUES (28, 18, '删除', 'delBtn', 'delete', '2017-01-25 18:49:18');
INSERT INTO `sys_menu_btn` VALUES (29, 18, '导出', 'exportBtn', 'exportCsv', '2017-01-25 18:49:18');
INSERT INTO `sys_menu_btn` VALUES (30, 15, '增加', 'addBtn', 'add,addPage', '2017-01-25 18:49:57');
INSERT INTO `sys_menu_btn` VALUES (31, 15, '修改', 'updateBtn', 'update,updatePage', '2017-01-25 18:49:57');
INSERT INTO `sys_menu_btn` VALUES (32, 15, '删除', 'delBtn', 'delete', '2017-01-25 18:49:57');
INSERT INTO `sys_menu_btn` VALUES (33, 15, '导出', 'exportBtn', 'exportCsv', '2017-01-25 18:49:57');
INSERT INTO `sys_menu_btn` VALUES (44, 19, '增加', 'addBtn', 'add,addPage', '2017-04-04 16:50:07');
INSERT INTO `sys_menu_btn` VALUES (45, 19, '修改', 'updateBtn', 'update,updatePage', '2017-04-04 16:50:07');
INSERT INTO `sys_menu_btn` VALUES (46, 19, '删除', 'delBtn', 'delete', '2017-04-04 16:50:07');
INSERT INTO `sys_menu_btn` VALUES (47, 19, '导出', 'exportBtn', 'exportCsv', '2017-04-04 16:50:07');
INSERT INTO `sys_menu_btn` VALUES (48, 20, '增加', 'addBtn', 'add,addPage', '2017-04-04 16:50:15');
INSERT INTO `sys_menu_btn` VALUES (49, 20, '修改', 'updateBtn', 'update,updatePage', '2017-04-04 16:50:15');
INSERT INTO `sys_menu_btn` VALUES (50, 20, '删除', 'delBtn', 'delete', '2017-04-04 16:50:15');
INSERT INTO `sys_menu_btn` VALUES (51, 20, '导出', 'exportBtn', 'exportCsv', '2017-04-04 16:50:15');
INSERT INTO `sys_menu_btn` VALUES (52, 21, '增加', 'addBtn', 'add,addPage', '2017-04-04 17:19:12');
INSERT INTO `sys_menu_btn` VALUES (53, 21, '修改', 'updateBtn', 'update,updatePage', '2017-04-04 17:19:12');
INSERT INTO `sys_menu_btn` VALUES (54, 21, '删除', 'delBtn', 'delete', '2017-04-04 17:19:12');
INSERT INTO `sys_menu_btn` VALUES (55, 21, '导出', 'exportBtn', 'exportCsv', '2017-04-04 17:19:12');
INSERT INTO `sys_menu_btn` VALUES (57, 23, '增加', 'addBtn', 'add,addPage', '2017-04-17 14:49:12');
INSERT INTO `sys_menu_btn` VALUES (58, 23, '修改', 'updateBtn', 'update,updatePage', '2017-04-17 14:49:12');
INSERT INTO `sys_menu_btn` VALUES (59, 23, '删除', 'delBtn', 'delete', '2017-04-17 14:49:12');
INSERT INTO `sys_menu_btn` VALUES (60, 23, '导出', 'exportBtn', 'exportCsv', '2017-04-17 14:49:12');
INSERT INTO `sys_menu_btn` VALUES (61, 29, '增加', 'addBtn', 'add,addPage', '2017-04-17 22:06:56');
INSERT INTO `sys_menu_btn` VALUES (62, 29, '修改', 'updateBtn', 'update,updatePage', '2017-04-17 22:06:56');
INSERT INTO `sys_menu_btn` VALUES (63, 29, '删除', 'delBtn', 'delete', '2017-04-17 22:06:56');
INSERT INTO `sys_menu_btn` VALUES (64, 29, '导出', 'exportBtn', 'exportCsv', '2017-04-17 22:06:56');
INSERT INTO `sys_menu_btn` VALUES (65, 27, '增加', 'addBtn', 'add,addPage', '2017-04-18 15:09:57');
INSERT INTO `sys_menu_btn` VALUES (66, 27, '修改', 'updateBtn', 'update,updatePage', '2017-04-18 15:09:57');
INSERT INTO `sys_menu_btn` VALUES (67, 27, '删除', 'delBtn', 'delete', '2017-04-18 15:09:57');
INSERT INTO `sys_menu_btn` VALUES (68, 27, '导出', 'exportBtn', 'exportCsv', '2017-04-18 15:09:57');
INSERT INTO `sys_menu_btn` VALUES (69, 28, '增加', 'addBtn', 'add,addPage', '2017-04-18 15:10:03');
INSERT INTO `sys_menu_btn` VALUES (70, 28, '修改', 'updateBtn', 'update,updatePage', '2017-04-18 15:10:03');
INSERT INTO `sys_menu_btn` VALUES (71, 28, '删除', 'delBtn', 'delete', '2017-04-18 15:10:03');
INSERT INTO `sys_menu_btn` VALUES (72, 28, '导出', 'exportBtn', 'exportCsv', '2017-04-18 15:10:03');
INSERT INTO `sys_menu_btn` VALUES (73, 25, '增加', 'addBtn', 'add,addPage', '2017-04-18 15:11:37');
INSERT INTO `sys_menu_btn` VALUES (74, 25, '修改', 'updateBtn', 'update,updatePage', '2017-04-18 15:11:37');
INSERT INTO `sys_menu_btn` VALUES (75, 25, '删除', 'delBtn', 'delete', '2017-04-18 15:11:37');
INSERT INTO `sys_menu_btn` VALUES (76, 25, '导出', 'exportBtn', 'exportCsv', '2017-04-18 15:11:37');
INSERT INTO `sys_menu_btn` VALUES (77, 34, '增加', 'addBtn', 'add,addPage', '2017-04-18 15:11:41');
INSERT INTO `sys_menu_btn` VALUES (78, 34, '修改', 'updateBtn', 'update,updatePage', '2017-04-18 15:11:41');
INSERT INTO `sys_menu_btn` VALUES (79, 34, '删除', 'delBtn', 'delete', '2017-04-18 15:11:41');
INSERT INTO `sys_menu_btn` VALUES (80, 34, '导出', 'exportBtn', 'exportCsv', '2017-04-18 15:11:41');
INSERT INTO `sys_menu_btn` VALUES (81, 18, '授权用户', 'authorizeBtn', 'authorize,authorizePage', '2017-05-01 19:03:15');
INSERT INTO `sys_menu_btn` VALUES (82, 33, '增加', 'addBtn', 'add,addPage', '2017-06-22 15:02:37');
INSERT INTO `sys_menu_btn` VALUES (83, 33, '修改', 'updateBtn', 'update,updatePage', '2017-06-22 15:02:37');
INSERT INTO `sys_menu_btn` VALUES (84, 33, '删除', 'delBtn', 'delete', '2017-06-22 15:02:37');
INSERT INTO `sys_menu_btn` VALUES (85, 33, '导出', 'exportBtn', 'exportCsv', '2017-06-22 15:02:37');

-- ----------------------------
-- Table structure for sys_menu_datarule
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_datarule`;
CREATE TABLE `sys_menu_datarule`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单',
  `field_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段名称',
  `symbol` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '符号',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'sql条件值',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu_datarule
-- ----------------------------
INSERT INTO `sys_menu_datarule` VALUES (3, 12, 'status', '=', '${user.id}', '2016-09-27 00:16:00');
INSERT INTO `sys_menu_datarule` VALUES (4, 7, 'user_name', '!=', 'admin', '2016-10-25 09:10:03');
INSERT INTO `sys_menu_datarule` VALUES (5, 2, 'id', '>', '17', '2016-10-25 09:12:18');

-- ----------------------------
-- Table structure for sys_oplog
-- ----------------------------
DROP TABLE IF EXISTS `sys_oplog`;
CREATE TABLE `sys_oplog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户',
  `op_content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作内容',
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'ip',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4512 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oplog
-- ----------------------------
INSERT INTO `sys_oplog` VALUES (2926, 1, '登陆系统', '116.228.88.66', '2018-05-15 15:08:08');
INSERT INTO `sys_oplog` VALUES (2927, 1, '登陆系统', '116.228.88.66', '2018-05-15 15:11:06');
INSERT INTO `sys_oplog` VALUES (2928, 1, '登陆系统', '116.228.88.66', '2018-05-15 15:17:11');
INSERT INTO `sys_oplog` VALUES (2929, 1, '登陆系统', '111.230.148.108', '2018-05-15 15:21:25');
INSERT INTO `sys_oplog` VALUES (2930, 1, '登陆系统', '111.18.41.254', '2018-05-15 23:11:58');
INSERT INTO `sys_oplog` VALUES (2931, 1, '[在线用户] 修改', '111.18.41.254', '2018-05-15 23:21:17');
INSERT INTO `sys_oplog` VALUES (2932, 1, '[在线用户] 修改', '111.18.41.254', '2018-05-15 23:22:15');
INSERT INTO `sys_oplog` VALUES (2933, 1, '[在线用户] 修改', '111.18.41.254', '2018-05-15 23:22:46');
INSERT INTO `sys_oplog` VALUES (2934, 1, '[在线用户] 修改', '111.18.41.254', '2018-05-15 23:23:16');
INSERT INTO `sys_oplog` VALUES (2935, 1, '[优惠设置] 删除', '111.18.41.254', '2018-05-15 23:25:07');
INSERT INTO `sys_oplog` VALUES (2936, 1, '[优惠设置] 增加', '111.18.41.254', '2018-05-15 23:28:32');
INSERT INTO `sys_oplog` VALUES (2937, 1, '[优惠设置] 修改', '111.18.41.254', '2018-05-15 23:28:58');
INSERT INTO `sys_oplog` VALUES (2938, 1, '[在线用户] 修改', '111.18.41.254', '2018-05-15 23:31:47');
INSERT INTO `sys_oplog` VALUES (2939, 1, '登陆系统', '111.18.41.254', '2018-05-16 09:11:02');
INSERT INTO `sys_oplog` VALUES (2940, 1, '登陆系统', '59.109.217.178', '2018-05-16 11:03:25');
INSERT INTO `sys_oplog` VALUES (2941, 1, '登陆系统', '111.18.41.210', '2018-05-16 14:13:50');
INSERT INTO `sys_oplog` VALUES (2942, 1, '登陆系统', '111.18.41.203', '2018-05-17 17:44:59');
INSERT INTO `sys_oplog` VALUES (2943, 1, '登陆系统', '123.149.208.25', '2018-05-22 16:58:01');
INSERT INTO `sys_oplog` VALUES (2944, 1, '登陆系统', '123.149.213.231', '2018-05-22 18:00:13');
INSERT INTO `sys_oplog` VALUES (2945, 1, '登陆系统', '123.149.213.231', '2018-05-22 18:10:37');
INSERT INTO `sys_oplog` VALUES (2946, 1, '[在线用户] 导出cvs', '123.149.208.25', '2018-05-22 18:15:54');
INSERT INTO `sys_oplog` VALUES (2947, 1, '登陆系统', '116.228.88.66', '2018-05-22 18:27:49');
INSERT INTO `sys_oplog` VALUES (2948, 1, '[设备记录] 增加', '116.228.88.66', '2018-05-22 18:31:59');
INSERT INTO `sys_oplog` VALUES (2949, 1, '设备1:1被删除', '116.228.88.66', '2018-05-22 18:32:03');
INSERT INTO `sys_oplog` VALUES (2950, 1, '[设备记录] 删除', '116.228.88.66', '2018-05-22 18:32:03');
INSERT INTO `sys_oplog` VALUES (2951, 1, '[设备记录] 增加', '123.149.208.25', '2018-05-22 19:08:20');
INSERT INTO `sys_oplog` VALUES (2952, 1, '登陆系统', '123.149.208.25', '2018-05-22 19:11:55');
INSERT INTO `sys_oplog` VALUES (2953, 1, '登陆系统', '123.149.208.25', '2018-05-22 19:14:54');
INSERT INTO `sys_oplog` VALUES (2954, 1, '登陆系统', '123.149.208.25', '2018-05-22 19:18:14');
INSERT INTO `sys_oplog` VALUES (2955, 1, '[设备记录] 修改', '123.149.208.25', '2018-05-22 19:38:51');
INSERT INTO `sys_oplog` VALUES (2956, 1, '登陆系统', '123.149.208.25', '2018-05-22 19:46:38');
INSERT INTO `sys_oplog` VALUES (2957, 1, '登陆系统', '123.149.208.25', '2018-05-23 09:30:46');
INSERT INTO `sys_oplog` VALUES (2958, 1, '设备三兴园1号车棚:3423048203849被删除', '123.149.208.25', '2018-05-23 09:32:19');
INSERT INTO `sys_oplog` VALUES (2959, 1, '[设备记录] 删除', '123.149.208.25', '2018-05-23 09:32:19');
INSERT INTO `sys_oplog` VALUES (2960, 1, '[设备记录] 增加', '123.149.208.25', '2018-05-23 09:36:23');
INSERT INTO `sys_oplog` VALUES (2961, 1, '[设备记录] 修改', '123.149.208.25', '2018-05-23 09:36:54');
INSERT INTO `sys_oplog` VALUES (2962, 1, '[在线用户] 修改', '123.149.208.25', '2018-05-23 09:51:08');
INSERT INTO `sys_oplog` VALUES (2963, 1, '[设备记录] 增加', '123.149.208.25', '2018-05-23 10:41:26');
INSERT INTO `sys_oplog` VALUES (2964, 1, '[设备记录] 修改', '123.149.208.25', '2018-05-23 10:42:02');
INSERT INTO `sys_oplog` VALUES (2965, 1, '[设备记录] 增加', '123.149.208.25', '2018-05-23 10:43:17');
INSERT INTO `sys_oplog` VALUES (2966, 1, '[设备记录] 修改', '123.149.208.25', '2018-05-23 10:43:33');
INSERT INTO `sys_oplog` VALUES (2967, 1, '[设备记录] 增加', '123.149.208.25', '2018-05-23 10:44:38');
INSERT INTO `sys_oplog` VALUES (2968, 1, '[设备记录] 修改', '123.149.208.25', '2018-05-23 10:44:52');
INSERT INTO `sys_oplog` VALUES (2969, 1, '[设备记录] 增加', '123.149.208.25', '2018-05-23 10:45:45');
INSERT INTO `sys_oplog` VALUES (2970, 1, '[设备记录] 修改', '123.149.208.25', '2018-05-23 10:46:05');
INSERT INTO `sys_oplog` VALUES (2971, 1, '[设备记录] 增加', '123.149.208.25', '2018-05-23 10:47:28');
INSERT INTO `sys_oplog` VALUES (2972, 1, '[设备记录] 修改', '123.149.208.25', '2018-05-23 10:47:38');
INSERT INTO `sys_oplog` VALUES (2973, 1, '[设备记录] 增加', '123.149.208.25', '2018-05-23 10:48:51');
INSERT INTO `sys_oplog` VALUES (2974, 1, '[设备记录] 修改', '123.149.208.25', '2018-05-23 10:48:59');
INSERT INTO `sys_oplog` VALUES (2975, 1, '[设备记录] 增加', '123.149.208.25', '2018-05-23 10:50:02');
INSERT INTO `sys_oplog` VALUES (2976, 1, '[设备记录] 修改', '123.149.208.25', '2018-05-23 10:50:12');
INSERT INTO `sys_oplog` VALUES (2977, 1, '[设备记录] 增加', '123.149.208.25', '2018-05-23 10:51:05');
INSERT INTO `sys_oplog` VALUES (2978, 1, '[设备记录] 修改', '123.149.208.25', '2018-05-23 10:51:20');
INSERT INTO `sys_oplog` VALUES (2979, 1, '[设备记录] 增加', '123.149.208.25', '2018-05-23 10:52:38');
INSERT INTO `sys_oplog` VALUES (2980, 1, '[设备记录] 修改', '123.149.208.25', '2018-05-23 10:52:47');
INSERT INTO `sys_oplog` VALUES (2981, 1, '登陆系统', '123.149.213.231', '2018-05-23 14:57:50');
INSERT INTO `sys_oplog` VALUES (2982, 1, '设备290000006:356566077526895被删除', '123.149.213.231', '2018-05-23 14:58:05');
INSERT INTO `sys_oplog` VALUES (2983, 1, '[设备记录] 删除', '123.149.213.231', '2018-05-23 14:58:05');
INSERT INTO `sys_oplog` VALUES (2984, 1, '[设备记录] 增加', '123.149.213.231', '2018-05-23 14:58:44');
INSERT INTO `sys_oplog` VALUES (2985, 1, '[设备记录] 修改', '123.149.213.231', '2018-05-23 14:59:00');
INSERT INTO `sys_oplog` VALUES (2986, 1, '[在线用户] 修改', '123.149.213.231', '2018-05-23 17:03:12');
INSERT INTO `sys_oplog` VALUES (2987, 1, '登陆系统', '123.149.208.25', '2018-05-23 18:46:52');
INSERT INTO `sys_oplog` VALUES (2988, 1, '[菜单管理] 修改', '123.149.213.231', '2018-05-23 19:05:05');
INSERT INTO `sys_oplog` VALUES (2989, 1, '[菜单管理] 修改', '123.149.213.231', '2018-05-23 19:06:08');
INSERT INTO `sys_oplog` VALUES (2990, 1, '[菜单管理] 修改', '123.149.213.231', '2018-05-23 19:08:41');
INSERT INTO `sys_oplog` VALUES (2991, 1, '[菜单管理] 修改', '123.149.213.231', '2018-05-23 19:09:31');
INSERT INTO `sys_oplog` VALUES (2992, 1, '[菜单管理] 修改', '123.149.213.231', '2018-05-23 19:10:09');
INSERT INTO `sys_oplog` VALUES (2993, 1, '登陆系统', '59.109.216.236', '2018-05-24 08:51:00');
INSERT INTO `sys_oplog` VALUES (2994, 1, '登陆系统', '118.187.53.6', '2018-05-24 09:34:38');
INSERT INTO `sys_oplog` VALUES (2995, 1, '登陆系统', '118.187.53.6', '2018-05-24 14:11:09');
INSERT INTO `sys_oplog` VALUES (2996, 1, '[在线用户] 导出cvs', '118.187.53.6', '2018-05-24 14:11:57');
INSERT INTO `sys_oplog` VALUES (2997, 1, '登陆系统', '123.149.213.231', '2018-05-24 15:24:11');
INSERT INTO `sys_oplog` VALUES (2998, 1, '[在线用户] 修改', '123.149.213.231', '2018-05-24 15:24:22');
INSERT INTO `sys_oplog` VALUES (2999, 1, '登陆系统', '123.149.208.25', '2018-05-24 18:00:57');
INSERT INTO `sys_oplog` VALUES (3000, 1, '[在线用户] 修改', '123.149.208.25', '2018-05-24 18:01:07');
INSERT INTO `sys_oplog` VALUES (3001, 1, '登陆系统', '111.18.41.247', '2018-05-24 20:24:16');
INSERT INTO `sys_oplog` VALUES (3002, 1, '[设备记录] 修改', '111.18.41.247', '2018-05-24 20:28:23');
INSERT INTO `sys_oplog` VALUES (3003, 1, '[设备记录] 修改', '111.18.41.247', '2018-05-24 21:42:21');
INSERT INTO `sys_oplog` VALUES (3004, 1, '[设备记录] 修改', '111.18.41.247', '2018-05-24 21:43:34');
INSERT INTO `sys_oplog` VALUES (3005, 1, '[设备记录] 修改', '111.18.41.247', '2018-05-24 21:45:39');
INSERT INTO `sys_oplog` VALUES (3006, 1, '[设备记录] 修改', '111.18.41.247', '2018-05-24 21:46:25');
INSERT INTO `sys_oplog` VALUES (3007, 1, '[设备记录] 修改', '111.18.41.247', '2018-05-24 21:47:29');
INSERT INTO `sys_oplog` VALUES (3008, 1, '[设备记录] 修改', '111.18.41.247', '2018-05-24 21:47:45');
INSERT INTO `sys_oplog` VALUES (3009, 1, '[设备记录] 修改', '111.18.41.247', '2018-05-24 21:48:06');
INSERT INTO `sys_oplog` VALUES (3010, 1, '设备公司测试-有线联网:356566071249973被删除', '111.18.41.247', '2018-05-24 21:49:09');
INSERT INTO `sys_oplog` VALUES (3011, 1, '[设备记录] 删除', '111.18.41.247', '2018-05-24 21:49:09');
INSERT INTO `sys_oplog` VALUES (3012, 1, '登陆系统', '118.187.53.6', '2018-05-25 09:39:20');
INSERT INTO `sys_oplog` VALUES (3013, 1, '登陆系统', '118.187.53.6', '2018-05-25 09:41:06');
INSERT INTO `sys_oplog` VALUES (3014, 1, '登陆系统', '123.149.208.25', '2018-05-25 10:41:10');
INSERT INTO `sys_oplog` VALUES (3015, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 11:19:41');
INSERT INTO `sys_oplog` VALUES (3016, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 11:22:08');
INSERT INTO `sys_oplog` VALUES (3017, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 11:37:24');
INSERT INTO `sys_oplog` VALUES (3018, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 11:37:43');
INSERT INTO `sys_oplog` VALUES (3019, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 11:52:23');
INSERT INTO `sys_oplog` VALUES (3020, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 11:52:42');
INSERT INTO `sys_oplog` VALUES (3021, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 12:08:46');
INSERT INTO `sys_oplog` VALUES (3022, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 12:09:00');
INSERT INTO `sys_oplog` VALUES (3023, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 12:29:45');
INSERT INTO `sys_oplog` VALUES (3024, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 12:29:59');
INSERT INTO `sys_oplog` VALUES (3025, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 12:40:19');
INSERT INTO `sys_oplog` VALUES (3026, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 12:40:31');
INSERT INTO `sys_oplog` VALUES (3027, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 13:46:26');
INSERT INTO `sys_oplog` VALUES (3028, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 13:46:47');
INSERT INTO `sys_oplog` VALUES (3029, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 14:03:32');
INSERT INTO `sys_oplog` VALUES (3030, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 14:03:45');
INSERT INTO `sys_oplog` VALUES (3031, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 14:18:04');
INSERT INTO `sys_oplog` VALUES (3032, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 14:18:17');
INSERT INTO `sys_oplog` VALUES (3033, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 14:28:34');
INSERT INTO `sys_oplog` VALUES (3034, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 14:29:06');
INSERT INTO `sys_oplog` VALUES (3035, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 14:45:35');
INSERT INTO `sys_oplog` VALUES (3036, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 14:46:09');
INSERT INTO `sys_oplog` VALUES (3037, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 14:55:59');
INSERT INTO `sys_oplog` VALUES (3038, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 14:56:12');
INSERT INTO `sys_oplog` VALUES (3039, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 15:36:34');
INSERT INTO `sys_oplog` VALUES (3040, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 15:36:46');
INSERT INTO `sys_oplog` VALUES (3041, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 15:47:15');
INSERT INTO `sys_oplog` VALUES (3042, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 15:47:27');
INSERT INTO `sys_oplog` VALUES (3043, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 15:59:53');
INSERT INTO `sys_oplog` VALUES (3044, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 16:00:05');
INSERT INTO `sys_oplog` VALUES (3045, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 16:09:26');
INSERT INTO `sys_oplog` VALUES (3046, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 16:09:41');
INSERT INTO `sys_oplog` VALUES (3047, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 16:15:13');
INSERT INTO `sys_oplog` VALUES (3048, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 16:15:28');
INSERT INTO `sys_oplog` VALUES (3049, 1, '登陆系统', '118.187.53.6', '2018-05-25 16:16:03');
INSERT INTO `sys_oplog` VALUES (3050, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 16:54:08');
INSERT INTO `sys_oplog` VALUES (3051, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 16:54:22');
INSERT INTO `sys_oplog` VALUES (3052, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 16:59:24');
INSERT INTO `sys_oplog` VALUES (3053, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 16:59:42');
INSERT INTO `sys_oplog` VALUES (3054, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 17:04:45');
INSERT INTO `sys_oplog` VALUES (3055, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 17:04:59');
INSERT INTO `sys_oplog` VALUES (3056, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 17:13:28');
INSERT INTO `sys_oplog` VALUES (3057, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 17:13:47');
INSERT INTO `sys_oplog` VALUES (3058, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 17:23:51');
INSERT INTO `sys_oplog` VALUES (3059, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 17:24:09');
INSERT INTO `sys_oplog` VALUES (3060, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 17:32:34');
INSERT INTO `sys_oplog` VALUES (3061, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 17:32:46');
INSERT INTO `sys_oplog` VALUES (3062, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 17:49:01');
INSERT INTO `sys_oplog` VALUES (3063, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 17:49:15');
INSERT INTO `sys_oplog` VALUES (3064, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 18:04:58');
INSERT INTO `sys_oplog` VALUES (3065, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 18:05:13');
INSERT INTO `sys_oplog` VALUES (3066, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 18:13:53');
INSERT INTO `sys_oplog` VALUES (3067, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 18:14:07');
INSERT INTO `sys_oplog` VALUES (3068, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 18:22:53');
INSERT INTO `sys_oplog` VALUES (3069, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 18:23:04');
INSERT INTO `sys_oplog` VALUES (3070, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 18:36:33');
INSERT INTO `sys_oplog` VALUES (3071, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 18:36:47');
INSERT INTO `sys_oplog` VALUES (3072, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 18:42:54');
INSERT INTO `sys_oplog` VALUES (3073, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 18:43:09');
INSERT INTO `sys_oplog` VALUES (3074, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 18:57:08');
INSERT INTO `sys_oplog` VALUES (3075, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 18:57:22');
INSERT INTO `sys_oplog` VALUES (3076, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 19:11:24');
INSERT INTO `sys_oplog` VALUES (3077, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 19:11:41');
INSERT INTO `sys_oplog` VALUES (3078, 1, '[在线用户] 修改', '123.149.212.41', '2018-05-25 19:17:36');
INSERT INTO `sys_oplog` VALUES (3079, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 19:26:11');
INSERT INTO `sys_oplog` VALUES (3080, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 19:26:28');
INSERT INTO `sys_oplog` VALUES (3081, 1, '[设备记录] 增加', '123.149.212.41', '2018-05-25 19:30:39');
INSERT INTO `sys_oplog` VALUES (3082, 1, '[设备记录] 修改', '123.149.212.41', '2018-05-25 19:30:52');
INSERT INTO `sys_oplog` VALUES (3083, 1, '登陆系统', '123.149.210.236', '2018-05-25 19:43:31');
INSERT INTO `sys_oplog` VALUES (3084, 1, '[设备记录] 增加', '123.149.210.236', '2018-05-25 19:44:16');
INSERT INTO `sys_oplog` VALUES (3085, 1, '[设备记录] 修改', '123.149.210.236', '2018-05-25 19:44:28');
INSERT INTO `sys_oplog` VALUES (3086, 1, '登陆系统', '123.149.212.41', '2018-05-26 10:24:18');
INSERT INTO `sys_oplog` VALUES (3087, 1, '登陆系统', '113.143.141.196', '2018-05-27 11:21:34');
INSERT INTO `sys_oplog` VALUES (3088, 1, '登陆系统', '113.143.141.196', '2018-05-27 15:04:30');
INSERT INTO `sys_oplog` VALUES (3089, 1, '[设备记录] 修改', '113.143.141.196', '2018-05-27 15:08:31');
INSERT INTO `sys_oplog` VALUES (3090, 1, '[设备记录] 修改', '113.143.141.196', '2018-05-27 15:09:29');
INSERT INTO `sys_oplog` VALUES (3091, 1, '[设备记录] 修改', '113.143.141.196', '2018-05-27 15:10:16');
INSERT INTO `sys_oplog` VALUES (3092, 1, '[设备记录] 修改', '113.143.141.196', '2018-05-27 15:10:25');
INSERT INTO `sys_oplog` VALUES (3093, 1, '登陆系统', '115.171.61.188', '2018-05-27 17:48:17');
INSERT INTO `sys_oplog` VALUES (3094, 1, '登陆系统', '113.143.141.196', '2018-05-27 18:30:46');
INSERT INTO `sys_oplog` VALUES (3095, 1, '[优惠设置] 修改', '113.143.141.196', '2018-05-27 19:07:05');
INSERT INTO `sys_oplog` VALUES (3096, 1, '[优惠设置] 修改', '113.143.141.196', '2018-05-27 19:07:16');
INSERT INTO `sys_oplog` VALUES (3097, 1, '[优惠设置] 修改', '113.143.141.196', '2018-05-27 19:07:29');
INSERT INTO `sys_oplog` VALUES (3098, 1, '[优惠设置] 修改', '113.143.141.196', '2018-05-27 19:07:37');
INSERT INTO `sys_oplog` VALUES (3099, 1, '[优惠设置] 修改', '113.143.141.196', '2018-05-27 19:07:45');
INSERT INTO `sys_oplog` VALUES (3100, 1, '[优惠设置] 修改', '113.143.141.196', '2018-05-27 19:07:52');
INSERT INTO `sys_oplog` VALUES (3101, 1, '[优惠设置] 修改', '113.143.141.196', '2018-05-27 19:08:00');
INSERT INTO `sys_oplog` VALUES (3102, 1, '[优惠设置] 修改', '113.143.141.196', '2018-05-27 19:08:09');
INSERT INTO `sys_oplog` VALUES (3103, 1, '[优惠设置] 修改', '113.143.141.196', '2018-05-27 19:08:19');
INSERT INTO `sys_oplog` VALUES (3104, 1, '[优惠设置] 修改', '113.143.141.196', '2018-05-27 19:08:25');
INSERT INTO `sys_oplog` VALUES (3105, 1, '登陆系统', '118.187.53.6', '2018-05-28 10:00:09');
INSERT INTO `sys_oplog` VALUES (3106, 1, '退出系统', '118.187.53.6', '2018-05-28 10:19:30');
INSERT INTO `sys_oplog` VALUES (3107, 1, '登陆系统', '111.18.41.252', '2018-05-28 11:29:39');
INSERT INTO `sys_oplog` VALUES (3108, 1, '登陆系统', '123.149.210.71', '2018-05-28 11:31:38');
INSERT INTO `sys_oplog` VALUES (3109, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 11:46:17');
INSERT INTO `sys_oplog` VALUES (3110, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 11:46:42');
INSERT INTO `sys_oplog` VALUES (3111, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 11:56:47');
INSERT INTO `sys_oplog` VALUES (3112, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 11:57:02');
INSERT INTO `sys_oplog` VALUES (3113, 1, '登陆系统', '111.18.41.252', '2018-05-28 13:40:12');
INSERT INTO `sys_oplog` VALUES (3114, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 13:55:01');
INSERT INTO `sys_oplog` VALUES (3115, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 13:55:22');
INSERT INTO `sys_oplog` VALUES (3116, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 14:16:15');
INSERT INTO `sys_oplog` VALUES (3117, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 14:16:29');
INSERT INTO `sys_oplog` VALUES (3118, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 14:29:56');
INSERT INTO `sys_oplog` VALUES (3119, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 14:30:08');
INSERT INTO `sys_oplog` VALUES (3120, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 14:39:29');
INSERT INTO `sys_oplog` VALUES (3121, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 14:39:45');
INSERT INTO `sys_oplog` VALUES (3122, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 14:48:13');
INSERT INTO `sys_oplog` VALUES (3123, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 14:48:27');
INSERT INTO `sys_oplog` VALUES (3124, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 15:02:33');
INSERT INTO `sys_oplog` VALUES (3125, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 15:02:46');
INSERT INTO `sys_oplog` VALUES (3126, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 15:13:56');
INSERT INTO `sys_oplog` VALUES (3127, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 15:14:27');
INSERT INTO `sys_oplog` VALUES (3128, 1, '登陆系统', '59.109.216.225', '2018-05-28 15:16:21');
INSERT INTO `sys_oplog` VALUES (3129, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 15:51:43');
INSERT INTO `sys_oplog` VALUES (3130, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 15:52:01');
INSERT INTO `sys_oplog` VALUES (3131, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 16:02:15');
INSERT INTO `sys_oplog` VALUES (3132, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 16:02:38');
INSERT INTO `sys_oplog` VALUES (3133, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 16:57:22');
INSERT INTO `sys_oplog` VALUES (3134, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 16:57:34');
INSERT INTO `sys_oplog` VALUES (3135, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 17:04:05');
INSERT INTO `sys_oplog` VALUES (3136, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 17:04:16');
INSERT INTO `sys_oplog` VALUES (3137, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 17:12:29');
INSERT INTO `sys_oplog` VALUES (3138, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 17:12:42');
INSERT INTO `sys_oplog` VALUES (3139, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 17:18:26');
INSERT INTO `sys_oplog` VALUES (3140, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 17:18:38');
INSERT INTO `sys_oplog` VALUES (3141, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 17:24:06');
INSERT INTO `sys_oplog` VALUES (3142, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 17:24:17');
INSERT INTO `sys_oplog` VALUES (3143, 1, '登陆系统', '111.18.41.252', '2018-05-28 17:25:45');
INSERT INTO `sys_oplog` VALUES (3144, 1, '[优惠设置] 修改', '111.18.41.252', '2018-05-28 17:44:38');
INSERT INTO `sys_oplog` VALUES (3145, 1, '[优惠设置] 修改', '111.18.41.252', '2018-05-28 17:45:28');
INSERT INTO `sys_oplog` VALUES (3146, 1, '[优惠设置] 修改', '111.18.41.252', '2018-05-28 17:49:03');
INSERT INTO `sys_oplog` VALUES (3147, 1, '[优惠设置] 修改', '111.18.41.252', '2018-05-28 17:50:25');
INSERT INTO `sys_oplog` VALUES (3148, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 17:55:27');
INSERT INTO `sys_oplog` VALUES (3149, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 17:55:38');
INSERT INTO `sys_oplog` VALUES (3150, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 18:01:32');
INSERT INTO `sys_oplog` VALUES (3151, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 18:01:45');
INSERT INTO `sys_oplog` VALUES (3152, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 18:07:36');
INSERT INTO `sys_oplog` VALUES (3153, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 18:07:50');
INSERT INTO `sys_oplog` VALUES (3154, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 18:13:35');
INSERT INTO `sys_oplog` VALUES (3155, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 18:13:47');
INSERT INTO `sys_oplog` VALUES (3156, 1, '设备290000064:356566077060317被删除', '123.149.208.212', '2018-05-28 18:28:11');
INSERT INTO `sys_oplog` VALUES (3157, 1, '[设备记录] 删除', '123.149.208.212', '2018-05-28 18:28:11');
INSERT INTO `sys_oplog` VALUES (3158, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 18:29:21');
INSERT INTO `sys_oplog` VALUES (3159, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 18:29:36');
INSERT INTO `sys_oplog` VALUES (3160, 1, '[优惠设置] 修改', '111.18.41.252', '2018-05-28 18:45:41');
INSERT INTO `sys_oplog` VALUES (3161, 1, '设备290000064:356566077060622被删除', '123.149.208.212', '2018-05-28 18:50:48');
INSERT INTO `sys_oplog` VALUES (3162, 1, '[设备记录] 删除', '123.149.208.212', '2018-05-28 18:50:48');
INSERT INTO `sys_oplog` VALUES (3163, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 18:54:32');
INSERT INTO `sys_oplog` VALUES (3164, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 18:54:44');
INSERT INTO `sys_oplog` VALUES (3165, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 19:00:56');
INSERT INTO `sys_oplog` VALUES (3166, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 19:01:11');
INSERT INTO `sys_oplog` VALUES (3167, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 19:11:27');
INSERT INTO `sys_oplog` VALUES (3168, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 19:11:44');
INSERT INTO `sys_oplog` VALUES (3169, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 19:17:05');
INSERT INTO `sys_oplog` VALUES (3170, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 19:17:19');
INSERT INTO `sys_oplog` VALUES (3171, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 19:22:24');
INSERT INTO `sys_oplog` VALUES (3172, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 19:22:35');
INSERT INTO `sys_oplog` VALUES (3173, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 19:27:40');
INSERT INTO `sys_oplog` VALUES (3174, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 19:27:52');
INSERT INTO `sys_oplog` VALUES (3175, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 19:33:06');
INSERT INTO `sys_oplog` VALUES (3176, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 19:33:18');
INSERT INTO `sys_oplog` VALUES (3177, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 19:38:41');
INSERT INTO `sys_oplog` VALUES (3178, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 19:38:57');
INSERT INTO `sys_oplog` VALUES (3179, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 19:48:00');
INSERT INTO `sys_oplog` VALUES (3180, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 19:48:12');
INSERT INTO `sys_oplog` VALUES (3181, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 19:58:49');
INSERT INTO `sys_oplog` VALUES (3182, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 19:58:59');
INSERT INTO `sys_oplog` VALUES (3183, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-28 20:07:24');
INSERT INTO `sys_oplog` VALUES (3184, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-28 20:07:34');
INSERT INTO `sys_oplog` VALUES (3185, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 20:15:20');
INSERT INTO `sys_oplog` VALUES (3186, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 20:15:36');
INSERT INTO `sys_oplog` VALUES (3187, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-28 20:23:02');
INSERT INTO `sys_oplog` VALUES (3188, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-28 20:23:12');
INSERT INTO `sys_oplog` VALUES (3189, 1, '登陆系统', '59.109.216.225', '2018-05-29 09:23:46');
INSERT INTO `sys_oplog` VALUES (3190, 1, '登陆系统', '118.187.53.6', '2018-05-29 09:31:27');
INSERT INTO `sys_oplog` VALUES (3191, 1, '登陆系统', '111.18.41.252', '2018-05-29 10:14:19');
INSERT INTO `sys_oplog` VALUES (3192, 1, '登陆系统', '123.149.210.71', '2018-05-29 10:33:12');
INSERT INTO `sys_oplog` VALUES (3193, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 10:45:29');
INSERT INTO `sys_oplog` VALUES (3194, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 10:45:49');
INSERT INTO `sys_oplog` VALUES (3195, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 11:12:16');
INSERT INTO `sys_oplog` VALUES (3196, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 11:12:26');
INSERT INTO `sys_oplog` VALUES (3197, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 11:19:19');
INSERT INTO `sys_oplog` VALUES (3198, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 11:19:37');
INSERT INTO `sys_oplog` VALUES (3199, 1, '登陆系统', '118.187.53.6', '2018-05-29 11:55:07');
INSERT INTO `sys_oplog` VALUES (3200, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-29 12:16:17');
INSERT INTO `sys_oplog` VALUES (3201, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-29 12:16:32');
INSERT INTO `sys_oplog` VALUES (3202, 1, '登陆系统', '59.109.216.225', '2018-05-29 12:43:45');
INSERT INTO `sys_oplog` VALUES (3203, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-29 14:13:37');
INSERT INTO `sys_oplog` VALUES (3204, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-29 14:13:46');
INSERT INTO `sys_oplog` VALUES (3205, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-29 14:25:51');
INSERT INTO `sys_oplog` VALUES (3206, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-29 14:26:02');
INSERT INTO `sys_oplog` VALUES (3207, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 14:34:23');
INSERT INTO `sys_oplog` VALUES (3208, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 14:34:34');
INSERT INTO `sys_oplog` VALUES (3209, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 14:43:04');
INSERT INTO `sys_oplog` VALUES (3210, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 14:43:16');
INSERT INTO `sys_oplog` VALUES (3211, 1, '登陆系统', '118.187.53.6', '2018-05-29 14:44:11');
INSERT INTO `sys_oplog` VALUES (3212, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 14:53:10');
INSERT INTO `sys_oplog` VALUES (3213, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 14:53:22');
INSERT INTO `sys_oplog` VALUES (3214, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 15:03:26');
INSERT INTO `sys_oplog` VALUES (3215, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 15:03:43');
INSERT INTO `sys_oplog` VALUES (3216, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 15:09:35');
INSERT INTO `sys_oplog` VALUES (3217, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 15:09:46');
INSERT INTO `sys_oplog` VALUES (3218, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 15:27:29');
INSERT INTO `sys_oplog` VALUES (3219, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 15:27:42');
INSERT INTO `sys_oplog` VALUES (3220, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 15:45:16');
INSERT INTO `sys_oplog` VALUES (3221, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 15:45:27');
INSERT INTO `sys_oplog` VALUES (3222, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 15:51:19');
INSERT INTO `sys_oplog` VALUES (3223, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 15:51:30');
INSERT INTO `sys_oplog` VALUES (3224, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-29 16:02:17');
INSERT INTO `sys_oplog` VALUES (3225, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 16:18:09');
INSERT INTO `sys_oplog` VALUES (3226, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 16:18:20');
INSERT INTO `sys_oplog` VALUES (3227, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 16:19:00');
INSERT INTO `sys_oplog` VALUES (3228, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 16:28:34');
INSERT INTO `sys_oplog` VALUES (3229, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 16:28:45');
INSERT INTO `sys_oplog` VALUES (3230, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-29 16:37:00');
INSERT INTO `sys_oplog` VALUES (3231, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-29 16:37:13');
INSERT INTO `sys_oplog` VALUES (3232, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 16:46:06');
INSERT INTO `sys_oplog` VALUES (3233, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 16:46:17');
INSERT INTO `sys_oplog` VALUES (3234, 1, '登陆系统', '111.18.41.252', '2018-05-29 16:48:41');
INSERT INTO `sys_oplog` VALUES (3235, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 16:57:12');
INSERT INTO `sys_oplog` VALUES (3236, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 16:57:33');
INSERT INTO `sys_oplog` VALUES (3237, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 17:10:07');
INSERT INTO `sys_oplog` VALUES (3238, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 17:10:18');
INSERT INTO `sys_oplog` VALUES (3239, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 17:19:03');
INSERT INTO `sys_oplog` VALUES (3240, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 17:19:14');
INSERT INTO `sys_oplog` VALUES (3241, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 17:32:57');
INSERT INTO `sys_oplog` VALUES (3242, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 17:33:11');
INSERT INTO `sys_oplog` VALUES (3243, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-29 17:50:19');
INSERT INTO `sys_oplog` VALUES (3244, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-29 17:50:30');
INSERT INTO `sys_oplog` VALUES (3245, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 18:16:48');
INSERT INTO `sys_oplog` VALUES (3246, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 18:17:02');
INSERT INTO `sys_oplog` VALUES (3247, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 18:26:41');
INSERT INTO `sys_oplog` VALUES (3248, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 18:26:52');
INSERT INTO `sys_oplog` VALUES (3249, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-29 18:40:24');
INSERT INTO `sys_oplog` VALUES (3250, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-29 18:40:35');
INSERT INTO `sys_oplog` VALUES (3251, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 19:06:52');
INSERT INTO `sys_oplog` VALUES (3252, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 19:07:05');
INSERT INTO `sys_oplog` VALUES (3253, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-29 19:15:32');
INSERT INTO `sys_oplog` VALUES (3254, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-29 19:15:51');
INSERT INTO `sys_oplog` VALUES (3255, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 19:23:31');
INSERT INTO `sys_oplog` VALUES (3256, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 19:23:46');
INSERT INTO `sys_oplog` VALUES (3257, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-29 19:31:08');
INSERT INTO `sys_oplog` VALUES (3258, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-29 19:31:19');
INSERT INTO `sys_oplog` VALUES (3259, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 19:43:30');
INSERT INTO `sys_oplog` VALUES (3260, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 19:43:43');
INSERT INTO `sys_oplog` VALUES (3261, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 19:48:18');
INSERT INTO `sys_oplog` VALUES (3262, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 19:48:31');
INSERT INTO `sys_oplog` VALUES (3263, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-29 19:50:52');
INSERT INTO `sys_oplog` VALUES (3264, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-29 19:51:03');
INSERT INTO `sys_oplog` VALUES (3265, 1, '登陆系统', '111.18.41.252', '2018-05-29 21:28:04');
INSERT INTO `sys_oplog` VALUES (3266, 1, '登陆系统', '118.187.53.6', '2018-05-30 09:19:57');
INSERT INTO `sys_oplog` VALUES (3267, 1, '登陆系统', '118.187.53.6', '2018-05-30 09:27:18');
INSERT INTO `sys_oplog` VALUES (3268, 1, '[消费记录] 导出cvs', '118.187.53.6', '2018-05-30 10:04:34');
INSERT INTO `sys_oplog` VALUES (3269, 1, '登陆系统', '117.36.17.168', '2018-05-30 10:04:50');
INSERT INTO `sys_oplog` VALUES (3270, 1, '[消费记录] 导出cvs', '118.187.53.6', '2018-05-30 10:59:08');
INSERT INTO `sys_oplog` VALUES (3271, 1, '登陆系统', '118.187.53.6', '2018-05-30 11:37:26');
INSERT INTO `sys_oplog` VALUES (3272, 1, '登陆系统', '123.149.208.212', '2018-05-30 12:49:09');
INSERT INTO `sys_oplog` VALUES (3273, 1, '[优惠设置] 修改', '123.149.208.212', '2018-05-30 12:50:59');
INSERT INTO `sys_oplog` VALUES (3274, 1, '[优惠设置] 修改', '123.149.208.212', '2018-05-30 12:51:48');
INSERT INTO `sys_oplog` VALUES (3275, 1, '[优惠设置] 修改', '123.149.208.212', '2018-05-30 12:52:46');
INSERT INTO `sys_oplog` VALUES (3276, 1, '登陆系统', '1.81.93.190', '2018-05-30 13:35:38');
INSERT INTO `sys_oplog` VALUES (3277, 1, '[优惠设置] 修改', '1.81.93.190', '2018-05-30 13:37:48');
INSERT INTO `sys_oplog` VALUES (3278, 1, '[优惠设置] 修改', '1.81.93.190', '2018-05-30 13:38:32');
INSERT INTO `sys_oplog` VALUES (3279, 1, '[优惠设置] 修改', '1.81.93.190', '2018-05-30 13:38:47');
INSERT INTO `sys_oplog` VALUES (3280, 1, '[优惠设置] 修改', '1.81.93.190', '2018-05-30 13:39:34');
INSERT INTO `sys_oplog` VALUES (3281, 1, '[优惠设置] 修改', '1.81.93.190', '2018-05-30 13:40:20');
INSERT INTO `sys_oplog` VALUES (3282, 1, '[优惠设置] 修改', '1.81.93.190', '2018-05-30 13:41:58');
INSERT INTO `sys_oplog` VALUES (3283, 1, '[优惠设置] 修改', '1.81.93.190', '2018-05-30 13:42:21');
INSERT INTO `sys_oplog` VALUES (3284, 1, '[优惠设置] 修改', '1.81.93.190', '2018-05-30 13:42:35');
INSERT INTO `sys_oplog` VALUES (3285, 1, '[优惠设置] 修改', '1.81.93.190', '2018-05-30 13:43:02');
INSERT INTO `sys_oplog` VALUES (3286, 1, '[优惠设置] 修改', '1.81.93.190', '2018-05-30 13:43:15');
INSERT INTO `sys_oplog` VALUES (3287, 1, '[设备记录] 修改', '1.81.93.190', '2018-05-30 13:57:12');
INSERT INTO `sys_oplog` VALUES (3288, 1, '登陆系统', '118.187.53.6', '2018-05-30 15:32:12');
INSERT INTO `sys_oplog` VALUES (3289, 1, '[优惠设置] 修改', '36.46.10.191', '2018-05-30 15:38:40');
INSERT INTO `sys_oplog` VALUES (3290, 1, '登陆系统', '118.187.53.6', '2018-05-30 15:38:43');
INSERT INTO `sys_oplog` VALUES (3291, 1, '[优惠设置] 修改', '36.46.10.191', '2018-05-30 15:44:52');
INSERT INTO `sys_oplog` VALUES (3292, 1, '[优惠设置] 修改', '36.46.10.191', '2018-05-30 15:48:43');
INSERT INTO `sys_oplog` VALUES (3293, 1, '登陆系统', '117.33.61.4', '2018-05-30 18:55:20');
INSERT INTO `sys_oplog` VALUES (3294, 1, '[在线用户] 修改', '117.33.61.4', '2018-05-30 19:13:43');
INSERT INTO `sys_oplog` VALUES (3295, 1, '[在线用户] 修改', '117.33.61.4', '2018-05-30 19:14:34');
INSERT INTO `sys_oplog` VALUES (3296, 1, '[在线用户] 修改', '111.18.41.254', '2018-05-30 19:58:12');
INSERT INTO `sys_oplog` VALUES (3297, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 21:52:03');
INSERT INTO `sys_oplog` VALUES (3298, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 21:53:17');
INSERT INTO `sys_oplog` VALUES (3299, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 21:54:08');
INSERT INTO `sys_oplog` VALUES (3300, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 21:54:31');
INSERT INTO `sys_oplog` VALUES (3301, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 21:55:20');
INSERT INTO `sys_oplog` VALUES (3302, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 21:57:32');
INSERT INTO `sys_oplog` VALUES (3303, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 21:58:28');
INSERT INTO `sys_oplog` VALUES (3304, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 21:59:48');
INSERT INTO `sys_oplog` VALUES (3305, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 22:00:39');
INSERT INTO `sys_oplog` VALUES (3306, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 22:01:19');
INSERT INTO `sys_oplog` VALUES (3307, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 22:01:55');
INSERT INTO `sys_oplog` VALUES (3308, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 22:03:22');
INSERT INTO `sys_oplog` VALUES (3309, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 22:03:41');
INSERT INTO `sys_oplog` VALUES (3310, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 22:03:52');
INSERT INTO `sys_oplog` VALUES (3311, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 22:04:15');
INSERT INTO `sys_oplog` VALUES (3312, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 22:04:40');
INSERT INTO `sys_oplog` VALUES (3313, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 22:04:50');
INSERT INTO `sys_oplog` VALUES (3314, 1, '[设备记录] 修改', '111.18.41.254', '2018-05-30 22:04:55');
INSERT INTO `sys_oplog` VALUES (3315, 1, '[在线用户] 修改', '111.18.41.254', '2018-05-30 22:09:28');
INSERT INTO `sys_oplog` VALUES (3316, 1, '登陆系统', '111.18.41.254', '2018-05-30 22:17:49');
INSERT INTO `sys_oplog` VALUES (3317, 1, '登陆系统', '111.18.41.245', '2018-05-31 09:28:26');
INSERT INTO `sys_oplog` VALUES (3318, 1, '登陆系统', '118.187.53.6', '2018-05-31 09:57:34');
INSERT INTO `sys_oplog` VALUES (3319, 1, '[功率日志] 导出cvs', '111.18.41.245', '2018-05-31 10:01:25');
INSERT INTO `sys_oplog` VALUES (3320, 1, '登陆系统', '123.149.208.212', '2018-05-31 10:17:00');
INSERT INTO `sys_oplog` VALUES (3321, 1, '[设备记录] 增加', '123.149.208.212', '2018-05-31 10:26:06');
INSERT INTO `sys_oplog` VALUES (3322, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-31 10:27:44');
INSERT INTO `sys_oplog` VALUES (3323, 1, '[设备记录] 修改', '123.149.208.212', '2018-05-31 10:28:17');
INSERT INTO `sys_oplog` VALUES (3324, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-31 10:31:22');
INSERT INTO `sys_oplog` VALUES (3325, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-31 10:37:10');
INSERT INTO `sys_oplog` VALUES (3326, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-31 10:37:50');
INSERT INTO `sys_oplog` VALUES (3327, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-31 10:40:19');
INSERT INTO `sys_oplog` VALUES (3328, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-31 10:40:45');
INSERT INTO `sys_oplog` VALUES (3329, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-31 10:43:18');
INSERT INTO `sys_oplog` VALUES (3330, 1, '[设备记录] 修改', '123.149.210.71', '2018-05-31 10:43:40');
INSERT INTO `sys_oplog` VALUES (3331, 1, '[设备记录] 增加', '123.149.210.71', '2018-05-31 10:46:15');
INSERT INTO `sys_oplog` VALUES (3332, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 10:46:54');
INSERT INTO `sys_oplog` VALUES (3333, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 10:48:49');
INSERT INTO `sys_oplog` VALUES (3334, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 10:51:13');
INSERT INTO `sys_oplog` VALUES (3335, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 10:51:39');
INSERT INTO `sys_oplog` VALUES (3336, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 10:54:37');
INSERT INTO `sys_oplog` VALUES (3337, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 10:55:04');
INSERT INTO `sys_oplog` VALUES (3338, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 10:57:09');
INSERT INTO `sys_oplog` VALUES (3339, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 10:57:43');
INSERT INTO `sys_oplog` VALUES (3340, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 10:59:35');
INSERT INTO `sys_oplog` VALUES (3341, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 11:00:01');
INSERT INTO `sys_oplog` VALUES (3342, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 11:03:00');
INSERT INTO `sys_oplog` VALUES (3343, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 11:03:28');
INSERT INTO `sys_oplog` VALUES (3344, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 11:07:44');
INSERT INTO `sys_oplog` VALUES (3345, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 11:08:07');
INSERT INTO `sys_oplog` VALUES (3346, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 11:10:09');
INSERT INTO `sys_oplog` VALUES (3347, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 11:10:36');
INSERT INTO `sys_oplog` VALUES (3348, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 11:23:29');
INSERT INTO `sys_oplog` VALUES (3349, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 11:23:52');
INSERT INTO `sys_oplog` VALUES (3350, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 11:27:02');
INSERT INTO `sys_oplog` VALUES (3351, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 11:27:22');
INSERT INTO `sys_oplog` VALUES (3352, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 11:29:06');
INSERT INTO `sys_oplog` VALUES (3353, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 11:29:25');
INSERT INTO `sys_oplog` VALUES (3354, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 11:31:11');
INSERT INTO `sys_oplog` VALUES (3355, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 11:31:33');
INSERT INTO `sys_oplog` VALUES (3356, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 11:33:55');
INSERT INTO `sys_oplog` VALUES (3357, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 11:34:17');
INSERT INTO `sys_oplog` VALUES (3358, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 11:36:40');
INSERT INTO `sys_oplog` VALUES (3359, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 11:37:02');
INSERT INTO `sys_oplog` VALUES (3360, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 11:40:36');
INSERT INTO `sys_oplog` VALUES (3361, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 11:40:57');
INSERT INTO `sys_oplog` VALUES (3362, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 11:45:13');
INSERT INTO `sys_oplog` VALUES (3363, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 11:45:44');
INSERT INTO `sys_oplog` VALUES (3364, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 11:47:15');
INSERT INTO `sys_oplog` VALUES (3365, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 11:47:36');
INSERT INTO `sys_oplog` VALUES (3366, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 11:49:43');
INSERT INTO `sys_oplog` VALUES (3367, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 11:50:01');
INSERT INTO `sys_oplog` VALUES (3368, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 11:51:40');
INSERT INTO `sys_oplog` VALUES (3369, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 11:51:58');
INSERT INTO `sys_oplog` VALUES (3370, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 11:53:30');
INSERT INTO `sys_oplog` VALUES (3371, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 11:54:00');
INSERT INTO `sys_oplog` VALUES (3372, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 11:55:27');
INSERT INTO `sys_oplog` VALUES (3373, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 11:55:45');
INSERT INTO `sys_oplog` VALUES (3374, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 11:59:02');
INSERT INTO `sys_oplog` VALUES (3375, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 11:59:24');
INSERT INTO `sys_oplog` VALUES (3376, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 12:01:48');
INSERT INTO `sys_oplog` VALUES (3377, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 12:02:09');
INSERT INTO `sys_oplog` VALUES (3378, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 12:04:51');
INSERT INTO `sys_oplog` VALUES (3379, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 12:05:06');
INSERT INTO `sys_oplog` VALUES (3380, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 12:07:22');
INSERT INTO `sys_oplog` VALUES (3381, 1, '登陆系统', '111.18.41.245', '2018-05-31 12:07:23');
INSERT INTO `sys_oplog` VALUES (3382, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 12:07:42');
INSERT INTO `sys_oplog` VALUES (3383, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 12:09:42');
INSERT INTO `sys_oplog` VALUES (3384, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 12:10:00');
INSERT INTO `sys_oplog` VALUES (3385, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 12:11:55');
INSERT INTO `sys_oplog` VALUES (3386, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 12:12:13');
INSERT INTO `sys_oplog` VALUES (3387, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 12:16:04');
INSERT INTO `sys_oplog` VALUES (3388, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 12:16:25');
INSERT INTO `sys_oplog` VALUES (3389, 1, '[设备记录] 增加', '123.149.212.54', '2018-05-31 12:22:21');
INSERT INTO `sys_oplog` VALUES (3390, 1, '[设备记录] 修改', '123.149.212.54', '2018-05-31 12:22:38');
INSERT INTO `sys_oplog` VALUES (3391, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:19:01');
INSERT INTO `sys_oplog` VALUES (3392, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:19:26');
INSERT INTO `sys_oplog` VALUES (3393, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:21:07');
INSERT INTO `sys_oplog` VALUES (3394, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:21:49');
INSERT INTO `sys_oplog` VALUES (3395, 1, '登陆系统', '111.18.41.245', '2018-05-31 13:22:13');
INSERT INTO `sys_oplog` VALUES (3396, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:23:45');
INSERT INTO `sys_oplog` VALUES (3397, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:24:05');
INSERT INTO `sys_oplog` VALUES (3398, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:26:39');
INSERT INTO `sys_oplog` VALUES (3399, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:26:57');
INSERT INTO `sys_oplog` VALUES (3400, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:28:56');
INSERT INTO `sys_oplog` VALUES (3401, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:29:17');
INSERT INTO `sys_oplog` VALUES (3402, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:31:06');
INSERT INTO `sys_oplog` VALUES (3403, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:31:26');
INSERT INTO `sys_oplog` VALUES (3404, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:33:29');
INSERT INTO `sys_oplog` VALUES (3405, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:33:51');
INSERT INTO `sys_oplog` VALUES (3406, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:35:48');
INSERT INTO `sys_oplog` VALUES (3407, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:36:07');
INSERT INTO `sys_oplog` VALUES (3408, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:39:45');
INSERT INTO `sys_oplog` VALUES (3409, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:41:00');
INSERT INTO `sys_oplog` VALUES (3410, 1, '设备290000145:356566077036820被删除', '123.149.212.174', '2018-05-31 13:43:22');
INSERT INTO `sys_oplog` VALUES (3411, 1, '[设备记录] 删除', '123.149.212.174', '2018-05-31 13:43:22');
INSERT INTO `sys_oplog` VALUES (3412, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:44:17');
INSERT INTO `sys_oplog` VALUES (3413, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:44:34');
INSERT INTO `sys_oplog` VALUES (3414, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:46:47');
INSERT INTO `sys_oplog` VALUES (3415, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:47:29');
INSERT INTO `sys_oplog` VALUES (3416, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:50:00');
INSERT INTO `sys_oplog` VALUES (3417, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:50:27');
INSERT INTO `sys_oplog` VALUES (3418, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:52:30');
INSERT INTO `sys_oplog` VALUES (3419, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:52:50');
INSERT INTO `sys_oplog` VALUES (3420, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:54:39');
INSERT INTO `sys_oplog` VALUES (3421, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:54:59');
INSERT INTO `sys_oplog` VALUES (3422, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:56:38');
INSERT INTO `sys_oplog` VALUES (3423, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:56:57');
INSERT INTO `sys_oplog` VALUES (3424, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 13:59:05');
INSERT INTO `sys_oplog` VALUES (3425, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 13:59:22');
INSERT INTO `sys_oplog` VALUES (3426, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:01:07');
INSERT INTO `sys_oplog` VALUES (3427, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:01:32');
INSERT INTO `sys_oplog` VALUES (3428, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:03:34');
INSERT INTO `sys_oplog` VALUES (3429, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:03:59');
INSERT INTO `sys_oplog` VALUES (3430, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:05:53');
INSERT INTO `sys_oplog` VALUES (3431, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:06:17');
INSERT INTO `sys_oplog` VALUES (3432, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:07:57');
INSERT INTO `sys_oplog` VALUES (3433, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:08:32');
INSERT INTO `sys_oplog` VALUES (3434, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:10:16');
INSERT INTO `sys_oplog` VALUES (3435, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:10:36');
INSERT INTO `sys_oplog` VALUES (3436, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:12:05');
INSERT INTO `sys_oplog` VALUES (3437, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:12:22');
INSERT INTO `sys_oplog` VALUES (3438, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:13:51');
INSERT INTO `sys_oplog` VALUES (3439, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:14:11');
INSERT INTO `sys_oplog` VALUES (3440, 1, '登陆系统', '118.187.53.6', '2018-05-31 14:15:17');
INSERT INTO `sys_oplog` VALUES (3441, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:16:02');
INSERT INTO `sys_oplog` VALUES (3442, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:16:23');
INSERT INTO `sys_oplog` VALUES (3443, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:18:07');
INSERT INTO `sys_oplog` VALUES (3444, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:18:23');
INSERT INTO `sys_oplog` VALUES (3445, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:19:51');
INSERT INTO `sys_oplog` VALUES (3446, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:20:08');
INSERT INTO `sys_oplog` VALUES (3447, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:21:37');
INSERT INTO `sys_oplog` VALUES (3448, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:21:57');
INSERT INTO `sys_oplog` VALUES (3449, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 14:23:44');
INSERT INTO `sys_oplog` VALUES (3450, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 14:24:01');
INSERT INTO `sys_oplog` VALUES (3451, 1, '登陆系统', '118.187.53.6', '2018-05-31 15:54:11');
INSERT INTO `sys_oplog` VALUES (3452, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 15:57:19');
INSERT INTO `sys_oplog` VALUES (3453, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 15:57:37');
INSERT INTO `sys_oplog` VALUES (3454, 1, '登陆系统', '123.149.212.174', '2018-05-31 16:01:24');
INSERT INTO `sys_oplog` VALUES (3455, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:02:29');
INSERT INTO `sys_oplog` VALUES (3456, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:02:45');
INSERT INTO `sys_oplog` VALUES (3457, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:04:10');
INSERT INTO `sys_oplog` VALUES (3458, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:04:27');
INSERT INTO `sys_oplog` VALUES (3459, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:05:59');
INSERT INTO `sys_oplog` VALUES (3460, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:06:21');
INSERT INTO `sys_oplog` VALUES (3461, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:07:52');
INSERT INTO `sys_oplog` VALUES (3462, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:08:11');
INSERT INTO `sys_oplog` VALUES (3463, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:09:36');
INSERT INTO `sys_oplog` VALUES (3464, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:09:57');
INSERT INTO `sys_oplog` VALUES (3465, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:11:42');
INSERT INTO `sys_oplog` VALUES (3466, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:11:59');
INSERT INTO `sys_oplog` VALUES (3467, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:13:27');
INSERT INTO `sys_oplog` VALUES (3468, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:13:44');
INSERT INTO `sys_oplog` VALUES (3469, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:15:04');
INSERT INTO `sys_oplog` VALUES (3470, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:15:24');
INSERT INTO `sys_oplog` VALUES (3471, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:17:08');
INSERT INTO `sys_oplog` VALUES (3472, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:17:26');
INSERT INTO `sys_oplog` VALUES (3473, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:19:08');
INSERT INTO `sys_oplog` VALUES (3474, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:19:26');
INSERT INTO `sys_oplog` VALUES (3475, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:21:01');
INSERT INTO `sys_oplog` VALUES (3476, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:21:22');
INSERT INTO `sys_oplog` VALUES (3477, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:23:04');
INSERT INTO `sys_oplog` VALUES (3478, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:23:23');
INSERT INTO `sys_oplog` VALUES (3479, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:24:40');
INSERT INTO `sys_oplog` VALUES (3480, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:24:59');
INSERT INTO `sys_oplog` VALUES (3481, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:26:31');
INSERT INTO `sys_oplog` VALUES (3482, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:27:06');
INSERT INTO `sys_oplog` VALUES (3483, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:28:58');
INSERT INTO `sys_oplog` VALUES (3484, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:29:18');
INSERT INTO `sys_oplog` VALUES (3485, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:31:18');
INSERT INTO `sys_oplog` VALUES (3486, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:31:34');
INSERT INTO `sys_oplog` VALUES (3487, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:33:29');
INSERT INTO `sys_oplog` VALUES (3488, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:33:48');
INSERT INTO `sys_oplog` VALUES (3489, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:35:32');
INSERT INTO `sys_oplog` VALUES (3490, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:35:49');
INSERT INTO `sys_oplog` VALUES (3491, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:37:16');
INSERT INTO `sys_oplog` VALUES (3492, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:37:34');
INSERT INTO `sys_oplog` VALUES (3493, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:39:51');
INSERT INTO `sys_oplog` VALUES (3494, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:40:08');
INSERT INTO `sys_oplog` VALUES (3495, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:41:57');
INSERT INTO `sys_oplog` VALUES (3496, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:42:18');
INSERT INTO `sys_oplog` VALUES (3497, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:43:47');
INSERT INTO `sys_oplog` VALUES (3498, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:44:05');
INSERT INTO `sys_oplog` VALUES (3499, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:45:39');
INSERT INTO `sys_oplog` VALUES (3500, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:45:56');
INSERT INTO `sys_oplog` VALUES (3501, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:47:22');
INSERT INTO `sys_oplog` VALUES (3502, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:47:37');
INSERT INTO `sys_oplog` VALUES (3503, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:49:07');
INSERT INTO `sys_oplog` VALUES (3504, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:49:23');
INSERT INTO `sys_oplog` VALUES (3505, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:51:14');
INSERT INTO `sys_oplog` VALUES (3506, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:51:29');
INSERT INTO `sys_oplog` VALUES (3507, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:53:08');
INSERT INTO `sys_oplog` VALUES (3508, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:53:33');
INSERT INTO `sys_oplog` VALUES (3509, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:54:59');
INSERT INTO `sys_oplog` VALUES (3510, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:55:14');
INSERT INTO `sys_oplog` VALUES (3511, 1, '[设备记录] 增加', '123.149.212.174', '2018-05-31 16:56:59');
INSERT INTO `sys_oplog` VALUES (3512, 1, '[设备记录] 修改', '123.149.212.174', '2018-05-31 16:57:16');
INSERT INTO `sys_oplog` VALUES (3513, 1, '登陆系统', '111.18.41.245', '2018-05-31 17:10:06');
INSERT INTO `sys_oplog` VALUES (3514, 1, '退出系统', '111.18.41.245', '2018-05-31 17:17:50');
INSERT INTO `sys_oplog` VALUES (3515, 1, '登陆系统', '111.18.41.245', '2018-05-31 17:18:04');
INSERT INTO `sys_oplog` VALUES (3516, 1, '登陆系统', '111.18.41.245', '2018-05-31 17:37:15');
INSERT INTO `sys_oplog` VALUES (3517, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 17:42:52');
INSERT INTO `sys_oplog` VALUES (3518, 1, '退出系统', '111.18.41.245', '2018-05-31 17:43:00');
INSERT INTO `sys_oplog` VALUES (3519, 1, '登陆系统', '111.18.41.245', '2018-05-31 17:43:19');
INSERT INTO `sys_oplog` VALUES (3520, 1, '[在线用户] 修改', '111.18.41.245', '2018-05-31 17:45:07');
INSERT INTO `sys_oplog` VALUES (3521, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 19:12:49');
INSERT INTO `sys_oplog` VALUES (3522, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 19:13:26');
INSERT INTO `sys_oplog` VALUES (3523, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 19:13:42');
INSERT INTO `sys_oplog` VALUES (3524, 1, '[在线用户] 修改', '111.18.41.245', '2018-05-31 19:48:28');
INSERT INTO `sys_oplog` VALUES (3525, 1, '[在线用户] 修改', '111.18.41.245', '2018-05-31 19:55:31');
INSERT INTO `sys_oplog` VALUES (3526, 1, '登陆系统', '123.149.212.54', '2018-05-31 20:02:29');
INSERT INTO `sys_oplog` VALUES (3527, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 20:29:54');
INSERT INTO `sys_oplog` VALUES (3528, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 20:32:56');
INSERT INTO `sys_oplog` VALUES (3529, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 20:51:19');
INSERT INTO `sys_oplog` VALUES (3530, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 20:51:36');
INSERT INTO `sys_oplog` VALUES (3531, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 20:51:45');
INSERT INTO `sys_oplog` VALUES (3532, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 20:53:15');
INSERT INTO `sys_oplog` VALUES (3533, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 20:54:41');
INSERT INTO `sys_oplog` VALUES (3534, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 20:55:31');
INSERT INTO `sys_oplog` VALUES (3535, 1, '登陆系统', '115.171.61.188', '2018-05-31 22:00:21');
INSERT INTO `sys_oplog` VALUES (3536, 1, '[在线用户] 修改', '111.18.41.245', '2018-05-31 23:08:57');
INSERT INTO `sys_oplog` VALUES (3537, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 23:11:47');
INSERT INTO `sys_oplog` VALUES (3538, 1, '[在线用户] 修改', '111.18.41.245', '2018-05-31 23:13:24');
INSERT INTO `sys_oplog` VALUES (3539, 1, '[在线用户] 修改', '111.18.41.245', '2018-05-31 23:14:24');
INSERT INTO `sys_oplog` VALUES (3540, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 23:15:23');
INSERT INTO `sys_oplog` VALUES (3541, 1, '[在线用户] 修改', '111.18.41.245', '2018-05-31 23:15:36');
INSERT INTO `sys_oplog` VALUES (3542, 1, '登陆系统', '111.18.41.245', '2018-05-31 23:16:37');
INSERT INTO `sys_oplog` VALUES (3543, 1, '[在线用户] 修改', '111.18.41.245', '2018-05-31 23:16:47');
INSERT INTO `sys_oplog` VALUES (3544, 1, '登陆系统', '111.18.41.245', '2018-05-31 23:17:29');
INSERT INTO `sys_oplog` VALUES (3545, 1, '[在线用户] 修改', '111.18.41.245', '2018-05-31 23:17:47');
INSERT INTO `sys_oplog` VALUES (3546, 1, '登陆系统', '111.18.41.245', '2018-05-31 23:19:11');
INSERT INTO `sys_oplog` VALUES (3547, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 23:41:28');
INSERT INTO `sys_oplog` VALUES (3548, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 23:41:58');
INSERT INTO `sys_oplog` VALUES (3549, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 23:43:58');
INSERT INTO `sys_oplog` VALUES (3550, 1, '[设备记录] 修改', '111.18.41.245', '2018-05-31 23:45:02');
INSERT INTO `sys_oplog` VALUES (3551, 1, '[设备记录] 修改', '111.18.41.245', '2018-06-01 00:17:04');
INSERT INTO `sys_oplog` VALUES (3552, 1, '[设备记录] 修改', '111.18.41.245', '2018-06-01 00:17:29');
INSERT INTO `sys_oplog` VALUES (3553, 1, '[设备记录] 修改', '111.18.41.245', '2018-06-01 00:25:18');
INSERT INTO `sys_oplog` VALUES (3554, 1, '[设备记录] 修改', '111.18.41.245', '2018-06-01 01:16:33');
INSERT INTO `sys_oplog` VALUES (3555, 1, '[设备记录] 修改', '111.18.41.245', '2018-06-01 01:17:45');
INSERT INTO `sys_oplog` VALUES (3556, 1, '[设备记录] 修改', '111.18.41.245', '2018-06-01 01:20:54');
INSERT INTO `sys_oplog` VALUES (3557, 1, '[在线用户] 修改', '111.18.41.245', '2018-06-01 01:21:46');
INSERT INTO `sys_oplog` VALUES (3558, 1, '[在线用户] 修改', '111.18.41.245', '2018-06-01 01:42:27');
INSERT INTO `sys_oplog` VALUES (3559, 1, '登陆系统', '111.18.41.237', '2018-06-01 08:34:28');
INSERT INTO `sys_oplog` VALUES (3560, 1, '[优惠设置] 修改', '111.18.41.237', '2018-06-01 08:35:15');
INSERT INTO `sys_oplog` VALUES (3561, 1, '[优惠设置] 修改', '111.18.41.237', '2018-06-01 08:35:32');
INSERT INTO `sys_oplog` VALUES (3562, 1, '[优惠设置] 修改', '111.18.41.237', '2018-06-01 08:36:10');
INSERT INTO `sys_oplog` VALUES (3563, 1, '[优惠设置] 修改', '111.18.41.237', '2018-06-01 08:36:24');
INSERT INTO `sys_oplog` VALUES (3564, 1, '[优惠设置] 修改', '111.18.41.237', '2018-06-01 08:37:05');
INSERT INTO `sys_oplog` VALUES (3565, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 08:39:35');
INSERT INTO `sys_oplog` VALUES (3566, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 08:40:16');
INSERT INTO `sys_oplog` VALUES (3567, 1, '[在线用户] 修改', '111.18.41.237', '2018-06-01 08:46:58');
INSERT INTO `sys_oplog` VALUES (3568, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 08:58:59');
INSERT INTO `sys_oplog` VALUES (3569, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 08:59:19');
INSERT INTO `sys_oplog` VALUES (3570, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 08:59:35');
INSERT INTO `sys_oplog` VALUES (3571, 1, '登陆系统', '118.187.53.6', '2018-06-01 09:28:25');
INSERT INTO `sys_oplog` VALUES (3572, 1, '登陆系统', '123.149.212.54', '2018-06-01 10:05:01');
INSERT INTO `sys_oplog` VALUES (3573, 1, '[设备记录] 增加', '123.149.212.54', '2018-06-01 10:18:14');
INSERT INTO `sys_oplog` VALUES (3574, 1, '[设备记录] 修改', '123.149.212.54', '2018-06-01 10:18:37');
INSERT INTO `sys_oplog` VALUES (3575, 1, '登陆系统', '118.187.53.6', '2018-06-01 10:28:03');
INSERT INTO `sys_oplog` VALUES (3576, 1, '登陆系统', '123.149.212.174', '2018-06-01 11:51:55');
INSERT INTO `sys_oplog` VALUES (3577, 1, '登陆系统', '111.18.41.237', '2018-06-01 12:02:51');
INSERT INTO `sys_oplog` VALUES (3578, 1, '登陆系统', '118.187.53.6', '2018-06-01 12:10:46');
INSERT INTO `sys_oplog` VALUES (3579, 1, '登陆系统', '123.149.212.174', '2018-06-01 12:19:27');
INSERT INTO `sys_oplog` VALUES (3580, 1, '登陆系统', '111.18.41.237', '2018-06-01 12:31:37');
INSERT INTO `sys_oplog` VALUES (3581, 1, '登陆系统', '118.187.53.6', '2018-06-01 13:39:52');
INSERT INTO `sys_oplog` VALUES (3582, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 15:20:08');
INSERT INTO `sys_oplog` VALUES (3583, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 15:20:30');
INSERT INTO `sys_oplog` VALUES (3584, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 15:25:12');
INSERT INTO `sys_oplog` VALUES (3585, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 15:25:21');
INSERT INTO `sys_oplog` VALUES (3586, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 15:25:29');
INSERT INTO `sys_oplog` VALUES (3587, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 15:25:41');
INSERT INTO `sys_oplog` VALUES (3588, 1, '[设备记录] 修改', '111.18.41.237', '2018-06-01 15:25:50');
INSERT INTO `sys_oplog` VALUES (3589, 1, '[优惠设置] 修改', '111.18.41.237', '2018-06-01 15:49:47');
INSERT INTO `sys_oplog` VALUES (3590, 1, '[优惠设置] 修改', '111.18.41.237', '2018-06-01 15:49:56');
INSERT INTO `sys_oplog` VALUES (3591, 1, '[优惠设置] 修改', '111.18.41.237', '2018-06-01 15:50:12');
INSERT INTO `sys_oplog` VALUES (3592, 1, '登陆系统', '118.187.53.6', '2018-06-01 15:52:33');
INSERT INTO `sys_oplog` VALUES (3593, 1, '登陆系统', '111.18.41.237', '2018-06-01 16:40:45');
INSERT INTO `sys_oplog` VALUES (3594, 1, '登陆系统', '113.133.216.209', '2018-06-01 20:38:48');
INSERT INTO `sys_oplog` VALUES (3595, 1, '登陆系统', '111.18.41.204', '2018-06-02 13:14:18');
INSERT INTO `sys_oplog` VALUES (3596, 1, '登陆系统', '111.18.41.204', '2018-06-02 14:19:18');
INSERT INTO `sys_oplog` VALUES (3597, 1, '登陆系统', '111.18.41.204', '2018-06-02 14:36:40');
INSERT INTO `sys_oplog` VALUES (3598, 1, '登陆系统', '111.18.41.204', '2018-06-02 14:59:35');
INSERT INTO `sys_oplog` VALUES (3599, 1, '登陆系统', '111.18.41.204', '2018-06-02 15:05:02');
INSERT INTO `sys_oplog` VALUES (3600, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-02 15:06:40');
INSERT INTO `sys_oplog` VALUES (3601, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-02 15:07:22');
INSERT INTO `sys_oplog` VALUES (3602, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-02 15:08:01');
INSERT INTO `sys_oplog` VALUES (3603, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-02 15:09:05');
INSERT INTO `sys_oplog` VALUES (3604, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-02 15:09:38');
INSERT INTO `sys_oplog` VALUES (3605, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-02 15:10:14');
INSERT INTO `sys_oplog` VALUES (3606, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-02 15:11:01');
INSERT INTO `sys_oplog` VALUES (3607, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-02 15:11:35');
INSERT INTO `sys_oplog` VALUES (3608, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-02 15:12:08');
INSERT INTO `sys_oplog` VALUES (3609, 1, '登陆系统', '113.200.107.187', '2018-06-02 15:15:38');
INSERT INTO `sys_oplog` VALUES (3610, 1, '登陆系统', '113.200.107.187', '2018-06-02 15:21:12');
INSERT INTO `sys_oplog` VALUES (3611, 1, '登陆系统', '123.139.66.66', '2018-06-02 15:29:18');
INSERT INTO `sys_oplog` VALUES (3612, 1, '登陆系统', '123.139.66.66', '2018-06-02 15:36:30');
INSERT INTO `sys_oplog` VALUES (3613, 1, '登陆系统', '123.139.66.66', '2018-06-02 15:46:36');
INSERT INTO `sys_oplog` VALUES (3614, 1, '登陆系统', '123.139.66.66', '2018-06-02 17:23:25');
INSERT INTO `sys_oplog` VALUES (3615, 1, '登陆系统', '123.139.66.66', '2018-06-02 17:41:41');
INSERT INTO `sys_oplog` VALUES (3616, 1, '登陆系统', '123.139.66.66', '2018-06-02 17:53:43');
INSERT INTO `sys_oplog` VALUES (3617, 1, '登陆系统', '123.139.66.66', '2018-06-02 18:42:31');
INSERT INTO `sys_oplog` VALUES (3618, 1, '登陆系统', '123.139.66.66', '2018-06-02 18:49:03');
INSERT INTO `sys_oplog` VALUES (3619, 1, '登陆系统', '123.139.66.66', '2018-06-02 18:58:46');
INSERT INTO `sys_oplog` VALUES (3620, 1, '登陆系统', '123.139.66.66', '2018-06-02 19:27:31');
INSERT INTO `sys_oplog` VALUES (3621, 1, '登陆系统', '123.139.66.66', '2018-06-02 19:32:03');
INSERT INTO `sys_oplog` VALUES (3622, 1, '登陆系统', '123.139.66.66', '2018-06-02 19:44:10');
INSERT INTO `sys_oplog` VALUES (3623, 1, '登陆系统', '123.139.66.66', '2018-06-02 19:46:40');
INSERT INTO `sys_oplog` VALUES (3624, 1, '登陆系统', '123.139.66.66', '2018-06-02 19:53:25');
INSERT INTO `sys_oplog` VALUES (3625, 1, '登陆系统', '123.139.66.66', '2018-06-02 20:15:05');
INSERT INTO `sys_oplog` VALUES (3626, 1, '登陆系统', '123.139.66.66', '2018-06-02 20:49:45');
INSERT INTO `sys_oplog` VALUES (3627, 1, '登陆系统', '123.139.66.66', '2018-06-02 21:21:04');
INSERT INTO `sys_oplog` VALUES (3628, 1, '登陆系统', '123.139.66.66', '2018-06-02 21:33:10');
INSERT INTO `sys_oplog` VALUES (3629, 1, '登陆系统', '123.139.66.66', '2018-06-02 21:38:50');
INSERT INTO `sys_oplog` VALUES (3630, 1, '登陆系统', '123.139.66.66', '2018-06-02 22:22:18');
INSERT INTO `sys_oplog` VALUES (3631, 1, '登陆系统', '123.139.66.66', '2018-06-02 22:26:11');
INSERT INTO `sys_oplog` VALUES (3632, 1, '登陆系统', '123.139.66.66', '2018-06-02 22:31:40');
INSERT INTO `sys_oplog` VALUES (3633, 1, '登陆系统', '123.139.66.66', '2018-06-02 22:37:46');
INSERT INTO `sys_oplog` VALUES (3634, 1, '登陆系统', '123.139.66.66', '2018-06-02 22:48:40');
INSERT INTO `sys_oplog` VALUES (3635, 1, '登陆系统', '123.139.66.66', '2018-06-02 23:02:46');
INSERT INTO `sys_oplog` VALUES (3636, 1, '登陆系统', '123.139.66.66', '2018-06-02 23:08:46');
INSERT INTO `sys_oplog` VALUES (3637, 1, '登陆系统', '123.139.66.66', '2018-06-02 23:19:43');
INSERT INTO `sys_oplog` VALUES (3638, 1, '登陆系统', '123.139.66.66', '2018-06-02 23:37:06');
INSERT INTO `sys_oplog` VALUES (3639, 1, '登陆系统', '123.139.66.66', '2018-06-02 23:42:30');
INSERT INTO `sys_oplog` VALUES (3640, 1, '登陆系统', '123.139.66.66', '2018-06-02 23:52:27');
INSERT INTO `sys_oplog` VALUES (3641, 1, '登陆系统', '123.139.66.66', '2018-06-03 00:15:47');
INSERT INTO `sys_oplog` VALUES (3642, 1, '登陆系统', '123.139.66.66', '2018-06-03 00:19:46');
INSERT INTO `sys_oplog` VALUES (3643, 1, '登陆系统', '123.139.66.66', '2018-06-03 00:27:47');
INSERT INTO `sys_oplog` VALUES (3644, 1, '登陆系统', '123.139.66.66', '2018-06-03 00:56:14');
INSERT INTO `sys_oplog` VALUES (3645, 1, '登陆系统', '123.139.66.66', '2018-06-03 01:17:25');
INSERT INTO `sys_oplog` VALUES (3646, 1, '登陆系统', '123.139.66.66', '2018-06-03 01:18:31');
INSERT INTO `sys_oplog` VALUES (3647, 1, '登陆系统', '123.139.66.66', '2018-06-03 01:48:18');
INSERT INTO `sys_oplog` VALUES (3648, 1, '登陆系统', '123.139.66.66', '2018-06-03 06:59:57');
INSERT INTO `sys_oplog` VALUES (3649, 1, '登陆系统', '113.143.164.57', '2018-06-03 07:23:42');
INSERT INTO `sys_oplog` VALUES (3650, 1, '[功率日志] 导出cvs', '113.143.164.57', '2018-06-03 07:25:22');
INSERT INTO `sys_oplog` VALUES (3651, 1, '[消费记录] 导出cvs', '113.143.164.57', '2018-06-03 07:47:49');
INSERT INTO `sys_oplog` VALUES (3652, 1, '登陆系统', '123.139.115.149', '2018-06-03 09:51:06');
INSERT INTO `sys_oplog` VALUES (3653, 1, '登陆系统', '123.139.115.149', '2018-06-03 10:16:50');
INSERT INTO `sys_oplog` VALUES (3654, 1, '登陆系统', '123.139.115.149', '2018-06-03 11:53:39');
INSERT INTO `sys_oplog` VALUES (3655, 1, '登陆系统', '113.200.106.70', '2018-06-03 13:20:52');
INSERT INTO `sys_oplog` VALUES (3656, 1, '登陆系统', '113.200.106.70', '2018-06-03 13:58:16');
INSERT INTO `sys_oplog` VALUES (3657, 1, '登陆系统', '123.138.233.157', '2018-06-03 16:05:35');
INSERT INTO `sys_oplog` VALUES (3658, 1, '登陆系统', '117.33.124.228', '2018-06-03 16:37:44');
INSERT INTO `sys_oplog` VALUES (3659, 1, '登陆系统', '123.138.233.157', '2018-06-03 17:22:36');
INSERT INTO `sys_oplog` VALUES (3660, 1, '登陆系统', '115.171.61.188', '2018-06-03 19:49:44');
INSERT INTO `sys_oplog` VALUES (3661, 1, '登陆系统', '113.200.106.86', '2018-06-03 20:07:51');
INSERT INTO `sys_oplog` VALUES (3662, 1, '登陆系统', '113.200.106.86', '2018-06-03 20:37:45');
INSERT INTO `sys_oplog` VALUES (3663, 1, '登陆系统', '113.200.106.86', '2018-06-03 20:47:34');
INSERT INTO `sys_oplog` VALUES (3664, 1, '登陆系统', '113.200.106.86', '2018-06-03 21:00:44');
INSERT INTO `sys_oplog` VALUES (3665, 1, '登陆系统', '113.200.106.86', '2018-06-03 21:39:50');
INSERT INTO `sys_oplog` VALUES (3666, 1, '登陆系统', '175.4.11.38', '2018-06-03 22:29:08');
INSERT INTO `sys_oplog` VALUES (3667, 1, '登陆系统', '113.200.85.183', '2018-06-03 22:33:14');
INSERT INTO `sys_oplog` VALUES (3668, 1, '登陆系统', '123.139.115.149', '2018-06-04 02:57:57');
INSERT INTO `sys_oplog` VALUES (3669, 1, '登陆系统', '123.139.115.149', '2018-06-04 07:03:48');
INSERT INTO `sys_oplog` VALUES (3670, 1, '登陆系统', '111.18.41.218', '2018-06-04 08:43:13');
INSERT INTO `sys_oplog` VALUES (3671, 1, '登陆系统', '113.200.107.254', '2018-06-04 09:02:04');
INSERT INTO `sys_oplog` VALUES (3672, 1, '登陆系统', '113.200.106.77', '2018-06-04 12:00:25');
INSERT INTO `sys_oplog` VALUES (3673, 1, '登陆系统', '110.185.28.228', '2018-06-04 13:08:22');
INSERT INTO `sys_oplog` VALUES (3674, 1, '登陆系统', '110.185.28.226', '2018-06-04 14:46:49');
INSERT INTO `sys_oplog` VALUES (3675, 1, '登陆系统', '171.217.141.119', '2018-06-04 14:56:59');
INSERT INTO `sys_oplog` VALUES (3676, 1, '登陆系统', '123.149.208.188', '2018-06-04 16:04:45');
INSERT INTO `sys_oplog` VALUES (3677, 1, '设备290000201:356566072333396被删除', '123.149.208.188', '2018-06-04 16:09:34');
INSERT INTO `sys_oplog` VALUES (3678, 1, '[设备记录] 删除', '123.149.208.188', '2018-06-04 16:09:34');
INSERT INTO `sys_oplog` VALUES (3679, 1, '登陆系统', '111.18.41.218', '2018-06-04 16:10:39');
INSERT INTO `sys_oplog` VALUES (3680, 1, '登陆系统', '0:0:0:0:0:0:0:1', '2018-06-05 09:42:29');
INSERT INTO `sys_oplog` VALUES (3681, 1, '登陆系统', '110.185.28.178', '2018-06-05 09:46:06');
INSERT INTO `sys_oplog` VALUES (3682, 1, '登陆系统', '110.185.28.178', '2018-06-05 09:47:45');
INSERT INTO `sys_oplog` VALUES (3683, 1, '登陆系统', '182.138.153.214', '2018-06-05 09:50:56');
INSERT INTO `sys_oplog` VALUES (3684, 1, '登陆系统', '182.138.153.214', '2018-06-05 09:51:13');
INSERT INTO `sys_oplog` VALUES (3685, 1, '登陆系统', '116.228.88.72', '2018-06-05 09:53:52');
INSERT INTO `sys_oplog` VALUES (3686, 1, '[数据字典] 删除', '116.228.88.72', '2018-06-05 09:54:37');
INSERT INTO `sys_oplog` VALUES (3687, 1, '[在线表单] 删除', '116.228.88.72', '2018-06-05 09:54:42');
INSERT INTO `sys_oplog` VALUES (3688, 1, '[在线表单] 删除', '116.228.88.72', '2018-06-05 09:54:48');
INSERT INTO `sys_oplog` VALUES (3689, 1, '[菜单管理] 删除', '116.228.88.72', '2018-06-05 09:55:40');
INSERT INTO `sys_oplog` VALUES (3690, 1, '设备斯维小区2号机:356566077004984被删除', '116.228.88.72', '2018-06-05 09:56:15');
INSERT INTO `sys_oplog` VALUES (3691, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:15');
INSERT INTO `sys_oplog` VALUES (3692, 1, '设备290000025:356566077005296被删除', '116.228.88.72', '2018-06-05 09:56:19');
INSERT INTO `sys_oplog` VALUES (3693, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:19');
INSERT INTO `sys_oplog` VALUES (3694, 1, '设备斯维小区1号机:356566077039485被删除', '116.228.88.72', '2018-06-05 09:56:22');
INSERT INTO `sys_oplog` VALUES (3695, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:22');
INSERT INTO `sys_oplog` VALUES (3696, 1, '设备斯维小区3号机:356566077060598被删除', '116.228.88.72', '2018-06-05 09:56:24');
INSERT INTO `sys_oplog` VALUES (3697, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:24');
INSERT INTO `sys_oplog` VALUES (3698, 1, '设备290000166:356566077059970被删除', '116.228.88.72', '2018-06-05 09:56:30');
INSERT INTO `sys_oplog` VALUES (3699, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:30');
INSERT INTO `sys_oplog` VALUES (3700, 1, '设备290000021:356566077008365被删除', '116.228.88.72', '2018-06-05 09:56:38');
INSERT INTO `sys_oplog` VALUES (3701, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:38');
INSERT INTO `sys_oplog` VALUES (3702, 1, '设备三府湾天悦2号机车棚:356566077157089被删除', '116.228.88.72', '2018-06-05 09:56:41');
INSERT INTO `sys_oplog` VALUES (3703, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:41');
INSERT INTO `sys_oplog` VALUES (3704, 1, '设备三府湾天悦1号机车棚:356566077157071被删除', '116.228.88.72', '2018-06-05 09:56:44');
INSERT INTO `sys_oplog` VALUES (3705, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:44');
INSERT INTO `sys_oplog` VALUES (3706, 1, '设备290000182:356566077031532被删除', '116.228.88.72', '2018-06-05 09:56:47');
INSERT INTO `sys_oplog` VALUES (3707, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:47');
INSERT INTO `sys_oplog` VALUES (3708, 1, '设备290000191:356566077032761被删除', '116.228.88.72', '2018-06-05 09:56:49');
INSERT INTO `sys_oplog` VALUES (3709, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:49');
INSERT INTO `sys_oplog` VALUES (3710, 1, '设备290000179:356566077040988被删除', '116.228.88.72', '2018-06-05 09:56:51');
INSERT INTO `sys_oplog` VALUES (3711, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:51');
INSERT INTO `sys_oplog` VALUES (3712, 1, '设备290000194:356566076999549被删除', '116.228.88.72', '2018-06-05 09:56:54');
INSERT INTO `sys_oplog` VALUES (3713, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:54');
INSERT INTO `sys_oplog` VALUES (3714, 1, '设备290000196:356566077060846被删除', '116.228.88.72', '2018-06-05 09:56:57');
INSERT INTO `sys_oplog` VALUES (3715, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:56:57');
INSERT INTO `sys_oplog` VALUES (3716, 1, '设备290000195:356566077060325被删除', '116.228.88.72', '2018-06-05 09:57:00');
INSERT INTO `sys_oplog` VALUES (3717, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:00');
INSERT INTO `sys_oplog` VALUES (3718, 1, '设备290000192:356566077039519被删除', '116.228.88.72', '2018-06-05 09:57:02');
INSERT INTO `sys_oplog` VALUES (3719, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:02');
INSERT INTO `sys_oplog` VALUES (3720, 1, '设备290000186:356566076999580被删除', '116.228.88.72', '2018-06-05 09:57:05');
INSERT INTO `sys_oplog` VALUES (3721, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:05');
INSERT INTO `sys_oplog` VALUES (3722, 1, '设备290000199:356566077043172被删除', '116.228.88.72', '2018-06-05 09:57:09');
INSERT INTO `sys_oplog` VALUES (3723, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:09');
INSERT INTO `sys_oplog` VALUES (3724, 1, '设备290000198:356566077060770被删除', '116.228.88.72', '2018-06-05 09:57:12');
INSERT INTO `sys_oplog` VALUES (3725, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:12');
INSERT INTO `sys_oplog` VALUES (3726, 1, '设备290000200:356566077060010被删除', '116.228.88.72', '2018-06-05 09:57:14');
INSERT INTO `sys_oplog` VALUES (3727, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:14');
INSERT INTO `sys_oplog` VALUES (3728, 1, '设备290000183:356566077043198被删除', '116.228.88.72', '2018-06-05 09:57:17');
INSERT INTO `sys_oplog` VALUES (3729, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:17');
INSERT INTO `sys_oplog` VALUES (3730, 1, '设备290000193:356566077040889被删除', '116.228.88.72', '2018-06-05 09:57:19');
INSERT INTO `sys_oplog` VALUES (3731, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:19');
INSERT INTO `sys_oplog` VALUES (3732, 1, '设备290000190:356566077060200被删除', '116.228.88.72', '2018-06-05 09:57:22');
INSERT INTO `sys_oplog` VALUES (3733, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:22');
INSERT INTO `sys_oplog` VALUES (3734, 1, '设备290000197:356566077040921被删除', '116.228.88.72', '2018-06-05 09:57:25');
INSERT INTO `sys_oplog` VALUES (3735, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:25');
INSERT INTO `sys_oplog` VALUES (3736, 1, '设备290000187:356566077040970被删除', '116.228.88.72', '2018-06-05 09:57:27');
INSERT INTO `sys_oplog` VALUES (3737, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:27');
INSERT INTO `sys_oplog` VALUES (3738, 1, '设备290000189:356566077060739被删除', '116.228.88.72', '2018-06-05 09:57:29');
INSERT INTO `sys_oplog` VALUES (3739, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:29');
INSERT INTO `sys_oplog` VALUES (3740, 1, '设备290000188:356566077060226被删除', '116.228.88.72', '2018-06-05 09:57:32');
INSERT INTO `sys_oplog` VALUES (3741, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:32');
INSERT INTO `sys_oplog` VALUES (3742, 1, '设备290000185:356566077059996被删除', '116.228.88.72', '2018-06-05 09:57:34');
INSERT INTO `sys_oplog` VALUES (3743, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:34');
INSERT INTO `sys_oplog` VALUES (3744, 1, '设备290000177:356566077043289被删除', '116.228.88.72', '2018-06-05 09:57:37');
INSERT INTO `sys_oplog` VALUES (3745, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:37');
INSERT INTO `sys_oplog` VALUES (3746, 1, '设备290000184:356566077044188被删除', '116.228.88.72', '2018-06-05 09:57:40');
INSERT INTO `sys_oplog` VALUES (3747, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:40');
INSERT INTO `sys_oplog` VALUES (3748, 1, '设备290000176:356566077004737被删除', '116.228.88.72', '2018-06-05 09:57:42');
INSERT INTO `sys_oplog` VALUES (3749, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:42');
INSERT INTO `sys_oplog` VALUES (3750, 1, '设备290000178:356566077044170被删除', '116.228.88.72', '2018-06-05 09:57:45');
INSERT INTO `sys_oplog` VALUES (3751, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:45');
INSERT INTO `sys_oplog` VALUES (3752, 1, '设备290000181:356566077062461被删除', '116.228.88.72', '2018-06-05 09:57:48');
INSERT INTO `sys_oplog` VALUES (3753, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:48');
INSERT INTO `sys_oplog` VALUES (3754, 1, '设备290000180:356566076999598被删除', '116.228.88.72', '2018-06-05 09:57:50');
INSERT INTO `sys_oplog` VALUES (3755, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:50');
INSERT INTO `sys_oplog` VALUES (3756, 1, '设备290000175:356566077014322被删除', '116.228.88.72', '2018-06-05 09:57:53');
INSERT INTO `sys_oplog` VALUES (3757, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:53');
INSERT INTO `sys_oplog` VALUES (3758, 1, '设备290000174:356566077005031被删除', '116.228.88.72', '2018-06-05 09:57:55');
INSERT INTO `sys_oplog` VALUES (3759, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:55');
INSERT INTO `sys_oplog` VALUES (3760, 1, '设备290000172:356566077004760被删除', '116.228.88.72', '2018-06-05 09:57:58');
INSERT INTO `sys_oplog` VALUES (3761, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:57:58');
INSERT INTO `sys_oplog` VALUES (3762, 1, '设备290000173:356566077032662被删除', '116.228.88.72', '2018-06-05 09:58:00');
INSERT INTO `sys_oplog` VALUES (3763, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:00');
INSERT INTO `sys_oplog` VALUES (3764, 1, '设备290000171:356566077061398被删除', '116.228.88.72', '2018-06-05 09:58:02');
INSERT INTO `sys_oplog` VALUES (3765, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:02');
INSERT INTO `sys_oplog` VALUES (3766, 1, '设备290000133:356566077007698被删除', '116.228.88.72', '2018-06-05 09:58:04');
INSERT INTO `sys_oplog` VALUES (3767, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:04');
INSERT INTO `sys_oplog` VALUES (3768, 1, '设备290000165:356566077007714被删除', '116.228.88.72', '2018-06-05 09:58:06');
INSERT INTO `sys_oplog` VALUES (3769, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:06');
INSERT INTO `sys_oplog` VALUES (3770, 1, '设备290000118:356566077003549被删除', '116.228.88.72', '2018-06-05 09:58:10');
INSERT INTO `sys_oplog` VALUES (3771, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:10');
INSERT INTO `sys_oplog` VALUES (3772, 1, '设备290000119:356566077003093被删除', '116.228.88.72', '2018-06-05 09:58:13');
INSERT INTO `sys_oplog` VALUES (3773, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:13');
INSERT INTO `sys_oplog` VALUES (3774, 1, '设备290000120:356566077004745被删除', '116.228.88.72', '2018-06-05 09:58:15');
INSERT INTO `sys_oplog` VALUES (3775, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:15');
INSERT INTO `sys_oplog` VALUES (3776, 1, '设备290000116:356566077061281被删除', '116.228.88.72', '2018-06-05 09:58:18');
INSERT INTO `sys_oplog` VALUES (3777, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:18');
INSERT INTO `sys_oplog` VALUES (3778, 1, '设备290000114:356566077060028被删除', '116.228.88.72', '2018-06-05 09:58:20');
INSERT INTO `sys_oplog` VALUES (3779, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:20');
INSERT INTO `sys_oplog` VALUES (3780, 1, '设备290000115:356566077059962被删除', '116.228.88.72', '2018-06-05 09:58:24');
INSERT INTO `sys_oplog` VALUES (3781, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:24');
INSERT INTO `sys_oplog` VALUES (3782, 1, '设备290000117:356566077060093被删除', '116.228.88.72', '2018-06-05 09:58:26');
INSERT INTO `sys_oplog` VALUES (3783, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:26');
INSERT INTO `sys_oplog` VALUES (3784, 1, '设备290000121:356566077005064被删除', '116.228.88.72', '2018-06-05 09:58:30');
INSERT INTO `sys_oplog` VALUES (3785, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:30');
INSERT INTO `sys_oplog` VALUES (3786, 1, '设备290000122:356566077027530被删除', '116.228.88.72', '2018-06-05 09:58:33');
INSERT INTO `sys_oplog` VALUES (3787, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:33');
INSERT INTO `sys_oplog` VALUES (3788, 1, '设备290000123:356566077059608被删除', '116.228.88.72', '2018-06-05 09:58:37');
INSERT INTO `sys_oplog` VALUES (3789, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:37');
INSERT INTO `sys_oplog` VALUES (3790, 1, '设备290000124:356566077036804被删除', '116.228.88.72', '2018-06-05 09:58:40');
INSERT INTO `sys_oplog` VALUES (3791, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:40');
INSERT INTO `sys_oplog` VALUES (3792, 1, '设备290000112:356566077032654被删除', '116.228.88.72', '2018-06-05 09:58:43');
INSERT INTO `sys_oplog` VALUES (3793, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:43');
INSERT INTO `sys_oplog` VALUES (3794, 1, '设备290000104:356566077007722被删除', '116.228.88.72', '2018-06-05 09:58:45');
INSERT INTO `sys_oplog` VALUES (3795, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:45');
INSERT INTO `sys_oplog` VALUES (3796, 1, '设备290000111:356566077001113被删除', '116.228.88.72', '2018-06-05 09:58:48');
INSERT INTO `sys_oplog` VALUES (3797, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:48');
INSERT INTO `sys_oplog` VALUES (3798, 1, '设备290000113:356566077059327被删除', '116.228.88.72', '2018-06-05 09:58:50');
INSERT INTO `sys_oplog` VALUES (3799, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:50');
INSERT INTO `sys_oplog` VALUES (3800, 1, '设备290000125:356566077043297被删除', '116.228.88.72', '2018-06-05 09:58:53');
INSERT INTO `sys_oplog` VALUES (3801, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:53');
INSERT INTO `sys_oplog` VALUES (3802, 1, '设备290000126:356566077043255被删除', '116.228.88.72', '2018-06-05 09:58:56');
INSERT INTO `sys_oplog` VALUES (3803, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:56');
INSERT INTO `sys_oplog` VALUES (3804, 1, '设备290000109:356566077061026被删除', '116.228.88.72', '2018-06-05 09:58:59');
INSERT INTO `sys_oplog` VALUES (3805, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:58:59');
INSERT INTO `sys_oplog` VALUES (3806, 1, '设备290000053:356566077005122被删除', '116.228.88.72', '2018-06-05 09:59:02');
INSERT INTO `sys_oplog` VALUES (3807, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:02');
INSERT INTO `sys_oplog` VALUES (3808, 1, '设备290000108:356566077059061被删除', '116.228.88.72', '2018-06-05 09:59:04');
INSERT INTO `sys_oplog` VALUES (3809, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:04');
INSERT INTO `sys_oplog` VALUES (3810, 1, '设备290000110:356566076999572被删除', '116.228.88.72', '2018-06-05 09:59:07');
INSERT INTO `sys_oplog` VALUES (3811, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:07');
INSERT INTO `sys_oplog` VALUES (3812, 1, '设备290000107:356566077007664被删除', '116.228.88.72', '2018-06-05 09:59:20');
INSERT INTO `sys_oplog` VALUES (3813, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:20');
INSERT INTO `sys_oplog` VALUES (3814, 1, '设备290000106:356566077044212被删除', '116.228.88.72', '2018-06-05 09:59:22');
INSERT INTO `sys_oplog` VALUES (3815, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:22');
INSERT INTO `sys_oplog` VALUES (3816, 1, '设备290000105:356566077003648被删除', '116.228.88.72', '2018-06-05 09:59:24');
INSERT INTO `sys_oplog` VALUES (3817, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:24');
INSERT INTO `sys_oplog` VALUES (3818, 1, '设备290000102:356566077061323被删除', '116.228.88.72', '2018-06-05 09:59:26');
INSERT INTO `sys_oplog` VALUES (3819, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:26');
INSERT INTO `sys_oplog` VALUES (3820, 1, '设备290000103:356566077061364被删除', '116.228.88.72', '2018-06-05 09:59:28');
INSERT INTO `sys_oplog` VALUES (3821, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:28');
INSERT INTO `sys_oplog` VALUES (3822, 1, '设备290000101:356566077060002被删除', '116.228.88.72', '2018-06-05 09:59:31');
INSERT INTO `sys_oplog` VALUES (3823, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:31');
INSERT INTO `sys_oplog` VALUES (3824, 1, '设备290000100:356566077003598被删除', '116.228.88.72', '2018-06-05 09:59:35');
INSERT INTO `sys_oplog` VALUES (3825, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:35');
INSERT INTO `sys_oplog` VALUES (3826, 1, '设备290000099:356566077059178被删除', '116.228.88.72', '2018-06-05 09:59:38');
INSERT INTO `sys_oplog` VALUES (3827, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:38');
INSERT INTO `sys_oplog` VALUES (3828, 1, '设备290000098:356566077003622被删除', '116.228.88.72', '2018-06-05 09:59:40');
INSERT INTO `sys_oplog` VALUES (3829, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:40');
INSERT INTO `sys_oplog` VALUES (3830, 1, '设备290000089:356566077061372被删除', '116.228.88.72', '2018-06-05 09:59:46');
INSERT INTO `sys_oplog` VALUES (3831, 1, '[设备记录] 删除', '116.228.88.72', '2018-06-05 09:59:46');
INSERT INTO `sys_oplog` VALUES (3832, 1, '登陆系统', '182.138.153.214', '2018-06-05 10:03:56');
INSERT INTO `sys_oplog` VALUES (3833, 1, '登陆系统', '0:0:0:0:0:0:0:1', '2018-06-05 10:26:17');
INSERT INTO `sys_oplog` VALUES (3834, 1, '登陆系统', '116.228.88.72', '2018-06-05 10:34:02');
INSERT INTO `sys_oplog` VALUES (3835, 1, '登陆系统', '0:0:0:0:0:0:0:1', '2018-06-05 10:38:07');
INSERT INTO `sys_oplog` VALUES (3836, 1, '登陆系统', '116.228.88.72', '2018-06-05 10:39:15');
INSERT INTO `sys_oplog` VALUES (3837, 1, '登陆系统', '0:0:0:0:0:0:0:1', '2018-06-05 10:54:48');
INSERT INTO `sys_oplog` VALUES (3838, 1, '登陆系统', '0:0:0:0:0:0:0:1', '2018-06-05 16:37:24');
INSERT INTO `sys_oplog` VALUES (3839, 1, '登陆系统', '113.200.85.4', '2018-06-05 17:01:00');
INSERT INTO `sys_oplog` VALUES (3840, 1, '登陆系统', '182.138.153.214', '2018-06-05 17:08:35');
INSERT INTO `sys_oplog` VALUES (3841, 1, '登陆系统', '111.18.41.212', '2018-06-05 17:25:49');
INSERT INTO `sys_oplog` VALUES (3842, 1, '登陆系统', '111.18.41.212', '2018-06-05 17:49:14');
INSERT INTO `sys_oplog` VALUES (3843, 1, '[在线用户] 修改', '111.18.41.212', '2018-06-05 18:20:26');
INSERT INTO `sys_oplog` VALUES (3844, 1, '[设备记录] 修改', '111.18.41.193', '2018-06-05 19:13:24');
INSERT INTO `sys_oplog` VALUES (3845, 1, '登陆系统', '113.200.85.4', '2018-06-05 20:38:12');
INSERT INTO `sys_oplog` VALUES (3846, 1, '登陆系统', '113.200.85.4', '2018-06-05 21:04:10');
INSERT INTO `sys_oplog` VALUES (3847, 1, '登陆系统', '113.200.85.4', '2018-06-05 21:24:43');
INSERT INTO `sys_oplog` VALUES (3848, 1, '登陆系统', '110.185.28.178', '2018-06-05 22:09:40');
INSERT INTO `sys_oplog` VALUES (3849, 1, '[消费记录] 导出cvs', '110.185.28.176', '2018-06-05 22:09:46');
INSERT INTO `sys_oplog` VALUES (3850, 1, '登陆系统', '110.185.28.177', '2018-06-05 22:16:34');
INSERT INTO `sys_oplog` VALUES (3851, 1, '登陆系统', '113.143.166.2', '2018-06-05 22:31:54');
INSERT INTO `sys_oplog` VALUES (3852, 1, '[微信充值记录] 导出cvs', '110.185.28.177', '2018-06-05 23:54:42');
INSERT INTO `sys_oplog` VALUES (3853, 1, '[消费记录] 导出cvs', '110.185.28.176', '2018-06-06 00:04:21');
INSERT INTO `sys_oplog` VALUES (3854, 1, '登陆系统', '110.185.28.176', '2018-06-06 01:03:58');
INSERT INTO `sys_oplog` VALUES (3855, 1, '登陆系统', '110.185.28.175', '2018-06-06 01:20:19');
INSERT INTO `sys_oplog` VALUES (3856, 1, '登陆系统', '110.185.28.178', '2018-06-06 02:07:26');
INSERT INTO `sys_oplog` VALUES (3857, 1, '登陆系统', '118.114.12.171', '2018-06-06 08:49:30');
INSERT INTO `sys_oplog` VALUES (3858, 1, '登陆系统', '111.18.41.193', '2018-06-06 09:06:04');
INSERT INTO `sys_oplog` VALUES (3859, 1, '登陆系统', '111.18.41.193', '2018-06-06 09:10:18');
INSERT INTO `sys_oplog` VALUES (3860, 1, '登陆系统', '0:0:0:0:0:0:0:1', '2018-06-06 09:22:06');
INSERT INTO `sys_oplog` VALUES (3861, 1, '登陆系统', '118.114.12.173', '2018-06-06 12:30:08');
INSERT INTO `sys_oplog` VALUES (3862, 1, '登陆系统', '111.18.41.193', '2018-06-06 13:25:32');
INSERT INTO `sys_oplog` VALUES (3863, 1, '登陆系统', '111.18.41.193', '2018-06-06 13:28:35');
INSERT INTO `sys_oplog` VALUES (3864, 1, '登陆系统', '118.114.12.173', '2018-06-06 13:40:56');
INSERT INTO `sys_oplog` VALUES (3865, 1, '登陆系统', '116.228.88.66', '2018-06-06 14:10:07');
INSERT INTO `sys_oplog` VALUES (3866, 1, '登陆系统', '123.149.209.170', '2018-06-06 14:23:45');
INSERT INTO `sys_oplog` VALUES (3867, 1, '退出系统', '111.18.41.193', '2018-06-06 14:26:55');
INSERT INTO `sys_oplog` VALUES (3868, 1, '登陆系统', '111.18.41.193', '2018-06-06 14:33:17');
INSERT INTO `sys_oplog` VALUES (3869, 1, '退出系统', '111.18.41.193', '2018-06-06 14:37:00');
INSERT INTO `sys_oplog` VALUES (3870, 1, '登陆系统', '116.228.88.66', '2018-06-06 14:50:15');
INSERT INTO `sys_oplog` VALUES (3871, 1, '[设备记录] 增加', '116.228.88.66', '2018-06-06 14:50:26');
INSERT INTO `sys_oplog` VALUES (3872, 1, '登陆系统', '123.149.209.170', '2018-06-06 14:51:31');
INSERT INTO `sys_oplog` VALUES (3873, 1, '[设备记录] 增加', '123.149.209.170', '2018-06-06 14:51:59');
INSERT INTO `sys_oplog` VALUES (3874, 1, '[设备记录] 修改', '123.149.209.170', '2018-06-06 14:52:17');
INSERT INTO `sys_oplog` VALUES (3875, 1, '登陆系统', '118.114.12.175', '2018-06-06 16:28:31');
INSERT INTO `sys_oplog` VALUES (3876, 1, '登陆系统', '111.18.41.193', '2018-06-06 16:51:34');
INSERT INTO `sys_oplog` VALUES (3877, 1, '登陆系统', '118.114.250.165', '2018-06-06 17:04:13');
INSERT INTO `sys_oplog` VALUES (3878, 1, '登陆系统', '123.149.215.62', '2018-06-06 17:56:18');
INSERT INTO `sys_oplog` VALUES (3879, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-06 19:37:57');
INSERT INTO `sys_oplog` VALUES (3880, 1, '登陆系统', '113.200.204.165', '2018-06-06 19:43:26');
INSERT INTO `sys_oplog` VALUES (3881, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-06 19:49:38');
INSERT INTO `sys_oplog` VALUES (3882, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-06 19:50:00');
INSERT INTO `sys_oplog` VALUES (3883, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-06 20:09:50');
INSERT INTO `sys_oplog` VALUES (3884, 1, '[在线用户] 修改', '111.18.41.235', '2018-06-06 20:11:21');
INSERT INTO `sys_oplog` VALUES (3885, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-06 20:17:35');
INSERT INTO `sys_oplog` VALUES (3886, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-06 20:18:35');
INSERT INTO `sys_oplog` VALUES (3887, 1, '[在线用户] 修改', '111.18.41.235', '2018-06-06 20:25:58');
INSERT INTO `sys_oplog` VALUES (3888, 1, '[在线用户] 修改', '111.18.41.235', '2018-06-06 20:26:36');
INSERT INTO `sys_oplog` VALUES (3889, 1, '[在线用户] 修改', '111.18.41.235', '2018-06-06 20:34:36');
INSERT INTO `sys_oplog` VALUES (3890, 1, '登陆系统', '113.200.204.165', '2018-06-06 21:23:49');
INSERT INTO `sys_oplog` VALUES (3891, 1, '登陆系统', '221.11.61.250', '2018-06-06 23:13:49');
INSERT INTO `sys_oplog` VALUES (3892, 1, '登陆系统', '118.114.12.173', '2018-06-07 00:41:59');
INSERT INTO `sys_oplog` VALUES (3893, 1, '登陆系统', '118.114.12.175', '2018-06-07 01:45:41');
INSERT INTO `sys_oplog` VALUES (3894, 1, '登陆系统', '110.185.29.212', '2018-06-07 08:21:56');
INSERT INTO `sys_oplog` VALUES (3895, 1, '登陆系统', '111.18.41.235', '2018-06-07 09:30:43');
INSERT INTO `sys_oplog` VALUES (3896, 1, '[微信充值记录] 导出cvs', '111.18.41.235', '2018-06-07 09:32:14');
INSERT INTO `sys_oplog` VALUES (3897, 1, '[消费记录] 导出cvs', '111.18.41.235', '2018-06-07 09:32:23');
INSERT INTO `sys_oplog` VALUES (3898, 1, '[消费记录] 导出cvs', '111.18.41.235', '2018-06-07 09:32:31');
INSERT INTO `sys_oplog` VALUES (3899, 1, '[消费记录] 导出cvs', '111.18.41.235', '2018-06-07 09:35:12');
INSERT INTO `sys_oplog` VALUES (3900, 1, '登陆系统', '110.185.29.213', '2018-06-07 09:59:17');
INSERT INTO `sys_oplog` VALUES (3901, 1, '登陆系统', '111.18.41.235', '2018-06-07 10:07:15');
INSERT INTO `sys_oplog` VALUES (3902, 1, '退出系统', '111.18.41.235', '2018-06-07 10:10:12');
INSERT INTO `sys_oplog` VALUES (3903, 1, '[微信充值记录] 导出cvs', '111.18.41.235', '2018-06-07 10:54:59');
INSERT INTO `sys_oplog` VALUES (3904, 1, '登陆系统', '111.18.41.235', '2018-06-07 11:00:39');
INSERT INTO `sys_oplog` VALUES (3905, 1, '登陆系统', '123.149.209.170', '2018-06-07 11:01:01');
INSERT INTO `sys_oplog` VALUES (3906, 1, '退出系统', '111.18.41.235', '2018-06-07 11:01:17');
INSERT INTO `sys_oplog` VALUES (3907, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 11:01:50');
INSERT INTO `sys_oplog` VALUES (3908, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 11:02:31');
INSERT INTO `sys_oplog` VALUES (3909, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 11:05:12');
INSERT INTO `sys_oplog` VALUES (3910, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 11:06:06');
INSERT INTO `sys_oplog` VALUES (3911, 1, '登陆系统', '111.18.41.235', '2018-06-07 11:18:54');
INSERT INTO `sys_oplog` VALUES (3912, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 11:56:18');
INSERT INTO `sys_oplog` VALUES (3913, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 11:56:41');
INSERT INTO `sys_oplog` VALUES (3914, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 11:57:01');
INSERT INTO `sys_oplog` VALUES (3915, 1, '[微信充值记录] 导出cvs', '111.18.41.235', '2018-06-07 12:02:47');
INSERT INTO `sys_oplog` VALUES (3916, 1, '登陆系统', '182.138.153.221', '2018-06-07 12:15:30');
INSERT INTO `sys_oplog` VALUES (3917, 1, '登陆系统', '182.138.153.221', '2018-06-07 12:16:45');
INSERT INTO `sys_oplog` VALUES (3918, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 12:28:55');
INSERT INTO `sys_oplog` VALUES (3919, 1, '登陆系统', '182.138.153.221', '2018-06-07 12:34:15');
INSERT INTO `sys_oplog` VALUES (3920, 1, '[消费记录] 导出cvs', '110.185.29.216', '2018-06-07 12:37:43');
INSERT INTO `sys_oplog` VALUES (3921, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 12:48:19');
INSERT INTO `sys_oplog` VALUES (3922, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 12:48:39');
INSERT INTO `sys_oplog` VALUES (3923, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 12:48:54');
INSERT INTO `sys_oplog` VALUES (3924, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 12:49:54');
INSERT INTO `sys_oplog` VALUES (3925, 1, '登陆系统', '113.200.107.153', '2018-06-07 13:48:40');
INSERT INTO `sys_oplog` VALUES (3926, 1, '登陆系统', '113.200.107.153', '2018-06-07 14:14:52');
INSERT INTO `sys_oplog` VALUES (3927, 1, '登陆系统', '116.228.88.66', '2018-06-07 14:35:02');
INSERT INTO `sys_oplog` VALUES (3928, 1, '登陆系统', '113.200.107.153', '2018-06-07 14:44:32');
INSERT INTO `sys_oplog` VALUES (3929, 1, '登陆系统', '113.200.107.153', '2018-06-07 14:56:12');
INSERT INTO `sys_oplog` VALUES (3930, 1, '登陆系统', '113.200.85.70', '2018-06-07 15:33:22');
INSERT INTO `sys_oplog` VALUES (3931, 1, '登陆系统', '123.149.215.62', '2018-06-07 15:47:22');
INSERT INTO `sys_oplog` VALUES (3932, 1, '[优惠设置] 修改', '111.18.41.235', '2018-06-07 16:20:46');
INSERT INTO `sys_oplog` VALUES (3933, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 16:38:27');
INSERT INTO `sys_oplog` VALUES (3934, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 16:38:47');
INSERT INTO `sys_oplog` VALUES (3935, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 16:39:09');
INSERT INTO `sys_oplog` VALUES (3936, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 16:39:36');
INSERT INTO `sys_oplog` VALUES (3937, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 16:48:38');
INSERT INTO `sys_oplog` VALUES (3938, 1, '[设备记录] 修改', '111.18.41.235', '2018-06-07 16:49:18');
INSERT INTO `sys_oplog` VALUES (3939, 1, '登陆系统', '123.138.233.190', '2018-06-07 16:53:47');
INSERT INTO `sys_oplog` VALUES (3940, 1, '登陆系统', '123.138.233.190', '2018-06-07 17:03:52');
INSERT INTO `sys_oplog` VALUES (3941, 1, '登陆系统', '123.138.233.190', '2018-06-07 17:05:02');
INSERT INTO `sys_oplog` VALUES (3942, 1, '登陆系统', '111.18.41.235', '2018-06-07 17:36:28');
INSERT INTO `sys_oplog` VALUES (3943, 1, '登陆系统', '113.200.107.151', '2018-06-07 18:06:46');
INSERT INTO `sys_oplog` VALUES (3944, 1, '退出系统', '111.18.41.235', '2018-06-07 18:10:40');
INSERT INTO `sys_oplog` VALUES (3945, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-07 19:12:11');
INSERT INTO `sys_oplog` VALUES (3946, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-07 19:12:48');
INSERT INTO `sys_oplog` VALUES (3947, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-07 19:13:38');
INSERT INTO `sys_oplog` VALUES (3948, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-07 19:14:16');
INSERT INTO `sys_oplog` VALUES (3949, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-07 19:14:55');
INSERT INTO `sys_oplog` VALUES (3950, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-07 19:15:30');
INSERT INTO `sys_oplog` VALUES (3951, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-07 19:16:56');
INSERT INTO `sys_oplog` VALUES (3952, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-07 19:17:31');
INSERT INTO `sys_oplog` VALUES (3953, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-07 19:18:31');
INSERT INTO `sys_oplog` VALUES (3954, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-07 19:19:32');
INSERT INTO `sys_oplog` VALUES (3955, 1, '登陆系统', '111.18.41.238', '2018-06-07 19:45:25');
INSERT INTO `sys_oplog` VALUES (3956, 1, '登陆系统', '111.18.41.238', '2018-06-07 21:04:17');
INSERT INTO `sys_oplog` VALUES (3957, 1, '登陆系统', '111.18.41.238', '2018-06-07 21:38:38');
INSERT INTO `sys_oplog` VALUES (3958, 1, '登陆系统', '111.18.41.238', '2018-06-07 22:26:50');
INSERT INTO `sys_oplog` VALUES (3959, 1, '登陆系统', '113.200.107.151', '2018-06-07 23:16:52');
INSERT INTO `sys_oplog` VALUES (3960, 1, '登陆系统', '113.200.107.151', '2018-06-08 01:14:48');
INSERT INTO `sys_oplog` VALUES (3961, 1, '登陆系统', '113.200.107.151', '2018-06-08 01:15:51');
INSERT INTO `sys_oplog` VALUES (3962, 1, '登陆系统', '113.200.107.151', '2018-06-08 01:21:11');
INSERT INTO `sys_oplog` VALUES (3963, 1, '登陆系统', '113.200.107.151', '2018-06-08 01:40:24');
INSERT INTO `sys_oplog` VALUES (3964, 1, '登陆系统', '113.200.107.151', '2018-06-08 02:46:40');
INSERT INTO `sys_oplog` VALUES (3965, 1, '登陆系统', '113.200.107.151', '2018-06-08 07:39:38');
INSERT INTO `sys_oplog` VALUES (3966, 1, '登陆系统', '111.18.41.238', '2018-06-08 08:12:05');
INSERT INTO `sys_oplog` VALUES (3967, 1, '登陆系统', '111.18.41.238', '2018-06-08 09:08:32');
INSERT INTO `sys_oplog` VALUES (3968, 1, '退出系统', '111.18.41.238', '2018-06-08 09:17:26');
INSERT INTO `sys_oplog` VALUES (3969, 1, '登陆系统', '111.18.41.238', '2018-06-08 09:17:42');
INSERT INTO `sys_oplog` VALUES (3970, 1, '登陆系统', '111.18.41.238', '2018-06-08 09:33:15');
INSERT INTO `sys_oplog` VALUES (3971, 1, '登陆系统', '111.18.41.238', '2018-06-08 09:34:12');
INSERT INTO `sys_oplog` VALUES (3972, 1, '登陆系统', '111.18.41.238', '2018-06-08 09:49:31');
INSERT INTO `sys_oplog` VALUES (3973, 1, '[在线用户] 修改', '111.18.41.238', '2018-06-08 10:11:28');
INSERT INTO `sys_oplog` VALUES (3974, 1, '登陆系统', '111.18.41.238', '2018-06-08 10:35:05');
INSERT INTO `sys_oplog` VALUES (3975, 1, '登陆系统', '123.149.215.62', '2018-06-08 11:13:22');
INSERT INTO `sys_oplog` VALUES (3976, 1, '设备290000201:356566072333396被删除', '123.149.215.62', '2018-06-08 11:14:04');
INSERT INTO `sys_oplog` VALUES (3977, 1, '[设备记录] 删除', '123.149.215.62', '2018-06-08 11:14:04');
INSERT INTO `sys_oplog` VALUES (3978, 1, '登陆系统', '111.18.41.238', '2018-06-08 11:20:44');
INSERT INTO `sys_oplog` VALUES (3979, 1, '登陆系统', '111.18.41.238', '2018-06-08 11:23:07');
INSERT INTO `sys_oplog` VALUES (3980, 1, '[在线用户] 修改', '111.18.41.238', '2018-06-08 12:04:41');
INSERT INTO `sys_oplog` VALUES (3981, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 13:22:46');
INSERT INTO `sys_oplog` VALUES (3982, 1, '登陆系统', '113.200.107.59', '2018-06-08 13:25:45');
INSERT INTO `sys_oplog` VALUES (3983, 1, '登陆系统', '111.18.41.238', '2018-06-08 13:26:13');
INSERT INTO `sys_oplog` VALUES (3984, 1, '登陆系统', '113.200.107.59', '2018-06-08 13:27:43');
INSERT INTO `sys_oplog` VALUES (3985, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 13:29:34');
INSERT INTO `sys_oplog` VALUES (3986, 1, '登陆系统', '111.18.41.238', '2018-06-08 14:26:22');
INSERT INTO `sys_oplog` VALUES (3987, 1, '登陆系统', '111.18.41.238', '2018-06-08 14:41:43');
INSERT INTO `sys_oplog` VALUES (3988, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 14:45:48');
INSERT INTO `sys_oplog` VALUES (3989, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 14:47:43');
INSERT INTO `sys_oplog` VALUES (3990, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 14:48:23');
INSERT INTO `sys_oplog` VALUES (3991, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 14:48:43');
INSERT INTO `sys_oplog` VALUES (3992, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 14:49:29');
INSERT INTO `sys_oplog` VALUES (3993, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 14:50:35');
INSERT INTO `sys_oplog` VALUES (3994, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 14:51:36');
INSERT INTO `sys_oplog` VALUES (3995, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 14:52:27');
INSERT INTO `sys_oplog` VALUES (3996, 1, '登陆系统', '111.18.41.238', '2018-06-08 14:55:36');
INSERT INTO `sys_oplog` VALUES (3997, 1, '登陆系统', '111.18.41.238', '2018-06-08 15:30:13');
INSERT INTO `sys_oplog` VALUES (3998, 1, '登陆系统', '111.18.41.238', '2018-06-08 15:48:19');
INSERT INTO `sys_oplog` VALUES (3999, 1, '登陆系统', '111.18.41.238', '2018-06-08 16:36:10');
INSERT INTO `sys_oplog` VALUES (4000, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 17:31:51');
INSERT INTO `sys_oplog` VALUES (4001, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 17:32:16');
INSERT INTO `sys_oplog` VALUES (4002, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 17:57:13');
INSERT INTO `sys_oplog` VALUES (4003, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 17:57:43');
INSERT INTO `sys_oplog` VALUES (4004, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 17:57:59');
INSERT INTO `sys_oplog` VALUES (4005, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 17:58:27');
INSERT INTO `sys_oplog` VALUES (4006, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 17:59:48');
INSERT INTO `sys_oplog` VALUES (4007, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 18:08:32');
INSERT INTO `sys_oplog` VALUES (4008, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 18:08:51');
INSERT INTO `sys_oplog` VALUES (4009, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 18:09:09');
INSERT INTO `sys_oplog` VALUES (4010, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 18:09:34');
INSERT INTO `sys_oplog` VALUES (4011, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 18:09:53');
INSERT INTO `sys_oplog` VALUES (4012, 1, '[设备记录] 修改', '111.18.41.238', '2018-06-08 18:10:13');
INSERT INTO `sys_oplog` VALUES (4013, 1, '登陆系统', '111.18.41.250', '2018-06-08 18:35:31');
INSERT INTO `sys_oplog` VALUES (4014, 1, '登陆系统', '111.18.41.250', '2018-06-08 18:38:34');
INSERT INTO `sys_oplog` VALUES (4015, 1, '登陆系统', '111.18.41.250', '2018-06-08 18:59:03');
INSERT INTO `sys_oplog` VALUES (4016, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 19:08:46');
INSERT INTO `sys_oplog` VALUES (4017, 1, '[在线用户] 修改', '111.18.41.250', '2018-06-08 19:23:56');
INSERT INTO `sys_oplog` VALUES (4018, 1, '登陆系统', '111.18.41.250', '2018-06-08 20:03:32');
INSERT INTO `sys_oplog` VALUES (4019, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:06:55');
INSERT INTO `sys_oplog` VALUES (4020, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:07:38');
INSERT INTO `sys_oplog` VALUES (4021, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:08:06');
INSERT INTO `sys_oplog` VALUES (4022, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:09:31');
INSERT INTO `sys_oplog` VALUES (4023, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:30:54');
INSERT INTO `sys_oplog` VALUES (4024, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:31:11');
INSERT INTO `sys_oplog` VALUES (4025, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:31:26');
INSERT INTO `sys_oplog` VALUES (4026, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:33:26');
INSERT INTO `sys_oplog` VALUES (4027, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:34:01');
INSERT INTO `sys_oplog` VALUES (4028, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:34:42');
INSERT INTO `sys_oplog` VALUES (4029, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:35:27');
INSERT INTO `sys_oplog` VALUES (4030, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:37:19');
INSERT INTO `sys_oplog` VALUES (4031, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:40:37');
INSERT INTO `sys_oplog` VALUES (4032, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:40:52');
INSERT INTO `sys_oplog` VALUES (4033, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:41:52');
INSERT INTO `sys_oplog` VALUES (4034, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:42:12');
INSERT INTO `sys_oplog` VALUES (4035, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:45:32');
INSERT INTO `sys_oplog` VALUES (4036, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 20:46:14');
INSERT INTO `sys_oplog` VALUES (4037, 1, '[消费记录] 导出cvs', '111.18.41.250', '2018-06-08 21:12:26');
INSERT INTO `sys_oplog` VALUES (4038, 1, '登陆系统', '111.18.41.250', '2018-06-08 21:38:36');
INSERT INTO `sys_oplog` VALUES (4039, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 21:41:45');
INSERT INTO `sys_oplog` VALUES (4040, 1, '[设备记录] 修改', '111.18.41.250', '2018-06-08 21:50:50');
INSERT INTO `sys_oplog` VALUES (4041, 1, '登陆系统', '111.18.41.250', '2018-06-08 21:51:14');
INSERT INTO `sys_oplog` VALUES (4042, 1, '登陆系统', '117.136.25.218', '2018-06-08 23:29:46');
INSERT INTO `sys_oplog` VALUES (4043, 1, '登陆系统', '113.200.204.56', '2018-06-09 00:04:35');
INSERT INTO `sys_oplog` VALUES (4044, 1, '登陆系统', '113.200.204.56', '2018-06-09 00:47:43');
INSERT INTO `sys_oplog` VALUES (4045, 1, '登陆系统', '123.138.232.198', '2018-06-09 01:24:29');
INSERT INTO `sys_oplog` VALUES (4046, 1, '登陆系统', '113.200.106.140', '2018-06-09 06:38:47');
INSERT INTO `sys_oplog` VALUES (4047, 1, '登陆系统', '113.200.106.140', '2018-06-09 06:41:39');
INSERT INTO `sys_oplog` VALUES (4048, 1, '登陆系统', '123.138.232.198', '2018-06-09 07:01:26');
INSERT INTO `sys_oplog` VALUES (4049, 1, '登陆系统', '113.133.195.32', '2018-06-09 09:24:03');
INSERT INTO `sys_oplog` VALUES (4050, 1, '登陆系统', '123.138.232.198', '2018-06-09 10:11:01');
INSERT INTO `sys_oplog` VALUES (4051, 1, '登陆系统', '113.200.106.140', '2018-06-09 10:12:15');
INSERT INTO `sys_oplog` VALUES (4052, 1, '登陆系统', '123.138.232.198', '2018-06-09 11:49:18');
INSERT INTO `sys_oplog` VALUES (4053, 1, '登陆系统', '113.200.85.192', '2018-06-09 12:05:36');
INSERT INTO `sys_oplog` VALUES (4054, 1, '登陆系统', '113.200.107.161', '2018-06-09 13:48:54');
INSERT INTO `sys_oplog` VALUES (4055, 1, '登陆系统', '113.200.107.161', '2018-06-09 14:17:58');
INSERT INTO `sys_oplog` VALUES (4056, 1, '登陆系统', '113.133.195.32', '2018-06-09 14:42:04');
INSERT INTO `sys_oplog` VALUES (4057, 1, '登陆系统', '113.200.107.161', '2018-06-09 15:04:09');
INSERT INTO `sys_oplog` VALUES (4058, 1, '登陆系统', '113.200.107.161', '2018-06-09 15:32:26');
INSERT INTO `sys_oplog` VALUES (4059, 1, '登陆系统', '113.200.107.161', '2018-06-09 15:45:38');
INSERT INTO `sys_oplog` VALUES (4060, 1, '登陆系统', '113.200.107.161', '2018-06-09 16:14:19');
INSERT INTO `sys_oplog` VALUES (4061, 1, '登陆系统', '113.200.107.161', '2018-06-09 16:16:38');
INSERT INTO `sys_oplog` VALUES (4062, 1, '登陆系统', '113.200.107.161', '2018-06-09 16:31:39');
INSERT INTO `sys_oplog` VALUES (4063, 1, '登陆系统', '113.200.107.161', '2018-06-09 16:35:33');
INSERT INTO `sys_oplog` VALUES (4064, 1, '登陆系统', '113.200.107.161', '2018-06-09 16:41:40');
INSERT INTO `sys_oplog` VALUES (4065, 1, '登陆系统', '113.200.107.161', '2018-06-09 16:58:36');
INSERT INTO `sys_oplog` VALUES (4066, 1, '登陆系统', '113.200.107.161', '2018-06-09 17:38:26');
INSERT INTO `sys_oplog` VALUES (4067, 1, '登陆系统', '113.200.107.161', '2018-06-09 18:13:25');
INSERT INTO `sys_oplog` VALUES (4068, 1, '登陆系统', '113.200.107.161', '2018-06-09 18:15:41');
INSERT INTO `sys_oplog` VALUES (4069, 1, '登陆系统', '117.33.59.113', '2018-06-09 18:16:32');
INSERT INTO `sys_oplog` VALUES (4070, 1, '[在线用户] 修改', '117.33.59.113', '2018-06-09 18:20:39');
INSERT INTO `sys_oplog` VALUES (4071, 1, '[设备记录] 修改', '117.33.59.113', '2018-06-09 18:23:54');
INSERT INTO `sys_oplog` VALUES (4072, 1, '[设备记录] 修改', '117.33.59.113', '2018-06-09 18:30:42');
INSERT INTO `sys_oplog` VALUES (4073, 1, '[设备记录] 修改', '117.33.59.113', '2018-06-09 18:32:00');
INSERT INTO `sys_oplog` VALUES (4074, 1, '[设备记录] 修改', '117.33.59.113', '2018-06-09 18:32:35');
INSERT INTO `sys_oplog` VALUES (4075, 1, '[设备记录] 修改', '117.33.59.113', '2018-06-09 18:32:52');
INSERT INTO `sys_oplog` VALUES (4076, 1, '[设备记录] 修改', '117.33.59.113', '2018-06-09 18:33:09');
INSERT INTO `sys_oplog` VALUES (4077, 1, '登陆系统', '113.200.106.37', '2018-06-09 18:37:38');
INSERT INTO `sys_oplog` VALUES (4078, 1, '[设备记录] 修改', '117.33.59.113', '2018-06-09 18:43:41');
INSERT INTO `sys_oplog` VALUES (4079, 1, '[设备记录] 修改', '117.33.59.113', '2018-06-09 18:44:11');
INSERT INTO `sys_oplog` VALUES (4080, 1, '登陆系统', '113.200.107.161', '2018-06-09 19:07:54');
INSERT INTO `sys_oplog` VALUES (4081, 1, '登陆系统', '113.200.107.161', '2018-06-09 19:23:09');
INSERT INTO `sys_oplog` VALUES (4082, 1, '登陆系统', '113.200.107.161', '2018-06-09 19:50:23');
INSERT INTO `sys_oplog` VALUES (4083, 1, '登陆系统', '113.200.107.161', '2018-06-09 19:57:49');
INSERT INTO `sys_oplog` VALUES (4084, 1, '登陆系统', '113.200.107.161', '2018-06-09 20:11:44');
INSERT INTO `sys_oplog` VALUES (4085, 1, '登陆系统', '113.200.107.161', '2018-06-09 20:40:16');
INSERT INTO `sys_oplog` VALUES (4086, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:04:43');
INSERT INTO `sys_oplog` VALUES (4087, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:06:05');
INSERT INTO `sys_oplog` VALUES (4088, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:09:20');
INSERT INTO `sys_oplog` VALUES (4089, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:09:20');
INSERT INTO `sys_oplog` VALUES (4090, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:09:20');
INSERT INTO `sys_oplog` VALUES (4091, 1, '登陆系统', '117.33.59.113', '2018-06-09 22:09:23');
INSERT INTO `sys_oplog` VALUES (4092, 1, '[在线用户] 修改', '117.33.59.113', '2018-06-09 22:10:09');
INSERT INTO `sys_oplog` VALUES (4093, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:12:06');
INSERT INTO `sys_oplog` VALUES (4094, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:12:42');
INSERT INTO `sys_oplog` VALUES (4095, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:12:55');
INSERT INTO `sys_oplog` VALUES (4096, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:13:26');
INSERT INTO `sys_oplog` VALUES (4097, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:13:54');
INSERT INTO `sys_oplog` VALUES (4098, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:14:47');
INSERT INTO `sys_oplog` VALUES (4099, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:15:14');
INSERT INTO `sys_oplog` VALUES (4100, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:15:47');
INSERT INTO `sys_oplog` VALUES (4101, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:30:38');
INSERT INTO `sys_oplog` VALUES (4102, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:30:57');
INSERT INTO `sys_oplog` VALUES (4103, 1, '[设备记录] 修改', '123.138.232.141', '2018-06-09 22:31:14');
INSERT INTO `sys_oplog` VALUES (4104, 1, '登陆系统', '123.138.232.141', '2018-06-09 23:04:44');
INSERT INTO `sys_oplog` VALUES (4105, 1, '登陆系统', '123.138.232.141', '2018-06-09 23:12:09');
INSERT INTO `sys_oplog` VALUES (4106, 1, '登陆系统', '123.138.232.141', '2018-06-09 23:20:44');
INSERT INTO `sys_oplog` VALUES (4107, 1, '登陆系统', '123.138.232.141', '2018-06-09 23:36:02');
INSERT INTO `sys_oplog` VALUES (4108, 1, '登陆系统', '123.138.232.141', '2018-06-09 23:52:33');
INSERT INTO `sys_oplog` VALUES (4109, 1, '登陆系统', '123.138.232.141', '2018-06-09 23:53:35');
INSERT INTO `sys_oplog` VALUES (4110, 1, '登陆系统', '123.138.232.141', '2018-06-10 00:02:28');
INSERT INTO `sys_oplog` VALUES (4111, 1, '登陆系统', '123.138.232.141', '2018-06-10 00:13:40');
INSERT INTO `sys_oplog` VALUES (4112, 1, '登陆系统', '123.138.232.141', '2018-06-10 00:29:00');
INSERT INTO `sys_oplog` VALUES (4113, 1, '[在线用户] 修改', '113.143.143.195', '2018-06-10 00:31:55');
INSERT INTO `sys_oplog` VALUES (4114, 1, '[在线用户] 修改', '113.143.143.195', '2018-06-10 00:38:08');
INSERT INTO `sys_oplog` VALUES (4115, 1, '登陆系统', '123.138.232.141', '2018-06-10 00:45:10');
INSERT INTO `sys_oplog` VALUES (4116, 1, '登陆系统', '123.138.232.141', '2018-06-10 01:12:16');
INSERT INTO `sys_oplog` VALUES (4117, 1, '登陆系统', '123.138.232.141', '2018-06-10 01:26:49');
INSERT INTO `sys_oplog` VALUES (4118, 1, '登陆系统', '123.139.115.82', '2018-06-10 02:15:21');
INSERT INTO `sys_oplog` VALUES (4119, 1, '登陆系统', '123.139.115.82', '2018-06-10 03:35:07');
INSERT INTO `sys_oplog` VALUES (4120, 1, '登陆系统', '113.200.44.115', '2018-06-10 07:08:52');
INSERT INTO `sys_oplog` VALUES (4121, 1, '登陆系统', '113.200.44.115', '2018-06-10 07:10:14');
INSERT INTO `sys_oplog` VALUES (4122, 1, '登陆系统', '113.200.44.115', '2018-06-10 09:01:13');
INSERT INTO `sys_oplog` VALUES (4123, 1, '登陆系统', '123.139.115.82', '2018-06-10 10:10:23');
INSERT INTO `sys_oplog` VALUES (4124, 1, '登陆系统', '123.139.115.82', '2018-06-10 12:07:21');
INSERT INTO `sys_oplog` VALUES (4125, 1, '[设备记录] 修改', '123.139.115.82', '2018-06-10 12:12:33');
INSERT INTO `sys_oplog` VALUES (4126, 1, '[设备记录] 修改', '123.139.115.82', '2018-06-10 12:13:51');
INSERT INTO `sys_oplog` VALUES (4127, 1, '[设备记录] 修改', '123.139.115.82', '2018-06-10 12:14:11');
INSERT INTO `sys_oplog` VALUES (4128, 1, '登陆系统', '123.139.115.82', '2018-06-10 12:47:23');
INSERT INTO `sys_oplog` VALUES (4129, 1, '登陆系统', '113.200.85.106', '2018-06-10 13:11:05');
INSERT INTO `sys_oplog` VALUES (4130, 1, '登陆系统', '124.89.78.214', '2018-06-10 14:05:36');
INSERT INTO `sys_oplog` VALUES (4131, 1, '登陆系统', '124.89.78.214', '2018-06-10 14:16:48');
INSERT INTO `sys_oplog` VALUES (4132, 1, '登陆系统', '124.89.78.214', '2018-06-10 14:22:53');
INSERT INTO `sys_oplog` VALUES (4133, 1, '登陆系统', '123.138.233.103', '2018-06-10 15:00:03');
INSERT INTO `sys_oplog` VALUES (4134, 1, '登陆系统', '123.138.233.103', '2018-06-10 15:04:36');
INSERT INTO `sys_oplog` VALUES (4135, 1, '登陆系统', '123.138.233.103', '2018-06-10 15:10:44');
INSERT INTO `sys_oplog` VALUES (4136, 1, '登陆系统', '113.200.106.38', '2018-06-10 15:24:37');
INSERT INTO `sys_oplog` VALUES (4137, 1, '登陆系统', '123.138.233.103', '2018-06-10 15:47:43');
INSERT INTO `sys_oplog` VALUES (4138, 1, '登陆系统', '123.138.233.103', '2018-06-10 15:48:45');
INSERT INTO `sys_oplog` VALUES (4139, 1, '登陆系统', '123.138.233.103', '2018-06-10 16:05:16');
INSERT INTO `sys_oplog` VALUES (4140, 1, '登陆系统', '123.138.233.103', '2018-06-10 16:32:16');
INSERT INTO `sys_oplog` VALUES (4141, 1, '登陆系统', '123.138.233.103', '2018-06-10 16:51:12');
INSERT INTO `sys_oplog` VALUES (4142, 1, '登陆系统', '123.138.233.103', '2018-06-10 17:26:42');
INSERT INTO `sys_oplog` VALUES (4143, 1, '登陆系统', '123.138.233.103', '2018-06-10 18:17:22');
INSERT INTO `sys_oplog` VALUES (4144, 1, '登陆系统', '123.138.233.103', '2018-06-10 18:18:17');
INSERT INTO `sys_oplog` VALUES (4145, 1, '登陆系统', '123.138.233.103', '2018-06-10 18:56:46');
INSERT INTO `sys_oplog` VALUES (4146, 1, '登陆系统', '123.138.233.103', '2018-06-10 19:05:09');
INSERT INTO `sys_oplog` VALUES (4147, 1, '登陆系统', '123.138.233.103', '2018-06-10 19:32:46');
INSERT INTO `sys_oplog` VALUES (4148, 1, '登陆系统', '123.138.233.103', '2018-06-10 19:48:25');
INSERT INTO `sys_oplog` VALUES (4149, 1, '登陆系统', '123.138.233.103', '2018-06-10 20:13:31');
INSERT INTO `sys_oplog` VALUES (4150, 1, '登陆系统', '123.138.233.103', '2018-06-10 20:23:33');
INSERT INTO `sys_oplog` VALUES (4151, 1, '登陆系统', '123.138.233.103', '2018-06-10 20:48:10');
INSERT INTO `sys_oplog` VALUES (4152, 1, '登陆系统', '123.138.233.103', '2018-06-10 20:52:28');
INSERT INTO `sys_oplog` VALUES (4153, 1, '登陆系统', '123.138.233.103', '2018-06-10 21:21:45');
INSERT INTO `sys_oplog` VALUES (4154, 1, '登陆系统', '123.138.233.103', '2018-06-10 22:41:45');
INSERT INTO `sys_oplog` VALUES (4155, 1, '登陆系统', '123.138.233.103', '2018-06-11 00:05:48');
INSERT INTO `sys_oplog` VALUES (4156, 1, '登陆系统', '123.138.233.103', '2018-06-11 00:06:58');
INSERT INTO `sys_oplog` VALUES (4157, 1, '登陆系统', '123.138.233.103', '2018-06-11 00:21:23');
INSERT INTO `sys_oplog` VALUES (4158, 1, '登陆系统', '123.138.233.103', '2018-06-11 00:22:17');
INSERT INTO `sys_oplog` VALUES (4159, 1, '登陆系统', '123.138.233.103', '2018-06-11 00:42:53');
INSERT INTO `sys_oplog` VALUES (4160, 1, '登陆系统', '123.138.233.103', '2018-06-11 01:02:41');
INSERT INTO `sys_oplog` VALUES (4161, 1, '登陆系统', '123.139.66.55', '2018-06-11 02:17:15');
INSERT INTO `sys_oplog` VALUES (4162, 1, '登陆系统', '123.138.233.103', '2018-06-11 07:19:44');
INSERT INTO `sys_oplog` VALUES (4163, 1, '登陆系统', '123.138.233.103', '2018-06-11 08:28:58');
INSERT INTO `sys_oplog` VALUES (4164, 1, '登陆系统', '111.18.41.204', '2018-06-11 09:03:22');
INSERT INTO `sys_oplog` VALUES (4165, 1, '登陆系统', '111.18.41.204', '2018-06-11 09:37:21');
INSERT INTO `sys_oplog` VALUES (4166, 1, '退出系统', '111.18.41.204', '2018-06-11 09:39:34');
INSERT INTO `sys_oplog` VALUES (4167, 1, '登陆系统', '111.18.41.204', '2018-06-11 09:46:49');
INSERT INTO `sys_oplog` VALUES (4168, 1, '登陆系统', '111.18.41.204', '2018-06-11 09:56:52');
INSERT INTO `sys_oplog` VALUES (4169, 1, '登陆系统', '111.18.41.204', '2018-06-11 10:07:12');
INSERT INTO `sys_oplog` VALUES (4170, 1, '登陆系统', '111.18.41.204', '2018-06-11 10:40:41');
INSERT INTO `sys_oplog` VALUES (4171, 1, '[消费记录] 导出cvs', '111.18.41.204', '2018-06-11 10:41:16');
INSERT INTO `sys_oplog` VALUES (4172, 1, '登陆系统', '113.200.106.208', '2018-06-11 10:57:22');
INSERT INTO `sys_oplog` VALUES (4173, 1, '登陆系统', '111.18.41.204', '2018-06-11 11:22:59');
INSERT INTO `sys_oplog` VALUES (4174, 1, '登陆系统', '113.200.106.208', '2018-06-11 12:11:54');
INSERT INTO `sys_oplog` VALUES (4175, 1, '登陆系统', '111.18.41.204', '2018-06-11 13:24:39');
INSERT INTO `sys_oplog` VALUES (4176, 1, '[消费记录] 导出cvs', '111.18.41.204', '2018-06-11 13:29:01');
INSERT INTO `sys_oplog` VALUES (4177, 1, '登陆系统', '113.200.107.163', '2018-06-11 14:00:49');
INSERT INTO `sys_oplog` VALUES (4178, 1, '登陆系统', '113.200.107.163', '2018-06-11 14:08:29');
INSERT INTO `sys_oplog` VALUES (4179, 1, '登陆系统', '111.18.41.204', '2018-06-11 14:23:17');
INSERT INTO `sys_oplog` VALUES (4180, 1, '登陆系统', '113.200.205.141', '2018-06-11 16:51:55');
INSERT INTO `sys_oplog` VALUES (4181, 1, '登陆系统', '111.18.41.204', '2018-06-11 17:01:25');
INSERT INTO `sys_oplog` VALUES (4182, 1, '登陆系统', '111.18.41.204', '2018-06-11 17:13:42');
INSERT INTO `sys_oplog` VALUES (4183, 1, '登陆系统', '111.18.41.204', '2018-06-11 17:21:35');
INSERT INTO `sys_oplog` VALUES (4184, 1, '登陆系统', '123.138.233.104', '2018-06-11 17:38:35');
INSERT INTO `sys_oplog` VALUES (4185, 1, '登陆系统', '123.138.233.104', '2018-06-11 17:39:48');
INSERT INTO `sys_oplog` VALUES (4186, 1, '登陆系统', '111.18.41.204', '2018-06-11 18:01:13');
INSERT INTO `sys_oplog` VALUES (4187, 1, '登陆系统', '111.18.41.204', '2018-06-11 18:14:05');
INSERT INTO `sys_oplog` VALUES (4188, 1, '登陆系统', '111.18.41.204', '2018-06-11 18:15:44');
INSERT INTO `sys_oplog` VALUES (4189, 1, '登陆系统', '111.18.41.204', '2018-06-11 18:16:48');
INSERT INTO `sys_oplog` VALUES (4190, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-11 18:18:41');
INSERT INTO `sys_oplog` VALUES (4191, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-11 18:19:47');
INSERT INTO `sys_oplog` VALUES (4192, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-11 18:20:16');
INSERT INTO `sys_oplog` VALUES (4193, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-11 18:20:56');
INSERT INTO `sys_oplog` VALUES (4194, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-11 18:21:10');
INSERT INTO `sys_oplog` VALUES (4195, 1, '[设备记录] 修改', '111.18.41.204', '2018-06-11 18:21:31');
INSERT INTO `sys_oplog` VALUES (4196, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:28:17');
INSERT INTO `sys_oplog` VALUES (4197, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:28:49');
INSERT INTO `sys_oplog` VALUES (4198, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:29:45');
INSERT INTO `sys_oplog` VALUES (4199, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:30:29');
INSERT INTO `sys_oplog` VALUES (4200, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:30:46');
INSERT INTO `sys_oplog` VALUES (4201, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:31:32');
INSERT INTO `sys_oplog` VALUES (4202, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:31:43');
INSERT INTO `sys_oplog` VALUES (4203, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:31:53');
INSERT INTO `sys_oplog` VALUES (4204, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:32:03');
INSERT INTO `sys_oplog` VALUES (4205, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:33:29');
INSERT INTO `sys_oplog` VALUES (4206, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:33:56');
INSERT INTO `sys_oplog` VALUES (4207, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:36:53');
INSERT INTO `sys_oplog` VALUES (4208, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:37:16');
INSERT INTO `sys_oplog` VALUES (4209, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:37:30');
INSERT INTO `sys_oplog` VALUES (4210, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:37:44');
INSERT INTO `sys_oplog` VALUES (4211, 1, '登陆系统', '113.200.106.133', '2018-06-11 18:38:14');
INSERT INTO `sys_oplog` VALUES (4212, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:43:36');
INSERT INTO `sys_oplog` VALUES (4213, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-11 18:44:01');
INSERT INTO `sys_oplog` VALUES (4214, 1, '登陆系统', '113.200.106.133', '2018-06-11 18:58:01');
INSERT INTO `sys_oplog` VALUES (4215, 1, '登陆系统', '113.200.106.133', '2018-06-11 19:00:15');
INSERT INTO `sys_oplog` VALUES (4216, 1, '登陆系统', '113.200.85.236', '2018-06-11 19:16:15');
INSERT INTO `sys_oplog` VALUES (4217, 1, '登陆系统', '111.18.41.210', '2018-06-11 19:27:08');
INSERT INTO `sys_oplog` VALUES (4218, 1, '登陆系统', '113.200.85.236', '2018-06-11 19:39:23');
INSERT INTO `sys_oplog` VALUES (4219, 1, '登陆系统', '111.18.41.210', '2018-06-11 20:30:52');
INSERT INTO `sys_oplog` VALUES (4220, 1, '登陆系统', '117.33.123.70', '2018-06-11 20:43:03');
INSERT INTO `sys_oplog` VALUES (4221, 1, '[在线用户] 修改', '117.33.123.70', '2018-06-11 20:43:48');
INSERT INTO `sys_oplog` VALUES (4222, 1, '[在线用户] 修改', '117.33.123.70', '2018-06-11 20:44:40');
INSERT INTO `sys_oplog` VALUES (4223, 1, '登陆系统', '123.138.233.215', '2018-06-11 20:50:06');
INSERT INTO `sys_oplog` VALUES (4224, 1, '登陆系统', '123.138.233.215', '2018-06-11 20:51:44');
INSERT INTO `sys_oplog` VALUES (4225, 1, '登陆系统', '111.18.41.210', '2018-06-11 22:58:42');
INSERT INTO `sys_oplog` VALUES (4226, 1, '登陆系统', '123.138.233.215', '2018-06-12 00:01:26');
INSERT INTO `sys_oplog` VALUES (4227, 1, '登陆系统', '123.138.233.215', '2018-06-12 00:13:18');
INSERT INTO `sys_oplog` VALUES (4228, 1, '登陆系统', '111.18.41.210', '2018-06-12 01:41:46');
INSERT INTO `sys_oplog` VALUES (4229, 1, '登陆系统', '111.18.41.210', '2018-06-12 04:53:50');
INSERT INTO `sys_oplog` VALUES (4230, 1, '登陆系统', '113.200.85.141', '2018-06-12 06:57:12');
INSERT INTO `sys_oplog` VALUES (4231, 1, '登陆系统', '111.18.41.210', '2018-06-12 09:00:00');
INSERT INTO `sys_oplog` VALUES (4232, 1, '登陆系统', '111.18.41.210', '2018-06-12 09:01:04');
INSERT INTO `sys_oplog` VALUES (4233, 1, '登陆系统', '111.18.41.210', '2018-06-12 09:14:49');
INSERT INTO `sys_oplog` VALUES (4234, 1, '登陆系统', '111.18.41.210', '2018-06-12 09:18:29');
INSERT INTO `sys_oplog` VALUES (4235, 1, '登陆系统', '113.200.85.141', '2018-06-12 09:36:53');
INSERT INTO `sys_oplog` VALUES (4236, 1, '登陆系统', '111.18.41.210', '2018-06-12 09:46:35');
INSERT INTO `sys_oplog` VALUES (4237, 1, '登陆系统', '111.18.41.210', '2018-06-12 10:41:34');
INSERT INTO `sys_oplog` VALUES (4238, 1, '登陆系统', '113.200.85.141', '2018-06-12 11:16:50');
INSERT INTO `sys_oplog` VALUES (4239, 1, '登陆系统', '113.200.85.141', '2018-06-12 11:49:40');
INSERT INTO `sys_oplog` VALUES (4240, 1, '登陆系统', '113.200.85.141', '2018-06-12 12:09:40');
INSERT INTO `sys_oplog` VALUES (4241, 1, '登陆系统', '123.138.233.215', '2018-06-12 12:45:26');
INSERT INTO `sys_oplog` VALUES (4242, 1, '登陆系统', '123.138.233.215', '2018-06-12 13:20:16');
INSERT INTO `sys_oplog` VALUES (4243, 1, '登陆系统', '111.18.41.210', '2018-06-12 14:03:32');
INSERT INTO `sys_oplog` VALUES (4244, 1, '设备290000021:356566077008365被删除', '111.18.41.210', '2018-06-12 14:09:44');
INSERT INTO `sys_oplog` VALUES (4245, 1, '[设备记录] 删除', '111.18.41.210', '2018-06-12 14:09:44');
INSERT INTO `sys_oplog` VALUES (4246, 1, '登陆系统', '111.230.148.108', '2018-06-12 14:23:38');
INSERT INTO `sys_oplog` VALUES (4247, 1, '[设备记录] 增加', '111.230.148.108', '2018-06-12 14:28:28');
INSERT INTO `sys_oplog` VALUES (4248, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-12 14:50:04');
INSERT INTO `sys_oplog` VALUES (4249, 1, '[设备记录] 修改', '111.18.41.210', '2018-06-12 15:27:01');
INSERT INTO `sys_oplog` VALUES (4250, 1, '登陆系统', '111.18.41.210', '2018-06-12 15:33:58');
INSERT INTO `sys_oplog` VALUES (4251, 1, '登陆系统', '111.18.41.210', '2018-06-12 17:29:06');
INSERT INTO `sys_oplog` VALUES (4252, 1, '登陆系统', '111.18.41.210', '2018-06-12 18:03:38');
INSERT INTO `sys_oplog` VALUES (4253, 1, '登陆系统', '113.200.85.33', '2018-06-12 19:07:36');
INSERT INTO `sys_oplog` VALUES (4254, 1, '登陆系统', '113.200.85.33', '2018-06-12 19:25:24');
INSERT INTO `sys_oplog` VALUES (4255, 1, '登陆系统', '113.200.85.33', '2018-06-12 19:25:55');
INSERT INTO `sys_oplog` VALUES (4256, 1, '登陆系统', '113.200.106.93', '2018-06-12 19:48:30');
INSERT INTO `sys_oplog` VALUES (4257, 1, '登陆系统', '1.83.236.95', '2018-06-12 20:15:21');
INSERT INTO `sys_oplog` VALUES (4258, 1, '登陆系统', '117.136.50.29', '2018-06-12 22:34:50');
INSERT INTO `sys_oplog` VALUES (4259, 1, '登陆系统', '117.136.50.29', '2018-06-12 23:35:50');
INSERT INTO `sys_oplog` VALUES (4260, 1, '登陆系统', '117.136.50.29', '2018-06-13 01:10:03');
INSERT INTO `sys_oplog` VALUES (4261, 1, '登陆系统', '117.136.50.29', '2018-06-13 02:05:05');
INSERT INTO `sys_oplog` VALUES (4262, 1, '登陆系统', '117.136.50.29', '2018-06-13 02:17:12');
INSERT INTO `sys_oplog` VALUES (4263, 1, '登陆系统', '111.18.41.201', '2018-06-13 09:46:03');
INSERT INTO `sys_oplog` VALUES (4264, 1, '登陆系统', '111.18.41.201', '2018-06-13 10:12:05');
INSERT INTO `sys_oplog` VALUES (4265, 1, '登陆系统', '111.18.41.201', '2018-06-13 10:17:50');
INSERT INTO `sys_oplog` VALUES (4266, 1, '[在线用户] 修改', '111.18.41.201', '2018-06-13 10:25:47');
INSERT INTO `sys_oplog` VALUES (4267, 1, '登陆系统', '123.149.214.106', '2018-06-13 10:32:43');
INSERT INTO `sys_oplog` VALUES (4268, 1, '登陆系统', '111.18.41.201', '2018-06-13 10:40:39');
INSERT INTO `sys_oplog` VALUES (4269, 1, '[设备记录] 增加', '123.149.214.106', '2018-06-13 11:12:14');
INSERT INTO `sys_oplog` VALUES (4270, 1, '[设备记录] 修改', '123.149.214.106', '2018-06-13 11:12:23');
INSERT INTO `sys_oplog` VALUES (4271, 1, '[设备记录] 修改', '111.18.41.201', '2018-06-13 11:49:05');
INSERT INTO `sys_oplog` VALUES (4272, 1, '[在线用户] 修改', '111.18.41.201', '2018-06-13 11:52:45');
INSERT INTO `sys_oplog` VALUES (4273, 1, '[在线用户] 修改', '111.18.41.201', '2018-06-13 11:52:57');
INSERT INTO `sys_oplog` VALUES (4274, 1, '[设备记录] 修改', '111.18.41.201', '2018-06-13 11:54:19');
INSERT INTO `sys_oplog` VALUES (4275, 1, '登陆系统', '117.136.25.185', '2018-06-13 12:29:06');
INSERT INTO `sys_oplog` VALUES (4276, 1, '登陆系统', '111.18.41.201', '2018-06-13 13:49:12');
INSERT INTO `sys_oplog` VALUES (4277, 1, '登陆系统', '111.18.41.201', '2018-06-13 14:07:42');
INSERT INTO `sys_oplog` VALUES (4278, 1, '登陆系统', '111.18.41.201', '2018-06-13 14:38:35');
INSERT INTO `sys_oplog` VALUES (4279, 1, '登陆系统', '123.138.233.123', '2018-06-13 14:50:41');
INSERT INTO `sys_oplog` VALUES (4280, 1, '登陆系统', '123.149.214.106', '2018-06-13 16:05:42');
INSERT INTO `sys_oplog` VALUES (4281, 1, '登陆系统', '113.133.203.160', '2018-06-13 16:57:53');
INSERT INTO `sys_oplog` VALUES (4282, 1, '登陆系统', '123.139.19.34', '2018-06-13 17:21:25');
INSERT INTO `sys_oplog` VALUES (4283, 1, '登陆系统', '111.18.41.201', '2018-06-13 17:42:39');
INSERT INTO `sys_oplog` VALUES (4284, 1, '登陆系统', '123.139.19.34', '2018-06-13 18:52:33');
INSERT INTO `sys_oplog` VALUES (4285, 1, '[角色管理] 增加', '111.18.41.223', '2018-06-13 20:50:03');
INSERT INTO `sys_oplog` VALUES (4286, 1, '[权限管理] 修改', '111.18.41.223', '2018-06-13 20:51:15');
INSERT INTO `sys_oplog` VALUES (4287, 1, '[用户管理] 增加', '111.18.41.223', '2018-06-13 20:53:17');
INSERT INTO `sys_oplog` VALUES (4288, 1, '退出系统', '111.18.41.223', '2018-06-13 20:53:25');
INSERT INTO `sys_oplog` VALUES (4289, 16, '登陆系统', '111.18.41.223', '2018-06-13 20:53:53');
INSERT INTO `sys_oplog` VALUES (4290, 16, '退出系统', '111.18.41.223', '2018-06-13 20:56:28');
INSERT INTO `sys_oplog` VALUES (4291, 1, '登陆系统', '111.18.41.223', '2018-06-13 20:56:35');
INSERT INTO `sys_oplog` VALUES (4292, 1, '[权限管理] 修改', '111.18.41.223', '2018-06-13 20:57:36');
INSERT INTO `sys_oplog` VALUES (4293, 1, '[权限管理] 修改', '111.18.41.223', '2018-06-13 20:57:56');
INSERT INTO `sys_oplog` VALUES (4294, 1, '[用户管理] 删除', '111.18.41.223', '2018-06-13 20:58:09');
INSERT INTO `sys_oplog` VALUES (4295, 1, '[用户管理] 增加', '111.18.41.223', '2018-06-13 20:58:32');
INSERT INTO `sys_oplog` VALUES (4296, 1, '退出系统', '111.18.41.223', '2018-06-13 20:58:33');
INSERT INTO `sys_oplog` VALUES (4297, 17, '登陆系统', '111.18.41.223', '2018-06-13 20:58:51');
INSERT INTO `sys_oplog` VALUES (4298, 17, '退出系统', '111.18.41.223', '2018-06-13 20:59:19');
INSERT INTO `sys_oplog` VALUES (4299, 1, '登陆系统', '111.18.41.223', '2018-06-13 20:59:28');
INSERT INTO `sys_oplog` VALUES (4300, 1, '登陆系统', '117.136.25.185', '2018-06-13 21:49:35');
INSERT INTO `sys_oplog` VALUES (4301, 1, '登陆系统', '117.136.25.185', '2018-06-13 22:36:11');
INSERT INTO `sys_oplog` VALUES (4302, 1, '登陆系统', '117.136.25.185', '2018-06-13 22:40:23');
INSERT INTO `sys_oplog` VALUES (4303, 1, '登陆系统', '123.139.105.157', '2018-06-14 00:55:03');
INSERT INTO `sys_oplog` VALUES (4304, 1, '登陆系统', '123.139.105.157', '2018-06-14 00:56:20');
INSERT INTO `sys_oplog` VALUES (4305, 1, '登陆系统', '123.139.105.157', '2018-06-14 09:03:12');
INSERT INTO `sys_oplog` VALUES (4306, 1, '登陆系统', '111.18.41.223', '2018-06-14 09:06:47');
INSERT INTO `sys_oplog` VALUES (4307, 1, '登陆系统', '111.18.41.223', '2018-06-14 09:10:42');
INSERT INTO `sys_oplog` VALUES (4308, 1, '[消费记录] 导出cvs', '111.18.41.223', '2018-06-14 09:23:17');
INSERT INTO `sys_oplog` VALUES (4309, 1, '[消费记录] 导出cvs', '111.18.41.223', '2018-06-14 09:24:20');
INSERT INTO `sys_oplog` VALUES (4310, 1, '[微信充值记录] 导出cvs', '111.18.41.223', '2018-06-14 09:27:20');
INSERT INTO `sys_oplog` VALUES (4311, 1, '[微信充值记录] 导出cvs', '111.18.41.223', '2018-06-14 09:29:54');
INSERT INTO `sys_oplog` VALUES (4312, 1, '登陆系统', '117.136.25.185', '2018-06-14 10:14:42');
INSERT INTO `sys_oplog` VALUES (4313, 1, '登陆系统', '111.18.41.223', '2018-06-14 13:21:21');
INSERT INTO `sys_oplog` VALUES (4314, 1, '登陆系统', '111.18.41.223', '2018-06-14 13:22:33');
INSERT INTO `sys_oplog` VALUES (4315, 1, '退出系统', '111.18.41.223', '2018-06-14 13:41:43');
INSERT INTO `sys_oplog` VALUES (4316, 1, '登陆系统', '111.18.41.223', '2018-06-14 13:41:50');
INSERT INTO `sys_oplog` VALUES (4317, 1, '登陆系统', '111.18.41.223', '2018-06-14 13:57:01');
INSERT INTO `sys_oplog` VALUES (4318, 1, '登陆系统', '113.200.107.204', '2018-06-14 16:13:25');
INSERT INTO `sys_oplog` VALUES (4319, 1, '登陆系统', '111.18.41.223', '2018-06-14 16:21:53');
INSERT INTO `sys_oplog` VALUES (4320, 1, '登陆系统', '111.18.41.223', '2018-06-14 16:24:22');
INSERT INTO `sys_oplog` VALUES (4321, 1, '[消费记录] 导出cvs', '111.18.41.234', '2018-06-14 19:02:13');
INSERT INTO `sys_oplog` VALUES (4322, 1, '登陆系统', '111.18.41.234', '2018-06-14 19:14:32');
INSERT INTO `sys_oplog` VALUES (4323, 1, '登陆系统', '36.47.17.106', '2018-06-14 22:50:05');
INSERT INTO `sys_oplog` VALUES (4324, 1, '登陆系统', '113.200.106.56', '2018-06-15 00:26:32');
INSERT INTO `sys_oplog` VALUES (4325, 1, '登陆系统', '113.200.106.56', '2018-06-15 01:00:05');
INSERT INTO `sys_oplog` VALUES (4326, 1, '登陆系统', '123.139.105.157', '2018-06-15 01:10:29');
INSERT INTO `sys_oplog` VALUES (4327, 1, '登陆系统', '113.200.106.51', '2018-06-15 01:13:01');
INSERT INTO `sys_oplog` VALUES (4328, 1, '登陆系统', '123.139.35.103', '2018-06-15 06:54:58');
INSERT INTO `sys_oplog` VALUES (4329, 1, '登陆系统', '111.18.41.234', '2018-06-15 07:54:54');
INSERT INTO `sys_oplog` VALUES (4330, 1, '[在线用户] 导出cvs', '113.200.106.51', '2018-06-15 08:32:30');
INSERT INTO `sys_oplog` VALUES (4331, 1, '登陆系统', '111.18.41.234', '2018-06-15 08:55:19');
INSERT INTO `sys_oplog` VALUES (4332, 1, '[在线用户] 修改', '111.18.41.234', '2018-06-15 09:34:39');
INSERT INTO `sys_oplog` VALUES (4333, 1, '登陆系统', '123.138.232.45', '2018-06-15 09:38:08');
INSERT INTO `sys_oplog` VALUES (4334, 1, '[在线用户] 修改', '111.18.41.234', '2018-06-15 09:40:43');
INSERT INTO `sys_oplog` VALUES (4335, 1, '[设备记录] 修改', '111.18.41.234', '2018-06-15 09:51:21');
INSERT INTO `sys_oplog` VALUES (4336, 1, '登陆系统', '111.18.41.234', '2018-06-15 10:28:54');
INSERT INTO `sys_oplog` VALUES (4337, 1, '登陆系统', '111.18.41.234', '2018-06-15 10:47:09');
INSERT INTO `sys_oplog` VALUES (4338, 1, '登陆系统', '123.149.209.180', '2018-06-15 11:11:48');
INSERT INTO `sys_oplog` VALUES (4339, 1, '登陆系统', '111.18.41.234', '2018-06-15 12:03:37');
INSERT INTO `sys_oplog` VALUES (4340, 1, '登陆系统', '111.18.41.234', '2018-06-15 12:47:15');
INSERT INTO `sys_oplog` VALUES (4341, 1, '登陆系统', '113.200.107.25', '2018-06-15 13:01:29');
INSERT INTO `sys_oplog` VALUES (4342, 1, '登陆系统', '111.18.41.234', '2018-06-15 13:02:57');
INSERT INTO `sys_oplog` VALUES (4343, 1, '登陆系统', '111.18.41.234', '2018-06-15 13:03:53');
INSERT INTO `sys_oplog` VALUES (4344, 1, '登陆系统', '123.149.209.180', '2018-06-15 13:47:55');
INSERT INTO `sys_oplog` VALUES (4345, 1, '登陆系统', '111.18.41.234', '2018-06-15 13:48:33');
INSERT INTO `sys_oplog` VALUES (4346, 1, '登陆系统', '111.18.41.234', '2018-06-15 15:28:53');
INSERT INTO `sys_oplog` VALUES (4347, 1, '登陆系统', '111.18.41.234', '2018-06-15 16:35:21');
INSERT INTO `sys_oplog` VALUES (4348, 1, '登陆系统', '124.89.116.4', '2018-06-15 17:19:26');
INSERT INTO `sys_oplog` VALUES (4349, 1, '登陆系统', '124.89.116.4', '2018-06-15 17:22:43');
INSERT INTO `sys_oplog` VALUES (4350, 1, '登陆系统', '36.46.20.88', '2018-06-15 18:23:40');
INSERT INTO `sys_oplog` VALUES (4351, 1, '登陆系统', '36.46.20.88', '2018-06-15 18:24:09');
INSERT INTO `sys_oplog` VALUES (4352, 1, '登陆系统', '123.138.232.61', '2018-06-15 18:42:00');
INSERT INTO `sys_oplog` VALUES (4353, 1, '登陆系统', '111.18.41.240', '2018-06-15 19:06:35');
INSERT INTO `sys_oplog` VALUES (4354, 1, '登陆系统', '36.47.17.106', '2018-06-15 19:21:54');
INSERT INTO `sys_oplog` VALUES (4355, 1, '登陆系统', '123.138.232.61', '2018-06-15 20:58:30');
INSERT INTO `sys_oplog` VALUES (4356, 1, '登陆系统', '123.138.232.61', '2018-06-15 21:23:16');
INSERT INTO `sys_oplog` VALUES (4357, 1, '登陆系统', '123.138.232.61', '2018-06-15 21:46:59');
INSERT INTO `sys_oplog` VALUES (4358, 1, '登陆系统', '36.47.17.106', '2018-06-15 22:59:25');
INSERT INTO `sys_oplog` VALUES (4359, 1, '登陆系统', '123.138.232.61', '2018-06-15 23:11:47');
INSERT INTO `sys_oplog` VALUES (4360, 1, '登陆系统', '123.138.232.61', '2018-06-15 23:34:12');
INSERT INTO `sys_oplog` VALUES (4361, 1, '登陆系统', '123.139.112.134', '2018-06-16 01:04:45');
INSERT INTO `sys_oplog` VALUES (4362, 1, '登陆系统', '123.139.112.134', '2018-06-16 11:08:49');
INSERT INTO `sys_oplog` VALUES (4363, 1, '登陆系统', '123.139.112.134', '2018-06-16 11:23:48');
INSERT INTO `sys_oplog` VALUES (4364, 1, '登陆系统', '113.200.107.159', '2018-06-16 11:51:37');
INSERT INTO `sys_oplog` VALUES (4365, 1, '登陆系统', '113.200.85.167', '2018-06-16 12:39:02');
INSERT INTO `sys_oplog` VALUES (4366, 1, '登陆系统', '113.200.205.26', '2018-06-16 13:46:31');
INSERT INTO `sys_oplog` VALUES (4367, 1, '登陆系统', '111.18.41.240', '2018-06-16 15:23:48');
INSERT INTO `sys_oplog` VALUES (4368, 1, '登陆系统', '111.18.41.240', '2018-06-16 16:37:31');
INSERT INTO `sys_oplog` VALUES (4369, 1, '登陆系统', '111.18.41.240', '2018-06-16 16:55:22');
INSERT INTO `sys_oplog` VALUES (4370, 1, '登陆系统', '111.18.41.240', '2018-06-16 16:55:59');
INSERT INTO `sys_oplog` VALUES (4371, 1, '登陆系统', '111.18.41.240', '2018-06-16 16:56:17');
INSERT INTO `sys_oplog` VALUES (4372, 1, '登陆系统', '111.18.41.240', '2018-06-16 16:56:30');
INSERT INTO `sys_oplog` VALUES (4373, 1, '登陆系统', '111.18.41.221', '2018-06-16 19:31:08');
INSERT INTO `sys_oplog` VALUES (4374, 1, '登陆系统', '113.200.205.26', '2018-06-16 21:19:50');
INSERT INTO `sys_oplog` VALUES (4375, 1, '登陆系统', '113.200.205.26', '2018-06-16 21:48:25');
INSERT INTO `sys_oplog` VALUES (4376, 1, '登陆系统', '1.80.232.113', '2018-06-16 22:39:15');
INSERT INTO `sys_oplog` VALUES (4377, 1, '登陆系统', '113.200.205.26', '2018-06-16 23:10:02');
INSERT INTO `sys_oplog` VALUES (4378, 1, '登陆系统', '113.200.205.26', '2018-06-17 00:23:36');
INSERT INTO `sys_oplog` VALUES (4379, 1, '登陆系统', '113.200.205.26', '2018-06-17 01:19:20');
INSERT INTO `sys_oplog` VALUES (4380, 1, '登陆系统', '113.200.106.15', '2018-06-17 09:19:32');
INSERT INTO `sys_oplog` VALUES (4381, 1, '登陆系统', '1.80.232.113', '2018-06-17 09:32:17');
INSERT INTO `sys_oplog` VALUES (4382, 1, '登陆系统', '123.139.112.134', '2018-06-17 09:59:04');
INSERT INTO `sys_oplog` VALUES (4383, 1, '登陆系统', '123.139.112.134', '2018-06-17 10:36:25');
INSERT INTO `sys_oplog` VALUES (4384, 1, '登陆系统', '123.139.112.134', '2018-06-17 10:37:50');
INSERT INTO `sys_oplog` VALUES (4385, 1, '登陆系统', '123.139.112.134', '2018-06-17 11:00:23');
INSERT INTO `sys_oplog` VALUES (4386, 1, '登陆系统', '123.139.112.134', '2018-06-17 11:29:43');
INSERT INTO `sys_oplog` VALUES (4387, 1, '登陆系统', '123.139.112.134', '2018-06-17 13:19:18');
INSERT INTO `sys_oplog` VALUES (4388, 1, '登陆系统', '123.139.112.134', '2018-06-17 14:10:32');
INSERT INTO `sys_oplog` VALUES (4389, 1, '登陆系统', '123.139.112.134', '2018-06-17 15:27:21');
INSERT INTO `sys_oplog` VALUES (4390, 1, '登陆系统', '123.139.112.134', '2018-06-17 15:40:19');
INSERT INTO `sys_oplog` VALUES (4391, 1, '登陆系统', '123.139.112.134', '2018-06-17 15:54:30');
INSERT INTO `sys_oplog` VALUES (4392, 1, '登陆系统', '117.33.120.106', '2018-06-17 16:13:00');
INSERT INTO `sys_oplog` VALUES (4393, 1, '[设备记录] 修改', '117.33.120.106', '2018-06-17 16:16:26');
INSERT INTO `sys_oplog` VALUES (4394, 1, '登陆系统', '123.139.112.134', '2018-06-17 16:19:27');
INSERT INTO `sys_oplog` VALUES (4395, 1, '[设备记录] 修改', '1.81.67.19', '2018-06-17 16:30:32');
INSERT INTO `sys_oplog` VALUES (4396, 1, '[设备记录] 修改', '1.81.67.19', '2018-06-17 16:32:07');
INSERT INTO `sys_oplog` VALUES (4397, 1, '登陆系统', '113.200.205.26', '2018-06-17 17:42:48');
INSERT INTO `sys_oplog` VALUES (4398, 1, '登陆系统', '113.200.205.26', '2018-06-17 18:45:21');
INSERT INTO `sys_oplog` VALUES (4399, 1, '登陆系统', '113.200.205.26', '2018-06-17 18:47:00');
INSERT INTO `sys_oplog` VALUES (4400, 1, '登陆系统', '113.200.205.26', '2018-06-17 20:50:06');
INSERT INTO `sys_oplog` VALUES (4401, 1, '登陆系统', '113.200.205.26', '2018-06-17 23:57:15');
INSERT INTO `sys_oplog` VALUES (4402, 1, '登陆系统', '113.200.205.26', '2018-06-17 23:58:44');
INSERT INTO `sys_oplog` VALUES (4403, 1, '登陆系统', '113.143.142.125', '2018-06-18 06:51:06');
INSERT INTO `sys_oplog` VALUES (4404, 1, '登陆系统', '111.18.41.200', '2018-06-18 07:52:30');
INSERT INTO `sys_oplog` VALUES (4405, 1, '[在线用户] 修改', '111.18.41.200', '2018-06-18 08:31:01');
INSERT INTO `sys_oplog` VALUES (4406, 1, '登陆系统', '111.18.41.200', '2018-06-18 08:57:31');
INSERT INTO `sys_oplog` VALUES (4407, 1, '登陆系统', '36.47.19.234', '2018-06-18 09:34:46');
INSERT INTO `sys_oplog` VALUES (4408, 1, '[在线用户] 修改', '111.18.41.200', '2018-06-18 12:04:12');
INSERT INTO `sys_oplog` VALUES (4409, 1, '[在线用户] 修改', '111.18.41.200', '2018-06-18 12:11:06');
INSERT INTO `sys_oplog` VALUES (4410, 1, '登陆系统', '111.18.41.200', '2018-06-18 12:55:40');
INSERT INTO `sys_oplog` VALUES (4411, 1, '登陆系统', '111.18.41.200', '2018-06-18 13:02:52');
INSERT INTO `sys_oplog` VALUES (4412, 1, '登陆系统', '111.18.41.200', '2018-06-18 14:21:02');
INSERT INTO `sys_oplog` VALUES (4413, 1, '登陆系统', '124.89.79.37', '2018-06-18 16:05:47');
INSERT INTO `sys_oplog` VALUES (4414, 1, '登陆系统', '36.47.19.234', '2018-06-18 16:58:49');
INSERT INTO `sys_oplog` VALUES (4415, 1, '登陆系统', '36.47.19.234', '2018-06-18 22:15:25');
INSERT INTO `sys_oplog` VALUES (4416, 1, '登陆系统', '117.136.50.201', '2018-06-18 22:16:29');
INSERT INTO `sys_oplog` VALUES (4417, 1, '登陆系统', '117.136.50.201', '2018-06-18 22:32:13');
INSERT INTO `sys_oplog` VALUES (4418, 1, '登陆系统', '123.139.114.21', '2018-06-18 22:46:19');
INSERT INTO `sys_oplog` VALUES (4419, 1, '登陆系统', '123.139.114.21', '2018-06-18 23:26:50');
INSERT INTO `sys_oplog` VALUES (4420, 1, '登陆系统', '123.139.114.21', '2018-06-19 09:08:42');
INSERT INTO `sys_oplog` VALUES (4421, 1, '登陆系统', '111.18.41.194', '2018-06-19 09:24:33');
INSERT INTO `sys_oplog` VALUES (4422, 1, '登陆系统', '111.18.41.194', '2018-06-19 09:48:24');
INSERT INTO `sys_oplog` VALUES (4423, 1, '登陆系统', '111.18.41.194', '2018-06-19 10:02:10');
INSERT INTO `sys_oplog` VALUES (4424, 1, '登陆系统', '111.18.41.194', '2018-06-19 10:39:32');
INSERT INTO `sys_oplog` VALUES (4425, 1, '登陆系统', '123.139.114.21', '2018-06-19 11:49:25');
INSERT INTO `sys_oplog` VALUES (4426, 1, '登陆系统', '111.18.41.194', '2018-06-19 12:14:33');
INSERT INTO `sys_oplog` VALUES (4427, 1, '登陆系统', '123.139.114.21', '2018-06-19 13:02:44');
INSERT INTO `sys_oplog` VALUES (4428, 15, '退出系统', '111.18.41.232', '2018-06-21 17:16:46');
INSERT INTO `sys_oplog` VALUES (4429, 1, '登陆系统', '111.18.41.232', '2018-06-21 17:16:52');
INSERT INTO `sys_oplog` VALUES (4430, 1, '[角色管理] 增加', '111.18.41.232', '2018-06-21 17:17:52');
INSERT INTO `sys_oplog` VALUES (4431, 1, '[权限管理] 修改', '111.18.41.232', '2018-06-21 17:18:22');
INSERT INTO `sys_oplog` VALUES (4432, 1, '[用户管理] 增加', '111.18.41.232', '2018-06-21 17:18:54');
INSERT INTO `sys_oplog` VALUES (4433, 1, '退出系统', '111.18.41.232', '2018-06-21 17:18:57');
INSERT INTO `sys_oplog` VALUES (4434, 18, '登陆系统', '111.18.41.232', '2018-06-21 17:19:07');
INSERT INTO `sys_oplog` VALUES (4435, 18, '退出系统', '111.18.41.232', '2018-06-21 17:21:07');
INSERT INTO `sys_oplog` VALUES (4436, 1, '登陆系统', '111.18.41.232', '2018-06-21 17:21:12');
INSERT INTO `sys_oplog` VALUES (4437, 1, '[用户管理] 修改', '111.18.41.232', '2018-06-21 17:22:38');
INSERT INTO `sys_oplog` VALUES (4438, 1, '退出系统', '111.18.41.232', '2018-06-21 17:25:29');
INSERT INTO `sys_oplog` VALUES (4439, 18, '登陆系统', '111.18.41.232', '2018-06-21 17:25:39');
INSERT INTO `sys_oplog` VALUES (4440, 18, '退出系统', '111.18.41.232', '2018-06-21 17:36:38');
INSERT INTO `sys_oplog` VALUES (4441, 1, '登陆系统', '111.18.41.232', '2018-06-21 17:36:45');
INSERT INTO `sys_oplog` VALUES (4442, 1, '[优惠设置] 修改', '111.18.41.232', '2018-06-21 17:40:15');
INSERT INTO `sys_oplog` VALUES (4443, 1, '[优惠设置] 修改', '111.18.41.232', '2018-06-21 17:40:37');
INSERT INTO `sys_oplog` VALUES (4444, 1, '[优惠设置] 修改', '111.18.41.232', '2018-06-21 17:40:46');
INSERT INTO `sys_oplog` VALUES (4445, 1, '[优惠设置] 修改', '111.18.41.232', '2018-06-21 17:41:46');
INSERT INTO `sys_oplog` VALUES (4446, 1, '[优惠设置] 修改', '111.18.41.232', '2018-06-21 17:42:52');
INSERT INTO `sys_oplog` VALUES (4447, 1, '[优惠设置] 修改', '111.18.41.232', '2018-06-21 17:46:36');
INSERT INTO `sys_oplog` VALUES (4448, 1, '[优惠设置] 修改', '111.18.41.232', '2018-06-21 17:46:42');
INSERT INTO `sys_oplog` VALUES (4449, 1, '登陆系统', '61.150.12.160', '2018-06-21 18:07:49');
INSERT INTO `sys_oplog` VALUES (4450, 1, '登陆系统', '111.18.41.219', '2018-06-21 18:28:15');
INSERT INTO `sys_oplog` VALUES (4451, 1, '退出系统', '111.18.41.219', '2018-06-21 19:20:24');
INSERT INTO `sys_oplog` VALUES (4452, 18, '登陆系统', '111.18.41.219', '2018-06-21 19:21:25');
INSERT INTO `sys_oplog` VALUES (4453, 1, '登陆系统', '111.18.41.219', '2018-06-21 19:24:00');
INSERT INTO `sys_oplog` VALUES (4454, 1, '[权限管理] 修改', '111.18.41.219', '2018-06-21 19:24:27');
INSERT INTO `sys_oplog` VALUES (4455, 18, '退出系统', '111.18.41.219', '2018-06-21 19:25:10');
INSERT INTO `sys_oplog` VALUES (4456, 18, '登陆系统', '111.18.41.219', '2018-06-21 19:25:27');
INSERT INTO `sys_oplog` VALUES (4457, 1, '登陆系统', '111.18.41.219', '2018-06-21 21:37:26');
INSERT INTO `sys_oplog` VALUES (4458, 1, '登陆系统', '111.18.41.219', '2018-06-22 09:05:09');
INSERT INTO `sys_oplog` VALUES (4459, 1, '登陆系统', '111.18.41.219', '2018-06-22 09:18:01');
INSERT INTO `sys_oplog` VALUES (4460, 1, '登陆系统', '111.18.41.219', '2018-06-22 11:21:27');
INSERT INTO `sys_oplog` VALUES (4461, 1, '[优惠设置] 修改', '111.18.41.219', '2018-06-22 11:36:01');
INSERT INTO `sys_oplog` VALUES (4462, 1, '[优惠设置] 修改', '111.18.41.219', '2018-06-22 11:48:50');
INSERT INTO `sys_oplog` VALUES (4463, 1, '[优惠设置] 修改', '111.18.41.219', '2018-06-22 11:49:09');
INSERT INTO `sys_oplog` VALUES (4464, 1, '登陆系统', '111.18.41.219', '2018-06-22 12:11:39');
INSERT INTO `sys_oplog` VALUES (4465, 1, '[优惠设置] 修改', '111.18.41.219', '2018-06-22 12:33:32');
INSERT INTO `sys_oplog` VALUES (4466, 1, '登陆系统', '113.200.107.49', '2018-06-22 13:15:16');
INSERT INTO `sys_oplog` VALUES (4467, 1, '[在线用户] 修改', '111.18.41.219', '2018-06-22 14:37:06');
INSERT INTO `sys_oplog` VALUES (4468, 1, '登陆系统', '111.18.41.219', '2018-06-22 17:37:27');
INSERT INTO `sys_oplog` VALUES (4469, 1, '登陆系统', '117.33.113.133', '2018-06-22 19:37:22');
INSERT INTO `sys_oplog` VALUES (4470, 1, '登陆系统', '1.81.85.96', '2018-06-22 19:42:43');
INSERT INTO `sys_oplog` VALUES (4471, 1, '登陆系统', '111.18.41.240', '2018-06-22 19:55:57');
INSERT INTO `sys_oplog` VALUES (4472, 1, '登陆系统', '111.18.41.240', '2018-06-22 21:14:06');
INSERT INTO `sys_oplog` VALUES (4473, 1, '登陆系统', '113.200.205.178', '2018-06-22 22:18:48');
INSERT INTO `sys_oplog` VALUES (4474, 1, '登陆系统', '113.200.107.172', '2018-06-22 23:32:09');
INSERT INTO `sys_oplog` VALUES (4475, 1, '登陆系统', '113.200.107.172', '2018-06-22 23:34:16');
INSERT INTO `sys_oplog` VALUES (4476, 1, '登陆系统', '123.139.112.173', '2018-06-22 23:40:26');
INSERT INTO `sys_oplog` VALUES (4477, 1, '登陆系统', '113.201.56.46', '2018-06-23 06:52:23');
INSERT INTO `sys_oplog` VALUES (4478, 1, '登陆系统', '123.139.112.173', '2018-06-23 07:29:27');
INSERT INTO `sys_oplog` VALUES (4479, 1, '登陆系统', '123.139.112.173', '2018-06-23 07:31:21');
INSERT INTO `sys_oplog` VALUES (4480, 1, '登陆系统', '113.143.164.88', '2018-06-23 07:44:57');
INSERT INTO `sys_oplog` VALUES (4481, 1, '[在线用户] 修改', '113.143.164.88', '2018-06-23 08:00:34');
INSERT INTO `sys_oplog` VALUES (4482, 1, '登陆系统', '113.200.106.185', '2018-06-23 11:48:41');
INSERT INTO `sys_oplog` VALUES (4483, 1, '登陆系统', '113.200.106.185', '2018-06-23 12:23:05');
INSERT INTO `sys_oplog` VALUES (4484, 1, '登陆系统', '113.200.106.185', '2018-06-23 12:58:13');
INSERT INTO `sys_oplog` VALUES (4485, 1, '登陆系统', '123.139.21.63', '2018-06-23 13:31:36');
INSERT INTO `sys_oplog` VALUES (4486, 1, '登陆系统', '36.46.16.81', '2018-06-23 13:38:28');
INSERT INTO `sys_oplog` VALUES (4487, 1, '[设备记录] 修改', '36.46.16.81', '2018-06-23 13:39:11');
INSERT INTO `sys_oplog` VALUES (4488, 1, '[设备记录] 修改', '36.46.16.81', '2018-06-23 13:40:24');
INSERT INTO `sys_oplog` VALUES (4489, 1, '登陆系统', '113.200.85.123', '2018-06-23 14:03:00');
INSERT INTO `sys_oplog` VALUES (4490, 1, '登陆系统', '36.46.16.81', '2018-06-23 14:05:23');
INSERT INTO `sys_oplog` VALUES (4491, 1, '登陆系统', '111.18.41.231', '2018-06-23 18:38:47');
INSERT INTO `sys_oplog` VALUES (4492, 1, '登陆系统', '113.200.107.33', '2018-06-23 19:45:24');
INSERT INTO `sys_oplog` VALUES (4493, 1, '登陆系统', '123.139.112.173', '2018-06-23 20:13:24');
INSERT INTO `sys_oplog` VALUES (4494, 1, '登陆系统', '123.139.112.173', '2018-06-23 20:14:15');
INSERT INTO `sys_oplog` VALUES (4495, 1, '登陆系统', '123.139.112.173', '2018-06-23 20:41:55');
INSERT INTO `sys_oplog` VALUES (4496, 1, '登陆系统', '36.46.16.81', '2018-06-23 20:45:18');
INSERT INTO `sys_oplog` VALUES (4497, 1, '登陆系统', '123.139.112.173', '2018-06-23 21:09:54');
INSERT INTO `sys_oplog` VALUES (4498, 1, '登陆系统', '123.139.112.173', '2018-06-23 21:39:04');
INSERT INTO `sys_oplog` VALUES (4499, 1, '登陆系统', '123.139.112.173', '2018-06-23 21:53:40');
INSERT INTO `sys_oplog` VALUES (4500, 1, '登陆系统', '123.139.112.173', '2018-06-23 21:57:55');
INSERT INTO `sys_oplog` VALUES (4501, 1, '登陆系统', '123.139.112.173', '2018-06-23 22:23:19');
INSERT INTO `sys_oplog` VALUES (4502, 1, '登陆系统', '123.139.112.173', '2018-06-23 22:35:39');
INSERT INTO `sys_oplog` VALUES (4503, 1, '登陆系统', '123.139.112.173', '2018-06-23 22:55:53');
INSERT INTO `sys_oplog` VALUES (4504, 1, '登陆系统', '123.139.112.173', '2018-06-24 07:49:19');
INSERT INTO `sys_oplog` VALUES (4505, 1, '登陆系统', '123.139.112.173', '2018-06-24 08:05:44');
INSERT INTO `sys_oplog` VALUES (4506, 1, '登陆系统', '113.143.143.186', '2018-06-24 08:14:20');
INSERT INTO `sys_oplog` VALUES (4507, 1, '登陆系统', '113.143.143.186', '2018-06-24 08:51:10');
INSERT INTO `sys_oplog` VALUES (4508, 1, '登陆系统', '123.139.25.188', '2018-06-24 09:54:25');
INSERT INTO `sys_oplog` VALUES (4509, 1, '登陆系统', '123.138.233.221', '2018-06-24 09:56:52');
INSERT INTO `sys_oplog` VALUES (4510, 1, '登陆系统', '36.46.16.81', '2018-06-24 15:13:04');
INSERT INTO `sys_oplog` VALUES (4511, 1, '[设备记录] 修改', '36.46.16.81', '2018-06-24 15:14:00');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '系统管理员', '2016-02-04 21:05:45');
INSERT INTO `sys_role` VALUES (9, '开卡专用', '2017-04-12 21:15:44');
INSERT INTO `sys_role` VALUES (10, '客服', '2018-06-13 20:50:02');
INSERT INTO `sys_role` VALUES (11, '代理', '2018-06-21 17:17:51');

-- ----------------------------
-- Table structure for sys_role_btn
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_btn`;
CREATE TABLE `sys_role_btn`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `btn_id` int(11) DEFAULT NULL COMMENT '按钮id',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3455 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_btn
-- ----------------------------
INSERT INTO `sys_role_btn` VALUES (249, 2, 10, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (250, 2, 9, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (251, 2, 8, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (252, 2, 7, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (253, 2, 14, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (254, 2, 13, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (255, 2, 12, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (256, 2, 11, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (257, 2, 6, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (258, 2, 5, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (259, 2, 4, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (260, 2, 3, '2017-01-25 19:04:21');
INSERT INTO `sys_role_btn` VALUES (338, 6, 33, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (339, 6, 32, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (340, 6, 31, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (341, 6, 30, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (342, 6, 42, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (343, 6, 37, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (344, 6, 36, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (345, 6, 35, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (346, 6, 34, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (347, 6, 41, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (348, 6, 40, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (349, 6, 39, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (350, 6, 38, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (351, 6, 29, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (352, 6, 28, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (353, 6, 27, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (354, 6, 26, '2017-01-25 20:33:40');
INSERT INTO `sys_role_btn` VALUES (499, 5, 33, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (500, 5, 32, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (501, 5, 31, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (502, 5, 30, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (503, 5, 43, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (504, 5, 42, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (505, 5, 37, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (506, 5, 36, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (507, 5, 35, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (508, 5, 34, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (509, 5, 47, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (510, 5, 46, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (511, 5, 45, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (512, 5, 44, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (513, 5, 51, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (514, 5, 50, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (515, 5, 49, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (516, 5, 48, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (517, 5, 55, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (518, 5, 54, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (519, 5, 53, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (520, 5, 52, '2017-04-04 17:20:24');
INSERT INTO `sys_role_btn` VALUES (533, 7, 56, '2017-04-04 19:24:41');
INSERT INTO `sys_role_btn` VALUES (534, 7, 29, '2017-04-04 19:24:41');
INSERT INTO `sys_role_btn` VALUES (535, 7, 28, '2017-04-04 19:24:41');
INSERT INTO `sys_role_btn` VALUES (536, 7, 27, '2017-04-04 19:24:41');
INSERT INTO `sys_role_btn` VALUES (537, 7, 26, '2017-04-04 19:24:41');
INSERT INTO `sys_role_btn` VALUES (538, 7, 47, '2017-04-04 19:24:41');
INSERT INTO `sys_role_btn` VALUES (539, 7, 46, '2017-04-04 19:24:41');
INSERT INTO `sys_role_btn` VALUES (540, 7, 45, '2017-04-04 19:24:41');
INSERT INTO `sys_role_btn` VALUES (541, 7, 44, '2017-04-04 19:24:41');
INSERT INTO `sys_role_btn` VALUES (1013, 8, 33, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1014, 8, 32, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1015, 8, 31, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1016, 8, 30, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1017, 8, 29, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1018, 8, 28, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1019, 8, 27, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1020, 8, 26, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1021, 8, 47, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1022, 8, 46, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1023, 8, 45, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1024, 8, 44, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1025, 8, 51, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1026, 8, 50, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1027, 8, 49, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1028, 8, 48, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1029, 8, 55, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1030, 8, 54, '2017-04-26 09:41:41');
INSERT INTO `sys_role_btn` VALUES (1860, 13, 29, '2017-06-23 23:08:48');
INSERT INTO `sys_role_btn` VALUES (1861, 13, 85, '2017-06-23 23:08:48');
INSERT INTO `sys_role_btn` VALUES (1862, 13, 84, '2017-06-23 23:08:48');
INSERT INTO `sys_role_btn` VALUES (1863, 13, 83, '2017-06-23 23:08:48');
INSERT INTO `sys_role_btn` VALUES (1864, 13, 82, '2017-06-23 23:08:48');
INSERT INTO `sys_role_btn` VALUES (1865, 13, 80, '2017-06-23 23:08:48');
INSERT INTO `sys_role_btn` VALUES (1866, 13, 79, '2017-06-23 23:08:48');
INSERT INTO `sys_role_btn` VALUES (1867, 13, 78, '2017-06-23 23:08:48');
INSERT INTO `sys_role_btn` VALUES (1868, 13, 77, '2017-06-23 23:08:48');
INSERT INTO `sys_role_btn` VALUES (3381, 12, 33, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3382, 12, 81, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3383, 12, 29, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3384, 12, 28, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3385, 12, 27, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3386, 12, 26, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3387, 12, 44, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3388, 12, 51, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3389, 12, 50, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3390, 12, 49, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3391, 12, 48, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3392, 12, 55, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3393, 12, 54, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3394, 12, 53, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3395, 12, 52, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3396, 12, 60, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3397, 12, 76, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3398, 12, 75, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3399, 12, 74, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3400, 12, 73, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3401, 12, 67, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3402, 12, 66, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3403, 12, 65, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3404, 12, 64, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3405, 12, 85, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3406, 12, 80, '2018-01-24 13:30:44');
INSERT INTO `sys_role_btn` VALUES (3423, 10, 33, '2018-06-13 20:57:35');
INSERT INTO `sys_role_btn` VALUES (3424, 10, 29, '2018-06-13 20:57:35');
INSERT INTO `sys_role_btn` VALUES (3425, 10, 47, '2018-06-13 20:57:35');
INSERT INTO `sys_role_btn` VALUES (3426, 10, 46, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3427, 10, 45, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3428, 10, 44, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3429, 10, 51, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3430, 10, 50, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3431, 10, 49, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3432, 10, 48, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3433, 10, 55, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3434, 10, 60, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3435, 10, 59, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3436, 10, 76, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3437, 10, 64, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3438, 10, 80, '2018-06-13 20:57:36');
INSERT INTO `sys_role_btn` VALUES (3439, 9, 25, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3440, 9, 33, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3441, 9, 29, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3442, 9, 47, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3443, 9, 46, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3444, 9, 45, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3445, 9, 44, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3446, 9, 51, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3447, 9, 55, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3448, 9, 60, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3449, 9, 76, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3450, 9, 68, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3451, 9, 72, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3452, 9, 64, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3453, 9, 85, '2018-06-13 20:57:55');
INSERT INTO `sys_role_btn` VALUES (3454, 9, 80, '2018-06-13 20:57:55');

-- ----------------------------
-- Table structure for sys_role_datarule
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_datarule`;
CREATE TABLE `sys_role_datarule`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) NOT NULL COMMENT '角色',
  `datarule_id` int(11) NOT NULL COMMENT '数据规则',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色数据规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_datarule
-- ----------------------------
INSERT INTO `sys_role_datarule` VALUES (7, 5, 5, '2017-04-04 17:20:24');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3056 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (573, 6, 18, '2017-01-25 20:33:40');
INSERT INTO `sys_role_menu` VALUES (574, 6, 15, '2017-01-25 20:33:40');
INSERT INTO `sys_role_menu` VALUES (575, 6, 16, '2017-01-25 20:33:40');
INSERT INTO `sys_role_menu` VALUES (576, 6, 17, '2017-01-25 20:33:40');
INSERT INTO `sys_role_menu` VALUES (639, 5, 19, '2017-04-04 17:20:24');
INSERT INTO `sys_role_menu` VALUES (660, 7, 19, '2017-04-04 19:24:41');
INSERT INTO `sys_role_menu` VALUES (661, 7, 20, '2017-04-04 19:24:41');
INSERT INTO `sys_role_menu` VALUES (662, 7, 21, '2017-04-04 19:24:41');
INSERT INTO `sys_role_menu` VALUES (663, 7, 17, '2017-04-04 19:24:41');
INSERT INTO `sys_role_menu` VALUES (664, 7, 15, '2017-04-04 19:24:41');
INSERT INTO `sys_role_menu` VALUES (665, 7, 18, '2017-04-04 19:24:41');
INSERT INTO `sys_role_menu` VALUES (1095, 8, 19, '2017-04-26 09:41:41');
INSERT INTO `sys_role_menu` VALUES (1096, 8, 20, '2017-04-26 09:41:41');
INSERT INTO `sys_role_menu` VALUES (1097, 8, 21, '2017-04-26 09:41:41');
INSERT INTO `sys_role_menu` VALUES (1098, 8, 29, '2017-04-26 09:41:41');
INSERT INTO `sys_role_menu` VALUES (1099, 8, 15, '2017-04-26 09:41:41');
INSERT INTO `sys_role_menu` VALUES (1100, 8, 18, '2017-04-26 09:41:41');
INSERT INTO `sys_role_menu` VALUES (1728, 13, 19, '2017-06-23 23:08:47');
INSERT INTO `sys_role_menu` VALUES (1729, 13, 20, '2017-06-23 23:08:47');
INSERT INTO `sys_role_menu` VALUES (1730, 13, 21, '2017-06-23 23:08:47');
INSERT INTO `sys_role_menu` VALUES (1731, 13, 29, '2017-06-23 23:08:47');
INSERT INTO `sys_role_menu` VALUES (1732, 13, 15, '2017-06-23 23:08:47');
INSERT INTO `sys_role_menu` VALUES (1733, 13, 33, '2017-06-23 23:08:47');
INSERT INTO `sys_role_menu` VALUES (1734, 13, 34, '2017-06-23 23:08:48');
INSERT INTO `sys_role_menu` VALUES (1735, 13, 25, '2017-06-23 23:08:48');
INSERT INTO `sys_role_menu` VALUES (1736, 13, 18, '2017-06-23 23:08:48');
INSERT INTO `sys_role_menu` VALUES (2776, 1, 19, '2017-12-07 11:36:07');
INSERT INTO `sys_role_menu` VALUES (2777, 1, 20, '2017-12-07 11:36:07');
INSERT INTO `sys_role_menu` VALUES (2778, 1, 21, '2017-12-07 11:36:07');
INSERT INTO `sys_role_menu` VALUES (2779, 1, 29, '2017-12-07 11:36:07');
INSERT INTO `sys_role_menu` VALUES (2780, 1, 33, '2017-12-07 11:36:07');
INSERT INTO `sys_role_menu` VALUES (2781, 1, 15, '2017-12-07 11:36:07');
INSERT INTO `sys_role_menu` VALUES (2782, 1, 34, '2017-12-07 11:36:07');
INSERT INTO `sys_role_menu` VALUES (2783, 1, 25, '2017-12-07 11:36:07');
INSERT INTO `sys_role_menu` VALUES (2784, 1, 2, '2017-12-07 11:36:07');
INSERT INTO `sys_role_menu` VALUES (2785, 1, 3, '2017-12-07 11:36:07');
INSERT INTO `sys_role_menu` VALUES (2786, 1, 4, '2017-12-07 11:36:07');
INSERT INTO `sys_role_menu` VALUES (2987, 12, 19, '2018-01-24 13:30:44');
INSERT INTO `sys_role_menu` VALUES (2988, 12, 20, '2018-01-24 13:30:44');
INSERT INTO `sys_role_menu` VALUES (2989, 12, 21, '2018-01-24 13:30:44');
INSERT INTO `sys_role_menu` VALUES (2990, 12, 29, '2018-01-24 13:30:44');
INSERT INTO `sys_role_menu` VALUES (2991, 12, 15, '2018-01-24 13:30:44');
INSERT INTO `sys_role_menu` VALUES (2992, 12, 33, '2018-01-24 13:30:44');
INSERT INTO `sys_role_menu` VALUES (2993, 12, 34, '2018-01-24 13:30:44');
INSERT INTO `sys_role_menu` VALUES (2994, 12, 25, '2018-01-24 13:30:44');
INSERT INTO `sys_role_menu` VALUES (2995, 12, 18, '2018-01-24 13:30:44');
INSERT INTO `sys_role_menu` VALUES (2996, 15, 19, '2018-02-24 11:38:53');
INSERT INTO `sys_role_menu` VALUES (2997, 15, 20, '2018-02-24 11:38:53');
INSERT INTO `sys_role_menu` VALUES (2998, 15, 21, '2018-02-24 11:38:53');
INSERT INTO `sys_role_menu` VALUES (2999, 15, 29, '2018-02-24 11:38:53');
INSERT INTO `sys_role_menu` VALUES (3000, 15, 15, '2018-02-24 11:38:53');
INSERT INTO `sys_role_menu` VALUES (3001, 15, 33, '2018-02-24 11:38:53');
INSERT INTO `sys_role_menu` VALUES (3002, 15, 34, '2018-02-24 11:38:53');
INSERT INTO `sys_role_menu` VALUES (3003, 15, 25, '2018-02-24 11:38:53');
INSERT INTO `sys_role_menu` VALUES (3004, 15, 24, '2018-02-24 11:38:53');
INSERT INTO `sys_role_menu` VALUES (3016, 10, 29, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3017, 10, 15, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3018, 10, 33, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3019, 10, 34, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3020, 10, 25, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3021, 10, 18, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3022, 10, 35, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3023, 10, 27, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3024, 10, 28, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3025, 10, 31, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3026, 10, 38, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3027, 10, 39, '2018-06-13 20:57:35');
INSERT INTO `sys_role_menu` VALUES (3028, 9, 19, '2018-06-13 20:57:55');
INSERT INTO `sys_role_menu` VALUES (3029, 9, 20, '2018-06-13 20:57:55');
INSERT INTO `sys_role_menu` VALUES (3030, 9, 21, '2018-06-13 20:57:55');
INSERT INTO `sys_role_menu` VALUES (3031, 9, 29, '2018-06-13 20:57:55');
INSERT INTO `sys_role_menu` VALUES (3032, 9, 15, '2018-06-13 20:57:55');
INSERT INTO `sys_role_menu` VALUES (3033, 9, 33, '2018-06-13 20:57:55');
INSERT INTO `sys_role_menu` VALUES (3034, 9, 34, '2018-06-13 20:57:55');
INSERT INTO `sys_role_menu` VALUES (3035, 9, 25, '2018-06-13 20:57:55');
INSERT INTO `sys_role_menu` VALUES (3036, 9, 18, '2018-06-13 20:57:55');
INSERT INTO `sys_role_menu` VALUES (3037, 9, 23, '2018-06-13 20:57:55');
INSERT INTO `sys_role_menu` VALUES (3038, 9, 35, '2018-06-13 20:57:55');
INSERT INTO `sys_role_menu` VALUES (3048, 11, 29, '2018-06-21 19:24:27');
INSERT INTO `sys_role_menu` VALUES (3049, 11, 15, '2018-06-21 19:24:27');
INSERT INTO `sys_role_menu` VALUES (3050, 11, 33, '2018-06-21 19:24:27');
INSERT INTO `sys_role_menu` VALUES (3051, 11, 34, '2018-06-21 19:24:27');
INSERT INTO `sys_role_menu` VALUES (3052, 11, 18, '2018-06-21 19:24:27');
INSERT INTO `sys_role_menu` VALUES (3053, 11, 35, '2018-06-21 19:24:27');
INSERT INTO `sys_role_menu` VALUES (3054, 11, 27, '2018-06-21 19:24:27');
INSERT INTO `sys_role_menu` VALUES (3055, 11, 28, '2018-06-21 19:24:27');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `roles` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `openid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `rate` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'zheshimima0.', '1', '2017-10-18 21:15:16', '11', NULL, NULL);
INSERT INTO `sys_user` VALUES (11, '开卡专用', 'zheshimima0.', '9', '2017-12-25 18:35:13', NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (13, '梧桐郡', 'zheshimima0.', '12', '2018-01-24 13:27:59', NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (15, 'admin', 'zheshimima0.', '1', '2016-01-08 15:33:05', 'oHxT3wDuZ6jUpVFF6PbnBVgx3YAA', 1, '0.006');
INSERT INTO `sys_user` VALUES (17, 'kefu', '12345678', '9', '2018-06-13 20:58:31', NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (18, 'agent01', 'ycyl...', '11', '2018-06-21 17:18:53', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for task_base
-- ----------------------------
DROP TABLE IF EXISTS `task_base`;
CREATE TABLE `task_base`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `target_type` int(11) DEFAULT NULL COMMENT '任务类型',
  `target_value` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '任务值',
  `cron` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'cron表达式',
  `last_run_result` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上次执行结果',
  `last_run_time` datetime(0) DEFAULT NULL COMMENT '上次执行时间',
  `last_run_time_cost` int(11) DEFAULT NULL COMMENT '上次执行耗时',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_log
-- ----------------------------
DROP TABLE IF EXISTS `task_log`;
CREATE TABLE `task_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `task_id` int(11) DEFAULT NULL COMMENT '所属任务',
  `result` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行结果',
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束时间',
  `cost_time` int(11) DEFAULT NULL COMMENT '耗时',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务-日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_proc_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_proc_log`;
CREATE TABLE `tb_proc_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `proc_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '过程名称',
  `statu` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `start_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束时间',
  `cost_time` int(11) DEFAULT NULL COMMENT '耗时',
  `error_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'error_code',
  `error_msg` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'error_msg',
  `step` int(11) DEFAULT NULL COMMENT 'step',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '存储过程运行日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for zcurd_field
-- ----------------------------
DROP TABLE IF EXISTS `zcurd_field`;
CREATE TABLE `zcurd_field`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `head_id` int(11) DEFAULT NULL COMMENT '所属表',
  `field_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段名称',
  `column_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '列表列名',
  `column_length` int(11) DEFAULT 120 COMMENT '列宽',
  `data_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据类型',
  `input_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'easyui-textbox' COMMENT '控件类型',
  `is_show_list` int(11) DEFAULT 1 COMMENT '是否列表显示',
  `is_allow_detail` int(11) DEFAULT 1 COMMENT '是否详情显示',
  `is_allow_add` int(11) DEFAULT 1 COMMENT '是否允许增加',
  `is_allow_update` int(11) DEFAULT 1 COMMENT '是否允许编辑',
  `is_search` int(11) DEFAULT 0 COMMENT '是否搜索',
  `is_allow_null` int(11) DEFAULT 1 COMMENT '是否允许为空',
  `dict_sql` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典sql',
  `order_num` int(11) DEFAULT 0 COMMENT '顺序',
  `search_type` int(11) DEFAULT 1 COMMENT '搜索类型(1:值,2:范围)',
  `is_sum` int(11) DEFAULT 0 COMMENT '是否汇总',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 10361 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '在线表单字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zcurd_field
-- ----------------------------
INSERT INTO `zcurd_field` VALUES (10226, 28, 'updateTime', '最后更新时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 1, '', 15, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10225, 28, 'remark', '备注', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 14, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (29, 5, 'method_name', '后台method名称', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 4, 1, 0, '2016-02-14 16:35:56');
INSERT INTO `zcurd_field` VALUES (27, 5, 'btn_name', '按钮名称', 100, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 2, 1, 0, '2016-02-14 16:35:56');
INSERT INTO `zcurd_field` VALUES (34, 6, 'value', '字段件值', 80, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 5, 1, 0, '2016-02-25 23:55:58');
INSERT INTO `zcurd_field` VALUES (36, 6, 'symbol', '符号', 80, 'varchar', 'easyui-combobox', 1, 1, 1, 1, 0, 0, 'select dict_key, dict_value from sys_dict where dict_type=\'datarule_symbol\'', 4, 1, 0, '2016-02-28 03:20:56');
INSERT INTO `zcurd_field` VALUES (33, 6, 'field_name', '字段名称', 100, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 3, 1, 0, '2016-02-25 23:55:58');
INSERT INTO `zcurd_field` VALUES (32, 6, 'menu_id', '菜单', 100, 'int', 'easyui-combobox', 1, 1, 1, 1, 1, 0, 'select id, menu_name from sys_menu', 2, 1, 0, '2016-02-25 23:55:58');
INSERT INTO `zcurd_field` VALUES (44, 8, 'create_time', '创建时间', 120, 'timestamp', 'easyui-datebox', 1, 1, 0, 0, 0, 1, '', 0, 1, 0, '2016-02-12 01:58:39');
INSERT INTO `zcurd_field` VALUES (43, 8, 'role_name', '角色名称', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 0, 1, 0, '2016-02-12 01:58:39');
INSERT INTO `zcurd_field` VALUES (42, 8, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 0, 1, 0, '2016-02-12 01:58:39');
INSERT INTO `zcurd_field` VALUES (30, 5, 'create_time', '创建时间', 120, 'timestamp', 'easyui-datebox', 0, 0, 0, 0, 0, 0, '', 5, 1, 0, '2016-02-14 16:35:56');
INSERT INTO `zcurd_field` VALUES (37, 7, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 0, 1, 0, '2016-03-01 04:39:14');
INSERT INTO `zcurd_field` VALUES (41, 7, 'create_time', '创建时间', 120, 'timestamp', 'easyui-datebox', 1, 1, 0, 0, 1, 0, '', 4, 2, 0, '2016-03-01 04:39:14');
INSERT INTO `zcurd_field` VALUES (40, 7, 'dict_value', '值', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 0, '', 2, 1, 0, '2016-03-01 04:39:14');
INSERT INTO `zcurd_field` VALUES (39, 7, 'dict_key', '键', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 0, '', 3, 1, 0, '2016-03-01 04:39:14');
INSERT INTO `zcurd_field` VALUES (38, 7, 'dict_type', '类型', 120, 'varchar', 'easyui-combobox', 1, 1, 1, 1, 1, 0, 'select distinct dict_type as \'key\', dict_type as \'value\' from sys_dict', 1, 1, 0, '2016-03-01 04:39:14');
INSERT INTO `zcurd_field` VALUES (61, 12, 'roles', '角色', 120, 'varchar', 'easyui-combobox', 1, 1, 1, 1, 0, 1, 'select id, role_name from sys_role where id>1', 4, 1, 0, '2016-02-23 04:31:08');
INSERT INTO `zcurd_field` VALUES (59, 12, 'password', '密码', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 3, 1, 0, '2016-01-07 21:31:45');
INSERT INTO `zcurd_field` VALUES (58, 12, 'user_name', '用户名', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 2, 1, 0, '2016-01-07 21:31:45');
INSERT INTO `zcurd_field` VALUES (57, 12, 'id', 'id', 120, 'int', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 1, 1, 0, '2016-01-07 21:31:45');
INSERT INTO `zcurd_field` VALUES (31, 6, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2016-02-25 23:55:58');
INSERT INTO `zcurd_field` VALUES (3, 1, 'btn_name', '按钮名称', 80, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 3, 1, 0, '2016-01-11 21:58:48');
INSERT INTO `zcurd_field` VALUES (10000, 1, 'head_id', '所属表单', 100, 'int', 'easyui-combobox', 1, 1, 1, 1, 1, 0, 'select id, form_name from zcurd_head', 2, 1, 0, '2016-09-27 00:06:53');
INSERT INTO `zcurd_field` VALUES (35, 6, 'create_time', '创建时间', 120, 'timestamp', 'easyui-datebox', 0, 0, 0, 0, 0, 0, '', 6, 1, 0, '2016-02-25 23:55:58');
INSERT INTO `zcurd_field` VALUES (25, 5, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 0, 1, 0, '2016-02-14 16:35:56');
INSERT INTO `zcurd_field` VALUES (28, 5, 'class_name', '页面class名称', 100, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 3, 1, 0, '2016-02-14 16:35:56');
INSERT INTO `zcurd_field` VALUES (1, 1, 'create_time', '创建时间', 130, 'timestamp', 'easyui-textbox', 1, 1, 0, 0, 0, 1, '', 7, 1, 0, '2016-01-11 21:58:48');
INSERT INTO `zcurd_field` VALUES (2, 1, 'func_content', '方法内容', 120, 'text', 'textarea', 0, 1, 1, 1, 0, 0, '', 6, 1, 0, '2016-01-11 21:58:48');
INSERT INTO `zcurd_field` VALUES (4, 1, 'location', '按钮位置', 80, 'int', 'easyui-combobox', 1, 1, 1, 1, 0, 0, 'select 1, \'顶部\' union all select 2, \'行内\'', 4, 1, 0, '2016-01-11 21:58:48');
INSERT INTO `zcurd_field` VALUES (5, 1, 'action', '行为', 80, 'int', 'easyui-combobox', 1, 1, 1, 1, 0, 0, 'select 0, \'自定义\' union all select 1, \'打开子页面\'', 5, 1, 0, '2016-01-11 21:58:48');
INSERT INTO `zcurd_field` VALUES (6, 2, 'create_time', '创建时间', 130, 'timestamp', 'easyui-textbox', 1, 1, 0, 0, 0, 1, '', 5, 1, 0, '2016-01-12 15:35:01');
INSERT INTO `zcurd_field` VALUES (7, 2, 'js_content', 'js内容', 120, 'text', 'textarea', 0, 1, 1, 1, 0, 0, '', 4, 1, 0, '2016-01-12 15:35:01');
INSERT INTO `zcurd_field` VALUES (8, 2, 'page', '页面', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 3, 1, 0, '2016-01-12 15:35:01');
INSERT INTO `zcurd_field` VALUES (9, 2, 'head_id', '所属表单', 120, 'int', 'easyui-combobox', 1, 1, 1, 1, 1, 0, 'select id, form_name from zcurd_head', 2, 1, 0, '2016-01-12 15:35:01');
INSERT INTO `zcurd_field` VALUES (26, 5, 'menu_id', '所属菜单', 100, 'int', 'easyui-combobox', 1, 1, 1, 1, 1, 0, 'select id, menu_name from sys_menu', 1, 1, 0, '2016-02-14 16:35:56');
INSERT INTO `zcurd_field` VALUES (10202, 26, 'chargenum', '充值次数', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 8, 1, 0, '2017-04-04 13:38:02');
INSERT INTO `zcurd_field` VALUES (10205, 26, 'jointime', '充卡时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 1, 1, '', 11, 2, 0, '2017-04-04 13:38:02');
INSERT INTO `zcurd_field` VALUES (10186, 24, 'createTime', '充值时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 0, '', 5, 1, 0, '2017-01-25 19:48:32');
INSERT INTO `zcurd_field` VALUES (10185, 24, 'amount', '剩余金额(分)', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 4, 1, 1, '2017-01-25 19:48:32');
INSERT INTO `zcurd_field` VALUES (10182, 24, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-01-25 19:48:32');
INSERT INTO `zcurd_field` VALUES (10183, 24, 'openId', '用户编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 2, 1, 0, '2017-01-25 19:48:32');
INSERT INTO `zcurd_field` VALUES (10184, 24, 'money', '充值金额(分)', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 3, 1, 1, '2017-01-25 19:48:32');
INSERT INTO `zcurd_field` VALUES (10194, 25, 'property', '物业名称', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 7, 1, 0, '2017-03-31 00:07:01');
INSERT INTO `zcurd_field` VALUES (10193, 25, 'address', '住址', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 6, 1, 0, '2017-03-31 00:07:01');
INSERT INTO `zcurd_field` VALUES (10191, 25, 'phone', '手机号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 0, '', 4, 1, 0, '2017-03-31 00:07:01');
INSERT INTO `zcurd_field` VALUES (10192, 25, 'cardnum', '卡号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 5, 1, 0, '2017-03-31 00:07:01');
INSERT INTO `zcurd_field` VALUES (10190, 25, 'propertyid', '物业编号', 120, 'int', 'easyui-numberspinner', 1, 0, 1, 0, 0, 1, '', 3, 1, 0, '2017-03-31 00:07:01');
INSERT INTO `zcurd_field` VALUES (10189, 25, 'name', '用户名', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 0, '', 2, 1, 0, '2017-03-31 00:07:01');
INSERT INTO `zcurd_field` VALUES (10188, 25, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-03-31 00:07:01');
INSERT INTO `zcurd_field` VALUES (10204, 26, 'total', '总共次数', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 10, 1, 0, '2017-04-04 13:38:02');
INSERT INTO `zcurd_field` VALUES (10216, 28, 'tow_hours_price', '非员会2小时价格', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 5, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10217, 28, 'four_hours_price', '非员会4小时价格', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 6, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10218, 28, 'eight_hours_price', '非员会8小时价格', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 7, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10221, 28, 'four_hours_mem_price', '员会4小时价格', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 10, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10220, 28, 'tow_hours_mem_price', '员会2小时价格', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 9, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10219, 28, 'twelve_hours_price', '非员会12小时价格', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 8, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10223, 28, 'twelve_hours_mem_price', '员会12小时价格', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 12, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10224, 28, 'status', '状态', 120, 'char', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 13, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10206, 27, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-04 16:26:15');
INSERT INTO `zcurd_field` VALUES (10210, 27, 'losttime', '挂失时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 1, 1, '', 5, 2, 0, '2017-04-04 16:26:15');
INSERT INTO `zcurd_field` VALUES (10211, 27, 'balance', '剩余次数', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 6, 1, 0, '2017-04-04 16:26:15');
INSERT INTO `zcurd_field` VALUES (10207, 27, 'uid', '用户编号', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 2, 1, 0, '2017-04-04 16:26:15');
INSERT INTO `zcurd_field` VALUES (10208, 27, 'name', '用户姓名', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 3, 1, 0, '2017-04-04 16:26:15');
INSERT INTO `zcurd_field` VALUES (10176, 23, 'cardnumber', '卡号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 2, 1, 0, '2017-01-25 19:17:41');
INSERT INTO `zcurd_field` VALUES (10177, 23, 'createtime', '办卡时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 1, 1, '', 3, 1, 0, '2017-01-25 19:17:41');
INSERT INTO `zcurd_field` VALUES (10215, 28, 'area', '设备位置', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 4, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10214, 28, 'match_num', '通讯序号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 3, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10213, 28, 'QR_num', '设备序号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 2, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10212, 28, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10174, 22, 'createDate', '创建时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 1, '', 13, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10170, 22, 'serverResultCode', '服务器返回充电结果', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 9, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10171, 22, 'serverResultDesc', ' 服务器充电结果描述', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 10, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10172, 22, 'status', '终端充电状态', 120, 'char', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 11, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10173, 22, 'chargeTime', '分钟', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 12, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10169, 22, 'operType', '充电方式(手动:M,微信W)', 120, 'char', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 8, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10168, 22, 'endTime', '结束充电时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 1, '', 7, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10167, 22, 'startTime', '开始充电时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 0, '', 6, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10166, 22, 'operStartTime', '操作时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 0, '', 5, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10165, 22, 'devicePort', '设备通道', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 4, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10164, 22, 'deviceId', '设备编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 0, '', 3, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10163, 22, 'openId', '微信用户号', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 0, '', 2, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10162, 22, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-01-25 18:54:32');
INSERT INTO `zcurd_field` VALUES (10180, 23, 'uid', '绑定用户ID', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 6, 1, 0, '2017-01-25 19:17:41');
INSERT INTO `zcurd_field` VALUES (10179, 23, 'state', '状态', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 5, 1, 0, '2017-01-25 19:17:41');
INSERT INTO `zcurd_field` VALUES (10178, 23, 'updatetime', '更新时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 1, '', 4, 1, 0, '2017-01-25 19:17:41');
INSERT INTO `zcurd_field` VALUES (10175, 23, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-01-25 19:17:41');
INSERT INTO `zcurd_field` VALUES (10181, 23, 'uname', '用户昵称', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 7, 1, 0, '2017-01-25 19:17:41');
INSERT INTO `zcurd_field` VALUES (10187, 24, 'chargeType', '充值类型(CH 物理充电卡,WA虚拟充电卡)', 120, 'char', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 6, 1, 0, '2017-01-25 19:48:32');
INSERT INTO `zcurd_field` VALUES (10203, 26, 'money', '消费金额', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 9, 1, 0, '2017-04-04 13:38:02');
INSERT INTO `zcurd_field` VALUES (10201, 26, 'cardcs', '卡次数', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 7, 1, 0, '2017-04-04 13:38:02');
INSERT INTO `zcurd_field` VALUES (10199, 26, 'phone', '手机号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 5, 1, 0, '2017-04-04 13:38:02');
INSERT INTO `zcurd_field` VALUES (10200, 26, 'cardnum', '卡号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 6, 1, 0, '2017-04-04 13:38:02');
INSERT INTO `zcurd_field` VALUES (10198, 26, 'name', '用户名称', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 4, 1, 0, '2017-04-04 13:38:02');
INSERT INTO `zcurd_field` VALUES (10197, 26, 'uid', '用户编号', 120, 'int', 'easyui-numberspinner', 0, 0, 1, 0, 0, 1, '', 3, 1, 0, '2017-04-04 13:38:02');
INSERT INTO `zcurd_field` VALUES (10196, 26, 'gid', '业物管理编号', 120, 'int', 'easyui-numberspinner', 0, 0, 1, 0, 0, 1, '', 2, 1, 0, '2017-04-04 13:38:02');
INSERT INTO `zcurd_field` VALUES (10195, 26, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-04 13:38:02');
INSERT INTO `zcurd_field` VALUES (10238, 29, 'value', '秘钥数值', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 4, 1, 0, '2017-04-06 22:23:05');
INSERT INTO `zcurd_field` VALUES (10237, 29, 'key', '秘钥名称', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 3, 1, 0, '2017-04-06 22:23:05');
INSERT INTO `zcurd_field` VALUES (10236, 29, 'gid', 'gid', 120, 'int', 'easyui-numberspinner', 0, 0, 0, 0, 0, 1, '', 2, 1, 0, '2017-04-06 22:23:05');
INSERT INTO `zcurd_field` VALUES (10235, 29, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-06 22:23:05');
INSERT INTO `zcurd_field` VALUES (10239, 29, 'remark', '备注', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 5, 1, 0, '2017-04-06 22:23:05');
INSERT INTO `zcurd_field` VALUES (10269, 31, 'cardCode', '充电卡号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 4, 1, 0, '2017-04-06 23:04:13');
INSERT INTO `zcurd_field` VALUES (10263, 30, 'walletNumber', 'walletNumber', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 24, 1, 0, '2017-04-06 22:42:43');
INSERT INTO `zcurd_field` VALUES (10264, 30, 'walletAccount', '钱包', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 25, 1, 0, '2017-04-06 22:42:43');
INSERT INTO `zcurd_field` VALUES (10333, 37, 'coupon_count', '代金券或立减优惠使用数量', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, NULL, 18, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10334, 37, 'attach', '商家数据包', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 19, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10315, 36, 'time', '支付日期', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 9, 2, 0, '2017-04-06 23:23:03');
INSERT INTO `zcurd_field` VALUES (10307, 36, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-06 23:23:03');
INSERT INTO `zcurd_field` VALUES (10308, 36, 'actNum', 'actNum', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 2, 1, 0, '2017-04-06 23:23:03');
INSERT INTO `zcurd_field` VALUES (10309, 36, 'type', '类型(CH:充电)', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 3, 1, 0, '2017-04-06 23:23:03');
INSERT INTO `zcurd_field` VALUES (10310, 36, 'money', '充值金额', 120, 'double', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 4, 1, 0, '2017-04-06 23:23:03');
INSERT INTO `zcurd_field` VALUES (10265, 30, 'updateTime', 'updateTime', 120, 'datetime', 'easyui-datebox', 0, 0, 0, 0, 0, 1, '', 26, 1, 0, '2017-04-06 22:42:43');
INSERT INTO `zcurd_field` VALUES (10270, 31, 'balance', '剩余金额', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 5, 1, 0, '2017-04-06 23:04:13');
INSERT INTO `zcurd_field` VALUES (10259, 30, 'phyCardNumber', '物理卡号', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 20, 1, 0, '2017-04-06 22:42:43');
INSERT INTO `zcurd_field` VALUES (10260, 30, 'cardAccount', 'cardAccount', 120, 'int', 'easyui-numberspinner', 0, 0, 0, 0, 0, 1, '', 21, 1, 0, '2017-04-06 22:42:43');
INSERT INTO `zcurd_field` VALUES (10261, 30, 'band', '是否绑定手机(Y:绑定 N:未绑定)', 120, 'char', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 22, 1, 0, '2017-04-06 22:42:43');
INSERT INTO `zcurd_field` VALUES (10256, 30, 'lastLoginDate', '最后登录时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 1, NULL, 17, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10262, 30, 'status', '用户状态(Y有效 N:无效或无此用户)', 120, 'char', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 23, 1, 0, '2017-04-06 22:42:43');
INSERT INTO `zcurd_field` VALUES (10258, 30, 'cardNumber', '卡号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 19, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10257, 30, 'level', '等级', 120, 'int', 'easyui-numberspinner', 0, 0, 0, 0, 0, 1, '', 18, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10255, 30, 'registerDate', '注册时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 0, NULL, 16, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10254, 30, 'remember', '是否记住密码', 120, 'char', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 15, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10253, 30, 'password2', '明文密码', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 14, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10252, 30, 'password', 'MD5密码', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 13, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10251, 30, 'tel', '手机号码', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 12, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10250, 30, 'email', '邮箱', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 11, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10248, 30, 'country', 'country', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 9, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10249, 30, 'province', '省会', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 10, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10247, 30, 'city', '城市', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 8, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10246, 30, 'privilege', 'privilege', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 7, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10242, 30, 'nickName', '昵称', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, NULL, 3, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10243, 30, 'headimgurl', '头像', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 4, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10244, 30, 'sex', '性别', 120, 'int', 'easyui-numberspinner', 0, 0, 0, 0, 0, 1, '', 5, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10245, 30, 'unionid', 'unionid', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 6, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10241, 30, 'openId', '微信用户的唯一识别', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 2, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10240, 30, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-06 22:42:42');
INSERT INTO `zcurd_field` VALUES (10268, 31, 'phyicalId', '物理编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 3, 1, 0, '2017-04-06 23:04:13');
INSERT INTO `zcurd_field` VALUES (10267, 31, 'deviceId', '设备编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 2, 1, 0, '2017-04-06 23:04:13');
INSERT INTO `zcurd_field` VALUES (10266, 31, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-06 23:04:13');
INSERT INTO `zcurd_field` VALUES (10271, 31, 'joinDate', '刷卡时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 1, 1, '', 6, 2, 0, '2017-04-06 23:04:13');
INSERT INTO `zcurd_field` VALUES (10278, 32, 'jointime', '记录时间', 140, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 1, 1, '', 7, 2, 0, '2017-04-06 23:08:13');
INSERT INTO `zcurd_field` VALUES (10343, 38, 'jointime', '操作时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 1, 1, '', 5, 2, 0, '2017-04-22 18:37:26');
INSERT INTO `zcurd_field` VALUES (10272, 32, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-06 23:08:13');
INSERT INTO `zcurd_field` VALUES (10273, 32, 'type', '类型', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 2, 1, 0, '2017-04-06 23:08:13');
INSERT INTO `zcurd_field` VALUES (10274, 32, 'openid', '用户编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 3, 1, 0, '2017-04-06 23:08:13');
INSERT INTO `zcurd_field` VALUES (10277, 32, 'content', '日志内容', 350, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 6, 1, 0, '2017-04-06 23:08:13');
INSERT INTO `zcurd_field` VALUES (10276, 32, 'ip', 'IP地址', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 5, 1, 0, '2017-04-06 23:08:13');
INSERT INTO `zcurd_field` VALUES (10275, 32, 'deviceid', '设备编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 4, 1, 0, '2017-04-06 23:08:13');
INSERT INTO `zcurd_field` VALUES (10285, 33, 'jointime', '记录时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 1, 1, '', 7, 2, 0, '2017-04-06 23:11:55');
INSERT INTO `zcurd_field` VALUES (10284, 33, 'content', '返回内容', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 6, 1, 0, '2017-04-06 23:11:55');
INSERT INTO `zcurd_field` VALUES (10283, 33, 'status', '状态', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 5, 1, 0, '2017-04-06 23:11:55');
INSERT INTO `zcurd_field` VALUES (10282, 33, 'channelnum', '通道编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 4, 1, 0, '2017-04-06 23:11:55');
INSERT INTO `zcurd_field` VALUES (10281, 33, 'openid', '用户编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 3, 1, 0, '2017-04-06 23:11:55');
INSERT INTO `zcurd_field` VALUES (10280, 33, 'deviceid', '设备编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 2, 1, 0, '2017-04-06 23:11:55');
INSERT INTO `zcurd_field` VALUES (10279, 33, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-06 23:11:54');
INSERT INTO `zcurd_field` VALUES (10291, 34, 'chargeType', '充值类型(CH 物理充电卡,WA虚拟充电卡)', 120, 'char', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 6, 1, 0, '2017-04-06 23:15:35');
INSERT INTO `zcurd_field` VALUES (10290, 34, 'createTime', '充值时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 1, 0, '', 5, 1, 0, '2017-04-06 23:15:35');
INSERT INTO `zcurd_field` VALUES (10289, 34, 'amount', '剩余金额(分)', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 4, 1, 0, '2017-04-06 23:15:35');
INSERT INTO `zcurd_field` VALUES (10288, 34, 'money', '充值金额(分)', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 3, 1, 0, '2017-04-06 23:15:35');
INSERT INTO `zcurd_field` VALUES (10286, 34, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-06 23:15:35');
INSERT INTO `zcurd_field` VALUES (10287, 34, 'openId', '用户编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 2, 1, 0, '2017-04-06 23:15:35');
INSERT INTO `zcurd_field` VALUES (10306, 35, 'MD5', '防止重复提交,时间+设备号+端口=MD5', 120, 'varchar', 'easyui-textbox', 0, 0, 0, 0, 0, 1, '', 15, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10305, 35, 'createDate', '操作时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 1, 1, '', 14, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10304, 35, 'chargeTime', '充电时间(分钟)', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 13, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10302, 35, 'status', '终端充电状态', 120, 'char', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 11, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10303, 35, 'charge', '消费金额', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 12, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10301, 35, 'serverResultDesc', ' 服务器充电结果描述', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 10, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10300, 35, 'serverResultCode', '服务器返回充电结果', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 9, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10299, 35, 'operType', '充电方式(手动:M,微信W)', 120, 'char', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 8, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10297, 35, 'startTime', '开始充电时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 1, '', 6, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10298, 35, 'endTime', '结束时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 1, '', 7, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10295, 35, 'devicePort', '设备端口号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 4, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10296, 35, 'operStartTime', '操作开始时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 0, 0, '', 5, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10294, 35, 'deviceId', '设备编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 0, '', 3, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10293, 35, 'openId', '用户编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 2, 1, 0, '2017-04-06 23:17:17');
INSERT INTO `zcurd_field` VALUES (10292, 35, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-06 23:17:16');
INSERT INTO `zcurd_field` VALUES (10314, 36, 'remark', '备注', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, '', 8, 1, 0, '2017-04-06 23:23:03');
INSERT INTO `zcurd_field` VALUES (10313, 36, 'status', '状态(有效:Y 无效N)', 120, 'char', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 7, 1, 0, '2017-04-06 23:23:03');
INSERT INTO `zcurd_field` VALUES (10312, 36, 'coupon', '送优惠次数', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 6, 1, 0, '2017-04-06 23:23:03');
INSERT INTO `zcurd_field` VALUES (10311, 36, 'chargeNum', '充电次数', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 5, 1, 0, '2017-04-06 23:23:03');
INSERT INTO `zcurd_field` VALUES (10227, 28, 'createTime', '设备加入时间', 120, 'datetime', 'easyui-datebox', 1, 1, 1, 1, 1, 1, '', 16, 2, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10222, 28, 'eight_hours_mem_price', '员会8小时价格', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, '', 11, 1, 0, '2017-04-04 18:25:56');
INSERT INTO `zcurd_field` VALUES (10209, 27, 'cardnum', '用户卡号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 4, 1, 0, '2017-04-04 16:26:15');
INSERT INTO `zcurd_field` VALUES (10331, 37, 'coupon_id', '代金券或立减优惠ID', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 16, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10332, 37, 'coupon_fee', '单个代金券或立减优惠支付金额', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 1, NULL, 17, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10324, 37, 'result_code', '业务结果', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, NULL, 9, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10330, 37, 'transaction_id', '微信支付订单号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, NULL, 15, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10329, 37, 'bank_type', '付款银行', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, NULL, 14, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10328, 37, 'trade_type', '交易类型', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, NULL, 13, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10327, 37, 'is_subscribe', 'is_subscribe', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 12, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10326, 37, 'err_code_des', 'err_code_des', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 11, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10325, 37, 'err_code', '错误代码', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 10, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10323, 37, 'fee_type', '货币种类', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 8, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10322, 37, 'total_fee', '总金额', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, NULL, 7, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10321, 37, 'cash_fee', 'cash_fee', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, NULL, 6, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10320, 37, 'mch_id', 'mch_id', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, NULL, 5, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10319, 37, 'openId', '用户标识', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, NULL, 4, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10318, 37, 'out_trade_no', 'out_trade_no', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, NULL, 3, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10317, 37, 'appid', 'appid', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, '', 2, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10316, 37, 'id', '主键ID', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, NULL, 1, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10335, 37, 'time_end', '支付完成时间', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 0, '', 20, 2, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10336, 37, 'couresCount', '购买的课时数', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, NULL, 21, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10337, 37, 'couresId', '购买的课程编号', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, NULL, 22, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10338, 37, 'url', '视频播放地址', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 0, NULL, 23, 1, 0, '2017-04-06 23:25:54');
INSERT INTO `zcurd_field` VALUES (10341, 38, 'deviceid', '设备编号', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 3, 1, 0, '2017-04-22 18:37:26');
INSERT INTO `zcurd_field` VALUES (10342, 38, 'content', '日志内容', 280, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 1, 1, '', 4, 1, 0, '2017-04-22 18:37:26');
INSERT INTO `zcurd_field` VALUES (10339, 38, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 0, 0, '', 1, 1, 0, '2017-04-22 18:37:26');
INSERT INTO `zcurd_field` VALUES (10340, 38, 'type', '类别', 120, 'int', 'easyui-numberspinner', 1, 1, 1, 1, 1, 1, '', 2, 1, 0, '2017-04-22 18:37:26');
INSERT INTO `zcurd_field` VALUES (60, 12, 'create_time', '创建时间', 120, 'timestamp', 'easyui-textbox', 1, 1, 0, 0, 1, 1, '', 5, 2, 0, '2016-01-07 21:31:45');
INSERT INTO `zcurd_field` VALUES (10349, 39, 'remark', '设备位置', 120, 'varchar', 'easyui-textbox', 1, 1, 0, 0, 0, 1, '', 6, 1, 0, '2017-08-31 21:46:24');
INSERT INTO `zcurd_field` VALUES (10348, 39, 'reson', '事件原因', 120, 'varchar', 'easyui-textbox', 1, 1, 0, 0, 1, 1, '', 5, 1, 0, '2017-08-31 21:46:24');
INSERT INTO `zcurd_field` VALUES (10346, 39, 'ercode', '设备编号', 120, 'varchar', 'easyui-textbox', 1, 1, 0, 0, 1, 1, '', 3, 1, 0, '2017-08-31 21:46:24');
INSERT INTO `zcurd_field` VALUES (10347, 39, 'updatedate', '掉线时间', 120, 'datetime', 'easyui-datebox', 1, 1, 0, 0, 1, 1, '', 4, 1, 0, '2017-08-31 21:46:24');
INSERT INTO `zcurd_field` VALUES (10345, 39, 'deviceid', '联网模块', 120, 'varchar', 'easyui-textbox', 1, 1, 0, 0, 1, 1, '', 2, 1, 0, '2017-08-31 21:46:24');
INSERT INTO `zcurd_field` VALUES (10344, 39, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 0, 0, 0, 0, '', 1, 1, 0, '2017-08-31 21:46:24');
INSERT INTO `zcurd_field` VALUES (10353, 40, 'status', '状态', 120, 'char', 'easyui-textbox', 1, 1, 0, 0, 1, 1, '', 4, 1, 0, '2017-08-31 21:48:57');
INSERT INTO `zcurd_field` VALUES (10352, 40, 'port', '端口', 120, 'char', 'easyui-textbox', 1, 1, 0, 0, 0, 1, '', 3, 1, 0, '2017-08-31 21:48:57');
INSERT INTO `zcurd_field` VALUES (10351, 40, 'deviceid', '设备编号', 120, 'varchar', 'easyui-textbox', 1, 1, 0, 0, 1, 1, '', 2, 1, 0, '2017-08-31 21:48:57');
INSERT INTO `zcurd_field` VALUES (10350, 40, 'id', 'id', 120, 'int', 'easyui-numberspinner', 1, 1, 0, 0, 0, 0, '', 1, 1, 0, '2017-08-31 21:48:57');
INSERT INTO `zcurd_field` VALUES (10354, 40, 'updatedate', '更新日期', 120, 'datetime', 'easyui-datebox', 1, 1, 0, 0, 1, 1, '', 5, 2, 0, '2017-08-31 21:48:57');
INSERT INTO `zcurd_field` VALUES (10355, 41, 'id', 'id', 120, 'int unsigned', 'easyui-textbox', 1, 1, 1, 1, 0, 0, NULL, 1, 1, 0, '2017-11-03 10:45:44');
INSERT INTO `zcurd_field` VALUES (10356, 41, 'user_name', '用户名', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 2, 1, 0, '2017-11-03 10:45:44');
INSERT INTO `zcurd_field` VALUES (10357, 41, 'password', '密码', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 3, 1, 0, '2017-11-03 10:45:44');
INSERT INTO `zcurd_field` VALUES (10358, 41, 'roles', '角色', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 4, 1, 0, '2017-11-03 10:45:44');
INSERT INTO `zcurd_field` VALUES (10359, 41, 'create_time', '创建时间', 120, 'timestamp', 'easyui-datebox', 1, 1, 1, 1, 0, 1, NULL, 5, 1, 0, '2017-11-03 10:45:44');
INSERT INTO `zcurd_field` VALUES (10360, 41, 'openid', 'openid', 120, 'varchar', 'easyui-textbox', 1, 1, 1, 1, 0, 1, NULL, 6, 1, 0, '2017-11-03 10:45:44');

-- ----------------------------
-- Table structure for zcurd_head
-- ----------------------------
DROP TABLE IF EXISTS `zcurd_head`;
CREATE TABLE `zcurd_head`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `table_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据库表名',
  `form_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表单名称',
  `id_field` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'id' COMMENT '主键字段',
  `is_auto` int(11) DEFAULT 1 COMMENT '是否自增',
  `form_type` int(11) DEFAULT 1 COMMENT '表单类型（1:单表,2:主从）',
  `dialog_size` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '600x400' COMMENT '弹窗大小',
  `db_source` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据源',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '在线表单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zcurd_head
-- ----------------------------
INSERT INTO `zcurd_head` VALUES (1, 'zcurd_head_btn', '表单扩展按钮', 'id', 1, 1, '600x400', 'zcurd_base', '2016-01-11 21:58:48');
INSERT INTO `zcurd_head` VALUES (2, 'zcurd_head_js', '表单扩展js', 'id', 1, 1, '600x400', 'zcurd_base', '2016-01-12 15:35:01');
INSERT INTO `zcurd_head` VALUES (5, 'sys_menu_btn', '菜单按钮（权限编辑）', 'id', 1, 1, '400x300', 'zcurd_base', '2016-02-14 16:35:56');
INSERT INTO `zcurd_head` VALUES (6, 'sys_menu_datarule', '菜单数据（权限编辑）', 'id', 1, 1, '400x300', 'zcurd_base', '2016-02-25 23:55:58');
INSERT INTO `zcurd_head` VALUES (7, 'sys_dict', '数据字典', 'id', 1, 1, '400x300', 'zcurd_base', '2016-03-01 04:39:14');
INSERT INTO `zcurd_head` VALUES (8, 'sys_role', '角色管理', 'id', 1, 1, '600x400', 'zcurd_base', '2016-02-12 01:58:39');
INSERT INTO `zcurd_head` VALUES (12, 'sys_user', '用户管理', 'id', 1, 1, '400x300', 'zcurd_base', '2016-01-07 21:31:45');
INSERT INTO `zcurd_head` VALUES (27, 'lostcard', '挂失记录', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-04 16:26:15');
INSERT INTO `zcurd_head` VALUES (28, 'qr_match_device', '设备记录', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-04 18:25:56');
INSERT INTO `zcurd_head` VALUES (22, 'charge_battery_info', '充电桩充值记录', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-01-25 18:54:32');
INSERT INTO `zcurd_head` VALUES (23, 'card', '用户IC卡表', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-01-25 19:17:41');
INSERT INTO `zcurd_head` VALUES (24, 'charge_money_info', '充值金额管理', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-01-25 19:48:32');
INSERT INTO `zcurd_head` VALUES (25, 'customer', '物业用户卡信息', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-03-31 00:07:01');
INSERT INTO `zcurd_head` VALUES (26, 'charge', '物业端充电纪录表', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-04 13:38:02');
INSERT INTO `zcurd_head` VALUES (29, 'config', '秘钥配置', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-06 22:23:05');
INSERT INTO `zcurd_head` VALUES (30, 'tuser', '在线用户', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-06 22:42:42');
INSERT INTO `zcurd_head` VALUES (31, 'cardlog', '刷卡记录', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-06 23:04:13');
INSERT INTO `zcurd_head` VALUES (32, 'devicelog', '网络日志', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-06 23:08:13');
INSERT INTO `zcurd_head` VALUES (33, 'chargestatus', '充电状态', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-06 23:11:54');
INSERT INTO `zcurd_head` VALUES (34, 'charge_money_info', '微信充值', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-06 23:15:35');
INSERT INTO `zcurd_head` VALUES (35, 'charge_battery_info', '消费记录', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-06 23:17:16');
INSERT INTO `zcurd_head` VALUES (36, 'money_match_activity', '优惠设置', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-06 23:23:03');
INSERT INTO `zcurd_head` VALUES (37, 'orders', '支付之日', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-06 23:25:54');
INSERT INTO `zcurd_head` VALUES (38, 'devicemsg', '功率日志', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-04-22 18:37:26');
INSERT INTO `zcurd_head` VALUES (39, 'droppedlog', '掉线日志', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-08-31 21:46:24');
INSERT INTO `zcurd_head` VALUES (40, 'deviceport', '端口状态', 'id', 1, 1, '600x400', 'zcurd_busi', '2017-08-31 21:48:57');
INSERT INTO `zcurd_head` VALUES (41, 'sys_user', '用户管理', 'id', 1, 1, '600x400', 'zcurd_base', '2017-11-03 10:45:44');

-- ----------------------------
-- Table structure for zcurd_head_btn
-- ----------------------------
DROP TABLE IF EXISTS `zcurd_head_btn`;
CREATE TABLE `zcurd_head_btn`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `head_id` int(11) DEFAULT NULL COMMENT '所属表单',
  `btn_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '按钮名称',
  `location` int(11) DEFAULT 1 COMMENT '按钮位置（1：顶部，2：行内）',
  `action` int(11) DEFAULT 1 COMMENT '行为（0：无，1：打开子页面）',
  `func_content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '方法内容',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表单按钮' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zcurd_head_btn
-- ----------------------------
INSERT INTO `zcurd_head_btn` VALUES (1, 8, '权限设置', 2, 1, 'function(index) {\r\n var row = datagrid.datagrid(\"getRows\")[index];\r\n  //var url = basePath + \"/zcurd/135/listPage?riskgradeid=\" + row.id;\r\n var url = basePath + \"/role/editAuthPage?roleId=\" + row.id;\r\n _openSubPage(url);\r\n}', '2016-02-12 21:23:10');
INSERT INTO `zcurd_head_btn` VALUES (2, 134, '管理', 2, 1, 'function(index) {\n var row = datagrid.datagrid(\"getRows\")[index];\n  var url = basePath + \"/zcurd/135/listPage?riskgradeid=\" + row.id;\n _openSubPage(url);\n}', '2016-01-12 13:28:50');
INSERT INTO `zcurd_head_btn` VALUES (3, 133, '测试', 2, 0, 'function aaa(){}', '2016-01-13 09:30:35');
INSERT INTO `zcurd_head_btn` VALUES (5, 23, 'aaa', 2, 1, 'function(index) {\n var row = datagrid.datagrid(\"getRows\")[index];\n  //var url = basePath + \"/zcurd/135/listPage?riskgradeid=\" + row.id;\n var url = basePath + \"/role/editAuthPage?roleId=\" + row.id;\n _openSubPage(url);\n}', '2017-01-25 20:28:47');

-- ----------------------------
-- Table structure for zcurd_head_js
-- ----------------------------
DROP TABLE IF EXISTS `zcurd_head_js`;
CREATE TABLE `zcurd_head_js`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `head_id` int(11) DEFAULT NULL COMMENT '所属表单',
  `page` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '页面',
  `js_content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'js内容',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表单扩展js' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of zcurd_head_js
-- ----------------------------
INSERT INTO `zcurd_head_js` VALUES (1, 134, 'list', 'var operateWidth = 80;\r\nvar subPageWidth = \"50%\";\r\nvar subPageTitle = \"等级详情\";\r\ndgOptions.singleSelect=true;', '2016-01-12 16:25:45');
INSERT INTO `zcurd_head_js` VALUES (2, 5, 'list', '$(\"#searchBtnWrap\").hide();\n$(\".wrap_search\").hide();\ndgOptions.pageSize=1000;\ndgOptions.pagination=false;\n\nwindow.parent.getDgSelections = function() {\n    return datagrid.datagrid(\"getSelections\");\n}\n\ndgOptions.onLoadSuccess = selectAuthRow;\nfunction selectAuthRow() {\n  	var btnIds = window.parent.getCurrMenuBtns();\n 	var rows = datagrid.datagrid(\"getData\").rows;\n 	if(btnIds && rows.length > 0) {\n   		$.each(rows, function(i, item) {\n      		$.each(btnIds, function(j, btnId) {\n	       		if(item.id == btnId) {\n	          		datagrid.datagrid(\"selectRow\", j);\n	        	}\n     		});\n   		});\n 	}\n\n 	//如果无数据，则显示一键生成\n	if(rows.length == 0) {\n		genAuthBtnBatch();\n	}\n}\n\n//显示一键生成\nfunction genAuthBtnBatch() {\n	$(\"<button id=\'genAuthBtnBatchBtn\' style=\'position: fixed; top: 120px; left: 50%; margin-left: -30px; padding: 2px;\'>一键生成<button>\")\n	.linkbutton().click(function() {\n		$.post(\"../../role/genAuthBtnBatch\", {menuId: $(\"#menu_id\").val()}, function() {\n			$(\"#genAuthBtnBatchBtn\").remove();\n			showMsg(\"生成成功！\");\n			datagrid.datagrid(\"reload\");\n		});\n	}).appendTo(\"body\");\n}', '2016-02-15 11:13:14');
INSERT INTO `zcurd_head_js` VALUES (3, 8, 'list', 'var operateWidth = 80;\nvar subPageWidth = \"55%\";\nvar subPageTitle = \"权限设置\";\ndgOptions.singleSelect=true;', '2016-02-16 16:32:31');
INSERT INTO `zcurd_head_js` VALUES (4, 131, 'update', '$(function() {\n  changeComboboxToMult(\"roles\");\n});', '2016-02-23 23:09:57');
INSERT INTO `zcurd_head_js` VALUES (5, 12, 'add', '$(function() {\n changeComboboxToMult(\"roles\");\n});', '2016-02-23 23:10:03');
INSERT INTO `zcurd_head_js` VALUES (6, 152, 'list', '$(\"#searchBtnWrap\").hide();\n$(\".wrap_search\").hide();\ndgOptions.pageSize=1000;\ndgOptions.pagination=false;\n\nwindow.parent.getDgSelections2 = function() {\n    return datagrid.datagrid(\"getSelections\");\n}\n\ndgOptions.onLoadSuccess = selectAuthRow;\nfunction selectAuthRow() {\n var dataruleIds = window.parent.getCurrMenuDatarules();\n var rows = datagrid.datagrid(\"getData\").rows;\n if(dataruleIds && rows.length > 0) {\n    $.each(rows, function(i, item) {\n      $.each(dataruleIds, function(j, dataruleId) {\n       if(item.id == dataruleId) {\n         datagrid.datagrid(\"selectRow\", j);\n        }\n     });\n   });\n }\n}', '2016-02-15 11:13:14');
INSERT INTO `zcurd_head_js` VALUES (7, 6, 'list', '$(\"#searchBtnWrap\").hide();\n$(\".wrap_search\").hide();\ndgOptions.pageSize=1000;\ndgOptions.pagination=false;\n\nwindow.parent.getDgSelections2 = function() {\n    return datagrid.datagrid(\"getSelections\");\n}\n\ndgOptions.onLoadSuccess = selectAuthRow;\nfunction selectAuthRow() {\n	var dataruleIds = window.parent.getCurrMenuDatarules();\n	var rows = datagrid.datagrid(\"getData\").rows;\n	if(dataruleIds && rows.length > 0) {\n		$.each(rows, function(i, item) {\n			$.each(dataruleIds, function(j, dataruleId) {\n				if(item.id == dataruleId) {\n					datagrid.datagrid(\"selectRow\", j);\n				}\n			});\n		});\n	}\n}', '2016-09-27 00:17:56');
INSERT INTO `zcurd_head_js` VALUES (8, 12, 'update', '$(function() {\n changeComboboxToMult(\"roles\");\n});', '2016-09-27 00:40:00');
INSERT INTO `zcurd_head_js` VALUES (9, 6, 'update', '//支持变量\n$(function() {\n    $(\"#value\").textbox({iconCls:\'icon-bianliang\'});\n    $(\".icon-bianliang\").tooltip({\n        content: \"<span style=\'color:#fff\'>支持变量 如：<br/>${user.id}</span>\", \n            onShow: function(){\n                $(this).tooltip(\'tip\').css({\n                    backgroundColor: \'#666\',\n                    borderColor: \'#666\'\n                });\n            }\n        });\n});', '2016-11-01 19:28:10');
INSERT INTO `zcurd_head_js` VALUES (10, 19, 'add', '//支持变量\n$(function() {\n    $(\"#value\").textbox({iconCls:\'icon-bianliang\'});\n    $(\".icon-bianliang\").tooltip({\n        content: \"<span style=\'color:#fff\'>支持变量 如：<br/>${user.id}</span>\", \n            onShow: function(){\n                $(this).tooltip(\'tip\').css({\n                    backgroundColor: \'#666\',\n                    borderColor: \'#666\'\n                });\n            }\n        });\n});', '2016-11-02 19:12:40');
INSERT INTO `zcurd_head_js` VALUES (11, 6, 'add', '//支持变量\n$(function() {\n    $(\"#value\").textbox({iconCls:\'icon-bianliang\'});\n    $(\".icon-bianliang\").tooltip({\n        content: \"<span style=\'color:#fff\'>支持变量 如：<br/>${user.id}</span>\", \n            onShow: function(){\n                $(this).tooltip(\'tip\').css({\n                    backgroundColor: \'#666\',\n                    borderColor: \'#666\'\n                });\n            }\n        });\n});', '2016-11-02 19:13:46');

SET FOREIGN_KEY_CHECKS = 1;
