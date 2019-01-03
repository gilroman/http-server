package gil.server;

import java.io.UnsupportedEncodingException;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class ResponseTest {
    @Test
    public void shouldAllowSettingHTTPProtocol() {
        Response response = new Response();
        String protocol = "HTTP/1.1";

        response.setProtocol(protocol);

        assertEquals(protocol, response.getProtocol());
    }

    @Test
    public void shouldAllowSettingHTTPStatusCode() {
        Response response = new Response();
        String statusCode = "200";

        response.setStatusCode(statusCode);

        assertEquals(statusCode, response.getStatusCode());
    }

    @Test
    public void shouldAllowSettingReasonPhrase() {
        Response response = new Response();
        String reasonPhrase = "OK";

        response.setReasonPhrase(reasonPhrase);

        assertEquals(reasonPhrase, response.getReasonPhrase());
    }

    @Test
    public void shouldHaveAStartLine() {
        Response response = new Response();
        String startLine = "HTTP/1.1 200 OK";

        response.setProtocol("HTTP/1.1");
        response.setStatusCode("200");
        response.setReasonPhrase("OK");

        assertEquals(startLine, response.getStartLine());
    }

    @Test
    public void shouldAllowSettingBody() throws UnsupportedEncodingException {
        Response response = new Response();
        String body = "Some amazing content...";

        response.setBody(body);

        assertEquals(body, response.getBody());
    }

    @Test
    public void shouldHaveADateHeader() {
        Response response = new Response();

        String date = response.getDate();

        assertTrue(date.contains("Date"));
    }

    @Test
    public void shouldAllowSettingContentType() {
        Response response = new Response();
        String contentType = "text/html; charset=UTF-8";
        String expectedContentType = "Content-Type: text/html; charset=UTF-8";

        response.setContentType(contentType);

        assertEquals(expectedContentType, response.getContentType());
    }

    @Test
    public void shouldAllowSettingABody() throws UnsupportedEncodingException {
        Response response = new Response();
        String body = "<!DOCTYPE html><html lang=\"en-us\"><head></head><body><h1>Hello, world!</h1></body></html>";

        response.setBody(body);

        assertEquals(body, response.getBody());
    }

    @Test
    public void shouldHaveAContentLengthWhenGivenABody() throws UnsupportedEncodingException {
        Response response = new Response();
        response.setBody("<!DOCTYPE html><html lang=\"en-us\"><head></head><body><h1>Hello, world!</h1></body></html>");
        ;
        String contentLength = response.getContentLength();

        assertTrue(contentLength.contains("Content-Length:"));
    }
}
