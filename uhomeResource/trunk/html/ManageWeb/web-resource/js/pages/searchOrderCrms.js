//添加客服弹出
var orderc='';
var clickAddOrEdit=function(orderId,orderNo,evt){
	var html = '',
			e=(evt)?evt:window.event;  
	orderc=orderId;
	$.ajax({
		url:_ctxPath + "/admin/orderCrm/orderCrm-listOrderCrmByOrderId.htm",
		type:"POST",
		data:{'orderId':orderId},
		dataType:"html",
		async: false,
		success:function(data){
			html=data;
		},
		error:function(data){
		}
	});
	$.dialog({
	    lock: true,
	    padding: "0px 5px",
	    title:"客服事件管理 - " + orderNo,
	    content:html,
	    fixed: false,
	    cancel:function(){
	    	var temp = $(".J-txt").val();
	    	if(temp!=""){
	    		var r = confirm("离开之后您输入的信息将会丢失，确定离开吗？");
	    		if(!r){
	    			return false;
	    		}else{
	    			$(".J-txt").val("");
	    		}
	    	}
	    },
	    ok:false
  });
  // 滚动条到最底部，显示最新记录的信息
  scrollBottom();
  //隐藏取消按钮
  	$(".d-button").hide();
	if (window.event) {  
		e.cancelBubble=true;  
	} else {  
		e.stopPropagation();  
	}  
}

var saveOrderCrm = function(){
	var userName = '',
	myDate = new Date();
	myDate = ChangeTimeToString(myDate);
	var temp = HTMLEncode($(".J-txt").val());
	$("#orderIdCrm").val(orderc.toString());
	if(temp!=""){
		var result = $.formValidator.pageIsValid('100021');
    	if(result){
    		$.ajax({
    			url:_ctxPath + "/admin/orderCrm/orderCrm-addOrderCrm.htm",
    			type:"POST",
    			data:{'orderCrm.remark':temp,'orderCrm.orderId':orderc},
    			async: false,
    			success:function(data){
    				userName=data.info;
    			},
    			error:function(data){
    			}
    		});
    		var temp_html = "<li><p><b>"+userName+"</b><span> "+myDate+"</span></p><p>"+temp+"</p></li>";
    		$(".msg-main ul").append(temp_html);
    		// 保存信息之后清空输入框
    		$(".J-txt").val("");
    		// 滚动条到最底部，显示最新记录的信息
			scrollBottom();
    		return true;
    	}else{
    		return false;
    	}

	}
}


function ChangeTimeToString(DateIn){
	var Year =Month = Day = Hour =Minute = 0,
			CurrentDate="";
	// 初始化时间
	Year=DateIn.getFullYear();
	Month=DateIn.getMonth()+1;
	Day=DateIn.getDate();
	Hour=DateIn.getHours();
	Minute=DateIn.getMinutes();
	Seconds=DateIn.getSeconds()
	CurrentDate=Year+"-";
	if(Month>=10){
		CurrentDate=CurrentDate+Month+"-";
	}
	else{
		CurrentDate=CurrentDate+"0"+Month+"-";
	}
	if(Day>=10){
		CurrentDate=CurrentDate+Day;
	}
	else{
		CurrentDate=CurrentDate+"0"+Day;
	}
	if(Hour>=10){
		CurrentDate=CurrentDate+" "+Hour;
	}
	else{
		CurrentDate=CurrentDate+" 0"+Hour;
	}
	if(Minute >=10){
		CurrentDate = CurrentDate +":"+Minute;
	}
	else{
		CurrentDate =CurrentDate+":0"+ Minute;
	}
	/*
	 * if(Seconds>=10){ CurrentDate = CurrentDate +":"+Seconds; } else{
	 * CurrentDate =CurrentDate+":0"+ Seconds; }
	 */
	return CurrentDate;
}

// 滚动条到底部，显示最新消息
var scrollBottom = function(){
  var $li = $(".msg-main li:last"),
  		thisTop =0;
  if($li.length>0){
	thisTop = $li.position().top + $(".msg-main").scrollTop();
	$(".msg-main").scrollTop(thisTop);
  }
}
//点击回车提交客服内容
var KeyDown=function(){
	 var event=arguments.callee.caller.arguments[0]||window.event;//消除浏览器差异
    if(event.keyCode==13)
    {
    	event.keyCode=0;
    	saveOrderCrm();
    	return false;
    }
}
//html编码
function HTMLEncode(html)
{
  var temp = $("<div></div>")[0];
  (temp.textContent != null) ? (temp.textContent = html) : (temp.innerText = html);
  var output = temp.innerHTML;
  temp = null;
  return output;
}