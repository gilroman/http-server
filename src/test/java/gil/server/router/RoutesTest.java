package gil.server.router;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class RoutesTest {
   Router router = new Router();

    @Test
    public void shouldSetARootRouteOnTheRouter() {
        Request request = new Request();
        Routes routes = new Routes();
        request.setMethod(HTTPProtocol.GET);
        request.setURI("/");
        request.setHttpVersion(HTTPProtocol.PROTOCOL);

        routes.addRoutes(router);

        assertTrue(router.getRouteController(request).isPresent());
    }
}
