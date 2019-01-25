package gil.server.controllers;

import gil.server.http.Request;
import gil.server.http.Response;
import gil.server.router.Router;
import java.util.function.BiFunction;

public class RouteOptionsController {
    public static BiFunction<Request, Response, Response> options =
            (request, response) -> {
                Router router = new Router();
                String allow = router.getOptions(request);
                response.setProtocol("HTTP/1.1");
                response.setStatusCode("200");
                response.setReasonPhrase("OK");
                response.setContentType("text/html; charset=UTF-8");
                response.setAllow(allow);
                response.setBody("");

                return response;
            };
}
