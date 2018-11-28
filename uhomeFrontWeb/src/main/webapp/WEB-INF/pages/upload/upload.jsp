<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${_jsPath}/jquery/jquery-1.7.2.js"></script>  
<script type="text/javascript" src="${_jsPath}/plugin/uploadify/swfobject.js"></script>  
<script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<title>upload image</title>
</head>
<body>
<form id="resource_form" class="form" action="${_ctxPath}/upload/upload.htm" enctype="multipart/form-data" method="post">
	上传图片:<input type="file" id="imgUpload" name="file"/><br/><br/>
	<input type="hidden" id="uploadImageValueId" name="uploadImageValueName" readonly="readonly"/>
	<img alt="" src="" id="showImg">
	<a href="#" id="deleteBtn">删除</a>
	<a href="#" id="submitBtn">保存</a>
	
</form>

<script type="text/javascript">
$(document).ready(function(){	
	var uploadDefaultParams = {
			'auto' : true,
			'buttonImg' : '${_jsPath}/plugin/uploadify/uploadimg_btn.png',
			'cancelImg' : '${_jsPath}/plugin/uploadify/cancel.png',
			'expressInstall' : '${_jsPath}/plugin/uploadify/expressInstall.swf',
			'fileDataName'   : 'file', 
			'fileDesc' : '请选择jpg、gif、png文件',
			'fileExt' : '*.jpg;*.jpeg;*.gif;*.png',
		    'height' : 20,
		    'multi' : false,
		    'script' : '${_ctxPath}/upload/upload.htm',
		    'sizeLimit' : 2097152,    
		    'uploader' : '${_jsPath}/plugin/uploadify/uploadify.allglyphs.swf',
		    'width' : 94
	};
	
	 var uploadlogoParams = uploadDefaultParams;
	 uploadlogoParams.scriptData  = {'category':'uhome-test'};   //目录名字 可以是多级  logo/2013/02/26
	 	
	 uploadlogoParams.onComplete = function(event, ID, fileObj, response, data){
			//var fileUri = $.parseJSON(response).fileUri;
			$('#uploadImageValueId').val(response);
			alert('${_fileThumbPath}'+response);
			$('#showImg').attr('src', '${_filePath}'+response);
			//$('#showImg').attr('src', '${_filePath}${_fileThumbTmpPath}'+response+'_t2.jpg');
			$('#showImg').show();
     };
	 $('#imgUpload').uploadify(uploadlogoParams);
	 
	 $('#deleteBtn').attr('href','');
});
</script>
</body>
</html>