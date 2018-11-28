delete from user where userId in (select userId from user_info where userId not in(100000));

delete from user_urole where userId in (select userId from user_info where userId not in(100000));

delete from user_info where userId not in(100000);

delete from order_coupon;
alter table order_coupon AUTO_INCREMENT=100000;

delete from order_express;
alter table order_express AUTO_INCREMENT=100000;

delete from order_head;
alter table order_head AUTO_INCREMENT=100000;

delete from order_item;
alter table order_item AUTO_INCREMENT=100000;

delete from order_payment;
alter table order_payment AUTO_INCREMENT=100000;

delete from order_return;
alter table order_return AUTO_INCREMENT=100000;

delete from order_return_item;
alter table order_return_item AUTO_INCREMENT=100000;

delete from order_return_payment;
alter table order_return_payment AUTO_INCREMENT=100000;

delete from user_coupon;
alter table user_coupon AUTO_INCREMENT=100000;

delete from point;
alter table point AUTO_INCREMENT=100000;

delete from point_detail;
alter table point_detail AUTO_INCREMENT=100000;

delete from receiver_address;
alter table receiver_address AUTO_INCREMENT=100000;

delete from suggest;
alter table suggest AUTO_INCREMENT=100000;
