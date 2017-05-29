package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import helper.Connection;


import java.io.PrintWriter;
import java.sql.SQLException;
/**
 * Servlet implementation class AndroidLogin
 */
//@WebServlet("/AndroidLogin")
public class AndroidLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidLogin() {
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
		
//		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
//		System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
//		// Verify CAPTCHA.
//		
//		boolean valid = VerifyUtils.verify(gRecaptchaResponse);
//		if (!valid) {
//		    //errorString = "Captcha invalid!";
//		    out.println("<HTML>" +
//				"<HEAD><TITLE>" +
//				"MovieDB: Error" +
//				"</TITLE></HEAD>\n<BODY>" +
//				"<P>Recaptcha WRONG!!!! </P></BODY></HTML>");
//		    return;
//		}
		    
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		
		Connection c = new Connection();
		try {
			c.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String nameID = "", passwordID = "";
		try {
			System.out.println("Testing");
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
			Gson gsonObject= new Gson();
			String json = gsonObject.toJson("success"); 
			response.getWriter().println(json);
			response.setContentType("application/json"); 
			response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(json);

			
		}
		else
		{
			Gson gsonObject= new Gson();
			String json = gsonObject.toJson("incorrect"); 
			response.getWriter().println(json);
			response.setContentType("application/json"); 
			response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(json);
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
