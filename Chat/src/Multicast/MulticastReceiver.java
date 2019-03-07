package Multicast;

import java.net.*;
import java.io.*;

public class MulticastReceiver extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];

    MulticastReceiver() {
        super();
    }

    public void run() {
        try {
            socket = new MulticastSocket(Const.PORT);
            InetAddress group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
                if ("end".equals(received)) {
                    break;
                }
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (IOException e) { 
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        new MulticastReceiver().start();
    }
}
