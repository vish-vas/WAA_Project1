package waa.swin.edu.au;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Class provides interface to the booking database it contains sql statements and database operations
 * for the required service operations
 * @author Vov
 *
 */
public class BookingDBInterface {
	
	public final String BOOKINGDB_TABLE_NAME = "booking_database";
	public final String STUDENTID_COLUMN = "student_id";
	public final String BOOKISBN_COLUMN = "book_isbn";
	public final String TRANSACTIONTYPE_COLUMN = "transaction_type";
	
	public final String SEPARATER = "~";
	Connection conn = null;
	
	
	/**
	 * used to add new booking entryt to database
	 * @param bookingData
	 * @return
	 */
	public boolean addNewBookingToDB(String bookingData)
	{
		String[] bookingDet = splitData(bookingData);
		try
		{
			conn = OracleDBConnect.getConnection();
			PreparedStatement pStmt = conn.prepareStatement("insert into " +BOOKINGDB_TABLE_NAME+ " values(?,?,?)");
			pStmt.setString(1, bookingDet[0]);
			pStmt.setString(2, bookingDet[1]);
			pStmt.setString(3, bookingDet[2]);
			
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
	
	/**
	 * used to get all bookings for a specific student.
	 * @param id
	 * @return
	 */
	public String getStudentBookingsFromDB(String id)
	{
		try
		{
			conn = OracleDBConnect.getConnection();
			PreparedStatement pStmt = conn.prepareStatement("select * from "+BOOKINGDB_TABLE_NAME+" where "+STUDENTID_COLUMN+" = ?");
			pStmt.setString(1, id);
			ResultSet rSet = pStmt.executeQuery();
			String tempBookings="";
			while(rSet.next())
			{
				
				tempBookings+=rSet.getString(BOOKISBN_COLUMN)+"\t";
				tempBookings+=rSet.getString(TRANSACTIONTYPE_COLUMN)+SEPARATER;
				
			}
			if(tempBookings.length()>0)
				return tempBookings.substring(0,tempBookings.length()-1);
			else
				return null;
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
	
	
	
	
	 public static String[] splitData(String data)
	    {
	    	String[] parts = data.split("\\~");
	    	return parts;
	    }
}


