package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

import helper.*;

/**
 * Servlet implementation class GetGenre
 */
//@WebServlet("/GetGenre")
public class GetGenre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetGenre() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public String makeGenreQuery()
    {
    	return "select genres.name from genres";
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html"); 
		Connection c = new Connection();
		boolean usePrepared = (boolean) getServletContext().getAttribute("usePrepared");
		PreparedStatement prepStatement = null;
		ResultSet rs = null;
		PrintWriter out = response.getWriter();
		boolean testScaled = (boolean) getServletContext().getAttribute("testScaledVersion");
		DataSource ds = null;
		DataSource ds2 = null;
		if (testScaled){
			// If doing reads, choose one datasource (master or slave) at random and send that to connection
			ds = (DataSource) getServletContext().getAttribute("masterDB");
			ds2 = (DataSource) getServletContext().getAttribute("slaveDB");
			if (ds == null || ds2 == null)
			{
				System.out.println("Within Login.java, one of masterDB or slaveDB Datasource is null");
			}
		}
		else{
			
			ds = (DataSource)getServletContext().getAttribute("DBCPool");
		}
		c.setDataSource(ds,  ds2);
		ArrayList<String> genreNameList = new ArrayList<String>();
		try
		{
			c.connect();
		}
		catch (Exception e)
		{
			out.println("Database or Password is wrong");
		}
		try{
			String query = makeGenreQuery();
			if (usePrepared){
				System.out.println("Using PreparedStatements");
				prepStatement = c.getConnection().prepareStatement(query);
				rs = prepStatement.executeQuery();
				
			}
			else{
				c.startQuery(query);
				rs = c.getResultSet();
			}
			while (rs.next()){
				String genreName = rs.getString(1);
				genreNameList.add(genreName);
			}
			
			HttpSession s = request.getSession();
			s.setAttribute("genreNameList", genreNameList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Browse.jsp");
			dispatcher.forward(request, response);
		}
		catch (SQLException e)
		{
			out.println("Failed to execute Query");
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
