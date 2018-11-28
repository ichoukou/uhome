<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.SpecialTopicTemplate"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>专题模板-广告查询</title>
    <link href="${ _cssPath}/common.css" rel="stylesheet" />
    <link href="${ _cssPath}/pages/special.css" rel="stylesheet" />
    <link href="${_jsPath }/plugin/artdialog/skins/ytoxl.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <script src="${_jsPath }/plugin/artdialog/jquery.artDialog.min.js"></script>
    <script src="${_jsPath }/plugin/plugin.js"  language="javascript"></script>
    <script src="${_jsPath }/pages/index.js"></script>
    <script src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"  language="javascript"></script>
    <script src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"  language="javascript"></script>
    <script type="text/javascript">
	    $(function(){
			$('#search').bind('click', function() {
				$("#searchForm").attr("action","${_ctxPath}/admin/specialtopic/specialTopic_searchAdv.htm");
				$("#searchForm").submit();
			});
		});
    </script>
</head>
<body>
<!--start header-->
<jsp:include page="../include/header.jsp" flush="true" />
<!--end header-->
<!--start body-->
<div class="body m-w980p">
  <jsp:include page="../include/pageManageMenu.jsp"  flush="true" />
  <form class="m-clear m-mt10p" id="searchForm">
    <div class="m-fl">
    	<input id="specialTopicTempletId" type="hidden" name="advertisementPage.params.specialTopicTempletId" value="${advertisementPage.params.specialTopicTempletId }"/>
    	<input id="templateName" type="hidden" name="advertisementPage.params.templateName" value="${advertisementPage.params.templateName }"/>
    	<input id="templateIsPublish" type="hidden" name="advertisementPage.params.templateIsPublish" value="${advertisementPage.params.templateIsPublish }"/>
    	<select class="m-sel" name="advertisementPage.params.specialTopicAdvPositionId" >
    		<option value="">全部广告位</option>
    		<c:forEach items="${advPositions }" var="advPosition" varStatus="status">
				<option value="${advPosition.specialTopicAdvPositionId }" <c:if test="${advertisementPage.params.specialTopicAdvPositionId==advPosition.specialTopicAdvPositionId }">selected = "selected"</c:if>>
				${advPosition.name}</option>
			</c:forEach>
    	</select>
    	<input type="button" class="m-btn" value="查询" id="search"/>
    	<input type="button" class="m-btn J-editAd" value="新增广告" onclick="popupAdv(0)"/>
    	<input type="button" class="m-btn-done" value="返回" onclick="javascript:window.location.href='${_ctxPath}/admin/specialtopic/specialTopic_searcheSecialTopicTemplate.htm';"/>
    </div>
    <!--右侧显示分页控件 start-->
    <c:if test="${not empty advertisementPage.result}">
		<div class="m-fr curr-num">
			<label>当前显示： </label>
			<wms:commPageSize page="${advertisementPage}" beanName="advertisementPage"></wms:commPageSize>
		</div>
	</c:if>
    <!--右侧显示分页控件 end-->
	</form>
	<!--100%表格样式-->
	<c:if test="${not empty advertisementPage}">
	<div class="m-mt10p">
		<div class="body-nav subnav">
			<ul class="m-clear">
				<li><a>${advertisementPage.params.templateName}</a></li>
			</ul>
		</div>
		<table id="tab" class="tab-control">
      <thead>
        <tr>
          <th width="40%">位置名称</th>
          <th width="40%">预览广告</th>
          <th width="20%">操作</th>
        </tr>
      </thead>
      <tbody>
      	<c:forEach items="${advertisementPage.result }" var="advertisement" varStatus="status">
			<tr class="list-tr">
				<td><span>${advertisement.advPosition.name }</span></td>
				<td><a class="c-blue" href="javascript:previewImg('${_filePath}${advertisement.imageUrl}','${advertisement.advPosition.positionCode}');">预览</a></td>
				<td><a class="c-blue J-editAd" href="javascript:;" onclick="popupAdv('${advertisement.specialTopicAdvId }')">编辑</a></td>
			</tr>
		</c:forEach>
      </tbody>
    </table>
	</div>
	</c:if>
	<!--分页插件 start -->
	<div class="pagination pagination-right">
		<c:if test="${not empty advertisementPage.result}">
			<wms:commPage page="${advertisementPage}" beanName="advertisementPage"></wms:commPage>
		</c:if>
	</div>
	<!--分页插件 end -->
