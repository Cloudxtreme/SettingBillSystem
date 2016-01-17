SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for billstatus
-- ----------------------------
DROP TABLE IF EXISTS `billstatus`;
CREATE TABLE `billstatus` (
  `ID` int(3) NOT NULL COMMENT '???????',
  `Name` varchar(10) NOT NULL COMMENT '????????',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of billstatus
-- ----------------------------
INSERT INTO `billstatus` VALUES ('-500', '???');
INSERT INTO `billstatus` VALUES ('-300', '???');
INSERT INTO `billstatus` VALUES ('-200', '?????');
INSERT INTO `billstatus` VALUES ('-100', '???');
INSERT INTO `billstatus` VALUES ('100', '???');
INSERT INTO `billstatus` VALUES ('150', '???');
INSERT INTO `billstatus` VALUES ('200', '???');
INSERT INTO `billstatus` VALUES ('300', '???');
INSERT INTO `billstatus` VALUES ('400', '???');
INSERT INTO `billstatus` VALUES ('500', '???');

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '??ID',
  `CompanyName` varchar(255) DEFAULT NULL COMMENT '????',
  `CompanyCode` varchar(6) DEFAULT NULL COMMENT '?????????',
  `Belong` int(255) DEFAULT NULL COMMENT '??????ID?????0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('1', '??????', '000001', '0');
INSERT INTO `company` VALUES ('2', '???????', '000002', '1');
INSERT INTO `company` VALUES ('3', '??????', '000003', '2');
INSERT INTO `company` VALUES ('4', '??????', '000004', '2');
INSERT INTO `company` VALUES ('5', '??????', '000005', '2');
INSERT INTO `company` VALUES ('6', '????????', '000006', '3');
INSERT INTO `company` VALUES ('7', '???????', '000007', '1');
INSERT INTO `company` VALUES ('8', '???????', '000008', '1');
INSERT INTO `company` VALUES ('9', '????????', '000009', '1');

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '??ID',
  `DeviceName` varchar(255) DEFAULT NULL COMMENT '????',
  `DeviceModel` varchar(255) DEFAULT NULL COMMENT '????',
  `Belong` int(255) DEFAULT NULL COMMENT '???????ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('1', '?????', 'APTX-4869', '1');
INSERT INTO `device` VALUES ('2', '?????', 'ASD-45', '2');
INSERT INTO `device` VALUES ('3', '?????', 'DAA456', '3');
INSERT INTO `device` VALUES ('4', '?????', 'FC-0001', '4');
INSERT INTO `device` VALUES ('5', '?????', 'APTX-4870', '1');
INSERT INTO `device` VALUES ('6', '?????', 'APTX-4871', '1');
INSERT INTO `device` VALUES ('7', '?????', 'ASD-58', '2');
INSERT INTO `device` VALUES ('8', '?????', 'DAA466', '3');
INSERT INTO `device` VALUES ('9', '?????', 'FC-0002', '4');
INSERT INTO `device` VALUES ('10', '?????', 'LP-001', '4');
INSERT INTO `device` VALUES ('11', '?????', 'LP-002', '4');
INSERT INTO `device` VALUES ('12', '?????', 'APTX-4872', '12');
INSERT INTO `device` VALUES ('13', '?????', 'APTX-4873', '12');
INSERT INTO `device` VALUES ('14', '?????', 'LP-003', '12');
INSERT INTO `device` VALUES ('15', '?????', 'LP-004', '12');
INSERT INTO `device` VALUES ('16', '?????', 'APTX-4875', '13');
INSERT INTO `device` VALUES ('17', '?????', 'LP-005', '13');
INSERT INTO `device` VALUES ('18', '?????', 'LP-006', '13');
INSERT INTO `device` VALUES ('19', '?????', 'APTX-4874', '13');
INSERT INTO `device` VALUES ('20', '???', 'JDQ-01', '5');
INSERT INTO `device` VALUES ('21', '???', 'JDQ-02', '6');
INSERT INTO `device` VALUES ('22', '???', 'JDQ-03', '7');

-- ----------------------------
-- Table structure for line
-- ----------------------------
DROP TABLE IF EXISTS `line`;
CREATE TABLE `line` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '??ID',
  `LineName` varchar(255) DEFAULT NULL COMMENT '????',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of line
-- ----------------------------
INSERT INTO `line` VALUES ('1', '???');
INSERT INTO `line` VALUES ('2', '????');
INSERT INTO `line` VALUES ('3', '???');
INSERT INTO `line` VALUES ('4', '???');

-- ----------------------------
-- Table structure for settingbill
-- ----------------------------
DROP TABLE IF EXISTS `settingbill`;
CREATE TABLE `settingbill` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `BillCode` varchar(255) NOT NULL COMMENT '????',
  `LocationFromID` varchar(255) NOT NULL COMMENT '????ID',
  `LocationToID` varchar(255) NOT NULL COMMENT '????ID',
  `DeviceBelongingID` varchar(255) NOT NULL COMMENT '????ID',
  `LineID` varchar(255) NOT NULL COMMENT '???????????ID?',
  `DeviceID` varchar(255) NOT NULL COMMENT '????(ID)',
  `Remark` varchar(255) DEFAULT '' COMMENT '??????',
  `Status` int(3) NOT NULL DEFAULT '100' COMMENT '??????\r\n????-500\r\n\r\n????100\r\n????150\r\n????-100\r\n\r\n????200\r\n??????-200\r\n\r\n????300\r\n????-300\r\n\r\n????400\r\n\r\n????500',
  `TemplateCode` varchar(255) DEFAULT NULL COMMENT '??????',
  `CreaterID` varchar(255) NOT NULL COMMENT '?????',
  `CreateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????',
  `LastEditorID` varchar(255) DEFAULT NULL COMMENT '??????????????????',
  `LastEditTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '??????',
  `CheckerID` varchar(255) DEFAULT NULL COMMENT '?????',
  `CheckTime` datetime DEFAULT NULL COMMENT '????',
  `RecovererID` varchar(255) DEFAULT NULL COMMENT '???ID',
  `RecoverTime` datetime DEFAULT NULL COMMENT '?????',
  `RecoverRemark` varchar(255) DEFAULT '' COMMENT '?????',
  `TrasherID` varchar(255) DEFAULT NULL COMMENT '?????',
  `TrashTime` datetime DEFAULT NULL COMMENT '????',
  `TrashRemark` varchar(255) DEFAULT '' COMMENT '?????',
  `AuditorID` varchar(255) DEFAULT NULL COMMENT '?????',
  `AuditTime` datetime DEFAULT NULL COMMENT '????',
  `AuditCancelRemark` varchar(255) DEFAULT '' COMMENT '????',
  `ReceiverID` varchar(255) DEFAULT NULL COMMENT '???',
  `ReceiveTime` datetime DEFAULT NULL COMMENT '????',
  `ReceiveCancelRemark` varchar(255) DEFAULT '' COMMENT '????',
  `ReporterID` int(11) DEFAULT NULL COMMENT '???',
  `ReportFeedbackTime` datetime DEFAULT NULL COMMENT '????',
  `ReportFeedbackRemark` varchar(255) DEFAULT '' COMMENT '????',
  `ExecuterID` varchar(255) DEFAULT NULL COMMENT '???',
  `ExecuteFeedbackTime` datetime DEFAULT NULL COMMENT '????',
  `ExecuteFeedbackRemark` varchar(255) DEFAULT '' COMMENT '????',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='???????????????????????????';

-- ----------------------------
-- Records of settingbill
-- ----------------------------
INSERT INTO `settingbill` VALUES ('1', '201404160000010001', '1', '3', '1', '1', '1', '????', '200', 'T0000010001', '1', '2014-04-16 16:08:36', '1', '2014-04-16 16:08:36', '11', '2014-05-15 22:11:21', null, null, '', null, null, '', '2', '2014-05-15 22:11:48', '', '3', '2014-05-15 22:12:08', '', '3', '2014-05-15 22:14:26', 'well done', '4', '2014-05-15 22:13:57', 'done');
INSERT INTO `settingbill` VALUES ('2', '201404160000010002', '1', '4', '2', '2', '2', '??', '500', 'T0000010002', '11', '2014-04-21 12:10:59', '11', '2014-04-21 12:10:59', null, null, null, null, '', null, null, '', null, null, '', null, null, '', null, null, '', null, null, '');
INSERT INTO `settingbill` VALUES ('3', '201404160000010003', '1', '5', '3', '3', '3', '??', '-500', 'T0000010003', '1', '2014-04-21 12:21:36', '1', '2014-04-21 12:21:36', null, null, null, null, '', null, null, '', null, null, '', null, null, '', null, null, '', null, null, '');
INSERT INTO `settingbill` VALUES ('4', '201404160000010004', '1', '7', '12', '1', '12', '????', '100', 'T0000020001', '11', '2014-04-21 12:23:25', '1', '2014-05-21 08:39:51', null, null, null, null, '', null, null, '', null, null, '', null, null, '', null, null, '', null, null, '');
INSERT INTO `settingbill` VALUES ('5', '201405250000010001', '1', '2', '5', '1', '20', '1234', '100', 'T0000020002', '1', '2014-05-25 14:25:32', '1', '2014-05-25 14:25:32', null, null, null, null, '', null, null, '', null, null, '', null, null, '', null, null, '', null, null, '');
INSERT INTO `settingbill` VALUES ('6', '201405250000010002', '1', '2', '5', '1', '20', '1234', '100', 'T0000010004', '1', '2014-05-25 14:26:26', '1', '2014-05-25 14:26:26', null, null, null, null, '', null, null, '', null, null, '', null, null, '', null, null, '', null, null, '');
INSERT INTO `settingbill` VALUES ('7', '201405250000010003', '1', '2', '5', '1', '20', '1234', '100', 'T0000010005', '1', '2014-05-25 14:28:36', '1', '2014-05-25 14:28:36', null, null, null, null, '', null, null, '', null, null, '', null, null, '', null, null, '', null, null, '');
INSERT INTO `settingbill` VALUES ('8', '201405250000010004', '1', '2', '5', '1', '20', '', '-500', 'T0000010006', '1', '2014-05-25 14:35:33', '1', '2014-05-25 14:35:33', null, null, null, null, '', '1', '2014-05-25 14:36:25', 'i dnt want it', null, null, '', null, null, '', null, null, '', null, null, '');

-- ----------------------------
-- Table structure for settingbilldetail
-- ----------------------------
DROP TABLE IF EXISTS `settingbilldetail`;
CREATE TABLE `settingbilldetail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `BillCode` varchar(255) NOT NULL COMMENT '????',
  `Name` varchar(255) NOT NULL COMMENT '?????',
  `Before1` varchar(255) DEFAULT NULL COMMENT '??????',
  `Before2` varchar(255) DEFAULT NULL COMMENT '??????',
  `After1` varchar(255) DEFAULT NULL COMMENT '??????',
  `After2` varchar(255) DEFAULT NULL COMMENT '??????',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='?????????';

-- ----------------------------
-- Records of settingbilldetail
-- ----------------------------
INSERT INTO `settingbilldetail` VALUES ('1', '201404160000010001', '???????????', '300', '220', '260', '180');
INSERT INTO `settingbilldetail` VALUES ('2', '201404160000010002', '?????????', '200', '125', '200', '260');
INSERT INTO `settingbilldetail` VALUES ('3', '201404160000010002', '???????????', '3.66', '2.65', '3.7', '3.2');
INSERT INTO `settingbilldetail` VALUES ('4', '201404160000010002', 'TA????', '1.0', '1.2', '1.3', '1.7');
INSERT INTO `settingbilldetail` VALUES ('5', '201404160000010002', '??????????', '600', '540', '650', '500');
INSERT INTO `settingbilldetail` VALUES ('6', '201404160000010002', '??????????', '300', '250', '200', '150');
INSERT INTO `settingbilldetail` VALUES ('7', '201404160000010002', 'TA?????????', '240', '190', '220', '180');
INSERT INTO `settingbilldetail` VALUES ('8', '201404160000010002', '??????', '0.667', '0.7', '0.3', '0.32');
INSERT INTO `settingbilldetail` VALUES ('9', '201404160000010002', '?????????', '960', '840', '860', '400');
INSERT INTO `settingbilldetail` VALUES ('10', '201404160000010002', '????I???????', '3.2', '2.5', '2.4', '1.6');
INSERT INTO `settingbilldetail` VALUES ('11', '201404160000010002', '????II???????', '10.0', '5', '8', '1');
INSERT INTO `settingbilldetail` VALUES ('12', '201404160000010002', '????II??????', '1.0', '1', '1', '1');
INSERT INTO `settingbilldetail` VALUES ('13', '201404160000010003', '????III???????', '20.0', '15', '15', '8');
INSERT INTO `settingbilldetail` VALUES ('14', '201404160000010003', '????III??????', '2.0', '1', '1', '1');
INSERT INTO `settingbilldetail` VALUES ('15', '201404160000010003', '????III???', '??', '??', '??', '??');
INSERT INTO `settingbilldetail` VALUES ('16', '201404160000010004', '??III????', '??', '??', '??', '??');
INSERT INTO `settingbilldetail` VALUES ('17', '201404160000010004', '?III?????', '??', '??', '??', '??');
INSERT INTO `settingbilldetail` VALUES ('18', '201404160000010004', '???????', '??', '??', '??', '??');
INSERT INTO `settingbilldetail` VALUES ('19', '201405250000010001', '1', '1', '2', '3', '4');
INSERT INTO `settingbilldetail` VALUES ('20', '201405250000010002', '1', '1234', '4123', '12341', '412');
INSERT INTO `settingbilldetail` VALUES ('21', '201405250000010003', 'test01', '1', '2', '3', '4');
INSERT INTO `settingbilldetail` VALUES ('22', '201405250000010004', 'test02', '1', '2', '3', '4');

-- ----------------------------
-- Table structure for systemlog
-- ----------------------------
DROP TABLE IF EXISTS `systemlog`;
CREATE TABLE `systemlog` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `Content` varchar(255) DEFAULT '' COMMENT '????',
  `Operator` varchar(30) DEFAULT NULL COMMENT '????',
  `AddTime` datetime DEFAULT NULL COMMENT '????',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemlog
-- ----------------------------

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `TemplateCode` varchar(11) NOT NULL COMMENT '????',
  `TemplateName` varchar(20) DEFAULT NULL COMMENT '????????20??',
  `Status` int(2) DEFAULT '1' COMMENT '???1????-1????-2????',
  `CreaterID` varchar(255) DEFAULT NULL COMMENT '???',
  `CreateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '????',
  `BelongID` varchar(255) DEFAULT NULL COMMENT '????ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='????';

-- ----------------------------
-- Records of template
-- ----------------------------
INSERT INTO `template` VALUES ('1', 'T0000010001', '??1', '1', '2', '2014-05-21 11:01:32', '1');
INSERT INTO `template` VALUES ('2', 'T0000010002', '??2', '1', '2', '2014-05-21 11:01:36', '1');
INSERT INTO `template` VALUES ('3', 'T0000010003', '??3', '1', '2', '2014-05-21 11:01:40', '1');
INSERT INTO `template` VALUES ('4', 'T0000020001', '??4', '1', '6', '2014-05-21 19:38:08', '2');
INSERT INTO `template` VALUES ('5', 'T0000020002', '??5', '1', '6', '2014-05-21 19:38:11', '2');
INSERT INTO `template` VALUES ('6', 'T0000010004', '??6', '-1', '2', '2014-05-22 01:27:43', '1');
INSERT INTO `template` VALUES ('7', 'T0000010005', '??7', '-1', '2', '2014-05-22 01:27:47', '1');
INSERT INTO `template` VALUES ('8', 'T0000010006', '??8', '-2', '2', '2014-05-22 01:27:51', '1');
INSERT INTO `template` VALUES ('10', 'T0000010007', '123', '1', '2', '2014-05-25 01:02:46', '1');
INSERT INTO `template` VALUES ('11', 'T0000010008', '1233', '-2', '2', '2014-05-25 01:11:37', '1');
INSERT INTO `template` VALUES ('12', 'T0000010009', '1', '-1', '2', '2014-05-25 01:14:46', '1');
INSERT INTO `template` VALUES ('13', 'T0000010010', '2', '-1', '2', '2014-05-25 01:17:05', '1');
INSERT INTO `template` VALUES ('14', 'T0000010011', 'a', '-2', '2', '2014-05-25 01:58:06', '1');
INSERT INTO `template` VALUES ('15', 'T0000010012', 'test', '1', '2', '2014-05-25 02:07:46', '1');

-- ----------------------------
-- Table structure for templatedetail
-- ----------------------------
DROP TABLE IF EXISTS `templatedetail`;
CREATE TABLE `templatedetail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `TemplateCode` varchar(11) DEFAULT NULL COMMENT '????',
  `Name` varchar(255) DEFAULT NULL COMMENT '????',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of templatedetail
-- ----------------------------
INSERT INTO `templatedetail` VALUES ('1', 'T0000010001', '???????????');
INSERT INTO `templatedetail` VALUES ('2', 'T0000010001', '?????????');
INSERT INTO `templatedetail` VALUES ('3', 'T0000010001', '???????????');
INSERT INTO `templatedetail` VALUES ('4', 'T0000010001', 'TA????');
INSERT INTO `templatedetail` VALUES ('5', 'T0000010001', '??????????');
INSERT INTO `templatedetail` VALUES ('6', 'T0000010001', '??????????');
INSERT INTO `templatedetail` VALUES ('7', 'T0000010002', 'TA?????????');
INSERT INTO `templatedetail` VALUES ('8', 'T0000010002', '??????');
INSERT INTO `templatedetail` VALUES ('9', 'T0000010002', '?????????');
INSERT INTO `templatedetail` VALUES ('10', 'T0000010002', '????I???????');
INSERT INTO `templatedetail` VALUES ('11', 'T0000010002', '????II???????');
INSERT INTO `templatedetail` VALUES ('12', 'T0000010002', '????II??????');
INSERT INTO `templatedetail` VALUES ('13', 'T0000010003', '????III???????');
INSERT INTO `templatedetail` VALUES ('14', 'T0000010003', '????III??????');
INSERT INTO `templatedetail` VALUES ('15', 'T0000010003', '????III???');
INSERT INTO `templatedetail` VALUES ('16', 'T0000010003', '??III????');
INSERT INTO `templatedetail` VALUES ('17', 'T0000010003', '?III?????');
INSERT INTO `templatedetail` VALUES ('18', 'T0000010003', '???????');
INSERT INTO `templatedetail` VALUES ('19', 'T0000020001', '??4');
INSERT INTO `templatedetail` VALUES ('20', 'T0000020001', '??4');
INSERT INTO `templatedetail` VALUES ('21', 'T0000020001', '??4');
INSERT INTO `templatedetail` VALUES ('22', 'T0000020001', '??4');
INSERT INTO `templatedetail` VALUES ('23', 'T0000020002', '??5');
INSERT INTO `templatedetail` VALUES ('24', 'T0000020002', '??5');
INSERT INTO `templatedetail` VALUES ('25', 'T0000020002', '??5');
INSERT INTO `templatedetail` VALUES ('26', 'T0000020002', '??5');
INSERT INTO `templatedetail` VALUES ('27', 'T0000020002', '??5');
INSERT INTO `templatedetail` VALUES ('28', 'T0000020002', '??5');
INSERT INTO `templatedetail` VALUES ('29', 'T0000010004', '??6');
INSERT INTO `templatedetail` VALUES ('30', 'T0000010004', '??6');
INSERT INTO `templatedetail` VALUES ('31', 'T0000010004', '??6');
INSERT INTO `templatedetail` VALUES ('32', 'T0000010004', '??6');
INSERT INTO `templatedetail` VALUES ('33', 'T0000010004', '??6');
INSERT INTO `templatedetail` VALUES ('34', 'T0000010005', '??7');
INSERT INTO `templatedetail` VALUES ('35', 'T0000010005', '??7');
INSERT INTO `templatedetail` VALUES ('36', 'T0000010005', '??7');
INSERT INTO `templatedetail` VALUES ('37', 'T0000010006', '??8');
INSERT INTO `templatedetail` VALUES ('38', 'T0000010006', '??8');
INSERT INTO `templatedetail` VALUES ('39', 'T0000010006', '??8');
INSERT INTO `templatedetail` VALUES ('40', 'T0000010006', '??8');
INSERT INTO `templatedetail` VALUES ('41', 'T0000010006', '??8');
INSERT INTO `templatedetail` VALUES ('42', 'T0000010006', '??8');
INSERT INTO `templatedetail` VALUES ('43', 'T0000010006', '??8');
INSERT INTO `templatedetail` VALUES ('44', 'T0000010006', '??8');
INSERT INTO `templatedetail` VALUES ('46', 'T0000010007', '1');
INSERT INTO `templatedetail` VALUES ('47', 'T0000010007', '22');
INSERT INTO `templatedetail` VALUES ('48', 'T0000010007', '333');
INSERT INTO `templatedetail` VALUES ('49', 'T0000010008', '312');
INSERT INTO `templatedetail` VALUES ('50', 'T0000010009', '1');
INSERT INTO `templatedetail` VALUES ('51', 'T0000010010', '2');
INSERT INTO `templatedetail` VALUES ('52', 'T0000010011', 'asd');
INSERT INTO `templatedetail` VALUES ('53', 'T0000010011', 'ad');
INSERT INTO `templatedetail` VALUES ('54', 'T0000010011', 'insert');
INSERT INTO `templatedetail` VALUES ('55', 'T0000010011', 'da');
INSERT INTO `templatedetail` VALUES ('56', 'T0000010012', 'estestse');

-- ----------------------------
-- Table structure for templatestatus
-- ----------------------------
DROP TABLE IF EXISTS `templatestatus`;
CREATE TABLE `templatestatus` (
  `ID` int(11) NOT NULL COMMENT '???',
  `Name` varchar(255) DEFAULT NULL COMMENT '????',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of templatestatus
-- ----------------------------
INSERT INTO `templatestatus` VALUES ('-2', '???');
INSERT INTO `templatestatus` VALUES ('-1', '???');
INSERT INTO `templatestatus` VALUES ('1', '???');

-- ----------------------------
-- Table structure for transformerstation
-- ----------------------------
DROP TABLE IF EXISTS `transformerstation`;
CREATE TABLE `transformerstation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '???ID',
  `TName` varchar(255) DEFAULT NULL COMMENT '?????',
  `Belong` int(255) DEFAULT NULL COMMENT '???????ID',
  `LineID` int(11) DEFAULT NULL COMMENT '???????ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of transformerstation
-- ----------------------------
INSERT INTO `transformerstation` VALUES ('1', '???', '3', '1');
INSERT INTO `transformerstation` VALUES ('2', '??????', '4', '2');
INSERT INTO `transformerstation` VALUES ('3', '???', '5', '3');
INSERT INTO `transformerstation` VALUES ('4', '???', '6', '4');
INSERT INTO `transformerstation` VALUES ('5', '???', '2', '1');
INSERT INTO `transformerstation` VALUES ('6', '???', '2', '2');
INSERT INTO `transformerstation` VALUES ('7', '???', '2', '3');
INSERT INTO `transformerstation` VALUES ('8', '???', '3', '4');
INSERT INTO `transformerstation` VALUES ('9', '???', '4', '1');
INSERT INTO `transformerstation` VALUES ('10', '???', '5', '2');
INSERT INTO `transformerstation` VALUES ('11', '???', '6', '3');
INSERT INTO `transformerstation` VALUES ('12', '???', '7', '4');
INSERT INTO `transformerstation` VALUES ('13', '???', '7', '3');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `ID` int(8) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `UserName` varchar(30) DEFAULT NULL COMMENT '?????????????',
  `DisplayName` varchar(30) DEFAULT NULL COMMENT '??????????????',
  `Password` varchar(50) DEFAULT '*FD571203974BA9AFE270FE62151AE967ECA5E0AA' COMMENT '?????111111?????admin???????',
  `Authority` int(2) DEFAULT NULL COMMENT '???\r\n1 - ?????\r\n2 - ?????\r\n3 - ?????\r\n4 - ??????\r\n5 - ???',
  `CompanyID` int(10) DEFAULT NULL COMMENT '????ID',
  `Status` int(2) DEFAULT '1' COMMENT '?????1????-1????-10????',
  `AddTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '????',
  `LastUpdateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '??????',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='????????????????\r\n??????????????';

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', '???', '*4ACFE3202A5FF5CF467898FC58AAB1D615029441', '5', '1', '1', '2014-04-07 23:06:56', '2014-04-07 23:06:56');
INSERT INTO `users` VALUES ('2', '1', '????', '*FD571203974BA9AFE270FE62151AE967ECA5E0AA', '1', '1', '1', '2014-04-12 21:12:46', '2014-04-12 21:12:46');
INSERT INTO `users` VALUES ('3', '2', '????', '*FD571203974BA9AFE270FE62151AE967ECA5E0AA', '2', '1', '1', '2014-04-21 12:23:35', '2014-04-21 12:23:35');
INSERT INTO `users` VALUES ('4', '3', '????', '*FD571203974BA9AFE270FE62151AE967ECA5E0AA', '3', '3', '1', '2014-04-21 12:23:38', '2014-04-21 12:23:38');
INSERT INTO `users` VALUES ('5', '4', '????', '*FD571203974BA9AFE270FE62151AE967ECA5E0AA', '4', '3', '1', '2014-05-13 03:09:43', '2014-05-13 03:09:43');
INSERT INTO `users` VALUES ('6', '11', '????', '*FD571203974BA9AFE270FE62151AE967ECA5E0AA', '1', '1', '1', '2014-05-15 22:16:26', '2014-05-15 22:16:26');
INSERT INTO `users` VALUES ('7', '5', '????', '*FD571203974BA9AFE270FE62151AE967ECA5E0AA', '5', '2', '1', '2014-05-21 11:02:26', '2014-05-21 11:02:26');
INSERT INTO `users` VALUES ('8', '6', '????', '*FD571203974BA9AFE270FE62151AE967ECA5E0AA', '2', '2', '1', '2014-05-21 19:38:38', '2014-05-21 19:38:38');
