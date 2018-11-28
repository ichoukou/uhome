/**
 * 查看优惠券页面js
 */
$(function(){
	$("#search").click(function(){
		var $input = $(".J-Keywords");
		if($input.val() == $input.attr("data-default")){
			$input.val("");
			//return false;
		}
		$(".sub-form").submit();
	});
});