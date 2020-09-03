import java.io.*;
import java.net.*;

public class IP {
    private final LowIP ip = new LowIP();

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

    public String getLocal(){
        try {
            return ip.getLocalIP();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Attention! There is no internet connection.";
    }
    public String getPublic(){
        try {
            return ip.getPublicIP();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Attention! There is no internet connection.";
    }
}