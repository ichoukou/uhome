<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单管理</title>
	<link href="${ _cssPath}/common.css" rel="stylesheet" />
	<link href="${ _cssPath}/pages/orderManagement.css" rel="stylesheet" />
	<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
	<script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
	<script src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js" language="javascript"></script>
	<script src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js" language="javascript"></script>
	<script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="${_jsPath}/plugin/uploadify/swfobject.js"></script>
	<link href="${_jsPath }/plugin/formvalidator/themes/Default/style/style.css" rel="stylesheet" />
<script type="text/javascript">
	$(function() {
		$(".returnStatus").bind("click",function(){
			$("#returnStatus").val($(this).attr("id"));
			$('#search').click();
		});
		$('#status_con li').bind('click', function() {
			if ($(this).val() == 0) {
				$('#status').val('');
			} else {
				$('#status').val($(this).val());
			}
			$('#search').click();
		});
		$('#search').bind('click', function() {
			var keywords = $(".J-keywords").val();
			if(keywords == $(".J-keywords").attr("data-default")){
				$(".J-keywords").val("");
			}
			$('#subform').submit();
		});
		$('#export').bind('click', function() {
			if($.trim($('tbody').html())==''){
				return;
			}
			var keywords = $(".J-keywords").val();
			if(keywords == $(".J-keywords").attr("data-default")){
				$(".J-keywords").val("");
			}
			<%--
			var url = '${_ctxPath}/admin/order/order-exportOrder.htm'
			if ($('#status').val() != '' && $('#status').val() != null) {
				url = url + '?orderPage.params.status=' + $('#status').val();
			}
			window.location.href = url;
			--%>
			$('#subform').attr("action","${_ctxPath}/admin/order/order-exportSellerOrders.htm");
			$('#subform').submit();
			$('#subform').attr("action","${_ctxPath}/admin/order/order-searchOrders4Seller.htm");
		});
	});

	function returnOrder(){
		if($('tbody').html().trim()==''){
			return;
		}
		var keywords = $(".J-keywords").val();
		if(keywords == $(".J-keywords").attr("data-default")){
			$(".J-keywords").val("");
		}
		$('#subform').attr("action","${_ctxPath}/admin/order/order-exportReturnOrders.htm");
		$('#subform').submit();
		$('#subform').attr("action","${_ctxPath}/admin/order/order-searchOrders4Seller.htm");
	}
