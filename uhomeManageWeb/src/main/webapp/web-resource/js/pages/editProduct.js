var $tr_productSku,$td_color,$td_size;
var $input_productSkuId,$input_color,$input_size,$input_color_override,$input_size_override;
var data = new Array();

//ckeditor操作
CKEDITOR.on('dialogDefinition', function( ev ){
    var dialogName = ev.data.name;
    var dialogDefinition = ev.data.definition;
    if ( dialogName == 'image' ){
        var infoTab = dialogDefinition.getContents('info');
        infoTab.add({
            type : 'button',
            id : 'upload_image',
            align : 'center',
            label : '添加图片',
            onClick : function( evt ){
                var thisDialog = this.getDialog();
                var txtUrlObj = thisDialog.getContentElement('info', 'txtUrl');
                var txtUrlId = txtUrlObj.getInputElement().$.id;
                addUploadImage(txtUrlId);
            }

        },"txtAlt");
    }
});
function addUploadImage(theURLElementId){
    var uploadUrl = _ctxPath + "/upload/image-upload.htm";
    var imgUrl = window.showModalDialog(uploadUrl,'title',
			'resizable=no;help=no;status=no;dialogWidth=400px;dialogHeight=300px'); 
    var urlObj = document.getElementById(theURLElementId);
    if(imgUrl){
	    urlObj.value = imgUrl;
	    onchangeFun(urlObj);
    }
}
function onchangeFun(obj){
	if (document.all) {
		obj.fireEvent("onchange");
	} else {
		var evt = document.createEvent('HTMLEvents');
		evt.initEvent('change', true, true);
		obj.dispatchEvent(evt);
	} 
}

