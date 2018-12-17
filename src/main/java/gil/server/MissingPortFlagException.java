package gil.server;

public class MissingPortFlagException extends Exception {
    public void MissingPortFlagException() {
        System.out.println("A port was not specified when starting the server.");
    }
}
