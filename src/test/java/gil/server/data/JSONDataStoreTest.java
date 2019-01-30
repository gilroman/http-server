package gil.server.data;

import org.junit.Test;

import javax.json.JsonObject;
import java.io.IOException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class JSONDataStoreTest {

    @Test
    public void shouldReturnAnEmptyJsonObjectIfFileIsEmpty() throws IOException {
        FileIO fio = new FileIO("peopleTest.txt");
        fio.initFile();
        JSONDataStore dataStore = new JSONDataStore(fio);
        JsonObject data = dataStore.loadData();

        assertEquals("{}", data.toString());
    }

    @Test
    public void shouldStoreAJsonObject() throws IOException {
        FileIO fio = new FileIO("peopleTest.txt");
        fio.initFile();
        JSONDataStore data = new JSONDataStore(fio);
        PeopleData people = new PeopleData();

        people.addPerson("Gil1", "g@tdd.com");
        data.storeData(people.getPeople());
        JsonObject dataFromFile = data.loadData();

        assertTrue(dataFromFile.keySet().contains("people"));
    }
}
