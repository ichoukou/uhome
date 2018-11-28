/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-4
 * Time: 下午4:52
 * Description:
 */
	//验证
    $.formValidator.initConfig({
	    validatorGroup: '1001',
	    formID: 'form1',
	 	errorFocus:false,
	    theme: 'yto'
	});
	
	$("#addName").formValidator({
		validatorGroup: '1001',
        onShow:"",
        onFocus:"请填写收获人姓名",
        onCorrect:"",
        tipID:'addNameTips'
	}).inputValidator({
		 min:1,
    	 max:32,
    	 empty:false,
        onError:"收货人姓名长度不正确(不能为空格),请确认"
    });
    $("#addAddress").formValidator({
    	validatorGroup: '1001',
        onShow:"",
        onFocus:"请填写街道地址",
        onCorrect:"",
        tipID:'addressTips'
    }).inputValidator({
    	min:6,
    	max:100,
    	empty:false,
        onError:"街道地址长度不正确(不能为空格),请确认"
    });
    $("#zipcode").formValidator({
    	validatorGroup: '1001',
        onShow:"",
        onFocus:"请填写邮编",
        onCorrect:"",
        empty:true,
        tipID:'zipcodeTips'
    }).inputValidator({
        min:6,
        max:6,
        onError:"你输入的邮编长度不正确,请确认"
    }).regexValidator({regExp:"zipcode",dataType:"enum",onError:"你输入的邮编格式不正确"});;
    $("#telephone").formValidator({
    	validatorGroup: '1001',
        onShow:"",
        onFocus:"手机号码11个数字",
        onCorrect:"",
        tipID:'telephoneTips'
    }).inputValidator({
        min:11,
        max:11,
        onError:"你输入的手机号码长度不正确,请确认"
    }).regexValidator({regExp:"mobile",dataType:"enum",onError:"你输入的手机号码格式不正确"});
    $("#phoneNum").formValidator({
    	validatorGroup: '1001',
        onShow:"",
        onFocus:"请输入电话号码",
        onCorrect:"",
     	empty:true,
        tipID:'phoneNumTips'
    }).inputValidator({
        min:11,
        max:13,
        onError:"你输入的电话号码长度不正确,请确认"
    }).functionValidator({
        fun: function (val, elem) {
            var reg=/^\d{3,4}-\d{7,8}$/;
            if(!reg.test(val)){
                return "你输入的电话号码格式不正确";
            }
        }
    });
  //验证三级联动地址
 	$(".linkagesel").formValidator({
    	validatorGroup: '1001',
        onShow:"请选择地区",
        onFocus:"请选择具体地区",
        onCorrect:"",
        tipID:'provinceTips'
    }).inputValidator({min:1,onError: "请选择具体地区!"});
var _ctxPath =$('#_ctxPath').val();
var prov = $("#area_prov").val(), city = $("#area_city").val(), district = $("#area_region").val();
var linkage = new linkageSelect();
linkage.init({
    "oneSel":['#province','请选择',prov],
    "twoSel":['#city', '请选择',city],
    "threeSel":['#region', '请选择',district]
});

//点击输入验证码
function nextValidateCode() {
	$("#validateCodeimg").attr("src",_ctxPath+'/validationCode.htm?date='+new Date());
}
function checkImg(){
	if($.isEmptyObject($('#jCaptchaResponseImg').val())){
		return false;
	}else{
		$.ajax({
			type: 'POST',
			url: _ctxPath+'/address/address-checkImg.htm',
			data:{'jCaptchaResponse':$('#jCaptchaResponseImg').val()},
			success: function(data){
				if('false' == data.code || 'true' == data.code){
					$("#jCaptchaResponseImgTips").text("    "+data.info);
					if('false' == data.code){
						nextValidateCode();
						return false;
					}
					nextValidateCode();
					return true;
				}
		    },
			dataType:'json'
			});
	}
}

function setDefualt(receiverAddressId){
			$.ajax({
				type: 'POST',
				url: _ctxPath+'/address/address-setDefualtReceiverAddress.htm',
				data: {'receiverAdd.receiverAddressId':receiverAddressId},
				success: function(data){
					 $.dialog({
			                title: false,
			                content:data.info ,
			                time: 5000
			            });
					 window.location.reload();
					 $(".d-close").hide();
			        },
				dataType:'json'
				});
		}

function setDel(receiverAddressId){
	 $.dialog({
		title: false,
		content: "<span style='color:#C00;'>您确定要删除此条地址？</span>",
        ok: function(){
            $.ajax({
                type: 'POST',
                url:_ctxPath+'/address/address-delReceiverAddress.htm',
                data: {
                    'receiverAdd.receiverAddressId':$.trim(receiverAddressId)
                }
                ,
                success: function(data){
                    $.dialog({
                        title: false,
                        content:data.info,
                        time: 5000
                    });
                    $(".d-close").hide();
                    window.location=_ctxPath+"/address/address-getReceiverAddress.htm";
                },
                dataType:'json'
            });
        },
        okValue: "确定",
        cancel: true,
        cancelValue: "取消"
	 });
    $(".d-close").hide();
}

	var save =$('#save');
	var checkLoad =$('#checkLoad');
	var isDefault = $('#isDefault');
	//初始化确定选择状态
	if(isDefault.val()==1){
		checkLoad.attr("checked",true);
	}else{
		checkLoad.attr("checked",false);
	}
	$('#addAddress').val($('#true_address').val());
	//点击的时候确定设置的状态
	checkLoad.click(function(){
		if(checkLoad.attr("checked")=='checked'){
			isDefault.val('1');
		}else{
			isDefault.val('0');
		}
	});
	var opert = $('#opert').val();
	if(opert=='edit')
		{	
			$('#addressTitle').html('编辑收货地址');
		}else{
			$('#addressTitle').html('新增收货地址');
		}
	save.click(function(){
		var opert =$('#opert');
        $('#true_address').val($('#addAddress').val());
    	$('#regionId').val($("#region").val());
		var form1 = $('#form1');
		if(!opert.val()){
			opert.val('update');
		}
		if(!isDefault.val()){
			isDefault.val(0);
		}
		//校验数据格式
    	var result = $.formValidator.pageIsValid('1001');
    	if(result){
			$.ajax({
			type: 'POST',
			url: _ctxPath+'/address/address-addReceiverAddress.htm',
			data: form1.serialize(),
			success: function(data){
				//有验证码且错误
				if(data.code == 'false'){
					$("#jCaptchaResponseImgTips").html("<span class='onError-txt'>"+data.info+"</span>").addClass("onError").show();
					nextValidateCode();
					return;
				}
				 $.dialog({
	                title: false,
	                content:data.info,
	                time: 5000
		            });
				 $(".d-close").hide();
				window.location=_ctxPath+'/address/address-getReceiverAddress.htm';
		        },
			dataType:'json'
			});
    	}else{
    		nextValidateCode();
    		return false;
    	}
	});
 function updateAddress(receiverAddressId){
		window.location=_ctxPath+"/address/address-getSingleReceiverAddress.htm?receiverAdd.receiverAddressId="+receiverAddressId+"&opert=edit";
 }
		