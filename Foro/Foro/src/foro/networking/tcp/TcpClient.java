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
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel Lopez Manriquez
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
                UtilFun.uploadFile(file, socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    public Pack downloadPack(int postId) throws IOException, ClassNotFoundException {
        Pack pack = new Pack(MyState.DOWNLOAD);
        pack.setPostId(postId);
        oos.writeObject(pack);
        Pack res = (Pack) ois.readObject();
        if (res.getImage() != null) {
            UtilFun.storeFile(res.getImage(), socket, Const.CLIENT_FOLDER);
            String filepath = Paths.get(Const.CLIENT_FOLDER, res.getImage().getName()).toString();
            pack.setFile(new File(filepath));
        }
	System.out.println(pack);
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
