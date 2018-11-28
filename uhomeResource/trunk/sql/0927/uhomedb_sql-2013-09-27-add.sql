drop table if exists plan_check;

/*==============================================================*/
/* Table: order_crm        结算管理                                     */
/*==============================================================*/
CREATE TABLE `plan_check` (                                                                                 
              `planCheckId` int(11) NOT NULL AUTO_INCREMENT,                                                            
              `planId` int(11) DEFAULT NULL COMMENT '排期id',                                                         
              `sellerId` int(11) DEFAULT NULL COMMENT '卖家id',                                                       
              `feedbackCount` tinyint(4) DEFAULT NULL COMMENT '反馈次数(1:待一次对账；2：待二次对账)',  
              `feedbackTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '反馈时间',                   
              `feedbackStatus` tinyint(4) DEFAULT NULL COMMENT '反馈状态(0:未完成；1已完成)',                
              `userId` int(11) DEFAULT NULL COMMENT '操作人id',                                                      
              `createTime` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '申请时间',                         
              `isConfirm` tinyint(4) DEFAULT NULL COMMENT '是否确认收款',                                         
              PRIMARY KEY (`planCheckId`)                                                                               
            ) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='结算管理';

alter table plan_check comment '结算管理';


/*==============================================================*/
/* Table: uresource                                             */
/*==============================================================*/
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) 
	values('结算管理','/seller/planCheck/plancheck-searchSellerPlanCheck',1,1,4,0);

/*==============================================================*/
/* Table: urole_uresource                                       */
/*==============================================================*/
insert into urole_uresource(uroleId,uresourceId) values
	(100003,(select uresourceId from uresource where url='/seller/planCheck/plancheck-searchSellerPlanCheck'));

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('商品月结报表','/admin/report/report-productMonthReport',1,0,1,
	(select uid from (select uresourceId uid from uresource where url='/admin/report/report-report') temp));

insert into urole_uresource(uroleId,uresourceId) values(100002,
	(select uresourceId from uresource where url='/admin/report/report-productMonthReport'));

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('商品类报表管理','/admin/report/productReport-everydaySalesDetailReport',1,0,2,
	(select uid from (select uresourceId uid from uresource where url='/admin/report/report-report') temp));

insert into urole_uresource(uroleId,uresourceId) values(100002,
	(select uresourceId from uresource where url='/admin/report/productReport-everydaySalesDetailReport'));

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('结算管理','/admin/planCheck/planCheck-listPlanCheck',1,0,3,
	(select uid from (select uresourceId uid from uresource where url='/admin/report/report-report') temp));

insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('运营管理报表','/admin/report/operationsReport-productSalesReport',1,0,4,
	(select uid from (select uresourceId uid from uresource where url='/admin/report/report-report') temp));

insert into urole_uresource(uroleId,uresourceId) values(100002,
	(select uresourceId from uresource where url='/admin/report/operationsReport-productSalesReport'));
	
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('特卖对账单','/admin/report/saleStatementReport-orderWaitSendReport',1,0,5,
	(select uid from (select uresourceId uid from uresource where url='/admin/report/report-report') temp));

insert into urole_uresource(uroleId,uresourceId) values(100002,
	(select uresourceId from uresource where url='/admin/report/saleStatementReport-orderWaitSendReport'));

/*==============================================================*/
/* Table: brand                                          */
/*==============================================================*/
ALTER TABLE brand ADD COLUMN isForbidden TINYINT(4) NOT NULL DEFAULT 0 COMMENT '品牌使用状态:0=不禁用，1=禁用';

/*==============================================================*/
/* Table: uresource                                             */
/*==============================================================*/
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('禁用品牌','/admin/brand/brand-forbiddenBrand',1,0,0,(select uid from (select uresourceId uid from uresource where url='/admin/brand/brand-listUserBrans') temp));
/*==============================================================*/
/* Table: urole_uresource                                       */
/*==============================================================*/
insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/brand/brand-forbiddenBrand'));


drop table if exists brand_sort;

/*==============================================================*/
/* Table: brand_sort                                            */
/*==============================================================*/
create table brand_sort
(
   brandSortId          int(11) NOT NULL AUTO_INCREMENT,
   brandId              int(11) NOT NULL comment '品牌id',
   sort                 tinyint NOT NULL,
   createTime           timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' comment '创建时间',
   updateTime           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
   primary key (brandSortId)
)ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='品牌优化排序表';

alter table brand_sort comment '品牌优化排序';

/*==============================================================*/
/* Table: order_postage                                            */
/*==============================================================*/
DROP TABLE IF EXISTS `order_postage`;
CREATE TABLE `order_postage` (
  `orderPostageId` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) DEFAULT NULL COMMENT '订单id',
  `postage` decimal(10,2) DEFAULT NULL COMMENT '邮费',
  PRIMARY KEY (`orderPostageId`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='订单邮费信息';




