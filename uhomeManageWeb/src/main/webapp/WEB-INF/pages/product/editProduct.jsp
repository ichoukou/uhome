﻿<!DOCTYPE html>
<%@page language="java" import="com.ytoxl.module.uhome.uhomebase.dataobject.Product" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh">
<head>
<meta charset="utf-8" />
<title>编辑商品</title>
	<link href="${_cssPath }/common.css" rel="stylesheet" />
  <link href="${_cssPath }/pages/addProduct.css" rel="stylesheet" />
  <script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
  <script type="text/javascript" src="${_jsPath}/plugin/uploadify/swfobject.js"></script>  
	<script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"></script>
  <script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
<body>
<!--start header-->
<jsp:include page="../include/header.jsp"></jsp:include> 
<!--end header-->
<!--start body-->
<div class="body m-w980p">
	<form id="addForm" method="post" target="_blank">
		<input type="hidden" name="product.productId" value="${product.productId }" id="productId"/>
		<input type="hidden" name="product.rebate" value="${product.rebate }" id="rebate"/>
		<input type="hidden" name="product.status" id="status"/>
		<input type="hidden" name="product.imageUrls" id="imageUrls"/>
		<input type="hidden" name="product.keyWord" id="keyWord"/>
		<table border="0" class="addPro-table">
			<tr>
				<th width="145" class="left-td">商品属性</th>
				<th width="130">&nbsp;</th>
				<th width="160">&nbsp;</th>
				<th width="543">&nbsp;</th>
			</tr>
			<tr>
				<td class="left-td">*品牌：</td>
				<td>
					<select class="m-sel" name="product.brandId" id="brandId">
						<option>请选择品牌</option>
						<c:forEach items="${sellerBrands }" var="brand">
							<option value="${brand.brandId }"
							<c:if test="${brand.brandId == product.brandId }">selected="selected"</c:if>
							>${brand.name }</option>
						</c:forEach>
					</select>
				</td>
				<td colspan="2"><span id="brandIdTip"></span></td>
			</tr>
			<tr>
				<td class="left-td">*商品品类：</td>
				<td>
					<select class="m-sel" name="product.productCategoryId" id="productCategoryId">
						<option>请选择品类</option>
						<c:forEach items="${childProductCategories }" var="productCategory">
						<option value="${productCategory.productCategoryId }"
							<c:if test="${productCategory.productCategoryId==product.productCategoryId }">
							selected = "selected"
							</c:if>
						>${productCategory.name}</option>
						</c:forEach>
					</select>
				</td>
				<td colspan="2"><span id="productCategoryIdTip"></span></td>
			</tr>
			<tr>
				<td class="left-td">*商品名称：</td>
				<td colspan="2"><input type="text" class="pro-name" name="product.name" value="${product.name }" id="name"/></td>
				<td><span id="nameTip"></span></td>
			</tr>
			<tr>
				<td class="left-td">*商品原价：</td>
				<td><input type="text" class="text-name" name="product.marketPrice" value="${product.marketPrice }" id="marketPrice"/></td>
				<td colspan="2"><span id="marketPriceTip"></span></td>
			</tr>
			<tr>
				<td class="left-td">*商品邮费：</td>
				<td colspan="2">
					<c:forEach items="${postageOptions }" var="option" varStatus="status">
					<input type="radio" class="input-radio" name="product.postage.option" 
						<c:if test="${product.postage.option == option || product.postage.option ==null && option == defaultPostageOption}">checked="checked"</c:if>
					value="${option }"/>
					<label><small><spring:message code="postage.option.${status.index}"/></small></label>
					</c:forEach>
				</td>
				<td><span id="postageTip"></span></td>
			</tr>
			<tr>
				<td class="left-td">特卖价：</td>
				<td><input type="text" class="text-name" name="product.salePrice" value="${product.salePrice }" id="salePrice"/></td>
				<td><small>折扣：<span class="rebate">${product.rebate }</span>折</small></td>
				<td><span id="salePriceTip"></span>
