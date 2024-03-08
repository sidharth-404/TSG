
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import main.Entity.Book;
import main.controller.DbConnection;

public class JdbcExample {
    public static void main(String[] args) {


        // JDBC variables for opening, closing, and managing the database connection
        Connection connection = null;

        try {
            connection = DbConnection.getConnection();
            System.out.println("Connected to the database!");

            System.out.println("enter the choice");
            System.out.println("1.insert \n 2.display \n 3.update \n 4.delete \n 5.softdelete");
            Scanner sc=new Scanner(System.in);
            int n=sc.nextInt();

          switch(n){
            case 1:
                    Book b1=new Book();
                    System.out.println("enter the book id");
                    b1.setBookId(sc.nextInt());
                    System.out.println("enter the book name");
                    b1.setBookName(sc.next()+sc.nextLine());
                    System.out.println("enter the author id ");
                    b1.setAuthorId(sc.next()+sc.nextLine());
                    System.out.println("enter the genres id");
                    b1.setGenId(sc.next()+sc.nextLine());
                    String sql1="select author_id from author";
                    Statement stmt1=connection.createStatement();
                    ResultSet rs1=stmt1.executeQuery(sql1);
                    if(b1.getBookId()==0){
                        System.out.println("book_id is may not be not null");
                         break;
                    }

                    boolean flag=false;
                    while(rs1.next()){
                        if(rs1.getString("author_id")==b1.getAuthorId()){  
                            flag=true;
                        }
                    }
                    if(flag)
                    {
                        String sql="insert into book (book_id,book_name,author_id,gen_id) values(?,?,?,?)";
                        PreparedStatement statement = connection.prepareStatement(sql);
                       statement.setInt(1, b1.getBookId());
                       statement.setString(2, b1.getBookName());
                       statement.setString(3,b1.getAuthorId());
                       statement.setString(4,b1.getGenId());
     
                int rowsInserted = statement.executeUpdate();
             if (rowsInserted > 0) {
              System.out.println("A new book was inserted successfully!");
                    }
                    }
                    else
                    {
                            System.out.println("Author id is not exist");
                    }
                    
            break;
        case 2:
              String query="select * from book";
              try(Statement stmt=connection.createStatement()){
                ResultSet rs = stmt.executeQuery(query);
                System.out.printf("%-30s %-30s %-20s %-15s%n","bookId","bookName","publishDate","AuthorId","GenersId");
                while (rs.next()) {
                    int bookId = rs.getInt("book_id");
                    String bookName = rs.getString("book_name");
                    String publishDate=rs.getString("pub_date");
                    String authorId=rs.getString("author_id");
                    String genId=rs.getString("gen_id");
                    System.out.printf("%-10s %-20s %-20s %-15s %-20s %n",bookId,bookName,publishDate,authorId,genId);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            break;
            case 3:
          System.out.println("enter the book id to update");
          int id=sc.nextInt();
          System.out.println("enter the book new book name");
          String bookName=sc.next();
          String q1="update book set book_name=? where book_id=?";
          PreparedStatement stmnt1= connection.prepareStatement(q1);
          stmnt1.setString(1, bookName);
          stmnt1.setInt(2,id);
         int updateRow=stmnt1.executeUpdate();  
         if(updateRow>0){
            System.out.println("updated succesfully");
         }       
            break;
        case 4:
        System.out.println("enter the book id to delete");
        int bookId=sc.nextInt();
        String q2 = "DELETE FROM book WHERE book_id=?";
 
        PreparedStatement stmt2 = connection.prepareStatement(q2);
        stmt2.setInt(1, bookId);
         
        int rowsDeleted = stmt2.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A user was deleted successfully!");
        }
          break;

          case 5:
              System.out.println("enter the book_id to delete");
              int book_id=sc.nextInt();
             String q5="update book set is_delete=true where book_id=?";  
             PreparedStatement pstmt=connection.prepareStatement(q5);
             pstmt.setInt(1,book_id); 
             pstmt.executeUpdate();
             break;

            default:System.out.println("invalid choice");
            break;

          }
   



            // Step 3: Perform database operations here...
        //     String query="select book_id,book_name from book";
        //    try(Statement stmt=connection.createStatement()){
        //     ResultSet rs = stmt.executeQuery(query);
        // while (rs.next()) {
        // int bookId = rs.getInt("book_id");
        // String bookName = rs.getString("book_name");
        // System.out.println(bookId + ", " + bookName);
        
        //    }
        //    String q1="delete from book where book_id='"+1+"'";
        //   int x=stmt.executeUpdate(q1);
        // if(x>0)
        // System.out.println("sucessfull");
        // else
        // System.out.println("error occured");
          // stmt.executeUpdate("insert into book (book_id,book_name,author_id,gen_id) values(11,'jefflin','auth01','gen01')" );
        //   String q1="update book set book_name=? where book_id=?";
        //   PreparedStatement statement=  connection.prepareStatement(q1);
        //   statement.setString(1, "evalution");
        //   statement.setInt(2,1);
        //  int updateRow=statement.executeUpdate();  
        //  if(updateRow>0){
        //     System.out.println("added succesfully");
        //  }        

        

            // Example: Display connection status


            

        } catch (SQLException e) {
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
