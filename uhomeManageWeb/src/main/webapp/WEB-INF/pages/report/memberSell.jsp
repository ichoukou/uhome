<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${ _cssPath}/common.css" rel="stylesheet" />
    <link href="${ _cssPath}/pages/reportForms.css" rel="stylesheet" />
    <link href="${ _cssPath}/pages/arrangement.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>

	<title>会员销售管理明细</title>
	<script type="text/javascript">
		function submitForm(value){
		 
			if(value == 1){
				document.memberForm.action = "${_ctxPath }/admin/report/productReport-searchMemberSellInfo.htm";
			}else{
				document.memberForm.action = "${_ctxPath }/admin/report/productReport-exportMemberSellReports.htm";
			}
			document.memberForm.submit();
		}
	</script>
</head>
<body>
	<jsp:include page="../include/header.jsp" flush="true" />
	<div class="body m-w980p m-mt10p">
		<!-- reportMenu -->
		<jsp:include page="./reportMenuHeader.jsp" flush="true" />
	  <form class="m-clear m-mt10p" method="post"  id="memberForm" name="memberForm">
	  		<!-- 此处用于报表菜单选择 -->
			<input type="hidden" name="reportMenuId" value="${reportMenuId}" />
	    <div class="m-fl">
	    	<select class="m-sel" name="memberReportPage.params.brandId" id="brandId">
				<option value="">全部品牌</option>
				<c:forEach items="${brands }" var="brand" varStatus="status">
					<option value="${brand.brandId }" <c:if test="${memberReportPage.params.brandId==brand.brandId }">selected = "selected"</c:if>>
						${brand.name}
					</option>
				</c:forEach>
			</select>
			<span class="position">
	    	<input type="text" id="begin" readonly="readonly" class="Wdate" value="${memberReportPage.params.beginTime}" onfocus="WdatePicker(({maxDate:'#F{$dp.$D(\'end\')}'}))" name="memberReportPage.params.beginTime"/> 至
	    	<input type="text" id="end" class="Wdate" readonly="readonly" value="${memberReportPage.params.endTime}" onfocus="WdatePicker(({minDate:'#F{$dp.$D(\'begin\')}'}))" name="memberReportPage.params.endTime"/>
	   	</span>
	     	<input type="button" class="m-btn" value="查询" onclick="submitForm(1)"/>
	        <input type="button" class="m-btn outer" onclick="submitForm(2)" value="导出"/>
	    </div>
	    <!--右侧显示分页控件 start-->
	    <c:if test="${not empty memberReportPage.result}">
			<div class="m-fr curr-num">
				<label>当前显示： </label> 
		          <wms:commPageSize page="${memberReportPage}" beanName="memberReportPage"
		          paramName="reportMenuId" paramValue="${reportMenuId}" ></wms:commPageSize>
				</div>
	     </c:if>
		</form>
		<div class="m-mt10p">
			<c:if test="${memberReportPage !=null}">
				<table class="tab-control">
			      <thead>
			        <tr>
			          <th width="11%">登录名</th>
			          <th width="11%">品牌</th>
			          <th width="17%">订单数</th>
			          <th width="11%">性别</th>
			          <th width="17%">购买金额</th>
			        </tr>
			      </thead>
			      <tbody>
			       <c:forEach items="${memberReportPage.result }" var="member">
				   	<tr class="list-tr">
				      <td>${member.memberName }</td>
				      <td>${member.brandName }</td>
				      <td>${member.orderNum }</td>
				      <td>${member.gender}</td>
				      <td>${member.totalPrice }</td>
				    </tr>
			      	</c:forEach>
			      </tbody>
		    	</table>
		    </c:if>	
		</div>
		 <div class="pagination pagination-right">
			<c:if test="${not empty memberReportPage.result}">
				<wms:commPage page="${memberReportPage}" beanName="memberReportPage"
				paramName="reportMenuId" paramValue="${reportMenuId}" ></wms:commPage>
			</c:if>
		</div>
	</div>
</body>
</html>