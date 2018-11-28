drop table if exists order_crm;

/*==============================================================*/
/* Table: order_crm        客服信息                                     						*/
/*==============================================================*/
create table order_crm
(
   orderCrmId           int(11) not null  AUTO_INCREMENT,
   orderId              int(11) comment '订单id',
   userId               int(11) comment '操作人id',
   userName             varchar(64),
   recordTime           timestamp not null default '0000-00-00 00:00:00' comment '记录时间',
   remark               varchar(1000) comment '描述信息',
    PRIMARY KEY (`orderCrmId`)
)ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='客服信息';

alter table order_crm comment '客服信息';


/*==============================================================*/
/* Table: uresource                                             */
/*==============================================================*/
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('客服事件','/admin/orderCrm/orderCrm-addOrderCrm',1,0,0,(select uid from (select uresourceId uid from uresource where url='/admin/order/order-searchOrders4Manager') temp));
/*==============================================================*/
/* Table: urole_uresource                                       */
/*==============================================================*/
insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/orderCrm/orderCrm-addOrderCrm'));


/*==============================================================*/
/* Table: region                                                */
/*==============================================================*/

delete from region where regionCode is null;

delete from region where regionId in (9271,9272);

update region set parentRegionId = -1 where regionId in(9254,9264,9268);

/*==============================================================*/
/* Table: express                                             */
/*==============================================================*/
UPDATE express SET companyCode = 'guotongkuaidi' WHERE expressName = '国通物流';

ALTER TABLE order_express MODIFY mailNo varchar(30) DEFAULT NULL COMMENT '快递单号'; 