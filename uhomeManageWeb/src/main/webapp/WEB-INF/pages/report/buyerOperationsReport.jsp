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
    <script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
    <script type="text/javascript" >
	    $(function(){
			$('#search').bind('click', function() {
				var result = $.formValidator.pageIsValid('1');
				if(!result){
					return false;
				}
				$("#searchForm").attr("action","${_ctxPath}/admin/report/operationsReport-searchBuyerOperationsReport.htm");
				$("#searchForm").submit();
			});
			
			$('#export').bind('click', function() {
				var result = $.formValidator.pageIsValid('1');
				if(!result){
					return false;
				}
				var url = '${_ctxPath}/admin/report/operationsReport-exportBuyerOperationsReport.htm';
				if ($('#startTime').val() != '' && $('#startTime').val() != null) {
					if(url.indexOf("?")!=-1){
						url = url + '&buyerOperationsReportPage.params.startTime=' + $('#startTime').val();
					}else{
						url = url + '?buyerOperationsReportPage.params.startTime=' + $('#startTime').val();
					}
				}
				if(url.indexOf("?")!=-1){
					url = url + '&buyerOperationsReportPage.params.endTime=' + $('#endTime').val();
				}else{
					url = url + '?buyerOperationsReportPage.params.endTime=' + $('#endTime').val();
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
	<jsp:include page="./operationsReportMenu.jsp" flush="true" />
 
  <form class="m-clear m-mt10p" id="searchForm" method="post">
  <!-- 此处用于报表菜单选择 -->
			<input type="hidden" name="reportMenuId" value="${reportMenuId}" />
    <div class="m-fl">
    	<span class="position">
    		<span class="c-red">*</span>
	  		<input type="text" class="Wdate" name="buyerOperationsReportPage.params.startTime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){endTime.focus();}})" value="${buyerOperationsReportPage.params.startTime }" id="startTime"/>
	   		至 <span class="c-red">*</span>
      	<input type="text" class="Wdate" name="buyerOperationsReportPage.params.endTime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd',readOnly:true})" value="${buyerOperationsReportPage.params.endTime }" id="endTime"/>    
      	<aut:authorize url="/admin/report/report-search">
				<input type="button" class="m-btn" value="查询" id="search"/>
			  </aut:authorize>
			  <aut:authorize url="/admin/report/report-exportMonthReports">
				<input type="button" class="m-btn outer" value="导出" id="export"/>
			  </aut:authorize>
      	<div id="dateTip"></div>
      </span>
    </div>
	</form>
	<c:if test="${buyerOperationsReport != null}"> 
	<div class="m-mt10p">
		<table class="tab-control">
      <thead>
        <tr>
		  <th width="16%">周期</th>
		  <th width="8%">新增买家</th>
		  <th width="8%">买家总数</th>
		  <th width="8%">新增注册会员</th>
		  <th width="8%">网站会员总数</th>
		  <th width="16%">2次购买以上的买家人数 </th>
		  <th width="8%">复购率</th>
        </tr>
      </thead>
      <tbody>
		<tr class="list-tr">
			<td>${buyerOperationsReport.period}</td>
			<td>${buyerOperationsReport.newBuyers}</td>
			<td>${buyerOperationsReport.buyers}</td>
			<td>${buyerOperationsReport.newUsers}</td>
			<td>${buyerOperationsReport.users}</td>
			<td>${buyerOperationsReport.moreBuy}</td>
			<td>${buyerOperationsReport.repeatBuy}</td>
		</tr>
      </tbody>
    </table>
	</div>
	</c:if>
</div>
<!--end body-->
<!--start footer-->
<!--end footer-->
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<script type="text/javascript">
	$.formValidator.initConfig({formID:"searchForm",theme:"Default",errorFocus:false,wideWord:false});
	$("#startTime").formValidator({tipID:"dateTip",onShow:"请选择开始时间",onFocus:"请选择开始时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"开始时间必填"});
	$("#endTime").formValidator({tipID:"dateTip",onShow:"请选择结束时间",onFocus:"请选择结束时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"结束时间必填"});
</script>
</body>
</html>