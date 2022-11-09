import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UDPReceiver {
    public static void print(String str, Object... o) {
        System.out.printf(str, o);
    }
    public UDPReceiver() throws IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        MulticastSocket socket = new MulticastSocket(39995);
        socket.joinGroup(InetAddress.getByName("224.0.5.5"));
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while(true) {
            socket.receive(packet);
            int len = packet.getLength();
            String src = packet.getAddress().getHostAddress().toString();
            int port = packet.getPort();
            String message = new String(buffer, 0, len);
            String[] messages = message.split("#");
            print("%s, %s:%d - %s\n", messages[1], src, port, messages[0]);
        }
    }
    public static void main(String[] args) throws IOException {
        new UDPReceiver();
    }
}