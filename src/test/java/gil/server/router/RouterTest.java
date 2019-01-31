package gil.server.router;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import org.junit.BeforeClass;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class RouterTest {

    @Test
    public void shouldAllowSettingAnEndpointForGetMethod() {
        Router router = new Router();
        Request request = new Request();
        HashMap<String, String> emptyParameters = new HashMap();
        request.setURI("/");
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setMethod(HTTPProtocol.GET);
        request.setParameters(emptyParameters);
        router.get("/", (Request req, Response res) -> {
            res.setProtocol(HTTPProtocol.PROTOCOL);
            res.setStatusCode(HTTPProtocol.STATUS_CODE_200);
            res.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);
            return res;
        });

        Response response = router.route(request);

        assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
        assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
    }

    @Test
    public void shouldCallTheControllerFunctionOfAnEndpoint() {
        Router router = new Router();
        Request request = new Request();
        HashMap<String, String> emptyParameters = new HashMap();
        request.setURI("/");
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setMethod(HTTPProtocol.GET);
        request.setParameters(emptyParameters);
        router.get("/", (Request req, Response res) -> {
            res.setProtocol(HTTPProtocol.PROTOCOL);
            res.setStatusCode(HTTPProtocol.STATUS_CODE_200);
            res.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);
            res.setBody("Hello, World".getBytes());
            return res;
        });

        Response response = router.route(request);
        String responseBody = new String(response.getBody());

        assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
        assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
        assertTrue(responseBody.contains("Hello, World"));
    }

    @Test
    public void shouldCallRouteNotFoundControllerWhenRouteDoesNotExist() {
        Router router = new Router();
        Request request = new Request();
        HashMap<String, String> emptyParameters = new HashMap();
        request.setURI("/fake-endpoint");
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setMethod(HTTPProtocol.GET);
        request.setParameters(emptyParameters);

        Response response = router.route(request);

        assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
        assertEquals(HTTPProtocol.STATUS_CODE_404, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_NOT_FOUND, response.getReasonPhrase());
    }

    @Test
    public void shouldCallTheParameterController() {
        Router router = new Router();
        Request request = new Request();
        HashMap<String, String> expectedParameters = new HashMap<>();
        expectedParameters.put("hobby", "surfing");
        request.setMethod(HTTPProtocol.GET);
        request.setURI("/api/parameters");
        request.setParameters(expectedParameters);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);

        Response response = router.route(request);
        String responseBody = new String(response.getBody());

        assertTrue(responseBody.contains("hobby=surfing"));
    }

    @Test
    public void shouldCallTheStaticFileHandler() {
        Router router = new Router();
        Request request = new Request();
        request.setMethod(HTTPProtocol.GET);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/file%20with%20space.txt");

        Response response = router.route(request);

        assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
        assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
        assertEquals("The title of this text file has spaces.", new String(response.getBody()));
    }

   @Test
   public void shouldCallTheStaticFileOptionsController() {
       Router router = new Router();
       Request request = new Request();
       request.setMethod(HTTPProtocol.OPTIONS);
       request.setHttpVersion(HTTPProtocol.PROTOCOL);
       request.setURI("/file%20with%20space.txt");
       String expectedHeaderFields = "Allow: OPTIONS, GET";

       Response response = router.route(request);

       assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
       assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
       assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
       assertTrue(response.getHeaders().contains(expectedHeaderFields));
   }

    @Test
    public void shouldSayIfRequestURIMatchesRoute() {
        Router router = new Router();
        Request request = new Request();
        String route = "/api/people/[0-9]+";
        request.setMethod(HTTPProtocol.GET);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/api/people/1");

        assertTrue(router.routeMatches(request, route));
    }

    public static class routesToPersonControllerTest {
        private static Router router;
        private static Request request;
        private static Response response;

        @BeforeClass
        public static void saveAPersonToDataStore() {
            router = new Router();
            request = new Request();
            request.setMethod(HTTPProtocol.POST);
            request.setHttpVersion(HTTPProtocol.PROTOCOL);
            request.setURI("/api/people");
            request.setBody("{\"name\": \"Gil\", \"email\": \"g@tdd.com\"}");

            response = router.route(request);
        }

        @Test
        public void shouldCallThePersonPostController() {
            String expectedHeaderFields = "Location: /api/people/";

            assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
            assertEquals(HTTPProtocol.STATUS_CODE_201, response.getStatusCode());
            assertEquals(HTTPProtocol.REASON_PHRASE_CREATED, response.getReasonPhrase());
            assertTrue(response.getHeaders().contains(expectedHeaderFields));
        }

        @Test
        public void shouldCallThePersonController() {
            Router router = new Router();
            Response response;
            Request request = new Request();
            request.setMethod(HTTPProtocol.GET);
            request.setHttpVersion(HTTPProtocol.PROTOCOL);
            request.setURI("/api/people/0");

            response = router.route(request);

            assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
            assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
            assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
        }
    }

    @Test
    public void shouldReturnNamesOfMethodsDynamicRouteIsRegisteredToHandle() {
        Router router = new Router();
        Request request = new Request();
        request.setMethod(HTTPProtocol.OPTIONS);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/api/people/0");

        String options = router.getOptions(request);

        assertEquals("OPTIONS, GET", options);
    }

    @Test
    public void shouldReturnNamesOfMethodsStaticRouteIsRegisteredToHandle() {
        Router router = new Router();
        Request request = new Request();
        request.setMethod(HTTPProtocol.OPTIONS);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/api/people");

        String options = router.getOptions(request);

        assertEquals("OPTIONS, POST", options);
    }

    @Test
    public void shouldCallTheRouteOptionsController() {
        Router router = new Router();
        Response response;
        Request request = new Request();
        request.setMethod(HTTPProtocol.OPTIONS);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/api/people");
        String expectedAllow = "Allow: OPTIONS, POST";
        response = router.route(request);

        assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
        assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());
        assertTrue(response.getHeaders().contains(expectedAllow));
    }

    @Test
    public void shouldCallTheDirectoryController() {
        Router router = new Router();
        Response response;
        Request request = new Request();
        request.setMethod(HTTPProtocol.GET);
        request.setHttpVersion(HTTPProtocol.PROTOCOL);
        request.setURI("/public");
        String filePathNames[] = { "public/img",
                           "file with space.txt",
                           "test.txt" };

        response = router.route(request);

        assertEquals(HTTPProtocol.PROTOCOL, response.getProtocol());
        assertEquals(HTTPProtocol.STATUS_CODE_200, response.getStatusCode());
        assertEquals(HTTPProtocol.REASON_PHRASE_OK, response.getReasonPhrase());

        for(String filePathName: filePathNames) {
            assertTrue(new String(response.getBody()).contains(filePathName));
        }
    }
}
