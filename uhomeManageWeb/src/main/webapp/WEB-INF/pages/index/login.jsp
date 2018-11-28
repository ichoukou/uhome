<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@include file="../include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>${_webSiteNameManager }</title>
<meta http-equiv="imagetoolbar" content="no" />
<script type="text/javascript">
	//var j = jQuery.noConflict();
	var _ctxPath = '${_ctxPath}';
	var _filePath = '${_filePath}';
	var _imagesPath = '${_imagesPath}';
	var winParams = {};
</script>
<link href="${ _cssPath}/pages/login.css" rel="stylesheet" />
<script type="text/javascript" src="${_jsPath}/jquery/jquery.1.7.2.js"></script>
<script type="text/javascript">
	var changeCaptcha=function(){
		$('#captchaImg').attr('src','${_ctxPath}/jcaptcha_image.htm?date='+new Date());
	}
</script>
</head>
<%
	Enumeration en = request.getParameterNames();
	if (en.hasMoreElements()) {
		String error = String.valueOf(en.nextElement());
		Object times = session.getAttribute("LOGIN_ERROR");
		request.setAttribute("error", error);
	}
%>
<body>
	<div class="head">
		<span>客服电话：${_telManager}</span>
	</div>
	<div class="title">
		<i class="tit-img"></i>
	</div>
	<div class="login-bg">
		<div class="login-main">
			<div class="login-left"></div>
			
				<form method="post" id="login_form"
					action="${_ctxPath}/j_spring_security_check" name="login_form"
					autocomplete="off">
					<div class="login-center">
						<ul>
							<li><span>登录名：</span> <input type="text" class="input-text"
								id="account_id" name="j_username"
								value="${SPRING_SECURITY_LAST_USERNAME }" maxlength="20" /> <span
								id="accountTip"></span> 
							</li>
							<li><span>密码：</span> <input type="password" name="j_password"
								id="psw" class="input-text" maxlength="20" /> 
								 <span id="pswTip"></span> 
								 
							</li>
							<s:if test="#session.LOGIN_ERROR > 1">
							<li><span>验证码：</span> <input type="text" name="j_captcha"
								id="checkcode" class="input-img" maxlength="4" /> <img
								alt="验证码" src="${_ctxPath}/jcaptcha_image.htm"
								class="captchaImg" id="captchaImg"/> <a class="yzmtxt"
								style="cursor: pointer;" onclick="changeCaptcha()">看不清换一张</a> 
							</li>
							</s:if>
							<li><span>&nbsp</span> 
								<input checked="checked" type="checkbox" class="l-checkbox" id="login_c" name="_spring_security_remember_me"/>
								<label for="login_c">十天内免登录</label>
								<!--<a href="#" class="l-forget">忘记密码</a> -->
							</li>
						</ul>
					</div>
					<div class="login-right">
					<div class="login-tip">
						<c:if test="${error == 'error1'}">
								<i class="l-error"></i>该用户不存在
						</c:if> 
						<c:if test="${error == 'error4'}">
								<i class="l-error"></i>
							该用户被冻结，请联系新龙管理员
						</c:if>
						<c:if test="${error == 'error5'}">
								<i class="l-error"></i>该用户被删除，请联系账号管理员
						</c:if>
						<c:if
							test="${error == 'error'}">
								<i class="l-error"></i>密码错误
						</c:if>
						<c:if
							test="${error == 'error2'}">
							<i class="l-error"></i>验证码错误
						</c:if>
					</div>
					<div class="">
						<input type="submit" value="" class="l-submit" />
					</div>
					</div>
					<div class="clear"></div>
				</form>
			</div>
	</div>
	<div class="login-foot">
		<p>${_footCopyrightManager }</p>
		<p>${_footAddressManager }</p>
	</div>
</body>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</html>