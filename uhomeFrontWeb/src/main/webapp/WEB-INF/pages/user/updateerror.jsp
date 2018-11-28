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
        <div class="m-rel">
            <div class="cs-tel">客服热线  <span>021-64703131</span></div>
        </div>
        <div class="step-status cf"><span class="step-1 original">1.输入账户名</span><span class="step-2 original">2.验证身份</span><span class="step-3">3.重置密码</span><span>4.完成</span></div>
        <div class="pwd-reset">
            <span class="pwd-dir pwd-three"></span>
            <form action="${_ctxPath}/user/user-updatePassword.htm" id ="getpassFrom"  method="post">
            <table class="pwd-tab">
                <tr>
                    <td></td>
                    <td><span class="cor-hui">请重新设置您的密码，旧时密码即时失效</span></td>
                </tr>
                <tr>
                    <td class="pwd-name">登陆名：</td>
                    <td class="pwd-cont">
                    <input type="text" class="input-tip" name="username" id="username"/><font color="red">账号已存在</font>
                    <span id="usernameTips"></span>
                    </td>
                </tr>
                <tr>
                    <td class="pwd-name">确认密码：</td>
                    <td class="pwd-cont">
                    <input type="text" class="input-tip" name="password" id="j_captcha"/>
                    <span id="j_captchaTips"></span>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><a class="btn-c" id="save"><span>确定</span></a></td>
                </tr>
            </table>
            <input type="hidden" id="_ctxPath" value="${_ctxPath}" >
        </form>    
        </div>
    </div>
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
	<script src="${_jsPath}/pages/pwdchange.js?d=${_resVerion}"></script>
</body>
</html>