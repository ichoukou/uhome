drop table if exists notice;

/*==============================================================*/
/* Table: notice   公告                                             */
/*==============================================================*/
CREATE TABLE `notice` (
  `noticeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(256) NOT NULL COMMENT '标题',
  `type` tinyint(4) NOT NULL COMMENT '公告类型,1-活动资讯；2-网站公告',
  `source` varchar(256) DEFAULT NULL COMMENT '来源',
  `content` longtext NOT NULL COMMENT '公告内容',
  `authorId` int(11) DEFAULT NULL COMMENT '作者ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态：1-未审核；2-已审核',
  `isTop` tinyint(4) DEFAULT NULL COMMENT '是否置顶；0-未置顶；1-已置顶；',
  `checkerId` int(11) DEFAULT NULL COMMENT '审核人ID',
  `topTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '置顶时间（可反复置顶）',
  `publishTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发布时间（即审核通过时间）',
  `createTime` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='公告表';



/*==============================================================*/
/* 公告后台相关权限                                                                      					*/
/*==============================================================*/
-- 查询公告
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('公告栏','/admin/notice/notice-searchNotice',1,0,0,(select uresourceId from (select uresourceId from uresource where url='/admin/adv/adv_searchAdvs') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/notice/notice-searchNotice'));

-- 添加公告
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('添加公告','/admin/notice/notice-addNotice',1,0,0,(select uresourceId from (select uresourceId from uresource where url='/admin/adv/adv_searchAdvs') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/notice/notice-addNotice'));

-- 修改公告
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('修改公告','/admin/notice/notice-updateNotice',1,0,0,(select uresourceId from (select uresourceId from uresource where url='/admin/adv/adv_searchAdvs') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/notice/notice-updateNotice'));

-- 删除公告
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('删除公告','/admin/notice/notice-deleteNotice',1,0,0,(select uresourceId from (select uresourceId from uresource where url='/admin/adv/adv_searchAdvs') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/notice/notice-deleteNotice'));

-- 审核公告
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('审核公告','/admin/notice/notice-checkNotice',1,0,0,(select uresourceId from (select uresourceId from uresource where url='/admin/adv/adv_searchAdvs') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/notice/notice-checkNotice'));

-- 置顶公告
insert into uresource (uresourceName,url,status,isMenu,rank,parentId) values
	('置顶公告','/admin/notice/notice-updateNoticeTop',1,0,0,(select uresourceId from (select uresourceId from uresource where url='/admin/adv/adv_searchAdvs') temp) );

insert into urole_uresource(uroleId,uresourceId) values(100002,(select uresourceId from uresource where url='/admin/notice/notice-updateNoticeTop'));
--首页轮播广告位
INSERT INTO `adv_position` (advType,num,name,code,height,width) VALUES ('2', null, '首页轮播广告模式', 'home_broadcast_adv', '280', '960');