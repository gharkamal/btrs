import java.io.FileInputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
                        boolean success1= login(accountID1,password1, false);
                        if(success1)
                        {
                            System.out.println("\n -----Login Successful!-----");
                        	   System.out.println("1 Reserve Trip");
                        	   System.out.println("2 Update user information");
                        	   System.out.println("3 Change trip destination");
                        	   System.out.println("4 Change Password");
                        	   System.out.println("5 Purchase Wifi");
                        	   System.out.println("6 Cancel Reservation");
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
                        	    		   possibleCars(trainIDSelected);
                        	    		   System.out.println("Select Car #: ");
                        	    		   int carID = sc.nextInt();
                        	    		   Boolean seatCheck = showPossibleSeat(trainIDSelected, carID);
                        	    		   
                        	    		   String seatSelected = "";
                        	    		   if(seatCheck == true)
                        	    		   {
                        	    			   System.out.println("Input Desired Seat: ");
                        	    			   seatSelected = sc.next();
                        	    		   }
                        	    		   else {
                        	    			   break;
                        	    		   }
                                    boolean success3 = reserveTrip(accountID1,stopStation,seatSelected, false, carID, trainIDSelected);
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
                        		   case 3: //Change Trip times
                        			   boolean check = checkIfAccIsPass(accountID1);
                        			   if( check == true) {
	                        			   showPossibleTripTimes(); //show user all the possible trip times                
		                    	    		   System.out.println("Input desired TrainID: <TrainID> ");
		                    	    		   int trainIDSelected1 = sc.nextInt();  //store TrainID here that user selects
		                    			   //Show all possible trains avaliable and then tell user to select which one
		                    	    		   System.out.println("\n");
		                    	    		   showPossibleStation();
		                    	    		   System.out.println("Input desired Stop Station: <StationID> ");                     	    		  
		                    	    		   int stopStation1 = sc.nextInt();   //only gets first token if more than one word    
		                    	    		   possibleCars(trainIDSelected1);
	                        	    		   System.out.println("Select Car #: ");
	                        	    		   int carID1 = sc.nextInt();
		                    	    		   Boolean seatCheck1 = showPossibleSeat(trainIDSelected1, carID1);
		                    	    		   String seatSelected1 = "";
		                    	    		   if(seatCheck1 == true)
		                    	    		   {
		                    	    			   System.out.println("Input Desired Seat: ");
		                    	    			   seatSelected1 = sc.next();
		                    	    		   }
		                    	    		   else {
		                    	    			   break;
		                    	    		   }
		                    	    		   updateTrip(accountID1, stopStation1, seatSelected1, trainIDSelected1, carID1);
                        			   } else {
                        				   System.out.println("Please make a reservation First");
                        			   }
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
                        			  // boolean wifi=sc.nextBoolean();
                        			   boolean success15 =wifiUpdate(accountID1,true);
                        			   if(success15)
                                         System.out.println("-----Successful Purchased Wifi!-----");
                        			   else 
                        				    System.out.println("-----Update WIFI Failed! Make a reservation first!-----");
                        			   break;
                        		   case 6:
                        			   deleteReservation(accountID1);
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
                    case 2: //Register for Account
                        System.out.println("Enter: <First Name> <Last Name> <age> <email> <password> <Credit Card #>");
                        String firstName=sc.next();
                        String lastName = sc.next();
                        int age = sc.nextInt();
                        String email = sc.next();
                        String password =sc.next();
                        String creditCard = sc.next();
                        System.out.println(creditCard);
                        boolean success2 = signUp(firstName,lastName, email,password,creditCard, age);
                        if(success2)
                            System.out.println("-----Sign Up Successful!-----");
                        else 
                            System.out.println("-----Sign Up Failed!-----");
                        break;
                    case 3://3  Reserve without Account
                    	   System.out.println("Enter: <First Name> <Last Name> <age> <Credit Card #>");
                    	   String firstNameWO = sc.next();
                    	   String lastNameWO = sc.next();
                    	   int ageWO = sc.nextInt();
                    	   long creditCardWO = sc.nextLong();
                    	   
                    	   showPossibleTripTimes(); //show user all the possible trip times                
	                    System.out.println("Input desired TrainID: <TrainID> ");
	                    int trainIDSelected = sc.nextInt();  //store TrainID here that user selects
	                     //Show all possible trains avaliable and then tell user to select which one
	      	    		   System.out.println("\n");
	      	    		   showPossibleStation();
	      	    		   System.out.println("Input desired Stop Station: <StationID> ");                     	    		  
	      	    		   int stopStation = sc.nextInt();   //only gets first token if more than one word 
	      	    		   possibleCars(trainIDSelected);
	      	    		   System.out.println("Select Car #: ");
	      	    		   int carID = sc.nextInt();
	      	    		   Boolean seatCheck = showPossibleSeat(trainIDSelected, carID);
	      	    		   String seatSelected = "";
	      	    		   if(seatCheck == true)
	      	    		   {
	      	    			   System.out.println("Input Desired Seat: ");
	      	    			   seatSelected = sc.next();
	      	    		   }
	      	    		   else {
	      	    			   break;
	      	    		   }
	                  boolean success3 = reserveTrip(0,stopStation,seatSelected, false, carID, trainIDSelected);
	                  if(success3) {
	                       System.out.println("-----Reservation Successful!-----");
//	                  	  ResultSet id = getPassengerID(0, stopStation, seatSelected, trainIDSelected, carID );
//	                  	  System.out.println("Your passenger ID is : " + id);

	                  }
	                  else {
	                       System.out.println("-----Reservation Failed!-----");
	                       break;
	                   }
	                  
	      			  break;

                    case 4: //admin 
                    	   System.out.println("Enter Admin Login: <AccountID> <password>");
                    	   int accountID= Integer.parseInt(sc.next()) ;
                           //System.out.print(accountID1);
                           String passw = sc.next();
                           //System.out.print(password1);
                           boolean success = login(accountID,passw, true);
                           if(success)
                           {
                               System.out.println("\n -----Login Successful!-----");
                               System.out.println("1 Ban Account Holder");
                               System.out.println("2 Remove Ban");
                        	   	  System.out.println("3 Average Ages");
                        	   	  System.out.println("4 passengers under 18");
                        	   	  System.out.println("5 All passengers going to LA");
                        	   	  System.out.println("6 Add new station");
                        	   	  System.out.println("7 Add new Train");
                        	   	  System.out.println("8 Archive Passengers from today");
                        	      try{
                                      opcode = Integer.parseInt(sc.next());
                                      switch(opcode){
                                      	case 1:
                                      		System.out.println("Enter: <accountID>");
                                      		int accountID11 = Integer.parseInt(sc.next());
                                      		banCustomer(accountID11);
                                      		break;
                                      	case 2:
                                      		System.out.println("Enter: <accountID>");
                                      		int accountID12 = Integer.parseInt(sc.next());
                                      		removeBan(accountID12);
                                      		break;
                                      	case 3:
                                      		showAverageAges();
                                      		break;
                                      	case 4:
                                      		showMinors();
                                      		break;
                                      	case 5:
                                      		going_to_LA();
                                      		break;
                                      	case 6:
                                      		System.out.println("Enter: <Station Name> <orderNumber>");
                                      		String name13 = sc.next();
                                              int order13 = sc.nextInt();
                                              boolean success13 = addStation(name13,order13);
                                              if(success13)
                                                 System.out.println("-----Station Successfully added!-----");
                                              else 
                                                 System.out.println("-----Add Station Failed!-----");
                                              break;
                                      	case 7:
                                      		System.out.println("Enter: < Departure Time>");
                                      		String deptTime12 = sc.next();
                                      		boolean success12 = addTrain(deptTime12,false);
                                      		if(success12)
                                      			System.out.println("-----Add Train Successful!-----");
                                      		else 
                                            	System.out.println("-----Add Train Failed!-----");
                                      		break; 
                                      	case 8:                     
                                      		callArchiveProcedure();
                                      		break;

                                      }
                        	      } catch(Exception e){
                                      System.out.println("-----Not a Valid Command!-----");   
                                      System.exit(0);
                                  }
                           }
                           else {
                        	   	System.out.println("\n -----Login Failed!-----");
                        	   	
                           }
                        break;
                    case 5: //recover account ID number 
                    		System.out.println("Enter <first Name> <last Name> <Email> <password> : ");
                    		String firstN = sc.next();
                    		String lastN = sc.next();
                    		String email2 = sc.next();
                    		String passwor = sc.next();
                    		int valid = recoverAccountID(firstN, lastN, email2, passwor);
                    		if(valid != 0)
                    		{
                    			System.out.println("Your accountID is: " + valid);
                    		}
                    		else {
                    			System.out.println("You are not an account Holder. Please Register");
                    		}
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
    public static void showAverageAges() {
    	Statement statement=null;
    	ResultSet hasResults= null;
		try{
			statement= connection.createStatement();
			String code = " USE BTRS";
			statement.execute(code);
			code = " select AVG(age) "
				  +" from Account_Holder"
				  +" where age >18 "
				  +" and EXISTS( select * from Passenger where Passenger.accountID= Account_Holder.accountID )";
			hasResults=statement.executeQuery(code);
			hasResults.first();
			int averageAge= hasResults.getInt("AVG(age)");
			code = "select MAX(age) "
				 + "from Account_Holder "
				 + "where EXISTS( select * from Passenger where Passenger.accountID= Account_Holder.accountID ) ";
			hasResults=statement.executeQuery(code);
			hasResults.first();   
			int oldest = hasResults.getInt("MAX(age)");
			code = " select MIN(age) "
			     + " from Account_Holder"
			     + " where EXISTS( select * from Passenger where Passenger.accountID= Account_Holder.accountID )";
			hasResults=statement.executeQuery(code);
			hasResults.first();   
			int youngest = hasResults.getInt("MIN(age)");
			System.out.println("The Youngest Passenger with Account is "+youngest);
			System.out.println("The Eldest Passenger with Account is "+ oldest);
			System.out.println("Average Age of Passenger with Account Holders is " + averageAge);
		}catch(Exception e){
            e.printStackTrace();				
		}
	}
    public static void showMinors(){
    	Statement statement=null;
    	ResultSet hasResults= null;
    	try{
			statement= connection.createStatement();
			String code = " USE BTRS";
			statement.execute(code);
			code = " select B.accountID, B.firstName,B.lastName, passengerID "
				 + " from (select * from Account_Holder where age<18)B LEFT JOIN "
				 + " Passenger on B.accountID = Passenger.accountID"
				 + " order by B.accountID ";
			hasResults=statement.executeQuery(code);
			System.out.println("Status of Passenger under age 18");
			while(hasResults.next()){
				int accountID= hasResults.getInt("accountID");
				String firstName= hasResults.getString("firstName");
				String lastName= hasResults.getString("lastName");
				int passengerID = hasResults.getInt("passengerID");
				System.out.println(accountID + "  " +firstName + "  " +lastName + "  "+passengerID );				
			}
    	}catch(Exception e){
    	    e.printStackTrace();	
    		
    	}

    }
    public static void going_to_LA(){
        Statement statement=null;
        ResultSet hasResults= null;
        
       try{
            statement= connection.createStatement();
            String code = " USE BTRS";
            statement.execute(code);
            code =" select accountID, firstName, lastName"
                + " from Account_Holder "
                + " where exists( select * from Passenger"
                + "    where Account_Holder.accountID = Passenger.accountID and "
                + "endStID = ( select stationID from Station where name='Los Angeles Union' ))"
                + " order by accountID";
            hasResults = statement.executeQuery(code);
            System.out.println("-----Persons going to Los Angelos-----");
            while(hasResults.next()){
                int accountID= hasResults.getInt("accountID");
                String firstName= hasResults.getString("firstName");
                String lastName= hasResults.getString("lastName");
                System.out.println(accountID + "  " +firstName + "  " +lastName  );                
            }        
            code = " select 'Miscellaneous' as name "
                  +" from Passenger "
                  +" where accountID=0 AND endStID= ( select stationID from Station where name='Los Angeles Union') ";
            hasResults = statement.executeQuery(code);
            while(hasResults.next()){
                String more = hasResults.getString("name");
                System.out.println(more);
            }        

       }catch(Exception e){
            e.printStackTrace();    
       }    
   }
    
	// Functional requirement 1
    public static boolean login(int accountID,String password, Boolean admin){
        Statement statement = null;
        ResultSet hasResults = null;
        try{
        	if(admin == false)
        	{
            statement = connection.createStatement();
            String code = "select *"
                        + "from Account_Holder \n"
                        + "where accountID ="+accountID
                        +" and password = "+ password ;
                      
             hasResults =statement.executeQuery(code);
        	}else {
        		statement = connection.createStatement();
                String code = "select *"
                            + "from Account_Holder \n"
                            + "where accountID ="+accountID
                            +" and password = "+ password + " and admin = true";
                          
                 hasResults =statement.executeQuery(code);
        	}
            if(hasResults.first() )
                return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
    // Functional requirement 2
    public static Boolean signUp(String firstName,String lastName, String email,String password,String creditCard, int age ){
        PreparedStatement preparedstatement=null;
        PreparedStatement getID=null;
        try{
            String code= "INSERT INTO Account_Holder(firstName,lastName, email,password,creditCard, age) "
                    +    "values(?,?,?,?,?, ?);  ";
            preparedstatement=connection.prepareStatement(code);
            
            preparedstatement.setString(1, firstName);
            preparedstatement.setString(2, lastName);
            preparedstatement.setString(3, email);
            preparedstatement.setString(4, password);
            preparedstatement.setString(5, creditCard);
            preparedstatement.setInt(6, age);
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
    

    
    //check if account holder is a passenger
    public static boolean checkIfAccIsPass(int accountID)
    {
    	 Statement statement = null;
         try{
             statement = connection.createStatement();
             String code = "select *"
                         + "from passenger "
                         + "where accountID ="+accountID;      
             ResultSet hasResults =statement.executeQuery(code);
             if(hasResults.first() )
                 return true;
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

    
    //ask user to select car
    public static void possibleCars(int trainID)
    {
    		PreparedStatement preparedstatement=null;
		try {
			String code = "select carNumber from Car WHERE trainID = ? group by carNumber";
			preparedstatement=connection.prepareStatement(code);
			 preparedstatement.setInt(1, trainID);
			ResultSet result = preparedstatement.executeQuery();
			int carID = 0;
    		   System.out.println("\n Car #");
    		   if (result.next()) {
    				carID = result.getInt("CarNumber"); 
    				System.out.println(carID);
            }
    		   else {
    			   System.out.println("All booked");
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
    public static boolean showPossibleSeat(int trainID, int carID)
    {
    		PreparedStatement preparedstatement=null;
    		try {
    			String code = "select seatID from Car, Train where Car.trainID = ? and Train.trainID = ? and carNumber = ?"; //check if seat is null
    			preparedstatement=connection.prepareStatement(code);
    		    preparedstatement.setInt(1, trainID);
    		    preparedstatement.setInt(2, trainID);
    		    preparedstatement.setInt(3, carID);
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
    public static boolean reserveTrip(int accountID, int endStID, String seatID, boolean wifi, int carID, int trainID)
    {
        PreparedStatement preparedstatement=null;
        try{
        	    String code = "INSERT INTO Passenger(accountID,endStID,wifi) values(?,?,?)";
            preparedstatement=connection.prepareStatement(code);
            preparedstatement.setInt(1, accountID);
            preparedstatement.setInt(2, endStID);
            preparedstatement.setBoolean(3, wifi);
            int hasChanged = preparedstatement.executeUpdate();
            PreparedStatement preparedstatement1=null;
            String maxCode = "select Max(passengerID) from Passenger";
            preparedstatement1=connection.prepareStatement(maxCode);
            ResultSet maxPass = preparedstatement1.executeQuery();
            int passID = 0;
            if (maxPass.next()) {
   				passID = maxPass.getInt(1); 
            }
   			
            if(hasChanged ==1) {
            	PreparedStatement seater =null;
            		String seatSet = "UPDATE Car SET passengerID = ? where carNumber = ? and seatID = ? and trainID = ?";
            			
            		seater=connection.prepareStatement(seatSet);
            		seater.setInt(1, passID);
            		seater.setInt(2, carID);
            		seater.setString(3, seatID);
            		seater.setInt(4, trainID);
                 int hasChanged1 = seater.executeUpdate();
                 if(hasChanged1 == 1) {
                    		System.out.println("Your Passenger ID is: " + passID);
                    		return true;
                 }
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
        
    }

    //Functional requirement 4: Change/update train destination: Update a trip destination.  FOR ACCOUNT HOLDER
    public static boolean updateTrip(int accountID, int endStID, String seatSelected1, int trainID, int carID)
    {
        PreparedStatement preparedstatement=null;
        try{
            String code = "UPDATE Passenger "
                         + "SET endStID = ? "
                         +"WHERE accountID = ?";
            preparedstatement=connection.prepareStatement(code);
            preparedstatement.setInt(1, endStID);
            preparedstatement.setInt(2, accountID);
            int hasChanged = preparedstatement.executeUpdate();
            if(hasChanged ==1)
            		setOldSeatNull(accountID, carID, trainID);
            		changeSeat(accountID, seatSelected1, trainID, endStID, carID);
                return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public static ResultSet getPassengerID(int accountID, int endStrID, String seatID, int carID, int trainID)
    {
    		PreparedStatement statement = null;
    		if(accountID != 0)
    		{
	        try{
	            String code = "select passengerID from passenger where accountID =  ?";
	            statement = connection.prepareStatement(code);
	            statement.setInt(1, accountID);  
	            ResultSet hasResults =statement.executeQuery();
	            if(hasResults.next() )
	                return hasResults;
	        }catch(Exception e){
	            e.printStackTrace();
	            return null;
	        }
    		}
    		else {
    			//if passenger doesn't have an account 
    			try{
    				
    	            String code = "select Passenger.passengerID from passenger, car where accountID =  0 and  seatID = ? and trainID = ? and carNumber = ? and Car.passengerID = Passenger.passengerID ";
    	            statement = connection.prepareStatement(code);
    	            statement.setString(1, seatID); 
    	            statement.setInt(2, trainID); 
    	            statement.setInt(3, carID); 
    	            ResultSet hasResults =statement.executeQuery();
    	            int passID = 0;
                	  while(hasResults.next())
                	  {
                		  passID = hasResults.getInt("passengerID");
                	  }
                	  System.out.println("Your passenger ID #: " + passID);
    	        }catch(Exception e){
    	            e.printStackTrace();
    	            return null;
    	        }		
    		}
    		return null;
    }
    
    public static boolean setOldSeatNull(int accountID, int carID, int trainID)
    {
    	 PreparedStatement preparedstatement=null;
         try{
             String code = "UPDATE Car "
                          + "SET passengerID = NULL "
                          +"WHERE passengerID = (select passengerID from passenger where accountID = ?) and carNumber = ? and trainID = ? ";
             preparedstatement=connection.prepareStatement(code);
             preparedstatement.setInt(1, accountID);
             preparedstatement.setInt(2, carID);
             preparedstatement.setInt(3, trainID);
             int hasChanged = preparedstatement.executeUpdate();
             if(hasChanged ==1)
                 return true;
         }catch(Exception e){
             e.printStackTrace();
             return false;
         }
         return false;
    }
    //Requirement 7 : Change   seat:   Update   a   trip   seat.
    public static boolean changeSeat(int accountID, String updateSeat, int trainID, int endStID, int carID){
        PreparedStatement preparedstatement=null;
        try{
        		int passengerID = 0;
        		ResultSet id = getPassengerID(accountID, endStID, null, carID, trainID);
        		passengerID = id.getInt("passengerID");
        		System.out.println("MY PASSENGER ID : " + passengerID );
            String code = "UPDATE car "
                         +"SET passengerID = ?  "
                         +"WHERE seatID = ? and trainID = ? and carNumber = ?";
            preparedstatement = connection.prepareStatement(code);
            preparedstatement.setInt(1,passengerID);
            preparedstatement.setString(2,updateSeat);
            preparedstatement.setInt(3,trainID);
            preparedstatement.setInt(4,carID);
            int hasChanged = preparedstatement.executeUpdate();
            if(hasChanged>0)
                return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
    
    //Recover Account ID
    public static int recoverAccountID(String firstN,String lastN, String email2, String passwor)
    {
    		PreparedStatement preparedstatement=null;
    		  try{
    			  String code = "select accountID from account_Holder where firstName = ? and"
    					  + " lastName = ? and email = ? and password = ?";
    			  preparedstatement = connection.prepareStatement(code);
    	           preparedstatement.setString(1,firstN);
    	           preparedstatement.setString(2,lastN);
    	           preparedstatement.setString(3,email2);
    	           preparedstatement.setString(4,passwor);
    	           
    	           ResultSet result = preparedstatement.executeQuery();
    	           if(result.next())
    	           {
    	        	   	return result.getInt("accountID");
    	           }
    		  }catch(Exception e){
    	            e.printStackTrace();
    	            return 0;
    		  }
    		return 0;
    }

    //Requirement 8: Delete reservation:   Delete   a   trip.  Cancellation
      public static boolean deleteReservation(int accountID){
        PreparedStatement preparedstatement=null;
        try{
            String code = "DELETE FROM Passenger WHERE accountID = ?";
            preparedstatement = connection.prepareStatement(code);
            preparedstatement.setInt(1,accountID);
            int hasChanged = preparedstatement.executeUpdate();
            if(hasChanged==1) {
            		System.out.print("Reservation successfully cancelled");
                 return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        System.out.println("Unsuccessful Cancellation");
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


    //call procedure 
    public static void callArchiveProcedure(){
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		Calendar cal = Calendar.getInstance();
    		cal.add(Calendar.DATE, 1);
    		String formatted = sdf.format(cal.getTime());
    		System.out.println(formatted);
    		//System.out.println(dateFormat.format(now));
    		PreparedStatement preparedstatement= null;
    		try {
    				String code = "{CALL archivePassengers('" + formatted.toString() +"')}";
    				preparedstatement = connection.prepareStatement(code);
    				//preparedstatement.setString(1,formatted.toString());
				int hasResult = preparedstatement.executeUpdate();
		
					System.out.println("Archieving Successful"); 
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Archieving unsuccessful"); 
				e.printStackTrace();
			}
    }

    
    
    
    
}