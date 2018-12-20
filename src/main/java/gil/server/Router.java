package gil.server;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.function.Function;

public class Router {
    private HashMap<String, Function<Response, Response>> routes;

    public Router() {
        routes = new HashMap<>();
        routes.put("/", (Response response) -> {
            try {
                GetMethodRules.success(response);
                response.setBody("<!DOCTYPE html><html lang=\"en-us\"><head></head><body><h1>Hello, world!</h1></body></html>");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return response;
        });
    }

    public Boolean hasRoute(String endpoint) {
        return routes.containsKey(endpoint);
    }

    public Response route(Request request) throws UnsupportedEncodingException {
        Response response = new Response();
        String requestURI = request.getRequestURI();
        if (hasRoute(requestURI)){
            Function controller = routes.get(requestURI);
            controller.apply(response);
        } else {
            GetMethodRules.error(response);
        }
        return response;
    }
}
