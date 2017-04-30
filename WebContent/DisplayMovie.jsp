<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.ArrayList,helper.Movie,helper.Star,helper.Genre" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>About Movie</title>
</head>

<body>
	<h1> About Movie </h1>
	<hr>
	<p>
	<br>
	<a href = DisplaySearch.jsp>Go back to previous page</a>
	
	<%
	if (session.getAttribute("username") == null)
	{
		%><a href = "index.html"> Please login first</a><%
	}
	else{
		%>
		<a href = "index.html">login</a>
		<a href = main.jsp>main page</a>
		<a href = Logout>logout</a>
		<br>
		<%
	}
	Movie movie = (Movie)request.getAttribute("currentMovie");
	%>
	<table width = "150" border = "0">
		<tr>
			<th align = left colspan = "2"> <img src = <%=movie.getBannerURL() %> width = 150> </th>
		</tr>
		<tr>
			<th >ID: </th>
			<td > <%=movie.getID()%></td>
		</tr>
		<tr>
			<th >Title:</th>
			<td><%=movie.getTitle()%></a> </td>
		</tr>
		<tr>
			<th>Year:</th>
			<td> <%=movie.getYear()%></td>
		</tr>
		<tr>
			<th>Director:</th>
			<td> <%= movie.getDirector()%></td>
		</tr>
		
		<%
		for (int j = 0; j < movie.getStarList().size(); j++)
		{
			Star star = movie.getStarList().get(j);
			
			if (j != 0)
			{
				%>
				<tr>
					<th ></th>
					<td > <a href = "GetStar?param1=<%=star.getStarID()%>"><%=star.getFirstName() + " " + star.getLastName()  %></a></td>
				
				</tr> 
				<%
			}
			else
			{
				%>
				<tr>
					<th >Stars: </th>
					<td > <a href = "GetStar?param1=<%=star.getStarID()%>"><%=star.getFirstName() + " " + star.getLastName()  %></a></td>
				</tr> 
				<%
			}
//				Star star = movieList.get(i).getStarList().get(j);
//				out.println("<br> Star Name: " + star.getFirstName() + " " + star.getLastName() + "<br>");
//				out.println("<br> StarID: " + star.getStarID() + "<br>");
//				out.println("<br> Star Name: " + star.getFirstName() + " " + star.getLastName() + "<br>");
//				out.println("<br> Star Date of Birth: " + star.getDateOfBirth() + "<br>");
//				out.println("<br> Star PhotoURL: " + star.getPhotoURL() + "<br>");
		}
		for (int k = 0; k < movie.getGenreList().size(); k++)
		{
			Genre genre = movie.getGenreList().get(k);
			if (k != 0)
			{
				%>
				<tr>
					<th ></th>
					<td > <%=genre.getName()%></td>
				</tr> 
				<%
			}
			else
			{
				%>
				<tr>
					<th >Genres: </th>
					<td ><%=genre.getName()%></td>
				</tr> 
				
				<%
			}
		}
		%>
		<tr>
			<th >Price: </th>
			<td > <%="$" + movie.getPrice()%></td>
		</tr> 
		<tr>
			<th> Trailer: </th>
			<td> Click <a href = <%= movie.getTrailerURL() %>>here </a>to watch the movie</td>
		</tr>
		<%
	%>
	<% session.removeAttribute("currentMovie"); %>
	</table>
</body>
</html>