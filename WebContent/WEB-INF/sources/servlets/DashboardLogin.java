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

import helper.Connection;
import helper.ShoppingCart;

/**
 * Servlet implementation class DashboardLogin
 */
//@WebServlet("/DashboardLogin")
public class DashboardLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");  
		
		String email = request.getParameter("Email");
		String password = request.getParameter("Password");
		Connection c = new Connection();
		try {
			c.connect();
		} catch (Exception e) {
			System.out.println("Database or Password is wrong");
		}
		String emailID = "", passwordID = "";
		try {
			c.startQuery(c.makeSearchQuery("password", "employees", "employees.email = '"+email+"'"));
			while (c.getResultSet().next())
			{
				passwordID = c.getResultSet().getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Invalid Query");
		}
		
		try {
			c.startQuery(c.makeSearchQuery("email", "employees", "employees.password = '"+password+"'"));
			while (c.getResultSet().next())
			{
				emailID = c.getResultSet().getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Invalid Query");
		}
		System.out.println("email:"+email+"password:"+password+"emailid:"+emailID+"passwordid:"+passwordID);
		
		if (email.equals(emailID) && password.equals(passwordID) && !emailID.equals("") && !passwordID.equals(""))
		{
			HttpSession session = request.getSession();
			
			//Do we need this?????
			session.setAttribute("email", email);
			RequestDispatcher rs = request.getRequestDispatcher("/Dashboard.jsp");
			rs.forward(request, response);
			
			
			//  call the main.html file
			
		}
		else
		{
			out.println("<br>Sorry, email or password is wrong<br>");
			
			RequestDispatcher rs = request.getRequestDispatcher("/_dashboard.html");
			rs.include(request, response);
		}
		out.close();
		try
		{
			c.closeConnection();
		}
		catch (SQLException e)
		{
			System.out.println("Failed to close connection");
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
