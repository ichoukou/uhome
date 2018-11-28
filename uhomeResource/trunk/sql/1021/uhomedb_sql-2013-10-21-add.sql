/*==============================================================*/
/* Table: event_range                            */
/*==============================================================*/
DROP TABLE IF EXISTS `event_range`;
CREATE TABLE `event_range` (
  `eventRangeId` int(11) NOT NULL AUTO_INCREMENT,
  `eventId` int(11) DEFAULT NULL COMMENT '活动ID',
  `outId` int(11) DEFAULT NULL COMMENT '外部ID',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型：0=全场，1=排期',
  PRIMARY KEY (`eventRangeId`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='优惠活动适用范围';

/*==============================================================*/
/* Table: postage                      */
/*==============================================================*/
DROP TABLE IF EXISTS `postage`;
CREATE TABLE `postage` (
  `postageId` int(11) NOT NULL AUTO_INCREMENT,
  `outId` int(11) DEFAULT NULL COMMENT '外部id',
  `postage` decimal(10,2) DEFAULT NULL COMMENT '邮费',
  `startTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '起始时间',
  `endTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '结束时间',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型：1=排期，2=商品',
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`postageId`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='邮费信息';

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('专题模板','/admin/specialtopic/specialTopic_searcheSecialTopicTemplate',1,0,0,(select uresourceId from (select uresourceId from uresource where url='/admin/adv/adv_searchAdvs') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/specialtopic/specialTopic_searcheSecialTopicTemplate'));
/*==============================================================*/
/* Table: special_topic_templet        专题模板                                     */
/*==============================================================*/
DROP TABLE IF EXISTS `special_topic_templet`;
CREATE TABLE `special_topic_templet` (
  `specialTopicTempletId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '主题模板名称',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `isPublish` tinyint(4) NOT NULL COMMENT '发布状态，1未发布   2 发布',
  PRIMARY KEY (`specialTopicTempletId`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='专题模板';

-- ----------------------------
-- Table structure for `specialtopic_adv_position`
-- ----------------------------
DROP TABLE IF EXISTS `specialtopic_adv_position`;
CREATE TABLE `specialtopic_adv_position` (
  `specialTopicAdvPositionId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '广告显示位置名称',
  `positionCode` varchar(64) DEFAULT NULL COMMENT '广告前台位置对应标示',
  PRIMARY KEY (`specialTopicAdvPositionId`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='专题模板广告显示位置';

-- ----------------------------
-- Records of specialtopic_adv_position
-- ----------------------------
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('大型活动广告图', 'B_1');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖活动banner', 'M_1');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('头部产品展示图1', 'S_1');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('头部产品展示图2', 'S_2');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('头部产品展示图3', 'S_3');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('头部产品展示图4', 'S_4');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('特卖品牌展示banner1', 'M_2');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图1_1', 'S_5');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图1_2', 'S_6');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图1_3', 'S_7');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图1_4', 'S_8');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图1_5', 'S_9');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图1_6', 'S_10');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图1_7', 'S_11');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图1_8', 'S_12');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('特卖品牌展示banner2', 'M_3');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图2_1', 'S_13');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图2_2', 'S_14');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图2_3', 'S_15');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图2_4', 'S_16');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('特卖品牌展示banner3', 'M_4');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图3_1', 'S_17');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图3_2', 'S_18');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图3_3', 'S_19');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图3_4', 'S_20');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('特卖品牌展示banner4', 'M_5');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图4_1', 'S_21');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图4_2', 'S_22');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图4_3', 'S_23');
INSERT INTO `specialtopic_adv_position` (name,positionCode) VALUES ('热卖产品展示图4_4', 'S_24');

-- ----------------------------
-- Table structure for `specialtopic_advertisement`
-- ----------------------------
DROP TABLE IF EXISTS `specialtopic_advertisement`;
CREATE TABLE `specialtopic_advertisement` (
  `specialTopicAdvId` int(11) NOT NULL AUTO_INCREMENT,
  `specialTopicAdvPositionId` int(11) NOT NULL COMMENT '广告显示位置id',
  `imageUrl` varchar(256) NOT NULL COMMENT '广告图片地址',
  `turnUrl` varchar(256) DEFAULT NULL COMMENT '跳转地址',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `specialTopicTempletId` int(11) NOT NULL COMMENT '主题模板id',
  PRIMARY KEY (`specialTopicAdvId`),
  KEY `Index_1` (`specialTopicAdvId`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='专题模板广告信息';


/* Table: special_topic_templet      专题模板添加活动时间                                  */
alter table special_topic_templet add column startTime date   NOT NULL  after isPublish, add column endTime date   NOT NULL  after startTime;

/*==============================================================*/
/* Table: specialtopic_advertisement           添加列                              */
/*==============================================================*/
ALTER TABLE `specialtopic_advertisement` 
ADD COLUMN `productIds`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '产品编码（即id），多个以逗号分隔',
ADD COLUMN `productImageUrls`  varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '产品图片路径，多个以逗号分隔',
ADD COLUMN `productLinkUrls`  varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '产品链接路径，多个以逗号分隔';

/*==============================================================*/
/* Table: specialtopic_adv_position     删除多余广告位                             */
/*==============================================================*/
DELETE FROM specialtopic_adv_position WHERE positionCode LIKE 'S_%';