package gil.server.http;

import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

public class RequestTest {

    @Test
    public void shouldAllowSettingTheURI() {
        Request request = new Request();
        String URI = "/";

        request.setURI(URI);

        assertEquals(URI, request.getURI());
    }

    @Test
    public void shouldAllowSettingTheMethod() {
        Request request = new Request();

        request.setMethod(HTTPProtocol.GET);

        assertEquals(HTTPProtocol.GET, request.getMethod());
    }

    @Test
    public void shouldAllowSettingTheHTTPVersion() {
        Request request = new Request();

        request.setHttpVersion(HTTPProtocol.HTTP_VERSION);

        assertEquals(HTTPProtocol.HTTP_VERSION, request.getHTTPVersion());
    }

    @Test
    public void shouldAllowSettingTheHeaders() {
        Request request = new Request();
        HashMap<String, String> headerFields = new HashMap<>();
        headerFields.put("Host:", "www.gil-server.org");
        headerFields.put("Accept:", "text/html,application");
        headerFields.put("Accept-Encoding:", "gzip, deflate, br");

        request.setHeaderFields(headerFields);

        assertEquals(headerFields, request.getHeaderFields());
    }

   @Test
   public void shouldAllowSettingTheBody() {
        Request request = new Request();
        String body = "<!DOCTYPE=html><html lang=\"en-us\"><head></head><body></body></html>";

        request.setBody(body);

        assertEquals(body, request.getBody());
    }

    @Test
    public void shouldAllowSettingTheParameters() {
        Request request = new Request();
        HashMap<String, String> parameters = new HashMap<>();

        request.setParameters(parameters);

        assertEquals(parameters, request.getParameters());
    }
}
