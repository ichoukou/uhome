/*==============================================================*/
/* Table: uresource      新龙管理员顺序                                     */
/*==============================================================*/

update uresource set rank = '1' where url='/admin/product/product-searchProducts';
update uresource set rank = '2' where url='/admin/plan/plan-searchPlans';
update uresource set rank = '3' where url='/admin/brand/brand-listUserBrans';
update uresource set rank = '4' where url='/admin/user/user-searchSellers';
update uresource set rank = '5' where url='/admin/user/user-searchBuyers';
update uresource set rank = '6' where url='/admin/report/report-report';
update uresource set rank = '7' where url='/admin/adv/adv_searchAdvs';
update uresource set rank = '8' where url='/admin/order/order-searchOrders4Manager';
update uresource set rank = '9' where url='/admin/coupon/coupon-searchCoupons';
update uresource set rank = '10' where url='/admin/auth/auth-listRoles';
update uresource set rank = '11' where url='/admin/supplier/supplier-searchSuppliers';

/*==============================================================*/
/* Table: uresource      卖家顺序                                     */
/*==============================================================*/
update uresource set rank = '1' where url='/seller/product/product-searchSellerProducts';
update uresource set rank = '2' where url='/admin/order/order-searchOrders4Seller';
update uresource set rank = '3' where url='/admin/user/user-sellerDetailSeller';