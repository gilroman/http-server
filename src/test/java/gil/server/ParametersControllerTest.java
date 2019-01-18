package gil.server;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import static org.junit.Assert.assertTrue;

public class ParametersControllerTest {
    ParametersController parametersController = new ParametersController();

    @Test
    public void shouldReceiveARequestWithParametersAndAddThemToTheBodyOfTheResponse() throws UnsupportedEncodingException {
        Request request = new Request();
        Response response = new Response();
        HashMap<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("hobby", "surfing");
        request.setHttpVersion("HTTP/1.1");
        request.setMethod("GET");
        request.setURI("/");
        request.setParameters(expectedParameters);

        parametersController.get.apply(request, response);
        String responseBody = response.getBody();

        assertTrue(responseBody.contains(expectedParameters.toString()));
    }
}
