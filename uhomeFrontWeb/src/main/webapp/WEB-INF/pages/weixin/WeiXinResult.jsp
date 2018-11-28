<%@page import="java.io.PrintWriter"%>
<%
	String result = (String)request.getAttribute("resultString");
	PrintWriter pw = response.getWriter();
	pw.print(result);
%>