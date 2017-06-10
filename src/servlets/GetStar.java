package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import helper.*;

/**
 * Servlet implementation class GetStar
 */
//@WebServlet("/GetStar")
public class GetStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public String getStarGivenID(int starID)
    {
    	// based on starID, getStar
    	String query = String.format("select stars.id, stars.first_name, stars.last_name, stars.dob, stars.photo_url "
    			+ "from stars "
    			+ "where stars.id = %s; ", starID);
    	return query;
        
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
    public Star getStarMethod(int starID) throws SQLException
    {
    	Connection c= new Connection();
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
		catch(Exception e)
		{
			System.out.println("Failed to connect");
		}
		try{
			
			
			ResultSet r = null;
			PreparedStatement prepStatement = null;

			if(usePrep){
			prepStatement = c.getConnection().prepareStatement(getStarGivenID(starID));
			r = prepStatement.executeQuery();
			}
			else{
				c.startQuery(getStarGivenID(starID));
				r = c.getResultSet();
			}
			
			int ID;
			String firstName;
			String lastName;
			Date dateOfBirth;
			String photoURL;
			Star star = new Star();
			while (r.next())
			{
				ID = r.getInt(1);
				firstName = r.getString(2);
				lastName = r.getString(3);
				dateOfBirth = r.getDate(4);
				photoURL = r.getString(5);
				star = new Star(ID, firstName, lastName, dateOfBirth, photoURL);
				
			}
			
			
			if(usePrep){
			prepStatement = c.getConnection().prepareStatement(makeStarredQuery(starID));
			r = prepStatement.executeQuery();
			}
			else{
				c.startQuery(makeStarredQuery(starID));
				r = c.getResultSet();
			}
			
			
			
			
			while (r.next())
			{
				int movieID = r.getInt(1);
				String title = r.getString(2);
				int year = r.getInt(3);
				String director = r.getString(4);
				String bannerURL = r.getString(5);
				String trailerURL = r.getString(6);
				
				star.addStarredMovie(movieID, title, year, director, bannerURL, trailerURL);
			}
			c.closeConnection();
			return star;
		}
		catch(SQLException e)
		{
			System.out.println("Failed to execute query");
			return null;
		}

    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html"); 

		String id = request.getParameter("param1");
		int starID = Integer.parseInt(id);
		Connection c = new Connection();
		HttpSession session = request.getSession(false);
		Star desiredStar = null;
		
		if (session == null)
		{
			out.println("<br>Please login first<br>");
			RequestDispatcher rs = request.getRequestDispatcher("/index.html");
			rs.include(request, response);
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<Movie> movieList = (ArrayList<Movie>)session.getAttribute("movieList");
		out.println("<br> STARID: " + starID + "<br>");
		for (int i = 0; i < movieList.size(); i++)
		{
			ArrayList<Star> starList = movieList.get(i).getStarList();
			for (int j = 0; j < starList.size(); j++)
			{
				Star star = starList.get(j);
				if (starID == star.getStarID()){
					desiredStar = star;
				}
			}
		}
		try{
			if (desiredStar == null)
			{
				desiredStar = getStarMethod(starID);
			}
		}
		catch(SQLException e)
		{
			out.println("Failed to findStar within getStarMethod");
		}
		
		request.setAttribute("currentStar", desiredStar);
		RequestDispatcher rs = request.getRequestDispatcher("/DisplayStar.jsp");
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
