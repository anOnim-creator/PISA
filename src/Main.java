import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IP ip = new IP();
        Cipher cipher = new Cipher();

        System.out.println(
                "\n" +
                "Welcome to PISA!\n" +
                "This is your application for privately sharing information on the web.\n" +
                "You can learn more about how it works and the code on GitHub." +
                "\n"
        );

        System.out.println("Your IP addresses");
        System.out.println("Local IP: " + ip.getLocal());
        System.out.println("Public IP: " + ip.getPublic());

        System.out.println("\n" +
                "Tell your interlocutor your ip address.\n" +
                "The rest of the application will do for you!\n" +
                "(Standard connection port: 4285)" +
                "\n"
        );

        System.out.print("Enter the IP address of the interlocutor: ");
        String ipAddress = new Scanner(System.in).nextLine();

        System.out.println("\n" +
                "Attention! There is a limit on the number of Unicode characters in a message.\n" +
                "The maximum allowed value is 255.\n");

        new Thread(new Server(cipher)).start();
        new Client(ipAddress, cipher);
    }
}