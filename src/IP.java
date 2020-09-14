import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class IP {
    private final LowIP ip = new LowIP();

    public String getLocal() {
        try {
            return ip.getLocalIP();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return Main.resourceBundle.getString("ip.attention");
    }

    public String getPublic() {
        try {
            return ip.getPublicIP();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return Main.resourceBundle.getString("ip.attention");
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class LowIP {
        public String getLocalIP() throws IOException {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("google.com", 80));
            return socket.getLocalAddress().toString().replace("/", "");
        }

        public String getPublicIP() throws IOException {
            URL whatIsMyIP = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatIsMyIP.openStream()));
            return in.readLine();
        }
    }
}