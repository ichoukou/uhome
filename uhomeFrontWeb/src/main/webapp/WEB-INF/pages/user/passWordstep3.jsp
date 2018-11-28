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
        <%--<div class="m-rel">--%>
            <%--<div class="cs-tel">客服热线  <span>${_tel}</span></div>--%>
        <%--</div>--%>
        <div class="step-status step-3 cf"><span class="step">输入账户名</span><span class="step">验证身份</span><span class="step">重置密码</span><span>完成</span></div>
        <div class="pwd-reset">
            <form action="${_ctxPath}/user/user-updatePassword.htm" id ="getpassFrom"  method="post">
            <table class="pwd-tab">
                <tr>
                    <td></td>
                    <td><span class="cor-hui">请重新设置您的密码，旧时密码即时失效</span></td>
                </tr>
                <tr>
                    <td class="pwd-name">新密码：</td>
                    <td class="pwd-cont">
                    	<input type="password" class="input-tip" name="password1" id="newPsw"/>
                    </td>
                </tr>
                <tr>
                	<td></td>
                	<td><div class="position"><span id="newPswTips"></span></div></td>
                </tr>
                <tr>
                    <td class="pwd-name">确认密码：</td>
                    <td class="pwd-cont">
                    	<input type="password" class="input-tip" name="password" id="password"/>
                    </td>
                </tr>
                <tr>
                	<td></td>
                	<td><div class="position"><span id="passwordTips"></span></div></td>
                </tr>
                <tr>
                    <td></td>
                    <td><a class="btn-c" id="save"><span>确定</span></a></td>
                </tr>
            </table>
            <input type="hidden" id="_ctxPath" value="${_ctxPath}" >
            <%-- 
            <input type="hidden" name="userId" value="${userId}">
             --%>
            <input type="hidden" name="username" value="${username}"> 
            <input type="hidden" name="active" value="${active}"> 
            <input type="hidden" name="sendTime" value="${sendTime}"> 
        </form>    
        </div>
    </div>
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
<script src="${_jsPath}/pages/pwdchange.js?d=${_resVerion}"></script>
</body>
</html>