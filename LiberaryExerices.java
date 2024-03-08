import java.sql.Statement;
import java.util.Scanner;

import main.controller.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LiberaryExerices {
    
   
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
         DbConnection dbConnect=new DbConnection();
        Connection connection=null;
        
        
try{
           
            System.out.println("Connecting to database...");
            connection =dbConnect.getConnection();
            System.out.println("Connected to the database!");

            //1
            System.out.println("enter the choice");

            int n=sc.nextInt();
            switch(n){
         case 1:   
            String q1="select book_id,book_name,pub_date from book b inner join author a on b.author_id=a.author_id where a.author_name=?";
            PreparedStatement stmt=connection.prepareStatement(q1);
            stmt.setString(1, "jack sparrow");
           ResultSet rs=stmt.executeQuery();
           System.out.print("bookId \t bookName \t publishDate \n");
            while(rs.next()){
            int bookId=rs.getInt("booK_id");
            String bookName=rs.getString("book_name");
            String publishDate=rs.getString("pub_date");
            System.out.println(bookId+"\t"+bookName+"\t"+publishDate);
           }

           break;
            

            //2
         case 2:

        String q2="select count(*) as book_count ,g.gen_type from generes g inner join book b on g.gen_id=b.gen_id group by g.gen_id";
        Statement stmt2=connection.createStatement();
        ResultSet rs2=stmt2.executeQuery(q2);
        System.out.print("count \t  genres type \n");
        while(rs2.next())
        {
            int count=rs2.getInt(1);
            String genType=rs2.getString(2);
            System.out.println(count+"\t"+genType);
        }
        break;

     case 3:
        String q3="select book.book_name,author_name,generes.gen_type,if(borrower.br_name is null,'available','not available') as borrower_status from book join author on book.author_id=author.author_id join generes on book.gen_id=generes.gen_id left join borrower on book.book_id=borrower.book_id";
        Statement stmt3=connection.createStatement();
        ResultSet rs3=stmt3.executeQuery(q3);
        System.out.printf("%-30s %-30s %-20s %-15s%n","bookName","authorName","genersType","borrowerStatus");
        while(rs3.next())
        {
            String bookName=rs3.getString(1);
            String authorName=rs3.getString(2);
            String genresType=rs3.getString(3);
            String borrowerStatus=rs3.getString(4);
            System.out.printf("%-30s %-30s %-20s %-15s%n",bookName,authorName,genresType,borrowerStatus);
        }
        break;
        case 4:
             String q4="select * from book where pub_date> ?";
             PreparedStatement stmt4=connection.prepareStatement(q4);
             stmt4.setString(1,"2000-02-11");
             ResultSet rs4=stmt4.executeQuery();
             System.out.printf("%-10s %-20s %-20s %-15s %-20s %n","bookId","publishDate","bookName","authorId","genId");
             while(rs4.next()){
                int bookId = rs4.getInt("book_id");
                String bookName = rs4.getString("book_name");
                String publishDate=rs4.getString("pub_date");
                String authorId=rs4.getString("author_id");
                String genId=rs4.getString("gen_id");
                System.out.printf("%-10s %-20s %-20s %-15s %-20s %n",bookId,publishDate,bookName,authorId,genId);
             }
             break;
             
             case 5:
                    
             String q5="select * from book where book_name like ?";
             PreparedStatement stmt5=connection.prepareStatement(q5);
             stmt5.setString(1,"th%");
             ResultSet rs5=stmt5.executeQuery();
             System.out.printf("%-10s %-20s %-20s %-15s %-20s %n","bookId","publishDate","bookName","authorId","genId");
             while(rs5.next()){
                int bookId = rs5.getInt("book_id");
                String bookName = rs5.getString("book_name");
                String publishDate=rs5.getString("pub_date");
                String authorId=rs5.getString("author_id");
                String genId=rs5.getString("gen_id");
                System.out.printf("%-10s %-20s %-20s %-15s %-20s %n",bookId,publishDate,bookName,authorId,genId);
             }
             break;

        default:
        System.out.println("invlid choice");
        break;

    }


}
catch (SQLException e) {
    e.printStackTrace();
} finally {
    try {
        // Step 4: Close the connection in a finally block to ensure it happens even if an exception occurs
        if (connection != null) {
            connection.close();
            System.out.println("Connection closed.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
}
    }
}
