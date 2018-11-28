<!doctype html>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
    <meta charset="utf-8"/>
    <title>每日特卖会销售明细</title>
    <link href="${ _cssPath}/common.css" rel="stylesheet" />
    <link href="${ _cssPagesPath}/reportForms.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <link href="${_jsPath }/plugin/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" />
    <script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
    <script type="text/javascript" >
    function ajaxValue(planId){
    	var url = '${_ctxPath}/admin/report/productReport-getPlansBySeller.htm';
		if ($('#time').val() != '' && $('#time').val() != null) {
			url = url + '?everySalesDetailReportPage.params.time=' + $('#time').val();
		}
		if ($('#sellerId').val() != '' && $('#sellerId').val() != null) {
			if(url.indexOf("?")!=-1){
				url = url + '&everySalesDetailReportPage.params.sellerId=' + $('#sellerId').val();
			}else{
				url = url + '?everySalesDetailReportPage.params.sellerId=' + $('#sellerId').val();
			}
		}
        $.get(url,function(data){
     		$("#planId").empty();
     		$("#planId").prepend("<option value=''>全部排期</option>"); 
     		 var result="";
 		   		$.each(data,function(i,plans){
 		   		   if( planId ==plans.planId ){
 						result = result +  "<option selected = 'selected' value='"+plans.planId+"'>"+plans.name+"</option>";
 					}else{
 						result = result +  "<option value='"+plans.planId+"'>"+plans.name+"</option>";
 					}
 		   		} )
         	$("#planId").append(result);
         },"json" ); 
     }
    
    $(function(){        
    	  ajaxValue('${everySalesDetailReportPage.params.planId}');
  	});
    
    $(function(){
    	$('#search').bind('click', function() {
			var result = $.formValidator.pageIsValid('1');
			if(!result){
				return false;
			}
			$("#searchForm").attr("action","${_ctxPath}/admin/report/productReport-searchEverydaySalesDetailReports.htm");
			$("#searchForm").submit();
		});
    	
		$('#export').bind('click', function() {
			var result = $.formValidator.pageIsValid('1');
			if(!result){
				return false;
			}
			var url = '${_ctxPath}/admin/report/productReport-exportEverydaySalesDetailReports.htm';
			if ($('#time').val() != '' && $('#time').val() != null) {
				url = url + '?everySalesDetailReportPage.params.time=' + $('#time').val();
			}
			if ($('#sellerId').val() != '' && $('#sellerId').val() != null) {
				if(url.indexOf("?")!=-1){
					url = url + '&everySalesDetailReportPage.params.sellerId=' + $('#sellerId').val();
				}else{
					url = url + '?everySalesDetailReportPage.params.sellerId=' + $('#sellerId').val();
				}
			}
			if ($('#planId').val() != '' && $('#planId').val() != null) {
				if(url.indexOf("?")!=-1){
					url = url + '&everySalesDetailReportPage.params.planId=' + $('#planId').val();
				}else{
					url = url + '?everySalesDetailReportPage.params.planId=' + $('#planId').val();
				}
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
	<jsp:include page="./reportMenuHeader.jsp" flush="true" />
 
  <form class="m-clear m-mt10p" id="searchForm" method="post">
  <!-- 此处用于报表菜单选择 -->
			<input type="hidden" name="reportMenuId" value="${reportMenuId}" />
    <div class="m-fl">
    	<span class="position">
    	<span class="c-red">*</span>
	  	<input type="text" class="Wdate" name="everySalesDetailReportPage.params.time" 
	  	onfocus="WdatePicker({dateFmt:'yyyy-MM',readOnly:true,onpicked:function(){time.focus();}})" 
	    onchange="ajaxValue('');"
	    value="${everySalesDetailReportPage.params.time }" id="time"/>
	    商家名称：
	  <select name="everySalesDetailReportPage.params.sellerId" id="sellerId" class="m-sel" onchange="ajaxValue('');">
		<option value="">全部商家</option>
		<c:forEach items="${sellers }" var="seller" varStatus="status">
			<option value="${seller.sellerId }" <c:if test="${everySalesDetailReportPage.params.sellerId==seller.sellerId }">selected = "selected"</c:if>>
				${seller.companyName}
			</option>
		</c:forEach>
	  </select>  
	  排期名称：
	  <select name="everySalesDetailReportPage.params.planId" id="planId" class="m-sel">
		<option value="">全部排期</option>
		<c:forEach items="${plans }" var="plan">
			<option value="${plan.planId }"
				<c:if test="${everySalesDetailReportPage.params.planId==plan.planId }">
		selected = "selected"
		</c:if>>${plan.name}</option>
		</c:forEach>
	  </select>
      <aut:authorize url="/admin/report/report-search">
		<input type="button" class="m-btn" value="查询" id="search"/>
	  </aut:authorize>
	  <aut:authorize url="/admin/report/report-exportMonthReports">
		<input type="button" class="m-btn outer" value="导出" id="export"/>
	  </aut:authorize>
	  <div id="dateTip"></div>
	  </span>
    </div>
    <!--右侧显示分页控件 start-->
    <c:if test="${not empty everySalesDetailReportPage.result}">
		<div class="m-fr curr-num">
			<label>当前显示： </label> 
	        <wms:commPageSize page="${everySalesDetailReportPage}" beanName="everySalesDetailReportPage"
	        paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPageSize>
		</div>
    </c:if>
    <!--右侧显示分页控件 end-->
	</form>
	<c:if test="${everySalesDetailReportPage!=null}"> 
	<div class="m-mt10p tab-over">
	<table width="7820">
      <thead>
        <tr>
		  <th width="150">商家</th>
		  <th width="100">品牌</th>
		  <th width="90">商品品类</th>
		  <th width="90">子类</th>
		  <th width="350">商品名称</th>  
		  <th width="60">SKU</th>
		  <th width="60">国际码</th>
		  <th width="60">尺寸</th>
		  <th width="60">颜色</th>
		  <th width="100">特卖价</th>  
		  <th width="100">D1销售数量</th>
		  <th width="100">D1销售金额</th>
		  <th width="100">D2销售数量</th>
		  <th width="100">D2销售金额</th>
		  <th width="100">D3销售数量</th>
		  <th width="100">D3销售金额</th>
		  <th width="100">D4销售数量</th>
		  <th width="100">D4销售金额</th>
		  <th width="100">D5销售数量</th>
		  <th width="100">D5销售金额</th>
		  <th width="100">D6销售数量</th>
		  <th width="100">D6销售金额</th>
		  <th width="100">D7销售数量</th>
		  <th width="100">D7销售金额</th>
		  <th width="100">D8销售数量</th>
		  <th width="100">D8销售金额</th>
		  <th width="100">D9销售数量</th>
		  <th width="100">D9销售金额</th>
		  <th width="100">D10销售数量</th>
		  <th width="100">D10销售金额</th>
		  <th width="100">D11销售数量</th>
		  <th width="100">D11销售金额</th>
		  <th width="100">D12销售数量</th>
		  <th width="100">D12销售金额</th>
		  <th width="100">D13销售数量</th>
		  <th width="100">D13销售金额</th>
		  <th width="100">D14销售数量</th>
		  <th width="100">D14销售金额</th>
		  <th width="100">D15销售数量</th>
		  <th width="100">D15销售金额</th>
		  <th width="100">D16销售数量</th>
		  <th width="100">D16销售金额</th>
		  <th width="100">D17销售数量</th>
		  <th width="100">D17销售金额</th>
		  <th width="100">D18销售数量</th>
		  <th width="100">D18销售金额</th>
		  <th width="100">D19销售数量</th>
		  <th width="100">D19销售金额</th>
		  <th width="100">D20销售数量</th>
		  <th width="100">D20销售金额</th>
		  <th width="100">D21销售数量</th>
		  <th width="100">D21销售金额</th>
		  <th width="100">D22销售数量</th>
		  <th width="100">D22销售金额</th>
		  <th width="100">D23销售数量</th>
		  <th width="100">D23销售金额</th>
		  <th width="100">D24销售数量</th>
		  <th width="100">D24销售金额</th>
		  <th width="100">D25销售数量</th>
		  <th width="100">D25销售金额</th>
		  <th width="100">D26销售数量</th>
		  <th width="100">D26销售金额</th>
		  <th width="100">D27销售数量</th>
		  <th width="100">D27销售金额</th>
		  <th width="100">D28销售数量</th>
		  <th width="100">D28销售金额</th>
		  <th width="100">D29销售数量</th>
		  <th width="100">D29销售金额</th>
		  <th width="100">D30销售数量</th>
		  <th width="100">D30销售金额</th>
		  <th width="100">D31销售数量</th>
		  <th width="100">D31销售金额</th>		  
		  <th width="100">合计销售数量</th>
		  <th width="100">合计销售金额</th>  
		  <th width="150">上线日期</th>
		  <th width="150">下线日期</th>
        </tr>
      </thead>
      <tbody>
      	<c:forEach items="${everySalesDetailReportPage.result }" var="everySalesDetailReportPage" varStatus="status">
			<tr class="list-tr">
				<td>${everySalesDetailReportPage.companyName}</td>
				<td>${everySalesDetailReportPage.brandName}</td>
				<td>${everySalesDetailReportPage.categoryName}</td>
				<td></td>
				<td>${everySalesDetailReportPage.productName}</td>
				<td>${everySalesDetailReportPage.skuCode}</td>
				<td>${everySalesDetailReportPage.internationalCode}</td>
				<td>${everySalesDetailReportPage.size}</td>
				<td>${everySalesDetailReportPage.colour}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salePrice!=null}">&yen;</c:if>${everySalesDetailReportPage.salePrice}</span></td>
				<td>${everySalesDetailReportPage.salesNumD1}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD1!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD1}</span></td>
				<td>${everySalesDetailReportPage.salesNumD2}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD2!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD2}</span></td>
				<td>${everySalesDetailReportPage.salesNumD3}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD3!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD3}</span></td>
				<td>${everySalesDetailReportPage.salesNumD4}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD4!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD4}</span></td>
				<td>${everySalesDetailReportPage.salesNumD5}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD5!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD5}</span></td>
				<td>${everySalesDetailReportPage.salesNumD6}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD6!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD6}</span></td>
				<td>${everySalesDetailReportPage.salesNumD7}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD7!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD7}</span></td>
				<td>${everySalesDetailReportPage.salesNumD8}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD8!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD8}</span></td>
				<td>${everySalesDetailReportPage.salesNumD9}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD9!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD9}</span></td>
				<td>${everySalesDetailReportPage.salesNumD10}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD10!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD10}</span></td>
				<td>${everySalesDetailReportPage.salesNumD11}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD11!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD11}</span></td>
				<td>${everySalesDetailReportPage.salesNumD12}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD12!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD12}</span></td>
				<td>${everySalesDetailReportPage.salesNumD13}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD13!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD13}</span></td>
				<td>${everySalesDetailReportPage.salesNumD14}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD14!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD14}</span></td>
				<td>${everySalesDetailReportPage.salesNumD15}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD15!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD15}</span></td>
				<td>${everySalesDetailReportPage.salesNumD16}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD16!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD16}</span></td>
				<td>${everySalesDetailReportPage.salesNumD17}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD17!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD17}</span></td>
				<td>${everySalesDetailReportPage.salesNumD18}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD18!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD18}</span></td>
				<td>${everySalesDetailReportPage.salesNumD19}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD19!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD19}</span></td>
				<td>${everySalesDetailReportPage.salesNumD20}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD20!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD20}</span></td>
				<td>${everySalesDetailReportPage.salesNumD21}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD21!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD21}</span></td>
				<td>${everySalesDetailReportPage.salesNumD22}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD22!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD22}</span></td>
				<td>${everySalesDetailReportPage.salesNumD23}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD23!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD23}</span></td>
				<td>${everySalesDetailReportPage.salesNumD24}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD24!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD24}</span></td>
				<td>${everySalesDetailReportPage.salesNumD25}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD25!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD25}</span></td>
				<td>${everySalesDetailReportPage.salesNumD26}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD26!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD26}</span></td>
				<td>${everySalesDetailReportPage.salesNumD27}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD27!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD27}</span></td>
				<td>${everySalesDetailReportPage.salesNumD28}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD28!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD28}</span></td>
				<td>${everySalesDetailReportPage.salesNumD29}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD29!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD29}</span></td>
				<td>${everySalesDetailReportPage.salesNumD30}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD30!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD30}</span></td>
				<td>${everySalesDetailReportPage.salesNumD31}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesPriceD31!=null}">&yen;</c:if>${everySalesDetailReportPage.salesPriceD31}</span></td>
				<td>${everySalesDetailReportPage.salesTotalNum}</td>
				<td><span class="coin"><c:if test="${everySalesDetailReportPage.salesTotalPrice!=null}">&yen;</c:if>${everySalesDetailReportPage.salesTotalPrice}</span></td>
				<td><fmt:formatDate value='${everySalesDetailReportPage.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td><fmt:formatDate value='${everySalesDetailReportPage.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
			</tr>
		</c:forEach>
      </tbody>
    </table>
	</div>
	<!--分页插件 start -->
	<div class="pagination pagination-right">
		<c:if test="${not empty everySalesDetailReportPage.result}">
			<wms:commPage page="${everySalesDetailReportPage}" beanName="everySalesDetailReportPage"
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
<script>
$.formValidator.initConfig({formID:"searchForm",theme:"Default",errorFocus:false,wideWord:false});
$("#time").formValidator({tipID:"dateTip",onShow:"请选择时间",onFocus:"请选择时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"^\\d{4}(\\-|\\/|\.)\\d{1,2}$",dataType:"string",onError:"时间必填"});
</script>
</body>
</html>