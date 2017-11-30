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
        System.out.println("   12 <deptDateTime> <isFull>                            		   // Add new train");
        System.out.println("   13 <name> <order>                                   			   // Add new station");
        System.out.println("   14 <accountID>                                                  // Remove ban");
        System.out.println("   15 <accountID> <wifi>                                           // Update wifi");
        System.out.println("   16                                            // View Passengers (Admin)");
        System.out.println("   17 Quit");
        int opcode=0;
    
        while(run){
            System.out.print("Input: ");    
            try{
                opcode = Integer.parseInt(sc.next());
                switch(opcode){
                    case 1:
                        int accountID1= Integer.parseInt(sc.next()) ;
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
                        int creditCard2= Integer.parseInt(sc.next());
                        boolean success2 = signUp(fullName2,email2,password2,creditCard2);
                        if(success2)
                            System.out.println("-----Sign Up Successful!-----");
                        else 
                            System.out.println("-----Sign Up Failed!-----");
                        break;
                    case 3:
                        int accountID3= Integer.parseInt(sc.next());
                        int startstdID3 =Integer.parseInt(sc.next()) ;
                        int endstID3=Integer.parseInt(sc.next());
                        int seatID3 =Integer.parseInt( sc.next());
                        String dateTime3= sc.next();
                        boolean success3 = reserveTrip(accountID3,startstdID3,endstID3,seatID3,dateTime3);
                        if(success3)
                            System.out.println("-----Reserve Successful!-----");
                        else 
                            System.out.println("-----Reserve Failed!-----");
                        break;
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
                    case 10:
                        int accountID10 = Integer.parseInt(sc.next());
                        String fullName10 = sc.next();
                        String email10 = sc.next();
                        int creditCard10 = Integer.parseInt(sc.next());
                        String newpassword10 = sc.next();
                        boolean success10 = updatePassword(accountID10,fullName10,email10,creditCard10,newpassword10);
                        if(success10)
                            System.out.println("-----Update Password Successful!-----");
                        else 
                            System.out.println("-----Update Password Failed!-----");
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
                    case 15:
                        int accountID15 = Integer.parseInt(sc.next());
                        boolean wifi=sc.nextBoolean();
                        boolean success15 =wifiUpdate(accountID15,wifi);
                        if(success15)
                            System.out.println("-----Update WIFI Successful!-----");
                        else 
                            System.out.println("-----Update WIFI Failed!-----");
                        break;
                    case 16:
                        //int accountID16 = Integer.parseInt(sc.next());
                        ResultSet success16 =currentPassengers();
                        if(success16.first())
                            System.out.println(success16);
                        else 
                            System.out.println("-----Update WIFI Failed!-----");
                        break;
                    case 17:
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
                        +" and password = '"+ password +"' ";
                      
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
        String code = "INSERT INTO Passenger(accountID,startStID,endStID,seatID,dateTime) values(?,?,?,?,?)";
            preparedstatement=connection.prepareStatement(code);
            preparedstatement.setInt(1, accountID);
            preparedstatement.setInt(2, startStdID);
            preparedstatement.setInt(3, endStID);
            preparedstatement.setInt(4, seatID);
            preparedstatement.setString(5, dateTime);
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
    
    
    
}