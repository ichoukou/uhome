<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>SEO信息</title>
<link href="${ _cssPath}/common.css" rel="stylesheet" />
<link href="${ _cssPath}/pages/arrangement.css" rel="stylesheet" />
<link href="${_jsPath }/plugin/artdialog/skins/ytoxl.css" rel="stylesheet" />
<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
<script src="${_jsPath }/plugin/artdialog/jquery.artDialog.min.js"></script>

<script src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"
	language="javascript"></script>
<script src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"
	language="javascript"></script>
</head>
<body>
<!--start header-->
<jsp:include page="../include/header.jsp"  flush="true" />
<!--end header-->

<!--start body-->
<div class="body m-w980p">
	<jsp:include page="../include/pageManageMenu.jsp"  flush="true" />
        <div class="m-mt10p">
            <div><input type="button" id="addSale-btn"  class="m-btn" value="新增SEO页面"/></div>
        </div>
      	<!--start SEO信息-->
        <div class="m-mt10p arrange-management">
            <table class="tab-control" id="tab">
                <thead>
                    <tr>
                        <th width="10%">Key</th>
                        <th width="10%">Key描述</th>
                        <th width="13%">标题</th>
                        <th width="15%">关键词</th>
                        <th width="30%">描述</th>
                        <th width="12%">创建时间</th>
                        <th width="10%">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${seoConfigPage.result}" var="seoConfig" varStatus="status">
                <tr class="list-tr">
                		<td >${seoConfig.urlKey }</td>
                		<td >${seoConfig.urlMean }</td>
               			<td >${seoConfig.title}</td>
						<td >${seoConfig.keyWords}</td>
						<td >${uhome:cutString(seoConfig.description,75)}</td>
						<td ><fmt:formatDate value='${seoConfig.creatTime}' pattern='yyyy-MM-dd HH:mm' /></td>
						<td >
							<input type="hidden" value="${seoConfig.seoConfigId }"/>
							<a href="javascript:;" class="J-edit-seo" >编辑</a>
							<a seoConfigId="${seoConfig.seoConfigId }" onclick="deleteSeoConfig(${seoConfig.seoConfigId});" href="javascript:;">删除</a>
						</td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
         <div class="pagination pagination-right">
			<c:if test="${not empty seoConfigPage.result}">
				<wms:commPage page="${seoConfigPage}" beanName="seoConfigPage"></wms:commPage>
			</c:if>
	   </div>
      	<!--end SEO信息-->
      	<!--start 新增、编辑SEO弹出层-->
      	
      		<div class="add-seo-info">
      		<form  method="post" id="form1" name="form1" action="${_ctxPath}/admin/seo/seo-saveSeoConfig.htm">
      		<input type="hidden" name="seoConfig.seoConfigId" id="seoConfigId" class="input-key"/>
      			<table>
      				<tr>
      					<td width="60" align="right">Key：</td>
      					<td><input type="text" class="input-key" name="seoConfig.urlKey" id="urlKey"/></td>
      				</tr>
      				 <tr>
                	<td></td>
                	<td><div class="position"><span id="urlKeyTips"></span></div></td>
                </tr>
                <tr>
      					<td width="60" align="right">Key描述：</td>
      					<td><input type="text" class="input-key" name="seoConfig.urlMean" id="urlMean"/></td>
      				</tr>
      				 <tr>
                	<td></td>
                	<td><div class="position"><span id="urlMeanTips"></span></div></td>
                </tr>
      				<tr>
      					<td width="20%" align="right">标题：</td>
      					<td><textarea type="text" class="input-text" name="seoConfig.title" id="title"></textarea></td>
      				</tr>
      				 <tr>
                	<td></td>
                	<td><div class="position"><span id="titleTips"></span></div></td>
                </tr>
      				<tr>
      					<td width="20%" align="right">关键字：</td>
      					<td><textarea type="text" class="input-text" name="seoConfig.keyWords" id="keyWords"></textarea></td>
      				</tr>
      				 <tr>
                	<td></td>
                	<td><div class="position"><span id="keyWordsTips"></span></div></td>
                </tr>
      				<tr>
      					<td width="20%" align="right">描述：</td>
      					<td><textarea type="text" class="input-text" name="seoConfig.description" id="description"></textarea></td>
      				</tr>
      				 <tr>
                	<td></td>
                	<td><div class="position"><span id="descriptionTips"></span></div></td>
                </tr>
      			</table>
      			</form>
      		</div>
      		
      	<!--end 新增、编辑SEO弹出层-->
    </div>


<!--end body-->

