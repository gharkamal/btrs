import java.io.FileInputStream;
import java.util.Properties;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.util.Scanner;

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
		//----------------------------Console------------------------------------------
		boolean run= true;
		Scanner sc = new Scanner(System.in);
		System.out.println("-----Welcome to the BTRS Console-----");
		System.out.println("Enter the opcode and the respective inputs:");
		System.out.println("   1 <accountID> <password>                                        // Login");
		System.out.println("   2 <fullName> <email> <password> <creditCard>                    // Sign up");
		System.out.println("   3 <accountID> <startStdID> <endStID> <seatID> <dateTime>        // Reserve trip");
		System.out.println("   4 <accountID> <startStdID> <endStID> <seatID> <dateTime>        // Change trip destination");
		System.out.println("   9 <accountID> <newEmail> <newCreditCard> <password>             // Update user information");
		System.out.println("   10 <accountID> <fullName> <email> <creditCard> <newPassword>    // Update new password");
		System.out.println("   11 <accountID>                                                  // Ban accountID");
		System.out.println("   12 <trainID> <deptDateTime> <isFull>                            // Add new train");
		System.out.println("   13 <stationID> <name> <order>                                   // Add new station");
		System.out.println("   14 <accountID>                                                  // Remove ban");
		System.out.println("   15 <accountID> <wifi>                                           // Update wifi");
		System.out.println("   16 Quit");
		
		while(run){
			try{
				int opcode = sc.nextInt();
				switch(opcode){
					case 1:
						int accountID1= sc.nextInt();
						String password1 = sc.next();
						boolean success1= login(accountID1,password1);
						if(success1)
							System.out.println("-----Login Successful!-----");
						else 
							System.out.println("-----Login Failed!-----");
						break;
					case 2:
						String fullName2=sc.next();
						String email2 = sc.next();
						String password2=sc.next();
						int creditCard2= sc.nextInt();
						boolean success2 = signUp(fullName2,email2,password2,creditCard2);
						if(success2)
							System.out.println("-----Sign Up Successful!-----");
						else 
							System.out.println("-----Sign Up Failed!-----");
						break;
					case 3:
						int accountID3= sc.nextInt();
						int startstdID3 =sc.nextInt();
						int endstID3=sc.nextInt();
						int seatID3 = sc.nextInt();
						String dateTime3= sc.next();
						boolean success3 = reserveTrip(accountID3,startstdID3,endstID3,seatID3,dateTime3);
						if(success3)
							System.out.println("-----Reserve Successful!-----");
						else 
							System.out.println("-----Reserve Failed!-----");
						break;
					case 4:
						int accountID4= sc.nextInt();
						int startstdID4 =sc.nextInt();
						int endstID4=sc.nextInt();
						int seatID4 = sc.nextInt();
						String dateTime4= sc.next();
						boolean success4 = updateTrip(accountID4,startstdID4,endstID4,seatID4,dateTime4);
						if(success4)
							System.out.println("-----Update Reserve Successful!-----");
						else 
							System.out.println("-----Update Reserve Failed!-----");
						break;
					case 5:
						break;
					case 6:
						break;
					case 7:
						break;
					case 8:
						break;
					case 9:
						int accountID9=sc.nextInt();
						String email9 = sc.next();
						int creditcard9 = sc.nextInt();
						String password9 = sc.next();
						boolean success9 = updateAccount(accountID9,email9,creditcard9,password9);
						if(success9)
							System.out.println("-----Update Account Successful!-----");
						else 
							System.out.println("-----Update Account Failed!-----");
						break;
					case 10:
						int accountID10 = sc.nextInt();
						String fullName10 = sc.next();
						String email10 = sc.next();
						int creditCard10 = sc.nextInt();
						String newpassword10 = sc.next();
						boolean success10 = updatePassword(accountID10,fullName10,email10,creditCard10,newpassword10);
						if(success10)
							System.out.println("-----Update Password Successful!-----");
						else 
							System.out.println("-----Update Password Failed!-----");
						break;
					case 11:
						int accountID11=  sc.nextInt();
						boolean success11 = banCustomer(accountID11);
						if(success11)
							System.out.println("-----Ban Successful!-----");
						else 
							System.out.println("-----Ban Failed!-----");
						break;
					case 12:
						int trainID12 = sc.nextInt();
						String deptTime12 = sc.next();
						boolean isfull12 = sc.nextBoolean();
						boolean success12 = addTrain(trainID12,deptTime12,isfull12);
						if(success12)
							System.out.println("-----Add Train Successful!-----");
						else 
							System.out.println("-----Add Train Failed!-----");
						break;
					case 13:
						int trainID13 = sc.nextInt();
						String name13 = sc.next();
						int order13 = sc.nextInt();
						boolean success13 = addStation(trainID13, name13,order13);
						if(success13)
							System.out.println("-----Add Station Successful!-----");
						else 
							System.out.println("-----Add Station Failed!-----");
						break;
					case 14:
						int accountID14 = sc.nextInt();
						boolean success14 = removeBan(accountID14);
						if(success14)
							System.out.println("-----Remove Ban Successful!-----");
						else 
							System.out.println("-----Remove Ban Failed!-----");
						break;
					case 15:
						int accountID15 = sc.nextInt();
						boolean wifi=sc.nextBoolean();
						boolean success15 =wifiUpdate(accountID15,wifi);
						if(success15)
							System.out.println("-----Update WIFI Successful!-----");
						else 
							System.out.println("-----Update WIFI Failed!-----");
						break;
					case 16:
						System.out.println("-----Bye!-----");
						run=false;
						break;
					default:
					System.out.println("-----Not a Valid Opcode!-----");
				}
				
			}catch(Exception e){
				System.out.println("-----Not a Valid Command!-----");	
			}
		}
		
		//--------------------Console-----------------------------------------------
		sc.close();
		
		try{
			connection.close();
		}catch(Exception e){
			
		}
		System.exit(0);
		
    }
    // Functional requirement 1
    public static boolean login(int accountID,String password){
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
    // Functional requirement 2
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

    //Functional requirement 3 : Reserve train destination: Sign up for a trip.
    public static boolean reserveTrip(int accountID, int startStdID, int endStID, int seatID, String dateTime)
    {
    	PreparedStatement preparedstatement=null;
    	try{
        String code = "INSERT INTO Passenger values(?,?,?,?,?,?)";
        	preparedstatement=connection.prepareStatement(code);
            preparedstatement.setInt(1, accountID);
            preparedstatement.setInt(2, startStdID);
            preparedstatement.setInt(3, endStID);
            preparedstatement.setInt(4, seatID);
            preparedstatement.setString(5, dateTime);
            preparedstatement.setBoolean(6, false);
            int hasChanged = preparedstatement.executeUpdate();
            if(hasChanged ==1)
                return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
        
    }

    //Functional requirement 4: Change/update train destination: Update a trip destination.
    public static boolean updateTrip(int accountID, int startStdID, int endStID, int seatID, String dateTime)
    {
    	PreparedStatement preparedstatement=null;
    	try{
         	String code = "UPDATE Passenger set startStdID = ?, endStID = ?, seatID = ?, dateTime = ?"
                         + "WHERE accountID = ?";
         	preparedstatement=connection.prepareStatement(code);
            preparedstatement.setInt(1, accountID);
            preparedstatement.setInt(2, startStdID);
            preparedstatement.setInt(3, endStID);
            preparedstatement.setInt(4, seatID);
            preparedstatement.setString(5, dateTime);
            preparedstatement.setInt(6, accountID);
            int hasChanged = preparedstatement.executeUpdate();
            if(hasChanged ==1)
                return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // Functional requirement 9
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
    
    // Functional requirement 10
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
    
    //Functional Requirement 11
    public static boolean banCustomer(int accountID) {
    		PreparedStatement preparedstatement= null;
    		
    		try {
    			String code = "INSERT INTO Banned(accountID) values(?) "
    					+ "where ? in (select accountID from Account_Holder)";
    			preparedstatement=connection.prepareStatement(code);
        		preparedstatement.setInt(1, accountID);
                preparedstatement.setInt(2, accountID);
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
    
    //Functional Requirement 15: Sign up for Wifi
    public static boolean wifiUpdate(int accountID, boolean wifi )
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
