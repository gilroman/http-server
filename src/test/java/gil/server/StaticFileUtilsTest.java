package gil.server;

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
    public void shouldObtainStaticFileContent() throws IOException {
        String expectedContent = "The title of this text file has spaces.";
        String content = StaticFileUtils.getFileContent("test.txt");

        assertEquals(expectedContent, content);
    }
}