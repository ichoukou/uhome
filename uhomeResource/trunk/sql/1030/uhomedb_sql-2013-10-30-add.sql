ALTER TABLE order_head ADD COLUMN `sendProductTime` TIMESTAMP null DEFAULT NULL COMMENT '发货时间';

alter table brand_sort  add column `type` tinyint (1)  NOT NULL COMMENT '1:服饰箱包；2：母婴用品；3：家居用品；4：美容护肤'  after updateTime;

/*==============================================================*/
/* 品牌排序权限 */
/*==============================================================*/ 

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('品牌排序','/admin/brandSort/brandSort-listBrandSort',1,0,0,(select uresourceId from (select uresourceId from uresource where url='/admin/adv/adv_searchAdvs') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/brandSort/brandSort-listBrandSort'));

ALTER TABLE user_coupon ADD INDEX I_couponStatus (couponStatus);