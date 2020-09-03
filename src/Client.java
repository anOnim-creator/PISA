import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {
    private ObjectOutputStream objectOutputStream = null;
    private final Cipher cipher;
    public int port = 4285;
    public String ip;

    public Client(String ip, Cipher cipher){
        this.ip = ip;
        this.cipher = cipher;
        sleep();
        connect();
        write();
    }

    public synchronized void write(){
        Scanner scanner = new Scanner(System.in);
        String s;

        try {
            objectOutputStream.writeObject(cipher.getPublicMyKey());
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true){
            s = scanner.nextLine();
            try {
                Message msg = new Message(s);
                msg.encrypt(cipher, cipher.getPublicKey());
                objectOutputStream.writeObject(msg);
                objectOutputStream.flush();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("The interlocutor is not available");
            }
        }
    }
    public synchronized void connect(){
        try {
            Socket socket = new Socket(ip, port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {

            try {
                Thread.sleep(4000);
            } catch (InterruptedException interruptedException) {
                //interruptedException.printStackTrace();
            }

            connect();
        }

    }
    private synchronized void sleep(){
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
