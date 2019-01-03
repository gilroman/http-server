package gil.server;

public class ServerMissingPortFlagException extends Exception {
    public void ServerMissingPortFlagException() {
        System.out.println("A port was not specified when starting the server.");
    }
}
