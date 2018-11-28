/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-5
 * Time: 下午6:07
 * Description:
 */
$(function () {
    //鼠标划过效果
    $(".item-wrap li").on({
        "mouseover":function(){
            $(this).addClass("hover");
        },
        "mouseout":function(){
            $(this).removeClass("hover");
        }
    });
    //倒计时
    $(".countdown").each(function () {
        var $this = $(this),
            data = $this.attr('data-config');
        $this.Merlin({
            "countDown": eval('(' + data + ')')
        });
    });
})