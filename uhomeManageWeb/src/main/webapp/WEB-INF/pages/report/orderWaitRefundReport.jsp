<!doctype html>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
    <meta charset="utf-8"/>
    <title>每日新增待退款订单</title>
    <link href="${ _cssPath}/common.css" rel="stylesheet" />
    <link href="${ _cssPath}/common.less" rel="stylesheet" />
    <link href="${ _cssPagesPath}/reportForms.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <link href="${_jsPath }/plugin/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" />
    <script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" >
    $(function(){
		$('#export').bind('click', function() {
			var url = '${_ctxPath}/admin/report/saleStatementReport-exportOrderWaitRefundReports.htm';
			if ($('#time').val() != '' && $('#time').val() != null) {
				url = url + '?orderWaitRefundReportPage.params.time=' + $('#time').val();
			}
			window.location.href = url;
		});
	});
    </script>

</head>
<body>
<!--start header-->
<!--end header-->
<!--start body-->
<jsp:include page="../include/header.jsp" flush="true" />
<div class="body m-w980p m-mt10p">
	<jsp:include page="./saleStatementReportMenu.jsp" flush="true" />
 
  <form action="${_ctxPath}/admin/report/saleStatementReport-searchEverydaySalesDetailReports.htm" class="m-clear m-mt10p" id="searchForm" method="post" >
  <!-- 此处用于报表菜单选择 -->
			<input type="hidden" name="reportMenuId" value="${reportMenuId}" />
    <div class="m-fl">
	  <input type="text" class="Wdate" name="orderWaitRefundReportPage.params.time" 
	  	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){time.focus();}})" 
	    value="${orderWaitRefundReportPage.params.time }" id="time"/>
      <aut:authorize url="/admin/report/report-search">
		<input type="submit" class="m-btn" value="查询" id="search"/>
	  </aut:authorize>
	  <aut:authorize url="/admin/report/report-exportMonthReports">
		<input type="button" class="m-btn outer" value="导出" id="export"/>
	  </aut:authorize>
    </div>
    <!--右侧显示分页控件 start-->
    <c:if test="${not empty orderWaitRefundReportPage.result}">
		<div class="m-fr curr-num">
			<label>当前显示： </label> 
	        <wms:commPageSize page="${orderWaitRefundReportPage}" beanName="orderWaitRefundReportPage"
	        paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPageSize>
		</div>
    </c:if>
    <!--右侧显示分页控件 end-->
	</form>
	<c:if test="${orderWaitRefundReportPage!=null}"> 
	<div class="m-mt10p tab-over">
	<table width="2900">
      <thead>
      	<tr class="th-c">
			<th colspan="3">退款人信息</th>
			<th colspan="11">订单信息</th>
			<th colspan="5">交易信息</th>
			<th colspan="2">供应商及品牌</th>
		</tr>
        <tr>
		  <th width="150">退款人姓名</th>
		  <th width="150">退款账号</th>
		  <th width="150">退款金额</th>
		  <th width="150">退款申请时间</th>
		  <th width="150">订单创建时间</th>  
		  <th width="150">订单支付时间</th>
		  <th width="150">订单号</th>
		  <th width="150">订单状态</th>
		  <th width="150">国际码</th>
		  <th width="150">SKU编码</th>  
		  <th width="150">商品名称</th>
		  <th width="80">颜色</th>
		  <th width="100">尺寸/规格</th>
		  <th width="100">购买商品数量</th>
		  <th width="150">商品单价</th>
		  <th width="150">优惠类型</th>
		  <th width="150">优惠金额</th>
		  <th width="70">运费</th>
		  <th width="150">买家实际支付金额</th>
		  <th width="150">商家名称</th>
		  <th width="150">品牌名称</th>
        </tr>
      </thead>
      <tbody>
      	<c:forEach items="${orderWaitRefundReportPage.result }" var="orderWaitRefundReportPage" varStatus="status">
			<tr class="list-tr">
				<td>${orderWaitRefundReportPage.userName}</td>
				<td>${orderWaitRefundReportPage.account}</td>
				<td><span class="coin"><c:if test="${orderWaitRefundReportPage.refundAmount!=null}">&yen;</c:if>${orderWaitRefundReportPage.refundAmount}</span></td>
				<td><fmt:formatDate value='${orderWaitRefundReportPage.refundTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td><fmt:formatDate value='${orderWaitRefundReportPage.orderCreateTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td><fmt:formatDate value='${orderWaitRefundReportPage.orderPayTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>	
				<td>${orderWaitRefundReportPage.orderNo}</td>
				<td>${orderWaitRefundReportPage.orderStatus}</td>
				<td>${orderWaitRefundReportPage.internationalCode}</td>
				<td>${orderWaitRefundReportPage.skuCode}</td>
				<td>${orderWaitRefundReportPage.productName}</td>
				<td>${orderWaitRefundReportPage.colour}</td>
				<td>${orderWaitRefundReportPage.size}</td>
				<td>${orderWaitRefundReportPage.num}</td>
				<td><span class="coin"><c:if test="${orderWaitRefundReportPage.salePrice!=null}">&yen;</c:if>${orderWaitRefundReportPage.salePrice}</span></td>
				<td>${orderWaitRefundReportPage.preferentialType}</td>
				<td><span class="coin"><c:if test="${orderWaitRefundReportPage.rebatePrice!=null}">&yen;</c:if>${orderWaitRefundReportPage.rebatePrice}</span></td>
				<td><span class="coin"><c:if test="${orderWaitRefundReportPage.postage!=null}">&yen;</c:if>${orderWaitRefundReportPage.postage}</span></td>
				<td><span class="coin"><c:if test="${orderWaitRefundReportPage.paymentAmount!=null}">&yen;</c:if>${orderWaitRefundReportPage.paymentAmount}</span></td>
				<td>${orderWaitRefundReportPage.companyName}</td>
				<td>${orderWaitRefundReportPage.brandName}</td>
			</tr>
		</c:forEach>
      </tbody>
    </table>
	</div>
	<!--分页插件 start -->
	<div class="pagination pagination-right">
		<c:if test="${not empty orderWaitRefundReportPage.result}">
			<wms:commPage page="${orderWaitRefundReportPage}" beanName="orderWaitRefundReportPage"
			paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPage>
		</c:if>
	</div>
	<!--分页插件 end -->
	</c:if>
</div>
<!--end body-->
<!--start footer-->
<!--end footer-->
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
</html>