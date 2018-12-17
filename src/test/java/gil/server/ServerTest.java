package gil.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class ServerTest {
    StringReader stringReader = new StringReader("Data coming in...");
    StringWriter stringWriter = new StringWriter();
    BufferedReader input = new BufferedReader(stringReader);
    PrintWriter output = new PrintWriter(stringWriter);
    ServerSocketMock serverSocketMock = new ServerSocketMock(input, output);
    Server server = new Server(serverSocketMock);

    @Test
    public void testServerStartMethodCallsStartInTheServerSocket() throws Exception {
        int PORT = 4040;
        server.start(PORT);
        assertTrue(serverSocketMock.wasCreateAndListenCalled());
    }

    @Test
    public void testDataIsReceived() throws IOException {
        assertEquals("Data coming in...", serverSocketMock.receiveData());
    }

    @Test
    public void testDataCanBeSent() {
        serverSocketMock.sendData("Data going out...");
        String outputString = stringWriter.toString();
        assertEquals("Data going out...", outputString);
    }

    @Test
    public void testCloseMethodOfSocketIsCalled() throws Exception {
        server.stop();
        assertTrue(serverSocketMock.wasCloseCalled());
    }

}
