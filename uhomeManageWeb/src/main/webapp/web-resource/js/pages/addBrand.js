//图片上传空间实例化
$(function(){
  //进入该页面的时候初始化文本域的值
  $('#describArea').val($('#describeInput').val());
  var uploadDefaultParams = {
		'auto' : true,
		'buttonImg' : _jsPath+'/plugin/uploadify/uploadimg_btn.png',
		'cancelImg' : _jsPath+'/plugin/uploadify/cancel.png',
		'expressInstall' : _jsPath+'/plugin/uploadify/expressInstall.swf',
		'fileDataName'   : 'file',
		'fileDesc' : '请选择jpg、gif、png文件',
		'fileExt' : '*.jpg;*.jpeg;*.gif;*.png',
	    'height' : 100,
	    'multi' : false,
	    'script' : _ctxPath+'/upload/upload.htm',
	    'uploader' : _jsPath+'/plugin/uploadify/uploadify.allglyphs.swf',
	    'width' : 200,
	    'scriptData' : {'category':'brand'}
	};
    var uploadLogoImgParams = uploadDefaultParams;
    uploadLogoImgParams.onComplete = function(event, ID, fileObj, response, data){
		$('#logoImageUrl').val(response);
		$('#logoImg').attr('src', _filePath+response);
		$('#logoImg').show();
   };
   $('#logoImgUpload').uploadify(uploadLogoImgParams);

  var uploadBrandImgParams = uploadDefaultParams;
  uploadBrandImgParams.onComplete = function(event, ID, fileObj, response, data){
		$('#brandImageUrl').val(response);
		$('#brandImg').attr('src', _filePath+response);
		$('#brandImg').show();
   };
   $('#brandImgUpload').uploadify(uploadBrandImgParams);

   var uploadBannerImgParams = uploadDefaultParams;
   uploadBannerImgParams.onComplete = function(event, ID, fileObj, response, data){
		$('#bannerImageUrl').val(response);
		$('#bannerImg').attr('src', _filePath+response);
		$('#bannerImg').show();
     };
	 $('#bannerImgUpload').uploadify(uploadBannerImgParams);

	 $('#addBrand-btn').on("click",function(){
		 var result = $.formValidator.pageIsValid('1');
		 var formBrand = $('#addBrand-form');
	   $('#describeInput').val($('#describArea').val());
		 if(result){
			 $.ajax({
					type: 'POST',
					url: _ctxPath+'/admin/brand/brand-singleBrandSset.htm',
					data: formBrand.serialize(),
					success: function(data){
						switch(data.info){
    					case '1':
    						 $.dialog({
					                title: false,
					                content: "品牌重复",
					                time: 2000
					            });
							 $(".d-close").hide();
    				    	  break;
    				      case '2':
    				    	  $.dialog({
					                title: false,
					                content: "提交成功",
					                time: 2000
					            });
							 $(".d-close").hide();
							 window.location=_ctxPath+'/admin/brand/brand-listUserBrans.htm';
    				    	  break;
    					}

				        },
					dataType:'json'
					});
		 }
	   });
	 if(!$("#set-hotBrand").attr("checked")){
		 //解除校验
		 $("#bannerImgTr").hide();
		 $("#bannerImageUrl").attr("disabled",true).unFormValidator(true);
	 }
	 $("#set-hotBrand").click(function(){
		 if($(this).attr("checked")){
			 $("#bannerImgTr").show();
			 //恢复校验
			 $("#bannerImageUrl").attr("disabled",false).unFormValidator(false);
		 }else{
			 $("#bannerImgTr").hide();
			 //解除校验
			 $("#bannerImageUrl").attr("disabled",true).unFormValidator(true);
		 }
	 });
});
	$.formValidator.initConfig({formID:"addBrand-form",theme:"Default",validatorGroup: '1',wideWord:false,onError:function(){}});
	function allEmpty(val,elem){
		var name = $("#addBrand-zh-name").val();
		var englishName = $("#addBrand-en-name").val();
		return ($.trim(name).length == 0 && $.trim(englishName).length == 0) ? '请至少填写一种品牌名字！' : true;
	}
	
	$("#addBrand-zh-name").formValidator({validatorGroup:'1',tipID:"addBrandnameTip",onShow:"请输入中文名",onFocus:"请输入中文名",oncorrect:"输入正确！"}).inputValidator({min:0,max:15,onErrorMax:"请输入不大于15个字符",empty:{leftEmpty:false,rightEmpty:false,emptyError:"品牌名称两边不能有空格"},onError:"你的输入错误"}).functionValidator({fun:allEmpty});
	$("#brandNamePinYin").formValidator({validatorGroup: '1',tipID:"brandNamePinYinTip"}).inputValidator({min:1,max:1,onErrorMin:"请输入一个字符（26个英文字母或0-9数字）",onErrorMax:"只能输入一个字符（26个英文字母或0-9数字）",onError:"只能输入一个字符（26个英文字母或0-9数字）"}).regexValidator({ regExp: "^[a-zA-Z0-9]+$",dataType: "string",onError: "只能输入英文字母或数字" });
	$("#addBrand-en-name").formValidator({validatorGroup: '1',tipID:"addBrandnameTip",onShow:"请输入英文名",onFocus:"请输入英文名",oncorrect:"输入正确！"}).inputValidator({min:0,max:30,onErrorMax:"请输入不大于30个字符",empty:{leftEmpty:false,rightEmpty:false,emptyError:"品牌名称两边不能有空格"},onError:"你的输入错误"}).functionValidator({fun:allEmpty});
	$("#selectBrand").formValidator({validatorGroup: '1',onshow:"请选择品牌类目",onfocus:"品牌类目必须选择",oncorrect:"谢谢你的配合",onError:"请选择品牌类目",defaultvalue:"a"}).inputValidator({min:1,onError: "你是不是忘了选择品牌类目!"}).defaultPassed();
	$("#brandTd").formValidator({validatorGroup: '1',empty:true,onShow:"请输入品牌特点"}).inputValidator({min:0,max:30,onErrorMax:"请输入小于30个字符",empty:{leftEmpty:false,rightEmpty:false,emptyError:"品牌特点两边不能有空格"},onError:"你的输入错误"});
	$("#country").formValidator({validatorGroup: '1',empty:true,onShow:"请输入品牌所属国家"}).inputValidator({min:0,max:10,onErrorMax:"请输入小于10个字符",empty:{leftEmpty:false,rightEmpty:false,emptyError:"品牌所属国家两边不能有空格"},onError:"你的输入错误"});
	$("#originator").formValidator({validatorGroup: '1',empty:true,onShow:"请输入品牌创始人"}).inputValidator({min:0,max:30,onErrorMax:"请输入小于30个字符",empty:{leftEmpty:false,rightEmpty:false,emptyError:"品牌创始人两边不能有空格"},onError:"你的输入错误"});
	$("#date").formValidator({validatorGroup: '1',empty:true,onShow:"请选择成立日期",onFocus:"请选择成立日期",onCorrect:"成立日期正确"}).regexValidator({regExp:"^\\d{4}$",onError:"日期格式不正确"});
	$("#describArea").formValidator({validatorGroup: '1',onShow:"请输入品牌简介",onFocus:"请输入品牌简介"}).inputValidator({min:10,max:10000,onErrorMin:"请输入大于10个字符",onErrorMax:"请输入小于10000个字符",onError:"你的输入错误"}).functionValidator({fun:function(val){
			return $.trim(val).length == 0? '请输入品牌简介' : true;
	 		 }});
	$("#logoImageUrl").formValidator({validatorGroup: '1',tipID:"logoImageTip", onShow:"请上传图片",onFocus:"请上传图片",onCorrect: "谢谢你的合作，你的图片名正确" }).inputValidator({min:1,onErrorMin:"请上传图片"}).regexValidator({ regExp: "picture", dataType: "enum", onError: "图片名格式不正确" });
	$("#brandImageUrl").formValidator({validatorGroup: '1',tipID:"brandImageTip", onShow:"请上传图片",onFocus:"请上传图片",onCorrect: "谢谢你的合作，你的图片名正确" }).inputValidator({min:1,onErrorMin:"请上传图片"}).regexValidator({ regExp: "picture", dataType: "enum", onError: "图片名格式不正确" });
	$("#bannerImageUrl").formValidator({validatorGroup: '1',tipID:"bannerImageTip", onShow:"请上传图片",onFocus:"请上传图片",onCorrect: "谢谢你的合作，你的图片名正确" }).inputValidator({min:1,onErrorMin:"请上传图片"}).regexValidator({ regExp: "picture", dataType: "enum", onError: "图片名格式不正确" });
