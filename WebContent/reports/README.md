# 122B-Project-2
Project 2

FILE LOCATIONS:

Project2/WebContent/WEB-INF/classes/helper contains all the .class files for helper classes
Project2/WebContent/WEB-INF/classes/servlets contains all the .class files for the servlets

Project2/WebContent/WEB-INF/sources contains all .java source files
Project2/WebContent contains all JSP files contains like-predicate.html and README.md

HOW TO COMPILE:

Assuming you are in working directory of the .java source files,
javac -classpath "../../lib/servlet-api.jar;../../lib/mysql-connector-java-5.0.8-bin.jar;../" -d ../../classes --filename--.java

the -d command will redirect the .class file to Project2/WebContent/WEB-INF/

Project2/WebContent/reports
Breakdown 5/1/17:

Helper Files:
	Movie.java, Star.java, Genre.java
	Represents all info about each class 
	(i.e Movie has title, director..., star has first and last name, date of birth...)
	
Current Workflow:

Logging in 
1)  index.html, user logs in, request to Login.java
2)  login.java checks if user info is valid
2a) if info is valid
		go to main page (main.jsp), 
	else
		back to index.html, displays error message 

Main page
1) User can log out, or go to advanced search page, or browse

Advanced Search
1) User fills out html form with movie info
2) search info is sent to FindMovie.java
3) FindMovie.java processes search info and finds right movies based on info
		** builds correct SQL queries
		3a) stores movies in movielist
		3b) stores movielist in the browser session so it can be accessed anywhere
		3c) sends movielist to DisplaySearch.jsp

Display Search
1) User can click on sort buttons

	if user wants to sort
		send sort info (by title/year and descending/ascending) to SortMovie.java
		grabs movieList from session, makes a copy
		sorts the copy based on sort info
		sends copy back to DisplaySearch
		
2) User can click on hyperlinks to see Movie or Star in detail

	If user clicks on Movie
		sends movieID to GetMovie.java
		retrieves correct movie based on movieID
		sends movie to DisplayMovie.jsp
		DisplayMovie.jsp displays the movie info
		
	If user clicks on star
		sends starID to GetStar.java
		retrieves correct star based on starID
		sends star to DisplayStar.jsp
		DisplayStar.jsp displays star
		*** If user clicks on starred movie ***
			StarID AND MovieID to GetMovie.java to find the correct movies to display
			sends correct movies to DisplayMovie.jsp
			
3) User can return to previous page

What we Need:

Browsing, Pagination, Shopping Cart w/ Checkout
Better looking frontend? (If we have time)

