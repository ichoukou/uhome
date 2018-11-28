<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.ExpressMessage" %>
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
            <div class="ord-num f-weight">
            	<!-- 未使用优惠券的订单 -->
            	<span><label>订单号：</label>${orderHead.orderNo}</span>
            	<span><label>状态：</label><b class="orange"><spring:message code="order.status.${orderHead.status}" text="" /></b></span>
            </div>
            <div class="ord-detail">
            	<var class="ord-mtb f-weight">订单跟踪</var>
            	<c:if test="${orderHead.expressMessage != null}">
	            <c:set var="status_success" value="<%=ExpressMessage.STATUS_SUCCESS%>"></c:set>
	            <c:choose>
	             	<c:when test="${orderHead.expressMessage.status eq status_success }">
	            	<table width="743">
	            		<!--<tr>
	            			<th width="30%">处理时间</th>
	            			<th width="70%">处理信息</th>
	            		</tr>-->
	            		<tbody>
			            	<c:forEach items="${orderHead.expressMessage.data }" var="dataItem">
	            			<tr>
	            				<td width="25%">${dataItem.time }</td>
	            				<td width="75%">${dataItem.context }</td>
	            			</tr>
	           				</c:forEach>
	            		</tbody>
	            	</table>
	            	</c:when>
		            <c:otherwise><p>系统正在更新，给您带来不便请见谅，请稍后查询！</p></c:otherwise>
	            </c:choose>
            	</c:if>
            </div>
            <div class="inf-title">
                <span class="f-weight">订单信息</span>
            </div>
            <div class="ord-detail">
	            <!-- 未使用优惠券的订单 -->
	         		<var class="f-weight">收货人信息</var>
	         		<ul>
			       		<li><span>收货人：</span>${orderHead.address.receiverName}</li>
			       		<li><span>地址：</span>${orderHead.address.region.province} &nbsp; ${orderHead.address.region.city} &nbsp; ${orderHead.address.region.county}&nbsp; ${orderHead.address.receiveAddress}</li>
			       		<li><span>固定电话：</span>${orderHead.address.telephone}</li>
			       		<li><span>手机号码：</span>${orderHead.address.mobile}</li>
	         		</ul>
            </div>
            <div class="ord-detail">
                <var class="ord-mtb f-weight">支付及配送方式</var>
                <ul>
                    <li>支付方式：<span>支付宝快捷支付</span></li>
                    <li>运费：<span>¥${orderHead.orderPostage.postage}</span></li>
                    <li>运货日期：<span>不限</span></li>
                	<!-- 未使用优惠券的订单 -->
                	<li>快递公司：<span><c:if test="${not empty orderHead.orderExpress.expressName}">${orderHead.orderExpress.expressName}</c:if></span></li>
                    <li>快递单号：<span><c:if test="${not empty orderHead.orderExpress.mailNo}">${orderHead.orderExpress.mailNo}</c:if></span></li>
                </ul>
            </div>
         <!-- 未使用优惠券的订单 -->
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
            <div class="order-tab ord-detailtab">
             <!-- 未使用优惠券的订单 -->
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
                        <td><span>${orderHead.orderNo}</span></td>
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
                            <div class="cor-red">¥${orderHead.payment.paymentAmount-orderHead.orderPostage.postage}元</div>
                        </td>
                        <td>
                        <c:if test="${orderHead.status >= 2 && orderHead.status != 5}">
                        	获得${uhome:computerPoint(orderHead.payment.paymentAmount)}积分
                        </c:if>	
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="ord-money cf">
                <!-- 未使用优惠券的订单 -->
                <table class="ord-moneytab">
                    <tr>
                       <td class="ord-mtr">订单总金额：</td>
                       <td>
                        <%--使用优惠券的closingCostAmount不为空 --%>
                        <%--
                       <c:if test="${null != orderHead.payment.closingCostAmount}">
                       	¥${orderHead.payment.closingCostAmount}
                       </c:if>
                       <c:if test="${null == orderHead.payment.closingCostAmount}">
                        ¥${orderHead.payment.paymentAmount}
                       </c:if> --%>
                       ¥${null == orderHead.payment.closingCostAmount ? (orderHead.payment.paymentAmount-orderHead.orderPostage.postage) : (orderHead.payment.closingCostAmount-orderHead.orderPostage.postage)}
                       </td>
                    </tr>
                    <tr>
                       <td class="ord-mtr">+运费：</td>
                       <td>¥${orderHead.orderPostage.postage}</td>
                    </tr>
                    <tr>
                       <td class="ord-mtr">-优惠：</td>
                       <c:if test="${null != orderHead.payment.closingCostAmount}">
                       <td>¥${orderHead.payment.closingCostAmount - orderHead.payment.paymentAmount}</td>
                       </c:if>
                       <c:if test="${null == orderHead.payment.closingCostAmount}">
                       <td>¥0.00</td>
                       </c:if>
                    </tr>
                    <tr>
                       <td class="ord-mtr">-余额：</td>
                       <td>¥0.00</td>
                    </tr>
                </table>
            </div>
            <div class="ord-money m-fr">
                <var class="f-weight">订单支付金额</var>
                <!-- 未使用优惠券的订单 -->
            	<c:if test="${orders == null}">	
                <em class="cor-red">¥${orderHead.payment.paymentAmount}</em>
                </c:if>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include> 
</body>
</html>