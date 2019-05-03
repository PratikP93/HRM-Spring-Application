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
<div class="nav-bar">
 	<ul class="nav nav-tabs">
  		<li name="list-home"><a href="${contextPath}/user">Home</a></li>
  		
  		<li name="list-attendance"><a href="${contextPath}/attendance">Attendance Records</a></li>
  		<li name="list-request"><a href="${contextPath}/allrequests">Manage Requests</a></li>
  		<li name="list-salary"><a href="${contextPath}/salary">Manage Salary</a></li>
  		<c:if test = "${employee.role.rolename == 'admin'}">
        	<li name="list-role"><a href="${contextPath}/roles">Manage Roles</a></li>
        </c:if>
        <c:if test = "${employee.role.rolename == 'manager'}">
        	<li name="list-role"><a href="${contextPath}/project">Manage Projects</a></li>
        </c:if>
        <c:if test = "${employee.role.rolename == 'admin' or employee.role.rolename == 'hr'}">
        	<li name="list-employee"><a href="${contextPath}/employee">Manage Employee</a></li>
        </c:if>
        <li name ="logout"> <a href="${contextPath}/logout">Logout</a></li>
	</ul>
 </div>
</body>
</html>
