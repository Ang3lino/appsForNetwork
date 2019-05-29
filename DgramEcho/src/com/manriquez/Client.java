
import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String args[]) throws IOException {
        while (true) {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            String request = bf.readLine();
            EchoClient client = new EchoClient();
            String response = client.sendEcho(request);
            System.out.println(response);
        }
    }
}