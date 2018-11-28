/**
 * 配置普通优惠券页面js
 */
$(function(){
	
	//取消新增，重置
	$(".j-reset").on("click",function(){
		var $prev_table = $(this).parent().prev("table");
		$prev_table.find("input").val("");
	});
	
	//导出报表
	$(".j-export").on("click", function(){
		var eventId = $(this).closest("form").find(".eventId").val();
		window.location.href = _ctxPath + "/admin/coupon/coupon-export.htm?event.eventId=" + eventId; 
	});
	
	//编辑按钮
	$(".j-edit").on("click",function(){
		var $parent_tr = $(this).closest(".list-tr");
		$parent_tr.find(".j-normal").hide();
		$parent_tr.find(".j-edited").show();
	});
	
	//取消按钮
	$(".j-cencal").on("click",function(){
		var $parent_tr = $(this).closest(".list-tr");
		
		$.each($parent_tr.find("input.j-edited"), function(i, input){
			var text = $(input).prev(".j-normal").text();
			$(input).val(text);
		});
		
		$parent_tr.find(".j-normal").show();
		$parent_tr.find(".j-edited").hide();
	});
	
	//选择适用范围
	$("#eventRange").on("click", function(){
		if($("#startTime").val() == "" || $("#endTime").val() == ""){
			alert("请先选择活动时间");
			return;
		}
		var dialog = $.dialog({
		    lock: true,
		    title:false,
		    okValue: "确认",
            ok: function(){
            	var nameStr = "";
            	var strList = "";
            	var index = 0;
            	var $allPlan = $(".J-parent .sel + label[type="+allPlan+"]");
            	if($allPlan.length > 0){
					idArray.splice(0, idArray.length);
            		var outId = $allPlan.attr("outId");
            		var type = $allPlan.attr("type");
            		strList += "<input type='hidden' name='event.eventRanges["+index+"].outId' value='"+outId+"'/>" 
            				+ "<input type='hidden'name='event.eventRanges["+index+"].type' value='"+type+"'/>";
            		nameStr += "<p>"+$allPlan.text()+"</p>";
            		
            		var id = type + "_" + outId;
            		idArray.push(id);
            	}else{
            		idArray.splice(0, idArray.length);
                	$.each($(".J-children .sel + label"), function(i, obj){
                		var outId = $(obj).attr("outId");
                		var type = $(obj).attr("type");
                		if(type.length > 0){
                			strList += "<input type='hidden' name='event.eventRanges["+index+"].outId' value='"+outId+"'/>" 
                					+ "<input type='hidden' name='event.eventRanges["+index+"].type' value='"+type+"'/>";

                    		nameStr += "<p>"+$(obj).text()+"</p>";
        					
                    		var id = type + "_" + outId;
                    		idArray.push(id);
                    		
                			index++;
                		}
                	});
            	}

            	$("#eventRange").nextAll().remove();
            	$("#eventRange").after(nameStr + strList);
            },
		    cancelValue:"取消",
		    cancel: true,
		    visible: false
		});
		  $.ajax({
				type:'POST',
				url: _ctxPath + '/admin/coupon/coupon-getEventRanges.htm',
				data: {"startTime" : $("#startTime").val(),"endTime" : $("#endTime").val()},
				success:function(html){
					dialog.visible().content(html);
					initTree();
					for(var i in idArray){
						$("#"+idArray[i]).click();
					}
				}
			});
		  $(".d-close").hide();
	});
	
	//完成按钮
	$(".j-done").on("click", function(){
		var $this = $(this);
		var $form = $this.closest("form");
		if(!checkInput($form)){
			return;
		}
		$.ajax({
			type:'POST',
			url: _ctxPath + '/admin/coupon/coupon-save.htm',
			data: $this.closest("form").serialize(),
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
						$.dialog({
    				        title: false,
    				        content: msg.info,
    				        time : 1000,
    				        beforeunload: function () {
    				        	window.location.reload();
    				        }
    				    });
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
});
var idArray = new Array();
//日期控件事件
function pickedFunc(){
	$("#eventRange").nextAll().remove();
	idArray.splice(0, idArray.length);
}
function checkInput(obj){
	var $inputs = obj.find("input.j-edited");
	var index = 0;
	$.each($inputs, function(){
		var $this = $(this);
		if($this.val() == ''){
			$this.addClass('warning');
			index++;
		}else{
			$this.removeClass('warning');
		}
	});
	if(index > 0){
		return false;
	}
	if(idArray.length == 0){
		alert("请选择适用范围");
		return false;
	}
	
	/*var allowance = parseInt(obj.find(".allowance").val());
	var limitCharge = parseInt(obj.find(".limitCharge").val());
	if(allowance >= limitCharge){
		popupDialog("抵扣金额必须小于最低消费金额");
		return false;
	}*/
	
	return true;
}