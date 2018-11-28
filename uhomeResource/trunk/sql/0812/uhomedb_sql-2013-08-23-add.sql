/*==============================================================*/
/* Table: seller                                           */
/*==============================================================*/
ALTER TABLE seller MODIFY bankName varchar(30) DEFAULT NULL COMMENT '开户银行'; 


drop table if exists links;

/*==============================================================*/
/* Table: links                                                 */
/*==============================================================*/
create table links 
(
   linkId               int(11)                        not null   AUTO_INCREMENT,
   name                 varchar(24)                    null,
   linkUrl              varchar(200)                   null,
   sort                 int(11)                        null,
   creatTime           timestamp                      NOT NULL DEFAULT '0000-00-00 00:00:00',
   updateTime           timestamp 					   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   constraint PK_LINKS primary key clustered (linkId)
)ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='网站链接';

INSERT INTO links (name,linkUrl,sort) VALUES ('淘宝网站', 'http://taobao.com', 1);
INSERT INTO links (name,linkUrl,sort) VALUES ('百度', 'http://www.baidu.com', 2);
INSERT INTO links (name,linkUrl,sort) VALUES ('天品', 'http://www.tianpin.com', 3);
INSERT INTO links (name,linkUrl,sort) VALUES ('天猫', 'http://www.tmall.com', 4);
INSERT INTO links (name,linkUrl,sort) VALUES ('一淘', 'http://www.etao.com', 5);
INSERT INTO links (name,linkUrl,sort) VALUES ('赶集网', 'http://www.ganji.com', 6);
/*==============================================================*/
/* Table: uresource                                           */
/*==============================================================*/
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values('友情链接','/admin/link/link-searchLinks',1,0,0,(select uid from (select uresourceId uid from uresource where url='/admin/adv/adv_searchAdvs') temp));
insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/link/link-searchLinks'));

/*==============================================================*/
/* Table: order_head                                           */
/*==============================================================*/
ALTER TABLE order_head ADD COLUMN `receiveProductTime` TIMESTAMP null DEFAULT NULL COMMENT '收货时间'; 
/*==============================================================*/
/* Table: order_head   更新    receiveProductTime 值为updateTime      */
/*==============================================================*/
update order_head set receiveProductTime = updateTime,updateTime = receiveProductTime where status in(4,6) and receiveProductTime is null;


/*==============================================================*/
/* Table: product_category                                      */
/*==============================================================*/
update product_category set name='家居用品' where name='日用家居';