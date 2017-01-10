package waa.swin.edu.au;

public class Constants 
{

	public static final String DB_USER = "unilib";
	public static final String DB_PASSWORD = "unilib";
	public static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String DB_CLASS = "oracle.jdbc.driver.OracleDriver";
	
	public static final String STUDENTDB_TABLE_NAME = "";
	public static final String BOOKINGDB_TABLE_NAME = "";
	public static final String REQUESTDB_TABLE_NAME = "";
	
	public static final String BLANK_SPACE = " ";
	
	/*public static final String SELECT_STATEMENT = "select * from "+TABLE_NAME;
    public static final String SELECT_WHERE_STATEMENT = "select * from "+TABLE_NAME+" where "+" = ?";
    public static final String INSERT_STATEMENT = "insert into" +BLANK_SPACE+TABLE_NAME+BLANK_SPACE+ "values()";
    public static final String UPDATE_STATEMENT = "update" +BLANK_SPACE+TABLE_NAME+BLANK_SPACE+ 
            "set"+BLANK_SPACE+NAME_COLUMN+BLANK_SPACE+"=?"+BLANK_SPACE+PASSWORD_COLUMN+BLANK_SPACE+
            "=?"+BLANK_SPACE+EMAIL_COLUMN+BLANK_SPACE+"=?"+BLANK_SPACE+TEL_COLUMN+BLANK_SPACE+"=?"
            +BLANK_SPACE+ADDRESS_COLUMN+BLANK_SPACE+"=?"+BLANK_SPACE+SECQN_COLUMN+BLANK_SPACE+
            "=?"+BLANK_SPACE+SECANS_COLUMN+BLANK_SPACE+"=? where"+BLANK_SPACE+USERID_COLUMN+BLANK_SPACE+ "= ?";
    
    public static final String DELETE_STATEMENT = "delete from" +BLANK_SPACE+TABLE_NAME+BLANK_SPACE+ 
            "where" +BLANK_SPACE+USERID_COLUMN+BLANK_SPACE+ "= ?";*/
	
	public static final String BOOK_STATUS_AVAILABLE = "available";
	public static final String BOOK_STATUS_ONLOAN = "onLoan";
	public static final String BOOK_STATUS_BACKORDER = "backOrder";
}
