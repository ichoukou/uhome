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
        resDy: $("#res_dy")
    }
    //全选
    ele.ckAll.on("change",function(){
        var $this = $(this);
        $this.Merlin({
            "checkAll": {}
        })
    });
    //订阅
    ele.resDy.on("click",function(){
        $.dialog({
            title: false,
            follow: ele.resDy.get(0),
            content: "恭喜你，订阅成功！"
        }).time(2000);
        $(".d-close").hide();
    })
})