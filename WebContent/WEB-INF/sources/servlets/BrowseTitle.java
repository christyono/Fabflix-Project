package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helper.Connection;
import helper.Movie;

/**
 * Servlet implementation class BrowseTitle
 */
//@WebServlet("/BrowseTitle")
public class BrowseTitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseTitle() {
        super();
        // TODO Auto-generated constructor stub
    }

    private String makeStarredQuery(int starID)
    {
    	// based on star.id find all movies that the star has starred in
    	String query = String.format("select movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url "
    			+ "from stars, stars_in_movies, movies "
    			+ "where stars_in_movies.movie_id = movies.id "
    			+ "and stars.id = %s "
    			+ "and stars_in_movies.star_id = stars.id;", starID);
    	return query;
    }
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
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		
		PrintWriter out = response.getWriter();
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
			HttpSession session = request.getSession(false);
			if (session == null)
			{
				out.println("<br>Please login first<br>");
				RequestDispatcher rs = request.getRequestDispatcher("/index.html");
				rs.include(request, response);
				return;
			}
			String query = "select movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url "
    				+ "from movies "
    				+ "where movies.title LIKE \"" + request.getParameter("title") + "%\"";
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
				if (!movieList.isEmpty())
				{
					if (movieID != movieList.get(counter - 1).getID())
					{
						// NOTE: this piece of code is here to make sure that if the same movie
						// shows up multiple times, it will only be added once to movieList
						// the reason why a movie shows up more than once
						// is because the query includes stars, and a movie can have more than one star
						newMovie = new Movie(movieID, title, year, director, bannerURL, trailerURL);
						movieList.add(newMovie);
						counter++;
					}
					
					
					
				}
				else
				{
					newMovie = new Movie(movieID, title, year, director, bannerURL, trailerURL);
					movieList.add(newMovie);
					counter++;
				}
				
				


			}
			
			for (int i = 0; i < movieList.size(); i++)
			{
				// make query given ID
				String starQuery = makeStarQuery(movieList.get(i).getID());
				String genreQuery = makeGenreQuery(movieList.get(i).getID());
				
				//out.println(starQuery);
				c.startQuery(starQuery);
				while (c.getResultSet().next())
				{
					// for each movie, given its movie ID, get the stars
					int starID = c.getResultSet().getInt(1);
					String firstName = c.getResultSet().getString(2);
					String lastName = c.getResultSet().getString(3);
					Date dateOfBirth = c.getResultSet().getDate(4);
					String photoURL = c.getResultSet().getString(5);
					movieList.get(i).addStar(starID, firstName, lastName, dateOfBirth, photoURL);
				}
				//out.println(genreQuery);
				c.startQuery(genreQuery);
				
				while (c.getResultSet().next())
				{
					// for each movie, given its movie ID, get the 
					String genre = c.getResultSet().getString(1);
					int genreID = c.getResultSet().getInt(2);
					movieList.get(i).addGenre(genreID, genre);
				}
			}
			
			// the below code adds starred movies to the stars in movieList
			
			for (int i = 0; i < movieList.size(); i++)
			{
				for (int j = 0; j < movieList.get(i).getStarList().size(); j++)
				{
					int starID = movieList.get(i).getStarList().get(j).getStarID();
					String starredQuery = makeStarredQuery(starID);
					c.startQuery(starredQuery);
					while (c.getResultSet().next())
					{
						// add list of movies in which the star has starred in 
						
						int movieID = c.getResultSet().getInt(1);
						String title = c.getResultSet().getString(2);
						int year = c.getResultSet().getInt(3);
						String director = c.getResultSet().getString(4);
						String bannerURL = c.getResultSet().getString(5);
						String trailerURL = c.getResultSet().getString(6);
						movieList.get(i).getStarList().get(j).addStarredMovie(movieID, title, year, director, bannerURL, trailerURL);
						
					}
					
				}
				
			}
			
			
			if (session != null && session.getAttribute("movieList") != null)
			{
				session.removeAttribute("movieList");
			}
			if (session.getAttribute("offSet") != null)
			{
				session.removeAttribute("offset");
			}
			if (session.getAttribute("pageNum") != null)
			{
				session.removeAttribute("pageNum");
			}
			if (session.getAttribute("limit") != null)
			{
				session.removeAttribute("limit");
			}
			session.setAttribute("movieList", movieList);
			RequestDispatcher rs = request.getRequestDispatcher("/DisplaySearch.jsp");
			rs.forward(request, response);
			c.closeConnection();
		}
		catch (SQLException e)
		{
			e.printStackTrace(response.getWriter());
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
