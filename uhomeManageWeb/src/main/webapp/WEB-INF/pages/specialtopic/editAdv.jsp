<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<script src="${_jsPath }/plugin/uploadify/swfobject.js"></script>
<script src="${_jsPath }/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<!--新增广告位弹出层 start-->
<div class="newAd">
	<form id="saveAdvForm" name="saveAdvForm" action="${_ctxPath}/admin/specialtopic/specialTopic_saveAdv.htm">
	<input type="hidden" name="advertisement.specialTopicTempletId" value="${advertisementPage.params.specialTopicTempletId }"/>
	<input type="hidden" id="advId" name="advertisement.specialTopicAdvId" value="${advertisement.specialTopicAdvId}"/>
	<table>
		<tr>
			<td class="m-tar"><span class="c-red">*</span>位置：</td>
			<td>
				<select class="m-sel J-adType" id="advPositionSel" name="advertisement.specialTopicAdvPositionId">
					<c:forEach items="${availableAdvPositions }" var="advPosition">
						<option adType="${ fn:substringBefore(advPosition.positionCode, '_')}" value="${advPosition.specialTopicAdvPositionId }" <c:if test="${advPosition.specialTopicAdvPositionId eq advertisement.specialTopicAdvPositionId}">selected="selected"</c:if> >${advPosition.name }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="m-tar"><span class="c-red">*</span>广告图片：</td>
			<td>
				<input type="text" class="txt-input J-imgSrc" id="imageUrl" name="advertisement.imageUrl" value="${advertisement.imageUrl }"/>
				<input type="file" id="imgUpload" name="file" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td><span class="c-red">*</span>请上传 <span class="c-red J-adSize">960px*390px</span> 像素的图片</td>
		</tr>
		<tr>
			<td></td>
			<td><div id="imgTip"></div></td>
		</tr>
		<tr>
			<td class="m-tar">跳转地址：</td>
			<td><input type="text" class="txt-input" id="turnUrl" name="advertisement.turnUrl" value="${advertisement.turnUrl }"/></td>
		</tr>
		<tr class="m-hide J-tr">
			<td><span class="c-red">*</span>商品编码：</td>
			<td>
				<input type="text" id="productIds" name="advertisement.productIds" class="txt-input input-marks" data-default="商品编码以“,”号为区分" value="${advertisement.productIds }"/>
				<input type="hidden" id="productImageUrls" name="advertisement.productImageUrls" value="${advertisement.productImageUrls }"/>
				<input type="hidden" id="productLinkUrls" name="advertisement.productLinkUrls" value="${advertisement.productLinkUrls }"/>
				<input type="button" class="m-btn J-preview" value="预览" />
			</td>
		</tr>
		<tr class="m-hide J-tr">
			<td></td>
			<td>
				<div class="preview">
					<c:if test="${not empty advertisement.productImageUrls }">
						<c:set var="productImageUrls" value="${fn:split(advertisement.productImageUrls,',') }"></c:set>
						<c:forEach items="${productImageUrls}" var="imageUrl" varStatus="status">
							<a href="##"><img src="${_fileThumbPath}${imageUrl }_t666.jpg" width="80" height="80" /></a>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		<tr class="m-hide J-tr">
			<td></td>
			<td><div id="codeTip"></div></td>
		</tr>
	</table>
	</form>
