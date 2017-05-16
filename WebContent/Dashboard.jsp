<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fabflix Dashboard</title>
</head>
<body>

<h1> Welcome to the Fabflix Dashboard</h1>
<hr>
<p>
	<br>
	<%if (session.getAttribute("email") == null)
	{
		%><a href = "EmployeeLogin.jsp"> Please login first</a><%
	}
	else{
		%>
		<a href = Dashboard.jsp>dashboard</a>
		<a href = EmployeeLogout>logout</a>
		<br>
		Welcome ${sessionScope['email']} 
		<p><a href = "NewStar.jsp">Insert New Star</a></p>
		<p><a href = "ShowMetadata">Display Metadata</a></p>
		<p><a href = "NewMovie.jsp">Add Movie</a></p>
		<% 
	}
	%>	
</p>
</body>
</html>
</body>
</html>