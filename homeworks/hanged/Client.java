import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        try {
            final int PORT = 6969;
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            String host = "localhost";
            Socket cl = new Socket(host, PORT);
            cl.setReuseAddress(true);
            PrintWriter pw = new PrintWriter(cl.getOutputStream());

            System.out.println("\nConexion exitosa...\n Seleccione una dificultad {0, 1, 2}: ");
            pw.println(br1.readLine());
            pw.flush();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            final String word = br2.readLine();
            System.out.println(word);

            if (play(word)) System.out.println("Ha ganado :D"); 
            else {
                System.out.println("Se la pelo D:");
                System.out.println("La cadena era " + word);
            }

            pw.close(); br1.close(); br2.close(); cl.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String repeatString(final String s, final int n) {
        return String.join("", Collections.nCopies(n, s));
    }

    // inclusive range
    public int randomInt(final int min, final int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    private Set<Integer> initialHelp(String word) {
        Set<Integer> indexes = new HashSet<>();
        final int helps = Math.floorDiv(word.length(), 3), b = word.length() - 1;
        while (indexes.size() < helps) indexes.add(randomInt(0, b));
        return indexes;
    }

    private String discoverCurrentInput(
                final Set<Integer> indexes, final String input, final String word) {
        char[] chars =input.toCharArray();
        indexes.forEach(i -> chars[i] = word.charAt(i));
        return String.valueOf(chars); 
    }

    private boolean play(final String word) {
        int lives = 3;
        Set<Integer> indexes = initialHelp(word);
        String current = repeatString("_", word.length());
        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < lives; ++i) {
            current = discoverCurrentInput(indexes, current, word);
            System.out.println("Palabra actual: " + current);
            System.out.print("Intente nuevamente: ");
            try {
                String input = br1.readLine(); // may throw IOException
                matchedIndexesSet(input, word).forEach(j -> indexes.add(j));
                if (indexes.size() == word.length()) return true; // Gano !
            } catch (IOException e) { 
                e.printStackTrace(); 
                break;
            } 
        }
        return false; // Se la pelo :c
    }

    private Set<Integer> matchedIndexesSet(final String input, final String source) {
        Set<Integer> indexes = new HashSet<>();
        for (int i = 0; i < source.length(); ++i) {
            if (input.charAt(i) == source.charAt(i)) indexes.add(i);
        }
        return indexes;
    }
}
