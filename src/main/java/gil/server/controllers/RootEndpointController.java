package gil.server.controllers;

import gil.server.http.Request;
import gil.server.http.Response;
import java.util.function.BiFunction;

public class RootEndpointController {
    public static BiFunction<Request, Response, Response> get =
            (request, response) -> {
                response.setProtocol("HTTP/1.1");
                response.setStatusCode("200");
                response.setReasonPhrase("OK");
                response.setContentType("text/html; charset=UTF-8");
                response.setBody("Hello, world!");

                return response;
            };
}
