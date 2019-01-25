package gil.server.http;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

public class HTTPConnectionReaderTest {
    private final String CRLF = "\r\n";
    private HTTPConnectionReader httpConnectionReader = new HTTPConnectionReader();

    @Test
    public void shouldReturnAHashMapOfTheRequest() throws IOException {
        String data = "GET / HTTP/1.1" + CRLF +
                "Content-Type: application/json" + CRLF +
                "cache-control: no-cache" + CRLF +
                "Postman-Token: fa99cc71-0e42-4dba-aeb8-8b1099465c79" + CRLF +
                "User-Agent: PostmanRuntime/7.4.0" + CRLF +
                "Accept: */*" + CRLF +
                "Host: 127.0.0.1:4040" + CRLF +
                "accept-encoding: gzip, deflat" + CRLF +
                "content-length: 26" + CRLF +
                "Connection: keep-alive" + CRLF +
                CRLF +
                "{" + CRLF +
                "\"hobby\": \"surfing\"" + CRLF +
                "}";
        StringReader inputStream = new StringReader(data);
        BufferedReader input = new BufferedReader(inputStream);
        HashMap<String, String> expectedHash = new HashMap<>();
        expectedHash.put("request-line", "GET / HTTP/1.1");
        expectedHash.put("headers", "Content-Type: application/json" + CRLF +
                "cache-control: no-cache" + CRLF +
                "Postman-Token: fa99cc71-0e42-4dba-aeb8-8b1099465c79" + CRLF +
                "User-Agent: PostmanRuntime/7.4.0" + CRLF +
                "Accept: */*" + CRLF +
                "Host: 127.0.0.1:4040" + CRLF +
                "accept-encoding: gzip, deflat" + CRLF +
                "content-length: 26" + CRLF +
                "Connection: keep-alive" + CRLF);
        expectedHash.put("body", "{" + CRLF +
                "\"hobby\": \"surfing\"" + CRLF +
                "}");

        assertEquals(expectedHash, httpConnectionReader.readRequest(input));
    }
}
