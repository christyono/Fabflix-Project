<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    %>
<%@ page import ="java.util.ArrayList,helper.Movie,helper.Star,helper.Genre" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/w3.css">
<title>Search Results</title>
<style>
/* Tooltip container */
.tooltip {
    position: relative;
    display: inline-block;
    border-bottom: 1px; /* If you want dots under the hoverable text */
}

/* Tooltip text */
.tooltip .tooltiptext {
    visibility: hidden;
    width: 300px;
    background-color: #555;
    color: #fff;
    text-align: center;
    padding: 5px 0;
    border-radius: 6px;

    /* Position the tooltip text */
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: 50%;
    margin-left: -150px;

    /* Fade in tooltip */
    opacity: 0;
    transition: opacity 1s;
}

/* Tooltip arrow */
.tooltip .tooltiptext::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 50%;
    margin-left: -10px;
    border-width: 10px;
    border-style: solid;
    border-color: #555 transparent transparent transparent;
}

/* Show the tooltip text when you mouse over the tooltip container */
.tooltip:hover .tooltiptext {
    visibility: visible;
    opacity: 1;
}
</style>
</head>
<jsp:include page = "FrontEnd/NavBar.jsp"/>
<body>
		<%
		if (session.getAttribute("username") == null)
		{
			%><div class = "w3-panel w3-red w3-large w3-padding"><a href = "index.html"> Please login first</a></div>"<%
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
		<table class = "w3-table" width = "150" border = "0" style = "float:left;display:inline;">
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
					<td class="tooltip" onmouseover="mOver(<%=movieList.get(i).getID()%>)"><a href="GetMovie?param1=<%=movieList.get(i).getID() %>"><%=movieList.get(i).getTitle()%> </a>
					<span class="tooltiptext" id = <%=movieList.get(i).getID() %>>
					<table id = <%=movieList.get(i).getID() %>>
								<tr>
									<th align = left colspan = "2"> <img src = <%=movieList.get(i).getBannerURL() %> width = 150> </th>
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
				}%>
				<tr>
					<th>Year:</th>
					<td> <%=movieList.get(i).getYear()%></td>
				</tr>
				<tr>
					<th >Price: </th>
					<td > <%="$" + movieList.get(i).getPrice()%></td>
				</tr> 
				<tr>
					<td>
						<button onclick = "addToCart('<%=movieList.get(i).getID() %>', '<%=movieList.get(i).getTitle() %>', '<%=movieList.get(i).getPrice()%>')"> Add to Cart </button>
					</td>
				</tr>
				<tr>
			<%if (movieList.get(i).getTrailerURL() != null) 
			{
				%> 
			<th> Trailer: </th>
			
			<td> Click <a href = <%= movieList.get(i).getTrailerURL() %>>here </a>to watch the movie</td>
			<% 
			}
			%>
			
		</tr>
				
							
							</table></span>
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
					<td class="tooltip" onmouseover="mOver(<%=movieList.get(i).getID()%>)"><a href="GetMovie?param1=<%=movieList.get(i).getID() %>"><%=movieList.get(i).getTitle()%> </a>
					<span class="tooltiptext" id = <%=movieList.get(i).getID() %>>
					<table id = <%=movieList.get(i).getID() %>>
								<tr>
									<th align = left colspan = "2"> <img src = <%=movieList.get(i).getBannerURL() %> width = 150> </th>
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
				}%>
				<tr>
					<th>Year:</th>
					<td> <%=movieList.get(i).getYear()%></td>
				</tr>
				<tr>
					<th >Price: </th>
					<td > <%="$" + movieList.get(i).getPrice()%></td>
				</tr> 
				<tr>
					<td>
						<button onclick = "addToCart('<%=movieList.get(i).getID() %>', '<%=movieList.get(i).getTitle() %>', '<%=movieList.get(i).getPrice()%>')"> Add to Cart </button>
					</td>
				</tr>
				<tr>
			<%if (movieList.get(i).getTrailerURL() != null) 
			{
				%> 
			<th> Trailer: </th>
			
			<td> Click <a href = <%= movieList.get(i).getTrailerURL() %>>here </a>to watch the movie</td>
			<% 
			}
			%>
			
		</tr>
				
							
							</table></span>
					<script>
					function mOver(id) {
					    var popup = document.getElementById(id);
					    popup.classList.toggle("show");
					}
					</script>
				</td>
					
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
