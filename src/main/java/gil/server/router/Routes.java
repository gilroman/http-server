package gil.server.router;

import gil.server.controllers.*;
import gil.server.data.FileIO;
import gil.server.data.JSONDataStore;
import gil.server.router.Router;

public class Routes {
    static FileIO fio = new FileIO("people.txt");
    static JSONDataStore dataStore = new JSONDataStore(fio);
    static PersonController personController = new PersonController(dataStore);

    public static final String ROUTE_NOT_FOUND = "route-not-found";
    public static final String STATIC_FILE_OPTIONS = "static-file-options";

    public void addRoutes(Router router) {
        router.get(ROUTE_NOT_FOUND, RouteNotFoundController.get);
        router.get("/", RootEndpointController.get);
        router.get("/api/parameters", ParametersController.get);
        router.get("/api/people/[0-9]+", personController.get);
        router.post("/api/people", PostPersonController.post);
        router.options(STATIC_FILE_OPTIONS, StaticFileOptionsController.options);
    }
}
