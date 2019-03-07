package Broadcast;

import Main.Const;
import Main.Message;
import Main.MyState;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    private static DatagramSocket socket = null;

    private void sendMessage(String user, String message, MyState state) {
        // Serialize to a byte array
        try {
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            oo.writeObject(new Message(user, message, state));
            oo.close();

            byte[] serializedMessage = bStream.toByteArray();
            System.out.println(serializedMessage);
            System.out.println(serializedMessage.length);

            broadcast(serializedMessage, InetAddress.getByName("255.255.255.255"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(
            byte[] buffer, InetAddress address) throws IOException {
        socket = new DatagramSocket(Const.PORT);
        socket.setBroadcast(true);
        DatagramPacket packet
                = new DatagramPacket(buffer, buffer.length, address, Const.PORT + 1);
        socket.send(packet);
        socket.close();
    }

    private Client() {
        // Scanner scanner = new Scanner(System.in);
        // System.out.print("Enter your nickname: ");
        // String username = scanner.nextLine();
        // System.out.print("Enter your message: ");
        // String message = scanner.nextLine();
        // sendMessage(username, message, MyState.PUBLIC_MSG);
        sendMessage("Angel", "Hola como estas", MyState.PUBLIC_MSG);
        System.out.println("Mesaje enviado");
    }

    public static void main(String args[]) throws IOException {
        new Client();
    }

}
