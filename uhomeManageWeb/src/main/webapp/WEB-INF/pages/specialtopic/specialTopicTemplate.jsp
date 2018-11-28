<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ _cssPath}/common.css" rel="stylesheet" />
<link href="${ _cssPath}/pages/special.css" rel="stylesheet" />
<link href="${ _jsPath}/plugin/artdialog/skins/ytoxl.css" rel="stylesheet" />
<link href="${_jsPath }/plugin/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" />
<script src="${ _jsPath}/jquery/jquery.1.8.1.js"  language="javascript"></script>
<script src="${ _jsPath}/plugin/plugin.js"  language="javascript"></script>
<script src="${ _jsPath}/pages/index.js"></script>
<script src="${ _jsPath}/plugin/artdialog/artDialog.plugins.min.js"></script>
<script src="${ _jsPath}/plugin/artdialog/jquery.artDialog.min.js"></script>
<script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
<script src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js" language="javascript"></script>
<script src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js" language="javascript"></script>
<title>专题模板</title>
</head>
<body>
	<jsp:include page="../include/header.jsp" flush="true" />
	<div class="body m-w980p m-mt10p">
	<!-- reportMenu -->
	<jsp:include page="../include/pageManageMenu.jsp"  flush="true" />
	  <form class="m-clear m-mt10p">
		<div class="m-fl">
		    <input type="button" class="m-btn J-addSpecial" value="新增专题" />
		</div>
 		<!--右侧显示分页控件 start-->
	    <c:if test="${not empty specialTopicPage.result}">
			<div class="m-fr curr-num">
				<label>当前显示： </label> 
		          <wms:commPageSize page="${specialTopicPage}" beanName="specialTopicPage"
		          paramName="reportMenuId" paramValue="${reportMenuId}" ></wms:commPageSize>
				</div>
	     </c:if>
	</form>
		<div class="m-mt10p">
			<c:if test="${specialTopicPage !=null}">
				<table class="tab-control">
			      <thead>
			        <tr>
			          <th width="11%">专题名称</th>
			          <th width="11%">专题时间</th>
			          <th width="11%">状态</th>
			          <th width="17%">操作</th>
			        </tr>
			      </thead>
			      <tbody>
			       <c:forEach items="${specialTopicPage.result }" var="specialtopic">
				   	<tr class="list-tr">
				      <td>${specialtopic.name }</td>
				      <td><fmt:formatDate value='${specialtopic.startTime }' pattern='yyyy-MM-dd'/>~<fmt:formatDate value='${specialtopic.endTime }' pattern='yyyy-MM-dd'/></td>
				      <td>
				      	<c:choose>
							<c:when test="${specialtopic.isPublish == 1 }">
				      			未发布
					      	</c:when>
					      	<c:otherwise>
					      		已发布
					      	</c:otherwise>				      	
				      	</c:choose>
				      </td>
				      <td>
				      	<a class="c-blue" href="#" onclick="update('${specialtopic.specialTopicTempletId }','${specialtopic.name }','<fmt:formatDate value='${specialtopic.startTime }' pattern='yyyy-MM-dd'/>','<fmt:formatDate value='${specialtopic.endTime }' pattern='yyyy-MM-dd'/>')">编辑</a>
				      	<a class="c-blue" href="${_ctxPath}/admin/specialtopic/specialTopic_searchAdv.htm?advertisementPage.params.specialTopicTempletId=${specialtopic.specialTopicTempletId}">查看广告位</a>
				      	<a class="c-blue" href="${_frontwebPath }/zt-${specialtopic.specialTopicTempletId}.htm" target="_blank">预览</a>
				      	<c:choose>
							<c:when test="${specialtopic.isPublish == 1 }">
				      			<a class="c-blue" href="#" onclick="updateIsPublish('${specialtopic.specialTopicTempletId }','${specialtopic.name }','${specialtopic.isPublish}')">发布</a>
				      		</c:when>
					      	<c:otherwise>
					    		<a class="c-blue" href="#" onclick="updateIsPublish('${specialtopic.specialTopicTempletId }','${specialtopic.name }','${specialtopic.isPublish}')">撤销</a>
					      	</c:otherwise>				      	
				      	</c:choose>
				      </td>
				    </tr>
			      	</c:forEach>
			      </tbody>
		    	</table>
		    </c:if>	
		</div>
		 <div class="pagination pagination-right">
			<c:if test="${not empty specialTopicPage.result}">
				<wms:commPage page="${specialTopicPage}" beanName="specialTopicPage"
				paramName="reportMenuId" paramValue="${reportMenuId}" ></wms:commPage>
			</c:if>
		</div>
		<div class="addSpecial m-hide">
			<form name="specialTopicForm" id="Special" method="post" onsubmit="false">
				<p>
					<span class="c-red">*</span><span>专题名称：</span>
					<input type="text" id="specialTopicTemplateName" name="specialTopicTemplate.name" class="txt-input" />
					<input type="hidden" id="isPublish" name="specialTopicTemplate.isPublish" />
					<input type="hidden" id="specialTopicTemplateId" name="specialTopicTemplate.specialTopicTempletId" />
					<span id="SpecialTip"></span>
				</p>
				<p>
					<span class="c-red">*</span><span>专题时间：</span>
					<input type="text" id="startTime" class="Wdate" onfocus="WdatePicker({minDate:'%y-%M-{%d+1}',onpicked:function(){endTime.focus();}})"   name="specialTopicTemplate.startTime"/> 至
					<input type="text" id="endTime" class="Wdate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"  name="specialTopicTemplate.endTime"/>
					<span id="timeTip"></span>
				</p>
			</form>
		</div>
	</div>
	<script> 
