package gil.server;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionTest {
    String data = "GET / HTTP/1.1\r\n";
    StringReader dataIn = new StringReader(data);
    StringWriter dataOut = new StringWriter();
    PrintWriter printWriter = new PrintWriter(dataOut);
    BufferedReader bufferedReader = new BufferedReader(dataIn);
    SocketMock socketMock = new SocketMock(bufferedReader, printWriter);
    Connection connection = new Connection(socketMock);

    @Test
    public void shouldReadIncomingData() throws IOException {
        BufferedReader input = connection.getInput();
        String inputString = input.readLine() + "\r\n";
        assertEquals(data, inputString );
    }

    @Test
    public void shouldPrintOutgoingData() {
        PrintWriter output = connection.getOutput();
        String outgoingMessage = "Message sent...";
        output.print(outgoingMessage);
        assertEquals(outgoingMessage, dataOut.toString());
    }

    @Test
    public void shouldCloseSocketAndStreams() throws Exception {
        connection.close();
        assertTrue(socketMock.wasCloseCalled());
    }
}
