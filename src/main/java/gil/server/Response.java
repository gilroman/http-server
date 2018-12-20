package gil.server;

import java.io.UnsupportedEncodingException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Response {
    private String protocol;
    private String statusCode;
    private String reasonPhrase;
    private String date;
    private String contentType;
    private String body;
    private String contentLength;

    public Response() throws UnsupportedEncodingException {
        setDate();
    }

    public String getBody() {
        return body;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentLength() {
        return contentLength;
    }

    public String getDate() {
        return date;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public String getStartLine() {
        return protocol + " " + statusCode + " " + reasonPhrase;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setBody(String body) throws UnsupportedEncodingException {
        this.body = body;
        setContentLength(this.body);
    }

    private void setContentLength (String body) throws UnsupportedEncodingException {
        byte[] responseBytes = body.getBytes("UTF-8");
        this.contentLength = "Content-Length: " + responseBytes.length;
    }

    public void setContentType (String body) {
        this.contentType = body;
    }

    private void setDate() {
        this.date = "Date: " + DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
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
