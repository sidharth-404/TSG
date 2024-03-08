
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import main.controller.BookHandler;
public class BookHandlerTest {
    private TestHttpExchange exchange;
    private  BookHandler bookHandler;
    @Before
    public void setUp(){
      exchange=new TestHttpExchange();
      bookHandler=new BookHandler();
    }
    
    @Test
    public void handleGetRequestByIdTest() throws IOException,SQLException{
    exchange.setRequestMethod("GET");
    exchange.setRequestHeader(URI.create("/books/2"));
    bookHandler.handleGetRequestById(exchange);
    assertEquals(200,exchange.getResponseCode());
    assertNotNull(exchange.getResponseBody()); 
    String expectedResponse = "[{\"genreId\":\"gen04\",\"publishDate\":\"2024-02-16\",\"bookname\":\"The fault in our sta\",\"authorId\":\"auth02\",\"bookid\":2}]";
    assertEquals(expectedResponse,exchange.getResponseBody().toString());
   

    }

    @Test
    public void testHandleDeleteRequest() throws IOException,SQLException
    {
        exchange.setRequestMethod("DELETE");
        exchange.setRequestHeader(URI.create("/books/33"));
        bookHandler.handleDeleteRequest(exchange);
        assertEquals(200,exchange.getResponseCode());
    
    }

    @Test
    public void testHandlePostRequest() throws IOException
    {
        exchange.setRequestMethod("POST");
        exchange.setRequestHeader(URI.create("/books"));
        String jsonPayload = "{\"bookId\":33,\"bookName\":\"The fault in our sta\",\"authorId\":\"auth02\",\"genId\":\"gen04\"}";
        InputStream inputStream = new ByteArrayInputStream(jsonPayload.getBytes(StandardCharsets.UTF_8));
        exchange.setRequestBody(inputStream);
        bookHandler. handlePostRequest(exchange);
        assertEquals(201,exchange.getResponseCode());
        assertEquals("Book created successfully",exchange.getResponseBody().toString());
      
    
        


    }
    @Test
    public void TestHandlePutRequest() throws IOException{
    
        exchange.setRequestMethod("PUT");
        exchange.setRequestHeader(URI.create("/books/33"));
        String jsonPayload = "{\"bookName\":\"new33book\",\"authorId\":\"auth02\",\"genId\":\"gen04\"}";
        InputStream inputStream = new ByteArrayInputStream(jsonPayload.getBytes(StandardCharsets.UTF_8));
        exchange.setRequestBody(inputStream);
        bookHandler.handlePutRequest(exchange);
        assertEquals(200,exchange.getResponseCode());
        assertEquals("Book updated successfully",exchange.getResponseBody().toString());
    }

@Test
public void handleGetRequestByIdTestInvalidData() throws IOException {
    exchange.setRequestMethod("GET");
       exchange.setRequestHeader(URI.create("/books/60"));
       bookHandler.handleGetRequestById(exchange);
       assertNotEquals(200, exchange.getResponseCode());
       assertEquals(404,exchange.getResponseCode());
       assertEquals("book not found",exchange.getResponseBody().toString());

}
@Test
public void testInvalidBookIdHandlePostRequest() throws IOException{
    exchange.setRequestMethod("PUT");
    exchange.setRequestHeader(URI.create("/books/60"));
    String jsonPayload = "{\"bookName\":\"new33book\",\"authorId\":\"auth02\",\"genId\":\"gen04\"}";
    InputStream inputStream = new ByteArrayInputStream(jsonPayload.getBytes(StandardCharsets.UTF_8));
    exchange.setRequestBody(inputStream);
    bookHandler.handlePutRequest(exchange);
    assertEquals(404, exchange.getResponseCode());
    assertEquals("Book not found",exchange.getResponseBody().toString());

}

@Test
    public void testInvalidBookIdHandleDeleteRequest() throws IOException{
    exchange.setRequestMethod("DELETE");
    exchange.setRequestHeader(URI.create("/books/44"));
    bookHandler.handleDeleteRequest(exchange);
    assertEquals(404,exchange.getResponseCode());
    }

    }

   
    