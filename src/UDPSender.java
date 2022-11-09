import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.Scanner;
import java.util.Random;
public class UDPSender {
    DatagramSocket socket;
    static String[] greetings = new String[20];
    public static void print(String str, Object... o) {
        System.out.printf(str, o);
    }
    public UDPSender() throws IOException {
        Random random = new Random();
        socket = new DatagramSocket(0);

            Thread t = new Thread(() -> {
                try {
                    while(true){
                        int index = random.nextInt(greetings.length);
                        int delay = random.nextInt(6)+5;
                        long timestamp = System.currentTimeMillis();
                        Date date = new Date(timestamp);
                        String datetime = date.toString();
                        String greeting = greetings[index]+'#'+datetime;
                        sendMsg(greeting, "224.0.5.5", 39995);
                        try {
                            Thread.sleep(delay * 1000);
                        } catch (InterruptedException ex) {}
                    }
                } catch (IOException ex) {
                } finally {
                    print("Connection drop!");
                }
            });
            t.start();

            Scanner scanner = new Scanner(System.in);
            print("Input END to terminate:\n");

            while(true) {
                String str = scanner.nextLine();
                if (str.equals("END")){
                    int delayStop = random.nextInt(10);
                    try {
                        Thread.sleep(delayStop * 1000);
                    } catch (InterruptedException ex) {}
                    System.exit(1);
                }
            }

    }
    public void sendMsg(String str, String destIP, int port) throws IOException {
        InetAddress destination = InetAddress.getByName(destIP);
        DatagramPacket packet =
                new DatagramPacket(str.getBytes(), str.length(), destination, port);
        socket.send(packet);
    }
    public static void main(String[] args) throws IOException {
        int n = 0;
        File file = new File("./out/production/lab_assignment/greetings.txt");
        FileInputStream in = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = reader.readLine();
        while(line != null){
            greetings[n] = line;
            n++;
            line = reader.readLine();
        }
        in.close();
        new UDPSender();
    }
}
