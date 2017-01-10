package au.edu.swin.waa;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;

/**
 * @author Vov
 *
 */
public class GoogleBookServiceJSON
{
	
		
		/**
		 * @param request
		 * @return
		 * @throws XMLStreamException
		 */
		public OMElement getBookFromGoogle(OMElement request) throws XMLStreamException 
		{
			String isbn="";
			Iterator iterator = request.getChildren();
			while (iterator.hasNext()) {
				OMElement child = (OMElement)iterator.next();
				isbn= child.getText();
				//System.out.println(child.getLocalName());
				//System.out.println(child.getText());
			}
			String response ="";
			try{
			// Prepare response
				response = "<getBookFromGoogle><return>" + GoogleBooksHelper.getBook(isbn) + "</return></getBookFromGoogle>";
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
				
	        return new StAXOMBuilder(new ByteArrayInputStream(response.getBytes())).getDocumentElement();
		
		
		}
		
		
}