package au.edu.swin.waa;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMDataSource;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMSourcedElement;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.axiom.om.impl.llom.OMSourcedElementImpl;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.json.AbstractJSONDataSource;
import org.apache.axis2.json.JSONDataSource;
import org.codehaus.jettison.badgerfish.BadgerFishXMLInputFactory;
import org.codehaus.jettison.json.JSONTokener;
import org.codehaus.jettison.mapped.MappedXMLInputFactory;

/**JSON restful client uses google books api to retrieve book data with reference to isbn.
 * @author Vov
 *
 */
public class GoogleBooksHelper 
{

   /**used to get various required feilds of data for a book. used for validation, and information requirement wilr adding a new book
 * @param isbn
 * @return
 * @throws AxisFault
 */
public static String getBook (String isbn) throws AxisFault {
        String epr = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn+"&key=AIzaSyAOX3v6Jg8iZwUbdpR_BDcaHoI-UD_yF2U";

        EndpointReference targetEPR = new EndpointReference(epr);

        Options options = new Options();
        options.setTo(targetEPR);
        options.setProperty(Constants.Configuration.MESSAGE_TYPE, "application/json");
        options.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_GET);
        options.setAction(null); // important

        File configFile = new File("axis2.xml");
        ConfigurationContext clientConfigurationContext;
        clientConfigurationContext = ConfigurationContextFactory
                .createConfigurationContextFromFileSystem(null, configFile.getAbsolutePath());

        ServiceClient sender = new ServiceClient(clientConfigurationContext, null);
        sender.setOptions(options);

        OMElement response = sender.sendReceive(null); // no payload

        try{
            return processResponsePayload(response);
            }
            catch(Exception e)
            {
            	return "Network error";
            }
    }

    private static String processResponsePayload(OMElement response) {
    	String bookInfo="";
    	String separator = "~";
        if (response instanceof OMSourcedElement) {
            OMDataSource omDataSource = ((OMSourcedElementImpl) response).getDataSource();
            if (omDataSource instanceof AbstractJSONDataSource) {
                String jsonString = ((AbstractJSONDataSource) omDataSource).getCompleteJOSNString();
                try {
                    XMLStreamReader reader = getReader("{ root : " + jsonString + "}", omDataSource);
                    OMElement element =
                            OMXMLBuilderFactory.createStAXOMBuilder(reader).getDocumentElement();
                    //System.out.println(element);
                    if(element.getFirstChildWithName(new QName("totalItems")).getText().equals("0"))
                    {
                    	return "Book Not Found.";
                    }
                    else
                    {
                    	
                    // normal XML navigation of "element"  e.g. element.getFirstChildWithName(new QName("items"));
                    OMElement items = element.getFirstChildWithName(new QName("items"));
                    OMElement volumeInfo = items.getFirstChildWithName(new QName("volumeInfo"));
                    OMElement industryIdentifiers = volumeInfo.getFirstChildWithName(new QName("industryIdentifiers"));
                    OMElement saleInfo = items.getFirstChildWithName(new QName("saleInfo"));
                    bookInfo += items.getFirstChildWithName(new QName("id")).getText()+separator;
                    //System.out.println("Id : " + items.getFirstChildWithName(new QName("id")).getText());
                    bookInfo += volumeInfo.getFirstChildWithName(new QName("title")).getText()+separator;
                    //System.out.println("Title : " + volumeInfo.getFirstChildWithName(new QName("title")).getText());
                    Iterator authors = volumeInfo.getChildrenWithLocalName("authors");
                    String authorsString = "";
                    while (authors.hasNext()) {
                        OMElement omElement = (OMElement) authors.next();
                        authorsString += omElement.getText() + ", ";
                    }
                    
                    bookInfo += authorsString.substring(0,authorsString.length()-2)+separator;
                    //System.out.println("Authors: " + authorsString);
                    
                    bookInfo += industryIdentifiers.getFirstChildWithName(new QName("identifier")).getText()+separator;
                    //System.out.println("ISBN: "+industryIdentifiers.getFirstChildWithName(new QName("identifier")).getText());
                    bookInfo += volumeInfo.getFirstChildWithName(new QName("publisher")).getText()+separator;
                    //System.out.println("Publisher: "+volumeInfo.getFirstChildWithName(new QName("publisher")).getText());
                    bookInfo += volumeInfo.getFirstChildWithName(new QName("publishedDate")).getText()+separator;
                    //System.out.println("Publish Date: "+volumeInfo.getFirstChildWithName(new QName("publishedDate")).getText());
                    bookInfo += "back order"+separator;
                    bookInfo += volumeInfo.getFirstChildWithName(new QName("averageRating")).getText()+separator;
                    //System.out.println("Average Rating : "+volumeInfo.getFirstChildWithName(new QName("averageRating")).getText());
                    bookInfo += saleInfo.getFirstChildWithName(new QName("saleability")).getText();
                    //System.out.println("Saleability(AU) : "+saleInfo.getFirstChildWithName(new QName("saleability")).getText());
                    
                    System.out.println(bookInfo);
                    
                    
                    }
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } else {
            // normal XML navigation
        	
        }
        return bookInfo;
    }
    
    

    private static XMLStreamReader getReader(String text, OMDataSource omDataSource) throws XMLStreamException {
        if (omDataSource instanceof JSONDataSource) {
            return new MappedXMLInputFactory(new HashMap()).createXMLStreamReader(new JSONTokener(text));
        } else {
            return new BadgerFishXMLInputFactory().createXMLStreamReader(new JSONTokener(text));
        }
    }
}
