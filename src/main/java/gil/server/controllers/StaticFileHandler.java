package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import java.util.function.BiFunction;

public class StaticFileHandler {
    public static BiFunction<Request, Response, Response> get =
            (request, response) -> {
                String uri = request.getURI();
                String contentType = "";
                byte[] body = "".getBytes();

                try {
                    switch(StaticFileUtils.getFileType(uri)) {
                        case "TEXT":
                            body = StaticFileUtils.getTextFileContent(uri);
                            contentType = "text/html; charset=UTF-8";
                            break;
                        case "JPEG":
                            body = StaticFileUtils.getImageFileContent(uri);
                            contentType = "image/jpeg";
                            break;
                    }

                    response.setProtocol(HTTPProtocol.PROTOCOL);
                    response.setStatusCode(HTTPProtocol.STATUS_CODE_200);
                    response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);
                    response.addHeader(HTTPProtocol.CONTENT_TYPE, contentType);
                    response.setBody(body);
                } catch (Exception e) {
                    RouteNotFoundController.get.apply(request, response);
                }

                return response;
            };
}
