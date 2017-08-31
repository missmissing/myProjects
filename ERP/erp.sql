/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : erp

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-08-31 16:13:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dep
-- ----------------------------
DROP TABLE IF EXISTS `dep`;
CREATE TABLE `dep` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `name` varchar(30) NOT NULL COMMENT '部门名称',
  `tele` varchar(30) NOT NULL COMMENT '电话',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dep
-- ----------------------------
INSERT INTO `dep` VALUES ('1', '系统管理', '12345678');
INSERT INTO `dep` VALUES ('2', '总裁办', '88888888');
INSERT INTO `dep` VALUES ('3', '采购部', '78262232');
INSERT INTO `dep` VALUES ('4', '销售部', '12121212');
INSERT INTO `dep` VALUES ('5', '仓储部', '89223322');
INSERT INTO `dep` VALUES ('6', '财务部', '58585858');
INSERT INTO `dep` VALUES ('7', '人事部', '14141414');
INSERT INTO `dep` VALUES ('8', '售后服务中心', '90909090');
INSERT INTO `dep` VALUES ('9', '市场营销部', '99819182');
INSERT INTO `dep` VALUES ('10', '策划部', '80112211');

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '员工编号',
  `userName` varchar(15) DEFAULT NULL COMMENT '登陆名称',
  `pwd` varchar(32) DEFAULT NULL COMMENT '登陆密码',
  `name` varchar(28) DEFAULT '' COMMENT '姓名',
  `email` varchar(255) DEFAULT NULL COMMENT 'E-mail',
  `tele` varchar(30) DEFAULT NULL COMMENT '电话',
  `gender` int(1) DEFAULT NULL COMMENT '性别',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `depUuid` bigint(20) DEFAULT NULL COMMENT '部门编号',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES ('1', 'admin', '3ef7164d1f6167cb9f2658c07d3c2f0a', '超级管理员', 'admin@itcast.cn', '12345678', '1', '香格里拉', '1979-06-21', '1');
INSERT INTO `emp` VALUES ('2', 'sunwukong', '35c101cdb5aec8ed46b888a27ca44b02', '孙悟空', 'sunwukong@itcast.cn', '111', '1', '花果山水帘洞1栋1号', '2000-07-04', '2');
INSERT INTO `emp` VALUES ('3', 'tangseng', 'aae3fec644615753b71b7d9e5cf86e94', '唐僧', 'tangseng@itcast.cn', '111', '1', '东土大唐皇家寺院', '1983-04-05', '2');
INSERT INTO `emp` VALUES ('4', 'zhubajie', 'a4403905fa8f71d416b0dfd8e95b06e7', '猪八戒', 'zhubajie@itcast.cn', '111', '1', '高老庄五段32号', '1982-01-06', '3');
INSERT INTO `emp` VALUES ('5', 'shaheshang', 'a67cb54d016b65e92d8813c7fe3b8360', '沙和尚', 'shaheshang@itcast.cn', '111', '1', '流沙河1-2', '1972-12-13', '4');
INSERT INTO `emp` VALUES ('6', 'bailongma', '1f580a2a444d84d4b81d26955d0d51c2', '白龙马', 'bailongma@itcast.cn', '111', '1', '西海太子路11-22', '1988-03-23', '5');
INSERT INTO `emp` VALUES ('7', 'xiaolongnv', '85092acc12a24dbc6b1609b80e1905d4', '小龙女', 'xiaolongnv@itcast.cn', '111', '0', '古墓大街', '1991-02-12', '6');
INSERT INTO `emp` VALUES ('8', 'yangguo', '0b2e30a6aae6da72e44980cd55068621', '杨过', 'yangguo@itcast.cn', '111', '1', '古墓大街', '1993-03-25', '7');
INSERT INTO `emp` VALUES ('9', 'guojing', '38641f915a303070b0b880b8c30a2f2e', '郭靖', 'guojing@itcast.cn', '111', '1', '长江路三段', '2015-06-02', '8');
INSERT INTO `emp` VALUES ('10', 'huangrong', 'd2919433c3cab6f6cf73d45e42319c87', '黄蓉', 'huangrong@itcast.cn', '111', '0', '黄河路三端9号', '1995-02-15', '8');
INSERT INTO `emp` VALUES ('11', 'linghuchong', '7dabdbb5b27f0ad6db1772384ab0d9a1', '令狐冲', 'linghuchong@itcast.cn', '111', '1', '青年路三段2号', '2015-06-01', '9');

