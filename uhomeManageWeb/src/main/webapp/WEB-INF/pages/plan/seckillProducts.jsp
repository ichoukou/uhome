<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Product" %>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!--star 选择商品 -->
<c:set var="checkPend" value="<%=Product.STATUS_CHECK_PEND %>"></c:set>
<c:forEach items="${sellers }" var="seller">
<div class="addSale-saler-list">
	<div class="addSale-saler">
		<p>${seller.contactName }</p>
	</div>
	<div class="addSale-product">
		<ul>
		<c:choose>
		<c:when test="${empty seller.productSkus }">
			<li>没有可参加秒杀的商品</li>
		</c:when>
		<c:otherwise>
			<c:forEach items="${seller.productSkus }" var="productSku">
			<li>
				<img <c:if test="${productSku.product.status == checkPend }">style="filter:gray;-moz-opacity:.1;opacity:0.1;"</c:if>
				src="<zx:thumbImage originalPath='${productSku.product.defaultImage}' imageType='t17'></zx:thumbImage>" />
				<p>
					<label>${productSku.seckillRebate < 0.1 ? 0.1 : productSku.seckillRebate }折</label>
					<input name="plan.planProducts[0].productSkuId" type="radio" value="${productSku.productSkuId }" productId="${productSku.productId }"/>
				</p>
				<div class="sale-product-info left-icon hide">
			    	<i></i>
			    	<h1><a href="#">${productSku.product.name }</a></h1>
			    	<c:forEach items="${productSku.productSkuOptionValues }" var="proSkuOptVal">
				    	<c:choose>
				    		<c:when test="${not empty proSkuOptVal.overrideSkuOptionValue }">
				    			${proSkuOptVal.skuOptionValue.skuOption.skuOptionName}:${proSkuOptVal.overrideSkuOptionValue }
				    		</c:when>
				    		<c:otherwise>
				    			${proSkuOptVal.skuOptionValue.skuOption.skuOptionName}:${proSkuOptVal.skuOptionValue.skuOptionValue }
				    		</c:otherwise>
				    	</c:choose>
			    	</c:forEach>
			    </div>
			</li>
			</c:forEach>
		</c:otherwise>
		</c:choose>
			<div class="clear"></div>
		</ul>
	</div>
	<div class="clear"></div>
</div>
</c:forEach>
<!--end 选择商品 -->