$(function(){
	var vals = "";
	function loadRoleList(){
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
	    showCheckedUroleName();
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
	    showCheckedUroleName();
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
	
	
	
	$('#add-role-btn,#editUrole').on("click",function(){
		var uroleId = $(this).attr("uroleId");
		$.ajax({
			type : 'POST',
			url : _ctxPath+'/admin/auth/auth-toEdit.htm',
			data : {"uroleId" : uroleId
							},
			success : function(data) {
				$(".pop-box-left").html("");
				var info = eval('('+data.info+')'); 
				var allUresouceListNoMenu = info.allUresouceListNoMenu;
				var allUresouceListMenu = info.allUresouceListMenu;
				for(var i=0;i<allUresouceListMenu.length; i++){
						$(".pop-box-left").append('<div id="div'+allUresouceListMenu[i].uresourceId+'" class="role-list"><i></i><p id="'+allUresouceListMenu[i].uresourceId+'" class="J-parent"><em></em><label title="'+allUresouceListMenu[i].uresourceId+'">'+allUresouceListMenu[i].uresourceName+'</label></p><ul></ul></div>');
						for(var j=0;j<allUresouceListNoMenu.length; j++){
							if(allUresouceListNoMenu[j].parentId==allUresouceListMenu[i].uresourceId){//属于该菜单
								$("#div"+allUresouceListMenu[i].uresourceId+" ul").append('<li class="role-list"><i class="sub"></i><p id="'+allUresouceListNoMenu[j].uresourceId+'" class="J-children"><em></em><label title="'+allUresouceListNoMenu[j].uresourceId+'">'+allUresouceListNoMenu[j].uresourceName+'</label></p></li>');
							}
						}
				}
				loadRoleList();//加载js
				
				var currentUresouceList = info.currentUresouceList;
				var isEditText = "新增角色";
				if(currentUresouceList){
					isEditText = "编辑角色";
				   for(var j=0;j<currentUresouceList.length; j++){
				      	  $("#"+currentUresouceList[j].uresourceId).find("em").addClass("sel");
					}
				   showCheckedUroleName();
				}
				var uroleName = info.uroleName;
				if(uroleName){
					$("#uroleName").val(uroleName);
				}else{
					$("#uroleName").val('');
				}
				  var html = $(".role").get([0]);
				  $.dialog({
				    lock: true,
				    padding: "10px",
				    title:isEditText,
				    content:html,
				    fixed: true,
				    cancel: true,
				    cancelValue:"取  消",
				    ok:function(){
				    	var roleNameTrim = $.trim($("#uroleName").val());     
				    	if(roleNameTrim==null||roleNameTrim==""||roleNameTrim==undefined){
				    		alert("请正确填写角色名!");
				    		$("#uroleName").val("");
				    		$("#uroleName").focus();
				    		return false;
				    	}else{
				    		
					    	$.ajax({
								type : 'POST',
								url : _ctxPath+'/admin/auth/auth-save.htm',
								data : {"uresourceIds":vals,"uroleName":$("#uroleName").val(),"uroleId" : uroleId},
								success : function(data) {
										if(data!=null){
											var info = data.info;
											alert(info);
										}
										window.location.reload();
									}
								});
				    	}
				    },
				    okValue:"保  存"
				  });
				}
			});
	});
	function showCheckedUroleName(){
        var arr=new Array();
        var html="";
		vals = "";
        var length=$(".role-list .sel").length;
        for(var i=0;i<length;i++){
            var text=$(".role-list .sel").eq(i).parent().find("label").text();
            vals = vals+$(".role-list .sel").eq(i).parent().find("label").attr("title")+",";
            var html='<li>'+text+'</li>';
            arr.push(html);
        }
        $(".pop-box-right ul").html(arr.join(""));

    }
	$("#addKeyword").on("click",function(){
			var $userName = $("input[name='myUser.username']");
			if($userName.length > 0 && $.trim($userName.val()).length == 0)
    		{
    				alert("登录名不能为空");
    				$userName.focus();
    				return;
    		}
    		if($('input:radio[name="myUser.urole.uroleId"]:checked').val() == undefined)
			{
				alert("请选择角色");
				return;
			}
    		if($userName.length > 0){
    			$.ajax({
        			type : 'POST',
        			url : _ctxPath+'/admin/auth/auth-checkUser.htm',
        			data : {"username" : $.trim($userName.val())},
        			success : function(data) {
        				if(data){
        					alert(data.info);
        					$userName.focus();
        				}else{
        					$("#mainForm").submit();
        				}
        			}
    			});
    		}else{
    			$("#mainForm").submit();
    		}
    	});
});
function deleteUrole(uroleId){
	if(confirm("确认删除该角色？")){
	$.ajax({
		type : 'POST',
		url : _ctxPath+'/admin/auth/auth-deleteUrole.htm',
		data : {"uroleId" : uroleId
						},
		success : function(data) {
			if(data){
				alert(data.info);
			}
			window.location.reload();
			}
		});
	}
}