<!-- 				<small>温馨提示：<br />填写特卖价后可参与特卖活动，填写秒杀价后商品<br/>可参与秒杀活动：请直接填写一条价格信息！</small> -->
				</td>
			</tr>
			<tr>
				<td class="left-td">秒杀价：</td>
				<td><input type="text" class="text-name" name="product.secKillPrice" value="${product.secKillPrice }" id="secKillPrice"/></td>
				<td><small>折扣：<span class="rebate"></span>折</small></td>
				<td><span id="secKillPriceTip"></span></td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3">
					<span class="small">温馨提示：填写特卖价后可参与特卖活动，填写秒杀价后商品可参与秒杀活动：请直接填写一条价格信息！</span>
				</td>
			</tr>
			<tr <c:if test="${empty product.secKillPrice}">style="display:none;"</c:if>>
				<td></td>
				<td colspan="3">
				<input type="text" class="text-tip" value="${product.secKillDescribe }" name="product.secKillDescribe" id="secKillDescribe"/>
				<span id="secKillDescribeTip"></span>
				</td>
			</tr>
			<tr>
				<td class="left-td">可售日期：</td>
				<td colspan="3">
				<c:choose>
				<c:when test="${product == null || product!=null&&product.maxSellStartTime==null&&product.minSellEndTime==null}">
					<input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'%y-%M-{%d+1}',onpicked:function(){sellEndTime.focus();}})" 
						name="product.sellStartTime" value="<fmt:formatDate value='${product.sellStartTime }' pattern='yyyy-MM-dd'/>" 
						id="sellStartTime"/> 至  
					<input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'sellStartTime\',{d:1});}'})" 
						name="product.sellEndTime" value="<fmt:formatDate value='${product.sellEndTime }' pattern='yyyy-MM-dd'/>" 
						id="sellEndTime"/>
				</c:when>
				<c:otherwise>
					<fmt:formatDate value='${product.maxSellStartTime }' pattern='yyyy-MM-dd' var="maxSellStartTime"/>
					<fmt:formatDate value='${product.minSellEndTime }' pattern='yyyy-MM-dd' var="minSellEndTime"/>
					<input type="text" class="Wdate" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'${maxSellStartTime}',onpicked:function(){sellEndTime.focus();}})" 
						name="product.sellStartTime" value="<fmt:formatDate value='${product.sellStartTime }' pattern='yyyy-MM-dd'/>" 
						id="sellStartTime"/> 至  
					<input type="text" class="Wdate" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'${minSellEndTime}'})" 
						name="product.sellEndTime" value="<fmt:formatDate value='${product.sellEndTime }' pattern='yyyy-MM-dd'/>" 
						id="sellEndTime"/>
				</c:otherwise>
				</c:choose>
				</td>
				<td><span id="dateTip"></span></td>
			</tr>
			<tr>
				<td class="left-td">*是否为进口商品：</td>
				<td colspan="2">
					<c:forEach items="${isImports }" var="isImport" varStatus="status">
					<input type="radio" class="input-radio" name="product.isImport" 
						<c:if test="${product.isImport == isImport || product.isImport ==null && isImport == isImportNo}">checked="checked"</c:if>
					value="${isImport }"/>
					<label><small><spring:message code="product.isImport.${status.index}"/></small></label>
					</c:forEach>
				</td>
				<td><span id="isImportTip"></span></td>
			</tr>
			<tr valign="top" class="small">
				<td class="left-td">${skuOptions[0].skuOptionName }：</td>
				<td colspan="3" id="color">
					<div class="c-select">
					<dl>
					<c:forEach items="${skuOptions[0].skuOptionValues }" var="obj">
					<dd><input type="checkbox" class="input-checkbox" value="${obj.skuOptionValueId }"/><span class="label-sel">${obj.skuOptionValue }</span></dd>
					</c:forEach>
					<div class="clear"></div>
					</dl>
					</div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3">
					<span class="small">温馨提示：勾选颜色框，可对颜色进行编辑，请填写1-4位汉字组成的商品颜色</span>
				</td>
			</tr>
			<tr valign="top" class="small">
				<td class="left-td">${skuOptions[1].skuOptionName }：</td>
				<td colspan="3" class="c-select" id="size">
					<c:forEach items="${skuOptions[1].skuOptionValues }" var="obj">
					<dd><input type="checkbox" class="input-checkbox" value="${obj.skuOptionValueId }"/><span class="label-sel">${obj.skuOptionValue }</span></dd>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3">
					<span class="small">温馨提示：勾选尺寸/规格框，可对尺寸/规格进行编辑，请填写1-15位字符组成的商品规格</span>
				</td>
			</tr>
			<tr valign="top">
				<td class="left-td" class="small">库存：</td>
				<td colspan="3" class="small">
					<table>
						<tr>
							<td width="450">
								<table class="repertories" width="630" class="small">
									<tr>
										<th width="12%">颜色</th>
										<th width="12%">规格</th>
										<th width="12%">特卖数量</th>
										<th width="12%">秒杀数量</th>
										<th width="26%">国际码</th>
										<th width="26%">SKU编码</th>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr valign="top" class="small">
				<td class="left-td">*商品图片：</td>
				<td colspan="3">
					<table class="no-border-table">
						<tr>
							<td width="120">
								<div class="default-img">
									<div class="del-img"></div>
									<i class="img-prev"></i>
									<i class="img-next"></i>
								</div>
								<input type="hidden" id="fontImgUrl" class="imageUrl"/>
								<input type="file" id="fontImgUpload"/>
							</td>
							<td width="120">
								<div class="default-img">
									<div class="del-img"></div>
									<i class="img-prev"></i>
									<i class="img-next"></i>
								</div>
								<input type="hidden" id="backImgUrl" class="imageUrl"/>
								<input type="file" id="backImgUpload"/>
							</td>
							<td width="120">
								<div class="default-img">
									<div class="del-img"></div>
									<i class="img-prev"></i>
									<i class="img-next"></i>
								</div>
								<input type="hidden" id="styleImgUrl" class="imageUrl"/>
								<input type="file" id="styleImgUpload"/>
							</td>
							<td width="120">
								<div class="default-img">
									<div class="del-img"></div>
									<i class="img-prev"></i>
									<i class="img-next"></i>
								</div>
								<input type="hidden" id="workmanshipImgUrl" class="imageUrl"/>
								<input type="file" id="workmanshipImgUpload"/>
							</td>
							<td valign="bottom" id="no_img"><span id="imgUrlTip"></span></td>
						</tr>
						<tr>
							<td colspan="5">温馨提示：为保证图片效果请上传800px X 800px的商品图片</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr valign="top" class="small">
				<td class="left-td">批量上传图片：</td>
				<td colspan="3">
					<input type="file" id="uploadBatch" />
				</td>
			</tr>
			<tr valign="top" class="small">
				<td class="left-td">*商品描述：</td>
				<td colspan="3">
					<textarea class="edit-textarea" name="product.describe" id="describe">${product.describe }</textarea>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2"><span id="describeTip"></span></td>
				<td></td>
