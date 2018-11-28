/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-6
 * Time: 下午2:09
 * Description:
 */
$(function () {
    //     顶部工具条
    $(".topbar .follow").hover(function(){
        $(this).toggleClass("hover").find(".follow-box").toggle();
    });
    //    返回顶部
    $(".go-top").find("a").on("click",function(){
        $('html, body').animate({scrollTop:0},400)
    })
    //    面包屑固定
    var bread = $(".bread-wrap"),  //面包屑对象
        breadHeight = bread.outerHeight(),  //面包屑高度
        offset_bread = bread.offset(),  //面包屑在视窗的相对偏移
        goTop =  $(".rt-fixed-wrap"),  //返回顶部对象
        multiProduct = $(".multi-product"),   //新品上线
        upcomProduct = $(".upcom-product"),   //即将上线
        multiPanel = $(".multi-product .pro-panel"),  //商品多选项面板
        offset_multiPanel = multiProduct.offset(),  //商品多选项面板在视窗的相对偏移
        upcomPanel = $(".upcom-product .pro-panel"),  //即将上线商品面板
        offset_upcomPanel =  upcomProduct.offset(),  //即将上线商品面板在视窗的相对偏移
        topBread = parseInt(offset_bread.top),
        topMulti = parseInt(offset_multiPanel.top),
        topUpcom = parseInt(offset_upcomPanel.top);
    //    非ie6浏览器执行
    if($.browser.version != '6.0'){
        goTop.hide();
        $(window).scroll(function(){
            var scrollTop = $(window).scrollTop();
            if(scrollTop >= topBread && scrollTop < topMulti){
                bread.css({
                    "position": "fixed",
                    "top": 0,
                    "left": 0,
                    "width": "100%",
                    "z-index": 10
                });
                goTop.show();
                multiPanel.removeClass("pro-fixed-m");
                upcomPanel.removeClass("pro-fixed-u");
            }
            else if(scrollTop >= topMulti && scrollTop < topUpcom){
                bread.css({
                    "position": "fixed",
                    "top": 0,
                    "left": 0,
                    "width": "100%",
                    "z-index": 10
                });
                goTop.show();
                upcomPanel.removeClass("pro-fixed-u");
                multiPanel.addClass("pro-fixed-m");
            }
            else if(scrollTop >= topUpcom){
                bread.css({
                    "position": "fixed",
                    "top": 0,
                    "left": 0,
                    "width": "100%",
                    "z-index": 10
                });
                goTop.show();
                multiPanel.removeClass("pro-fixed-m");
                upcomPanel.addClass("pro-fixed-u");
            }
            else if(scrollTop < topBread){
                bread.removeAttr("style");
                goTop.hide()
            }

        });
    }
    if($.browser.version == '6.0'){
        $(".new-goods-issue li").hover(function(){
            $(this).toggleClass("hover");
        });
    }
    //    显示购物车list
    $(".shopping-cart").hover(function(){
        $(".shopping-box").toggle();
    });
//        //    品牌预约
//        var barndApp = $(".brand-appointment .main").find("li");
//        barndApp.mouseenter(function(){
//            $(this).addClass("hover").find(".sale-wrap").fadeIn();
//        });
//        barndApp.mouseleave(function(){
//            $(this).removeClass("hover").find(".sale-wrap").hide();
//        });
    //        倒计时
    $(".countdown").each(function () {
        var $this = $(this),
            data = $this.attr('data-config');
        $this.Merlin({
            "countDown": eval('(' + data + ')')
        });
    });
    //      input焦点文本
    $(".data-frame input").each(function(){
        var $this = $(this);
        $this.Merlin({
            "inputFocus":{
                "setDefault": true
            }
        }).on("keyup",function(){
                var $this = $(this);
                if(parseInt($.trim($this.val()).length) > 0 ){
                    $this.next().addClass("highlight");
                }
                else{
                    $this.next().removeClass("highlight");
                }
            });
    });
    //    选项卡
    $(".sale-notice .tab").Merlin({
        "tabSwitcher":{
            tabCls:"ul li",
            trigger:"click",
            effect: true
        }
    });
    //    最新上线左侧面板
    $(".multi-product").Merlin({
        "tabSwitcher":{
            tabCls:".pro-panel a",
            trigger:"click",
            effect: true,
            evt: function(){
                var ele = $(".pro-panel a.cur"),
                    index = $(".pro-panel a").index(ele);
                multiPanel.css("background-position","-"+index*26+"px" + " -608px")
            }
        }
    });
    //    品牌预约提交
    var poBrInf = $("#postBrandInfo");
    poBrInf.click(function(){
        $.dialog({
            title: false,
            follow: poBrInf.get(0),
            content: "提交成功，谢谢！"
        });
        $(".d-close").hide();
    })
    //      注册登录
    $("#log-reg-wrap").Merlin({
        "tabSwitcher":{
            tabCls:".log-reg-panel li",
            trigger:"click",
            effect: true
        }
    });
    $(".login,.register").click(function(){
        var $this = $(this);
        var dialog = $.dialog({
            title: false,
            padding: "0",
            content: $("#log-reg-wrap").get(0),
            fixed: true,
            lock: true
        });
        if ($this.hasClass('register')) {
            $('#log-reg-wrap .log-reg-panel li:eq(1)').trigger('click');
        } else if ($this.hasClass('login')) {
            $('#log-reg-wrap .log-reg-panel li:eq(0)').trigger('click');
        }
    });
    //    表格内效果
    $(".tab-item input").each(function(){
        var $this = $(this);
        $this.Merlin({
            "inputFocus":{
                "setDefault": true
            }
        })
    });
    //   验证
    //右侧列表显示效果
    $(".s-link").mouseenter(function(){
        var $this = $(this);
        $(".s-link").show();
        $(".s-detail").hide();
        $this.hide().next().stop().fadeIn();
    })
})