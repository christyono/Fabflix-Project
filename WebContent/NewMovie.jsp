<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Movie</title>
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
	Please enter information about the movie you want to add.
	<br>
	* Designates required fields
	<br>
	If star only has one name, you may leave one of the name fields blank
	<br>
	<form action = "AddMovie" method = "get">
	<table>
		<tr> 
			<th >Insert Movie: </th>
		</tr>
		<tr>
			<th >Title*:</th>
			<td ><input type="text" name="title" /></td>
		</tr>
		<tr>
			<th >Year*:</th>
			<td ><input type="text" name="year" /></td>
		</tr>
		<tr>
			<th >Director*:</th>
			<td ><input type="text" name="director" /></td>
		</tr>
		<tr>
			<th >Banner URL:</th>
			<td ><input type="text" name="bannerURL" /></td>
		</tr>
		<tr>
			<th >Trailer URL:</th>
			<td ><input type="text" name="trailerURL" /></td>
		</tr>
		<tr> 
			<th >Insert Star: </th>
		</tr>
		<tr>
			<th >First Name*:</th>
			<td ><input type="text" name="firstName" /></td>
		</tr>
		<tr>
			<th >Last Name*:</th>
			<td ><input type="text" name="lastName" /></td>
		</tr>
		<tr>
			<th >Date Of Birth (YYYY-MM-DD)*:</th>
			<td ><input type="text" name="dateOfBirth" /></td>
		</tr>
		<tr>
			<th >Photo URL:</th>
			<td ><input type="text" name="photoURL" /></td>
		</tr>
		<tr> 
			<th >Insert Genre: </th>
		</tr>
		<tr>
			<th >Genre Name*:</th>
			<td ><input type="text" name="genreName" /></td>
		</tr>
		<td align = "left"><input type = "submit" value = "Submit"/></td>
		</tr>
	</table>
	</form>
</p>
</body>
</html>