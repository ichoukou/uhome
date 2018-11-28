/**
 * 编辑秒杀排期页面相关js
 */
$(function(){
	//新增秒杀排期
	$("#addSeckill-btn").on("click", function () {
		var dialog = $.dialog({
    		title: false,
            okValue: "保存",
            ok: function(){
            	var result = $.formValidator.pageIsValid('1');
	    		if(!result){
	    			return false;
	    		}
	    		if($(".addSale-product :radio:checked").length == 0){
	    			popupDialog("请选择秒杀商品！");
	    			return false;
	    		}
	    		$("#planProductId").val($(".addSale-product :radio:checked").attr("productId"));
				//保存特卖排期数据
				$.ajax({
					type: "POST",
				    url: _ctxPath + "/admin/plan/plan-savePlan.htm",
				    data: $("#seckill").serialize(),
				    success: function (msg) {
				    	if(msg.code == "true"){
				    		showMessage(msg.info);
    						window.location.reload();
				    	}
				    },
					error: function(msg){
						popupDialog("error:"+msg.info);
			        }
				});
            },
            cancelValue: "取消",
            cancel: true,
            lock: true,
            fixed:false,
            visible: false
    	});

   		$.ajax({
			type:'POST',
			url: _ctxPath + '/admin/plan/plan-editPlan.htm',
			data: "type=" + TYPE_SEC_KILL,
			success:function(html){
				dialog.visible().content(html);
			}
		});
   		
	});
	//编辑
	$(".edit").click(function(){
		var dialog = $.dialog({
    		title: false,
            okValue: "保存",
            ok: function(){
            	var result = $.formValidator.pageIsValid('1');
	    		if(!result){
	    			return false;
	    		}
	    		if($(".addSale-product :radio:checked").length == 0){
	    			popupDialog("请选择秒杀商品！");
	    			return false;
	    		}
	    		$("#planProductId").val($(".addSale-product :radio:checked").attr("productId"));
				//保存秒杀排期数据
				$.ajax({
					type: "POST",
				    url: _ctxPath + "/admin/plan/plan-savePlan.htm",
				    data: $("#seckill").serialize(),
				    success: function (msg) {
				    	if(msg.code == "true"){
				    		showMessage(msg.info);
				    		window.location.reload();
				    	}
				    },
					error: function(msg){
						popupDialog("error:"+msg.info);
			        }
				});
            },
            cancelValue: "取消",
            cancel: true,
            lock: true,
            fixed:false,
            visible: false
    	});
		var value = "plan.planId=" + $(this).closest(".pbox").attr("planId")
					+"&type=" + TYPE_SEC_KILL
   		$.ajax({
			type:'POST',
			url: _ctxPath + '/admin/plan/plan-editPlan.htm',
			data: value,
			success:function(html){
				dialog.visible().content(html);
			}
		});
	});
	//确认发布
	$(".pub").click(function(){
		var $div = $(this).closest(".pbox");
		var planId = $div.attr("planId");
		$.dialog({
            title: false,
            lock: true,
            content: "<br/>是否确定发布？",
            okValue: "确定",
            ok: function(){
            	$.ajax({
    				type:'POST',
    				url: _ctxPath + '/admin/plan/plan-release.htm',
    				data: "plan.planId=" + planId,
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
	//删除
	$(".plan-del").click(function(){
		var planId = $(this).closest(".pbox").attr("planId");
		$.dialog({
            title: false,
            lock: true,
            content: "是否确定删除？",
            okValue: "确定",
            ok: function(){
            	$.ajax({
    				type:'POST',
    				url: _ctxPath + '/admin/plan/plan-deletePlan.htm',
    				data: "plan.planId=" + planId,
    				success:function(msg){
    					if(msg.code == "true"){
    						showMessage(msg.info);
    						window.location.reload();
    					}
    				},
    				error: function(msg){
    					popupDialog("删除排期出错！" + msg.info);
    		        }
    			});
            },
            cancelValue: "取消",
            cancel: true
		});
	});
	//查看活动中的商品
	$(".tit").on("click", function () {
		 var dialog = $.dialog({
			    lock: true,
			    padding: "0px",
			    title:false,
			    fixed: true,
			    okValue: "确认",
	            ok: function(){
	            	$.ajax({
	    				type:'POST',
	    				url: _ctxPath + '/admin/plan/plan-editPlanProductRank.htm',
	    				data: $("#viewForm").serialize(),
	    				success:function(msg){
	    					if(msg.code == "true"){
	    						showMessage(msg.info);
	    					}
	    				},
	    				error: function(msg){
	    					popupDialog("修改权重出错！" + msg.info);
	    		        }
	    			});
	            },
			    cancelValue:"关 闭",
			    cancel: true,
			    visible: false
		  });
		  var value = "plan.planId=" + $(this).val() + "&type=" + TYPE_SEC_KILL;
		  $.ajax({
				type:'POST',
				url: _ctxPath + '/admin/plan/plan-getPlanProducts.htm',
				data: value,
				success:function(html){
					dialog.visible().content(html)
				}
			});options.relativeTo
		  $(".d-close").hide();
	});
	
	$(".pbox").on("hover",function(){
		var thisTip = $(this).find(".f-pop");
    var thisLeft = $(this).position().left;
    var thisWidth = $(this).parents(".plans-list-wp").scrollLeft() + 750;
    //alert(thisLeft +"&nbsp;"+ thisWidth);
    if(thisLeft > thisWidth){
    	thisTip.css("left","-80px");
    }else{
    	thisTip.css("left","29px");
    }
    thisTip.toggle();
	});
});
