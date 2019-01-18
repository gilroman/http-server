package gil.server;

import java.io.UnsupportedEncodingException;
import java.util.function.BiFunction;

public class StaticFileOptionsController {
    public static BiFunction<Request, Response, Response> options =
            (request, response) -> {
                String uri = request.getURI();

                if(StaticFileUtils.staticFileExists(uri)) {
                    String allow = "Allow: OPTIONS, GET";
                    response.setProtocol("HTTP/1.1");
                    response.setStatusCode("200");
                    response.setReasonPhrase("OK");
                    response.setContentType("text/html; charset=UTF-8");
                    response.setAllow(allow);
                    try {
                        response.setBody("");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    RouteNotFoundController.get.apply(request, response);
                }

                return response;
            };
}
