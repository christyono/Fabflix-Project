package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helper.*;
import java.util.*;

/**
 * Servlet implementation class CreditCheck
 */
//@WebServlet("/CreditCheck")
public class CreditCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreditCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public String findCustomerQuery(String ccNum)
    {
    	String query = String.format("select customers.id from customers where customers.cc_id = %s;", ccNum);
    	return query;
    }
    public String makeInsertTransaction(int customerID, int movieID, String saleDate)
    {
    	String query = String.format("insert into sales(customer_id, movie_id, sale_date) "
    			+ "values(%d, %d, '%s');", customerID, movieID, saleDate);
    	return query;
    }
    public String makeCreditQuery(String firstName, String lastName, String cardNum, String expirationDate)
    {
    	String query = String.format("select * from creditcards "
    			+ "where id = %s "
    			+ "and first_name = \"%s\" "
    			+ "and last_name = \"%s\" "
    			+ "and expiration = \"%s\";", cardNum, firstName, lastName, expirationDate);
    	
    	return query;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String[] urlParams = {"firstName", "lastName", "cardNum", "expDate", "expMonth", "expYear"};
		HashMap <String, String> searchParams = new HashMap<String, String>();
		
		// this function matches the parameter name with the request and adds into hashmap
		//out.println(request.getParameter("expYear") + " and "+ request.getParameter("expYear").length());
		for (int i = 0; i < urlParams.length; i++)
		{
			if (request.getParameter("expDate") == "")
			{
				searchParams.put("expDate", "11");
			}
			if (request.getParameter("expMonth") == "")
			{
				searchParams.put("expMonth", "11");
			}

			if (request.getParameter("cardNum") == "")
			{
				searchParams.put("cardNum", "0");
			}
			if (request.getParameter("firstName") == "")
			{
				searchParams.put("firstName", "a");
			}
			if (request.getParameter("lastName") == "")
			{
				searchParams.put("lastName", "b");
			}
			
			searchParams.put(urlParams[i], request.getParameter(urlParams[i]));
		
			if (request.getParameter("expYear").length() == 0)
			{
				//out.println("passed the test");
				searchParams.put("expYear", "1111");
			}
		
		}
			
	
		
		
		String expirationDate = searchParams.get("expYear") + "-" + searchParams.get("expMonth") + "-" + searchParams.get("expDate");
		
		String query = makeCreditQuery(searchParams.get("firstName"), searchParams.get("lastName"),searchParams.get("cardNum"), expirationDate);
		//out.println(query);
		Connection c = new Connection();
		try
		{
			c.connect();
		}
		catch (Exception e)
		{
			out.println("Invalid Username or Password");
		}
		try
		{
			c.startQuery(query);
			if (!c.getResultSet().isBeforeFirst())
			{
				out.println("<br> Please enter a valid data <br>");
				RequestDispatcher rs = request.getRequestDispatcher("/Checkout.jsp");
				rs.include(request, response);
				return;
			}
			// CHECK TO SEE IF USER ENTERED ALL INFO PROPERLY 
			
			String query1 = findCustomerQuery(searchParams.get("cardNum"));
			out.print(query1);
			c.startQuery(query1);
			if (!c.getResultSet().isBeforeFirst())
			{
				out.println("<br> No credit card information for customer with this creditcard number <br>");
				RequestDispatcher rs = request.getRequestDispatcher("/Checkout.jsp");
				rs.include(request, response);
			}
			else
			{
				int customerID = 0;
				while (c.getResultSet().next())
				{
					customerID = c.getResultSet().getInt(1);
				}
				
				HttpSession session = request.getSession();
				ShoppingCart Cart = (ShoppingCart)session.getAttribute("Cart");
				for (int i = 0; i < Cart.getLength(); i++)
				{
					if (Cart.getItem(i).getQuantity() >= 1)
					{
						for (int j = 0; j < Cart.getItem(i).getQuantity(); j++)
						{
							long millis = System.currentTimeMillis();
							java.sql.Date date = new java.sql.Date(millis);
							
							String query2 = makeInsertTransaction(customerID, Cart.getID(i), date.toString());
							out.println("<br>" + query2 + "<br>");
							c.executeUpdate(query2);
						}
					}
				}
				//request.setParameter("success", "true");
				RequestDispatcher rs = request.getRequestDispatcher("/Confirmation.jsp?success=true");
				rs.forward(request, response);
				
				
			}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace(response.getWriter());
			out.println("Failed to execute query");
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
