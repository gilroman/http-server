package gil.server;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class RoutesTest {
    private final String ROUTE_NOT_FOUND = "route-not-found";
    Router router = new Router();

    @Test
    public void shouldSetADefaultRouteNotFoundRouteOnTheRouter() {
        Request request = new Request();
        Routes routes = new Routes();
        request.setMethod("GET");
        request.setURI(ROUTE_NOT_FOUND);
        request.setHttpVersion("HTTP/1.1");

        routes.addRoutes(router);

        assertTrue(router.hasRoute(request));
    }

    @Test
    public void shouldSetARootRouteOnTheRouter() {
        Request request = new Request();
        Routes routes = new Routes();
        request.setMethod("GET");
        request.setURI("/");
        request.setHttpVersion("HTTP/1.1");

        routes.addRoutes(router);

        assertTrue(router.hasRoute(request));
    }
}
