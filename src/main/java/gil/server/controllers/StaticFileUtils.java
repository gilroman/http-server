package gil.server.controllers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

  public static String getTextFileContent(String filePath) throws IOException {
    String decodedPath = decodePath(filePath);
    File file = new File(decodedPath);

    StringBuilder contentBuilder = new StringBuilder();
    List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.US_ASCII);

    for(String line: lines) {
        contentBuilder.append(line);
    }

    return contentBuilder.toString();
  }

  public static String getFileType(String uri) {
    Integer indexOfFileTypeDelimiter = uri.indexOf(".");
    String fileExtension = uri.substring(indexOfFileTypeDelimiter);
    String fileType;

    switch(fileExtension){
      case ".jpg":
        fileType = "IMAGE";
        break;
      case ".txt":
        fileType = "TEXT";
        break;
      default:
        fileType = "UNKNOWN";
    }

    return fileType;
  }

  public static String getImageFileContent(String filePath) throws IOException {
    String decodedPath = decodePath(filePath);

    BufferedImage image = null;
    try {
      image = ImageIO.read(new File(decodedPath));
    } catch (IOException e) {
    }

    return image.toString();
  }
}
