package gil.server.router;

import gil.server.controllers.ParametersController;
import gil.server.controllers.PersonController;
import gil.server.controllers.RootEndpointController;
import gil.server.data.FileIO;
import gil.server.data.JSONDataStore;

public class Routes {
    static FileIO fio = new FileIO("people.txt");
    static JSONDataStore dataStore = new JSONDataStore(fio);
    static PersonController personController = new PersonController(dataStore);

    public void addRoutes(Router router) {
        router.get("/", RootEndpointController.get);
        router.get("/api/parameters", ParametersController.get);
        router.get("/api/people/[0-9]+", personController.get);
        router.post("/api/people", personController.post);
    }
}
