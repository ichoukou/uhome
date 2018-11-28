/**
 * Created with JetBrains PhpStorm.
 * User: admin
 * Date: 13-3-7
 * Time: 下午1:34
 * To change this template use File | Settings | File Templates.
 */
/*默认文本*/
var _ctxPath=$('#_ctxPath').val();

function nextValidateCodePass() {
			$("#j_validateCodePass").attr("src",
					_ctxPath+"/validationCode.htm?date="+new Date());
		}
$(function(){
//    $('.input-tip').each(function(){
//        $(this).Merlin({
//            // 文本域 设置默认值
//            "inputFocus":{"setDefault":true}
//        });
//
//    })
	//验证补丁
	$(".pwd-cont input").on("focus",function(){
		$(this).parents("tr").next("tr").find(".error").remove();
	});
	//验证
    $.formValidator.initConfig({
	    validatorGroup: '1010',
	    formID: 'getpassFrom',
	    errorFocus:false, //错误时不聚焦到第一个控件
	    theme: 'yto'
	});
	
	$("#username").formValidator({
		validatorGroup: '1010',
		onShowText:"请输入您注册的邮箱地址",
        onShow:"",
        onFocus:"请输入您注册的邮箱地址",
        onCorrect:"",
        tipID:'usernameTips'
	}).inputValidator({
    	min:6,
    	max:50,
        onError:"登录名长度不正确,请确认"
    });
//	$("#j_captcha_1").formValidator({
//		validatorGroup: '1010',
//		onShow:"",
//        onFocus:"请填写验证码",
//        tipID:'j_captchaTips'
//	}).inputValidator({
//    	min:4,
//    	max:4,
//      onError:"验证码输入不正确"
//    });
//    .ajaxValidator({
//    	type:"post",
//    	url:_ctxPath+"/user/user-ajaxJCaptchaValidator.htm",
//    	dataType:"json",
//    	data:{"jCaptchaResponse":$("#j_captcha_1").val()},
//    	success:function(data){
//    		console.log(data.code);
//    		 if(data.code){
//    			 return true;
//    		 }else{
//    			 return false;
//    		 }
//    	},
//    	onError:"验证码错误"
//    });

    $("#newPsw").formValidator({
        validatorGroup: '1010',
        onShow:"请输入至少6位以上数字、字母或符号",
        onFocus:"请输入至少6位以上数字、字母或符号",
        onCorrect:"",
        tipID: "newPswTips"
    }).inputValidator({
        min:6,
        max:20,
        empty:{
            leftEmpty:false,
            rightEmpty:false,
            emptyError:"密码两边不能有空符号"},
        onError:"密码格式错误,请确认"
    }).regexValidator({regExp:"^[^\\s]{1,}$",onError:"密码格式不正确"});;
    $("#password").formValidator({
        validatorGroup: '1010',
        onShow:"请确认本次输入和上次输入字符一致",
        onFocus:"请确认本次输入和上次输入字符一致",
        onCorrect:"",
        tipID: "passwordTips"
    }).inputValidator({
        min:6,
        max:20,
        empty:{
            leftEmpty:false,
            rightEmpty:false,
            emptyError:"重复密码两边不能有空符号"},
        onError:"重复密码格式错误,请确认"
    }).compareValidator({
        desID:"newPsw",
        operateor:"=",
        onError:"两次密码输入不一致，请重新输入"
    });

    //提交
    $("#save").on("click",function(){
    	//校验数据格式
    	var result = $.formValidator.pageIsValid('1010');
    	if(result){
	    	var getpassFrom =$('#getpassFrom');
	    	getpassFrom.submit();
	    	return true;
    	}else{
    		return false;
    	}
    })
})
