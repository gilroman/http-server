package gil.server;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RouteNotFoundControllerTest {
    RouteNotFoundController routeNotFoundController = new RouteNotFoundController();

    @Test
    public void getShouldResultInResponseWith404Status() {
        Request request = new Request();
        Response response = new Response();
        request.setHttpVersion("HTTP/1.1");
        request.setMethod("GET");
        request.setURI("/fake-endpoint");

        routeNotFoundController.get.apply(request, response);

        assertEquals("404", response.getStatusCode());
        assertEquals("Not Found", response.getReasonPhrase());
    }
}
