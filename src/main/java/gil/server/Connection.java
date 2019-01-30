package gil.server;

import java.io.BufferedReader;
import java.io.OutputStream;

public class Connection {
    private SocketWrapperInterface socket;
    private BufferedReader input;
    private OutputStream output;

    public Connection(SocketWrapperInterface socket) {
        this.socket = socket;
        this.input = socket.getInput();
        this.output = socket.getOutput();
    }

    public BufferedReader getInput() {
        return this.input;
    }

    public OutputStream getOutput() {
        return this.output;
    }

    public void close() throws Exception {
        socket.close();
    }
}
