package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

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
		
		// ESTABLISHES CONNECTION WITH PROPER DATASOURCE
		
		Connection c = new Connection();
		boolean testScaled = (boolean) getServletContext().getAttribute("testScaledVersion");
		boolean isPrepared = (boolean) getServletContext().getAttribute("usePrepared");
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

		PreparedStatement pStatement = null;
		ResultSet rs = null;
		try {
			c.connect();
		} catch (Exception e) {
			System.out.println("Database or Password is wrong");
		}
		String emailID = "", passwordID = "";
		try {
			if (isPrepared){
				pStatement = c.getConnection().prepareStatement(c.makeSearchQuery("password", "employees", "employees.email = '"+email+"'"));
				rs = pStatement.executeQuery();
			}
			else{
				c.startQuery(c.makeSearchQuery("password", "employees", "employees.email = '"+email+"'"));
				rs = c.getResultSet();
			}
			while (rs.next())
			{
				passwordID = rs.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Invalid Query");
		}
		
		try {
			if (isPrepared){
				pStatement = c.getConnection().prepareStatement(c.makeSearchQuery("email", "employees", "employees.password = '"+password+"'"));
				rs = pStatement.executeQuery();
			}
			else{
				c.startQuery(c.makeSearchQuery("email", "employees", "employees.password = '"+password+"'"));
				rs = c.getResultSet();
			}
			while (rs.next())
			{
				emailID = rs.getString(1);
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
			RequestDispatcher rd = request.getRequestDispatcher("/Dashboard.jsp");
			rd.forward(request, response);
			
			
			//  call the main.html file
			
		}
		else
		{
			out.println("<br>Sorry, email or password is wrong<br>");
			
			RequestDispatcher rd = request.getRequestDispatcher("/_dashboard.html");
			rd.include(request, response);
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
