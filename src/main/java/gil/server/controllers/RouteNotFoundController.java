package gil.server.controllers;

import gil.server.http.Request;
import gil.server.http.Response;

import java.util.function.BiFunction;

public class RouteNotFoundController {
    public static BiFunction<Request, Response, Response> get =
            (request, response) -> {
                response.setProtocol("HTTP/1.1");
                response.setStatusCode("404");
                response.setReasonPhrase("Not Found");
                response.setContentType("text/html; charset=UTF-8");
                response.setBody("");

                return response;
            };
}
