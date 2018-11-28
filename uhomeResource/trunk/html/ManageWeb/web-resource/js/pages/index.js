/*鼠标hover变色*/
//弹窗公共方法
function popupDialog(obj){
	$.dialog({
		title: false,
		lock:true,
		content: obj,
        cancelValue: "确定",
        cancel: true
    });
}
$(function(){
    $('.tab-control').Merlin({
			// 表格行hover
	    "trHover": {
	      "eType":'hover', // 事件类型
	      "trList":'.list-tr',
	      "trMCVal":"#fdf8e7" // hover颜色值
	    },
			// 表格点击下拉
			"foldingTable":{
				"derail":'.tr-height',
	      "content":'.order-info',
				"className":'change'
			}
    });
    /*input默认框点击为空*/
    $('.input-marks').each(function(){
    	var $this = $(this);
    	if(!$this.val()){
    	    $this.Merlin({
    	        // 文本域 设置默认值
    	        "inputFocus":{"setDefault":true}
    	    });
    	}
    	$this.blur(function(){
    		if(!$this.val()){
        	    $this.Merlin({
        	        // 文本域 设置默认值
        	        "inputFocus":{"setDefault":true}
        	    });
        	}
    	});
    });
    /*tab切换*/
    $(".tab-control").on("mouseenter",".show-tr",function(){
    	var $this = $(this);
    	$this.find(".fr-tab li").each(function(i){
    		$(this).on("mouseenter",function(){
	    		$(this).addClass("bot-no").siblings().removeClass("bot-no");
	    		$this.find(".tabcont-item:eq("+i+")").show().siblings(".tabcont-item").hide();
  	    });
  		});
  		});
    /*秒杀、特卖切换*/
   /* $('.body').Merlin({
      "tabSwitcher":{
    	startIndex: $("#arrangeMent li a").index($(".current-chose")),
        tabCls:"#arrangeMent li a",
        itemCls:".arrange-tab",
        trigger:"click",
        activeCls:"current-chose"
      }
    });*/
    //新邮件阅读
    $(".no-read").click(function(){
    	$(this).removeClass("no-read");
    });
    
});