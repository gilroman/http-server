package gil.server;

import org.junit.Test;
import java.io.UnsupportedEncodingException;
import static org.junit.Assert.assertEquals;

public class RouterTest {

    @Test
    public void shouldAllowSettingAnEndpointForGetMethod() {
        Router router = new Router();
        Request request = new Request();
        request.setURI("/");
        request.setHttpVersion("HTTP/1.1");
        request.setMethod("GET");
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
        request.setURI("/");
        request.setHttpVersion("HTTP/1.1");
        request.setMethod("GET");
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

        assertEquals("HTTP/1.1", response.getProtocol());
        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getReasonPhrase());
        assertEquals("Hello, World", response.getBody());
    }

    @Test
    public void shouldCallRouteNotFoundControllerWhenRouteDoesNotExist() {
        Router router = new Router();
        Request request = new Request();
        request.setURI("/fake-endpoint");
        request.setHttpVersion("HTTP/1.1");
        request.setMethod("GET");

        Response response = router.route(request);

        assertEquals("HTTP/1.1", response.getProtocol());
        assertEquals("404", response.getStatusCode());
        assertEquals("Not Found", response.getReasonPhrase());
    }
}
