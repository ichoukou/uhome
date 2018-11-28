//点击输入验证码
function nextValidateCode() {
	$("#j_validateCode").attr("src",_ctxPath+'/validationCode.htm?date='+new Date());
	return false;
}
//注册输入验证码
function nextValidateCodeRegister(){
	$("#j_validateCode_register").attr("src",_ctxPath+'/validationCode.htm?date='+new Date());
	return false;
}
//注册输入验证码
function nextValidateCodeRegister(){
	$("#j_validateCode_register").attr("src",_ctxPath+'/validationCode.htm?date='+new Date());
}
//记住我多少天
function checkMe(){
	var f = $('#safeLogin').attr("checked");
	if (f == "checked"){
		//$(remberMe).attr("name", "_spring_security_remember_me");
		setCookie("xlbuy365username",$('#j_username').val());
	}
	else{
		//$(remberMe).attr("name", "");
		delCookie("xlbuy365username");
	}
}
//设置cookies
function setCookie(name,value)
{
    var exp = new Date();
    exp.setTime(exp.getTime() + 2592000000);
    document.cookie = name + "="+ escape (value) + ";path=/;expires=" + exp.toGMTString();
}

//读取cookies
function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}

//删除cookies
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}


$(function(){
	var xlbuy365username = getCookie("xlbuy365username");//登录名
	$("#j_username").val(xlbuy365username);
	
	var opertNum = $('#opertNum').val();//操作数
	if(!opertNum){
		$('#opertNum').val(1);
		opertNum =1;
	}
	//登陆
    $("#login_btn").on("click",function(){
//        var flag = false;
//        var useNmVal = $.trim($("#j_username").val());
//        var pswVal = $.trim($("#j_password").val());
    	if(opertNum>3&&$("#j_captcha").val()==""){
    		$('#yanzhengma').show();
    		 showTipMsg('#verifiCode','请输入验证码');
    		$('#verifiCode').focus();
    		 return;
    	}
//        if(useNmVal.length > 0 && pswVal.length >0){
//            flag = true;
//        }
//        if(useNmVal.length == 0){
//          $("#account").show().children().text("请输入登录名");
//        }else if(useNmVal.length > 0){
//            $("#account").hide();
//        }
//        if(pswVal.length == 0){
//          $("#passWord").show().children().text("请输入密码");
//        }else if(pswVal.length > 0){
//            $("#passWord").hide();
//        }
    	var result = $.formValidator.pageIsValid('1'); // 组号必须用带引号
    	
        if (result) {
    	//if (flag) {
    		//登陆
    		var login_form = $('#login_form');
        	$.ajax({
    			type: 'POST',
    			url:_ctxPath+'/user/user-checkUserName.htm',
    			data: login_form.serializeArray(),
    			success:function(data){
    				var yanzhengma = $('#yanzhengma');//验证码
    				switch(data.info){
    					case '0':
    					case '1':
    					case '2':
    					case '3':
    					case '4':
    					case '5':
    					case '8':
    				    returnMsg(yanzhengma,data.info,'login');
    				    break;
    				    case '6':
    				    	 checkMe();
    				      	$('#tname').val( $('#j_username').val());
    						$('#tpassword').val($('#j_password').val());
    						if(opertNum>=3){
    							$('#timg').val($('#j_captcha').val());
    						}
    						var true_from =$('#true_form');
    						//console.log(true_from);
    						true_from.submit();
    						break;	  
    				}
    			},
    			dataType:'json'
    			});
    	}
    	
    	
    });
    
  //验证Email
//    $("#r_userName").on("blur",function(){
//    //$("#r_password").on("focus",function(){
//    		//登陆
//    		var login_form = $('#registerForm');
//        	$.ajax({
//    			type: 'POST',
//    			url:_ctxPath+'/user/user-registerCheck.htm',
//    			data: login_form.serializeArray(),
//    			success:function(data){
//    				var yanzhengma = $('#yanzhengma');//验证码
//    				if(data.info=='1'){
//                        returnMsg(yanzhengma,data.info,'r_userNameReg');//邮箱重复
//    				}
//    			}
//    			});
//    });

    //后台传回的信息
    function returnMsg(yzm,stuts,opert){
    	if(opert == 'login'){
    		opertNum++;
    		$('#opertNum').val(opertNum);
    		if(opertNum>3){
    			yzm.show();
    			$('#j_captcha').val('');
    			if(opertNum!=4){
    				nextValidateCode();//刷新验证码
    			}
    		};
    	}
    	switch(stuts){
		case '0':
			showTipMsg('#passWord','登录名或密码错误')
	    	  break;
	      case '1':
	    	  if(opert=='login'){
	    		  showTipMsg('#account','该用户已冻结')
	    	  }else{
	    		  showTipMsg('#r_userNameReg','该帐号已存在');
	    		  nextValidateCodeRegister();
	    	  }
	    	  break;	  	  
	      case '3':
	    	  showTipMsg('#verifiCode','验证码错误')
	    	 // nextValidateCode();//刷新验证码
	    	  break;
	      case '5':
	    	  if(opert=='login'){
	    		  showTipMsg('#passWord','登录名或密码错误')
	    	  }else{
	    		  showTipMsg('#r_emailReg','邮箱已存在，请重新输入其他邮箱')
	    	  }
	    	 
	    	  break;
	      case '7':
	    	  showTipMsg('#r_userNameReg','注册异常')
	    	  break;		  
	      case '8':
	    	  showTipMsg('#passWord','登录名或密码错误')
	    	  break;	  
    	  }
    };
    $("#j_captcha_register").bind("focus",function(){
     	$("#verifiCodeRegister").html("");
    });
    //注册
    $("#registerBtn").on("click",function(){
    	var yanzhengma = $("#j_captcha_register").val();
    	if(yanzhengma ==""){
    		$('#registerYanzhengma').show();
    		 showTipMsg('#verifiCodeRegister','请输入验证码');
    		$('#verifiCodeRegister').focus();
    		 return;
    	}
        var result = $.formValidator.pageIsValid('2'); // 组号必须用带引号
        if (result) {
            var register_form =$('#registerForm');
            $.ajax({
                type: 'POST',
                url: _ctxPath+'/user/user-userRegister.htm',
                data: register_form.serializeArray(),
                success:function(data){
                    switch(data.info){
                        case '1':
                            returnMsg(yanzhengma,data.info,'r_userNameReg');//账号重复
                            break;
                        //case '5':
                          //  returnMsg(yanzhengma,data.info,'r_emailReg');//邮箱重复
                            //break;
                        case '3':
                          showTipMsg('#verifiCodeRegister','验证码错误');
                          $("#j_captcha_register").val('');
                          nextValidateCodeRegister();
              	    	 // nextValidateCode();//刷新验证码
              	    	  break;
                        case '6':
                            $('#tname').val( $('#r_userName').val());
                            $('#tpassword').val($('#r_password').val());
                            /*	if(opertNum>=3){
                             $('#timg').val($('#j_captcha').val());
                             }*/
                            var true_from =$('#true_form');
                            true_from.submit();
                            break;
                        case '7':
                            returnMsg(yanzhengma,data.info,'r_userNameReg');//注册异常
                            break;
                    }
                },
                dataType:'json'
            });
        }
    });

    //阅读条款-如果不同意服务条款，则不允许注册，并做出提示
    $("#readed").on("click",function(){
        if(!$(this).attr("checked")){
            $("#registerBtn").off().on("click",function(){
                $.dialog({
                    title: false,
                    cancel:false,
                    content: "<span style='color:#C00;'>您未同意服务条款，无法进行注册操作<br/>请勾选同意服务条款后再进行注册!</span>",
                    padding: "10px 20px",
                    ok: false,
                    follow: $("#registerBtn").get(0),
                    time: 2000
                });
            });
        }else{
            //注册
            $("#registerBtn").off().on("click",function(){
            	var yanzhengma = $("#j_captcha_register").val();
            	if(yanzhengma ==""){
            		$('#registerYanzhengma').show();
            		 showTipMsg('#verifiCodeRegister','请输入验证码');
            		$('#verifiCodeRegister').focus();
            		 return;
            	}
                var result = $.formValidator.pageIsValid('2'); // 组号必须用带引号
                if (result) {
                    var register_form =$('#registerForm');
                    $.ajax({
                        type: 'POST',
                        url: _ctxPath+'/user/user-userRegister.htm',
                        data: register_form.serializeArray(),
                        success:function(data){
                            switch(data.info){
                                case '1':
                                    returnMsg(yanzhengma,data.info,'r_userNameReg');//账号重复
                                    break;
                                //case '5':
                                  //  returnMsg(yanzhengma,data.info,'r_emailReg');//邮箱重复
                                    //break;
                                case '3':
                                    showTipMsg('#verifiCodeRegister','验证码错误');
                                    $("#j_captcha_register").val('');
                                    nextValidateCodeRegister();
                        	    	 // nextValidateCode();//刷新验证码
                        	    	break;
                                case '6':
                                    $('#tname').val( $('#r_userName').val());
                                    $('#tpassword').val($('#r_password').val());
                                    /*	if(opertNum>=3){
                                     $('#timg').val($('#j_captcha').val());
                                     }*/
                                    var true_from =$('#true_form');
                                    true_from.submit();
                                    break;
                                case '7':
                                    returnMsg(yanzhengma,data.info,'r_userNameReg');//注册异常
                                    break;
                            }
                        },
                        dataType:'json'
                    });
                }
            });
        }
    });

    /* *
    * 监听回车键
    * */
    //登录
    $("#j_password,#j_username,#j_captcha").on("keyup",function(e){
        var code = (e.keyCode ? e.keyCode : e.which);
        if (code == 13) {
            $("#login_btn").trigger('click');
        }
    });
    //注册
    $("#r_userName,#r_password,#r_querypassword","#j_captcha_register").on("keyup",function(e){
        var code = (e.keyCode ? e.keyCode : e.which);
        if (code == 13) {
            $("#registerBtn").trigger('click');
        }
    });

    function showTipMsg(id,msg){
    	$(id).removeClass("onCorrect").addClass("onError").html('<span class="onError-txt">' + msg + '</span>').show();
    };
})
