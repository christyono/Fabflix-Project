<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fabflix Homepage</title>
</head>
<body>

<h1> Welcome to Fabflix </h1>
<hr>
<p>
	
	<br>
	<%if (session.getAttribute("username") == null)
	{
		%><a href = "index.html"> Please login first</a><%
	}
	else{
		%>
		<a href = "index.html">login</a>
		<a href = main.jsp>main page</a>
		<a href = Logout>logout</a>
		<br>
		Welcome ${sessionScope['username']} 
		<p><a href =  "AdvancedSearch.jsp">Advanced Search</a></p>
		Browse by Genres: <br>
		<p><a href="" >Fantasy </a></p>
		Browse by Titles: <br>
		<p><a href="" >A </a></p>
		<% 
	}
	%>
	
		
	
</p>
</body>
</html>
</body>
</html>