package gil.server.controllers;

import gil.server.http.Request;
import gil.server.http.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StaticFileOptionsControllerTest {
    StaticFileOptionsController staticFileOptionsController = new StaticFileOptionsController();

    @Test
    public void shouldRespondToAnOPTIONSRequestForAnExistingStaticFile() {
        Request request = new Request();
        Response response = new Response();
        request.setMethod("OPTIONS");
        request.setHttpVersion("HTTP/1.1");
        request.setURI("/file%20with%20space.txt");
        String expectedHeaderFields = "Allow: OPTIONS, GET";

        staticFileOptionsController.options.apply(request, response);

        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getReasonPhrase());
        assertEquals(expectedHeaderFields, response.getAllow());
    }

    @Test
    public void shouldRespondWith404ToAnOPTIONSRequestForANonExistingStaticFile() {
        Request request = new Request();
        Response response = new Response();
        request.setHttpVersion("HTTP/1.1");
        request.setMethod("OPTIONS");
        request.setURI("/mystery-file.txt");

        staticFileOptionsController.options.apply(request, response);

        assertEquals("404", response.getStatusCode());
        assertEquals("Not Found", response.getReasonPhrase());
    }
}
