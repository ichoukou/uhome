<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>友情链接</title>
<link href="${ _cssPath}/common.css" rel="stylesheet" />
<link href="${ _cssPath}/pages/arrangement.css" rel="stylesheet" />
<link href="${_jsPath }/plugin/artdialog/skins/ytoxl.css" rel="stylesheet" />
<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
<script src="${_jsPath }/plugin/artdialog/jquery.artDialog.min.js"></script>
<script src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js" language="javascript"></script>
<script src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js" language="javascript"></script>
</head>
<body>
<!--start header-->
<jsp:include page="../include/header.jsp"  flush="true" />
<!--end header-->

<!--start body-->
<div class="body m-w980p">
	<jsp:include page="../include/pageManageMenu.jsp"  flush="true" />
        <div class="m-mt10p">
            <div><input type="button" id="addSale-btn"  class="m-btn" value="新增链接页面"/></div>
        </div>
      	<!--start 链接信息-->
        <div class="links-wrap m-mt10p">
        	<ul class="m-clear">
        		<c:forEach items="${linksList}" var="links" varStatus="status">
	        		<li><a href="${links.linkUrl }" target="_blank">${links.name }</a>
	        			<span class="links-oprea m-hide">
	        				<input type="hidden" value="${links.linkId }"/>
	        				<a href="javascript:;" class="J-edit-seo">编辑</a>
	        				<a href="javascript:;" linkId="${links.linkId }" onclick="deleteLinks(${links.linkId});">删除</a>
	        			</span>
	        		</li>
        		</c:forEach>
        	</ul>
        </div>
      	<!--end 链接信息-->
      	<!--start 新增、编辑链接信息弹出层-->
      	<div class="add-seo-info">
      		<form  method="post" id="form1" name="form1" action="${_ctxPath}/admin/link/link-saveLinks.htm">
      			<input type="hidden" name="links.linkId" id="linkId" class="input-key"/>
      			<table>
      				<tr>
      					<td width="60" align="right">网站名称：</td>
      					<td><input type="text" class="input-key" name="links.name" id="name"/></td>
      				</tr>
      				<tr>
	                	<td></td>
	                	<td><div class="position"><span id="nameTips"></span></div></td>
                	</tr>
                	<tr>
      					<td width="60" align="right">网站地址：</td>
      					<td><input type="text" class="input-key" name="links.linkUrl" id="linkUrl"/></td>
      				</tr>
      				 <tr>
	                	<td></td>
	                	<td><div class="position"><span id="linkUrlTips"></span></div></td>
                	</tr>
                 	<tr>
      					<td width="60" align="right">排序：</td>
      					<td><input type="text" class="input-key" name="links.sort" id="sort"/></td>
      				</tr>
      				<tr>
	                	<td></td>
	                	<td><div class="position"><span id="sortTips"></span></div></td>
                	</tr>
      			</table>
      		</form>
      	</div>
      	<!--end 新增、编辑链接编辑弹出层-->
    </div>
<!--end body-->
<!--start footer-->	
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<!--end footer-->
<script src="${_jsPath }/pages/searchLinks.js" language="javascript"></script>
</body>
</html>