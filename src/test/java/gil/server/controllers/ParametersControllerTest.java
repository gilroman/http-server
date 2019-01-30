package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.assertTrue;

public class ParametersControllerTest {
    ParametersController parametersController = new ParametersController();

    @Test
    public void shouldReceiveARequestWithParametersAndAddThemToTheBodyOfTheResponse() {
        Request request = new Request();
        Response response = new Response();
        HashMap<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("hobby", "surfing");
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setMethod(HTTPProtocol.GET);
        request.setURI("/");
        request.setParameters(expectedParameters);

        parametersController.get.apply(request, response);
        String responseBody = new String(response.getBody());

        assertTrue(responseBody.contains(expectedParameters.toString()));
    }
}