</div>
<!--新增广告位弹出层 start-->
<script>
//验证
$.formValidator.initConfig({formID:"saveAdvForm",theme:"Default",validatorGroup: '1',errorFocus:false,submitOnce:true,onError:function(){}});
$("#imageUrl").formValidator({validatorGroup: '1',tipID:"imgTip",onShow: "请上传图片",onFocus:"请上传图片", onCorrect: "谢谢你的合作，你的图片名正确"}).inputValidator({min:1,onErrorMin:"请上传图片"}).regexValidator({ regExp: "picture", dataType: "enum", onError: "图片名格式不正确" });
$("#productIds").formValidator({validatorGroup: '1',tipID:"codeTip",onFocus:"请填写商品编码"}).inputValidator({min:1,onErrorMin:"请填写商品编码"}).functionValidator({
	fun:function(val){//商品编码验证
		var reg = /^(\d+)(,\d+)*$/,
			aIds = val.split(","),
			nary = aIds.sort(),
			flag =false;
		if(!reg.test(val)){//判断是否为数字
			return '商品编码错误';
		}else if(aIds.length%4){//判断长度是否为4的倍数
			return '请输入4的倍数的编码';
		}else{//判断是否重复
			for(var i = 0; i < nary.length - 1; i++){
		    if (nary[i] == nary[i+1]){
		      return '商品重复';
		    }
			}
		}
	}
});
$("#productImageUrls").formValidator({validatorGroup: '1',tipID:"codeTip",onShow: "请先预览商品图片",onFocus:"请先预览商品图片", onCorrect: ""}).inputValidator({min:1,onErrorMin:"请先预览商品图片"});
var imgLimitSize = 2*1024*1024; //图片最大限制，单位：byte
//广告尺寸修改
var changeImgSize = function(adType){
	var size = getImgSize(adType);
	$(".J-adSize").text(size);
}
//获取图片尺寸
var getImgSize = function (adType){
	var size = "960px*390px";
	if(adType == 'B'){
		size = "960px*390px";
		$(".J-tr").hide();
		$("#productIds,#productImageUrls").attr("disabled",true).unFormValidator(true); 
	}else if(adType == 'M'){
		size = "960px*100px";
		$(".J-tr").show();
		$("#productIds,#productImageUrls").attr("disabled",false).unFormValidator(false); 
	}else if(adType == 'S'){
		size = "228px*285px";
		$(".J-tr").hide();
		$("#productIds,#productImageUrls").attr("disabled",true).unFormValidator(true); 
	}
	return size;
}
$(function(){
	var _adType = $(this).find("option:selected").attr("adType");
	changeImgSize(_adType);
	//广告尺寸修改
	$(".J-adType").on("change",function(){
		var adType = $(this).find("option:selected").attr("adType");
		changeImgSize(adType);
	});
	//上传图片
	var uploadDefaultParams = {
		'auto' : true,
		'buttonImg' : '${_jsPath}/plugin/uploadify/uploadimg_btn.png',
		'cancelImg' : '${_jsPath}/plugin/uploadify/cancel.png',
		'expressInstall' : '${_jsPath}/plugin/uploadify/expressInstall.swf',
		'fileDataName'   : 'file',
		'fileDesc' : '请选择jpg、gif、png文件',
		'fileExt' : '*.jpg;*.jpeg;*.gif;*.png',
	    'height' : 24,
	    'multi' : false,
	    'script' : '${_ctxPath}/upload/upload.htm',
	    'uploader' : '${_jsPath}/plugin/uploadify/uploadify.allglyphs.swf',
	    'width' : 100,
	    'sizeLimit' : imgLimitSize,
	    'scriptData' : {'category':'topicAdv'}
	};
    var uploadLogoImgParams = uploadDefaultParams;
    uploadLogoImgParams.onComplete = function(event, ID, fileObj, response, data){
		$('#imageUrl').val(response);
		$('.J-imgSrc').val(response);
   };
   $('#imgUpload').uploadify(uploadLogoImgParams);
   //“预览”按钮绑定事件
   $(".J-preview").on("click",function(){
	   var ids = $("#productIds").val();
	   $.ajax({ 
			type: "post", 
			url: "${_ctxPath}/admin/specialtopic/specialTopic_listProductImgUrls.htm", 
			dataType: "json", 
			data:{'advertisement.productIds':ids},
			success: function(data){
				//var advProducts = data.advProducts;
				var _pids = ids.split(",");
				var _innerHtml = ""; //商品预览内容
				var _productLinkUrls = ""; //商品链接URLs
				var _productImageUrls = ""; //商品图片URLs
				var _defaultImage = "/error.jpg"; //若商品不存在，使用默认图片
				for(var i=0;i<_pids.length;i++){
					var separator = ",";
					var isExist = false; //是否存在
					for(var j=0;j<data.advProducts.length;j++){
						if(_pids[i]==data.advProducts[j].productId){ //商品存在
							isExist=true;
							_innerHtml +='<a href="##"><img src="${_fileThumbPath}'+data.advProducts[j].previewImage+'_t666.jpg" width="80" height="80" /></a>';
							if(_productLinkUrls){//已存在，需加分隔符
								_productLinkUrls += separator+"/item-"+data.advProducts[j].productId+".htm";
								_productImageUrls += separator+data.advProducts[j].previewImage;
							}else{
								_productLinkUrls += "/item-"+data.advProducts[j].productId+".htm";
								_productImageUrls += data.advProducts[j].previewImage;
							}
							continue;
						}
					}
					if(!isExist){//商品不存在
						_innerHtml +='<a href="##"><img src="${_fileThumbPath}'+_defaultImage+'_t666.jpg" width="80" height="80" /></a>';
						if(_productLinkUrls){ //已存在，需加分隔符
							_productLinkUrls += separator+"/item-"+_pids[i]+".htm";
							_productImageUrls += separator+_defaultImage;
						}else{
							_productLinkUrls += "/item-"+_pids[i]+".htm";
							_productImageUrls += _defaultImage;
						}
					}
				}
				
				$(".preview").html(_innerHtml);
				//showAdvDialog(html,hasAdvPositoin,title);
				$("#productImageUrls").val(_productImageUrls);
				$("#productLinkUrls").val(_productLinkUrls);
			}
		});
   });
   //商品编码输入框绑定事件
   $("#productIds").on("change",function(){
	   $("#productImageUrls").val("");
	   $("#productLinkUrls").val("");
   });
});

</script>