package gil.server;

import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import java.io.StringReader;
import java.io.BufferedReader;
import static org.junit.Assert.assertEquals;

public class HTTPParserTest {
    private final String CRLF = "\r\n";
    private String data = "GET / HTTP/1.1" + CRLF +
            "Host: www.gil-server.org" + CRLF +
            "Accept: text/html,application" + CRLF +
            "Accept-Encoding: gzip, deflate, br" + CRLF +
            CRLF +
            "<!DOCTYPE=html>";
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
}
