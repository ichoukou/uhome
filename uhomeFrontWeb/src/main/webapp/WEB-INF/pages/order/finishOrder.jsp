<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page  import="com.ytoxl.module.uhome.uhomeorder.dataobject.OrderHead"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html>
<head>
    <meta charset="utf-8"/>
    <title></title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/shopping-cart.css?d=${_resVerion}" media="all">
    
</head>
<body>
    <jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
    <div class="m-w960p">
    	<div class="cart-wrap m-mt30p">
		    <div class="cf">
		        <div class="step-status step-3"><span class="step">我的购物车</span><span class="step">确认订单信息</span><span class="step">成功提交订单</span></div>
		    </div>
	        <!--订单提交成功-->
	        <div class="success">
	            <div class="hd">恭喜您,订单已成功提交!</div>
	            <div class="bd">交易成功后，您将可以获得<var><fmt:formatNumber value="${point}" type="number" /></var>${_webSiteName}积分</div>
	            <div class="ft cf">
	            
	             
	            <a href="${_ctxPath}" class="btn-c"><span>继续购物</span></a><a href="javascript:;" class="btn-e"  id="showOrder1"><span>查看订单</span></a>
	            </div>
	        </div>
	        <!--订单提交成功 end-->
        </div>
    </div>
    <form id="form1" method="post" action="${_ctxPath}/order/order-myOrders.htm"><input type="hidden" id="status" name="orderPage.params.status" value="${status[1]}" /> </form>
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
</html>
<script type="text/javascript">
    $("#showOrder1").on('click',function(){
    	var form1=$("#form1");
    	form1.submit();
    	return false;
    });
</script>