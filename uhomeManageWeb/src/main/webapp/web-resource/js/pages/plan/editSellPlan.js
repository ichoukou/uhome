/**
 * 编辑排期页面js
 */
$(function(){
	$("#save").click(function(){
		var result = $.formValidator.pageIsValid('1');
		if(!result){
			return false;
		}
		if($(".addSale-product input:checked").length == 0){
			popupDialog("请选择特卖商品！");
			return false;
		}
    	var strList = "";
    	$.each($(".addSale-product-seqencing input"), function(i, input){
    		var productId = $(input).attr("productId");
    		var rank = $(input).val();
    		strList += "<input type='hidden' name='plan.planProducts["+i+"].productId' value='"+productId+"'/>"
    					+"<input type='hidden' name='plan.planProducts["+i+"].rank' value='"+rank+"'/>";
		});
    	$(".addSale-table").append(strList);
		//保存特卖排期数据
		$.ajax({
			type: "POST",
		    url: _ctxPath + "/admin/plan/plan-savePlan.htm",
		    data: $("#sale_date").serialize(),
		    success: function (msg) {
		    	if(msg.code == "true"){
		    		$.dialog({
		    	        title: false,
		    	        content: msg.info,
		    	        time : 3000
		    	    });
		    		$(".d-close").hide();
					setTimeout("window.location.href = _ctxPath + '/admin/plan/plan-searchPlans.htm'", 3000);
		    	}
		    },
			error: function(msg){
				popupDialog("保存排期出错！" + msg.info);
	        }
		});
	});
	
	$("#cancle").click(function(){
		window.location.href = _ctxPath + "/admin/plan/plan-searchPlans.htm";
	});
	
});
//选择排期商品
$(document).ready(function(){
	$(".addSale-product .select-box").live("click",function(){
		var img_url = $(this).parent().siblings("img").attr("src");
		var ckeck_temp = $(this).attr("checked");
		if(ckeck_temp){
			var $li = $("<li id='checkbox-"+$(this).attr("id")+"'><img src='"+img_url+"' />" +
					"<input type='text' class='product-seqencing-num' productId='"+$(this).val()+"' onchange='checkRank(this)'/></li>");
			var $infoDiv = $(this).closest("li").find(".sale-product-info").clone();
			$li.append($infoDiv);
			$(".addSale-product-seqencing ul").append($li);
			
		}else{
			var temp_id = $(this).attr("id");
			$("#checkbox-" + temp_id ).remove();
		}
		
		var $div = $(this).closest(".addSale-product");
		var $input = $div.siblings(".addSale-saler").find("input");
		if($div.find("input:checked").length == $div.find("input").length){
			$input.attr("checked", "checked");
		}else{
			$input.removeAttr("checked");
		}
	});
	
	//全选复选框单击事件
	$("input[id='select-all']").live("click", function(){
		if($(this).attr("checked")){
			var $inputs = $(this).closest(".addSale-saler-list").find("ul input:not(:checked)[disabled!='disabled']");
			$.each($inputs, function(i,input){
				$(input).attr("checked", "checked");
				var img_url = $(input).parent().siblings("img").attr("src");
				var $li = $("<li id='checkbox-"+$(input).attr("id")+"'><img src='"+img_url+"' />" +
						"<input type='text' class='product-seqencing-num' productId='"+$(input).val()+"' onchange='checkRank(this)'/></li>");
				var $infoDiv = $(input).closest("li").find(".sale-product-info").clone();
				$li.append($infoDiv);
				$(".addSale-product-seqencing ul").append($li);
			});
		}else{
			var $inputs = $(this).closest(".addSale-saler-list").find("ul input:checked");
			$.each($inputs, function(i,input){
				$(input).removeAttr("checked");
				var temp_id = $(input).attr("id");
				$("#checkbox-" + temp_id ).remove();
			});
		}
	});	
});