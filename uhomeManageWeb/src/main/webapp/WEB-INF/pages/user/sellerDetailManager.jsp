<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>商家详细信息</title>
<link href="${_cssPath}/common.css" rel="stylesheet" />
<link href="${_cssPath}/pages/arrangement.css" rel="stylesheet" />
<script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
</head>
<body>
<jsp:include page="../include/header.jsp"  flush="true" />
<div class="body m-w980p">
		<div class="body-nav m-mt10p">
			<ul class="m-clear">
				<li><a class="current-chose" href="${_ctxPath}/admin/user/user-sellerDetailSeller.htm">帐号信息</a></li>
				<li><a href="${_ctxPath}/admin/user/user-getPasswordSell.htm">修改密码</a></li>
			</ul>
		</div>
		<div class="account-border">
			<table>
				<tr>
					<td width="135" align="right">登录名：</td>
					<td>${seller.user.username}</td>
				</tr>
				<tr>
					<td width="135" align="right">联系人姓名：</td>
					<td>${seller.contactName}</td>
				</tr>
				<tr>
					<td width="135" align="right">联系人电话1：</td>
					<td>${seller.mobile}</td>
				</tr>
				<tr>
					<td width="135" align="right">联系人电话2：</td>
					<td>${seller.user.tel}</td>
				</tr>
				<tr>
					<td width="135" align="right">邮箱：</td>
					<td>${seller.user.email}</td>
				</tr>
				<tr>
					<td width="135" align="right">公司名称：</td>
					<td>${seller.companyName}</td>
				</tr>
				<tr>
					<td width="135" align="right">发货地址：</td>
					<td>${seller.shiperAddress}</td>
				</tr>
				<tr>
					<td width="135" align="right">退货地址：</td>
					<td>${seller.receiverAddress}</td>
				</tr>
				<tr>
					<td width="135" align="right">可售品牌：</td>
					<td>${seller.brandNames}</td>
				</tr>
				<tr>
					<td width="135" align="right">支付宝帐号：</td>
					<td>${seller.alipayNo}</td>
				</tr>
			</table>
		</div>
	</div>
	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
</html>