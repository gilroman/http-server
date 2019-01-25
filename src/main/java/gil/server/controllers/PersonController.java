package gil.server.controllers;

import gil.server.data.JSONDataStore;
import gil.server.data.Person;
import gil.server.http.Request;
import gil.server.http.Response;
import java.util.function.BiFunction;

public class PersonController {
    public static JSONDataStore dataStore;

    public PersonController(JSONDataStore dataStore) {
        this.dataStore = dataStore;
    }
    public static BiFunction<Request, Response, Response> get =
            (request, response) -> {
                String requestURI = request.getURI();
                String[] uri = requestURI.split("/api/people/");
                int indexOfId = 1;
                String id = uri[indexOfId];

                try {
                    Person person = dataStore.getPerson(Integer.parseInt(id));
                    Integer pid = person.getId();
                    String name = person.getName();
                    String email = person.getEmail();
                    response.setProtocol("HTTP/1.1");
                    response.setStatusCode("200");
                    response.setReasonPhrase("OK");
                    response.setContentType("application/json");
                    response.setBody("{ \"id\": " + pid + ", \"name\": \"" + name + "\", \"email\": \"" + email + "\"}");

                } catch (Exception e) {
                    response.setProtocol("HTTP/1.1");
                    response.setStatusCode("404");
                    response.setReasonPhrase("Not Found");
                    response.setContentType("application/json");
                }

                return response;
            };
}
