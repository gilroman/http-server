package gil.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public interface SocketWrapperInterface {
        BufferedReader getInput();
        OutputStream getOutput();
        void close() throws IOException;
}
