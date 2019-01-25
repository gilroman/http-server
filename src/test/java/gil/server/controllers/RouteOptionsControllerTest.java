package gil.server.controllers;

import gil.server.http.Request;
import gil.server.http.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RouteOptionsControllerTest {

    RouteOptionsController routeOptionsController = new RouteOptionsController();

    @Test
    public void shouldReturnAResponseWithTheGivenAllowHeader() {
        Request request = new Request();
        Response response = new Response();
        request.setHttpVersion("HTTP/1.1");
        request.setMethod("OPTIONS");
        request.setURI("/api/people/0");

        String expectedAllow = "Allow: OPTIONS, GET";

        routeOptionsController.options.apply(request, response);

        assertEquals("HTTP/1.1", response.getProtocol());
        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getReasonPhrase());
        assertEquals(expectedAllow, response.getAllow());
    }
}
