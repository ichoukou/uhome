<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.Notice"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%><html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>编辑公告</title>
    <link href="${_cssPath}/common.css" rel="stylesheet" />
    <link href="${_cssPath}/pages/announcement.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <script src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"  language="javascript"></script>
    <script src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"  language="javascript"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/ckeditor/ckeditor.js"></script>
	
	<c:set var="TYPE_REBATE" value="<%=Notice.TYPE_REBATE %>" ></c:set>
    <c:set var="TYPE_NETWORK" value="<%=Notice.TYPE_NETWORK %>"></c:set>
    <c:choose>
    	<c:when test="${empty notice.noticeId }">
    		<c:set var="TITLE" value="新增公告"></c:set>
    	</c:when>
    	<c:otherwise>
    		<c:set var="TITLE" value="修改公告"></c:set>
    	</c:otherwise>
    </c:choose>
    
</head>
<body>
	<!--start header-->
	<jsp:include page="../include/header.jsp" flush="true" />
	<!--end header-->
	<!--start body-->
	<div class="body m-w980p">
	  <jsp:include page="../include/pageManageMenu.jsp"  flush="true" />
	  <div class="title">${TITLE }</div>
	  <form id="form1" method="post" action="${_ctxPath}/admin/notice/notice-saveNotice.htm">
	  <input type="hidden" name="notice.noticeId" value="${notice.noticeId}"/>
	  <table class="tab-a">
	  	<tr>
	  		<td width="50"><span class="c-red">*</span>标题：</td>
	  		<td colspan="2"  width="420">
	  			<input type="text" class="txt-input txt-input-w" id="title" name="notice.title" value="${fn:escapeXml(notice.title)}"/>
	  		</td>
	  		<td><span id="titleTip"></span></td>
	  	</tr>
	  	<tr>
	  		<td><span class="c-red">*</span>类型：</td>
	  		<td width="220">
	  			<select class="m-sel" id="type" name="notice.type">
	  				<option value="">请选择</option>
	    			<option value="${TYPE_REBATE }" <c:if test="${notice.type eq TYPE_REBATE }">selected = "selected"</c:if>><spring:message code="notice.type.${TYPE_REBATE }"/></option>
	    			<option value="${TYPE_NETWORK }" <c:if test="${notice.type eq TYPE_NETWORK }">selected = "selected"</c:if>><spring:message code="notice.type.${TYPE_NETWORK }"/></option>
	  			</select>
	  		</td>
	  		<td colspan="2"><span id="typeTip"></span></td>
	  	</tr>
	  	<tr>
	  		<td><span class="c-red">*</span>来源：</td>
	  		<td><input type="text" class="txt-input" id="source" name="notice.source" value="${fn:escapeXml(notice.source)}"/></td>
	  		<td colspan="2"><span id="sourceTip"></span></td>
	  	</tr>
	  	<tr valign="top">
	  		<td><span class="c-red">*</span>内容：</td>
	  		<td colspan="3">
	  			<!--此处为文本编辑器 start-->
	  			<textarea id="content" name="notice.content">${notice.content }</textarea>
	  			<!--此处为文本编辑器 end-->
	  		</td>
	  	</tr>
	  	<tr>
			<td></td>
			<td colspan="2"><span id="contentTip"></span></td>
			<td></td>
		</tr>
	  	<tr>
	  		<td></td>
	  		<td colspan="3">
	  			<input type="submit" class="m-btn" value="保存" />
	  			<input type="button" class="m-btn" value="取消" onclick="turnSearch();"/>
	  		</td>
	  	</tr>
	  </table>
	  </form>
	</div>
	<!--start footer-->
	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
	<!--end footer-->
</body>
<!--end body-->
<script type="text/javascript">
//ckeditor操作
	CKEDITOR.on('dialogDefinition', function( ev ){
	    var dialogName = ev.data.name;
	    var dialogDefinition = ev.data.definition;
	    if ( dialogName == 'image' ){
	        var infoTab = dialogDefinition.getContents('info');
	        infoTab.add({
	            type : 'button',
	            id : 'upload_image',
	            align : 'center',
	            label : '添加图片',
	            onClick : function( evt ){
	                var thisDialog = this.getDialog();
	                var txtUrlObj = thisDialog.getContentElement('info', 'txtUrl');
	                var txtUrlId = txtUrlObj.getInputElement().$.id;
	                addUploadImage(txtUrlId);
	            }
	
	        },"txtAlt");
	    }
	});
	function addUploadImage(theURLElementId){
	    var uploadUrl = "${_ctxPath}/upload/image-upload.htm";
	    var imgUrl = window.showModalDialog(uploadUrl,'title',
				'resizable=no;help=no;status=no;dialogWidth=400px;dialogHeight=300px'); 
	    var urlObj = document.getElementById(theURLElementId);
	    if(imgUrl){
		    urlObj.value = imgUrl;
		    onchangeFun(urlObj);
	    }
	}
	function onchangeFun(obj){
		if (document.all) {
			obj.fireEvent("onchange");
		} else {
			var evt = document.createEvent('HTMLEvents');
			evt.initEvent('change', true, true);
			obj.dispatchEvent(evt);
		} 
	}
	var editor = CKEDITOR.replace("content");
	//验证
	$.formValidator.initConfig({formID:"form1",theme:"Default",errorFocus:false,wideWord:false,submitOnce:true,onError:function(){}});
	$("#title").formValidator({onShow:"请输入标题",onFocus:"请输入标题"}).inputValidator({min:1,max:50,onError:"请输入1-50个字符的标题"});
	$("#type").formValidator({onShow:"请选公告类型",onFocus:"请选公告类型"}).inputValidator({min:1,onError: "请选公告类型！"});
	$("#source").formValidator({onShow:"请输入来源",onFocus:"请输入来源"}).inputValidator({min:1,max:12,onError:"请输入1-12个字符的来源"});
	$("#content").formValidator({
		tipId : "contentTip",
		onFocus : "请填写公告内容",
		onCorrect : "谢谢你的配合"
	}).functionValidator({
		fun : function() {
			var content = editor.getData();
			if(content.length == 0){
				return "公告内容不能为空";
			}
		}
	});
	//跳转查询页面
	function turnSearch(){
		window.location.href="${_ctxPath}/admin/notice/notice-searchNotice.htm";
	}
</script>
</html>