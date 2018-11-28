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
        <div class="step-status step-2 cf"><span class="step">输入账户名</span><span class="step">验证身份</span><span>重置密码</span><span>完成</span></div>
        <div class="pwd-reset">
            <table class="pwd-tab pwd-email" >
                <tr>
                    <td class="pwd-name">找回密码邮件已发送到<!-- 注册 -->(${email})邮箱</td>
                    <td></td>
                </tr>
                <tr>
                    <td><span class="cor-hui">请查收后根据邮件内容操作</span></td>
                    <td></td>
                </tr>
                <tr>
                    <td><span class="cor-hui">请在24小时内通过邮件中的连接重设密码。</span></td>
                    <td></td>
                </tr>
                <tr>
                    <td><span class="cor-hui">注：如果您没有收到“找回密码”邮件，建议去垃圾邮箱中找找看，或者点此<a href="${_ctxPath}/user/user-findPassWord.htm">重新找回密码</a>。</span></td>
                    <td></td>
                </tr>
            </table>
            <table class="pwd-tab yz-tab" style="display: none">
                <tr>
                    <td  class="pwd-yzname">激活码已发送到注册手机</td>
                    <td></td>
                </tr>
                <tr>
                    <td class="pwd-cont pwd-validate"><span class="cor-hui">请输入收到的激活码</span><input type="text"/></td>
                    <td class="pwd-cont"><a class="btn-c" id="save"><span>确认</span></a><span class="cor-red">激活码错误请重新输入</span></td>
                </tr>
                <tr>
                    <td><span class="cor-hui">（短信在繁忙时可能稍有延迟，请耐心等待）</span></td>
                    <td></td>
                </tr>
                <tr>
                    <td><span class="cor-red m-mt10p">50秒后重新获取</span></td>
                    <td></td>
                </tr>
            </table>
        </div>
    </div>
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
</body>
</html>