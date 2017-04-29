package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import java.util.*;

import helper.Connection;
import helper.Movie;
import helper.Star;

/**
 * Servlet implementation class DisplayMovie
 */
// @WebServlet("/DisplayMovie")
public class DisplayMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private String makeMovieQuery(String title, String year, String director, String lastName, String firstName)
    {
    	// NOTE: this function is used only within this class
    	// makeConditionalQuery will create the necessary search query for the movies only
    	
    	// LIKE: 
    	// %__% substring matching, where __ pattern will be matched anywhere within string
    	String query;
    	if (firstName.equals("%%") && lastName.equals("%%"))
    	{
    		// if first and last name are empty, there's no need to include it as part of query
    		
    		query = String.format("select * "
    				+ "from movies, stars_in_movies, stars "
    				+ "where movies.title LIKE \"%s\" "
        			+ "and movies.year LIKE \"%s\" "
        			+ "and movies.director LIKE \"%s\" "
        			+ "and movies.id = stars_in_movies.movie_id "
        			+ "and stars.id = stars_in_movies.star_id;", title, year, director, firstName, firstName);
    	}
    	else if (lastName.equals("%%"))
    	{
    		// when only firstName is given, either firstName or lastName can have the "firstName" value
    		query = String.format("select * "
    				+ "from movies, stars_in_movies, stars "
    				+ "where movies.title LIKE \"%s\" "
        			+ "and movies.year LIKE \"%s\" "
        			+ "and movies.director LIKE \"%s\" "
        			+ "and movies.id = stars_in_movies.movie_id "
        			+ "and stars.id = stars_in_movies.star_id "
        			+ "and (stars.first_name LIKE \"%s\" or stars.last_name LIKE \"%s\");", title, year, director, firstName, firstName);
    	}
    	else if (firstName.equals("%%"))
    	{
    		// when only lastName is given, either firstName or lastName can have the "lastname" value
    		query = String.format("select * "
    				+ "from movies, stars_in_movies, stars "
    				+ "where movies.title LIKE \"%s\" "
        			+ "and movies.year LIKE \"%s\" "
        			+ "and movies.director LIKE \"%s\" "
        			+ "and movies.id = stars_in_movies.movie_id "
        			+ "and stars.id = stars_in_movies.star_id "
        			+ "and (stars.first_name LIKE \"%s\" or stars.last_name LIKE \"%s\");", title, year, director, lastName, lastName);
    	}
    	else
    	{
    		// if both first and last name are given, both are taken into account in the search query
    		
    		query = String.format("select * "
    				+ "from movies, stars_in_movies, stars "
    				+ "where movies.title LIKE \"%s\" "
        			+ "and movies.year LIKE \"%s\" "
        			+ "and movies.director LIKE \"%s\" "
        			+ "and movies.id = stars_in_movies.movie_id "
        			+ "and stars.id = stars_in_movies.star_id "
        			+ "and stars.first_name LIKE \"%s\" "
        			+ "and stars.last_name LIKE \"%s\";", title, year, director, firstName, lastName);
    	}
    	return query;
    }
    private String makeStarQuery(int movieID)
    {
    	// not called in this servlet right now, but it finds a star's first and last name based on a certain movieID
    	// might be helpful
    	
    	String query = String.format("select movies.title, stars.first_name, stars.last_name "
    			+ "from movies, stars_in_movies, stars "
    			+ "where movies.id = stars_in_movies.movie_id "
    			+ "and stars.id = stars_in_movies.star_id "
    			+ "and movies.id = %s;", movieID);
    	return query;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		
		PrintWriter out = response.getWriter();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String [] urlParams = {"title", "year", "director", "first_name", "last_name"};
		HashMap <String, String> searchParams = new HashMap<String, String>();
		
		// this function matches the parameter name with the request and adds into hashmap
		for (int i = 0; i < urlParams.length; i++)
		{
			searchParams.put(urlParams[i], request.getParameter(urlParams[i]));
		}
		
		Connection c = new Connection();
		try
		{
			c.connect();
		}
		catch (Exception e)
		{
			out.println("Database or Password is wrong");
		}
		try
		{
			
			String query = makeMovieQuery("%" + searchParams.get("title") + "%", 
					"%" + searchParams.get("year") + "%",
					"%" + searchParams.get("director") + "%",
					"%" + searchParams.get("first_name") + "%",
					"%" + searchParams.get("last_name") + "%");
			// these Strings are the search Queries
			// NOTE: if user leaves one of the fields blank in the advanced search, the generated search query will be
			// LIKE "%%" for that particular field, so that it doesn't match anything
			out.println(query);
			c.startQuery(query);
			Movie newMovie = null;
			int counter = 0;
			while (c.getResultSet().next())
			{
				// add all attributes to movie 
				int movieID = c.getResultSet().getInt(1);
				String title = c.getResultSet().getString(2);
				int year = c.getResultSet().getInt(3);
				String director = c.getResultSet().getString(4);
				String bannerURL = c.getResultSet().getString(5);
				String trailerURL = c.getResultSet().getString(6);
				
				// add all star attributes to movieStar
				int starID = c.getResultSet().getInt(9);
				String firstName = c.getResultSet().getString(10);
				String lastName = c.getResultSet().getString(11);
				int dateOfBirth = c.getResultSet().getInt(12);
				String photoURL = c.getResultSet().getString(13);
				
				if (!movieList.isEmpty())
				{
					if (movieID == movieList.get(counter - 1).getID())
					{
						// when iterating through resultSet, if the movieID is the same as the previous one
						// that means all other attributes will be the same except the star firstName and lastName
						// in this case, just add a new star to the existing movie
						movieList.get(counter - 1).addStar(starID, firstName, lastName, dateOfBirth, photoURL);
					}			
				}
				else
				{
					// add a new movie
					newMovie = new Movie(movieID, title, year, director, bannerURL, trailerURL);
					newMovie.addStar(starID, firstName, lastName, dateOfBirth, photoURL);
					movieList.add(newMovie);
					counter++;
				}
				
				
				
				
			}
			
			request.setAttribute("movieList", movieList);
			RequestDispatcher rs = request.getRequestDispatcher("/DisplaySearch.jsp");
			rs.forward(request, response);
			c.close();
			
		}
		catch (SQLException e)
		{
			out.println("Failed to execute Query");
		}
		finally
		{
			out.close();
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
