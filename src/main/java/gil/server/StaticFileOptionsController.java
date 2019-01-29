package gil.server;

import java.net.HttpURLConnection;
import java.util.function.BiFunction;

public class StaticFileOptionsController {
    public static BiFunction<Request, Response, Response> options =
            (request, response) -> {
                String uri = request.getURI();

                if(StaticFileUtils.staticFileExists(uri)) {
                    response.setProtocol(HTTPProtocol.PROTOCOL);
                    response.setStatusCode(HTTPProtocol.STATUS_CODE_200);
                    response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);
                    response.addHeader(HTTPProtocol.CONTENT_TYPE,"text/html; charset=UTF-8");
                    response.addHeader(HTTPProtocol.ALLOW, "OPTIONS, GET");
                } else {
                    RouteNotFoundController.get.apply(request, response);
                }

                return response;
            };
}
