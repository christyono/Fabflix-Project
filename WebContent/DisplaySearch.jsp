<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    %>
<%@ page import ="java.util.ArrayList,helper.Movie,helper.Star" %>
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
		Welcome ${sessionScope['username']} 
		<%
		
		ArrayList<Movie> movieList = (ArrayList<Movie>) request.getAttribute("movieList");
		if (movieList == null)
		{
			%>
			Please enter keywords into search bar.
			<form action = "DisplayMovie" method = "get">
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
			</p>
			</form>
		<%
		} 
		else
		{
			%> <p><a href = 'DisplaySearch.jsp'>Click here to make a new search</a></p> <%
			
			for (int i = 0; i < movieList.size(); i++)
			{
				
				out.println("<br><u> Search Result: " + (i+1) + "</u><br>");
				out.println("<br> <img src = " + movieList.get(i).getBannerURL() + " width = 150> <br>" );
				out.println("<br> MovieID: " + movieList.get(i).getID() + "<br>");
				out.println("<br> Movie Title: " + movieList.get(i).getTitle() + "<br>");
				out.println("<br> Movie Release: " + movieList.get(i).getYear() + "<br>");
				out.println("<br> Movie Director: " + movieList.get(i).getDirector() + "<br>");
				
				for (int j = 0; j < movieList.get(i).getStarList().size(); j++)
				{
					Star star = movieList.get(i).getStarList().get(j);
					out.println("<br> Star Name: " + star.getFirstName() + " " + star.getLastName() + "<br>");
					out.println("<br> StarID: " + star.getStarID() + "<br>");
					out.println("<br> Star Name: " + star.getFirstName() + " " + star.getLastName() + "<br>");
					out.println("<br> Star Date of Birth: " + star.getDateOfBirth() + "<br>");
					out.println("<br> Star PhotoURL: " + star.getPhotoURL() + "<br>");
				}
			}
		}
		
		%>
	
</body>
</html>