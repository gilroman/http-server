package gil.server;

import java.io.BufferedReader;
import java.io.OutputStream;

public class SocketMock implements SocketWrapperInterface {
        private Boolean closeCalled;
        private BufferedReader input;
        private OutputStream output;

        public SocketMock(BufferedReader bufferedReader, OutputStream outputStream) {
            this.input = bufferedReader;
            this.output = outputStream;
        }

        public BufferedReader getInput() {
            return this.input;
        }

        public OutputStream getOutput() {
            return this.output;
        }

        public void close() {
            closeCalled = true;
        }

        public boolean wasCloseCalled() {
            return closeCalled;
        }
}
