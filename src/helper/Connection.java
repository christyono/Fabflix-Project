package helper;
import java.sql.*;     
public class Connection {
	private String username = "root";
	private String password = "root";
	private java.sql.Connection connection;
	Connection()
	{
		connection = null;
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
	
}
