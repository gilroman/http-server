package gil.server.controllers;

import gil.server.data.JSONDataStore;
import gil.server.data.PeopleData;
import gil.server.data.Person;
import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.util.function.BiFunction;

public class PersonController {
    private static JSONDataStore dataStore;
    private static JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
    private static JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
    private static PeopleData people = new PeopleData();
    private static final String route = "/api/people/";

    public PersonController(JSONDataStore dataStore) {
        this.dataStore = dataStore;
    }

    private static Integer parsePersonId(Request request) {
        int indexOfId = 1;
        String requestURI = request.getURI();
        String[] uri = requestURI.split(route);
        String id = uri[indexOfId];

        return Integer.parseInt(id);
    }

    public static BiFunction<Request, Response, Response> get =
            (request, response) -> {
                Integer id = parsePersonId(request);

                try {
                    people.updatePeople(dataStore.loadData());
                    Person person = people.getPerson(id);
                    Integer pid = person.getId();
                    String name = person.getName();
                    String email = person.getEmail();
                    JsonObject body = jsonBuilder.add("id", pid)
                                                 .add("name", name)
                                                 .add("email", email)
                                                 .build();
                    response.setProtocol(HTTPProtocol.PROTOCOL);
                    response.setStatusCode(HTTPProtocol.STATUS_CODE_200);
                    response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);
                    response.addHeader(HTTPProtocol.CONTENT_TYPE, "application/json");
                    response.setBody(body.toString().getBytes());

                } catch (Exception e) {
                    response.setProtocol(HTTPProtocol.PROTOCOL);
                    response.setStatusCode(HTTPProtocol.STATUS_CODE_404);
                    response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_NOT_FOUND);
                    response.addHeader(HTTPProtocol.CONTENT_TYPE, "application/json");
                }

                return response;
            };

    public static BiFunction<Request, Response, Response> post =
            (request, response) -> {
                String requestBody = request.getBody();
                JsonObject personJSON = people.getPersonJSONObject(requestBody);

                try {
                    JsonObject data = dataStore.loadData();

                    if (data.isEmpty()) {
                        JsonArray jsonArray = jsonArrayBuilder.build();
                        data = jsonBuilder.add("people", jsonArray).build();
                    }

                    people.updatePeople(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (personJSON.containsKey("name") && personJSON.containsKey("email")) {
                    String name = personJSON.getString("name");
                    String email = personJSON.getString("email");
                    Person person = null;

                    try {
                        person = people.addPerson(name, email);
                        dataStore.storeData(people.getPeople());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Integer personId = person.getId();
                    String body = jsonBuilder.add("id", personId).build().toString();
                    response.setProtocol(HTTPProtocol.PROTOCOL);
                    response.setStatusCode(HTTPProtocol.STATUS_CODE_201);
                    response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_CREATED);
                    response.addHeader(HTTPProtocol.CONTENT_TYPE,"application/json");
                    response.addHeader(HTTPProtocol.LOCATION,route + personId);
                    response.setBody(body.getBytes());

                } else {
                    response.setProtocol(HTTPProtocol.PROTOCOL);
                    response.setStatusCode(HTTPProtocol.STATUS_CODE_400);
                    response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_BAD_REQUEST);
                    response.addHeader(HTTPProtocol.CONTENT_TYPE,"text/html; charset=UTF-8");
                }

                return response;
            };
}
