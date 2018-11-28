/**
 * Created with JetBrains PhpStorm.
 * User: admin
 * Date: 13-3-12
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
$(function(){
    //    选项卡
    $(".tab-gn").Merlin({
        "tabSwitcher":{
            tabCls:".tab-gnul li",
            trigger:"click",
            itemCls: ".protab-item"
        }
    });
})