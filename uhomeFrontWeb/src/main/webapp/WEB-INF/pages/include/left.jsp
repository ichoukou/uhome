<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div class="m-fl person-bread m-mt20p">
    <ul>
        <li id="myorder" ><a href="${_ctxPath}/user/orders.htm">我的订单<i></i></a></li>
        <li id="user"><a href="${_ctxPath}/user/userInfo.htm">我的资料<i></i></a></li>
        <li id="address"><a href="${_ctxPath}/user/receiverAddress.htm">我的收货地址<%--<font color="red"><span id="myAddresss"></span></font>--%><i></i></a></li>
        <li id="joinMe"><a href="${_ctxPath}/user/joinMe.htm">邀请好友<i></i></a></li>
        <li id="returnOrder"><a href="${_ctxPath}/user/returnOrders.htm">退货管理 <i></i></a></li>
        <li id="getWord"><a href="${_ctxPath}/user/reNewPassWord.htm">修改密码<i></i> </a></li>
        <li id="point"><a href="${_ctxPath}/user/points.htm">积分管理<i></i></a></li>
        <li id="mycoupon"><a href="${_ctxPath}/user/userCoupons.htm">我的优惠券<i></i></a></li>
    </ul>
</div>
<script type="text/javascript" src="${_jsPath }/pages/left.js?d=${_resVerion}"></script>
