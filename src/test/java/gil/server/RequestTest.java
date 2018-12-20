package gil.server;

import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

public class RequestTest {

    @Test
    public void shouldReturnTheRequestHeaders() {
        String requestMessage = "GET / HTTP/1.1\nHost: www.gil-server.org\nAccept: text/html,application\nAccept-Encoding: gzip, deflate, br\n\n<!DOCTYPE=html>";
        Request request = new Request(requestMessage);
        HashMap<String, String> expectedHeaderFields = new HashMap<String, String>();
        expectedHeaderFields.put("Host:", "www.gil-server.org");
        expectedHeaderFields.put("Accept:", "text/html,application");
        expectedHeaderFields.put("Accept-Encoding:", "gzip, deflate, br");
        assertEquals(expectedHeaderFields, request.getHeaderFields());
    }

    @Test
    public void shouldReturnTheRequestLine() {
        String requestMessage = "GET / HTTP/1.1\nHost: www.gil-server.org\nAccept: text/html,application\nAccept-Encoding: gzip, deflate, br\n\n<!DOCTYPE=html>";
        Request request = new Request(requestMessage);
        HashMap<String, String> expectedRequestLine =  new HashMap<>();
        expectedRequestLine.put("Method", "GET");
        expectedRequestLine.put("Request-URI", "/");
        expectedRequestLine.put("HTTP-Version", "HTTP/1.1");
        assertEquals(expectedRequestLine, request.getRequestLine());
    }

    @Test
    public void shouldReturnTheRequestURI() {
        String requestMessage = "GET / HTTP/1.1\nHost: www.gil-server.org\nAccept: text/html,application\nAccept-Encoding: gzip, deflate, br\n\n<!DOCTYPE=html>";
        Request request = new Request(requestMessage);
        assertEquals("/", request.getRequestURI());
    }

    @Test
   public void shouldReturnTheBody() {
        String requestMessage = "GET / HTTP/1.1\nHost: www.gil-server.org\nAccept: text/html,application\nAccept-Encoding: gzip, deflate, br\n\n<!DOCTYPE=html>";
        Request request = new Request(requestMessage);
        assertEquals("<!DOCTYPE=html>", request.getBody());
    }
}
