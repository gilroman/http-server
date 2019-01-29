package gil.server;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private String SPACE = " ";
    private String headerKeyValueSeparator = ": ";
    private String body;
    private HashMap<String, String> headerFields;
    private String protocol;
    private String reasonPhrase;
    private String statusCode;

    public Response() {
        headerFields = new HashMap<>();
        setDate();
        setBody("");
    }

    public String getBody() {
        return body;
    }

    public String getHeaders() {
        StringBuilder headers = new StringBuilder();

        for(Map.Entry<String, String> header : this.headerFields.entrySet()) {
            headers.append(header.getKey() + headerKeyValueSeparator + header.getValue() + HTTPProtocol.CRLF);
        }

        return headers.toString();
    }

    public String getProtocol() {
        return protocol;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public String getStartLine() {
        return protocol + SPACE + statusCode + SPACE + reasonPhrase;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setBody(String body) {
        this.body = body;
        setContentLength(this.body);
    }

    private void setContentLength (String body) {
        byte[] responseBytes = body.getBytes();
        this.headerFields.put(HTTPProtocol.CONTENT_LENGTH, String.valueOf(responseBytes.length));
    }

    private void setDate() {
        ZonedDateTime currentUTCDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        String RFC1123FormattedDate = DateTimeFormatter.RFC_1123_DATE_TIME.format(currentUTCDateTime);
        this.headerFields.put(HTTPProtocol.DATE, RFC1123FormattedDate);
    }

    public void addHeader(String headerName, String headerValue) {
        this.headerFields.put(headerName, headerValue);
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
