/*==============================================================*/
/* Table: uresource                                           */
/*==============================================================*/
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('配置优惠券礼包','/admin/coupon/coupon-editCouponPackage',1,0,0,(select uid from (select uresourceId uid from uresource where url='/admin/coupon/coupon-searchCoupons') temp));
insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/coupon/coupon-editCouponPackage'));

/*==============================================================*/
/* Table: express                                           */
/*==============================================================*/
INSERT INTO express (expressName, companyCode) VALUES ('国通物流', 'guotong');