package gil.server.controllers;

import gil.server.http.Request;
import gil.server.http.Response;

import java.util.function.BiFunction;

public class StaticFileHandler {
    public static String STATIC_FILE_PATH = "public";

    public static BiFunction<Request, Response, Response> get =
            (request, response) -> {
                String uri = request.getURI();

                try {
                    String body = StaticFileUtils.getFileContent(uri);

                    response.setProtocol("HTTP/1.1");
                    response.setStatusCode("200");
                    response.setReasonPhrase("OK");
                    response.setContentType("text/html; charset=UTF-8");
                    response.setBody(body);
                } catch (Exception e) {
                    RouteNotFoundController.get.apply(request, response);
                }

                return response;
            };
}
