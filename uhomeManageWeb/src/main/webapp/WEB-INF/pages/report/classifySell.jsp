<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${ _cssPath}/common.css" rel="stylesheet" />
    <link href="${ _cssPath}/pages/reportForms.css" rel="stylesheet" />
     <link href="${ _cssPath}/pages/arrangement.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
 
    <script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
 
	<title>分类销售管理明细</title>
	<script type="text/javascript">
		function submitForm(value){
		 
			if(value == 1){
				document.classifyForm.action = "${_ctxPath }/admin/report/productReport-searchClassifySellInfo.htm";
			}else{
				document.classifyForm.action = "${_ctxPath }/admin/report/productReport-exportClassifySellReports.htm";
			}
			document.classifyForm.submit();
		}
	</script>
</head>
<body>
	<jsp:include page="../include/header.jsp" flush="true" />
	<div class="body m-w980p m-mt10p">
		<!-- reportMenu -->
		<jsp:include page="./reportMenuHeader.jsp" flush="true" />
	  <form class="m-clear m-mt10p" name="classifyForm" id="classifyForm" method="post" >
	  	<input type="hidden" name="reportMenuId" value="${reportMenuId}" />
	    <div class="m-fl">
		    <select class="m-sel" name="classifyReportPage.params.brandId" id="brandId">
					<option value="">全部品牌</option>
					<c:forEach items="${brands }" var="brand" varStatus="status">
						<option value="${brand.brandId }" <c:if test="${classifyReportPage.params.brandId==brand.brandId }">selected = "selected"</c:if>>
							${brand.name}
						</option>
					</c:forEach>
			</select> 
			<select class="m-sel" name="classifyReportPage.params.productCategoryId" id="productCategoryId">
					<option value="">请选择品类</option>
					<c:forEach items="${childProductCategorys }" var="productCategory" varStatus="status">
						<option value="${productCategory.productCategoryId }" <c:if test="${classifyReportPage.params.productCategoryId==productCategory.productCategoryId }">selected = "selected"</c:if>>
						${productCategory.name}</option>
					</c:forEach>
			</select>
		<span class="position">
	      <input type="text" id="begin" readonly="readonly" name="classifyReportPage.params.beginTime" value="${classifyReportPage.params.beginTime}" class="Wdate" onfocus="WdatePicker(({maxDate:'#F{$dp.$D(\'end\')}'}))" /> 至
	      <input type="text" id="end" readonly="readonly" name="classifyReportPage.params.endTime"  value="${classifyReportPage.params.endTime}" class="Wdate" onfocus="WdatePicker(({minDate:'#F{$dp.$D(\'begin\')}'}))" />
	    </span>
	      <input type="button" class="m-btn" value="查询" onclick="submitForm(1)"/>
	      <input type="button" class="m-btn outer" value="导出" onclick="submitForm(2)"/>
	    </div>
	   
	   
	    <!--右侧显示分页控件 start-->
	    <c:if test="${not empty classifyReportPage.result}">
			<div class="m-fr curr-num">
				<label>当前显示： </label> 
		          <wms:commPageSize page="${classifyReportPage}" beanName="classifyReportPage"
		          paramName="reportMenuId" paramValue="${reportMenuId}" ></wms:commPageSize>
				</div>
	     </c:if>
		</form>
		<div class="m-mt10p">
		<c:if test="${classifyReportPage != null }">
			<table class="tab-control">
		      <thead>
		        <tr>
		          <th width="11%">商品品类</th>
		          <th width="11%">子类</th>
		          <th width="11%">品牌</th>
		          <th width="17%">销售数量</th>
		          <th width="11%">销售金额</th>
		        </tr>
		      </thead>
		      	  <tbody>
		      		<c:forEach items="${classifyReportPage.result }" var="classify">
		      		<tr class="list-tr">
						<td>${classify.classifyName }</td>
						<td></td>
						<td>${classify.brandName }</td>
						<td>${classify.num }</td>
						<td>${classify.price }</td>
					</tr>
					</c:forEach>
		      	</tbody>
		      
		    </table>
	    </c:if>
		</div>
		<div class="pagination pagination-right">
			<c:if test="${not empty classifyReportPage.result}">
				<wms:commPage page="${classifyReportPage}" beanName="classifyReportPage"
				paramName="reportMenuId" paramValue="${reportMenuId}" ></wms:commPage>
			</c:if>
		</div>
	</div>
	 
</body>
</html>