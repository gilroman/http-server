package gil.server;

public class Routes {
    private final String ROUTE_NOT_FOUND = "route-not-found";

    public void addRoutes(Router router) {
        router.get(ROUTE_NOT_FOUND, RouteNotFoundController.get);
        router.get("/", RootEndpointController.get);
    }
}
