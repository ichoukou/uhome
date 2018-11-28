<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="zx" tagdir="/WEB-INF/tags"%>

<%@ attribute name="originalPath" required="true" type="java.lang.String" description="原图片路径"%>
<%@ attribute name="imageType" required="false" type="java.lang.String" description="缩略图片类型"%>
<c:if test="${not empty originalPath }">
	<c:set var="suffix" value="${fn:split(originalPath,'.')[1] }"></c:set>
</c:if>
<c:if test="${not empty originalPath }">
	<c:if test="${not empty imageType }">
		${_fileThumbPath }${originalPath }_${imageType }.${suffix }
	</c:if>	
</c:if>




