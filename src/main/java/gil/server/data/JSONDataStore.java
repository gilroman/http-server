package gil.server.data;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.stream.Collectors.toList;


public class JSONDataStore {
    private List<Person> people = new ArrayList<>();
    private IOProvider ioProvider;

    public JSONDataStore(IOProvider ioProvider) {
        this.ioProvider = ioProvider;
    }

    private void loadData() throws IOException {
        InputStream input = ioProvider.getInputStream();
        JsonReader reader = Json.createReader(new InputStreamReader(input, "UTF-8"));
        JsonObject data;

        try {
            data = reader.readObject();
        } catch(JsonParsingException e) {
            data = Json.createObjectBuilder().add("people", Json.createArrayBuilder().build()).build();
        } finally {
            reader.close();
            input.close();
        }

        JsonArray peopleArray = data.getJsonArray("people");

        this.people = peopleArray.stream()
                .map(json ->
                        new Person(
                                json.asJsonObject().getInt("id"), json.asJsonObject().getString("name"), json.asJsonObject().getString("email"))).collect(toList());
    }

    private void storeData() throws IOException {
        OutputStream output = ioProvider.getOutputStream();
        PrintWriter printWriter = new PrintWriter(output);
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

        printWriter.write(jsonData.toString());
        printWriter.flush();
        printWriter.close();
    }

    public Person createPerson(String name, String email) throws IOException {
        Person p = new Person(generateId(), name, email);
        this.people.add(p);
        storeData();

        return p;
    }

    private Integer generateId() throws IOException {
        Integer id = 0;
        loadData();

        if(!people.isEmpty()){
            List<Integer> listOfID = people.stream()
                                           .map(Person::getId)
                                           .collect(toList());

            id = Collections.max(listOfID) + 1;
        }

        return id;
    }

    public JsonObject getPersonJSONObject(String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = reader.readObject();

        return jsonObject;
    }

    public Person getPerson(int personId) throws IOException {
        loadData();
        List<Person> locatedPerson =  this.people.stream()
                                            .filter(person -> person.getId() == personId)
                                            .collect(toList());

        return locatedPerson.get(0);
    }
}
