package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import gil.server.router.Router;
import java.util.function.BiFunction;

public class RouteOptionsController {
    public static BiFunction<Request, Response, Response> options =
            (request, response) -> {
                Router router = new Router();
                String allow = router.getOptions(request);

                response.setProtocol(HTTPProtocol.PROTOCOL);
                response.setStatusCode(HTTPProtocol.STATUS_CODE_200);
                response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);
                response.addHeader(HTTPProtocol.CONTENT_TYPE, "text/html; charset=UTF-8");
                response.addHeader(HTTPProtocol.ALLOW, allow);
                response.setBody("");

                return response;
            };
}
