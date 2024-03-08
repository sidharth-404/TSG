package main.service;

import java.sql.SQLException;

public class TestCode {
    public static void main(String args[]) throws SQLException{
        BookServiceImpl service=new BookServiceImpl();
        //System.out.println(service.getSeverityMsag("torn pages"));
     System.out.println(service.manageRepair());
    }
    
}
