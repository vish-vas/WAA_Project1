
/**
 * StudentAuthenticationServiceSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v14  Built on : Jul 25, 2015 (11:19:54 IST)
 */
    package waa.swin.edu.au;

    /**created by wsdl topdown soap artifact.
     *  StudentAuthenticationServiceSOAPSkeleton java skeleton for the axisService
     */
    public class StudentAuthenticationServiceSOAPSkeleton{
        
         
        /**
         * Auto generated method signature
         * 
                                     * @param studentAuthentication 
             * @return studentAuthenticationResponse 
         */
        
                 /**used to verify student details and authenticate them.
                 * @param studentAuthentication
                 * @return
                 */
                public waa.swin.edu.au.StudentAuthenticationResponse studentAuthentication
                  (
                  waa.swin.edu.au.StudentAuthentication studentAuthentication
                  )
            {
                	 StudentAuthenticationResponse response = new StudentAuthenticationResponse();
         			
         			try
         			{

         			String enteredId = studentAuthentication.getStudentID();
         			String enteredPin = studentAuthentication.getStudentPin();

         			StudentDBInterface db = new StudentDBInterface();
         			String student = db.getStudentFromDB(enteredId);
         			if(student!=null)
         			{
         				String[] stuDet = splitData(student);
         				if(enteredPin.equals(stuDet[2]))
         				{
         					response.setMessage("Authenticated");
         					return response;
         					
         				}
         				
         				response.setMessage("Failed");
         				return response;
         			}

         			response.setMessage("Failed");
         			return response;

         			}
         			catch(Exception ex)
         			{
         				response.setMessage("Failed");
         				return response;
         			}
         			}
         	public static String[] splitData(String data)
             {
             	String[] parts = data.split("\\~");
             	return parts;
             }
     
    }
    