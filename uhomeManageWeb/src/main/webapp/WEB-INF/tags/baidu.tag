<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tongji" tagdir="/WEB-INF/tags"%>

<%@ attribute name="showStatistics" required="true" type="java.lang.Boolean" description="统计开关"%>
<c:if test="${showStatistics}">
	<script type="text/javascript">
		var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
		document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F1272ff59297e3f612cde8f69d03f60a5' type='text/javascript'%3E%3C/script%3E"));
	</script>

</c:if>

