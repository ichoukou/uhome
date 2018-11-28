<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomeorder.dataobject.OrderPayment"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单管理</title>
	
	<link href="${ _cssPath}/pages/orderManagement.css" rel="stylesheet" />
	<link href="${_jsPath }/plugin/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" />
	<script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
	<link href="${ _cssPath}/common.css" rel="stylesheet" />
	<link href="${_cssPath }/pages/message.css" rel="stylesheet" /> 
	<link href="${_jsPath }/plugin/artdialog/skins/ytoxl.css" rel="stylesheet" />
	<script src="${_jsPath }/pages/searchOrderCrms.js" language="javascript"></script>
	<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
	<script src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js" language="javascript"></script>
	<script src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js" language="javascript"></script>
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
			if($('tbody').html().trim()==''){
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
			$('#subform').attr("action","${_ctxPath}/admin/order/order-exportOrders.htm");
			$('#subform').submit();
			$('#subform').attr("action","${_ctxPath}/admin/order/order-searchOrders4Manager.htm");
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
		$('#subform').attr("action","${_ctxPath}/admin/order/order-searchOrders4Manager.htm");
	}
</script>
</head>
<body>
	<jsp:include page="../include/header.jsp" flush="true" />
	<div class="body m-w980p">
		<!--start form-->
		<form class="m-mt10p m-clear" action="${_ctxPath}/admin/order/order-searchOrders4Manager.htm" method="post" id="subform">
			<input type="hidden" name="orderPage.params.buyerId" value="${orderPage.params.buyerId }"/>
			<div class="m-fl"> 
					<select name="orderPage.params.productCategoryId" class="m-sel">
							<option value="">全部品类</option>
							<c:forEach items="${productCategories }" var="productCategory">
								<option value="${productCategory.productCategoryId }"
									<c:if test="${orderPage.params.productCategoryId==productCategory.productCategoryId }"> selected = "selected" </c:if>>
									${productCategory.name}
								</option>
							</c:forEach>
					</select>
					<select name="orderPage.params.brandId" class="m-sel">
							<option value="">全部品牌</option>
							<c:forEach items="${brands }" var="brand">
								<option value="${brand.brandId }"
									<c:if test="${orderPage.params.brandId==brand.brandId }"> selected = "selected" </c:if>>
										${brand.name}
								</option>
							</c:forEach>
					</select>
						<label>交易日期：</label> 
						<input name="orderPage.params.beginTime" value="${orderPage.params.beginTime}" class="Wdate" type="text" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						<label>至 </label> 
						<input name="orderPage.params.endTime" value="${orderPage.params.endTime }" class="Wdate" type="text" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					    <input type="hidden" id="returnStatus" name="orderPage.params.returnStatus" value="${orderPage.params.returnStatus}" /><%--退货订单状态 --%>
					    <select name="orderPage.params.sellerId" class="m-sel">
							<option value="">全部商家</option>
							<c:forEach items="${sellers }" var="seller" varStatus="status">
								<option value="${seller.sellerId }"
									<c:if test="${orderPage.params.sellerId==seller.sellerId }"> selected = "selected" </c:if>>
									${seller.companyName}
								</option>
							</c:forEach>
					    </select>
			</div>
			<c:if test="${not empty orderPage.result}">
				<div class="m-fr curr-num">
					<label>当前显示： </label> 
	        <wms:commPageSize page="${orderPage}" beanName="orderPage"></wms:commPageSize>
				</div>
      </c:if>
      <div class="m-fl m-mt10p">
      			<input type="text" class="J-keywords txt-input input-marks" data-default="输入收货人、订单号" name="orderPage.params.keywords" value="${orderPage.params.keywords }" /><aut:authorize url="/admin/order/order-search"><input type="button" class="m-btn m-btn-search" value="查 询" id="search" /> 
						</aut:authorize>
				<input type="hidden" id="status" name="orderPage.params.status" value="${orderPage.params.status}" /> 
			<c:if test="${orderPage.params.status != 6}"><%--当选择查询状态是退货时 --%>	
	      		<aut:authorize url="/admin/order/order-exportOrders">
	      		<input type="button" class="m-btn" value="导出" id="export" />
	      		</aut:authorize>
      		</c:if>
      		<c:if test="${orderPage.params.status == 6}"><%--当选择查询状态是退货时 --%>	
	      		<input type="button" class="m-btn" value="导出" id="exportReturn" onclick="returnOrder()" />
      		</c:if>
      </div>
      <!--S 分页-->
<!-- 			<div class="pagination pagination-right"> -->
<%-- 				<c:if test="${not empty orderPage.result}"> --%>
<%-- 					<wms:commPage page="${orderPage}" beanName="orderPage"></wms:commPage> --%>
<%-- 				</c:if> --%>
<!-- 			</div> -->
			<!--E 分页-->
