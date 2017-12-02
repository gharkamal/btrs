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
       // System.out.println("   3 <accountID> <startStdID> <endStID> <seatID> <dateTime>        // Reserve trip");
        //System.out.println("   4 <accountID> <startStdID> <endStID> <seatID> <dateTime>        // Change trip destination");
       // System.out.println("   11 <accountID>                                                  // Ban accountID");
        //System.out.println("   12 <deptDateTime> <isFull>                            		   // Add new train");
        //System.out.println("   13 <name> <order>                                   			   // Add new station");
        //System.out.println("   14 <accountID>                                                  // Remove ban");
        //System.out.println("   16                                            // View Passengers (Admin)");
        //System.out.println("   17 Quit");
        int opcode=0;
        System.out.println("-----Welcome to the BTRS Console-----");
        while(run){
        		Scanner sc = new Scanner(System.in); 
            System.out.println("Enter the opcode or 0 to quit:");
            System.out.println("	1  Login");
            System.out.println("	2  Register for an Account");
            System.out.println("	3  Reserve without Account");
            System.out.println("	4  Admin login");
            System.out.println("	5  Retrieve Account Number");
            System.out.print("Input: ");    
            try{
                opcode = Integer.parseInt(sc.next());
                switch(opcode){
                    case 1:
                    	   System.out.println("Enter below: <accountID> <Password>");
                    	   
                        int accountID1= Integer.parseInt(sc.next()) ;
                        //System.out.print(accountID1);
                        String password1 = sc.next();
                        //System.out.print(password1);
                        boolean success1= login(accountID1,password1);
                        if(success1)
                        {
                            System.out.println("\n -----Login Successful!-----");
                        	   System.out.println("1 Reserve Trip");
                        	   System.out.println("2 Update user information");
                        	   System.out.println("3 Change trip destination");
                        	   System.out.println("4 Change Password");
                        	   System.out.println("5 Purchase Wifi");
                        	   try {
                        		   opcode = Integer.parseInt(sc.next());
                        		   switch(opcode)
                        		   {
                        		   case 1:		  
                        			   showPossibleTripTimes(); //show user all the possible trip times                
                        	    		   System.out.println("Input desired TrainID: <TrainID> ");
                        	    		   int trainIDSelected = sc.nextInt();  //store TrainID here that user selects
                        			   //Show all possible trains avaliable and then tell user to select which one
                        	    		   System.out.println("\n");
                        	    		   showPossibleStation();
                        	    		   System.out.println("Input desired Stop Station: <StationID> ");                     	    		  
                        	    		   int stopStation = sc.nextInt();   //only gets first token if more than one word                      	    		  
                        	    		   Boolean seatCheck = showPossibleSeat(trainIDSelected);
                        	    		   String seatSelected = "";
                        	    		   if(seatCheck == true)
                        	    		   {
                        	    			   System.out.println("Input Desired Seat: ");
                        	    			   seatSelected = sc.next();
                        	    		   }
                        	    		   else {
                        	    			   break;
                        	    		   }
                                    boolean success3 = reserveTrip(accountID1,stopStation,seatSelected, false);
                                    if(success3)
                                         System.out.println("-----Reservation Successful!-----");
                                    else {
                                         System.out.println("-----Reservation Failed!-----");
                                         break;
                                    }
                        			  break;
                        		   case 2:
                        			   System.out.println("Enter: <newEmail> <newCreditCard>");
                        			   String email2 = sc.next();
                                    int creditcard2 = Integer.parseInt(sc.next());
                                    boolean success9 = updateAccount(accountID1,email2,creditcard2,password1);
                                    if(success9) {
                                        System.out.println("-----Update Account Successful!-----");
                                        break;
                                    }
                                    else 
                                        System.out.println("-----Update Account Failed!-----");
                                    break;
                        		   case 3:
                        			   //show possible train times to allow user to update 
                        			   break;
                        		   case 4:
                        			   System.out.println("Enter: <oldPassword> <newPassword>");
                        			   String oldPassword = sc.next();
                        			   String newPassword = sc.next();
                        			   if(oldPassword.equals(password1)) {
                        				   boolean success4 = updatePassword(accountID1,newPassword);
                                           if(success4) {
                                               System.out.println("-----Update Password Successful!-----");
                                           	  break;  }
                                           else 
                                               System.out.println("-----Update Password Failed!-----");
                                           break;   
                        			   }
                        		   case 5:
                        			   boolean wifi=sc.nextBoolean();
                        			   boolean success15 =wifiUpdate(accountID1,wifi);
                        			   if(success15)
                                         System.out.println("-----Successful Purchased Wifi!-----");
                        			   else 
                        				    System.out.println("-----Update WIFI Failed!-----");
                        			   break;
                        			   

                        					   
                        		   }
                        		   
                        	   } catch(Exception e){
                                   System.out.println("-----Not a Valid Command!-----");   
                                   System.exit(0);
                               }
                        	  
                        	   
                        }
                        else 
                            System.out.println("-----Login Failed!-----");
                        	   break;
                    case 2:
                        System.out.println("Enter: <First Name> <Last Name> <email> <password> <Credit Card #>");
                        String firstName=sc.next();
                        String lastName = sc.next();
                        String email = sc.next();
                        String password =sc.next();
                        String creditCard = sc.next();
                        System.out.println(creditCard);
                        boolean success2 = signUp(firstName,lastName, email,password,creditCard);
                        if(success2)
                            System.out.println("-----Sign Up Successful!-----");
                        else 
                            System.out.println("-----Sign Up Failed!-----");
                        break;
//                    case 3:
//                        int accountID3= Integer.parseInt(sc.next());
//                        int startstdID3 =Integer.parseInt(sc.next()) ;
//                        int endstID3=Integer.parseInt(sc.next());
//                        int seatID3 =Integer.parseInt( sc.next());
//                        String dateTime3= sc.next();
//                       // boolean success3 = reserveTrip(accountID3,startstdID3,endstID3,seatID3,dateTime3);
//                        if(success3)
//                            System.out.println("-----Reserve Successful!-----");
//                        else 
//                            System.out.println("-----Reserve Failed!-----");
//                        break;
                    case 4:
                        int accountID4= Integer.parseInt(sc.next());
                        int startstdID4 =Integer.parseInt(sc.next());
                        int endstID4=Integer.parseInt(sc.next());
                        int seatID4 = Integer.parseInt(sc.next());
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
                        int accountID7=Integer.parseInt(sc.next());
                        int newseat7 = Integer.parseInt(sc.next());
                        boolean success7 = changeSeat(accountID7,newseat7);
                        if(success7)
                            System.out.println("-----Update Seat Successful!-----");
                        else 
                            System.out.println("-----Update Seat Failed!-----");
                        break;
                    case 8:
                        break;
                    case 9:
                        int accountID9=Integer.parseInt(sc.next());
                        String email9 = sc.next();
                        int creditcard9 = Integer.parseInt(sc.next());
                        String password9 = sc.next();
                        boolean success9 = updateAccount(accountID9,email9,creditcard9,password9);
                        if(success9)
                            System.out.println("-----Update Account Successful!-----");
                        else 
                            System.out.println("-----Update Account Failed!-----");
                        break;
                    case 11:
                        int accountID11= Integer.parseInt( sc.next());
                        boolean success11 = banCustomer(accountID11);
                        if(success11)
                            System.out.println("-----Ban Successful!-----");
                        else 
                            System.out.println("-----Ban Failed!-----");
                        break;
                    case 12:
                        String deptTime12 = sc.next();
                        boolean isfull12 = sc.nextBoolean();
                        boolean success12 = addTrain(deptTime12,isfull12);
                        if(success12)
                            System.out.println("-----Add Train Successful!-----");
                        else 
                            System.out.println("-----Add Train Failed!-----");
                        break;
                    case 13:
                
                        String name13 = sc.next();
                        int order13 = Integer.parseInt(sc.next());
                        boolean success13 = addStation(name13,order13);
                        if(success13)
                            System.out.println("-----Add Station Successful!-----");
                        else 
                            System.out.println("-----Add Station Failed!-----");
                        break;
                    case 14:
                        int accountID14 = Integer.parseInt(sc.next());
                        boolean success14 = removeBan(accountID14);
                        if(success14)
                            System.out.println("-----Remove Ban Successful!-----");
                        else 
                            System.out.println("-----Remove Ban Failed!-----");
                        break;
                     
                    case 16:
                        //int accountID16 = Integer.parseInt(sc.next());
                        ResultSet success16 =currentPassengers();
                        if(success16.first())
                            System.out.println(success16);
                        else 
                            System.out.println("-----Update WIFI Failed!-----");
                        break;
                    case 0:
                        System.out.println("-----Bye!-----");
                        run=false;
                        break;
                    default:
                    System.out.println("-----Not a Valid Opcode!-----");
                }
                
            }catch(Exception e){
                System.out.println("-----Not a Valid Command!-----");   
                System.exit(0);
            }
            
        }
        
        //--------------------Console-----------------------------------------------
        
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
                        +" and password = "+ password;
                      
            ResultSet hasResults =statement.executeQuery(code);
            if(hasResults.first() )
                return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
    // Functional requirement 2
    public static Boolean signUp(String firstName,String lastName, String email,String password,String creditCard ){
        PreparedStatement preparedstatement=null;
        PreparedStatement getID=null;
        try{
            String code= "INSERT INTO Account_Holder(firstName,lastName, email,password,creditCard) "
                    +    "values(?,?,?,?,?);  ";
            preparedstatement=connection.prepareStatement(code);
            
            preparedstatement.setString(1, firstName);
            preparedstatement.setString(2, lastName);
            preparedstatement.setString(3, email);
            preparedstatement.setString(4, password);
            preparedstatement.setString(5, creditCard);
            int hasChanged = preparedstatement.executeUpdate();
            if(hasChanged == 1) {
            		String returnAccountID = "Select accountID from Account_Holder where "
            				+ "firstName = ? and lastName =? and email= ? and password = ? and creditCard = ? ";
            		getID=connection.prepareStatement(returnAccountID);
            		getID.setString(1, firstName);
            		getID.setString(2, lastName);
            		getID.setString(3, email);
            		getID.setString(4, password);
            		getID.setString(5, creditCard);
                ResultSet result = getID.executeQuery();
                int accountID = 0;
                while (result.next()) {
                 accountID = result.getInt("accountID");
                }
                System.out.println("Your Account ID is: " + accountID);
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
        
    }
    
    //allow user to see all possible trips from SF to select right one
    public static void showPossibleTripTimes()
    {
    		PreparedStatement preparedstatement=null;
    		try {
    			String code = "select trainID, deptTime from Train where isFULL = false";
    			preparedstatement=connection.prepareStatement(code);
    			ResultSet result = preparedstatement.executeQuery();
    			String deptTime = "";
	    		   int trainID = 0;
	    		   System.out.println("Train ID"  + " " + "Departure Time");
	    		   while (result.next()) {
	    				deptTime = result.getString("deptTime");
	    				trainID = result.getInt("trainID"); 
	    				System.out.println("	" +trainID + " " + deptTime);
	            }
    		}catch(Exception e){
                e.printStackTrace();
                //return null;
            }   
    }


    //allow user to see all possible Stations from SF to select right one
    public static void showPossibleStation()
    {
    		PreparedStatement preparedstatement=null;
    		try {
    			String code = "select stationID, name from Station";
    			preparedstatement=connection.prepareStatement(code);
    			ResultSet result = preparedstatement.executeQuery();
    			String name = "";
    			int stationID = 0;
	    		   System.out.println("\nStation   StationID");
	    		   while (result.next()) {
	    				name = result.getString("name"); 
	    				stationID = result.getInt("stationID"); 
	    				System.out.println(stationID + "	  " + name);
	            }
    		}catch(Exception e){
                e.printStackTrace();
                //return null;
            }   
    }
    
    //Find available seat
    public static boolean showPossibleSeat(int trainID)
    {
    		PreparedStatement preparedstatement=null;
    		try {
    			String code = "select seatID from Car, Train where Car.trainID = ? and Train.trainID = ?"; //check if seat is null
    			preparedstatement=connection.prepareStatement(code);
    		    preparedstatement.setInt(1, trainID);
    		    preparedstatement.setInt(2, trainID);
    			ResultSet result = preparedstatement.executeQuery();
    			String seat;
	    		   
    			  if(!result.next()) {
	    				System.out.println("No Seats Available");
	    				return false;
	    			}
    			  System.out.println("\nAvailable Seats:");
	    		   while (result.next()) {
	    				seat = result.getString("seatID"); 
	    				System.out.println(seat);
	    				return true;
	    		   	}
	    		 
    		}catch(Exception e){
                e.printStackTrace();
                //return null;
            }
    		return false;
    }

    
    //Functional requirement 3 : Reserve train destination: Sign up for a trip.
    public static boolean reserveTrip(int accountID, int endStID, String seatID, boolean wifi)
    {
        PreparedStatement preparedstatement=null;
        try{
        	    String code = "INSERT INTO Passenger(accountID,endStID,wifi) values(?,?,?)";
            preparedstatement=connection.prepareStatement(code);
            preparedstatement.setInt(1, accountID);
            preparedstatement.setInt(2, endStID);
            preparedstatement.setBoolean(3, wifi);
            int hasChanged = preparedstatement.executeUpdate();
            if(hasChanged ==1) {
            	PreparedStatement seater =null;
            		String seatSet = "UPDATE Car SET passengerID = "
            				+ "(select passengerID from Passenger where accountID = ? and endStID = ?)"
            				+ "WHERE seatID = ? ";
            		seater=connection.prepareStatement(seatSet);
            		seater.setInt(1, accountID);
            		seater.setInt(2, endStID);
            		seater.setString(3, seatID);
                 int hasChanged1 = seater.executeUpdate();
                 if(hasChanged1 ==1) {
                	 return true;
                 }
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
        
    }

    //Functional requirement 4: Change/update train destination: Update a trip destination.
    public static boolean updateTrip(int accountID, int startStID, int endStID, int seatID, String dateTime)
    {
        PreparedStatement preparedstatement=null;
        try{
            String code = "UPDATE Passenger "
                         + "SET startStID = ?, endStID = ?, dateTime = ?,seatID = ? \n"
                         +"WHERE accountID = ?";
            preparedstatement=connection.prepareStatement(code);
            preparedstatement.setInt(1, startStID );
            preparedstatement.setInt(2, endStID);
            preparedstatement.setString(3, dateTime);
            preparedstatement.setInt(4, seatID);
            preparedstatement.setInt(5, accountID);
            int hasChanged = preparedstatement.executeUpdate();
            if(hasChanged ==1)
                return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

      //Functional Requirment 5: Change train time: Update a trip time.
    

    //Requirement 7 : Change   seat:   Update   a   trip   seat.
    public static boolean changeSeat(int accountID, int updateSeat){
        PreparedStatement preparedstatement=null;
        try{
            String code = "UPDATE Passenger "
                         +"SET seatID = ?  "
                         +"WHERE accountID = ? ";
            preparedstatement = connection.prepareStatement(code);
            preparedstatement.setInt(1,updateSeat);
            preparedstatement.setInt(2,accountID);
            int hasChanged = preparedstatement.executeUpdate();
            if(hasChanged>0)
                return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //Requirement 8: Delete reservation:   Delete   a   trip.
      public static boolean deleteReservation(int accountID){
        PreparedStatement preparedstatement=null;
        try{
            String code = "DELETE FROM Passenger WHERE accountID = ?";
            preparedstatement = connection.prepareStatement(code);
            preparedstatement.setInt(1,accountID);
            int hasChanged = preparedstatement.executeUpdate();
            if(hasChanged==1)
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
            String code = "UPDATE Account_Holder "
                         +"SET email = ?, creditCard=? \n"
                         +"WHERE accountID = ? AND password = ?";
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
    public static boolean updatePassword(int accountID, String newPassword ){
        PreparedStatement preparedstatement = null;
        try{
            String code = "Update Account_Holder "
                         +"Set password = ?"
                         +"where accountID = ?";
            preparedstatement = connection.prepareStatement(code);
            preparedstatement.setString(1,newPassword);
            preparedstatement.setInt(2,accountID);
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
                String code = "INSERT INTO Banned(accountID) values(?)";
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
    public static boolean addTrain(String deptDateTime, boolean isFull)
    {
            PreparedStatement preparedstatement= null;
            try {
                String code = "INSERT INTO Train(deptTime, isFull) values(?,?)";
                preparedstatement = connection.prepareStatement(code);
                preparedstatement.setString(1, deptDateTime);
                preparedstatement.setBoolean(2, isFull);
                int hasChanged = preparedstatement.executeUpdate();
                if(hasChanged ==1)
                    return true;
            }catch(Exception e) {
                e.printStackTrace();
                return false;
            }
            return false;
    }
    
    //Functional Requirement 13: Adding station
    public static boolean addStation(String name, int order)
    {
            PreparedStatement preparedstatement= null;
            try {
                String code = "INSERT INTO Station(name, orderNumber) values(?,?)";
                preparedstatement = connection.prepareStatement(code);
                preparedstatement.setString(1, name);
                preparedstatement.setInt(2, order);
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
            String code = "UPDATE Passenger set wifi = ? where accountID = ? ";
            preparedstatement = connection.prepareStatement(code);
            preparedstatement.setBoolean(1, wifi);
            preparedstatement.setInt(2, accountID);
            int hasResult = preparedstatement.executeUpdate();
            if(hasResult>0)
                return true;
            
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    //Functional Requirement 16: Admin view to see the passengers and projected train and ride
    public static ResultSet currentPassengers() {
    	Statement statement= null;
        try {
        	statement = connection.createStatement();
            String code = "SELECT accountID, carNumber FROM Passenger, Car"
            		+ " where Passenger.passengerID = Car.passengerID";
            
            ResultSet hasResults =statement.executeQuery(code);
            
            System.out.println("this is here " + hasResults.first());
            if(hasResults.first() )
                return hasResults;
            
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        //return hasResults; 
		return null;
    }
    
//    //Functional Requirement 17: Admin view to see the passengers and projected train and ride
//    public static ResultSet currentPassengers() {
//    	Statement statement= null;
//        try {
//        	statement = connection.createStatement();
//            String code = "SELECT accountID, carNumber FROM Passenger, Car"
//            		+ " where Passenger.passengerID = Car.passengerID";
//            
//            ResultSet hasResults =statement.executeQuery(code);
//            
//            System.out.println("this is here " + hasResults.first());
//            if(hasResults.first() )
//                return hasResults;
//            
//        }catch(Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        //return hasResults; 
//		return null;
//    }
    
    
    
    
    
}