<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.ExpressMessage" %>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<tr class="show-tr">
	<td colspan="10" class="show-tab">
		<div class="order-info">
			<div class="m-clear">
				<div class="m-fl f-wl">
					<div class="order-botcolor order-tdwidth">
						<h4>订单详情</h4>
						<ul>
						<!-- 订单所购的商品 -->
						<c:forEach items="${orderHead.items}" var="item">
							<li>
								<table>
									<tr>
										<td rowspan="2" width="70px"><img src="<zx:thumbImage originalPath='${item.productSku.product.defaultImage}' imageType='t16'/>" border=""/></td>
										<td width="15%">${item.productSku.product.brand.name}</td>
										<td width="42%">${item.productName}</td>
										<td width="15%"><span class="coin"><span class="coin">&yen;</span></span><label>${item.closingCost}</label></td>
										<td width="10%">X   ${item.num}</td>
									</tr>
									<tr>
										<td colspan="4">
											<!-- 商品sku信息 -->
											<c:forEach items="${item.productSku.productSkuOptionValues}" var="option">
												${option.skuOptionValue.skuOption.skuOptionName}:
												<label>
												<c:choose>
													<c:when test="${not empty option.overrideSkuOptionValue}">
													 ${option.overrideSkuOptionValue}&nbsp;&nbsp;
													</c:when>
													<c:otherwise>
														${option.skuOptionValue.skuOptionValue}&nbsp;&nbsp;
													</c:otherwise>
												</c:choose>
												</label>
											</c:forEach>
										</td>
									</tr>
								</table>
							</li>
						</c:forEach>
						</ul>
					</div>
					<div class="order-tdwidth">
						<table>
							<tr>
								<td width="15%">商品金额：</td>
								<td width="23%"><span class="coin">&yen;</span><label>${orderHead.payment.closingCostAmount - orderHead.orderPostage.postage}</label></td>
								<td width="10%">运费：</td>
								<td width="15%"><span class="coin">&yen;</span><label>${orderHead.orderPostage.postage }</label></td>
								<td width="*">
									优惠抵扣：<span class="coin">&yen;</span>${orderHead.payment.closingCostAmount - orderHead.payment.paymentAmount} <br/>
									支付总额：<span class="coin">&yen;</span>${orderHead.payment.paymentAmount}
								</td>
							</tr>
							<c:if test="${orderHead.payment.hasInvoice == 1}">
								<tr>
									<td colspan="2">发票信息：</td>
									<td colspan="3">
										<label>
											<c:if test="${empty orderHead.payment.invoiceTitle}">个人</c:if>
											<c:if test="${not empty orderHead.payment.invoiceTitle}">${orderHead.payment.invoiceTitle}</c:if>
										</label>
									</td>
									<%--<td width="20%" class="c-red">
										<span> <span class="coin">&yen;</span>${orderHead.payment.paymentAmount} </span>
									</td>--%>
								</tr>
							</c:if>
							<tr>
								<td width="20%">支付宝帐号:</td>
								<td colspan="4">
									${orderHead.payment.account}
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="f-wr tab-change">
					<div class="fr-tab">
						<ul class="m-clear">
							<li class="bot-no">收货信息</li>
							<li>物流状态</li>
						</ul>
					</div>
					<div class="order-tdwidth fr-tabcont tabcont-item">
						<table>
							<tr>
								<td>收货人:${orderHead.address.receiverName}&nbsp;&nbsp;&nbsp;</td>
								<td>手机:${orderHead.address.mobile}&nbsp;&nbsp;&nbsp;</td>
								<td>电话:${orderHead.address.telephone}&nbsp;&nbsp;&nbsp;</td>
								<td>邮编:${orderHead.address.postCode}</td>
							</tr>
							<tr>
								<td colspan="4">${orderHead.address.region.province}, ${orderHead.address.region.city},${orderHead.address.region.county} ${orderHead.address.receiveAddress}</td>
							</tr>
						</table>
	        		</div>
					<div class="order-tdwidth fr-tabcont tabcont-item m-hide">
	              <!-- 如果订单是未发货  显示发货信息 -->
	              <c:if test="${orderHead.status == orderStatusWaitSend}">
	              <table width="100%">
	                <tr>
	                    <td width="30%">
		                   	<label>发货：</label>
		                   	<select class="select-mail" id="expressId${orderHead.orderId}">
								<c:forEach items="${listExpresses }" var="express" varStatus="status">
									<option value="${express.expressId }">${express.expressName}</option>
								</c:forEach>
							</select>
	                    </td>
	                    <td width="40%"><form id="form${orderHead.orderId}"><div class="position"><input id="expressNo${orderHead.orderId}" type="text" class="odd-numbers" /><div id="expressNo${orderHead.orderId}Tip"></div></div></form></td>
	                	<td width="20%">
	                	<aut:authorize url="/admin/order/order-confirmSendProduct">
	                		<input onclick="confirmSend(${orderHead.orderId})" type="button" class="confirm-btn" value="确认发货" />
	                	</aut:authorize>
	                	</td>
	                </tr>
	            </table>
	            </c:if>
	            <!-- 如果已经发货 显示物流信息 -->
	            <c:if test="${orderHead.status != orderStatusWaitSend }">
            	订单状态:<spring:message code="order.status.${orderHead.status}" />
	           	<c:if test="${orderHead.orderExpress.expressName!='' &&  orderHead.orderExpress.expressName!=null}">
					物流公司:${orderHead.orderExpress.expressName}
				</c:if>
				<c:if test="${orderHead.orderExpress.expressName=='' ||  orderHead.orderExpress.expressName==null}">
					物流公司:${orderHead.orderExpress.express.expressName}
				</c:if>
				<c:if test="${orderHead.orderExpress.mailNo!='' ||  orderHead.orderExpress.mailNo!=null}">
					面单号:${orderHead.orderExpress.mailNo}
				</c:if>
	           	<!-- 运单信息 -->
				<c:if test="${orderHead.expressMessage != null}">
		            <c:set var="status_success" value="<%=ExpressMessage.STATUS_SUCCESS%>"></c:set>
		            <c:choose>
		             	<c:when test="${orderHead.expressMessage.status eq status_success }">
		            	<ul>
							<c:forEach items="${orderHead.expressMessage.data }" var="dataItem">
							<li>
								<label>${dataItem.time }</label>
								<span>${dataItem.context }</span>
							</li>
							</c:forEach>
			            </ul>
		            	</c:when>
			            <c:otherwise><p>系统正在更新，给您带来不便请见谅，请稍后查询！</p></c:otherwise>
		            </c:choose>
	            </c:if>
	           </c:if>
          </div>
		</div>
		</div>
			<div class="order-tdwidth order-shops ">
			<c:if test="${orderReturns != null}">
				<div class="return-t">退货商品：</div>
				<c:forEach items="${orderReturns}" var="item" varStatus="status">
				<div class="return-product">
					<div class="return-sumary">
						<b>申请日期：</b>
						<span><fmt:formatDate value="${item.createTime}" type="both"/></span>
						<b>联系人：</b>
						<span>${item.customerName}</span>
						<b>联系电话：</b>
						<span>${item.telephone}</span>
						<c:if test="${not empty item.express }">
						<b>${item.express.expressName}：</b>
						<span>${item.express.mailNo}</span>
						</c:if>
						<b>应退金额：</b>
						<span>${item.refundAmount}</span>
					</div>
					<table width="100%">
						<c:forEach items="${item.returnItems}" var="rItem">
						<tr>
							<td width="80">
								<img src="<zx:thumbImage originalPath='${rItem.orderItem.productSku.product.defaultImage}' imageType='t16'/>" />
							</td>
							<td align="left">
								<p> ${rItem.orderItem.productName}</p> 
								<p>
									<!-- 商品sku信息 -->
									<c:forEach items="${rItem.orderItem.productSku.productSkuOptionValues}" var="option">
										${option.skuOptionValue.skuOption.skuOptionName}:
										<span>
										<c:choose>
											<c:when test="${not empty option.overrideSkuOptionValue}">
											 ${option.overrideSkuOptionValue}&nbsp;
											</c:when>
											<c:otherwise>
												${option.skuOptionValue.skuOptionValue}&nbsp;
											</c:otherwise>
										</c:choose>
										</span>
									</c:forEach>
								</p>
								<p>问题描述:${rItem.describe}</p>
							</td>
							<td width="60">
								<span>${rItem.num}</span><br/>
								<span><span class="coin">&yen;</span> ${rItem.orderItem.closingCost}</span>
							</td>
							<td width="280" class="buyer-upload">
								<c:forEach items="${rItem.imageList}" var="image">
									<img src="<zx:thumbImage originalPath='${image}' imageType='t16'/>" />
								</c:forEach>
							</td>
							<td width="140" align="center">
								<!-- 订单项状态是未审核 -->
								<c:if test="${rItem.status == orderReturnItemStatusNotAudit}">
								  <aut:authorize url="/admin/order/order-passAudit">
									<p><input type="button" class="m-btn" value="审核通过" id="passAudit${rItem.orderReturnItemId}" onclick="passAudit(${rItem.orderReturnItemId})" /></p>
								  </aut:authorize>
								  <aut:authorize url="/admin/order/order-rejectAudit">
									<p><input type="button" class="m-btn" value="审核不通过" id="rejectAudit${rItem.orderReturnItemId}" onclick="rejectAudit(${rItem.orderReturnItemId})" /></p>
								  </aut:authorize>
								</c:if>
								<!-- 订单项状态为 审核通过  -->
								<aut:authorize url="/admin/order/order-agreePayment">
									<c:if test="${rItem.status == orderReturnItemStatusAccept}">
										<p><input type="button" class="m-btn" value="同意退款" id="agreePayment${rItem.orderReturnItemId}" onclick="agreePayment(${rItem.orderReturnItemId})" /></p>
										<b>温馨提示：</b>请确认收到退货商品后，再点击此按钮
									</c:if>
								</aut:authorize>
								<!-- 订单项状态为 审核未通过 -->
								<c:if test="${rItem.status == orderReturnItemStatusRefuseed}">
									<p>审核未通过</p>
								</c:if>
								<!-- 已经收货  且退款订单状态 未付款 -->
								<c:if test="${item.orderReturnPayment.status != alreadyRefund && rItem.status == orderReturnItemStatusTakeGoods}">
									<p>等待退款</p>
								</c:if>
								<c:if test="${item.orderReturnPayment.status == alreadyRefund}">
									<p>已完成</p>
								</c:if> 
							</td>
						</tr>
						</c:forEach>
					</table>
				</div>
				</c:forEach>
				</c:if>
			</div>
		</div>
	</td>
