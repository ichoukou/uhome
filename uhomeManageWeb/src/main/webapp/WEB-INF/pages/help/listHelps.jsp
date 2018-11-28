<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>网站地图</title>
	<link href="${ _cssPath}/common.css" rel="stylesheet" />
	<link href="${ _cssPath}/pages/footInfo.css" rel="stylesheet" />
	<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/ckeditor/ckeditor.js"></script>
</head>
<body>
<jsp:include page="../include/header.jsp"  flush="true" />
<div class="body m-w980p m-clear">
	<jsp:include page="../include/pageManageMenu.jsp"  flush="true" />
    <div class="foot-left">
	    <div class="foot-left-t">底部信息</div>
	    <c:forEach items="${helpCategorys}" var="helpCategory" varStatus="status">
			<div class="foot-left-m">
		    	<div class="foot-list">
		    		<h1><i class="foot-list-i"></i>${helpCategory.name}</h1>
		    		<c:forEach items="${helpCategory.helpCategorys}" var="help" varStatus="index">
						<a href="javascript:void(0)" onclick="getContent(this,${help.help.helpId})">${help.name}</a>
					</c:forEach>
		    	</div>
		    </div>
		</c:forEach>
	  </div>
    <!--end left-->
    <!--star right-->
    <div class="foot-right">
    	<div><span>标签：</span><input type="text" class="txt-input" readonly="readonly" id="title"/></div><br/>
    	<textarea rows="5" cols="15" id="content" name="help.content"></textarea>
    	<div class="input-num">源码：已输入<b class="c-red" id="contentCount">0</b>/最多输入<b class="c-green">25000</b></div>
    	<input type="hidden"  name="help.helpId" id="helpId"/>
    	<aut:authorize url="/admin/help/help-updateContent">
    	<div>
    		<input type="button" class="m-btn" value="保存" id="save"/>
    		<input type="button" class="m-btn" value="取 消" />
    	</div>
    	</aut:authorize>
    </div>
    <!--end right-->
</div>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<script src="${_jsPath }/pages/help.js" language="javascript"></script>
</body>
</html>