package gil.server;

import java.io.UnsupportedEncodingException;

public class GetMethodRules {
    public static void success(Response response) {
            response.setProtocol("HTTP/1.1");
            response.setStatusCode("200");
            response.setReasonPhrase("OK");
            response.setContentType("Content-Type: text/html; charset=UTF-8");
    }

    public static void error(Response response) throws UnsupportedEncodingException {
            response.setProtocol("HTTP/1.1");
            response.setStatusCode("404");
            response.setReasonPhrase("Not Found");
            response.setContentType("Content-Type: text/html; charset=UTF-8");
            response.setBody("");
    }
}
