/**
 * 品牌合作页面js
 */
$(function(){
	$("#postComments").click(function() {
		var result = $.formValidator.pageIsValid('1');
		if (!result) {
			return;
		}
		$.ajax({
			type : 'POST',
			url : _ctxPath + '/supplier-saveSupplier.htm',
			data : $("#addForm").serialize(),
			success : function(msg) {
				if(msg.info == "captchaError"){
					$("#captchaTip").text("验证码错误");
					$("#changeCaptcha").click();
					$("#captcha").select();
				}else if(msg.code == "true"){
					$("#captchaTip").empty();
					$.dialog({
		                title: false,
		                content: msg.info,
		                time: 3000
		            });
					$(".d-close").hide();
					setTimeout("window.location.replace(_ctxPath)", 3000);
				}
			},
			error : function(msg) {
				$.dialog({
	                title: false,
	                content: "保存出错",
	                time: 3000
	            });
				$(".d-close").hide();
			}
		});
	});
	
	$.formValidator.initConfig({
		validatorGroup : '1',
		formID : "addForm",
		theme : "yto",
		errorFocus : false,
		wideWord : false
	});
	$("#brandName").formValidator({
		onShow : "请填写品牌名称",
		onFocus : "品牌名称必须填写",
		onCorrect : "谢谢你的配合"
	}).inputValidator({
		min : 1,
		onError : "请填写品牌名称!"
	}).functionValidator({
        fun: function (val, elem) {
            if(!$.trim(val)){
                return "品牌名称不能为空";
            }
        }
    });
	$("#contactName").formValidator({
		onShow : "请填写您的姓名",
		onFocus : "姓名必须填写",
		onCorrect : "谢谢你的配合"
	}).inputValidator({
		min : 1,
		onError : "请填写您的姓名!"
	}).functionValidator({
        fun: function (val, elem) {
            if(!$.trim(val)){
                return "您的姓名不能为空";
            }
        }
    });
	$("#telephone").formValidator({
		onShow : "请填写联系电话",
		onFocus : "联系电话必须填写",
		onCorrect : "谢谢你的配合"
	}).inputValidator({
		min : 1,
		onError : "请填写联系电话!"
	}).regexValidator({
		regExp : "mobile",
		dataType : "enum",
		onError : "你输入的手机号码格式不正确"
	});
});