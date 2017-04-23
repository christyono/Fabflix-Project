package com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String name = request.getParameter("Username");
		String password = request.getParameter("Password");
		if (!name.isEmpty() && !password.isEmpty() && password.equals("yan") )
		{
			HttpSession session = request.getSession();
			session.setAttribute("username", name);
			RequestDispatcher rs = request.getRequestDispatcher("/main.jsp");
			rs.include(request, response);
			
			
			//  call the main.html file
			
		}
		else
		{
			out.println("<h>Sorry, username or password is wrong</h>");
			RequestDispatcher rs = request.getRequestDispatcher("/index.html");
			rs.include(request, response);
		}
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
