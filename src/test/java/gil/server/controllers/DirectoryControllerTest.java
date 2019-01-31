package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DirectoryControllerTest {

    @Test
    public void shouldRespondToAGETRequest() {
        DirectoryController directoryController = new DirectoryController();
        Request request = new Request();
        Response response = new Response();
        String filePathNames[] = { "public/img",
                           "file with space.txt",
                           "test.txt" };
        request.setMethod(HTTPProtocol.GET);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/public");

        directoryController.get.apply(request, response);
        String body = new String(response.getBody());

        assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());

        for(String filePathName: filePathNames) {
            assertTrue(body.contains(filePathName));
        }
    }

    @Test
    public void shouldResponseWithStatus404ToRequestOfInvalidResource() {
        DirectoryController directoryController = new DirectoryController();
        Request request = new Request();
        Response response = new Response();
        request.setMethod(HTTPProtocol.GET);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/publicsiaondioan;a93");

        directoryController.get.apply(request, response);
        String body = new String(response.getBody());

        assertEquals(HTTPProtocol.STATUS_CODE_404, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_NOT_FOUND, response.getReasonPhrase());

    }
}
