package waa.swin.edu.au;

/**REST ful student service used to access student details from the database.
 * @author Vov
 *
 */
public class StudentServiceREST
{
	String separator ="~";
	/**can be used to add new student to db
	 * @param studentID
	 * @param studentName
	 * @param studentPin
	 * @return
	 * @throws Exception
	 */
	public String addStudent(String studentID, String studentName, String studentPin) throws Exception
	{
		String studentData = studentID+separator+studentName+separator+studentPin;
		StudentDBInterface db = new StudentDBInterface();
		if(db.addNewStudentToDB(studentData))
			return "Student added";
		else
			return "Student not added";
	}
	
	/**used to get details for a student
	 * @param studentID
	 * @return
	 * @throws Exception
	 */
	public String getStudentDetails(String studentID) throws Exception
	{
		StudentDBInterface db = new StudentDBInterface();
		String resp = db.getStudentFromDB(studentID);
		if(resp!=null&& !resp.equals(""))
			return resp;
		else
			return "Student not found";
	}
	
	/** can be used to update user details to database.
	 * @param StudentId
	 * @param studentName
	 * @param studentPin
	 * @return
	 * @throws Exception
	 */
	public String updateStudent(String StudentId, String studentName, String studentPin) throws Exception
	{
		
		String studentData = StudentId+separator+studentName+separator+studentPin;
		StudentDBInterface db = new StudentDBInterface();
		if(db.updateStudentInDB(studentData))
			return "Student updated";
		else
			return "Student not updated";
	}
	
	
}