<%@ tag pageEncoding="UTF-8" import="com.ytoxl.module.core.common.pagination.BasePagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="wms" tagdir="/WEB-INF/tags"%>

<%@ attribute name="page" required="true" type="com.ytoxl.module.core.common.pagination.BasePagination" description="分页类对象"%>
<%@ attribute name="beanName" required="false" type="java.lang.String" description="Action中BasePagination定义的名称"%>
<%@ attribute name="actionUrl" required="false" type="java.lang.String" description="查询Action路径"%>
<%@ attribute name="paramName" required="false" type="java.lang.String" description="自定义参数名"%>
<%@ attribute name="paramValue" required="false" type="java.lang.String" description="自定义参数值"%>
<c:if test="${not empty page}">
	<c:if test="${empty beanName}">
	    <c:set var="beanName" value="page"/>
	</c:if>
	<c:set var="url" value="?"/>
	<c:forEach items="${page.params}" var="map">
	    <c:set var="url" value="${url}${beanName}.params.${map.key}=${map.value}&"/>
	</c:forEach>
	<c:if test="${not empty paramName and not empty paramValue }">
		<c:set var="url" value="${url }${paramName }=${paramValue }&"/>
	</c:if>
	
	<c:set var="url"
	    value="${url}${beanName}.total=${page.total}&${beanName}.sort=${page.sort}&${beanName}.dir=${page.dir}&${beanName}.limit="
	    scope="page" />
	<c:if test="${not empty actionUrl}">
	    <c:set var="url" value="${actionUrl}${url}"/>
	</c:if>

<!-- 	<div style="float:right"> -->
	 <select name="${beanName}.limit" class="m-sel" onchange="window.location = '${url}' + this.value;">
<!-- 	     <option value="20">-每页显示条数-</option> -->
	     <c:forEach var="i" items="20,50,100,200,500">
	         <option value="${i}"
	             <c:if test="${i==page.limit}">selected=selected</c:if>
	         >每页${i}条</option>
	     </c:forEach>
	 </select>
<!-- 	 </div> -->
 </c:if>

