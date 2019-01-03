package gil.server;

import java.util.HashMap;

public class Request {
    private String body;
    private HashMap<String, String> headerFields;
    private String HTTPVersion;
    private String method;
    private String URI;

    public String getBody() {
        return this.body;
    }

    public HashMap<String, String> getHeaderFields() {
        return this.headerFields;
    }

    public String getHTTPVersion() { return this.HTTPVersion; }

    public String getMethod() { return this.method; }

    public String getURI() { return this.URI; }

    public void setBody(String body){
        this.body = body;
    }

    public void setHeaderFields(HashMap<String, String> requestHeaders){
        this.headerFields = requestHeaders;
    }

    public void setHttpVersion(String HTTPVersion) {
        this.HTTPVersion = HTTPVersion;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
