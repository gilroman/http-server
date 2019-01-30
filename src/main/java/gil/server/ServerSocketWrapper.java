package gil.server;

import gil.server.http.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWrapper implements ServerSocketWrapperInterface {
    private ServerSocket serverSocket;
    private Handler handler = new Handler();

    private void createServerSocket(int port) throws Exception {
        serverSocket = new ServerSocket(port);
    }

    private void listenForConnections() throws Exception {
        while(true){
            Socket socket = serverSocket.accept();
            SocketWrapper wrappedSocket = new SocketWrapper(socket);
            Connection connection = new Connection(wrappedSocket);
            handleConnection(connection);
        }
    }

    public void createAndListen(int port) throws Exception {
        createServerSocket(port);
        System.out.println("Server is listening to connections on port: " + port);
        listenForConnections();
    }

    private void handleConnection(Connection connection) throws Exception {
        processRequest(connection);
        connection.close();
    }

    private void processRequest(Connection connection) throws Exception {
        BufferedReader input = connection.getInput();
        OutputStream output = connection.getOutput();
        Response response = handler.processRequest(input);
        sendResponse(output, response);
    }

    private void sendResponse(OutputStream output, Response response) throws IOException {
        output.write(response.toByteArray());
    }

    public void close() throws Exception {
        serverSocket.close();
    }
}
