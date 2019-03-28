package com.manriquez;

import com.manriquez.Paquete.Data;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class UdpReceive {
    DatagramSocket sock;
    byte[] buff;

    public UdpReceive() {
        try {
            sock = new DatagramSocket(Const.PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    // TODO
    public void receive() throws IOException {
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        sock.receive(packet);
        File file = (File) UtilFun.deserialize(packet.getData());
        TreeSet<Data> set = new TreeSet<>();

    }

    public static void partitionBytesWriteFile(TreeSet<Data> src, File file) {
        byte[] bfile = new byte[(int) file.length()];
        final int step = src.iterator().next().bytes.length;
        Iterator<Data> it = src.iterator();
        for (int destPos = 0; destPos < file.length(); destPos += step) {
            Data data = it.next();
            System.arraycopy(data.bytes, 0, bfile, destPos,
                    file.length() - destPos < step ?
                            (int) (file.length() - destPos) : data.bytes.length);
        }
        UtilFun.writeBytes(bfile, file);
    }
}
