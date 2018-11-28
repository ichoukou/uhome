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
<script type="text/javascript" >
var nowDate='${nowDateTime}';
	var submitForm=function(){
		var timeVal=$('#time').val();
		if(timeVal=='' || timeVal>nowDate){
			return false;
		}else{
			return true;
		}
	};
	$(function(){
			$('#export').bind('click', function() {
 				var url = '${_ctxPath}/admin/report/productReport-exportSellerReports.htm';
 				$("#searchForm").attr("action",url);
 				$("#searchForm").submit();
 				 
 			});
			
			$('#search').bind('click', function() {
 				var url = '${_ctxPath}/admin/report/productReport-searchSellerReport.htm';
 				$("#searchForm").attr("action",url);
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
			<form action="${_ctxPath}/admin/report/productReport-searchSellerReport.htm" class="m-clear m-mt10p" id="searchForm"  name="searchForm" method="post" onsubmit="return submitForm();">
			 <!-- 此处用于报表菜单选择 -->
			 <input type="hidden" name="reportMenuId" value="${reportMenuId}" />
				<div class="m-fl">
		 			    <span>商家:</span><input type="text" class="txt-input"  name="sellerReportPage.params.username"  value="${sellerReportPage.params.username}" id="username"/>
						<aut:authorize url="/admin/report/productReport-searchSellerReport">
						<input type="button" class="m-btn" value="查询" id="search"/>
						</aut:authorize>
						<aut:authorize url="/admin/report/productReport-exportSellerReports">
						<input type="button" class="m-btn outer" value="导出" id="export"/>
						</aut:authorize>
				</div>
				<c:if test="${not empty sellerReportPage.result}">
					<div class="m-fr curr-num">
						<label>当前显示： </label> 
		               		<wms:commPageSize page="${sellerReportPage}" beanName="sellerReportPage"  paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPageSize>
					</div>
	            </c:if>
			</form>
			<c:if test="${sellerReportPage!=null}"> 
				<div class="m-mt10p">
					<table class="tab-control" id="tab">
						<thead>
							<tr>
								<th width="11%">商家</th>
								<th width="11%">业务联系人</th>
								<th width="17%">手机号码</th>
								<th width="11%">固定电话</th>
								<th width="17%">邮箱</th>
								<th width="8%">支付宝账号</th>
							 
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${sellerReportPage.result }" var="sellerReport" varStatus="status">
								<tr class="list-tr">
									<td>${sellerReport.username }</td>
									<td>${sellerReport.contactName}</td>
									<td>${sellerReport.mobile}</td>
									<td>${sellerReport.tel}</td>
									<td>${sellerReport.email}</td>
									<td>${sellerReport.alipayNo} </td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				 <div class="pagination pagination-right">
					<c:if test="${not empty sellerReportPage.result}">
						<wms:commPage page="${sellerReportPage}" beanName="sellerReportPage" paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPage>
					</c:if>
				</div>
			</c:if>
		</div>
	</div>
	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
</html>