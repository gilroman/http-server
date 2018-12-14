package gil.server;

import java.io.UnsupportedEncodingException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Response {
    private String startLine = "HTTP/1.1 200 OK";
    private String date = "Date: " + java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
    private String contentType = "Content-Type: text/html; charset=UTF-8";
    private String body = "<!DOCTYPE html><html lang=\"en-us\"><head></head><body><h1>Hello, world!</h1></body></html>";
    private byte[] responseBytes = body.getBytes("UTF-8");
    private String contentLength = "Content-Length: " + responseBytes.length;

    public Response() throws UnsupportedEncodingException {
    }

    public String getStartLine() {
        return startLine;
    }

    public String getDate() {
        return date;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentLength() {
        return contentLength;
    }

    public String getBody() {
        return body;
    }

}
