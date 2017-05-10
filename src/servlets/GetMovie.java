package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

import helper.*;
import java.util.*;

/**
 * Servlet implementation class GetMovie
 */
//@WebServlet("/GetMovie")
public class GetMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private String makeStarQuery(int movieID)
    {
    	// based on movieID, find given stars
    	String query = String.format("select stars.id, stars.first_name, stars.last_name, stars.dob, stars.photo_url "
    			+ "from movies, stars_in_movies, stars "
    			+ "where movies.id = stars_in_movies.movie_id "
    			+ "and stars.id = stars_in_movies.star_id "
    			+ "and movies.id = %s;", movieID);
    	return query;
    }
    private String makeGenreQuery(int movieID)
    {
    	// based on movieID, find Genres
    	String query = String.format("select genres.name, genres.id "
    			+ "from movies, genres, genres_in_movies "
    			+ "where movies.id = %s "
    			+ "and genres_in_movies.movie_id = movies.id "
    			+ "and genres_in_movies.genre_id = genres.id;", movieID);
    	return query;
    }
    private String findMovieGivenID(int movieID)
    {
    	
    	// based on star.id find all movies that the star has starred in
    	String query = String.format("select movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url "
    			+ "from stars, stars_in_movies, movies "
    			+ "where stars_in_movies.movie_id = movies.id "
    			+ "and movies.id = %s "
    			+ "and stars_in_movies.star_id = stars.id;",movieID);
    	return query;
    }
    public Movie getMovieFromStar(int movieID) throws SQLException
    {
    	Connection c = new Connection();
    	try{
    		
    		c.connect();
    	}
    	catch (Exception e)
    	{
    		System.out.println("Failed to connect, username or password wrong");
    	}
    	try{
    		c.startQuery(findMovieGivenID(movieID));
    		// Get the movie based on starID and movieID
    		Movie movie = new Movie();
    		int movID;
    		String title;
    		int year;
    		String director;
    		String bannerURL;
    		String trailerURL;
    		while (c.getResultSet().next()){
    			movID = c.getResultSet().getInt(1);
    			title = c.getResultSet().getString(2);
    			year = c.getResultSet().getInt(3);
    			director = c.getResultSet().getString(4);
    			bannerURL = c.getResultSet().getString(5);
    			trailerURL = c.getResultSet().getString(6);
    			movie.setID(movID);
    			movie.setTitle(title);
    			movie.setYear(year);
    			movie.setDirector(director);
    			movie.setBannerURL(bannerURL);
    			movie.setTrailerURL(trailerURL);
    			movie.setPrice(2);
    			
    		}
    		
			
			
			c.startQuery(makeStarQuery(movieID));
			while (c.getResultSet().next())
			{
				// for each movie, given its movie ID, get the stars
				int star = c.getResultSet().getInt(1);
				String firstName = c.getResultSet().getString(2);
				String lastName = c.getResultSet().getString(3);
				Date dateOfBirth = c.getResultSet().getDate(4);
				String photoURL = c.getResultSet().getString(5);
				movie.addStar(star, firstName, lastName, dateOfBirth, photoURL);
			}
			//out.println(genreQuery);
			c.startQuery(makeGenreQuery(movieID));
			
			while (c.getResultSet().next())
			{
				// for each movie, given its movie ID, get the 
				String genre = c.getResultSet().getString(1);
				int genreID = c.getResultSet().getInt(2);
				movie.addGenre(genreID, genre);
			}
			c.closeConnection();
			return movie;
    		
    	}
    	catch (SQLException e){
    		System.out.println("query failed to execute");
    		return null;
    	}
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// What this servlet does:
		// 1) given movieID, find the desiredmovie
		// 2) given movieID and starID, find the given movie where the star is featured
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();
		String id1 = request.getParameter("param1");
		String id2 = request.getParameter("param2");
		
		int movieID = Integer.parseInt(id1);
		int starID;
		
		HttpSession session = request.getSession(false);
		
		if (session == null)
		{
			out.println("<br>Please login first<br>");
			RequestDispatcher rs = request.getRequestDispatcher("/index.html");
			rs.include(request, response);
			return;
		}
		ArrayList<Movie> movieList = (ArrayList<Movie>)session.getAttribute("movieList");
		Movie desiredMovie = null;
		// first block: user clicks on a hyperlinked movie from the display search page
		if (id2 == null)
		{
			for (int i = 0; i < movieList.size(); i++)
			{
				Movie movie = movieList.get(i);
				if (movie.getID() == movieID)
				{
					desiredMovie = movieList.get(i);
					break;
				}
			}
		}
		// second block: user clicks on a hyperlinked movie from the display star page
		// therefore, there is no guarantee that the movielist on session will have all the movies
		// that a particular star has starred in
		// therefore, find the movie based on the title and starID
		else
		{
			starID = Integer.parseInt(id2);
			try{
				desiredMovie = getMovieFromStar(movieID);
			}
			catch (SQLException e){
				System.out.println("SQL query failed to execute");
			}
			
//			starID = Integer.parseInt(id2);
//			for (int i = 0; i < movieList.size(); i++)
//			{
//				Movie movie = movieList.get(i);
//				// make the second loop to search through the list of movies where the star is featured
//				for (int j = 0; j < movie.getStarList().size(); j++)
//				{
//					Star star = movieList.get(i).getStarList().get(j);
//					// given the star, find its starredMovieList
//					if (star.getStarID() == starID)
//					{
//						for (int k = 0; j < star.getStarredMovieList().size();k++)
//						{
//							// given the starredmovieList, find the correct movie
//							Movie m = star.getStarredMovieList().get(k);
//							if (movieID == m.getID())
//							{
//								desiredMovie = m;
//								break;
//							}
//						}
//					}
//				}
//			}
			
		}
		
		
		request.setAttribute("currentMovie", desiredMovie);
		if (desiredMovie == null)
		{
			return;
		}
		out.println("desiredMovie Title: " + desiredMovie.getTitle());
		ArrayList <Genre> g = desiredMovie.getGenreList();
		for (int i = 0; i < g.size(); i++)
		{
			out.println("<br>" + g.get(i).getName() + "<br>");
		}
		RequestDispatcher rs = request.getRequestDispatcher("/DisplayMovie.jsp");
		rs.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
