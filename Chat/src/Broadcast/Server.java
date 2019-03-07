package Broadcast;

import Main.Const;
import Main.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    private Server() {
        byte[] buff = new byte[Const.MAX_UDP_LENGTH];
        try {
            DatagramSocket ds = new DatagramSocket(Const.PORT + 1); // it may throw SocketException
            ds.setBroadcast(true);
            DatagramPacket dp = new DatagramPacket(buff, buff.length);
            ds.receive(dp); // it may throw IOException
            Message message = (Message) deserialize(dp.getData());
            System.out.println(message);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("Service ON");
        new Server();
    }
}
