package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import java.util.function.BiFunction;

public class RouteNotFoundController {
    public static BiFunction<Request, Response, Response> get =
            (request, response) -> {
                response.setProtocol(HTTPProtocol.PROTOCOL);
                response.setStatusCode(HTTPProtocol.STATUS_CODE_404);
                response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_NOT_FOUND);
                response.addHeader(HTTPProtocol.CONTENT_TYPE,"text/html; charset=UTF-8");

                return response;
            };
}
