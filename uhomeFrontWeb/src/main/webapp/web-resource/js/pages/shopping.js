/**
 * Created with JetBrains WebStorm.
 * User: jVan
 * Date: 13-3-2
 * Time: 下午2:59
 * Description:
 */
var cart = { //购物车对象
    opt: {
        oneLevel: $(".order-list-hd :checkbox"), //购物车头部复选框
        twoLevel: $(".order-brand-top :checkbox"), //购物车内品牌卖家头部复选框
        allCkb: $("#show :checkbox"), //购物车内所有复选框个数(含品牌卖家头部复选框)
        cartEle: $("#show"), //购物车商品展示盒子
        cartCkb: $("#show .ckb").length, //购物车内所有复选框个数(不含品牌卖家头部复选框)
        payMon: $(".order-price"), //商品金额
        flag: false, //开关
        arrSub: [], //子数组-商品
        arrPar: [], //父数组-卖家
        arrPri: [], //单价数组
        arrFreight: [],//运费数组
        arrSkus: [], //skuids
        plus: $(".plus"), //递减对象
        subStru: $(".substru") //累加对象
    },
    int: function(){
    },
    judgeBool: function(){ //判断购物车中商品是否被勾选
        var checked = $(".ckb:checked"),
            bTopCked = $(".order-brand-top :checked"); //购物车内品牌头部已选复选框
        cart.opt.arrSub = [];
        cart.opt.arrPar = [];
        cart.opt.arrSkus = [];
        if (checked.length) {
            cart.opt.flag = true; //开启开关
            $.each(checked, function () {
                var $this = $(this),
                    val = $this.prop("value"),
                    curTr = $("#"+val),
                    subBra = $("#"+curTr.attr("data-item"));
                cart.opt.arrSub.push(curTr);
                cart.opt.arrSkus.push(val);
            })
        }
        else {
            cart.opt.flag = false; //重置开关
        }
        if(bTopCked.length){
            $.each(bTopCked,function(){
                var brand = $(this).closest(".order-brand");
                cart.opt.arrPar.push(brand);
            })
        }
    },
    editVal: function(obj,i){ //修改商品的显示数目
        var tr = obj.closest("tr"),
            inpVal = tr.find(".order-value"),
            pointVal = tr.find(".order-point"),
            unitPrice = tr.find(".unit-price"),
            ordPrice = tr.find(".order-price"),
            maxVal = tr.find(".hide-value");
        //修改本地数据
        $.ajax({
            type : 'POST',
            url  : _ctxPath + '/order/order-adjustCartItem.htm',
            data : {"item.productSkuId": tr.find('.ckb').val(), "item.num" : i},
            success:function(html){
            	inpVal.val(i);
            	showTotal(); //执行购物车数量函数
                var sigPri =unitPrice.text();
                var total = new BigDecimal(sigPri).multiply(new BigDecimal(i.toString()));
                ordPrice.html(total.toString());
                pointVal.text(parseInt(ordPrice.text()));
                cart.showTotal(); //给商品金额求总和
                var classNmL = (parseInt(inpVal.val()) == 1) ? "plus disable" : "plus";
                var classNmR = (parseInt(inpVal.val()) == maxVal.val()) ? "substru disable" : "substru";
                var input = parseInt(inpVal.val());
                var max =  parseInt(maxVal.val());
                tr.find(".plus").removeClass().addClass(classNmL);
                tr.find(".substru").removeClass().addClass(classNmR);
                if(input > max || max == 0){
        			tr.find(".warnning").show();
                }else{
                	tr.find(".warnning").hide();
                }
            }
        });

    },
    sum: function(arguments){ //数组求和
        var r=0;
        for (var i=0;i<arguments.length ;i++ )
        {
            r=new BigDecimal(arguments[i].toString()).add(new BigDecimal(r.toString()));
        }
        return r;
    },
    showTotal: function(){ //显示商品总额
        cart.opt.arrPri = [];
        $.each($(".order-price"),function(){
        	if($(this).parent().parent().find(".ckb").prop("checked")){
        		 cart.opt.arrPri.push($(this).text());
        	}
        });
        var result = cart.sum(cart.opt.arrPri);
        $(".pro-sum var").text("¥"+result.toString());
        cart.freightTotal();
    },
    delCartItem: function(obj, callback){
    	var arr = null;
    	if(obj instanceof Array){
    		arr = obj;
    	} else {
    		arr = new Array();
        	arr.push(obj);
    	}
        var skuIds = $.param({'skuIds':arr},true);
        $.ajax({
            type : 'POST',
            url  : _ctxPath + '/order/order-delCartProduct.htm',
            data : skuIds,
            success:function(html){
            	callback();
            }
        });
        
    },
    freightTotal: function(){ //显示运费总额
    	cart.opt.arrFreight = [],
    	$(".order-brand").each(function(i,n){
    		var $orderBrandTop = $(n).children(".order-brand-top"),
    			$orderBrandMain = $(n).children(".order-brand-main"),
    			bflag = true,tempPostage = 0;
    		
    		$orderBrandMain.find("input[class='ckb']:checked").each(function(i,n){
    			var postageStr = $(n).next("input[name='sku-postage']").val();
    			if(new BigDecimal(postageStr.toString()).compareTo(new BigDecimal("0"))==0){
    				bflag = false;
    			}else{
    				tempPostage = postageStr;
    			}
    		});
    		if(bflag&&tempPostage!=0){  
    			$orderBrandTop.children(".fare").html("<var>"+tempPostage+"</var>元");
    			cart.opt.arrFreight.push(tempPostage);
    		}else{
    			$orderBrandTop.children(".fare").text("免运费");
    		}
    	});
        $(".fare var").each(function(i){
        	//cart.opt.arrFreight.push($(this).text());
        });
        var result = cart.sum(cart.opt.arrFreight);
        $(".transport-sum var").text("¥"+result.toString());
    }
}
 $(function(){
     //初始化选择有效数据
//     $.each($(".ckb"),function(){
//         var $this = $(this);
//         if($this.closest("tr").find(".warnning")){
//             console.log(1);
//             $this.prop("checked",true);
//         }
//     });
     //初始化判断商品数量左侧递减按钮是否被激活状态
     $(".plus").each(function(){
         var $this = $(this);
         if(parseInt($this.next().val()) == 1){
             $this.addClass("disable");
         }
     });
     //初始化判断商品数量右侧递增按钮是否被激活状态
     $(".substru").each(function(){
         var $this = $(this),
             maxVal = $this.closest("tr").find(".hide-value"),
             max =  parseInt(maxVal.val()),
             curr = parseInt($this.prev().val());
         if(curr >= max){
             $this.addClass("disable");
         }
     });
     //初始化时检测checkbox是否激活，并添加当条数据选中状态
     $(".ckb").each(function(){
         var $this = $(this);
         if(!$this.closest("tr").find(".warnning").get(0)){
             $this.prop("checked",true);
             var breadMn = $this.closest(".order-brand-main"),
                 allCkb = breadMn.find(".ckb").length,
                 ckbed = breadMn.find(".ckb:checked").length;
             if($this.get(0).checked){
                 $this.closest("tr").addClass("selected");
             }
             if(ckbed === allCkb){
                 breadMn.prev().find(":checkbox").prop("checked",true);
             }
         }
     });
     //初始化检测品牌头checkbox是否激活，并勾选全选
     var braTopCkd = $(".order-brand-top input:checked");
     setTimeout(function(){
         if(braTopCkd.length == cart.opt.twoLevel.length){
           cart.opt.oneLevel.prop("checked",true);
         }
     },0);
     //解除购物车事件
     $(".cart-sum").off("mouseover");
     // 购物车一级全反选
     cart.opt.oneLevel.on("click",function(){
         var $this = $(this),
             brandBox = cart.opt.cartEle.find(".order-brand-main tr");
         if($this.prop("checked") == true){
             cart.opt.allCkb.prop("checked",true);
             brandBox.addClass("selected");
         }
         else{
             cart.opt.allCkb.prop("checked",false);
             brandBox.removeClass("selected");
         }
         //给商品金额求总和
         cart.showTotal();
     });
     //购物车二级全反选
     cart.opt.twoLevel.on("click",function(){
         var $this = $(this),
             topCkbLen = $this.closest("#show").find(".order-brand-top :checkbox").length,
             brandBox = $this.closest(".order-brand-top").next(),
             itemTr = brandBox.find("tr"),
             subEle = brandBox.find(":checkbox");
         if($this.prop("checked")){
             subEle.prop("checked",true);
             itemTr.addClass("selected");
         }
         else{
             subEle.prop("checked",false);
             itemTr.removeClass("selected");
         }
         setTimeout(function(){
             var len = $this.closest("#show").find(".order-brand-top :checked").length;
             if(len === topCkbLen){
                 cart.opt.oneLevel.prop("checked",true);
             }
             else{
                 cart.opt.oneLevel.prop("checked",false);
             }
         },0)
         //给商品金额求总和
         cart.showTotal();
     });
     //单选满时回选父级
     $(".ckb").on("change", function(){
         var $this = $(this),
             brandBox = $("#"+$this.attr("value")), //当前tr
             parEle = $("#"+brandBox.attr("data-item")), //当前品牌父级盒子
             topCkb =  parEle.find(".order-brand-top :checkbox"), //当前品牌头部复选框
             subCkb = parEle.find(".order-brand-main :checkbox").length; //当前品牌内所有复选框个数
         if($this.prop("checked")){
             brandBox.addClass("selected");
         }
         else{
             brandBox.removeClass("selected");
         }
         setTimeout(function () {
             var len =  parEle.find(".ckb:checked").length, //当前品牌内被激活的复选框个数
                 catrLen = cart.opt.cartEle.find(".ckb:checked").length; //购物车中所有被激活的复选框个数
             if(len === subCkb){
                 topCkb.prop("checked",true);
             }
             else{
                 topCkb.prop("checked",false);
             }
             if(catrLen === cart.opt.cartCkb){
                 cart.opt.oneLevel.prop("checked",true);
             }
             else{
                 cart.opt.oneLevel.prop("checked",false);
             }
         },0);
         //给商品金额求总和
         cart.showTotal();
     });
    //购物车单项商品删除
     $('.delete').on("click", function(){
         var $this = $(this),
             skuTd = $this.attr("data-sku"), //取点击当前删除按钮的skuid
             itemTr = $("#"+skuTd), //当前商品行
             itemId = itemTr.attr("data-item"),
             brandDiv = $("#"+itemId); //当前品牌
         $.dialog({
             title: false,
             padding: "15px 20px 8px",
             content: "<span style='color:#C00;'>确定从购物车中删除此商品？</span>",
             follow: $this.get(0),
             okValue: "确定",
             ok: function(){
                 if(brandDiv.find("tr").length == 1){
                     brandDiv.delay(400).fadeOut("400",function(){
                         $this =  $(this);
                    	 cart.delCartItem(skuTd, function(){
                    		 showTotal(); //执行购物车数量函数
                        	 $this.remove();
                             cart.showTotal(); //给商品金额求总和
                         });
                     });
                     if(cart.opt.cartEle.find(".order-brand").length == 1){
                         $("#has-goods").delay(400).fadeOut("400",function(){
                        	 $this =  $(this);
                        	 cart.delCartItem(skuTd, function(){
                        		 showTotal(); //执行购物车数量函数
                            	 $this.remove();
                            	 $("#no-goods").show();
                                 cart.showTotal(); //给商品金额求总和
                             });
                         });
                     }
                 }
                 else{
                	 cart.delCartItem(skuTd, function(){
                         itemTr.animate({
                        	 "opacity":0
                         },800,function(){
                        	 itemTr.remove();
                        	 showTotal(); //执行购物车数量函数
                             cart.showTotal(); //给商品金额求总和
                         });
                     });
                 }
             },
             cancelValue: "取消",
             cancel: true
         });
         $(".d-close").hide();
     });
     //购物车批量删除
     $("#J_bulkDelete").on("click",function(){
         cart.judgeBool();
         if(!cart.opt.flag){
             $.dialog({
                 title: false,
                 padding: "7px 20px 10px",
                 content: "<span style='color:#C00;'>请选择需要结算的商品</span>",
                 follow: $("#J_bulkDelete").get(0),
                 time: 1000
             });
             $(".d-close").hide();
         }
         else{
             $.dialog({
                 title: false,
                 padding: "15px 20px 8px",
                 content: "<span style='color:#C00;'>确定从购物车中删除选定商品？</span>",
                 follow: $("#J_bulkDelete").get(0),
                 okValue: "确定",
                 ok: function(){
                	 //清空cookie
                	 cart.delCartItem(cart.opt.arrSkus, function(){
                         $(".order-brand-top input").each(function(){
                             if($(this).prop("checked")){
                                 $.each(cart.opt.arrPar,function(){
                                     $(this).delay(400).fadeOut("400",function(){
                                         $(this).remove();
                                         cart.showTotal(); //给商品金额求总和
                                     });
                                 });
                             }else{
                                 $.each(cart.opt.arrSub,function(){
                                     $(this).delay(400).fadeOut("400",function(){
                                         $(this).remove();
                                         cart.showTotal(); //给商品金额求总和
                                     });
                                 });
                                 showTotal(); //执行购物车数量函数
                             }
                         });
                         if($(".order-list-hd :checked").length){
                             $("#has-goods").delay(400).fadeOut("400",function(){
                                 $(this).remove();
                             });
                             $("#no-goods").show();
                         }
                         showTotal(); //执行购物车数量函数
                     });
                 },
                 cancelValue: "取消",
                 cancel: true
             });
             $(".d-close").hide();
         }
     });
     //提交订单结算
     $("#J_checkOut").on("click",function(){
         var bool = true;
         var $this = $(this);
         cart.judgeBool();
         if(!cart.opt.flag){
             $.dialog({
                 title: false,
                 padding: "7px 20px 10px",
                 content: "<span style='color:#C00;'>请选择需要结算的商品</span>",
                 follow: $("#J_checkOut").get(0),
                 time: 1000
             });
             $(".d-close").hide();
         }else{
            $(".ckb:checked").each(function(){
                var $this = $(this),
                    hdVal = $this.closest("tr").find(".hide-value"),
                    bStr = $this.closest("tr").find(".hide-value2");
                if(hdVal.val() == "0" || bStr.val() == "true"){
                    bool = false;
                    $.dialog({
                         title: false,
                         padding: "7px 20px 10px",
                         content: "<span style='color:#C00;'>请确认商品状态为可结算状态</span>",
                         follow: $("#J_checkOut").get(0),
                         time: 1000
                     });
                     $(".d-close").hide();
                }
            });
             if(bool){
                 $.ajax({
                     type:'POST',
                     url: _ctxPath + '/user/user-checkIsLogin.htm',
                     success:function(html){
                         if(html.code == "false"){
                             if(html.info == unlogin || html.info ==timeout || html.info == forbid){
                                 //hack the common login
                                 $this.addClass("login");
                                 popupLogin($this);
                             }
                         } else {
                        	 $('#cartform').submit();
                         }

                     }
                 });
                 
             }
         }
     });
     //增加商品数量
     cart.opt.subStru.on("click",function(){
        var $this = $(this),
            hideVal = $this.closest("tr").find(".hide-value").val(),
            amount = parseInt($this.prev(".order-value").val());
         if(amount == parseInt(hideVal)){
             return false;
         }else{
            amount++;
            cart.editVal($this,amount);
         }
     });
     //减少商品数量
     cart.opt.plus.on("click",function(){
         var $this = $(this),
             amount = parseInt($this.next(".order-value").val());
         if(amount <= 1){
             return false;
         }else{
             amount--;
             cart.editVal($this,amount);
         }
     });
     //遍历隐藏域，当商品库存为0时禁止增加数量操作
     $(".hide-value").each(function(){
         var $this = $(this),
             inpVal = $this.closest("tr").find(".order-value");
         if(parseInt($this.val()) == 0 || parseInt(inpVal.val()) > parseInt($this.val())){
             $this.closest("tr").find(".substru").off("click");
         }
     });
     //遍历隐藏域，当商品不在销售期时禁止增减数量操作
     $(".hide-value2").each(function(){
         var $this = $(this),
             ltBtn = $this.closest("tr").find(".plus"),
             rtBtn = $this.closest("tr").find(".substru");
         if($this.val() == "true"){
             ltBtn.off("click");
             rtBtn.off("click");
         }
     });
     /* ===== 支持商品数额手动修改更新 ===== */
     $(".order-value").each(function(){
        var $this = $(this),
            parTr = $this.closest("tr"),
            point = parTr.find(".order-point"),
            sigPri = parTr.find(".unit-price"),
            showSigPri = parTr.find(".order-price"),
            hideVal = parTr.find(".hide-value");
         $this.on({
            "keyup":function(){
                var value = $this.val();
                if(value!=""){
                    var reg = /[^0-9]/g;
                    value = value.replace(reg,"")
                    if(value==""){
                        value = 1;
                    }
                    if(parseInt(value)==0){
                        value = 1;
                    }
                    if(value.length>9){
                        value = value.substring(0,9);
                    }
                    $this.val(value);
                }else{
                    $this.val(1);
                }
            },
            "focus":function(){
                $this.css({
                    border: "1px solid #46ac22"
                });
            },
            "blur":function(){
                $this.removeAttr("style");
                if($this.val()>parseInt(hideVal.val())){
                    $this.val(hideVal.val());
                }
                /*===== 更新cookie =====*/
                cart.editVal($this,$this.val());
                var sumTot = new BigDecimal($this.val().toString()).multiply(new BigDecimal(sigPri.text().toString()));
                //更新积分
                point.text(parseInt(sumTot));
                //更新当前商品金额
                showSigPri.text(sumTot);
                cart.showTotal(); //给商品金额求总和
            }
        })
     });
     /*===== 新增倒计时 =====*/
     $("#J_cartCount").each(function () {
         var $this = $(this),
         	data = $this.attr('data-config');
         data = eval('(' + data + ')');
         $this.Merlin({
         	"countDown": data
         });
     });
 })