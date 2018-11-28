/**
 * Created with JetBrains PhpStorm.
 * User: admin
 * Date: 13-3-11
 * Time: 下午2:41
 * To change this template use File | Settings | File Templates.
 */
$(function(){
	var _ctxPath=$('#_ctxPath').val();
	//清楚浏览器默认填写密码
	$("#nowPwd").attr("value","");
	$("#password1").attr("value","");
	$("#password2").attr("value","");
   // var form = function () {
        //配置表单
        $.formValidator.initConfig({
    		validatorGroup: '1',
            formID: 'form1',
    	    theme: 'yto'
        });
        //判断密码
        $("#nowPwd").formValidator({
    		validatorGroup: '1',
            onShow:"",
            onFocus:"请输入6~20位的密码",
            onCorrect:"",
            tipID:'nowPwdTips'
        }).inputValidator({
                min:6,
                max:20,
                onErrorMin:"请输入大于6位的密码",
                onErrorMax:"请输入小于20位的密码"
            }).regexValidator({regExp:"^[^\\s]{1,}$",onError:"密码格式不正确"});
            /*.ajaxValidator({
                dataType : "json",
                async : true,
                url : "http://www.yhuan.com/Handler.ashx",
                success : function(data){
                    if( data == "0" ) return true;
                    return "该用户名不可用，请更换用户名";
                },
                buttons: $("#save"),
                error: function(jqXHR, textStatus, errorThrown){
                    alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);
                },
                onError : "该用户名不可用，请更换用户名",
                onWait : "正在对用户名进行合法性校验，请稍候..."
            })*/

        //判断密码是否为空状态
        $("#password1").formValidator({
    		validatorGroup: '1',
            onShow:"",
            onFocus:"请输入6~20位的新密码",
            onCorrect:"",
            tipID:'newmimaTips'
        }).inputValidator({
                min:6,
                max:20,
                onErrorMin:"请输入大于6位的密码",
                onErrorMax:"请输入小于20位的密码"
            }).regexValidator({regExp:"^[^\\s]{1,}$",onError:"密码格式不正确"});
        //判断密码是否保持一致
        $("#password2").formValidator({
    		validatorGroup: '1',
            onShow:"",
            onFocus:"请输入6~20位的密码",
            onCorrect:"",
            tipID:'resetmimaTips'
        }).inputValidator({
                min:6,
                max:20,
                onErrorMin:"请输入大于6位的密码",
                onErrorMax:"请输入小于20位的密码",
                onError:"两次密码不一致,请重新输入"
            }).compareValidator({
                desID:"password1",
                operateor:"=",
                onError:"两次密码不一致,请重新输入"
            });
        // 保存
        $('#save').on('click', function () {
            var result = $.formValidator.pageIsValid('1');
            var form1 = $('#form1');
            if (!result) {
                return false;   //验证不通过,直接返回
            }else{
	            $.ajax({
					type: 'POST',
					url: _ctxPath+'/user/user-updatePassWord.htm',
					data: form1.serializeArray(),
					success: function(data){
						var innnerHtml;
						if(data.info ==5){
							$("#nowPwdTips").show().addClass("onError").html("<span class=\"onError-txt\">您输入的密码有误！</span>");
							innnerHtml = '您输入的密码有误!';
						}else if(data.info == 6){
							innnerHtml = '修改成功!';
							$("#nowPwd").attr("value","");
							$("#password1").attr("value","");
							$("#password2").attr("value","");
							$.dialog({
				                title: false,
				                content: innnerHtml,
				                time: 2000
				            });
						}
						 $(".d-close").hide();
				        },
					dataType:'json'
				});
			}
        });
   // form();
})