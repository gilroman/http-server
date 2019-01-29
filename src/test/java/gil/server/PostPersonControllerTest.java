package gil.server;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PostPersonControllerTest {
    PostPersonController postPersonController = new PostPersonController();

    @Test
    public void shouldRespondToAPOSTRequest() {
        Request request = new Request();
        Response response = new Response();
        String jsonBody = "{\"name\": \"Gil\", \"email\": \"g@tdd.com\"}";
        request.setMethod("POST");
        request.setHttpVersion("HTTP/1.1");
        request.setURI("/api/people");
        request.setBody(jsonBody);


        postPersonController.post.apply(request, response);

        assertEquals("201", response.getStatusCode());
        assertEquals("Created", response.getReasonPhrase());
        assertTrue(response.getHeaders().contains("Location:"));
        assertTrue(response.getBody().contains("id"));
    }

    @Test
    public void shouldRespondWith400ToAPOSTRequestWithIncorrectlyFormattedData() {
        Request request = new Request();
        Response response = new Response();
        String jsonBody = "{\"badKey\": \"Gil\", \"email\": \"g@tdd.com\"}";
        request.setMethod("POST");
        request.setHttpVersion("HTTP/1.1");
        request.setURI("/api/people");
        request.setBody(jsonBody);

        postPersonController.post.apply(request, response);

        assertEquals("400", response.getStatusCode());
        assertEquals("Bad Request", response.getReasonPhrase());
    }
}
