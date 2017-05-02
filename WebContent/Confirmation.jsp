<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transaction Success!</title>
</head>
<body>
	<h1> Welcome to Fabflix </h1>
	<a href = "index.html">login</a>
	<a href = main.jsp>main page</a>
	<a href = Logout>logout</a>
	<a href = ShoppingCart.jsp>shopping cart</a>
	<hr>
	<% if (request.getParameter("success").equals("true"))
		{
			%> <u>Transaction successful</u> <%
		
		}
			%>
</body>
</html>