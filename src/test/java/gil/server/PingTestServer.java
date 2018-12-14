package gil.server;

import java.io.IOException;
import java.net.InetAddress;

public class PingTestServer {
    public static String sendPingRequest(String ipAddress) throws IOException
    {
        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        System.out.println("Sending Ping Request to " + ipAddress);
        if (inetAddress.isReachable(5000))
            return "Server is reachable";
        else
            return "Sorry! We can't reach to this server";
    }
}
