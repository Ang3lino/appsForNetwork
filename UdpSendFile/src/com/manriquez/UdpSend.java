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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UdpSend {
    private DatagramSocket sock;
    private File file;
    private InetAddress addr;
    public static final int TIMEOUT = 1000; // ms

    public void close() {
        if (sock != null) sock.close();
    }

    public UdpSend(File file) {
        this.file = file;
        try {
            sock = new DatagramSocket();
            addr = InetAddress.getByName("localhost");
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Data> splitBytes(byte[] src) {
        ArrayList<Data> result = new ArrayList<>();
        final int segmentLen = Const.MAX_UDP_LENGHT >> 1;
        int i, j;
        for (i = 0, j = 0; i < src.length; i += segmentLen, ++j) {
            final byte[] chunk = new byte[segmentLen];
            System.arraycopy(src, i, chunk, 0,
                    (src.length - i < segmentLen ? src.length - i : segmentLen ));
            result.add(new Data(j, chunk));
        }
        return result;
    }

    public void sendFile() {
        Pack<File> metadata = new Pack<>(PackType.FILE_METADATA, file);
        UtilFun.sendSecure(sock, addr, metadata); // important to send File data
        byte[] bFile = UtilFun.fileToBytes(file);
        ArrayList<Data> partitionBytes = splitBytes(bFile);
        Set<Integer> indexesToSend = IntStream.rangeClosed(0, partitionBytes.size() - 1)
                .boxed().collect(Collectors.toSet());
        while (!indexesToSend.isEmpty()) {
            Set<Integer> removeIndexes = new HashSet<>();
            indexesToSend.forEach(i -> {
                Pack<Data> indexSent = UtilFun.sendSecure(
                        sock, addr, new Pack<Data>(PackType.FILE_DATA, partitionBytes.get(i)));
                if (indexSent != null) removeIndexes.add(indexSent.value.index);
            });
            indexesToSend.removeAll(removeIndexes);
        }
        System.out.println("File " + file.getName() + " sent succesfully.");
    }
}
