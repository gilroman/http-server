package gradle.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

import gil.server.PingTestServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Stepdefs {
    HttpURLConnection requestConnection;
    String requestProtocol = "http";
    String localhost = "127.0.0.1";
    int port = 4040;

    @Given("^the server is running$")
    public void the_server_is_running() throws IOException {
        assertEquals("Server is reachable", PingTestServer.sendPingRequest(localhost));
    }

    @When("^I send a simple GET request$")
    public void i_send_a_simple_get_request() throws IOException {
        URL url = new URL(requestProtocol, localhost, port, "/");
        requestConnection = (HttpURLConnection) url.openConnection();
        requestConnection.setRequestMethod("GET");
        requestConnection.connect();
    }

    @Then("I get a response of {string}")
    public void i_get_a_response_of(String string) throws IOException {
        String response;
        BufferedReader in = new BufferedReader(new InputStreamReader(
                requestConnection.getInputStream()));
        response = in.readLine();
        System.out.println("The server's response is: " + response);
        requestConnection.disconnect();
        assertTrue(response.contains(string));
    }
}

