//添加广告弹出
var clickAddOrEdit=function(id){
	var html = '';
	$.ajax({
		url:_ctxPath + "/admin/adv/adv_addAdv.htm",
		type:"POST",
		data:{'adv.advertisementId':id},
		dataType:"html",
		async: false,
		success:function(data){
			html=data;
			$.dialog({
			  lock: true,
			  padding: "5px",
			  title:false,
			  content:html,
			  fixed: false,
			  cancel: true,
			  cancelValue:"取  消",
			  ok:function(){
			  	var result = $.formValidator.pageIsValid('1');
				if(!result){
					return false;
				}
			  	$('#advForm').submit();
			  },
			  okValue:"发  布"
			});
		},
		error:function(data){
			//alert(data.responseText);
		}
	});

	
}
var imgView=function(src, width, height){
	var suffix = src.split(".")[1];
var url =src + "_t5." + suffix;
	if(src!='' && src != null){
		var html = '<img src="'+_fileThumbPath+url+'"/>';
		  $.dialog({
		    lock: true,
		    padding: "5px",
		    title:false,
		    content:html,
		    fixed: true,
		    cancel: true,
		    cancelValue: "关  闭",
		    closed:false
		  });
		  $(".d-close").hide();
	}

}
