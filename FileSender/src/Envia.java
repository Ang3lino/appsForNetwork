import javax.swing.JFileChooser;
import java.io.*;
import java.net.*;

public class Envia {

    public static void main(String[] args) {
        try {
            int PORT = 1234; // l e
            String host = "127.0.0.1";
            JFileChooser jf = new JFileChooser();
            jf.setMultiSelectionEnabled(true);
            // jf.setRequestFocusEnabled(); // ?
            int r = jf.showOpenDialog(null); // null because we don't have a GUI as parent
            if (r == JFileChooser.APPROVE_OPTION) { // the user clicked ACCEPT
                File f = jf.getSelectedFile(); // jf.getSelectedFiles returns an array of Files
                String nombre = f.getName();
                long tam = f.length();
                String path = f.getAbsolutePath();
                Socket cl = new Socket(host, PORT);
                cl.setReuseAddress(true);
                System.out.println("Se enviara el archivo: " + path + " que mide " + tam + "bytes");

                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                DataInputStream dis = new DataInputStream(new FileInputStream(path));

                dos.writeUTF(nombre); // ASCII ONLY
                dos.flush();

                dos.writeLong(tam);
                dos.flush();

                byte[] b = new byte[2000];

                long enviados = 0;
                int porciento = 0;
                while (enviados < tam) {
                    int n = dis.read(b);
                    dos.write(b, 0, n);
                    dos.flush();
                    enviados += n;
                    porciento = (int) ((enviados * 100) / tam);
                    System.out.println("\tTransmitido el " + porciento + "%");
                }
                dis.close();
                dos.close();
                cl.close();
                System.out.println("\nArchivo enviado...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
