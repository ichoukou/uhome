/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-4
 * Time: 下午8:11
 * Description:
 */
$(function () {
    //倒计时
    $(".countdown").each(function () {
        var $this = $(this),
            data = $this.attr('data-config');
        $this.Merlin({
            "countDown": eval('(' + data + ')')
        });
    });
    //左右切换分页效果的实现；
    var totalnum=$(".productshow li").length,
        numperpage=10,
        pages=Math.ceil(totalnum/numperpage),
        temp=$(".scrollwraper ul");

    function app(){
        temp=$("<ul />").append(temp.find("li:gt("+(numperpage-1)+")")).appendTo($(".scrollwraper"));
        if(temp.find("li").length>numperpage){
            app();
        }
    }
    app();

    $(".productshow").Xslider({
        scrollobj:".scrollwraper",
        unitdisplayed:1,
        numtoMove:1,
        viewedSize:822,
        unitlen:822,
        scrollobjSize:822*pages
    });
})