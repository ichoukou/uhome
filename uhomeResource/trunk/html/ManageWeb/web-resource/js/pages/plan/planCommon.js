/**
 * 特卖、秒杀排期页面公用js
 */
function showMessage(msg){
	$.dialog({
        title: false,
        content: msg,
        time : 1000
    });
	$(".d-close").hide();
}
function checkRank(obj){
	var regFn = /^([1-9]|10)$/;
	var rank = $.trim(obj.value);
	if(rank == "" || !regFn.test(rank)){
		obj.value = "";
		obj.focus();
	}
}
$(function(){
	//点击排期状态筛选排期
	$(".brand-query li").click(function(){
		$("#status").val($(this).val());
		$("#searchForm").submit();
	});
	//一键发布
	$("#releaseBatch").click(function(){
		var value = "";
		var $divs = $(".pbox.unpublished");
		$.each($divs, function(i,div){
			value += "&planIds["+i+"]=" + $(div).attr("planId");
		});
		if(value.length == 0){
			showMessage("当前页面没有需要发布的排期！");
			return;
		}
		value = value.substring(1);
		$.dialog({
            title: false,
            lock: true,
            content: "<br/>是否确认发布？",
            okValue: "确认",
            ok: function(){
            	$.ajax({
    				type:'POST',
    				url: _ctxPath + '/admin/plan/plan-releaseBatch.htm',
    				data: value,
    				success:function(msg){
    					if(msg.code == "true"){
    						showMessage(msg.info);
    						window.location.reload();
    					}
    				},
    				error: function(msg){
    					popupDialog("发布排期出错！" + msg.info);
    		        }
    			});
            },
            cancelValue: "取消",
            cancel: true
		});
	});
	
    //排期显示界面
    var plansHt = $(window).height()-236;
    var leftPan = $(".tit-item ul"); //左侧面板
    var topPan = $(".plans-date"); //顶部日期面板
    $(".plans-list-wp").scroll(function(){
        leftPan.css({"top": -$(this).scrollTop()});
        topPan.css({"left":-$(this).scrollLeft()});
    });
    if(leftPan.height()<plansHt){
        leftPan.parent().height(leftPan.height()+18);
        $(".plans-list-wp").height(leftPan.height()+17);
    }else{
        leftPan.parent().height(plansHt);
        $(".plans-list-wp").height(plansHt);
    }
    
	//弹出商品信息
	$(".addSale-product img,.addSale-product-seqencing img,.look-sale-product img").live("mouseenter",function(){
		var this_left =$(this).parent().position().left + 365;
		if( this_left > 820){
			$(this).parent().find(".sale-product-info").removeClass("left-icon").addClass("right-icon");
		};
		$(this).parent().find(".sale-product-info").show();
	});
	$(".addSale-product li img,.addSale-product-seqencing li img,.look-sale-product li img").live("mouseleave",function(){
		$(this).parent().find(".sale-product-info").hide();
	});
});