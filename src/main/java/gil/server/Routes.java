package gil.server;

public class Routes {

    public void addRoutes(Router router) {
        router.get("/", RootEndpointController.get);
        router.get("/api/parameters", ParametersController.get);
        router.post("/api/people", PostPersonController.post);
    }
}
