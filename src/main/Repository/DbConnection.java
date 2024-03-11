package main.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;

public class DbConnection {
    public static Connection getConnection(){
        ResourceBundle rd
            = ResourceBundle.getBundle("resource.system", Locale.US);

        String jdbcUrl = rd.getString("url");
        String username = rd.getString("userName");
        String password = rd.getString("password");
        String driverClass=rd.getString("driver");
        Connection connection = null;
        try {
            // Step 1: Register JDBC driver
            Class.forName(driverClass);
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
