<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglibs.jsp"%>
<%-- <c:set var="ctxPath" value="${pageContext.request.contextPath}" scope="request"/> --%>
<%-- <c:set var="cssPath" value="${ctxPath}/css" scope="request"/> --%>
<%-- <c:set var="imagesPath" value="${ctxPath}/images" scope="request"/> --%>
<%-- <c:set var="jsPath" value="${ctxPath}/js" scope="request"/> --%>

<meta charset="utf-8" />
<!-- 公共CSS -->
<link rel="stylesheet" type="text/css" href="${_cssPath}/base/reset.css?d=${_resVerion}" media="all" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/common/common.css?d=${_resVerion}" media="all" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/module/button.css?d=${_resVerion}" media="all" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/module/table.css?d=${_resVerion}" media="all" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/module/dialog.css?d=${_resVerion}" media="all" />
<!--[if ie]><link href="${_cssPath}/page/ie.css" type="text/css" rel="stylesheet"><![endif]-->
<!-- 公共JS -->
<script type="text/javascript" src="${_jsPath}/lib/jquery-1.7.min.js"></script>
<script type="text/javascript" src="${_jsPath}/module/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${_jsPath}/module/input.js"></script>
<script type="text/javascript" src="${_jsPath}/module/dialog.js"></script>
<script type="text/javascript" src="${_jsPath}/util/position_fixed.js"></script>

<script type="text/javascript">
var _ctxPath = "${_ctxPath}";
var _baseUrl = '<%=(request.getScheme()+"://"+  request.getServerName() +":"+ request.getServerPort() + (com.ytoxl.module.core.common.utils.StringUtils.isEmpty(request.getContextPath()) ? "" : (request.getContextPath()))  )%>';
 
 	/**
  	*version 2.0
  	*打印公共方法，调用前页面必须导入print_dev.js,否则会Js报错。
  	*/
 	var doPrint = function(url,printerName,isPaper){
	 	var header = ""; 
		var footer = !!isPaper ? "&w&b页码:&p/&P" : "";
		var margin_bottom = !!isPaper ? "0.25" : "0"; 
		var margin_left = !!isPaper ? "0.35" : "0";  
		var margin_right = !!isPaper ? "0.45" : "0";  
		var margin_top = !!isPaper ? "0.55" : "0";
		//调用
		var print = new Print(); 
		print.init({
	    	footer : footer,
			margin_bottom : margin_bottom, 
			margin_left : margin_left,  
			margin_right : margin_right,
			margin_top : margin_top, 
	    	printerName : printerName,
	    	url : url     //相对路径
		});
 	}
</script>