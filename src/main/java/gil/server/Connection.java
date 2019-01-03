package gil.server;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Connection {
    private SocketWrapperInterface socket;
    private BufferedReader input;
    private PrintWriter output;

    public Connection(SocketWrapperInterface socket) {
        this.socket = socket;
        this.input = socket.getInput();
        this.output = socket.getOutput();
    }

    public BufferedReader getInput() {
        return this.input;
    }

    public PrintWriter getOutput() {
        return this.output;
    }

    public void close() throws Exception {
        socket.close();
    }
}
