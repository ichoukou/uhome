<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Product" %>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!--star 选择商品 -->
<c:set var="checkPend" value="<%=Product.STATUS_CHECK_PEND %>"></c:set>
<c:forEach items="${sellers }" var="seller">
<div class="addSale-saler-list">
	<div class="addSale-saler">
		<p>${seller.contactName }</p>
		<input type="checkbox" id="select-all" /><label for="select-all">全选</label>
	</div>
	<div class="addSale-product">
		<ul class="m-clear">
		<c:choose>
		<c:when test="${empty seller.products}">
			<li>没有可参加特卖的商品</li>
		</c:when>
		<c:otherwise>
			<c:forEach items="${seller.products }" var="product">
			<li>
				<img <c:if test="${product.status == checkPend }">style="filter:gray;-moz-opacity:.1;opacity:0.1;"</c:if> 
				src="<zx:thumbImage originalPath='${product.defaultImage}' imageType='t17'></zx:thumbImage>"/>
				<p>
					<label>${product.rebate }折</label>
					<input type="checkbox"  class="select-box" value="${product.productId }" id="${product.productId }"/>
				</p>
				<div class="sale-product-info left-icon m-hide">
			    	<i></i>
			    	<h1><a href="#">${product.name }</a></h1>
			    	<p>库存：${product.saleInventoryCount }件&nbsp;销售次数：${product.salesNum }次</p>
			    </div>
			</li>
			</c:forEach>
		</c:otherwise>
		</c:choose>
		</ul>
	</div>
</div>
</c:forEach>
<!--end 选择商品 -->