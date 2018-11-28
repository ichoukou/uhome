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
<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
<script type="text/javascript" >
$(function(){
	$('#search').bind('click', function() {
		var result = $.formValidator.pageIsValid('1');
		if(!result){
			return false;
		}
		$("#searchForm").attr("action","${_ctxPath}/admin/report/productReport-searchOrderDetailReport.htm");
		$("#searchForm").submit();
	});
});
$(function(){
	$('#export').bind('click', function() {
		var result = $.formValidator.pageIsValid('1');
		if(!result){
			return false;
		}
		$("#searchForm").attr("action","${_ctxPath}/admin/report/productReport-exportOrderDetailReports.htm");
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
			<form action="${_ctxPath}/admin/report/productReport-searchOrderDetailReport.htm" class="m-clear m-mt10p" id="searchForm"  name="searchForm" method="post">
				<!-- 此处用于报表菜单选择 -->
				<input type="hidden" name="reportMenuId" value="${reportMenuId}" />
				<div class="m-fl">
					<span class="position">
 						<span class="c-red">*</span>
 						<input type="text" name="orderDetailReportPage.params.beginTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate" readonly="readonly" value="${orderDetailReportPage.params.beginTime}" id="beginTime"/>
						至 <span class="c-red">*</span>
						<input type="text" name="orderDetailReportPage.params.endTime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly="readonly" value="${orderDetailReportPage.params.endTime}" id="endTime"/>
						<aut:authorize url="/admin/productReport/report-searchOrderDetailReport">
						<input type="submit" class="m-btn" value="查询" id="search"/>
						</aut:authorize>
						<aut:authorize url="/admin/productReport/report-exportOrderDetailReports">
						<input type="button" class="m-btn outer" value="导出" id="export"/>
						</aut:authorize>
						<div id="dateTip"></div>
					</span>
				</div>
				<c:if test="${not empty orderDetailReportPage.result}">
					<div class="m-fr curr-num">
						<label>当前显示： </label> 
		               		<wms:commPageSize page="${orderDetailReportPage}" beanName="orderDetailReportPage"  paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPageSize>
					</div>
	            </c:if>
			</form>
			<c:if test="${orderDetailReportPage!=null}"> 
				<div class="m-mt10p">
					<table class="tab-control" id="tab">
						<thead>
							<tr>
								<th width="11%">订单号</th>
								<th width="22%">商品品名</th>
								<th width="9%">商品数量</th>
								<th width="11%">订单金额</th>
							    <th width="8%">订单状态</th>
								<th width="16%">订单日期</th>
 							 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${orderDetailReportPage.result }" var="orderDetailReport" varStatus="status">
								<tr class="list-tr">
									<td>${orderDetailReport.orderNo }</td>
									<td>${orderDetailReport.productName }</td>
									<td>${orderDetailReport.num }</td>
									<td>${orderDetailReport.closingCost }</td>
									<td><spring:message	code="order.status.${orderDetailReport.status}" /></td>
									<td><fmt:formatDate value='${orderDetailReport.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				 <div class="pagination pagination-right">
					<c:if test="${not empty orderDetailReportPage.result}">
						<wms:commPage page="${orderDetailReportPage}" beanName="orderDetailReportPage" paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPage>
					</c:if>
				</div>
			</c:if>
		</div>
	</div>
	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
		<script type="text/javascript">
	$.formValidator.initConfig({formID:"searchForm",theme:"Default",errorFocus:false,wideWord:false});
	$("#beginTime").formValidator({tipID:"dateTip",onShow:"请选择开始时间",onFocus:"请选择开始时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"开始时间必填"});
	$("#endTime").formValidator({tipID:"dateTip",onShow:"请选择结束时间",onFocus:"请选择结束时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"结束时间必填"});
	</script>
</body>
</html>