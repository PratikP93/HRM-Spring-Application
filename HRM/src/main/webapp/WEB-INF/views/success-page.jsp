<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	
	<div class="container">
		<h1> <c:out value="${message}" /> </h1>
		<p> Click here to go back to <a href="${contextPath}/user">Main Page</a> </p>
	</div>

</body>
</html>