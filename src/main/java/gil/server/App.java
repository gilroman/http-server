package gil.server;

public class App {
    public static void main(String[] args) {
        ServerArgumentsParser serverArgumentsParser = new ServerArgumentsParser();
        ServerSocketWrapperInterface serverSocket = new ServerSocketWrapper();

        try {
            int port;

            if (serverArgumentsParser.hasPortFlag(args)) {
                port = serverArgumentsParser.getPortFlagValue(args);
                Server server = new Server(serverSocket);
                server.start(port);
            }

        } catch (Exception e) {
            System.out.println("Please enter a valid port number: [1025 - 48999] to start the server.");
        }

    }
}
