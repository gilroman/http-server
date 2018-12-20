package gil.server;

import org.junit.Test;
import java.io.UnsupportedEncodingException;
import static org.junit.Assert.*;

public class HandlerTest {
    Handler handler = new Handler();

    @Test
    public void shouldReturnAResponseObject() throws UnsupportedEncodingException {
        String emptyRequest = "GET / HTTP/1.1\n\n";
        assertTrue (handler.processRequest(emptyRequest) instanceof Response);
    }
}
