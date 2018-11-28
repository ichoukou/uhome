<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>

<!-- 加载报表一级菜单 -->
<jsp:include page="./reportRootMenu.jsp" flush="true" />
<div class="report-tab">
	<ul class="m-clear">
	
 		<li id="jsgl_one" <c:if test="${reportMenuId eq 'jsgl_one'}">class="cur"</c:if>><a href="${_ctxPath}/admin/planCheck/planCheck-listPlanCheck.htm?planCheckPage.params.feedbackCount=1&reportMenuId=jsgl_one">待一次对账</a>
		</li>
		<li id="jsgl_two" <c:if test="${reportMenuId eq 'jsgl_two'}">class="cur"</c:if>><a href="${_ctxPath}/admin/planCheck/planCheck-listPlanCheck.htm?planCheckPage.params.feedbackCount=2&reportMenuId=jsgl_two">待二次对账</a>
		</li>
		<li id="jsgl_finish" <c:if test="${reportMenuId eq 'jsgl_finish'}">class="cur"</c:if>><a href="${_ctxPath}/admin/planCheck/planCheck-listPlanCheck.htm?planCheckPage.params.feedbackCount=3&reportMenuId=jsgl_finish">对账结束</a>
		</li>		
	</ul>
</div>

