/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-4-15
 * Time: 下午4:41
 * Description:
 */
$(function () {
//订单批量操作
    $(".ord-checked-all").on("change",function(){
        var $this = $(this),
            arr=[];
       $(".ord-checkbox").each(function(){
           var $this = $(this);
          if($this.attr("disabled") != "disabled"){
              arr.push($this);
          }
       });
        $.each(arr,function(){
           if($this.get(0).checked){
               $(this).attr("checked",true);
           }else{
               $(this).attr("checked",false);
           }
        });
    });
})