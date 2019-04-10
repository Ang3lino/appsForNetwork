package com.manriquez;

import com.manriquez.Paquete.Pack;
import com.manriquez.Paquete.PackType;

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

    public static boolean writeBytes(byte[] src, String filepath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            fos.write(src);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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

    public static Pack<?> sendSecure(
            final DatagramSocket sock, final InetAddress addr,
            final Pack<?> object, final PackType type) throws IOException {
        sock.setSoTimeout(TIMEOUT);
        byte[] objectAsBytes = UtilFun.serialize(object);
        DatagramPacket inputPack = new DatagramPacket(objectAsBytes, objectAsBytes.length),
                sendPack = new DatagramPacket(objectAsBytes, objectAsBytes.length, addr, Const.PORT);
        boolean continueSending = true;
        Pack<?> res = null;
        for (int countTry = 1; continueSending; ++countTry) {
            sock.send(sendPack);
            try {
                sock.receive(inputPack);
                res = (Pack<?>) UtilFun.deserialize(inputPack.getData());
                if (res != null && res.response != type) continue;
                System.out.println("Package sent at try: " + countTry);
                continueSending = false;
            } catch (SocketException e) {
                // timeOutException, send again
                System.out.println("Package ack timeout, trying again...");
            }
        }
        return res;
        // sock.setSoTimeout(0); // set sock timeout as infinite
    }

    public static Pack<?> receiveSecure(final DatagramSocket sock, final PackType type) {
        byte[] buff = new byte[Const.MAX_UDP_LENGHT];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        while (true) {
            try {
                sock.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Pack<?> res = (Pack<?>) UtilFun.deserialize(packet.getData());
            if (res != null && res.request == type) return res;
        }
    }

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


