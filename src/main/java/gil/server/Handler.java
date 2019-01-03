package gil.server;

import java.io.BufferedReader;
import java.io.IOException;

public class Handler {
    private Router router = new Router();
    private HTTPParser parser = new HTTPParser();

    public Response processRequest(BufferedReader input) throws IOException {
        Request request = parser.parse(input);
        Response response = router.route(request);
        return response;
    }
}
