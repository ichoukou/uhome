$(function(){
    //zoom放大
    $('#multizoom').addimagezoom({ // multi-zoom: options same as for previous Featured Image Zoomer's addimagezoom unless noted as '- new'
        descArea: '#description', // description selector (optional - but required if descriptions are used) - new
        speed: 1500, // duration of fade in for new zoomable images (in milliseconds, optional) - new
        descpos: true, // if set to true - description position follows image position at a set distance, defaults to false (optional) - new
        imagevertcenter: true, // zoomable image centers vertically in its container (optional) - new
        magvertcenter: true, // magnified area centers vertically in relation to the zoomable image (optional) - new
        zoomrange: [3, 10],
        magnifiersize: [200,200],
        magnifierpos: 'right',
        cursorshadecolor: '#fdffd5',
        cursorshade: true
    });
    //当前停留样式增/减
    $(".thumbs a").on("click",function(){
        var $this = $(this);
        $this.addClass("cur").siblings().removeClass("cur");
    });
  //查看/审核商品页面js

	$("#remark").blur(function(){
		var $this = $(this);
		if(remark != $this.attr("data-default")){
			$("#remarkTip").hide();
		}
	});
	//上一条
	$("#previous").click(function(){
		$.ajax({
			type: "POST",
			url: _ctxPath + "/admin/product/product-previous.htm",
			data: "product.productId="+$("#productId").val(),
			success:function(msg){
				var previousProductId = msg.info;
				if(!previousProductId){
					popupDialog("已经是第一条记录了！");
					return;
				}
				window.location.href =  _ctxPath + "/admin/product/product-view.htm?product.productId="+previousProductId;
			}
		});
	});
	//下一条
	$("#next").click(function(){
		$.ajax({
			type: "POST",
			url: _ctxPath + "/admin/product/product-next.htm",
			data: "product.productId="+$("#productId").val(),
			success:function(msg){
				var nextProductId = msg.info;
				if(!nextProductId){
					popupDialog("已经是最后一条记录了！");
					return;
				}
				window.location.href =  _ctxPath + "/admin/product/product-view.htm?product.productId="+nextProductId;
			}
		});
	});
	
	//审核通过
	$("#pass").bind("click",function(){
		var productId = $("#productId").val();
		var value = "product.productId="+productId
					+"&product.status="+STATUS_PASS;
		$.ajax({
			type: "POST",
			url: _ctxPath + "/admin/product/product-saveReviewResult.htm",
			data: value,
			success:function(msg){
				if(msg.code == "true"){
					$.dialog({
					    title: false,
					    content: msg.info,
					    lock: true,
					    fixed: true,
					    ok: function(){
					    	$.ajax({
			        			type: "POST",
			        			url: _ctxPath + "/admin/product/product-nextPending.htm",
			        			data: "product.productId="+productId,
			        			success:function(msg){
			        				var productId = msg.info;
			        				if(!productId){
			        					popupDialog("已经是最后一条待审核记录！");
			        					return;
			        				}
			        				window.location.href = _ctxPath + "/admin/product/product-review.htm?product.productId="+productId;
			        			}
					    	});
					    },
					    okValue:"下一条",
	   				    cancel: function(){
	   				    	window.location.reload();
	   				    },
	   				    cancelValue: "取  消"
					});
				}
			},
			error: function(msg){
				popupDialog("error:"+msg.info);
	        }
		});
	});
	
	//审核不通过
	$("#notPass").click(function(){
		var remark = $("#remark").val();
		if(remark == $("#remark").attr("data-default")){
			$("#remarkTip").show();
			return;
		}
		var productId = $("#productId").val();
		var value = "product.productId="+productId
					+"&product.status="+STATUS_NO_PASS+"&product.remark="+remark;
		$.ajax({
			type:'POST',
			url: _ctxPath + '/admin/product/product-saveReviewResult.htm',
			data: value,
			success:function(msg){
				if(msg.code == "true"){
					$.dialog({
					    title: false,
					    content: msg.info,
					    lock: true,
					    fixed: true,
					    ok: function(){
					    	$.ajax({
			        			type: "POST",
			        			url: _ctxPath + "/admin/product/product-nextPending.htm",
			        			data: "product.productId="+productId,
			        			success:function(msg){
			        				var productId = msg.info;
			        				if(!productId){
			        					popupDialog("已经是最后一条待审核记录！");
			        					return;
			        				}
			        				window.location.href = _ctxPath + "/admin/product/product-review.htm?product.productId="+productId;
			        			}
					    	});
					    },
					    okValue:"下一条",
	   				    cancel: function(){
	   				    	window.location.reload();
	   				    },
	   				    cancelValue: "取  消"
					});
				}
			},
			error: function(msg){
				popupDialog("error:"+msg.info);
	        }
		});
	});
	
	//返回
	$("#back").click(function(){
		window.location.href = _ctxPath + '/admin/product/product-searchProducts.htm';
	});
});