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

    public static boolean createFolderToStoreImages() {
        final String folderName = "img";
        final File folder = new File(folderName);
        if (folder.exists()) {
            System.out.println("The folder already exists.");
            return true;   
        } else {
            final boolean created = folder.mkdir();
            if (created) System.out.println("Folder created");
            else System.out.println("It couldn't be created");
            return created;
        }
    }

    public static void uploadFile() {
        Pack pack = new Pack(MyState.UPLOAD);

        // ensure you have permission to write files
        assert createFolderToStoreImages();
        File file = new File("escom.jpg");

        // addPost(String nick, String topic, String title, String description, File img)
        pack.addPost("Om3Ga", "Educacion", "La mejor escuela de ISC en Mexico", 
                "La ESCOM Se encuentra entre las 10 mejores segun deforma", file);
        TcpClient client = new TcpClient();
        client.uploadPack(pack);
        
        //String pathToSave = Paths.get("img", file.getName()).toString(); // img/name
    }

    public static void search() {
        TcpClient client = new TcpClient();

        ArrayList<Pack> packs;

        packs = client.getListPost();
        packs.forEach(p -> { System.out.println(p); } );

        packs = client.getPostsByKeyword("Un");
        packs.forEach(p -> { System.out.println(p); } );

        packs = client.getPostsByKeyword("Acuchillado");
        packs.forEach(p -> { System.out.println(p); } );
    }

    public static void downloadFile() throws IOException, ClassNotFoundException {
        TcpClient client = new TcpClient();
        Pack pack = client.downloadPack(8); 
    }
    
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        uploadFile(); 
    }
}
