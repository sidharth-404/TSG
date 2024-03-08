package main.Repository;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    public static Connection getConnection(){
        String jdbcUrl = "jdbc:mysql://localhost:3306/liberary";
        String username = "root";
        String password = "";
        Connection connection = null;
        try {
            // Step 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Step 2: Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        }
        catch(Exception e ){
            e.printStackTrace();
        }
           return connection;
        }
    
    
}
