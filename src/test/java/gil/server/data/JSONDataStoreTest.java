package gil.server.data;

import java.io.IOException;
import org.junit.Test;
import javax.json.JsonObject;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JSONDataStoreTest {

    @Test
    public void shouldStoreAPerson() throws IOException {
        FileIO fio = new FileIO("peopleTest.txt");
        fio.initFile();

        JSONDataStore data = new JSONDataStore(fio);

        Person p = data.createPerson("Gil1", "g@tdd.com");
        Person retrievedPerson = data.getPerson(p.getId());

        assertEquals("Gil1", p.getName());
        assertEquals("g@tdd.com", p.getEmail());
        assertTrue(p.getId() >= 0);
        assertTrue(p.equals(retrievedPerson));
    }

    @Test
    public void shouldStoreMultiplePersons() throws IOException {
        FileIO fio = new FileIO("peopleTest.txt");
        fio.initFile();

        JSONDataStore data = new JSONDataStore(fio);

        Person p = data.createPerson("Gil2", "g@tdd.com");

        assertEquals("Gil2", p.getName());
        assertEquals("g@tdd.com", p.getEmail());
        assertTrue(p.getId() >= 0);

        Person p2 = data.createPerson("Angel2", "a@tdd.com");

        assertEquals("Angel2", p2.getName());
        assertEquals("a@tdd.com", p2.getEmail());
        assertTrue(p2.getId() >= 0);
        assertNotEquals(p.getId(), p2.getId());
    }

    @Test
    public void shouldConvertAStringToAJsonObject() throws IOException {
        FileIO fio = new FileIO("peopleTest.txt");
        fio.initFile();

        JSONDataStore data = new JSONDataStore(fio);

        String jsonString = "{\"name\": \"Gil\", \"email\": \"g@tdd.com\"}";

        JsonObject jsonObject = data.getPersonJSONObject(jsonString);

        assertEquals("Gil", jsonObject.getString("name"));
        assertEquals("g@tdd.com", jsonObject.getString("email"));
    }
}
