/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import foro.networking.Pack;
import foro.networking.tcp.TcpClient;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Sigma
 */
public class Test {
    
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        TcpClient client = new TcpClient();
        ArrayList<Pack> packs = client.getListPost();
        packs.forEach(p -> { System.out.println(p); } );
    }
}
