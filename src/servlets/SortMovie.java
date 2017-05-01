package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import helper.*;
import java.util.*;

/**
 * Servlet implementation class SortMovie
 */
//@WebServlet("/SortMovie")
public class SortMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SortMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		
		String sortSelection = request.getParameter("sortBy");
		String sortOrder = request.getParameter("order");
		
		ArrayList<Movie> movieList = (ArrayList<Movie>) session.getAttribute("movieList");
//		if (session.getAttribute("movieList") == null)
//		{
//			out.println("Error");
//			return;
//		}
		ArrayList<Movie> copyMovie = (ArrayList<Movie>) movieList.clone();

		
		
		
		
		if (session.getAttribute("username") == null)
		{
			out.println("<br> please login first <br>");
			RequestDispatcher rs = request.getRequestDispatcher("/index.html");
			rs.include(request, response);
		}
		if (session.getAttribute("movieList") != null && sortOrder != null && sortSelection != null)
		{
			if (sortSelection.equals("title"))
			{
				if (sortOrder.equals("ascending"))
					Collections.sort(copyMovie, Movie.AscendingTitleComparator);
				else
					Collections.sort(copyMovie, Movie.DescendingTitleComparator);
			}
			else
			{
				if (sortOrder.equals("ascending"))
					Collections.sort(copyMovie, Movie.AscendingYearComparator);
				else
					Collections.sort(copyMovie, Movie.DescendingYearComparator);
			}
			out.println("<br> attributes: " + sortSelection + " " + sortOrder + "<br>");
			request.setAttribute("searchParams", sortSelection + " and " + sortOrder);
			request.setAttribute("sortedList", copyMovie);
			RequestDispatcher rs = request.getRequestDispatcher("/DisplaySearch.jsp");
			rs.forward(request, response);
			
			
		}
		else{
			String error = "Please make sure to check the sort by and order check boxes before submitting";
			request.setAttribute("error", error);
			RequestDispatcher rs = request.getRequestDispatcher("/DisplaySearch.jsp");
			rs.forward(request, response);
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
