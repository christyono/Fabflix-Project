package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helper.*;

/**
 * Servlet implementation class Login
 */

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");  
		// VERIFY reCAPTCHA 
		
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
		// Verify CAPTCHA.
		
		boolean valid = VerifyUtils.verify(gRecaptchaResponse);
		if (!valid) {
		    //errorString = "Captcha invalid!";
		    out.println("<HTML>" +
				"<HEAD><TITLE>" +
				"MovieDB: Error" +
				"</TITLE></HEAD>\n<BODY>" +
				"<P>Recaptcha WRONG!!!! </P></BODY></HTML>");
		    return;
		}
		    
		String name = request.getParameter("Username");
		String password = request.getParameter("Password");
		Connection c = new Connection();
		try {
			c.connect();
		} catch (Exception e) {
			System.out.println("Database or Password is wrong");
		}
		String nameID = "", passwordID = "";
		try {
			c.startQuery(c.makeSearchQuery("customers.id", "customers", "customers.email = '"+name+"'"));
			while (c.getResultSet().next())
			{
				nameID = c.getResultSet().getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Invalid Query");
		}
		
		try {
			c.startQuery(c.makeSearchQuery("customers.id", "customers", "customers.password = '"+password+"'"));
			while (c.getResultSet().next())
			{
				passwordID = c.getResultSet().getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Invalid Query");
		}
		if (nameID.equals(passwordID) && !nameID.equals("") && !passwordID.equals(""))
		{
			HttpSession session = request.getSession();
			session.setAttribute("username", name);
			ShoppingCart Cart = new ShoppingCart();
			session.setAttribute("Cart", Cart);
			RequestDispatcher rs = request.getRequestDispatcher("/main.jsp");
			rs.forward(request, response);
			
			
			//  call the main.html file
			
		}
		else
		{
			out.println("<br>Sorry, username or password is wrong<br>");
			
			RequestDispatcher rs = request.getRequestDispatcher("/index.html");
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
