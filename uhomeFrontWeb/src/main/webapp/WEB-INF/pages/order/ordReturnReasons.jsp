<!DOCTYPE html>
<%@page import="com.ytoxl.module.uhome.uhomeorder.dataobject.OrderReturnItem"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
  <meta charset="utf-8"/>
    <title>退货订单-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/return-manage.css?d=${_resVerion}"/>
</head>
<body>
<%
int gg = 0;
%>
 <jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
    <div class="m-w960p cf">
        <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/order/order-myOrders.htm">个人中心 ></a>我的退换货</div>
        <!--面包屑 end-->
        <jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>       
 <div class="inf-detail m-mt20p">
 	 <div class="tab-gn">
          <ul class="tab-gnul">
             <li class="cur"><a href="${_ctxPath}/returnOrder/returnOrder-myOrders.htm">申请退货</a></li>
             <li><a href="${_ctxPath}/returnOrder/returnOrder-myReturnOrders.htm">退货记录</a></li>
          </ul>
                <div class="protab-item cf">
                	<!--star 退款单个商品-->
                    <form action="" method="post" name="subForm" id="returnMsg">
	                	<input type="hidden" name="orderReturn.orderId"  value="${orderHead.orderId}">
            		<c:forEach items="${orderHead.items}" var="item" varStatus="status">
                		<div class="user-tab">
       			 		<table>
                       		 <thead>
	                          <tr>
	                              <th width="40%">商品名称</th>
	                              <th width="20%">下单时间</th>
	                              <th width="20%">该商品实际支付金额</th>
	                              <th width="20%">退货数量</th>
	                          </tr>
                        	</thead>
	                          <tbody>
	                          <tr>
                              <td>
                                  <ul class="cf">
                                     <li class="cf bor-none">
                                         <div class="m-fl ret-bgimg">
                                             <img src="<zx:thumbImage originalPath='${item.productSku.product.defaultImage}' imageType='t16'></zx:thumbImage>" width="65px" height="65px"/>
                                         </div>
                                         <div class="over-box">
                                             <div><a href="${_ctxPath}/item-${item.productSku.product.productId}.htm" class="cor-orange">${item.productName}</a></div>
                                             <div>
                                               <c:forEach items="${item.productSku.productSkuOptionValues}" var="option" >
												<label>${option.skuOptionValue.skuOption.skuOptionName}:
											<c:choose>
											<c:when test="${not empty option.overrideSkuOptionValue}">
												${option.overrideSkuOptionValue}&nbsp;&nbsp;</label>
											</c:when>
											<c:otherwise>
												${option.skuOptionValue.skuOptionValue}&nbsp;&nbsp;</label>
											</c:otherwise>
											</c:choose>
											</c:forEach>
                                             </div>
                                         </div>
                                     </li>
                                  </ul>
                              </td>
                              <td><fmt:formatDate value='${orderHead.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                              <td>
                              <c:if test="${item.rebatePrice==null}">
                               <span class="cor-orange">
                               	商品金额(<span class="coin">¥</span>${item.closingCost}*${item.num})-优惠券抵扣金额(<span class="coin">¥</span>0.00)
                               	=实际支付金额(<span class="coin">¥</span>${item.closingCost*item.num})
                               	</span>
                              </c:if>
                              <c:if test="${item.rebatePrice!=null}">
                               <span class="cor-orange">
                               	商品金额(<span class="coin">¥</span>${item.closingCost}*${item.num})-优惠券抵扣金额(<span class="coin">¥</span>${item.closingCost*item.num - item.rebatePrice})
                               	=实际支付金额(<span class="coin">¥</span>${item.rebatePrice})
                               	</span>
                               	
                               	<c:if test="${item.rebatePrice eq '0.00' }">
		                		<input type="hidden" name="orderReturn.returnItems[${status.index}].refundAmount" value="0" />	
                               	</c:if>
                              </c:if>
                              </td>
                              <td>
                              	      可退数量：<span class="cor-orange">${item.ableReturnNum}</span><br>
                              	      实退数量：<input type="text" name="orderReturn.returnItems[${ status.index }].num" class="key_press" value="${item.ableReturnNum}" />
                              </td>
                          </tr>
                          </tbody>
                      </table>
                      </div>
                      <div class="return-info">
		                  	<table>
		                  		<tr>
		                  			<td width="12%" align="right">*问题描述：</td>
		                  			<td width="455"><textarea class="describe-textarea" id="describe${ status.index }" name="orderReturn.returnItems[${ status.index }].describe"></textarea></td>
		                  			<td><div id="describe${ status.index }Tip"></div></td>
		                  		</tr>
		                  		<tr>
		                  			<td align="right">上传凭证：</td>
		                  			<td>
		                  				<table width="100%">
	                  					<tr class="show-image">
	                  						<td width="25%">
	                  							<div class="default-img"> <div class="del-img"></div> <!--不上传图片没有img标签--> </div>
												<input type="hidden" class="imageUrl"/>
	                  						</td>
	                  						<td width="25%">
	                  							<div class="default-img"> <div class="del-img"></div> <!--不上传图片没有img标签--> </div>
												<input type="hidden" class="imageUrl"/>
	                  						</td>
	                  						<td width="25%">
	                  							<div class="default-img"><div class="del-img"></div><!--不上传图片没有img标签--></div>
												<input type="hidden" class="imageUrl"/>
	                  						</td>
	                  						<td width="25%" valign="bottom">
	                  							<div class="upload_btn">
	                  								<input  type="file"  name="file" value="上传图片" id="imgUpload_${status.index }" />
	                  							</div>
	                  						</td>
	                  					</tr>
		                  				</table>
		                  			</td>
		                  			<td></td>
		                  		</tr>
		                  		<tr>
		                  			<td align="right"></td>
		                  			<td colspan="2"><span>最多上传3张，每张不超过1M，支持JPG、GIF、PNG、BMP格式</span></td>
		                  		</tr>
							</table>
                  		</div>	 
		                	<input type="hidden" name="orderReturn.returnItems[${status.index}].imageUrls" >
		                	<input type="hidden" name="orderReturn.returnItems[${status.index}].status" value="<%=OrderReturnItem.STATUS_NOTAUDIT %>">	
		                	<input type="hidden" name="orderReturn.returnItems[${status.index}].orderItemId" value="${item.orderItemId}">	
            	        </c:forEach>
           	        	<table class="return-tab" width="100%">	                  		
           	        		<tr>
	                  			<td align="right" width="12%">*退款方式：</td>
	                  			<td colspan="2"><span>退货完成后系统将以原支付方式退回支付金额.</span></td>
	                  		</tr>
	                  		<tr>
	                  			<td align="right">*是否有发票：</td>
	                  			<td colspan="2">	
					                <c:if test="${orderHead.payment.hasInvoice  eq 1 }"><span>是，请将发票与商品一起退回.</span></c:if>	
									<c:if test="${orderHead.payment.hasInvoice  eq 0 }"><span>没有发票.</span></c:if>	
							   </td>
	                  		</tr>
	                  		<tr>
	                  			<td align="right">*联系人：</td>
	                  			<td width="200"><input type="text" class="text-r" name="orderReturn.customerName" value="" id="link_name" /></td>
	                  			<td><div id="link_nameTip"></div></td>
	                  		</tr>
	                  		<tr>
	                  			<td align="right">*手机号码：</td>
	                  			<td><input type="text" class="text-r" name="orderReturn.telephone" value="" id="tel" maxlength="11" /></td>
	                  			<td><div id="telTip"></div></td>
	                  		</tr>
	                  	</table>
            	    </form>		  
                  <!--end 退款单个商品-->
                    <div class="return-submit">
	                	<a class="btn-r" id="return-sub"><span>提&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交</span></a>
	                  	<a class="btn-cencal" onclick="javascript:history.go(-1);"><span>取&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;消</span></a>
	                </div>
				</div>      
	</div>
 </div>
 </div>

 
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<script type="text/javascript" src="${_jsPath}/plugin/uploadify/swfobject.js"></script>
<script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<script>

