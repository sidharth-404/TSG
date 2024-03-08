package main.Api;
import com.sun.net.httpserver.HttpServer;

import main.controller.BookHandler;
import main.controller.RepairController;

import java.io.IOException;
import java.net.InetSocketAddress;
 
public class ApiServer {
 
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/books", new BookHandler());
        server.createContext("/books/repair",new RepairController());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8000");
    }
}