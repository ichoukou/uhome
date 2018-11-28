<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
  <meta charset="utf-8"/>
    <title>我的新龙-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/changepwd.css?d=${_resVerion}"/>
</head>
<body >
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
	 <div class="m-w960p pwd-wrap">
        <!--面包屑-->
        <div class="crumbs"><a href="${_ctxPath }">${_webSiteName} ></a>找回密码</div>
        <!--面包屑 end-->
        <div class="m-rel">
            <div class="cs-tel">客服热线  <span>${_tel}</span></div>
        </div>
        <div class="step-status cf"><span class="step">输入账户名</span><span>验证身份</span><span>重置密码</span><span>完成</span></div>
        <div class="pwd-reset">
            <form action="${_ctxPath}/user/user-passwordBackQueryMsg.htm" id ="getpassFrom" method="post">
            <table class="pwd-tab">
                <tr>
                    <td class="pwd-name">邮箱地址：</td>
                    <td class="pwd-cont">
	                    <input id="username" type="text" class="input-tip" name="username"/>
                    </td>
                </tr>
                <tr>
                	<td></td>
                	<td>
                		<div class="position"><span class="error">该邮箱不存在</span><span id="usernameTips"></span></div>
                	</td>
                </tr>
                <tr>
                    <td class="pwd-name">验证码：</td>
                    <td class="pwd-cont pwd-validate">
                    	<input type="text" id="j_captcha_1" name="jCaptchaResponse"/>
                    	<img src="${_ctxPath}/validationCode.htm"  id="j_validateCodePass"/>
                    	<a href="javascript:nextValidateCodePass()" class="pwd-changev">看不清，换一个</a>
                    </td>
                </tr>
                <tr>
                	<td></td>
                	<td><div class="position"><span id="j_captchaTips"></span></div></td>
                </tr>
                <tr>
                    <td></td>
                    <td><a class="btn-c" id="save"><span>提交</span></a></td>
                </tr>
            </table>
            <input type="hidden" id="_ctxPath" value="${_ctxPath}" >
            </form>
        </div>
    </div>
   	<%@include file="/WEB-INF/pages/include/foot.jsp"%>
	<script src="${_jsPath}/pages/pwdchange.js?d=${_resVerion}"></script>
	<script type="text/javascript">
		$("#save").click(function(){
			$(".position").find(".error").remove();
		});
	</script>
</body>
</html>