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

import java.sql.*;

import helper.Connection;

/**
 * Servlet implementation class InsertStar
 */
//@WebServlet("/InsertStar")
public class InsertStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertStar() {
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
		
		Connection c = new Connection();
		boolean testScaled = (boolean) getServletContext().getAttribute("testScaledVersion");
		boolean usePrepared = (boolean) getServletContext().getAttribute("usePrepared");
		DataSource ds = null;
		DataSource ds2 = null;
		PreparedStatement preparedStatement = null;
		int retID;
		if (testScaled){
			// If doing reads, choose one datasource (master or slave) at random and send that to connection
			// We get connection from master because we are doing writes
			ds = (DataSource) getServletContext().getAttribute("masterDB");
		}
		else{
			
			ds = (DataSource)getServletContext().getAttribute("DBCPool");
		}
		c.setDataSource(ds, ds2);
		
		   String lastName = "";
		   String firstName = "";

		   if (request.getParameter("firstName") != "")
		   {
			   if (request.getParameter("lastName") != "")
			   {
				   firstName = (String) request.getParameter("firstName");
				   lastName = (String) request.getParameter("lastName");
			   }
			   else
			   {
				   lastName = (String) request.getParameter("firstName");
			   }
		   }
		   else
		   {
			   lastName = (String) request.getParameter("lastName");
		   }

		   String dateOfBirth = (String) request.getParameter("dateOfBirth");
		   
		   String URL = (String) request.getParameter("photoURL");
		
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
					RequestDispatcher rs = request.getRequestDispatcher("/_dashboard.html");
					rs.include(request, response);
					return;
				}
			   String sqlQuery = "insert into stars (first_name, last_name, dob, photo_url) values ('" + firstName+ "', '" + lastName+ "', '" + dateOfBirth + "', '" + URL + "');";
			   if (usePrepared){
				   preparedStatement = c.getConnection().prepareStatement(sqlQuery);
				   retID = preparedStatement.executeUpdate();  
			   }
			   else{
				c.executeUpdate(sqlQuery);
				retID = c.getRetID();
				   
			   }
				System.out.println("Number of Rows Changed: " + retID);
				if(retID==0)
				{
					out.println("<br>Failed to execute Query<br>");

					RequestDispatcher rs = request.getRequestDispatcher("/NewStar.jsp");
					rs.include(request, response);
					out.close();
				}
				else
				{
					RequestDispatcher rs = request.getRequestDispatcher("/EmployeeConfirmation.jsp?success=true");
					rs.forward(request, response);
					
				}
			}
			catch (SQLException e)
			{
				out.println("<br>Failed to execute Query<br>");
				RequestDispatcher rs = request.getRequestDispatcher("/NewStar.jsp");
				rs.include(request, response);
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
