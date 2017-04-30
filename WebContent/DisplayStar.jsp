<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.ArrayList,helper.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>About Star</title>
</head>
<body>
	<h1> About Star </h1>
	<hr><br>
	<a href = DisplaySearch.jsp>Go back to previous page</a>
	
	<% 
	if (session.getAttribute("username") == null)
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
	<%Star star = (Star) request.getAttribute("currentStar");%>
	<table width = "150" border = "0">
		<tr>
			<th align = left colspan = "2"> <img src = <%=star.getPhotoURL() %> width = 150> </th>
		</tr>
		<tr>
			<th >ID: </th>
			<td > <%=star.getStarID()%></td>
		</tr>
		<tr>
			<th >Name:</th>
			<td><%=star.getFirstName() + " " + star.getLastName()%></a> </td>
		</tr>
		<tr>
			<th>Date of Birth:</th>
			<td> <%=star.getDateOfBirth()%></td>
		</tr>
		<%
		for (int i = 0; i < star.getStarredMovieList().size(); i++)
		{
			Movie movie = star.getStarredMovieList().get(i);
			if (i != 0)
			{
				%>
				<tr>
					<th></th>
					<td> <a href = GetMovie?param1=<%=movie.getID()%>><%= movie.getTitle() %></a></td>
				</tr>
				<%
			}
			else
			{
				%>
				<tr>
					<th>Starred Movies: </th>
					<td> <a href = GetMovie?param1=<%=movie.getID()%>><%= movie.getTitle() %></a></td>
				</tr>
				<%
			}
			
		}
		%>
		
	</table>
</body>
</html>