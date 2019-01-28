package gil.server.controllers;

import gil.server.http.Request;
import gil.server.http.Response;

import java.util.function.BiFunction;

public class StaticFileHandler {

    public static BiFunction<Request, Response, Response> get =
            (request, response) -> {
                String uri = request.getURI();
                String body = "";

                try {
                    switch(StaticFileUtils.getFileType(uri)) {
                        case "TEXT":
                            body = StaticFileUtils.getTextFileContent(uri);
                            break;
                    }

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
