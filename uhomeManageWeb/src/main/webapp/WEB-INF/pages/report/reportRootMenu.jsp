<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="aut" uri="http://www.springsecurity.org/jsp"%>
<!-- 报表一级菜单 -->
<div class="body-nav">
	<ul class="m-clear">
		<!-- 设置默认选中第一个 -->
		<c:if test="${empty reportMenuId}">
			<c:set var="reportMenuId" value="sjyj_sjyj" scope="request" />			
		</c:if>
		<!-- 设置报表一级菜单ID -->
		<c:set var="rootMenu" value="${fn:substringBefore(reportMenuId, '_')}"/>
		<aut:authorize url="/admin/report/report-productMonthReport">
			<li id="sjyj"><a <c:if test="${rootMenu eq 'sjyj'}">class="current-chose"</c:if> 
			href="${_ctxPath}/admin/report/report-report.htm?reportMenuId=sjyj_">商家月结报表</a></li>
		</aut:authorize>
		<aut:authorize url="/admin/report/productReport-everydaySalesDetailReport">
		<li id="spl"><a <c:if test="${rootMenu eq 'spl'}">class="current-chose"</c:if> 
			href="${_ctxPath}/admin/report/productReport-everydaySalesDetailReport.htm?reportMenuId=spl_mrxsmx">商品类报表管理</a></li>
		</aut:authorize>
		<aut:authorize url="/admin/planCheck/planCheck-listPlanCheck">
		<li id="jsgl"><a <c:if test="${rootMenu eq 'jsgl'}">class="current-chose"</c:if> 
			href="${_ctxPath}/admin/planCheck/planCheck-listPlanCheck.htm?planCheckPage.params.feedbackCount=1&reportMenuId=jsgl_one">结算管理</a></li>
		</aut:authorize>
		<aut:authorize url="/admin/report/operationsReport-productSalesReport">
		<li id="yygl"><a <c:if test="${rootMenu eq 'yygl'}">class="current-chose"</c:if> 
			href="${_ctxPath}/admin/report/operationsReport-productSalesReport.htm?reportMenuId=yygl_spxs">运营管理报表</a></li>
		</aut:authorize>
		<aut:authorize url="/admin/report/saleStatementReport-orderWaitSendReport">
			<li id="tmdz"><a <c:if test="${rootMenu eq 'tmdz'}">class="current-chose"</c:if> 
				href="${_ctxPath}/admin/report/saleStatementReport-orderWaitSendReport.htm?reportMenuId=tmdz_mrdfh">特卖对账单</a></li>
		</aut:authorize>
	</ul>
</div>


