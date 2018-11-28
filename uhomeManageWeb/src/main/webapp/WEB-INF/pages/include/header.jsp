<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="aut" uri="http://www.springsecurity.org/jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<div class="header">
	<div class="m-w980p m-clear">
		<div class="topbar-fl m-fl">
				<span><sec:authentication property="name" /></span>，感谢您光临${_webSiteNameManager}！
						<a href="${_ctxPath}/j_spring_security_logout" class="topbar-logout">退出</a>
<%-- 					<aut:authorize url="/admin/user/user-editPassword"> --%>
					 <a class="topbar-psword" href="${_ctxPath}/admin/user/user-editPassword.htm">修改密码    </a>
<%-- 				    </aut:authorize> --%>
				    <!-- 存储卖家id -->
				    <input type="hidden" name="userId" id="userId" value="${session.SPRING_SECURITY_CONTEXT.authentication.principal.userId }"/>
		</div>
		<div class="topbar-fr m-fr">客服热线：${_telManager}</div>
	</div>
</div>
<div class="nav">
		<div class="m-w980p m-clear">
			<h1>
				<img src="${_imagesPath }/logo.png" />
			</h1>
			<ul id="headerMenuUl">
				<c:forEach items="${canVisitMenu.childMenuModel }" var="m" varStatus="vs">
					<li class="${menuFlag==m.uresourceId?'active':'' }"><a id="vs${vs.index }" href="${_ctxPath}${m.url }.htm?menuFlag=${m.uresourceId }">${m.uresourceName }</a></li>
				</c:forEach>
			</ul>
			<div class="nav-enter">
				<a href="${toShopping}" target="_blank">进入商城&gt;&gt;</a>
			</div>
		</div>
	</div>
<script type="text/javascript">
<!--
if(location.href.indexOf('/index.htm')>0){
	document.getElementById("vs0").click();
}
//-->
</script>

