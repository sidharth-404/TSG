

import static org.junit.Assert.assertEquals;


import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Test;

import main.Entity.DummyBook;
import main.Repository.BookRepo;

public class BookRepoTest {
    
    @Test
    public void testGetData() throws SQLException
    {
       BookRepo bookrepo=new BookRepo();
       DummyBook book=new DummyBook();
       DummyBook book1=new DummyBook(1,"evalution",LocalDate.parse("2024-02-16"),"auth01","gen01");
       book=bookrepo.getSingleData(1);
       assertEquals(book1.getBookId(),book.getBookId());
       assertEquals(book1.getBookName(),book.getBookName());
       assertEquals(book1.getAuthorId(),book.getAuthorId());
       assertEquals(book1.getGenreId(),book.getGenreId());


    }

    
}
