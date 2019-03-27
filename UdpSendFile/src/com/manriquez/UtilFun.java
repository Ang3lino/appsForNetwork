package com.manriquez;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

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


