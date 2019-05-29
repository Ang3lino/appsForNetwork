package Ahorcado;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.*;
import java.nio.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AhorcadoS {

    public static void main(String[] args) {
        int bandera = 0;
        int pto = 9999;
        try {
            ServerSocketChannel s = ServerSocketChannel.open();
            s.configureBlocking(false);
            s.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            s.socket().bind(new InetSocketAddress(pto));
            Selector sel = Selector.open();
            s.register(sel, SelectionKey.OP_ACCEPT);
            System.out.println("Servicio iniciado..esperando clientes..");
            List<String> palabras = new ArrayList<>();
            palabras.add("ave");
            palabras.add("red");
            palabras.add("abeja");
            palabras.add("domestico");
            palabras.add("neurona");
            palabras.add("plastico");
            palabras.add("tenochtitlan");
            palabras.add("murcielago");
            palabras.add("configuracion");
            while (true) {
                sel.select();
                Iterator<SelectionKey> it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey k = (SelectionKey) it.next();
                    it.remove();
                    if (k.isAcceptable()) {
                        SocketChannel cl = s.accept();
                        System.out.println("Cliente conectado desde->" + cl.socket().getInetAddress().getHostAddress() + ":" + cl.socket().getPort());
                        bandera = 0;
                        cl.configureBlocking(false);
                        cl.register(sel, SelectionKey.OP_READ);
                        continue;
                    }//if
                    if (k.isReadable()) {
                        SocketChannel ch = (SocketChannel) k.channel();
                        ByteBuffer b = ByteBuffer.allocate(2000);
                        b.clear();
                        int n = ch.read(b);
                        b.flip();
                        if (bandera == 0) {

                            String msj = new String(b.array(), 0, n);
                            int dificultad = (int) (Math.random() * 2);
                            String palabra;
                            System.out.println("Se recibio una peticion de nivel " + msj + ". La palabra designada es: ");
                            if (msj.equalsIgnoreCase("Facil")) {
                                palabra = palabras.get(dificultad);
                            } else if (msj.equalsIgnoreCase("Medio")) {
                                dificultad += 3;
                                palabra = palabras.get(dificultad);
                            } else {
                                dificultad += 6;
                                palabra = palabras.get(dificultad);
                            }
                            String palabraEscondida = "";
                            for (int i = 0; i < palabra.length(); i++) {
                                palabraEscondida += "_";
                            }
                            Objeto ejercicio = new Objeto(palabraEscondida, dificultad, "");
                            System.out.println(palabra + "\nDevolviendo palabra escondida...");

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ObjectOutputStream oos = new ObjectOutputStream(baos);
                            oos.writeObject(ejercicio);
                            oos.flush();
                            ByteBuffer b2 = ByteBuffer.wrap(baos.toByteArray());
                            bandera = 1;
                            ch.write(b2);
                        } else {
                            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(b.array()));
                            Objeto ejercicio = (Objeto) ois.readObject();
                            String palabra = ejercicio.getPalabra();

                            if (palabra.equalsIgnoreCase("SALIR")) {
                                System.out.println("El juego terminó! El cliente se va..");
                                ch.close();
                                continue;
                            } else {
                                int indice = ejercicio.getIndice();
                                String letra = ejercicio.getLetra();
                                String palabraActualizada = "";
                                int error = 1, terminado = 1;
                                for (int i = 0; i < palabra.length(); i++) {
                                    if (palabra.charAt(i) == '_' && palabras.get(indice).charAt(i) == letra.charAt(0)) {
                                        error = 0;
                                        palabraActualizada += letra.charAt(0);
                                    } else {
                                        if (palabra.charAt(i) == '_') {
                                            terminado = 0;
                                        }
                                        palabraActualizada += palabra.charAt(i);
                                    }
                                }
                                System.out.println("La palabra a buscar es " + palabras.get(indice) + ". La cadena recibida es " + palabra + " Con letra : " + letra.charAt(0) + " . El string a regresar es: " + palabraActualizada);
                                Resultado actualizacion = new Resultado(palabraActualizada, error, terminado);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(baos);
                                oos.writeObject(actualizacion);
                                oos.flush();
                                ByteBuffer b2 = ByteBuffer.wrap(baos.toByteArray());
                                ch.write(b2);
                                continue;
                            }//else
                        }
                    }//if_readable

                }//while
            }//while
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }//main
}
