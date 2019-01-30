package gil.server.data;

import org.junit.Test;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PeopleDataTest {

    @Test
    public void shouldAddAPerson() {
        PeopleData peopleData = new PeopleData();
        Person p = peopleData.addPerson("Gil1", "g@tdd.com");
        Person retrievedPerson = peopleData.getPerson(p.getId());

        assertEquals("Gil1", retrievedPerson.getName());
        assertEquals("g@tdd.com", retrievedPerson.getEmail());
    }

    @Test
    public void shouldAddMultiplePersons() {
        PeopleData peopleData = new PeopleData();
        Person p = peopleData.addPerson("Gil2", "g@tdd.com");

        assertEquals("Gil2", p.getName());
        assertEquals("g@tdd.com", p.getEmail());
        assertTrue(p.getId() >= 0);

        Person p2 = peopleData.addPerson("Angel2", "a@tdd.com");

        assertEquals("Angel2", p2.getName());
        assertEquals("a@tdd.com", p2.getEmail());
        assertTrue(p2.getId() >= 0);
        assertNotEquals(p.getId(), p2.getId());
    }

    @Test
    public void shouldConvertAStringRepresentingAPersonToAJsonObject() {
        PeopleData peopleData = new PeopleData();
        String jsonString = "{\"name\": \"Gil\", \"email\": \"g@tdd.com\"}";

        JsonObject jsonObject = peopleData.getPersonJSONObject(jsonString);

        assertEquals("Gil", jsonObject.getString("name"));
        assertEquals("g@tdd.com", jsonObject.getString("email"));
    }

    @Test
    public void shouldReturnAJSONObjectWithAnArrayOfPeople() {
        PeopleData peopleData = new PeopleData();
        peopleData.addPerson("Oliver", "oliver@tdd.com");
        JsonObject data = peopleData.getPeople();
        JsonArray people = data.getJsonArray("people");
        Integer indexOfLastPersonAdded = people.size();
        JsonObject lastPersonEntered = people.getJsonObject(indexOfLastPersonAdded - 1);

        assertEquals("Oliver", lastPersonEntered.getString("name"));
        assertEquals("oliver@tdd.com", lastPersonEntered.getString("email"));
    }

    @Test
    public void shouldAcceptAJsonObjectToUpdateTheStoreOfPeople() {
        String jsonString = "{\"id\": 0, \"name\": \"Gil\", \"email\": \"g@tdd.com\"}";
        PeopleData peopleData = new PeopleData();
        JsonObject personAsJSON = peopleData.getPersonJSONObject(jsonString);
        JsonArray people = Json.createArrayBuilder().add(personAsJSON).build();

        JsonObject data = Json.createObjectBuilder().add("people", people).build();
        peopleData.updatePeople(data);
        Person retrievedPerson = peopleData.getPerson(0);

        assertEquals("Gil", retrievedPerson.getName());
        assertEquals("g@tdd.com", retrievedPerson.getEmail());
    }
}
