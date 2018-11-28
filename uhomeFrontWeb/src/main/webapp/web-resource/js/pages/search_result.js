/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-4
 * Time: 下午8:11
 * Description:
 */
$(function () {
    //鼠标划过效果
    $(".item-wrap li").on("hover",function(){
    	$(this).toggleClass("hover");
    });
    //左右切换分页效果的实现；
    var totalnum=$(".productshow li").length,
        numperpage=6,
        pages=Math.ceil(totalnum/numperpage),
        temp=$(".scrollwraper ul");

    function app(){
        temp=$("<ul />").append(temp.find("li:gt("+(numperpage-1)+")")).appendTo($(".scrollwraper"));
        if(temp.find("li").length>numperpage){
            app();
        }
    }
    app();

    
    //选择排序选中的效果 TODO  页面是动态的刷型没有效果  通过判断url来做
//    $(".action").find("span").click(function(){
//    	$(this).siblings().removeClass("cur-down");
//    	$(this).addClass("cur-down");
//    });
})