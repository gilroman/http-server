package gil.server.http;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ResponseTest {
    @Test
    public void shouldAllowSettingHTTPProtocol() {
        Response response = new Response();

        response.setProtocol(HTTPProtocol.PROTOCOL);

        assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
    }

    @Test
    public void shouldAllowSettingHTTPStatusCode() {
        Response response = new Response();

        response.setStatusCode(HTTPProtocol.STATUS_CODE_200);

        assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
    }

    @Test
    public void shouldAllowSettingReasonPhrase() {
        Response response = new Response();

        response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);

        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
    }

    @Test
    public void shouldHaveAStartLine() {
        Response response = new Response();
        String startLine = "HTTP/1.1 200 OK";

        response.setProtocol(HTTPProtocol.PROTOCOL);
        response.setStatusCode(HTTPProtocol.STATUS_CODE_200);
        response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);

        assertEquals(startLine, response.getStartLine());
    }

    @Test
    public void shouldAllowSettingBody() {
        Response response = new Response();
        String body = "Some amazing content...";

        response.setBody(body.getBytes());

        assertEquals(body, new String(response.getBody()));
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

        response.setBody(body.getBytes());

        assertEquals(body, new String(response.getBody()));
    }

    @Test
    public void shouldHaveAContentLengthWhenGivenABody() {
        Response response = new Response();
        String body = "<!DOCTYPE html><html lang=\"en-us\"><head></head><body><h1>Hello, world!</h1></body></html>";
        response.setBody(body.getBytes());

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

    @Test
    public void shouldSetLocationHeaderField() {
        Response response = new Response();
        String expectedLocationHeader = "Location: /api/people/1";

        response.addHeader(HTTPProtocol.LOCATION, expectedLocationHeader);

        assertTrue(response.getHeaders().contains(expectedLocationHeader));
    }

    @Test
    public void shouldSetAReasonPhraseProgrammatically() {
        Response response = new Response();

        response.setProtocol(HTTPProtocol.PROTOCOL);
        response.setStatusCode(HTTPProtocol.STATUS_CODE_200);

        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
    }

    @Test
    public void shouldReturnABytesArrayRepresentationOfTheResponse() {
        Response response = new Response();
        StringBuilder expectedResponse = new StringBuilder();
        response.setProtocol(HTTPProtocol.PROTOCOL);
        response.setStatusCode(HTTPProtocol.STATUS_CODE_200);
        response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);
        response.setBody("".getBytes());
        expectedResponse.append(response.getStartLine() + HTTPProtocol.CRLF +
                                response.getHeaders() +
                                HTTPProtocol.CRLF +
                                new String(response.getBody()));

        assertEquals(expectedResponse.toString(), new String(response.toByteArray()));
    }
}
