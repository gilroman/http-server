package gil.server;

import java.util.HashMap;
import java.util.function.BiFunction;

public class Router {
    private HashMap<String, BiFunction<Request, Response, Response>> get = new HashMap();
    private final String ROUTE_NOT_FOUND = "route-not-found";
    private enum METHOD {GET}
    ParametersController parametersMiddleware = new ParametersController();

    public Router() {
        Routes routes = new Routes();
        routes.addRoutes(this);
    }

    public void get(String route, BiFunction<Request, Response, Response> controller) {
        get.put(route, controller);
    }

    private HashMap<String, BiFunction<Request, Response, Response>> getMapForMethod(String methodName) {
        String method = methodName.toUpperCase();

        switch (METHOD.valueOf(method)) {
            case GET:
                return this.get;
        }

        return null;
    }

    public Boolean hasRoute(Request request) {
        String endpoint = request.getURI();
        String method = request.getMethod().toLowerCase();

        HashMap<String, BiFunction<Request, Response, Response>> methodHash = getMapForMethod(method);

        return methodHash.containsKey(endpoint);
    }

    public Response route(Request request) {
        BiFunction controller;
        Response response = new Response();
        String requestMethod = request.getMethod();
        HashMap<String, BiFunction<Request, Response, Response>> methodHash = getMapForMethod(requestMethod);

        if (hasRoute(request)){
            String requestURI = request.getURI();
            controller = methodHash.get(requestURI);
        } else {
            controller = methodHash.get(ROUTE_NOT_FOUND);
        }

        controller.apply(request, response);

        return response;
    }
}
