/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foro.networking;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

/**
 *
 * @author Sigma
 */
public class UtilFun {
   
    public static boolean createFolderToStoreImages(final String folderName) {
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

    public static void uploadFile(File file, Socket socket) 
            throws FileNotFoundException, IOException {
        String name = file.getName();
        DataInputStream dis = new DataInputStream(new FileInputStream(name));
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        byte[] b = new byte[Const.MAX_TCP_LENGTH];
        long len = file.length();
        long sentCount = 0;
        while (sentCount < len) {
            int n = dis.read(b);
            dos.write(b, 0, n);
            dos.flush();
            sentCount += n;
        }
        dis.close();
        dos.close();
    }

    public static boolean storeFile(File file, Socket socket, String folder) 
            throws FileNotFoundException, IOException {
        if ( !createFolderToStoreImages(folder) ) return false;
        
        // A better way to obtain filepath, it offers more compatibility over OS
        String filepath = Paths.get(folder, file.getName()).toString();
        // String filepath = folder + file.getName();
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream fos = new DataOutputStream( 
                new FileOutputStream(filepath) );
        // new FileOutputStream("img/"+file.getName()) );

        long size = file.length();
        long count = 0;
        byte[] buff = new byte[Const.MAX_TCP_LENGTH];
        while (count < size) {
            int n = dis.read(buff);
            fos.write(buff, 0, n);
            fos.flush();
            count += n;
        }
        fos.close();
        dis.close();

	return true;
    }
}
