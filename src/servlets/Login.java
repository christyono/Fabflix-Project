package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;
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
		    
		String name = request.getParameter("Username");
		String password = request.getParameter("Password");
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
		// SET usePrepared flag == true to use preparedStatements instead of regular statements


		if (ds == null){
			System.out.println("In login.java: ds is null");
		}
		else{
			
			c.setDataSource(ds, ds2);
		}
		
		try {
			c.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String nameID = "", passwordID = "";
		try {
			//System.out.println("Testing");
			String searchQuery = c.makeSearchQuery("customers.id", "customers", "customers.email = '"+name+"'");
			if (usePrepared){
				System.out.println("Using preparedStatement");
				prepStatement = c.getConnection().prepareStatement(searchQuery);
				rs = prepStatement.executeQuery();
			}
			else{
				c.startQuery(searchQuery);
				rs = c.getResultSet();
			}
			while (rs.next())
			{
				nameID = rs.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Invalid Query");
		}
		
		try {
			String searchQuery2 = c.makeSearchQuery("customers.id", "customers", "customers.password = '"+password+"'");
			if (usePrepared){
				prepStatement = c.getConnection().prepareStatement(searchQuery2);
				rs = prepStatement.executeQuery();
			}
			else{
				c.startQuery(searchQuery2);
				rs = c.getResultSet();
			}
			while (rs.next())
			{
				passwordID = rs.getString(1);
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
			dispatcher.forward(request, response);
			
			
			//  call the main.html file
			
		}
		else
		{
			request.setAttribute("Incorrect", "Sorry, username or password is wrong");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/LoginPage.jsp");
			dispatcher.include(request, response);
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
