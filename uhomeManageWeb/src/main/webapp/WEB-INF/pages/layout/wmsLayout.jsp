<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title><c:if test="${not empty htmTitle}">${htmTitle}</c:if>
       <c:if test="${empty htmTitle}">新龙WMS</c:if> </title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:if test="${not empty htmKeywords}">${htmKeywords} </c:if>新龙WMS" />
<c:if test="${not empty htmDescription}">
<meta name="description" content="${htmDescription}<c:if test="${not empty htmKeywords}">。${htmKeywords}</c:if>" />
</c:if>
<meta http-equiv="imagetoolbar" content="no" />

<link rel="stylesheet" type="text/css" href="${_cssPath}/base/reset.css?${_version}" media="all" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/common/common.css?${_version}" media="all" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/module/button.css?${_version}" media="all" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/module/box.css?${_version}" media="all" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/module/table.css?${_version}" media="all" />

<link rel="stylesheet" type="text/css" href="${_cssPath}/module/dialog.css?${_version}" media="all" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/module/page.css?${_version}" media="all"/>
<link rel="stylesheet" type="text/css" href="${_cssPath}/page/question.css?${_version}" media="all" />

<script type="text/javascript" src="${_jsPath}/lib/jquery-1.7.min.js?${_version}"></script>
<script language="javascript" src="${_jsPath}/util/position_fixed.js?${_version}"></script>
<script language="javascript" src="${_jsPath}/module/dialog.js?${_version}"></script>
<script language="javascript" src="${_jsPath}/util/DD_belatedPNG.js?${_version}"></script>
<script language="javascript" src="${_jsPath}/module/heartcode-canvasloader-min-0.9.1.js?${_version}"></script>
<script language="javascript" src="${_jsPath}/page/menu.js?${_version}"></script>
<script language="javascript" src="${_jsPath}/module/calendar/WdatePicker.js?${_version}"></script>
<script type="text/javascript">
	//var j = jQuery.noConflict();
	var _ctxPath = '${_ctxPath}';
	var _filePath = '${_filePath}';
	var _imagesPath = '${_imagesPath}';
</script>

</head>
<body>	
	<div id="main" class="clearfix">
		<div id="header">
			<tiles:insertAttribute name="header" />
		</div>		
		<tiles:insertAttribute name="container" />		
		<div id="footer">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
</body>
</html>










