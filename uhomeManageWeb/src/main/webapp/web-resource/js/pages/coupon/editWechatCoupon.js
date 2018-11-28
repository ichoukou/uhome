/**
 * 配置微信优惠券页面js
 */
$(function(){
	//新增
	$(".new_tr").live("click",function(){
		//var index = $(".j-rowspan").attr("rowspan");
		
		var html = '<tr class="list-tr">'
				+ '<td width="150"><input type="text" class="j-edited input-nobor allowance" onblur="checkAllowance(this)"/></td>'
				+ '<td width="174"><input type="text" class="j-edited input-nobor limitCharge" onblur="checkDouble(this)"/></td>'
				+ '<td width="139"><input type="text" class="j-edited input-nobor couponCount" onblur="checkCouponCount(this)"/></td>'
                + '<td width="69"><a href="javascript:void(0);" class="cou_del">删除</a></td>'
				+ '</tr>';
		
		//$(".j-rowspan").attr("rowspan", parseInt(index) + 1);
		$("#dataTable").append(html);
//        $(".new_tr").removeClass().addClass("cou_del").text("删除");
//        $(".cou_del:last").removeClass().addClass("new_tr").text("新增");
	});
	//删除
    $(".cou_del").live("click",function(){
       $(this).parent().parent().remove();
    });
	//取消新增，重置
	$(".j-reset").on("click",function(){
		$("#dataTable tr:not(:last)").remove();
		$("#addTable input").val("");
		$(".j-rowspan").attr("rowspan","1");
	});
	
	//生成按扭
	$("#create").click(function(){
		var $form = $(this).closest("form");
		if(!checkInput($form)){
			return;
		}
		if($("input[name='event.eventName']").val().length>64){
			alert("活动名称长度不能超过64个字符");
			return;
		}
		$.each($("#dataTable tr"), function(i, tr){
			var $tr = $(tr);
			$tr.find(".allowance").attr("name", "event.couponConfigList["+i+"].allowance");
			$tr.find(".limitCharge").attr("name", "event.couponConfigList["+i+"].limitCharge");
			$tr.find(".couponCount").attr("name", "event.couponConfigList["+i+"].couponCount");
		});
		$.dialog({
            title: false,
            lock: true,
            content: "生成后不能再修改，是否确认生成？",
            okValue: "确认",
            ok: function(){
            	$.ajax({
        			type:'POST',
        			url: _ctxPath + '/admin/coupon/coupon-save.htm',
        			data: $("#addForm").serialize(),
        			success:function(msg){
        				if(msg.code == "true"){
        					$.dialog({
        				        title: false,
        				        content: msg.info,
        				        time : 1000,
        				        beforeunload: function () {
        				        	window.location.reload();
        				        }
        				    });
        				}else if(msg.code = "false"){
        					popupDialog(msg.info);
        				}
        			},
        			error: function(xhr){
        				popupDialog(xhr.responseText);
        	        }
        		});
            },
            cancelValue: "取消",
            cancel: true
		});
	});
	
	//编辑按钮
	$(".j-edit").on("click", function(){
		var $table = $(this).closest("table");
		$table.find(".j-normal").hide();
		$table.find(".j-edited").show();
	});
	
	//取消按钮
	$(".j-cencal").on("click", function(){
		var $table = $(this).closest("table");
		
		$.each($table.find("input.j-edited"), function(i, input){
			var text = $(input).prev(".j-normal").text();
			$(input).val(text);
		});
		
		$table.find(".j-normal").show();
		$table.find(".j-edited").hide();
	});
	
	//完成按钮
	$(".j-done").on("click", function(){
		var $form = $(this).closest("form");
		if(!checkInput($form)){
			return;
		}
		$.ajax({
			type:'POST',
			url: _ctxPath + '/admin/coupon/coupon-save.htm',
			data: $(this).closest("form").serialize(),
			success:function(msg){
				if(msg.code == "true"){
					$.dialog({
				        title: false,
				        content: msg.info,
				        time : 1000
				    });
					window.location.reload();
				}else if(msg.code = "false"){
					popupDialog(msg.info);
				}
			},
			error: function(xhr){
				popupDialog(xhr.responseText);
	        }
		});
	});
	
	//非常规删除
	$(".j-abnormalDel").click(function(){
		var eventId = $(this).closest("form").find(".eventId").val();
		$.dialog({
            title: false,
            lock: true,
            content: "是否确定删除？",
            okValue: "确定",
            ok: function(){
            	$.ajax({
        			type:'POST',
        			url: _ctxPath + '/admin/coupon/coupon-abnormalDel.htm',
        			data: "event.eventId=" + eventId,
        			success:function(msg){
        				if(msg.code == "true"){
        					$.dialog({
        				        title: false,
        				        content: msg.info,
        				        time : 1000,
        				        beforeunload: function () {
        				        	window.location.reload();
        				        }
        				    });
        				}
        			},
        			error: function(xhr){
        				popupDialog(xhr.responseText);
        	        }
        		});
            },
            cancelValue: "取消",
            cancel: true
		});
	});
	
	//删除
	$(".j-delete").click(function(){
		var eventId = $(this).closest("form").find(".eventId").val();
		$.dialog({
            title: false,
            lock: true,
            content: "是否确定删除？",
            okValue: "确定",
            ok: function(){
            	$.ajax({
        			type:'POST',
        			url: _ctxPath + '/admin/coupon/coupon-delete.htm',
        			data: "event.eventId=" + eventId,
        			success:function(msg){
        				if(msg.code == "true"){
        					$.dialog({
        				        title: false,
        				        content: msg.info,
        				        time : 1000,
        				        beforeunload: function () {
        				        	window.location.reload();
        				        }
        				    });
        				}
        			},
        			error: function(xhr){
        				popupDialog(xhr.responseText);
        	        }
        		});
            },
            cancelValue: "取消",
            cancel: true
		});
	});
	
	//回车事件
	/*document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	$(".list-tr .j-done").click();
	    }
	  }*/
});
function checkInput(obj){
	var $inputs = obj.find("input.j-edited");
	var index1 = 0;
	$.each($inputs, function(){
		var $this = $(this);
		if($this.val() == ''){
			$this.addClass('warning');
			index1++;
		}else{
			$this.removeClass('warning');
		}
	});
	if(index1 > 0){
		return false;
	}
/*	var index2 = 0;
	var $trs = obj.find(".list-tr");
	$.each($trs, function(i, tr){
		var allowance = parseInt($(tr).find(".allowance").val());
		var limitCharge = parseInt($(tr).find(".limitCharge").val());
		if(allowance >= limitCharge){
			index2++;
			return false;
		}
	});
	if(index2 > 0){
		popupDialog("抵扣金额必须小于最低消费金额");
		return false;
	}*/
	
	return true;
}