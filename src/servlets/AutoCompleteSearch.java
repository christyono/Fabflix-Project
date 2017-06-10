package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import helper.*;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.google.gson.*;
/**
 * Servlet implementation class AutoCompleteSearch
 */
//@WebServlet("/AutoCompleteSearch")
public class AutoCompleteSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoCompleteSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String text = request.getParameter("query");
//
//	    response.setContentType("text/html");  // Set content type of the response so that jQuery knows what it can expect.
//	    response.getWriter().write(text);       // Write response body.
//		ArrayList<String> list = new ArrayList<String>();
//		list.add("item1");
//		list.add("item2");
//		list.add("item3");
//		System.out.println("connected");
//		String json = new Gson().toJson(list);
//		response.setContentType("application/json");
//	    response.getWriter().write(json);
		Connection c = new Connection();
		boolean testScaled = (boolean) getServletContext().getAttribute("testScaledVersion");
		boolean usePrepared = (boolean) getServletContext().getAttribute("usePreparedStatement");
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
		try{
			c.connect();
			ArrayList<String> titles = new ArrayList<String>();
			System.out.println(request.getParameter("query"));
			String query = request.getParameter("query");
			response.setHeader("cache-control", "no-cache");
			if (!query.equals("")){
				String tokens[] = query.split(" ");
				String keywords = "";
				for (int i = 0; i < tokens.length; i++){
					
					keywords += "+" + tokens[i] + "* ";
					
				}
				System.out.println("what we're inserting: " + keywords);
				String sqlQuery = String.format("SELECT title FROM movies WHERE MATCH (title) AGAINST ('%s' IN BOOLEAN MODE);", keywords);
				ResultSet rs = null;
				PreparedStatement prepStatement = null;
				if (usePrepared){
					prepStatement = c.getConnection().prepareStatement(sqlQuery);
					rs = prepStatement.executeQuery(sqlQuery);
					
				}
				else{
					c.startQuery(sqlQuery);
					rs = c.getResultSet();
				}
			
				
				
				while (rs.next()){
					System.out.println("title: " + rs.getString(1));
					titles.add(rs.getString(1));
					
				}
				
				if (titles.size() != 0){
					Gson gsonObject= new Gson();
					String json = gsonObject.toJson(titles);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
				    response.getWriter().write(json);
					
				}
			}
			else{
				System.out.println("No search query input");
			}
			
			
		}
		catch (Exception e){
			e.printStackTrace();
			// TODO Auto-generated catch block
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
