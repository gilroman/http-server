package gil.server;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PeopleData {
    private List<Person> people = new ArrayList<>();

    public Person addPerson(String name, String email) {
            Person p = new Person(generateId(), name, email);
            this.people.add(p);

            return p;
    }

    private Integer generateId() {
        Integer id = 0;

        if(!this.people.isEmpty()){
            List<Integer> listOfID = people.stream()
                    .map(Person::getId)
                    .collect(toList());

            id = Collections.max(listOfID) + 1;
        }

        return id;
    }

    public JsonObject getPeople() {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        this.people.stream().forEach(person -> {
            JsonObject jobj = jsonBuilder.add("id", person.getId())
                    .add("name", person.getName())
                    .add("email", person.getEmail()).build();
            jsonArrayBuilder.add(jobj);
        });

        JsonArray jsonArray = jsonArrayBuilder.build();
        JsonObject jsonData = jsonBuilder.add("people", jsonArray).build();

        return jsonData;
    }

    public Person getPerson(int personId) {
        List<Person> locatedPerson =  this.people.stream()
                .filter(person -> person.getId() == personId)
                .collect(toList());

        return locatedPerson.get(0);
    }

    public JsonObject getPersonJSONObject(String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = reader.readObject();

        return jsonObject;
    }

    public void updatePeople(JsonObject data) {
        JsonArray peopleArray = data.getJsonArray("people");

        if (!peopleArray.isEmpty()) {
            this.people = peopleArray.stream()
                    .map(json ->
                            new Person(
                                    json.asJsonObject().getInt("id"),
                                    json.asJsonObject().getString("name"),
                                    json.asJsonObject().getString("email"))).collect(toList());
        }
    }
}
