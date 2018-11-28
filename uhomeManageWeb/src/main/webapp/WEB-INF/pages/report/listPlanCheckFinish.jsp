<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>结算管理</title>
<link href="${ _cssPath}/common.css" rel="stylesheet" />
<link href="${_jsPath }/plugin/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" />
<link href="${ _cssPath}/pages/reportForms.css" rel="stylesheet" />
<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
<script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
<script src="${_jsPath }/plugin/plugin.js"  language="javascript"></script>
<script src="${_jsPath }/pages/index.js"></script>
<script type="text/javascript" >
var nowDate='${nowDateTime}';
	var submitForm=function(){
		var timeVal=$('#time').val();
		if(timeVal=='' || timeVal>nowDate){
			return false;
		}else{
			return true;
		}
	};
	$(function(){
		$("#search").click(function(){
			var keywords = $("#name").val();
			if(keywords == $("#name").attr("data-default")){
				$("#name").val("");
			}
			$("#searchForm").submit();
		});
	});	 
</script>
</head>
<body>
	<jsp:include page="../include/header.jsp" flush="true" />
	<div class="body m-w980p m-mt10p">
	<jsp:include page="./planCheckMenuHeader.jsp" flush="true" />
		<div>
			<form class="m-clear m-mt10p" action="${_ctxPath}/admin/planCheck/planCheck-listPlanCheck.htm" class="m-clear m-mt10p" id="searchForm"  name="searchForm" method="post">
 				
 				<input type="hidden" name="planCheckPage.params.feedbackCount" value="${planCheckPage.params.feedbackCount}" />
 				 <input type="hidden" name="reportMenuId" value="${reportMenuId}" />
				<div class="m-fl">
		 		<span>特卖时间:</span>	    
 				<span class="position">
 					
					<input type="text" name="planCheckPage.params.startTime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"  readonly="readonly" value="${planCheckPage.params.startTime}" id="startTime"/>
					至 
					<input type="text" name="planCheckPage.params.endTime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" readonly="readonly" value="${planCheckPage.params.endTime}" id="endTime"/>
				
				</span>
				<select name="planCheckPage.params.planId" id="planId" class="m-sel">
					<option value="">选择特卖活动</option>
					<c:forEach items="${plans }" var="plan" varStatus="status">
						<option value="${plan.planId }"
							<c:if test="${planCheckPage.params.planId==plan.planId }">
					selected = "selected"
					</c:if>>${plan.name }</option>
					</c:forEach>
				</select>
				
					<select name="planCheckPage.params.sellerId" id="sellerId" class="m-sel">
						<option value="">所属商家</option>
						<c:forEach items="${sellers }" var="seller" varStatus="status">
							<option value="${seller.sellerId }"
								<c:if test="${planCheckPage.params.sellerId==seller.sellerId }">
						selected = "selected"
						</c:if>>${seller.companyName}</option>
						</c:forEach>
					</select>
					
					  <input name="planCheckPage.params.name" id="name" value="${planCheckPage.params.name }" type="text" class="txt-input input-marks" data-default="输入活动名称，商家名称进行搜索"/>
					
						<aut:authorize url="/admin/planCheck/planCheck-listPlanCheck">
						<input type="button" class="m-btn" value="查询" id="search"/>
						</aut:authorize>
				</div>
				 <c:if test="${not empty planCheckPage.result}">
					<div class="m-fr curr-num">
						<label>当前显示： </label> 
		               		<wms:commPageSize page="${planCheckPage}" beanName="planCheckPage"  paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPageSize>
					</div>
	            </c:if>
			</form>
			<c:if test="${planCheckPage!=null}"> 
				<div class="m-mt10p">
					<table class="tab-control tab-report">
			      <thead>
			        <tr>
			          <th width="19%">特卖活动名称</th>
			          <th width="16%">特卖时间</th>
			          <th width="15%">所属商家</th>
			          <th width="15%">结束时间</th>
 			          <th width="10%">对账申请状态</th>
 			        </tr>
			      </thead>
			      <tbody>
			      
			      <c:set var="tempPlanId" value="-1" scope="request" />
			  
			      <c:forEach items="${planCheckPage.result }" var="planCheck" varStatus="status">
			     	  
  					<tr>
 						<c:choose>
							<c:when test="${planCheck.tempPlanId != tempPlanId}">
								<td rowspan="${planCheck.sellerNum }">${planCheck.name }</td>
					      		<td rowspan="${planCheck.sellerNum }"><fmt:formatDate value='${planCheck.startTime}' pattern='yyyy.MM.dd'/>-<fmt:formatDate value='${planCheck.endTime}' pattern='yyyy.MM.dd'/></td>
				      		</c:when>
						</c:choose>
			      		
			      		<td>${planCheck.companyName }</td>
 			      		<td><fmt:formatDate value='${planCheck.feedbackTime}' pattern='yyyy.MM.dd HH:mm:ss'/></td>
			      		<td>二次对账结束</td>
			      	 
			      	</tr>
			      	 <c:set var="tempPlanId" value="${planCheck.tempPlanId	}" scope="request" />
					</c:forEach>
			      </tbody>
			    </table>
				</div>	
				<div class="pagination pagination-right">
					<c:if test="${not empty planCheckPage.result}">
						<wms:commPage page="${planCheckPage}" beanName="planCheckPage" paramName="reportMenuId" paramValue="${reportMenuId}"></wms:commPage>
					</c:if>
				</div>  
			</c:if>
		</div>
	</div>
	
 </body>
</html>