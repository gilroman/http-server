package gil.server;

import org.junit.Test;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouterTest {

    @Test
    public void shouldAllowSettingAnEndpointForGetMethod() {
        Router router = new Router();
        Request request = new Request();
        HashMap<String, String> emptyParameters = new HashMap();
        request.setURI("/");
        request.setHttpVersion("HTTP/1.1");
        request.setMethod("GET");
        request.setParameters(emptyParameters);
        router.get("/", (Request req, Response res) -> {
            res.setProtocol("HTTP/1.1");
            res.setStatusCode("200");
            res.setReasonPhrase("OK");
            return res;
        });

        Response response = router.route(request);

        assertEquals("HTTP/1.1", response.getProtocol());
        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getReasonPhrase());
    }

    @Test
    public void shouldCallTheControllerFunctionOfAnEndpoint() {
        Router router = new Router();
        Request request = new Request();
        HashMap<String, String> emptyParameters = new HashMap();
        request.setURI("/");
        request.setHttpVersion("HTTP/1.1");
        request.setMethod("GET");
        request.setParameters(emptyParameters);
        router.get("/", (Request req, Response res) -> {
            res.setProtocol("HTTP/1.1");
            res.setStatusCode("200");
            res.setReasonPhrase("OK");
            try {
                res.setBody("Hello, World");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return res;

        });

        Response response = router.route(request);
        String responseBody = response.getBody();

        assertEquals("HTTP/1.1", response.getProtocol());
        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getReasonPhrase());
        assertTrue(responseBody.contains("Hello, World"));
    }

    @Test
    public void shouldCallRouteNotFoundControllerWhenRouteDoesNotExist() {
        Router router = new Router();
        Request request = new Request();
        HashMap<String, String> emptyParameters = new HashMap();
        request.setURI("/fake-endpoint");
        request.setHttpVersion("HTTP/1.1");
        request.setMethod("GET");
        request.setParameters(emptyParameters);

        Response response = router.route(request);

        assertEquals("HTTP/1.1", response.getProtocol());
        assertEquals("404", response.getStatusCode());
        assertEquals("Not Found", response.getReasonPhrase());
    }

    @Test
    public void shouldCallTheParameterController() {
        Router router = new Router();
        Request request = new Request();
        HashMap<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("hobby", "surfing");
        request.setMethod("GET");
        request.setURI("/api/parameters");
        request.setParameters(expectedParameters);
        request.setHttpVersion("HTTP/1.1");

        Response response = router.route(request);
        String responseBody = response.getBody();

        assertTrue(responseBody.contains("hobby=surfing"));
    }

    @Test
    public void shouldCallTheStaticFileHandler() {
        Router router = new Router();
        Request request = new Request();
        request.setMethod("GET");
        request.setHttpVersion("HTTP/1.1");
        request.setURI("/file%20with%20space.txt");

        Response response = router.route(request);
        assertEquals("HTTP/1.1", response.getProtocol());
        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getReasonPhrase());
        assertEquals("The title of this text file has spaces.", response.getBody());
    }
}
