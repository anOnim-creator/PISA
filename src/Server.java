import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;

public class Server implements Runnable {
    private final Cipher cipher;
    private final String ipAddress;
    public int port = 4285;

    public Server(String ipAddress, Cipher cipher) {
        this.ipAddress = ipAddress;
        this.cipher = cipher;
    }

    @Override
    public synchronized void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                if (socket.getInetAddress().toString().replace("/", "").equals(ipAddress)) {
                    new Thread(new ClientHandler(socket)).start();
                    System.out.println(Main.resourceBundle.getString("server.connection")
                            + " " + socket.getInetAddress().toString().replace("/", "")
                            + "\n\n");
                    break;
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        ObjectInputStream objectInputStream;
        Socket socket;

        public ClientHandler(Socket socket) {
            try {
                this.socket = socket;
                objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }

        public synchronized void go() {
            Message message;

            try {
                cipher.setPublicKey((PublicKey) objectInputStream.readObject());
            } catch (IOException | ClassNotFoundException e) {
                //e.printStackTrace();
            }

            try {
                while ((message = (Message) objectInputStream.readObject()) != null) {
                    message.decrypt(cipher, cipher.getPrivateMyKey());
                    if (!message.checkHash()) System.out.println(Main.resourceBundle.getString("server.spoofing"));
                    System.out.print(Main.resourceBundle.getString("server.interlocutor") + " ");
                    System.out.println(message.toString());
                }
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println(Main.resourceBundle.getString("server.exit1") + "\n" + Main.resourceBundle.getString("server.exit2"));
                System.exit(0);
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
                System.out.println(Main.resourceBundle.getString("server.error"));
            }
        }

        @Override
        public synchronized void run() {
            go();
        }
    }
}
