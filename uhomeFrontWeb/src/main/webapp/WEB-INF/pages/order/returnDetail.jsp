<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
  <meta charset="utf-8"/>
    <title>退货订单-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/return-manage.css?d=${_resVerion}"/>
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/order.css?d=${_resVerion}"/>
	<script type="text/javascript" src="${_jsPath }/plugin/calendar/WdatePicker.js"></script>
</head>
<body>
	 <jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
     <div class="m-w960p cf">
        <!--面包屑-->
      <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/order/order-myOrders.htm">个人中心 ></a>我的退换货</div>
        <!--面包屑 end-->
        <jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>
   		 <div class="inf-detail m-mt20p">
            <div class="inf-title  cf">
                <div class="fon-big m-fl">订单信息</div>
            </div>
            <div class="ord-detail">
            	<ul>
            		<li>订单号：${orderReturn.orderHead.orderNo}</li>
                <li>收货地址：${orderReturn.address.region.province}${orderReturn.address.region.city} ${orderReturn.address.region.county} ${orderReturn.address.receiveAddress}</li>
                <li>联系人：${orderReturn.customerName}</li>
                <li>手机：${orderReturn.telephone}</li>
            	</ul>
            </div>
            <c:forEach items="${orderReturn.returnItems}" var="item">
	            <div class="order-tab ord-detailtab">
	                <table>
	                    <thead>
	                    <tr>
	                        <th COLSPAN="2" class="ord-thtitle" ><span >详细信息</span><a class="onshow"><em class="sp1">收起</em><em class="sp2 hide">展开</em></a></th>
	                    </tr>
	                    </thead>
	                    <tbody class="show-td">
	                    <tr>
	                        <td width="20%"><span class="f-weight" >商品名称</span></td>
	                        <td  width="80%">
	                           <span> ${item.orderItem.productName}</span>
	                        </td>
	
	                    </tr>
	                    <tr>
	                        <td width="20%"><span class="f-weight" >问题描述</span></td>
	                        <td  width="80%">
	                            <span>${item.describe}</span>
	                        </td>
	
	                    </tr>
        				<tr>
	                        <td width="20%"><span class="f-weight" >状态</span></td>
	                        <td  width="80%">
	                            <span>
                           		  <c:if test="${item.status == 1}">未审核</c:if>	
                             	  <c:if test="${item.status == 2}">审核通过</c:if>	
                            	  <c:if test="${item.status == 3}">审核不通过</c:if>
                            	  <c:if test="${item.status == 4}">
                            	  		<c:if test="${ ps eq 0}">
		                              		  		审核通过
		                              		  	</c:if>
		                              	<c:if test="${ ps eq 1}">
		                              		  		完成
		                              	</c:if>
                            	  </c:if>
	                            </span>
	                        </td>
	                    </tr>
	                    <c:if test="${item.status == 3}">
	        				<tr>
		                        <td width="20%"><span class="f-weight" >不通过原因</span></td>
		                        <td  width="80%">
		                            <span>${item.noPassReason}</span>
		                        </td>
		                    </tr>
	                    </c:if>
	                    </tbody>
	                </table>
	            </div>
            </c:forEach>
        </div>
   		 </div>
        <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<script>
$(function(){
	$(".onshow").on("click",function(){
		$(this).find("em").toggle();
		$(this).parents("thead").next().toggle();
	});
});
</script>
</body>
</html>