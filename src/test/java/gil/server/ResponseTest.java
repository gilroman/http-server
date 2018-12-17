package gil.server;

import java.io.UnsupportedEncodingException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ResponseTest {
    Response response = new Response();

    public ResponseTest() throws UnsupportedEncodingException {
    }

    @Test
    public void shouldHaveAStartLine() {
        assertEquals("HTTP/1.1 200 OK", response.getStartLine());
    }

    @Test
    public void shouldHaveADateHeader() {
        String date = response.getDate();
        assertTrue(date.contains("Date"));
    }

    @Test
    public void shouldHaveAContentType() {
        assertEquals("Content-Type: text/html; charset=UTF-8", response.getContentType());
    }

    @Test
    public void shouldHaveABody() {
        assertEquals("<!DOCTYPE html><html lang=\"en-us\"><head></head><body><h1>Hello, world!</h1></body></html>", response.getBody());
    }

    @Test
    public void shouldHaveAContentLength() {
        String contentLength = response.getContentLength();
        assertTrue(contentLength.contains("Content-Length:"));
    }

}