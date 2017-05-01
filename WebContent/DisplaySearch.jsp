<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    %>
<%@ page import ="java.util.ArrayList,helper.Movie,helper.Star,helper.Genre" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
</head>
<body>
	<h1> Welcome to Fabflix </h1>
	<a href = "index.html">login</a>
	<a href = main.jsp>main page</a>
	<a href = Logout>logout</a>
	<hr>
	<p>
		<%
		if (session.getAttribute("username") == null)
		{
			%><a href = "index.html"> Please login first</a><%
		}
		else{
			if (request.getAttribute("error") != null)
			{
				out.println(request.getAttribute("error") + "<br>");
			}
			if (request.getAttribute("searchParams") != null)
			{
				out.println("Currently searching using: " + request.getAttribute("searchParams") + "<br>");
			}
			%>
			<form action = SortMovie method = get>
				Sort By:  <br> 
				<input type = radio name = "sortBy" value = "title"> Title <br>
				<input type = radio name = "sortBy" value = "year">  Year <br>
				Order: <br>
				<input type = radio name = "order" value = "ascending"> Ascending <br>
				<input type = radio name = "order" value = "descending"> Descending <br>
				
				<input type = submit value = "submit">
			</form>
			
			<%
		}
		%>
		<br>
		<%
		// JAVA CODE BEGINS HERE
		
		ArrayList<Movie> movieList;

		if (request.getAttribute("sortedList") != null)
		{
			movieList = (ArrayList<Movie>) request.getAttribute("sortedList");
		}
		else
		{
			movieList = (ArrayList<Movie>) session.getAttribute("movieList");
		}
		//HTML CODE BEGINS HERE
			%> <p><a href = 'AdvancedSearch.jsp'>Click here to make a new search</a></p>
			
			<table width = "150" border = "0">
			<%
			// HTML CODE ENDS HERE
			// basically the <td> are forming a table, in place of out.println()
			
			for (int i = 0; i < movieList.size(); i++)
			{
				%>
				
				<tr>
					<th align = left colspan = "2"> <img src = <%=movieList.get(i).getBannerURL() %> width = 150> </th>
				</tr>
				<tr>
					<th >ID: </th>
					<td > <%=movieList.get(i).getID()%></td>
				</tr>
				<tr>
					<th >Title:</th>
					<td><a href = "GetMovie?param1=<%=movieList.get(i).getID() %>" ><%=movieList.get(i).getTitle()%></a> </td>
					
				</tr>
				<tr>
					<th>Year:</th>
					<td> <%=movieList.get(i).getYear()%></td>
				</tr>
				<tr>
					<th>Director:</th>
					<td> <%= movieList.get(i).getDirector()%></td>
				</tr>
				
				<%
				for (int j = 0; j < movieList.get(i).getStarList().size(); j++)
				{
					Star star = movieList.get(i).getStarList().get(j);
					
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
							<td > <a href = "GetStar?param1=<%=star.getStarID()%>"><%=star.getFirstName() + " " + star.getLastName()  %></td>
						</tr> 
						<%
					}
// 					Star star = movieList.get(i).getStarList().get(j);
// 					out.println("<br> Star Name: " + star.getFirstName() + " " + star.getLastName() + "<br>");
// 					out.println("<br> StarID: " + star.getStarID() + "<br>");
// 					out.println("<br> Star Name: " + star.getFirstName() + " " + star.getLastName() + "<br>");
// 					out.println("<br> Star Date of Birth: " + star.getDateOfBirth() + "<br>");
// 					out.println("<br> Star PhotoURL: " + star.getPhotoURL() + "<br>");
				}
				for (int k = 0; k < movieList.get(i).getGenreList().size(); k++)
				{
					Genre genre = movieList.get(i).getGenreList().get(k);
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
					<td > <%="$" + movieList.get(i).getPrice()%></td>
				</tr> 
				<%
				
			}
			%>
			
			</table>
			
			
			
		
		
		
	
</body>
</html>