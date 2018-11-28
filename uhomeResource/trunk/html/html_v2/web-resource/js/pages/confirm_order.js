/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-4
 * Time: 下午1:40
 * Description:
 */

$(function(){
    //发票类型选择
    $(".radio :radio").on("change",function(){
        var $this = $(this);
        if($this.attr("id")){
            $("#invoice-detail").show();
        }
        else{
            $("#invoice-detail").hide();
        }
    });
    //多级联动
    var area = {
        data: districtData,
        selStyle: 'margin-left:3px;',
        select: ['#province']
    };
    var linkageSel = new LinkageSel(area);
    //调动修改地址弹窗
    $("#useNew").on("click",function(){
        $.dialog({
            title: false,
            content: $("#addressWrap").get(0),
            okValue: "保存修改",
            ok: function(){},
            cancelValue: "取消",
            cancel: true,
            lock: true
        });
        $(".d-close").hide();
    })
})