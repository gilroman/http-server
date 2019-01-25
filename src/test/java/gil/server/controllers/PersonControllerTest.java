package gil.server.controllers;

import gil.server.data.FileIO;
import gil.server.data.JSONDataStore;
import gil.server.http.Request;
import gil.server.http.Response;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class PersonControllerTest {
    @Test
    public void shouldRespondToAGETRequest() throws IOException {
        FileIO fio = new FileIO("peopleTest.txt");
        fio.initFile();
        JSONDataStore dataStore = new JSONDataStore(fio);
        dataStore.createPerson("Gil", "gil@tdd.com");
        PersonController personController = new PersonController(dataStore);
        String expectedBody = "{ \"id\": 0, \"name\": \"Gil\", \"email\": \"gil@tdd.com\"}";
        Request request = new Request();
        Response response = new Response();
        request.setMethod("GET");
        request.setHttpVersion("HTTP/1.1");
        request.setURI("/api/people/0");


        personController.get.apply(request, response);

        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getReasonPhrase());
        assertEquals(expectedBody, response.getBody());
    }

    @Test
    public void shouldReturnAResponseOfStatus404IfPersonDoesNotExist() throws IOException {
        FileIO fio = new FileIO("peopleTest.txt");
        fio.initFile();
        JSONDataStore dataStore = new JSONDataStore(fio);
        dataStore.createPerson("Gil", "g@tdd.com");
        PersonController personController = new PersonController(dataStore);
        Request request = new Request();
        Response response = new Response();
        request.setMethod("GET");
        request.setHttpVersion("HTTP/1.1");
        request.setURI("/api/people/1000");


        personController.get.apply(request, response);

        assertEquals("HTTP/1.1", response.getProtocol());
        assertEquals("404", response.getStatusCode());
        assertEquals("Not Found", response.getReasonPhrase());
    }
}
