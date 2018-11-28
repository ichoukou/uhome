/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-19
 * Time: 下午5:27
 * Description: index
 */
$(function () {
    if($.browser.version == '6.0'){
        //即将上线
        $(".goods-block li").on("hover",function(){
            $(this).toggleClass("hover");
        });
    }
    //焦点图
    var jSli = $('#j_img li'), //展示部分
    	jTrig = $('#j_strigger li'), //控制部分
    	sNum = 0, //初始值
    	sTimer, //定时器
    	speed = 3000, //速率
    	slider = function(i){
    	jSli.eq(i).stop(true).fadeIn().siblings().hide();
    	jTrig.eq(i).addClass('on').siblings().removeClass('on');
    }
    $('#j_slider').on({
    	'mouseover':function(){
    		clearInterval(sTimer);
    	},
    	'mouseout':function(){
    		//自动播放
    		sTimer = setInterval(function(){
    			slider(sNum);
    			if(sNum < jTrig.length){
    				sNum++;
    			}
    			if(sNum == jTrig.length){
					sNum = -1;
					sNum++;
				}
    		},speed)
    	}
    }).trigger('mouseout');
    jTrig.on('mouseover',function(){
    	var _this = $(this);
    		sNum = _this.index(); //重赋sNum
    	slider(sNum);
    })
    
    //人气商品
    $("#J-topGood li").on("mouseover",function(){
        $(this).addClass("hot-top").siblings().removeClass("hot-top");
    });
    $("#J-topGood").on("mouseleave",function(){
       $(this).find("li").eq(0).addClass("hot-top").siblings().removeClass("hot-top");
    });
    //  input焦点文本
    $(".text-input").each(function(){
        var $this = $(this);
        $this.input();
//        $this.on("keyup",function(){
//            var $this = $(this);
//            if(parseInt($.trim($this.val()).length) > 0 ){
//                $this.next().addClass("highlight");
//            }
//            else{
//                $this.next().removeClass("highlight");
//            }
//        });
    });
    //倒计时
    $(".J-countdown").each(function () {
        var $this = $(this),
            data = $this.attr('data-config');
        data = eval('(' + data + ')');
        data.callback = function(){
        	$this.closest("li").remove();
        };
        $this.Merlin({
            "countDown": data
        });
    });
    //选项卡
    $(".sale-notice .tab").Merlin({
        "tabSwitcher":{
            tabCls:"ul li",
            trigger:"hover",
            effect: true
        }
    });
    //  品牌预约
    var barndApp = $(".bespeak-brand").find("li");
    var Timer,show,hide;
    var flag = false;
    barndApp.on({
        mouseenter: function(){
            var $this = $(this),
            $wrapBox = $this.find(".sale-wrap");
            window.clearTimeout(Timer);
            window.clearTimeout(hide);
            if(!flag){
                    Timer = setTimeout(function(){
                        $this.addClass("hover");
                        $wrapBox.stop().fadeIn();
                        flag = true;
                    },400);
            }else{
                show = setTimeout(function(){
                    $this.addClass("hover").find(".sale-wrap").show();
                    $this.siblings().removeClass("hover").find(".sale-wrap").hide();
                },200);
            }
        },
        mouseleave: function(){
            window.clearTimeout(show);
            window. clearTimeout(Timer);
            hide = setTimeout(function(){
                $(".bespeak-brand").find("li.hover").removeClass("hover").find(".sale-wrap").fadeOut();
                flag =false;
            },200);
            $(this).find(".email-error").hide();
        }
    });
    /* ======
     * 右侧挂件
     ====== */
    //即将上线锚点
    var sidLi = $(".sidebar-list").find("li"), //类别li
        upcome = $("#J_forOnline").offset(), //即将上线坐标
        spike = $("#J_spike").offset(), //往期特卖坐标
        goodsShow = $(".goods-show").offset(); //商品显示
    sidLi.on("click",function(){
       var idx = $(this).index();
       $(this).addClass("hover").siblings().removeClass("hover");
       $(".goods-show").find("ul").eq(idx).stop().fadeIn().siblings().hide();
       $("html,body").stop().animate({scrollTop:parseInt(goodsShow.top)},300);
    });
    sidLi.eq(3).on("click",function(){
        $("html,body").stop().animate({scrollTop:parseInt(upcome.top)},300);
    });
    sidLi.eq(4).on("click",function(){
        $("html,body").stop().animate({scrollTop:parseInt(spike.top)},300);
    });
    /* ======
     * 广告
     ====== */
    //顶部广告
//    $("#J_topAd").animate({
//       "margin-top": 0
//    },"slow");
    /*var showAd = setTimeout(function(){
        $("#J_topAd").animate({
            "margin-top": -60
        },"slow");
    },5000);*/
    /* ======
     * 首页底部品牌集中营切换
     ====== */
    var hoverTimer;
    $(".tab-ctrl i").on({
    	'mouseenter':function(){
    		var $this = $(this),
    		$idx = $this.index();
	    	hoverTimer=setTimeout(function(){
	    		$this.addClass('cur').siblings().removeClass('cur');
	        	$('.show-bra-con').hide().eq($idx).stop(true).fadeIn();	
	    	},200)
    	},
    	'mouseleave':function(){
    		clearTimeout(hoverTimer);
    	}
    });
    var hoverTimer2;
    $('.bra-camp li').on({
    	'mouseenter':function(){
        	var $this = $(this);
        	hoverTimer2=setTimeout(function(){
        		$this.find('img').animate({'top':-50},300);
            	$this.find('span').animate({'top':0},300);
        	},100)
        },
    	'mouseleave':function(){
    		var $this = $(this);
    		clearTimeout(hoverTimer2);
    		$this.find('img').animate({'top':0},300);
        	$this.find('span').animate({'top':50},300);
    	}
    });
});

