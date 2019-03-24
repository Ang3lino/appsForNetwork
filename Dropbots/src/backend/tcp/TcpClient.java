/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.tcp;

import backend.utilidades.*;

import javax.rmi.CORBA.Util;
import java.io.*;

import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel Lopez Manriquez
 */
public class TcpClient extends Observable{
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

    public void uploadFile(File file, String path) {
        Pack p = new Pack(MyState.UPLOAD);
        p.file = file;
        p.currentPath = path;
        try {
            oos.writeObject(p);
            UtilFun.uploadFile(file, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File[] getSubFiles(String currPath) {
        Pack p = new Pack(MyState.UPDATE_DIR);
        File[] subFiles = null;
        p.currentPath = currPath;
        try {
            oos.writeObject(p);
            subFiles = (File[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
        if (subFiles != null)
            for (File subFile : subFiles) {
                System.out.println(subFile);
            }
        else
            System.out.println("Got NULL");
        return subFiles;
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

}
