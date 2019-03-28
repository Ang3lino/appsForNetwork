package com.manriquez;

import com.manriquez.Paquete.Pack;

import java.io.*;
import java.net.*;
import java.nio.file.Paths;

import static com.manriquez.UdpSend.TIMEOUT;

/**
 *
 * @author Angel Lopez Manriquez
 */
public class UtilFun {

    public static boolean createFolder(final String folderName) {
        final File folder = new File(folderName);
        if (folder.exists()) {
            System.out.println("[Ok] The folder already exists.");
            return true;
        } else {
            final boolean created = folder.mkdir();
            if (created) System.out.println("[Ok] Folder created");
            else System.out.println("[Error] It couldn't be created");
            return created;
        }
    }

    public static byte[] serialize(Object obj) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(obj);
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            final byte[] o = null;
            return o;
        }
    }

    public static void writeBytes(byte[] src, File file) {
        FileOutputStream fos = null;
        createFolder(Const.SERVER_FOLDER);
        String filepath = Paths.get(Const.SERVER_FOLDER, file.getName()).toString();
        try {
            fos = new FileOutputStream(filepath);
            fos.write(src);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] fileToBytes(File file) {
        int len = (int) file.length();
        byte[] bytes = new byte[len];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(bytes);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static void syncSendDatagramPacket(
            DatagramSocket sock, DatagramPacket in, DatagramPacket out) {
        try {
            sock.send(out);
            sock.receive(in);
        } catch (SocketException e) {
            // timeOutException, send again
            System.out.println("Package ack timeout, trying again...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean attemptSetTimeout(DatagramSocket sock, int timeout) {
        try {
            sock.setSoTimeout(TIMEOUT);
            return true;
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T> Pack<T> sendSecure(
                final DatagramSocket sock, final InetAddress addr, final Pack<T> sendPack) {
        if (!attemptSetTimeout(sock, TIMEOUT)) return null;

        byte[] buffObj = UtilFun.serialize(sendPack);

        DatagramPacket inputPack = new DatagramPacket(buffObj, buffObj.length);
        DatagramPacket outPack = new DatagramPacket(buffObj, buffObj.length, addr, Const.PORT);

        while (true) {
            syncSendDatagramPacket(sock, inputPack, outPack);
            Pack<T> res = (Pack<T>) UtilFun.deserialize(inputPack.getData());
            if (res != null && res.type == sendPack.type) return res;
        }
        // sock.setSoTimeout(0); // set sock timeout as infinite
    }

//    public static pack<?> receivesecure(final datagramsocket sock, final packtype type) {
//        byte[] buff = new byte[const.max_udp_lenght];
//        datagrampacket packet = new datagrampacket(buff, buff.length);
//        while (true) {
//            try {
//                sock.receive(packet);
//            } catch (ioexception e) {
//                e.printstacktrace();
//            }
//            pack<?> res = (pack<?>) utilfun.deserialize(packet.getdata());
//            if (res != null && res.type == type) return res;
//        }
//    }

    /**
     * You should cast the object returned.
     * @param data
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}


