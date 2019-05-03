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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<title>HRM Application</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ include file="navbar.jsp" %>
 
 
 <div class="container">
    <form:form class="well form-horizontal" action="${contextPath}/requests" method="post"  id="contact_form">
	<fieldset>

	<!-- Form Name -->
	<legend><center><h2><b>Leave Request Form</b></h2></center></legend><br>

	<!-- Text input
	
-->
		<!-- Text input-->
		<div class="form-group">
  		<label class="col-md-4 control-label">Start Date</label>  
  		<div class="col-md-4 inputGroupContainer">
  		<div class="input-group">
  			<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
  			<input  type= "date" id="StartDate" class="datepicker form-control" name = "startDate" required/>
    	</div>
  		</div>
		</div>
		
		<div class="form-group">
  		<label class="col-md-4 control-label">First Name</label>  
  		<div class="col-md-4 inputGroupContainer">
  		<div class="input-group">
  			<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
  			<input  type= "date"  id="EndDate"  class="datepicker form-control" name = "endDate" required/>
    	</div>
  		</div>
		</div>
	
	
	
		<div class="form-group"> 
		  <label class="col-md-4 control-label">Type</label>
		    <div class="col-md-4 selectContainer">
		    <div class="input-group">
		        <span class="input-group-addon"><i class="glyphicon glyphicon-list"></i></span>
		    <select name="type" class="form-control selectpicker">
		    <option value="Planned Leave">Planned Leave</option>
		    <option value="Birthday Leave">Birthday Leave</option>
		     </select>
		  </div>
		</div>
		</div>
		
		
		<!-- Button -->
		<div class="form-group">
		  <label class="col-md-4 control-label"></label>
		  <button type= "Submit">Submit</button>
		</div>

</fieldset>
</form:form>
 </div>     
 
 
 <script type="text/javascript">

 $("#EndDate").change(function () {
	    var startDate = document.getElementById("StartDate").value;
	    var endDate = document.getElementById("EndDate").value;

	    if ((Date.parse(startDate) > Date.parse(endDate))) {
	        alert("End date should be greater than Start date");
	        document.getElementById("EndDate").value = "";
	        $("#submitbtn").attr("disabled", true);
	    }else{
	    	 $("#submitbtn").attr("disabled", false);
		    }
	});

$('#StartDate').change(function(){
	 var startDate = document.getElementById("StartDate").value;
		var endDate = document.getElementById("EndDate").value;
	 var currentDate = new Date();
console.log(currentDate);
console.log(startDate);
currentDate.setHours(0,0,0,0);
if((Date.parse(startDate) < Date.parse(currentDate)) || (endDate && Date.parse(startDate)> Date.parse(endDate) )){
	alert("Start date  should not be less than current date");
	  document.getElementById("StartDate").value = "";
	  document.getElementById("EndDate").value = "";
}
	 
	
});

 
 </script>
 
</body>
</html>