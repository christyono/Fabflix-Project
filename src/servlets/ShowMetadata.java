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

import com.mysql.jdbc.ResultSet;

import helper.Connection;

/**
 * Servlet implementation class ShowMetadata
 */
@WebServlet("/ShowMetadata")
public class ShowMetadata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowMetadata() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
		Connection c = new Connection();
		String output = "";
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
				RequestDispatcher rs = request.getRequestDispatcher("/index.html");
				rs.include(request, response);
				return;
			}
            c.startQuery("select table_name from information_schema.tables where table_schema = 'moviedb'");
            ResultSet result = (ResultSet) c.getResultSet();
             //loops until all table names are printed
            while(result.next())
            {

            	output += "<br>";
            	output += ("Table name: " + result.getString(1) + "<br>");
          	   
          	   //print table data
            	c.startQuery("describe " + result.getString(1));
            	output += "Fields :<br>";
            	ResultSet tableData = (ResultSet) c.getResultSet();
            	while(tableData.next())
            	{
            		output += (tableData.getString(1) + " [" + tableData.getString(2) + "] <br>");
            	}
           }
            session.setAttribute("output", output);
			RequestDispatcher rs = request.getRequestDispatcher("/DisplayMetadata.jsp");
			rs.forward(request, response);
			c.closeConnection();
  	   }
  	   catch(SQLException e)
  	   {
  		   out.print("<br>Failed to print metadata<br>");
  		   
  	   }
		finally
		{
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
