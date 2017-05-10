package servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation class GetMovie
 */
//@WebServlet("/GetMovie")
public class GetMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// What this servlet does:
		// 1) given movieID, find the desiredmovie
		// 2) given movieID and starID, find the given movie where the star is featured
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		String id1 = request.getParameter("param1");
		String id2 = request.getParameter("param2");
		
		int movieID = Integer.parseInt(id1);
		int starID;
		
		HttpSession session = request.getSession(false);
		
		if (session == null)
		{
			out.println("<br>Please login first<br>");
			RequestDispatcher rs = request.getRequestDispatcher("/index.html");
			rs.include(request, response);
			return;
		}
		ArrayList<Movie> movieList = (ArrayList<Movie>)session.getAttribute("movieList");
		Movie desiredMovie = null;
		if (id2 == null)
		{
			for (int i = 0; i < movieList.size(); i++)
			{
				Movie movie = movieList.get(i);
				if (movie.getID() == movieID)
				{
					desiredMovie = movieList.get(i);
					break;
				}
			}
		}
		
		else
		{
			starID = Integer.parseInt(id2);
			for (int i = 0; i < movieList.size(); i++)
			{
				Movie movie = movieList.get(i);
				// make the second loop to search through the list of movies where the star is featured
				for (int j = 0; j < movie.getStarList().size(); j++)
				{
					Star star = movieList.get(i).getStarList().get(j);
					// given the star, find its starredMovieList
					if (star.getStarID() == starID)
					{
						for (int k = 0; j < star.getStarredMovieList().size();k++)
						{
							// given the starredmovieList, find the correct movie
							Movie m = star.getStarredMovieList().get(k);
							if (movieID == m.getID())
							{
								desiredMovie = m;
								break;
							}
						}
					}
				}
			}
		}
		
		
		request.setAttribute("currentMovie", desiredMovie);
		out.println("desiredMovie Title: " + desiredMovie.getTitle());
		RequestDispatcher rs = request.getRequestDispatcher("/DisplayMovie.jsp");
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
