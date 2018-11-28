<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
  <meta charset="utf-8"/>
    <title>找回密码-我的新龙-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/changepwd.css?d=${_resVerion}"/>
</head>
<body >
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
  	<div class="m-w960p pwd-wrap">
        <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a>找回密码</div>
        <!--面包屑 end-->
        <div class="step-status step-4 cf"><span class="step">输入账户名</span><span class="step">验证身份</span><span class="step">重置密码</span><span class="step">完成</span></div>
        <div class="pwd-reset">
            <table class="pwd-tab pwd-email">
                <tr>
                    <td class="pwd-yzname">${findPasswordInfo}</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="pwd-cont"><span class="cor-hui">请等待3秒系统自动跳转首页</span></td>
                    <td></td>
                </tr>
                <tr>
                    <td>如果3秒后系统未自动跳转，请点击<a href="${_ctxPath }" class="pwd-changev">立即跳转</a></td>
                    <td></td>
                </tr>
            </table>
            <input type="hidden" id="_ctxPath" value="${_ctxPath}" >
        </div>
    </div>
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
	<script src="${_jsPath}/pages/pwdchange.js?d=${_resVerion}"></script>
   	<form id="true_form" action="${_ctxPath}/j_spring_security_check" style="display:none;"  method="post">
    	<input type="text"  id="tname" name="j_username" type="text" value="${username}">
    	<input name="j_password" id="tpassword"  type="password" value="${password}">
    </form>
</body>
<script type="text/javascript">
	function goToIndex(){
		window.location=_ctxPath;
	}
	setInterval(goToIndex,3000);
</script>
</html>