package gil.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface SocketWrapperInterface {
        BufferedReader getInput();
        PrintWriter getOutput();
        void close() throws IOException;
}
