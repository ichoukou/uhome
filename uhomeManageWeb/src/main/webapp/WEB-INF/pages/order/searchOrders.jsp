<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>订单管理</title>
<link href="${ _cssPath}/common.css" rel="stylesheet" />
<link href="${ _cssPath}/pages/orderManagement.css" rel="stylesheet" />
<link href="${_jsPath }/plugin/My97DatePicker/skin/WdatePicker.css"
	rel="stylesheet" />
<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
<script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
<script src="${_jsPath }/plugin/plugin.js" language="javascript"></script>
<script src="${_jsPath }/pages/index.js"></script>
<script type="text/javascript"
	src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<script type="text/javascript"
	src="${_jsPath}/plugin/uploadify/swfobject.js"></script>
<script type="text/javascript">
	var searchOrder = function() {
		var confirmMessage = "是否禁用？";
		if (status == 2) {
			confirmMessage = "是否开启？";
		}
		if (confirm(confirmMessage)) {
			window.location.href = "${_ctxPath}/admin/user/user-updateUserStatus.htm?user.userId="
					+ userId + "&user.status=" + status;
		}
	};
	$(function() {
		$('#status_con li').bind('click', function() {
			// 			alert($(this).val());
			if ($(this).val() == 0) {
				$('#status').val('');
			} else {
				$('#status').val($(this).val());
			}
			$('#subform').submit();
		});
		$('#search').bind('click', function() {
			$('#subform').submit();
		});
		$('#export').bind('click', function() {
			var url = '${_ctxPath}/admin/order/order-exportOrder.htm'
			if ($('#status').val() != '' && $('#status').val() != null) {
				url = url + '?orderPage.params.status=' + $('#status').val();
			}
			window.location.href = url;
		});

	});
