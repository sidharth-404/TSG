package main.Repository;

import java.sql.SQLException;

import main.service.BookServiceImpl;

public class TestCode {
    public static void main(String args[]) throws SQLException{
       BookRepo service=new BookRepo();
        System.out.println(service.getRepairBook());
    }
    
}
