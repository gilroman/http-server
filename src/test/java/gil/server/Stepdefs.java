package gradle.cucumber;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gil.server.http.HTTPProtocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Stepdefs {
    HttpURLConnection requestConnection;
    String requestProtocol = "http";
    String localhost = "127.0.0.1";
    int port = 4040;

    public String readResponseBody(BufferedReader connectionInput) throws IOException {
        StringBuilder response = new StringBuilder();
        String line;

        while((line = connectionInput.readLine()) != null) {
            response.append(line);
        }

        return response.toString();
    }

    @When("^I send a simple GET request$")
    public void iSendASimpleGetRequest() throws IOException {
        URL url = new URL(requestProtocol, localhost, port, "/");
        requestConnection = (HttpURLConnection) url.openConnection();
        requestConnection.setRequestMethod(HTTPProtocol.GET);
        requestConnection.connect();
    }

    @Then("I get a response of {string}")
    public void iGetAResponseOf(String string) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                requestConnection.getInputStream()));

        String response = readResponseBody(in);

        requestConnection.disconnect();

        assertTrue(response.contains(string));
    }

    @When("I send a simple GET request to {string}")
    public void iSendASimpleGETRequestTo(String endpoint) throws IOException {
        URL url = new URL(requestProtocol, localhost, port, endpoint);
        requestConnection = (HttpURLConnection) url.openConnection();
        requestConnection.setRequestMethod(HTTPProtocol.GET);
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
        requestConnection.setRequestMethod(HTTPProtocol.GET);
        requestConnection.connect();
    }

    @Then("I get an HTTP response with a message body containing {string}")
    public void iGetAnHTTPResponseWithAMessageBodyOf(String parameters) throws IOException {
        String response;
        BufferedReader in = new BufferedReader(new InputStreamReader(
                requestConnection.getInputStream()));
        response = readResponseBody(in);
        requestConnection.disconnect();
        assertTrue(response.contains(parameters));
    }

    @When("I send a GET request with multiple query parameters to {string}")
    public void iSendAGETRequestWithMultipleQueryParametersTo(String endpoint) throws IOException {
        URL url = new URL(requestProtocol, localhost, port, endpoint);
        requestConnection = (HttpURLConnection) url.openConnection();
        requestConnection.setRequestMethod(HTTPProtocol.GET);
        requestConnection.connect();
    }

    @Then("I get an HTTP response with a message body containing {string} and {string}")
    public void iGetAHTTPResponseWithAMessageBodyOf(String parameter1, String parameter2) throws IOException {
        String response;
        BufferedReader in = new BufferedReader(new InputStreamReader(
                requestConnection.getInputStream()));
        response = readResponseBody(in);
        in.close();
        requestConnection.disconnect();
        assertTrue(response.contains(parameter1) && response.contains(parameter2));
    }

    @When("I send an OPTIONS request to {string}")
    public void iSendAnOPTIONSRequestTo(String endpoint) throws IOException {
        URL url = new URL(requestProtocol, localhost, port, endpoint);
        requestConnection = (HttpURLConnection) url.openConnection();
        requestConnection.setRequestMethod(HTTPProtocol.OPTIONS);
        requestConnection.connect();
    }

    @Then("I get an HTTP response with an Allow header field of {string}")
    public void iGetAnHTTPResponseWithAnAllowHeaderFieldOf(String header) {
        Map<String, List<String>> headerFields = requestConnection.getHeaderFields();
        requestConnection.disconnect();
        assertTrue(headerFields.get(HTTPProtocol.ALLOW).contains(header));
    }

    @When("I send a POST request to {string}")
    public void i_send_a_POST_request_to(String endpoint) throws IOException {
        String jsonString = "{\"name\": \"Gil\", \"email\": \"g@tdd.com\"}";
        URL url = new URL(requestProtocol, localhost, port, endpoint);
        requestConnection = (HttpURLConnection) url.openConnection();
        requestConnection.setDoOutput(true);
        requestConnection.setRequestMethod(HTTPProtocol.POST);
        OutputStream outputStream = requestConnection.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
        outputStreamWriter.write(jsonString);
        outputStreamWriter.flush();
        outputStreamWriter.close();
        outputStream.close();

        requestConnection.connect();
    }

    @Then("I get an HTTP response with status code {int} and content-type field of {string}")
    public void i_get_an_HTTP_response_with_status_code_and_content_type_field_of(Integer statusCode, String contentType) throws IOException {
        Integer responseCode = requestConnection.getResponseCode();
        Map<String, List<String>> headerFields = requestConnection.getHeaderFields();
        requestConnection.disconnect();
        assertEquals(statusCode, responseCode);
        assertTrue(headerFields.get(HTTPProtocol.CONTENT_TYPE).contains(contentType));
    }

}
