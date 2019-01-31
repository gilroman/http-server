package gil.server.controllers;

import gil.server.http.HTTPProtocol;
import gil.server.http.Request;
import gil.server.http.Response;
import gil.server.router.Routes;
import java.util.function.BiFunction;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class DirectoryController {

    static DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
        @Override
        public boolean accept(Path entry) {
            return !entry.toFile().isHidden();
        }
    };

    static String buildHTMLListItems(Request request) throws ResourceNotFoundException {
        MessageFormat linkFormat = new MessageFormat("<li><a href=\"{0}\">{1}</a></li>");
        Integer indexAfterForwardSlash = 1;
        String uri = request.getURI().substring(indexAfterForwardSlash);
        StringBuilder listItemBuilder = new StringBuilder();

        try {
            Files.newDirectoryStream(Paths.get(uri), filter)
                    .forEach(entry -> {
                        String link = "/" + entry.toString();
                        String linkLabel = entry.getFileName().toString();

                        if (entry.toFile().isFile()) {
                            link = uri.substring(Routes.PUBLIC_FOLDER.length()) + "/" + entry.getFileName().toString();
                        }

                        listItemBuilder.append(linkFormat.format(new Object[]{link, linkLabel}));
                    });
        } catch (IOException e) {
            throw new ResourceNotFoundException();
        }

        return listItemBuilder.toString();
    }

    public static BiFunction<Request, Response, Response> get = (request, response) -> {

        try {
            String listItems = buildHTMLListItems(request);
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("<html>")
                    .append("<head></head>")
                    .append("<body>")
                    .append("<ul>")
                    .append(listItems)
                    .append("</ul>")
                    .append("</body>")
                    .append("</html>");

            String body = bodyBuilder.toString();

            response.setProtocol(HTTPProtocol.PROTOCOL);
            response.setStatusCode(HTTPProtocol.STATUS_CODE_200);
            response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_OK);
            response.addHeader(HTTPProtocol.CONTENT_TYPE,"text/html; charset=UTF-8");
            response.setBody(body.getBytes());
        } catch (ResourceNotFoundException e) {
            response.setProtocol(HTTPProtocol.PROTOCOL);
            response.setStatusCode(HTTPProtocol.STATUS_CODE_404);
            response.setReasonPhrase(HTTPProtocol.REASON_PHRASE_NOT_FOUND);
            response.addHeader(HTTPProtocol.CONTENT_TYPE,"text/html; charset=UTF-8");
        }

        return response;
    };
}
