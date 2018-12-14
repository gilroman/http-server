package gil.server;

public class Server {
    ServerSocketWrapperInterface serverSocket;

    public Server(ServerSocketWrapperInterface serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start(int port) throws Exception {
        serverSocket.createAndListen(port);
    }

    public void stop() throws Exception {
        serverSocket.close();
    }
}

