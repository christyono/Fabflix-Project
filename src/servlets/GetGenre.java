package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;
import java.sql.SQLException;

import java.util.*;

import helper.*;

/**
 * Servlet implementation class GetGenre
 */
//@WebServlet("/GetGenre")
public class GetGenre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetGenre() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public String makeGenreQuery()
    {
    	return "select genres.name from genres";
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		Connection c = new Connection();
		PrintWriter out = response.getWriter();
		ArrayList<String> genreNameList = new ArrayList<String>();
		try
		{
			c.connect();
		}
		catch (Exception e)
		{
			out.println("Database or Password is wrong");
		}
		try{
			String query = makeGenreQuery();
			
			c.startQuery(query);
			while (c.getResultSet().next()){
				String genreName = c.getResultSet().getString(1);
				genreNameList.add(genreName);
			}
			
			HttpSession s = request.getSession();
			s.setAttribute("genreNameList", genreNameList);
			RequestDispatcher rs = request.getRequestDispatcher("/Browse.jsp");
			rs.forward(request, response);
		}
		catch (SQLException e)
		{
			out.println("Failed to execute Query");
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
