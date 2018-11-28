<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<tr class="show-tr">
	<td colspan="10" class="show-tab">
		<div class="order-info" style="display: block;">
			<div class="layout">
				<div class="m-fl f-wl">
					<div class="order-botcolor order-tdwidth">
						<h4>订单详情</h4>
						<ul class="layout">
							<c:forEach items="${orderHead.items}" var="item">
								<li>
									<table>
										<tbody>
											<tr>
												<td rowspan="2" width="70px"><img
													src="<zx:thumbImage originalPath='${item.productSku.product.defaultImage}' imageType='t16'></zx:thumbImage>" border="">
												</td>
												<td width="15%">品牌</td>
												<td width="42%">${item.productName}</td>
												<td width="15%"><span class="price">&yen;<label>${item.closingCost}</label></span>
												</td>
												<td width="10%">X${item.num}</td>
											</tr>
											<tr>
												<td colspan="4"><c:forEach
														items="${item.productSku.productSkuOptionValues}"
														var="option">
														<label>${option.skuOptionValue.skuOption.skuOptionName}:
															<c:choose>
																<c:when
																	test="${not empty option.overrideSkuOptionValue}">
																			${option.overrideSkuOptionValue}&nbsp;&nbsp;
														
														
														
														</label>
														</c:when>
														<c:otherwise>
																			${option.skuOptionValue.skuOptionValue}&nbsp;&nbsp;</label>
														</c:otherwise>
														</c:choose>
													</c:forEach>
												</td>
											</tr>
										</tbody>
									</table>
								</li>
							</c:forEach>
						</ul>
					</div>
					<div class="order-botcolor order-tdwidth">
						<table>
							<tbody>
								<tr>
									<td width="20%">商品金额：</td>
									<td width="25%"><span class="price">&yen;<label>${orderHead.payment.paymentAmount}</label></span>
									</td>
									<td width="15%">运费：</td>
									<td width="25%"><span class="price">&yen;<label>0.00</label></span></td>
									<td width="20%">总金额：</td>
								</tr>
								<tr>
									<td width="20%">发票信息：</td>
									<td colspan="3"><c:if
											test="${orderHead.payment.hasInvoice == 1}">
											<label>${orderHead.payment.invoiceTitle}</label>
										</c:if>
									</td>
									<td width="20%"><span class="c-red price">&yen;${orderHead.payment.paymentAmount}</span>


									
									</td>

								</tr>
								<tr>
									<td width="20%">支付宝帐号：</td>
									<td colspan="4">${orderHead.payment.account}</td>

								</tr>
							</tbody>
						</table>
					</div>
					<div class="order-tdwidth">
					<h4>收货信息</h4>
						<table>
							<tbody>
								<tr>
									<td width="20%">${orderHead.address.receiverName}</td>
									<td width="25%"><label>${orderHead.address.mobile}</label>
									</td>
									<td width="25%"><label>${orderHead.address.telephone}</label>
									</td>
									<td width="25%"><label>${orderHead.address.postCode}</label>
									</td>
								</tr>
								<tr>
									<td colspan="2"><label>${orderHead.address.region.province}
											， ${orderHead.address.region.city} ，
											${orderHead.address.region.county} </label></td>
									<td colspan="2"><label>${orderHead.address.receiveAddress}</label>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="m-fr f-wr tab-change">
					<div class="fr-tab">
						<ul class="layout">
							<li class="bot-no">物流详情</li>
							<c:if test="${orderReturn.items!=null }">
								<li class=""><c:if test="${isAdmin }">
									退款确认
								</c:if> <c:if test="${!isAdmin }">
									退货审核
								</c:if>
								</li>
							</c:if>
						</ul>

					</div>
					<div class="order-tdwidth fr-tabcont tabcont-item">
						<table>
							<tbody>
								<c:if test="${isAdmin }">
									<tr>
										<td width="30%"><label>包裹：</label><span><spring:message
													code="order.status.${orderHead.status}" text="" /> </span></td>
										<td width="30%"><label>物流公司：</label><span>
											<c:if test="${orderHead.orderExpress.expressName!='' &&  orderHead.orderExpress.expressName!=null}">
												${orderHead.orderExpress.expressName}
											</c:if>
											<c:if test="${orderHead.orderExpress.expressName=='' ||  orderHead.orderExpress.expressName==null}">
												${orderHead.orderExpress.express.expressName}
											</c:if>
										</span>
										</td>
										<td width="30%"><label>运单号码：</label><span>${orderHead.orderExpress.mailNo}</span>
										</td>
									</tr>
								</c:if>

								<c:if test="${!isAdmin }">
									<c:if
										test="${(orderHead.orderExpress.expressName!='' &&  orderHead.orderExpress.expressName!=null) || (orderHead.orderExpress.express.expressName !='' &&orderHead.orderExpress.express.expressName !=null)}">
										<tr>
											<td width="30%"><label>物流公司：</label><span>
												<c:if test="${orderHead.orderExpress.expressName!='' &&  orderHead.orderExpress.expressName!=null}">
													${orderHead.orderExpress.expressName}
												</c:if>
												<c:if test="${orderHead.orderExpress.expressName=='' ||  orderHead.orderExpress.expressName==null}">
													${orderHead.orderExpress.express.expressName}
												</c:if>
											</span>
											</td>
											<td width="30%"><label>运单号码：</label><span>${orderHead.orderExpress.mailNo}</span>
											</td>
										</tr>
									</c:if>

									<c:if
										test="${(orderHead.orderExpress.expressName=='' ||  orderHead.orderExpress.expressName==null) && (orderHead.orderExpress.express.expressName =='' || orderHead.orderExpress.express.expressName ==null)}">
										<tr id="expressDisplay${orderHead.orderId}">
											<td width="30%"><label>物流公司：</label>
												<select
												class="select-mail" id="expressId${orderHead.orderId}">
													<c:forEach items="${listExpresses }" var="express"
														varStatus="status">
														<option value="${express.expressId }">${express.expressName}</option>
													</c:forEach>
												</select>
											</td>
											<td width="30%"><input type="text" class="odd-numbers"
												value="请输入快递单号" onfocus="if(this.value=='请输入快递单号'){this.value=''}" onblur="if(this.value==''){this.value='请输入快递单号'}" id="expressNo${orderHead.orderId}" />
											</td>
											<td width="30%"><input type="button" class="confirm-btn"
												value="确认发货" id="confirmSend${orderHead.orderId}" />
												<script type="text/javascript">
	                      					$('#confirmSend${orderHead.orderId}').bind('click',function(){
	                      						var expressName=$('#expressId${orderHead.orderId} option:selected').text();
	                      						var expressMailNo=$('#expressNo${orderHead.orderId}').val();
	                      						$.ajax({
	                      							type : 'POST',
	                      							url : '${_ctxPath}/admin/order/order-confirmSendProduct.htm',
	                      							data : {"orderExpress.expressId" : $('#expressId${orderHead.orderId}').val(),
	                      								'orderExpress.mailNo' :$('#expressNo${orderHead.orderId}').val(),
	                      								'orderExpress.orderId': '${orderHead.orderId}'
	                      									},
	                      							success : function(data) {
	                      								alert(data.info);
	                      								if(data.code=='true'){
		                      								var content='<td width="30%"><label>物流公司：</label><span>'+expressName+'</span>'
															+'</td>'
															+'<td width="30%"><label>运单号码：</label><span>'+expressMailNo+'</span>'
															+'</td>'
															$('#expressDisplay${orderHead.orderId}').html($(content));
															$('#sendButton_${orderHead.orderId}').remove();
															$('#orderStatus_${orderHead.orderId}').text(data.infoValues);
	                      								}
	                      								
	                      							}
	                      						});
	                      					});
	                      				</script>
											</td>
										</tr>
									</c:if>

								</c:if>

							</tbody>
						</table>
						<c:if test="${orderHead.detailExpress!=null}">
							<ul>
								<c:forEach items="${orderHead.detailExpress['steps'] }" var="step" varStatus="status">
									<li><label><fmt:formatDate value="${step['acceptTime']}" pattern='yyyy-MM-dd HH:mm:ss'/></label>
										<span>${step['acceptAddress']} 
											  <c:if test="${step['name']!=''}">[${step['name']}]</c:if>
											  [${step['remark']}]
										</span>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</div>
					<c:if test="${orderReturn.items!=null }">
						<div class="order-tdwidth fr-tabcont tabcont-item hide">
							<table>
								<tbody>
									<tr>
										<td colspan="6"><b>退货商品</b></td>
									</tr>
									<c:set var="totalPrice" value="0"></c:set>
									<c:forEach items="${orderReturn.items }" var="item"
										varStatus="status">
										<tr>
											<td colspan="6">${item.brand.name} ${item.productName }<c:forEach
													items="${item.productSku.productSkuOptionValues}"
													var="option">
													<label>${option.skuOptionValue.skuOption.skuOptionName}:<c:choose>
															<c:when test="${not empty option.overrideSkuOptionValue}">${option.overrideSkuOptionValue}&nbsp;&nbsp;
													
													
													</label>
													</c:when>
													<c:otherwise>${option.skuOptionValue.skuOptionValue}&nbsp;&nbsp;</label>
													</c:otherwise>
													</c:choose>
												</c:forEach>X${item.num}</td>
											<c:set var="totalPrice"
												value="${totalPrice+ item.num*item.closingCost}"></c:set>
										</tr>
									</c:forEach>
									<c:if test="${isAdmin }">
										<tr>
											<td align="left"><b>应退金额：</b></td>
											<td width="90"><span class="price">&yen;${totalPrice }</span></td>
											<td align="right"><b>申请日期：</b></td>
											<td><fmt:formatDate value='${orderReturn.createTime}'
													pattern='yyyy-MM-dd HH:mm:ss' /></td>
											<td align="right"><b>订单状态：</b></td>
											<td><spring:message
													code="order.return.status.${orderReturn.status }" /></td>
										</tr>
										<tr valign="top">
											<td align="left"><b>问题描述：</b></td>
											<td colspan="5">${orderReturn.describe }</td>
										</tr>
										<c:if test="${orderReturn.orderPayment.status==paymentStatusArrived }">
											<script type="text/javascript">
												$('#confirmReturnPayment${orderReturn.orderPayment.orderPaymentId}').bind('click',function(){
													$.ajax({
		                      							type : 'POST',
		                      							url : '${_ctxPath}/admin/order/order-confirmPayment.htm',
		                      							data : {"orderPayment.orderPaymentId" : ${orderReturn.orderPayment.orderPaymentId}
		                      									},
		                      							success : function(data) {
		                      								alert(data.info);
		                      								if(data.code=='true'){
			                      								$('#confirmReturnPayment${orderReturn.orderPayment.orderPaymentId}').remove();
			                      								$('#returnPayment_${orderHead.orderId}').remove();
			                      								$('#payment_${orderHead.orderId}').text(data.infoValues);
		                      								}
		                      							}
		                      						});
												});
											</script>
											<tr valign="top">
												<td colspan="6"><input type="button"
													class="examine-btn" value="确认退款"
													id="confirmReturnPayment${orderReturn.orderPayment.orderPaymentId }">
												</td>
											</tr>
										</c:if>
									</c:if>

									<c:if test="${!isAdmin}">
										<tr valign="top">
											<td align="right">问题描述：</td>
											<td colspan="5"><textarea class="problem-text" disabled="disabled">${orderReturn.describe }</textarea>
											</td>
										</tr>
										<tr>
											<td align="right">应退金额：</td>
											<td width="90"><span class="price">&yen;${totalPrice }</span></td>
											<td align="right">联系人：</td>
											<td>${orderReturn.customerName }</td>
											<td align="right">联系电话：</td>
											<td>${orderReturn.telephone}</td>
										</tr>
										<tr>
											<td align="right">申请日期：</td>
											<td colspan="2"><fmt:formatDate
													value='${orderReturn.createTime}'
													pattern='yyyy-MM-dd HH:mm:ss' />
											</td>
											<td>圆通速递</td>
											<td colspan="2">1827907742</td>
										</tr>
										<c:if test="${orderReturn.status!=orderReturnStatusAccept }">
											<tr>
												<c:if
													test="${orderReturn.status==orderReturnStatusNotAudit }">
													<td colspan="6"
														id="noPassReason_${orderReturn.orderReturnId}"><textarea
															class="refuse-text"
															id="noPassReason${orderReturn.orderReturnId}">${orderReturn.noPassReason }</textarea>
													</td>
												</c:if>
												<c:if
													test="${orderReturn.status!=orderReturnStatusNotAudit }">
													<td colspan="6">${orderReturn.noPassReason }</td>
												</c:if>
											</tr>
										</c:if>
										<tr>
											<c:if
												test="${orderReturn.status==orderReturnStatusNotAudit }">
												<td colspan="4"
													id="returnOrderButton${orderReturn.orderReturnId}"><input
													type="button" class="examine-btn" value="审核通过"
													id="passReturn${orderReturn.orderReturnId}"> <input
													type="button" class="examine-btn" value="审核不通过"
													id="rejectReturn${orderReturn.orderReturnId}"></td>
												<script type="text/javascript">
												$('#passReturn${orderReturn.orderReturnId}').bind('click',function(){
													$.ajax({
		                      							type : 'POST',
		                      							url : '${_ctxPath}/admin/order/order-passAudit.htm',
		                      							data : {"orderReturn.orderReturnId" : ${orderReturn.orderReturnId},
		                      									"orderReturn.orderId":${orderReturn.orderId}
		                      									},
		                      							success : function(data) {
		                      								alert(data.info);
		                      								if(data.code=='true'){
			                      								var content='<input type="button" class="examine-btn"'+
			        												'value="同意退款" id="agreePayment_${orderReturn.orderReturnId}" >温馨提示：请确认收到退货商品后，在点击此按钮。'
																$('#returnOrderButton${orderReturn.orderReturnId}').html(content);
		        												$('#tipReasion_${orderReturn.orderReturnId}').hide();
		        												$('#noPassReason_${orderReturn.orderReturnId}').parent().remove();
		        												$('#sendButton_${orderHead.orderId}').remove();
																$('#orderStatus_${orderHead.orderId}').text(data.infoValues);
																
																$('#agreePayment_${orderReturn.orderReturnId}').bind('click',function(orderPaymentId){
																	$.ajax({
						                      							type : 'POST',
						                      							url : '${_ctxPath}/admin/order/order-agreePayment.htm',
																		data : {"orderPayment.orderPaymentId" : ${orderReturn.orderPayment.orderPaymentId}
							                      									},
						                      							success : function(data) {
						                      								alert(data.info);
						                      								if(data.code=='true'){
						                      									$('#agreePayment_${orderReturn.orderReturnId}').parent().text('等待新龙管理员退款');
						                      									$('#payment_${orderHead.orderId}').text(data.infoValues);
						                      								}
						                      							}
						                      						});
																});
		                      								}
		                      							}
		                      						});
												});
												
												$('#rejectReturn${orderReturn.orderReturnId}').bind('click',function(){
													var content=$('#noPassReason${orderReturn.orderReturnId}').val();
													if(content==''){
														alert('请填写不通过原因');
														return ;
													}
													$.ajax({
		                      							type : 'POST',
		                      							url : '${_ctxPath}/admin/order/order-rejectAudit.htm',
		                      							data : {"orderReturn.orderReturnId" : ${orderReturn.orderReturnId},
		                      									"orderReturn.noPassReason" :$('#noPassReason${orderReturn.orderReturnId}').val(),
		                      									"orderReturn.orderId":${orderReturn.orderId}
		                      									},
		                      							success : function(data) {
		                      								alert(data.info);
		                      								if(data.code=='true'){
		        												$('#rejectReturn${orderReturn.orderReturnId}').parent().remove();
		        												$('#noPassReason${orderReturn.orderReturnId}').remove();
																$('#orderStatus_${orderHead.orderId}').text(data.infoValues);
																$('#sendButton_${orderHead.orderId}').remove();
																$('#tipReasion_${orderReturn.orderReturnId}').hide();
																$('#noPassReason_${orderReturn.orderReturnId}').text(content);
		                      								}
															
		                      							}
		                      						});
												});
											</script>
												<td colspan="2" id="tipReasion_${orderReturn.orderReturnId}"><span class="c-red">请填写不通过原因</span></td>
											</c:if>
											
											<c:if
												test="${orderReturn.status==orderReturnStatusAccept && orderReturn.orderPayment.status==paymentStatusPayed}">
												<script type="text/javascript">
											$('#agreePayment_${orderReturn.orderReturnId}').bind('click',function(orderPaymentId){
												$.ajax({
	                      							type : 'POST',
	                      							url : '${_ctxPath}/admin/order/order-agreePayment.htm',
													data : {"orderPayment.orderPaymentId" : ${orderReturn.orderPayment.orderPaymentId}
		                      									},
	                      							success : function(data) {
	                      								alert(data.info);
	                      								if(data.code=='true'){
	                      									$('#agreePayment_${orderReturn.orderReturnId}').parent().text('等待新龙管理员退款');
	                      									$('#payment_${orderHead.orderId}').text(data.infoValues);
	                      								}
	                      							}
	                      						});
											});
										</script>
												<td colspan="4"><input type="button"
													class="examine-btn" value="同意退款" id="agreePayment_${orderReturn.orderReturnId}"
													>温馨提示：请确认收到退货商品后，在点击此按钮。
												</td>
											</c:if>
											<c:if
												test="${orderReturn.orderPayment.status==paymentStatusArrived}">
												<td colspan="4">等待新龙管理员退款</td>
											</c:if>
											<c:if
												test="${orderReturn.orderPayment.status==paymentStatusRefund}">
												<td colspan="4">退货完成</td>
											</c:if>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</c:if>
				</div>
			</div>
			<c:if test="${isAdmin}">
				<div class="order-tdwidth order-shops ">
					<h4>买家信息</h4>
					<table>
						<tbody>
							<tr>
								<td width="20%"><label>登录帐号：</label>${user.user.username}</td>
								<td width="25%"><label>姓名：</label>${user.name}</td>
								<td width="15%"><label>&nbsp;&nbsp;生日：</label> <fmt:formatDate
										value="${user.birthday}" type="date" /></td>
								<td width="25%"><label>最后登录时间：</label>${user.user.lastLoginTime}</td>
								<td width="15%"><label>当前积分：</label>${user.point.total}</td>
							</tr>
							<tr>
								<td width="20%"><label>注册时间：</label>${user.user.createTime}</td>
								<td width="25%"><label>&nbsp;&nbsp;&nbsp;&nbsp;电话：</label>${user.telephone}</td>
								<td width="15%"><label>邮箱：</label>${user.user.email}</td>
								<td width="25%"><label>所在地：</label>${user.region.province} ，
									${user.region.city} ， ${user.region.county}</td>
								<td width="15%"><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地址：</label>${user.address}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
	</td>
</tr>