import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {
    private final Cipher cipher;
    public int port = 4285;
    public String ip;
    private ObjectOutputStream objectOutputStream = null;

    public Client(String ip, Cipher cipher) {
        this.ip = ip;
        this.cipher = cipher;
        sleep();
        connect();
        write();
    }

    public synchronized void write() {
        Scanner scanner = new Scanner(System.in);
        String s;

        try {
            objectOutputStream.writeObject(cipher.getPublicMyKey());
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            cipher.setPublicMyKey(null);
        }


        while (true) {
            s = scanner.nextLine();
            try {
                Message msg = new Message(s);
                msg.encrypt(cipher, cipher.getPublicKey());
                objectOutputStream.writeObject(msg);
                objectOutputStream.flush();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println(Main.resourceBundle.getString("client.exit1") + "\n" + Main.resourceBundle.getString("client.exit2"));
                System.exit(0);
            }
        }
    }

    public synchronized void connect() {
        try {
            Socket socket = new Socket(ip, port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            System.out.println(Main.resourceBundle.getString("client.connection1")
                    + " " + socket.getInetAddress().toString().replace("/", "")
                    + " " + Main.resourceBundle.getString("client.connection2"));

        } catch (IOException e) {

            System.out.println(Main.resourceBundle.getString("client.fail"));

            try {
                Thread.sleep(4000);
            } catch (InterruptedException interruptedException) {
                //interruptedException.printStackTrace();
            }

            connect();
        }

    }

    private synchronized void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000); //Required to connect to the server after launch
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }
}