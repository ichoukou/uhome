<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 加载报表一级菜单 -->
<jsp:include page="./reportRootMenu.jsp" flush="true" />
<!-- 运营管理报表菜单 -->
<div class="report-tab">
	<ul class="m-clear">
		<li id="yygl_spxs" <c:if test="${reportMenuId eq 'yygl_spxs'}">class="cur"</c:if>><a href="${_ctxPath}/admin/report/operationsReport-productSalesReport.htm?reportMenuId=yygl_spxs">商品销售报表</a>
		</li>
		<li id="yygl_mjyy" <c:if test="${reportMenuId eq 'yygl_mjyy'}">class="cur"</c:if>><a href="${_ctxPath}/admin/report/operationsReport-buyerOperationsReport.htm?reportMenuId=yygl_mjyy">买家运营报表</a>
		</li>
	</ul>
</div>

