package gil.server;

import java.io.IOException;

public interface ServerSocketWrapperInterface {
    void createAndListen(int port) throws Exception;
    String receiveData() throws IOException;
    void sendData(String data) throws Exception;
    void close() throws Exception;
}
