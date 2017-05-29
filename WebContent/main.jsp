<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fabflix Homepage</title>
<link rel="stylesheet" href="/Fabflix/css/w3.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<jsp:include page = "FrontEnd/NavBar.jsp"/>
<body>
	
	<div class = "w3-panel-large w3-round jw3-teal w3-padding">
		Welcome ${sessionScope['username']} 
	</div>
	<div class = "w3-container w3-blue">
		<p><a href = "AdvancedSearch.jsp">Advanced Search</a></p>
		<p><a href="GetGenre">Browse Movies</a></p>
	</div>
</body>
</html>
</body>
</html>