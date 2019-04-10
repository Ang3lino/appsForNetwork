package com.manriquez;

import com.manriquez.Paquete.Data;
import com.manriquez.Paquete.Pack;
import com.manriquez.Paquete.PackType;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UdpReceive {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buff = new byte[Const.MAX_UDP_LENGHT];

    public UdpReceive() {
        try {
            socket = new DatagramSocket(Const.PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public int partitionSize(final File file) {
        return (int) Math.ceil( file.length() / (Const.MAX_UDP_LENGHT >> 1) );
    }

    // TODO
    public void receive() throws IOException {
        Pack<File> reqFile = (Pack<File>) UtilFun.receiveSecure(socket, PackType.FILE_METADATA);
        ArrayList<Data> partitions = new ArrayList<>(partitionSize(reqFile.value));
        HashSet<Integer> receivedIndexes = new HashSet<>(partitions.size());
        while (receivedIndexes.size() < partitions.size()) {
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            socket.receive(packet);
            byte[] bChunk = packet.getData();
            Pack<Data> chunkPack = (Pack<Data>) UtilFun.deserialize(bChunk);
            partitions.add(chunkPack.value.index, chunkPack.value);
            receivedIndexes.add(chunkPack.value.index);
        }
    }

}
