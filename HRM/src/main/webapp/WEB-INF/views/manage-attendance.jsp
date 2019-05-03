<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">


<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<meta charset="ISO-8859-1">
<title>HRM Application</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<%@ include file="navbar.jsp" %> 
	
 <div class="container">
 	
 	<form>
 	<legend> Today's Attendance</legend>
 		<table class="table">
 			<tr>
 				<th>Check In </th>
 				<th>Check Out</th>
 			</tr>
 			<tr>
 				
 				<td> 
 				<c:choose>
 					<c:when test="${not empty att.checkIn}">
 						<c:out value="${att.checkIn}" />
 					</c:when>
 					<c:otherwise>
 						<a href="${contextPath}/attendance/checkIn/${sessionScope.employee.empId}" class="btn btn-class">Check In</a>
 					</c:otherwise>
 				</c:choose>
 				</td>
 				<td> 
 				<c:choose>
 					<c:when test="${not empty att.checkIn and not empty att.checkOut}">
 						<c:out value="${att.checkOut}" />
 					</c:when>
 					<c:otherwise>
 						<a href="${contextPath}/attendance/checkOut/${sessionScope.employee.empId}" class="btn btn-class">Check Out</a>
 					</c:otherwise>
 				</c:choose>
 				</td>
 			</tr>
 		</table>
 	</form>
 	
 	<br> <br>
 	<h3> Previous Attendance Records </h3>
 	
 	<table class="table">
 		<tr>
 			<th>Start Time </th>
 			<th>End Time </th>
 			<th>Total Hours </th>
 		</tr>
 		<c:forEach var="attn" items="${attList}">
    		<tr>
    			<td><c:out value="${attn.checkIn}"/> </td>
    			<td><c:out value="${attn.checkOut}"/> </td>
    			<td><c:out value="${attn.totalHours}" /></td>
    		</tr>
    	</c:forEach>
 	</table>
 	
 	
 	
 </div>     
 

</body>
</html>