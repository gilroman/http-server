package gil.server;

public class App {
    public static void main(String[] args) {
        ArgumentsParser argumentsParser = new ArgumentsParser();
        ServerSocketWrapperInterface serverSocket = new ServerSocketWrapper();

        try {
            int port;

            if (argumentsParser.hasPortFlag(args)) {
                port = argumentsParser.getPortFlagValue(args);
                Server server = new Server(serverSocket);
                server.start(port);
            }

        } catch (Exception e) {
            System.out.println("Please enter a valid port number: [1025 - 48999] to start the server.");
        }

    }
}
