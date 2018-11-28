<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="${_jsPath}/plugin/uploadify/swfobject.js"></script>
<script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<form action="${_ctxPath}/admin/adv/adv_saveAdv.htm" method="post" id="advForm">
	 <!--添加广告弹出部分-->
            	<input name="adv.advertisementId" value="${adv.advertisementId}" type="hidden"/>
            	<table>
            		<tr>
            			<td width="95" align="right">*位置：</td>
            			<td width="30"></td>
            			<td width="300">
            				<select name="adv.advPositionId" id="advPositionWH" class="m-sel">
								<c:forEach items="${advPositions}" var="advPosition">
									<option data-wh="${advPosition.width}:${advPosition.height}" value="${advPosition.advpositionId}"
										<c:if test="${advPosition.advpositionId==adv.advPositionId}"> selected="selected"</c:if>>
										<c:out value="${advPosition.name}"></c:out>
									</option>
								</c:forEach>
							</select>
            			</td>
            		</tr>
            		<tr>
            			<td align="right">*广告名称：</td>
            			<td></td>
            			
            			<td>
            				<div class="p-relative">
	            				<input name="adv.name" id="adv-name" type="text" value="${adv.name}" class="ad-input-text"/>
	            				<input type="checkbox" class="set-checkbox" name="adv.isDefault" id="isDefault" <c:if test="${adv.isDefault==1}">checked</c:if> value="1" /><label for="set-default">设为默认广告</label>
								<div id="adv-nameTip"></div>
							</div>
						</td>
            		</tr>
            		<tr>
            			<td align="right">顺序：</td>
            			<td></td>
            			<td>
            				<div class="p-relative">
	            				<input name="adv.rank" type="text" id="sort" maxlength="9" 
	            					value='<c:if test="${empty adv}">1</c:if><c:if test="${not empty adv}">${adv.rank}</c:if>' 
	            					class="ad-input-text"/>
	            				<div id="sortTip"></div>
	            			</div>
            			</td>
            		</tr>
            		<tr>
            			<td align="right">*广告图片：</td>
            			<td></td>
            			<td>
            				<div class="p-relative">
            					<input type="file" id="imgUpload" name="file" />
            					<div id="advUploadImageTip">
	            					<em class="c-red">*</em> 
									<c:forEach items="${advPositions}" var="advPosition" varStatus="status">
		            				<span id="advPosition-${advPosition.advpositionId }" 
		            					<c:choose>
		            					<c:when test="${empty adv }">
		            						<c:if test="${status.index != 0 }">class="m-hide"</c:if>
		            					</c:when>
		            					<c:otherwise>
		            						<c:if test="${adv.advPositionId != advPosition.advpositionId }">class="m-hide"</c:if>
		            					</c:otherwise>
		            					</c:choose>
		            				>请上传像素为${advPosition.width}px*${advPosition.height}px的图片</span>
		            				</c:forEach>
	            				</div>
	            				<input type="hidden" id="uploadImageValueId" name="adv.imageUrl" value="${adv.imageUrl}"/>
								<c:if test="${adv.imageUrl != '' && adv.imageUrl!= null}">
									<img src="${_filePath}${adv.imageUrl}" id="showImg" width="${adv.advPosition.width/2 }" height="${adv.advPosition.height/2 }" />
								</c:if>
								<c:if test="${adv.imageUrl == '' || adv.imageUrl==null}">
									<img src="" id="showImg" />
								</c:if>
								<div id="imgTip"></div>
							</div>
						</td>
            		</tr>
            		<tr>
            			<td align="right">跳转地址：</td>
            			<td></td>
            			<td>
            				<div class="p-relative">
	            			<input name="adv.linkUrl" type="text" id="jumpUrl" value="${adv.linkUrl}" class="ad-input-addr"/>
	            			<div id="jumpUrlTip"></div>
	            		</div>
            			</td>
            		<tr>
            			<td align="right" id="startText"><c:if test="${adv.isDefault!=1}">*</c:if>广告开始时间：</td>
            			<td></td>
            			<td>
            				<div class="p-relative">
            					<input type="text" name="adv.startTime" id="starDate"  class="Wdate" value="<fmt:formatDate value='${adv.startTime}' pattern='yyyy-MM-dd'/>" 
            						 onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}',readOnly:true})" /> 
            					<div id="starDateTip"></div>
            				</div>
            			</td>
            		</tr>
            		<tr>
            			<td align="right" id="endText"><c:if test="${adv.isDefault!=1}">*</c:if>广告结束时间：</td>
            			<td></td>
            			<td>
            				<div class="p-relative">
            					<input type="text" name="adv.endTime" id="endDate" class="Wdate" value="<fmt:formatDate value='${adv.endTime}' pattern='yyyy-MM-dd'/>" 
            						 onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starDate\')||\'%y-%M-%d\'}',readOnly:true})" />
            					<div id="endDateTip"></div>
            				</div>
            			</td>
            		</tr>
            		<tr>
            			<td align="right">验证登录：</td>
            			<td></td>
            			<td>
            				<c:if test="${adv.isLogin==null||adv.isLogin==0}">
            					<input type="radio" name="adv.isLogin" checked="checked" value="0" />否 <input type="radio" name="adv.isLogin" value="1"/>是
            				</c:if>
            				<c:if test="${adv.isLogin==1}">
            					<input type="radio" name="adv.isLogin" value="0" />否 <input type="radio" name="adv.isLogin" checked="checked" value="1"/>是
            				</c:if>
            				
            			</td>
            		</tr>
            		<tr>
            			<td align="right">创建时间：</td>
            			<td></td>
            			<td><fmt:formatDate value='${adv.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            		</tr>
            		<tr>
            			<td align="right">修改时间：</td>
            			<td></td>
            			<td><fmt:formatDate value='${adv.updateTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            		</tr>
            	</table>
            <!--添加广告弹出部分 结束-->
	
	</form>
