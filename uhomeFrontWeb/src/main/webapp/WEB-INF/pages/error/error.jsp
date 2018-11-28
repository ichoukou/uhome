<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${_webSiteName }-error页面</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath}/pages/not-find.css?d=${_resVerion}" media="all">
</head>
<body>
    <%@include file="/WEB-INF/pages/include/head.jsp"%>
    <div class="m-w960p">
        <div class="not-find">
            <div class="link">
            	错误页面!
            	<a href="javascript:;">查看其它优惠</a>
            	<a href="javascript:;">返回上级页面</a>
           	</div>
        </div>
    </div>
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
</body>
</html>