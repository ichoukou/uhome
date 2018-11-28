<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>

<!-- 加载报表一级菜单 -->
<jsp:include page="./reportRootMenu.jsp" flush="true" />
<div class="report-tab">
	<ul class="m-clear">
		<li id="spl_mrxsmx" <c:if test="${reportMenuId eq 'spl_mrxsmx'}">class="cur"</c:if>><a href="${_ctxPath}/admin/report/productReport-everydaySalesDetailReport.htm?reportMenuId=spl_mrxsmx">每日销售明细表</a>
		</li>
		<li id="spl_sjxs" <c:if test="${reportMenuId eq 'spl_sjxs'}">class="cur"</c:if>><a href="${_ctxPath}/admin/report/productReport-salesDetailReport.htm?reportMenuId=spl_sjxs">商家销售管理</a>
		</li>
		<li id="spl_ppxs" <c:if test="${reportMenuId eq 'spl_ppxs'}">class="cur"</c:if>><a href="${_ctxPath}/admin/report/productReport-brandSell.htm?reportMenuId=spl_ppxs">品牌销售明细</a>
		</li>
		<li id="spl_flxs" <c:if test="${reportMenuId eq 'spl_flxs'}">class="cur"</c:if>><a href="${_ctxPath}/admin/report/productReport-classifySell.htm?reportMenuId=spl_flxs">分类销售明细</a>
		</li>
		<li id="spl_hyxs" <c:if test="${reportMenuId eq 'spl_hyxs'}">class="cur"</c:if>><a href="${_ctxPath}/admin/report/productReport-memberSell.htm?reportMenuId=spl_hyxs">会员销售管理</a>
		</li>
		<li id="spl_thsp" <c:if test="${reportMenuId eq 'spl_thsp'}">class="cur"</c:if>><a href="${_ctxPath}/admin/report/productReport-productReturn.htm?reportMenuId=spl_thsp">退货商品管理</a>
		</li>
		<li id="spl_sjgl" <c:if test="${reportMenuId eq 'spl_sjgl'}">class="cur"</c:if>><a href="${_ctxPath}/admin/report/productReport-sellerReport.htm?reportMenuId=spl_sjgl">商家管理</a>
		</li>
		<li id="spl_xsqy" <c:if test="${reportMenuId eq 'spl_xsqy'}">class="cur"</c:if>><a href="${_ctxPath}/admin/report/productReport-areaSellReport.htm?reportMenuId=spl_xsqy">销售区域统计</a>
		</li>
		<li id="spl_ddxx" <c:if test="${reportMenuId eq 'spl_ddxx'}">class="cur"</c:if>><a href="${_ctxPath}/admin/report/productReport-orderDetailReport.htm?reportMenuId=spl_ddxx">订单信息</a>
		</li>
	</ul>
	
</div>

