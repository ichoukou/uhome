function editUser(){
	window.location = _ctxPath+"/admin/auth/auth-toEditUser.htm";
}
//禁用帐号
function unAbleUser(userId){
	var isAblehtml = $("#"+userId).html();
	var url = "";
	var showHtml = "";
	if(isAblehtml=='禁用'){
		showHtml = "启用"
		url = _ctxPath+'/admin/auth/auth-updateUnableUser.htm';
	}else{
		showHtml="禁用"
		url = _ctxPath+'/admin/auth/auth-updateAbleUser.htm';
	}
	if(confirm("确认"+isAblehtml+"该帐号？")){
		$.ajax({
			type : 'POST',
			url : url,
			data : {"myUser.userId" : userId
							},
			success : function(data) {
				$("#"+userId).html(showHtml);
				}
			});
	}
}
//重置密码
function resetPassWd(userId){
		$.ajax({
			type : 'POST',
			url : _ctxPath+'/admin/auth/auth-updateDefaultPassword.htm',
			data : {"myUserId" : userId
							},
			success : function(data) {
				alert("密码已重置为123456");
				}
			});
}
//删除子账户
function deleteUser(userId){
	if(confirm("确认删除该帐号？")){
		$.ajax({
			type : 'POST',
			url : _ctxPath+'/admin/auth/auth-deleteUser.htm',
			data : {"myUserId" : userId
							},
			success : function(data) {
				window.location=_ctxPath+"/admin/auth/auth-listUsers.htm";
				}
			});
	}
}
