package helper;
import java.sql.*;   
import java.util.ArrayList;

public class Connection {
	private String username = "root";
	private String password = "root";
	private ResultSet result;
	private Statement select;
	private int retID;
	private java.sql.Connection connection;
	private CallableStatement proc;
	
	public Connection()
	{
		result = null;
		connection = null;
		select = null;
		
	}
	public void connect() throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		try
        {
 		   connection = DriverManager.getConnection("jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false", username, password);
        }
 	   catch (Exception e)
 	   {
     	   System.out.println("Database or Password is wrong");
 	   }
	}
	public String makeSearchQuery(String select, String from, String where)
	{

		return "select " + select + " from " + from + " where " + where + ";";
	}
	public String makeUpdateQuery(String action, String from, String where)
	{
		// change this ïnsert into".. 
		return action + "from " + from + " where " + where;
	}
	public Statement createNewStatement()
	{
		try
		{
			select = connection.createStatement();
			return select;
			
		}
		catch (SQLException e)
		{
			System.out.println("failed to execute Query");
			return null;
			
		}
	}
	public void startQuery(String query) throws SQLException
	{
		try
		{
			select = connection.createStatement();
			result = select.executeQuery(query);
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("From Connection: failed to execute Query");
			
		}
		
	}
	public void executeUpdate(String query) throws SQLException
	{
		try
		{
			select = connection.createStatement();
			retID = select.executeUpdate(query);
 		    //System.out.println("Number of Rows Changed: " + retID);
		}
		catch (SQLException e)
		{
			System.out.println("From Connection: failed to execute Update");
		}
	}
	public int getRetID()
	{
		return retID;
	}
	
	public ResultSet getResultSet()
	{
		// returns most recent results
		return result;
	}
	public java.sql.Connection getConnection()
	{
		return connection;
	}
	public void closeStatement() throws SQLException
	{
		try
		{
			select.close();
		}
		catch (SQLException e)
		{
			System.out.println("failed to close statement");
		}
	}
	public void closeConnection() throws SQLException
	{
		try
		{
			connection.close();
		}
		catch (SQLException e)
		{
			System.out.println("Failed to close SQL connection");
		}
		
	}
	public String prepareCall(String storedProcedure, Object...args) throws SQLException
	{
		try{
			int count = 0;
			int count2 = 1; 
			for (Object o:args)
			{
				count ++;
			}
			String repeated = new String(new char[count]).replace("\0", ", ?");
			System.out.println("{ call " + storedProcedure + "(?" + repeated+ ") }");
			proc = connection.prepareCall("{ call " + storedProcedure + "(?" + repeated+ ") }");
		    for (Object o:args)
		    {
		    	if(o instanceof String)
		    	{
		    		proc.setString(count2,(String) o);
		    	}else if(o instanceof Integer)
		    	{
		    		proc.setInt(count2, (int) o);
		    	}
		    	count2++;
		    }
		    proc.registerOutParameter(count2, java.sql.Types.VARCHAR);
		    proc.execute();
		    return proc.getString(count2);
		}
		catch (SQLException e)
		{
			System.out.println("Failed to call stored procedure");
			throw e;
		}
		
		
	}
	
	
	
	
}
