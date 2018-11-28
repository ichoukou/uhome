<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 加载报表一级菜单 -->
<jsp:include page="./reportRootMenu.jsp" flush="true" />
<!-- 特卖对账单报表菜单 -->
<div class="report-tab">
	<ul class="m-clear">
		<li id="tmdz_mrdfh" <c:if test="${reportMenuId eq 'tmdz_mrdfh'}">class="cur"</c:if> style="width:140px;">
			<a href="${_ctxPath}/admin/report/saleStatementReport-orderWaitSendReport.htm?reportMenuId=tmdz_mrdfh">每日新增待发货订单</a>
		</li>
		<li id="tmdz_mrdth" <c:if test="${reportMenuId eq 'tmdz_mrdth'}">class="cur"</c:if> style="width:140px;">
			<a href="${_ctxPath}/admin/report/saleStatementReport-orderWaitRefundReport.htm?reportMenuId=tmdz_mrdth">每日新增待退款订单</a>
		</li>
	</ul>
</div>

