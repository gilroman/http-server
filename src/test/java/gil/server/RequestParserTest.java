package gil.server;

import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class RequestParserTest {
    private HTTPParser parser = new HTTPParser();

    @Test
    public void shouldReturnTheHTTPProtocol() {
        String startLine = "GET / HTTP/1.1";
        String expectedVersion = "1.1";

        assertEquals(expectedVersion, parser.getHttpProtocol(startLine));
    }

    @Test
    public void shouldReturnTheRequestMethod() {
        String startLine = "GET / HTTP/1.1";
        String expectedMethod = "GET";

        assertEquals(expectedMethod, parser.getMethod(startLine));
    }

    @Test
    public void shouldReturnTheRequestURI() {
        String startLine = "GET / HTTP/1.1";
        String expectedURI = "/";

        assertEquals(expectedURI, parser.getRequestURI(startLine));
    }

    @Test
    public void shouldReturnTheHeaders() {
        String CRLF = "\r\n";
        String headers = "Content-Type: multipart/form-data; boundary=--------------------------308161163843060198298118" + CRLF +
                "cache-control: no-cache" + CRLF +
                "Postman-Token: 05086562-916e-49b1-9af7-da8d4e8e9f7c" + CRLF +
                "User-Agent: PostmanRuntime/7.4.0" + CRLF +
                "Accept: */*" + CRLF +
                "Host: 127.0.0.1:4040" + CRLF +
                "accept-encoding: gzip, deflate" + CRLF +
                "content-length: 162" + CRLF +
                "Connection: keep-alive";

        HashMap<String, String> headersHash = new HashMap<>();
        headersHash.put("Content-Type".toLowerCase(),  "multipart/form-data; boundary=--------------------------308161163843060198298118");
        headersHash.put("cache-control".toLowerCase(), "no-cache");
        headersHash.put("Postman-Token".toLowerCase(), "05086562-916e-49b1-9af7-da8d4e8e9f7c");
        headersHash.put("User-Agent".toLowerCase(), "PostmanRuntime/7.4.0");
        headersHash.put("Accept".toLowerCase(), "*/*");
        headersHash.put("Host".toLowerCase(), "127.0.0.1:4040");
        headersHash.put("accept-encoding".toLowerCase(), "gzip, deflate");
        headersHash.put("content-length".toLowerCase(), "162");
        headersHash.put("Connection".toLowerCase(), "keep-alive");

        assertEquals(headersHash, parser.getHeaders(headers));
    }

    @Test(expected = HTTPInvalidRequestFormatException.class)
    public void shouldThrowAnExceptionWhenARequestStartsWithASpace() throws HTTPInvalidRequestFormatException {
        String invalidRequestMessageWithLeadingWhitespace = " GET / HTTP/1.1";
        parser.isRequestValid(invalidRequestMessageWithLeadingWhitespace);
    }

    @Test(expected = HTTPInvalidRequestFormatException.class)
    public void shouldThrowAnExceptionWhenItReceivesAnHttpResponseAsARequest() throws HTTPInvalidRequestFormatException {
        String request = "HTTP/1.1 200 Ok";
        parser.isRequestValid(request);
    }

    @Test(expected = HTTPInvalidRequestFormatException.class)
    public void shouldThrowAnExceptionWhenTheRequestIsAnEmptyString() throws HTTPInvalidRequestFormatException {
        String request = "";
        assertFalse(parser.isRequestValid(request));
    }
}
