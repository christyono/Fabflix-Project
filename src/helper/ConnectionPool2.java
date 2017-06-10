package helper;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.sql.DataSource;
/**
 * Application Lifecycle Listener implementation class ConnectionPool2
 *
 */
//@WebListener
public class ConnectionPool2 implements ServletContextListener, ServletContextAttributeListener {
    /**
     * Default constructor. 
     */
	private boolean testScaledVersion = false;
	private boolean usePreparedStatement = false;
    public ConnectionPool2() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("Connection Pool has been closed");
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent arg0)  { 
         // TODO Auto-generated method stub
    	
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
         // TODO Auto-generated method stub

    	try
		{
			Context initCtx = new InitialContext();			
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = null;
			DataSource ds2 = null;
    		if (testScaledVersion != true)
    		{
    			// create resource from localhost
    			
				ds = (DataSource) envCtx.lookup("jdbc/TestDB");
				event.getServletContext().setAttribute
			      ("DBCPool", ds);
				System.out.println("Datasource was created");
				if (ds == null)
				{
					System.out.println("Datasource was null");
				}
    		}
    		else
    		{
    			// create both master and slave resources
    			
				ds = (DataSource) envCtx.lookup("jdbc/slaveDB");
				ds2 = (DataSource) envCtx.lookup("jdbc/masterDB");
				event.getServletContext().setAttribute
			      ("slaveDB", ds);
				event.getServletContext().setAttribute("masterDB", ds2);
				System.out.println("Datasource for master and slave were created");
				if (ds == null || ds2 == null)
				{
					System.out.println("One of the master or slave datasources were null");
				}
    		}
    		
    		// set the usepreparedStatement to be true or false depending on whether you want to use it
    		event.getServletContext().setAttribute("usePrepared", usePreparedStatement);
    		event.getServletContext().setAttribute("testScaledVersion", testScaledVersion);
    	}
		catch (NamingException e)
		{
			e.printStackTrace();
			System.out.println("Failed to find context");
		}
    }
	
}
