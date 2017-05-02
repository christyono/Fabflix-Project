<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.ArrayList,helper.Movie,helper.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>test</title>
</head>
<body>



<% 
	ShoppingCart Cart = (ShoppingCart)session.getAttribute("Cart");
	for(int i = 0; i < Cart.getLength(); i++){
		if(Cart.getID(i) == Integer.parseInt(request.getParameter("id")))
			out.println("currently " + Cart.getItem(i).getQuantity() + " of this item in the cart");
	}
%>

<form action = "AddItem" method = "get">
	Enter Quantity: <br>
	<input type = "text" name = "quantity"><br>
	<input type = "hidden" name = "title" value = "<%= request.getParameter("title") %>">
	<input type = "hidden" name = "id" value = "<%= request.getParameter("id") %>">
	<input type = "hidden" name = "price" value = "<%= request.getParameter("price") %>">
	<input type = "hidden" name = "special" value = "0">
	<input type = "submit" value = "Submit"><br>
</form>


</body>
</html>