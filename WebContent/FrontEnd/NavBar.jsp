<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type = "text/css" href="/Fabflix/css/w3.css">
<!-- <script src = "DisplayOutput.js" type = "text/javascript"> -->

<!-- </script> -->

<script src = "/Fabflix/FrontEnd/Test.js">
</script>
</head>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
	$(document).on("keyup", '#autocomplete', function(){
		console.log("Hello");
		if ($("#autocomplete").val().length > 2){
			var $select = $("#json-datalist");
			$select.empty();
			$.get("/Fabflix/AutoCompleteSearch?query=" + $("#autocomplete").val(), function(responseJson){
				console.log("In responseJson");
				console.log(responseJson);
				if (!$.isArray(responseJson) || !responseJson.length){
					console.log("responseJson is empty");
				}
				$.each(responseJson, function(index, item){
					console.log("adding option nodes");
					$("<option>").text(item).appendTo($select);
				});
			});	
		}
		
	});
</script>
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
		<a href = "/Fabflix/LoginPage.jsp"> Please login first</a>
		</div>
		<%
	}
	else{
		%>
			<form class="w3-bar w3-border w3-green w3-mobile w3-padding w3-round" action = "/Fabflix/FindMovie">
				<a href="/Fabflix/LoginPage.jsp" class="w3-bar-item w3-button w3-mobile">Login</a>
				<a href="/Fabflix/main.jsp" class="w3-bar-item w3-button w3-mobile">Main Page</a>
				<a href="/Fabflix/Logout" class="w3-bar-item w3-button w3-mobile">Logout</a> 
				<a href="/Fabflix/ShoppingCart.jsp" class="w3-bar-item w3-button w3-mobile w3-padding">Shopping Cart</a> 
				<input type="text" class="w3-bar-item w3-mobile" list = 'json-datalist' name = "title" id = 'autocomplete' autocomplete="off" placeholder="Search..">
				<datalist id = 'json-datalist'>
				</datalist> 
				<input type="hidden" name="year" value="" /> 
				<input type="hidden" name="director" value="" /> 
				<input type="hidden" name="first_name" value="" /> 
				<input type="hidden" name="last_name" value="" />
				 <button class="w3-button w3-green w3-mobile">Go</button> 
				 </form>

		<% 
	}
	%>
</body>
</html>