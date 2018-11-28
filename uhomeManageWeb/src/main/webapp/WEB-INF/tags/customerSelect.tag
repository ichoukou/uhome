<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@	taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="name"  type="java.lang.String" description="select标签的名字"%>
<%@ attribute name="id"  type="java.lang.String" description="select标签的id"%>
<%@ attribute name="style"  type="java.lang.String" %>
<%@ attribute name="selected"  type="java.lang.Integer" description="selected为customerId"%>

<select name="${name }" id="${id }" style="${style}">
		<option value=""><spring:message code="select.all" text=""/></option>
		<c:forEach items="${customers}" var = "customer">
			<option  
					 	value="${customer.customerId}" 
					    <c:if test="${selected eq customer.customerId}">selected="selected"</c:if>
			 >
			 <c:out value="${customer.customerCode}(${customer.shopName})"/>    
			 </option>
		</c:forEach>
</select> 


