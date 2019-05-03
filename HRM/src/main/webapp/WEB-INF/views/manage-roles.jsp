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
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">


<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<title>HRM Application</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ include file="navbar.jsp" %> 

 <div class="container">
 	<h3>Manage Existing Roles</h3>
    <table class="table">
    	<tr>
    		<th>RoleId</th>
    		<th>RoleName</th>
    	</tr>
    	<c:forEach var="role" items="${roles}">
    		<tr>
    			<td><c:out value="${role.roleId}"/> </td>
    			<td><c:out value="${role.rolename}"/> </td>
    			<td><form action="${contextPath}/roles/delete/${role.roleId}" method="post">
    				<input type="submit" value="Delete" />
    				</form>
    		</tr>
    	</c:forEach>
    </table>
    
    <form:form action="${contextPath}/roles" method="post"
        modelAttribute="role">
    <legend>Create New Role</legend>
        <table>            
            <tr>
                <td>Role Name:</td>
                <td><form:input type="text" path="rolename" required="required" /></td>
            </tr>
            
            <tr>
                <td colspan="2"><input type="submit" value="Create Role" /></td>
            </tr>
        </table>
    </form:form>
 </div> 


</body>
</html>