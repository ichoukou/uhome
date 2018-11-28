<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Plan"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh">
<head>
<meta charset="utf-8" />
<title>编辑排期</title>
	<link href="${_cssPath }/common.css" rel="stylesheet" />
	<link href="${_cssPath }/pages/arrangement.css" rel="stylesheet" />
  <script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
  <script type="text/javascript" src="${_jsPath}/plugin/uploadify/swfobject.js"></script>  
	<script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
  <script type="text/javascript" src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
    <script type="text/javascript" src="${_jsPath }/pages/plan/planCommon.js"></script>
    <script type="text/javascript" src="${_jsPath }/pages/plan/editSellPlan.js"></script>
<body>
 	<!--start header-->
 	<jsp:include page="../include/header.jsp"></jsp:include> 
    <!--end header-->
    <!--start body-->
    <div class="m-w980p">
   	<!--star 新增特卖 -->
   	<form id="sale_date">
      	<div class="addSale-table">
      		<input type="hidden" value="${plan.planId }" name="plan.planId" id="planId"/>
      		<input type="hidden" name="plan.type"  value="<%=Plan.TYPE_SPECIAL_SELLER%>"/>
			<input type="hidden" name="plan.status"  value="<%=Plan.STATUS_UNRELEASE%>"/>
      		<div class="addSale-time m-clear">
      			<div class="m-fl">
							<em class="c-red">*</em>
							<span>选择特卖日期：</span>
							<input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}',readOnly:true,onpicked:function(){startTimePickedFunc();endTime.focus();}})" value="<fmt:formatDate value='${plan.startTime }' pattern='yyyy-MM-dd'/>" name="plan.startTime" id="startTime"/>
							至
							<input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\',{d:5})||\'%y-%M-{%d+6}\'}',maxDate:'#F{$dp.$D(\'startTime\',{d:30})||\'%y-%M-{%d+31}\'}',readOnly:true,onpicked:pickedFunc})" value="<fmt:formatDate value='${plan.endTime }' pattern='yyyy-MM-dd'/>" name="plan.endTime" id="endTime"/>
							<div id="dateTip"></div>
	       		</div>
      		</div>
      		<div class="addSale-sel m-clear">
      			<div class="m-fl">
							<em class="c-red">*</em>
							<span>选择特卖品牌：</span>
							<select class="m-sel" name="plan.brandId" id="brand">
								<option value="">选择品牌</option>
								<c:forEach items="${brands }" var="brand">
								<option value="${brand.brandId }"
								<c:if test="${plan.brandId==brand.brandId }">
								selected = "selected"
								</c:if>
								>${brand.name }</option>
								</c:forEach>
							</select>
							<select class="m-sel" name="plan.productCategoryId" id="productCategory">
								<option value="">选择特卖分类</option>
								<c:forEach items="${productCategories }" var="productCategory">
								<option value="${productCategory.productCategoryId }"
								<c:if test="${plan.productCategoryId==productCategory.productCategoryId }">
								selected = "selected"
								</c:if>
								>${productCategory.name}</option>
								</c:forEach>
							</select>
						</div>
					<div id="brandTip"></div>
      		</div>
	       	<div class="addSale-select-product">
	   			<span id="loading" style="display: none;">数据正在加载中……</span>
	  		</div>
	  		<!--star 商品排序 -->
	  		<div><b>商品权重：(1-10)</b></div><div id="productTip"></div>
	  		<div class="addSale-product-seqencing">
	  			<ul></ul>
	  			<div class="clear"></div>
	  		</div>
	  		<!--end 商品排序 -->
	  		<div class="position">
	  			<em class="c-red">*</em>
	  			<span>特卖活动名称：</span>
	  			<input type="text" value="${plan.name }" class="txt-input" name="plan.name" id="name"/>
	  			<div id="nameTip"></div>
	  		</div>
	  		<!-- 邮费 -->
	  		<div class="position m-mt10p">
	  			<span>邮费：</span>
				<input type="checkbox" class="input-checkbox" name="plan.postage.option" 
					<c:if test="${plan.postage.option == defaultPostageOption }">checked="checked"</c:if>
				value="${defaultPostageOption }"/>
				<label><small><spring:message code="postage.option.${defaultPostageOption}"/></small></label>
	  		</div>
	  		<div class="addSale-img">
	  			<div><em class="c-red">*</em> <span>请上传像素为420px*180px的图片</span></div>
	  			<div class="sale-default">
		  			<c:if test="${not empty plan.imageUrl}">
		  				<img src="${_filePath}${plan.imageUrl}" />
		  			</c:if>
		      </div>
	  			<input type="hidden" value="${plan.imageUrl}" name="plan.imageUrl" id="planImage"/>
	  			<input type="file" id="uploadSellPlanImg"/>
	  			<div id="planImageTip"></div>
	  		</div>
	  		<!--上传即将开始图片-->
	  		<div class="addSale-img">
	  			<div><em class="c-red">*</em> <span>请上传像素为316px*250px的图片</span></div>
	  			<div class="sale-upcoming">
		  			<c:if test="${not empty plan.coverImageUrl}">
		  				<img src="${_filePath}${plan.coverImageUrl}" />
		  			</c:if>
		      </div>
		      <input type="hidden" value="${plan.coverImageUrl}" name="plan.coverImageUrl" id="upcomingImage"/>
	  			<input type="file" id="uploadCoverImage"/>
	  			<div id="upcomingImageTip"></div>
	    	</div>
	    	<!--上传即将开始图片-->
	  		<div class="addSale-img">
	  			<div><em class="c-red">*</em> <span>请上传像素为680px*210px的图片</span></div>
	  			<div class="sale-banner">
		  			<c:if test="${not empty plan.planBannerImageUrl}">
		  				<img src="${_filePath}${plan.planBannerImageUrl}" />
		  			</c:if>
		      </div>
		      <input type="hidden" value="${plan.planBannerImageUrl }" name="plan.planBannerImageUrl" id="planBannerImage"/>
	  			<input type="file" id="uploadBannerImage"/>
	  			<div id="planBannerImageTip"></div>
	    	</div>
	    	<div class="m-mt10p">
		    	<input type="button" class="m-btn" value="保&nbsp;&nbsp;存" id="save"/>
	      		<input type="button" class="m-btn" value="取&nbsp;&nbsp;消" id="cancle"/>
      		</div>
      	</div>
    </form>
    </div>
      <!--end 新增特卖 -->
