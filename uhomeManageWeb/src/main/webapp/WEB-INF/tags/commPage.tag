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
	<c:url var="url" value="?">
		<c:forEach items="${page.params }" var="map">	    
		    <c:param name="${beanName}.params.${map.key}">${map.value}</c:param>			
		</c:forEach>
		<c:if test="${not empty paramName and not empty paramValue }">
		<c:param name="${paramName }">${paramValue }</c:param>
		</c:if>
	</c:url>
	<c:set var="url"
		value="${url}${beanName}.total=${page.total}&${beanName}.limit=${page.limit}&${beanName}.sort=${page.sort}&${beanName}.dir=${page.dir}&${beanName}.currentPage="
		scope="page" />
	<c:if test="${not empty actionUrl}">
		<c:set var="url" value="${actionUrl}${url}"/>
	</c:if>
	<div class="pagination">	
		<wms:basePage urlEnd="" urlStart="${url}" page="${page}"></wms:basePage>	
	    <span>
	                共<i>${page.totalPage}</i>页<i>${page.total}</i>条
	                到 <input type="text" class="goto-num" id="pageIndex" value="${page.currentPage + 1 }" onfocus="this.value='';" maxLength="8" />页
	    </span>
	    <input type="button" value="转到" class="goto" id="go_page" onclick="window.location = '${url}' + (document.getElementById('pageIndex').value - 1 );" />
	</div>
</c:if>
