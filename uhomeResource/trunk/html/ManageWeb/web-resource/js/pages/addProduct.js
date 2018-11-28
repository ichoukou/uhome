$(document).ready(function(){
	$(".label-sel").click(function(){
		var temp = $(this).text();
		var this_label = $(this);
		$(this).hide().after("<input type='text' class='c-input' value="+temp+" />");
		$(".c-input").focus();
		$(".c-input").blur(function(){
			temp = $(this).val();
			$(this).remove();
			if(!temp){
				temp = "无"
			}
			$(this_label).show().text(temp);
		});
	});
});