package gil.server;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import static org.junit.Assert.assertTrue;

public class HandlerTest {
    Handler handler = new Handler();

    @Test
    public void shouldReturnAResponseObject() throws IOException {
        String data = "GET / HTTP/1.1 text/html\r\n";
        StringReader dataStream = new StringReader(data);
        BufferedReader bufferedReader = new BufferedReader(dataStream);
        assertTrue (handler.processRequest(bufferedReader) instanceof Response);
    }
}
