package gil.server.controllers;

import gil.server.data.FileIO;
import gil.server.data.JSONDataStore;
import gil.server.data.PeopleData;
import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonControllerTest {

    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

    @Test
    public void shouldRespondToAPOSTRequest() throws IOException {
        FileIO fio = new FileIO("peopleTest.txt");
        fio.initFile();
        JSONDataStore dataStore = new JSONDataStore(fio);
        PersonController personController = new PersonController(dataStore);
        Request request = new Request();
        Response response = new Response();
        String jsonBody = jsonObjectBuilder.add("name", "Gil")
                                           .add("email", "g@tdd.com")
                                           .build().toString();
        request.setMethod(HTTPProtocol.POST);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/api/people");
        request.setBody(jsonBody);


        personController.post.apply(request, response);

        assertEquals(HTTPProtocol.STATUS_CODE_201, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_CREATED, response.getReasonPhrase());
        assertTrue(response.getHeaders().contains("Location:"));
        assertTrue(new String(response.getBody()).contains("id"));
    }

    @Test
    public void shouldRespondWith400ToAPOSTRequestWithIncorrectlyFormattedData() throws IOException {
        FileIO fio = new FileIO("peopleTest.txt");
        fio.initFile();
        JSONDataStore dataStore = new JSONDataStore(fio);
        PersonController personController = new PersonController(dataStore);
        Request request = new Request();
        Response response = new Response();
        String jsonBody = jsonObjectBuilder.add("badKey", "Gil")
                                           .add("email", "g@tdd.com")
                                           .build().toString();
        request.setMethod(HTTPProtocol.POST);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/api/people");
        request.setBody(jsonBody);

        personController.post.apply(request, response);

        assertEquals(HTTPProtocol.STATUS_CODE_400, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_BAD_REQUEST, response.getReasonPhrase());
    }

    @Test
    public void shouldRespondToAGETRequest() throws IOException {
        FileIO fio = new FileIO("peopleTest.txt");
        fio.initFile();
        JSONDataStore dataStore = new JSONDataStore(fio);
        PeopleData people = new PeopleData();
        PersonController personController = new PersonController(dataStore);
        people.addPerson("Gil", "gil@tdd.com");
        dataStore.storeData(people.getPeople());
        String expectedBody = jsonObjectBuilder.add("id", 0)
                                               .add("name", "Gil")
                                               .add("email", "gil@tdd.com")
                                               .build().toString();
        Request request = new Request();
        Response response = new Response();
        request.setMethod(HTTPProtocol.GET);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/api/people/0");


        personController.get.apply(request, response);

        assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
        assertEquals(expectedBody, new String(response.getBody()));
    }

    @Test
    public void shouldReturnAResponseOfStatus404IfPersonDoesNotExist() throws IOException {
        FileIO fio = new FileIO("peopleTest.txt");
        fio.initFile();
        JSONDataStore dataStore = new JSONDataStore(fio);
        PeopleData people = new PeopleData();
        PersonController personController = new PersonController(dataStore);
        people.addPerson("Gil", "gil@tdd.com");
        Request request = new Request();
        Response response = new Response();
        request.setMethod(HTTPProtocol.GET);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/api/people/1000");


        personController.get.apply(request, response);

        assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
        assertEquals(HTTPProtocol.STATUS_CODE_404, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_NOT_FOUND, response.getReasonPhrase());
    }
}
