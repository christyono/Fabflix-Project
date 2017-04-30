package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helper.*;

/**
 * Servlet implementation class GetStar
 */
//@WebServlet("/GetStar")
public class GetStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStar() {
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
		String id = request.getParameter("param1");
		int starID = Integer.parseInt(id);
		HttpSession session = request.getSession(false);
		
		if (session == null)
		{
			out.println("<br>Please login first<br>");
			RequestDispatcher rs = request.getRequestDispatcher("/index.html");
			rs.include(request, response);
			return;
		}
		out.println("<br> STARID: " + starID + "<br>");
		ArrayList<Movie> movieList = (ArrayList<Movie>) session.getAttribute("movieList");
		Star desiredStar = null;
		for (int i = 0; i < movieList.size(); i++)
		{
			Movie movie = movieList.get(i);
			for (int j = 0; j < movie.getStarList().size(); j++)
			{
				Star star = movie.getStarList().get(j);
				if (star.getStarID() == starID)
				{
					desiredStar = star;
					break;
				}
			}
		}
		
		request.setAttribute("currentStar", desiredStar);
		RequestDispatcher rs = request.getRequestDispatcher("/DisplayStar.jsp");
		rs.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
