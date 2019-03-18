/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foro.networking.tcp;

import foro.networking.Const;
import foro.networking.Pack;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import foro.networking.UtilFun;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sigma
 */
public class TcpServer implements Runnable {
    private ServerSocket serverSocket;
    private Socket socket;

    public TcpServer() {
        try {
            serverSocket = new ServerSocket(Const.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                socket = serverSocket.accept();
		        Pack pack = (Pack) UtilFun.deserialize(socket); // importante castear
            } catch (IOException | CalssNotFoundException e) {
                System.out.println("Something went wrong with TCP connection.");
                e.printStackTrace();
            }     
        }
    }
    
}
