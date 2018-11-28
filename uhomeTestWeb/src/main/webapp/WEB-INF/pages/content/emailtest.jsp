<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>邮件测试</title>
<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
 <script type="text/javascript">
$(function(){
	$(".brand-query").click(function(){
		var ss= $("#email").val();
		var exp=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
         if(!exp.test(ss)){
        	 alert("邮箱格式不正确");
        	 return ;
         }
		var value="email=" + $("#email").val();
		$.each($(":checkbox:checked"),function(i,obj){
			value += "&typelist["+i+"]=" + $(obj).val();
		}); 
		$.ajax({   
			   url:"${_ctxPath}/test/testemail-send.htm",   
			   type:"POST",
			   data:value,
			   success:function(data){   
				  alert(data.info);
			   },
			   error:function(xhr){
				  alert(xhr.responseText)
			   }
		  });
	  });
});
</script>
</head>
<body>
	<div>
	  <p style="margin-top: 50px;"> 
	        <c:forEach items="${types}" var="type">
	             <input style="margin-left: 20px;" type="checkbox" name="type" value="${type}"/> <label><small><spring:message code="mailSubscribe.type.${type}"/></small></label>
	        </c:forEach>
	        <input type="text" id="email" name="email" style="margin-left: 20px;" />
	        <input type="button" class="brand-query" value="发&nbsp;送" style="margin-left: 20px; width: 50px;" />
	  </p>
	</div>
</body>
</html>