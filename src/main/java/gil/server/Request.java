package gil.server;

import java.util.HashMap;

public class Request {
    private String body;
    private HashMap<String, String> headerFields;
    private HashMap<String, String> parameters;
    private String HTTPVersion;
    private String method;
    private String URI;

    public Request() {
        setBody("");
    }

    public String getBody() {
        return this.body;
    }

    public HashMap<String, String> getHeaderFields() {
        return this.headerFields;
    }

    public String getHTTPVersion() { return this.HTTPVersion; }

    public String getMethod() { return this.method; }

    public HashMap<String, String> getParameters() {
        return this.parameters;
    }

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

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

}
