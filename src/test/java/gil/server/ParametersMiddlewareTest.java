package gil.server;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import static org.junit.Assert.assertTrue;

public class ParametersMiddlewareTest {
    ParametersMiddleware parametersMiddleware = new ParametersMiddleware();

    @Test
    public void shouldReceiveARequestWithParametersAndAddThemToTheBodyOfTheResponse() throws UnsupportedEncodingException {
        Request request = new Request();
        Response response = new Response();
        HashMap<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("hobby", "surfing");
        response.setBody("");
        request.setParameters(expectedParameters);

        Response newResponse = parametersMiddleware.use.apply(request, response);
        String newResponseBody = newResponse.getBody();
        assertTrue(newResponseBody.contains(expectedParameters.toString()));
    }
}
