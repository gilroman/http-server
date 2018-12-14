package gil.server;

import java.io.*;
import java.net.Socket;

public class ServerSocketMock implements ServerSocketWrapperInterface {
    private Boolean createAndListenCalled;
    private Boolean closeCalled;
    private BufferedReader input;
    private PrintWriter output;

    public ServerSocketMock(BufferedReader bufferedReader, PrintWriter printWriter) {
        this.input = bufferedReader;
        this.output = printWriter;
    }

    public void createAndListen(int port) {
        createAndListenCalled = true;
    }

    public String receiveData() throws IOException {
        return input.readLine();
    }

    public void sendData(String data) {
        output.print(data);
    }

    public void close() {
        closeCalled = true;
    }

    public boolean wasCreateAndListenCalled() {
        return createAndListenCalled;
    }

    public boolean wasCloseCalled() {
        return closeCalled;
    }

}