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
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ include file="navbar.jsp" %>  
 
 
  <div class="container">
 
  	<h3>Manage  Projects</h3>
    <table class="table">
    	<tr>
    		<th>Project Id</th>
    		<th>Project Name</th>
    	</tr>
    	<c:forEach var="proj" items="${projects}">
    		<tr>
    			<td><c:out value="${proj.projectID}"/> </td>
    			<td><c:out value="${proj.projectName}"/> </td>
    		
    				
    		</tr>
    	</c:forEach>
    </table>
    
 
 
    <form class="well form-horizontal" action="${contextPath}/project" method="post"  id="contact_form">
	<fieldset>
		<label id="label-1">Project Name:</label>
 <input type="text" name="projectName"  required="required"></input>
 
	<div class="form-group">
		  <label class="col-md-4 control-label"></label>
		  <button type= "Submit">Submit</button>
		</div>
 
 </fieldset>
</form>
</div>

</body>
</html>