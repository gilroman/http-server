package gil.server;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class SocketMock implements SocketWrapperInterface {
        private Boolean closeCalled;
        private BufferedReader input;
        private PrintWriter output;

        public SocketMock(BufferedReader bufferedReader, PrintWriter printWriter) {
            this.input = bufferedReader;
            this.output = printWriter;
        }

        public BufferedReader getInput() {
            return this.input;
        }

        public PrintWriter getOutput() {
            return this.output;
        }

        public void close() {
            closeCalled = true;
        }

        public boolean wasCloseCalled() {
            return closeCalled;
        }
}
