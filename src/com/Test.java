package com;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;


/**
 * Servlet implementation class Test
 */

public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * 
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loginUser = "root";
        String loginPasswd = "root";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT (send HTML to the client based on setContentType)
        
        PrintWriter out = response.getWriter();
        out.println("<HTML><HEAD><TITLE>MovieDB: Testing</TITLE></HEAD>");
        out.println("<BODY><H1>MovieDB: Stars</H1>");
        
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			Statement statement = connection.createStatement();
			String query = "Select * from Stars;";
			ResultSet rs = statement.executeQuery(query);
			out.println("<TABLE border>");

            // Iterate through each row of rs
	      out.println("<tr>" +
			  "<td>" + "ID" + "</td>" +
			  "<td>" + "First Name" + "</td>" +
			  "<td>" + "Last Name" + "</td>" +
			  "</tr>");
            while (rs.next())
            {
                String m_ID = rs.getString("ID");
                String m_FN = rs.getString("first_name");
                String m_LN = rs.getString("last_name");
                out.println("<tr>" +
                            "<td>" + m_ID + "</td>" +
                            "<td>" + m_FN + "</td>" +
                            "<td>" + m_LN + "</td>" +
                            "</tr>");
            }
            out.println("</TABLE>");

            rs.close();
            statement.close();
            connection.close();
		}
		catch (SQLException e)
		{
			out.println("<p> SQL EXCEPTION </p>");
		}
		catch (java.lang.Exception e)
		{
			out.println("<p> JAVA LANG EXCEPTION </p>");
		}
		finally
		{
			out.close();
		}
	}

	/**
	 *
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
