<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Login to Enterprise Portal 	
</h1>

<P>  The time on the server is ${serverTime}. </P>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="main-content">
		<form id="login" name="login" method="post" action="${contextPath}/login">
		<table>
			<tr>
				<td> Username : </td>
				<td> <input type="text" name="username" /> </td> 
			</tr>
			
			<tr>
				<td> Password : </td>
				<td> <input type="password" name="password" /> </td> 
			</tr>
			
			<tr>
				<td colspan="2"> <input type="submit" value="Login"> </td> 
			</tr>
			
		</table>
		</form>
	</div>

</body>
</html>
