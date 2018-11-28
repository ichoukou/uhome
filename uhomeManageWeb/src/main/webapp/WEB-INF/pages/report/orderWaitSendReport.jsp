<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>每日新增待发货订单</title>
    <link href="${ _cssPath}/common.css" rel="stylesheet" />
    <link href="${ _cssPath}/pages/reportForms.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <script src="${_jsPath }/plugin/plugin.js"  language="javascript"></script>
    <script src="${_jsPath }/pages/index.js"></script>
    <script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
    <script type="text/javascript">
	    $(function(){
			$('#search').bind('click', function() {
				var result = $.formValidator.pageIsValid('1');
				if(!result){
					return false;
				}
				$("#searchForm").attr("action","${_ctxPath}/admin/report/saleStatementReport-searchOrderWaitSendReport.htm");
				$("#searchForm").submit();
				
			});
		});
	    $(function(){
			$('#export').bind('click', function() {
				var result = $.formValidator.pageIsValid('1');
				if(!result){
					return false;
				}
				$("#searchForm").attr("action","${_ctxPath}/admin/report/saleStatementReport-exportOrderWaitSendReport.htm");
				$("#searchForm").submit();
			});
		});
    </script>
</head>
<body>
	<!--start body-->
	<jsp:include page="../include/header.jsp" flush="true" />
	<div class="body m-w980p m-mt10p">
		
		<!-- reportMenu -->
		<jsp:include page="./saleStatementReportMenu.jsp" flush="true" />
		<form class="m-clear m-mt10p" id="searchForm" method="post">
			<!-- 此处用于报表菜单选择 -->
			<input type="hidden" name="reportMenuId" value="${reportMenuId}" />
			<div class="m-fl">
				<span class="position">
					<span class="c-red">*</span>
					<input type="text" name="orderWaitSendReportPage.params.time" class="Wdate"	onfocus="WdatePicker()" value="${orderWaitSendReportPage.params.time}" id="time" /> 
					<input type="button" class="m-btn" value="查询" id="search" /> 
					<input type="button" class="m-btn outer" value="导出" id="export" />
					<div id="dateTip"></div>
				</span>
			</div>
			<!--右侧显示分页控件 start-->
			<c:if test="${not empty orderWaitSendReportPage.result}">
				<div class="m-fr curr-num">
					<label>当前显示： </label>
					<wms:commPageSize page="${orderWaitSendReportPage}" beanName="orderWaitSendReportPage" 
					paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPageSize>
				</div>
			</c:if>
			<!--右侧显示分页控件 end-->
		</form>
		<!--100%表格样式-->
		<c:if test="${not empty orderWaitSendReportPage}">
			<div class="m-mt10p tab-over">
				<table width="5000">
					<!--100%表格样式-->
					<!--需要宽表格样式-->
					<!--<div class="m-mt10p tab-over">
						<table>-->
					<!--需要宽表格样式-->
					<thead>
						<tr class="th-c">
							<th colspan="2">供应商及品牌</th>
							<th colspan="10">订单信息</th>
							<th colspan="5">交易信息</th>
							<th colspan="6">发货信息</th>
							<th colspan="2">供应商填写</th>
						</tr>
						<tr>
							<th width="150">商家名称</th>
							<th width="150">品牌名称</th>
							<th width="120">订单创建时间</th>
							<th width="120">订单支付时间</th>
							<th width="80">订单号</th>
							<th width="80">订单状态</th>
							<th width="80">国际码</th>
							<th width="80">SKU编码</th>
							<th width="150">商品名称</th>
							<th width="50">颜色</th>
							<th width="60">尺码/规格</th>
							<th width="100">购买商品数量</th>
							<th width="80">商品单价</th>
							<th width="100">优惠类型</th>
							<th width="100">优惠金额</th>
							<th width="60">运费</th>
							<th width="120">买家实际支付金额</th>
							<th width="60">收货人姓名</th>
							<th width="200">收货地址</th>
							<th width="60">邮编</th>
							<th width="60">联系电话</th>
							<th width="60">联系手机 </th>
							<th width="150">发票抬头</th>
							<th width="100">物流公司</th>
							<th width="100">物流单号</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${orderWaitSendReportPage.result }"
							var="orderWaitSendReport" varStatus="status">
							<tr class="list-tr">
								<td>${orderWaitSendReport.sellerName}</td>
								<td>${orderWaitSendReport.brandName}</td>
								<td><fmt:formatDate value="${orderWaitSendReport.orderCreateTime}" pattern='yyyy-MM-dd HH:mm:ss'/></td>
								<td><fmt:formatDate value="${orderWaitSendReport.orderPayTime}" pattern='yyyy-MM-dd HH:mm:ss'/></td>
								<td>${orderWaitSendReport.orderNo}</td>
								<td>${orderWaitSendReport.orderStatus}</td>
								<td>${orderWaitSendReport.internationalCode}</td>
								<td>${orderWaitSendReport.skuCode}</td>
								<td>${orderWaitSendReport.productName}</td>
								<td>${orderWaitSendReport.color}</td>
								<td>${orderWaitSendReport.specification}</td>
								<td>${orderWaitSendReport.buyNum}</td>
								<td><span class="coin">&yen;${orderWaitSendReport.unitPrice}</span></td>
								<td>${orderWaitSendReport.rebateType}</td>
								<td><c:if test="${not empty orderWaitSendReport.rebatePrice}"><span class="coin">&yen;${orderWaitSendReport.rebatePrice}</span></c:if></td>
								<td><span class="coin">&yen;${orderWaitSendReport.postage}</span></td>
								<td><span class="coin">&yen;${orderWaitSendReport.paymentAmount}</span></td>
								<td>${orderWaitSendReport.receiver}</td>
								<td>${orderWaitSendReport.receiveAddress}</td>
								<td>${orderWaitSendReport.postCode}</td>
								<td>${orderWaitSendReport.telephone}</td>
								<td>${orderWaitSendReport.mobile}</td>
								<td>${orderWaitSendReport.invoiceTitle}</td>
								<td></td>
								<td></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!--分页插件 start -->
			<div class="pagination pagination-right">
				<c:if test="${not empty orderWaitSendReportPage.result}">
					<wms:commPage page="${orderWaitSendReportPage}" beanName="orderWaitSendReportPage" 
					paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPage>
				</c:if>
			</div>
			<!--分页插件 end -->
		</c:if>
	</div>
	<!--end body-->
	<!--start footer-->
	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
	<!--end footer-->
	<script type="text/javascript">
	$.formValidator.initConfig({formID:"searchForm",theme:"Default",errorFocus:false,wideWord:false});
	$("#time").formValidator({tipID:"dateTip",onShow:"请选择时间",onFocus:"请选择时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"时间必填"});
	</script>
</body>
</html>