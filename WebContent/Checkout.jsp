<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Checkout</title>
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
	Please enter your credit card information.
	<br>
	<form action = "CreditCheck" method = "post">
	<table>
		<tr> 
			<th >Credit Card Information: </th>
		</tr>
		<tr>
			<th >First Name:</th>
			<td ><input type="text" name="firstName" /></td>
		</tr>
		<tr>
			<th>Last Name:</th>
			<td><input type = "text" name = "lastName" /></td>
		</tr>
		<tr>
			<th>Credit Card Number:</th>
			<td><input type = "text" name = "cardNum"/></td>
		</tr>
		<tr>
			<th>Expiration Day (Two digit number): </th>
			<td><input type = "text" name = "expDate"/></td>
		</tr>
		<tr>
			<th>Expiration Month (Two digit number): </th>
			<td><input type = "text" name = "expMonth"/></td>
		</tr>
		<tr>
			<th>Expiration Year: </th>
			<td><input type = "text" name = "expYear"/></td>
		</tr>
		<tr>
		<td align = "left"><input type = "submit" value = "Submit"/></td>
		</tr>
		
	</table>
	</form>
</p>
</body>
</html>