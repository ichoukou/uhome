INSERT INTO `uresource` VALUES(100027,'后台管理员修改密码','/admin/user/user-editPassword',1,0,0,0);

INSERT INTO `urole_uresource` VALUES(100027,100002,100027);

UPDATE `uresource` SET url='/admin/order/order-searchOrders4Seller' WHERE uresourceId=100023;

UPDATE `uresource` SET url='/admin/order/order-searchOrders4Manager' WHERE uresourceId=100024;

