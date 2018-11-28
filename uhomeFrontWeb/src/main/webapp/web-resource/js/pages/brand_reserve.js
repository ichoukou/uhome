/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-12
 * Time: 下午3:13
 * Description: 品牌预约
 */
$(function () {
    var ele = {
        ckAll: $("#ckAll"),
        resDy: $("#res_dy"),
        ckb: $(".reserve-wrap li :checkbox")
    }
    //全反选
    ele.ckAll.on("change",function(){
        var $this = $(this);
        if(this.checked){
            ele.ckb.prop("checked",true);
        }else{
            ele.ckb.prop("checked",false);
        }
    });
    //单选
    ele.ckb.on("change",function(){
        var $this = $(this),
            hasCkb;
        ele.ckb.each(function(){
            hasCkb = $(".reserve-wrap li :checked");
        });
        if(!this.checked){
            ele.ckAll.prop("checked",false);
        }
        else if(hasCkb.length == ele.ckb.length){
            ele.ckAll.prop("checked",true);
        }
    });
})