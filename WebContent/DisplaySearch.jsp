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
	<a href = ShoppingCart.jsp>shopping cart</a>
	<hr>
	<p>
		<%
		if (session.getAttribute("username") == null)
		{
			%><a href = "index.html"> Please login first</a><%
		}
		

		else
		{
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
			<br>
			
			<form action = Pagination method = get>
				Show Results: <br>
				<input type = radio name = "limit" value = 10> 10 <br>
				<input type = radio name = "limit" value = 25> 25 <br>
				<input type = radio name = "limit" value = 50> 50 <br>
				<input type = submit value = "submit">
			</form>
			<%
		}
		%>
		<br>
		<%
		// JAVA CODE BEGINS HERE
		
		ArrayList<Movie> movieList;
		int numOfResults = 0;
		int limit = 10;
		int offSet = 0;
		int pageNum = 1;
		
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
		<p><a href = 'GetGenre'>Click here to browse</a></p>
		<table width = "150" border = "0">
		<%
		// HTML CODE ENDS HERE
		// basically the <td> are forming a table, in place of out.println()
		
		if (movieList.size() > limit)
		{
			
			
			if (session.getAttribute("pageNum") != null)
				pageNum = (Integer) session.getAttribute("pageNum");
			if (session.getAttribute("limit") != null)
				limit = (Integer) session.getAttribute("limit");
			if (session.getAttribute("offset") != null)
				offSet = (Integer) session.getAttribute("offset");
			
			int i = offSet;
			
			%><p> Page Number: <%=pageNum%> </p><% 
			if (pageNum != 1)
			{
				%><a href = Pagination?limit=<%=limit %>&pageNum=<%=pageNum - 1%>>Previous</a><%
			}
			if ((offSet + limit) < movieList.size())
			{
				
				%><a href = Pagination?limit=<%=limit %>&pageNum=<%=pageNum + 1%>>Next</a><%
			}
			for (; i < movieList.size() && i < (offSet + limit); i++)
			{
				numOfResults++;
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
				<tr>
					<td>
						<button onclick = "addToCart('<%=movieList.get(i).getID() %>', '<%=movieList.get(i).getTitle() %>', '<%=movieList.get(i).getPrice()%>')"> Add to Cart </button>
					</td>
				</tr>
				<%
				
			}
			%>
			
			</table>
			<script>
		    function addToCart(p1, p2, p3){
		    	window.open("quantity.jsp" + "?id="+p1+"&title="+p2+"&price="+p3,null,
		    	"height=200,width=400,status=yes,toolbar=no,menubar=no,location=no");
		      }
			</script>
			<u> Displaying: <%=numOfResults %> Results</u>
			<u> Total Number of Movies: <%= movieList.size() %></u>
			<u> Offset + Limit: <%= offSet + limit %></u>
		<%
		}
		else
		{
			for (int i = 0; i < movieList.size(); i++)
			{
				numOfResults++;
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
				<tr>
					<td>
						<button onclick = "addToCart('<%=movieList.get(i).getID() %>', '<%=movieList.get(i).getTitle() %>', '<%=movieList.get(i).getPrice()%>')"> Add to Cart </button>
					</td>
				</tr>
				<%
				
			}
			
			%>
			
			</table> 
			<u> Displaying: <%=numOfResults %> Results</u>
			<u> Total Number of Movies: <%= movieList.size() %></u>
			<script>
		    function addToCart(p1, p2, p3){
		    	window.open("quantity.jsp" + "?id="+p1+"&title="+p2+"&price="+p3,null,
		    	"height=200,width=400,status=yes,toolbar=no,menubar=no,location=no");
		      }
			</script>
			<% 
		}
		%>
		
		
			
			
			
		
		
		
	
</body>
</html>
