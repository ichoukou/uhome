<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>修改密码</title>
<link href="${_cssPath}/common.css" rel="stylesheet" />
<link href="${_cssPath}/pages/arrangement.css" rel="stylesheet" />
<link href="${_jsPath }/plugin/artdialog/skins/ytoxl.css" rel="stylesheet" />
<script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/artdialog/jquery.artDialog.min.js"></script>
<script src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js" language="javascript"></script>
<script src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js" language="javascript"></script>
</head>
<body>
<jsp:include page="../include/header.jsp"  flush="true" />
<div class="body m-w980p">
		<div class="body-nav m-mt10p">
			<ul class="m-clear">
				<li><a href="${_ctxPath}/admin/user/user-sellerDetailSeller.htm">帐号信息</a></li>
				<li><a class="current-chose" href="${_ctxPath}/admin/user/user-getPasswordSell.htm">修改密码</a></li>
			</ul>
		</div>
		<div class="account-border">
			<form id="editpsw">
			<table>
				<tr>
					<td width="135" align="right">输入当前密码</td>
					<td width="210"><input type="password" class="password-text" id="password"/></td>
					<td><div id="passwordTip"></div></td>
				</tr>
				<tr>
					<td width="135" align="right">输入新密码</td>
					<td><input type="password" class="password-text" id="newPassword"/></td>
					<td><div id="newPasswordTip"></div></td>
				</tr>
				<tr>
					<td width="135" align="right">重复输入新密码</td>
					<td><input type="password" class="password-text" id="confirmPassword"/></td>
					<td><div id="confirmPasswordTip"></div></td>
				</tr>
				<tr >
					<td width="135" align="right" >
						
					</td>
					<td><input type="button" value="保存" id="save" class="m-btn"/></td>
				</tr>
			</table>
			</form>
		</div>
</div>
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
					'seller.user.password' : $('#password').val(),
					'seller.newPassword' : $('#newPassword').val(),
					'seller.confirmNewPassword' : $('#confirmPassword').val()
				},
				dataType : "json",
				success : function(msg) {
					$.dialog({
            title: false,
            content: msg.info,
            cancelValue: "确定",
            cancel: function(){
							$('#password').val('');
							$('#newPassword').val('');
							$('#confirmPassword').val('');
	          }
					});
					$('.d-close').hide();
				},
				error : function(msg) {
					console.log(msg.info);
				}
			});
		});
	});
	$(document).ready(function(){
		//验证
		$.formValidator.initConfig({validatorGroup:'1001',formID:"editpsw",theme:"Default",onError:function(){}});
		$("#newPassword").formValidator({validatorGroup:'1001',onShow:"请输入新密码",onFocus:"请输入新密码",onCorrect:"密码合法"}).inputValidator({min:6,max:16,empty:{leftEmpty:false,rightEmpty:false,emptyError:"密码两边不能有空符号"},onErrorMin:"请输入6-16位的密码",onErrorMax:"请输入6-16位的密码",onError:"密码不能为空,请确认"});
		$("#confirmPassword").formValidator({validatorGroup:'1001',onShow:"请再次输入密码",onFocus:"请再次输入密码",onCorrect:"密码一致"}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"重复密码两边不能有空符号"},onError:"重复密码不能为空,请确认"}).compareValidator({desID:"newPassword",operateor:"=",onError:"2次密码不一致,请确认"});
		
		$("#password").formValidator({
			validatorGroup : '1001',
			onShow : "请输入原密码",
			onFocus : "请输入原密码",
			onCorrect : "密码合法"
		}).inputValidator({
			min : 6,
			max : 16,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "密码两边不能有空符号"
			},
			onError : "密码不能为空,请确认"
		}).ajaxValidator({
			type : "GET",
			url : "${_ctxPath}/admin/user/user-validPassword.htm",
			data : {
				"seller.user.password" : function(){return $("#password").val();}
			},
			datatype : "json",
			async : "true",
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.info == "false") {
					return false;
				}
				return true;
			},
			buttons: $("#save"),
			error : function(jqXHR, textStatus, errorThrown) {
				alert("服务器没有返回数据，可能服务器忙，请重试" + errorThrown);
			},
			onError : "密码错误",
			onWait : "正在对密码进行校验，请稍候..."
		});
	});
</script>
</body>
</html>