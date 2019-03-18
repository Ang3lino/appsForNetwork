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

    public void uploadPack(Pack pack) {
        
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
