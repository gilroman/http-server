package gil.server;

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

        String headers = response.getHeaders();

        assertTrue(headers.contains("Date"));
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

        String headers = response.getHeaders();

        assertTrue(headers.contains("Content-Length:"));
    }

    @Test
    public void shouldSetAHeaderField() {
        Response response = new Response();
        response.setProtocol(HTTPProtocol.PROTOCOL);
        response.setStatusCode(HTTPProtocol.STATUS_CODE_200);
        response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);
        String allowHeaderValue = "OPTIONS, GET";

        response.addHeader(HTTPProtocol.ALLOW, allowHeaderValue);

        assertTrue(response.getHeaders().contains(allowHeaderValue));
    }

    @Test
    public void shouldSetTwoHeaderFields() {
        Response response = new Response();
        String allowHeaderValue = "OPTIONS, GET";
        String contentTypeHeaderValue = "\"text/html; charset=UTF-8\"";

        response.addHeader(HTTPProtocol.ALLOW, allowHeaderValue);
        response.addHeader(HTTPProtocol.CONTENT_TYPE, contentTypeHeaderValue);

        assertTrue(response.getHeaders().contains(allowHeaderValue));
        assertTrue(response.getHeaders().contains(contentTypeHeaderValue));
    }
}
