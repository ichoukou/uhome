/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-7
 * Time: 下午5:46
 * Description:
 */
jQuery(document).ready(function($){
    //zoom放大
    $('#multizoom').addimagezoom({ // multi-zoom: options same as for previous Featured Image Zoomer's addimagezoom unless noted as '- new'
        descArea: '#description', // description selector (optional - but required if descriptions are used) - new
        speed: 1500, // duration of fade in for new zoomable images (in milliseconds, optional) - new
        descpos: true, // if set to true - description position follows image position at a set distance, defaults to false (optional) - new
        imagevertcenter: true, // zoomable image centers vertically in its container (optional) - new
        magvertcenter: true, // magnified area centers vertically in relation to the zoomable image (optional) - new
        zoomrange: [3, 10],
        magnifiersize: [200,200],
        magnifierpos: 'right',
        cursorshadecolor: '#fdffd5',
        cursorshade: true
    });
    //当前停留样式增/减
    $(".thumbs a").on("click",function(){
        var $this = $(this);
        $this.addClass("cur").siblings().removeClass("cur");
    });
    //倒计时
    $(".countdown").each(function () {
        var $this = $(this),
            data = $this.attr('data-config');
        $this.Merlin({
            "countDown": eval('(' + data + ')')
        });
    });
    //    选项卡
    $(".tab-gn").Merlin({
        "tabSwitcher":{
            tabCls:"ul li",
            trigger:"click",
            itemCls: ".protab-item"
        }
    });
})