-- ----------------------------
-- Table structure for emp_role
-- ----------------------------
DROP TABLE IF EXISTS `emp_role`;
CREATE TABLE `emp_role` (
  `empUuid` bigint(20) NOT NULL COMMENT '员工编号',
  `roleUuid` bigint(20) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`empUuid`,`roleUuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of emp_role
-- ----------------------------
INSERT INTO `emp_role` VALUES ('1', '1');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品编号',
  `name` varchar(30) NOT NULL COMMENT '商品名称',
  `origin` varchar(30) NOT NULL COMMENT '产地',
  `producer` varchar(30) NOT NULL COMMENT '厂家',
  `unit` varchar(30) NOT NULL COMMENT '计量单位',
  `inPrice` double(10,2) NOT NULL COMMENT '进货价格',
  `outPrice` double(10,2) NOT NULL COMMENT '出货价格',
  `goodsTypeUuid` bigint(20) DEFAULT NULL COMMENT '商品类型',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', '透明皂', '北京', '北京皂厂', '块', '2.00', '4.00', '1');
INSERT INTO `goods` VALUES ('2', '洗衣粉', '北京', '北京皂厂', '袋', '4.00', '8.00', '1');
INSERT INTO `goods` VALUES ('3', '中华香烟', '北京', '中华烟厂', '条', '100.00', '200.00', '2');
INSERT INTO `goods` VALUES ('4', '红花郎', '北京', '郎酒酒厂', '瓶', '60.00', '200.00', '2');
INSERT INTO `goods` VALUES ('5', '可比克', '北京', '薯片厂', '袋', '2.00', '5.00', '3');
INSERT INTO `goods` VALUES ('6', '泡泡糖', '天津', '天津大大泡泡糖厂', '包', '3.00', '10.00', '3');
INSERT INTO `goods` VALUES ('7', '苹果', '上海', '·', '斤', '4.00', '8.00', '4');
INSERT INTO `goods` VALUES ('8', '水蜜桃', '北京', '', '斤', '6.00', '15.00', '4');
INSERT INTO `goods` VALUES ('9', '大酱', '北京', '老北京大酱', '袋', '2.00', '5.00', '5');
INSERT INTO `goods` VALUES ('10', '红方', '北京', '王致和', '瓶', '3.00', '6.00', '5');
INSERT INTO `goods` VALUES ('11', '臭豆腐', '北京', '王致和', '瓶', '3.00', '6.00', '5');
INSERT INTO `goods` VALUES ('12', '前进帽', '北京', '北京帽子厂', '个', '40.00', '90.00', '6');
INSERT INTO `goods` VALUES ('13', '袜子', '', '', '包', '4.00', '8.00', '6');

-- ----------------------------
-- Table structure for goodstype
-- ----------------------------
DROP TABLE IF EXISTS `goodstype`;
CREATE TABLE `goodstype` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品类别编号',
  `name` varchar(30) NOT NULL COMMENT '商品类别名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goodstype
-- ----------------------------
INSERT INTO `goodstype` VALUES ('1', '日用百货');
INSERT INTO `goodstype` VALUES ('2', '烟酒糖茶');
INSERT INTO `goodstype` VALUES ('3', '儿童食品');
INSERT INTO `goodstype` VALUES ('4', '水果蔬菜');
INSERT INTO `goodstype` VALUES ('5', '油盐酱醋');
INSERT INTO `goodstype` VALUES ('6', '服装鞋帽');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menuid` varchar(20) NOT NULL COMMENT '菜单ID',
  `menuname` varchar(30) NOT NULL COMMENT '菜单名称',
  `icon` varchar(20) DEFAULT NULL COMMENT '图标',
  `url` varchar(255) NOT NULL COMMENT '菜单URL',
  `pid` varchar(20) NOT NULL COMMENT '上级菜单ID',
  PRIMARY KEY (`menuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('0', '系统菜单', 'icon-sys', '-', '-1');
INSERT INTO `menu` VALUES ('100', '基础信息', 'icon-sys', '-', '0');
INSERT INTO `menu` VALUES ('101', '商品类别', 'icon-sys', 'goodstype.html', '100');
INSERT INTO `menu` VALUES ('102', '商品', 'icon-sys', 'goods.html', '100');
INSERT INTO `menu` VALUES ('103', '供应商管理', 'icon-sys', 'supplier.html?type=1', '100');
INSERT INTO `menu` VALUES ('104', '客户管理', 'icon-sys', 'supplier.html?type=2', '100');
INSERT INTO `menu` VALUES ('105', '仓库管理', 'icon-sys', 'store.html', '100');
INSERT INTO `menu` VALUES ('200', '人事管理', 'icon-sys', '-', '0');
INSERT INTO `menu` VALUES ('201', '部门管理', 'icon-sys', 'dep.html', '200');
INSERT INTO `menu` VALUES ('202', '员工管理', 'icon-sys', 'emp.html', '200');
INSERT INTO `menu` VALUES ('300', '采购管理', 'icon-sys', '-', '0');
INSERT INTO `menu` VALUES ('301', '采购申请', 'icon-sys', 'order_add.html?type=1', '300');
INSERT INTO `menu` VALUES ('302', '采购订单查询', 'icon-sys', 'orders.html?type=1', '300');
INSERT INTO `menu` VALUES ('303', '采购订单审核', 'icon-sys', 'orders_0.html', '300');
INSERT INTO `menu` VALUES ('304', '采购订单确认', 'icon-sys', 'orders_1.html', '300');
INSERT INTO `menu` VALUES ('305', '采购订单入库', 'icon-sys', 'orders_2.html?type=1', '300');
INSERT INTO `menu` VALUES ('400', '销售管理', 'icon-sys', '-', '0');
INSERT INTO `menu` VALUES ('401', '销售订单录入', 'icon-sys', 'order_add.html?type=2', '400');
INSERT INTO `menu` VALUES ('402', '销售订单查询', 'icon-sys', 'orders.html?type=2', '400');
INSERT INTO `menu` VALUES ('403', '销售订单出库', 'icon-sys', 'orders_2.html?type=2', '400');
INSERT INTO `menu` VALUES ('500', '统计分析', 'icon-sys', '-', '0');
INSERT INTO `menu` VALUES ('501', '销售报表', 'icon-sys', 'sheet.html', '500');
INSERT INTO `menu` VALUES ('600', '权限设置', 'icon-sys', '-', '0');
INSERT INTO `menu` VALUES ('601', '角色管理', 'icon-sys', 'role.html', '600');
INSERT INTO `menu` VALUES ('602', '角色权限设置', 'icon-sys', 'roleMenuSet.html', '600');
INSERT INTO `menu` VALUES ('603', '用户角色设置', 'icon-sys', 'empRoleSet.html', '600');

-- ----------------------------
-- Table structure for orderdetail
-- ----------------------------
DROP TABLE IF EXISTS `orderdetail`;
CREATE TABLE `orderdetail` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `num` int(11) DEFAULT NULL COMMENT '数量',
  `price` double(10,2) DEFAULT NULL COMMENT '价格',
  `money` double(10,2) DEFAULT NULL COMMENT '金额',
  `goodsUuid` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `goodsName` varchar(50) DEFAULT NULL COMMENT '商品ID',
  `orderUuid` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `endTime` date DEFAULT NULL COMMENT '出入库时间',
  `ender` bigint(20) DEFAULT NULL COMMENT '库管员',
  `storeUuid` bigint(20) DEFAULT NULL COMMENT '仓库编号',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderdetail
-- ----------------------------
INSERT INTO `orderdetail` VALUES ('1', '100', '2.00', '200.00', '1', '透明皂', '1', '2015-06-18', '1', '2', '1');
INSERT INTO `orderdetail` VALUES ('2', '200', '4.00', '800.00', '2', '洗衣粉', '1', '2015-06-18', '1', '2', '1');
INSERT INTO `orderdetail` VALUES ('3', '100', '2.00', '200.00', '1', '透明皂', '2', '2015-06-18', '1', '2', '1');
INSERT INTO `orderdetail` VALUES ('4', '11', '4.00', '44.00', '1', '透明皂', '3', '2015-06-18', '1', '2', '1');
INSERT INTO `orderdetail` VALUES ('5', '22', '8.00', '176.00', '7', '苹果', '3', '2015-06-18', '1', '1', '1');
INSERT INTO `orderdetail` VALUES ('6', '100', '4.00', '400.00', '7', '苹果', '4', '2015-06-18', '1', '1', '1');
INSERT INTO `orderdetail` VALUES ('7', '500', '4.00', '2000.00', '7', '苹果', '5', '2015-06-18', '1', '1', '1');
INSERT INTO `orderdetail` VALUES ('8', '11', '100.00', '1100.00', '3', '中华香烟', '6', null, null, '0', '0');
INSERT INTO `orderdetail` VALUES ('9', '11', '4.00', '44.00', '7', '苹果', '7', null, null, '0', '0');
INSERT INTO `orderdetail` VALUES ('10', '10000', '2.00', '20000.00', '5', '可比克', '8', '2015-06-30', '1', '1', '1');
INSERT INTO `orderdetail` VALUES ('11', '2000', '6.00', '12000.00', '8', '水蜜桃', '8', '2015-06-30', '1', '1', '1');
INSERT INTO `orderdetail` VALUES ('12', '100', '5.00', '500.00', '5', '可比克', '9', '2015-06-30', '1', '1', '1');
INSERT INTO `orderdetail` VALUES ('13', '200', '15.00', '3000.00', '8', '水蜜桃', '9', '2015-06-30', '1', '1', '1');
INSERT INTO `orderdetail` VALUES ('14', '100', '5.00', '500.00', '5', '可比克', '10', null, null, '0', '0');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `orderNum` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `createTime` date DEFAULT NULL COMMENT '生成日期',
  `checkTime` date DEFAULT NULL COMMENT '检查日期',
  `startTime` date DEFAULT NULL COMMENT '开始日期',
  `endTime` date DEFAULT NULL COMMENT '结束日期',
  `orderType` int(1) DEFAULT NULL COMMENT '订单类型',
  `creater` bigint(20) DEFAULT NULL COMMENT '下单员',
  `checker` bigint(20) DEFAULT NULL COMMENT '审查员',
  `starter` bigint(20) DEFAULT NULL COMMENT '采购员',
  `ender` bigint(20) DEFAULT NULL COMMENT '库管员',
  `supplierUuid` bigint(20) DEFAULT NULL COMMENT '供应商ID',
  `totalNum` int(11) DEFAULT NULL COMMENT '总数量',
  `totalPrice` double(10,2) DEFAULT NULL COMMENT '总价格',
  `state` varchar(1) DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', '2015061800001', '2015-06-18', '2015-06-18', '2015-06-18', null, '1', '1', '1', '1', null, '1', '300', '1000.00', '3');
INSERT INTO `orders` VALUES ('2', '2015061800002', '2015-06-18', '2015-06-18', '2015-06-18', null, '1', '1', '1', '1', null, '3', '100', '200.00', '3');
INSERT INTO `orders` VALUES ('3', '2015061800003', '2015-06-18', null, null, null, '2', '1', null, null, null, '5', '33', '220.00', '3');
INSERT INTO `orders` VALUES ('4', '2015061800004', '2015-06-18', '2015-06-18', '2015-06-18', '2015-06-18', '1', '1', '1', '1', null, '4', '100', '400.00', '3');
INSERT INTO `orders` VALUES ('5', '2015061800005', '2015-06-18', '2015-06-18', '2015-06-18', '2015-06-18', '1', '1', '1', '1', null, '4', '500', '2000.00', '3');
INSERT INTO `orders` VALUES ('6', '2015062100001', '2015-06-21', null, null, null, '1', '1', null, null, null, '2', '11', '1100.00', '0');
INSERT INTO `orders` VALUES ('7', '2015062100002', '2015-06-21', null, null, null, '1', '1', null, null, null, '2', '11', '44.00', '0');
INSERT INTO `orders` VALUES ('8', '2015063000001', '2015-06-30', '2015-06-30', '2015-06-30', '2015-06-30', '1', '1', '1', '1', null, '1', '12000', '32000.00', '3');
INSERT INTO `orders` VALUES ('9', '2015063000002', '2015-06-30', '2015-06-30', '2015-06-30', '2015-06-30', '2', '1', null, null, null, '7', '300', '3500.00', '3');
INSERT INTO `orders` VALUES ('10', '2015063000003', '2015-06-30', null, null, null, '2', '1', null, null, null, '7', '100', '500.00', '3');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员');
INSERT INTO `role` VALUES ('2', '采购员');
INSERT INTO `role` VALUES ('3', '销售员');
INSERT INTO `role` VALUES ('4', '库管员');
INSERT INTO `role` VALUES ('5', '销售部经理');
INSERT INTO `role` VALUES ('6', '采购部经理');
INSERT INTO `role` VALUES ('7', '采购计划员');
INSERT INTO `role` VALUES ('8', '总经理');
INSERT INTO `role` VALUES ('9', '人事部经理');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `roleUuid` bigint(20) NOT NULL COMMENT '角色ID',
  `menuUuid` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`roleUuid`,`menuUuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '100');
INSERT INTO `role_menu` VALUES ('1', '101');
INSERT INTO `role_menu` VALUES ('1', '102');
INSERT INTO `role_menu` VALUES ('1', '103');
INSERT INTO `role_menu` VALUES ('1', '104');
INSERT INTO `role_menu` VALUES ('1', '105');
INSERT INTO `role_menu` VALUES ('1', '200');
INSERT INTO `role_menu` VALUES ('1', '201');
INSERT INTO `role_menu` VALUES ('1', '202');
INSERT INTO `role_menu` VALUES ('1', '300');
INSERT INTO `role_menu` VALUES ('1', '301');
INSERT INTO `role_menu` VALUES ('1', '302');
INSERT INTO `role_menu` VALUES ('1', '303');
INSERT INTO `role_menu` VALUES ('1', '304');
INSERT INTO `role_menu` VALUES ('1', '305');
INSERT INTO `role_menu` VALUES ('1', '400');
INSERT INTO `role_menu` VALUES ('1', '401');
INSERT INTO `role_menu` VALUES ('1', '402');
INSERT INTO `role_menu` VALUES ('1', '403');
INSERT INTO `role_menu` VALUES ('1', '500');
INSERT INTO `role_menu` VALUES ('1', '501');
INSERT INTO `role_menu` VALUES ('1', '600');
INSERT INTO `role_menu` VALUES ('1', '601');
INSERT INTO `role_menu` VALUES ('1', '602');
INSERT INTO `role_menu` VALUES ('1', '603');
INSERT INTO `role_menu` VALUES ('7', '301');
INSERT INTO `role_menu` VALUES ('7', '302');

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(30) NOT NULL COMMENT '仓库',
  `address` varchar(30) NOT NULL COMMENT '地址',
  `empUuid` bigint(20) NOT NULL COMMENT '管理员ID',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES ('1', '食品仓库', '中关村', '1');
INSERT INTO `store` VALUES ('2', '百货仓库', '奥运村', '1');

-- ----------------------------
-- Table structure for storedetail
-- ----------------------------
DROP TABLE IF EXISTS `storedetail`;
CREATE TABLE `storedetail` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `storeUuid` bigint(20) NOT NULL COMMENT '仓库ID',
  `goodsUuid` bigint(20) NOT NULL COMMENT '商品ID',
  `num` int(11) NOT NULL COMMENT '库存数量',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of storedetail
-- ----------------------------
INSERT INTO `storedetail` VALUES ('1', '2', '1', '189');
INSERT INTO `storedetail` VALUES ('2', '2', '2', '200');
INSERT INTO `storedetail` VALUES ('3', '1', '7', '578');
INSERT INTO `storedetail` VALUES ('4', '1', '5', '9900');
INSERT INTO `storedetail` VALUES ('5', '1', '8', '1800');

-- ----------------------------
-- Table structure for storeoper
-- ----------------------------
DROP TABLE IF EXISTS `storeoper`;
CREATE TABLE `storeoper` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `empUuid` bigint(20) NOT NULL COMMENT '员工ID',
  `operTime` datetime NOT NULL COMMENT '操作事件',
  `storeUuid` bigint(20) NOT NULL COMMENT '仓库ID',
  `goodsUuid` bigint(20) NOT NULL COMMENT '商品ID',
  `num` int(11) NOT NULL COMMENT '数量',
  `type` int(1) NOT NULL COMMENT '出入库标记',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of storeoper
-- ----------------------------
INSERT INTO `storeoper` VALUES ('1', '1', '2015-06-18 22:09:14', '2', '1', '100', '1');
INSERT INTO `storeoper` VALUES ('2', '1', '2015-06-18 22:09:19', '2', '2', '200', '1');
INSERT INTO `storeoper` VALUES ('3', '1', '2015-06-18 22:13:38', '2', '1', '100', '1');
INSERT INTO `storeoper` VALUES ('4', '1', '2015-06-18 22:17:37', '2', '1', '11', '2');
INSERT INTO `storeoper` VALUES ('5', '1', '2015-06-18 22:22:06', '1', '7', '500', '1');
INSERT INTO `storeoper` VALUES ('6', '1', '2015-06-18 22:22:14', '1', '7', '100', '1');
INSERT INTO `storeoper` VALUES ('7', '1', '2015-06-18 22:22:23', '1', '7', '22', '2');
INSERT INTO `storeoper` VALUES ('8', '1', '2015-06-30 07:24:19', '1', '5', '10000', '1');
INSERT INTO `storeoper` VALUES ('9', '1', '2015-06-30 07:24:23', '1', '8', '2000', '1');
INSERT INTO `storeoper` VALUES ('10', '1', '2015-06-30 07:25:04', '1', '5', '100', '2');
INSERT INTO `storeoper` VALUES ('11', '1', '2015-06-30 07:25:07', '1', '8', '200', '2');

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `uuid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(30) DEFAULT NULL COMMENT '名称',
  `address` varchar(30) DEFAULT NULL COMMENT '地址',
  `contact` varchar(30) DEFAULT NULL COMMENT '联系人',
  `tele` varchar(30) DEFAULT NULL COMMENT '电话',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES ('1', '王致和', '北京臭豆腐大街', '王二和', '22222223', '1');
INSERT INTO `supplier` VALUES ('2', '北京帽子厂', '帽子胡同', '朱式帽', '11221122', '1');
INSERT INTO `supplier` VALUES ('3', '北京皂厂', '公主坟33-11', '孙俪', '66666666', '1');
INSERT INTO `supplier` VALUES ('4', '玄德商贸有限公司', '四川成都友谊大街', '刘玄德', '12345678', '1');
INSERT INTO `supplier` VALUES ('5', '兴隆超市', '文化路三段49号', '李兴隆', '55555555', '2');
INSERT INTO `supplier` VALUES ('6', '新玛特', '长江路东口南300米', '辛巴达', '33332222', '2');
INSERT INTO `supplier` VALUES ('7', '鸿运商场', '天津剪子胡同', '赵鸿运', '99991111', '2');
