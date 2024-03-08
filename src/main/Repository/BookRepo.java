package main.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.Entity.Book;
import main.Entity.RepairBook;
import main.Entity.RepairDetails;
import main.Entity.RepairDummy;
//import main.Entity.DummyBook;
//import main.Entity.RepairDummy;
import main.Exception.SqlHandleException;

public class BookRepo {

    public void postData(int bookId, String bookName,LocalDate pubDate, String authorId, String genreId) throws SQLException,SqlHandleException{
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();
            System.out.println("Connected to the database!");
            String checkIfExistsSql = "SELECT * FROM book WHERE book_id=?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkIfExistsSql)) {
                checkStmt.setInt(1, bookId);
                ResultSet rs = checkStmt.executeQuery();
    
                if (rs.next()) {
                    throw new SqlHandleException(" Book with ID " + bookId + " already exists. Skipping insertion.");
                }
            }
            if(pubDate.isAfter(LocalDate.now())){
                throw new SqlHandleException("publish date is after now");
            }
            String sql = "insert into book(book_id,book_name,pub_date,author_id,gen_id) values(?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);
            statement.setString(2, bookName);
            statement.setDate(3, java.sql.Date.valueOf(pubDate));
            statement.setString(4, authorId);
            statement.setString(5, genreId);
            statement.executeUpdate();
        }

      finally {
            try {
                if (connection != null)
                    connection.close();
                System.out.println("connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Book> getData() throws SQLException {
        Connection connection = null;
        List<Book> books = new ArrayList<>();
        try {
            connection = DbConnection.getConnection();
            System.out.println("Connected to the database!");
            String query = "select * from book";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String bookName = rs.getString("book_name");
                Date publishDate = rs.getDate("pub_date");
                String authorId = rs.getString("author_id");
                String genId = rs.getString("gen_id");
                books.add(new Book(bookId, bookName, publishDate.toLocalDate(), authorId, genId));
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (connection != null)
                    connection.close();
                System.out.println("connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return books;
    }

    public int updateData(int bookId, String bookName, String authorId, String genreId) throws SQLException {
        Connection connection = null;
        int updateRow = 0;
        try {
            connection = DbConnection.getConnection();

            String q1 = "update book set book_name=?,author_id=?,gen_id=? where book_id=?";
            PreparedStatement stmnt1 = connection.prepareStatement(q1);
            stmnt1.setString(1, bookName);
            stmnt1.setString(2, authorId);
            stmnt1.setString(3, genreId);
            stmnt1.setInt(4, bookId);
            updateRow = stmnt1.executeUpdate();
        } 
        finally {
            try {
                if (connection != null)
                    connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return updateRow;
    }

    public int deleteData(int bookId) throws SQLException {
        Connection connection = null;
        int row = 0;
        try {
            connection = DbConnection.getConnection();
            String q2 = "DELETE FROM book WHERE book_id=?";
            PreparedStatement stmt2 = connection.prepareStatement(q2);
            stmt2.setInt(1, bookId);
            row = stmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return row;

    }

    public Book getSingleData(int id) throws SQLException {
        Connection connection = null;
        Book books = new Book();
        try {
            connection = DbConnection.getConnection();
            System.out.println("Connected to the database!");
            String query = "select * from book where book_id=?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String bookName = rs.getString("book_name");
                Date publishDate = rs.getDate("pub_date");
                String authorId = rs.getString("author_id");
                String genId = rs.getString("gen_id");
                books = new Book(bookId, bookName, publishDate.toLocalDate(), authorId, genId);
            }

        }  finally {
            try {
                if (connection != null)
                    connection.close();
                System.out.println("connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       return books;
    }



    public void updateDataStatus(int bookId, String repair_status) throws SQLException {
        Connection connection = null;
        try {
            connection = DbConnection.getConnection();

            String q1 = "update bookrepair set repair_status=? where book_id=?";
            PreparedStatement stmnt1 = connection.prepareStatement(q1);
            stmnt1.setString(1, repair_status);
            stmnt1.setInt(2, bookId);
            stmnt1.executeUpdate();
        } 
        finally {
            try {
                if (connection != null)
                    connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public List<RepairBook> getRepairBook() throws SQLException{
        Connection connection = null;
       List<RepairBook> repairBooks = new ArrayList<>();
        try {
            connection = DbConnection.getConnection();
            System.out.println("Connected to the database!");
            String query = "select * from bookrepair";
            
            PreparedStatement stmt = connection.prepareStatement(query);
       
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String damageType = rs.getString("damage_type");
                String repairStatus = rs.getString("repair_status");
                Date repairStartDate=rs.getDate("repair_startDate");
                repairBooks.add(new RepairBook(bookId,damageType,repairStatus,repairStartDate.toLocalDate()));
            }

        }  finally {
            try {
                if (connection != null)
                    connection.close();
                System.out.println("connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       return repairBooks;
    }

    public List<RepairDetails> getRepairBookDetails() throws SQLException{
        Connection connection = null;
       List<RepairDetails> repairBooks = new ArrayList<>();
        try {
            connection = DbConnection.getConnection();
            System.out.println("Connected to the database!");
            String query = "select b.book_id,b.book_name,r.damage_type,r.repair_status from book b join bookrepair r on b.book_id=r.book_id;";
            
            PreparedStatement stmt = connection.prepareStatement(query);
       
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt(1);
                String bookName = rs.getString(2);
                String damage = rs.getString(3);
                String repairStatus=rs.getString(4);
                repairBooks.add(new RepairDetails(bookId,bookName,damage,repairStatus));
            }

        }  finally {
            try {
                if (connection != null)
                    connection.close();
                System.out.println("connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       return repairBooks;
    }



    public RepairDummy getSeverity(RepairBook book) throws SQLException{
        Connection connection = null;
        RepairDummy repairData=new RepairDummy();
        try{
        connection = DbConnection.getConnection();
        String severity="select severity_level,repair_cost from damages where damage_type=?";
        PreparedStatement stmt=connection.prepareStatement(severity);
        stmt.setString(1,book.getDamageType());
       ResultSet rs=stmt.executeQuery();
       while(rs.next()){
        int severityLevel=rs.getInt("severity_level");
        int cost=rs.getInt("repair_cost");
          repairData=new RepairDummy(severityLevel,cost);
       }

    }  finally {
        try {
            if (connection != null)
                connection.close();
            System.out.println("connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return repairData;
}
public void postRepairData(int bookId, String damageType,String repairStatus,LocalDate repairStartDate) throws SQLException,SqlHandleException{
    Connection connection = null;
    try {
        connection = DbConnection.getConnection();
        System.out.println("Connected to the database!");
        String sql1="select * from book where book_id=?";
        try(PreparedStatement stmt=connection.prepareStatement(sql1)){
            stmt.setInt(1,bookId);
            ResultSet rs=stmt.executeQuery();
            
            if(rs.next()==false){
                throw new SqlHandleException("book is not available");
            }
    
        }

        String sql = "insert into bookrepair(book_id,damage_type,repair_status,repair_startDate) values(?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, bookId);
        statement.setString(2,damageType);
        statement.setString(3,repairStatus);
        statement.setDate(4,java.sql.Date.valueOf(repairStartDate));
        statement.executeUpdate();
        
    }

  finally {
        try {
            if (connection != null)
                connection.close();
            System.out.println("connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


}
