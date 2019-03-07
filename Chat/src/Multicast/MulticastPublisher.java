package Multicast;

import Main.Const;

import java.net.*;
import java.io.*;

public class MulticastPublisher {
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;
 
    public void multicast(
      String multicastMessage) throws IOException {
        socket = new DatagramSocket();
        group = InetAddress.getByName("230.0.0.0");
        buf = multicastMessage.getBytes();
 
        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length, group, Const.PORT);
        socket.send(packet);
        socket.close();
    }

    MulticastPublisher() throws IOException {
        multicast("end");
    }

    public static void main(String ...args) throws IOException {
        new MulticastPublisher();
    }
}