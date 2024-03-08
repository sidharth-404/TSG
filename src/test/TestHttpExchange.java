import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class TestHttpExchange extends HttpExchange {
    private  String method;
    private  URI uri;
    private  Headers requestHeaders;
    private InputStream requestBody;
    private Headers responseHeaders;
    private int responseCode;
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private long responseLength;
    public TestHttpExchange(){

    }
   
    public TestHttpExchange(String method,URI uri,Headers requestHeaders,InputStream requestBody,Headers responseHeaders) {
        this.method = method;
        this.uri=uri;
        this.requestHeaders=requestHeaders;
        this.requestBody=requestBody;
        this.responseHeaders=responseHeaders;
    }
    

    @Override
    public String getRequestMethod() {
        return method;
    }
    public void setRequestMethod(String method){
        this.method=method;
    }
    public void setRequestHeader(URI uri )
    {
        this.uri=uri;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRemoteAddress'");
    }

    @Override
    public int getResponseCode() {
        // TODO Auto-generated method stub
        return responseCode;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLocalAddress'");
    }

    @Override
    public String getProtocol() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProtocol'");
    }

    @Override
    public Object getAttribute(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAttribute'");
    }

    @Override
    public void setAttribute(String name, Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAttribute'");
    }

    @Override
    public void setStreams(InputStream i, OutputStream o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStreams'");
    }

    @Override
    public HttpPrincipal getPrincipal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrincipal'");
    }

    @Override
    public Headers getRequestHeaders() {
        // TODO Auto-generated method stub
        return requestHeaders;
        //throw new UnsupportedOperationException("Unimplemented method 'getRequestHeaders'");
    }

    @Override
    public Headers getResponseHeaders() {
        // TODO Auto-generated method stub
       // throw new UnsupportedOperationException("Unimplemented method 'getResponseHeaders'");
       return responseHeaders;
    }

    @Override
    public URI getRequestURI() {
        // TODO Auto-generated method stub
        return uri;
    }

    @Override
    public HttpContext getHttpContext() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHttpContext'");
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

    @Override
    public InputStream getRequestBody() {
        // TODO Auto-generated method stub
       // throw new UnsupportedOperationException("Unimplemented method 'getRequestBody'");
       return requestBody;
    }
    public void setRequestBody(InputStream requestBody) {
        // TODO Auto-generated method stub
       // throw new UnsupportedOperationException("Unimplemented method 'getRequestBody'");
       this.requestBody=requestBody;
    }

    @Override
    public OutputStream getResponseBody() {
        return outputStream;
        
    }
     
    @Override
    public void sendResponseHeaders(int responseCode, long responseLength) throws IOException {
        this.responseCode= responseCode;
        this.responseLength = responseLength;
        this.responseHeaders = getResponseHeaders();
    }
}
