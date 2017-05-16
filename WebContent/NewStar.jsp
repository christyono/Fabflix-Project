<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Star</title>
</head>
<body>
<h1> Welcome to the Fabflix Dashboard </h1>
	<a href = Dashboard.jsp>dashboard</a>
	<a href = EmployeeLogout>logout</a>
<hr>
<p>
	<%if (session.getAttribute("email") == null)
	{
		%><a href = "_dashboard.html"> Please login first</a><%
	}
	%>
	<br>
	Please enter information about the new star.
	<br>
	If the star only has one name, enter it into Last Name field and leave the First Name blank.
	<br>
	<form action = "InsertStar" method = "get">
	<table>
		<tr> 
			<th >Insert Star: </th>
		</tr>
		<tr>
			<th >First Name:</th>
			<td ><input type="text" name="firstName" /></td>
		</tr>
		<tr>
			<th >Last Name:</th>
			<td ><input type="text" name="lastName" /></td>
		</tr>
		<tr>
			<th >Date Of Birth (YYYY-MM-DD):</th>
			<td ><input type="text" name="dateOfBirth" /></td>
		</tr>
		<tr>
			<th >Photo URL:</th>
			<td ><input type="text" name="photoURL" /></td>
		</tr>
		<td align = "left"><input type = "submit" value = "Submit"/></td>
		</tr>
	</table>
	</form>
</p>
</body>
</html>