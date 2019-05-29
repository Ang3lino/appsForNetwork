
/**
 *
 * @author Martin
 */
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.*;
import java.nio.channels.*;
import java.net.InetSocketAddress;
import java.util.Iterator;
import javax.swing.JFileChooser;

public class Cliente {

    public static void main(String[] args) {
        try {
            int pto = 7;
            InetSocketAddress dst = new InetSocketAddress("127.0.0.1", pto);
            DatagramChannel cl = DatagramChannel.open();
            cl.configureBlocking(false);
            Selector sel = Selector.open();
            cl.connect(dst);
            cl.register(sel, SelectionKey.OP_WRITE);
            ByteBuffer bb = ByteBuffer.allocate(65535);
            //while(true){
            sel.select();
            Iterator<SelectionKey> it = sel.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey k = (SelectionKey) it.next();
                it.remove();
                if (k.isWritable()) {

                    DatagramChannel ch = (DatagramChannel) k.channel();

                    JFileChooser jf = new JFileChooser();
                    int r = jf.showOpenDialog(null);
                    if (r == JFileChooser.APPROVE_OPTION) {
                        //obtención de datos del archivo
                        File f = jf.getSelectedFile();
                        String nombre = f.getName();
                        long tam = f.length();
                        String path = f.getAbsolutePath();
                        //se crea el canal de datagrama
                        System.out.println("Se enviará el archivo: " + path + " que mide " + tam + " bytes.");
                        DataInputStream dis = new DataInputStream(new FileInputStream(path));
                        byte[] b = new byte[65535];
                        b = nombre.getBytes();

                        //mandamos información
                        bb = ByteBuffer.wrap(b);
                        ch.write(bb);
                        System.out.println("Nombre del archivo enviado");
                        bb.clear();
                        bb.flip();
                        b = new byte[65535];
                        b = Long.toString(tam).getBytes();
                        bb = ByteBuffer.wrap(b);
                        ch.write(bb);
                        System.out.println("Tamaño del archivo enviado");

                        bb.clear();
                        bb.flip();

                        long enviados = 0;
                        int porciento = 0, n = 0;
                        while (enviados < tam) {
                            n = dis.read(b);
                            System.out.println("n=" + n);
                            bb = ByteBuffer.wrap(b);
                            ch.write(bb);
                           // bb.clear();
                            // bb.flip();
                            enviados += n;
                            porciento = (int) ((enviados * 100) / tam);
                            System.out.println("\r Transmitido el " + porciento + "%");
                        }
                        dis.close();

                    }
                    continue;
                }//if
            }//while
            //}//while
            cl.close();
            System.out.println("Termina envio de datagramas");
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }//main
}
