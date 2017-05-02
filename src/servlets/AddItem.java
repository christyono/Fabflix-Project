package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import helper.*;

/**
 * Servlet implementation class AddItem
 */
@WebServlet("/AddItem")
public class AddItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		
		if(request.getParameter("quantity").equals("") || request.getParameter("quantity").contains("-")){
			out.println("enter valid quantity");
			RequestDispatcher rs = request.getRequestDispatcher("quantity.jsp");
			rs.include(request, response);
			return;
		}
		
		
		String Title = request.getParameter("title");
		int ID = Integer.parseInt(request.getParameter("id"));
		int Price = Integer.parseInt(request.getParameter("price"));
		
		try{
			Integer.parseInt(request.getParameter("quantity"));
		}
		catch(NumberFormatException c){
			out.println("enter valid quantity");
			RequestDispatcher rs = request.getRequestDispatcher("quantity.jsp");
			rs.include(request, response);
			return;
		}
		
		int Quantity = Integer.parseInt(request.getParameter("quantity"));
		
		CartItem item = new CartItem(ID, Title, Price, Quantity);
		
		HttpSession session = request.getSession();
		ShoppingCart Cart = (ShoppingCart)session.getAttribute("Cart");
		
		Cart.addItem(item);
		
		out.println("Quantity Modified \n");
		
		if(request.getParameter("special").equals("1")){
			RequestDispatcher rs = request.getRequestDispatcher("ShoppingCart.jsp");
			rs.include(request, response);
			return;
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
