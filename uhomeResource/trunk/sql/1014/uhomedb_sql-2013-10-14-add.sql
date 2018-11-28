/*==============================================================*/
/* Table: urole_uresource        添加权限                                  */
/*==============================================================*/
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/admin/auth/auth-listRoles'));
/*==============================================================*/
/* 商家商品权限 */
/*==============================================================*/
/* Table: uresource ,urole_uresource       添加商品                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('添加商品','/seller/product/product-addProduct',1,0,0,(select uid 
	from (select uresourceId uid from uresource where url='/seller/product/product-searchSellerProducts') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/seller/product/product-addProduct'));

/* Table: uresource ,urole_uresource       编辑商品                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('编辑商品','/seller/product/product-edit',1,0,0,(select uid 
	from (select uresourceId uid from uresource where url='/seller/product/product-searchSellerProducts') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/seller/product/product-edit'));

/* Table: uresource ,urole_uresource       删除商品                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('删除商品','/seller/product/product-delete',1,0,0,(select uid 
	from (select uresourceId uid from uresource where url='/seller/product/product-searchSellerProducts') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/seller/product/product-delete'));

/* Table: uresource ,urole_uresource       快速编辑商品                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('快速编辑商品','/seller/product/product-quickEdit',1,0,0,
	(select uid from (select uresourceId uid from uresource where url='/seller/product/product-searchSellerProducts') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/seller/product/product-quickEdit'));

/* Table: uresource ,urole_uresource       售罄商品                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('售罄商品','/seller/product/product-sellOut',1,0,0,(select uid 
	from (select uresourceId uid from uresource where url='/seller/product/product-searchSellerProducts') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/seller/product/product-sellOut'));

/* Table: uresource ,urole_uresource       批量上传商品                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('批量上传商品','/seller/product/product-batchUpload',1,0,0,
	(select uid from (select uresourceId uid from uresource where url='/seller/product/product-searchSellerProducts') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/seller/product/product-batchUpload'));

/*==============================================================*/
/* 商家订单权限*/
/*==============================================================*/
/* Table: uresource ,urole_uresource       审核通过                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('审核通过','/admin/order/order-passAudit',1,0,0,(select uid 
	from (select uresourceId uid from uresource where url='/admin/order/order-searchOrders4Seller') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/admin/order/order-passAudit'));

/* Table: uresource ,urole_uresource       审核不通过                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('审核不通过','/admin/order/order-rejectAudit',1,0,0,
	(select uid from (select uresourceId uid from uresource where url='/admin/order/order-searchOrders4Seller') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/admin/order/order-rejectAudit'));

/* Table: uresource ,urole_uresource       同意退款                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('同意退款','/admin/order/order-agreePayment',1,0,0,(select uid 
	from (select uresourceId uid from uresource where url='/admin/order/order-searchOrders4Seller') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/admin/order/order-agreePayment'));

/* Table: uresource ,urole_uresource       确认发货                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('确认发货','/admin/order/order-confirmSendProduct',1,0,0,
	(select uid from (select uresourceId uid from uresource where url='/admin/order/order-searchOrders4Seller') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/admin/order/order-confirmSendProduct'));

/* Table: uresource ,urole_uresource       导出订单                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('导出订单','/admin/order/order-exportSellerOrders',1,0,0,(select uid 
	from (select uresourceId uid from uresource where url='/admin/order/order-searchOrders4Seller') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/admin/order/order-exportSellerOrders'));

/*==============================================================*/
/* 商家结算管理权限*/
/*==============================================================*/

 /* Table: uresource ,urole_uresource       申请结算                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('申请结算','/seller/planCheck/plancheck-saveSellerPlanCheck',1,0,0,
	(select uid from (select uresourceId uid from uresource where url='/seller/planCheck/plancheck-searchSellerPlanCheck') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/seller/planCheck/plancheck-saveSellerPlanCheck'));

/* Table: uresource ,urole_uresource       收款确认                                  */
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('收款确认','/seller/planCheck/plancheck-editSellerPlanCheck',1,0,0,
	(select uid from (select uresourceId uid from uresource where url='/seller/planCheck/plancheck-searchSellerPlanCheck') temp));
insert into urole_uresource(uroleUresourceId,uroleId,uresourceId) values(DEFAULT,100003,(select uresourceId from uresource where url= '/seller/planCheck/plancheck-editSellerPlanCheck'));


alter table advertisement add isLogin tinyint(4) comment '是否验证登录0否1是';

insert into adv_position(advpositionId,advType,num,name,code,height,width) values(DEFAULT,1,null,'首页右部广告模式1','home_right_adv_80',80,304);
insert into adv_position(advpositionId,advType,num,name,code,height,width) values(DEFAULT,1,null,'首页右部广告模式2','home_right_adv_300',300,304);