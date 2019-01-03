package gradle.cucumber;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Stepdefs {
    HttpURLConnection requestConnection;
    String requestProtocol = "http";
    String localhost = "127.0.0.1";
    int port = 4040;

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
        requestConnection.disconnect();
        assertTrue(response.contains(string));
    }

    @When("I send a simple GET request to {string}")
    public void i_send_a_simple_GET_request_to(String endpoint) throws IOException {
        URL url = new URL(requestProtocol, localhost, port, endpoint);
        requestConnection = (HttpURLConnection) url.openConnection();
        requestConnection.setRequestMethod("GET");
        requestConnection.connect();
    }

    @Then("I get an HTTP response with status code {int}")
    public void i_get_an_HTTP_response_with_status_code(int code) throws IOException {
        int statusCode = requestConnection.getResponseCode();
        requestConnection.disconnect();
        assertEquals(code, statusCode);
    }

}
