package gil.server;

public class ServerSocketMock implements ServerSocketWrapperInterface {
    private Boolean createAndListenCalled;
    private Boolean closeCalled;


    public void createAndListen(int port) {
        createAndListenCalled = true;
    }

    public void close() {
        closeCalled = true;
    }

    public boolean wasCreateAndListenCalled() {
        return createAndListenCalled;
    }

    public boolean wasCloseCalled() {
        return closeCalled;
    }
}
