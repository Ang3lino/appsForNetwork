package Multicast;

import Main.Const;
import Main.Message;
import Main.MyState;
import Main.UtilFun;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastPublisher {
    private DatagramSocket socket;
    private InetAddress group;

    public void multicast(
      String multicastMessage) throws IOException {
        multicast(multicastMessage.getBytes());
    }

    public void multicast(String usr, String msg, MyState state) throws IOException {
        byte[] buff = UtilFun.serialize(new Message(usr, msg, state));
        System.out.println(buff);
        System.out.println(buff.length);
        multicast(buff);
    }

    public void multicast(byte[] buff) throws IOException {
        socket = new DatagramSocket();
        group = InetAddress.getByName("230.0.0.0");
        DatagramPacket packet = new DatagramPacket(buff, buff.length, group, Const.PORT);
        socket.send(packet);
        socket.close();
    }

    private MulticastPublisher() throws IOException {
        StringBuilder builder = new StringBuilder();
        for (int i = 10; i != 0; --i)
            builder.append("a");
        multicast("Aldo",
                builder.toString(), MyState.PUBLIC_MSG);
    }

    public static void main(String ...args) throws IOException {
        new MulticastPublisher();
    }
}