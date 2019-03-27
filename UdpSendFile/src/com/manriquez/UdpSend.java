package com.manriquez;

import com.manriquez.Const;
import com.manriquez.Paquete.Data;
import com.manriquez.Paquete.Pack;
import com.manriquez.Paquete.PackType;
import com.manriquez.UtilFun;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UdpSend {
    private DatagramSocket sock;
    private File file;
    private InetAddress addr;
    public static final int TIMEOUT = 1000; // ms

    public UdpSend(File file) {
        this.file = file;
        try {
            sock = new DatagramSocket();
            addr = InetAddress.getByName("localhost");
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Data> splitBytes(byte[] src) {
        ArrayList<Data> result = new ArrayList<>();
        final int segmentLen = Const.MAX_UDP_LENGHT >> 1;
        for (int i = 0, j = 0; i < src.length; i += segmentLen, ++j) {
            final byte[] chunk = new byte[segmentLen];
            System.arraycopy(src, i, chunk, 0, segmentLen);
            result.add(new Data(j, chunk));
        }
        return result;
    }

    public void send() throws  IOException {
        Pack<File> metadata = new Pack<>(PackType.FILE_METADATA, file);
        UtilFun.sendSecure(sock, addr, metadata, PackType.FILE_METADATA); // important to send File data
        byte[] bFile = UtilFun.fileToBytes(file), bPack = new byte[Const.MAX_UDP_LENGHT];
        ArrayList<Data> partitionBytes = splitBytes(bFile);
        List<Integer> indexesToSend = IntStream.rangeClosed(0, partitionBytes.size() - 1)
                .boxed().collect(Collectors.toList());
        while (!indexesToSend.isEmpty()) {
            indexesToSend.forEach(i -> {
                DatagramPacket sendPack = new DatagramPacket(bPack, bPack.length, addr, Const.PORT);
                try { sock.send(sendPack); } catch (IOException e) { e.printStackTrace(); }
            });
            List<Integer> sentIndexes = (List<Integer>) UtilFun.receiveSecure(sock, PackType.INDEXES);
            indexesToSend.removeAll(sentIndexes);
            System.out.printf("Sent %d / %d files.", indexesToSend.size(), partitionBytes.size());
        }
        System.out.println("File " + file.getName() + " sent succesfully.");
    }
}
