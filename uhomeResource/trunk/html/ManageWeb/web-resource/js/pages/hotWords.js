var submitForm=function(){
	var key_words = $("#txtKeywords").val();
	if(key_words=='' || key_words=='添加热搜索词'){
		$.dialog({
		    lock: true,
		    title: '消息',
		    padding: "5px",
		    content: '热搜词不允许为空',
		    cancelValue: '确认',
		    cancel: function () {
		    	$("#txtKeywords").focus();
		    }
		});
		return false;
	}else{
		return true;
	}
}
$(document).ready(function(){
	//关键词向前排序
	$(".up").live("click",function(){
		var this_temp = $(this).parents("li");
		var prev_temp = $(this_temp).prev("li").not($(".search-cont li:eq(0)"));
		if(prev_temp[0]){
			$(prev_temp).before(this_temp);
			$.ajax({
				url : _ctxPath+"/admin/hotword/hotword-moveHotWordOrder.htm",
				type : "POST",
				data : {
					'firstId' : $(this_temp).attr('id'),
					'secondId' : $(prev_temp).attr('id')
				},
				dataType : "json",
				success : function(data) {
				},
				error : function(data) {
					console.log(data);
					$.dialog({
    				    lock: true,
    				    title: '消息',
    				    content: data.info,
    				    cancelValue: '确认',
    				    cancel: function () {
    				    }
    				});
				}
			});
		}
	});
	//关键词向后排序
	$(".down").live("click",function(){
		var this_temp = $(this).parents("li");
		var next_temp = $(this_temp).next("li");
		if(next_temp[0]){
			$(next_temp).after(this_temp);
//     				alert($(next_temp).attr('id')+'-----'+$(this_temp).attr('id'))
			$.ajax({
				url : _ctxPath+"/admin/hotword/hotword-moveHotWordOrder.htm",
				type : "POST",
				data : {
					'firstId' : $(next_temp).attr('id'),
					'secondId' : $(this_temp).attr('id')
				},
				dataType : "json",
				success : function(data) {
				},
				error : function(data) {
					console.log(data);
					$.dialog({
    				    lock: true,
    				    title: '消息',
    				    content: data.info,
    				    cancelValue: '确认',
    				    cancel: function () {
    				    }
    				});
				}
			});
		}
		
	});
	//删除关键词
	$(".del").live("click",function(){
		var id=$(this).parents("li").attr('id');
		$.dialog({
		    lock: true,
		    title: '消息',
		    padding: "5px",
		    content:'你确定要删除热关键字吗?',
		    fixed: true,
		    cancel: true,
		    cancelValue:"取  消",
		    ok:function(){
		    	$.ajax({
  					url : _ctxPath+"/admin/hotword/hotword-delete.htm",
  					type : "POST",
  					data : {
  						'hotWord.hotWordId' : id
  					},
  					dataType : "json",
  					success : function(data) {
  						$("#"+id).remove();
  						$.dialog({
  	    				    lock: true,
  	    				    title: '消息',
  	    				    content: data.info,
  	    				    cancelValue: '确认',
  	    				    cancel: function () {
  	    				    }
  	    				});
  					},
  					error : function(data) {
  						console.log(data);
  					}
  				});
		    },
		    okValue:"确  认"
		  });
	});
	//编辑关键词
	$(".edit").live("click",function(){
		var text_temp = $(this).prev("label").text();
		var edit_input = "<input type=\"text\" class=\"eidt-text-input\" value='"+text_temp+"'/>";
		$(this).prev("label").hide().after(edit_input);
		
		var id=$(this).parents("li").attr('id');
		$(".eidt-text-input").focus();
		$(".eidt-text-input").blur(function(){
			if($(this).val()==''){
				$.dialog({
				    lock: true,
				    title: '消息',
				    content: '关键字不允许为空.',
				    cancelValue: '确认',
				    cancel: function () {
				    	$(".eidt-text-input").focus();
				    }
				});
				return;
			}
			if($(this).prev("label").text()==$(this).val()){
				$('.eidt-text-input').prev("label").show();
				$('.eidt-text-input').remove();
				return;
			}
			if(text_temp!=$(this).val()){
				$.ajax({
  					url : _ctxPath+"/admin/hotword/hotword-updateHotWord.htm",
  					type : "POST",
  					data : {
  						'hotWord.hotWordId' : id,
  						'hotWord.word' : $(this).val()
  					},
  					dataType : "json",
  					success : function(data) {
  						$.dialog({
  	    				    lock: true,
  	    				    title: '消息',
  	    				    content: data.info,
  	    				    cancelValue: '确认',
  	    				    cancel: function () {
  	    				    }
  	    				});
  					},
  					error : function(data) {
  						console.log(data);
  					}
  				});
  				var text_temp = $('.eidt-text-input').val();
				$('.eidt-text-input').prev("label").show().text(text_temp);
				$('.eidt-text-input').remove();
			}
    	});
		document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	$(".eidt-text-input").blur();
	     }
		}
	});
});
