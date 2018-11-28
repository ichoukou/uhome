<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>我的订单-个人中心-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/order.css?d=${_resVerion}"/>
</head>
<body>
	<jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
    <div class="m-w960p cf">
    	 <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/order/order-myOrders.htm">个人中心 ></a>我的订单</div>
        <!--面包屑 end-->
        <%--<div class="m-rel">--%>
            <%--<div class="cs-tel">客服热线  <span>021-64703131</span></div>--%>
        <%--</div>--%>
		<jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>
        <div class="inf-detail inf-mine m-mt20p">
            <form method="post" action="${_ctxPath}/order/order-myOrders.htm" id="searchForm">
            <div class="inf-title fon-big">我的订单</div>
            <div class="ord-select layout">
                <div class="ord-search">
	               	<input class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="" type="text" name="orderPage.params.beginTime" value="${orderPage.params.beginTime}" style="width:160px">
	                <label class="date-to">至</label>
	                <input class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="" type="text" name="orderPage.params.endTime" value="${orderPage.params.endTime}" style="width:160px">
                </div>
                <div class="ord-search">
                    <select class="addr-region" name="orderPage.params.status" id='order_status'>
                        <option value=""><spring:message code="select.all" text=""/></option>
                    <c:forEach items="${status}" var="value">
                        <option value="${value}"  <c:if test="${orderPage.params.status==value}">selected="selected"</c:if>>
                        <spring:message code="order.status.${value}" text=""/></option>
                    </c:forEach>
                    </select>
                </div>
                <input type='submit' class="ord-submit" value='查询'>
            </div>
            </form>
            <div class="order-tab">
                <table>
                    <thead>
                    <tr>
                        <th width="16%">订单号</th>
                        <th width="24%">订单商品</th>
                        <th width="12%">收货人</th>
                        <th width="12%">订单支付金额</th>
                        <th width="16%">下单时间</th>
                        <th width="10%">订单状态</th>
                        <th width="10%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${orderPage.result}" var="order" >
                    <!-- 使用优惠券的订单 -->
                    <c:if test="${null == order.parentOrderId && null == order.status}">
                    	<c:forEach items="${order.childOrders}" var="order2" varStatus="vs">
                   			<tr>
                   				<td style="border-top:0px;"><span class="cor-orange"><label>${order2.orderNo}</label></span></td>
		                        <td class="ord-sales">
		                            <ul>
		                                <c:set var="type" value="0" />
		                            	<c:forEach items="${order2.items}" var="item">
		                                <li class="layout">
		                                    <div class="m-fl">
		                                        <img src="<zx:thumbImage originalPath='${item.productSku.product.defaultImage}' imageType='t12'></zx:thumbImage>"/>
		                                    </div>
		                                    <div class="m-fr ">
		                                        <c:set var="type" value="${item.orderSource}" />
		                                        <div><a href="${_ctxPath}/item-${item.productSku.product.productId}.htm" class="cor-orange">${item.productName}</a>
		                                        </div>
		                                        <div>
	                                       	    <c:forEach items="${item.productSku.productSkuOptionValues}" var="option" >
													<label>${option.skuOptionValue.skuOption.skuOptionName}:
													<c:choose>
														<c:when test="${not empty option.overrideSkuOptionValue}">
															${option.overrideSkuOptionValue}
														</c:when>
														<c:otherwise>
															${option.skuOptionValue.skuOptionValue}
														</c:otherwise>
													</c:choose>
													</label>
												</c:forEach>
												<div><label>数量:${item.num}</label></div>
		                                        </div>
		                                    </div>
		                                </li>
		                                </c:forEach>
		                            </ul>
		                        </td>
		                        <c:if test="${vs.index == 0 && fn:length(order.childOrders) > 1}">
		                        	<td rowspan="${fn:length(order.childOrders)}">${order2.address.receiverName}</td>
		                        </c:if>
		                        <c:if test="${vs.index == 0 && fn:length(order.childOrders) == 1}">
		                        	<td >${order2.address.receiverName}</td>
		                        </c:if>
		                        <td>
		                            <div class="cor-red">¥${order2.payment.paymentAmount}元</div>
		                            <!-- 已经付款的获得积分 待发货-->
		                            <c:if test="${order2.status >= 2 && order2.status != 5}">
		                            	<div>获得${uhome:computerPoint(order2.payment.paymentAmount)}积分</div>
		                            </c:if>
		                        </td>
		                        <td><fmt:formatDate value="${order2.createTime}" type="both" /></td>
		                        <c:if test="${vs.index == 0 && fn:length(order.childOrders) > 1}">
			                        <c:if test="${order2.status == 1}">
			                        <td rowspan="${fn:length(order.childOrders)}">
			                        	<span class="f-weight"><spring:message code="order.status.${order2.status}" text="" /></span>
			                        </td>
			                        <td rowspan="${fn:length(order.childOrders)}" class="ord-operation">
			                        	<a href="${_ctxPath}/order/order-getOrderCoupons.htm?orderIds=${order.childOrderIds}&orderId=${order2.orderId}" class="cor-orange view">查看</a>
			                        	<a href="javascript:;" class="cor-orange cancel" id="corOrangeCancel" value="${order.childOrderIds}">取消</a>
			                        	<a href="javascript:;" id="corOrangePayment" value="${order.childOrderIds}" class="cor-orange payment" target="_blank">立即付款</a>
			                        </td>
			                        </c:if>
		                        </c:if>
		                        <c:if test="${order2.status != 1}">
			                        <td>
			                        	<span class="f-weight"><spring:message code="order.status.${order2.status}" text="" /></span>
			                        </td>
			                        <td class="ord-operation">
			                        	<a target="_blank" href="${_ctxPath}/order/order-getOrderCoupons.htm?orderIds=${order.childOrderIds}&orderId=${order2.orderId}" class="cor-orange view">查看</a>
			                        	<c:if test="${order2.status == 3}">
			                        		<a href="javascript:;" class="cor-orange receive" value="${order2.orderId}">确认收货</a>
			                        	</c:if>
				                        <c:if test="${order2.allowReturn == 1}">
				                        	<a href="${_ctxPath}/returnOrder/returnOrder-myOrders.htm?tuihuoOrderIds=${order2.orderId}" class="cor-orange">申请退货</a>
				                        </c:if>
			                        </td>
			                    </c:if>
		                        <c:if test="${fn:length(order.childOrders) == 1 && order2.status == 1}">
			                        <td>
			                        	<span class="f-weight"><spring:message code="order.status.${order2.status}" text="" /></span>
			                        </td>
			                        <td class="ord-operation">
			                        	<%-- 
			                        	<a href="${_ctxPath}/order/order-getOrder.htm?orderHead.orderId=${order2.orderId}" class="cor-orange" >查看</a>
			                        	<a href="javascript:;" class="cor-orange cancel" id="corOrangeCancel" value="${order.childOrderIds}">取消</a>
			                        	<a href="javascript:;" id="corOrangePayment" value="${order.childOrderIds}" class="cor-orange payment" target="_blank">立即付款</a>
			                        	 --%>
			                        	<a href="${_ctxPath}/order/order-getOrderCoupons.htm?orderIds=${order.childOrderIds}&orderId=${order2.orderId}" class="cor-orange view">查看</a>
			                        	<c:if test="${order2.status == 1}">
				                        	<a href="${_ctxPath}/order/order-payMyOrder.htm?ordIds=${order2.orderId}" class="cor-orange" target="_blank">立即付款</a>
				                        	<a href="javascript:;" class="cor-orange cancel" value="${order2.orderId}">取消</a>
				                        </c:if>
			                        	<c:if test="${order2.status == 3}">
			                        		<a href="javascript:;" class="cor-orange receive" value="${order2.orderId}">确认收货</a>
			                        	</c:if>
				                        <c:if test="${order2.allowReturn == 1}">
				                        	<a href="${_ctxPath}/returnOrder/returnOrder-myOrders.htm?tuihuoOrderIds=${order2.orderId}" class="cor-orange">申请退货</a>
				                        </c:if>
			                        </td>
		                        </c:if>
		                        <%-- 
		                        <td class="ord-operation">
			                        
			                        <a href="${_ctxPath}/order/order-getOrder.htm?orderHead.orderId=${order2.orderId}" class="cor-orange" >查看</a>
			                       
			                        <a href="${_ctxPath}/order/order-getOrderCoupons.htm?orderIds=${order.childOrderIds}" class="cor-orange view">查看</a>
			                        <c:if test="${fn:length(order.childOrders) == 1 || vs.index == (fn:length(order.childOrders))/2}">
			                        	<a href="${_ctxPath}/order/order-getOrderCoupons.htm?orderIds=${order.childOrderIds}" class="cor-orange view">查看</a>
				                        <c:if test="${order2.status == 1}">
				                        	${_ctxPath}/order/order-payMyOrder.htm?ordIds=${order.childOrderIds}
				                        	<a href="javascript:;" id="corOrangePayment" value="${order.childOrderIds}" class="cor-orange payment" target="_blank">立即付款</a>
				                        </c:if>
				                        <c:if test="${order2.status == 1 }">
				                        	<a href="javascript:;" class="cor-orange cancel" id="corOrangeCancel" value="${order.childOrderIds}">取消</a>
				                        </c:if>
				                    </c:if>
			                        <c:if test="${order2.status == 3}">
			                        	<a href="javascript:;" class="cor-orange receive" value="${order2.orderId}">确认收货</a>
			                        </c:if>
			                        <c:if test="${order2.allowReturn == 1}">
			                        	<a href="${_ctxPath}/returnOrder/returnOrder-myOrders.htm?tuihuoOrderIds=${order2.orderId}" class="cor-orange">申请退货</a>
			                        </c:if>
		                        </td>
		                         --%>
		                    </tr>
                    	</c:forEach>
                    </c:if>
                    <!-- 没有使用优惠券的订单 -->
                    <c:if test="${null == order.parentOrderId && null != order.status}">
                    <tr>
                        <td><span class="cor-orange"><label><input type="checkbox" class="ord-checkbox"  status-value="${order.status}" data-value="${order.orderId}" <c:if test="${order.status != 1 and order.status != 3}">disabled="disabled"</c:if>> ${order.orderNo}</label></span></td>
                        <td class="ord-sales">
                            <ul>
                                <c:set var="type" value="0" />
                            	<c:forEach items="${order.items}" var="item">
                                <li class="layout">
                                    <div class="m-fl">
                                        <img src="<zx:thumbImage originalPath='${item.productSku.product.defaultImage}' imageType='t12'></zx:thumbImage>"/>
                                    </div>
                                    <div class="m-fr ">
                                        <c:set var="type" value="${item.orderSource}" />
                                        <div><a href="${_ctxPath}/item-${item.productSku.product.productId}.htm" class="cor-orange">${item.productName}</a>
                                        </div>
                                        <div>
                                       	    <c:forEach items="${item.productSku.productSkuOptionValues}" var="option" >
												<label>${option.skuOptionValue.skuOption.skuOptionName}:
												<c:choose>
													<c:when test="${not empty option.overrideSkuOptionValue}">
														${option.overrideSkuOptionValue}
													</c:when>
													<c:otherwise>
														${option.skuOptionValue.skuOptionValue}
													</c:otherwise>
												</c:choose>
												</label>
											</c:forEach>
                                        </div>
                                        <div><label>数量：${item.num}</label></div>
                                    </div>
                                </li>
                                </c:forEach>
                            </ul>
                        </td>
                        <td >${order.address.receiverName}</td>
                        <td >
                            <div class="cor-red">¥${order.payment.paymentAmount}元</div>
                            <c:if test="${order.status >= 2 && order.status != 5}">
                            	<div>获得${uhome:computerPoint(order.payment.paymentAmount)}积分</div>
                       		</c:if>
                        </td>
                        <td><fmt:formatDate value="${order.createTime}" type="both" /></td>
                        <td><span class="f-weight"><spring:message code="order.status.${order.status}" text="" /></span></td>
                        <td class="ord-operation">
	                        <a target="_blank" href="${_ctxPath}/order/order-getOrder.htm?orderHead.orderId=${order.orderId}" class="cor-orange" >查看</a>
	                        <c:if test="${order.status == 1}">
	                        	<a href="${_ctxPath}/order/order-payMyOrder.htm?ordIds=${order.orderId}" class="cor-orange" target="_blank">立即付款</a>
	                        </c:if>
	                        <c:if test="${order.status == 1 }">
	                        	<a href="javascript:;" class="cor-orange cancel" value="${order.orderId}">取消</a>
	                        </c:if>
	                        <c:if test="${order.status == 3}">
	                        	<a href="javascript:;" class="cor-orange receive" value="${order.orderId}">确认收货</a>
	                        </c:if>
	                        <c:if test="${ order.allowReturn == 1}">
	                        	<a href="${_ctxPath}/returnOrder/returnOrder-myOrders.htm?tuihuoOrderIds=${order.orderId}" class="cor-orange">申请退货</a>
	                        </c:if>
                        </td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="bm-control">
                <ul>
                    <li><label><input type="checkbox" class="ord-checked-all" /> 全选</label></li>
                    <li><a href="javascript:;" id="batch-pay">合并付款</a></li>
                    <li><a href="javascript:;" id="batch-receive">批量确认收货</a></li>
                </ul>
            </div>
	         <c:if test="${not empty orderPage.result}">
		     	<wms:commPage page="${orderPage}" beanName="orderPage"></wms:commPage>
		     </c:if>
        </div>
    </div>

	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
	<script type="text/javascript" src="${_jsPath }/plugin/calendar/WdatePicker.js"></script>
	<script type="text/javascript" src="${_jsPath }/pages/myOrders.js?d=${_resVerion}"></script>
    <script type="text/javascript">
    	//使用优惠券付款连接
    	$("#corOrangePayment").on("click",function(event){
    		var $this = $(this);
    		var orderIds = $this.attr("value").split("|");
    		var oIds = $.param({'ordIds':orderIds},true);
    		window.open(_ctxPath +'/order/order-payMyOrder.htm?' + oIds,"_blank");
    		return false;
    	});
    	//取消使用优惠券的订单
    	$("#corOrangeCancel").on("click",function(event){
    		 var $this = $(this);
    		 var orderIds = $this.attr("value");
 	    	 $.dialog({
                 title: false,
                 padding: "15px 20px 8px",
                 content: "<span style='color:#C00;'>确定取消该订单吗？</span>",
                 follow: $this.get(0),
                 okValue: "确定",
                 ok: function(){
                	 //取消该订单
                     $.ajax({
                         type : 'POST',
                         url  : '${_ctxPath }/order/order-cancelOrders.htm',
                         data :  {"orderIds":orderIds},
                         success:function(html){
                        	 if(html.code == "true"){
                        		 window.location.reload();
                        	 }else{
                        		 alert(html.info);
                        	 }
                         }
                     });
                 },
                 cancelValue: "取消",
                 cancel: true
             });
 	    	 $(".d-close").hide();
			return false;
    	});
    	
 	    $(".cor-orange.cancel").on("click",function(event){
 	    	 var $this = $(this);
 	    	 $.dialog({
                 title: false,
                 padding: "15px 20px 8px",
                 content: "<span style='color:#C00;'>确定取消该订单吗？</span>",
                 follow: $this.get(0),
                 okValue: "确定",
                 ok: function(){
                	 //取消该订单
                     $.ajax({
                         type : 'POST',
                         url  : '${_ctxPath }/order/order-cancelOrder.htm',
                         data :  {"orderHead.orderId" : $this.attr("value")},
                         success:function(html){
                        	 if(html.code == "true"){
                        		 window.location.reload();
                        	 }else{
                        		 alert(html.info);
                        	 }
                         }
                     });
                 },
                 cancelValue: "取消",
                 cancel: true
             });
 	    	 $(".d-close").hide();
			return false;
		}); 
 	    
 	    $(".cor-orange.receive").on("click",function(event){
	    	 var $this = $(this);
	    	 $.dialog({
                title: false,
                padding: "15px 20px 8px",
                content: "<span style='color:#C00;'>确认已经收到该商品？</span>",
                follow: $this.get(0),
                okValue: "确定",
                ok: function(){
               	 //收到该订单
                    $.ajax({
                        type : 'POST',
                        url  : '${_ctxPath }/order/order-receiveOrder.htm',
                        data :  {"ordIds" : $this.attr("value")},
                        success:function(html){
                       	 if(html.code == "true"){
                       		 window.location.reload();
                       	 }else{
                       		 alert(html.info);
                       	 }
                        }
                    });
                },
                cancelValue: "取消",
                cancel: true
            });
	    	 $(".d-close").hide();
			return false;
		}); 
 	    //批量收货
 	    $("#batch-receive").on("click",function(event){
            var $this = $(this);
            if($('.ord-checkbox:checked').length > 0){
                var chk_value =new Array();
                $('.ord-checkbox:checked').each(function(){
                	var statusValue = $(this).attr("status-value");
                	if(statusValue=='3'){//只添加 已发货 的订单
                        chk_value.push($(this).attr("data-value"));
                    	}
                });
                if(chk_value.length==0){
                	return;
                }
                var oIds = $.param({'ordIds':chk_value},true);
                $.dialog({
                    title: false,
                    padding: "15px 20px 8px",
                    content: "<span style='color:#C00;'>确认已经收到这些商品？</span>",
                    follow: $this.get(0),
                    okValue: "确定",
                    ok: function(){
                    //收到该订单
                    $.ajax({
                    type : 'POST',
                    url  : '${_ctxPath }/order/order-receiveOrder.htm',
                    data :  oIds,
                    success:function(html){
                        if(html.code == "true"){
                            window.location.reload();
                        }else{
                        alert(html.info);
                        }
                    }
                    });
                    },
                    cancelValue: "取消",
                    cancel: true
                });
                $(".d-close").hide();
                return false;
            }else{
                $.dialog({
                title: false,
                padding: "15px 20px",
                content: "<span style='color:#C00;'>请选择确认收货的商品，可以选择多个</span>",
                follow: $this.get(0),
                okValue: "确定",
                time: 1500
                });
                $(".d-close").hide();
            }
		}); 
 	    //批量付款
 	    $("#batch-pay").on("click",function(event){
 	    	var $this = $(this);
            if($('.ord-checkbox:checked').length > 0){
                var chk_value =new Array();
                $('.ord-checkbox:checked').each(function(){
                	var statusValue = $(this).attr("status-value");
                	if(statusValue=='1'){//只添加 待付款 的订单
                    chk_value.push($(this).attr("data-value"));
                	}
                });
                if(chk_value.length==0){
                	return;
                }
                var oIds = $.param({'ordIds':chk_value},true);
                $.dialog({
                    title: false,
                    padding: "15px 20px 8px",
                    content: "<span style='color:#C00;'>确认选择订单合并付款吗？</span>",
                    follow: $this.get(0),
                    okValue: "确定",
                    ok: function(){
                    //合并支付订单
                    //window.location.href= _ctxPath +'/order/order-payMyOrder.htm?' + oIds;
                    window.open(_ctxPath +'/order/order-payMyOrder.htm?' + oIds,"_blank");
                    },
                    cancelValue: "取消",
                    cancel: true
                });
                $(".d-close").hide();
                return false;
            }else{
                $.dialog({
                    title: false,
                    padding: "15px 20px",
                    content: "<span style='color:#C00;'>请选择商品付款，可以选择多个</span>",
                    follow: $this.get(0),
                    okValue: "确定",
                    time: 1500
                });
                $(".d-close").hide();
             }
		}); 
    </script>
</body>
</html>