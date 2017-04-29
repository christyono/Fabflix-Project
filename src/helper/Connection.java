package helper;
import java.sql.*;     
public class Connection {
	private String username = "root";
	private String password = "root";
	private ResultSet result;
	private Statement select;
	private java.sql.Connection connection;

	
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
	public void startQuery(String query) throws SQLException
	{
		try
		{
			select = connection.createStatement();
			result = select.executeQuery(query);
		}
		catch (SQLException e)
		{
			System.out.println("failed to execute Query");
		}
		
	}
	public ResultSet getResultSet()
	{
		return result;
	}
	public java.sql.Connection getConnection()
	{
		return connection;
	}
	public void close() throws SQLException
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
	
	
	
	
}
