<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type = "text/css" href="../css/w3.css">
</head>

<body>
<!-- header -->
 	<header class="w3-display-container w3-wide w3-content w3-mobile" style = "max-width:1500px">
	 	<img class ="w3-image" src = "http://media.architecturaldigest.com/photos/5699802bc6772b7614567435/2:1/w_2560/new-york-city-guide.jpg" alt = "New York" style = "height:400px;width:1500px">
	 	<div class = "w3-display-middle w3-padding w3-wide w3-border w3-text-light-grey w3-center ">
	 	<h1>Welcome to Fabflix</h1>
	 	</div>
	</header>
	
	<%if (session.getAttribute("username") == null)
	{
		%>
		<div class = "w3-panel w3-red">
		<a href = "index.html"> Please login first</a>
		</div>
		<%
	}
	else{
		%>
			<div class="w3-bar w3-border w3-green w3-mobile w3-padding w3-round">
				<a href="LoginPage.jsp" class="w3-bar-item w3-button w3-mobile">Login</a>
				<a href="main.jsp" class="w3-bar-item w3-button w3-mobile">Main Page</a>
				<a href="Logout" class="w3-bar-item w3-button w3-mobile">Logout</a>
				<a href="ShoppingCart.jsp" class="w3-bar-item w3-button w3-mobile w3-padding">Shopping Cart</a>
				<input type="text" class="w3-bar-item w3-input w3-mobile" placeholder="Search..">
				<a href="#" class="w3-bar-item w3-button w3-green w3-mobile">Go</a>
			</div>

		<% 
	}
	%>
</body>
</html>