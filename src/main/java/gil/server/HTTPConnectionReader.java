package gil.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class HTTPConnectionReader {
    private static final String CRLF = "\r\n";

    public HashMap<String, String> readRequest(BufferedReader bufferedReader) throws IOException {
        HashMap<String, String> request = new HashMap<>();
        request.put("request-line", readRequestLine(bufferedReader));
        request.put("headers", readHeaders(bufferedReader));
        request.put("body", readBody(bufferedReader));

        System.out.println("the request is: " + request);

        return request;
    }

    private String readRequestLine(BufferedReader connectionInput) throws IOException {
        return connectionInput.readLine();
    }

    private String readHeaders(BufferedReader connectionInput) throws IOException {
        StringBuilder headers = new StringBuilder();
        String line;

        while((line = connectionInput.readLine()) != null) {
            if (line.isEmpty()) break;
            headers.append(line + CRLF);
        }

        return headers.toString();
    }

    private String readBody(BufferedReader connectionInput) throws IOException {
        StringBuilder body = new StringBuilder();
        int nothingToRead = -1;
        int integer;

        while (connectionInput.ready() && (integer = connectionInput.read()) != nothingToRead) {
            char character = (char) integer;
            body.append(character);
        }

        return body.toString();
    }
}
