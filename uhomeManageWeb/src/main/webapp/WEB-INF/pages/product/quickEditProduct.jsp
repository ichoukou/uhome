<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<link href="${_cssPath }/pages/addProduct.css" rel="stylesheet" />
<form id="productSkuForm">
	<input type="hidden" name="product.productId" value="${product.productId }"/>
	<div style="margin-bottom: 5px;">
		<em class="c-red">*</em>
		<span>商品名称：</span>
		<input type="text" class="pro-name" name="product.name" value="${product.name }" id="name"/>
		<div id="nameTip"></div>
	</div>
	<table class="repertories" width="630" class="small">
		<tr>
			<th width="12%">颜色</th>
			<th width="12%">规格</th>
			<th width="12%">特卖数量</th>
			<th width="12%">秒杀数量</th>
			<th width="26%">国际码</th>
			<th width="26%">SKU编码</th>
		</tr>
		<c:set value="0" var="num"></c:set>
		<c:choose>
		<c:when test="${empty product.productSkuColorNum or empty product.productSkuSizeNum }">
			<c:set value="1" var="rowspan"></c:set>
		</c:when>
		<c:otherwise>
			<c:set value="${product.productSkuSizeNum }" var="rowspan"></c:set>
		</c:otherwise>
		</c:choose>
		<c:forEach items="${product.productSkus }" var="productSku" varStatus="status">
		<tr>
			<c:if test="${num == 0 }">
			<td rowspan="${rowspan}">${productSku.productSkuColor }</td>
			</c:if>
			<td>${productSku.productSkuSize }
				<input type="hidden" name="product.productSkus[${status.index }].productSkuId" value="${productSku.productSkuId }"/>
			</td>
			<td><input type="text" class="repertories-num inventory" maxlength="5" name="product.productSkus[${status.index }].inventory" value="${productSku.inventory }" /></td>
			<td><input type="text" class="repertories-num secKillInventory" maxlength="5" name="product.productSkus[${status.index }].secKillInventory" value="${productSku.secKillInventory }" /></td>
			<td><input type="text" class="repertories-num repertories-width" maxlength="64" name="product.productSkus[${status.index }].internationalCode" value="${productSku.internationalCode }" /></td>
			<td><input type="text" class="repertories-num repertories-width" maxlength="64" name="product.productSkus[${status.index }].skuCode" value="${productSku.skuCode }" /></td>
		</tr>
		<c:set value="${num + 1 }" var="num"></c:set>
		<c:if test="${num == rowspan }">
			<c:set value="0" var="num"></c:set>
		</c:if>
		</c:forEach>
	</table>
	<div id="inventoryTip"></div>
</form>
<script type="text/javascript">
	$.formValidator.initConfig({
		validatorGroup : '1',
		formID : "productSkuForm",
		theme : "Default",
		errorFocus : true,
		wideWord : false
	});
	$("#name").formValidator({
		tipID : "nameTip",
		onFocus : "请填写1-20个字符组成的商品名称"
	}).inputValidator({
		min : 1,
		max : 20,
		onErrorMin : "请填写1-20个字符组成的商品名称",
		onErrorMax : "请输入小于20个字符",
		onError : "你输入的商品名称错误"
	});
	$("input.inventory,input.secKillInventory").formValidator({
		tipID : "inventoryTip",
		empty : true,
		onFocus : "请填写1-5 位的数字"
	}).inputValidator({
		min : 1,
		max : 5,
		onErrorMin : "请填写1-5 位的数字",
		onError : "请填写1-5 位的数字"
	}).regexValidator({
		regExp : "^\\d{0,5}$",
		dataType : "string",
		onError : "请填写1-5 位的数字"
	});
</script>