package gil.server;

import java.util.HashMap;

public class Request {
    private HashMap<String, String> requestLine;
    private HashMap<String, String> headerFields;
    private String body;


    public Request(String request) {
        requestLine = new HashMap<>();
        headerFields = new HashMap<>();
        parseRequest(request);
    }

    public HashMap<String, String> getHeaderFields() {
        return this.headerFields;
    }

    public HashMap<String, String> getRequestLine() {
        return this.requestLine;
    }

    public String getBody() {
        return this.body;
    }

    public String getRequestURI() { return this.requestLine.get("Request-URI"); }

    private void parseRequest(String request){
        String[] headersAndBody = request.split("\n\n");
        String headers = headersAndBody[0];
        String body = headersAndBody.length >= 2 ? headersAndBody[1] : " ";
        String[] headersArray = headers.split("\n");
        String requestLine = headersArray[0];
        setRequestLine(requestLine);
        setHeaderFields(headersArray);
        setBody(body);
    }

    private void setHeaderFields(String[] headers){
        for (int i = 1; i < headers.length; i++) {
            String[] headerField = headers[i].split("(?<=:)\\s");
            headerFields.put(headerField[0], headerField[1]);
        }
    }

    private void setRequestLine(String requestLine) {
        String[] requestLineArray = requestLine.split("\\s");
        this.requestLine.put("Method", requestLineArray[0]);
        this.requestLine.put("Request-URI", requestLineArray[1]);
        this.requestLine.put("HTTP-Version", requestLineArray[2]);
    }

    private void setBody(String body){
        this.body = body;
    }

}
