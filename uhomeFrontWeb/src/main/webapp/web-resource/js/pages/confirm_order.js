/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-4
 * Time: 下午1:40
 * Description:
 */
//var arr = address.receipt[0]; //取json数组对象
//修改地址方法
function renderDomEdit(obj, arr){
    var obj = obj.closest(".editAddress"); //obj为jq对象
    obj.find(".province").empty().text(arr.province);
    obj.find(".city").empty().text(arr.city);
    obj.find(".county").empty().text(arr.county);
    obj.find(".receive-address").empty().text(arr.receiveAddress);
    obj.find(".receiver-name").empty().text(arr.receiverName);
    obj.find(".mobile").empty().text(arr.mobile);
};
//新增地址方法
function renderDomAdd(arr){
    var str = '<div class="editAddress cf">' +
    		  '<a href="javascript:;" value="'+arr.receiverAddressId+'" class="J-edit edit">修改本地址' + '</a>'+
    		  '<i>送至</i>' +
              '<label>' +
              '<input type="radio" name="defaultAddress">' +
              '<em class="province">'+arr.province+'</em><em class="city">'+arr.city+'</em><em class="county">'+arr.county+'</em><em class="receive-address">'+arr.receiveAddress+'</em>（<em class="receiver-name">'+arr.receiverName+'</em>收）<em class="mobile">'+arr.mobile+'</em>' +
              '</label></div>' ;
    $(".receipt").append(str);
};
//使用优惠券
function useCoupon(allowanceShow,userCouponId){
	$("#userCouponId").val(userCouponId);
	var payment = new BigDecimal($("#totalValue").val().toString()).subtract(new BigDecimal(allowanceShow.toString())) ;
	$("#allowanceShow").html(allowanceShow);
	$("#allowanceShow1").html(allowanceShow);
	//优惠券金额大于商品金额，显示应付总额为0
	$("#total").html("¥"+(payment < 0 ? 0 : payment));
    $(".coupon-wp .bd").hide();
    $(".coup-action").hide();
    $(".show-status").show();
}
/**
 * 激活并使用优惠券
 */
