package gil.server;

public class Routes {
    public static final String ROUTE_NOT_FOUND = "route-not-found";
    public static final String STATIC_FILE_OPTIONS = "static-file-options";

    public void addRoutes(Router router) {
        router.get(ROUTE_NOT_FOUND, RouteNotFoundController.get);
        router.get("/", RootEndpointController.get);
        router.get("/api/parameters", ParametersController.get);
        router.post("/api/people", PostPersonController.post);
        router.options(STATIC_FILE_OPTIONS, StaticFileOptionsController.options);
    }
}
