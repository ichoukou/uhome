<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改密码</title>
	<link href="${_cssPath}/common.css" rel="stylesheet" />
	<link href="${_cssPath}/pages/arrangement.css" rel="stylesheet" />
	<script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
	<script src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js" language="javascript"></script>
	<script src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js" language="javascript"></script>
</head>
<body>
<jsp:include page="../include/header.jsp"  flush="true" />
<div class="body m-w980p">
	<form id="editpsw">
		<div class="account-border">
			<table>
				<tr>
					<td width="135" align="right">输入当前密码</td>
					<td><input type="password" class="password-text" id="psw" /></td>
					<td><div id="pswTip"></div></td>
				</tr>
				<tr>
					<td width="135" align="right">输入新密码</td>
					<td><input type="password" class="password-text" id="inputpsw" /></td>
					<td><div id="inputpswTip"></div></td>
				</tr>
				<tr>
					<td width="135" align="right">重复输入新密码</td>
					<td><input type="password" class="password-text" id="repeatpsw" /></td>
					<td><div id="repeatpswTip"></div></td>
				</tr>
			<tr>
					<td width="135" align="right" >
					</td>
					<td><input type="button" value="保存" id="save" class="m-btn"/></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<script type="text/javascript">
	$(function(){
		$('#save').bind('click',function(){
			var result = $.formValidator.pageIsValid('1001');
			if(!result){
				return;
			}
			$.ajax({
				url : "${_ctxPath}/admin/user/user-savePassword.htm",
				type : "POST",
				data : {
					'seller.user.password' : $('#psw').val(),
					'seller.newPassword' : $('#inputpsw').val(),
					'seller.confirmNewPassword' : $('#repeatpsw').val()
				},
				dataType : "json",
				success : function(data) {
					$.dialog({
	            title: false,
	            content: data.info,
	            cancelValue: "确定",
	            cancel: function(){
								$('#psw').val('');
								$('#inputpsw').val('');
								$('#repeatpsw').val('');
		          }
					});
					$('.d-close').hide();
					//window.location.href='${_ctxPath}/admin/user/user-sellerDetailSeller.htm';
				},
				error : function(data) {
				}
			});
		});
	});
	//验证
	$.formValidator.initConfig({validatorGroup:'1001',formID:"editpsw",theme:"Default",submitOnce:true,onError:function(){}});
	$("#psw").formValidator({validatorGroup:'1001',onShow:"请输入原密码",onFocus:"请输入原密码",onCorrect:"密码合法"}).inputValidator({min:6,max:16,empty:{leftEmpty:false,rightEmpty:false,emptyError:"密码两边不能有空符号"},onError:"密码不能为空,请确认"});
	$("#inputpsw").formValidator({validatorGroup:'1001',onShow:"请输入新密码",onFocus:"请输入新密码",onCorrect:"密码合法"}).inputValidator({min:6,max:16,empty:{leftEmpty:false,rightEmpty:false,emptyError:"密码两边不能有空符号"},onMinError:"请输入6-16位的密码",onMaxError:"请输入6-16位的密码",onError:"密码不能为空,请确认"});
	$("#repeatpsw").formValidator({validatorGroup:'1001',onShow:"请再次输入密码",onFocus:"请再次输入密码",onCorrect:"密码一致"}).inputValidator({min:6,max:16,empty:{leftEmpty:false,rightEmpty:false,emptyError:"重复密码两边不能有空符号"},onError:"重复密码不能为空,请确认"}).compareValidator({desID:"inputpsw",operateor:"=",onError:"2次密码不一致,请确认"});
</script>
</body>
</html>