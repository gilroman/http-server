package gil.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketWrapper implements SocketWrapperInterface {
    private Socket socket;
    private BufferedReader input;
    private OutputStream output;


    public SocketWrapper(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "US-ASCII"));
        this.output = socket.getOutputStream();
    }

    public BufferedReader getInput() {
        return this.input;
    }

    public OutputStream getOutput() {
        return this.output;
    }

    public void close() throws IOException {
        output.close();
        input.close();
        socket.close();
    }
}