<!-- 				<td align="center">源码：已输入<b class="c-green">0</b>/最多输入<b class="c-red">25000</b></td> -->
			</tr>
			<tr>
				<td></td>
				<td colspan="3">
					<div class="cilck-tip">
							<c:set var="draft" value="<%=Product.STATUS_DRAFT %>"></c:set>
							<c:if test="${product.productId == null || product.status == draft }">
							<input type="button" class="m-btn" value="保&nbsp;&nbsp;存" id="save"/>
							</c:if>
							<input type="button" class="m-btn" value="提交审核" id="submitReview"/>
							<input type="button" class="m-btn" value="预&nbsp;&nbsp;览" id="preview"/>
							<c:if test="${product.productId == null }">
							<input type="button" class="m-btn" value="提交审核并复制" id="submitReviewAndCopy"/>
							</c:if>
						<div class="tip-main">
							<i class="save-succseed"></i><span id="message"></span>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<!--end body-->
<!--start footer-->
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<!--end footer-->
<script type="text/javascript" src="${_jsPath }/pages/editProduct.js"></script>
<script type="text/javascript">
	var STATUS_DRAFT = <%= Product.STATUS_DRAFT%>;
	var STATUS_CHECK_PEND = <%= Product.STATUS_CHECK_PEND%>;
	//图片上传操作
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
		    'width' : 94,
		    'scriptData'  : {'category':'product'}
	};
	var multiUploadParams = {
			'auto' : true,
			'buttonImg' : '${_jsPath}/plugin/uploadify/uploadimg_btn.png',
			'cancelImg' : '${_jsPath}/plugin/uploadify/cancel.png',
			'expressInstall' : '${_jsPath}/plugin/uploadify/expressInstall.swf',
			'fileDataName'   : 'file', 
			'fileDesc' : '请选择jpg、gif、png文件',
			'fileExt' : '*.jpg;*.jpeg;*.gif;*.png',
		    'height' : 20,
		    'multi' : true,
		    'script' : '${_ctxPath}/upload/upload.htm',
		    'sizeLimit' : 2097152,    
		    'uploader' : '${_jsPath}/plugin/uploadify/uploadify.allglyphs.swf',
		    'width' : 94,
		    'queueSizeLimit' : 4,
		    'scriptData'  : {'category':'product'}
	};
	
	//商品图片上传
	$.each($(".no-border-table").find(":file"),function(i,obj){
		var uploadImgParams = uploadDefaultParams;
		uploadImgParams.onComplete = function(event, ID, fileObj, response, data){
			var $td = $(obj).closest("td");
			//移除原有图片
			$td.find(".default-img").find("img").remove();
			//得到缩略图url
			var suffix = response.split(".")[1];
			var url = '${_fileThumbPath}' + response + "_t15." + suffix;
			
			$td.find(".del-img").after("<img src='"+url+"'/>");
			$td.find(".imageUrl").val(response);
	     };
		 $(obj).uploadify(uploadImgParams);
	});
	
	//商品图片批量上传
	var uploadMultiImgParams = multiUploadParams;
	var index = 0;
	uploadMultiImgParams.onComplete = function(event, ID, fileObj, response, data){
    	var $td = $(".no-border-table td").eq(index++);
//     	$td.find(".default-img").find("img").remove();
// 		var suffix = response.split(".")[1];
// 		var url = '${_fileThumbPath}' + response + "_t15." + suffix;
// 		$td.find(".del-img").after("<img src='' url='"+url+"'/>");
		$td.find(".imageUrl").val(response);
		if(index > 3){
			index = 0;
		}
	};
	uploadMultiImgParams.onAllComplete = function(event, data){
		var i = 0;
		var timer = setInterval(function(){
			if(i == 3){
				clearInterval(timer);
			}
			var $td = $(".no-border-table td").eq(i);
	    	$td.find(".default-img").find("img").remove();
	    	var url = $td.find(".imageUrl").val();
			if(url){
				var suffix = url.split(".")[1];
				var thumbUrl = '${_fileThumbPath}' + url + "_t15." + suffix;
				$td.find(".del-img").after("<img src='" + thumbUrl + "'/>");	
			}
			i++;
		},500);
	};
	$("#uploadBatch").uploadify(uploadMultiImgParams);
	
	//库存表格数据操作
	<c:if test="${product != null}">
		<c:forEach items="${product.productSkus }" var="proSku" varStatus="status">
			var id = "";
			<c:set var="size" value="${fn:length(proSku.productSkuOptionValues)}"></c:set>
			<c:forEach items="${proSku.productSkuOptionValues }" var="proSkuVal">
 				id += "${proSkuVal.skuOptionValueId}_";
			</c:forEach> 
			if(${size} > 1){
				id = id.substring(0, id.length-1);
			}else if(${size} == 0){
				id = "_";
			}
			var value = {
					"id" : id,
					"productSkuId" : "${proSku.productSkuId}",
					"inventory" : "${proSku.inventory}",
					"secKillInventory" : "${proSku.secKillInventory}",
					"internationalCode" : "${proSku.internationalCode}",
					"skuCode" : "${proSku.skuCode}",
					"seckillPlanNum" : "${proSku.seckillPlanNum}"
			};
			data.push(value);
		</c:forEach>
		
		//颜色、尺寸复选框处理
		<c:forEach items="${product.skuOptions }" var="skuOpt">
			<c:forEach items="${skuOpt.skuOptionValues }" var="skuOptVal">
				var $input_checkbox = $(".input-checkbox[value='${skuOptVal.skuOptionValueId}']");
				$input_checkbox.attr("checked", "checked");
				var $span = $input_checkbox.closest("dd").find("span");
				if("${skuOptVal.overrideFlag}" == "1"){
					$input_checkbox.attr("overrideFlag", "1");
					$span.text("${skuOptVal.skuOptionValue}");
				}
				$span.hide().after("<input type='text' class='c-input' value='"+$span.text()+"' onchange='checkSkuInput(this)'/>");
			</c:forEach>
		</c:forEach>
		
		//商品图片显示
		<c:forEach items="${product.imageList }" var="imageUrl" varStatus="status">
			<c:set var="suffix" value="${fn:split(imageUrl, '.')[1]}"></c:set>
			var $del_img = $(".del-img").eq(${status.index});
			$del_img.after("<img src='${_fileThumbPath}${imageUrl}_t15.${suffix}'/>");
			var $td = $del_img.closest("td");
			$td.find(".imageUrl").val("${imageUrl}");
		</c:forEach>
	</c:if>
	
	//生成库存表格
	createRepertoriesTable();
	
	var editor = CKEDITOR.replace("describe");
	<c:if test="${product == null}">
		$.ajax({
			type: 'GET',
			async: false,
			url: _ctxPath + '/seller/product/product-getTemplate.htm',
			success:function(html){
				editor.setData(html);
			}
		});
	</c:if>	
	
	//验证
	$.formValidator.initConfig({validatorGroup: '1',formID:"addForm",theme:"Default",errorFocus:true,wideWord:false});
	$("#brandId").formValidator({onShow:"请选择品牌",onFocus:"请选择品牌",onCorrect:"谢谢你的配合"}).inputValidator({min:1,onError: "请选择品牌"});
	$("#productCategoryId").formValidator({onShow:"请选择品类",onFocus:"请选择品类",onCorrect:"谢谢你的配合"}).inputValidator({min:1,onError: "请选择品类"});
	$("#name").formValidator({onFocus:"请填写1-20个字符组成的商品名称"}).inputValidator({min:1,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},onError:"请填写1-20个字符组成的商品名称"});
	$("#marketPrice").formValidator({onShow:"请填写0.01-99999999.99之间的数字",onFocus:"请填写0.01-99999999.99之间的数字",onCorrect:"输入正确"}).regexValidator({regExp:"^[1-9]{1}\\d{0,7}([.]\\d{1,2})?$|^0.(\\d[1-9]|[1-9]\\d){1}$",dataType:"string",onError:"你输入的商品价格格式不正确"});
	$(".input-radio[name='product.postage.option']").formValidator({tipID:"postageTip",onShow:"请选择商品是否免邮",onFocus:"请选择商品是否免邮",onCorrect:"输入正确"}).inputValidator({min:1,max:1,onError:"请选择商品是否免邮"});
	$("#salePrice").formValidator({empty:true,onShow:"请填写0.01-99999999.99之间的数字",onFocus:"请填写0.01-99999999.99之间的数字"}).regexValidator({regExp:"^[1-9]{1}\\d{0,7}([.]\\d{1,2})?$|^0.(\\d[1-9]|[1-9]\\d){1}$",dataType:"string",onError:"你输入的商品价格格式不正确"});
	$("#secKillPrice").formValidator({empty:true,onShow:"请填写0.01-99999999.99之间的数字",onFocus:"请填写0.01-99999999.99之间的数字",onCorrect:"输入正确"}).regexValidator({regExp:"^[1-9]{1}\\d{0,7}([.]\\d{1,2})?$|^0.(\\d[1-9]|[1-9]\\d){1}$",dataType:"string",onError:"你输入的商品价格格式不正确"});
