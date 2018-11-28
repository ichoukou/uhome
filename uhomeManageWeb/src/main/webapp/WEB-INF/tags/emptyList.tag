<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<%@ attribute name="list" required="true" type="java.util.Collection" description="List集合"%>
<%@ attribute name="colspan" required="true" type="java.lang.String" description="合并列数"%>
	<c:if test="${ list!=null && fn:length(list)==0}">
	  <tr>
	     <td colspan="${colspan}" align="center" style="background-color: white;" onmouseout="void(0);" onmouseover="void(0);">
	     	    <img src="${_imagesPath}/single/guai.jpg" style="margin-top: 50px;"/>
	     </td>
	  </tr>
	</c:if>

	
	