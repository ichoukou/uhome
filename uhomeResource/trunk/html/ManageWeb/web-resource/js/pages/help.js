$(document).ready(function(){
	//ckeditor操作
	CKEDITOR.replace("help.content");
	CKEDITOR.on('dialogDefinition', function( ev ){
        var dialogName = ev.data.name;
        var dialogDefinition = ev.data.definition;
        if ( dialogName == 'image' ){
            var infoTab = dialogDefinition.getContents( 'info' );
            infoTab.add({
                type : 'button',
                id : 'upload_image',
                align : 'center',
                label : '上传',
                onClick : function( evt ){
                    var thisDialog = this.getDialog();
                    var txtUrlObj = thisDialog.getContentElement('info', 'txtUrl');
                    var txtUrlId = txtUrlObj.getInputElement().$.id;
                    addUploadImage(txtUrlId);
                }
            }, "txtAlt"); //place front of the browser button
        }
    });
	function addUploadImage(theURLElementId){
	    var uploadUrl = _ctxPath+"/upload/help-image-upload.htm";
	    var imgUrl = window.showModalDialog(uploadUrl,'title',
				'=yes;resizable=no;help=no;status=no;dialogHeight=200px;dialogWidth=300px'); 
	    var urlObj = document.getElementById(theURLElementId);
	    urlObj.value = imgUrl;
	    onchangeFun(urlObj);
	}
	function onchangeFun(obj){
		if (document.all) {
			obj.fireEvent("onchange");
		} else {
			var evt = document.createEvent('HTMLEvents');
			evt.initEvent('change', true, true);
			obj.dispatchEvent(evt);
		} 
	}
	
	CKEDITOR.instances.content.on("instanceReady",
			function() {
				this.document.on("keyup", countBusinessScopeText);
				this.document.on("paste", countBusinessScopeText);
			}); 
	function countBusinessScopeText(){
		CKEDITOR.tools.setTimeout(function() {
			var length = CKEDITOR.instances.content.getData().length;
			$("#contentCount").text(length);
		}, 0);   
	}
	
	$('#save').bind('click',function(){
		if($('#helpId').val()==''){
			return;
		}
		$.ajax({
			url : _ctxPath+"/admin/help/help-updateContent.htm",
			type : "POST",
			data : {
				'help.helpId' : $('#helpId').val(),
				'help.content' : CKEDITOR.instances.content.getData()
			},
			dataType : "json",
			success : function(data) {
				alert(data.info)
			},
			error : function(data) {
				console.log(data);
			}
		});
	});
});
var getContent=function(a, id){
	$.ajax({
		url : _ctxPath+"/admin/help/help-getContentByHelpId.htm",
		type : "POST",
		data : {
			'help.helpId' : id
		},
		dataType : "json",
		success : function(data) {
			$('#helpId').val(id);
			$('#title').val($(a).text())
			CKEDITOR.instances.content.setData( data.info)
		},
		error : function(data) {
			console.log(data);
		}
	});
	
}