<!--start footer-->	
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<!--end footer-->
<script type="text/javascript">
		//验证
		$.formValidator.initConfig({
		  validatorGroup: '100001',
		  formID: 'form1',
		  errorFocus:false, //错误时不聚焦到第一个控件
		  theme: 'Default'
		});
		$("#urlKey").formValidator({
			validatorGroup: '100001',
			onShow:"请输入正确关键字",
			onFocus:"请输入正确关键字",
			onCorrect:"请输入正确关键字",
			tipID:'urlKeyTips'
			}).inputValidator({
			min:2,
			max:64,
			empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},
			onError:"你输入的关键字格式错误" 
			}).ajaxValidator({ 
						type : "post",
						dataType : "json",              
						async : true,              
						url : "${_ctxPath}/admin/seo/seo-repeatUrlKey.htm", 
						success : function(data){ 
							//console.log(data.info);
							var urlKey = $("#urlKey").val();
							var id = $("#seoConfigId").val();
							/* console.log(data.seoConfig.seoConfigId);
							console.log(data.seoConfig.urlKey);
							console.log(data); */
						if(data.seoConfig.urlKey==urlKey&&data.seoConfig.seoConfigId!=id){
							return false;
						}
							return true;              
						},              
						onError : "该关键字已存在"              
						//onWait : "正在进行合法性校验，请稍候..."          
					});
		$("#urlMean").formValidator({
			validatorGroup: '100001',
		  onShow:"",
		  onFocus:"请输入Key描述",
		  onCorrect:"",
		  tipID:'urlMeanTips'
		}).inputValidator({
			min:2,
			max:200,
			empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},
		  onError:"输入长度不正确,请确认"
		});
		$("#title").formValidator({
			validatorGroup: '100001',
		  onShow:"",
		  onFocus:"请输入标题",
		  onCorrect:"",
		  tipID:'titleTips'
		}).inputValidator({
			min:2,
			max:64,
			empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},
		  onError:"标题长度不正确,请确认"
		});
		$("#keyWords").formValidator({
			validatorGroup: '100001',
		  onShow:"",
		  onFocus:"请输入关键字",
		  onCorrect:"",
		  tipID:'keyWordsTips'
		}).inputValidator({
			min:2,
			max:128,
			empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},
		  onError:"关键字长度不正确,请确认"
		});
		$("#description").formValidator({
			validatorGroup: '100001',
		  onShow:"",
		  onFocus:"请输入描述语",
		  onCorrect:"",
		  tipID:'descriptionTips'
		}).inputValidator({
			min:2,
			max:256,
			empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空格"},
		  onError:"描述语长度不正确,请确认"
		});

	//弹层 添加
	$('#addSale-btn').on("click", function () {
		$("input[class='input-key']").val("");
   	 	$("textarea").val("");	
   	 	
	 	 var html = $(".add-seo-info").get([0]);
		 $.dialog({
			    lock: true,
			    padding: "10px",
			    title:"新增SEO页面",
			    content:html,
			    fixed: true,
			    cancel: true,
			    cancelValue:"取  消",
			    ok:function(){
			    	var result = $.formValidator.pageIsValid('100001');
			    	if(result){
			    		$('#form1').submit();
			    		return true;
			    	}else{
			    		return false;
			    	}
			    },
			    cancel:function(){
			    	 $("input[class='input-key']").val("");
			    	 $("textarea").val("");
			    	 $(".position").children().remove();
			    	 window.location.reload();
			    },
			    okValue:"保  存"
		  });
	});
	
	
	//编辑
	$('.J-edit-seo').on("click", function () {
			$("input[class='input-key']").val("");
   	 		$("textarea").val("");
   	 	
		    var html = $(".add-seo-info").get([0]);
			//var list = $(this).parent().parent().find("td:lt(4)");
			var seoConfigId = $(this).prev().val();
			$.ajax({
				type:"post",
				dataType:"json",
				data:{'seoConfig.seoConfigId':seoConfigId},
				url:"${_ctxPath}/admin/seo/seo-getSeoconfigByIdAjax.htm",
				success:function(data){
					/* console.log(data.seoConfig.seoConfigId);
					console.log(data.seoConfig.urlKey); */
					$('#seoConfigId').val(seoConfigId);
					$('#urlKey').val(data.seoConfig.urlKey);
					$('#urlMean').val(data.seoConfig.urlMean);
					$('#title').val(data.seoConfig.title);
					$('#keyWords').val(data.seoConfig.keyWords);
					$('#description').val(data.seoConfig.description);
				}
			});
			
			//判断是添加还是编辑
			/* if(list.text()!=null&&list.text()!=0&&seoConfigId!=null){
				$('#seoConfigId').val(seoConfigId);
				$('#urlKey').val($(list[0]).text());
				$('#title').text($(list[1]).text());
				$('#keyWords').text($(list[2]).text());
				$('#description').text($(list[3]).text());
			} */
			
			 $.dialog({
				    lock: true,
				    padding: "10px",
				    title:"编辑SEO页面",
				    content:html,
				    fixed: true,
				    cancel: true,
				    cancelValue:"取  消",
				    ok:function(){
				    	var result = $.formValidator.pageIsValid('100001');
				    	if(result){
				    		$('#form1').submit();
				    		return true;
				    	}else{
				    		return false;
				    	}
				    },
				    cancel:function(){
				    	 $("input[class='input-key']").val("");
				    	 $("textarea").val("");
				    	 $(".position").children().remove();
				    	 window.location.reload();
				    },
				    okValue:"保  存"
			  });
			 
	});



/*删除SEO */
function deleteSeoConfig(seoConfigId){
	if(confirm("确认删除该条记录？")){
		$.ajax({
			type : 'POST',
			url : '${_ctxPath}/admin/seo/seo-deleteSeoConfig.htm',
			data : {"seoConfigId" : seoConfigId},
			success : function(data) {
				if(data){
					alert(data.info);
				}
				window.location.reload();
				}
		});
	}
}


</script>
</body>
</html>