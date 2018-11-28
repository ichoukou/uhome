//ckeditor操作
CKEDITOR.on('dialogDefinition', function( ev ){
    var dialogName = ev.data.name;
    var dialogDefinition = ev.data.definition;
    if ( dialogName == 'image' ){
        var infoTab = dialogDefinition.getContents( 'info' );
        infoTab.add({
            type : 'button',
            id : 'upload_image',
            align : 'center',
            label : '添加图片',
            onClick : function( evt ){
                var thisDialog = this.getDialog();
                var txtUrlObj = thisDialog.getContentElement('info', 'txtUrl');
                var txtUrlId = txtUrlObj.getInputElement().$.id;
                addUploadImage(txtUrlId);
            }
        }, 'txtAlt');
    }
});
function addUploadImage(theURLElementId){
    var uploadUrl = contextPath+"/upload/image-upload.htm";
    var imgUrl = window.showModalDialog(uploadUrl,'title',
			'=yes;resizable=no;help=no;status=no;dialogHeight=200px;dialogWidth=300px'); 
    var urlObj = document.getElementById(theURLElementId);
    if(imgUrl){
	    urlObj.value = imgUrl;
	    onchangeFun(urlObj);
    }
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
/*
CKEDITOR.instances.businessScope.on("instanceReady",
		function() {
			this.document.on("keyup", countBusinessScopeText);
			this.document.on("paste", countBusinessScopeText);
		}); 
function countBusinessScopeText(){
	CKEDITOR.tools.setTimeout(function() {
		var length = CKEDITOR.instances.businessScope.getData().length;
		$("#businessScopeCount").text(length);
	}, 0);   
}

CKEDITOR.instances.remark.on("instanceReady",
		function() {
			this.document.on("keyup", countRemarkText);
			this.document.on("paste", countRemarkText);
		}); 
function countRemarkText(){
	CKEDITOR.tools.setTimeout(function() {
		var length = CKEDITOR.instances.remark.getData().length;
		$("#remarkCount").text(length);
	}, 0);   
}*/