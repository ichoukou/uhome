/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-6
 * Time: 下午2:09
 * Description:
 */
$(function () {
    //返回顶部
    $(".go-top").find("a").on("click",function(){
        $('html, body').animate({scrollTop:0},400)
    })
    //面包屑固定
    var bread = $(".bread-wrap"),  //面包屑对象
        proPanel = $(".pro-panel"), //左侧菜单操作面板
        breadHeight = bread.outerHeight(),  //面包屑高度
        offset_bread = bread.offset(),  //面包屑在视窗的相对偏移
        goTop =  $(".rt-fixed-wrap"),  //返回顶部对象
        topBread = parseInt(offset_bread.top);
    //非ie6浏览器执行
    if($.browser.version != '6.0'){
        goTop.hide();
        $(window).scroll(function(){
            var scrollTop = $(window).scrollTop();
            if(scrollTop >= topBread){
                bread.css({
                    "position": "fixed",
                    "margin-top": 0,
                    "z-index": "10",
                    "top": "0",
                    "left": offset_bread.left
                }).addClass("fixed");
                proPanel.css({
                    "position": "fixed",
                    "z-index": "8",
                    "top": 58
                });
                goTop.show();
            }
            else{
                bread.removeAttr("style").removeClass("fixed");
                proPanel.removeAttr("style");
                goTop.hide()
            }

        });
    }
    if($.browser.version == '6.0'){
        $(".new-goods-issue li").hover(function(){
            $(this).toggleClass("hover");
        });
    }
    //倒计时
    $(".countdown").each(function () {
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
            trigger:"click",
            effect: true
        }
    });
    //品牌分类切换
    var proSort = $(".brands-sort").find("a"),  //各分类品牌链接
        multiBox = $(".multi-product").find(".sort-item"), //内容展示块
        panelLik = $(".pro-panel").find("a"), //左侧筛选链接
        pNum = 0 ; //起始索引值
    function goBread() {
        if($(window).scrollTop() != topBread){
            $("html,body").stop().animate({scrollTop:topBread},300);
        }
    }
    proSort.on("click",function(){
       var $this = $(this);
       pNum = $this.index();
       var $multiBox = multiBox.eq(pNum);
        if($this.text() !="进口商品"){
           $this.addClass("current").siblings().removeClass("current");
        }
        $multiBox.stop().fadeIn("fast", goBread);
        $multiBox.siblings(".sort-item").hide();
    });
    panelLik.on("click",function(){
        var $this = $(this),
            inx = $this.index();
        $this.addClass("current").siblings().removeClass("current");
        $(".sort-item").eq(pNum).children().eq(inx).fadeIn("fast", goBread).siblings().hide();
    });
    $("#upcomLink").on("click",function(){
        var upPro = $(".upcom-product");
        if(upPro.children("ul").length != 0){
            var scrTop = upPro.offset().top;
            $("html,body").stop().animate({scrollTop:scrTop-58},300);
        }
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