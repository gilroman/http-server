package gil.server;

public interface ServerSocketWrapperInterface {
    void createAndListen(int port) throws Exception;
    void close() throws Exception;
}
