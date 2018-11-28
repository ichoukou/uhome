<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>热搜词管理</title>
	<link href="${ _cssPath}/common.css" rel="stylesheet" />
 	<link href="${ _cssPath}/pages/advertise.css" rel="stylesheet" />
	<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
	<script src="${_jsPath }/pages/hotWords.js" language="javascript"></script>
</head>
<body>
<jsp:include page="../include/header.jsp"  flush="true" />
	<div class="body m-w980p">
		<div class="body-nav m-mt10p">
			<ul class="m-clear">
				<aut:authorize url="/admin/adv/adv_searchAdvs">
	            <li><a href="${_ctxPath}/admin/adv/adv_searchAdvs.htm">广告页面</a></li>
	            </aut:authorize>
				<li><a class="current-chose">热搜词管理</a></li>
				<aut:authorize url="/admin/suggest/suggest-searchSuggests">
				<li><a href="${_ctxPath}/admin/suggest/suggest-searchSuggests.htm">站内信</a></li>
				</aut:authorize>
	            <li><a href="${_ctxPath}/admin/help/help-listHelps.htm">网站地图</a></li>
	             <aut:authorize url="/admin/seo/seo-listSeoConfigs">
			<li><a href="${_ctxPath}/admin/seo/seo-listSeoConfigs.htm">SEO信息</a></li>
			</aut:authorize>
			<aut:authorize url="/admin/link/link-searchLinks">
			<li><a href="${_ctxPath}/admin/link/link-searchLinks.htm">友情链接</a></li>
			</aut:authorize>
			</ul>
		</div>
		<div class="search-cont">
			<ul class="m-clear">
				<li>显示的热搜索词：</li>
				<c:forEach items="${hotWords}" var="hotWord" varStatus="status">
					<li id="${hotWord.hotWordId}">
		                <div class="search-edit">
		                    <i class="up"></i>
		                    <label>${hotWord.word }</label>
		                    <i class="edit" ></i>
		                    <i class="del"></i>
		                    <i class="down"></i>
		                </div>
		            </li>
				</c:forEach>
			</ul>
		</div>
		<!--start form-->
		<form class="sub-form layout" action="${_ctxPath}/admin/hotword/hotword-saveHotWord.htm" method="post" onsubmit="return submitForm();">
			<div>
					<input type="text" data-default="添加热搜索词" class="txt-input input-marks" id="txtKeywords" name="hotWord.word"/><input type="submit" class="m-btn m-btn-search" value="添  加" />
			</div>
		</form>
		<!--end form-->
	</div>
	<!--end body-->
	<!--start footer-->
	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
	<!--end footer-->
</body>
</html>