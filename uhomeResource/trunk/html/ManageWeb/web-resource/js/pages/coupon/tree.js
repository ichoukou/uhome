/**
 * 
 */

function initTree(){
	//选择事件
	$(".role-list").on('click',".J-parent",function(){
		var $this=$(this);
		var $thisEm=$this.children("em");//checkbox
		var $parent=$(this).parent();
		//var $parent=$this.children("ul");//子级
		var $em=$parent.find("em");//选中、删除状态
		//全选
		if($thisEm.hasClass("sel")){
		    $em.removeClass("sel-hover sel");
		}else{
		    $em.addClass("sel");
		}
	});
	//点选子集
	$(".J-children").on('click',function(){
	    var $this=$(this);
	    var $thisEm=$this.children("em");//checkbox
	    var $parentEm=$this.parents(".role-list").children("p").children("em");
	    var $parent=$(this).parents(".role-list").find("ul");
	    var $em=$parent.find("em");//选中、删除状态
	    //全选
	    if($thisEm.hasClass("sel")){
	        $thisEm.removeClass("sel-hover sel");
	        if($parent.find(".sel").length<=0){
	        	$parentEm.removeClass("sel-hover sel");
	        }
	    }else{
	        $thisEm.addClass("sel");
	        $parentEm.addClass("sel");
	    }
	});
	//鼠标划过事件
	$(".role-list").children("p").on("mouseenter",function(){
		var $this = $(this),
				$em = $this.children("em");
		$this.addClass("hover");
		if($em.hasClass("sel")){
			$em.addClass("sel-hover");
		}
	});
	$(".role-list").children("p").on("mouseleave",function(){
		var $this = $(this),
				$em = $this.children("em");
		$this.removeClass("hover");
		$em.removeClass("sel-hover");
	});
	$(".role-list").children("i").on("click",function(){
		$(this).toggleClass("sub").siblings("ul").toggle();
	});
}
