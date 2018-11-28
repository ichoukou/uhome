<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/base/reset.css?d=${_resVerion}" media="all" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/common/common.css?d=${_resVerion}" media="all" />
<link rel="stylesheet" type="text/css" href="${_cssPath}/module/button.css?d=${_resVerion}" media="all" />

<!--当前css-->
<link rel="stylesheet" type="text/css" href="${_cssPath}/page/login.css?d=${_resVerion}"
	media="all" />
<title>欢迎登陆WMS系统</title>
<%
	Enumeration en = request.getParameterNames();
	if (en.hasMoreElements()) {
		String error = String.valueOf(en.nextElement());
		if (error != null)
			request.setAttribute("error", error);
	}
	//SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");  
	//登录名  
	//request.setAttribute("usernameError",securityContextImpl.getAuthentication().getName());
%>
<script type="text/javascript">
	if (window != top)
		top.location.href = location.href;
</script>
</head>
<body onkeydown="enterPress(event);">
	<div class="login">
		<div class="login_logo">
			<img src="${_imagesPath}/single/login_logo.png" alt="" />
		</div>
		<div id="login_box" <c:if test="${empty LOGIN_ERROR or LOGIN_ERROR <= 1 }">style="width:680px;"</c:if>>
			<form action="${_ctxPath}/j_spring_security_check" method="post"
				id=wmslogin_form>
				<div class="box_left">
					<div id="accid_err"></div>
					<div id="all_err" class="all_err">
						<c:choose>
							<c:when test="${error == 'error1'}">
								<span class="yto_onError">该用户不存在</span>
							</c:when>
							<c:when test="${error == 'error4'}">
								<span class="yto_onError">该用户被冻结，请联系新龙管理员</span>
							</c:when>
							<c:when test="${error == 'error5'}">
								<span class="yto_onError">该用户被删除，请联系账号管理员</span>
							</c:when>
						</c:choose>
					</div>
					<p>
						<img class="icon" src="${_imagesPath}/single/user.jpg" alt="" />
						<label for="account_id" data-text="请输入账号" for="account_id"></label>
						<input type="text" class="input_text input_text_a use_ico"
							id="account_id" name="j_username"
							value="${SPRING_SECURITY_LAST_USERNAME}" tabIndex="1" maxlength="20"/>

					</p>
				</div>
				<div class="box_left">
					<div id="psw_err"></div>
					<div class="all_err" style="display: block;">
						<c:if test="${error == 'error'}">
							<span class="yto_onError">密码错误</span>
						</c:if>
					</div>
					<p>
						<img class="icon" src="${_imagesPath}/single/lock.jpg" alt="" />
						<label data-text="请输入密码" for="psw"></label> <input id="psw"
							class="input_text input_text_a psw_ico" type="password"
							name="j_password" tabIndex="2" maxlength="20"/>
					</p>
					<p>
						<!-- 暂时隐藏找回密码 <a href="${_ctxPath}/user/user-fetchBackPassword.htm">找回密码</a>-->
					</p>
				</div>
				<c:if test="${LOGIN_ERROR > 1}">
					<div class="box_left">
						<div id="code_err"></div>
						<div class="all_err" style="display: block;">
							<c:if test="${error == 'error2'}">
								<span class="yto_onError">验证码错误</span>
							</c:if>
						</div>
						<p>
                            <span class="img_check">
                            <img src="${_ctxPath}/validationCode.htm" alt=""
                            id="validateCode" onclick="nextValidateCode();" /> </span>
							<img class="icon" src="${_imagesPath}/single/lock.jpg" alt="" />
							<label data-text="请输入验证码" for="checkNum"></label> <input
								id="checkNum" class="input_text input_check" type="text"
								name="j_captcha" tabIndex="3" maxlength="4"/>
						</p>
						<p>
							看不清？<a href="javascript:void(0);" onclick="nextValidateCode();">换一个</a>
						</p>
					</div>
				</c:if>
				<div class="box_right">
					<p>
						<a class="btn btn_g" title="登录" href="javascript:;"
							id="wmslogin_btn"> <span class="add">登录</span> </a>
					</p>
					<p>
						<input id="mark_psw" class="input_checkbox" type="checkbox"
							name="" onchange="rememberMe(this);" /> <label for="mark_psw">记住密码</label>
					</p>
				</div>
			</form>
		</div>
		<div class="footer">
			<p>上海圆通新龙电子商务有限公司：上海市徐汇区桂林路396号1号楼 联系电话：021-64703131-107</p>
			<p>Copyright© 2000-2013 ALL rught reserved</p>
		</div>
	</div>
	<script type="text/javascript" src="${_jsPath }/lib/jquery-1.7.min.js"></script>
	<script type="text/javascript"
		src="${_jsPath }/module/formvalidator/formValidator-4.1.1.js"></script>
	<script type="text/javascript"
		src="${_jsPath }/module/formvalidator/formValidatorRegex.js"></script>
	<script type="text/javascript" src="${_jsPath }/module/input.js"></script>
	<!--当前js-->
	<script type="text/javascript" src="${_jsPath }/page/login.js?d=${_resVerion}"></script>
	<script type="text/javascript">
		function nextValidateCode() {
			$("#validateCode").attr("src",
					"${_ctxPath}/validationCode.htm?date=" + new Date());
		}
		$(function() {
			var flag = $(".all_err").html();
			if (flag != "")
				$(".all_err").show();
		});
		function enterPress(e) {
			if (e.keyCode == 13) {
				//判断验证是否通过
				var result = $.formValidator.pageIsValid('1');
				if (result) {
					$('#wmslogin_form').submit();//验证通过提交表单
				}
			}
		}
		function rememberMe(obj) {
			var f = $(obj).attr("checked");
			if (f == "checked")
				$(obj).attr("name", "_spring_security_remember_me");
			else
				$(obj).attr("name", "");
		}
	</script>
</body>
</html>
