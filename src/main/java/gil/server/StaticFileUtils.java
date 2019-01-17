package gil.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;
import java.util.List;

public class StaticFileUtils {

  public static String STATIC_FILE_PATH = "public/";

  public static Boolean staticFileExists(String filePath) {
    String decodedPath = decodePath(filePath);
    File file = new File(decodedPath);
    return file.exists();
  }

  private static String decodePath(String filePath) {
    return URLDecoder.decode(STATIC_FILE_PATH.concat(filePath), StandardCharsets.UTF_8);
  }

  public static String getFileContent(String filePath) throws IOException {
    String decodedPath = decodePath(filePath);
    File file = new File(decodedPath);

    StringBuilder contentBuilder = new StringBuilder();
    List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.US_ASCII);

    for(String line: lines) {
        contentBuilder.append(line);
    }

    return contentBuilder.toString();
  }
}
