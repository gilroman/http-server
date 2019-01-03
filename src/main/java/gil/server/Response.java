package gil.server;

import java.io.UnsupportedEncodingException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Response {
    private String CONTENT_LENGTH = "Content-Length: ";
    private String CONTENT_TYPE = "Content-Type: ";
    private String DATE = "Date: ";
    private String SPACE = " ";
    private String UTF8 = "UTF-8";
    private String body;
    private String contentLength;
    private String contentType = "Content-Type: ";
    private String date;
    private String protocol;
    private String reasonPhrase;
    private String statusCode;

    public Response() {
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
        return protocol + SPACE + statusCode + SPACE + reasonPhrase;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setBody(String body) throws UnsupportedEncodingException {
        this.body = body;
        setContentLength(this.body);
    }

    private void setContentLength (String body) throws UnsupportedEncodingException {
        byte[] responseBytes = body.getBytes(UTF8);
        this.contentLength = CONTENT_LENGTH + responseBytes.length;
    }

    public void setContentType (String body) {
        this.contentType = CONTENT_TYPE + body;
    }

    private void setDate() {
        ZonedDateTime currentUTCDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        String RFC1123FormattedDate = DateTimeFormatter.RFC_1123_DATE_TIME.format(currentUTCDateTime);
        this.date = DATE + RFC1123FormattedDate;
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
