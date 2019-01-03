package gil.server;

public class HTTPInvalidRequestFormatException extends Exception {
    public void HTTPInvalidRequestFormatException() {
        System.out.println("HTTP Request is not formatted correctly.");
    }
}
