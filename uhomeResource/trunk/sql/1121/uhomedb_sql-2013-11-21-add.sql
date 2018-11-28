drop table if exists promotion;
CREATE TABLE `promotion` (
  `promotionId` int(11) NOT NULL AUTO_INCREMENT,
  `promoName` varchar(200) DEFAULT NULL COMMENT '促销活动名称',
  `limitAmount` decimal(10,2) DEFAULT NULL COMMENT '最小消费金额',
  `reduceAmount` decimal(10,2) DEFAULT NULL COMMENT '减免金额',
  `smallAmount` tinyint(4) DEFAULT NULL COMMENT '最小消费件数',
  `relationId` int(11) DEFAULT NULL COMMENT '关联业务 当前存储的为 排期id',
  `promoType` tinyint(4) DEFAULT NULL COMMENT '促销活动类型 1 满减 ',
  `startDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '促销开始日期',
  `endDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '促销结束日期',
  `couponEventId` int(11) DEFAULT NULL COMMENT '关联业务',
  `promoStatus` tinyint(4) DEFAULT NULL COMMENT '促销活动状态0删除， 1未启用，2启用',
  PRIMARY KEY (`promotionId`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='促销';

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('搜索促销活动','/admin/promotion/promotion-searchPromotions',1,0,7,(select uresourceId from (select uresourceId from uresource where url='/admin/coupon/coupon-searchCoupons') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/promotion/promotion-searchPromotions'));

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('新增促销活动','/admin/promotion/promotion-add',1,0,8,(select uresourceId from (select uresourceId from uresource where url='/admin/coupon/coupon-searchCoupons') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/promotion/promotion-add'));

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('修改促销活动','/admin/promotion/promotion-edit',1,0,9,(select uresourceId from (select uresourceId from uresource where url='/admin/coupon/coupon-searchCoupons') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/promotion/promotion-edit'));

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('删除促销活动','/admin/promotion/promotion-delete',1,0,10,(select uresourceId from (select uresourceId from uresource where url='/admin/coupon/coupon-searchCoupons') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/promotion/promotion-delete'));

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('审核促销活动','/admin/promotion/promotion-review',1,0,11,(select uresourceId from (select uresourceId from uresource where url='/admin/coupon/coupon-searchCoupons') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/promotion/promotion-review'));

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('查看促销活动','/admin/promotion/promotion-view',1,0,12,(select uresourceId from (select uresourceId from uresource where url='/admin/coupon/coupon-searchCoupons') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/promotion/promotion-view'));

UPDATE uresource SET rank = 1 WHERE uresourceId = (SELECT a.uresourceId FROM (SELECT uresourceId FROM uresource WHERE url = '/admin/coupon/coupon-editCommonCoupon') a);
UPDATE uresource SET rank = 2 WHERE uresourceId = (SELECT a.uresourceId FROM (SELECT uresourceId FROM uresource WHERE url = '/admin/coupon/coupon-editWechatCoupon') a);
UPDATE uresource SET rank = 3 WHERE uresourceId = (SELECT a.uresourceId FROM (SELECT uresourceId FROM uresource WHERE url = '/admin/coupon/coupon-editCouponPackage') a);
UPDATE uresource SET rank = 4 WHERE uresourceId = (SELECT a.uresourceId FROM (SELECT uresourceId FROM uresource WHERE url = '/admin/coupon/coupon-search') a);
UPDATE uresource SET rank = 5 WHERE uresourceId = (SELECT a.uresourceId FROM (SELECT uresourceId FROM uresource WHERE url = '/admin/coupon/coupon-edit') a);
UPDATE uresource SET rank = 6 WHERE uresourceId = (SELECT a.uresourceId FROM (SELECT uresourceId FROM uresource WHERE url = '/admin/coupon/coupon-delete') a);
