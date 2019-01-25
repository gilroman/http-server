package gil.server.data;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PersonTest {

    @Test
    public void hasId() {
        Integer id = 1;
        Person programmer = new Person();

        programmer.setId(id);

        assertEquals(id, programmer.getId());
    }

    @Test
    public void hasName() {
        String name = "Gil Roman";
        Person programmer = new Person();

        programmer.setName(name);

        assertEquals(name, programmer.getName());
    }

    @Test
    public void hasEmail() {
        String email = "gil@tdd.com";
        Person programmer = new Person();

        programmer.setEmail(email);

        assertEquals(email, programmer.getEmail());
    }

    @Test
    public void createsStringRepresentation() {
        String expectedString = "1, Gil Roman, gil@tdd.com";
        Person programmer = new Person();

        programmer.setId(1);
        programmer.setName("Gil Roman");
        programmer.setEmail("gil@tdd.com");

        assertEquals(expectedString, programmer.toString());
    }
}
