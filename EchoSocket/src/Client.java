import java.io.*;
import java.net.Socket;
import java.util.logging.SocketHandler;

public class Client {
    public static void main(String[] args) {
        try {
            int PORT = 9000;
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

            String host = "localhost";
            Socket cl = new Socket(host, PORT);
            cl.setReuseAddress(true);
            System.out.println("\nSuccessful conection...\nWrite something");

            PrintWriter pw = new PrintWriter(cl.getOutputStream());
            pw.println(br1.readLine());
            pw.flush();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));

            String msg = br2.readLine();
            System.out.println(msg);

            pw.close();
            br1.close();
            br2.close();
            cl.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