</script>
</head>
<body>
	<jsp:include page="../include/header.jsp" flush="true" />
	<div class="body m-w980p">
		<!--start form-->
		<form class="sub-form" action="${_ctxPath}/admin/order/order-searchOrders4Seller.htm" method="post" id="subform">
			<div class="m-clear m-mt10p">
			<div class="m-fl"> 
					<%-- <c:if test="${!isAdmin }"> --%>
						<select name="orderPage.params.brandId" class="m-sel">
								<option value="">全部品牌</option>
								<c:forEach items="${brandList }" var="brand">
									<option value="${brand.brandId }"
										<c:if test="${orderPage.params.brandId==brand.brandId }"> selected = "selected" </c:if>>
											${brand.name}
									</option>
								</c:forEach>
						</select>
				<%-- 	</c:if> --%>
						<label>交易日期：</label> 
						<input name="orderPage.params.beginTime" value="${orderPage.params.beginTime}" class="Wdate" type="text" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						<label>至 </label> 
						<input name="orderPage.params.endTime" value="${orderPage.params.endTime }" class="Wdate" type="text" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						<input type="hidden" id="status" name="orderPage.params.status" value="${orderPage.params.status}" /><%--订单状态 --%>
						<input type="hidden" id="returnStatus" name="orderPage.params.returnStatus" value="${orderPage.params.returnStatus}" /><%--退货订单状态 --%>
						<input type="text" name="orderPage.params.keywords" value="${orderPage.params.keywords }"
						class="J-keywords txt-input input-marks" data-default="输入买家姓名、电话、订单号" /><input type="button" class="m-btn m-btn-search" value="查 询" id="search" /> 
						<c:if test="${orderPage.params.status != 6}"><%--当选择查询状态是退货时 --%>	
				      		<aut:authorize url="/admin/order/order-exportSellerOrders">
				      		<input type="button" class="m-btn" value="导出" id="export" />
				      		</aut:authorize>
			      		</c:if>
			      		<c:if test="${orderPage.params.status == 6}"><%--当选择查询状态是退货时 --%>
			      			<aut:authorize url="/admin/order/order-exportSellerOrders">	
				      			<input type="button" class="m-btn" value="导出" id="exportReturn" onclick="returnOrder()" />
				      		</aut:authorize>
			      		</c:if>
			</div>
			<c:if test="${not empty orderPage.result}">
				<div class="m-fr curr-num">
					<label>当前显示： </label> 
	        <wms:commPageSize page="${orderPage}" beanName="orderPage"></wms:commPageSize>
				</div>
      </c:if>
      </div>
		</form>
		<div class="m-clear m-mt10p">
			<div class="m-fl body-nav subnav" id="status_con">
				<ul class="m-clear">
					<li >
					<a
						<c:if test="${orderPage.params.status==null || orderPage.params.status=='' }"> class="current-chose"</c:if>>
						 全部订单
					</a>
					</li>
					<c:forEach items="${statuses }" var="statuse" varStatus="status">
					<c:if test="${statuse < 6}">	
						<li value="${statuse}">
						<a
							<c:if test="${orderPage.params.status==status.index+1 }"> class="current-chose" </c:if>>
								<spring:message code="order.status.${status.index+1}" /> 
						</a>
						</li>
					</c:if>
					<!-- 部分退货 和 全部退货  显示退货 -->
					<c:if test="${statuse == 6}">	
						<li value="${statuse}">
						<a
							<c:if test="${orderPage.params.status==status.index+1 }"> class="current-chose" </c:if>>
								退货 
						</a>
						</li>
					</c:if>
					</c:forEach>
				</ul>
			</div>
			<!--S 分页-->
