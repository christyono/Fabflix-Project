package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import helper.Connection;

/**
 * Servlet implementation class ShowMetadata
 */
//@WebServlet("/ShowMetadata")
public class ShowMetadata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowMetadata() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();
		response.getWriter().append("Served at: ").append(request.getContextPath());


		Connection c = new Connection();
		boolean testScaled = (boolean) getServletContext().getAttribute("testScaledVersion");
		boolean usePrepared = (boolean) getServletContext().getAttribute("usePrepared");
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
		ResultSet rs = null;
		PreparedStatement prepStatement = null;

		String output = "";

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
			String searchQuery = "select table_name from information_schema.tables where table_schema = 'moviedb'";
			if (session == null)
			{
				out.println("<br>Please login first<br>");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
				dispatcher.include(request, response);
				return;
			}
			if (usePrepared)
			{
				System.out.println("Using preparedStatement");
				prepStatement = c.getConnection().prepareStatement(searchQuery);
				rs = prepStatement.executeQuery();	
			}
			else
			{
				
				c.startQuery(searchQuery);
				rs = c.getResultSet();
			}
             //loops until all table names are printed
            while(rs.next())
            {

            	output += "<br>";
            	output += ("Table name: " + rs.getString(1) + "<br>");
          	   
          	   //print table data
            	c.startQuery("describe " + rs.getString(1));
            	output += "Fields :<br>";
            	ResultSet tableData = (ResultSet) c.getResultSet();
            	while(tableData.next())
            	{
            		output += (tableData.getString(1) + " [" + tableData.getString(2) + "] <br>");
            	}
           }
            session.setAttribute("output", output);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/DisplayMetadata.jsp");
			dispatcher.forward(request, response);
			c.closeConnection();
  	   }
  	   catch(SQLException e)
  	   {
  		   out.print("<br>Failed to print metadata<br>");
  		   
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
