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
						<ul class="layout">
						<!-- 订单所购的商品 -->
						<c:forEach items="${orderHead.items}" var="item">
							<li>
								<table>
									<tr>
										<td rowspan="2" width="70px"><img src="<zx:thumbImage originalPath='${item.productSku.product.defaultImage}' imageType='t16'/>" border=""/></td>
										<td width="15%">${item.productSku.product.brand.name}</td>
										<td width="42%">${item.productName}</td>
										<td width="15%"><span class="coin">&yen;</span><label>${item.closingCost}</label></td>
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
								<td width="23%"><span class="coin">&yen;</span><label>${orderHead.payment.closingCostAmount - orderHead.orderPostage.postage }</label></td>
								<td width="10%">运费：</td>
								<td width="15%"><span class="coin">&yen;</span><label>${orderHead.orderPostage.postage }</label></td>
								<%-- <td width="30%">总金额：<span class="coin">&yen;</span>${orderHead.payment.paymentAmount}</td>--%>
								<td width="*">
									优惠抵扣：<span class="coin">&yen;</span>${orderHead.payment.closingCostAmount - orderHead.payment.paymentAmount} <br/>
									支付总额：<span class="coin">&yen;</span>${orderHead.payment.paymentAmount}
								</td>
							</tr>
							<c:if test="${orderHead.payment.hasInvoice == 1}">
								<tr>
									<td width="15%">发票信息：</td>
									<td colspan="4">
										<label>
											<c:if test="${empty orderHead.payment.invoiceTitle}">个人</c:if>
											<c:if test="${not empty orderHead.payment.invoiceTitle}">${orderHead.payment.invoiceTitle}</c:if>
										</label>
									</td>
									<%--<td width="20%">
										<span class="c-red"> <span class="coin">&yen;</span>${orderHead.payment.paymentAmount} </span>
									</td>--%>
								</tr>
							</c:if>
							<tr>
								<td colspan="2">支付宝帐号：</td>
								<td colspan="3">
									${orderHead.payment.account}
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="m-fr f-wr">
					<div class="fr-tab">
						<ul class="m-clear">
							<li class="bot-no">商家信息</li>
							<li>物流状态</li>
						</ul>
					</div>
					<div class="order-tdwidth fr-tabcont tabcont-item">
						<table>
						<c:forEach items="${orderHead.items}" var="item" begin="0" end="0">
							<tr>
								<td>发货人:${item.seller.contactName}&nbsp;&nbsp;&nbsp;</td>
								<td>手机:${item.seller.mobile}&nbsp;&nbsp;&nbsp;</td>
								<td>邮箱:${item.seller.email}&nbsp;&nbsp;&nbsp;</td>
							</tr>
							<tr>
							<td colspan="4">
								${item.seller.companyAddress}
							</td>
							</tr>
						</c:forEach>
						</table>
	        		</div>
		  <div class="order-tdwidth fr-tabcont tabcont-item m-hide">
<%-- 		   <c:if test="${orderHead.status == orderStatusSend || orderHead.status == orderStatusFinished}"> --%>
	  		订单状态:<spring:message code="order.status.${orderHead.status}"/>
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
			<c:if test="${orderHead.expressMessage!=null}">
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
          </div>
		</div>
			</div>
			<div class="order-tdwidth order-shops ">
				<h4>会员信息</h4>
				<table width="100%">
					<tr valign="top">
						<td width="20%"><label>登录帐号：</label>${user.user.username}</td>
						<td width="15%"><label>姓名：</label>${user.name}</td>
						<td width="20%"><label>生日：</label><fmt:formatDate value="${user.birthday}" type="date"/></td>
						<td width="25%"><label>最后登录时间：</label><fmt:formatDate value="${user.user.lastLoginTime}" type="both"/></td>
						<td width="20%"><label>当前积分：</label>${user.point.total}</td>
					</tr>
					<tr valign="top">
						<td><label>注册时间：</label><fmt:formatDate value="${user.user.createTime}" type="both"/></td>
						<td><label>电话：</label>${user.telephone}</td>
						<td><label>邮箱：</label>${user.user.email}</td>
						<td><label>所在地：</label>${user.region.province},${user.region.city}, ${user.region.county}</td>
						<td><label>地址：</label>${user.address}</td>
					</tr>
				</table>
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
					<table>
						<tr>
							<td width="780">
								<div class="return-l">
									<table>
									<c:forEach items="${item.returnItems}" var="rItem">
										<tr>
											<td width="80">
												<img src="<zx:thumbImage originalPath='${rItem.orderItem.productSku.product.defaultImage}' imageType='t16'/>" />
											</td>
											<td width="180" align="left">
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
											<td width="280" class="buyer-upload" align="left">
												<c:forEach items="${rItem.imageList}" var="image">
													<img src="<zx:thumbImage originalPath='${image}' imageType='t16'/>" />
												</c:forEach>
											</td>
											<td width="180" align="left">
												<div class="div-line"><b>退货审核状态：</b><span><spring:message code="order.return.status.${rItem.status}" /></span></div>
												<div class="div-line"><b>审核日期：</b>
												<span>
													<!-- 状态等于 审核未通过  不显示审核时间 -->
													<c:if test="${rItem.status != orderReturnItemStatusNotAudit}">
														<fmt:formatDate value="${rItem.updateTime}" pattern="yyyy-MM-dd"/><br/>
														<fmt:formatDate value="${rItem.updateTime}" pattern="HH:mm:ss"/>
													</c:if>
												</span>
												</div>
											</td>
										</tr>
									</c:forEach>
									</table>
								</div>
							</td>
							<td valign="middle" align="center">
								<!-- 当退货订单 退款状态是已经退款 -->
								<c:if test="${item.orderReturnPayment.status == alreadyRefund}">
									已完成
								</c:if>
								<c:if test="${item.orderReturnPayment.status != alreadyRefund}">
									<c:if test="${item.returnDisplay}">
										<aut:authorize url="/admin/order/order-confirmPayment">
										<input type="button" class="m-btn confirmPayment" value="确认退款" id="confirmPayment${item.orderReturnPayment.orderReturnPaymentId}" onclick="confirmPayment(${item.orderReturnPayment.orderReturnPaymentId})" />
										</aut:authorize>
										<!-- 传orderReturnPaymentId-->
									</c:if>
									<c:if test="${!item.returnDisplay}">
										退货订单中部分审核或者卖家没有收到货
									</c:if>
								</c:if>
							</td>
						</tr>
					</table>
				</div>
				</c:forEach>
			</c:if>
			</div>
		</div>
	</td>
</tr>
<script type="text/javascript">
	//确认退款
	function confirmPayment(id){
		$.ajax({
			type:'post',
			data:{'orderReturnPayment.orderReturnPaymentId':id},
			url:'${_ctxPath}/admin/order/order-confirmPayment.htm',
			success:function(data){
				$id = $("#confirmPayment"+id);
				var $a = $id.closest(".show-tr").prev("tr").find(".c-blue");
				$id.parent().append("已完成");
				$id.remove();
				//移除"退款"操作
				if($(".confirmPayment").length == 0){
					$a.remove();	
				}
			},
			error:function(data){
				//error
			}
		});
	}
</script>