<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<div class="address-wrap" id="addressWrap">               
	<form id="addressForm" >
		<div class="line">
      <div class="tit"><i>*</i>收货人姓名：</div>
      <div class="info">
      	<input id="v_receiverName" type="text" value="${addr.receiverName}" name="addr.receiverName" >
    		<div><p id="e_v_receiverName"></p></div>
    	</div>
    </div>
	    <div class="line">
	        <div class="tit"><i>*</i>所在地区：</div>
	        <div class="info" id="pcsAvalid">
	        	<select id="province" class="linkagesel m-mr5p"></select>
                <select id="city" class="linkagesel m-mr5p"></select>
                <select id="region" class="linkagesel"></select>
	        	<div><p id="e_v_province"></p></div>
	        	<input type="hidden"  id="regionCode" value="${addr.regionCodes}" name="addr.regionCodes"/>
	        	<input type="hidden"  id="regionId" value="${addr.regionId}" name="addr.regionId">
	            <input type="hidden"  id="area_prov" value='${addr.regionModel.provinceId}'/>
				<input type="hidden"  id="area_city" value='${addr.regionModel.cityId}'/>
				<input type="hidden"  id="area_region" value='${addr.regionModel.countyId}'/>
	        </div>
	    </div>
	    <div class="line">
        <div class="tit"><i>*</i>街道地址：</div>
        <div class="info">
        	<textarea name="addr.receiveAddress" id="v_receiveAddress" cols="30" rows="10" class="textarea">${addr.receiveAddress}</textarea>
      		<div><p id="e_v_receiveAddress"></p></div>
      	</div>
	    </div>
	    <div class="line">
        <div class="tit">邮编：</div>
        <div class="info">
        	<input id="v_postCode" type="text" value="${addr.postCode}" name="addr.postCode" >
      		<div><p id="e_v_postCode"></p></div>
      	</div>
    	</div>
	    <div class="line">
        <div class="tit"><i>*</i>移动电话：</div>
        <div class="info">
        	<input id="v_mobile" type="text"  value="${addr.mobile}" name="addr.mobile" maxlength="11" >
        	<div><p id="e_v_mobile"></p></div>	
      	</div>
	    </div>
	    <div class="line">
        <div class="tit">固定电话：</div>
        <div class="info">
        	<input type="text" value="${addr.telephone}"  name="addr.telephone"  style="width: 115px;" id='telephone'>
        	（格式如：021-5485624）
        	<div><p id="e_v_telphone"></p></div>
        </div>
	    </div>
	    <div class="line">
	        <div class="tit">设为默认：</div>
	        <div class="info">
	        <span>
		        <label>
		        	<input type="checkbox" id='ckb_default' <c:if test="${addr.isDefault == 1 }">checked</c:if>>设置为默认地址
		        	<input type="hidden" value="${addr.isDefault}" name="addr.isDefault"  id='isDefault'/>
		        </label>
	        </span>设置后系统将在购买时自动选中该收货地址</div>
	    </div>
	    <input type="hidden" value="${addr.receiverAddressId}" name="addr.receiverAddressId" />
	</form>
</div>
    <script src="${_jsPath}/plugin/select/linkage_sel.js"></script>
    <script type="text/javascript">
	$(function(){
        //多级联动
        var prov = $("#area_prov").val(), city = $("#area_city").val(), district = $("#area_region").val();
        var linkage = new linkageSelect();
        linkage.init({
            "oneSel":['#province','请选择',prov],
            "twoSel":['#city', '请选择',city],
            "threeSel":['#region', '请选择',district]
        });
    	//验证
        $.formValidator.initConfig({
    	    validatorGroup: '101',
    	    formID: 'addressForm',
    	    errorFocus:false,
    	    theme: 'yto'
    	});
       //验证三级联动地址
	 	$(".linkagesel").formValidator({
        	validatorGroup: '101',
            onShow:"请选择地区",
            onFocus:"请选择具体地区",
            onCorrect:"",
            tipID:'e_v_province'
        }).inputValidator({min:1,onError: "请选择具体地区!"});
        $("#v_postCode").formValidator({
        	validatorGroup: '101',
        		empty:true,
        		onEmpty:"",
            onShow:"",
            onFocus:"请输入6位数字的邮政编码",
            onCorrect:"",
            tipID:'e_v_postCode'
        }).inputValidator({
        	min:6,
            max:6,
            onError:"你输入邮政编码长度不正确,请确认"
        });
        $("#v_receiveAddress").formValidator({
        	validatorGroup: '101',
            onShow:"",
            onFocus:"街道地址5-100个字符内",
            onCorrect:"",
            tipID:'e_v_receiveAddress'
        }).inputValidator({
            min:5,
            max:100,
            onError:"你输入的街道地址长度不正确,请确认"
        }).regexValidator({
            regExp:"^[^\>\<\.\?\&]{5,100}$",
            onError:"你输入的街道地址格式不正确"
        });
        $("#v_receiverName").formValidator({
        	validatorGroup: '101',
            onShow:"",
            onFocus:"姓名1-32个字符",
            onCorrect:"",
            tipID:'e_v_receiverName'
        }).inputValidator({
            min:1,
            max:32,
            onError:"你输入的姓名长度不正确,请确认"
        }).regexValidator({
            regExp:"^[^\>\<\.\?\&]{1,32}$",
            onError:"你输入的街道地址格式不正确"
        });
        $("#v_mobile").formValidator({
        	validatorGroup: '101',
            onShow:"",
            onFocus:"手机号码11个数字",
            onCorrect:"",
            tipID:'e_v_mobile'
        }).inputValidator({
            min:11,
            max:11,
            onError:"你输入的手机号码长度不正确,请确认"
        }).regexValidator({
        	regExp:"^1\\d{10}$",
        	datatype:"string",
            onError:"你输入的手机号码格式不正确"
        });
    });
    </script>