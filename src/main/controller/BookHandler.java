package main.controller;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import main.Entity.Book;
import main.Entity.RepairBook;
import main.Exception.SqlHandleException;
import main.Repository.BookRepo;
import main.service.BookServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//import org.jcp.xml.dsig.internal.dom.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

class Utils {
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}

public class BookHandler implements HttpHandler {
    BookRepo bookRepo = new BookRepo();
    BookServiceImpl service = new BookServiceImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            String[] path = exchange.getRequestURI().getPath().split("/");
            if (path.length == 2 || path.length == 3) {
                if (path.length == 2) {
                    handleGetRequest(exchange);
                } else {

                    handleGetRequestById(exchange);

                }
            }
        } else if (method.equals("POST")) {
            String path[]= exchange.getRequestURI().getPath().split("/");
                 if(path.length==2||path.length==3){
                if(path.length==2){
                    handlePostRequest(exchange);
                }
                else{
                     if(path[3].equalsIgnoreCase("managerepair")){

                    try {
                        handlePostRequestRepair(exchange);
                    } catch (IOException | SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                
               
            }
                 }

        } else if (method.equals("PUT")) {
            handlePutRequest(exchange);
        } else if (method.equals("DELETE")) {
            handleDeleteRequest(exchange);
        } else {
            // Handle unsupported methods
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }

    public void handleGetRequest(HttpExchange exchange) throws IOException {
        try {
            List<Book> books = service.getAllBooks();
            if (books == null) {
                sendJsonResponse(exchange, 404, "Books not found");
            } else {
                JSONArray jsonArray = new JSONArray();
                for (Book book : books) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("bookid", book.getBookId());
                    jsonObject.put("bookname", book.getBookName());
                    jsonObject.put("publishDate", book.getPubDate());
                    jsonObject.put("authorId", book.getAuthorId());
                    jsonObject.put("genreId", book.getGenId());

                    jsonArray.put(jsonObject);
                }
                String response = jsonArray.toString();
                sendJsonResponse(exchange, 200, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "Error retrieving books from the database";
            sendResponse(exchange, 400, errorMessage);
        }
    }

    public void handlePostRequest(HttpExchange exchange) throws IOException {
        // Implement logic to create a book in the data layer and send response

        String requestBody = Utils.convertInputStreamToString(exchange.getRequestBody());
        JSONObject json = new JSONObject(requestBody);
        int bookId = json.getInt("bookId");
        String bookName = json.getString("bookName");
        String pubDate = json.getString("pubDate");
        String authorId = json.getString("authorId");
        String genreId = json.getString("genId");

        try {
            // Implement logic to insert new book into the database
            service.createBook(bookId, bookName, LocalDate.parse(pubDate), authorId, genreId);
            String response = "Book created successfully";
            sendResponse(exchange, 201, response);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "Error creating book in the database";
            sendResponse(exchange, 400, errorMessage);
        } catch (SqlHandleException e) {
            sendResponse(exchange, 400, e.getMessage());

        }

    }

    public void handlePutRequest(HttpExchange exchange) throws IOException {
        // Implement logic to update a book in the data layer and send response
        int bookIdUrl = Integer.parseInt(exchange.getRequestURI().getPath().replaceAll("[^\\d]", ""));
        String requestBody = Utils.convertInputStreamToString(exchange.getRequestBody());
        JSONObject json = new JSONObject(requestBody);
        // int bookId = json.getInt("bookId");
        String bookName = json.getString("bookName");
        String authorId = json.getString("authorId");
        String genreId = json.getString("genId");
        try {

            int rowsAffected = service.updateBook(bookIdUrl, bookName, authorId, genreId);
            String response = (rowsAffected > 0) ? "Book updated successfully" : "Book not found";
            sendResponse(exchange, (rowsAffected > 0) ? 200 : 404, response);

        } catch (SQLException e) {
            e.printStackTrace();
            sendJsonResponse(exchange, 500, "internal server error");
        }

    }

    public void handleDeleteRequest(HttpExchange exchange) throws IOException {
        try {
            int bookId = Integer.parseInt(exchange.getRequestURI().getPath().replaceAll("[^\\d]", ""));
            try {

                int rowsAffected = service.deleteBook(bookId);
                String response = (rowsAffected > 0) ? "Book deleted successfully" : "Book not found";
                sendResponse(exchange, (rowsAffected > 0) ? 200 : 404, response);
            } finally {

            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            sendResponse(exchange, 400, "Bad request" + e.getMessage());
        }

    }

    public void handleGetRequestById(HttpExchange exchange) throws IOException {
        int bookId = Integer.parseInt(exchange.getRequestURI().getPath().replaceAll("[^\\d]", ""));

        try {
            Book books = bookRepo.getSingleData(bookId);
            if (books.getBookId() != bookId) {
                sendResponse(exchange, 404, "book not found");
            } else {
                JSONArray jsonArray = new JSONArray();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("bookid", books.getBookId());
                jsonObject.put("bookname", books.getBookName());
                jsonObject.put("publishDate", books.getPubDate());
                jsonObject.put("authorId", books.getAuthorId());
                jsonObject.put("genreId", books.getGenId());
                jsonArray.put(jsonObject);

                String response = jsonArray.toString();

                sendJsonResponse(exchange, 200, response);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            String errorMessage = "Error retrieving books from the database";
            sendResponse(exchange, 500, errorMessage);
        }

    }

    public void handlePostRequestRepair(HttpExchange exchange) throws IOException, SQLException {
       

        List<RepairBook> books = new ArrayList<>();
        books = service.manageRepair();

        try {
            // Implement logic to insert new book into the database
            service.updateStatus(books);

            String response = "repair status updated.";
            sendResponse(exchange, 201, response);

        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "Error creating book in the database";
            sendResponse(exchange, 400, errorMessage);
        } catch (SqlHandleException e) {
            sendResponse(exchange, 400, e.getMessage());

        }
    }

   

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void sendJsonResponse(HttpExchange exchange, int statusCode, String jsonResponse) throws IOException {

        Headers headers = exchange.getResponseHeaders();
        if (headers != null) {
            headers.set("Content-Type", "application/json");
        }

        // Send the response headers with the specified status code
        exchange.sendResponseHeaders(statusCode, jsonResponse.length());

        // Write the JSON response to the response body
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(jsonResponse.getBytes());
        }
    }
}