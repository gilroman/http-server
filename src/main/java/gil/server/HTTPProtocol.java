package gil.server;

import java.util.HashMap;
import java.util.Map;

public class HTTPProtocol {
    public static final String PROTOCOL = "HTTP/1.1";
    public static final String HTTP_VERSION = "1.1";
    public static final String ALLOW = "Allow";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String DATE = "Date ";
    public static final String LOCATION = "Location";
    public static final String CRLF = "\r\n";
    public static final String GET = "GET";
    public static final String OPTIONS = "OPTIONS";
    public static final String POST = "POST";
    public static final String STATUS_CODE_200 = "200";
    public static final String STATUS_CODE_201 = "201";
    public static final String STATUS_CODE_400 = "400";
    public static final String STATUS_CODE_404 = "404";
    public static final String REASON_PHRASE_OK = "OK";
    public static final String REASON_PHRASE_BAD_REQUEST = "Bad Request";
    public static final String REASON_PHRASE_CREATED = "Created";
    public static final String REASON_PHRASE_NOT_FOUND = "Not Found";
    public static Map<String, String> responsePhrases;

    static {
        responsePhrases= new HashMap<>();
        responsePhrases.put(STATUS_CODE_200, REASON_PHRASE_OK);
        responsePhrases.put(STATUS_CODE_201, REASON_PHRASE_CREATED);
        responsePhrases.put(STATUS_CODE_400, REASON_PHRASE_BAD_REQUEST);
        responsePhrases.put(STATUS_CODE_404, REASON_PHRASE_NOT_FOUND);
    }
}
