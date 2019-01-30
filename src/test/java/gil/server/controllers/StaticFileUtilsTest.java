package gil.server.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import javax.imageio.ImageIO;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class StaticFileUtilsTest {
    @Test
    public void shouldVerifyFileExists()
    {
        assertTrue(StaticFileUtils.staticFileExists("test.txt"));
    }

    @Test
    public void shouldObtainStaticTextFileContent() throws IOException {
        String expectedContent = "This is a test file.";
        byte[] content = StaticFileUtils.getTextFileContent("test.txt");

        assertEquals(expectedContent, new String(content));
    }

    @Test
    public void shouldReturnTheTypeOfAnImageFile() {
        String imageURI = "/img/kitty-300x300.jpg";
        String fileType = StaticFileUtils.getFileType(imageURI);

        assertEquals("JPEG", fileType);
    }

    @Test
    public void shouldReturnTheTypeOfATextFile() {
        String imageURI = "/test.txt";
        String fileType = StaticFileUtils.getFileType(imageURI);

        assertEquals("TEXT", fileType);
    }

    @Test
    public void shouldObtainStaticImageFileContent() {
        String testUrlImageFilePath = "/img/kitty-300x300.jpg";
        String testImage = "public/img/kitty-300x300.jpg";
        BufferedImage image = null;
        byte[] expectedData = null;
        try {
            image = ImageIO.read(new File(testImage));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream );
            expectedData = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] imageBytes = StaticFileUtils.getImageFileContent(testUrlImageFilePath);

        assertArrayEquals(expectedData, imageBytes);
    }
}
