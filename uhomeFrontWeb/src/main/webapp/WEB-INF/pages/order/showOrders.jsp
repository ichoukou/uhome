<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", -10);
%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>订单完成</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/shopping-cart.css?d=${_resVerion}" media="all">
</head>
<body>
<jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
<div class="m-w960p">
	<div class="cart-wrap m-mt30p">
		<div class="submit-order">
		<c:if test="${allCouponFlag != null }">
		<div class="yhq-use">该订单已全部使用优惠券支付，无需再支付。<a href="${_ctxPath}"><span>回首页</span></a></div>
		</c:if>
		<c:if test="${allCouponFlag == null}"> 
		    <div class="hd">请核对金额并支付</div>
		    <form  id="payForm"  method="post" target="_blank" action="${_ctxPath}/order/order-payit.htm">
		    	<input type="hidden" name="aliPay.outTradeNo" value="${aliPay.outTradeNo}" />
		    	<input type="hidden" name="aliPay.subject" value="${aliPay.subject}" />
		    	<input type="hidden" name="aliPay.body" value="${aliPay.body}" />
		    	<input type="hidden" name="aliPay.totalFee" value="${aliPay.totalFee}" />
		    </form>
		    <div class="bd">
		        <ul>
		            <li><div class="tit">您的订单号为：</div><div class="order-num-list">${aliPay.outTradeNo} </div></li>
                    <li><div class="tit">支付方式为：</div><div class="order-num-list">支付宝</div></li>
                    <li><div class="tit">待支付金额：</div><div class="order-num-list"><span class="coin">¥</span>${aliPay.totalFee}元</div></li>
		        </ul>
		    </div>
		    <div class="btn cf"><div class="m-fl">核对无误， 您可以</div><a class="btn-c" href="#"><span>支付订单</span></a></div>
		   </c:if>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
</html>
<script type="text/javascript">
    $('.btn-c').click(function(event){
    	var $paramValue = '${param.orderIds}';
		var orderIds = $paramValue.toString().split("|");
		var oIds = $.param({'ordIds':orderIds},true);
		var isCommit = true;
		$.ajax({
			type : 'POST',
            url  : _ctxPath +'/order/order-payMyOrder.htm',
            data :  oIds,
            async : false,
            success:function(data){
               	 if(data.code == "false"){
               		isCommit = false;
               		alert("亲，该品牌特卖已结束，下次请赶早哦!");
               	 }
            }
		});
		if(isCommit){
			// 新窗口提交表单
			$('#payForm').trigger('submit');
		}
		return false;
	});
	function callAliApi(){
		// 新窗口提交表单
		$('#payForm').trigger('submit');
	}
   // window.setTimeout(callAliApi,2000);
</script>	