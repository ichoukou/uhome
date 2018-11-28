<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>订单详情-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/order.css?d=${_resVerion}"/>
</head>
<body>
    <jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
    <div class="m-w960p cf">
        <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/user/orders.htm">个人中心 ></a>订单详情</div>
        <!--面包屑 end-->
        <jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>
        <div class="inf-detail m-mt20p m-mb10p cf">
          	<%-- 使用优惠券的订单 --%>
          	<c:if test="${orders != null}">
          		<c:forEach items="${orders}" var="orderHead">
          			<div class="ord-num m-mb10p f-weight"><span>订单号：${orderHead.orderNo}</span><span>状态：<b class="orange"><spring:message code="order.status.${orderHead.status}" text="" /></b></span></div>
          		</c:forEach>
          	</c:if>
          	<div class="inf-title cf">
              <div class="f-weight">订单详情</div>
            </div>
            <%-- 使用优惠券的订单 --%>
            <div class="ord-detail">
            	<%-- 使用优惠券的订单 --%>
	         	<div class="ord-address">
		         	<c:if test="${orders != null}">
		               <c:forEach items="${orders}" var="orderHead" begin="0" end="0">
		                <var class="f-weight">收货地址：</var><span>${orderHead.address.region.province} &nbsp; ${orderHead.address.region.city} &nbsp; ${orderHead.address.region.county}&nbsp; ${orderHead.address.receiveAddress}（${orderHead.address.receiverName} 收）${orderHead.address.mobile}</span>
		               </c:forEach>
		            </c:if>
	         	</div>
            </div>
            <div class="ord-detail">
                <var class="ord-mtb f-weight">支付及配送方式</var>
                <ul>
                    <li>支付方式：<span>支付宝快捷支付</span></li>
                    <li>运费：<span class="postage">¥0.00</span></li>
                    <li>运货日期：<span>不限</span></li>
                </ul>
            </div>
          <%-- 未使用优惠券的订单 --%>
           <c:if test="${orders != null}">
           <c:forEach items="${orders}" var="orderHead" begin="0" end="0">
	         	<c:if test="${orderHead.payment.hasInvoice == 1}">
	            <div class="ord-detail">
	                <var class="ord-mtb f-weight">发票信息</var>
	                <ul>
	                    <li>发票类型：<span>普通发票</span></li>
	                    <li>发票抬头：
		                    <span>
		                    	<c:if test="${empty orderHead.payment.invoiceTitle}">个人 </c:if>
		                    	<c:if test="${not empty orderHead.payment.invoiceTitle}">${orderHead.payment.invoiceTitle} </c:if>
		                    </span>
	                    </li>
	                    <li>发票内容：<span>明细</span></li>
	                </ul>
	            </div>
	            </c:if>
            </c:forEach>
            </c:if>
            <div class="order-tab ord-detailtab">
             <%-- 使用优惠券的订单 --%>
             <c:if test="${orders != null}">
           		<c:forEach items="${orders}" var="orderHead">
	         		<table>
                    <thead>
                    <tr>
                        <th width="14%">订单号</th>
                        <th>订单商品</th>
                        <th width="25%">订单金额</th>
                        <th width="25%">获赠积分</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><span class="cor-orange">${orderHead.orderNo}</span></td>
                        <td class="ord-sales">
                            <ul>
                            	<c:forEach items="${orderHead.items}" var="item">
                                <li class="cf">
                                    <div class="pic">
                                        <img src="<zx:thumbImage originalPath='${item.productSku.product.defaultImage}' imageType='t12'></zx:thumbImage>" width="48" height="48"/>
                                    </div>
                                    <div class="info">
                                        <div class="tit"><a href="${_ctxPath}/item-${item.productSku.product.productId}.htm" class="cor-orange">${item.productName}</a>
                                        </div>
                                        <div>
                                          <c:forEach items="${item.productSku.productSkuOptionValues}" var="option" >
											<label>${option.skuOptionValue.skuOption.skuOptionName}:
											<c:choose>
												<c:when test="${not empty option.overrideSkuOptionValue}">
													${option.overrideSkuOptionValue}&nbsp;&nbsp;</label>
												</c:when>
												<c:otherwise>
													${option.skuOptionValue.skuOptionValue}&nbsp;&nbsp;</label>
												</c:otherwise>
											</c:choose>
										  </c:forEach>
                                        </div>
                                    </div>
                                </li>
                                </c:forEach>
                            </ul>
                        </td>
                        <td >
                            <div class="cor-red">
                            ¥${orderHead.payment.closingCostAmount-orderHead.orderPostage.postage}元
                            <%-- 订单商品总金额 --%>
                            <c:set var="productsAmount" value="${productsAmount+orderHead.payment.closingCostAmount}"/>
                            <%-- 订单实际支付总金额 --%>
                            <c:set var="payAmount" value="${payAmount+orderHead.payment.paymentAmount}"/>
                            <%-- 订单运费 --%>
                            <c:set var="postageAmount" value="${postageAmount+orderHead.orderPostage.postage}"/>
                            </div>
                        </td>
                        <td>
                        <c:if test="${orderHead.status >= 2 && orderHead.status != 5}">
                        	获得${uhome:computerPoint(orderHead.payment.paymentAmount)}积分
                        </c:if>	
                        </td>
                    </tr>
                    </tbody>
                </table>
            	</c:forEach>
            </c:if>
            </div>
            <div class="ord-money cf">
                <%-- 使用优惠券的订单 --%>
                <c:if test="${orders != null}">	
                <table class="ord-moneytab">
                    <tr>
                       <td class="ord-mtr">订单总金额：</td>
                       <td>¥${productsAmount-postageAmount}</td>
                    </tr>
                    <tr>
                       <td class="ord-mtr">+运费：</td>
                       <td id="postage">¥${postageAmount}</td>
                    </tr>
                    <tr>
                       <td class="ord-mtr">-优惠：</td>
                       <td>¥${productsAmount - payAmount}</td>
                    </tr>
                    <tr>
                       <td class="ord-mtr">-余额：</td>
                       <td>¥0.00</td>
                    </tr>
                </table>
                </c:if>
            </div>
            <div class="ord-money m-fr">
                <var class="f-weight">订单支付金额</var>
                <%-- 使用优惠券的订单 --%>
            	<c:if test="${orders != null}">	
                <em class="cor-red">¥${payAmount}</em>
                </c:if>
            </div>
        </div>
    </div>
    <script type="text/javascript">
    	/* 将下面运费复制上面显示 */
    	$(function(){
    		$(".postage").empty().text($("#postage").text());
    	});
    </script>
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include> 
</body>
</html>