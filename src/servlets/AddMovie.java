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

import helper.Connection;

/**
 * Servlet implementation class AddMovie
 */
@WebServlet("/AddMovie")
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovie() {
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
		   String photoURL = (String) request.getParameter("photoURL");
		   String title = (String) request.getParameter("title");
		   int year = 0;
		   try{
			   year = Integer.parseInt((String) request.getParameter("year"));
		   }catch(Exception e)
		   {
			   out.println("<br>Enter an integer for the year<br>");
			   RequestDispatcher rs = request.getRequestDispatcher("/NewMovie.jsp");
			   rs.include(request, response);
			   out.close();
			   
		   }
		   String director = (String) request.getParameter("director");
		   String bannerURL = (String) request.getParameter("bannerURL");
		   String trailerURL = (String) request.getParameter("trailerURL");
		   String genreName = (String) request.getParameter("genreName");
		   try
			{
				c.connect();
			}
			catch (Exception e)
			{
				out.println("Database or Password is wrong");
			}
		   try{
				HttpSession session = request.getSession(false);
				if (session == null)
				{
					out.println("<br>Please login first<br>");
					RequestDispatcher rs = request.getRequestDispatcher("/_dashboard.html");
					rs.include(request, response);
					return;
				}
				
				String output;
				
				output = c.prepareCall("add_movie", title, year, director, bannerURL, trailerURL, firstName, lastName, dateOfBirth, photoURL, genreName);
				if(output.equals("Movie added to the database"))
				{
					out.println("<br>"+ output + "<br>");
					RequestDispatcher rs = request.getRequestDispatcher("/EmployeeConfirmation.jsp?success=true");
					rs.forward(request, response);
					
				}
				else
				{
					out.println("<br>"+ output + "<br>");
					RequestDispatcher rs = request.getRequestDispatcher("/NewMovie.jsp");
					rs.include(request, response);
					out.close();
					
				}
		   } catch (SQLException e) {
			   e.printStackTrace();
			   out.println("<br>Failed to execute query<br>");
				RequestDispatcher rs = request.getRequestDispatcher("/NewMovie.jsp");
				rs.include(request, response);
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