<!-- 			<div class="pagination pagination-right"> -->
<%-- 				<c:if test="${not empty orderPage.result}"> --%>
<%-- 					<wms:commPage page="${orderPage}" beanName="orderPage"></wms:commPage> --%>
<%-- 				</c:if> --%>
<!-- 			</div> -->
			<!--E 分页-->
			<%-- <c:if test="${!isAdmin }"> --%>
				<div class="m-fr">
					<a class="download m-fr" href="${_ctxPath}/admin/order/order-downloadTemplate.htm">点击下载模板</a>
					<div class="inline">
					<script type="text/javascript">
						$(document).ready(
										function() {
											var uploadDefaultParams = {
												'auto' : true,
												'buttonImg' : '${_jsPath}/plugin/uploadify/batch.jpg',
												'cancelImg' : '${_jsPath}/plugin/uploadify/cancel.png',
												'expressInstall' : '${_jsPath}/plugin/uploadify/expressInstall.swf',
												'fileDataName' : 'file',
												'fileDesc' : '请选择xls、xlsx文件',
												'width' : 80,
												'fileExt' : '*.xls;*.xlsx',
												'multi' : false,
												'script' : '${_ctxPath}/admin/order/order-batchUpload.htm?user.userId=${orderPage.params.sellerId}',
												'sizeLimit' : 2097152,
												'uploader' : '${_jsPath}/plugin/uploadify/uploadify.allglyphs.swf'
											};
		
											var uploadlogoParams = uploadDefaultParams;
		
											uploadlogoParams.onComplete = function(
													event, ID, fileObj,
													response, data) {
												alert($.parseJSON(response).info);
											};
												$('#imgUpload').uploadify(
														uploadlogoParams);
										});
					</script>
					<input type="file" id="imgUpload" name="file" />
					</div>
				</div>
			<%-- </c:if> --%>
		</div>
		<c:if test="${orderPage.params.status == 6}"><%--当选择查询状态是退货时 --%>	
			<div class="child-menu"><%--1=未审核，2=审核通过，3=审核不通过，4=已到货 --%>
			<ul>
				<c:forEach items="${itemStatuses}" var="statuse" varStatus="status">
					<c:if test="${status.index==0 }">
						<li <c:if test="${orderPage.params.returnStatus==''||orderPage.params.returnStatus==0}"> class="on" </c:if> >
						<a class="returnStatus" id="${statuse}"
							><spring:message code="order.return.item.status.${status.index}" /> </a><%--1=未审核，2=审核通过，3=审核不通过，4=已到货 --%>
						</li>	
					</c:if>
					<c:if test="${status.index!=0 }">
						<li <c:if test="${orderPage.params.returnStatus==status.index }"> class="on" </c:if> >
						<a class="returnStatus" id="${statuse}"
							><spring:message code="order.return.item.status.${status.index}" /> </a><%--1=未审核，2=审核通过，3=审核不通过，4=已到货 --%>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		</c:if>
		<!--start 下单管理table-->
		<div class="order-management">
			<table id="order-tab" width="100%">
				<thead class="tab-control">
					<tr>
						<th width="10%">订单编号</th>
						<th width="12%">下单时间</th>
						<th width="10%">总金额</th>
						<th width="10%">收货人</th>
						<th width="10%">支付方式</th>
						<th width="10%">配送方式</th>
						<th width="10%">支付状态</th>
						<th width="10%">订单状态</th>
						<th width="8%">订单来源</th>
						<th width="10%">操作</th>
					</tr>
				
				</thead>
				<tbody>
				
					<c:forEach items="${orderPage.result}" var="order" varStatus="status">
						<tr class="list-tr">
							<td colspan="10" class="td-nobor">
								<table class="tab-control">
									<tr class="tr-height">
										<td class="num-icon" width="10%">
											<input type="hidden" value="${order.orderId}" id='orderId' />
											<input type ="hidden" value="${order.orderReturn.orderReturnId}" id="orderReturnId"/>
											<i></i><label><a>${order.orderNo}</a>
										</label>
										</td>
										<td width="12%"><fmt:formatDate value='${order.createTime}' pattern='yyyy-MM-dd HH:mm' /></td>
										<%-- <td width="10%">${order.totalPrice}</td>--%>
										<td width="10%">${order.payment.paymentAmount}</td>
										<td width="10%">${order.address.receiverName}</td>
										<td width="10%"><spring:message code="order.payment.type.0" /></td>
										<td width="10%">
											<c:if test="${order.orderExpress.expressName!='' &&  order.orderExpress.expressName!=null}">
												${order.orderExpress.expressName}
											</c:if>
											<c:if test="${order.orderExpress.expressName=='' ||  order.orderExpress.expressName==null}">
												${order.orderExpress.express.expressName}
											</c:if>
										</td>
										<td width="10%" id="payment_${order.orderId}">
											<c:if test="${order.payment.status ==null}">
											<spring:message code="order.payment.status.0" />
											</c:if> 
											<c:if test="${order.payment.status !=null}">
											<spring:message code="order.payment.status.${order.payment.status }" />
											</c:if>
										</td>
										<c:if test="${orderPage.params.status == 6}"><%--当选择查询状态是退货时 --%>	
											<td>
												<c:if test="${order.orderReturn.item.status==4 }">
													<c:choose>
														<c:when test="${order.orderReturn.orderReturnPayment.status==1 }">
															<spring:message code="order.return.item.status.5" />
														</c:when>
														<c:otherwise >
															<spring:message  code="order.return.item.status.3" />
														</c:otherwise>
													</c:choose>
												</c:if>
												<c:if test="${order.orderReturn.item.status!=4 }">
													<c:choose>
														<c:when test="${order.orderReturn.item.status==1 }">
															<spring:message code="order.return.item.status.${order.orderReturn.item.status}" />
														</c:when>
														<c:when test="${order.orderReturn.item.status==2 }">
															<spring:message code="order.return.item.status.${order.orderReturn.item.status}" />
														</c:when>
														<c:when test="${order.orderReturn.item.status==3 }">
															<spring:message code="order.return.item.status.4"  />
														</c:when>
													</c:choose>
												</c:if>
											</td>
											<td width="8%">
												<c:if test="${order.item!=null }">
													<spring:message code="order.source.${order.item.orderSource}" />
												</c:if>
											</td>
											<td width="10%">
	<%-- 											<c:if test="${order.orderReturn.status == null}"> --%>
												<aut:authorize url="/admin/order/order-confirmSendProduct">
													<c:if test="${order.status ==orderStatusWaitSend}">
														<a href="javascript:;" class="c-blue" id="sendButton_${order.orderId}">发货</a>
													</c:if>
												</aut:authorize>
	<%-- 											</c:if> --%>
												<c:if test="${not empty order.orderReturn}">
													<c:if test="${order.orderReturn.item.status == orderReturnItemStatusNotAudit }">
																<a href="javascript:;" class="c-blue">退货审核</a>
													</c:if>
												<aut:authorize url="/admin/order/order-agreePayment">
													<c:if test="${order.orderReturn.item.status == orderReturnItemStatusAccept }">
																<a href="javascript:;" class="c-blue">退款</a>
													</c:if>
												</aut:authorize>
												</c:if>
											</td>
										</c:if>
										<c:if test="${orderPage.params.status != 6}"><%--当选择查询状态是退货时 --%>	
											<td width="10%" id="orderStatus_${order.orderId}">
												<spring:message code="order.status.${order.status }" />
											</td>
											<td width="8%">
											<c:if test="${order.items[0]!=null }">
												<spring:message code="order.source.${order.items[0].orderSource}" />
											</c:if> 
											</td>
											<td width="10%">
	<%-- 											<c:if test="${order.orderReturn.status == null}"> --%>
												<aut:authorize url="/admin/order/order-confirmSendProduct">
													<c:if test="${order.status ==orderStatusWaitSend}">
														<a href="javascript:;" class="c-blue" id="sendButton_${order.orderId}">发货</a>
													</c:if>
												</aut:authorize>
	<%-- 											</c:if> --%>
												<c:if test="${not empty order.orderReturns}">
													<c:set var="num1" value="0"></c:set>
													<c:set var="num2" value="0"></c:set>
													<c:forEach items="${order.orderReturns }" var="orderReturn">
														<c:forEach items="${orderReturn.returnItems }" var="item">
															<c:if test="${num1 == 0 && item.status == orderReturnItemStatusNotAudit }">
																<a href="javascript:;" class="c-blue">退货审核</a>
																<c:set var="num1" value="${num1+1 }"></c:set>
															</c:if>
														<aut:authorize url="/admin/order/order-agreePayment">
															<c:if test="${num2 == 0 && item.status == orderReturnItemStatusAccept }">
																<a href="javascript:;" class="c-blue">退款</a>
																<c:set var="num2" value="${num2+1 }"></c:set>
															</c:if>
														</aut:authorize>
														</c:forEach>
													</c:forEach>
												</c:if>
											</td>
										</c:if>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="table-bm-wrap cf">
			<!--S 右功能区-->
			<div class="fn-right">
				<!--S 分页-->
				<div class="pagination pagination-right">
					<c:if test="${not empty orderPage.result}">
						<wms:commPage page="${orderPage}" beanName="orderPage"></wms:commPage>
					</c:if>
				</div>
				<!--E 分页-->
			</div>
			<!--E 右功能区-->
		</div>
	</div>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		//防止在一次请求 多次点击
		var isPost = true;
		$(".tr-height").on("click", function() {
			$this = $(this);
			var orderId = $this.find('#orderId').val();
			var returnStatus = $('#returnStatus').val();<%--当前选中的退货订单状态--%>
			var orderReturnId = $this.find('#orderReturnId').val();
			var status = $('#status').val();<%--当前选中的订单状态--%>
			if (!$(this).next(".show-tr")[0]) {
				if(isPost){
					isPost = false;
					$.ajax({
						type : 'POST',
						url : '${_ctxPath}/admin/order/order-getOrder4Seller.htm',
						data : {
							"orderHead.orderId" : orderId,
							"orderPage.params.returnStatus":returnStatus,
							"orderPage.params.orderReturnId":orderReturnId,
							"orderPage.params.status":status,
							"orderPage.params.orderId":orderId
						},
						success : function(html) {
							var content;
							if (typeof html == "object") {
								data = eval(html);
								content = data.info;
							} else {
								content = html;
							}
							$($this).after($(content));
							$this.addClass("change");
							$this.click();
							isPost = true;
						},
						error:function(){
							isPost = true;
						}
					});
				}
			}
		});
});
</script>
</body>
</html>