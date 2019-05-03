<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HRM Application</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">


<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>

 <c:set var="contextPath" value="${pageContext.request.contextPath}" />
 <c:set value='${sessionScope["employee"]}' var="emp" />

 <br>
 
<%@ include file="navbar.jsp" %>  

 <div class="container">
 	<h2>Welcome <c:out value="${emp.firstName}" />  to HRM Application</h2>
 </div> 
 
 <script type="text/javascript">
 	var element = document.getElementsByName("list-home");
 	console.log(element[0]);
 	element[0].classname == "active";
 </script> 
 
</body>
</html>