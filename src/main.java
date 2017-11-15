import java.io.FileInputStream;
import java.util.Properties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;

public class main{
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
			Statement statement = connection.createStatement();
			statement.execute("USE BTRS;");
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
		success = signUp("Maddie Mai","grassonfire@sjsu.edu","friedchicken",4645 );
		if(success)
			System.out.println("SignUp Okay");
		
		
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
    		boolean hasResults = preparedstatement.execute();
    		if(hasResults)
    			return true;
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return false;
    	
    }

}
