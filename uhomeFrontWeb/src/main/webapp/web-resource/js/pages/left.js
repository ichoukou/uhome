		
 $(function(){
 	var url = location.href;
 	if(url.indexOf("orders") > 0 || url.indexOf("order-myOrders") > 0 || url.indexOf("order-getOrder") > 0){
 		$("#myorder").addClass("li-hover").siblings().removeClass("li-hover");
 	}else if(url.indexOf("userInfo") > 0){
 		$("#user").addClass("li-hover").siblings().removeClass("li-hover");
 	}else if(url.indexOf("eceiverAddress") > 0){
 		$("#address").addClass("li-hover").siblings().removeClass("li-hover");
 	}else if(url.indexOf("returnOrder") > 0){
 		$("#returnOrder").addClass("li-hover").siblings().removeClass("li-hover");
 	}else if(url.indexOf('reNewPassWord')>0){
 		$("#getWord").addClass("li-hover").siblings().removeClass("li-hover");
 	}else if(url.indexOf('points')>0){
 		$("#point").addClass("li-hover").siblings().removeClass("li-hover");
 	}else if(url.indexOf("joinMe")>0){
 		$("#joinMe").addClass("li-hover").siblings().removeClass("li-hover");
 	}else if(url.indexOf("userCoupons")>0){
 		$("#mycoupon").addClass("li-hover").siblings().removeClass("li-hover");
 	}
 	/*$.ajax({
		type: 'POST',
		url: _ctxPath+'/user/getAllAddress.htm',
		success:function(data){
			$('#myAddresss').html('('+data.info+')');
			},
		dataType:'json'
		});*/
 });
