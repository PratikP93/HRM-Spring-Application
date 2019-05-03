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
 	<h3>Manage Employees</h3>
    <table class="table">
    	<tr>
    		<th>Employee Id</th>
    		<th>Employee Name</th>
    	</tr>
    	<c:forEach var="empl" items="${employees}">
    		<tr>
    			<td><c:out value="${empl.empId}"/> </td>
    			<td><c:out value="${empl.firstName}"/> </td>
    			<td><a href="${contextPath}/employee/${empl.empId}" class="button">Edit</a></td>
    		</tr>
    	</c:forEach>
    </table>
    
    <br>
    <h4>Create New Employee</h4>
    <p> Click <a href="${contextPath}/employee/create/">here</a> to create new Employee </p>
    
    
  </div>  
 

</body>
</html>