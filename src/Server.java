import java.io.*;
import java.net.*;
import java.security.PublicKey;

public class Server implements Runnable{
    private final Cipher cipher;
    public int port = 4285;

    public Server(Cipher cipher){
        this.cipher = cipher;
    }

    @Override
    public synchronized void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        ObjectInputStream objectInputStream;
        Socket socket;

        public ClientHandler(Socket socket){
            try {
                this.socket = socket;
                objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public synchronized void go(){
            Message message;

            try {
                cipher.setPublicKey((PublicKey) objectInputStream.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                while ((message = (Message) objectInputStream.readObject()) != null) {
                    message.decrypt(cipher, cipher.getPrivateMyKey());
                    if (!message.checkHash()) System.out.println("The hash does not match! Possible message spoofing");
                    System.out.println(message.toString());
                }
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("The interlocutor disconnected");
                System.exit(0);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        @Override
        public synchronized void run() {
            go();
        }
    }
}
