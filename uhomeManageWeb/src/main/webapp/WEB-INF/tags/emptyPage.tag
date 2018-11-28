<%@ tag pageEncoding="UTF-8" import="com.ytoxl.module.core.common.pagination.BasePagination"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<%@ attribute name="page" required="true" type="com.ytoxl.module.core.common.pagination.BasePagination" description="Action中BasePagination定义的名称"%>
<%@ attribute name="colspan" required="true" type="java.lang.String" description="合并列数"%>
	<c:if test="${ page!=null && fn:length(page.result)==0}">
	  <tr>
	     <td colspan="${colspan}" align="center" style="background-color: white;" onmouseout="void(0);" onmouseover="void(0);">
	     	    <img src="${_imagesPath}/single/guai.jpg" style="margin-top: 50px;"/>
	     </td>
	  </tr>
	</c:if>

	
	