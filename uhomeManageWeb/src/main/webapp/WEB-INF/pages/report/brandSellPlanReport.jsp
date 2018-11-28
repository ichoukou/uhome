<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>品牌销售明细</title>
    <link href="${ _cssPath}/common.css" rel="stylesheet" />
    <link href="${ _cssPath}/pages/reportForms.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <script src="${_jsPath }/plugin/plugin.js"  language="javascript"></script>
    <script src="${_jsPath }/pages/index.js"></script>
    <script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
    function ajaxValue(value,brandId){
        $.get("${_ctxPath}/admin/report/productReport-getBrandsBySeller.htm?sellerId="+value+"&brandId="+brandId+"&time="+new Date().getTime(),function(data){
     		$("#brandId").empty();
     		$("#brandId").prepend("<option value=''>全部品牌</option>"); 
     		 var result="";
 		   		$.each(data,function(i,brands){ 
 		   		   
 		   		   if( brandId ==brands.brandId ){
 						result = result +  "<option selected = 'selected' value='"+brands.brandId+"'>"+brands.name+"</option>";
 					}else{
 						result = result +  "<option value='"+brands.brandId+"'>"+brands.name+"</option>";
 					}
 		   		} )
         	$("#brandId").append(result);
         },"json" ); 
     }
     $(function(){        
   	  ajaxValue('${brandSellReportPage.params.sellerId}','${brandSellReportPage.params.brandId}');
 	});
	    $(function(){
			$('#search').bind('click', function() {
				$("#searchForm").attr("action","${_ctxPath}/admin/report/productReport-searchBrandSellReport.htm");
				$("#searchForm").submit();
				
			});
		});
	    $(function(){
			$('#export').bind('click', function() {
				$("#searchForm").attr("action","${_ctxPath}/admin/report/productReport-exportBrandSellReport.htm");
				$("#searchForm").submit();
			});
		});
    </script>
</head>
<body>
	<!--start body-->
	<jsp:include page="../include/header.jsp" flush="true" />
	<div class="body m-w980p m-mt10p">
		
		<!-- reportMenu -->
		<jsp:include page="./reportMenuHeader.jsp" flush="true" />
		<form class="m-clear m-mt10p"
			action=""
			id="searchForm" method="post">
			<!-- 此处用于报表菜单选择 -->
			<input type="hidden" name="reportMenuId" value="${reportMenuId}" />
			<div class="m-fl">
				<select class="m-sel" name="brandSellReportPage.params.sellerId" id="sellerId" onchange="ajaxValue(this.value,'');">
					<option value="">全部商家</option>
					<c:forEach items="${sellers }" var="seller" varStatus="status">
						<option value="${seller.sellerId }" <c:if test="${brandSellReportPage.params.sellerId==seller.sellerId }">selected = "selected"</c:if>>
						${seller.companyName}</option>
					</c:forEach>
				</select> 
				<select class="m-sel" name="brandSellReportPage.params.brandId" id="brandId">
					<option value="">全部品牌</option>
					<c:forEach items="${brands }" var="brand" varStatus="status">
						<option value="${brand.brandId }" <c:if test="${brandSellReportPage.params.brandId==brand.brandId }">selected = "selected"</c:if>>
						${brand.name}</option>
					</c:forEach>
				</select> 
				<input type="text" name="brandSellReportPage.params.time" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate" readonly="readonly" value="${brandSellReportPage.params.time}" id="time"/> 
				<input type="button" class="m-btn" value="查询" id="search" /> 
				<input type="button" class="m-btn outer" value="导出" id="export" />
			</div>
			<!--右侧显示分页控件 start-->
			<c:if test="${not empty brandSellReportPage.result}">
				<div class="m-fr curr-num">
					<label>当前显示： </label>
					<wms:commPageSize page="${brandSellReportPage}" beanName="brandSellReportPage" 
						paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPageSize>
				</div>
			</c:if>
			<!--右侧显示分页控件 end-->
		</form>
		<!--100%表格样式-->
		<c:if test="${not empty brandSellReportPage}">
			<div class="m-mt10p">
				<table class="tab-control">
					<!--100%表格样式-->
					<!--需要宽表格样式-->
					<!--<div class="m-mt10p tab-over">
						<table>-->
					<!--需要宽表格样式-->
					<thead>
						<tr>
							<th width="17%">商家</th>
							<th width="11%">品牌</th>
							<th width="8%">分类</th>
							<th width="8%">上线SKU数</th>
							<th width="10%">有销售SKU数</th>
							<th width="7%">销售数量</th>
							<th width="11%">销售金额</th>
							<th width="15%">上线日期</th>
							<th width="15%">下线日期</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${brandSellReportPage.result }"
							var="brandSellReport" varStatus="status">
							<tr class="list-tr">
								<td>${brandSellReport.sellerName }</td>
								<td>${brandSellReport.brandName}</td>
								<td>${brandSellReport.categoryName}</td>
								<td>${brandSellReport.onlineSkuNum}</td>
								<td>${brandSellReport.skuSellNum}</td>
								<td>${brandSellReport.sellNum}</td>
								<td><span class="coin">&yen;${brandSellReport.sellAmount}</span>
								</td>
								<td><fmt:formatDate value='${brandSellReport.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
								<td><fmt:formatDate value='${brandSellReport.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!--分页插件 start -->
			<div class="pagination pagination-right">
				<c:if test="${not empty brandSellReportPage.result}">
					<wms:commPage page="${brandSellReportPage}" beanName="brandSellReportPage" 
						paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPage>
				</c:if>
			</div>
			<!--分页插件 end -->
		</c:if>
	</div>
	<!--end body-->
	<!--start footer-->
	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
	<!--end footer-->
</body>
</html>