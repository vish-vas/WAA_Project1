package au.edu.swin.waa;

import java.io.File;
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
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;

/**
 * This JSON client is used to access the google services api for getting book data.
 * @author Vov
 *
 */
public class GoogleServiceJSONClient 
{

	/**
	 * this restful client module encodes xml data in json format and requests the JSON service for book details.
	 * @param isbn
	 * @return
	 * @throws AxisFault
	 */
	public static String getGoogleBookData(String isbn) throws AxisFault 
	{
		String epr = LocalConstants.GoogleServiceEndpoint;
		EndpointReference targetEPR = new EndpointReference(epr);
		Options options = new Options(); 
		options.setTo(targetEPR);
		options.setProperty(Constants.Configuration.MESSAGE_TYPE, "application/json");
		options.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_POST);
		File configFile = new File("axis2.xml"); 
		ConfigurationContext clientConfigurationContext; 
		clientConfigurationContext = ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, configFile.getAbsolutePath());
		ServiceClient sender = new ServiceClient(clientConfigurationContext, null);
		sender.setOptions(options);
		OMElement response = sender.sendReceive(createRequestPayload(isbn));
		return processResponsePayload(response); 
	}
	
	/**
	 * used to create xml payload data for the request.
	 * @param bookIsbn
	 * @return
	 */
	private static OMElement createRequestPayload(String bookIsbn) 
	{
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("http://waa.swin.edu.au","ns");
		OMElement method = fac.createOMElement("getBookFromGoogle", omNs); 
		OMElement isbn = fac.createOMElement("isbn", omNs); 
		isbn.addChild(fac.createOMText(isbn, bookIsbn)); 
		method.addChild(isbn);
		
		return method; 
	}
	
	private static String processResponsePayload(OMElement response) 
	{ 
		Iterator iterator = response.getChildrenWithLocalName("return");
		OMElement returnElement = (OMElement)iterator.next(); 
		return returnElement.getText();
	}
	
}
