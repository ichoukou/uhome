/**
 * Created with JetBrains PhpStorm.
 * User: admin
 * Date: 13-3-4
 * Time: 下午2:54
 * To change this template use File | Settings | File Templates.
 */
$(function () {
    $('.addr-tab').Merlin({
        "interlaced":{
            "trList":".list-tr",
            "iClassName":'tr-white'
        }
    });
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

            }
        });
        //判断名字是否为空
        $(".add-name").formValidator({
            onShow:"",
            onFocus:"请输入收货人姓名",
            onCorrect:"",
            tipID:'addNameTips'
        }).inputValidator({
                min:1,
                onError:"用户名不为空"
            });
        //城市联动
        var area = {
            data:districtData,
            selStyle:'margin-left:3px;',
            select:['#province']
        };

        var linkageSel = new LinkageSel(area);

        // 请选择省份
        $('#province').formValidator({
            tipID:'provinceTips',
            onShow:"",
            onFocus:'',
            onCorrect:function () {
                return '';
            }
        }).inputValidator({
                min:1,
                onError:"请选择省份"
            });

        // 市
        var city = function (obj, tipID) {
            var city = obj.next('select');
            if (city.val() === '') {
                city.formValidator({
                    'tipID':'provinceTips',
                    'onShow':'',
                    'onFocus':'',
                    'onCorrect':function () {
                        return '';
                    }
                }).inputValidator({
                        'min':1,
                        'onError':"请选择城市"
                    });
            }
        };
        // 区
        var area = function (obj, tipID) {
            var area = obj.next('select').next('select');
            area.formValidator({
                tipID:'provinceTips',
                onShow:'',
                onFocus:'',
                onCorrect:''
            }).functionValidator({
                    fun:function () {
                        if (!area.is(':hidden')) {
                            if (area.val() == '') {
                                var $tip = $('#' + tipID);
                                $tip.html('<span class="yto_onError">请选择地区</span>');
                                setTimeout(function () {
                                    $tip.show();
                                }, 0);
                            }
                        }
                    }
                });
        };

        //街道是否为空
        $("#addAddress").formValidator({
            onShow:"",
            onFocus:"请输入街道地址",
            onCorrect:"",
            tipID:'addressTips'
        }).inputValidator({
                min:1,
                onError:"街道地址不能为空"
            });
        //判断邮箱是否为空

        $("#addrEmail").formValidator({
            onShow:"",
            onFocus:"",
            onCorrect:"",
            tipID:'emailTips',
            empty:true //  非必填
        }).inputValidator({
                min:6,
                onError:"你输入的邮箱长度非法,请确认"
            }).regexValidator({
                regExp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",
                onError:"你输入的邮箱格式不正确"
            });
        //手机是否为空
        $("#telephone").formValidator({
            onShow:"",
            onFocus:"请输入手机号",
            onCorrect:"",
            tipID:'telephoneTips',
            empty:true
        }).inputValidator({
                min:11,
                max:11,
                onError:"手机号码必须是11位的,请确认"
            }).regexValidator({
                regExp:"mobile",
                dataType:"enum",
                onError:"你输入的手机号码格式不正确"
            });

        // 保存
        $('#save').on('click', function () {
            // 新增 3联动验证方法
            city($('#province'), 'provinceTips');
            area($('#province'), 'provinceTips');
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

});
