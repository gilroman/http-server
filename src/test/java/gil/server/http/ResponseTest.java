package gil.server.http;

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
    public void shouldAllowSettingBody() {
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
    public void shouldAllowSettingABody() {
        Response response = new Response();
        String body = "<!DOCTYPE html><html lang=\"en-us\"><head></head><body><h1>Hello, world!</h1></body></html>";

        response.setBody(body);

        assertEquals(body, response.getBody());
    }

    @Test
    public void shouldHaveAContentLengthWhenGivenABody() {
        Response response = new Response();
        response.setBody("<!DOCTYPE html><html lang=\"en-us\"><head></head><body><h1>Hello, world!</h1></body></html>");

        String contentLength = response.getContentLength();

        assertTrue(contentLength.contains("Content-Length:"));
    }

    @Test
    public void shouldSetAllowHeaderField() {
        Response response = new Response();
        String allow = "OPTIONS, GET";
        String expectedAllowHeader = "Allow: OPTIONS, GET";

        response.setAllow(allow);

        assertEquals(expectedAllowHeader, response.getAllow());
    }

    @Test
    public void shouldSetLocationHeaderField() {
        Response response = new Response();
        String expectedLocationHeader = "Location: /api/people/1";

        response.setLocation(expectedLocationHeader);

        assertEquals(expectedLocationHeader, response.getLocation());
    }
}