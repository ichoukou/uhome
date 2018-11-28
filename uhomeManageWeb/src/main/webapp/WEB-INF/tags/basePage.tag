<%@ tag pageEncoding="UTF-8" import="com.ytoxl.module.core.common.pagination.BasePagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ attribute name="page" required="true" type="com.ytoxl.module.core.common.pagination.BasePagination" description="分页类对象"%>
<%@ attribute name="urlStart" required="true" type="java.lang.String" description="翻页url开始"%>
<%@ attribute name="urlEnd" required="true" type="java.lang.String" description="翻页url结束"%>

<c:if test="${not empty page.displayIndexs}">
	<a  <c:if test="${empty page.beforePage }">class="prev_no" href="javascript:; "</c:if>
	    <c:if test="${!empty page.beforePage }">href="${urlStart}${page.beforePage}${urlEnd}" class="prev_link" </c:if> >
	    <span class="disable"></span>
	</a>
	<c:if test="${page.displayIndexs[0]>0}">
		<a href="${urlStart}0${urlEnd}" >1</a>
	</c:if>
	<c:if test="${page.displayIndexs[0]>1}">
		<span class="etc">...</span>
	</c:if>
	<c:forEach items="${page.displayIndexs }" var="index">
		<a href="${urlStart}${index}${urlEnd}" ><span class="<c:if test="${(page.currentPage) == index }">current</c:if>">${index+1}</span></a>
	</c:forEach>
	<c:if test="${page.displayIndexs[page.displayIndexsLen-1]<page.totalPage-2}">
		<span class="etc">...</span>
	</c:if>	
	<c:if test="${page.displayIndexs[page.displayIndexsLen-1]<page.totalPage-1}">
		<a href="${urlStart}${page.totalPage - 1}${urlEnd}" class="fy_page"><span>${page.totalPage}</span></a>
	</c:if>
	<a <c:if test="${empty page.nextPage }"> href="javascript:; " </c:if> <c:if test="${!empty page.nextPage }">href="${urlStart}${page.nextPage}${urlEnd}"</c:if> >
	   <span class="next"></span>
	</a>
</c:if>
