<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<c:forEach items="${brands }" var="brand" varStatus="status">
	<input type="checkbox" id="brand-${brand.brandId }" name="seller.listBrandIds" 
		<c:if test="${brand.isChecked }"> checked</c:if>
		value="${brand.brandId }" />
	<label for="brand-${brand.brandId }">${not empty brand.name ? brand.name : brand.englishName}</label>
</c:forEach>
<script type="text/javascript">
$("input:checkbox").formValidator({tipID:"brandTip",onShow:"请选择可售品牌",onFocus:"你至少选择1个可售品牌",onCorrect:"恭喜你,你选对了"}).inputValidator({min:1,onError:"你至少选择1个可售品牌"});
</script>