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
 * This is Student Service client for the library it uses RESTful services to retrive student data.
 * @author Vov
 *
 */
public class StudentServiceRESTClient 
{
	
	/**
	 * used to add new student to database
	 * @param studentData
	 * @return
	 * @throws AxisFault
	 */
	public static String servicePOST(String studentData) throws AxisFault {
        String epr = LocalConstants.StudentServiceEndpoint+"students";
        EndpointReference targetEPR = new EndpointReference(epr);

        Options options = new Options();
        options.setTo(targetEPR);
        options.setProperty(Constants.Configuration.MESSAGE_TYPE, "text/xml");
        options.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_POST);
        options.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);

        ServiceClient sender = new ServiceClient();
        sender.setOptions(options);

        OMElement response = sender.sendReceive(createRequestPayload(studentData));
        return processResponsePayload(response);
    }

    /**
     * used to update existing student data in database
     * @param id
     * @param studentData
     * @return
     * @throws AxisFault
     */
    public static String servicePUT(String id, String studentData) throws AxisFault {
        String epr = LocalConstants.StudentServiceEndpoint+"student/"+id;
        EndpointReference targetEPR = new EndpointReference(epr);

        Options options = new Options();
        options.setTo(targetEPR);
        options.setProperty(Constants.Configuration.MESSAGE_TYPE, "application/xml");
        options.setProperty(Constants.Configuration.HTTP_METHOD, Constants.Configuration.HTTP_METHOD_PUT);
        options.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);

        ServiceClient sender = new ServiceClient();
        sender.setOptions(options);

        OMElement response = sender.sendReceive(createRequestPayload(studentData));
        return processResponsePayload(response);
    }

    /**
     * used to get student data from database
     * @param id
     * @return
     * @throws AxisFault
     */
    public static String serviceGET(String id) throws AxisFault {
        String epr = LocalConstants.StudentServiceEndpoint+"student/"+id;

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
     * created xml payload to transfer data.
     * @param studentData
     * @return
     */
    private static OMElement createRequestPayload(String studentData) {
    	String[] studentDet = splitData(studentData);
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace("http://waa.swin.edu.au", "ns");

        OMElement method = fac.createOMElement("addStudent", omNs);
        
        OMElement studentID = fac.createOMElement("studentID", omNs);
        studentID.addChild(fac.createOMText(studentID, studentDet[0]));
        method.addChild(studentID);

        OMElement studentName = fac.createOMElement("studentName", omNs);
        studentName.addChild(fac.createOMText(studentName, studentDet[1]));
        method.addChild(studentName);
        
        OMElement studentPin = fac.createOMElement("studentPin", omNs);
        studentPin.addChild(fac.createOMText(studentPin, studentDet[2]));
        method.addChild(studentPin);
        
        return method;
    }
    
    public static String[] splitData(String studentData)
    {
    	String[] parts = studentData.split("\\~");
    	return parts;
    }
    
    /**
     * used to process the response from REST service.
     * @param response
     * @return
     */
    private static String processResponsePayload(OMElement response) {
        Iterator iterator = response.getChildrenWithLocalName("return");
        OMElement returnElement = (OMElement) iterator.next();
        return returnElement.getText();
    }
}
