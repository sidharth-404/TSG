package main.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.Entity.Book;
import main.Entity.RepairBook;
import main.Entity.RepairDetails;
import main.Repository.BookRepo;

public class BookServiceImpl implements BookService {
    BookRepo bookRepo=new BookRepo();
    int budget=50;

    @Override
    public List<Book> getAllBooks() throws SQLException {
        return bookRepo.getData();
    }

    @Override
    public Book getBookById(int bookId) throws SQLException {
        return bookRepo.getSingleData(bookId);
    }

    @Override
    public void createBook(int bookId, String bookName, LocalDate pubDate,String authorId, String genreId) throws SQLException {
        bookRepo.postData(bookId, bookName,pubDate,authorId, genreId);
    }

    @Override
    public int updateBook(int bookId, String bookName, String authorId, String genreId) throws SQLException {
        return bookRepo.updateData(bookId, bookName, authorId, genreId);
    }

    @Override
    public int deleteBook(int bookId) throws SQLException {
        return bookRepo.deleteData(bookId);
    }

public void updateStatus(List<RepairBook> books) throws SQLException{
    for(RepairBook book:books){
        bookRepo.updateDataStatus(book.getBookId(),book.getRepairStatus());
    }
    
}

// @Override
// public int updateRepair(int bookId, String damage, String repairStatus, LocalDate repairStartDate) throws SQLException {
// return bookRepo.updateRepair(bookId,damage,repairStatus,repairStartDate);
// }

    public List<RepairBook> manageRepair() throws SQLException{

       List<RepairBook> books=new ArrayList<>();
       List<RepairBook> bookAdd=new ArrayList<>();
       books=sortBooks(bookRepo.getRepairBook());
       

       int cost =0;
       for(RepairBook book:books){
        
        if(!book.getRepairStatus().contains("completed")){
         // int level=bookRepo.getSeverity(book).getSeverityLevel();
          long daysDifference = ChronoUnit.DAYS.between(book.getRepairStartDate(),LocalDate.now());
          if(daysDifference>20){
            int repairCost =bookRepo.getSeverity(book).getPrice();
             cost+=repairCost;
             if(cost<=budget){
                book.setRepairStatus("completed");
                bookAdd.add(book);
             }
             else
             {
                book.setRepairStatus("progress");
                bookAdd.add(book);
             }
            }
            else{
                book.setRepairStatus("progress");
                bookAdd.add(book);
            }
        }
    }
       return bookAdd;

    }
    
    public int getSeverityLevel(RepairBook book) throws SQLException{
        return bookRepo.getSeverity(book).getSeverityLevel();

    }
 

  public List<RepairBook> sortBooks(List<RepairBook> books) throws SQLException{

    books=bookRepo.getRepairBook();
    books.sort((b1,b2)->{
    
        try {
            return getSeverityLevel(b2)-getSeverityLevel(b1);
        } catch (SQLException e) {
         
            e.printStackTrace();
        }
        return budget;
    
  });
  return books;
}

@Override
public void createRepairBook(int bookId, String damageType, String repairStatus,LocalDate repairStartDate) throws SQLException {
    bookRepo.postRepairData(bookId,damageType,repairStatus,repairStartDate);
}

@Override
public List<RepairDetails> getRepairDetails() throws SQLException {
   return bookRepo.getRepairBookDetails();
}

public void manageRepairs() throws SQLException{

    List<RepairBook> books=new ArrayList<>();
    List<RepairBook> bookAdd=new ArrayList<>();
    books=sortBooks(bookRepo.getRepairBook());
    

    int cost =0;
    for(RepairBook book:books){
     
     if(!book.getRepairStatus().contains("completed")){
      // int level=bookRepo.getSeverity(book).getSeverityLevel();
       long daysDifference = ChronoUnit.DAYS.between(book.getRepairStartDate(),LocalDate.now());
       System.out.println(daysDifference);
       if(daysDifference>20){
        System.out.println(("hai"));
         int repairCost =bookRepo.getSeverity(book).getPrice();
          cost+=repairCost;
          if(cost<=budget){
             book.setRepairStatus("completed");
             bookAdd.add(book);
          }
          else
          {
             book.setRepairStatus("progress");
             bookAdd.add(book);
          }
         }
         else{
            System.out.println(("hai"));
             book.setRepairStatus("progress");
             bookAdd.add(book);
         }
     }
 }
    

 }

}
