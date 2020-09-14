import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    public static ResourceBundle resourceBundle;

    public static void main(String[] args) {
        resourceBundle = ResourceBundle.getBundle("text");

        IP ip = new IP();
        Cipher cipher = new Cipher();

        System.out.println("\n"
                + resourceBundle.getString("welcome.info1") + "\n"
                + resourceBundle.getString("welcome.info2") + "\n"
                + resourceBundle.getString("welcome.info3") + "\n");

        System.out.println(resourceBundle.getString("welcome.ip"));
        System.out.println(resourceBundle.getString("welcome.local") + " " + ip.getLocal());
        System.out.println(resourceBundle.getString("welcome.public") + " " + ip.getPublic());

        System.out.println("\n" +
                resourceBundle.getString("welcome.warning1") + "\n" +
                resourceBundle.getString("welcome.warning2") + "\n" +
                resourceBundle.getString("welcome.warning3") + "\n");

        System.out.print(resourceBundle.getString("welcome.interlocutor") + " ");
        String ipAddress = new Scanner(System.in).nextLine();

        System.out.println("\n"
                + resourceBundle.getString("welcome.attention1") + "\n"
                + resourceBundle.getString("welcome.attention2") + "\n");

        new Thread(new Server(ipAddress, cipher)).start();
        new Client(ipAddress, cipher);
    }
}