$("#return-sub").on("click",function(){
	var orderLength = "${fn:length(orderHead.items) }";
	for(var i = 0;i<orderLength;i++){
		var describeCon = $("#describe"+i).val();
		if(describeCon==''){
			alert("问题描述不能为空");
			return;
		}
		if(describeCon.length>500){
			alert("问题描述最多500字");
			return;
		}
	}
	var result = $.formValidator.pageIsValid('1001');
	if(!result){
		return false;
	}else{
		$.dialog({
		  lock: true,
		  padding: "50px 60px",
		  title:false,
		  content:"确定本次退货操作吗？",
		  fixed: true,
		  cancel: true,
		  cancelValue:"取  消",
		  ok:function(){
			  
				$.each($(".show-image"),function(i,obj){
					var imageUrls = "";
					$.each($(obj).find(".imageUrl"),function(i,obj){
						var imgUrl = $(obj).val();
						if(imgUrl!="")
							imageUrls += imgUrl + ",";
					});
					if(imageUrls.length > 0){
						imageUrls = imageUrls.substring(0, imageUrls.length-1);
						$("input[name='orderReturn.returnItems["+ i +"].imageUrls']").val(imageUrls);
					}
				});
				$.ajax({
					type: 'POST',
					url: _ctxPath+'/returnOrder/returnOrder-addReturnMssage.htm',
					data: $("#returnMsg").serializeArray(),
					success:function(data){
						if(data.code == "true"){
							window.location.href=_ctxPath+'/returnOrder/returnOrder-myReturnOrders.htm';
						} else {
							popupDialog("保存出错！" + data.info);
						}
					},
					dataType:'json'
				 });
		  },
		  okValue:"确  认"
		});
		$(".d-close").hide();
	}
	})
	//上传图片显示页面
