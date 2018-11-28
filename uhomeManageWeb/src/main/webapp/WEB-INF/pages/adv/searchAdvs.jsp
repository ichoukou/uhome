<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>广告管理</title>
	<link href="${ _cssPath}/common.css" rel="stylesheet" />
	<link href="${ _cssPath}/pages/advertise.css" rel="stylesheet" />
	<link href="${_jsPath }/plugin/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" />
	<script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
	<script src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
	<script src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js" language="javascript"></script>
	<script src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js" language="javascript"></script>
	<script src="${_jsPath }/pages/searchAdvs.js" language="javascript"></script>
</head>
<body>
<jsp:include page="../include/header.jsp"  flush="true" />
<div class="body m-w980p">
<jsp:include page="../include/pageManageMenu.jsp"  flush="true" />
    <!--start form-->
    <form action="${_ctxPath}/admin/adv/adv_searchAdvs.htm" method="post" id="search-form" class="m-clear m-mt10p">
            <div class="m-fl">
              <select name="advPage.params.code" class="m-sel">
							<option value="">全部</option>
							<c:forEach items="${advPositions}" var="value">
								<option value="${value.code}"
									<c:if test="${advPage.params.code==value.code}"> selected="selected"</c:if>>
									<spring:message code="${value.name}" text="" />
									<c:out value="${value.name}"></c:out>
								</option>
							</c:forEach>
						</select>
						<input type="submit" class="m-btn" value="查询"/>
						<aut:authorize url="/admin/adv/adv_add">
            	<input type="button" class="m-btn" value="添加广告" id="addAdv" onclick="clickAddOrEdit()"/>
            	</aut:authorize>
            </div>
            <c:if test="${not empty advPage.result}">
	            <div class="m-fr curr-num">
								<label>当前显示： </label> 
				        <wms:commPageSize page="${advPage}" beanName="advPage"></wms:commPageSize>
							</div>
            </c:if>
        </form>
       <!--end form-->
       <div class="examine layout">
<!--             <div class="pagination pagination-right"> -->
<%-- 							<c:if test="${not empty advPage.result}"> --%>
<%-- 								<wms:commPage page="${advPage}" beanName="advPage"></wms:commPage> --%>
<%-- 							</c:if> --%>
<!-- 					   </div> -->
<!-- 					   <div class="clear"></div> -->
        </div>
         <!--start 广告管理table-->
        <div class="m-mt10p advertise-manage">
            <table class="tab-control" id="tab">
                <thead>
                    <tr>
                        <th width="25%">广告名称</th>
                        <th width="15%">预览广告</th>
                        <th width="20%">广告开始时间</th>
                        <th width="20%">广告结束时间</th>
                        <th width="10%">顺序</th>
                        <th width="10%">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${advPage.result}" var="adv" varStatus="status">
					<tr class="list-tr">
						<td >
							<a class="c-blue" 
							<aut:authorize url="/admin/adv/adv_editAdv">
							onclick="clickAddOrEdit(${adv.advertisementId})"
							</aut:authorize>
							> ${adv.name}<c:if test="${adv.isDefault==1}">(备用)</c:if></a></td>
						<td ><a class="preview" onclick="imgView('${adv.imageUrl}', ${adv.advPosition.width }, ${adv.advPosition.height })">预览</a></td>
						<td ><fmt:formatDate value='${adv.startTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
						<td ><fmt:formatDate value='${adv.endTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
						<td >${adv.rank}</td>
						<td >
							<aut:authorize url="/admin/adv/adv_edit">
							<a href="javascript:void(0);" class="c-blue" id="edit" onclick="clickAddOrEdit(${adv.advertisementId})">编辑</a>
							</aut:authorize>
						</td>
					</tr>
				</c:forEach>
                </tbody>
            </table>
        </div>
       <!--end 广告管理table-->
       <div class="pagination pagination-right">
			<c:if test="${not empty advPage.result}">
				<wms:commPage page="${advPage}" beanName="advPage"></wms:commPage>
			</c:if>
	   </div>
</div>
<!--end body-->
<!--start footer-->
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<!--end footer-->
</body>
</html>