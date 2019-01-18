package gil.server;

import java.util.HashMap;
import java.util.function.BiFunction;

public class Router {
    private HashMap<String, BiFunction<Request, Response, Response>> get = new HashMap<>();
    private HashMap<String, BiFunction<Request, Response, Response>> options = new HashMap<>();
    private enum METHOD {GET, OPTIONS};

    public Router() {
        Routes routes = new Routes();
        routes.addRoutes(this);
    }

    public void get(String route, BiFunction<Request, Response, Response> controller) {
        get.put(route, controller);
    }

    public void options(String route, BiFunction<Request, Response, Response> controller) {
        options.put(route, controller);
    }

    private HashMap<String, BiFunction<Request, Response, Response>> getMapForMethod(String methodName) {
        String method = methodName.toUpperCase();

        switch (METHOD.valueOf(method)) {
            case GET:
                return this.get;
            case OPTIONS:
                return this.options;
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
        String requestURI = request.getURI();
        HashMap<String, BiFunction<Request, Response, Response>> methodHash = getMapForMethod(requestMethod);
        BiFunction<Request, Response, Response> controller = this.get.get(Routes.ROUTE_NOT_FOUND);

        switch(METHOD.valueOf(requestMethod)) {
            case GET:
                if (StaticFileUtils.staticFileExists(requestURI)) {
                    controller = StaticFileHandler.get;
                }

                if (hasRoute(request)) {
                    controller = methodHash.get(requestURI);
                }
                break;
            case OPTIONS:
                controller = methodHash.get(Routes.STATIC_FILE_OPTIONS);
                break;
            default:
                controller = this.get.get(Routes.ROUTE_NOT_FOUND);
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
