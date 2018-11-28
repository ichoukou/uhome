/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-4
 * Time: 下午4:52
 * Description:
 */
$(function(){
    $("#postComments").on("click",function(){
    	var result = $.formValidator.pageIsValid('1');
		if (!result) {
			return;
		}
    	$.ajax({
			type: 'POST',
			url: _ctxPath+'/suggest-saveSuggest.htm',
			data: $("#addForm").serialize(),
			success: function(msg){
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
					 window.setTimeout(function(){window.location.href=_ctxPath},3000);
				}
	        }
		});
    });
    
    $.formValidator.initConfig({
		validatorGroup : '1',
		formID : "addForm",
		theme : "yto",
		errorFocus : true,
		wideWord : false
	});
    
    
    
	$("#title").formValidator({
		onShowText:'请填写标题',
		onShow : "请填写标题",
		onFocus : "标题必须在1-30字之间",
		onCorrect : "谢谢你的配合"
	}).inputValidator({
		min : 1,
		max : 30,
		//empty:false,
		onError : "标题必须在1-30字之间"
	}).functionValidator({
        fun: function (val, elem) {
            if(!$.trim(val)){
                return "标题不能为空";
            }
        }
    });
	$("#content").formValidator({
		onShowText:'请填写内容',
		onShow : "请填写内容",
		onFocus : "内容必须在1-500字之间",
		onCorrect : "谢谢你的配合"
	}).inputValidator({
		min : 1,
		max : 500,
		//empty:false,
		onError : "内容必须在1-500字之间"
	}).functionValidator({
        fun: function (val, elem) {
            if(!$.trim(val)){
                return "内容不能为空";
            }
        }
    });
})
