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
 * Servlet implementation class Pagination
 */
//@WebServlet("/Pagination")
public class Pagination extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pagination() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public int calculateOffset(int pageNum, int limit)
    {
    	int offSet = limit * (pageNum - 1);
    	return offSet;
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		// Calculate Offset
		int limit;
		int pageNum;
		int offSet;
		if (request.getParameter("pageNum") == null && request.getParameter("limit") != null)
		{
			// if the user clicks the radio button to change the limit
			// just send them back to page 1
			
			pageNum = 1;
			limit = Integer.parseInt(request.getParameter("limit"));
			offSet = calculateOffset(pageNum, limit);
			
			HttpSession session = request.getSession();
			session.setAttribute("offset", offSet);
			session.setAttribute("limit", limit);
			session.setAttribute("pageNum", pageNum);
			RequestDispatcher rs = request.getRequestDispatcher("/DisplaySearch.jsp");
			rs.forward(request, response);
		}
		if (request.getParameter("limit") != null && request.getParameter("pageNum") != null)
		{
			limit = Integer.parseInt(request.getParameter("limit"));
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
			//out.println("<br> PageNum: " + pageNum + "<br>");
			offSet = calculateOffset(pageNum, limit);
			
			HttpSession session = request.getSession();
			session.setAttribute("offset", offSet);
			session.setAttribute("limit", limit);
			session.setAttribute("pageNum", pageNum);
			
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
