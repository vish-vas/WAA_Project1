package waa.swin.edu.au;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Class provides interface to the books database it contains sql statements and database operations
 * for the required service operations
 * @author Vov
 *
 */
public class BookDBInterface 
{
	public final String BOOKDB_TABLE_NAME = "book_database";
	public final String BOOKID_COLUMN = "book_id";
	public final String BOOKTITLE_COLUMN = "book_title";
	public final String BOOKAUTHOR_COLUMN = "book_authors";
	public final String BOOKISBN_COLUMN = "book_isbn";
	public final String BOOKPUBLISHER_COLUMN = "book_publisher";
	public final String BOOKPUBLISHDATE_COLUMN = "book_publishdate";
	public final String BOOKSTATUS_COLUMN = "book_status";
	public final String SEPARATER = "~";
	Connection conn = null;
	
	
	/**
	 * extracts data from input string and saves it in the book database.
	 * @param bookData
	 * @return
	 */
	public boolean addNewBookToDB(String bookData)
	{
		String[] bookDet = splitData(bookData);
		try
		{
			conn = OracleDBConnect.getConnection();
			PreparedStatement pStmt = conn.prepareStatement("insert into " +BOOKDB_TABLE_NAME+ " values(?,?,?,?,?,?,?)");
			pStmt.setString(1, bookDet[0]);
			pStmt.setString(2, bookDet[1]);
			pStmt.setString(3, bookDet[2]);
			pStmt.setString(4, bookDet[3]);
			pStmt.setString(5, bookDet[4]);
			pStmt.setString(6, bookDet[5]);
			pStmt.setString(7, bookDet[6]);
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
	 * reads book records from database.
	 * @param bookIsbn
	 * @return
	 */
	public String getBookFromDB(String bookIsbn)
	{
		try
		{
			conn = OracleDBConnect.getConnection();
			PreparedStatement pStmt = conn.prepareStatement("select * from "+BOOKDB_TABLE_NAME+" where "+BOOKISBN_COLUMN+" = ?");
			pStmt.setString(1, bookIsbn);
			ResultSet rSet = pStmt.executeQuery();
			String tempBook="";
			while(rSet.next())
			{
				
				tempBook+=rSet.getString(BOOKID_COLUMN)+SEPARATER;
				tempBook+=rSet.getString(BOOKTITLE_COLUMN)+SEPARATER;
				tempBook+=rSet.getString(BOOKAUTHOR_COLUMN)+SEPARATER;
				tempBook+=rSet.getString(BOOKISBN_COLUMN)+SEPARATER;
				tempBook+=rSet.getString(BOOKPUBLISHER_COLUMN)+SEPARATER;
				tempBook+=rSet.getString(BOOKPUBLISHDATE_COLUMN)+SEPARATER;
				tempBook+=rSet.getString(BOOKSTATUS_COLUMN);
			}
			
			return tempBook;
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
	
	/**
	 * used to get all book records from the database and send just the book title back
	 * @return
	 */
	public String getAllBooksFromDB()
	{
		try
		{
			conn = OracleDBConnect.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rSet = stmt.executeQuery("select * from "+BOOKDB_TABLE_NAME);
			String tempBooks="";
			while(rSet.next())
			{
				
				tempBooks+=rSet.getString(BOOKTITLE_COLUMN)+SEPARATER;
			}
			
			return tempBooks.substring(0,tempBooks.length()-1);
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
	
	/**used to update book in database
	 * @param bookData
	 * @return
	 */
	public boolean updateBookInDB(String bookData)
	{
		String[] bookDet = splitData(bookData);
		try
		{
			conn = OracleDBConnect.getConnection();
			PreparedStatement pStmt = conn.prepareStatement("update "+BOOKDB_TABLE_NAME+" set "+BOOKTITLE_COLUMN+"=?, "
					+BOOKAUTHOR_COLUMN+"=?, "+BOOKID_COLUMN+"=?, "+BOOKPUBLISHER_COLUMN+"=?, "+BOOKPUBLISHDATE_COLUMN+"=?, "
					+BOOKSTATUS_COLUMN+"=? where "+BOOKISBN_COLUMN+"=?");
			
			
			pStmt.setString(1, bookDet[1]);
			pStmt.setString(2, bookDet[2]);
			pStmt.setString(3, bookDet[0]);
			pStmt.setString(4, bookDet[4]);
			pStmt.setString(5, bookDet[5]);
			pStmt.setString(6, bookDet[6]);
			pStmt.setString(7, bookDet[3]);
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
	 * used to delete book from database
	 * @param isbn
	 * @return
	 */
	public boolean deleteBookFromDB(String isbn)
	{
		try
        {
            conn = OracleDBConnect.getConnection();
            PreparedStatement pStmt = conn.prepareStatement("delete from " +BOOKDB_TABLE_NAME+ 
            " where " +BOOKISBN_COLUMN+ "= ?");
            pStmt.setString(1, isbn);
            pStmt.executeUpdate();
            return true;
        }
        catch(Exception e)
        {
            System.out.println("Error msg: "+e.getMessage());
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
