<%@ tag pageEncoding="UTF-8" import="com.ytoxl.module.core.common.pagination.BasePagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ attribute name="page" required="true" type="com.ytoxl.module.core.common.pagination.BasePagination" description="分页类对象"%>
<%@ attribute name="urlStart" required="true" type="java.lang.String" description="翻页url开始"%>
<%@ attribute name="urlEnd" required="true" type="java.lang.String" description="翻页url结束"%>

<c:if test="${not empty page.displayIndexs}">
<div class="navigation">	
		
	<span class="prev<c:if test='${empty page.beforePage }'>-disable </c:if>">
		<c:if test="${empty page.beforePage }">上一页</c:if>
		<c:if test="${!empty page.beforePage }"><a href="${urlStart}${page.beforePage}${urlEnd}" class="prev_link" >上一页</a></c:if>
	</span>

	<c:if test="${page.displayIndexs[0]>0}">
		<span><a href="${urlStart}0${urlEnd}" class="fy_page">1</a></span>
	</c:if>
	<c:if test="${page.displayIndexs[0]>1}">
		<span>...</span>
	</c:if>
	<c:forEach items="${page.displayIndexs }" var="index">
		<span class="<c:if test="${(page.currentPage) == index }">cur</c:if>"><a href="${urlStart}${index}${urlEnd}" >${index+1}</a></span>
	</c:forEach>	
	<c:if test="${page.displayIndexs[page.displayIndexsLen-1]<page.totalPage-2}">
		<span>...</span>
	</c:if>	
	<c:if test="${page.displayIndexs[page.displayIndexsLen-1]<page.totalPage-1}">
		<span><a href="${urlStart}${page.totalPage - 1}${urlEnd}" class="fy_page">${page.totalPage}</a></span>
	</c:if>
	<span class="next<c:if test='${empty page.nextPage }'>-disable</c:if>">
	    <c:if test="${empty page.nextPage }">下一页</c:if>
		<c:if test="${!empty page.nextPage }"><a href="${urlStart}${page.nextPage}${urlEnd}" class="next_page">下一页</a></c:if>
	</span>
	
    <span class="fy_total">
                共<i>${page.totalPage}</i>页<i>${page.total}</i>条
                到 <input type="text" class="reach_page" id="pageIndex" value="${page.currentPage + 1 }" onfocus="this.value='';" maxLength="8" />页
    </span>
    <input type="button" value="确定" class="go_page" id="go_page" onclick="window.location = '${urlStart}' + (document.getElementById('pageIndex').value - 1 )+'${urlEnd}';" />
</div>   
</c:if>