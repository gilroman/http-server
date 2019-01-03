package gil.server;


import org.junit.Test;
import static org.junit.Assert.assertTrue;


public class ServerTest {
    ServerSocketMock serverSocketMock = new ServerSocketMock();
    Server server = new Server(serverSocketMock);

    @Test
    public void testServerStartMethodCallsStartInTheServerSocket() throws Exception {
        int PORT = 4040;
        server.start(PORT);
        assertTrue(serverSocketMock.wasCreateAndListenCalled());
    }

    @Test
    public void testCloseMethodOfSocketIsCalled() throws Exception {
        server.stop();
        assertTrue(serverSocketMock.wasCloseCalled());
    }
}