</script>
</head>
<body>
	<jsp:include page="../include/header.jsp" flush="true" />
	<div class="body m-w980p">
		<!--start form-->
		<form class="sub-form layout"
			action="${_ctxPath}/admin/order/order-searchOrders.htm" method="post"
			id="subform">
			<div class="m-fl"> 
				<ul>
					<c:if test="${isAdmin }">
			<input type="hidden" name="orderPage.params.buyerId" value="${orderPage.params.buyerId}"/>
						<li><select name="orderPage.params.productCategoryId">
								<option value="">全部品类</option>
								<c:forEach items="${productCategories }" var="productCategory">
									<option value="${productCategory.productCategoryId }"
										<c:if test="${orderPage.params.productCategoryId==productCategory.productCategoryId }">
								selected = "selected"
								</c:if>>${productCategory.name}</option>
								</c:forEach>
						</select>
						</li>
						<li><select name="orderPage.params.brandId">
								<option value="">全部品牌</option>
								<c:forEach items="${brands }" var="brand">
									<option value="${brand.brandId }"
										<c:if test="${orderPage.params.brandId==brand.brandId }">
								selected = "selected"
								</c:if>>${brand.name}</option>
								</c:forEach>
						</select>
						</li>

						<li><select name="orderPage.params.sellerId">
								<option value="">全部商家</option>
								<c:forEach items="${sellers }" var="seller" varStatus="status">
									<option value="${seller.sellerId }"
										<c:if test="${orderPage.params.sellerId==seller.sellerId }">
								selected = "selected"
								</c:if>>${seller.companyName
										}</option>
								</c:forEach>
						</select>
						</li>
					</c:if>
					<c:if test="${!isAdmin }">
						<li><select name="orderPage.params.brandId">
								<option value="">全部品牌</option>
								<c:forEach items="${brands }" var="brand">
									<option value="${brand.brandId }"
										<c:if test="${orderPage.params.brandId==brand.brandId }">
								selected = "selected"
								</c:if>>${brand.name}</option>
								</c:forEach>
						</select>
						</li>
					</c:if>

					<li><label>交易日期：</label> <input
						name="orderPage.params.beginTime"
						value="${orderPage.params.beginTime}" class="Wdate" type="text"
						readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></input>
						<label>至 </label> <input name="orderPage.params.endTime"
						value="${orderPage.params.endTime }" class="Wdate" type="text"
						readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></input>
					</li>
					<li><input type="text" name="orderPage.params.receiverName"
						value="${orderPage.params.receiverName }"
						class="txt-Keywords input-marks input-default" /> <input
						type="hidden" id="status" name="orderPage.params.status"
						value="${orderPage.params.status}" /> <input type="button"
						class="btn btn-danger btn-search" value="查 询" id="search" /> <input
						type="button" class="btn btn-danger btn-search" value="导出"
						id="export" />
						<div class="inline"><c:if test="${!isAdmin }">
							<script type="text/javascript">
								$(document)
										.ready(
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
							<!-- 							<input type="button" class="btn btn-danger btn-search" -->
							<!-- 								value="批量发货" id="batchUpload"/> -->
							<input type="file" id="imgUpload" name="file" />
							</div>
							<a class="inline a-btn" href="${_ctxPath}/admin/order/order-downloadTemplate.htm">下载模板</a>
						</c:if>
					</li>
				</ul>
			</div>
		</form>

		<div class="examine body-nav layout">
			<div id="status_con">
				<ul>
					<li ><a
						<c:if test="${orderPage.params.status==null || orderPage.params.status=='' }">
					class="current-chose"
					</c:if>>全部订单</a>
					</li>
					<c:forEach items="${statuses }" var="statuse" varStatus="status">
						<li value="${statuse}">
						
						<a
							<c:if test="${orderPage.params.status==status.index+1 }">
					class="current-chose"
					</c:if>><spring:message
									code="order.status.${status.index+1}" /> 
									
									</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			<c:if test="${not empty orderPage.result}">
				<div class="m-fr curr-num">
					<label>当前显示： </label> 
	               	<wms:commPageSize page="${orderPage}" beanName="orderPage"></wms:commPageSize>
				</div>
            </c:if>
		</div>

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
					<c:forEach items="${orderPage.result}" var="order"
						varStatus="status">
						<tr class="list-tr">
							<td colspan="10" class="td-nobor">
								<table class="tab-control">
									<tr class="tr-height">
										<td class="num-icon" width="10%">
											<input type="hidden" value="${order.orderId}" id='orderId' />
											<i></i><label><a>${order.orderNo}</a>
										</label>
										</td>
										<td width="12%"><fmt:formatDate
												value='${order.createTime}' pattern='yyyy-MM-dd HH:mm' /></td>
										<td width="10%">${order.totalPrice}</td>
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
										<td width="10%" id="payment_${order.orderId}"><c:if
												test="${order.payment.status ==null}">
												<spring:message code="order.payment.status.0" />
											</c:if> <c:if test="${order.payment.status !=null}">
												<spring:message
													code="order.payment.status.${order.payment.status }" />
											</c:if>
										</td>
										<td width="10%" id="orderStatus_${order.orderId}"><c:if
												test="${order.orderReturn.status ==null }">
												<spring:message code="order.status.${order.status }" />
											</c:if> <c:if test="${order.orderReturn.status !=null }">
												<spring:message
													code="order.return.status.${order.orderReturn.status }" />
											</c:if></td>
										<td width="10%"><c:if test="${order.items[0]!=null }">
												<spring:message
													code="order.source.${order.items[0].orderSource}" />
											</c:if>
										</td>
										<td width="8%">
										<c:if test="${!isAdmin}">
											<c:if test="${order.orderReturn.status ==null }">
												<c:if test="${order.status ==orderStatusWaitSend}">
													<a id="sendButton_${order.orderId}">发货</a>
												</c:if>
											</c:if>
											<c:if test="${order.orderReturn.status ==orderReturnStatusNotAudit}">
												<a id="sendButton_${order.orderId}">退货审核</a>
											</c:if>
										</c:if>
										<c:if
												test="${isAdmin && order.payment.status ==2}">
												<a href="#" class="c-blue" id="returnPayment_${order.orderId}">退款</a>
											</c:if></td>
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
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$(".tr-height").on("click", function() {
			$this = $(this);
			var orderId = $this.find('#orderId').val();
			if (!$(this).next(".show-tr")[0]) {
				$.ajax({
					type : 'POST',
					url : '${_ctxPath}/admin/order/order-getOrder.htm',
					data : {
						"orderHead.orderId" : orderId
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
					}
				});
			}
		});
	});
</script>
</html>