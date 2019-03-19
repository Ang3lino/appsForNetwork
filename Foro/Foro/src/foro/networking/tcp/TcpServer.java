/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foro.networking.tcp;

import database.DBHelper;
import foro.networking.Const;
import foro.networking.Pack;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import foro.networking.UtilFun;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sigma
 */
public class TcpServer implements Runnable {
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    public TcpServer() {
        try {
            serverSocket = new ServerSocket(Const.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildPosts() {
        try {
            ArrayList<Pack> packs = DBHelper.listPost();
            oos.writeObject(packs);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } 
    }

    public void buildFindByKeyword(String keyword) {
        try {
            ArrayList<Pack> packs = DBHelper.findByKeyword(keyword);
            oos.writeObject(packs);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } 
    }

    public void storeImage(Pack pack) {
        
    }
    
    public void storePost(Pack pack) {
        try {
            DBHelper.appendPost(pack);
        } catch (SQLException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            Pack pack = null;
            try {
                System.out.println("Waiting a connection");
                socket = serverSocket.accept();

                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());

                pack = (Pack) ois.readObject();

                switch (pack.getState()) {
                    case LOG_IN: buildPosts(); break;
                    case SEARCH: buildFindByKeyword(pack.getKeyword()); break;
                    case UPLOAD: storePost(pack);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Something went wrong with TCP connection.");
                e.printStackTrace();
            }     
        }
    }
    
    public static void main(String args[]) {
        new Thread(new TcpServer()).start();
    }

}
