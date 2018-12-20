package gil.server;

import java.io.UnsupportedEncodingException;

public class Handler {
    private Router router = new Router();

    public Response processRequest(String req) throws UnsupportedEncodingException {
        Request request = new Request(req);
        Response response;
        response = router.route(request);
        System.out.println(request.getRequestLine());
        return response;
    }
}