// 	$("#sellStartTime").formValidator({tipID:"dateTip",empty:true,onShow:"请选择开始时间",onFocus:"请选择开始时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
// 	$("#sellEndTime").formValidator({tipID:"dateTip",empty:true,onShow:"请选择结束时间",onFocus:"请选择结束时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
	$(".input-radio[name='product.isImport']").formValidator({tipID:"isImportTip",onShow:"请选择商品是否进口",onFocus:"请选择商品是否进口",onCorrect:"输入正确"}).inputValidator({min:1,max:1,onError:"请选择商品是否进口"});
	$(".imageUrl").formValidator({tipID:"imgUrlTip", onFocus: "请上传完整的商品展示图片", onCorrect: "谢谢你的合作，你的图片名正确" })
	.functionValidator({
		fun:function() {
			if($(".default-img img").length == 0){
				return "请上传完整的商品展示图片";
			}
		}
	});
	$("#secKillDescribe").formValidator({tipID:"secKillDescribeTip",onShow:"请填写秒杀商品描述",onFocus:"请填写秒杀商品描述"}).inputValidator({min:1,onErrorMin:"请填写秒杀商品描述",empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"}});
	$("#describe").formValidator({
		tipId : "describeTip",
		onFocus : "请填写商品描述",
		onCorrect : "谢谢你的配合"
	}).functionValidator({
		fun : function() {
			var describe = editor.getData();
			if(describe.length == 0){
				return "商品描述不能为空";
			}
		}
	});
</script>
</body>
</html>