</tr>
<script type="text/javascript">
	//审核通过
	function passAudit(id){
		$.ajax({
			type:'post',
			data:{'orderReturnItem.orderReturnItemId':id},
			url:'${_ctxPath}/admin/order/order-passAudit.htm',
			success:function(data){
				//success 改变按钮 变成  同意退款 并添加注释  同时移除  审核通过  和  审核不通过
				$id = $("#passAudit"+id);
				$id.parent().siblings().remove();
				$id.parent().parent().append('<p><input type="button" class="btn btn-danger" value="同意退款" id="agreePayment'+id+'" onclick="agreePayment('+id+')"  /><p>');
				$id.parent().remove();
			},
			error:function(data){
				//error
			}
		});
	}
	//审核不通过
	function rejectAudit(id,obj){
		$id = $("#rejectAudit"+id);
		$txt = $id.parent().parent();
		//反之重复出现
		if(! $id.parent().next("p")[0]){
			$txt.append('<p><span id="showRejectErr'+id+'" style="display:none;color:#B22222;">请填写不通过原因</span><textarea id="rejectAnditTxt'+id+'"></textarea><input onclick="rejectAuditOk('+id+')" id="rejectAnditOk'+id+'" type="button" class="btn btn-danger" value="提交"/></P>');
		}
		return;
	}
	//审核不通过  提交
	function rejectAuditOk(id){
		if($("#rejectAnditTxt"+id).val()=='')
		{
			$("#showRejectErr"+id).css("display","");
			$("#rejectAnditTxt"+id).focus();
			return;
		}
		$.ajax({
			type:'post',
			data:{'orderReturnItem.orderReturnItemId':id,'orderReturnItem.noPassReason':$("#rejectAnditTxt"+id).val()},
			url:'${_ctxPath}/admin/order/order-rejectAudit.htm',
			success:function(data){
				//success 改变按钮 变成  审核不通过
				$id.parent().siblings().remove();
				$id.parent().parent().append('<p>审核不通过<p>');
				$id.parent().remove();
			},
			error:function(data){
				//error
			}
		});
	}
	//同意退款
	function agreePayment(id){
		$.ajax({
			type:'post',
			data:{'orderReturnItem.orderReturnItemId':id},
			url:'${_ctxPath}/admin/order/order-agreePayment.htm',
			success:function(data){
				//success 改变按钮 变成  审核不通过
				$id = $("#agreePayment"+id);
				$parent = $id.parent().parent();
				$parent.empty();
				$parent.append('<p>等待退款<p>');
			},
			error:function(data){
				//error
			}
		});
	}
	//确认发货
	function confirmSend(id){
		var num = id.toString();
		var result = $.formValidator.pageIsValid(num); // 组号必须用带引号
	    if (!result) {
	        return false;   // 验证不通过,直接返回
	    }else{
	    	var expressName = $("#expressId"+id+" option:selected").text();
			var expressId = $("#expressId"+id+" option:selected").val();
            var expressMailNo = $("#expressNo"+id).val();
            if('请输入快递单号'==expressMailNo){
            	alert("请输入快递单号");
            	return;
            }
            //alert(expressId+"-"+expressName+"-"+expressMailNo);
           //return;
            $.ajax({
					type : 'POST',
					url : '${_ctxPath}/admin/order/order-confirmSendProduct.htm',
					data : {'orderExpress.expressId':expressId,
							'orderExpress.expressName' : expressName,
							'orderExpress.mailNo' :expressMailNo,
							'orderExpress.orderId': id
							},
					success : function(data) {
						if(data.code=='true'){
							var content='<label>订单状态：</label><span>已发货</span>\t'+'<label>物流公司：</label><span>'+expressName+'</span>\t'+'<label>运单号码：</label><span>'+expressMailNo+'</span>'
							$tr = $("#expressNo"+id).closest("tr");
							$tr.empty();
							$tr.html(content);
						}else{
							alert("运单号重复!");
						}
					}
			});
		}
   }
	
$(function () {
	$(".tab-control").on("mouseenter",".show-tr",function(){
		$(".odd-numbers").each(function(i){
			var Code = $(this).attr("id"),
			sendCode = Code.substr(9,99);
	    $.formValidator.initConfig({
	        "validatorGroup":sendCode, // 页面有多个验证的时候需要添加验证组号 组号必须用带引号
	        "formID":'form1'
	    });
	  	$(this).formValidator({
	        "validatorGroup": sendCode,// 组号必须用带引号
	        "onShow": '',
	        "onFocus": '请输入快递单号',
	        "onCorrect": ''
	    }).inputValidator({
	        "min":1,  // 最小长度
	        "max":30,// 最大长度
	        "onErrorMin":"请输入快递单号",
	        "onError":'请输入1-30位快递单号'
	      }).regexValidator({
	      	regExp:"username",
	      	dataType:"enum",
	      	onError:"快递单号错误"
	    });
	  });
	});
    
});
	</script>