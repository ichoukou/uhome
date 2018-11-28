<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Plan" %>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Product" %>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!---star 查看活动中的商品--->
<c:set value="<%=Plan.TYPE_SPECIAL_SELLER %>" var="TYPE_SPECIAL_SELLER"></c:set>
<c:set value="<%=Plan.TYPE_SEC_KILL %>" var="TYPE_SEC_KILL"></c:set>
<c:set var="checkPend" value="<%=Product.STATUS_CHECK_PEND %>"></c:set>
<div class="show-sale">
<form id="viewForm">
	<b>
	<c:choose>
		<c:when test="${plan.type == TYPE_SPECIAL_SELLER}">商品权重：(1-10)</c:when>
		<c:when test="${plan.type == TYPE_SEC_KILL}">排期权重：(1-10)</c:when>
	</c:choose>
	</b>
	<div class="look-sale-product">
		<ul class="m-clear">
		<c:choose>
			<c:when test="${plan.type == TYPE_SPECIAL_SELLER}">
			<c:forEach items="${plan.planProducts }" var="planProduct" varStatus="status">
				<li><img <c:if test="${planProduct.product.status == checkPend }">style="filter:gray;-moz-opacity:.1;opacity:0.1;"</c:if> 
					src="<zx:thumbImage originalPath='${planProduct.product.defaultImage}' imageType='t17'></zx:thumbImage>"/>
					<input type="hidden" name="plan.planProducts[${status.index }].planProductId" value="${planProduct.planProductId }"/> 
					<input class="product-seqencing-num" name="plan.planProducts[${status.index }].rank" value="${planProduct.rank }" type="text" onchange="checkRank(this)"
					<c:if test="${plan.expireFlag=='1' }">disabled="disabled"</c:if> >
					<div class="sale-product-info left-icon m-hide">
						<i></i>
						<h1><a href="#">${planProduct.product.name }</a></h1>
						<p>库存：${planProduct.product.saleInventoryCount }件&nbsp;销售次数：${planProduct.product.salesNum }次</p>
					</div>
				</li>
			</c:forEach>
			</c:when>
			<c:when test="${plan.type == TYPE_SEC_KILL}">
			<c:forEach items="${plan.planProducts }" var="planProduct" varStatus="status">
				<li><img <c:if test="${planProduct.product.status == checkPend }">style="filter:gray;-moz-opacity:.1;opacity:0.1;"</c:if> 
					src="<zx:thumbImage originalPath='${planProduct.product.defaultImage}' imageType='t17'></zx:thumbImage>"/> 
					<input type="hidden" name="plan.planProducts[${status.index }].planProductId" value="${planProduct.planProductId }"/> 
					<input class="product-seqencing-num" name="plan.planProducts[${status.index }].rank" value="${planProduct.rank }" type="text"
					<c:if test="${plan.expireFlag=='1' }">disabled="disabled"</c:if> >
					<div class="sale-product-info left-icon m-hide">
						<i></i>
						<h1>
							<a href="#">${planProduct.product.name }</a>
						</h1>
						<c:forEach items="${planProduct.product.productSkus[0].productSkuOptionValues }" var="proSkuOptVal">
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
			</c:when>
		</c:choose>
		</ul>
	</div>
</form>
</div>
<!---end 查看活动中的商品--->
<script type="text/javascript">
	$(".d-state-highlight").hide();
	<aut:authorize url="/admin/plan/plan-editPlanProductRank">
	if("${plan.expireFlag}" == "0" && ${plan.status} != <%=Plan.STATUS_OFF_SHELF%>){
		$(".d-state-highlight").show();
	}
	</aut:authorize>
</script>