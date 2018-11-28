<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>报表管理</title>
<link href="${ _cssPath}/common.css" rel="stylesheet" />
<link href="${_jsPath }/plugin/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" />
<link href="${ _cssPath}/pages/reportForms.css" rel="stylesheet" />
<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
<script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
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
	  ajaxValue('${monthReportPage.params.sellerId}','${monthReportPage.params.brandId}');
});
	/* var nowDate='${nowDateTime}';
	var submitForm=function(){
		var timeVal=$('#time').val();
		if(timeVal=='' || timeVal>nowDate){
			return false;
		}else{
			return true;
		}
	}; */
	$(function(){
		$('#search').bind('click', function() {
			var result = $.formValidator.pageIsValid('1');
			if(!result){
				return false;
			}
			$("#searchForm").attr("action","${_ctxPath}/admin/report/report-searchMonthReports.htm");
			$("#searchForm").submit();
		});
		$('#export').bind('click', function() {
			var result = $.formValidator.pageIsValid('1');
			if(!result){
				return false;
			}
			$("#searchForm").attr("action","${_ctxPath}/admin/report/report-exportMonthReports.htm");
			$("#searchForm").submit();
		});
	});
</script>
</head>
<body>
	<jsp:include page="../include/header.jsp" flush="true" />
	<div class="body m-w980p m-mt10p">
	<!-- 加载报表一级菜单 -->
	<jsp:include page="./reportRootMenu.jsp" flush="true" />
 	<aut:authorize url="/admin/report/report-productMonthReport">
		<div>
			<form class="m-clear m-mt10p" id="searchForm" method="post">
				<div class="m-fl">
					<select name="monthReportPage.params.sellerId" id="sellerId" class="m-sel" onchange="ajaxValue(this.value,'');">
								<option value="">全部商家</option>
								<c:forEach items="${sellers }" var="seller" varStatus="status">
									<option value="${seller.sellerId }"
										<c:if test="${monthReportPage.params.sellerId==seller.sellerId }">
								selected = "selected"
								</c:if>>${seller.companyName
										}</option>
								</c:forEach>
							</select>
						<select name="monthReportPage.params.brandId" id="brandId" class="m-sel">
								<option value="">全部品牌</option>
								<c:forEach items="${brands }" var="brand">
									<option value="${brand.brandId }"
										<c:if test="${monthReportPage.params.brandId==brand.brandId }">
								selected = "selected"
								</c:if>>${brand.name}</option>
								</c:forEach>
							</select>
							<input type="text" name="monthReportPage.params.time" onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'${nowDateTime}'})" class="Wdate" readonly="readonly" value="${monthReportPage.params.time}" id="time"/>
						<aut:authorize url="/admin/report/report-search">
						<input type="button" class="m-btn" value="查询" id="search"/>
						</aut:authorize>
						<aut:authorize url="/admin/report/report-exportMonthReports">
						<input type="button" class="m-btn outer" value="导出" id="export"/>
						</aut:authorize>
						<div id="dateTip"></div>
				</div>
				<c:if test="${not empty monthReportPage.result}">
					<div class="m-fr curr-num">
						<label>当前显示： </label> 
		               		<wms:commPageSize page="${monthReportPage}" beanName="monthReportPage"></wms:commPageSize>
					</div>
	            </c:if>
			</form>
			<c:if test="${monthReport!=null}">
				<div class="report m-mt10p m-clear">
					<h1>新龙直销  ${monthReportPage.params.time} 月结报表</h1>
					<ul>
						<li><span>成功订单</span><b class="c-red">${monthReport.sellOrderCount }</b>笔</li>
						<li><span>退货订单</span><b class="c-red">${monthReport.returnOrderCount }</b>笔</li>
						<li><span>销售总额</span><b class="c-red coin">&yen;${monthReport.sellOrderAmount }</b>元</li>
						<li><span>退货总额</span><b class="c-red coin">&yen;${monthReport.returnOrderAmount }</b>元</li>
					</ul>
					<div class="pay-total">
						<span>应付总额</span><b class="c-red coin">&yen;${monthReport.sellOrderAmount - monthReport.returnOrderAmount}</b>元
					</div>
				</div>
				<div class="m-mt10p">
					<table class="tab-control" id="tab">
						<thead>
							<tr>
								<th width="11%">订单编号</th>
								<th width="11%">订单类型</th>
								<th width="17%">订单完成时间</th>
								<th width="11%">品牌</th>
								<th width="17%">商品名称</th>
								<th width="8%">单价</th>
								<th width="8%">优惠金额</th>
								<th width="8%">数量</th>
								<th width="9%">金额</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${monthReportPage.result }" var="monthReport" varStatus="status">
								<tr class="list-tr">
									<td>${monthReport.orderNo }</td>
									<td><spring:message	code="order.type.${monthReport.type}" /></td>
									<td><fmt:formatDate value='${monthReport.payTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
									<td>${monthReport.brandName}</td>
									<td>${monthReport.productName}
									</td>
									<td><span class="coin">&yen;${monthReport.closingCost}</span></td>
									<td>
									<c:choose>
										<c:when test="${monthReport.allowance > 0}"><span class="coin">&yen;${monthReport.allowance}</span></c:when>
										<c:otherwise>无优惠</c:otherwise>
									</c:choose>
									</td>
									<td>${monthReport.num}</td>
									<td><span class="coin">&yen;${monthReport.totalPrice}</span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				 <div class="pagination pagination-right">
					<c:if test="${not empty monthReportPage.result}">
						<wms:commPage page="${monthReportPage}" beanName="monthReportPage"></wms:commPage>
					</c:if>
				</div>
			</c:if>
		</div>
		</aut:authorize>
	</div>
	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
	<script>
	$.formValidator.initConfig({formID:"searchForm",theme:"Default",errorFocus:false,wideWord:false});
	$("#time").formValidator({tipID:"dateTip",onShow:"请选择月份",onFocus:"请选择月份",onCorrect:"时间选择正确"}).inputValidator({min:1,onError:"月份必须选择"});
	</script>
</body>
</html>