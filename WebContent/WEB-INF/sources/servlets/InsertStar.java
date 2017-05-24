package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;

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
			   
				
				c.executeUpdate(sqlQuery);
				System.out.println("Number of Rows Changed: " + c.getRetID());
				if(c.getRetID()==0)
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