$(document).ready(function(){
	
	// 编辑页面初始化秒杀折扣
	if($("#marketPrice").val()){
		var secKillPrice = $("#secKillPrice").val();
		if(secKillPrice){
			var rebate = (secKillPrice/$("#marketPrice").val()*10).toFixed(1);
			rebate = rebate < 0.1 ? 0.1 : rebate;
			$("#secKillPrice").closest("tr").find("span.rebate").text(rebate);
		}
	}
	
	if(!$("#secKillPrice").val()){
		$("#secKillDescribe").attr("disabled",true).unFormValidator(true); 
	}
	
	//折扣计算
	$("#salePrice").blur(function(){
		if(!$(this).val()){
			$(this).closest("tr").find("span.rebate").text("");
			$("#rebate").val("");
			return;
		}
		var marketPrice = $("#marketPrice").val();
		if(marketPrice){
			var rebate = ($(this).val()/marketPrice*10).toFixed(1);
			rebate = rebate < 0.1 ? 0.1 : rebate;
			$(this).closest("tr").find("span.rebate").text(rebate);
			$("#rebate").val(rebate);
		}
	});
	$("#secKillPrice").blur(function(){
		if(!$(this).val()){
			$(this).closest("tr").find("span.rebate").text("");
			//隐藏秒杀描述输入框
			$("#secKillDescribe").closest("tr").hide();
			//解除校验
			$("#secKillDescribe").attr("disabled",true).unFormValidator(true); 
			return;
		}
		var marketPrice = $("#marketPrice").val();
		if(marketPrice){
			var rebate = ($(this).val()/marketPrice*10).toFixed(1);
			rebate = rebate < 0.1 ? 0.1 : rebate;
			$(this).closest("tr").find("span.rebate").text(rebate);
		}
		//显示秒杀描述输入框
		$("#secKillDescribe").closest("tr").show();
		//恢复校验
		$("#secKillDescribe").attr("disabled",false).unFormValidator(false);
	});
	$("#marketPrice").blur(function(){
		if(!$(this).val()){
			$("#salePrice").closest("tr").find("span.rebate").text("");
			$("#secKillPrice").closest("tr").find("span.rebate").text("");
			$("#rebate").val("");
			return;
		}
		var salePrice = $("#salePrice").val();
		var secKillPrice = $("#secKillPrice").val();
		if(salePrice){
			var rebate = (salePrice / $(this).val() * 10).toFixed(1);
			rebate = rebate < 0.1 ? 0.1 : rebate;
			$("#salePrice").closest("tr").find("span.rebate").text(rebate);
			$("#rebate").val(rebate);
		}
		if(secKillPrice){
			var rebate = (secKillPrice / $(this).val() * 10).toFixed(1);
			rebate = rebate < 0.1 ? 0.1 : rebate;
			$("#secKillPrice").closest("tr").find("span.rebate").text(rebate);
		}
	});
	
	//保存按扭操作
	$("#save").click(function(){
		var result = $.formValidator.pageIsValid('1');
		if(!result){
			return;
		}
		$("#status").val(STATUS_DRAFT);
		handleFormData();
		$.ajax({
			type:'POST',
			url: _ctxPath + '/seller/product/product-save.htm',
			data: $("#addForm").serialize(),
			success:function(msg){
				if(msg.code == "true"){
					var json = $.parseJSON(msg.info);
					data = json.productSkus;
					$("#productId").val(json.productId);
					$.each(data,function(i,obj){
						var id = obj.id;
						var $input_productSkuId = $("#"+id).find(".productSkuId");
						$input_productSkuId.val(obj.productSkuId);
					});
					$("#message").text("保存成功");
					$(".tip-main").show();
					setTimeout(function(){$(".tip-main").hide();},2000);
				}
			},
			error: function(msg){
				$.dialog({
		            title: false,
		            content: "保存商品出错！" + msg.info,
		            cancelValue: "确定",
		            cancel: true
				});
	        }
		});
	});
	
	//提交审核按扭操作
	$("#submitReview").click(function(){
		var result = $.formValidator.pageIsValid('1');
		if(!result){
			return;
		}
		$("#status").val(STATUS_CHECK_PEND);
		handleFormData();
		$.ajax({
			type:'POST',
			url: _ctxPath + '/seller/product/product-submitReview.htm',
			data: $("#addForm").serialize(),
			success:function(msg){
				if(msg.code == "true"){
					$("#submitReview").attr("disabled", true);
					$("#message").text(msg.info);
					$(".tip-main").show();
					//3秒钟后跳转到商品列表页面
					setTimeout("window.location.replace(_ctxPath + '/seller/product/product-searchSellerProducts.htm')", 3000);							
				}
			},
			error: function(msg){
				$.dialog({
		            title: false,
		            content: "提交审核出错！" + msg.info,
		            cancelValue: "确定",
		            cancel: true
				});
	        }
		});
		
	});
	
	//预览按扭操作
	$("#preview").click(function(){
		var result = $.formValidator.pageIsValid('1');
		if(!result){
			return;
		}
		handleFormData();
		$("#addForm").attr("action", _ctxPath + "/seller/product/product-preview.htm");
		$("#addForm").submit();
	});
	
	//提交审核并复制操作
	$("#submitReviewAndCopy").click(function(){
		var result = $.formValidator.pageIsValid('1');
		if(!result){
			return;
		}
		handleFormData();
		$("#submitReview").click();
		$("#addForm").attr("action", _ctxPath + "/seller/product/product-submitReviewAndCopy.htm");
		$("#addForm").submit();
	});
	
	//sku选项值复选框事件
	$(".input-checkbox").click(function(){
		createRepertoriesTable();
		var $dd = $(this).closest("dd");
		var $span = $dd.find("span");
		if($(this).attr("checked") != "checked" ){
			$dd.find(".c-input").remove();
			$span.show();
			return;
		}
		$span.hide().after("<input type='text' class='c-input' value='"+$span.text()+"' onchange='checkSkuInput(this)'/>");
	});
	
	//如果有上传图片，显示交换按钮
	$(".default-img").hover(function(){
		if($(this).find("img")[0]){
			$(this).find("i").show();
			$(this).find(".del-img").show();
		}
	},function(){
		$(this).find("i").hide();
		$(this).find(".del-img").hide();
	});
	//图片向前排序
	$(".img-prev").on("click",function(){
		var this_td = $(this).parent().parent();
		var prev_td = $(this_td).prev("td");
		$(prev_td).before(this_td);
	});
	//图片向后排序
	$(".img-next").on("click",function(){
		var this_td = $(this).parent().parent();
		var next_td = $(this_td).next("td");
		var no_img = $(next_td).attr("id");
		if(no_img != "no_img"){
			$(next_td).after(this_td);
		}
	});
	//删除图片
	$(".del-img").on("click",function(){
		$(this).next("img").remove();
		$(this).closest("td").find(".imageUrl").val("");
	});
});

function handleFormData(){
	//设置商品图片url
	var imageUrls = "";
	$.each($(".no-border-table .imageUrl"),function(i,obj){
		var url = $(obj).val();
		if(url){
			imageUrls += url + ",";	
		}
	});
	if(imageUrls.length > 0){
		imageUrls = imageUrls.substring(0, imageUrls.length-1);
	}
	$("#imageUrls").val(imageUrls);
	//关键词
	var keyWord = $("#brandId option:selected").text() + "," + $("#name").val();
	$("#keyWord").val(keyWord);
	//商品描述
	$("#describe").val(CKEDITOR.instances.describe.getData());
}

