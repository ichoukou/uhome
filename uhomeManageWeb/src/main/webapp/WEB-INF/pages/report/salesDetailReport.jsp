<!doctype html>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
    <meta charset="utf-8"/>
    <title>商家销售管理</title>
    <link href="${ _cssPath}/common.css" rel="stylesheet" />
    <link href="${ _cssPagesPath}/reportForms.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <link href="${_jsPath }/plugin/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" />
    <script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" >
    
    function ajaxValue(value,brandId){
       $.get("${_ctxPath}/admin/report/productReport-getBrandsBySeller.htm?sellerId="+value+"&brandId="+brandId+"&time="+new Date().getTime(),function(data){
    		$("#brandId").empty();
    		$("#brandId").prepend("<option value=''>全部品牌</option>"); 
    		 var result="";
		   		$.each(data,function(i,brands){ 
		   		   
		   		   if( brandId ==brands.brandId ){
						result = result +  "<option selected = 'selected' value='"+brands.brandId+"'>"+brands.name+"</option>";
					}else{
						result = result +  "<option value='"+brands.brandId+"'>"+brands.name+"</option>";
					}
		   		} )
        	$("#brandId").append(result);
        },"json" ); 
    }
    
    $(function(){        
  	  ajaxValue('${salesDetailReportPage.params.sellerId}','${salesDetailReportPage.params.brandId}');
	});

    $(function(){ 
		$('#export').bind('click', function() {
			var url = '${_ctxPath}/admin/report/productReport-exportSalesDetailReports.htm';
			if ($('#sellerId').val() != '' && $('#sellerId').val() != null) {
				url = url + '?salesDetailReportPage.params.sellerId=' + $('#sellerId').val();
			}
			if ($('#brandId').val() != '' && $('#brandId').val() != null) {
				if(url.indexOf("?")!=-1){
					url = url + '&salesDetailReportPage.params.brandId=' + $('#brandId').val();
				}else{
					url = url + '?salesDetailReportPage.params.brandId=' + $('#brandId').val();
				}
			}
			if ($('#startTime').val() != '' && $('#startTime').val() != null) {
				if(url.indexOf("?")!=-1){
					url = url + '&salesDetailReportPage.params.startTime=' + $('#startTime').val();
				}else{
					url = url + '?salesDetailReportPage.params.startTime=' + $('#startTime').val();
				}
			}
			if(url.indexOf("?")!=-1){
				url = url + '&salesDetailReportPage.params.endTime=' + $('#endTime').val();
			}else{
				url = url + '?salesDetailReportPage.params.endTime=' + $('#endTime').val();
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
 
  <form action="${_ctxPath}/admin/report/productReport-searchSalesDetailReports.htm" class="m-clear m-mt10p" id="searchForm" method="post">
  <!-- 此处用于报表菜单选择 -->
			<input type="hidden" name="reportMenuId" value="${reportMenuId}" />
    <div class="m-fl">
      <select name="salesDetailReportPage.params.sellerId" id="sellerId" class="m-sel" onchange="ajaxValue(this.value,'');">
		<option value="">全部商家</option>
		<c:forEach items="${sellers }" var="seller" varStatus="status">
			<option value="${seller.sellerId }" <c:if test="${salesDetailReportPage.params.sellerId==seller.sellerId }">selected = "selected"</c:if>>
				${seller.companyName}
			</option>
		</c:forEach>
	  </select>
      <select name="salesDetailReportPage.params.brandId" id="brandId" class="m-sel">
		<option value="">全部品牌</option>
		<c:forEach items="${brands }" var="brand">
			<option value="${brand.brandId }"
				<c:if test="${salesDetailReportPage.params.brandId==brand.brandId }">
		selected = "selected"
		</c:if>>${brand.name}</option>
		</c:forEach>
	  </select>
	  <input type="text" class="Wdate" name="salesDetailReportPage.params.startTime" 
	  	onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd',readOnly:true})" 
	    value="${salesDetailReportPage.params.startTime }" id="startTime"/>
	   	至
      <input type="text" class="Wdate" name="salesDetailReportPage.params.endTime" 
      	onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd',readOnly:true})" 
        value="${salesDetailReportPage.params.endTime }" id="endTime"/>    
      <aut:authorize url="/admin/report/report-search">
		<input type="submit" class="m-btn" value="查询" id="search"/>
	  </aut:authorize>
	  <aut:authorize url="/admin/report/report-exportMonthReports">
		<input type="button" class="m-btn outer" value="导出" id="export"/>
	  </aut:authorize>
    </div>
    <!--右侧显示分页控件 start-->
    <c:if test="${not empty salesDetailReportPage.result}">
		<div class="m-fr curr-num">
			<label>当前显示： </label> 
	        <wms:commPageSize page="${salesDetailReportPage}" beanName="salesDetailReportPage"
	        paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPageSize>
		</div>
    </c:if>
    <!--右侧显示分页控件 end-->
	</form>
	<c:if test="${salesDetailReportPage!=null}"> 
	<div class="m-mt10p">
		<table class="tab-control">
      <thead>
        <tr>
		  <th width="16%">商家</th>
		  <th width="16%">品牌</th>
		  <th width="16%">分类</th>
		  <th width="16%">上线日期</th>
		  <th width="16%">下线日期</th>
		  <th width="8%">销售数量</th>
		  <th width="8%">销售金额</th>
        </tr>
      </thead>
      <tbody>
      	<c:forEach items="${salesDetailReportPage.result }" var="salesDetailReport" varStatus="status">
			<tr class="list-tr">
				<td>${salesDetailReport.companyName}</td>
				<td>${salesDetailReport.brandName}</td>
				<td>${salesDetailReport.categoryName}</td>
				<td><fmt:formatDate value='${salesDetailReport.planStartTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td><fmt:formatDate value='${salesDetailReport.planEndTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
				<td>${salesDetailReport.salesNum}</td>
				<td><span class="coin"><c:if test="${salesDetailReport.salesPrice!=null}">&yen;</c:if>${salesDetailReport.salesPrice}</span></td>
			</tr>
		</c:forEach>
      </tbody>
    </table>
	</div>
	<!--分页插件 start -->
	<div class="pagination pagination-right">
		<c:if test="${not empty salesDetailReportPage.result}">
			<wms:commPage page="${salesDetailReportPage}" beanName="salesDetailReportPage"
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