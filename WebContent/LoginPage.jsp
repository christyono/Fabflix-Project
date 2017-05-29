<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome to Fabflix</title>
<link rel="stylesheet" href="css/w3.css">
<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
	<div class= "w3-container w3-teal">
		<h1> Welcome to Fabflix. Please Login </h1>
	</div>
	<%if (request.getAttribute("Incorrect") != null)
	{
		%>
		 	<div class = "w3-panel w3-red w3-padding">
		 		<p> <% out.println(request.getAttribute("Incorrect")); %></p>
		 	</div>
		<%
	}
	%>
	<% if (request.getAttribute("logout") != null)
	{
		%>
			<div class = "w3-panel w3-cyan w3-padding">
				<p> <%out.println(request.getAttribute("logout")); %></p>
			</div>
		<%
	}
	%>
	<!--  change method of form action to POST to hide parameters user and password -->
	<form class = "w3-container w3-left w3-mobile w3-padding" action = "Login" method = "post">
		<label class = "w3-text-blue"><b>Username</b></label>
		<input class = "w3-input w3-border" type ="text" name = "Username"><br>
		<label class = "w3-text-blue"><b>Password</b></label>
		<input class = "w3-input w3-border" type ="password" name = "Password"><br>
		<button class = "w3-button w3-round w3-green">Submit</button>
	</form>
	<div class="g-recaptcha" data-sitekey="6LfcBSAUAAAAAHVRxVudl2NHzd94H6jH5UROCx8I"></div>
	
</body>
</html>
</form>