$(function(){
	var iFlash = null;
    var version = null;
    var isIE = navigator.userAgent.toLowerCase().indexOf("msie") != -1
    if(isIE){
        //for IE

        if (window.ActiveXObject) {
            var control = null;
            try {
                control = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
            } catch (e) {
                iFlash = false;
            }
            if (control) {
                iFlash = true;
            }
        }
    }else{
        //for other
        if (navigator.plugins) {
          for (var i=0; i < navigator.plugins.length; i++) {
            if (navigator.plugins[i].name.toLowerCase().indexOf("shockwave flash") >= 0) {
              iFlash = true;
            }
          }
        }
    }
    if(!iFlash){
        if(confirm("您的浏览器未安装FLASH插件，点击确定按钮去下载。")){
        	var mypop = window.open("http://www.macromedia.com/go/getflashplayer");
        	try{            
     	       mypop.focus();//
     	     }catch(e){
     	        alert('您的浏览器自动阻止了弹出窗口，请根据浏览器提示允许窗口弹出。');
     	     }
        }
    }
	var uploadDefaultParams = {
					'auto' : true,
					'buttonImg' : '${_jsPath}/plugin/uploadify/uploadimg_btn.png',
					'cancelImg' : '${_jsPath}/plugin/uploadify/cancel.png',
					'expressInstall' : '${_jsPath}/plugin/uploadify/expressInstall.swf',
					'fileDataName'   : 'file', 
					'fileDesc' : '请选择jpg、gif、png文件',
					'fileExt' : '*.jpg;*.jpeg;*.gif;*.png',
				    'height' :20,
				    'multi' : true,
				    'script' : '${_ctxPath}/upload/upload.htm',
				    'sizeLimit' : 2097152, 
				    'uploader' : '${_jsPath}/plugin/uploadify/uploadify.allglyphs.swf',
				    'width' : 100
			};
	
	$.each($(":file"),function(i,obj){
		var uploadlogoParams = uploadDefaultParams;
		uploadlogoParams.scriptData  = {'category':'returnOrder'}; 
		var index = num = 0;
		uploadlogoParams.onComplete = function(event, ID, fileObj, response, data){
			var $td = $(obj).parents(".show-image").find('td').eq(index++);
	    	//$td.find(".default-img").find("img").remove();
			var suffix = response.split(".")[1];
			var url = '${_fileThumbPath}' + response + "_t15." + suffix;
			if($td.find("img").get(0)){
				$td.find("img").attr("src",url);
			}else{
				$td.find(".del-img").after("<img src='"+url+"'/>");
			}
			$td.find(".imageUrl").val(response);
			if(index > 2){
				index = 0;
				num = 3;
			}
			if(num!=3){
				num = index;
			}
			console.log("index:"+index+"num:"+num)
	     }
	     $(".del-img").on("click",function() {
	     	num--;
	     	index = num;
	     	console.log("index:"+index+"num:"+num)
	     });
	    $(obj).uploadify(uploadlogoParams);
	 });
	 //如果有上传图片，显示交换按钮
		$(".default-img").hover(function(){
			if($(this).find("img")[0]){
				$(this).find(".del-img").show();
			}
		},function(){
			$(this).find(".del-img").hide();
		});
		//删除图片
		$(".del-img").on("click",function(){
			var $this = $(this),
				$td = $this.closest("td"),
				$td_last = $td.siblings(":last");
			$this.hide().next("img").remove();
			$td_last.before($td);
			$td.find(".imageUrl").val("");
		});
		//退货数量输入触发事件
		$(".key_press").keyup(function(event) {
            $this = $(this);
            var value = $this.val();
            if(value!=""){
				var reg = /[^0-9]/g;
				value = value.replace(reg,"")
				if(parseInt(value)==0){
					value = "";
				}
				if(value.length>9){
					value = value.substring(0,9);
				}
				$this.val(value);
			}
        });  
		$(".key_press").on("blur",function(){
			if($(this).val()==''||$(this).val()=='0'){
				alert("实退数量至少为1");
				$(this).val(1);
				return;
			}
			var this_num = parseInt($(this).val());
			var max_num = parseInt($(this).siblings(".cor-orange").text());
			if( this_num > max_num ){
				alert("实退数量不能大于可退数量");
				$(this).val(max_num);
			}
		});
		
		$("textarea").on("blur",function(){
			if($(this).val()==''){
				alert("问题描述不能为空");
				//$(this).focus();
				return;
			}
			if($(this).val().length>500){
				alert("问题描述最多500字");
				//$(this).focus();
				return;
			}
		});
  	//验证
    $.formValidator.initConfig({
	    validatorGroup: '1001',
	    formID: 'returnMsg',
	    theme: 'yto'
  	});
  	
    $("#link_name").formValidator({
  		validatorGroup: '1001',
          onShow:"请填写联系人姓名",
          onFocus:"请填写联系人姓名",
          onCorrect:""
  	}).inputValidator({
      	min:2,
      	max:20,
        onError:"联系人姓名长度不正确,请确认"
   	});
    $("#tel").formValidator({
    	validatorGroup: '1001',
        onShow:"请输手机号码",
        onFocus:"手机号码11个数字",
        onCorrect:""
    }).inputValidator({
        min:11,
        max:11,
        onError:"你输入的手机号码长度不正确,请确认"
    }).regexValidator({regExp:"mobile",dataType:"enum",onError:"你输入的手机号码格式不正确"});
   
});



</script>
</body>
</html>