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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<meta charset="ISO-8859-1">
<title>HRM Application</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<%@ include file="navbar.jsp" %> 
	
 <div class="container">
 	<c:if test="${employee.role.rolename == 'hr'}">
 	<form action="${contextPath}/salary/generate" method="POST">
 	<legend>Generate Salary for all Employees</legend>
 	<table>
 		<tr>
 			<td> Start Date </td>
 			<td> <input id="StartDate" type="date" name="startDate"  required="required"/> </td>
 		</tr>
 		<tr>
 			<td> End Date </td>
 			<td> <input id="EndDate" type="date" name="endDate"  required="required"/></td>
 		</tr>
 		<tr> <td colspan="2"><input type="Submit" value="Generate"/> </td></tr>
 		</table>
 		
 	</form>
 	<br><br>
 	</c:if>
 	
 	<h3>View Historical Salaries </h3>
 	
 	<table class="table">
 		<tr>
 				<th>Emp Id </th>
 				<th>Start Date</th>
 				<th>End Date</th>
 				<th>Total </th>
 				<th> Action </th>
 			</tr>
 		<c:forEach var="sal" items="${salaries}">
    		<tr>
    			<td><c:out value="${sal.empId}" /> </td>
    			<td><c:out value="${sal.startDate}"/> </td>
    			<td><c:out value="${sal.endDate}"/> </td>
    			<td><c:out value="${sal.total}" /></td>
    			<td> <a href="${contextPath}/salary/export/${sal.salaryId}">View</a> </td>
    		</tr>
    	</c:forEach>
 	</table>
 	
 	
 	
 </div>     
 
 
 
 <script type="text/javascript">

 $("#EndDate").change(function () {
	    var startDate = document.getElementById("StartDate").value;
	    var endDate = document.getElementById("EndDate").value;

	    if ((Date.parse(startDate) > Date.parse(endDate))) {
	        alert("End date should be greater than Start date");
	        document.getElementById("EndDate").value = "";
	        $("#submitbtn").attr("disabled", true);
	    }
	});


 
 </script>
 

</body>
</html>