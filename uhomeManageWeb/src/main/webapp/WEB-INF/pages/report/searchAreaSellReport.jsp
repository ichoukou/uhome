<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>报表管理</title>
<link href="${ _cssPath}/common.css" rel="stylesheet" />
<link href="${_jsPath }/plugin/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" />
<link href="${ _cssPath}/pages/reportForms.css" rel="stylesheet" />
<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
<script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
<script src="${_jsPath }/plugin/plugin.js"  language="javascript"></script>
<script src="${_jsPath }/pages/index.js"></script>
<script type="text/javascript" >

	$(function(){
		$('#search').bind('click', function() {
			$("#searchForm").attr("action","${_ctxPath}/admin/report/productReport-searchAreaSellReport.htm");
			$("#searchForm").submit();
		});
	});
	$(function(){
		$('#export').bind('click', function() {
			$("#searchForm").attr("action","${_ctxPath}/admin/report/productReport-exportAreaSellReports.htm");
			$("#searchForm").submit();
		});
	}); 
 
  
</script>
</head>
<body>
	<jsp:include page="../include/header.jsp" flush="true" />
	<div class="body m-w980p m-mt10p">
		<jsp:include page="./reportMenuHeader.jsp" flush="true" />
		<div>
			<form action="${_ctxPath}/admin/report/productReport-searchAreaSellReport.htm" class="m-clear m-mt10p" id="searchForm"  name="searchForm" method="post" >
			  <!-- 此处用于报表菜单选择 -->
				<input type="hidden" name="reportMenuId" value="${reportMenuId}" />
				
				<div class="m-fl">
		 		<span>品牌:</span>	    
 				<select class="m-sel" name="areaSellReportPage.params.brandId" id="brandId">
					<option value="">全部品牌</option>
					<c:forEach items="${brands }" var="brand" varStatus="status">
						<option value="${brand.brandId }" <c:if test="${areaSellReportPage.params.brandId==brand.brandId }">selected = "selected"</c:if>>
						${brand.name}</option>
					</c:forEach>
				</select> 
						<input type="text" name="areaSellReportPage.params.beginTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate" readonly="readonly" value="${areaSellReportPage.params.beginTime}" id="beginTime"/>
					      至<input type="text" name="areaSellReportPage.params.endTime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly="readonly" value="${areaSellReportPage.params.endTime}" id="endTime"/>
						
						<aut:authorize url="/admin/report/productReport-searchAreaSellReport">
						<input type="button" class="m-btn" value="查询" id="search"/>
						</aut:authorize>
						<aut:authorize url="/admin/report/productReport-exportAreaSellReports">
						<input type="button" class="m-btn outer" value="导出" id="export"/>
						</aut:authorize>
				</div>
				
				<c:if test="${not empty areaSellReportPage.result}">
					<div class="m-fr curr-num">
						<label>当前显示： </label> 
		               		<wms:commPageSize page="${areaSellReportPage}" beanName="areaSellReportPage"  paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPageSize>
					</div>
	            </c:if>
			</form>
			<c:if test="${areaSellReportPage!=null}"> 
				<div class="m-mt10p">
					<table class="tab-control" id="tab">
						<thead>
							<tr>
								<th width="11%">品牌</th>
								<th width="22%">收货地址</th>
								<th width="7%">订单数量</th>
								<th width="7%">销售数量</th>
								 
							 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${areaSellReportPage.result }" var="areaSellReport" varStatus="status">
								<tr class="list-tr">
									<td>${areaSellReport.name }</td>
									<td>${areaSellReport.adress}</td>
									<td>${areaSellReport.orderNum}</td>
									<td>${areaSellReport.itemNum}</td>
									 
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				 <div class="pagination pagination-right">
					<c:if test="${not empty areaSellReportPage.result}">
						<wms:commPage page="${areaSellReportPage}" beanName="areaSellReportPage" paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPage>
					</c:if>
				</div>
			</c:if>
		</div>
	</div>
	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
</html>