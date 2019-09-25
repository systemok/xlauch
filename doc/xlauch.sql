/*
Navicat MySQL Data Transfer

Source Server         : 个人服务器 Xlauch@123456
Source Server Version : 50720
Source Host           : 39.106.64.79:3306
Source Database       : xlauch

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-04-27 16:30:23
*/
drop database xlauch if exists;
create database xlauch;
use xlauch;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `CALENDAR_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `CRON_EXPRESSION` varchar(120) COLLATE utf8_bin NOT NULL,
  `TIME_ZONE_ID` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('schedulerFactoryBean', '系统参数_JOB_GROUP_TRI', 'TRI_GROUP', '0/5 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `ENTRY_ID` varchar(95) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) COLLATE utf8_bin NOT NULL,
  `IS_DURABLE` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_UPDATE_DATA` varchar(1) COLLATE utf8_bin NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('schedulerFactoryBean', '系统参数', 'JOB_GROUP', '', 'com.xlauch.web.job.ParamJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `LOCK_NAME` varchar(40) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
INSERT INTO `QRTZ_LOCKS` VALUES ('schedulerFactoryBean', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` VALUES ('schedulerFactoryBean', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('schedulerFactoryBean', 'fangxz1524817546369', '1524817831574', '7500');
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('schedulerFactoryBean', 'huangxy1524815246014', '1524817828235', '7500');
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('schedulerFactoryBean', 'localhost1524740944735', '1524817824682', '7500');

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `STR_PROP_1` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `STR_PROP_2` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `STR_PROP_3` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `TRIGGER_TYPE` varchar(8) COLLATE utf8_bin NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_TRIGGERS` VALUES ('schedulerFactoryBean', '系统参数_JOB_GROUP_TRI', 'TRI_GROUP', '系统参数', 'JOB_GROUP', null, '1513243170000', '1513243165000', '5', 'PAUSED', 'CRON', '1513063965000', '4102415999000', null, '0', '');

-- ----------------------------
-- Table structure for t_biz_user
-- ----------------------------
DROP TABLE IF EXISTS `t_biz_user`;
CREATE TABLE `t_biz_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户号码',
  `mobile` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `nickname` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱|登录帐号',
  `pswd` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` bigint(1) DEFAULT '1' COMMENT '状态 1:有效，0:禁止登录',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='普通用户表';

-- ----------------------------
-- Records of t_biz_user
-- ----------------------------
INSERT INTO `t_biz_user` VALUES ('1', '159802735712', '15980273571', '15980273571', '', 'e10adc3949ba59abbe56e057f20f883e', '2018-04-27 00:00:00', '2018-04-27 16:08:05', '1');

-- ----------------------------
-- Table structure for t_deve_exp
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_exp`;
CREATE TABLE `t_deve_exp` (
  `exp_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '报表名称',
  `datasource` varchar(40) NOT NULL COMMENT '数据源',
  `code` varchar(30) DEFAULT NULL COMMENT '编码',
  `sqlx` varchar(1500) NOT NULL COMMENT '报表SQL',
  `script` varchar(1500) DEFAULT NULL COMMENT '页面脚本',
  `page_path` varchar(255) DEFAULT NULL COMMENT '页面路径',
  `txt_able` int(1) DEFAULT NULL COMMENT '是否导出TXT 1:是  0:否',
  `excel_able` int(1) DEFAULT NULL COMMENT '是否导出EXCEL 1:是  0:否',
  `printtitle_able` int(1) DEFAULT NULL COMMENT 'EXCEL是否打印标题 1:是  0:否',
  `begin_row` int(3) DEFAULT NULL COMMENT 'EXCEL导出开始行',
  `begin_col` int(3) DEFAULT NULL COMMENT 'EXCEL导出开始列',
  `excel_temp` varchar(400) DEFAULT NULL COMMENT 'EXCEL模板路径',
  `split_char` varchar(20) DEFAULT NULL COMMENT '文本分隔符',
  `encoding` varchar(30) DEFAULT NULL COMMENT '文件编码',
  `xml_able` int(1) DEFAULT NULL COMMENT '是否导出XML 1:是  0:否',
  `root_name` varchar(60) DEFAULT NULL COMMENT 'XML根节点名称',
  `nodes_name` varchar(60) DEFAULT NULL COMMENT 'XML节点名称',
  `line_char` varchar(20) DEFAULT NULL COMMENT '换行符',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `page_able` int(1) DEFAULT NULL COMMENT '是否生成页面 1:是  0:否',
  `fit_able` int(1) DEFAULT NULL COMMENT '是否FITCOLUMN 1:是  0:否 ',
  PRIMARY KEY (`exp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='通用查询、导出';

-- ----------------------------
-- Records of t_deve_exp
-- ----------------------------
INSERT INTO `t_deve_exp` VALUES ('64', '用户管理(带冻结、字典转换示例)', 'dataSourcePrimary', 'exp_100064', 'SELECT\nt.user_id,\nt.username,\nt.mobile,\nt.nickname,\nt.email,\nt.pswd,\nt.create_time,\nt.last_login_time,\nt.`status`\nFROM\nt_sys_user AS t where 1 = 1 #{username} #{mobile} #{status}', 'status', 'deve/deveExpPriviewList', '1', '1', '1', '0', '0', null, '/', 'UTF-8', '1', 'ROOT', 'NODE', null, '2017-11-17 11:47:16', '1', '0');

-- ----------------------------
-- Table structure for t_deve_exp_column
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_exp_column`;
CREATE TABLE `t_deve_exp_column` (
  `column_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '字段名',
  `title` varchar(100) DEFAULT NULL COMMENT '字段标题',
  `width` int(11) DEFAULT NULL COMMENT '字段宽度',
  `exp_id` int(11) DEFAULT NULL COMMENT '导出表主键',
  `seq` int(11) DEFAULT NULL COMMENT '排序号',
  `align` varchar(100) DEFAULT NULL COMMENT '对齐',
  `checkable` int(1) DEFAULT NULL COMMENT '是否复选框 1:是  0:否',
  `orderable` int(1) DEFAULT NULL COMMENT '是否排序 1:是  0:否',
  `showable` int(1) DEFAULT NULL COMMENT '是否显示 1:是  0:否',
  `forzenable` int(1) DEFAULT NULL COMMENT '是否冻结 1:是  0:否',
  `formater` varchar(500) DEFAULT NULL COMMENT '字段格式化',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=utf8 COMMENT='导出字段';

-- ----------------------------
-- Records of t_deve_exp_column
-- ----------------------------
INSERT INTO `t_deve_exp_column` VALUES ('205', 'ID', 'ID', '100', '63', '1', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('206', 'REALNAME', 'REALNAME', '100', '63', '2', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('207', 'PASSWORD', 'PASSWORD', '100', '63', '3', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('208', 'BIRTHDAY', 'BIRTHDAY', '100', '63', '4', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('209', 'V_SEQNO', 'V_SEQNO', '100', '63', '5', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('210', 'user_id', 'user_id', '200', '64', '1', 'center', '1', '1', '1', '1', null);
INSERT INTO `t_deve_exp_column` VALUES ('211', 'username', '用户名', '200', '64', '2', 'center', '0', '1', '1', '1', null);
INSERT INTO `t_deve_exp_column` VALUES ('212', 'mobile', '手机号码', '200', '64', '3', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('213', 'nickname', '昵称', '200', '64', '4', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('214', 'email', '邮箱', '500', '64', '5', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('215', 'pswd', '密122码', '200', '64', '6', 'center', '0', '1', '0', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('216', 'create_time', '创建时间', '200', '64', '7', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('217', 'last_login_time', '最后登录时间', '200', '64', '8', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('218', 'status', '状态', '200', '64', '9', 'center', '0', '1', '1', '0', 'formatter: _DICT.STATUS');
INSERT INTO `t_deve_exp_column` VALUES ('219', 'user_id', 'user_id', '100', '65', '1', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('220', 'username', 'username', '100', '65', '2', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('221', 'mobile', 'mobile', '100', '65', '3', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('222', 'nickname', 'nickname', '100', '65', '4', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('223', 'email', 'email', '100', '65', '5', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('224', 'pswd', 'pswd', '100', '65', '6', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('225', 'create_time', 'create_time', '100', '65', '7', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('226', 'last_login_time', 'last_login_time', '100', '65', '8', 'center', '0', '1', '1', '0', null);
INSERT INTO `t_deve_exp_column` VALUES ('227', 'status', 'status', '100', '65', '9', 'center', '0', '1', '1', '0', null);

-- ----------------------------
-- Table structure for t_deve_exp_param
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_exp_param`;
CREATE TABLE `t_deve_exp_param` (
  `param_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `exp_id` int(11) DEFAULT NULL COMMENT '导出模板主键',
  `name` varchar(100) DEFAULT NULL COMMENT '参数名',
  `title` varchar(100) DEFAULT NULL COMMENT '参数标题',
  `likeable` int(2) DEFAULT NULL COMMENT '是否模糊查询 1：是 0：否',
  `like_type` varchar(50) DEFAULT NULL COMMENT '模糊查询类型  full 全模糊 left 左模糊 right 右模糊',
  `dictable` int(2) DEFAULT NULL COMMENT '是否数据字典  1：是 0：否',
  `dict_type` varchar(40) DEFAULT NULL COMMENT '数据字典类型',
  `width` int(11) DEFAULT NULL COMMENT '显示宽度',
  `event` varchar(100) DEFAULT NULL COMMENT '触发事件',
  `form` varchar(300) DEFAULT NULL COMMENT '表单内容',
  `form_type` varchar(100) DEFAULT NULL COMMENT '表单类型',
  `seq` int(255) DEFAULT NULL COMMENT '排序号',
  `sqlx` varchar(800) DEFAULT NULL COMMENT '参数查询语句',
  `defalut_value` varchar(100) DEFAULT NULL COMMENT '默认值',
  PRIMARY KEY (`param_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='导出参数';

-- ----------------------------
-- Records of t_deve_exp_param
-- ----------------------------
INSERT INTO `t_deve_exp_param` VALUES ('42', '64', 'username', '用户名', '1', 'full', '0', null, '200', null, '<input type=\"text\" name=\"username\" class=\"easyui-textbox\" style=\"width: 200px\"/>', 'text', '1', 'and username like ?', null);
INSERT INTO `t_deve_exp_param` VALUES ('43', '64', 'mobile', '手机号码', '1', 'full', '0', null, '200', null, '<input type=\"text\" name=\"mobile\" class=\"easyui-textbox\" style=\"width: 200px\"/>', 'text', '1', 'and mobile like ?', null);
INSERT INTO `t_deve_exp_param` VALUES ('44', '64', 'status', '状态', '0', 'full', '1', 'status', '200', null, '<select class=\"easyui-combobox\" name=\"status\" style=\"width: 200px\" >\n<option value=\"\" >请选择</option>\n                            <option value=\"1\" >正常</option>\n                            <option value=\"0\">锁定</option>\n                        </select>', 'select', '1', 'and status = ?', null);
INSERT INTO `t_deve_exp_param` VALUES ('45', '453', 'sf', 'dfg', '1', 'full', '0', null, '200', null, '<input name=\"sf\" style=\"width: 200px\" value=\"\" class=\"easyui-textbox\" />', 'text', '1', 'ssss', null);

-- ----------------------------
-- Table structure for t_deve_file_info
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_file_info`;
CREATE TABLE `t_deve_file_info` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `upload_name` varchar(255) DEFAULT NULL COMMENT '文件上传名称',
  `save_name` varchar(50) DEFAULT NULL COMMENT '文件实际保存名称',
  `size` varchar(50) DEFAULT NULL COMMENT '文件大小',
  `type` varchar(10) DEFAULT NULL COMMENT '文件类型',
  `md5` varchar(50) DEFAULT NULL COMMENT '文件md5',
  `finish` int(1) DEFAULT NULL COMMENT '是否上传完成(0:未,1:完成)',
  `seq` int(4) DEFAULT NULL COMMENT '排序',
  `note` varchar(400) DEFAULT NULL COMMENT '备注',
  `path_type` varchar(1) DEFAULT NULL COMMENT '文件路径类型（系统参数）',
  `path` varchar(500) DEFAULT NULL COMMENT '文件路径',
  `path_all` varchar(500) DEFAULT NULL COMMENT '文件完整路径',
  `batcode` varchar(32) DEFAULT NULL COMMENT '上传批次号',
  `createuser` varchar(50) DEFAULT NULL COMMENT '创建人员',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8 COMMENT='文件信息表';

-- ----------------------------
-- Records of t_deve_file_info
-- ----------------------------
INSERT INTO `t_deve_file_info` VALUES ('72', '2018年技术人员结构.xlsx', 'c3aca855-9398-49c8-9c89-bbc948bd0b98.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\c3aca855-9398-49c8-9c89-bbc948bd0b98.xlsx', null, 'admin', '2018-01-22 09:01:11');
INSERT INTO `t_deve_file_info` VALUES ('75', 'change_wallpaper.zip', 'ef174547-19d4-4953-9807-466aac833ce0.zip', '91.25 KB', 'zip', null, '1', null, null, null, null, 'e:\\\\test\\\\\\ef174547-19d4-4953-9807-466aac833ce0.zip', null, 'admin', '2018-01-22 14:13:30');
INSERT INTO `t_deve_file_info` VALUES ('77', '2018年技术人员结构.xlsx', '2e73f0af-e29a-48a3-90e0-596ceecf6bb3.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\2e73f0af-e29a-48a3-90e0-596ceecf6bb3.xlsx', null, 'admin', '2018-01-26 11:22:53');
INSERT INTO `t_deve_file_info` VALUES ('80', '2018年技术人员结构.xlsx', '17495534-8eb5-46b5-9b1c-5cbd9b25791b.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\17495534-8eb5-46b5-9b1c-5cbd9b25791b.xlsx', null, 'admin', '2018-02-02 09:55:02');
INSERT INTO `t_deve_file_info` VALUES ('81', '2018年技术人员结构.xlsx', 'f7a082df-dd13-4df3-8d66-22f12a83525d.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\f7a082df-dd13-4df3-8d66-22f12a83525d.xlsx', null, 'admin', '2018-02-02 09:56:28');
INSERT INTO `t_deve_file_info` VALUES ('82', '2018年技术人员结构.xlsx', 'bf10246b-e4a4-47c6-a754-ff16aed2ab79.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\bf10246b-e4a4-47c6-a754-ff16aed2ab79.xlsx', null, 'admin', '2018-02-02 10:04:31');
INSERT INTO `t_deve_file_info` VALUES ('83', '2018年技术人员结构.xlsx', 'c7974705-4aa3-4e74-ba0b-7d894c498e61.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\c7974705-4aa3-4e74-ba0b-7d894c498e61.xlsx', null, 'admin', '2018-02-02 10:05:25');
INSERT INTO `t_deve_file_info` VALUES ('87', '2018年技术人员结构.xlsx', '3607c7d8-82fa-482e-a3a7-0ea11d70a74d.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\/3607c7d8-82fa-482e-a3a7-0ea11d70a74d.xlsx', null, 'admin', '2018-02-06 09:48:11');
INSERT INTO `t_deve_file_info` VALUES ('89', '资源情况说明.xlsx', 'd6171dd1-f0df-401b-843d-8495f5189fc1.xlsx', '15.32 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\d6171dd1-f0df-401b-843d-8495f5189fc1.xlsx', null, 'admin', '2018-02-06 11:42:32');
INSERT INTO `t_deve_file_info` VALUES ('90', '2017年责任清单任务完成评价表.xlsx', 'df5542c3-17fa-457e-a40a-ff0bf05f81c0.xlsx', '11.93 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\df5542c3-17fa-457e-a40a-ff0bf05f81c0.xlsx', null, 'admin', '2018-02-06 11:54:27');
INSERT INTO `t_deve_file_info` VALUES ('91', '春节放假登记表.xlsx', '6e89e88c-b96e-4b80-9d2a-dd14e9f34ab8.xlsx', '9.95 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\6e89e88c-b96e-4b80-9d2a-dd14e9f34ab8.xlsx', null, 'admin', '2018-02-06 11:54:27');
INSERT INTO `t_deve_file_info` VALUES ('92', '2017年责任清单任务完成评价表.xlsx', '7f5aae3a-9923-4f57-9be8-c4e631884bc0.xlsx', '11.93 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\7f5aae3a-9923-4f57-9be8-c4e631884bc0.xlsx', null, 'admin', '2018-02-06 11:55:34');
INSERT INTO `t_deve_file_info` VALUES ('93', '春节放假登记表.xlsx', '08ebcb4e-6e7e-4277-80a9-0d2f5d1030cc.xlsx', '9.95 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\08ebcb4e-6e7e-4277-80a9-0d2f5d1030cc.xlsx', null, 'admin', '2018-02-06 11:55:34');
INSERT INTO `t_deve_file_info` VALUES ('94', '2017年责任清单任务完成评价表.xlsx', '4b8d696e-2999-4f90-a5a4-4d1c19186786.xlsx', '11.93 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\4b8d696e-2999-4f90-a5a4-4d1c19186786.xlsx', null, 'admin', '2018-02-06 11:58:11');
INSERT INTO `t_deve_file_info` VALUES ('95', '2018年技术人员结构.xlsx', '1fbe0ac0-03f7-4599-8857-be7028aa8a96.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\1fbe0ac0-03f7-4599-8857-be7028aa8a96.xlsx', null, 'admin', '2018-02-06 11:58:11');
INSERT INTO `t_deve_file_info` VALUES ('102', '2018年技术人员结构.xlsx', '5d4b0e48-b860-4c2f-adcd-e570cfae535f.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\5d4b0e48-b860-4c2f-adcd-e570cfae535f.xlsx', null, 'admin', '2018-02-06 13:56:37');
INSERT INTO `t_deve_file_info` VALUES ('103', '春节放假登记表.xlsx', '98c62abf-5547-4a82-8958-b5167909cd00.xlsx', '9.95 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\98c62abf-5547-4a82-8958-b5167909cd00.xlsx', null, 'admin', '2018-02-06 13:56:37');
INSERT INTO `t_deve_file_info` VALUES ('106', '2018年技术人员结构.xlsx', 'bfc5cc28-5ec2-429a-a82f-3aa63abf69b9.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\bfc5cc28-5ec2-429a-a82f-3aa63abf69b9.xlsx', null, 'admin', '2018-02-06 14:07:10');
INSERT INTO `t_deve_file_info` VALUES ('107', '春节放假登记表.xlsx', '5cf260a5-3bb6-497d-8769-5245b98c4b80.xlsx', '9.95 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\5cf260a5-3bb6-497d-8769-5245b98c4b80.xlsx', null, 'admin', '2018-02-06 14:07:10');
INSERT INTO `t_deve_file_info` VALUES ('114', '春节放假登记表.xlsx', '735bb024-1b64-4c80-a899-fa794d68808a.xlsx', '9.95 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\735bb024-1b64-4c80-a899-fa794d68808a.xlsx', null, 'admin', '2018-02-06 17:05:13');
INSERT INTO `t_deve_file_info` VALUES ('115', '2018年技术人员结构.xlsx', 'a5976172-1639-46c7-858a-d6cac23b0b43.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\a5976172-1639-46c7-858a-d6cac23b0b43.xlsx', null, 'admin', '2018-02-06 17:05:13');
INSERT INTO `t_deve_file_info` VALUES ('119', '2018年技术人员结构.xlsx', '9213b484-e77b-48db-93b1-78b48fbca64e.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\9213b484-e77b-48db-93b1-78b48fbca64e.xlsx', null, 'admin', '2018-02-07 10:17:33');
INSERT INTO `t_deve_file_info` VALUES ('121', '资源情况说明.xlsx', 'c8eee12c-253e-437a-90f7-df04a0f61e74.xlsx', '15.32 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\c8eee12c-253e-437a-90f7-df04a0f61e74.xlsx', null, 'admin', '2018-02-07 10:27:53');
INSERT INTO `t_deve_file_info` VALUES ('122', '2018年技术人员结构.xlsx', '4df4c1e4-41b2-44a7-8ced-7e7882d830be.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\4df4c1e4-41b2-44a7-8ced-7e7882d830be.xlsx', null, 'admin', '2018-02-07 10:36:44');
INSERT INTO `t_deve_file_info` VALUES ('123', '春节放假登记表.xlsx', '4e8f2cd9-66a1-4a88-9923-9010c98549a4.xlsx', '9.95 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\4e8f2cd9-66a1-4a88-9923-9010c98549a4.xlsx', null, 'admin', '2018-02-07 10:36:44');
INSERT INTO `t_deve_file_info` VALUES ('127', '2017年责任清单任务完成评价表.xlsx', '0fc80ea3-0da7-4693-8120-c23481e8933c.xlsx', '11.93 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\0fc80ea3-0da7-4693-8120-c23481e8933c.xlsx', null, 'admin', '2018-02-07 11:10:43');
INSERT INTO `t_deve_file_info` VALUES ('128', '2018年技术人员结构.xlsx', '4382a77b-9cac-4511-bb11-ce1653b48876.xlsx', '13.39 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\4382a77b-9cac-4511-bb11-ce1653b48876.xlsx', null, 'admin', '2018-02-07 11:11:23');
INSERT INTO `t_deve_file_info` VALUES ('130', '春节放假登记表.xlsx', 'ebb6e6b7-3375-4b71-a361-ecb021ed1eeb.xlsx', '9.95 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\ebb6e6b7-3375-4b71-a361-ecb021ed1eeb.xlsx', null, 'admin', '2018-02-07 11:11:44');
INSERT INTO `t_deve_file_info` VALUES ('131', '资源情况说明.xlsx', '277fa2be-8a5f-4791-bbd3-22c111d5cfcc.xlsx', '15.32 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\277fa2be-8a5f-4791-bbd3-22c111d5cfcc.xlsx', null, 'admin', '2018-02-07 11:12:24');
INSERT INTO `t_deve_file_info` VALUES ('132', '春节放假登记表.xlsx', 'd532592f-caab-4985-81e1-c310f3bf9ab3.xlsx', '9.95 KB', 'xlsx', null, '1', null, null, null, null, 'e:\\\\test\\\\\\d532592f-caab-4985-81e1-c310f3bf9ab3.xlsx', null, 'admin', '2018-02-07 11:12:50');
INSERT INTO `t_deve_file_info` VALUES ('133', 'errorpage.zip', 'babbdce9-6808-4163-b430-0fce5b4864f6.zip', '19.30 KB', 'zip', null, '1', null, null, null, null, 'e:\\\\test\\\\\\babbdce9-6808-4163-b430-0fce5b4864f6.zip', null, 'admin', '2018-04-23 17:53:56');
INSERT INTO `t_deve_file_info` VALUES ('134', '研究开发项目的基本情况说明2017-二.xls', '9f6673b6-f9bb-4a64-87b4-2bd08fb3139c.xls', '41.00 KB', 'xls', null, '1', null, null, null, null, 'e:\\\\test\\\\\\9f6673b6-f9bb-4a64-87b4-2bd08fb3139c.xls', null, 'admin', '2018-04-27 16:05:32');
INSERT INTO `t_deve_file_info` VALUES ('135', '研究开发项目的基本情况说明2017-二.xls', '26dccb38-f620-415a-ac39-ae67b1f94b1b.xls', '41.00 KB', 'xls', null, '1', null, null, null, null, 'e:\\\\test\\\\\\26dccb38-f620-415a-ac39-ae67b1f94b1b.xls', null, 'admin', '2018-04-27 16:26:58');

-- ----------------------------
-- Table structure for t_deve_file_link
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_file_link`;
CREATE TABLE `t_deve_file_link` (
  `ID` varchar(40) NOT NULL COMMENT 'ID',
  `V_FILEID` varchar(40) DEFAULT NULL COMMENT '文件ID',
  `V_BUSID` varchar(40) DEFAULT NULL COMMENT '业务ID',
  `V_NODEID` varchar(40) DEFAULT NULL COMMENT '节点ID',
  `V_USERID` varchar(40) DEFAULT NULL COMMENT '用户ID',
  `V_CREATETIME` varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件关联关系表';

-- ----------------------------
-- Records of t_deve_file_link
-- ----------------------------
INSERT INTO `t_deve_file_link` VALUES ('4b3dc4885b444f8fa2200a78960f12c2', 'a3e8c1fcf2594c22b27afd01f2256103', '3c3d71088ea44170be8676f1ff39b009', null, '0001', '2016-11-06 16:52:25');
INSERT INTO `t_deve_file_link` VALUES ('4e92660713b54ece8355aa34ba7bb3aa', '9002687709184c5b94b267fcc8bd9b55', '7697b257b78d4d34af73fa7a5932e4de', null, '0001', '2016-07-19 16:26:37');
INSERT INTO `t_deve_file_link` VALUES ('7079251393814f77bcaa917558236d27', 'd7f27a1d43b04d919c4bb93835db4ae9', '0857663e74b146bbab097ecc72294d46', null, '0001', '2016-07-19 16:22:10');
INSERT INTO `t_deve_file_link` VALUES ('89a0eee456c5479c85d91b827a3cbd68', '85fbad7f5f4d401585e6b3ec5c0cbba6', '56369c4c9d7549768fee93daadcc9153', null, '0001', '2016-07-19 16:27:44');
INSERT INTO `t_deve_file_link` VALUES ('a89ea16c9e3047b6a7966493da0f0ec2', '33181735984347c1be304e8561b479a7', '2f9c83dc89e847999ca518c455a89998', null, '0001', '2016-07-19 16:20:43');
INSERT INTO `t_deve_file_link` VALUES ('d9ccf40e162d4fa6b35373a6235d4eb8', '26de80ddafaf4f3b9f306c0b4a9360d5', 'db39b95cf77d4ff08e4600c7f24a049a', null, '0001', '2016-07-19 16:20:20');
INSERT INTO `t_deve_file_link` VALUES ('e395ebbbc52d4933a1346e0f2f7a3738', 'a6b0499cc5974e65bdc4f7afc6553322', '2d5f491d8ba44bb0becffffacc2ccda4', null, '0001', '2016-11-06 16:52:15');
INSERT INTO `t_deve_file_link` VALUES ('e55969858cf341a9900dd97ef4cadf23', '021243cdcad24af1927cd0227d73bca2', '195258235406469c9dd21eb46def7c6a', null, '0001', '2017-05-19 09:09:02');

-- ----------------------------
-- Table structure for t_deve_object
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_object`;
CREATE TABLE `t_deve_object` (
  `obj_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '对象主键',
  `code` varchar(40) CHARACTER SET utf8 NOT NULL COMMENT '对象编码',
  `name` varchar(40) CHARACTER SET utf8 NOT NULL COMMENT '对象名称',
  `db_name` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '保存数据主表或查询数据视图',
  `type` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '类型  TABLE:表 VIEW:视图',
  `pk_name` varchar(40) CHARACTER SET utf8 DEFAULT NULL COMMENT '主键',
  `data_source` varchar(40) CHARACTER SET utf8 DEFAULT 'main' COMMENT '数据源',
  `status` int(1) DEFAULT NULL COMMENT '状态 1：可用 0：不可用',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `add_user` int(11) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` int(11) DEFAULT NULL COMMENT '修改人员',
  PRIMARY KEY (`obj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='二次开发-对象表';

-- ----------------------------
-- Records of t_deve_object
-- ----------------------------
INSERT INTO `t_deve_object` VALUES ('200', 't_biz_user', 'ss', 't_biz_user', 'TABLE', 'user_id', 'dataSourcePrimary', '1', '2018-04-27 16:07:46', '15', null, null);

-- ----------------------------
-- Table structure for t_deve_object_column
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_object_column`;
CREATE TABLE `t_deve_object_column` (
  `column_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字段id',
  `method_id` int(11) DEFAULT NULL COMMENT '方法id',
  `ename` varchar(60) DEFAULT NULL COMMENT '字段名',
  `cname` varchar(60) DEFAULT NULL COMMENT '字段标题',
  `width` int(4) DEFAULT NULL COMMENT '字段显示宽度',
  `height` int(4) DEFAULT NULL COMMENT '高度',
  `form_type` varchar(40) DEFAULT NULL COMMENT '表单类型',
  `form_str` varchar(255) DEFAULT NULL COMMENT '表单字符',
  `default_value` varchar(100) DEFAULT NULL COMMENT '默认值',
  `cols` int(1) DEFAULT '1' COMMENT '占用列数',
  `seq` int(4) DEFAULT NULL COMMENT '序列',
  `show_able` int(1) DEFAULT NULL COMMENT '是否显示 1:是,0:否',
  `frozen_able` int(1) DEFAULT NULL COMMENT '是否冻结',
  `order_able` int(1) DEFAULT NULL COMMENT '是否排序 1：是 0：否',
  `dict_able` int(1) DEFAULT NULL COMMENT '是否需要字典字段转换  1:是,0:否',
  `dict_str` varchar(120) DEFAULT NULL COMMENT '字典转换内容对照',
  `align` varchar(30) DEFAULT NULL COMMENT '字段对齐方式',
  `checkbox_able` int(1) DEFAULT NULL COMMENT '是否是复选框',
  `css` varchar(400) DEFAULT NULL COMMENT '字段样式',
  `uiCheck` varchar(255) DEFAULT NULL COMMENT 'UI校验器',
  `invalidMessage` varchar(255) DEFAULT NULL COMMENT '被验证为无效时提示',
  `formatter` varchar(1500) DEFAULT NULL COMMENT '格式化',
  `enent` varchar(1000) DEFAULT NULL COMMENT '字段事件',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14311 DEFAULT CHARSET=utf8 COMMENT='二次开发-模块管理--字段管理';

-- ----------------------------
-- Records of t_deve_object_column
-- ----------------------------
INSERT INTO `t_deve_object_column` VALUES ('1001', '-1', 'name', '名称,昵称', '200', '-1', 'easyui-textbox', '<input id=\"name\" name=\"name\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"required:true,validType:[\'length[0,20]\'] \"/>', '', '1', '2', '1', '0', '1', '0', '', 'center', '0', '', 'required:true,validType:[\'length[0,20]\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('1002', '-1', 'email', '电子邮箱', '200', '-1', 'easyui-textbox', '<input id=\"email\" name=\"email\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,30]\',\'email\'] \"/>', '', '1', '3', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,30]\',\'email\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('1003', '-1', 'phone', '电话号码', '200', '-1', 'easyui-textbox', '<input id=\"phone\" name=\"phone\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,13]\',\'TEL\'] \"/>', '', '1', '4', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,13]\',\'TEL\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('1004', '-1', 'mobile', '手机号码', '200', '-1', 'easyui-numberbox', '<input id=\"mobile\" name=\"mobile\" class=\"easyui-numberbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,13]\',\'mobile\'] \"/>', '', '1', '5', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,13]\',\'mobile\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('1005', '-1', 'birthday', '生日', '200', '-1', 'easyui-datebox', '<input id=\"birthday\" name=\"birthday\" class=\"easyui-datebox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,20]\',\'DATATIME\'] \"/>', '', '1', '6', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,20]\',\'DATATIME\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('1006', '-1', 'file', '附件', '200', '-1', 'file', '<input id=\"file\" name=\"file\" type=\"file\" class=\"xlauch-file\">', '', '3', '20', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,255]\']', '', 'formatter: showFile', '');
INSERT INTO `t_deve_object_column` VALUES ('1007', '-1', 'date', '日期', '200', '-1', 'easyui-datebox', '<input id=\"date\" name=\"date\" class=\"easyui-datebox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,20]\',\'DATATIME\'] \"/>', '', '1', '6', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,20]\',\'DATATIME\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('1008', '-1', 'id', '主键,pk,id', '200', '-1', 'hidden', '<input id=\"id\" name=\"id\" type=\"hidden\">', 'PK', '1', '99', '1', '0', '1', '0', '', 'center', '0', '', '', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('1009', '-1', 'note', '备注', '610', '50', 'textArea', '<input id=\"note\" name=\"note\" class=\"easyui-textbox\" style=\"width: 610px;height:50px \" multiline=\"true\"   data-options=\"validType:[\'length[0,600]\',\'mobile\'] \"/>', '', '3', '99', '0', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,600]\',\'mobile\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('1010', '-1', 'pwd', '密钥', '200', '-1', 'easyui-passwordbox', '<input id=\"pwd\" name=\"pwd\" class=\"easyui-passwordbox\" style=\"width: 200px; \"    data-options=\"required:true,validType:[\'length[0,20]\'] \"/>', '', '1', '99', '0', '0', '0', '0', '', 'center', '0', '', 'required:true,validType:[\'length[0,20]\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('10010', '-1', 'descript', '描述', '610', '50', 'textArea', '<input id=\"descript\" name=\"descript\" class=\"easyui-textbox\" style=\"width: 610px;height:50px \" multiline=\"true\"   data-options=\"validType:[\'length[0,600]\',\'mobile\'] \"/>', '', '3', '99', '0', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,600]\',\'mobile\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('10103', '-1', 'password', '密码,密钥', '200', '-1', 'easyui-passwordbox', '<input id=\"password\" name=\"password\" class=\"easyui-passwordbox\" style=\"width: 200px; \"    data-options=\"required:true,validType:[\'length[0,20]\'] \"/>', '', '1', '99', '0', '0', '0', '0', '', 'center', '0', '', 'required:true,validType:[\'length[0,20]\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('10104', '-1', 'addtime', '创建时间,新增时间', '200', '-1', 'hidden', '<input id=\"addtime\" name=\"addtime\" type=\"hidden\">', 'DATE', '1', '99', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,20]\',\'DATATIME\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('10105', '-1', 'updatetime', '最后登录时间,最后修改时间', '200', '-1', 'hidden', '<input id=\"updatetime\" name=\"updatetime\" type=\"hidden\">', 'NOW', '1', '99', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,20]\',\'DATATIME\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('10106', '-1', 'status', '状态', '200', '-1', 'radio', '<#dictTag name=\"status\" showType=\"radio\" dictCode=\"\" width=\"200\" value=\"1\"  showNull=\"false\" ></#dictTag>', '1', '1', '99', '1', '0', '1', '1', 'status', 'center', '0', null, null, null, 'formatter: _DICT.STATUS', null);
INSERT INTO `t_deve_object_column` VALUES ('10143', '-1', 'is', '是否', '200', '-1', 'radio', '<#dictTag name=\"is\" showType=\"radio\" dictCode=\"yesornot\" width=\"200\" value=\"1\"  showNull=\"false\" ></#dictTag>', '1', '1', '99', '1', '0', '1', '1', 'yesornot', 'center', '0', null, null, null, 'formatter: _DICT.YESORNOT', null);
INSERT INTO `t_deve_object_column` VALUES ('10144', null, null, null, null, null, null, null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('10145', '-1', 'sex', '性别', '200', '-1', 'radio', '<#dictTag name=\"sex\" showType=\"radio\" dictCode=\"sex\" width=\"200\" value=\"1\"  showNull=\"false\" ></#dictTag>', '1', '1', '99', '1', '0', '1', '1', 'sex', 'center', '0', '', '', '', 'formatter: _DICT.SEX', '');
INSERT INTO `t_deve_object_column` VALUES ('10146', '-1', 'money', '金额', '200', '-1', 'easyui-numberbox', '<input id=\"money\" name=\"money\" class=\"easyui-numberbox\" style=\"width: 200px; \"    data-options=\"min:0,precision:2,validType:[\'length[0,20]\'] \"/>', '', '1', '99', '1', '0', '0', '0', '', 'center', '0', '', 'min:0,precision:2,validType:[\'length[0,20]\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('12492', '-1', 'textDefault', '文本默认格式', '200', '-1', 'easyui-textbox', '<input id=\"textDefault\" name=\"textDefault\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,20]\'] \"/>', '', '1', '1', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,20]\']', '', '', '');
INSERT INTO `t_deve_object_column` VALUES ('14275', '724', 'user_id', '主键', '200', '-1', 'hidden', '<input id=\"user_id\" name=\"user_id\" type=\"hidden\">', 'PK', '1', '1', '1', '0', '1', '0', null, 'center', '1', null, null, null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14276', '724', 'username', '用户号码', '200', '-1', 'easyui-textbox', '<input id=\"username\" name=\"username\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\'] \"/>', null, '1', '2', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14277', '724', 'mobile', '手机号码', '200', '-1', 'easyui-numberbox', '<input id=\"mobile\" name=\"mobile\" class=\"easyui-numberbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\',\'mobile\'] \"/>', null, '1', '3', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'mobile\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14278', '724', 'nickname', '用户昵称', '200', '-1', 'easyui-textbox', '<input id=\"nickname\" name=\"nickname\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"required:true,validType:[\'length[0,200]\'] \"/>', null, '1', '4', '1', '0', '1', '0', null, 'center', '0', null, 'required:true,validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14279', '724', 'email', '邮箱|登录帐号', '200', '-1', 'easyui-textbox', '<input id=\"email\" name=\"email\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\',\'email\'] \"/>', null, '1', '5', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'email\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14280', '724', 'pswd', '密码', '200', '-1', 'easyui-passwordbox', '<input id=\"pswd\" name=\"pswd\" class=\"easyui-passwordbox\" style=\"width: 200px; \"    data-options=\"required:true,validType:[\'length[0,200]\'] \"/>', null, '1', '6', '0', '0', '0', '0', null, 'center', '0', null, 'required:true,validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14281', '724', 'create_time', '创建时间', '200', '-1', 'hidden', '<input id=\"create_time\" name=\"create_time\" type=\"hidden\">', 'DATE', '1', '7', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'DATATIME\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14282', '724', 'last_login_time', '最后登录时间', '200', '-1', 'hidden', '<input id=\"last_login_time\" name=\"last_login_time\" type=\"hidden\">', 'NOW', '1', '8', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'DATATIME\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14283', '724', 'status', '状态 1:有效，0:禁止登录', '200', '-1', 'radio', '<#dstatus\"status\" showType=\"radio\" dictCode=\"\" width=\"200\" value=\"1\"  showNull=\"false\" ></#dictTag>', '1', '1', '9', '1', '0', '1', '1', 'status', 'center', '0', null, null, null, 'formatter: _DICT.STATUS', null);
INSERT INTO `t_deve_object_column` VALUES ('14284', '725', 'user_id', '主键', '200', '-1', 'hidden', '<input id=\"user_id\" name=\"user_id\" type=\"hidden\">', 'PK', '1', '1', '1', '0', '1', '0', null, 'center', '1', null, null, null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14285', '725', 'username', '用户号码', '200', '-1', 'easyui-textbox', '<input id=\"username\" name=\"username\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\'] \"/>', null, '1', '2', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14286', '725', 'mobile', '手机号码', '200', '-1', 'easyui-numberbox', '<input id=\"mobile\" name=\"mobile\" class=\"easyui-numberbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\',\'mobile\'] \"/>', null, '1', '3', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'mobile\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14287', '725', 'nickname', '用户昵称', '200', '-1', 'easyui-textbox', '<input id=\"nickname\" name=\"nickname\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"required:true,validType:[\'length[0,200]\'] \"/>', null, '1', '4', '1', '0', '1', '0', null, 'center', '0', null, 'required:true,validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14288', '725', 'email', '邮箱|登录帐号', '200', '-1', 'easyui-textbox', '<input id=\"email\" name=\"email\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\',\'email\'] \"/>', null, '1', '5', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'email\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14289', '725', 'pswd', '密码', '200', '-1', 'easyui-passwordbox', '<input id=\"pswd\" name=\"pswd\" class=\"easyui-passwordbox\" style=\"width: 200px; \"    data-options=\"required:true,validType:[\'length[0,200]\'] \"/>', null, '1', '6', '0', '0', '0', '0', null, 'center', '0', null, 'required:true,validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14290', '725', 'create_time', '创建时间', '200', '-1', 'hidden', '<input id=\"create_time\" name=\"create_time\" type=\"hidden\">', 'DATE', '1', '7', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'DATATIME\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14291', '725', 'last_login_time', '最后登录时间', '200', '-1', 'hidden', '<input id=\"last_login_time\" name=\"last_login_time\" type=\"hidden\">', 'NOW', '1', '8', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'DATATIME\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14292', '725', 'status', '状态 1:有效，0:禁止登录', '200', '-1', 'radio', '<#dstatus\"status\" showType=\"radio\" dictCode=\"\" width=\"200\" value=\"1\"  showNull=\"false\" ></#dictTag>', '1', '1', '9', '1', '0', '1', '1', 'status', 'center', '0', null, null, null, 'formatter: _DICT.STATUS', null);
INSERT INTO `t_deve_object_column` VALUES ('14293', '726', 'user_id', '主键', '200', '-1', 'hidden', '<input id=\"user_id\" name=\"user_id\" type=\"hidden\">', 'PK', '1', '1', '1', '0', '1', '0', null, 'center', '1', null, null, null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14294', '726', 'username', '用户号码', '200', '-1', 'easyui-textbox', '<input id=\"username\" name=\"username\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\'] \"/>', null, '1', '2', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14295', '726', 'mobile', '手机号码', '200', '-1', 'easyui-numberbox', '<input id=\"mobile\" name=\"mobile\" class=\"easyui-numberbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\',\'mobile\'] \"/>', null, '1', '3', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'mobile\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14296', '726', 'nickname', '用户昵称', '200', '-1', 'easyui-textbox', '<input id=\"nickname\" name=\"nickname\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"required:true,validType:[\'length[0,200]\'] \"/>', null, '1', '4', '1', '0', '1', '0', null, 'center', '0', null, 'required:true,validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14297', '726', 'email', '邮箱|登录帐号', '200', '-1', 'easyui-textbox', '<input id=\"email\" name=\"email\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\',\'email\'] \"/>', null, '1', '5', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'email\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14298', '726', 'pswd', '密码', '200', '-1', 'easyui-passwordbox', '<input id=\"pswd\" name=\"pswd\" class=\"easyui-passwordbox\" style=\"width: 200px; \"    data-options=\"required:true,validType:[\'length[0,200]\'] \"/>', null, '1', '6', '0', '0', '0', '0', null, 'center', '0', null, 'required:true,validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14299', '726', 'create_time', '创建时间', '200', '-1', 'hidden', '<input id=\"create_time\" name=\"create_time\" type=\"hidden\">', 'DATE', '1', '7', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'DATATIME\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14300', '726', 'last_login_time', '最后登录时间', '200', '-1', 'hidden', '<input id=\"last_login_time\" name=\"last_login_time\" type=\"hidden\">', 'NOW', '1', '8', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'DATATIME\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14301', '726', 'status', '状态 1:有效，0:禁止登录', '200', '-1', 'radio', '<#dstatus\"status\" showType=\"radio\" dictCode=\"\" width=\"200\" value=\"1\"  showNull=\"false\" ></#dictTag>', '1', '1', '9', '1', '0', '1', '1', 'status', 'center', '0', null, null, null, 'formatter: _DICT.STATUS', null);
INSERT INTO `t_deve_object_column` VALUES ('14302', '727', 'user_id', '主键', '200', '-1', 'hidden', '<input id=\"user_id\" name=\"user_id\" type=\"hidden\">', 'PK', '1', '1', '1', '0', '1', '0', null, 'center', '1', null, null, null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14303', '727', 'username', '用户号码', '200', '-1', 'easyui-textbox', '<input id=\"username\" name=\"username\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\'] \"/>', null, '1', '2', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14304', '727', 'mobile', '手机号码', '200', '-1', 'easyui-numberbox', '<input id=\"mobile\" name=\"mobile\" class=\"easyui-numberbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\',\'mobile\'] \"/>', null, '1', '3', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'mobile\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14305', '727', 'nickname', '用户昵称', '200', '-1', 'easyui-textbox', '<input id=\"nickname\" name=\"nickname\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"required:true,validType:[\'length[0,200]\'] \"/>', null, '1', '4', '1', '0', '1', '0', null, 'center', '0', null, 'required:true,validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14306', '727', 'email', '邮箱|登录帐号', '200', '-1', 'easyui-textbox', '<input id=\"email\" name=\"email\" class=\"easyui-textbox\" style=\"width: 200px; \"    data-options=\"validType:[\'length[0,200]\',\'email\'] \"/>', null, '1', '5', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'email\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14307', '727', 'pswd', '密码', '200', '-1', 'easyui-passwordbox', '<input id=\"pswd\" name=\"pswd\" class=\"easyui-passwordbox\" style=\"width: 200px; \"    data-options=\"required:true,validType:[\'length[0,200]\'] \"/>', null, '1', '6', '0', '0', '0', '0', null, 'center', '0', null, 'required:true,validType:[\'length[0,200]\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14308', '727', 'create_time', '创建时间', '200', '-1', 'hidden', '<input id=\"create_time\" name=\"create_time\" type=\"hidden\">', 'DATE', '1', '7', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'DATATIME\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14309', '727', 'last_login_time', '最后登录时间', '200', '-1', 'hidden', '<input id=\"last_login_time\" name=\"last_login_time\" type=\"hidden\">', 'NOW', '1', '8', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,200]\',\'DATATIME\']', null, null, null);
INSERT INTO `t_deve_object_column` VALUES ('14310', '727', 'status', '状态 1:有效，0:禁止登录', '200', '-1', 'radio', '<#dstatus\"status\" showType=\"radio\" dictCode=\"\" width=\"200\" value=\"1\"  showNull=\"false\" ></#dictTag>', '1', '1', '9', '1', '0', '1', '1', 'status', 'center', '0', null, null, null, 'formatter: _DICT.STATUS', null);

-- ----------------------------
-- Table structure for t_deve_object_column_copy
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_object_column_copy`;
CREATE TABLE `t_deve_object_column_copy` (
  `column_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字段id',
  `method_id` int(11) DEFAULT NULL COMMENT '方法id',
  `ename` varchar(60) DEFAULT NULL COMMENT '字段名',
  `cname` varchar(60) DEFAULT NULL COMMENT '字段标题',
  `width` int(4) DEFAULT NULL COMMENT '字段显示宽度',
  `height` int(4) DEFAULT NULL COMMENT '高度',
  `form_type` varchar(40) DEFAULT NULL COMMENT '表单类型',
  `form_str` varchar(255) DEFAULT NULL COMMENT '表单字符',
  `default_value` varchar(100) DEFAULT NULL COMMENT '默认值',
  `cols` int(1) DEFAULT '1' COMMENT '占用列数',
  `seq` int(4) DEFAULT NULL COMMENT '序列',
  `show_able` int(1) DEFAULT NULL COMMENT '是否显示 1:是,0:否',
  `frozen_able` int(1) DEFAULT NULL COMMENT '是否冻结',
  `order_able` int(1) DEFAULT NULL COMMENT '是否排序 1：是 0：否',
  `dict_able` int(1) DEFAULT NULL COMMENT '是否需要字典字段转换  1:是,0:否',
  `dict_str` varchar(120) DEFAULT NULL COMMENT '字典转换内容对照',
  `align` varchar(30) DEFAULT NULL COMMENT '字段对齐方式',
  `checkbox_able` int(1) DEFAULT NULL COMMENT '是否是复选框',
  `css` varchar(400) DEFAULT NULL COMMENT '字段样式',
  `uiCheck` varchar(255) DEFAULT NULL COMMENT 'UI校验器',
  `invalidMessage` varchar(255) DEFAULT NULL COMMENT '被验证为无效时提示',
  `formatter` varchar(1500) DEFAULT NULL COMMENT '格式化',
  `enent` varchar(1000) DEFAULT NULL COMMENT '字段事件',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10147 DEFAULT CHARSET=utf8 COMMENT='二次开发-模块管理--字段管理';

-- ----------------------------
-- Records of t_deve_object_column_copy
-- ----------------------------
INSERT INTO `t_deve_object_column_copy` VALUES ('1001', '-1', 'name', '名称,昵称', '200', '-1', 'easyui-textbox', '<input name=\"name\" class=\"easyui-textbox\" style=\"width: 200px; \"   value=\"\" data-options=\"required:true,validType:[\'length[0,20]\'] \"/>', '', '1', '0', '1', '0', '1', '0', '', 'center', '0', '', 'required:true,validType:[\'length[0,20]\']', '', '', '');
INSERT INTO `t_deve_object_column_copy` VALUES ('1002', '-1', 'email', '电子邮箱', '200', '-1', 'easyui-textbox', '<input name=\"email\" class=\"easyui-textbox\" style=\"width: 200px; \"   value=\"\" data-options=\"validType:[\'length[0,30]\',\'email\'] \"/>', '', '1', '3', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,30]\',\'email\']', '', '', '');
INSERT INTO `t_deve_object_column_copy` VALUES ('1003', '-1', 'phone', '电话号码', '200', '-1', 'easyui-textbox', '<input name=\"phone\" class=\"easyui-textbox\" style=\"width: 200px;\"   value=\"\" data-options=\"validType:[\'length[0,13]\',\'TEL\'] \"/>', '', '1', '4', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,13]\',\'TEL\']', '', '', '');
INSERT INTO `t_deve_object_column_copy` VALUES ('1004', '-1', 'mobile', '手机号码', '200', null, 'easyui-numberbox', '<input name=\"mobile\" class=\"easyui-numberbox\" style=\"width: 200px; \"  data-options=\"validType:[\'length[0,13]\',\'mobile\']\" /> ', null, '1', '5', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,13]\',\'mobile\']', null, null, null);
INSERT INTO `t_deve_object_column_copy` VALUES ('1005', '-1', 'birthday', '生日', '200', '-1', 'easyui-datebox', '<input name=\"birthday\" class=\"easyui-datebox\" style=\"width: 200px; \"   value=\"\" data-options=\"validType:[\'length[0,20]\',\'DATATIME\'] \"/>', '', '1', '6', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,20]\',\'DATATIME\']', '', '', '');
INSERT INTO `t_deve_object_column_copy` VALUES ('1006', '-1', 'file', '附件', '200', '-1', 'file', '<input name=\"file\" type=\"file\">', '', '1', '7', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,255]\']', '', '', '');
INSERT INTO `t_deve_object_column_copy` VALUES ('1007', '-1', 'birthday', '日期', '200', '-1', 'easyui-datebox', '<input name=\"birthday\" class=\"easyui-datebox\" style=\"width: 200px; \"   value=\"\" data-options=\"validType:[\'length[0,20]\',\'DATATIME\'] \"/>', '', '1', '6', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,20]\',\'DATATIME\']', '', '', '');
INSERT INTO `t_deve_object_column_copy` VALUES ('1008', '-1', 'id', '主键,pk,id', '200', '-1', 'hidden', '<input name=\"id\" type=\"hidden\">', '', '1', '99', '1', '0', '1', '0', '', 'center', '0', '', '', '', '', '');
INSERT INTO `t_deve_object_column_copy` VALUES ('1009', '-1', 'note', '备注', '610', '50', 'textArea', '<input name=\"note\" class=\"easyui-textbox\" style=\"width: 610px;height:50px \" multiline=\"true\" data-options=\"validType:[\'length[0,600]\',\'mobile\'] \"/>', null, '3', '99', '0', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,600]\',\'mobile\']', null, null, null);
INSERT INTO `t_deve_object_column_copy` VALUES ('1010', '-1', 'pwd', '密钥', '200', '-1', 'easyui-passwordbox', '<input name=\"password\" class=\"easyui-passwordbox\" style=\"width: 200px; \"   value=\"\" data-options=\"required:true,validType:[\'length[0,20]\'] \"/>', '', '1', '99', '0', '0', '0', '0', '', 'center', '0', '', 'required:true,validType:[\'length[0,20]\']', '', '', '');
INSERT INTO `t_deve_object_column_copy` VALUES ('10010', '-1', 'descript', '描述', '610', '50', 'textArea', '<input name=\"descript\" class=\"easyui-textbox\" style=\"width: 610px;height:50px \" multiline=\"true\"  value=\"\" data-options=\"validType:[\'length[0,600]\',\'mobile\'] \"/>', '', '3', '99', '0', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,600]\',\'mobile\']', '', '', '');
INSERT INTO `t_deve_object_column_copy` VALUES ('10103', '-1', 'password', '密码', '200', '-1', 'easyui-passwordbox', '<input name=\"password\" class=\"easyui-passwordbox\" style=\"width: 200px; \"   value=\"\" data-options=\"required:true,validType:[\'length[0,20]\'] \"/>', null, '1', '99', '0', '0', '0', '0', null, 'center', '0', null, 'required:true,validType:[\'length[0,20]\']', null, null, null);
INSERT INTO `t_deve_object_column_copy` VALUES ('10104', '-1', 'addtime', '创建时间，新增时间', '200', '-1', 'hidden', '<input name=\"addtime\" type=\"hidden\">', 'DATE', '1', '99', '1', '0', '1', '0', null, 'center', '0', null, 'validType:[\'length[0,20]\',\'DATATIME\']', null, null, null);
INSERT INTO `t_deve_object_column_copy` VALUES ('10105', '-1', 'updatetime', '最后登录时间,最后修改时间', '200', '-1', 'hidden', '<input name=\"updatetime\" type=\"hidden\">', 'NOW', '1', '99', '1', '0', '1', '0', '', 'center', '0', '', 'validType:[\'length[0,20]\',\'DATATIME\']', '', '', '');
INSERT INTO `t_deve_object_column_copy` VALUES ('10106', '-1', 'status', '状态', '200', '-1', 'radio', '<#dictTag name=\"status\" showType=\"radio\" dictCode=\"\" width=\"200\" value=\"1\"  showNull=\"false\" ></#dictTag>', '1', '1', '99', '1', '0', '1', '1', 'status', 'center', '0', null, null, null, 'formatter: _DICT.STATUS', null);
INSERT INTO `t_deve_object_column_copy` VALUES ('10143', '-1', 'is', '是否', '200', '-1', 'radio', '<#dictTag name=\"is\" showType=\"radio\" dictCode=\"yesornot\" width=\"200\" value=\"1\"  showNull=\"false\" ></#dictTag>', '1', '1', '99', '1', '0', '1', '1', 'yesornot', 'center', '0', null, null, null, 'formatter: _DICT.YESORNOT', null);
INSERT INTO `t_deve_object_column_copy` VALUES ('10145', '-1', 'sex', '性别', '200', '-1', 'radio', '<#dictTag name=\"sex\" showType=\"radio\" dictCode=\"sex\" width=\"200\" value=\"\"  showNull=\"false\" ></#dictTag>', null, '1', '99', '1', '0', '1', '1', 'sex', 'center', '0', null, null, null, 'formatter: _DICT.SEX', null);
INSERT INTO `t_deve_object_column_copy` VALUES ('10146', '-1', 'money', '金额', '200', '-1', 'easyui-numberbox', '<input name=\"money\" class=\"easyui-numberbox\" style=\"width: 200px; \"   value=\"\" data-options=\"min:0,precision:2,validType:[\'length[0,20]\'] \"/>', '', '1', '99', '1', '0', '0', '0', '', 'center', '0', '', 'min:0,precision:2,validType:[\'length[0,20]\']', '', '', '');

-- ----------------------------
-- Table structure for t_deve_object_method
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_object_method`;
CREATE TABLE `t_deve_object_method` (
  `method_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `obj_id` int(11) DEFAULT NULL COMMENT '对象ID id为-1时，表示该方法为系统预设方法',
  `code` varchar(40) NOT NULL COMMENT '方法编码',
  `name` varchar(40) NOT NULL COMMENT '模块名称(中文）',
  `icon` varchar(60) DEFAULT NULL COMMENT '按钮图标',
  `icon_path` varchar(150) DEFAULT NULL,
  `icon_position` int(1) DEFAULT NULL COMMENT '图标位置 1:左边  2:右边',
  `method_type` varchar(20) DEFAULT NULL COMMENT '保存方法（新增 ADD、修改 EDIT、删除 DEL、查询 QUERY）',
  `show_type` varchar(20) DEFAULT NULL COMMENT '展示类型 列表，弹窗，异步',
  `fit_cloumn` int(11) DEFAULT NULL COMMENT '是否自适应 1：是 0：否',
  `width` int(4) DEFAULT NULL COMMENT '显示宽度',
  `height` int(4) DEFAULT NULL COMMENT '显示高度',
  `seq` int(2) DEFAULT NULL COMMENT '排序',
  `msg` varchar(200) DEFAULT NULL COMMENT '提示内容',
  `default_method` int(1) DEFAULT NULL COMMENT '默认方法 1：是 0：否',
  `view_sql` varchar(1000) DEFAULT NULL COMMENT '视图SQL',
  `single_able` tinyint(1) DEFAULT '1' COMMENT '是否单选',
  `first_load` tinyint(1) DEFAULT '1' COMMENT '是否初始加载',
  `default_order` varchar(100) DEFAULT NULL COMMENT '默认排序字段(desc)',
  `order_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '排序方式 desc：降序 asc：升序',
  `diy_js` varchar(100) DEFAULT NULL COMMENT '依赖JS文件',
  `init_dict` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '初始化字典',
  `page_path` varchar(100) DEFAULT NULL COMMENT '页面生成目录',
  `status` int(1) DEFAULT NULL COMMENT '是否可用 1：可用 0：不可用',
  `note` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`method_id`)
) ENGINE=InnoDB AUTO_INCREMENT=728 DEFAULT CHARSET=utf8 COMMENT='二次开发--模块管理--模块方法表';

-- ----------------------------
-- Records of t_deve_object_method
-- ----------------------------
INSERT INTO `t_deve_object_method` VALUES ('1', '-1', 'query', '查询', 'icon-search', '/res/jquery-easyui-1.5.3/themes/icons/search.png', '2', 'query', 'dataGrid', '1', '1000', '1000', '0', null, '1', null, '1', '1', null, null, null, null, null, '1', '查询');
INSERT INTO `t_deve_object_method` VALUES ('2', '-1', 'add', '新增', 'icon-add', '/res/jquery-easyui-1.5.3/themes/icons/edit_add.png', '1', 'add', 'dialog', '1', '800', '600', '1', '新增成功', '0', null, '1', '1', null, null, null, null, null, '1', '新增');
INSERT INTO `t_deve_object_method` VALUES ('3', '-1', 'edit', '修改', 'icon-edit', '/res/jquery-easyui-1.5.3/themes/icons/pencil.png', '1', 'edit', 'dialog', '1', '800', '600', '2', '修改成功', '0', null, '1', '1', null, null, null, null, '', '1', '修改');
INSERT INTO `t_deve_object_method` VALUES ('4', '-1', 'remove', '删除', 'icon-remove', '/res/jquery-easyui-1.5.3/themes/icons/edit_remove.png', '1', 'remove', 'ajax', '1', '800', '600', '3', '删除成功', '0', null, '1', '1', null, null, null, null, '', '1', '删除');
INSERT INTO `t_deve_object_method` VALUES ('724', '200', 'query', '查询', 'icon-search', '/res/jquery-easyui-1.5.3/themes/icons/search.png', '2', 'query', 'dataGrid', '1', '1000', '280', '0', null, '1', 'select user_id,username,mobile,nickname,email,pswd,create_time,last_login_time,status from t_biz_user where 1 = 1 #{username} #{mobile} #{status}', '1', '1', null, null, null, 'status', null, '1', '查询');
INSERT INTO `t_deve_object_method` VALUES ('725', '200', 'add', '新增', 'icon-add', '/res/jquery-easyui-1.5.3/themes/icons/edit_add.png', '1', 'add', 'dialog', '1', '800', '280', '1', '新增成功', '0', 'insert into t_biz_user (user_id,username,mobile,nickname,email,pswd,create_time,last_login_time,status) values ( ?,?,?,?,?,?,?,?,?)', '1', '1', null, null, null, 'status', null, '1', '新增');
INSERT INTO `t_deve_object_method` VALUES ('726', '200', 'edit', '修改', 'icon-edit', '/res/jquery-easyui-1.5.3/themes/icons/pencil.png', '1', 'edit', 'dialog', '1', '800', '280', '2', '修改成功', '0', 'update t_biz_user set username = ? ,mobile = ? ,nickname = ? ,email = ? ,pswd = ? ,create_time = ? ,last_login_time = ? ,status = ?  where user_id = ? ', '1', '1', null, null, null, 'status', null, '1', '修改');
INSERT INTO `t_deve_object_method` VALUES ('727', '200', 'remove', '删除', 'icon-remove', '/res/jquery-easyui-1.5.3/themes/icons/edit_remove.png', '1', 'remove', 'ajax', '1', '800', '280', '3', '删除成功', '0', 'delete from t_biz_user where user_id = ? ', '1', '1', null, null, null, 'status', null, '1', '删除');

-- ----------------------------
-- Table structure for t_deve_object_param
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_object_param`;
CREATE TABLE `t_deve_object_param` (
  `param_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `method_id` int(11) DEFAULT NULL COMMENT '方法主键',
  `ename` varchar(100) DEFAULT NULL COMMENT '参数名',
  `cname` varchar(100) DEFAULT NULL COMMENT '参数标题',
  `like_able` int(2) DEFAULT NULL COMMENT '是否模糊查询 1：是 0：否',
  `like_type` varchar(50) DEFAULT NULL COMMENT '模糊查询类型  full 全模糊 left 左模糊 right 右模糊',
  `dict_able` int(2) DEFAULT NULL COMMENT '是否数据字典  1：是 0：否',
  `dict_type` varchar(40) DEFAULT NULL COMMENT '数据字典类型',
  `width` int(11) DEFAULT NULL COMMENT '显示宽度',
  `event` varchar(100) DEFAULT NULL COMMENT '触发事件',
  `form_str` varchar(300) DEFAULT NULL COMMENT '表单内容',
  `form_type` varchar(100) DEFAULT NULL COMMENT '表单类型',
  `seq` int(255) DEFAULT NULL COMMENT '排序号',
  `sqlx` varchar(800) DEFAULT NULL COMMENT '参数查询语句',
  `default_value` varchar(100) DEFAULT NULL COMMENT '默认值',
  PRIMARY KEY (`param_id`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8 COMMENT='参数';

-- ----------------------------
-- Records of t_deve_object_param
-- ----------------------------
INSERT INTO `t_deve_object_param` VALUES ('68', '-1', 'username', '名称', '1', 'full', '0', null, '200', null, '<input name=\"username\" style=\"width: 200px\" value=\"\" class=\"easyui-textbox\" />', 'text', '1', 'and upper(username) like ? ', null);
INSERT INTO `t_deve_object_param` VALUES ('69', '-1', 'code', '编码', '1', 'full', '0', null, '200', null, '<input name=\"code\" style=\"width: 200px\" value=\"\" class=\"easyui-textbox\" />', 'text', '2', 'and upper(code) like ? ', null);
INSERT INTO `t_deve_object_param` VALUES ('70', '-1', 'mobilephone', '手机号码', '1', 'full', '0', null, '200', null, '<input name=\"mobilephone\" style=\"width: 200px\" value=\"\" class=\"easyui-textbox\" />', 'text', '3', 'and upper(mobilephone) like ? ', null);
INSERT INTO `t_deve_object_param` VALUES ('71', '-1', 'is', '是否', '0', 'full', '1', 'yesornot', '200', null, '<input name=\"is\" style=\"width: 200px\" value=\"\" class=\"easyui-textbox\" />', 'text', '4', 'and is = ? ', null);
INSERT INTO `t_deve_object_param` VALUES ('72', '-1', 'status', '状态', '0', 'full', '1', 'status', '200', null, '<input name=\"status\" style=\"width: 200px\" value=\"\" class=\"easyui-textbox\" />', 'text', '10', 'and status = ? ', null);
INSERT INTO `t_deve_object_param` VALUES ('156', '724', 'username', '名称', '1', 'full', '0', null, '200', null, '<input name=\"username\" style=\"width: 200px\" value=\"\" class=\"easyui-textbox\" />', 'text', '1', 'and upper(username) like ? ', null);
INSERT INTO `t_deve_object_param` VALUES ('157', '724', 'mobile', '手机号码', '1', 'full', '0', null, '200', null, '<input name=\"mobile\" style=\"width: 200px\" value=\"\" class=\"easyui-textbox\" />', 'text', '3', 'and upper(mobile) like ? ', null);
INSERT INTO `t_deve_object_param` VALUES ('158', '724', 'status', '状态', '0', 'full', '1', 'status', '200', null, '<input name=\"status\" style=\"width: 200px\" value=\"\" class=\"easyui-textbox\" />', 'text', '10', 'and status = ? ', null);

-- ----------------------------
-- Table structure for t_deve_object_priview
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_object_priview`;
CREATE TABLE `t_deve_object_priview` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(13) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `email` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(13) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `mobile` varchar(13) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `file1` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '附件',
  `money` int(10) DEFAULT NULL COMMENT '订单金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_deve_object_priview
-- ----------------------------
INSERT INTO `t_deve_object_priview` VALUES ('6', 'sdf', 'sf@fsfs.com', '0591-45456448', '13448745442', '2018-02-07', '[{id:132,name:\"春节放假登记表.xlsx\"},{id:131,name:\"资源情况说明.xlsx\"},{id:128,name:\"2018年技术人员结构.xlsx\"}]', '23');

-- ----------------------------
-- Table structure for t_deve_quartz
-- ----------------------------
DROP TABLE IF EXISTS `t_deve_quartz`;
CREATE TABLE `t_deve_quartz` (
  `job_id` int(11) DEFAULT NULL,
  `job_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '任务分组名',
  `tri_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '触发器名称',
  `tri_group` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '触发器分组',
  `begin_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '结束时间',
  `next_fire_time` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '下次执行时间',
  `cronExpression` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '轮循表达式',
  `class_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '执行类',
  `state` int(1) DEFAULT NULL COMMENT '状态',
  `desc` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='定时任务';

-- ----------------------------
-- Records of t_deve_quartz
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict` (
  `dictid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT '字典名',
  `val` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '字典值',
  `code` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '字典编码',
  `status` int(1) DEFAULT NULL COMMENT '状态',
  `ext1` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '扩展值1',
  `ext2` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '扩展值2',
  `ext3` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '扩展值3',
  PRIMARY KEY (`dictid`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统字典表';

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
INSERT INTO `t_sys_dict` VALUES ('1', ' 男', '1', 'sex', '1', '1dd', '2', '3');
INSERT INTO `t_sys_dict` VALUES ('2', '女', '2', 'sex', '1', '2', '2', '3');
INSERT INTO `t_sys_dict` VALUES ('3', '正常', '1', 'status', '1', null, null, '方式');
INSERT INTO `t_sys_dict` VALUES ('4', '禁用', '0', 'status', '1', '111', null, null);
INSERT INTO `t_sys_dict` VALUES ('7', '是', '1', 'yesornot', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('8', '否', '0', 'yesornot', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('9', '禁用', '0', 'dictStatus', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('10', '启用', '1', 'dictStatus', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('11', '禁用', '0', 'userStatus', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('12', '启用', '1', 'userStatus', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('13', '禁用', '0', 'permisionStatus', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('14', '启用', '1', 'permisionStatus', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('15', '禁用', '0', 'roleStatus', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('16', '启用', '1', 'roleStatus', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('21', '菜单', '1', 'permissionType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('22', '按钮', '2', 'permissionType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('23', '默认数据源', 'dataSourcePrimary', 'dataSource', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('24', '250数据源', 'dataSourceSecond', 'dataSource', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('25', '表', 'TABLE', 'dataType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('26', '视图', 'VIEW', 'dataType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('27', '升序', 'asc', 'orderType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('28', '降序', 'desc', 'orderType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('29', '左边', '1', 'iconPosition', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('30', '右边', '2', 'iconPosition', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('31', '查询', 'query', 'methodType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('32', '新增', 'add', 'methodType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('33', '编辑', 'edit', 'methodType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('34', '删除', 'remove', 'methodType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('35', '列表', 'dataGrid', 'showType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('36', '弹窗', 'dialog', 'showType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('37', '异步', 'ajax', 'showType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('38', '左对齐', 'left', 'alignType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('39', '居中', 'center', 'alignType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('40', '右对齐', 'right', 'alignType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('41', '用户ID', 'USERID', 'defaultValue', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('42', '用户名', 'USERNAME', 'defaultValue', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('43', '当前日期', 'DATE', 'defaultValue', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('44', '当前时间', 'NOW', 'defaultValue', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('45', '文本框', 'easyui-textbox', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('46', '文本域', 'textArea', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('47', '密码框', 'easyui-passwordbox', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('48', '数值框', 'easyui-numberbox', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('49', '日期框', 'easyui-datebox', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('50', '时间框', 'easyui-datetimebox', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('51', '下拉框', 'select', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('52', '单选框', 'radio', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('53', '复选框', 'checkbox', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('54', '文件框', 'file', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('55', '图片框', 'image', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('56', '隐藏域', 'hidden', 'formType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('57', '自增主键', 'PK', 'defaultValue', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('58', '全模糊', 'full', 'likeType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('59', '左模糊', 'left', 'likeType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('60', '右模糊', 'right', 'likeType', '1', null, null, null);
INSERT INTO `t_sys_dict` VALUES ('61', 'UUID主键', 'UUID', 'defaultValue', '1', null, null, null);

-- ----------------------------
-- Table structure for t_sys_param
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_param`;
CREATE TABLE `t_sys_param` (
  `param_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '参数名称',
  `note` varchar(100) DEFAULT NULL COMMENT '备注',
  `value` varchar(100) DEFAULT NULL COMMENT '参数值',
  `ext1` varchar(100) DEFAULT NULL COMMENT '参数值-扩展1',
  `ext2` varchar(100) DEFAULT NULL COMMENT '参数值-扩展2',
  `ext3` varchar(100) DEFAULT NULL COMMENT '参数值-扩展1',
  `type` varchar(10) DEFAULT NULL COMMENT '参数类型',
  `editable` varchar(1) DEFAULT NULL COMMENT '能否修改（0：不允许 1：允许）',
  `status` int(1) DEFAULT NULL COMMENT '是否已伪删除（1：已删除，0：正常）（用于公司复制的数据，公司被删除时）',
  `createuser` varchar(40) DEFAULT NULL COMMENT '创建人员',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `updateuser` varchar(40) DEFAULT NULL COMMENT '修改人员',
  `updatetime` datetime DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`param_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1233 DEFAULT CHARSET=utf8 COMMENT='SysParam(系统参数表)';

-- ----------------------------
-- Records of t_sys_param
-- ----------------------------
INSERT INTO `t_sys_param` VALUES ('1226', 'FileUploadPath', '文件上传存放目录', 'e:\\\\test\\\\', null, null, null, null, '1', null, 'admin', '2017-12-06 11:04:58', 'admin', '2017-12-06 11:37:44');
INSERT INTO `t_sys_param` VALUES ('1227', 'FileDownloadPath', '文件下载生成临时文件存放目录', 'e:\\\\test\\\\', null, null, null, null, '1', null, 'admin', '2017-12-07 09:52:31', null, null);
INSERT INTO `t_sys_param` VALUES ('1228', 'MailHost', '邮箱smtp地址', 'smtp.fjsxing.com', null, null, null, null, '1', null, 'admin', '2017-12-14 17:38:23', 'admin', '2017-12-14 17:40:47');
INSERT INTO `t_sys_param` VALUES ('1229', 'MailPass', '邮箱密码', 'Abc123456', null, null, null, null, '1', null, 'admin', '2017-12-14 17:38:36', 'admin', '2017-12-14 17:40:33');
INSERT INTO `t_sys_param` VALUES ('1230', 'MailPort', '邮箱发件端口', '25', null, null, null, null, '1', null, 'admin', '2017-12-14 17:38:48', 'admin', '2017-12-14 17:40:23');
INSERT INTO `t_sys_param` VALUES ('1232', 'MailUser', '邮箱帐号', 'hxy@fjsxing.com', null, null, null, null, '1', null, 'admin', '2017-12-14 17:41:28', null, null);

-- ----------------------------
-- Table structure for t_sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_permission`;
CREATE TABLE `t_sys_permission` (
  `permission_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_pid` bigint(20) DEFAULT NULL,
  `permission_code` varchar(50) DEFAULT '',
  `url` varchar(256) DEFAULT NULL COMMENT 'url地址',
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `sort_order` int(11) DEFAULT NULL COMMENT '排序',
  `status` int(1) DEFAULT NULL COMMENT '状态',
  `permission_type` int(1) DEFAULT NULL COMMENT '权限类型1:菜单，2按钮',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of t_sys_permission
-- ----------------------------
INSERT INTO `t_sys_permission` VALUES ('1', '0', '', '', '系统管理', '/res/jquery-easyui-1.5.3/themes/myicons/menu/icon06.png', '1', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('22', '1', '', '', '权限管理', '/res/jquery-easyui-1.5.3/themes/myicons/menu/application_osx_locked.png', '1', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('23', '22', '', '/sys/sysPermission', '菜单管理', '/res/jquery-easyui-1.5.3/themes/myicons/menu/application_windows_locked.png', '1', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('24', '22', '', '/sys/sysRole/listInit', '角色管理', '/res/jquery-easyui-1.5.3/themes/myicons/menu/group.png', '2', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('26', '22', '', '/sys/sysUser/listInit', '管理员管理', '/res/jquery-easyui-1.5.3/themes/myicons/menu/contact_blue.png', '3', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('31', '1', '', '', '二次开发', '/res/jquery-easyui-1.5.3/themes/myicons/menu/applications_osx_shrink.png', '4', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('32', '31', '', '/deve/deveExp/listInit', '查询导出', '/res/jquery-easyui-1.5.3/themes/myicons/menu/document_small_upload.png', '1', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('33', '1', '', '', '配置管理', '/res/jquery-easyui-1.5.3/themes/myicons/menu/applications_windows.png', '5', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('34', '33', '', '/sys/sysDict/listInit', '字典管理', '/res/jquery-easyui-1.5.3/themes/myicons/menu/applications_windows.png', '1', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('42', '33', '', '/sys/sysParam/listInit', '参数管理', '/res/jquery-easyui-1.5.3/themes/myicons/menu/shield_star.png', '5', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('43', '31', '', '/deve/deveExpResource/listInit', '资源管理', '/res/jquery-easyui-1.5.3/themes/myicons/menu/box.png', '3', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('44', '23', 'sysPermissionAdd', '', '权限管理添加', '/res/jquery-easyui-1.5.3/themes/myicons/menu/add.png', '1', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('45', '23', 'sysPermissionEdit', '', '权限管理修改', '/res/jquery-easyui-1.5.3/themes/myicons/menu/pencil.png', '2', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('46', '23', 'sysPermissionDelete', '', '权限管理删除', '/res/jquery-easyui-1.5.3/themes/myicons/menu/action_delete.png', '3', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('47', '24', 'sysRoleAdd', '', '角色添加', '/res/jquery-easyui-1.5.3/themes/myicons/menu/add.png', '1', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('48', '24', 'sysRoleEdit', '', '角色修改', '/res/jquery-easyui-1.5.3/themes/myicons/menu/pencil.png', '2', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('49', '24', 'sysRoleDelete', '', '角色删除', '/res/jquery-easyui-1.5.3/themes/myicons/menu/action_delete.png', '3', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('50', '24', 'sysRoleGrant', '', '角色授权', '/res/jquery-easyui-1.5.3/themes/myicons/menu/contact_grey.png', '4', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('51', '26', 'sysUserAdd', '', '管理员添加', '/res/jquery-easyui-1.5.3/themes/myicons/menu/add.png', '1', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('52', '26', 'sysUserEdit', '', '管理员修改', '/res/jquery-easyui-1.5.3/themes/myicons/menu/pencil.png', '2', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('53', '26', 'sysUserDelete', '', '管理员删除', '/res/jquery-easyui-1.5.3/themes/myicons/menu/action_delete.png', '3', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('54', '34', 'sysDictAdd', '', '字典增加', '/res/jquery-easyui-1.5.3/themes/myicons/menu/add.png', '1', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('55', '34', 'sysDictEdit', '', '字典修改', '/res/jquery-easyui-1.5.3/themes/myicons/menu/pencil.png', '2', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('56', '34', 'sysDictDelete', '', '字典删除', '/res/jquery-easyui-1.5.3/themes/myicons/menu/action_delete.png', '3', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('57', '42', 'sysParamAdd', '', '参数增加', '/res/jquery-easyui-1.5.3/themes/myicons/menu/add.png', '1', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('58', '42', 'sysParamEdit', '', '参数修改', '/res/jquery-easyui-1.5.3/themes/myicons/menu/pencil.png', '2', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('59', '31', '', '/deve/deveFileInfo/listInit', '文件上传', '/res/jquery-easyui-1.5.3/themes/myicons/menu/arrow_large_up.png', '2', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('60', '61', 'deveFileInfoAdd', '', '文件添加', '/res/jquery-easyui-1.5.3/themes/myicons/menu/add.png', '1', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('61', '31', '', '/deve/deveQuartz/listInit', '定时任务', '/res/jquery-easyui-1.5.3/themes/myicons/menu/rss.png', '4', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('62', '22', 'deveQuartzAdd', '', '添加任务', '/res/jquery-easyui-1.5.3/themes/myicons/menu/add.png', '1', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('63', '61', 'deveQuartzEdit', '', '修改任务', '/res/jquery-easyui-1.5.3/themes/myicons/menu/application_osx_edit.png', '2', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('64', '61', 'deveQuartzDelete', null, '删除任务', '/res/jquery-easyui-1.5.3/themes/myicons/menu/action_delete.png', '5', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('65', '61', 'deveQuartzPause', '', '暂停任务', '/res/jquery-easyui-1.5.3/themes/myicons/menu/application_osx_locked.png', '3', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('66', '61', 'deveQuartzResume', null, '恢复任务', '/res/jquery-easyui-1.5.3/themes/myicons/menu/application_osx_new.png', '4', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('67', '31', '', '/deve/deveObject/listInit', '对象管理', '/res/jquery-easyui-1.5.3/themes/myicons/menu/box_new.png', '5', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('68', '67', 'deveObjectAdd', '', '添加', '/res/jquery-easyui-1.5.3/themes/myicons/menu/application_osx_add.png', '7', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('69', '67', 'deveObjectEdit', '', '修改', '/res/jquery-easyui-1.5.3/themes/myicons/menu/application_osx_edit.png', '8', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('70', '67', 'deveObjectDelete', '', '删除', '/res/jquery-easyui-1.5.3/themes/myicons/menu/application_osx_remove.png', '9', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('71', '67', 'deveObjectPriview', '', '预览', '/res/jquery-easyui-1.5.3/themes/myicons/menu/marker_rounded_yellow.png', '6', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('72', '67', '', '/deve/deveObjectColumn/listInit?methodId=-1', '字段预设', '/res/jquery-easyui-1.5.3/themes/myicons/menu/document_a4_marked.png', '2', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('73', '67', '', '/deve/deveObjectMethod/listInit?objId=-1', '方法预设', '/res/jquery-easyui-1.5.3/themes/myicons/menu/calendar_month_left.png', '1', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('74', '67', '', '/deve/deveObjectParam/listInit?methodId=-1', '参数预设', '/res/jquery-easyui-1.5.3/themes/myicons/menu/calendar_day.png', '3', '1', '1');
INSERT INTO `t_sys_permission` VALUES ('75', '74', 'deveObjectParamAdd', null, '添加', '/res/jquery-easyui-1.5.3/themes/myicons/menu/application_osx_add.png', '1', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('76', '74', 'deveObjectParamEdit', null, '修改', '/res/jquery-easyui-1.5.3/themes/myicons/menu/application_osx_edit.png', '2', '1', '2');
INSERT INTO `t_sys_permission` VALUES ('77', '74', 'deveObjectParamDelete', null, '删除', '/res/jquery-easyui-1.5.3/themes/myicons/menu/application_osx_remove.png', '3', '1', '2');

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `status` int(1) DEFAULT NULL COMMENT '角色状态',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('6', '我是管理员', '1');
INSERT INTO `t_sys_role` VALUES ('7', '222', '1');
INSERT INTO `t_sys_role` VALUES ('8', 'test11', '1');
INSERT INTO `t_sys_role` VALUES ('9', 'ss', '1');
INSERT INTO `t_sys_role` VALUES ('10', '1', '1');

-- ----------------------------
-- Table structure for t_sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_permission`;
CREATE TABLE `t_sys_role_permission` (
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of t_sys_role_permission
-- ----------------------------
INSERT INTO `t_sys_role_permission` VALUES ('10', '1');
INSERT INTO `t_sys_role_permission` VALUES ('10', '33');
INSERT INTO `t_sys_role_permission` VALUES ('10', '34');
INSERT INTO `t_sys_role_permission` VALUES ('10', '54');
INSERT INTO `t_sys_role_permission` VALUES ('10', '55');
INSERT INTO `t_sys_role_permission` VALUES ('10', '56');
INSERT INTO `t_sys_role_permission` VALUES ('10', '42');
INSERT INTO `t_sys_role_permission` VALUES ('10', '57');
INSERT INTO `t_sys_role_permission` VALUES ('10', '58');

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) NOT NULL COMMENT '用户号码',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机号码',
  `nickname` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱|登录帐号',
  `pswd` varchar(32) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` bigint(1) DEFAULT '1' COMMENT '1:有效，0:禁止登录',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('15', 'admin', '15980273571', '超级管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '2017-11-06 15:37:19', null, '1');
INSERT INTO `t_sys_user` VALUES ('20', '1', '2', '3', '4', 'e4da3b7fbbce2345d7772b0674a318d5', '2017-11-24 11:52:31', null, '0');
INSERT INTO `t_sys_user` VALUES ('21', '2', '2', '3', '4', 'e4da3b7fbbce2345d7772b0674a318d5', '2017-11-24 14:26:56', null, '1');

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES ('21', '6');
INSERT INTO `t_sys_user_role` VALUES ('21', '7');
INSERT INTO `t_sys_user_role` VALUES ('20', '7');
INSERT INTO `t_sys_user_role` VALUES ('15', '6');

-- ----------------------------
-- View structure for V_DATA_OBJECTS
-- ----------------------------
DROP VIEW IF EXISTS `V_DATA_OBJECTS`;
CREATE ALGORITHM=UNDEFINED DEFINER=`xlauch`@`%` SQL SECURITY DEFINER VIEW `V_DATA_OBJECTS` AS select `information_schema`.`columns`.`COLUMN_NAME` AS `列名`,`information_schema`.`columns`.`DATA_TYPE` AS `字段类型`,`information_schema`.`columns`.`COLUMN_COMMENT` AS `字段注释` from `information_schema`.`columns` ;

-- ----------------------------
-- View structure for v_test
-- ----------------------------
DROP VIEW IF EXISTS `v_test`;
CREATE ALGORITHM=UNDEFINED DEFINER=`xlauch`@`%` SQL SECURITY DEFINER VIEW `v_test` AS select `t_biz_user`.`user_id` AS `user_id`,`t_biz_user`.`username` AS `username`,`t_biz_user`.`mobile` AS `mobile`,`t_biz_user`.`nickname` AS `nickname`,`t_biz_user`.`email` AS `email`,`t_biz_user`.`pswd` AS `pswd`,`t_biz_user`.`create_time` AS `create_time`,`t_biz_user`.`last_login_time` AS `last_login_time`,`t_biz_user`.`status` AS `status` from `t_biz_user` ;

-- ----------------------------
-- Procedure structure for new_procedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `new_procedure`;
DELIMITER ;;
CREATE DEFINER=`xlauch`@`%` PROCEDURE `new_procedure`()
BEGIN

-- 遍历数据结束标志
  DECLARE done INT DEFAULT FALSE;  
-- 需要定义接收游标数据的变量 
  DECLARE V_NAME VARCHAR(30);
  -- 游标
  DECLARE cur CURSOR FOR SELECT name FROM xlauch.t_deve_exp;
  
  
  -- 打开游标
  OPEN cur;
  
  -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据，这里只有一个，多个的话也一样；
    FETCH cur INTO V_NAME;
	
		-- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
     
    -- 这里做你想做的循环的事件

    SELECT V_NAME;

  END LOOP;
  -- 关闭游标
  CLOSE cur;

END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getPmsChildLst
-- ----------------------------
DROP FUNCTION IF EXISTS `getPmsChildLst`;
DELIMITER ;;
CREATE DEFINER=`xlauch`@`%` FUNCTION `getPmsChildLst`(rootId INT) RETURNS varchar(1000) CHARSET utf8
BEGIN
	DECLARE sTemp VARCHAR(1000);
	DECLARE sTempChd VARCHAR(1000);

	SET sTemp = '$';
	SET sTempChd =cast(rootId as CHAR);

	WHILE sTempChd is not null DO
		if sTempChd != cast(rootId as CHAR) then
			SET sTemp = concat(sTemp,',',sTempChd);
		end if;
		SELECT group_concat(permission_id) INTO sTempChd FROM t_sys_permission where FIND_IN_SET(parent_pid,sTempChd);
	END WHILE;
	RETURN sTemp;
END
;;
DELIMITER ;
