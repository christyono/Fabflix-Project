<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.ArrayList,helper.Movie,helper.Star,helper.Genre" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/w3.css">
<title>Browse</title>
</head>

<jsp:include page = "FrontEnd/NavBar.jsp"/>
<body>
<p>
		
	<%
	ArrayList<String> genreNameList = new ArrayList<String>();
	genreNameList = (ArrayList<String>) session.getAttribute("genreNameList");
	String s = "0123456789abcdefghijklmnopqrstuvwxyz";
	ArrayList<String> alphaList = new ArrayList<String>();
	for (int i = 0; i < s.length(); i++){
	    alphaList.add(""+s.charAt(i));
	}
	%>
	<div class = "w3-container w3-padding">
	<div class = "w3-panel w3-light-grey w3-large">Browse by Titles:</div>
	<table class = "w3-table w3-striped w3-padding w3-bordered w3-mobile" style="width:100%">
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
	</div>
	
	<div class = "w3-container w3-padding">
	<div class = "w3-panel w3-light-grey w3-large">Browse by Titles:</div>
	<table class = "w3-table w3-striped w3-padding w3-bordered  w3-mobile" style="width:100%">
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
	</table>
	</div>
	
	
	
</p>
</body>
</html>