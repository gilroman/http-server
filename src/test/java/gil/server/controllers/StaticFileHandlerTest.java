package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StaticFileHandlerTest {
    StaticFileHandler staticFileHandler = new StaticFileHandler();

    @Test
    public void shouldReturnAResponseOfStatus200IfFileExists() {
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
}
