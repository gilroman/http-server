package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import java.util.function.BiFunction;

public class RootEndpointController {
    public static BiFunction<Request, Response, Response> get =
            (request, response) -> {
                response.setProtocol(HTTPProtocol.PROTOCOL);
                response.setStatusCode(HTTPProtocol.STATUS_CODE_200);
                response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);
                response.addHeader(HTTPProtocol.CONTENT_TYPE, "text/html; charset=UTF-8");
                response.setBody("Hello, world!");

                return response;
            };
}
