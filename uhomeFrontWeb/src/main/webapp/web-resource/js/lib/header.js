/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-5
 * Time: 下午6:07
 * Description:
 */

//收藏本站
function addFavorite() {
		var sURL = window.location;
    var sTitle = document.title;
    try {
        window.external.addFavorite(sURL, sTitle);
    }
    catch (e) {
        try {
            window.sidebar.addPanel(sTitle, sURL, "");
        }
        catch (e) {
            alert("加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}
// 热搜词
function searchHotWord(txt){
    	var searchValue = $.trim(txt);
    	if(searchValue != ""){
    		location.href=_ctxPath+'/search.htm?keyWord='+searchValue;
    	}
    }

//搜索
function search(){
	var searchValue = $("#search").val();
	var reg = /^[-\\\s/a-zA-Z0-9\u4E00-\u9FA5\[\]\(\)\{\}\*\#\@\!\~\+\=]*$/
	//	;/^[^\&\>\<\?]$/;
	if(!reg.test(searchValue)){
		popupDialog("亲,搜索不能有非法字符!");
		return;
	}
	if(searchValue.length > 30){
		popupDialog("搜索的字符串长度不能大于30个字符!");
		return;
	}
	if( $.trim(searchValue)!=''&& searchValue !="搜索商品/品牌" ){
		location.href=_ctxPath+'/search.htm?keyWord='+searchValue;
	}
}
//搜索响应回车事件
$("#search").keydown(function(e){
	//console.log("搜索响应回车事件");
	if(e.keyCode == 13){
		search();
	}
});

// 显示购物车车中的数量
function showTotal(){
    var cookie = $.cookie;
    cookie.json = true;
    var num = 0;
    if(typeof(cookie(SHOPCART)) != "undefined"){
    	var json = cookie(SHOPCART)
        num = json.totalNum;
    }
    $(".show-sum").html(num);
    return false;
}
//修改商品数量,本地修改cookie存在bug弃用
function adjustCart(skuId, quantity){
    var cookie = $.cookie;
    cookie.json = true;
    if(typeof(cookie(SHOPCART)) != "undefined"){
       var json = JSON.parse(cookie(SHOPCART));
       var oldQty = json['cartItems'][skuId];
       json['cartItems'][skuId] = quantity;
       json['totalNum'] = json['totalNum'] - oldQty + quantity
       cookie(SHOPCART, null);
       cookie(SHOPCART, JSON.stringify(json));
    }
    return false;
}

//登陆方法
function popupLogin(obj){
	
    $.formValidator.initConfig({
	    validatorGroup: '1',
	    formID: 'login_form',
	    errorFocus:false, //错误时不聚焦到第一个控件
	    theme: 'yto'
	});
    
	//注册
	$.formValidator.initConfig({
	    validatorGroup: '2',
	    formID: 'registerForm',
	    errorFocus:false, //错误时不聚焦到第一个控件
	    theme: 'yto'
	});
    $("#r_userName").formValidator({
    	validatorGroup: '2',
        onShowText:"",
        onShow:"",
        onFocus:"",
        onCorrect:'',
        tipID:'r_userNameReg',
        leftTrim:true,
        rightTrim:true
    }).functionValidator({
    	fun:function(val){
            if(val == ""){return "请输入邮箱地址"}}
    }).inputValidator({
        min:6,
        max:50,
        onError:"邮箱格式错误"
    }).regexValidator({
        regExp:"email",
        dataType:"enum",
        onError:"邮箱格式错误"
    })
//    .ajaxValidator({
//        url:_ctxPath+'/user/user-registerCheck.htm?f=r', 
//        onWait:"",
//        datatype : "json",
//        success : function(data){  
//        	var d=eval("("+data+")");
//            if(d.info == "1" ){  
//                return false;  
//            }else{  
//                return true;  
//            }  
//        },  
//        onError:"该帐号已存在"
//    })
    ;
    
    $("#r_password_t").formValidator({
    	validatorGroup: '2',
        onShowText:"请输入6位以上数字、字母或组合"
    });
    $("#password_t").formValidator({
    	validatorGroup: '1',
        onShowText:"请输入6位以上数字、字母或组合",
        tipID: "passWord"
    })
    ;
    $("#j_username").formValidator({
    	validatorGroup: '1',
    	 tipID: "account",
        onShowText:"",
        onCorrect:""
        //leftTrim:true,
        //rightTrim:true
    }).functionValidator({
    	fun:function(val){
            if(val == ""){return "请输入注册邮箱"}}
    }).inputValidator({
        min:6,
        max:50,
        onError:"邮箱格式错误"
    }).regexValidator({
    	 regExp:"email",
         dataType:"enum",
         onError:"邮箱格式错误"
    });
    
    $("#j_password").formValidator({
    	validatorGroup: '1',
        tipID: "passWord",
        onCorrect:""
    }).functionValidator({
    	fun:function(val){
            if(val == ""){return "请输入密码"}
            if(val.replace(/\s+/g,'').length<val.length){return "请输入6位以上数字、字母或组合"}
    	}
    }).inputValidator({
        min:6,
        max:20,
        onError:"密码格式错误"
    });
    $("#r_password").formValidator({
    	validatorGroup: '2',
        tipID: "r_passwordReg"
            //leftTrim:true,
           // rightTrim:true
    }).functionValidator({
    	fun:function(val){
            if(val == ""){return "请输入密码"}
            if(val.replace(/\s+/g,'').length<val.length){return "请输入6位以上数字、字母或组合"}}
    }).inputValidator({
        min:6,
        max:20,
        onError:"密码格式错误"
    });
    $("#r_querypassword").formValidator({
    	validatorGroup: '2',
        tipID: "r_querypasswordReg"
    }).functionValidator({
    	fun:function(val){
            if(val == ""){return "请输入确认密码"}}
    }).inputValidator({
        min:6,
        max:20,
        onError:"确认密码格式错误"
    }).compareValidator({
        desID:"r_password",
        operateor:"=",
        onError:"两次密码输入不一致，请重新输入"
    });
    
    $("#r_querypassword_t").formValidator({
    	validatorGroup: '2',
        onShowText:"请再次输入密码"
    });
	
    if (obj.hasClass('register')) {
    	$.dialog({
            title: false,
            padding: "0",
            content: $("#log-reg-wrap").get(0),
            fixed: true,
            lock: true
        });
      $("#log-reg-wrap .tab-item:eq(1)").show().siblings().hide();
      //判断邮箱是否有默认值
	    var emailTemp = $("#r_userName").val();
	    if(emailTemp){
	    	$("#r_userName").siblings(".email-tip").hide();
	    }else{
	    	$("#r_userName").siblings(".email-tip").show();
	    }
    } else if (obj.hasClass('login')) {
    	$.dialog({
            title: false,
            padding: "0",
            content: $("#log-reg-wrap").get(0),
            fixed: true,
            lock: true
        });
      $("#log-reg-wrap .tab-item:eq(0)").show().siblings().hide();
      //判断邮箱是否有默认值
	    var emailTemp = $("#j_username").val();
	    if(emailTemp){
	    	$("#j_username").siblings(".email-tip").hide();
	    }else{
	    	$("#j_username").siblings(".email-tip").show();
	    }
    }
	    //点击提示文本消失
	    $(".email-tip").on("click",function(){
	    	$(this).hide().siblings(".J-email").focus();
	    });
	    $(".J-email").on("focus",function(){
	    	$(this).siblings(".email-tip").hide();
	    });
	    $(".J-email").on("blur",function(){
	    	if(!$(this).val()){
	    		$(this).siblings(".email-tip").show();
	    	}
	    });
}
//弹窗公共方法
function popupDialog(obj,nVal){
	$.dialog({
		title: false,
		content: obj,
        id: nVal,
        lock : true,
        okValue: "确定",
        ok: true
    });
    $(".d-close").hide();
}
//判断object是否数组
function isArray(obj) {   
	  return Object.prototype.toString.call(obj) === '[object Array]';    
	} 

$(function(){
	//  显示购物车list
    var pTimer1,pTimer2,
        listBox = $(".cart-list");
    $(".cart-sum").on({
        "mouseenter": function(){
            var $this = $(this);
            clearTimeout(pTimer2);
            pTimer1 = setTimeout(function(){
                $this.parent().addClass("on");
            },400);
            var showNum = $(".cart-sum").find("span").text();
        	var showListNum = $(".show-text").text();
        	if(parseInt(showNum)>parseInt(showListNum)){
            	$('.cart-list').empty();
        	}
        	$.ajax({
                 type:'POST',
                 url:_ctxPath +'/order/order-listCartItem.htm',
                 async: false,
                 success:function(html){
                     listBox.empty();
                     listBox.append(html);
                 }
             });
            //鼠标移入显示更新后的数量
            showTotal();
        },
        "mouseleave": function(){
            var $this = $(this);
            var showNum = $(".cart-sum").find("span").text();
        	var showListNum = $(".show-text").text();
        	if(parseInt(showNum)>parseInt(showListNum)){
            	$('.cart-list').empty();
        	}
            clearTimeout(pTimer1);
            pTimer2 = setTimeout(function(){
                $this.parent().removeClass("on");
            },400);
        }
    });
    listBox.mouseover(function(){
        clearTimeout(pTimer2);
    });
    listBox.mouseout(function(){
        var $this = $(this);
        pTimer2 = setTimeout(function(){
            $this.parent().removeClass("on");
        },400);
    });
    $(".cart-list li").live("hover",function(){
        $(this).find(".del").toggle();
    });
    //购物车内容删除
    $(".cart-list .del").live({
        "click": function(event){
             var _this = $(this);
             var productId = _this.attr("value");
             $.ajax({
                type:'POST',
                url:  _ctxPath + '/order/order-delCartProduct.htm',
                data:{"skuIds": productId},
                success:function(html){
                    //_this.parent().remove();
                	$.ajax({
                        type:'POST',
                        url:_ctxPath +'/order/order-listCartItem.htm',
                        success:function(html){
                            listBox.empty();
                            listBox.append(html);
                        }
                    });
                    showTotal(); //把商品总数量显示在前台购物车
                    }
                });
            return false;
	    }
    });
    //  input焦点文本
//    $(".data-frame input").each(function(){
//        var $this = $(this);
//        $this.input();
//        $this.on("keyup",function(){
//            var $this = $(this);
//            if(parseInt($.trim($this.val()).length) > 0 ){
//                $this.next().addClass("highlight");
//            }
//            else{
//                $this.next().removeClass("highlight");
//            }
//        });
//    });
    //  注册登录
    /*$("#log-reg-wrap").Merlin({
        "tabSwitcher":{
            tabCls:".J-log-reg",
            trigger:"click",
            effect: true
        }
    });*/
    $("#log-reg-wrap .J-log-reg").on("click",function(){
    	$this = $(this);
    	var index = $(this).attr("id");
    	$this.closest(".tab-item").hide().siblings(".tab-item").show();
    	//判断邮箱是否有默认值
	    var emailTemp = $("#j_username").val();
		if(index=='one'){//免费注册
    		nextValidateCodeRegister();
    	}else{
    		nextValidateCode();
    	}
	    if(emailTemp){
	    	$("#j_username").siblings(".email-tip").hide();
	    }else{
	    	$("#j_username").siblings(".email-tip").show();
	    }
    });
    //点击弹窗登录/注册
    $(".login,.register").on("click", function(){
    	var obj = $(this)
    	popupLogin(obj);
    });
    //  表格内效果
    $(".tab-item input").each(function(){
        var $this = $(this);
        $this.Merlin({
            "inputFocus":{
                "setDefault": true
            }
        })
    });
    //自动匹配邮箱下拉列表
    $(".J-email").mailAutoComplete();
    $(".emailist").on("click",function(){
    	$("#j_username").focus().removeClass("input-error");
    	$("#r_userName").focus().removeClass("input-error");
    });
    /* ======
    * 右侧挂件
    ====== */
    var $foot = $(".footer-top"),//底部DOM
    		$sidebar = $(".sidebar-block"),//挂件DOM
    		footTop,//底部位置
    		footHeight = $foot.height(),
    		sidebarHeight = $sidebar.height(),//挂件高度
    		screenWidth,
    		scrollTop = 0,//滚动条位置
    		windowHeight = document.documentElement.clientHeight,//浏览器高度
    		newSidebarTop,//挂件重新判断后高度
    		scrollTimer = null,//滚动条延时
    		resizeTimer = null;//窗口变化延时延时
    //右侧挂件屏幕自适应
    function screenAuto(){
    	screenWidth = $(window).width();
    	if(screenWidth < 1260){
    		$(".left-sider").css({"left":"0","margin-left":"0"});
    	}else{
    		$(".left-sider").attr("style","");
    	}
    	if(screenWidth < 1145){
    		$sidebar.removeClass("screen-default").addClass("screen-auto");
    	}else{
    		$sidebar.removeClass("screen-auto").addClass("screen-default");
    	}
    }
    //右侧挂件底部适应
    function footAuto(){
    	if(scrollTimer){
    		clearTimeout(scrollTimer);
    	}
    	resizeTimer = setTimeout(function(){
    		footTop = $foot.position().top;
    		newSidebarTop = footTop - sidebarHeight - 25;
    		windowHeight = document.documentElement.clientHeight;
    		scroolTop = document.documentElement.scrollTop||document.body.scrollTop;
	    	if(scroolTop + windowHeight -97 > footTop){
	    		$sidebar.css({"position":"absolute","top":newSidebarTop,"bottom":"inherit","height":sidebarHeight});
	    	}else{
	    		$sidebar.attr("style","");
	    	}
    	},50);
    }
    //resize重新判断执行
    function auto(){
    	footAuto();
    	screenAuto();
    }
    //滚动条滚动事件
    $(window).scroll(footAuto);
    //页面加载默认判断
    screenAuto();
    //窗口变化重新判断
    $(window).on("resize",function(){
    	if(resizeTimer){
    		clearTimeout(resizeTimer);
    	}
    	resizeTimer = setTimeout(auto,100);
    });
    //返回顶部
    $(".sidebar-totop").find("a").on("click",function(){
        $('html, body').animate({scrollTop:0},400)
    });
    var offset_ft = $(".footer").offset().top; //顶部的距顶坐标
    //非ie6浏览器执行
    if($.browser.version != '6.0'){
        $(window).scroll(function(){
            //console.log(offset_ft - $(".footer").outerHeight() - 25);
            if($(this).scrollTop() == offset_ft - $(".footer").outerHeight() - 25){
                $(".sidebar-block").css("top",500)
            }
        })
    }
    //验证码
    $(".verific").on("focus",function(){
    	$('#verifiCode').html('');
    	if($(this).val() === "验证码不区分大小写"){
    		$(this).val('');
    	}
    });
});