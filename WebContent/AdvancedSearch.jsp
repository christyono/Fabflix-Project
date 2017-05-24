<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.ArrayList,helper.Movie,helper.Star,helper.Genre" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/w3.css">
<title>Advanced Search</title>
</head>
<jsp:include page = "FrontEnd/NavBar.jsp"/>
<body>
	<div class = "w3-cell-row w3-padding-24">
		<form class = "w3-container w3-card-4 w3-mobile w3-margin w3-light-grey" action = "FindMovie" method = "get">
			<h2 class = "w3-center">Advanced Search</h2>
			<div class = "w3-row w3-section"></div>
				<div class = "w3-col s3"><label class = "w3-text-blue" style="width:50px"><b>Title: </b></label></div>
				<div class = "w3-col s9"><input class = "w3-input w3-border" name = "title" type = "text"></div>
			<div class = "w3-row w3-section"></div>
				<div class = "w3-col s3"> <label class = "w3-text-blue" style="width:50px"><b>Year: </b></label></div>
				<div class = "w3-col s9"> <input class = "w3-input w3-border" type ="text" name = "year"> </div>
			<div class = "w3-row w3-section"></div>
				<div class = "w3-col s3"> <label class = "w3-text-blue" style="width:50px"><b>Director: </b></label></div>
				<div class = "w3-col s9"> <input class = "w3-input w3-border" type ="text" name = "director"> </div>
			<div class = "w3-row w3-section"></div>
				<div class = "w3-col s3"> <label class = "w3-text-blue" style="width:50px"><b>Star's First Name: </b></label></div>
				<div class = "w3-col s9"> <input class = "w3-input w3-border" type ="text" name = "first_name"> </div>
			<div class = "w3-row w3-section"></div>
				<div class = "w3-col s3"> <label class = "w3-text-blue" style="width:50px"><b>Star's Last Name: </b></label></div>
				<div class = "w3-col s9"> <input class = "w3-input w3-border" type ="text" name = "last_name"> </div>
			<div class = "w3-row w3-section"></div>
			<button class = "w3-button w3-block w3-section w3-green w3-padding">Submit</button>
		</form>
	</div>


</body>
</html>