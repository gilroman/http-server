package gil.server;

import java.io.*;
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
        System.out.println("Server is listening to connections on port: " + Integer.toString(port));
        listenForConnections();
    }

    private void handleConnection(Socket connection) throws Exception {
        connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        connectionOutput = new PrintWriter(connection.getOutputStream());
        processRequest(connectionInput, connectionOutput);
        close();
    }

    private void processRequest(BufferedReader connectionInput, PrintWriter connectionOutput) throws Exception {
        Response helloWorld = new Response();
        connectionOutput.println(helloWorld.getStartLine());
        connectionOutput.println(helloWorld.getDate());
        connectionOutput.println(helloWorld.getContentType());
        connectionOutput.println(helloWorld.getContentLength());
        connectionOutput.println();
        connectionOutput.println(helloWorld.getBody());
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