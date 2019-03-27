package com.manriquez;

import com.manriquez.Paquete.Pack;

import java.io.File;
import java.io.IOException;
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

    public ArrayList<Pack> splitBytes(byte[] src) {
        ArrayList<Pack> result = new ArrayList<>();
        final int segmentLen = Const.MAX_UDP_LENGHT >> 1;
        for (int i = 0, j = 0; i < src.length; i += segmentLen, ++j) {
            final byte[] chunk = new byte[segmentLen];
            System.arraycopy(src, i, chunk, 0, segmentLen);
            result.add(new Pack(chunk, j));
        }
        return result;
    }

    public void send() {
        Pack file = new Pack();
        sendSecure(); // important to send File data
        byte[] bFile = UtilFun.fileToBytes(file), bPack = new byte[Const.MAX_UDP_LENGHT];
        ArrayList<Pack> partitionBytes = splitBytes(bFile);
        List<Integer> indexesToSend = IntStream.rangeClosed(0, partitionBytes.size() - 1)
                .boxed().collect(Collectors.toList());

        while (!indexesToSend.isEmpty()) {
            indexesToSend.forEach(i -> {
                DatagramPacket sendPack = new DatagramPacket(
                        bPack, bPack.length, addr, Const.PORT);
                sock.send(sendPack);
            });
            List<Integer> sentIndexes = (List<Integer>) receiveSecure();
            indexesToSend.removeAll(sentIndexes);
            System.out.printf("Sent %d / %d files.", indexesToSend.size(), partitionBytes.size());
        }
        System.out.println("File " + file.getName() + " sent succesfully.");
    }

    public Pack sendSecure(Pack object) throws IOException {
        sock.setSoTimeout(TIMEOUT);
        byte[] objectAsBytes = UtilFun.serialize(object);
        DatagramPacket inputPack = new DatagramPacket(objectAsBytes, objectAsBytes.length),
                sendPack = new DatagramPacket(objectAsBytes, objectAsBytes.length, addr, Const.PORT);
        boolean continueSending = true;
        for (int countTry = 1; continueSending; ++countTry) {
            sock.send(sendPack);
            try {
                sock.receive(inputPack);
                System.out.println("Package send at try: " + countTry);
                continueSending = false;
            } catch (SocketException e) {
                // timeOutException, send again
                System.out.println("Package ack timeout, trying again...");
            }
        }
        return UtilFun.deserialize(inputPack.getData());
        // sock.setSoTimeout(0); // set sock timeout as infinite
    }

}
