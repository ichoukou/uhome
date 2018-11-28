/**
 * 编辑特卖排期页面相关js
 */
$(function(){
	//新增特卖排期
	$("#addSale-btn").on("click", function () {
		window.location.href = _ctxPath + "/admin/plan/plan-editPlan.htm?type=" + TYPE_SPECIAL_SELLER;
	});
	//编辑
	$(".plan-edit").click(function(){
		var planId = $(this).closest(".pbox").attr("planId");
		window.location.href = _ctxPath + "/admin/plan/plan-editPlan.htm?plan.planId=" + planId + "&type=" + TYPE_SPECIAL_SELLER;
	});
	
	//确认发布
	$(".plan-pub").click(function(){
		var $div = $(this).closest(".pbox");
		var planId = $div.attr("planId");
		var planTime = $div.find("span:eq(0)").text();
		$.dialog({
            title: false,
            lock: true,
            content: "<br/>是否确定发布？" + planTime,
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
	//下架
	$(".plan-offshelf").click(function(){
		var planId = $(this).closest(".pbox").attr("planId");
		$.dialog({
            title: false,
            lock: true,
            content: "是否确定下架？",
            okValue: "确定",
            ok: function(){
            	$.ajax({
    				type:'POST',
    				url: _ctxPath + '/admin/plan/plan-offShelf.htm',
    				data: "plan.planId=" + planId,
    				success:function(msg){
    					if(msg.code == "true"){
    						showMessage(msg.info);
    						window.location.reload();
    					}
    				},
    				error: function(msg){
    					popupDialog("下架出错！" + msg.info);
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
		  var value = "plan.planId=" + $(this).val() + "&type=" + TYPE_SPECIAL_SELLER;
		  $.ajax({
				type:'POST',
				url: _ctxPath + '/admin/plan/plan-getPlanProducts.htm',
				data: value,
				success:function(html){
					dialog.visible().content(html)
				}
			});
		  $(".d-close").hide();
	});
	
	
});
