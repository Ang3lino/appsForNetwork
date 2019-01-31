import java.net.*;
import java.io.*;
import java.util.logging.SocketHandler;

public class Server {

    public static void main(String[] args) {
        int PORT = 9000;
        System.out.println("Waiting for a client");
        try {
            ServerSocket server = new ServerSocket(PORT); // create the server once
            while (true) {
                Socket s = server.accept(); // wait for a connection
                s.setReuseAddress(true);
                System.out.println("Established connection");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                            s.getInputStream()
                        )
                );
                // very important to call flush method, close doesn't call it
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                out.println(in.readLine());

                out.close();
                in.close();
                s.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
