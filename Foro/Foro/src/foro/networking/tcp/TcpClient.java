/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foro.networking.tcp;

import foro.networking.Const;
import foro.networking.MyState;
import foro.networking.Pack;
import foro.networking.UtilFun;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sigma
 */
public class TcpClient {
    private Socket socket;
    private ObjectOutputStream oos;   
    private ObjectInputStream ois; 

    public TcpClient() {
        try {
            socket = new Socket(Const.HOST, Const.PORT);
            socket.setReuseAddress(true);

            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() { 
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } 
        }
    }

    public ArrayList<Pack> getPostsByKeyword(String keyword) {
        Pack pack = new Pack(MyState.SEARCH);
        pack.setKeyword(keyword);
        ArrayList<Pack> matchables = null;
        try {
            oos.writeObject(pack); // request
            matchables = (ArrayList<Pack>) ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            return matchables; // returns null if exception catched
        }
    }

    // pack must have UPLOAD as state, otherwise it won't do its work
    public void uploadPack(Pack pack) {
        try {
            oos.writeObject(pack);
        } catch (IOException ex) {
            Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file = pack.getImage();
        if (file != null) {
            try {
                uploadFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    public void uploadFile(File file) throws FileNotFoundException, IOException {

        String name = file.getName();
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(new FileInputStream(name));

        byte[] b = new byte[2048];
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

    public Pack downloadPack(int postId) {
        Pack pack = null;
        return pack;
    }

    /**
     * Metodo que pide la lista de posts hechos en el foro, retorna null en caso
     * de algun error.
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public ArrayList<Pack> getListPost() {
        ArrayList<Pack> list = null;
        try {
            Pack pack = new Pack(MyState.LOG_IN);
            oos.writeObject(pack); // request the list port to the server
            list = (ArrayList<Pack>) ois.readObject(); // response
        } catch (IOException | ClassNotFoundException e) { 
            e.printStackTrace(); 
        } finally { 
            return list; 
        }
    }

}