<script type="text/javascript">
	$(document).ready(function(){
		if('${adv.imageUrl}'==''||'${adv.imageUrl}'==null){
			$('#showImg').hide();
		}
		var uploadDefaultParams = {
			'auto' : true,
			'buttonImg' : '${_jsPath}/plugin/uploadify/uploadimg_btn.png',
			'cancelImg' : '${_jsPath}/plugin/uploadify/cancel.png',
			'expressInstall' : '${_jsPath}/plugin/uploadify/expressInstall.swf',
			'fileDataName' : 'file',
			'fileDesc' : '请选择jpg、gif、png文件',
			'fileExt' : '*.jpg;*.jpeg;*.gif;*.png',
			'multi' : false,
			'script' : '${_ctxPath}/upload/upload.htm',
			'sizeLimit' : 2097152,
			'uploader' : '${_jsPath}/plugin/uploadify/uploadify.allglyphs.swf'
		};
	
		var uploadlogoParams = uploadDefaultParams;
		uploadlogoParams.scriptData = {'category' : 'adv'}; //目录名字 可以是多级  logo/2013/02/26
		uploadlogoParams.onComplete = function(event, ID, fileObj, response, data) {
			$('#uploadImageValueId').val(response);
			var url = '${_filePath}' + response;
			var whArray = $("#advPositionWH > option:selected").attr("data-wh").split(":");
			$('#showImg').attr({src: url, width: whArray[0]/2 + "px", height: whArray[1]/2 + "px"});
			$('#showImg').show();
		};
		$('#imgUpload').uploadify(uploadlogoParams);
	});
	
	//选择广告位置 改变图片大小提示
	$(function(){
		var advPositionWH = $("#advPositionWH");
		var data,dataDetail;
		advPositionWH.on("change",function(){
			var $this = $(this).find("option:selected");
			var advPositionId = $this.val();
			$("#advUploadImageTip > span:visible").hide();
			$("#advPosition-" + advPositionId).show();
			
// 			data = $this.attr("data-wh");
// 			dataDetail = data.split(":");
// 			$("#advUploadImageTip").find("span").empty().text("请上传像素为"+dataDetail[0]+"px*"+dataDetail[1]+"px的图片");
// 			$("#showImg").attr({width: dataDetail[0] + "px", height: dataDetail[1] + "px"});
		});
		//默认第一个
// 		data = advPositionWH.find("option:first").attr("data-wh");
// 		dataDetail = data.split(":");
// 		$("#advUploadImageTip").find("span").empty().text("请上传像素为"+dataDetail[0]+"px*"+dataDetail[1]+"px的图片");
// 		$("#showImg").attr({width: dataDetail[0] + "px", height: dataDetail[1] + "px"});
		
// 		<c:if test="${not empty adv}">
// 		advPositionWH.change();
// 		</c:if>
	});
		//验证
		$.formValidator.initConfig({formID:"advForm",theme:"Default",submitOnce:true,onError:function(){}});
		$("#adv-name").formValidator({onShow:"请输入广告名称"}).inputValidator({min:1,max:30,onErrorMin:"请输入大于1个字符",onErrorMax:"请输入小于30个字符",empty:{leftEmpty:false,rightEmpty:false,emptyError:"广告名称两边不能有空格"},onError:"请输入广告名称"});
		$("#sort").formValidator({empty:true,onShow:"请输入排序号",onCorrect:"谢谢你的合作，你的排序号正确"}).inputValidator({min:0,max:9}).regexValidator({regExp:"intege1",dataType:"enum",onError:"请填写0~9位数字"});
		$("#uploadImageValueId").formValidator({tipID:"imgTip",onShow: "请输入图片名", onCorrect: "谢谢你的合作，你的图片名正确" }).inputValidator({min:1,onErrorMin:"请上传图片"}).regexValidator({ regExp: "picture", dataType: "enum", onError: "图片名格式不正确" });
		$("#jumpUrl").formValidator({empty:true,onShow:"请输入跳转地址",onFocus:"请输入跳转地址"<c:if test="${adv.linkUrl=='' ||adv.linkUrl==null}">,defaultValue:""</c:if>}).inputValidator({min:3,onError:"你输入网址格式不正确"}).regexValidator({regExp:"url",dataType:"enum",onError:"你输入网址格式不正确"});
		<c:if test="${adv.isDefault!=1}">
		$("#starDate").formValidator({onShow:"请输入成立日期",onFocus:"例如：2012-01-03",onCorrect:"成立日期正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
		$("#endDate").formValidator({onShow:"请输入成立日期",onFocus:"例如：2012-01-03",onCorrect:"成立日期正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
		</c:if>
		<c:if test="${adv.isDefault==1}">
		$("#starDate").formValidator({empty:true,onShow:"请输入成立日期",onFocus:"例如：2012-01-03",onCorrect:"成立日期正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
		$("#endDate").formValidator({empty:true,onShow:"请输入成立日期",onFocus:"例如：2012-01-03",onCorrect:"成立日期正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
		</c:if>
		$(function(){
			$('#isDefault').bind('click',function(){
				if(this.checked){
					$('#startText').text('广告开始时间：');
					$('#endText').text('广告结束时间：');
					$("#starDate").formValidator({empty:true});
					$("#endDate").formValidator({empty:true});
				}else{
					$('#startText').text('*广告开始时间：');
					$('#endText').text('*广告结束时间：');
					$("#starDate").formValidator({onShow:"请输入成立日期",onFocus:"例如：2012-01-03",onCorrect:"成立日期正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
					$("#endDate").formValidator({onShow:"请输入成立日期",onFocus:"例如：2012-01-03",onCorrect:"成立日期正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
				}
			});
		})
	</script>
