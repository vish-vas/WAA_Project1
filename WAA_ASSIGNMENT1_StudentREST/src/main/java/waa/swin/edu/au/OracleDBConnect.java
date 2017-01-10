package waa.swin.edu.au;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * this class helps to get a connection to the oracle database
 * @author Vov
 *
 */
public class OracleDBConnect 
{
	public static Connection getConnection()
	{
		try
		{
			Class.forName(Constants.DB_CLASS);
			Connection conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
			return conn;
		}
		catch(ClassNotFoundException ex)
        {
            System.out.println("Could not find the DB connection class. Error msg: "+ex.toString());
            return null;
        }
        catch(SQLException ex)
        {
            System.out.println("Encountered a SQL Exception. Error msg: "+ex.toString());
            return null;
        }
		
	}
	
	public static boolean closeConnection(Connection conn)
	{
		try
        {
            if(conn!=null)
                conn.close();
            return true;
        }
        catch(Exception e)
        {
            System.out.println("Error encountered while closing the connection. Error msg: "+e.toString());
            return false;
        }
	}
}
