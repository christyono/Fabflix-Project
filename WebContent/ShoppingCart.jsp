<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.ArrayList,helper.Movie,helper.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping Cart</title>
<jsp:include page = "FrontEnd/NavBar.jsp">
</head>
<body>
	
<% 
	ShoppingCart Cart = (ShoppingCart)session.getAttribute("Cart");
	for(int i = 0; i < Cart.getLength(); i++){
		out.println("Title: \n" + Cart.getItem(i).getTitle());
		out.println("Price: \n" + Cart.getItem(i).getPrice());
		out.println("Quantity: \n" + Cart.getItem(i).getQuantity()); %>

		<form action = "AddItem" method = "get">
			Change Quantity: <br>
			<input type = "text" name = "quantity"><br>
			<input type = "hidden" name = "title" value = "<%= Cart.getItem(i).getTitle()%>">
			<input type = "hidden" name = "id" value = "<%= Cart.getItem(i).getMovieID()%>">
			<input type = "hidden" name = "price" value = "<%= Cart.getItem(i).getPrice()%>">
			<input type = "hidden" name = "special" value = "1">
			<input type = "submit" value = "Submit"><br><br>
		</form>
<%
	}
	out.println("Total price: $" + Cart.getTotalPrice() + "\n");
	
	%> <br> <a href = Checkout.jsp>Checkout</a><% 
%>
</body>
</html>