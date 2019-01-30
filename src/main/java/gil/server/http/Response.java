package gil.server.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private String SPACE = " ";
    private String headerKeyValueSeparator = ": ";
    private byte[] body;
    private HashMap<String, String> headerFields;
    private String protocol;
    private String reasonPhrase;
    private String statusCode;

    public Response() {
        headerFields = new HashMap<>();
        setDate();
        setBody("".getBytes());
    }

    public byte[] getBody() {
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

    public void addHeader(String headerName, String headerValue) {
        this.headerFields.put(headerName, headerValue);
    }

    public void setBody(byte[] body) {
        this.body = body;
        setContentLength(this.body.length);
    }

    private void setContentLength (int bodyLength) {
        this.headerFields.put(HTTPProtocol.CONTENT_LENGTH, String.valueOf(bodyLength));
    }

    private void setDate() {
        ZonedDateTime currentUTCDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        String RFC1123FormattedDate = DateTimeFormatter.RFC_1123_DATE_TIME.format(currentUTCDateTime);
        this.headerFields.put(HTTPProtocol.DATE, RFC1123FormattedDate);
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        this.reasonPhrase = HTTPProtocol.responsePhrases.get(statusCode);
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

        try {
            responseStream.write(getStartLine().getBytes());
            responseStream.write(HTTPProtocol.CRLF.getBytes());
;            responseStream.write(getHeaders().getBytes());
            responseStream.write(HTTPProtocol.CRLF.getBytes());
            responseStream.write(getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseStream.toByteArray();
    }
}
