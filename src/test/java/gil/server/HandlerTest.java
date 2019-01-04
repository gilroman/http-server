package gil.server;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import static org.junit.Assert.assertEquals;

public class HandlerTest {
    Handler handler = new Handler();

    @Test
    public void shouldReturnAResponseObject() throws IOException {
        String data = "GET / HTTP/1.1 text/html\r\n";
        String expectedBody = "Hello, world!";
        String expectedStatusCode = "200";
        StringReader dataStream = new StringReader(data);
        BufferedReader bufferedReader = new BufferedReader(dataStream);

        Response result = handler.processRequest(bufferedReader);

        assertEquals(expectedBody, result.getBody());
        assertEquals(expectedStatusCode, result.getStatusCode());
    }
}