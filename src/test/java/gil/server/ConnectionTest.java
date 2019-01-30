package gil.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionTest {
    String data = "GET / HTTP/1.1\r\n";
    StringReader dataIn = new StringReader(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    BufferedReader bufferedReader = new BufferedReader(dataIn);
    SocketMock socketMock = new SocketMock(bufferedReader, outputStream);
    Connection connection = new Connection(socketMock);

    @Test
    public void shouldReadIncomingData() throws IOException {
        BufferedReader input = connection.getInput();
        String inputString = input.readLine() + "\r\n";
        assertEquals(data, inputString );
    }

    @Test
    public void shouldPrintOutgoingData() throws IOException {
        OutputStream output = connection.getOutput();
        String outgoingMessage = "Message sent...";

        output.write(outgoingMessage.getBytes());
        String dataOut = new String(outputStream.toByteArray());

        assertEquals(outgoingMessage, dataOut);
    }

    @Test
    public void shouldCloseSocketAndStreams() throws Exception {
        connection.close();
        assertTrue(socketMock.wasCloseCalled());
    }
}
