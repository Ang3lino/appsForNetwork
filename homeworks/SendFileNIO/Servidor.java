
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.nio.*;
import java.nio.channels.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Iterator;

public class Servidor {

    public static void main(String[] args) {
        try {
            int pto = 7;
            InetSocketAddress dir = new InetSocketAddress(pto);
            DatagramChannel s = DatagramChannel.open();
            s.configureBlocking(false);
            s.socket().bind(dir);
            Selector sel = Selector.open();
            s.register(sel, SelectionKey.OP_READ);

            System.out.println("Servidor listo.. Esperando datagramas...");
            //int n=0;
            while (true) {
                System.out.println("Esperando un nuevo datagrama");
                sel.select();
                Iterator<SelectionKey> it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey k = (SelectionKey) it.next();
                    it.remove();
                    if (k.isReadable()) {
                        DatagramChannel ch = (DatagramChannel) k.channel();
                        ByteBuffer bb = ByteBuffer.allocate(65535);
                        bb.clear();

                        SocketAddress emisor = ch.receive(bb);
                        bb.flip();

                        InetSocketAddress d = (InetSocketAddress) emisor;
                        System.out.println("Petición Datagrama recibido desde " + d.getAddress() + ":" + d.getPort());

                        byte[] b = new byte[bb.remaining()];
                        bb.get(b);
                        String nombre = new String(b, 0, b.length);
                        System.out.println("El nombre del archivo es: " + nombre);

                        bb.clear();
                        emisor = ch.receive(bb);
                        bb.flip();
                        b = new byte[bb.remaining()];
                        bb.get(b);
                        String tamString = new String(b, 0, b.length);
                        System.out.println("El tamaño del archivo es: " + tamString);

                        long tam = Long.parseLong(tamString);
                        System.out.println("El tamaño del archivo es: " + tam);

                        //DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
                        FileOutputStream os = new FileOutputStream(nombre);
                        FileChannel destination = os.getChannel();
                        long recibidos = 0;
                        int porciento = 0, n = 0;
                        while (recibidos < tam) {
                            bb.clear();
                            emisor = ch.receive(bb);
                            bb.flip();
                            b = new byte[bb.remaining()];
                            bb.get(b);
                            n = b.length;
                            ByteBuffer z = ByteBuffer.allocate(65535);
                            destination.write(z.wrap(b));

                            recibidos += n;
                            porciento = (int) ((recibidos * 100) / tam);
                            System.out.println("\r Recibido el " + porciento + "%");
                        }
                  //    bb.clear();
                        //    bb.flip();
                        System.out.println("Recibido completado");
                        //dos.close();
                        destination.close();
                        os.close();
                        continue;
                    }//if
                }//while
            }//while
            //cl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }//main
}
