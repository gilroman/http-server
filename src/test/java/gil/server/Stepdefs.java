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
    public void iSendASimpleGetRequest() throws IOException {
        URL url = new URL(requestProtocol, localhost, port, "/");
        requestConnection = (HttpURLConnection) url.openConnection();
        requestConnection.setRequestMethod("GET");
        requestConnection.connect();
    }

    @Then("I get a response of {string}")
    public void iGetAResponseOf(String string) throws IOException {
        String response;
        BufferedReader in = new BufferedReader(new InputStreamReader(
                requestConnection.getInputStream()));
        response = in.readLine();
        requestConnection.disconnect();
        assertTrue(response.contains(string));
    }

    @When("I send a simple GET request to {string}")
    public void iSendASimpleGETRequestTo(String endpoint) throws IOException {
        URL url = new URL(requestProtocol, localhost, port, endpoint);
        requestConnection = (HttpURLConnection) url.openConnection();
        requestConnection.setRequestMethod("GET");
        requestConnection.connect();
    }

    @Then("I get an HTTP response with status code {int}")
    public void iGetAnHTTPResponseWithStatusCode(int code) throws IOException {
        int statusCode = requestConnection.getResponseCode();
        requestConnection.disconnect();
        assertEquals(code, statusCode);
    }

    @When("I send a GET request with a single query to {string}")
    public void iSendGETRequestWithASingleQueryTo(String endpoint) throws IOException {
        URL url = new URL(requestProtocol, localhost, port, endpoint);
        requestConnection = (HttpURLConnection) url.openConnection();
        requestConnection.setRequestMethod("GET");
        requestConnection.connect();
    }

    @Then("I get an HTTP response with a message body containing {string}")
    public void iGetAnHTTPResponseWithAMessageBodyOf(String parameters) throws IOException {
        String response;
        BufferedReader in = new BufferedReader(new InputStreamReader(
                requestConnection.getInputStream()));
        response = in.readLine();
        requestConnection.disconnect();
        assertTrue(response.contains(parameters));
    }

    @When("I send a GET request with multiple query parameters to {string}")
    public void iSendAGETRequestWithMultipleQueryParametersTo(String endpoint) throws IOException {
        URL url = new URL(requestProtocol, localhost, port, endpoint);
        requestConnection = (HttpURLConnection) url.openConnection();
        requestConnection.setRequestMethod("GET");
        requestConnection.connect();
    }

    @Then("I get an HTTP response with a message body containing {string} and {string}")
    public void iGetAHTTPResponseWithAMessageBodyOf(String parameter1, String parameter2) throws IOException {
        String response;
        BufferedReader in = new BufferedReader(new InputStreamReader(
                requestConnection.getInputStream()));
        response = in.readLine();
        requestConnection.disconnect();
        assertTrue(response.contains(parameter1) && response.contains(parameter2));
    }
}
