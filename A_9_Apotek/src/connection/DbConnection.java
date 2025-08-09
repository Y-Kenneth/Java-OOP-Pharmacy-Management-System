package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 
 * @author Yakhe Kenneth Sugiharto - 230712379
 */

public class DbConnection {
    public static Connection CON;
    public static final String URL = "jdbc:mysql://";
    public static final String DBNAME = "a_9_apotek";
    public static final String PATH = "localhost:3306/" + DBNAME;
    
    public Connection makeConnection(){
        System.out.println("Opening Database...");
        try{
            CON = DriverManager.getConnection(URL + PATH, "root", "");
            System.out.println("Success...");
        }catch(Exception e){
            System.out.println("Error Opening Database...");
            System.out.println(e);
        }
        return CON;
    }
    
    public void closeConnection(){
        System.out.println("Closing Database...");
        try{
            CON.close();
            System.out.println("Success...");
        }catch(Exception e){
            System.out.println("Error Closing Database...");
            System.out.println(e);
        }
    }
}
