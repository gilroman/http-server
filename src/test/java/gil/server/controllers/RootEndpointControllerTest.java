package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RootEndpointControllerTest {
    RootEndpointController rootEndpointController = new RootEndpointController();

    @Test
    public void getShouldResultInResponseWith200Status() {
        Request request = new Request();
        Response response = new Response();
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setMethod(HTTPProtocol.GET);
        request.setURI("/");

        rootEndpointController.get.apply(request, response);

        assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
        assertEquals("Hello, world!", response.getBody());
    }
}
