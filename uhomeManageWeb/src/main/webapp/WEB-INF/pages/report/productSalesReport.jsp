<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>商品销售报表</title>
    <link href="${ _cssPath}/common.css" rel="stylesheet" />
    <link href="${ _cssPath}/pages/reportForms.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <script src="${_jsPath }/plugin/plugin.js"  language="javascript"></script>
    <script src="${_jsPath }/pages/index.js"></script>
    <script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
    <script type="text/javascript">
    	
	    $(function(){
			$('#search').bind('click', function() {
				var result = $.formValidator.pageIsValid('1');
				if(!result){
					return false;
				}
				$("#searchForm").attr("action","${_ctxPath}/admin/report/operationsReport-searchProductSalesReport.htm");
				$("#searchForm").submit();
			});
		});
	    $(function(){
			$('#export').bind('click', function() {
				var result = $.formValidator.pageIsValid('1');
				if(!result){
					return false;
				}
				$("#searchForm").attr("action","${_ctxPath}/admin/report/operationsReport-exportProductSalesReport.htm");
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
		<jsp:include page="./operationsReportMenu.jsp" flush="true" />
		<form class="m-clear m-mt10p" id="searchForm" method="post">
			<!-- 此处用于报表菜单选择 -->
			<input type="hidden" name="reportMenuId" value="${reportMenuId}" />
		
			<div class="m-fl">
				<span class="position">
					<span class="c-red">*</span>
					<input type="text" name="searchParams.stime" class="Wdate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'etime\')}'})" readonly="readonly"	value="${searchParams.stime}" id="stime" /> 至
					<span class="c-red">*</span>
					<input type="text" name="searchParams.etime" class="Wdate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'stime\')}'})" value="${searchParams.etime}"	id="etime" /> 
					<input type="button" class="m-btn" value="查询" id="search" /> 
					<input type="button" class="m-btn outer" value="导出" id="export" />
					<div id="dateTip"></div>
				</span>
			</div>
		</form>
		<!--100%表格样式-->
		<c:if test="${not empty productSalesReport}">
			<div class="m-mt10p">
				<table class="tab-control">
					<!--100%表格样式-->
					<!--需要宽表格样式-->
					<!--<div class="m-mt10p tab-over">
						<table>-->
					<!--需要宽表格样式-->
					<thead>
						<tr>
							<th width="17%"> 日期</th>
							<th width="11%">销售金额</th>
							<th width="11%">订单数</th>
							<th width="11%">客单价</th>
						</tr>
					</thead>
					<tbody>
						<tr class="list-tr">
							<td>${productSalesReport.date }</td>
							<td><span class="coin">&yen;${productSalesReport.orderAmount}</span></td>
							<td>${productSalesReport.orderNum}</td>
							<td><span class="coin">&yen;${productSalesReport.unitPrice}</span></td>
						</tr>
					</tbody>
				</table>
			</div>
		</c:if>
	</div>
	<!--end body-->
	<!--start footer-->
	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
	<!--end footer-->
	<script type="text/javascript">
	$.formValidator.initConfig({formID:"searchForm",theme:"Default",errorFocus:false,wideWord:false});
	$("#stime").formValidator({tipID:"dateTip",onShow:"请选择开始时间",onFocus:"请选择开始时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"开始时间必填"});
	$("#etime").formValidator({tipID:"dateTip",onShow:"请选择结束时间",onFocus:"请选择结束时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"结束时间必填"});
	</script>
</body>
</html>