$(function(){
	//添加专题
	$('.J-addSpecial').on("click",function(){
		var html = $(".addSpecial").get(0);
	  	$('#specialTopicTemplateId').val('');
		$('#specialTopicTemplateName').val('');
		$('#startTime').val('');
		$('#endTime').val('');
		$('#isPublish').val('');
	  show(html,'','新增专题活动');
	});
	//验证
	$.formValidator.initConfig({formID:"Special",theme:"Default",validatorGroup: '1',errorFocus:false,submitOnce:true,wideWord:false,onError:function(){}});
	$("#specialTopicTemplateName").formValidator({validatorGroup: '1',tipID:"SpecialTip",onShow:"请输入专题名称"}).inputValidator({min:1,max:30,onErrorMax:"请输入小于30个字符",onError:"请输入专题名称"});
	$("#startTime,").formValidator({validatorGroup: '1',tipID:"timeTip",onFocus:"请选专题开始日期"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
	$("#endTime,").formValidator({validatorGroup: '1',tipID:"timeTip",onFocus:"请选专题结束日期"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
})
function update(id,name,startTime,endTime){
	$('#specialTopicTemplateId').val(id);
	$('#specialTopicTemplateName').val(name);
	$('#startTime').val(startTime);
	$('#endTime').val(endTime);
	 var html = $(".addSpecial").get(0);
	show(html,'update','修改专题活动');
}
function updateIsPublish(id,name,state){
	var tishi = "";
	var ispublish = 2;
	if(state == '1'){
		tishi = "确定要发布【"+name+"】专题吗？";
	}else{
		tishi = "确定要撤销发布【"+name+"】专题吗？";
		ispublish = 1;
	}
	if(confirm(tishi)){
		$('#specialTopicTemplateId').val(id);
		$('#isPublish').val(ispublish);
		document.specialTopicForm.action = "${_ctxPath }/admin/specialtopic/specialTopic_updateSpecialTopicTemplate.htm";
		document.specialTopicForm.submit();
	}
}
function show(html,mark,title){
	$.dialog({
	    lock: true,
	    padding: "10px 30px",
	    title:title,
	    content:html,
	    fixed: true,
	    cancel: function(){
	    	$("#timeTip,#SpecialTip").empty().removeClass();
	    	$(".Wdate").val("");
	    },
	    cancelValue:"取  消",
	    ok:function(){
	    	var result = $.formValidator.pageIsValid('1');
	    	if(result){
		    	if(mark == 'update'){
		    		document.specialTopicForm.action = "${_ctxPath }/admin/specialtopic/specialTopic_updateSpecialTopicTemplate.htm";
		    	}else{
		    		document.specialTopicForm.action = "${_ctxPath }/admin/specialtopic/specialTopic_addSpecialTopicTemplate.htm";
		    	}
		    	document.specialTopicForm.submit();
		    }else{
		    	return false;
		    }
	    },
	    okValue:"提  交"
	  });
}
</script>
	
</body>
</html>