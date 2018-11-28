<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
  <meta charset="utf-8"/>
    <title>修改密码-个人中心-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/alter-password.css?d=${_resVerion}"/>
</head>
<body >
    <jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
    <div class="m-w960p cf">
        <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/user/orders.htm">个人中心 ></a>修改密码</div>
        <!--面包屑 end-->
        <jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>
        <div class="inf-detail m-mt20p m-mb10p">
            <div class="inf-title fon-big">
                	修改密码
            </div>
            <form class="user-tab alt-tab" id="form1"  method="post">
                <table>
                    <thead>
                    <tr>
                        <td class="alt-title" width="13%">输入当前密码：</td>
                        <td class="alt-text" width="30%"><input type="password" id="nowPwd" name="password" /></td>
                        <td class="error" width="35%"><span id="nowPwdTips"></span></td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="alt-title">输入新密码：</td>
                        <td class="alt-text"><input  type="password" id="password1" name="newpassword"/></td>
                        <td class="error"><span id="newmimaTips"></span></td>
                    </tr>
                    <tr>
                        <td class="alt-title">重复输入新密码：</td>
                        <td class="alt-text "><input  type="password" id="password2"/></td>
                        <td class="error"><span id="resetmimaTips"></span></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="2" class="alt-btn">
                            <a class="btn-c" id="save" href="javascript:;"><span>确定</span></a>
                        </td>
                    </tr>
                    </tbody>
                </table>
                	<input type="hidden" id="_ctxPath" value="${_ctxPath}" >
            </form>
        </div>
    </div>
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
    <script src="${_jsPath}/plugin/select/district-all.js"></script>
    <script src="${_jsPath}/plugin/select/jquery.linkagesel-min.js"></script>
    <script src="${_jsPath}/pages/alert-password.js?d=${_resVerion}"></script>
</body>
</html>