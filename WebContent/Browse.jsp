<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.ArrayList,helper.Movie,helper.Star,helper.Genre" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse</title>
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
		<a href = ShoppingCart.jsp>shopping cart</a>
		<br>
		<% 
		
	}
	%>
	Welcome ${sessionScope['username']}
	<br>
	<%
	ArrayList<String> genreNameList = new ArrayList<String>();
	genreNameList = (ArrayList<String>) session.getAttribute("genreNameList");
	String s = "0123456789abcdefghijklmnopqrstuvwxyz";
	ArrayList<String> alphaList = new ArrayList<String>();
	for (int i = 0; i < s.length(); i++){
	    alphaList.add(""+s.charAt(i));
	}
	%>
	Browse by Genres: 
	<table style="width:100%">
	<%
	for(int i = 0; i < genreNameList.size(); i++)
	{
		if(i%5==0)
		{
		%>
		<tr>
		<td><a href="BrowseGenre?genre=<%=genreNameList.get(i)%>"><%=genreNameList.get(i)%></a></td>
		<% 
		}else{%>
			<td><a href="BrowseGenre?genre=<%=genreNameList.get(i)%>"><%=genreNameList.get(i)%></a></td>
			<%
		}
		if(i%5==4)
		{
			%></tr><%
		}
	}
	%>
	</table>
	Browse by Titles:
	<table style="width:100%">
	<%
	for(int i = 0; i < alphaList.size(); i++)
	{
		if(i%5==0)
		{
		%>
		<tr>
		<td><a href="BrowseTitle?title=<%=alphaList.get(i)%>"><%=alphaList.get(i).toUpperCase()%></a></td>
		<% 
		}else{%>
			<td><a href="BrowseTitle?title=<%=alphaList.get(i)%>"><%=alphaList.get(i).toUpperCase()%></a></td>
			<%
		}
		if(i%5==4)
		{
			%></tr><%
		}
	}
	%>
	
</p>
</body>
</html>