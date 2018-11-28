<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
    <meta charset="utf-8"/>
    <title>购物车-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath}/pages/shopping-cart.css?d=${_resVerion}" media="all">
</head>
<body>
	<jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include> 
    <div class="m-w960p">
        <%--<div class="m-rel">--%>
            <%--<div class="cs-tel">客服热线  <span>021-64703131</span></div>--%>
        <%--</div>--%>
        <form id="cartform" method="post" action="${_ctxPath}/order/order-checkOrder.htm">
        <div class="cart-wrap">
            <div class="cf">
                <div class="step-status"><span class="step">我的购物车</span><span>确认订单信息</span><span>成功提交订单</span></div>
            </div>
            <c:if test="${not empty sellers}">
            <div id="has-goods">
            	<div class="cart-item-count">
	           	 购物袋中的商品，为您保留   
	           	 <span class="countdown" id="J_cartCount" data-config="{
	            'endTime':'<fmt:formatDate value="${cartExpireTime}" type="both"/>',
	            'nowTime':'<%=System.currentTimeMillis()/1000 %>',
	            'stopTips':'<span>商品已超时！</span>'
	            }"></span> ，请及时结算。
            	</div>
                <div class="order-show">
                    <div id="show">
                        <div class="order-list-hd m-mt8p">
                            <span class="c1"><label for="selectAllList"><input type="checkbox" id="selectAllList">全选</label></span>
                            <span class="c2">商品名称</span>
                            <span class="c3">积分</span>
                            <span class="c4">单价（元）</span>
                            <span class="c5" style="width:110px;padding-left:40px;">数量</span>
                            <span class="c6">金额</span>
                            <span class="c7">操作</span>
                        </div>
                        <input type="hidden" name="type" value='<%=com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku.TYPE_SPECIAL_SELLER%>' />
                        <c:set value="0" var="total" />
                        <c:set value="0" var="totalPostage"/>
                        <c:forEach items="${sellers}" var="seller" varStatus="status">
                        <div class="order-brand m-mt20p" id="${status.index}">
                        	<c:set value="${totalPostage + seller.postage}" var="totalPostage" />
                        	<div class="order-brand-top">
                        	<input type="checkbox" class="order-ckb">特卖商家： ${seller.companyName}
                        	<span class="fare" id="seller${seller.productSkus[0].product.seller.sellerId}">
                        		<c:choose>
		                        	<c:when test="${seller.postage > 0}"><var>${seller.postage }</var>元</c:when>
		                        	<c:otherwise>免运费</c:otherwise>
		                        </c:choose>
		                     </span>
                        	</div>
                            <div class="order-brand-main">
                                <table width="100%">
                                    <tbody>
                                    <c:set value="0" var="sum" />
                                    <c:forEach items="${seller.productSkus}" var="sku" varStatus="product">
	                                    <c:if test="${sku.isActivity and sku.inventory >= sku.num}">
	                                    	<c:set value="${sum + sku.product.salePrice * sku.num}" var="sum" />
	                                    </c:if>
                                    <tr id="${sku.productSkuId}" data-item="${status.index}">
                                        <td width="50">
                                        	<input type="checkbox" class="ckb" name="skuIds" value="${sku.productSkuId}">
                                        	<input type="hidden" name="sku-postage" value="${sku.postage}">
                                        </td>
                                        <td width="58"><img src="<zx:thumbImage originalPath='${sku.product.defaultImage}' imageType='t12'></zx:thumbImage>" alt=""></td>
                                        <td width="307"><a target="_blank" href="${_ctxPath}/item-${sku.product.productId}.htm" class="order-link">${sku.product.name}</a>
                                            <p class="order-detail">
                                                <c:forEach items="${sku.productSkuOptionValues}" var="option" >
                                                        ${option.skuOptionValue.skuOption.skuOptionName}:
                                                        <c:choose>
                                                            <c:when test="${not empty option.overrideSkuOptionValue}">
                                                                ${option.overrideSkuOptionValue}&nbsp;&nbsp;
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${option.skuOptionValue.skuOptionValue}&nbsp;&nbsp;
                                                            </c:otherwise>
                                                        </c:choose>
                                                </c:forEach>
                                            </p>
                                            <c:forEach items="${sku.events}" var="event">
                                            <p class="order-detail">优惠券：${event.eventName}</p>
                                            </c:forEach>
                                        </td>
                                        <td width="95" class="order-point">${uhome:computerPoint(sku.product.salePrice * sku.num)}</td>
                                        <td width="105">
                                            <span class="unit-price">${sku.product.salePrice}</span>
                                            <p class="origina">${sku.product.marketPrice}</p>
                                        </td>
                                        <!-- 根据库存判断显示内容 -->
                                        <c:choose>
                                            <c:when test="${not sku.isActivity}">
                                                	<td width="152">
                                                		<a href="javascript:;" class="plus"></a><input type="text" value="${sku.num}" class="order-value"><a href="javascript:;" class="substru"></a>
                                                		<span class="warnning">对不起当前商品不在销售期!</span>
                                                	</td>
                                            </c:when>
                                            <c:when test="${sku.inventory == 0}">
                                                	<td width="152">
                                                		<a href="javascript:;" class="plus"></a><input type="text" value="${sku.num}" class="order-value"><a href="javascript:;" class="substru"></a>
                                                		<span class="warnning">对不起当前商品已经售罄!</span>
                                                	</td>
                                            </c:when>
                                            <c:when test="${sku.inventory > 0 and sku.inventory < sku.num}">
                                                	<td width="152">
                                                		<a href="javascript:;" class="plus"></a><input type="text" value="${sku.num}" class="order-value"><a href="javascript:;" class="substru"></a>
                                                		<span class="warnning">对不起当前商品库存不足,请调整数量!</span>
                                                	</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td width="152"><a href="javascript:;" class="plus"></a><input type="text" value="${sku.num}" class="order-value"><a href="javascript:;" class="substru"></a></td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td width="120"><span class="order-price">${sku.product.salePrice * sku.num}</span></td>
                                        <td width="83"><a data-sku="${sku.productSkuId}" href="javascript:;" class="delete">删除</a></td>
                                        <input type="hidden" class='hide-value' value="${sku.inventory}" />
                                        <input type="hidden" class='hide-value2' value="${not sku.isActivity}" />
                                    </tr>
                                    </c:forEach>
                                    <c:set value="${total + sum}" var="total" />
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        </c:forEach>
                        <div class="cart-bar m-mt10p cf"><a class="btn-f" href="javascript:;" id="J_bulkDelete" style="display:none;"><span>批量删除</span></a><span class="tips">所有商品均由供应商发货，如有个别商品延迟发货，请致电${_tel}！</span></div>
                    </div>
                </div>
                <div class="cf m-mt20p">
                    <div class="sum-price m-fr"><p class="pro-sum"><span>商品总额：</span><var>¥${total}</var></p><p class="transport-sum"><span>运费：</span><var>¥${totalPostage }</var></p></div>
                </div>
                <div class="cf m-mt20p">
                    <div class="m-fl"><a class="go-back" href="${_ctxPath}"><i></i>继续购物</a></div>
                    <div class="m-fr settlement"><a class="btn-c" href="javascript:;" id="J_checkOut"><span>结算</span></a></div>
                </div>
            </div>
            </c:if>
            <div id="no-goods" <c:if test="${empty sellers}">style="display:block;"</c:if>>
                <div class="inner">购物车内暂时没有商品，您可以<a href="${_domain}">去首页</a>挑选喜欢的商品</div>
            </div>
        </div>
        </form>
    </div>
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include> 
    <!--footer.jsp放置在这-->
    <script src="${_jsPath}/plugin/bigdecimal/mathcontext.js"></script>
    <script src="${_jsPath}/plugin/bigdecimal/bigdecimal.js"></script>
    <script src="${_jsPath}/pages/shopping.js?d=${_resVerion}"></script>
    <script>
        var unlogin = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.UNLOGIN_ERROR %>';
        var timeout = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.LOGIN_TIMEOUT_ERROR %>';
        var forbid = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.MANAGE_SELLER_FORBID %>';
    </script>
</body>
</html>