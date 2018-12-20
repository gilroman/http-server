package gil.server;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.UnsupportedEncodingException;

public class RouterTest {
    Router router = new Router();

    @Test
    public void shouldReturnTrueIfEndpointExists() {
        assertTrue(router.hasRoute("/"));
    }

    @Test
    public void shouldReturnFalseIfEndpointDoesNotExist() {
        assertFalse(router.hasRoute("/fake_endpoint"));
    }

    @Test
    public void shouldReturnAResponseIfTheRouteExists() throws UnsupportedEncodingException {
        Request request = new Request("GET / HTTP/1.1\nHost: www.gil-server.org\nAccept: text/html,application\nAccept-Encoding: gzip, deflate, br\n\n<!DOCTYPE=html>");
        Router router = new Router();
        assertTrue(router.route(request) instanceof Response);
    }
}
