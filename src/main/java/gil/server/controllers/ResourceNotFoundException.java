package gil.server.controllers;

public class ResourceNotFoundException extends Exception {
    public void ResourceNotFoundException() {
        System.out.println("A port was not specified when starting the server.");
    }
}
