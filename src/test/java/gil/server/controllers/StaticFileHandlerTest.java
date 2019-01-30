package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StaticFileHandlerTest {
    StaticFileHandler staticFileHandler = new StaticFileHandler();

    @Test
    public void shouldReturnAResponseOfStatus200IfTextFileExists() {
        Request request = new Request();
        Response response = new Response();
        request.setMethod(HTTPProtocol.GET);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/test.txt");

        staticFileHandler.get.apply(request, response);

        assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
        assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
    }

    @Test
    public void shouldReturnAResponseOfStatus404IfFileDoesNotExist() {
        Request request = new Request();
        Response response = new Response();
        request.setMethod(HTTPProtocol.GET);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/mysteryfile.txt");

        staticFileHandler.get.apply(request, response);

        assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
        assertEquals(HTTPProtocol.STATUS_CODE_404, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_NOT_FOUND, response.getReasonPhrase());
    }

    @Test
    public void shouldReturnAResponseOfStatus200IfImageFileExists() {
        Request request = new Request();
        Response response = new Response();
        request.setMethod("GET");
        request.setHttpVersion("HTTP/1.1");
        request.setURI("/img/kitty-300x300.jpg");

        staticFileHandler.get.apply(request, response);

        assertEquals("HTTP/1.1", response.getProtocol());
        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getReasonPhrase());
        assertTrue(response.getHeaders().contains("image/jpeg"));
    }
}
