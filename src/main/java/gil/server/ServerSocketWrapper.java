package gil.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWrapper implements ServerSocketWrapperInterface {
    private ServerSocket serverSocket;
    private Socket connection;
    private BufferedReader connectionInput;
    private PrintWriter connectionOutput;

    public void createServerSocket(int port) throws Exception {
        serverSocket = new ServerSocket(port);
    }

    public void listenForConnections() throws Exception {
        while(true){
            connection = serverSocket.accept();
            handleConnection(connection);
        }
    }

    public void createAndListen(int port) throws Exception {
        createServerSocket(port);
        System.out.println("Server is listening to connections on port: " + port);
        listenForConnections();
    }

    private void handleConnection(Socket connection) throws Exception {
        connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        connectionOutput = new PrintWriter(connection.getOutputStream());
        processRequest(connectionInput, connectionOutput);
        close();
    }

    private void processRequest(BufferedReader connectionInput, PrintWriter connectionOutput) throws Exception {
        Response response;
        String request = connectionInput.readLine();
        Handler handler = new Handler();
        response = handler.processRequest(request);
        connectionOutput.println(response.getStartLine());
        connectionOutput.println(response.getDate());
        connectionOutput.println(response.getContentType());
        connectionOutput.println(response.getContentLength());
        connectionOutput.println();
        connectionOutput.println(response.getBody());
    }

    public String receiveData() throws IOException {
        return connectionInput.readLine();
    }

    public void sendData(String data) {
        connectionOutput.println();
    }

    public void close() throws Exception {
        connectionOutput.close();
        connectionInput.close();
        connection.close();
    }
}