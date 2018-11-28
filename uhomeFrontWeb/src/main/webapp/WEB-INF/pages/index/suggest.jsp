<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
 <meta charset="utf-8"/>
 <title>建议意见-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath}/pages/suggest.css?d=${_resVerion}" media="all">
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>	
     <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a>建议意见</div>
        <div class="suggest">
        	<div class="suggest-title mf">
        		<b>建议意见</b>
        	</div>
        <form id="addForm">
            <table>
                <tr>
                    <td width="135" align="right">标题：</td>
                    <td><input type="text" class="input-txt" id="title" name="suggest.title"><span id="titleTip" style="margin-left: 10px;"></span></td>
                    <td></td>
                </tr>
                <tr>
                    <td valign="top" align="right">内容：</td>
                    <td><textarea  id="content" name="suggest.content" cols="30" rows="10" class="text-area"></textarea><div style="margin-top: 10px;"><span id="contentTip"></span></div></td>
                	<td></td>
                </tr>
                <tr>
                    <td valign="top" align="right">验证码：</td>
                    <td>
	                    <input type="text" name="jCaptchaResponse" id="captcha" class="input-img" maxlength="4" /> 
	                    <img alt="验证码" src="${_ctxPath}/jcaptcha_image.htm" class="captchaImg" id="captchaImg"/>
	                    <a id="changeCaptcha" style="cursor: pointer;" onclick="changeCaptcha()">看不清换一张</a>
	                    <span id="captchaTip" style="color: red"></span> 
                    </td>
                </tr>
                <tr>
                	<td></td>
                	<td>
                		<input type="hidden" id="Stype" name="suggest.type" value="1"/>
			            <div class="cf"><a class="btn-c" href="javascript:;" id="postComments"><span>提交留言</span></a></div>
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
    <script type="text/javascript" src="${_jsPath}/pages/suggest.js?d=${_resVerion}"></script>
</body>
</html>