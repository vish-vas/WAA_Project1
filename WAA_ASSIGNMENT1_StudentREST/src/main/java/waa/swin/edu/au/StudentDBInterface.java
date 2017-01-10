package waa.swin.edu.au;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Class provides interface to the student database it contains sql statements and database operations
 * for the required service operations
 * @author Vov
 *
 */
public class StudentDBInterface 
{
	public final String STUDENTDB_TABLE_NAME = "student_database";
	public final String STUDENTID_COLUMN = "student_id";
	public final String STUDENTNAME_COLUMN = "student_name";
	public final String STUDENTPIN_COLUMN = "student_pin";
	
	public final String SEPARATER = "~";
	Connection conn = null;
	
	
	public boolean addNewStudentToDB(String studentData)
	{
		String[] studentDet = splitData(studentData);
		try
		{
			conn = OracleDBConnect.getConnection();
			PreparedStatement pStmt = conn.prepareStatement("insert into " +STUDENTDB_TABLE_NAME+ " values(?,?,?)");
			pStmt.setString(1, studentDet[0]);
			pStmt.setString(2, studentDet[1]);
			pStmt.setString(3, studentDet[2]);
			
			pStmt.executeUpdate();
			return true;
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		finally
		{
			OracleDBConnect.closeConnection(conn);
		}
		
	}
	
	public String getStudentFromDB(String studentId)
	{
		try
		{
			conn = OracleDBConnect.getConnection();
			PreparedStatement pStmt = conn.prepareStatement("select * from "+STUDENTDB_TABLE_NAME+" where "+STUDENTID_COLUMN+" = ?");
			pStmt.setString(1, studentId);
			ResultSet rSet = pStmt.executeQuery();
			String tempStudent="";
			while(rSet.next())
			{
				
				tempStudent+=rSet.getString(STUDENTID_COLUMN)+SEPARATER;
				tempStudent+=rSet.getString(STUDENTNAME_COLUMN)+SEPARATER;
				tempStudent+=rSet.getString(STUDENTPIN_COLUMN);
				
			}
			
			return tempStudent;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		finally
		{
			OracleDBConnect.closeConnection(conn);
		}
	}
	
	
	
	public boolean updateStudentInDB(String studentData)
	{
		String[] studentDet = splitData(studentData);
		try
		{
			conn = OracleDBConnect.getConnection();
			PreparedStatement pStmt = conn.prepareStatement("update "+STUDENTDB_TABLE_NAME+" set "+STUDENTNAME_COLUMN+"=?, "
					+STUDENTPIN_COLUMN+"=? where "+STUDENTID_COLUMN+"=?");
			
			
			pStmt.setString(1, studentDet[1]);
			pStmt.setString(2, studentDet[2]);
			pStmt.setString(3, studentDet[0]);
			;
			pStmt.executeUpdate();
			return true;
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		finally
		{
			OracleDBConnect.closeConnection(conn);
		}
	}
	public static String[] splitData(String data)
    {
    	String[] parts = data.split("\\~");
    	return parts;
    }
	
	
}
