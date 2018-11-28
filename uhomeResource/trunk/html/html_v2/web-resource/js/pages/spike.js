/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-11
 * Time: 下午3:07
 * Description: 秒杀首页
 */
$(function () {
    //input焦点文本
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
    //倒计时
    $(".countdown").each(function () {
        var $this = $(this),
            data = $this.attr('data-config');
        $this.Merlin({
            "countDown": eval('(' + data + ')')
        });
    });
    //秒杀规则
    $(".spi-rule").on("click",function(){
        $.dialog({
            title: false,
            content: $(".rule-wrap").get(0),
            fixed: true,
            lock: true,
            ok: true,
            okValue: "确定"
        });
        $(".d-close").hide();
    });
})