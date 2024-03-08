package main.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import main.Entity.Book;
import main.Entity.RepairDetails;
import main.Exception.SqlHandleException;

public interface BookService {
     public List<Book> getAllBooks() throws SQLException;
     public Book getBookById(int bookId) throws SQLException;
     public void createBook(int bookId, String bookName,LocalDate pubDate, String authorId, String genreId) throws SQLException;
     public int updateBook(int bookId, String bookName, String authorId, String genreId) throws SQLException;
     public int deleteBook(int bookId) throws SQLException;
    // public int updateRepair(int bookId,String damage,String repairStatus,LocalDate repairStartDate) throws SQLException;
     public void createRepairBook(int bookId,String damageType,String repairStatus,LocalDate repairStartDate)throws SQLException,SqlHandleException;
     public List<RepairDetails> getRepairDetails() throws SQLException;
}
