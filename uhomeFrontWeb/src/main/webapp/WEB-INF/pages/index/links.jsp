<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
<meta charset="utf-8" />
<title>友情链接</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
	<link rel="stylesheet" type="text/css" href="${_cssPath}/pages/links.css?d=${_resVerion}" media="all">
</head>
<body>
<jsp:include page="../include/head.jsp" flush="true" />
	<div class="m-w960p">
		 <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a>友情链接</div>
        <div class="links-wrap">
            <h3>友情链接</h3>
            <ul class="cf">
                <c:forEach items="${linksList}" var="links" varStatus="status">
                    <li><a href="${links.linkUrl }" target="_blank">${links.name }</a></li>
                </c:forEach>
            </ul>
        </div>
	</div>
	<!-- foot -->
	<%@include file="/WEB-INF/pages/include/foot.jsp"%>
</body>
</html>