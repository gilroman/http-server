package gil.server;

import java.io.UnsupportedEncodingException;
import java.util.function.BiFunction;

public class ParametersMiddleware {
    public static BiFunction<Request, Response, Response> use =
            (request, response) -> {
                String currentResponseBody = response.getBody() == null ? "" : response.getBody();
                String responseParameters = request.getParameters().toString();
                Boolean requestHasParameters = responseParameters.length() > 2;
                String newBody = requestHasParameters ? currentResponseBody + responseParameters : currentResponseBody;

                try {
                    response.setBody(newBody);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return response;
            };
}