<!--start footer-->
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<!--end footer-->
<script type="text/javascript">
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
		    'scriptData' : {'category':'plan'}
	};	
	var uploadImgParams = uploadDefaultParams;
	uploadImgParams.onComplete = function(event, ID, fileObj, response, data){
		var url = '${_filePath}' + response;
		$(".sale-default img").remove();
		$(".sale-default").append("<img src='"+url+"'/>");
		$("#planImage").val(response);
     };
	 $("#uploadSellPlanImg").uploadify(uploadImgParams);
	 
 	//即将上线图片上传参数设置
 	var uploadCoverImgParams = uploadDefaultParams;
 	uploadCoverImgParams.onComplete = function(event, ID, fileObj, response, data){
		var url = '${_filePath}' + response;
		$(".sale-upcoming img").remove();
		$(".sale-upcoming").append("<img src='"+url+"'/>");
		$("#upcomingImage").val(response);
     };
	 $("#uploadCoverImage").uploadify(uploadCoverImgParams);
	 
	//排期详细顶部图片上传参数设置
 	var uploadBannerImgParams = uploadDefaultParams;
 	uploadBannerImgParams.onComplete = function(event, ID, fileObj, response, data){
		var url = '${_filePath}' + response;
		$(".sale-banner img").remove();
		$(".sale-banner").append("<img src='"+url+"'/>");
		$("#planBannerImage").val(response);
     };
	 $("#uploadBannerImage").uploadify(uploadBannerImgParams);
	 
	function clear(){
		$(".addSale-saler-list").remove();
		$(".addSale-product-seqencing ul").empty();
	}
	//加载商品信息方法
	function loadProducts(){
		$("#loading").show();
		var value = {
				"startTime" : $("#startTime").val(),
				"endTime" : $("#endTime").val(),
				"brandId" : $("#brand").val(),
				"productCategoryId" : $("#productCategory").val(),
				"planId" : $("#planId").val()
		};
		$.ajax({
			type:'POST',
			url: _ctxPath + '/admin/plan/plan-listSellersForSpecialSell.htm',
			data: value,
			success:function(html){
				$("#loading").hide();
				$(".addSale-select-product").append(html);
				//当前操作为编辑时
				<c:if test="${not empty plan}">
					//选中列表中已加入该排期的商品
					<c:forEach items="${plan.planProducts }" var="planProduct">
					$(":checkbox[value='${planProduct.productId}']")
							.attr("rank","${planProduct.rank}")
							.attr("checked","checked");
					</c:forEach>
					
					var $divs = $(".addSale-product");
					$.each($divs, function(i, div){
						var checkedNum = $(div).find(":checkbox:checked").length;
						var checkboxNum = $(div).find(":checkbox").length;
						if(checkedNum != 0 && checkedNum == checkboxNum){
							$(div).siblings(".addSale-saler").find(":checkbox").attr("checked","checked");
						}
					});
					
					//将选中的商品显示在商品排序div中
					var $inputs = $(".addSale-product input:checked");
					$.each($inputs, function(i, input){
						var rank = $(input).attr("rank");
						var productId = $(input).val();
						var $li = $("<li id='checkbox-"+productId+"'>" +
								"<input type='text' class='product-seqencing-num' value='"+rank+"' productId='"+productId+"' onchange='checkRank(this)'/></li>");
						var $img = $(input).closest("li").find("img").clone();
						$li.prepend($img);
						var $infoDiv = $(input).closest("li").find(".sale-product-info").clone();
						$li.append($infoDiv);
						$(".addSale-product-seqencing ul").append($li);
					});
				</c:if>
			}
		});
	}
	
	<c:if test="${not empty plan}">
		loadProducts();
	</c:if>
	//日期控件事件
	function pickedFunc(){
		clear();
		if($("#startTime").val() !="" && $("#brand").val() != "" && $("#productCategory").val() != ""){
			loadProducts();
		}
	}
	function startTimePickedFunc(){
		clear();
		if($("#endTime").val() !="" && $("#brand").val() != "" && $("#productCategory").val() != ""){
			loadProducts();
		}
	}
	
	//品牌、品类下拉框事件(此写法解决校验框架引起change事件两次触发问题)
	$("#brand,#productCategory").bind({
		focus:function(){
			$(this).change(function(){
				clear();
				if($("#startTime").val()!="" && $("#endTime").val()!="" 
						&& $("#productCategory").val()!="" && $("#brand").val()!=""){
					loadProducts();
				}
				if($(this).val() != ""){
					var $brandTip = $("#brandTip");
					$brandTip.addClass("onCorrect");
					$brandTip.empty();
					$brandTip.html("<span class=\"onCorrect\"></span>");
				}
			});
		},
		blur:function(){
			$(this).unbind("change");
		}
	});
	
	//验证
	$.formValidator.initConfig({formID:"sale_date",theme:"Default",errorFocus:false,wideWord:false});
	$("#startTime").formValidator({tipID:"dateTip",onShow:"请选择排期开始时间",onFocus:"请选择排期开始时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
	$("#endTime").formValidator({tipID:"dateTip",onShow:"请选择排期结束时间",onFocus:"请选择排期结束时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
	$("#brand").formValidator({tipID:"brandTip",onShow:"请选择品牌",onFocus:"品牌必须选择",onCorrect:"谢谢你的配合"}).inputValidator({min:1,onError: "品牌必须选择!"});
	$("#productCategory").formValidator({tipID:"brandTip",onShow:"请选择特卖分类",onFocus:"特卖分类必须选择",onCorrect:"谢谢你的配合"}).inputValidator({min:1,onError: "特卖分类必须选择!"});
	$("#name").formValidator({tipID:"nameTip",onShow:"请输入3-20位活动名称"}).inputValidator({min:3,max:20,onError:"请输入3-20位活动名称"});
// 	$(".input-checkbox[name='plan.postage.option']").formValidator({empty:true,tipID:"postageTip",onShow:"请选择排期是否免邮",onFocus:"请选择排期是否免邮",onCorrect:"输入正确"}).inputValidator({min:1,max:1,onError:"请选择排期是否免邮"});
	$("#planImage").formValidator({tipID:"planImageTip",onShow: "请输入图片名", onCorrect: "谢谢你的合作，你的图片名正确" }).inputValidator({min:1,onErrorMin:"请上传图片"}).regexValidator({ regExp: "picture", dataType: "enum", onError: "图片名格式不正确" });
	$("#upcomingImage").formValidator({tipID:"upcomingImageTip",onShow: "请输入图片名", onCorrect: "谢谢你的合作，你的图片名正确" }).inputValidator({min:1,onErrorMin:"请上传图片"}).regexValidator({ regExp: "picture", dataType: "enum", onError: "图片名格式不正确" });
	$("#planBannerImage").formValidator({tipID:"planBannerImageTip",onShow: "请输入图片名", onCorrect: "谢谢你的合作，你的图片名正确" }).inputValidator({min:1,onErrorMin:"请上传图片"}).regexValidator({ regExp: "picture", dataType: "enum", onError: "图片名格式不正确" });
</script>
</body>
</html>