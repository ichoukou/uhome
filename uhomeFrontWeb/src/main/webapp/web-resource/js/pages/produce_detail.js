/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-7
 * Time: 下午5:46
 * Description:
 */
//顶部悬浮选择规格弹窗函数
var clearPop = function(){
	var clone = $('.goods-sort-list').get(0);
	$(".goods-org-item").after(clone);
	$('#popup10').remove();
	$('.mask').remove();
};
var quickChoose = function(ele){
	var clone = $('.goods-sort-list').get(0),
		str = '<div class="popup" style="z-index: 101; position: fixed;_position:absolute;width: 280px;padding: 15px;top: 50%;left: 50%;_top:expression(eval(document.documentElement.scrollTop+(document.documentElement.clientHeight/2)));margin: -90px 0 0 -140px;" id="popup10">' +
    '<div class="popup-hd"><a href="javascript:;" class="ico-close" title="关闭"></a></div> '+
    '<div class="popup-bd"> '+
        '<div class="pro-details" id="forChoose"></div>' +
    '</div>'+
    '<div class="popup-ft"><a href="javascript:;" class="btn '+ele+'">确定</a></div> ' +
	'</div>'+
	'<div class="mask" style="z-index: 100; position: fixed;_position:absolute; left: 0px; top: 0px;_top:expression(eval(document.documentElement.scrollTop)); width: 100%; height: 100%; overflow: hidden;"></div>';
	$('body').append(str);
	$('#forChoose').append(clone);
	$('.ico-close').on('click',function(){
		clearPop();
	});	
}
$(function(){
    //zoom放大
    $('#multizoom').addimagezoom({ // multi-zoom: options same as for previous Featured Image Zoomer's addimagezoom unless noted as '- new'
        descArea: '#description', // description selector (optional - but required if descriptions are used) - new
        speed: 0, // duration of fade in for new zoomable images (in milliseconds, optional) - new
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
    //倒计时
    $(".countdown").each(function () {
        var $this = $(this),
            data = $this.attr('data-config');
        $this.Merlin({
            "countDown": eval('(' + data + ')')
        });
    });
    //选项卡
    $(".tab-gn").Merlin({
        "tabSwitcher":{
            tabCls:".panel li",
            trigger:"click",
            itemCls: ".protab-item"
        }
    });
    //规格尺码判断
    if(!$('.skuOptionValueMap_1').length){
        $('.goods-sort-list .tit').css('width',40);
    }
    //顶部悬浮条
     var offProMain = $('.pro-main').offset();
     $(window).scroll(function(){
         if($(this).scrollTop() > offProMain.top){
             $('.shop-top-bar').fadeIn();
         }else{
             $('.shop-top-bar').fadeOut();
             clearPop();
         }
     });
    //最近浏览样式
	$(".static-mod .bd dl").hover(function(){
		$(this).addClass("hover");
	},function(){
		$(this).removeClass("hover");
	});
	//数量只可以输入数字
	$(".product_num").keyup(function(event) {
        $this = $(this);
        var value = $this.val();
        if(value!=""){
			var reg = /[^0-9]/g;
			value = value.replace(reg,"")
			if(parseInt(value)==0){
				value = "";
			}
			if(value.length>9){
				value = value.substring(0,9);
			}
			$this.val(value);
		}
    });
});
//sku只有一个默认选中  1.只有颜色且只有一个颜色 2.只有规格且只有一个规格 3.2个都有且2个都只有一个选项
function chooseDefaultSku(spanClick){
	var skuOptionColor = $(".skuOptionValueMap_0");
	var skuOptionGauge = $(".skuOptionValueMap_1");
	//只有颜色
	if(skuOptionColor.length > 0 && skuOptionGauge.length == 0){
		var spanColor = skuOptionColor.find("span");
		if(spanColor.length == 1){
			spanColor[0].click(spanClick);
		}
		return;
	}
	//只有规格
	if(skuOptionColor.length == 0 && skuOptionGauge.length > 0){
		var spanGauge = skuOptionGauge.find("span");
		if(spanGauge.length == 1){
			spanGauge[0].click();
		}
		return;
	}
	//规格颜色都有
	if(skuOptionColor.length > 0 && skuOptionGauge.length > 0){
		var spanColor = skuOptionColor.find("span");
		var spanGauge = skuOptionGauge.find("span");
		if(spanColor.length == 1 && spanGauge.length == 1){
			spanColor[0].click(spanClick);
			spanGauge[0].click();
		}
		return;
	}
}

function clearOutOfRangeInventory(){
	//如果文本框的数量大于当前sku库存  失去焦点 去除 库存不足
	if($(".product_num").hasClass("hotwarning") && $(".showInventory").find("em").text() == "超出库存"){
		$(".product_num").removeClass("hotwarning");
		$(".showInventory").addClass("m-hidden");
	}
}

//用户自己改变商品数量
function editProductNum(skuOptionJson,productSkuInventoryJson,productSkuId,productInventory){
	$this = $(".product_num")
	var oldProductNum = ""
	if($this.length > 1){
		oldProductNum = $($this[1]).val();
	}else{
		oldProductNum = $this.val();
	}
	
	var productNum = oldProductNum;
	//判断选中具体sku没有
	if(productSkuId == undefined || productSkuId == 0){
		if(productNum <= 0){
			productNum = 1;
		}
		//判断是否大于总库存
		if(productNum >= productInventory){
			productNum = productInventory;
		}
	}
	//如果选中了具体的sku
	if(productSkuId != undefined && productSkuId != 0){
		skuInventory = productSkuInventoryJson[productSkuId];
		//如果换一个sku  可能对应的库存小于现在sku的库存
		if(productNum >= skuInventory){
			productNum = skuInventory;
			//如果文本框的数量大于当前sku库存  失去焦点 去除 库存不足
			clearOutOfRangeInventory();
		}
		if(productNum <= 0){
			productNum = 1;
		}
		//如果库存为0
		if(skuInventory == 0 && productNum <= 0){
			productNum = 0;
		}
	}
	if(oldProductNum != productNum){
		$this.attr("value",productNum);
	}
	if($this.length > 1){
		$($this[0]).attr("value",$($this[1]).val());
	}
}

//商品数量加减
function addProductNum(skuOptionJson,productSkuInventoryJson,productSkuId,productInventory){
	$this = $(".product_num")
	productNum = $this.val();
	//判断选中具体sku没有
	if(productSkuId == undefined || productSkuId == 0){
		productNum = Number(productNum) + Number(1);
		//判断是否大于总库存
		if(productNum >= productInventory){
			productNum = productInventory;
		}
	}
	//如果选中了具体的sku
	if(productSkuId != undefined && productSkuId != 0){
		skuInventory = productSkuInventoryJson[productSkuId];
		productNum = Number(productNum) + Number(1);
		if(productNum >= skuInventory){
			productNum = skuInventory;
			//如果文本框的数量大于当前sku库存  失去焦点 去除 库存不足
			clearOutOfRangeInventory();
		}
	}
	$this.attr("value",productNum);
}
function reduceProductNum(skuOptionJson,productSkuInventoryJson,productSkuId){
	$this = $(".product_num")
	productNum = $this.val();
	//判断选中具体sku没有
	if(productSkuId == undefined || productSkuId == 0){
		productNum = Number(productNum) - Number(1);
		//productNum等于0  购买数量最小为1
		if(productNum <= 0){
			productNum = 1;
		}
	}
	//如果选中了具体的sku
	if(productSkuId != undefined && productSkuId != 0){
		skuInventory = productSkuInventoryJson[productSkuId];
		productNum = Number(productNum) - Number(1);
		//如果换一个sku  可能对应的库存小于现在sku的库存
		if(productNum >= skuInventory){
			productNum = skuInventory;
			//如果文本框的数量大于当前sku库存  失去焦点 去除 库存不足
			clearOutOfRangeInventory();
		}
		if(productNum <= 0){
			productNum = 1;
		}
		//如果库存为0
		if(skuInventory == 0 && productNum <= 0){
			productNum = 0;
		}
	}
	$this.attr("value",productNum);
}

//显示库存紧张信息
function showInventoryInfo(minInventory,skuInventory){
	//$(".showInventory").text("").append(skuInventory);
	//如果库存小于规定的数量 显示库存紧张
	if(minInventory > skuInventory && skuInventory > 0 ){
		$(".showInventory").removeClass("m-hidden").find("em").empty().text("库存紧张");
	}
	if(skuInventory >= minInventory){
		$(".showInventory").addClass("m-hidden");
		$(".product_num").removeClass("hotwarning");
	}
}
//购买时提示库存信息
function showInventoryInfo4Buy(minInventory,curSkuInventory,buyNum){
	if(curSkuInventory <= 0 ){
		 $(".showInventory").removeClass("m-hidden").find("em").empty().text("库存不足");
		 return true;
	 }
	//数量大于库存数量
	 if(buyNum > curSkuInventory){
		 $(".product_num").addClass("hotwarning");
		 $(".showInventory").removeClass("m-hidden").find("em").empty().text("超出库存");
		 return true;
	 }
	 $(".product_num").removeClass("hotwarning");
	//如果库存 大于 规定数量隐藏 否则 显示
	 if(curSkuInventory < minInventory){
		 $(".showInventory").removeClass("m-hidden").find("em").empty().text("库存紧张");
	 }else{
		 $(".showInventory").addClass("m-hidden").find("em").empty().text("库存紧张");
	 }
	return false;
}
//提示选中颜色或者规格
function showColorGaugePrompt(color,gauge,skuOptionJson){
	 //根据SkuOptionValue选择skuId
 	 var productSkuId = skuOptionJson[color+"-"+gauge];
	 //有选项且用户没有选择选项
	 if(productSkuId == undefined){
		 productSkuId = skuOptionJson[color];
		 if(productSkuId == undefined && color == undefined){
			 popupDialog("请选择商品的颜色","p1005");
			 return true;
		 }
		 //如果颜色没有 在验证 规格
		 if(productSkuId == undefined){
			 productSkuId = skuOptionJson[gauge];
			 if(productSkuId == undefined && gauge == undefined){
				 popupDialog("请选择商品的规格/尺码","p1006");
				 return true;
			 }
		 }
		 if(productSkuId == undefined){
			 popupDialog("请选择商品的颜色或者规格/尺码","p1007");
			 return true; 
		 }
	 }
	 return false;
}
//加入购物车和立即购买 type=秒杀或特卖 buyType=加入购物车或者立即购买
function addCartAndNowBuy(skuOptionJson,productSkuInventoryJson,minInventory,type,buyType,productSkuId){
	//选项为空
 	 if($.isEmptyObject(skuOptionJson)){
		//如果没有规格直接获取sku
		//productSkuId = ${product.productSkus[0].productSkuId};
	 //选项不为空
 	 }else{
		 if(showColorGaugePrompt(color,gauge,skuOptionJson)){
			 return;
		 }
	 }
 	 var curSkuInventory = productSkuInventoryJson[productSkuId];
	 productNum = $('.product_num').val();
	 if(showInventoryInfo4Buy(minInventory,curSkuInventory,productNum)){
		 return;
	 }
	 $.ajax({
			type:'POST',
			url: _ctxPath+'/order/order-addCartProduct.htm',
			data:{"item.productSkuId": productSkuId, "item.num": productNum, "type": type},
			success:function(html){
				//库存不为0时
				if(html.code == "true"){					
	                showTotal(); //把商品总数量显示在前台购物车
	                //立即购买
	                if(buyType == 0){
	                	location.href = _ctxPath+"/order/order-shoppingCart.htm";
	                }
	                //加入购物车
	                if(buyType == 1){
	                	$.dialog({
			                title: false,
			                content: html.info,
			                time: 2000
				            });
						$(".d-close").hide();
						//清除购物弹窗
						clearPop();
	                }
				}else{
					$.dialog({
		                title: false,
		                content: html.info,
		                time: 2000
			            });
					$(".d-close").hide();
					//清除购物弹窗
					clearPop();
				}
			},
			error:function(data){
				popupDialog("网络连接错误!");
			}
		 });
}