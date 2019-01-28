package gil.server.controllers;

import java.io.IOException;

import org.junit.Test;
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
        String content = StaticFileUtils.getTextFileContent("test.txt");

        assertEquals(expectedContent, content);
    }

    @Test
    public void shouldReturnTheTypeOfAnImageFile() {
        String imageURI = "/img/kitty-300x300.jpg";
        String fileType = StaticFileUtils.getFileType(imageURI);

        assertEquals("IMAGE", fileType);
    }

    @Test
    public void shouldReturnTheTypeOfATextFile() {
        String imageURI = "/test.txt";
        String fileType = StaticFileUtils.getFileType(imageURI);

        assertEquals("TEXT", fileType);
    }

    @Test
    public void shouldObtainStaticImageFileContent() throws IOException {
        String expectedContent = "This is a test file.";
        String content = StaticFileUtils.getImageFileContent("/img/kitty-300x300.jpg");

        assertEquals(expectedContent, content);
    }
}
