import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Recibe {
    private static int PORT = 1234;

    public static void main(String[] args) {
        try {

            ServerSocket server = new ServerSocket(PORT);
            Socket socket = server.accept();

            DataInputStream dis = new DataInputStream(socket.getInputStream());

            String name = dis.readUTF();
            DataOutputStream fos = new DataOutputStream( new FileOutputStream(name) );
            long size = dis.readLong();

            long count = 0;
            byte[] buff = new byte[2000];
            while (count < size) {
                int n = dis.read(buff);
                count += n;
                //dis.read(buff, 0, n);
                fos.write(buff, 0, n);
                fos.flush();
            }

            fos.close();
            dis.close();
            socket.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
