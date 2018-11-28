<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑商家</title>
	<link href="${_cssPath}/common.css" rel="stylesheet" />
	<link href="${_cssPath}/pages/addBrand.css" rel="stylesheet" />
	<link href="${_jsPath }/plugin/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" />
	<script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
	<script src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js" language="javascript"></script>
	<script src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js" language="javascript"></script>
	<script src="${_jsPath}/plugin/select/linkage_sel.js"></script>
	<script type="text/javascript" src="${_jsPath}/plugin/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/ckeditor/ckeditor.js"></script>
	<script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${_jsPath }/pages/seller.js"></script>
	<script type="text/javascript">
	var contextPath = '${_ctxPath}';
	var reset = function(userId) {
		if (confirm("你确定要重置密码吗?")) {
			return;
			$.ajax({
				url : "${_ctxPath}/admin/user/user-resetPassword.htm",
				type : "POST",
				data : {
					'user.userId' : userId
				},
				dataType : "json",
				success : function(data) {
					alert(data.info);
				},
				error : function(data) {
					console.log(data);
				}
			});
		}
	};
	$(function() {
		//图片上传操作
		var uploadDefaultParams = {
			'auto' : true,
			'buttonImg' : '${_jsPath}/plugin/uploadify/uploadimg_btn.png',
			'cancelImg' : '${_jsPath}/plugin/uploadify/cancel.png',
			'expressInstall' : '${_jsPath}/plugin/uploadify/expressInstall.swf',
			'fileDataName' : 'file',
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
		uploadImgParams.scriptData = {'category' : 'seller'};
		uploadImgParams.onComplete = function(event, ID, fileObj, response, data) {
			$('#uploadImageValueId').val(response);
			//得到缩略图url
			var suffix = response.split(".")[1];
			var url = '${_fileThumbPath}' + response + "_t24." + suffix;
			$(".license").empty();
			$(".license").append("<img src='"+url+"'/>");
		};
		$('#imgUpload').uploadify(uploadImgParams);
	});
</script>
</head>
<body>
<jsp:include page="../include/header.jsp"  flush="true" />
	<div class="body m-w980p">
			<h1 class="add-title">编辑商家</h1>
			<form action="${_ctxPath}/admin/user/user-saveSeller.htm" method="post"
				 id="form1">
				<input type="hidden" name="seller.sellerId" id="sellerId" value="${seller.sellerId }" />
				<input type="hidden" name="seller.user.userId" id="sellerUserId" value="${seller.user.userId }" />
					<table border="0" class="add-table">
						<tr>
							<th width="100">基本信息</th>
							<th colspan="4">&nbsp;</th>
						</tr>
						<tr>
							<td align="right"><span class="c-red">*</span>登录名：</td>
							<td width="225">
								<c:if test="${seller.sellerId !=null && seller.sellerId != ''}">
									${seller.user.username}
								</c:if>
								<c:if test="${seller.sellerId ==null || seller.sellerId == ''}">
								<input id="business-name" type="text" name="seller.user.username"
									value="${seller.user.username}" class="add-text" />
								</c:if>
								
							</td>
							<td colspan="3"><span id="business-nameTip"></span></td>
						</tr>
						<tr>
							<td align="right"><span class="c-red">*</span>业务联系人：</td>
							<td><input id="linkman" type="text" name="seller.contactName"
								value="${seller.contactName}" class="add-text"/></td>
							<td colspan="3"><span id="linkmanTip"></span></td>
						</tr>
						<tr>
							<td align="right"><span class="c-red">*</span>手机号码：</td>
							<td><input id="tel-text" type="text" name="seller.mobile"
								value="${seller.mobile}" class="add-text" maxlength="11"/></td>
							<td colspan="3"><span id="tel-textTip"></span></td>
						</tr>
						<tr>
							<td align="right">固定电话：</td>
							<td><input id="dh" type="text" name="seller.user.tel"
								value="${seller.user.tel}" class="add-text"/></td>
							<td colspan="3"><span id="dhTip"></span></td>
						</tr>
						<tr>
							<td align="right"><span class="c-red">*</span>电子邮箱：</td>
							<td><input id="email" type="text" name="seller.user.email"
									value="${seller.user.email}" class="add-text"/></td>
							<td colspan="3"><span id="emailTip"></span></td>
						</tr>
						<tr>
							<td align="right">QQ：</td>
							<td><input type="text" name="seller.qq" value="${seller.qq}" class="add-text" id="QQ"/></td>
							<td colspan="3"><span id="QQTip"></span></td>
						</tr>
						<tr>
							<td align="right">传真：</td>
							<td><input type="text" name="seller.fax" value="${seller.fax}" class="add-text" id="fax"/></td>
							<td colspan="3"><span id="faxTip"></span></td>
						</tr>
						<tr>
							<th>公司信息</th>
							<th colspan="4">&nbsp;</th>
						</tr>
						<tr>
							<td align="right"><span class="c-red">*</span>公司名称：</td>
							<td><input type="text" name="seller.companyName"
						value="${seller.companyName}" class="add-text" id="company-name"/></td>
						<td colspan="3"><span id="company-nameTip"></span></td>
						</tr>
						<tr>
							<td align="right">公司类型：</td>
							<td>
								<input type="text" name="seller.companyType"
								value="${seller.companyType}" class="add-text" id="company-type"/>
							</td>
							<td colspan="3"><span id="company-typeTip"></span></td>
						</tr>
						<tr>
							<td align="right">供应商性质：</td>
							<td>
								<select name="seller.supplierType" class="m-sel" id="gys">
									<c:forEach items="${supplierTypes }" var="supplierType" varStatus="status">
											<option value="${supplierType}"
											<c:if test="${seller.supplierType==status.index }">
											selected = "selected"
											</c:if>
											><spring:message code="seller.supplierType.${status.index}"/></option>
									</c:forEach>
								</select>
							</td>
							<td colspan="3"><span id="gysTip"></span></td>
						</tr>
						<tr>
							<td align="right">成立日期：</td>
							<td><input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM',readOnly:true,})"
								value="<fmt:formatDate value='${seller.registerDate}' pattern='yyyy-MM'/>" class="Wdate" id="rq"/>
								<input type="hidden" name="seller.registerDate" id="registerDate"/>
							</td>
							<td colspan="3"><span id="rqTip"></span></td>
						</tr>
						<tr>
							<td align="right">注册资本：</td>
							<td><input type="text" name="seller.registerCapital"
								value="${seller.registerCapital}" class="add-text" id="regdit-zb" /></td>
								<td>万元</td>
							<td colspan="2"><span id="regdit-zbTip"></span></td>
						</tr>
						<tr>
							<td align="right">实收资本：</td>
							<td><input type="text" name="seller.paidUpCapital"
								value="${seller.paidUpCapital}" class="add-text" id="ss-zb"/></td>
							<td>万元</td>
							<td colspan="2"><span id="ss-zbTip"></span></td>
						</tr>
						<tr valign="top">
							<td align="right">经营范围：</td>
							<td colspan="4"><textarea class="edit-textarea" name="seller.businessScope"
							id="businessScope">${seller.businessScope}</textarea></td>
						</tr>
						<tr>
							<td></td>
							<td colspan="4"><div id="businessScopeTip"></div></td>
<!-- 							<td align="center">源码：已输入<b class="c-green" id="businessScopeCount">0</b>/最多输入<b class="c-red">100</b></td> -->
						</tr>
						<tr valign="top">
							<td align="right"><span class="c-red">*</span>公司简介：</td>
							<td colspan="4"><textarea class="edit-textarea" name="seller.remark" id="remark">${seller.remark}</textarea></td>
						</tr>
						<tr>
							<td></td>
							<td colspan="4"><div id="remarkTip"></div></td>
<!-- 							<td align="center">源码：已输入<b class="c-green" id="remarkCount">0</b>/最多输入<b class="c-red">200</b></td> -->
						</tr>
						<tr>
							<td align="right">公司网址：</td>
							<td><input type="text" name="seller.companyWebSite"
								value="${seller.companyWebSite}" class="add-text" id="url" /></td>
							<td colspan="3"><span id="urlTip"></span></td>
						</tr>
						<tr>
							<td align="right"><span class="c-red">*</span>可售品牌：</td>
							<td colspan="3">
								<div class="sal-brand-t">
									<a href="javascript:void(0);">A</a>
									<a href="javascript:void(0);">B</a>
									<a href="javascript:void(0);">C</a>
									<a href="javascript:void(0);">D</a>
									<a href="javascript:void(0);">E</a>
									<a href="javascript:void(0);">F</a>
									<a href="javascript:void(0);">G</a>
									<a href="javascript:void(0);">H</a>
									<a href="javascript:void(0);">I</a>
									<a href="javascript:void(0);">J</a>
									<a href="javascript:void(0);">K</a>
									<a href="javascript:void(0);">L</a>
									<a href="javascript:void(0);">M</a>
									<a href="javascript:void(0);">N</a>
									<a href="javascript:void(0);">O</a>
									<a href="javascript:void(0);">P</a>
									<a href="javascript:void(0);">Q</a>
									<a href="javascript:void(0);">R</a>
									<a href="javascript:void(0);">S</a>
									<a href="javascript:void(0);">T</a>
									<a href="javascript:void(0);">U</a>
									<a href="javascript:void(0);">V</a>
									<a href="javascript:void(0);">W</a>
									<a href="javascript:void(0);">X</a>
									<a href="javascript:void(0);">Y</a>
									<a href="javascript:void(0);">Z</a>
									<a href="javascript:void(0);">0</a>
									<a href="javascript:void(0);">1</a>
									<a href="javascript:void(0);">2</a>
									<a href="javascript:void(0);">3</a>
									<a href="javascript:void(0);">4</a>
									<a href="javascript:void(0);">5</a>
									<a href="javascript:void(0);">6</a>
									<a href="javascript:void(0);">7</a>
									<a href="javascript:void(0);">8</a>
									<a href="javascript:void(0);">9</a>
								</div>
								<div class="br-list">
									<input type="checkbox" name="seller.listBrandIds" style="display: none;"/>
								</div>
								<c:forEach items="${brandMap }" var="map">
								<div class="br-list" id="brand-${map.key }" style="display: none;">
									<c:forEach items="${map.value }" var="brand">
									<input type="checkbox" id="brand-${brand.brandId }" name="seller.listBrandIds" value="${brand.brandId }" 
										<c:if test="${brand.isChecked }">checked</c:if>/>
									<label for="brand-${brand.brandId }">${brand.name }</label>
									</c:forEach>
								</div>
								</c:forEach>
							</td>
							<td><span id="brandTip"></span></td>
						</tr>
						<tr>
							<td align="right">已勾选品牌：</td>
							<td colspan="3" id="checkedBrands">
								<b id="selectedBrandNames">${seller.brandNames }</b>
							</td>
							<td><span id=""></span></td>
						</tr>
						<tr>
							<td align="right">公司地址：</td>
							<td colspan="3" class="mc-sel">
								<select id="company_province" class="company linkagesel m-mr5p"></select>
		                    	<select id="company_city" class="company linkagesel m-mr5p"></select>
		                    	<select id="company_region" class="company linkagesel"></select>
		                    	<input type="hidden"  id="companyRegionCodes" value="${seller.companyRegionId}" name="seller.companyRegionId"/>
					            <input type="hidden"  id="company_area_prov" value='${seller.companyModel.provinceId}'/>
								<input type="hidden"  id="company_area_city" value='${seller.companyModel.cityId}'/>
								<input type="hidden"  id="company_area_region" value='${seller.companyModel.countyId}'/>
							</td>
							<td>
								<span class="region-tip"><span id="regionTips1" class='ming'></span></span>
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="3">
								<input type="text" name="seller.companyAddress"	value="${seller.companyAddress}" class="addr-text" id="addr-info"/>
							</td>
							<td width="290"><span id="addrTip"></span></td>
						</tr>
						<tr>
							<td align="right"><span class="c-red">*</span>发货地址：</td>
							<td colspan="3">
								<div id="add-f" class="mc-sel">
								    <select id="shiper_province" class="shiper linkagesel m-mr5p"></select>
                    				<select id="shiper_city" class="shiper linkagesel m-mr5p"></select>
                    				<select id="shiper_region" class="shiper linkagesel"></select>
									<input type="hidden" name="seller.shiperRegionId" id="shiperRegionCodes" value="${seller.shiperRegionId }" />
									<input type="hidden"  id="shiper_area_prov" value='${seller.shiperModel.provinceId}'/>
									<input type="hidden"  id="shiper_area_city" value='${seller.shiperModel.cityId}'/>
									<input type="hidden"  id="shiper_area_region" value='${seller.shiperModel.countyId}'/>
								</div>
							</td>
							<td>
								 <span id="addrTip-f"></span>
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="3"><input type="text" name="seller.shiperAddress" value="${seller.shiperAddress}"  class="addr-text" id="addr-info-f" /></td>
							<td><span id="addr-info-fTip"></span></td>
						</tr>
						<tr>
							<td align="right"><span class="c-red">*</span>退货地址：</td>
							<td colspan="3">
								<div id="add-t" class="mc-sel">
								<select id="receiver_province" class="receiver linkagesel m-mr5p"></select>
                    			<select id="receiver_city" class="receiver linkagesel m-mr5p"></select>
                    			<select id="receiver_region" class="receiver linkagesel"></select>
								<input type="hidden" name="seller.receiverRegionId" id="receiverRegionCodes" value="${seller.receiverRegionId}" />
								<input type="hidden"  id="receiver_area_prov" value='${seller.receiverModel.provinceId}'/>
								<input type="hidden"  id="receiver_area_city" value='${seller.receiverModel.cityId}'/>
								<input type="hidden"  id="receiver_area_region" value='${seller.receiverModel.countyId}'/>
								</div>
							</td>
							<td>
							   <span id="addrTip-t"></span>
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="3"><input type="text" name="seller.receiverAddress"
									value="${seller.receiverAddress}" class="addr-text" id="addr-info-t" /></td>
							<td><span id="addr-info-tTip"></span></td>
						</tr>
						<tr>
							<td align="right">法人代表：</td>
							<td><input type="text" name="seller.companyCorporation"
								value="${seller.companyCorporation}" class="add-text" id="juridicalPperson" /></td>
							<td colspan="3"><span id="juridicalPpersonTip"></span></td>
						</tr>
						<tr>
							<td align="right">税务登记号：</td>
							<td><input type="text" name="seller.taxNo"
							value="${seller.taxNo}" class="add-text" id="registration"/></td>
							<td colspan="3"><span id="registrationTip"></span></td>
						</tr>
						<tr>
							<td align="right">企业法人营业执照注册号：</td>
							<td><input type="text"
								name="seller.companyCode" value="${seller.companyCode}" class="add-text" id="registrationNum" /></td>
							<td colspan="3"><span id="registrationNumTip"></span></td>
						</tr>
						<tr>
							<td align="right">企业编码：</td>
							<td><input type="text" name="seller.companyNo"
								value="${seller.companyNo}" class="add-text" id="companyNum" /></td>
							<td colspan="3"><span id="companyNumTip"></span></td>
						</tr>
						<tr>
							<td align="right"><span class="c-red">*</span>支付宝帐号：</td>
							<td><input type="text" name="seller.alipayNo"
						value="${seller.alipayNo}" class="add-text" id="alipay" /></td>
							<td align="right"><span class="c-red">*</span>注册支付宝姓名：</td>
							<td><input type="text" 
								name="seller.alipayName" value="${seller.alipayName}" class="add-text" id="alipayName"/></td>
							<td><span id="paynum"></span></td>
						</tr>
						<tr>
							<td align="right"><span class="c-red">*</span>开户银行：</td>
							<td><input type="text" name="seller.bankName"
								value="${seller.bankName}" class="add-text" id="bank" /></td>
							<td align="right"><span class="c-red">*</span>银行帐号：</td>
							<td><input type="text"
								name="seller.bankAccount" value="${seller.bankAccount}" class="add-text" id="bankcardNum"/></td>
							<td></td>
						</tr>
						<tr valign="top">
							<td align="right">营业执照：</td>
							<td colspan="3">
								<input type="file" id="imgUpload" name="file" /> 
								<input type="hidden" id="uploadImageValueId" name="seller.licenseImageUrl" value="${seller.licenseImageUrl}" />
								<div class="license">
									<img src="<zx:thumbImage originalPath='${seller.licenseImageUrl}' imageType='t24'></zx:thumbImage>"/>
								</div>
							</td>
							<td><span id="uploadImageValueIdTip"></span></td>
						</tr>
						<tr>
							<td colspan="5" align="center">
								<input type="button" class="m-btn" value="保存所有信息" id="submitForm"/>
								<input type="button" class="m-btn" value="返回" id="backList"/>
							</td>
						</tr>
					</table>
			</form>
	</div>
	 <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
	<script type="text/javascript">
	    
		//初始化ckeditor编辑器
	    var businessScopeEditor = CKEDITOR.replace("seller.businessScope");
	    var remarkEditor = CKEDITOR.replace("seller.remark");
	    function validatorSelect(cssName,id,isValid){
	    	$(cssName).formValidator({
	    		empty:isValid,
	        	validatorGroup: '2001',
	            onShow:"请选择地区",
	            onFocus:"请选择具体地区",
	            onCorrect:"",
	            tipID:id
	        }).inputValidator({min:1,onError: "请选择具体地区!"});
	    }
	    $(function(){
	   	 	//验证
			$.formValidator.initConfig({
 	    	    validatorGroup: '2001',
 	    	    formID: 'form1',
 	    	 	errorFocus:false,
 	    	    theme: 'Default'
 	    	});
	    	//验证三级联动地址
		 	//validatorSelect(".company","regionTips1",true);
		 	validatorSelect(".shiper","regionTips2",false);
		 	validatorSelect(".receiver","regionTips3",false);
	    	 var prov = $("#company_area_prov").val(), city = $("#company_area_city").val(), district = $("#company_area_region").val();
	    	 var linkage = new linkageSelect();
             linkage.init({
                 "oneSel":['#company_province','请选择',prov],
                 "twoSel":['#company_city', '请选择',city],
                 "threeSel":['#company_region', '请选择',district]
             });
             var prov1 = $("#shiper_area_prov").val(), city1 = $("#shiper_area_city").val(), district1 = $("#shiper_area_region").val();
	    	 var linkage1 = new linkageSelect();
             linkage1.init({
                 "oneSel":['#shiper_province','请选择',prov1],
                 "twoSel":['#shiper_city', '请选择',city1],
                 "threeSel":['#shiper_region', '请选择',district1]
             });
             var prov2 = $("#receiver_area_prov").val(), city2 = $("#receiver_area_city").val(), district2 = $("#receiver_area_region").val();
	    	 var linkage2 = new linkageSelect();
             linkage2.init({
                 "oneSel":['#receiver_province','请选择',prov2],
                 "twoSel":['#receiver_city', '请选择',city2],
                 "threeSel":['#receiver_region', '请选择',district2]
             });
	    	//linkageSel1.changeValues([$("#shiper_area_prov").val(), $("#shiper_area_city").val(), $("#shiper_area_region").val()]);
	    	//linkageSel2.changeValues([$("#receiver_area_prov").val(), $("#receiver_area_city").val(), $("#receiver_area_region").val()]);
	    	
	    	$('#submitForm').bind('click',function(){
	    		//成立日期
	    		var regDateStr = $("#rq").val();
	    		if(regDateStr){
		    		$("#registerDate").val(regDateStr + "-01");	
	    		}
		        $('#companyRegionCodes').val($("#company_region").val());
		        $('#shiperRegionCodes').val($("#shiper_region").val());
		        $('#receiverRegionCodes').val($("#receiver_region").val());
		        $('#form1').submit();
		    });
	    });
	    /* 
	    var submitForm=function(){
		        $('#companyRegionCodes').val($("#company_region").val());
		        $('#shiperRegionCodes').val($("#shiper_region").val());
		        $('#receiverRegionCodes').val($("#receiver_region").val());
		        return true;
		        $('#form1').submit();
		};
	     */
	  $.formValidator.initConfig({formID:"form1",theme:"Default",submitOnce:true,wideWord:false,onError:function(){}});
	  <c:if test="${seller.sellerId ==null || seller.sellerId == ''}">
	  $("#business-name").formValidator({onShow:"由6~30位，英文，中文，数字和下划线组成",onFocus:"由6~30位，英文，中文，数字和下划线组成",onCorrect:"登陆名可以使用"})
	  	.regexValidator({regExp:"^[a-zA-Z0-9_\\u4E00-\\u9FA5]{6,30}$",dataType:"string",onError:"由6~30位，英文，中文，数字和下划线组成"})
	  	.inputValidator({min:6,max:30,onErrorMin:"请输入大于6个字符的登陆名",onErrorMax:"请输入不大于30个字符的登陆名",empty:{leftEmpty:false,rightEmpty:false,emptyError:"登陆名两边不能有空格"},onError:"你输入的品牌名称格式错误"})
	  	.ajaxValidator({ 
			dataType : "json",              
			async : true,              
			url : "${_ctxPath}/admin/user/user-validLoginName.htm",             
			success : function(data){  
				if(data.info==1){
					return false;
				}
				return true;              
			},              
			buttons: $("#submitForm"),              
			error: function(jqXHR, textStatus, errorThrown){
			alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);},              
			onError : "该登录名已经存在",              
			onWait : "正在对登录名进行合法性校验，请稍候..."          
		});
	  </c:if>
		$("#linkman").formValidator({onShow:"请输入2~20位英文或中文姓名",onFocus:"请输入2~20位英文或中文姓名"}).inputValidator({min:2,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"业务联系人两边不能有空格"},onError:"请输入2~20位英文或中文姓名"});
		$("#tel-text").formValidator({onShow:"请输入手机号码",onFocus:"请输入手机号",onCorrect:"谢谢你的合作"}).inputValidator({min:11,max:11,onError:"手机号码必须是11位的,请确认"}).regexValidator({regExp:"mobile",dataType:"enum",onError:"手机号码格式不正确"});
		$("#QQ").formValidator({empty:true,onShow:"请输入QQ号码",onFocus:"请输入QQ号码",onCorrect:"谢谢你的合作，你的QQ号码正确"}).inputValidator({min:5,max:10}).regexValidator({regExp:"qq",dataType:"enum",onError:"QQ号码格式不正确"});
		$("#fax").formValidator({empty:true,onShow:"请输入你的传真号码",onFocus:"请输入正确的传真号码",onCorrect:"谢谢你的合作"}).inputValidator({min:10,max:12,onError:"传真号码不正确，请确认"}).regexValidator({regExp:"tel",dataType:"enum",onError:"你输入的传真号码格式不正确"});;
		$("#dh").formValidator({empty:true,onShow:"请输入固定电话",onFocus:"例如：021-88888888或省略区号88888888",onCorrect:"谢谢你的合作，你的固定电话正确"}).regexValidator({regExp:"tel",dataType:"enum",onError:"国内电话格式不正确"});
	  	$("#company-name").formValidator({onShow:"由6~30个字符组成",onFocus:"由6~30个字符组成",onCorrect:"公司名称可以使用"}).inputValidator({min:6,max:30,empty:{leftEmpty:false,rightEmpty:false,emptyError:"公司名称两边不能有空格"},onError:"你输入的公司名称格式错误"});
		
	  	$("#company-type").formValidator({empty:true,onShow:"请填写3~20个字符",onFocus:"请填写3~20个字符",onCorrect:"公司类型可以使用"}).inputValidator({min:3,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"公司类型两边不能有空格"},onError:"请填写3~20个字符"});
 		$("#rq").formValidator({empty:true,onShow:"请输入成立日期",onFocus:"例如：2012-01",onCorrect:"成立日期正确"}).regexValidator({regExp:"^\\d{4}(\\-|\\/|\.)\\d{1,2}$",dataType:"string",onError:"日期格式不正确"});
		$("#regdit-zb").formValidator({empty:true,onShow:"请填写0~8位数字",onFocus:"请填写0~8位数字",onCorrect:"谢谢你的合作，你的注册资本数正确"}).inputValidator({min:0,max:8}).regexValidator({regExp:"decmal4",dataType:"enum",onError:"请填写0~8位数字"});
		$("#ss-zb").formValidator({empty:true,onShow:"请填写0~8位数字",onFocus:"请填写0~8位数字",onCorrect:"谢谢你的合作，你的实收资本数正确"}).inputValidator({min:0,max:8}).regexValidator({regExp:"decmal4",dataType:"enum",onError:"请填写0~8位数字"});
		
		$("#url").formValidator({empty:true,defaultValue:""}).inputValidator({min:3,max:100,onError:"你输入公司网站格式不正确"}).regexValidator({regExp:"url",dataType:"enum",onError:"你输入公司网站格式不正确"});
		$("input:checkbox").formValidator({tipID:"brandTip",onShow:"请选择可售品牌",onFocus:"你至少选择1个可售品牌",onCorrect:"恭喜你,你选对了"}).inputValidator({min:1,onError:"你至少选择1个可售品牌"});
		//$("#addr-info").formValidator({empty:true,tipID:"addrTip",onShow:"请输入0~30位的详细地址",onFocus:"请输入0~30位的公司详细地址"}).inputValidator({min:0,max:30,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},onError:"请输入0~30位的详细地址"});
		$("#add-f").on("click","select",function(){
	    	var obj = $(this).next("select");
	    	if(obj.length == 0){
	    		obj = $(this);
	    	}
	    	obj.formValidator({
		          onFocus:"请选择具体地区",
		          tipID:"addrTip-f"
		      }).inputValidator({min:1,onError: "请选择具体地区!"});
	    });
		$("#addr-info-f").formValidator({
			onShow:"请输入1~30位的详细地址",onFocus:"请输入1~30位的详细地址"}).inputValidator({
			min:1,
			max:30,
			empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},
			onError:"请输入1~30位的详细地址"});
		$("#add-t").on("click","select",function(){
	    	var obj = $(this).next("select");
	    	if(obj.length == 0){
	    		obj = $(this);
	    	}
	    	obj.formValidator({
		          onFocus:"请选择具体地区",
		          tipID:"addrTip-t"
		      }).inputValidator({min:1,onError: "请选择具体地区!"});
	    });
		$("#addr-info-t").formValidator({
			onShow:"请输入1~30位的详细地址",onFocus:"请输入1~30位的详细地址"}).inputValidator({
			min:1,
			max:30,
			empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},
			onError:"请输入1~30位的详细地址"});
		
		$("#juridicalPperson").formValidator({empty:true,onShow:"请输入2~20位英文或中文姓名",onFocus:"请输入2~20位英文或中文姓名"}).inputValidator({min:2,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},onError:"请输入2~20位英文或中文姓名"});
		$("#registration").formValidator({empty:true,onShow:"请输入税务登记号",onFocus:"请输入税务登记号",onCorrect:"谢谢你的合作，你的税务登记号正确"}).inputValidator({min:1,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},onError:"请填写1~20位字符"});
		$("#companyNum").formValidator({empty:true,onShow:"请输入企业编码",onFocus:"请输入企业编码",onCorrect:"谢谢你的合作，你的企业编码输入正确"}).inputValidator({min:0,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},onError:"请填写0~20位字符"});
		$("#registrationNum").formValidator({empty:true,onShow:"请输入企业法人营业执照注册号",onFocus:"请输入企业法人营业执照注册号",onCorrect:"你的企业法人营业执照注册号输入正确"}).inputValidator({min:15,max:15,onError:"请填写15位数字"}).regexValidator({regExp:"intege1",dataType:"enum",onError:"请填写15位数字"});
		
