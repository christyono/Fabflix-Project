package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
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
		ResultSet r = null;
		PreparedStatement prepStatement = null;
		boolean testScaled = (boolean) getServletContext().getAttribute("testScaledVersion");
    	boolean usePrep = (boolean) getServletContext().getAttribute("usePrepared");
		DataSource ds = null;
		DataSource ds2 = null;
		if (testScaled){
			// If doing reads, choose one datasource (master or slave) at random and send that to connection
			ds = (DataSource) getServletContext().getAttribute("masterDB");
			ds2 = (DataSource) getServletContext().getAttribute("slaveDB");
		}
		else{
			
			ds = (DataSource)getServletContext().getAttribute("DBCPool");
		}
		c.setDataSource(ds, ds2);	
    	
    	try{
    		
    		c.connect();
    	}
    	catch (Exception e)
    	{
    		System.out.println("Failed to connect, username or password wrong");
    	}
    	try{
    		
			if(usePrep){
			prepStatement = c.getConnection().prepareStatement(findMovieGivenID(movieID));
			r = prepStatement.executeQuery();
			}
			else{
				c.startQuery(findMovieGivenID(movieID));
				r = c.getResultSet();
			}
    		
    		// Get the movie based on starID and movieID
    		Movie movie = new Movie();
    		int movID;
    		String title;
    		int year;
    		String director;
    		String bannerURL;
    		String trailerURL;
    		while (r.next()){
    			movID = r.getInt(1);
    			title = r.getString(2);
    			year = r.getInt(3);
    			director = r.getString(4);
    			bannerURL = r.getString(5);
    			trailerURL = r.getString(6);
    			movie.setID(movID);
    			movie.setTitle(title);
    			movie.setYear(year);
    			movie.setDirector(director);
    			movie.setBannerURL(bannerURL);
    			movie.setTrailerURL(trailerURL);
    			movie.setPrice(2);
    			
    		}
    		
			
			if(usePrep){
			prepStatement = c.getConnection().prepareStatement(makeStarQuery(movieID));
			r = prepStatement.executeQuery();
			}
			else{
				c.startQuery(makeStarQuery(movieID));
				r = c.getResultSet();
			}
    		
    		
			
			c.startQuery(makeStarQuery(movieID));
			while (r.next())
			{
				// for each movie, given its movie ID, get the stars
				int star = r.getInt(1);
				String firstName = r.getString(2);
				String lastName = r.getString(3);
				Date dateOfBirth = r.getDate(4);
				String photoURL = r.getString(5);
				movie.addStar(star, firstName, lastName, dateOfBirth, photoURL);
			}
			//out.println(genreQuery);
			
			
			if(usePrep){
			prepStatement = c.getConnection().prepareStatement(makeGenreQuery(movieID));
			r = prepStatement.executeQuery();
			}
			else{
				c.startQuery(makeGenreQuery(movieID));
				r = c.getResultSet();
			}
			
			
			
			while (r.next())
			{
				// for each movie, given its movie ID, get the 
				String genre = r.getString(1);
				int genreID = r.getInt(2);
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
		@SuppressWarnings("unchecked")
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