//验证邮箱TODO
function validtorEmail(email){
	var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	if(reg.test(email)){
		return true;
	}
	popupDialog("邮箱格式不正确!","1000");
	return false;
}

function isEmail(email){
	var reg = /^(?=\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$).{0,50}$/;
	return reg.test(email);
}

//保存品牌预约
function brandOpenInform(brandId,obj,bType,bStatus){
	var $this = $(obj);
	//获取用户填写的邮箱
	var vid = "#"+brandId+"_email";
	var txt = $(vid).val();
	if(txt == $(vid).attr("data-default")){
		return;
	}
	//邮箱格式不正确
	//var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	if(!isEmail(txt)){
		$this.prev().prev().addClass("visible");
		return;
	}
	$.ajax({
		type: 'POST',
		url: _ctxPath+'/subscriptionEmail.htm',
		async: true,
		data: {
			'mailSubscribe.type':bType,
			'mailSubscribe.status':bStatus,
			'mailSubscribe.email':txt,
			'mailSubscribe.brandId':brandId
		},
		dataType:'json',
		success: function(data){
			//popupDialog(data.info);
			$this.prev().prev().addClass("visible").find("span").html(data.info);
			$(vid).attr("value","");
			var subEtime = setTimeout(function(){
				$this.prev().prev().removeClass("visible").find("span").html("请输入正确的邮箱");
			},2000);
	        },
        error: function(data){
        	//popupDialog("error:"+data.responseText);
        }
	});
}

//邮件订阅
function subEmail(postId,specType,specStatus){
    var subEmail = $(postId);
    subEmail.click(function(event){
    	var $this = $(this);
    	//获取用户的邮箱
    	var txt = subEmail.prev().val();
    	if(txt == subEmail.prev().attr("data-default")){
    		return;
    	}
    	//邮箱格式不正确
    	//var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    	if(!isEmail(txt)){
    		$this.prev().prev().addClass("visible");
    		return;
    	}
    	$.ajax({
    		type: 'POST',
			url: _ctxPath+'/subscriptionEmail.htm',
			async: true,
			data: {
				'mailSubscribe.type':specType,
				'mailSubscribe.status':specStatus,
				'mailSubscribe.email':txt
			},
			dataType:'json',
			success: function(data){
				//popupDialog(data.info);
				$this.prev().prev().addClass("visible").find("span").html(data.info);
				subEmail.prev().attr("value","");
				var subEtime = setTimeout(function(){
					$this.prev().prev().removeClass("visible").find("span").html("请输入正确的邮箱");
				},2000);
		        },
	        error: function(data){
	        	//popupDialog("error:"+data.responseText);
	        }
    	});
    });
}

//即将上线订阅
function postSubSoonEmail(brandId,planId,obj,typeLine,mStatus){
	var $this = $(obj);
	var email = $("#postSubSoonEmail"+planId).val();
	if(email == $("#postSubSoonEmail"+planId).attr("data-default")){
		return;
	}
	//邮箱格式不正确
	//var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	if(!isEmail(email)){
		$this.prev().prev().addClass("visible");
		return;
	}
	$.ajax({
		type: 'POST',
		url: _ctxPath+'/subscriptionEmail.htm',
		async: true,
		data: {
			'mailSubscribe.type':typeLine,
			'mailSubscribe.status':mStatus,
			'mailSubscribe.email':email,
			'mailSubscribe.planId':planId
		},
		dataType:'json',
		success: function(data){
			//popupDialog(data.info);
			$this.prev().prev().addClass("visible").find("span").html(data.info);
			$("#postSubSoonEmail"+planId).attr("value","");
			var subEtime = setTimeout(function(){
				$this.prev().prev().removeClass("visible").find("span").html("请输入正确的邮箱");
			},2000);
	        },
        error: function(data){
        	popupDialog("error:"+data.responseText);
        }
	});
}

//广告位要登录
function checkLogin(url,advertisementId,unloginError,loginTimerError){
	var unlogin = unloginError;
    var timeout = loginTimerError;
	$.ajax({
        type:'POST',
        url: _ctxPath+'/user/user-checkIsLogin.htm',
        data:{'urlBeforeLogin':url},
        success:function(html){
            if(html.code == "false"){
                if(html.info == unlogin || html.info ==timeout || html.info == forbid){
                	var popWinClick = $('#'+advertisementId);
		        	popWinClick.addClass('login');
		        	popupLogin(popWinClick);
                }
            } else {
            	window.location.href=url;
            }
        }
    });
}

function joinMe(unloginError,loginTimerError){
	var unlogin = unloginError;
    var timeout = loginTimerError;
	$.ajax({
        type:'POST',
        url: _ctxPath+'/user/user-checkIsLogin.htm',
        data:{'urlBeforeLogin':'/user/joinMe.htm'},
        success:function(html){
            if(html.code == "false"){
                if(html.info == unlogin || html.info ==timeout || html.info == forbid){
                	var popWinClick = $('#joinMe');
		        	popWinClick.addClass('login');
		        	popupLogin(popWinClick);
                }
            } else {
            	window.location.href='${_ctxPath }/user/joinMe.htm';
            }
        }
    });
}