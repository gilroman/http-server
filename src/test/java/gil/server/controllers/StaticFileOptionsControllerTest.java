package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StaticFileOptionsControllerTest {
    StaticFileOptionsController staticFileOptionsController = new StaticFileOptionsController();

    @Test
    public void shouldRespondToAnOPTIONSRequestForAnExistingStaticFile() {
        Request request = new Request();
        Response response = new Response();
        request.setMethod(HTTPProtocol.OPTIONS);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/file%20with%20space.txt");
        String expectedHeaderFields = "Allow: OPTIONS, GET";
        System.out.println("headers: " + response.getHeaders());

        staticFileOptionsController.options.apply(request, response);

        assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
        assertTrue(response.getHeaders().contains(expectedHeaderFields));
    }

    @Test
    public void shouldRespondWith404ToAnOPTIONSRequestForANonExistingStaticFile() {
        Request request = new Request();
        Response response = new Response();
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setMethod(HTTPProtocol.OPTIONS);
        request.setURI("/mystery-file.txt");

        staticFileOptionsController.options.apply(request, response);

        assertEquals(HTTPProtocol.STATUS_CODE_404, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_NOT_FOUND, response.getReasonPhrase());
    }
}
