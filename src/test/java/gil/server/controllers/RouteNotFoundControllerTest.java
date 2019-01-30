package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RouteNotFoundControllerTest {
    RouteNotFoundController routeNotFoundController = new RouteNotFoundController();

    @Test
    public void getShouldResultInResponseWith404Status() {
        Request request = new Request();
        Response response = new Response();
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setMethod(HTTPProtocol.GET);
        request.setURI("/fake-endpoint");

        routeNotFoundController.get.apply(request, response);

        assertEquals(HTTPProtocol.STATUS_CODE_404, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_NOT_FOUND, response.getReasonPhrase());
    }
}
