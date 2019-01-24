package gil.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOProvider {
        InputStream getInputStream() throws IOException;
        OutputStream getOutputStream() throws IOException;
}