function atcAnduseCoupon(){
	var couponNoShow = $.trim($("#couponNo").val());
		if(couponNoShow==""){
			return;
		}
		$.ajax({
			type: 'POST',
			url: _ctxPath+'/user/activateCoupon.htm',
			async: true,
			data: {
				'couponNo':couponNoShow
			},
			dataType:'json',
			success: function(data){
				var info = data.info;
				if(info==3){
					$("#couponNo").val('');
					$(".coup-action").click();
					$(".coup-action").click();
				}else if(info==0){
					alert("优惠券不存在，请核对优惠券号！");
				}else if(info==2){
					alert("优惠券已过期！");
				}else if(info==5){
					alert("活动已被禁止或删除！");
				}else if(info==1){
					alert("优惠券已激活，不能再次激活！");
				}else if(info==6){
					alert("激活码已被激活，不能重复激活！");
				}else if(info==99){
					alert("激活名额已满！");
				}else if(info.indexOf("100")>-1){
					var infoArr = info.split("-");
					alert("活动已结束，活动时间："+infoArr[1]+" 到 "+infoArr[2]);
				}
		    }
		});
}
//释放优惠券
function releCoupon(){
	$("#userCouponId").val("");
	
	$("#allowanceShow1").html("0.00");
	$("#total").html("¥"+$("#totalValue").val());
    if($(".coupon-wp .bd:visible")){
        $(".coupon-wp .bd").hide();
    }
    $(".coup-action").show();
    $(".show-status").hide();
}
//修改优惠券
function editCoupon(){
    $(".coupon-wp .bd").show();
    $(".coup-action").hide();
    $(".show-status").show();
}
$(function(){
    //解除购物车事件
    $(".cart-sum").off("mouseover");
    //是否开具发票
    $(".radio :radio").on("change",function(){
    		$(".radio").removeClass("selected");
    	  $(this).parents(".radio").addClass("selected");
        var $hasInv = $("#invoice-detail");
        if(this.id){
            $hasInv.show();
        }
        else{
            $hasInv.hide();
        }
    });
    //发票类型选择
    $(".inv :radio").on("change",function(){
        var $gain = $("#gain");
        if(this.id){
            $gain.hide();
        }else{
            $gain.show();
        }
    });
    //公司发票抬头检测字符
    $("#payInvoice").on("keyup",function(){
        var $this = $(this);
       if($.trim($this.val())){
           $this.removeAttr("style");
       }
    });
    //调动修改地址弹窗-登录前
    /*$('#addAddress').on("click",function(event){
    	var dialog = $.dialog({
    		title: false,
            okValue: "保存修改",
            ok: function(){
            	var isDefault = $('#ckb_default').prop("checked") ? 1 : 0;
            	$('#isDefault').val(isDefault);
            	//校验数据格式
            	var result = $.formValidator.pageIsValid('101');
            	if(result){
	        		$.ajax({
	        			type:'POST',
	        			url: _ctxPath +'/order/order-saveAddress.htm',
	        			data:$("#addressForm").serialize(),
	        			success:function(data){
	        				if(data.code == "true"){
	        					var user = JSON.parse(data.info);
	        					$('#tname').val(user.username);
	 							$('#tpassword').val(pass);
	 							var true_from =$('#true_form');
	 							true_from.submit();
	        				} else {
	        					popupDialog("保存地址出错！" + data.info);
	        				}
	    				}
	    			});
            	}else{
            		//校验出错
            		return false;
            	}
            },
            cancelValue: "取消",
            cancel: true,
            lock: true,
            fixed:true,
            visible: false
    	});
   	$.ajax({
			type:'POST',
			url: _ctxPath + '/order/order-editAddress.htm',
			success:function(html){
				dialog.visible().content(html);
		        $(".d-close").hide();
			}
		});
	});*/
    //调动修改地址弹窗-登录后
    $("#useNew").on("click",function(){
    	var dialog = $.dialog({
    		title: false,
            okValue: "保存修改",
            ok: function(){
            	var isDefault = $('#ckb_default').prop("checked") ? 1 : 0;
            	$('#isDefault').val(isDefault);
            	$("#regionId").val($("#region").val());
            	//校验数据格式
            	var result = $.formValidator.pageIsValid('101');
            	if(result){
    			$.ajax({
        			type:'POST',
        			url: _ctxPath +'/order/order-saveAddress.htm',
        			data:$("#addressForm").serialize(),
        			success:function(data){
        				if(data.code == "true"){
        					arr = JSON.parse(data.info);
                            renderDomAdd(arr);  //新增
                            var parEle = $(".receipt");
                            var lastEle = $(".receipt .editAddress").last();
                            lastEle.addClass("selected").siblings().removeClass("selected");
                            parEle.find(":radio").prop("checked",true);
                            var edLinkVal = lastEle.find(".edit").attr("value");
                            $(".hd_addr").val(edLinkVal);
        				} else {
        					popupDialog("保存地址出错！" + data.info);
        				}
    				}
    			});
            	} else {
            		return false;
            	}
            },
            cancelValue: "取消",
            cancel: true,
            lock: true,
            fixed:true,
            visible: false
    	});
	   	$.ajax({
				type:'POST',
				url: _ctxPath + '/order/order-editAddress.htm',
				success:function(html){
					dialog.visible().content(html);
			        $(".d-close").hide();
				}
		});
    });
    //设置送货地址
    $(".receipt .editAddress").live({
        "click": function(event){
            var $this = $(this);
            event.preventDefault();
            $.dialog({
                "title": false,
                "content": "<span style='color: #000000;'>更换地址后，您需要重新确认订单信息</span>",
                "ok": function(){
                	var $edLink = $this.find(".J-edit");
                    $(".hd_addr").val($edLink.attr("value"));
                    $this.addClass("selected").siblings().removeClass("selected");
                    $this.find(":radio").prop("checked",true);
                },
                "okValue": "确定",
                "cancel": true,
                "cancelValue": "取消"
            });
            $(".d-close").hide();
        }
    });
    //修改地址
    $('.J-edit').live("click",function(event){
        var $this = $(this); //当前指针
    	var dialog = $.dialog({
    		title: false,
            okValue: "保存修改",
            ok: function(){
            	var isDefault = $('#ckb_default').prop("checked") ? 1 : 0;
            	$('#isDefault').val(isDefault);
            	$("#regionId").val($("#region").val());
            	//校验数据格式
            	var result = $.formValidator.pageIsValid('101');
            	if(result){
	    			$.ajax({
	        			type:'POST',
	        			url: _ctxPath +'/order/order-saveAddress.htm',
	        			data:$("#addressForm").serialize(),
	        			success:function(data){
	        				if(data.code == "true"){
	        					var arr = JSON.parse(data.info);
                                renderDomEdit($this, arr); //修改
	        				} else {
	        					alert("保存地址出错！" + data.info);
	        				}
	    				}
	    			});
	            }else{
            		//校验出错
            		return false;
	            }
            },
            cancelValue: "取消",
            cancel: true,
            lock: true,
            fixed:true,
            visible: false
    	});
    	//ajax请求
   	 	var $this = $(this);
		var addressId = $this.attr("value");  
		$.ajax({
			type:'POST',
			url: _ctxPath + '/order/order-editAddress.htm',
			data:{"addr.receiverAddressId": addressId},
			success:function(html){
				dialog.visible().content(html);
		        $(".d-close").hide();
			}
		});
        return false;
    });
    //设为默认地址
    $(".editAddress").on("hover",function(){
    	$(this).find(".J-default").toggle();
    });
    
    //提交订单
    $('#btn_accept').on("click",function(event){
		var $this = $(this);
    	var dialog = $.dialog({
    		title: false,
            lock: true,
            fixed:true,
            visible: false
    	});
		//前端校验
    	//效验送货地址
    	if($("input[name='defaultAddress']:checked").size()<1){
    		  popupDialog("<span style='color: #000000;'>请选择收货地址！</span>", "10000");
    		  return;
    	}
    	//判断发票信息是否填写
    	var $gain = $("#gain");
//    	var isSubmit = $("#isSubmit").val();
//    	if(isSubmit == "true"){
//	      	  popupDialog("<span style='color: #000000;'>请不要重复提交订单！</span>");
//	      	  return false;
//    	}else 
    	if(!$gain.is(":hidden")){
            if($.trim($("#payInvoice").val()) ==""){
                $.dialog({
                    title: false,
                    padding: "7px 20px 10px",
                    content: "<span style='color:#C00;'>单位发票请填写公司名称！</span>",
                    okValue: "确定",
                    lock: true,
                    ok: function(){
                        $("#payInvoice").focus().css("border","1px solid #C00");
                    }
                });
                $(".d-close").hide();
            }else if($.trim($("#payInvoice").val()).length >64){
            	$.dialog({
                    title: false,
                    padding: "7px 20px 10px",
                    content: "<span style='color:#C00;'>请输入小于64个字符长度的单位发票！</span>",
                    okValue: "确定",
                    lock: true,
                    ok: function(){
                        $("#payInvoice").focus().css("border","1px solid #C00");
                    }
                });
                $(".d-close").hide();
            } else {
//            	$("#isSubmit").val("true");
                //ajax提交订单
                $.ajax({
                    type:'POST',
                    url: _ctxPath + '/order/order-submitOrder.htm',
                    data:$("#orderForm").serialize(),
                    success:function(html){
                        if(html.code == "false"){
                          	if(html.info == isSubmit){
                           	 popupDialog("<span style='color: #000000;'>请不要重复提交订单！</span>", "10000");
                          	}
                        	if(html.info == empty){
                            	 popupDialog("<span style='color: #000000;'>提交数据有误，请核对后再提交！</span>", "10000");
                            }
                            if(html.info == unlogin || html.info ==timeout){
                                //hack the common login
                                $this.addClass("login");
                                popupLogin($this);
                            }
                            if(html.info == address){
                                popupDialog("<span style='color: #000000;'>请选择收货地址！</span>", "10000");
                            }
                            if(html.info == notActivity || (html.info == underStock && type == specialSeller)){
                                window.location.href = _ctxPath +'/order/order-shoppingCart.htm';
                            }
                            if(html.info == underStock && type == secKill){
                            	 popupDialog("<span style='color: #000000;'>秒杀已经结束，下次请早！</span>", "10000");
                            }
                        } else {
                            window.location.href = _ctxPath +'/order/order-showOrders.htm?orderIds=' + html.info;
                            //dialog.visible().content(html);
                            //$(".d-close").hide();
                        }
                    }
                });
            }
    	}else {
//    		$("#isSubmit").val("true");
            //ajax提交订单
            $.ajax({
                type:'POST',
                url: _ctxPath + '/order/order-submitOrder.htm',
                data:$("#orderForm").serialize(),
                success:function(html){
                    if(html.code == "false"){
                      	if(html.info == isSubmit){
                          	 popupDialog("<span style='color: #000000;'>请不要重复提交订单！</span>", "10000");
                     	}
                    	if(html.info == empty){
                         	 popupDialog("<span style='color: #000000;'>提交数据有误，请核对后再提交！</span>", "10000");
                        }
                        if(html.info == unlogin || html.info ==timeout){
                            //hack the common login
                            $this.addClass("login");
                            popupLogin($this);
                        }
                        if(html.info == address){
                            popupDialog("<span style='color: #000000;'>请选择收货地址！</span>", "10000");
                        }
                        if(html.info == notActivity || (html.info == underStock && type == specialSeller)){
                            window.location.href = _ctxPath +'/order/order-shoppingCart.htm';
                        }
                        if(html.info == underStock && type == secKill){
                        	 popupDialog("<span style='color: #000000;'>秒杀已经结束，下次请早！</span>", "10000");
                        }
                    } else {
                        window.location.href = _ctxPath +'/order/order-showOrders.htm?orderIds=' + html.info;
                        //dialog.visible().content(html);
                        //$(".d-close").hide();
                    }

                }
            });
        }
    	// 新窗口提交表单
//		$('#orderForm').trigger('submit');
    });
    //优惠券功能
    $(".coup-action").on("click",function(){
    	$.ajax({
    		type:'POST',
    		data:"userCouponPage.params._allProduct="+$("input[name='_allProduct']").val(),
    		url: _ctxPath + '/user/selectCanUseUserConpon.htm',
            success:function(data){
            	var info = eval(data.info);
            	var len = info.length;
            	var showCoupons = $("#showCoupons");
            	showCoupons.html("");
            	if(len==0){
            		showCoupons.append('<div class="no-coup">暂无可使用优惠劵</div>');
            	}else{
            		for(var i=0;i<len;i++){
            			var obj = info[i];
            			
            			var opt = "";
            			var total = $("#totalValue").val();
                		if(obj.userCouponStatusShow==0){
                			if(obj.couponConfig.limitCharge==0||total>=obj.couponConfig.limitCharge){
                				opt = '<a href="javascript:useCoupon('+obj.preferentialMoney+','+obj.userCouponId+');">使用</a>';
                			}else{
                				opt = '未满足使用条件';
                			}
                		}
                		else if(obj.userCouponStatusShow==2){
                			opt = '已过期';
                		}else if(obj.userCouponStatusShow==6){
                			opt = '未满足使用条件';//未到期
                		}
            			showCoupons.append('<div class="det-list"><ul><li class="t1">'+obj.couponNo+'</li><li class="t2">'+obj.couponConfig.allowance+'</li><li class="t3">'+obj.couponConfig.limitCharge+'</li><li class="t4">'+obj.couponConfig.startTime+' 至 '+obj.couponConfig.endTime+'</li><li class="t6">'+obj.couponUseScope+'</li><li class="t5">'+opt+'</li></ul></div>');
            		}
            	}
            }
    	})
      var $this = $(this);
        $(".coupon-wp .bd").toggle();
        $this.toggleClass("toggle");
    });
    $(".show-status").on("hover",function(){
        var $this = $(this);
        $this.find("a").toggle();
    });
    /*===== 新增倒计时 =====*/
    $("#J_cartCount").each(function () {
    	 var $this = $(this),
    	 	data = $this.attr('data-config');
    	 data = eval('(' + data + ')');
    	 data.callback = function(){
    		 window.location.href = _ctxPath+'/order/order-shoppingCart.htm';
    	 };
      $this.Merlin({
      	"countDown": data
      });
    });
})