<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HRM Application</title>
</head>
<body>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ include file="navbar.jsp" %> 
	
 <div class="container">
 	<h3> Previous Leave Request Applications</h3>
    <table class="table">
    	<tr>
    		<th>Start Date</th>
    		<th>End Date</th>
    		<th>Status</th>
    	</tr>
    	<c:forEach var="l" items="${leaves}">
    		<tr>
    			<td><c:out value="${l.startDate}"/> </td>
    			<td><c:out value="${l.endDate}"/> </td>
    			<td><c:out value="${l.status}"/></td>
    		</tr>
    	</c:forEach>
    </table>
    
    <br>  
    <p> Click <a href="${contextPath}/requests/">here</a> to create New Leave Request </p>
    <br>
    <c:if test = "${employee.role.rolename == 'manager'}">
    <p> Click <a href="${contextPath}/managereq/">here</a> to Manage Engineer Leaves </p>
  	</c:if>
  </div>  
 


</body>
</html>