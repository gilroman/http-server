package gil.server;

import java.util.HashMap;
import java.util.function.BiFunction;

public class Router {
    private HashMap<String, BiFunction<Request, Response, Response>> get = new HashMap<>();
    private final String ROUTE_NOT_FOUND = "route-not-found";
    private enum METHOD {GET};

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

    private BiFunction<Request, Response, Response> getController(Request request) {
        String requestMethod = request.getMethod();
        HashMap<String, BiFunction<Request, Response, Response>> methodHash = getMapForMethod(requestMethod);
        BiFunction<Request, Response, Response> controller = methodHash.get(ROUTE_NOT_FOUND);

        if (StaticFileUtils.staticFileExists(request.getURI())) {
            controller = StaticFileHandler.get;
        }

        if (hasRoute(request)){
            String requestURI = request.getURI();
            controller = methodHash.get(requestURI);
        }

        return controller;
    }

    public Response route(Request request) {
        Response response = new Response();
        BiFunction<Request, Response, Response> controller = getController(request);

        controller.apply(request, response);

        return response;
    }
}
