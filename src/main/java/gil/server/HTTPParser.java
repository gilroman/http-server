package gil.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class HTTPParser {
    private HTTPConnectionReader connectionReader = new HTTPConnectionReader();
    private final String COLON = ":";
    private final String CRLF = "\r\n";
    private final String FORWARD_SLASH = "/";
    private final String HEADERS = "headers";
    private final String PROTOCOL = "HTTP";
    private final String REQUEST_LINE = "request-line";
    private final String SPACE = " ";

    public Boolean isRequestValid(String request) throws HTTPInvalidRequestFormatException {
        if (isEmpty(request) ||
                hasLeadingWhiteSpace(request) ||
                isAnHttpResponse(request)) {
            throw new HTTPInvalidRequestFormatException();
        }

        return true;
    }

    private Boolean hasLeadingWhiteSpace(String request) {
        return request.startsWith(SPACE);
    }

    private Boolean isAnHttpResponse(String request) {
        return request.substring(0, PROTOCOL.length()).contentEquals(PROTOCOL);
    }

    private Boolean isEmpty(String request) {
        return (request.length() == 0);
    }

    public String getHttpProtocol(String startLine) {
        String[] startLineArray = startLine.split(SPACE);
        String protocol = startLineArray[2];
        String[] protocolArray = protocol.split(FORWARD_SLASH);
        String protocolVersion = protocolArray[1];

        return protocolVersion;
    }

    public String getMethod(String startLine) {
        String[] startLineArray = startLine.split(SPACE);
        String method = startLineArray[0];

        return method;
    }

    public String getRequestURI(String startLine) {
        String[] startLineArray = startLine.split(SPACE);
        String URI = startLineArray[1];

        return URI;
    }

    public HashMap<String, String> getHeaders(String headers) {
        HashMap<String, String> headersHash = new HashMap<>();

        if (headers.length() > 0) {
            String[] headersArray = headers.split(CRLF);

            for (String header : headersArray) {
                int indexOfColon = header.indexOf(COLON);
                String key = header.substring(0, indexOfColon);
                String value = header.substring(indexOfColon + 1);
                headersHash.put(key.toLowerCase(), value.trim());
            }
        }
        return headersHash;
    }

    public Request parse(BufferedReader connectionInput) throws IOException {
        Request request = new Request();
        HashMap requestHash = connectionReader.readRequest(connectionInput);
        String requestLine = requestHash.get(REQUEST_LINE).toString();
        String headers = requestHash.get(HEADERS).toString();

        request.setURI(getRequestURI(requestLine));
        request.setMethod(getMethod(requestLine));
        request.setHttpVersion(getHttpProtocol(requestLine));
        request.setHeaderFields(getHeaders(headers));

        return request;
    }
}
