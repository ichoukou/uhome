drop table if exists coupon;

drop table if exists coupon_config;

drop table if exists `event`;

drop table if exists user_coupon;

drop table if exists order_coupon;

/*==============================================================*/
/* Table: coupon                                                */
/*==============================================================*/
create table coupon
(
   couponId             int not null auto_increment comment '优惠券明细ID',
   couponNo            varchar(20) comment '优惠券号',
   isActive             tinyint comment '激活状态 0=未激活，1=已激活',
   activeTime           timestamp default '0000-00-00 00:00:00' comment '激活时间',
   wxuFlag              varchar(64) comment '微信激活用户唯一标识',
   packageId			int comment '活动包ID',
   couponConfigId       int comment '优惠券配置ID',
   primary key (couponId)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='优惠券明细';

Alter table coupon add unique(couponNo);

/*==============================================================*/
/* Table: coupon_config                                         */
/*==============================================================*/
create table coupon_config
(
   couponConfigId       int not null auto_increment comment '配置ID',
   allowance            decimal(10,2) comment '抵扣金额',
   limitCharge          decimal(10,2) comment '最低消费金额',
   startTime            timestamp default '0000-00-00 00:00:00' comment '开始时间',
   endTime              timestamp default '0000-00-00 00:00:00' comment '结束时间',
   couponCount          int comment '单个用户优惠券数量',
   eventId              int comment '活动ID',
   primary key (couponConfigId)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='优惠券配置';

/*==============================================================*/
/* Table: event                                                 */
/*==============================================================*/
create table `event`
(
   eventId              int not null auto_increment comment '活动ID',
   eventName            varchar(64) comment '活动名称',
   eventCount           int comment '优惠券数量',
   `type`               tinyint comment '0=普通活动，1=微信活动',
   `status`             tinyint comment '活动状态(0：正常，1：已禁用，2：已删除)',
   createTime           timestamp default '0000-00-00 00:00:00',
   updateTime           timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   primary key (eventId)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='活动';

/*==============================================================*/
/* Table: user_coupon                                           */
/*==============================================================*/
create table user_coupon
(
   userCouponId         int not null auto_increment comment 'ID',
   userId               int comment '用户ID',
   username             varchar(64) comment '用户名',
   couponNo             varchar(20) comment '优惠券号',
   couponStatus         tinyint comment '优惠券状态 0：未使用 1：已使用 2：已过期',
   couponId             int comment '优惠券明细ID',
   couponConfigId       int comment '配置ID',
   primary key (userCouponId)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='用户优惠券关联表';

/*==============================================================*/
/* Table: order_coupon                                          */
/*==============================================================*/
create table order_coupon
(
   orderCouponId        int not null auto_increment,
   orderId              int,
   couponNo             varchar(20),
   allowance            decimal(10,2) comment '抵扣金额',
   limitCharge          decimal(10,2) comment '最低消费金额',
   createTime           timestamp default '0000-00-00 00:00:00',
   primary key(orderCouponId)
)ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='订单优惠券关联表';

/*==============================================================*/
/* Table: order_head                                           */
/*==============================================================*/
alter table order_head add column parentOrderId int;

/*==============================================================*/
/* Table: order_item                                           */
/*==============================================================*/
alter table order_item add column rebatePrice decimal(10,2);

/*==============================================================*/
/* Table: order_payment	                                       */
/*==============================================================*/
alter table order_payment add column closingCostAmount decimal(10,2);

/*==============================================================*/
/* Table: order_return_payment	                               */
/*==============================================================*/
alter table order_return_payment add column closingCostAmount decimal(10,2);
/*增加应退金额*/
ALTER TABLE order_return_item
ADD COLUMN refundAmount  decimal(10,2) NULL COMMENT '应退金额';
/*增加更新时间*/
ALTER TABLE order_return_item
ADD COLUMN updateTime timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';
alter table user add unique(email);
alter table user_info add unique(email);
/*更新order_return_item*/
update order_return_item a set a.refundamount=(select b.closingcost*a.num from order_item b where a.orderitemid=b.orderitemid) where a.refundamount is null;
/*更新后台优惠券权限管理信息*/
insert into uresource(uresourceName,url,status,isMenu,rank) values('后台管理优惠券管理','/admin/coupon/coupon-searchCoupons',1,0,0);
insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/coupon/coupon-searchCoupons'));

