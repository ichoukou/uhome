/**
 * 商品预览页面js
 */
$(function(){
	 //当前停留样式增/减
    $(".thumbs a").live("click",function(){
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
    //    选项卡
    $(".tab-gn").Merlin({
        "tabSwitcher":{
            tabCls:"ul li",
            trigger:"click",
            itemCls: ".protab-item"
        }
    });
    
    //基本信息
	var name = $("#name", window.opener.document).val();
	var salePrice = $("#salePrice", window.opener.document).val();
	var marketPrice = $("#marketPrice", window.opener.document).val();
	var rebate = $("#rebate", window.opener.document).val();
	
	$(".crumbs").append(name);
	
	$(".goods-name").text(name);
	$("#salePrice").text(salePrice);
	$("#marketPrice").text(marketPrice);
	$("#rebate").text(rebate);
	
	//尺寸
	var $sizes = $("#size", window.opener.document).find(":checked");
	if($sizes.length > 0){
		$("#size").prev("div").text($("#size", window.opener.document).prev("td").text());
		var strList1 = "";
		$.each($sizes, function(i,size){
			if(i = 0){
				strList1 = "<span class='selected'>"+$(size).closest("dd").find("span").text()+"</span>";
				
			}else{
				strList1 += "<span>"+$(size).closest("dd").find("span").text()+"</span>";
			}
		});
		$("#size").append(strList1);
		
	}
	//颜色
	var $colors = $("#color", window.opener.document).find(":checked");
	if($colors.length > 0){
		$("#color").prev("div").text($("#color", window.opener.document).prev("td").text());
		var strList2 = "";
		$.each($colors, function(i,color){
			if(i=0){
				strList2 = "<span class='selected'>"+$(color).closest("dd").find("span").text()+"</span>";
				
			}else{
				strList2 += "<span>"+$(color).closest("dd").find("span").text()+"</span>";
			}
		});
		$("#color").append(strList2);
	}
	
	
	//库存
	var inventory = $("#tempInventory", window.opener.document).val();
	$("#inventory").text(inventory);
	
	//图片
	var imageUrls = $("#imageUrls", window.opener.document).val();
	$.each(imageUrls.split(","), function(i,url){
		var suffix = url.split(".")[1];
		
		var largeImgUrl = _fileThumbPath + url + "_t10." + suffix;
		var smallImgUrl = _fileThumbPath + url + "_t9." + suffix;
		
		var $a = $("<a></a>");
		if(i == 0){
			$a.attr("class", "cur");
		}
		var $smallImg = $("<img src='"+smallImgUrl+"' alt='"+name+"' title='"+name+"'/>");
		$a.append($smallImg).append("<i></i>");
		$(".thumbs").append($a);
		
		var $largeImg = $("<img src='"+largeImgUrl+"' alt='"+name+"' title='"+name+"'/>")
		if(i != 0){
			$largeImg.hide();
		}
		$(".targetarea").append($largeImg);
	});
	
	//商品描述
	var describe = $("#tempDescribe", window.opener.document).val();
	$("#describe").html(describe);
	
	//图片切换
	 $(".multizoom a").each(function(i){
			$(this).on("mouseenter",function(){
				$(this).addClass("cur").siblings().removeClass("cur");
				$(".targetarea img:eq("+i+")").show().siblings().hide();
			});
		});
		
});