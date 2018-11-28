<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
 <meta charset="utf-8"/>
 <title>品牌合作-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath}/pages/suggest.css?d=${_resVerion}" media="all">
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>	
     <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a>品牌合作</div>
        <div class="suggest">
        	<div class="suggest-title mf">
        		<b>品牌合作</b>
        	</div>
        <form id="addForm">
            <table width="100%">
                <tr>
                    <td width="135" align="right"><i>*</i>品牌名称：</td>
                    <td width="300"><input type="text" class="input-txt" name="supplier.brandName" id="brandName"></td>
                    <td width="525"><span id="brandNameTip"></span></td>
                </tr>
                <tr>
                    <td width="135" align="right">公司名称：</td>
                    <td colspan="2"><input type="text" class="input-txt" name="supplier.companyName"></td>
                </tr>
                <tr>
                    <td width="135" align="right"><i>*</i>您的姓名：</td>
                    <td><input type="text" class="input-txt" name="supplier.contactName" id="contactName"></td>
                    <td><span id="contactNameTip"></span></td>
                </tr>
                <tr>
                    <td width="135" align="right"><i>*</i>联系电话：</td>
                    <td><input type="text" class="input-txt" name="supplier.telephone" id="telephone"></td>
                    <td><span id="telephoneTip"></span></td>
                </tr>
                <tr>
                    <td width="135" align="right">商家性质：</td>
                    <td colspan="2">
                    	<c:forEach items="${types }" var="obj">
						<input type="radio" class="input-radio" name="supplier.type" value="${obj }"/>
						<label><spring:message code="supplier.type.${obj}"/></label>
					</c:forEach>
                    </td>
                </tr>
                <tr>
                    <td valign="top" align="right">品牌说明：</td>
                    <td colspan="2"><textarea name="supplier.describe" id="Scontent" cols="30" rows="10" class="text-area"></textarea></td>
                </tr>
                 <tr>
                    <td valign="top" align="right">验证码：</td>
                    <td colspan="2">
	                    <input type="text" name="jCaptchaResponse" id="captcha" class="input-img" maxlength="4" /> 
	                    <img alt="验证码" src="${_ctxPath}/jcaptcha_image.htm" class="captchaImg" id="captchaImg"/>
	                    <a id="changeCaptcha" style="cursor: pointer;" onclick="changeCaptcha()">看不清换一张</a>
	                    <span id="captchaTip" style="color: red"></span> 
                    </td>
                </tr>
                <tr>
                	<td></td>
                	<td colspan="2">
			            <div class="cf"><a class="btn-c" href="javascript:;" id="postComments"><span>确认提交</span></a></div>
                	</td>
                </tr>
            </table>
        </form>    
        </div>
    </div>
    <!--footer.jsp放置在这-->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
   	<script type="text/javascript">
		var changeCaptcha=function(){
			$('#captchaImg').attr('src','${_ctxPath}/jcaptcha_image.htm?date='+new Date());
		}
	</script>
    <script type="text/javascript" src="${_jsPath}/pages/editSupplier.js?d=${_resVerion}"></script>
</body>
</html>
