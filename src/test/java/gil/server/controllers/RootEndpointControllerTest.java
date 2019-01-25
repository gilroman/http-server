package gil.server.controllers;

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
        request.setHttpVersion("HTTP/1.1");
        request.setMethod("GET");
        request.setURI("/");

        rootEndpointController.get.apply(request, response);

        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getReasonPhrase());
        assertEquals("Hello, world!", response.getBody());
    }
}
