package au.edu.swin.waa;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import au.edu.swin.waa.StudentAuthenticationServiceSOAPStub.StudentAuthentication;
import au.edu.swin.waa.StudentAuthenticationServiceSOAPStub.StudentAuthenticationResponse;

public class LibraryClient 
{
	/**
	 * Provides ui for the system.
	 */
	public void runUI()
    {
        while(true)
        {
            switch(mainMenu())
            {
                case 1:
                	requestNewBook();
                    break;
                case 2:
                	borrowBook();
                    break;
                case 3:
                	viewAllBooks();
                    break;
                case 4:
                	viewStudentRecords();
                    break;
                case 5:
                    System.out.println("Closing System.");
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }
    
    /**
     * creates main menu for application
     * @return integer selection of menu.
     */
    public int mainMenu()
    {
        System.out.println("----Library System Test Harness.----\nMenu:\n1) Request New Book\n2) Borrow Book\n3) View All Books\n4) View Student Records\n5) Exit");
        return InputHelper.getValidInt(5, "Select Option: ");
    }
    
    
	/**
	 * the method navigates user thru the process of requesting a new from for library.
	 */
	public void requestNewBook()
	{
		InputHelper.print("Please follow the prompts to make a request for new book.\n----------------------------------");
		String id = InputHelper.getInputString("Please enter your details:\nStudent ID:");
		String pin = InputHelper.getNumbers(LocalConstants.PIN_CONSTRAINT , "Pin:");
		if(authenticateStudent(id,pin))
		{
			String bookIsbn="";
			while(bookIsbn.equals(""))
			{
				bookIsbn= InputHelper.getInputString("Please enter ISBN number of the Book:");
			}
			if(checkISBN(bookIsbn))
			{
				if(checkGoogleBookConstraints(bookIsbn))
				{
					String bookData = getGoogleBookData(bookIsbn);
					//BookServiceRESTClient bookServiceClient = new BookServiceRESTClient();
					try
					{
						InputHelper.print(BookServiceRESTClient.servicePOST(bookData));
						BookServiceRESTClient.bookingServicePOST(id+LocalConstants.SEPARATOR
								+bookIsbn+LocalConstants.SEPARATOR
								+LocalConstants.BOOK_REQUEST_TRANSACTION);
					}
					catch(Exception e)
					{
						InputHelper.print(e.getMessage());
					}
				}
				else
				{
					InputHelper.print("Sorry, Book either low ratings or is not available for sale.\nRequest Canclled.");
				}
			}
			else
			{
				InputHelper.print("No Book found with ISBN: "+bookIsbn);
			}
		}
		else
		{
			InputHelper.print("StudentId and Pin do not match.");
		}
		InputHelper.waitForKeypress();
	}
	
	/**
	 * the method navigates user thru the process of borrowing a book from library.
	 */
	public void borrowBook()
	{
		InputHelper.print("Please follow the prompts to borrow a book.\n-----------------------------------");
		String id = InputHelper.getInputString("Please enter your details:\nStudent ID:");
		String pin = InputHelper.getNumbers(LocalConstants.PIN_CONSTRAINT , "Pin:");
		if(authenticateStudent(id,pin))
		{
			String bookIsbn="";
			while(bookIsbn.equals(""))
			{
				bookIsbn= InputHelper.getInputString("Please enter ISBN number of the Book:");
			}
			try
			{
				String bookDat = BookServiceRESTClient.serviceGET(bookIsbn);
				//System.out.println(bookDat);
				if(!bookDat.equals("Book not found"))
				{
				String[] bookData = splitData(bookDat);
				if(bookData[6].equalsIgnoreCase(LocalConstants.BOOK_STATUS_AVAILABLE))
				{
					bookDat="";
					for(int i=0;i<bookData.length-1;i++)
					{
						bookDat += bookData[i]+LocalConstants.SEPARATOR;
					}
					bookDat = bookDat+LocalConstants.BOOK_STATUS_ONLOAN;
					String bookingData = id+LocalConstants.SEPARATOR
							+bookIsbn+LocalConstants.SEPARATOR
							+LocalConstants.BOOK_BORROW_TRANSACTION;
					BookServiceRESTClient.servicePUT(bookIsbn, bookDat);
					BookServiceRESTClient.bookingServicePOST(bookingData);
					InputHelper.print("Booking Successful");
				}
				else
				{
					InputHelper.print("Book currently on loan.");
				}
				}
				else
				{
					InputHelper.print("No Book found with ISBN: "+bookIsbn);
				}
			}
			catch(Exception e)
			{
				InputHelper.print(e.getMessage());
			}
		}
		else
		{
			InputHelper.print("StudentId and Pin do not match.");
		}
		InputHelper.waitForKeypress();
	}
	
	/**
	 * The method lists all the books saved in the database.
	 */
	public void viewAllBooks()
	{
		InputHelper.print("Following is the list of books in the Library.\nBook Titles:\n----------------------------");
		try
		{
			String books = BookServiceRESTClient.serviceGETAll();
			String[] bookList = splitData(books);
			for(String s:bookList)
			{
				InputHelper.print(s);
			}
		InputHelper.print("-----------------------------");
		}
		catch(Exception e)
		{
			InputHelper.print(e.getMessage());
		}
		InputHelper.waitForKeypress();
	}
	
	/**
	 * the method shows the student records by retriving data from the database.
	 */
	public void viewStudentRecords()
	{
		InputHelper.print("Please follow the prompts to view student records.\n------------------------------------");
		String id = InputHelper.getInputString("Please enter your details:\nStudent ID:");
		try
		{
			String student = StudentServiceRESTClient.serviceGET(id);
			//System.out.println(student);
			if(!student.equals("Student not found"))
			{
				String records = BookServiceRESTClient.bookingServiceGET(id);
				String[] studentData = splitData(student);
			if(!records.equals("No bookings found"))
			{
				InputHelper.print("Following are the library records for Student Name: "+studentData[1]+", Student ID: "+id+".");
				InputHelper.print("\nBookISBN\tTransaction\n--------------------------------");
				String[] recordList = splitData(records);
				for(String s: recordList)
				{
					InputHelper.print(s);
				}
			InputHelper.print("--------------------------------");
			}
			else
			{
				InputHelper.print("No Records present for "+studentData[1]+"Student ID: "+id);
			}
			}
			else
			{
				InputHelper.print("No student reqistered with Student ID: "+id);
			}
		}
		catch(Exception e)
		{
			InputHelper.print(e.getMessage());
		}
		InputHelper.waitForKeypress();
	}
	
	/**
	 * used to check if the isbn actually reffers to a book or not.
	 * @param bookIsbn isbn supplied by user
	 * @return true if isbn is real or false if not.
	 */
	public boolean checkISBN(String bookIsbn)
	{
		try
		{
			String bookData = GoogleServiceJSONClient.getGoogleBookData(bookIsbn);
			if(bookData.equals("Book Not Found."))
				return false;
			else
				return true;
		}
		catch(Exception e)
		{
			e.getMessage();
			return false;
		}
	}
	
	/**
	 * checks if the book has a minimum rating of 3.5 and if it is available for sale in australia
	 * @param bookIsbn isbn from user
	 * @return true if both constrains satisfied else false.
	 */
	public boolean checkGoogleBookConstraints(String bookIsbn)
	{
		try
		{
			String bookDat = GoogleServiceJSONClient.getGoogleBookData(bookIsbn);
			String[] bookData = splitData(bookDat);
			Double rating = Double.parseDouble(bookData[7]);
			String saleability = bookData[8];
			//System.out.println(rating+"  - "+saleability);
			if(rating>=3.5 && saleability.equalsIgnoreCase("FOR_SALE"))
				return true;
			else
				return false;
		}
		catch(Exception e)
		{
			e.getMessage();
			return false;
		}
	}
	
	/**
	 * JSON REST service used to get book data from google books api
	 * @param bookIsbn isbn supplied by user
	 * @return returns book data in form of string.
	 */
	public String getGoogleBookData(String bookIsbn)
	{
		try
		{
			String bookData = GoogleServiceJSONClient.getGoogleBookData(bookIsbn);
			return bookData;
		}
		catch(Exception e)
		{
			e.getMessage();
			return null;
		}
	}
	
	/**
	 * SOAP service used to authenticate user by checking credentials from database.
	 * @param id studentID
	 * @param pin student pin
	 * @return returns true if user authentiated else false
	 */
	public boolean authenticateStudent(String id, String pin)
	{
		try 
		{
			StudentAuthenticationServiceSOAPStub stub = new StudentAuthenticationServiceSOAPStub();
			StudentAuthentication studentAuthentication = new StudentAuthentication();
			studentAuthentication.setStudentID(id);
			studentAuthentication.setStudentPin(pin);
			StudentAuthenticationResponse studentAuthenticationResponse = stub.studentAuthentication(studentAuthentication);
			//System.out.println(studentAuthenticationResponse.getMessage());
			if(studentAuthenticationResponse.getMessage().equals("Authenticated"))
				return true;
			else
				return false;
		} 
		catch (AxisFault e) 
		{ 
			e.printStackTrace();
			return false;
		} 
		catch (RemoteException e) 
		{ 
			e.printStackTrace();
			return false;
		}
	}
	
	public String[] splitData(String bookingData)
    {
    	String[] parts = bookingData.split("\\~");
    	return parts;
    }
}