<!-- 			<div class="clear"></div> -->
		</form>
		<div class="body-nav subnav m-mt10p" id="status_con">
				<ul class="m-clear">
					<li>
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
						<th width="10%">订单来源</th>
						<th width="8%">操作</th>
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
											<input type="hidden" value="${order.orderNo}" id='orderNo' />
											<input type ="hidden" value="${order.orderReturn.orderReturnId}" id="orderReturnId"/>
											<i></i><label><a>${order.orderNo}</a>
										</label>
										</td>
										<td width="12%"><fmt:formatDate value='${order.createTime}' pattern='yyyy-MM-dd HH:mm' /></td>
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
											<td width="10%">
												<c:if test="${order.item!=null }">
													<spring:message code="order.source.${order.item.orderSource}" />
												</c:if>
											</td>
											<td width="8%">
											<aut:authorize url="/admin/orderCrm/orderCrm-addOrderCrm">
											<a href="javascript:void(0);" class="blue" onclick="clickAddOrEdit(${order.orderId},${order.orderNo},event)">客服操作</a><br/>
            								</aut:authorize>
											<c:if test="${isAdmin && not empty order.orderReturn}">
												<c:if test="${ order.orderReturn.orderReturnPayment.status == returnPaymentStatusWaitRefund }">
													<a href="javascript:;" class="c-blue">退款</a>
												</c:if>
											</c:if>
											</td>
										</c:if>
										<c:if test="${orderPage.params.status != 6}"><%--当选择查询状态不是退货时 --%>	
											<td width="10%" id="orderStatus_${order.orderId}">
												<spring:message code="order.status.${order.status }" />
											</td>
											<td width="10%">
											<c:if test="${order.items[0]!=null }">
												<spring:message code="order.source.${order.items[0].orderSource}" />
											</c:if>
											</td>
											<td width="8%">
											<aut:authorize url="/admin/orderCrm/orderCrm-addOrderCrm">
											<a href="javascript:void(0);" class="blue" onclick="clickAddOrEdit(${order.orderId},${order.orderNo},event)">客服操作</a><br/>
            								</aut:authorize>
											<c:if test="${isAdmin && not empty order.orderReturns}">
												<c:set var="num" value="0"></c:set>
												<c:forEach items="${order.orderReturns }" var="orderReturn">
														<c:set var="returnPayment" value="${orderReturn.orderReturnPayment }"></c:set>
														<c:if test="${num == 0 && returnPayment.status == returnPaymentStatusWaitRefund }">
															<a href="javascript:;" class="c-blue">退款</a>
															<c:set var="num" value="${num+1 }"></c:set>
														</c:if>
												</c:forEach>
											</c:if>
	<%-- 										<c:if test="${isAdmin && order.orderReturn.orderReturnPayment.status == returnPaymentStautsWaitRefund}"> --%>
	<%-- 											<a href="javascript:;" class="c-blue" id="returnPayment_${order.orderId}">退款</a> --%>
	<%-- 										</c:if> --%>
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
			if($('tbody').html().trim()==''){
				return;
			}
			var keywords = $(".J-keywords").val();
			if(keywords == $(".J-keywords").attr("data-default")){
				$(".J-keywords").val("");
			}
			/*
			var url = '${_ctxPath}/admin/order/order-exportOrder.htm'
			if ($('#status').val() != '' && $('#status').val() != null) {
				url = url + '?orderPage.params.status=' + $('#status').val();
			}
			window.location.href = url;
			*/
			$('#subform').attr("action","${_ctxPath}/admin/order/order-exportOrders.htm");
			$('#subform').submit();
			$('#subform').attr("action","${_ctxPath}/admin/order/order-searchOrders4Manager.htm");
		});
		
		//防止在一次请求 多次点击
		var isPost = true;
	 	$(".tr-height").on("click", function() {
			$this = $(this);
			var orderId = $this.find('#orderId').val();
			var orderReturnId = $this.find('#orderReturnId').val();
			var returnStatus = $('#returnStatus').val();<%--当前选中的退货订单状态--%>
			var status = $('#status').val();<%--当前选中的订单状态--%>
			if (!$(this).next(".show-tr")[0]) {
				if(isPost){
					isPost =false;
					$.ajax({
						type : 'POST',
						url : '${_ctxPath}/admin/order/order-getOrder4Manager.htm',
						data : {
							"orderHead.orderId" : orderId,
							"orderPage.params.orderReturnId":orderReturnId,
							"orderPage.params.returnStatus":returnStatus,
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