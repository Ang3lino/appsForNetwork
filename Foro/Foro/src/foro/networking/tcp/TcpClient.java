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
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Sigma
 */
public class TcpClient {
    private Socket socket;
    private DataOutputStream dos;    
    private DataInputStream dis;    

    public TcpClient() {
        try {
            socket = new Socket(Const.HOST, Const.PORT);
            socket.setReuseAddress(true);
            //dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Pack> getListPost() throws IOException, ClassNotFoundException {
        ArrayList<Pack> list = new ArrayList<>();
        Pack pack = new Pack(MyState.LOG_IN);
        dis = new DataInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeObject(pack);
        
        /*
        int count = 0;
        int length = dis.readInt();

        byte[] listAsBytes = new byte[length];
        byte[] buff = new byte[2048];

        while (count < length){
            int n = dis.read(buff);
            count += n;
            System.arraycopy(listAsBytes, count, buff, 0, n);
        }

        list = (ArrayList<Pack>) UtilFun.deserialize(listAsBytes);
        */
        return list;
    }

}
