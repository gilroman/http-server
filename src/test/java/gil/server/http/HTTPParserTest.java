package gil.server.http;

import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import java.io.StringReader;
import java.io.BufferedReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HTTPParserTest {
    private final String CRLF = "\r\n";
    private String data = "GET /?let's=go%20surfing&pet=Lula HTTP/1.1" + CRLF +
            "Host: www.gil-server.org" + CRLF +
            "Accept: text/html,application" + CRLF +
            "Accept-Encoding: gzip, deflate, br" + CRLF +
            CRLF +
            "{\n" +
            "\t\"name\": \"lula\",\n" +
            "\t\"email\": \"kitty@gmail.com\"\n" +
            "}";
    private StringReader dataStream = new StringReader(data);
    private BufferedReader input = new BufferedReader(dataStream);
    private HTTPParser parser = new HTTPParser();

    @Test
    public void shouldParseRequestAndReturnObjectWithCorrectHTTPMethod() throws IOException {
        String expectedMethod = "GET";

        Request request = parser.parse(input);

        assertEquals(expectedMethod, request.getMethod());
    }

    @Test
    public void shouldParseRequestAndReturnObjectWithCorrectURI() throws IOException {
        String expectedURI = "/";

        Request request = parser.parse(input);

        assertEquals(expectedURI, request.getURI());
    }

    @Test
    public void shouldParseRequestAndReturnObjectWithCorrectHTTPProtocolVersion() throws IOException {
        String expectedVersion = "1.1";

        Request request = parser.parse(input);

        assertEquals(expectedVersion, request.getHTTPVersion());
    }

    @Test
    public void shouldParseRequestAndReturnObjectWithCorrectHeadersFields() throws IOException {
        HashMap<String, String> expectedHeaderFields = new HashMap<>();
        expectedHeaderFields.put("Host".toLowerCase(), "www.gil-server.org");
        expectedHeaderFields.put("Accept".toLowerCase(), "text/html,application");
        expectedHeaderFields.put("Accept-Encoding".toLowerCase(), "gzip, deflate, br");

        Request request = parser.parse(input);

        assertEquals(expectedHeaderFields, request.getHeaderFields());
    }

    @Test
    public void shouldParseAQueryWithParameters() throws IOException {
        HashMap<String, String> expectedQueryHash = new HashMap<>();
        expectedQueryHash.put("let's", "go surfing");
        expectedQueryHash.put("pet", "Lula");

        Request request = parser.parse(input);

        assertEquals(expectedQueryHash, request.getParameters());
    }

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

    @Test
    public void shouldReturnTheBody() throws IOException {
        String expectedBody = "{\n" +
                "\t\"name\": \"lula\",\n" +
                "\t\"email\": \"kitty@gmail.com\"\n" +
                "}";

        Request request = parser.parse(input);

        assertEquals(expectedBody, request.getBody());
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
