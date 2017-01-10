package au.edu.swin.waa;

import java.util.Iterator;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

/**
 * This is Book Service client for the library it uses RESTful services to retrive book data student record data.
 *  @author Vov
 *
 */
public class BookServiceRESTClient 
{
	
	/**
	 * used to add new book to the database
	 * @param bookData
	 * @return
	 * @throws AxisFault
	 */
	public static String servicePOST(String bookData) throws AxisFault {
        String epr = LocalConstants.BookServiceEndpoint+"books";
        EndpointReference targetEPR = new EndpointReference(epr);

        Options options = new Options();
        options.setTo(targetEPR);
        options.setProperty(Constants.Configuration.MESSAGE_TYPE, "text/xml");
        options.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_POST);
        options.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);

        ServiceClient sender = new ServiceClient();
        sender.setOptions(options);

        OMElement response = sender.sendReceive(createRequestPayload(bookData));
        return processResponsePayload(response);
    }
	
	/**
	 * method used to send add booking request to the database.
	 * @param bookingData
	 * @return
	 * @throws AxisFault
	 */
	public static String bookingServicePOST(String bookingData) throws AxisFault {
        String epr = LocalConstants.BookServiceEndpoint+"bookings";
        EndpointReference targetEPR = new EndpointReference(epr);

        Options options = new Options();
        options.setTo(targetEPR);
        options.setProperty(Constants.Configuration.MESSAGE_TYPE, "text/xml");
        options.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_POST);
        options.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);

        ServiceClient sender = new ServiceClient();
        sender.setOptions(options);

        OMElement response = sender.sendReceive(createBookingRequestPayload(bookingData));
        return processResponsePayload(response);
    }

    /**
     * ised to update book in database
     * @param isbn
     * @param bookData
     * @return
     * @throws AxisFault
     */
    public static String servicePUT(String isbn, String bookData) throws AxisFault {
        String epr = LocalConstants.BookServiceEndpoint+"book/"+isbn;
        EndpointReference targetEPR = new EndpointReference(epr);

        Options options = new Options();
        options.setTo(targetEPR);
        options.setProperty(Constants.Configuration.MESSAGE_TYPE, "application/xml");
        options.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_PUT);
        options.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);

        ServiceClient sender = new ServiceClient();
        sender.setOptions(options);

        OMElement response = sender.sendReceive(createRequestPayload(bookData));
        return processResponsePayload(response);
    }

    /**
     * used to get book from database
     * @param isbn
     * @return
     * @throws AxisFault
     */
    public static String serviceGET(String isbn) throws AxisFault {
        String epr = LocalConstants.BookServiceEndpoint+"book/"+isbn;

        EndpointReference targetEPR = new EndpointReference(epr);

        Options options = new Options();
        options.setTo(targetEPR);
        options.setProperty(Constants.Configuration.MESSAGE_TYPE, "text/x-www-form-urlencoded");
        options.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_GET);
        options.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);

        ServiceClient sender = new ServiceClient();
        sender.setOptions(options);
        OMElement response = sender.sendReceive(null);
        return processResponsePayload(response);
    }
    
    /**
     * used to get student bookings from the database.
     * @param id
     * @return
     * @throws AxisFault
     */
    public static String bookingServiceGET(String id) throws AxisFault {
        String epr = LocalConstants.BookServiceEndpoint+"booking/"+id;

        EndpointReference targetEPR = new EndpointReference(epr);

        Options options = new Options();
        options.setTo(targetEPR);
        options.setProperty(Constants.Configuration.MESSAGE_TYPE, "text/x-www-form-urlencoded");
        options.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_GET);
        options.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);

        ServiceClient sender = new ServiceClient();
        sender.setOptions(options);
        OMElement response = sender.sendReceive(null);
        return processResponsePayload(response);
    }
    
    /**
     * used to get all the books from the book database
     * @return
     * @throws AxisFault
     */
    public static String serviceGETAll() throws AxisFault {
        String epr = LocalConstants.BookServiceEndpoint+"allBooks";

        EndpointReference targetEPR = new EndpointReference(epr);

        Options options = new Options();
        options.setTo(targetEPR);
        options.setProperty(Constants.Configuration.MESSAGE_TYPE, "text/x-www-form-urlencoded");
        options.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_GET);
        options.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);

        ServiceClient sender = new ServiceClient();
        sender.setOptions(options);
        OMElement response = sender.sendReceive(null);
        return processResponsePayload(response);
    }


    
    /**
     * creates xml data payload for add book request
     * @param bookData
     * @return
     */
    private static OMElement createRequestPayload(String bookData) {
    	String[] bookDet = splitData(bookData);
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace("http://au.edu.swin.waa", "ns");

        OMElement method = fac.createOMElement("addBook", omNs);
        OMElement bookID = fac.createOMElement("bookID", omNs);
        bookID.addChild(fac.createOMText(bookID, bookDet[0]));
        method.addChild(bookID);

        OMElement bookTitle = fac.createOMElement("bookTitle", omNs);
        bookTitle.addChild(fac.createOMText(bookTitle, bookDet[1]));
        method.addChild(bookTitle);
        
        OMElement bookAuthors = fac.createOMElement("bookAuthors", omNs);
        bookAuthors.addChild(fac.createOMText(bookAuthors, bookDet[2]));
        method.addChild(bookAuthors);
        
        OMElement bookIsbn = fac.createOMElement("bookIsbn", omNs);
        bookIsbn.addChild(fac.createOMText(bookIsbn, bookDet[3]));
        method.addChild(bookIsbn);
        
        
        OMElement bookPublisher = fac.createOMElement("bookPublisher", omNs);
        bookPublisher.addChild(fac.createOMText(bookPublisher, bookDet[4]));
        method.addChild(bookPublisher);
        
        OMElement bookPublishDate = fac.createOMElement("bookPublishDate", omNs);
        bookPublishDate.addChild(fac.createOMText(bookPublishDate, bookDet[5]));
        method.addChild(bookPublishDate);
        
        
        OMElement bookStatus = fac.createOMElement("bookStatus", omNs);
        bookStatus.addChild(fac.createOMText(bookStatus, bookDet[6]));
        method.addChild(bookStatus);
        
        /*OMElement studentID = fac.createOMElement("studentID", omNs);
        studentID.addChild(fac.createOMText(studentID, bookDet[7]));
        method.addChild(studentID);*/

        return method;
    }
    
    /**
     * creates xml data payload for add booking request
     * @param bookingData
     * @return
     */
    private static OMElement createBookingRequestPayload(String bookingData) {
    	String[] bookingDet = splitData(bookingData);
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace("http://au.edu.swin.waa", "ns");

        OMElement method = fac.createOMElement("addBooking", omNs);
        OMElement studentID = fac.createOMElement("studentID", omNs);
        studentID.addChild(fac.createOMText(studentID, bookingDet[0]));
        method.addChild(studentID);

        OMElement bookISBN = fac.createOMElement("bookISBN", omNs);
        bookISBN.addChild(fac.createOMText(bookISBN, bookingDet[1]));
        method.addChild(bookISBN);
        
        OMElement transactionType = fac.createOMElement("transactionType", omNs);
        transactionType.addChild(fac.createOMText(transactionType, bookingDet[2]));
        method.addChild(transactionType);
        
        return method;
    }
    
    public static String[] splitData(String bookData)
    {
    	String[] parts = bookData.split("\\~");
    	return parts;
    }
    
    private static String processResponsePayload(OMElement response) {
        Iterator iterator = response.getChildrenWithLocalName("return");
        OMElement returnElement = (OMElement) iterator.next();
        return returnElement.getText();
    }
}
