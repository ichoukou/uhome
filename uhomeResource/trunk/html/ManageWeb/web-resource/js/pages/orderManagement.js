$(document).ready(function(){
	//鼠标移过事件
	$(".order-management tr").not(".order-info").hover(function(){
		$(this).css("background","#fdf8e7");
	},function(){
		$(this).css("background","none");
	});
	//点击隐藏事件
	$(".order-info:eq(0)").hide();
	$(".order-management tr").not(".order-info").click(function(){
		$(this).find("a").addClass("change");
		$(this).next(".order-info").show().siblings(".order-info").hide();
		$(this).siblings("tr").not(".order-info").find("a").removeClass("change");
	});
	//切换tab事件
	$(".fr-tab li").each(function(i){
		$(this).click(function(){
			$(this).addClass("bot-no").siblings().removeClass("bot-no");
			$(".fr-tabcont:eq("+i+")").show().siblings(".fr-tabcont").hide();
		});
	});
});