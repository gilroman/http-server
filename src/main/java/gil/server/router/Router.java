package gil.server.router;

import gil.server.controllers.*;
import gil.server.http.Request;
import gil.server.http.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.function.BiFunction;

public class Router {
    private HashMap<String, BiFunction<Request, Response, Response>> get = new HashMap<>();
    private HashMap<String, BiFunction<Request, Response, Response>> post = new HashMap<>();
    private enum METHOD {GET, OPTIONS, POST};

    public Router() {
        Routes routes = new Routes();
        routes.addRoutes(this);
    }

    public void get(String route, BiFunction<Request, Response, Response> controller) {
        get.put(route, controller);
    }

    public void post(String route, BiFunction<Request, Response, Response> controller) {
        post.put(route, controller);
    }

    private HashMap<String, BiFunction<Request, Response, Response>> getMapForMethod(String methodName) {
        String method = methodName.toUpperCase();

        switch (METHOD.valueOf(method)) {
            case GET:
                return this.get;
            case POST:
                return this.post;
        }

        return null;
    }

    public Optional<BiFunction<Request, Response, Response>> getRouteController(Request request) {
        String method = request.getMethod().toLowerCase();

        HashMap<String, BiFunction<Request, Response, Response>> methodHash = getMapForMethod(method);

        Optional<BiFunction<Request, Response, Response>> controller = methodHash.entrySet()
                      .stream()
                      .filter( entry -> routeMatches(request, entry.getKey()))
                      .map(Map.Entry::getValue)
                      .findFirst();

        return controller;
    }

    private BiFunction<Request, Response, Response> getController(Request request) {
        String requestMethod = request.getMethod();
        String requestURI = request.getURI();
        HashMap<String, BiFunction<Request, Response, Response>> methodHash = getMapForMethod(requestMethod);
        BiFunction<Request, Response, Response> controller = RouteNotFoundController.get;

        switch(METHOD.valueOf(requestMethod)) {
            case GET:
                if (StaticFileUtils.staticFileExists(requestURI)) {
                    controller = StaticFileHandler.get;
                }

                Optional<BiFunction<Request, Response, Response>> controllerOptional = getRouteController(request);

                if (controllerOptional.isPresent()) {
                    controller = controllerOptional.get();
                }
                break;
            case OPTIONS:
                if (StaticFileUtils.staticFileExists(requestURI)) {
                    controller = StaticFileOptionsController.options;
                }
                else {
                    controller = RouteOptionsController.options;
                }
                break;
            case POST:
                controller = methodHash.get(requestURI);
                break;
            default:
                controller = RouteNotFoundController.get;
        }

        return controller;
    }

    public Response route(Request request) {
        Response response = new Response();
        BiFunction<Request, Response, Response> controller = getController(request);

        controller.apply(request, response);

        return response;
    }

    public Boolean routeMatches(Request request, String route) {
        String uri = request.getURI();
        Pattern pattern = Pattern.compile(route);
        Matcher matcher = pattern.matcher(uri);
        boolean isMatch = matcher.matches();

        return isMatch;
    }

    public String getOptions(Request request) {
        String options = "OPTIONS";
        String uri = request.getURI();

        List<HashMap<String, BiFunction<Request, Response, Response>>> methodHashes = new ArrayList<>();
        methodHashes.add(this.get);
        methodHashes.add(this.post);

        Optional<String> getRoute = this.get.entrySet()
                .stream()
                .filter( entry -> routeMatches(request, entry.getKey()))
                .map(Map.Entry::getKey)
                .findFirst();

        if (getRoute.isPresent() || StaticFileUtils.staticFileExists(uri)){
            options = options.concat(", GET");
        }

        Optional<String> postRoute = this.post.entrySet()
                .stream()
                .filter( entry -> this.post.containsKey(request.getURI()))
                .map(Map.Entry::getKey)
                .findFirst();

        if (postRoute.isPresent()){
            options = options.concat(", POST");
        }

        return options;
    }
}
