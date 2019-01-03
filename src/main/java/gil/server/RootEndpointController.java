package gil.server;

import java.io.UnsupportedEncodingException;
import java.util.function.BiFunction;

public class RootEndpointController {
    public static BiFunction<Request, Response, Response> get =
            (request, response) -> {
                response.setProtocol("HTTP/1.1");
                response.setStatusCode("200");
                response.setReasonPhrase("OK");
                response.setContentType("text/html; charset=UTF-8");
                try {
                    response.setBody("Hello, world!");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return response;
            };
}
