package gil.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.Socket;

public class SocketWrapper implements SocketWrapperInterface {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;


    public SocketWrapper(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "US-ASCII"));
        this.output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public BufferedReader getInput() {
        return this.input;
    }

    public PrintWriter getOutput() {
        return this.output;
    }

    public void close() throws IOException {
        output.close();
        input.close();
        socket.close();
    }
}