// 		$("#alipay").formValidator({tipID:"paynum",onShow:"请输入支付宝帐号",onFocus:"请输入支付宝帐号"}).inputValidator({min:1,max:30,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},onError:"请输入1~30位的支付宝帐号"});
// 		$("#alipayName").formValidator({tipID:"paynum",onShow:"请输入注册支付宝姓名",onFocus:"请输入注册支付宝姓名"}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},onError:"你输入的姓名错误"});
// 		$("#bank").formValidator({tipID:"bankTip",onShow:"请输入开户银行",onFocus:"请输入开户银行"}).inputValidator({min:3,max:30,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},onError:"你输入3~30位的银行名称"});
// 		$("#bankcardNum").formValidator({tipID:"bankTip",onShow:"请输入1~20位数字的银行卡号",onFocus:"请输入1~20位数字的银行卡号"}).inputValidator({min:1,max:20,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},onError:"请输入1~20位数字的银行卡号"});
// 		function allEmpty(val,elem){return ($("#alipay").val()=="" && $("#bank").val()=="")?'为了保证您能收到货款，请至少填写一种收<br/>款帐号！':true;} 
	
		$("#alipay,#bank,#alipayName,#bankcardNum").formValidator({
			tipID:"paynum",onFocus:"银行账号和支付宝账号至少填写一个",onCorrect:"输入正确！"
			})
			.functionValidator({
				fun : function() {
					var $alipay = $("#alipay"),
						$alipayName = $("#alipayName"),
					    $bank = $("#bank"),
					    $bankcardNum = $("#bankcardNum");
					    
					var alipayVal = $.trim($alipay.val()),
					    bankVal = $.trim($bank.val()),
					    alipayNameVal = $.trim($alipayName.val()),
					    bankcardNumVal = $.trim($bankcardNum.val());
					
					if(alipayVal.length == 0 && bankVal.length == 0
							&& alipayNameVal.length == 0 && bankcardNumVal.length == 0){
						return "银行账号和支付宝账号至少填写一个";
					}
					if(alipayVal.length > 0 || alipayNameVal.length > 0){
						var $span = $("#paynum");
						$("#paynum").remove();
						var $td = $alipayName.closest("tr").find("td:last");
						$td.append($span);
						
						if(alipayVal.length == 0 || alipayVal.length > 30){
							return"请输入1~30位的支付宝帐号";
						}
						if(alipayNameVal.length < 1 || alipayNameVal.length > 50){
							return "请输入1~50位的注册支付宝姓名";
						}
					}
					if(bankVal.length > 0 || bankcardNumVal.length > 0){
						var $span = $("#paynum");
						$("#paynum").remove();
						var $td = $bankcardNum.closest("tr").find("td:last");
						$td.append($span);
						
						if(bankVal.length < 3 || bankVal.length > 30){
	 						return "请输入3~30位的开户银行名称";
	 					}
						if(!/^\d{1,20}$/.test(bankcardNumVal)){
							return "请输入1~20位数字的银行卡号";
						}
					}
				}
			});
		$("#uploadImageValueId").formValidator({empty:true, onShow: "请输入图片名", onCorrect: "谢谢你的合作，你的图片名正确" }).inputValidator({min:1,onErrorMin:"请上传图片"}).regexValidator({ regExp: "picture", dataType: "enum", onError: "图片名格式不正确" });
		$('#businessScope').formValidator({
			empty:true,
			tipId : "businessScopeTip",
			onShow : "请填写经营范围",
			onFocus : "经营范围必须填写",
			onCorrect : "谢谢您的配合"
		}).functionValidator({
			fun : function() {
				var describe = businessScopeEditor.getData();
				if(describe.length == 0){
					return "请输入经营范围";
				}
// 				else if(describe.length > 100 || describe.length<3){
// 					return "请输入请输入3~100个字符个字符";
// 				}
			}
		});
		
		$('#remark').formValidator({
			tipId : "remarkTip",
			onShow : "请填写公司简介",
			onFocus : "公司简介必须填写",
			onCorrect : "谢谢您的配合"
		}).functionValidator({
			fun : function() {
				var remark = remarkEditor.getData();
				if(remark.length == 0){
					return "请输入公司简介";
				}
// 				else if(remark.length > 200 ||　remark.length<3){
// 					return "请输入3~200个字符";
// 				}
			}
		});
		$("#email").formValidator({
			onShow : "请输入邮箱",
			onFocus : "邮箱6-100个字符,输入正确了才能离开焦点",
			onCorrect : "恭喜你,你输对了"
		}).inputValidator({
			min : 6,
			max : 100,
			onError : "邮箱格式错误"
		}).regexValidator({
			regExp : "^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",
			onError : "你输入的邮箱格式不正确"
		}).ajaxValidator({
			type : "GET",
			url : "${_ctxPath}/admin/user/user-validateEmailIsRepate.htm",
			data : {
				"userId" : function(){return $("#sellerUserId").val();},
				"email" : function(){return $("#email").val();}
			},
			datatype : "json",
			async : "true",
			success : function(data) {
				var json = $.parseJSON(data);
				if (json.code == "false") {
					return false;
				}
				return true;
			},
			buttons: $("#submitForm"),
			error : function(jqXHR, textStatus, errorThrown) {
				alert("服务器没有返回数据，可能服务器忙，请重试" + errorThrown);
			},
			onError : "该邮箱已被使用，请更换邮箱",
			onWait : "正在对邮箱进行合法性校验，请稍候..."
		});
		
	  //品牌筛选
	  var bindCheckBox=function(){
		  $(':checkbox[name="seller.listBrandIds"]').bind('click',function(){
			  var brandName = $(this).next().text();
			  var st=$('#selectedBrandNames').text();
			  if(this.checked){
			 	 if($('#selectedBrandNames').text() == ''){
						$('#selectedBrandNames').text(brandName);
					}else{
						$('#selectedBrandNames').text(st + ',' + brandName);
					}
				}else{
					if(st.indexOf(brandName) == 0){
						if(st.indexOf(',')==-1){
							$('#selectedBrandNames').text(st.replace(brandName,''));
						}else{
							$('#selectedBrandNames').text(st.replace(brandName+',',''));
						}
					}else if(st.indexOf(brandName)>0){
						$('#selectedBrandNames').text(st.replace(','+brandName,''));
					}
				}
				
			});
	  };
		$(document).ready(function(){
			$("#add-f select").click();
			$("#add-t select").click();
			$(".sal-brand-t a").click(function(){
				var text = $(this).text();
				/* if(text.length > 1){
					text = "";
				} */
				var brand_temp = "brand-" + text;
				if(!$("#"+brand_temp)[0]){
					$.ajax({
						url : "${_ctxPath}/admin/brand/brand-listBrands.htm",
						type : "POST",
						data : {
							'brand.brandPinYin' : text,
							'brand.seller.sellerId': $('#sellerId').val()
						},
						dataType : "html",
						success : function(data) {
							if(data!='' && data!=null){
								var data="<div id=\""+brand_temp+"\" class=\"br-list\">"+data+"</div>";
								$(".sal-brand-t").after($(data));
								$("#"+brand_temp).show().siblings(".br-list").hide();
								$(':checkbox[name="seller.listBrandIds"]').unbind('click');
								bindCheckBox();
							}
						},
						error : function(data) {
							alert(data.responseText);
						}
					});
					
				}else{
					$("#"+brand_temp).show().siblings(".br-list").hide();
				}
				
			});
			$('#backList').bind('click', function(){
				  window.location.href="${_ctxPath}/admin/user/user-searchSellers.htm";
			});
			bindCheckBox();
		});
	  
	 </script>
</body>
</html>