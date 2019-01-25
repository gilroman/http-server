package gil.server.controllers;

import gil.server.data.FileIO;
import gil.server.data.JSONDataStore;
import gil.server.data.Person;
import gil.server.http.Request;
import gil.server.http.Response;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.function.BiFunction;

public class PostPersonController {
    public static BiFunction<Request, Response, Response> post =
            (request, response) -> {
                JSONDataStore data = new JSONDataStore(new FileIO("people.txt"));
                String body = request.getBody();
                JsonObject personJSON = data.getPersonJSONObject(body);

                if (personJSON.containsKey("name") && personJSON.containsKey("email")) {
                    String name = personJSON.getString("name");
                    String email = personJSON.getString("email");
                    Person person = null;

                    try {
                        person = data.createPerson(name, email);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Integer personId = person.getId();
                    response.setProtocol("HTTP/1.1");
                    response.setStatusCode("201");
                    response.setReasonPhrase("Created");
                    response.setContentType("application/json");
                    response.setLocation("Location: /api/people/" + personId);
                    response.setBody("{ \"id\": " + personId + " }");

                } else {
                    response.setProtocol("HTTP/1.1");
                    response.setStatusCode("400");
                    response.setReasonPhrase("Bad Request");
                    response.setContentType("text/html; charset=UTF-8");
                    response.setBody("");
                }

                return response;
            };
}
