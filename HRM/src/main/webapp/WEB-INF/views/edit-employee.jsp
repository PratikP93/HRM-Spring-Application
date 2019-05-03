<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<meta charset="ISO-8859-1">
<title>HRM Application</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<%@ include file="navbar.jsp"%>

	<div class="container">
		<form:form class="well form-horizontal"
			action="${contextPath}/employee/update/${emp.empId}" method="post"
			id="contact_form" modelAttribute="emp">
			<fieldset>

				<!-- Form Name -->
				<legend>
					<center>
						<h2>
							<b>Edit Employee</b>
						</h2>
					</center>
				</legend>
				<br>

				<!-- Text input-->
				<div class="form-group">
					<label class="col-md-4 control-label">First Name</label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span>
							<form:input name="first_name" value="${emp.firstName}"
								path="firstName" placeholder="First Name" class="form-control"
								type="text" />
						</div>
					</div>
				</div>

				<!-- Text input-->

				<div class="form-group">
					<label class="col-md-4 control-label">Last Name</label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span>
							<form:input name="last_name" value="${emp.lastName}"
								placeholder="Last Name" path="lastName" class="form-control"
								type="text" />
						</div>
					</div>
				</div>

					<div class="form-group"> 
		  <label class="col-md-4 control-label">Role</label>
		    <div class="col-md-4 selectContainer">
		    <div class="input-group">
		        <span class="input-group-addon"><i class="glyphicon glyphicon-list"></i></span>
		    <select name="roles" class="form-control selectpicker" >
		    
		        	<option  value="${emp.role.roleId }" selected> <c:out value = "${emp.role.rolename}" /> </option>
		    </select>
		  </div>
		</div>
		</div>
				<c:if test="${employee.role.rolename == 'role'}">
					<div class="form-group eng" style="display: none">
						<label class="col-md-4 control-label">Manager</label>
						<div class="col-md-4 selectContainer">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-list"></i></span> <select id="manager"
									name="manager" class="form-control selectpicker">
									<option value="">Select a Option</option>
								</select>
							</div>
						</div>
					</div>

					<div class="form-group eng" style="display: none">
						<label class="col-md-4 control-label">Projects</label>
						<div class="col-md-4 selectContainer">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-list"></i></span> <select id="project"
									name="project" class="form-control selectpicker">
									<option value="">Select an Option</option>
								</select>
							</div>
						</div>
					</div>
				</c:if>


				<!-- Text input-->
				<div class="form-group">
					<label class="col-md-4 control-label">E-Mail</label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-envelope"></i></span>
							<form:input name="email" value="${emp.email}"
								placeholder="E-Mail Address" path="email" class="form-control"
								type="text" />
						</div>
					</div>
				</div>


				<!-- Text input-->

				<div class="form-group">
					<label class="col-md-4 control-label">Contact No.</label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-earphone"></i></span>
							<form:input name="contact_no" value="${emp.phone}" path="phone"
								class="form-control" type="text" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-4 control-label">Wage (Per hour)</label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-earphone"></i></span>
							<form:input name="wage" id="Wage" value="${emp.wage}" path="wage"
								class="form-control" type="number" required="required" />
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-md-4 control-label">Leave Count</label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-earphone"></i></span>
							<form:input name="Leave_Count" id="lCount"
								value="${emp.leaveCount}" path="leaveCount" class="form-control"
								type="number" required="required" />
						</div>
					</div>
				</div>


				<!-- Button -->
				<div class="form-group">
					<label class="col-md-4 control-label"></label>
					<button type="Submit">Submit</button>
				</div>

			</fieldset>
		</form:form>
	</div>

	<script type="text/javascript">
	


		$(document).ready(function(){

		 	var el = document.getElementById("role");
			console.log("Inside change2");
			var s = el.options[el.selectedIndex].text;
		    console.log(s);
		    if(s == "engineer")
			{			  
		    	var managerSelect = document.getElementById("manager");
		    	var i;
		        for(i = managerSelect.options.length - 1 ; i > 0 ; i--)
		        {
		        	managerSelect.remove(i);
		        }  
		    	$.ajax({
		   	     type: "GET",
		   	     url: '/edu/managers', 
		   	     success: function(data) {
		   		     console.log(data);
		   	         for (var i = 0; i < data.length; i++) {
		   		         console.log(data[i]);
		   		         console.log("")
		   		         var option = document.createElement("option");
		   		         option.value = data[i][0];
		   		         option.innerHTML = data[i][1];
		   		         managerSelect.appendChild(option);
		   	         }
		   	         var elements = document.querySelectorAll('.eng');
		   	         elements.forEach(element => {
		   	       	  console.log('element: ', element);
		   	       	  element.style.display = 'block';
		   	       	});     
		   	     }
		   	   });
			}
		    else{
		    	var managerSelect = document.getElementById("manager");
		    	var i;
		        for(i = managerSelect.options.length - 1 ; i > 0 ; i--)
		        {
		        	managerSelect.remove(i);
		        }
		    	var elements = document.querySelectorAll('.eng');
	   	         elements.forEach(element => {
	   	       	  console.log('element: ', element);
	   	       	  element.style.display = 'none';
	   	       	});
			}    
	     });

	 $("select#manager").change(function(){

		 	var el = document.getElementById("manager");
			console.log("Inside Project change");
			var s = el.options[el.selectedIndex].value;
			var projSelect = document.getElementById("project");
	    	var i;
	        for(i = projSelect.options.length - 1 ; i > 0 ; i--)
	        {
	        	projSelect.remove(i);
	        }
		    console.log(s);
		    	$.ajax({
		   	     type: "GET",
		   	     url: '/edu/manager/projects?managerId='+s, 
		   	     success: function(data) {
		   		     console.log(data);
		   	         for (var i = 0; i < data.length; i++) {
		   		         console.log(data[i]);
		   		         var projSelect = document.getElementById("project");
		   		         console.log("")
		   		         var option = document.createElement("option");
		   		         option.value = data[i].projectID;
		   		         option.innerHTML = data[i].projectName;
		   		      	 projSelect.appendChild(option);
		   	         }
		   	     }
		   	   });
			   
	     });


	 $("#Wage").change(function(){

		 var wage = document.getElementById("Wage").value;

		 if(wage < 0){

alert("Wage can not be negative");
document.getElementById("Wage").value = "";
			 }

		 });


	 $("#lCount").change(function(){

		 var wage = document.getElementById("lCount").value;

		 if(wage < 0 ){

alert("Leave count can not be negative");
document.getElementById("lCount").value = "";
			 }
	 });
	
</script>

</body>
</html>