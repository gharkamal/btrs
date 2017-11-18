import java.io.FileInputStream;
import java.util.Properties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;

public class Driver{
	
	public static Connection connection;
    public static void main(String[] args){
    	connection=null;
		try{
			Properties props = new Properties();
			FileInputStream file = new FileInputStream("db.properties");
			props.load(file);
			MysqlDataSource mysqlID=  new MysqlDataSource();
			mysqlID.setURL( props.getProperty("MYSQL_DB_URL") );
			mysqlID.setUser(props.getProperty("MYSQL_DB_USERNAME") );
			mysqlID.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
			connection = mysqlID.getConnection();
		}catch(SQLException e){
			e.printStackTrace();
			return;
		}catch(Exception e){
			System.out.println("Failure");
		}
		//----------------------------TESTING------------------------------------------
		
		boolean success= login("1","lucky321");
		if(success)
			System.out.println("Login Okay");
		
		success = signUp("Maddie Mai","grassonfire1@sjsu.edu","friedchicken",4645 );
		if(success)
			System.out.println("SignUp Okay");
		
		success= updatePassword(1,"Yimin Mei","tyranny@sjsu.edu",44444,"ICANNOTSLEEP");
		if(success)
			System.out.println("Update password okay");
		
		//----------------------------TESTING------------------------------------------
		
    }
    // functional requirement 1
    public static boolean login(String accountID,String password){
    	Statement statement = null;
    	try{
    		statement = connection.createStatement();
    		String code = "select *"
    				    + "from Account_Holder \n"
    				    + "where accountID ="+accountID
    				    +" and password = ' "+ password +" ' ";
    				  
    		boolean hasResults =statement.execute(code);
    		if(hasResults)
    			return true;
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return false;
    }
    // functional requirement 2
    public static boolean signUp(String fullName,String email,String password,int creditCard ){
    	PreparedStatement preparedstatement=null;
    	try{
    		String code= "INSERT INTO Account_Holder(fullName,email,password,creditCard) "
    				+    "values(?,?,?,?);  ";
    		preparedstatement=connection.prepareStatement(code);
    		preparedstatement.setString(1, fullName);
    		preparedstatement.setString(2, email);
    		preparedstatement.setString(3, password);
    		preparedstatement.setInt(4, creditCard);
    		int hasChanged = preparedstatement.executeUpdate();
    		if(hasChanged ==1)
    			return true;
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return false;
    	
    }
    
    
    
    // functional requirement 9
    public static boolean updateAccount(int accountID,String newEmail,int newCreditCard, String password ){
    	PreparedStatement preparedstatement=null;
    	try{
    		String code = "Update Account_Holder "
    					 +"Set email = ?, creditCard=?"
    					 +"where accountID = ? password = ?";
    		preparedstatement = connection.prepareStatement(code);
    		preparedstatement.setString(1,newEmail);
    		preparedstatement.setInt(2,newCreditCard);
    		preparedstatement.setInt(3,accountID);
    		preparedstatement.setString(4,password);
    		int hasChanged = preparedstatement.executeUpdate();
    		if(hasChanged==1)
    			return true;
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return false;
    }
    
    // functional requirement 10
    public static boolean updatePassword(int accountID,String fullName,String email,int creditCard, String newPassword ){
    	PreparedStatement preparedstatement = null;
    	try{
    		String code = "Update Account_Holder "
    				     +"Set password = ?"
    				     +"where accountID = ? and fullName= ? and creditCard = ? and email=?";
    		preparedstatement = connection.prepareStatement(code);
    		preparedstatement.setString(1,newPassword);
    		preparedstatement.setInt(2,accountID);
    		preparedstatement.setString(3,fullName);
    		preparedstatement.setInt(4,creditCard);
    		preparedstatement.setString(5,email);
    		int hasChanged = preparedstatement.executeUpdate();
    		if(hasChanged==1)
    			return true;
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return false;
    }
    
    //Functional Requirment 11
    public static boolean banCustomer(int accountID) {
    		PreparedStatement preparedstatement= null;
    		
    		try {
    			String code = "INSERT INTO Banned(accountID) values(?) "
    					+ "where accountID in (select accountID from Account_Holder)";
    			preparedstatement=connection.prepareStatement(code);
        		preparedstatement.setInt(1, accountID);
        		int hasChanged = preparedstatement.executeUpdate();
        		if(hasChanged ==1)
        			return true;
    			
 		}catch(Exception e){
 			e.printStackTrace();
 			return false;
 		}
 		return false;
    	
    }
    
    //Functional Requirement 12: add a train
    public static boolean addTrain(int trainID, String deptDateTime, boolean isFull)
    {
    		PreparedStatement preparedstatement= null;
    		try {
    			String code = "INSERT INTO Train(trainID, deptDateTime, isFull) values(?,?,?)"
    					+ " where trainID not in (select trainID from Train)";
    			preparedstatement = connection.prepareStatement(code);
    			preparedstatement.setInt(1, trainID);
    			preparedstatement.setString(2, deptDateTime);
    			preparedstatement.setBoolean(3, isFull);
    			int hasChanged = preparedstatement.executeUpdate();
        		if(hasChanged ==1)
        			return true;
        		
    		}catch(Exception e) {
    			e.printStackTrace();
    			return false;
    		}
    		return false;
    }
    
    //Functional Requirement 13: Adding station: Add a new station.
    public static boolean addStation(int stationID, String name, int order)
    {
    		PreparedStatement preparedstatement= null;
    		try {
    			String code = "INSERT INTO Station(stationID, name, order) values(?,?,?)"
    					+ " where stationID not in (select stationID from Station)";
    			preparedstatement = connection.prepareStatement(code);
    			preparedstatement.setInt(1, stationID);
    			preparedstatement.setString(2, name);
    			preparedstatement.setInt(3, order);
    			int hasChanged = preparedstatement.executeUpdate();
        		if(hasChanged ==1)
        			return true;
        		
    		}catch(Exception e) {
    			e.printStackTrace();
    			return false;
    		}
    		return false;
    }
    
    //functional requirement 14: remove ban
    public static boolean removeBan(int accountID) {
    		PreparedStatement preparedstatement= null;
    		try {
    			String code = "DELETE FROM Banned where accountID = ?";
    			preparedstatement = connection.prepareStatement(code);
    			preparedstatement.setInt(1, accountID);
    			int hasChanged = preparedstatement.executeUpdate();
    			if(hasChanged ==1)
        			return true;
    			
    		}catch(Exception e) {
    			e.printStackTrace();
    			return false;
    		}
    		return false;
    }
    
    //Functional Requirment 15: Sign   up   for   Wifi
    public static boolean wifiUpdate(int accountID, boolean wifi  )
    {
    	PreparedStatement preparedstatement= null;
		try {
			String code = "UPDATE Account_Holder set wifi = ? where accountID = ? ";
			preparedstatement = connection.prepareStatement(code);
			preparedstatement.setBoolean(1, wifi);
			preparedstatement.setInt(2, accountID);
			int hasChanged = preparedstatement.executeUpdate();
			if(hasChanged ==1)
    			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
    }
}
