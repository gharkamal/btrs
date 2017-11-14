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
		}catch(SQLException e){
			e.printStackTrace();
			return;
		}catch(Exception e){
			System.out.println("Failure");
		}

    	
    	

    }








}
