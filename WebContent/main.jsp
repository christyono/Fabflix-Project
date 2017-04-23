<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1> Welcome to Fabflix </h1>
<hr>
<p>
	Welcome ${sessionScope['username']}
	<form action = "" method = "get">
		Search for movies..: <br>
		<input type ="text" name = "searchQuery"><br>
</p>
</form>
</body>
</html>