//变量初始化设置
function variableInit(){
	$tr_productSku = $("<tr></tr>");
	$input_productSkuId = $("<input style='display:none;' type='text' class='productSkuId'/>");
	$input_color = $("<input style='display:none;' type='text' />");
	$input_size = $("<input style='display:none;' type='text' />");
	$input_color_override = $("<input style='display:none;' type='text' />");
	$input_size_override = $("<input style='display:none;' type='text' />");
	$td_color = $("<td></td>");
	$td_size = $("<td></td>");
}

//库存表格js操作
function createRepertoriesTable(){
	$(".repertories").find("tr:gt(0)").remove();
	
	//已选颜色
	var $colors = $("#color input:checked");
	//已选尺寸
	var $sizes = $("#size input:checked");
	
	//没勾选一个选项时
	if($colors.length == 0 && $sizes.length == 0){
		variableInit();
		
		$tr_productSku.attr("id", "_");
		var strList = "<td><input type='text' class='repertories-num inventory' maxlength='5' name='product.productSkus[0].inventory' onchange='checkInventory(this)'/></td>"
					+ "<td><input type='text' class='repertories-num secKillInventory' maxlength='5' name='product.productSkus[0].secKillInventory' onchange='checkSecKillInventory(this)'/></td>"
					+ "<td><input type='text' class='repertories-num repertories-width internationalCode' maxlength='64' name='product.productSkus[0].internationalCode' onchange='checkInternationalCode(this)'/></td>"
					+ "<td><input type='text' class='repertories-num repertories-width skuCode' maxlength='64' name='product.productSkus[0].skuCode' onchange='checkSkuCode(this)'/></td>";
		
		$input_productSkuId.attr("name", "product.productSkus[0].productSkuId");
		//将隐藏文本框放入尺寸单元格中
		$td_size.append($input_productSkuId);
		
		$tr_productSku.append($td_color).append($td_size).append(strList);
		$(".repertories").append($tr_productSku);
	}
	//仅勾选了尺寸时
	else if($colors.length == 0 && $sizes.length > 0){
		var index = 0;
		$.each($sizes, function(i,size){
			variableInit();
			
			$tr_productSku.attr("id", $(size).val() + "_");
			var strList = "<td><input type='text' class='repertories-num inventory' maxlength='5' name='product.productSkus["+index+"].inventory' onchange='checkInventory(this)'/></td>"
						+ "<td><input type='text' class='repertories-num secKillInventory' maxlength='5' name='product.productSkus["+index+"].secKillInventory' onchange='checkSecKillInventory(this)'/></td>"
						+ "<td><input type='text' class='repertories-num repertories-width internationalCode' maxlength='64' name='product.productSkus["+index+"].internationalCode' onchange='checkInternationalCode(this)'/></td>"
						+ "<td><input type='text' class='repertories-num repertories-width skuCode' maxlength='64' name='product.productSkus["+index+"].skuCode' onchange='checkSkuCode(this)'/></td>";
			
			$input_productSkuId.attr("name", "product.productSkus["+index+"].productSkuId");
			$input_size.attr("name", "product.productSkus["+index+"].productSkuOptionValues[0].skuOptionValueId");
			$input_size.val($(size).val());
			$input_size_override.attr("name", "product.productSkus["+index+"].productSkuOptionValues[0].overrideSkuOptionValue");
			
			$td_size.text($(size).closest("dd").find("span").text());
			//将隐藏文本框放入尺寸单元格中
			$td_size.append($input_productSkuId).append($input_size);
			
			if($(size).attr("overrideFlag") == "1"){
				$input_size_override.val($(size).closest("dd").find("span").text());
				$td_size.append($input_size_override);
			}
			
			$tr_productSku.append($td_color).append($td_size).append(strList);
			$(".repertories").append($tr_productSku);
			index++;
		});
	}
	//仅勾选了颜色时
	else if($colors.length > 0 && $sizes.length == 0){
		var index = 0;
		$.each($colors, function(i,color){
			variableInit();
			
			$tr_productSku.attr("id", $(color).val() + "_");
			var strList = "<td><input type='text' class='repertories-num inventory' maxlength='5' name='product.productSkus["+index+"].inventory' onchange='checkInventory(this)'/></td>"
						+ "<td><input type='text' class='repertories-num secKillInventory' maxlength='5' name='product.productSkus["+index+"].secKillInventory' onchange='checkSecKillInventory(this)'/></td>"
						+ "<td><input type='text' class='repertories-num repertories-width internationalCode' maxlength='64' name='product.productSkus["+index+"].internationalCode' onchange='checkInternationalCode(this)'/></td>"
						+ "<td><input type='text' class='repertories-num repertories-width skuCode' maxlength='64' name='product.productSkus["+index+"].skuCode' onchange='checkSkuCode(this)'/></td>";
			
			$input_productSkuId.attr("name", "product.productSkus["+index+"].productSkuId");
			$input_color.attr("name", "product.productSkus["+index+"].productSkuOptionValues[0].skuOptionValueId");
			$input_color.val($(color).val());
			$input_color_override.attr("name", "product.productSkus["+index+"].productSkuOptionValues[0].overrideSkuOptionValue");
			
			$td_color.text($(color).closest("dd").find("span").text());
			//将隐藏文本框放入尺寸单元格中
			$td_size.append($input_productSkuId).append($input_color);
			
			if($(color).attr("overrideFlag") == "1"){
				$input_color_override.val($(color).closest("dd").find("span").text());
				$td_size.append($input_color_override);
			}
			
			$tr_productSku.append($td_color).append($td_size).append(strList);
			$(".repertories").append($tr_productSku);
			index++;
		});
	}
	//颜色与尺寸均勾选了
	else if($colors.length > 0 && $sizes.length > 0){
		var index = 0;
		$.each($colors, function(i,color){
			$.each($sizes, function(i,size){
				variableInit();
				
				$tr_productSku.attr("id", $(color).val() + "_" + $(size).val());
				var strList = "<td><input type='text' class='repertories-num inventory' maxlength='5' name='product.productSkus["+index+"].inventory' onchange='checkInventory(this)'/></td>"
							+ "<td><input type='text' class='repertories-num secKillInventory' maxlength='5' name='product.productSkus["+index+"].secKillInventory' onchange='checkSecKillInventory(this)'/></td>"
							+ "<td><input type='text' class='repertories-num repertories-width internationalCode' maxlength='64' name='product.productSkus["+index+"].internationalCode' onchange='checkInternationalCode(this)'/></td>"
							+ "<td><input type='text' class='repertories-num repertories-width skuCode' maxlength='64' name='product.productSkus["+index+"].skuCode' onchange='checkSkuCode(this)'/></td>";
				
				$input_productSkuId.attr("name", "product.productSkus["+index+"].productSkuId");
				//颜色相关值设置
				$input_color.attr("name", "product.productSkus["+index+"].productSkuOptionValues[0].skuOptionValueId");
				$input_color.val($(color).val());
				$input_color_override.attr("name", "product.productSkus["+index+"].productSkuOptionValues[0].overrideSkuOptionValue");
				
				$td_color.text($(color).closest("dd").find("span").text());
				$td_color.attr("rowspan", $sizes.length);
				//尺寸相关值设置
				$input_size.attr("name", "product.productSkus["+index+"].productSkuOptionValues[1].skuOptionValueId");
				$input_size.val($(size).val());
				$input_size_override.attr("name", "product.productSkus["+index+"].productSkuOptionValues[1].overrideSkuOptionValue");
				
				$td_size.text($(size).closest("dd").find("span").text());
				//将隐藏文本框放入尺寸单元格中
				$td_size.append($input_productSkuId).append($input_color).append($input_size);
				
				if($(color).attr("overrideFlag") == "1"){
					$input_color_override.val($(color).closest("dd").find("span").text());
					$td_size.append($input_color_override);
				}
				if($(size).attr("overrideFlag") == "1"){
					$input_size_override.val($(size).closest("dd").find("span").text());
					$td_size.append($input_size_override);
				}
				
				if(i == 0){
					$tr_productSku.append($td_color).append($td_size).append(strList);
				}else{
					$tr_productSku.append($td_size).append(strList);
				}
				$(".repertories").append($tr_productSku);
				
				index++;
			});

		});
	}
	
	//刷新库存表格时恢复已保存的sku数据
	if(data.length>0){
		$.each(data,function(i,obj){
			var id = obj.id;
			
			//为只有一个sku属性的商品增加另一个sku属性
			if(id.indexOf("_") == id.length-1){
				var skuOptValueId = id.substring(0,id.length-1);
				var $color = $("#color input:checked");
				if($color.length == 1 && $color.val() != skuOptValueId){
					id = $color.val() + "_" + skuOptValueId;
				}else{
					var $size = $("#size input:checked");
					if($size.length == 1 && $size.val() != skuOptValueId){
						id = skuOptValueId + "_" + $size.val();
					}
				}
			}
			
			var $productSku_tr = $("#"+id);
			if($productSku_tr.length == 0){
				return;
			}
			//设置productSkuId
			var $input_productSkuId = $productSku_tr.find(".productSkuId");
			$input_productSkuId.val(obj.productSkuId);
			//设置inventory
			var $input_inventory = $productSku_tr.find(".inventory");
			$input_inventory.val(obj.inventory);
			//设置secKillInventory
			var $input_secKillInventory = $productSku_tr.find(".secKillInventory");
			$input_secKillInventory.val(obj.secKillInventory);
			$input_secKillInventory.attr("seckillPlanNum", obj.seckillPlanNum);
			if(parseInt(obj.seckillPlanNum) > 0){
				$input_secKillInventory.attr("disabled", true);
			}
			//设置internationalCode
			var $input_internationalCode = $productSku_tr.find(".internationalCode");
			$input_internationalCode.val(obj.internationalCode);
			//设置skuCode
			var $input_skuCode = $productSku_tr.find(".skuCode");
			$input_skuCode.val(obj.skuCode);
		});
	}
	
	data.splice(0,data.length);
	$.each($(".repertories").find("tr:gt(0)"), function(i,tr){
		var $tr = $(tr);
		var num = $tr.find(".secKillInventory").attr("seckillPlanNum");
		if(!num){
			num = "0";
		}
		var value = {
			"id" : $tr.attr("id"),
			"productSkuId" : $tr.find(".productSkuId").val(),
			"inventory" : $tr.find(".inventory").val(),
			"secKillInventory" : $tr.find(".secKillInventory").val(),
			"internationalCode" : $tr.find(".internationalCode").val(),
			"skuCode" : $tr.find(".skuCode").val(),
			"seckillPlanNum" : num
		};
		data.push(value);
	});
}
function checkSkuInput(obj){
	var $this = $(obj);
	var $dd = $this.closest("dd");
	var $input_checkbox = $dd.find(".input-checkbox");
	var overrideFlag = $input_checkbox.attr("overrideFlag");
	var temp = $this.val();
	
	var flag = true;	
	var tdId = $dd.closest("td").attr("id");
	if(tdId == "color"){
		flag = checkColor(temp);
	}else if(tdId == "size"){
		flag = checkSize(temp);
	}
	if(!flag){
		temp = $dd.find("span").text();
		$this.val(temp);
	}else if(overrideFlag != "1"){
		$input_checkbox.attr("overrideFlag", "1"); //标志被修改过
	}
	$this.closest("dd").find("span").text(temp);
	createRepertoriesTable();
}
function checkColor(value){
	var regFn = /^[\u4E00-\u9FA5]{1,4}$/;
	return regFn.test(value);
}
function checkSize(value){
	var len = $.trim(value).length;
	if(len < 1 || len > 15){
		return false;
	}else{
		return true;
	}
}
function checkInventory(domObj){
	var regFn = /^\d{0,5}$/;
	if(!regFn.test(domObj.value)){
		domObj.value = "";
		domObj.focus();
	}
	var $this = $(domObj);
	var id = $this.closest("tr").attr("id");
	$.each(data, function(i,obj){
		if(id == obj.id){
			obj.inventory = $this.val();
		}
	});
}
function checkSecKillInventory(domObj){
	var regFn = /^\d{0,5}$/;
	if(!regFn.test(domObj.value)){
		domObj.value = "";
		domObj.focus();
	}
	var $this = $(domObj);
	var id = $this.closest("tr").attr("id");
	$.each(data, function(i,obj){
		if(id == obj.id){
			obj.secKillInventory = $this.val();
		}
	});
}
function checkInternationalCode(domObj){
	var $this = $(domObj);
	var id = $this.closest("tr").attr("id");
	$.each(data, function(i,obj){
		if(id == obj.id){
			obj.internationalCode = $this.val();
		}
	});
}
function checkSkuCode(domObj){
	var $this = $(domObj);
	var id = $this.closest("tr").attr("id");
	$.each(data, function(i,obj){
		if(id == obj.id){
			obj.skuCode = $this.val();
		}
	});
}