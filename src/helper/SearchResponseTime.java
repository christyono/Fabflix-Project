package helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class SearchResponseTime
 */
//@WebFilter("/SearchResponseTime")
public class SearchResponseTime implements Filter {

    /**
     * Default constructor. 
     */
    public SearchResponseTime() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		long startTime = System.nanoTime();
		// pass the request along the filter chain
		System.out.println("Before doFilter");
		chain.doFilter(request, response);
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
//		System.out.println("In SearchResponseTime: wrote to file");
		System.out.println("Servlet Execution Time: " + elapsedTime);
		try
		{
			String file = "SearchTimeLog.txt";
			PrintWriter write = new PrintWriter(new FileWriter(file, true));
		    write.println("Servlet Execution Time: " + elapsedTime);
		    write.flush();
		    write.close();
		    System.out.println("In SearchResponseTime: wrote to file");
		} 
		catch (IOException e) 
		{
			
			e.printStackTrace();
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("SearchResponseFilter has been placed into service" );
	}

}
