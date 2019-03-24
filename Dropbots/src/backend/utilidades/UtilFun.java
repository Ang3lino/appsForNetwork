/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.utilidades;

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

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    /**
     * You should cast the object returned.
     * @param data
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] data) throws IOException, 
            ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    private static void writeFileThroughStream(
            DataInputStream dis, DataOutputStream dos, File file) {
        try {
            // TODO this segment of code is the same than storeFile
            byte[] buff = new byte[Const.MAX_TCP_LENGTH];
            long sentCount = 0, len = file.length();

            while (sentCount < len) {
                int n = dis.read(buff);
                dos.write(buff, 0, n);
                dos.flush();
                sentCount += n;
            }
            //dis.close(); dos.close(); // is this a patch ?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void uploadFile(File file, Socket socket) {
        try {
            String filepath = file.getAbsolutePath();
            DataInputStream dis = new DataInputStream(new FileInputStream(filepath));
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            writeFileThroughStream(dis, dos, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean storeFile(File file, Socket socket, String folder) {
        try {
            if ( !createFolder(folder) ) return false;

            // A better way to obtain filepath, it offers more compatibility over OS
            String filepath = Paths.get(folder, file.getName()).toString();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream( new FileOutputStream(filepath) );

            writeFileThroughStream(dis, dos, file);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
