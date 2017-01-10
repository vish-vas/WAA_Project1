package waa.swin.edu.au;

public class BookServiceREST
{
	String separator ="~";
	
	/**
	 * add book mthod receives request from rest client and calls the database method to add the book to database.
	 * @param bookID
	 * @param bookTitle
	 * @param bookAuthors
	 * @param bookIsbn
	 * @param bookPublisher
	 * @param bookPublishDate
	 * @param bookStatus
	 * @return
	 * @throws Exception
	 */
	public String addBook(String bookID, String bookTitle, String bookAuthors, String bookIsbn, String bookPublisher, String bookPublishDate, String bookStatus/*, String studentID*/) throws Exception
	{
		String bookData = bookID+separator+bookTitle+separator+bookAuthors+separator+bookIsbn+separator+bookPublisher+separator+bookPublishDate+separator+bookStatus/*+separator+studentID*/;
		//String bookingData = studentID+separator+bookIsbn+separator+"Book Request";
		BookDBInterface db = new BookDBInterface();
		//BookingDBInterface dbb = new BookingDBInterface();
		if(db.addNewBookToDB(bookData)/*&&dbb.addNewBookingToDB(bookingData)*/)
			return "Book added";
		else
			return "Book not added";
	}
	
	/** used to get book details for a specific book form database and send back to client
	 * @param bookISBN
	 * @return
	 * @throws Exception
	 */
	public String getBookDetails(String bookISBN) throws Exception
	{
		BookDBInterface db = new BookDBInterface();
		String resp = db.getBookFromDB(bookISBN);
		if(resp!=null && !resp.equals(""))
			return resp;
		else
			return "Book not found";
	}
	
	/**
	 * used to update book details in the database.
	 * @param bookID
	 * @param bookTitle
	 * @param bookAuthors
	 * @param bookIsbn
	 * @param bookPublisher
	 * @param bookPublishDate
	 * @param bookStatus
	 * @param studentID
	 * @return
	 * @throws Exception
	 */
	public String updateBook(String bookID, String bookTitle, String bookAuthors, String bookIsbn, String bookPublisher, String bookPublishDate, String bookStatus, String studentID) throws Exception
	{
		
		String bookData = bookID+separator+bookTitle+separator+bookAuthors+separator+bookIsbn+separator+bookPublisher+separator+bookPublishDate+separator+bookStatus+separator+studentID;
		//String bookingData = studentID+separator+bookIsbn+separator+"Book Borrowed";
		BookDBInterface db = new BookDBInterface();
		if(db.updateBookInDB(bookData))
			return "Book updated";
		else
			return "Book not updated";
	}
	
	/** used to respond to the client with information of all books
	 * @return
	 * @throws Exception
	 */
	public String getAllBooks() throws Exception
	{
		BookDBInterface db = new BookDBInterface();
		String books = db.getAllBooksFromDB();
		if(books!=null)
			return books;
		else
			return "No books found";
	}
	
	/**
	 * used to retrive records from booking table for a single student
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getStudentRecords(String id) throws Exception
	{
		BookingDBInterface db = new BookingDBInterface();
		String studentRecords = db.getStudentBookingsFromDB(id);
		if(studentRecords!=null)
			return studentRecords;
		else
			return "No bookings found";
	}
	
	/**used to add a new bookuing in case of new book request or book borrowing event.
	 * @param studentID
	 * @param bookISBN
	 * @param transactionType
	 * @return
	 * @throws Exception
	 */
	public String addBooking(String studentID, String bookISBN, String transactionType) throws Exception
	{
		String bookingData = studentID+separator+bookISBN+separator+transactionType;
		BookingDBInterface db = new BookingDBInterface();
		if(db.addNewBookingToDB(bookingData))
			return "Booking added";
		else
			return "Booking not added";
	}
}