</div>
<!--end body-->
<!--start footer-->
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<!--end footer-->
<script>
//获取预览图片尺寸
var getPreviewImgSize = function (adType){
	var size = "960px*390px";
	if(adType == 'B'){
		size = "960px*390px";
	}else if(adType == 'M'){
		size = "960px*100px";
	}else if(adType == 'S'){
		size = "228px*285px";
	}
	return size;
}
var previewImg = function(imgPath,positionCode){
	var adType = positionCode.split("_")[0];
	var _size = getPreviewImgSize(adType);
	var width = _size.split("*")[0];
	var height = _size.split("*")[1];
	imgView(imgPath, width, height);
}
//广告预览
var imgView=function(src, width, height){
	//alert(222);
	//var suffix = src.split(".")[1];
	//var url =src + "_t5." + suffix;
	if(src!='' && src != null){
		var html = '<img src='+src+' width="'+width+'" height="'+height+'"/>';
		  $.dialog({
		    lock: true,
		    padding: "5px",
		    title:false,
		    content:html,
		    fixed: true,
		    cancel: true,
		    cancelValue: "关  闭"
		  });
		  $(".d-close").hide();
	}
}
//获取广告
function getAdvById(advId){
	var title = "新增广告";
	if(advId){
		title = "编辑广告";
	}
	$.ajax({ 
		type: "post", 
		url: "${_ctxPath}/admin/specialtopic/specialTopic_getAdv.htm", 
		dataType: "html", 
		data:{'specialTopicAdvId':advId,'advertisementPage.params.specialTopicTempletId':${advertisementPage.params.specialTopicTempletId}},
		success: function(data){
			html=data;
			$.dialog({
	 			  lock: true,
	 			  padding: "10px 30px",
	 			  title:title,
	 			  content:html,
	 			  fixed: true,
	 			  cancel: true,
	 			  cancelValue:"取  消",
	 			  ok:function(){
	 				 var result = $.formValidator.pageIsValid('1');
	 				 if(!result){
	 					return false;
	 				 }
	 				$('#saveAdvForm').submit();
	 			  },
	 			  okValue:"提  交"
	 			});
		}
	});
}
//弹出广告
function popupAdv(advId){
	var published = "<%=SpecialTopicTemplate.PUBLISH_YES%>";
	var isPublish = "${advertisementPage.params.templateIsPublish}";
	if(published == isPublish){//已发布专题不可编辑
		var html = "<div>当前专题已发布，不可进行“新增”或“编辑”操作!</div>";
		$.dialog({
		    lock: true,
		    padding: "5px",
		    title:false,
		    content:html,
		    fixed: true,
		    cancel: true,
		    cancelValue: "关  闭"
		  });
		  $(".d-close").hide();
		  return false;
	}
	if(advId > 0){//编辑
		getAdvById(advId);
	}else{
		var templateId = ${advertisementPage.params.specialTopicTempletId};
		 $.ajax({ 
				type: "post", 
				url: "${_ctxPath}/admin/specialtopic/specialTopic_listAvailableAdvPosition.htm", 
				dataType: "json", 
				data:{'specialTopicTemplate.specialTopicTempletId':templateId},
				success: function(data){
					var positions = data.availableAdvPositions;
					if(positions.length ==0){//广告位已满
						html = "<div style='width:260px;height:170px;text-align: center;'><span style='position:relative;top:80px;color:#ff0000'>已无可用广告位!</span></div>";
						$.dialog({
						    lock: true,
						    padding: "5px",
						    title:false,
						    content:html,
						    fixed: true,
						    cancel: true,
						    cancelValue: "关  闭"
						  });
						  $(".d-close").hide(); 
					}else{
						getAdvById(0);
					}
				}
			});
	}
}
</script>
</body>
</html>