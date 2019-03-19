/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import foro.networking.MyState;
import foro.networking.Pack;
import foro.networking.tcp.TcpClient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sigma
 */
public class Test {

    public static void uploadFile() {
        Pack pack = new Pack(MyState.UPLOAD); 
        File file = new File("escom.jpg"); 

        // addPost(String nick, String topic, String title, String description, File img)
        pack.addPost("Om3Ga", "Educacion", "La mejor escuela de ISC en Mexico", 
                "La ESCOM Se encuentra entre las 10 mejores segun deforma", file);
        TcpClient client = new TcpClient();
        client.uploadPack(pack);
	client.closeSocket();
    }

    public static void search() {
        TcpClient client = new TcpClient();

        ArrayList<Pack> packs;

	// obten la lista para inicializar los post, retorna titurlo, fecha e id del post
        packs = client.getListPost(); 
        packs.forEach(p -> { System.out.println(p); } );
	
	// obten los post que concidan con la palabra clave en titulo, fecha o descripcion 
	// (ver procedimiento find_by_keyword(keyword: VARCHAR)
        packs = client.getPostsByKeyword("Un");
        packs.forEach(p -> { System.out.println(p); } );

        packs = client.getPostsByKeyword("Acuchillado");
        packs.forEach(p -> { System.out.println(p); } );
	client.closeSocket();
    }

    public static void downloadFile() throws IOException, ClassNotFoundException {
        TcpClient client = new TcpClient();
	// Download a file given an id
        Pack pack = client.downloadPack(8); 
	// The file where the file is saved is in pack.getImage() which is a file
	File file = pack.getImage();
	client.closeSocket();
    }
    
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        downloadFile(); 
    }
}
