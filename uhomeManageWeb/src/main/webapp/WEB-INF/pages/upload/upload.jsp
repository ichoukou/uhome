<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${_jsPath}/jquery/jquery.1.7.2.js"></script>  
<script type="text/javascript" src="${_jsPath}/plugin/uploadify/swfobject.js"></script>  
<script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<title>上传图片</title>
</head>
<body>
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
	 var uploadImgParams = uploadDefaultParams;
	 uploadImgParams.scriptData  = {'category':'productDescribe'};	
	 uploadImgParams.onComplete = function(event, ID, fileObj, response, data){
			window.returnValue = '${_filePath}' + response;
			window.close();
     };
	 $('#imgUpload').uploadify(uploadImgParams);
});
</script>
<input type="file" id="imgUpload" name="file"/>
</body>
</html>