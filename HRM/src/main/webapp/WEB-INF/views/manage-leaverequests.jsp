<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
<title>Insert title here</title>
</head>
<body>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ include file="navbar.jsp" %>

	
 <div class="container">
    <table class="table">
    	<tr>
    		<th> Employee ID </th>
    		<th>Start Date</th>
    		<th>End Date</th>
    		<th>Status</th>
    	</tr>
    	<c:forEach var="l" items="${attRequests}">
    		<tr>
				<td><c:out value="${l.employee.empId}"/></td>
    			<td><c:out value="${l.startDate}"/> </td>
    			<td><c:out value="${l.endDate}"/> </td>
    			<td><c:out value="${l.status}"/></td>
    			
    			<td>
    			<c:if test= "${l.status == 'submitted'}">
    			<form method="post" action="${contextPath}/managereq/${l.requestId}" >
    			<input type="hidden" name="btnAction" value="Aprrove">
    			<button type="submit">Approve</button>
    			</form>
    			</c:if>
    			</td>
    			
    			<td>
    			<c:if test= "${l.status == 'submitted'}">
    			<form method="post" action="${contextPath}/managereq/${l.requestId}" >
    			<input type="hidden" name="btnAction" value="Reject">
    			<button type="submit">Reject</button>    			
    			</form>
    			</c:if>
    			</td>
    			
    		</tr>
    	</c:forEach>
    </table>
    
    <br>
    
   
    


</body>
</html>