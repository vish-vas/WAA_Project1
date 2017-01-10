package au.edu.swin.waa;

/**
 * used to store final constant values for easy access.
 * @author Vov
 *
 */
public class LocalConstants 
{
	public static final String BookServiceEndpoint = "http://localhost:9763/services/BookServiceREST.BookServiceRESTHttpEndpoint/";
	
	public static final String StudentServiceEndpoint = "http://localhost:9763/services/StudentServiceREST.StudentServiceRESTHttpEndpoint/";

	public static final String GoogleServiceEndpoint = "http://localhost:9763/services/GoogleBookServiceJSON";
	
	
    
   public static final String BLANK_SPACE = " ";
    
    public static final int[] PIN_CONSTRAINT = {4,4};
    public static final String SEPARATOR = "~";
    public static final String BOOK_REQUEST_TRANSACTION = "Book Request";
    public static final String BOOK_BORROW_TRANSACTION = "Borrow Request";
    public static final String BOOK_STATUS_AVAILABLE = "Available";
    public static final String BOOK_STATUS_ONLOAN = "On Loan";
    
    
    
}
