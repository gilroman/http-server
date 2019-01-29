package gil.server;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.function.BiFunction;

public class PostPersonController {
    public static BiFunction<Request, Response, Response> post =
            (request, response) -> {
                JSONDataStore dataStore = new JSONDataStore(new FileIO("people.txt"));
                PeopleData people = new PeopleData();

                try {
                    people.updatePeople(dataStore.loadData());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String body = request.getBody();
                JsonObject personJSON = people.getPersonJSONObject(body);

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
                    response.setProtocol(HTTPProtocol.PROTOCOL);
                    response.setStatusCode(HTTPProtocol.STATUS_CODE_201);
                    response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_CREATED);
                    response.addHeader(HTTPProtocol.CONTENT_TYPE,"application/json");
                    response.addHeader(HTTPProtocol.LOCATION,"/api/people/" + personId);
                    response.setBody("{ \"id\": " + personId + " }");

                } else {
                    response.setProtocol(HTTPProtocol.PROTOCOL);
                    response.setStatusCode(HTTPProtocol.STATUS_CODE_400);
                    response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_BAD_REQUEST);
                    response.addHeader(HTTPProtocol.CONTENT_TYPE,"text/html; charset=UTF-8");
                }

                return response;
            };
}
