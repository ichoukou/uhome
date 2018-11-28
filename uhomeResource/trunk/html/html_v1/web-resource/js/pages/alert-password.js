/**
 * Created with JetBrains PhpStorm.
 * User: admin
 * Date: 13-3-11
 * Time: 下午2:41
 * To change this template use File | Settings | File Templates.
 */
$(function(){
    var form = function () {
        //配置表单
        $.formValidator.initConfig({
            formID:"form1",
            theme:'yto',
            onSuccess:true,
            errorFocus:false,
            onSuccess:function () {
                console.log(1);
            },
            onError:function () {
                console.log(2);
            }
        });
       /* //判断密码
        $("#nowPwd").formValidator({
            onShowText:"请输入用户名",
            onShow:"请输入用户名,只有输入\"maodong\"才是对的",
            onFocus:"用户名至少5个字符,最多10个字符",
            onCorrect:"该用户名可以注册"
        }).inputValidator({
                min:5,
                max:10,
                onError:"你输入的用户名非法,请确认"
            })//.regexValidator({regExp:"username",dataType:"enum",onError:"用户名格式不正确"})
            .ajaxValidator({
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
            }).defaultPassed();*/

        //判断密码是否为空状态
        $("#password1").formValidator({
            onShow:"",
            onFocus:"请输入密码至少1个长度",
            onCorrect:"",
         tipID:'newmimaTips'
        }).inputValidator({
                min:1,
                onError:"密码不能为空,请确认"
            });
        //判断密码是否保持一致
        $("#password2").formValidator({
            onShow:"",
            onFocus:"至少1个长度",
            onCorrect:"",
            tipID:'resetmimaTips'
        }).inputValidator({
                min:1,
                onError:"重复密码不能为空,请确认"
            }).compareValidator({
                desID:"password1",
                operateor:"=",
                onError:"两次密码输入不一致，请重新输入"
            });

        // 保存
        $('#save').on('click', function () {
            var result = $.formValidator.pageIsValid('1');
            if (!result) {
                return false;   //验证不通过,直接返回
            }

            setTimeout(function () {
                $('#form1').trigger('submit');
            }, 0);

        });
    };

    form();
})