<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.ArrayList,helper.Movie,helper.Star,helper.Genre" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1> Welcome to Fabflix </h1>
<hr>
<p>
	<%if (session.getAttribute("username") == null)
	{
		%><a href = "index.html"> Please login first</a><%
	}
	else
	{
		%>
		<a href = "index.html">login</a>
		<a href = main.jsp>main page</a>
		<a href = Logout>logout</a>
		<br>
		<% 
		
	}
	%>
	Welcome ${sessionScope['username']}
	<br>
	Please enter keywords into search bar.
	<br>
	<form action = "FindMovie" method = "get">
	<table>
		<tr> 
			<th >Search for movies: </th>
		</tr>
		<tr>
			<th >Title:</th>
			<td ><input type="text" name="title" /></td>
		</tr>
		<tr>
			<th>Year:</th>
			<td><input type = "text" name = "year" /></td>
		</tr>
		<tr>
			<th>Director:</th>
			<td><input type = "text" name = "director"/></td>
		</tr>
		<tr>
			<th>Star First Name: </th>
			<td><input type = "text" name = "first_name"/></td>
		</tr>
		<tr>
			<th>Star Last Name: </th>
			<td><input type = "text" name = "last_name"/></td>
		</tr>
		<tr>
		<td align = "left"><input type = "submit" value = "Submit"/></td>
		</tr>
		
		
	</table>
	</form>
</p>
</body>
</html>