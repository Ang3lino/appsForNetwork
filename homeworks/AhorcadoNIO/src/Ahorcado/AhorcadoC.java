/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ahorcado;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.*;
import java.nio.*;
import java.net.*;
import java.util.Iterator;


public class AhorcadoC {
    
    public static void imprimir(String palabra){
        String palabraImprimir = "";
        for(int i = 0; i < palabra.length(); i++){
            palabraImprimir += palabra.charAt(i) + " ";
        }
        System.out.println(palabraImprimir);
    }
    
     public static void main(String[] args){
       String host = "127.0.0.1";
       String palabra = null;
       int indice = 0;
       int vidas = 4;
       int pto=9999;
       try{
           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
           SocketChannel cl = SocketChannel.open();
           cl.configureBlocking(false);
           Selector sel = Selector.open();
           cl.connect(new InetSocketAddress(host,pto));
           cl.register(sel,SelectionKey.OP_CONNECT);
           int bandera = 0;
           while(true){
               sel.select();
               Iterator<SelectionKey>it =sel.selectedKeys().iterator();
               while(it.hasNext()){
                   SelectionKey k = (SelectionKey)it.next();
                   it.remove();
                   if(k.isConnectable()){
                       SocketChannel ch = (SocketChannel)k.channel();
                       if(ch.isConnectionPending()){
                           try{
                               ch.finishConnect();
                               System.out.println("Conexion establecida.. Puedes salir presionando ENTER. Escribe la dificultad que deseas para tu palabra: ");
                           }catch(Exception e){
                               e.printStackTrace();
                           }//catch
                       }//if_conectionpending
                       //ch.configureBlocking(false);
                       ch.register(sel, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                       continue;
                   }//if
                   if(k.isWritable()){
                       SocketChannel ch2 = (SocketChannel)k.channel();
                       if(bandera==0){
                           String nivel = br.readLine();
                           ByteBuffer b = ByteBuffer.wrap(nivel.getBytes());
                           ch2.write(b);
                       }
                       else if(bandera == 1){                 
                           System.out.println("Ingresa la letra que crees correcta: ");
                           String letra = br.readLine();
                           Objeto ejercicio = new Objeto(palabra, indice, letra);
                           ByteArrayOutputStream baos = new ByteArrayOutputStream();
                           ObjectOutputStream oos = new ObjectOutputStream(baos);
                           oos.writeObject(ejercicio);
                           oos.flush();
                           ByteBuffer b2 = ByteBuffer.wrap(baos.toByteArray());
                           ch2.write(b2);
                           /*ch2.close();
                           System.exit(0);*/
                       }
                       else{
                           palabra = "SALIR";
                           Objeto ejercicio = new Objeto(palabra, indice, "");
                           ByteArrayOutputStream baos = new ByteArrayOutputStream();
                           ObjectOutputStream oos = new ObjectOutputStream(baos);
                           oos.writeObject(ejercicio);
                           oos.flush();
                           ByteBuffer b2 = ByteBuffer.wrap(baos.toByteArray());
                           ch2.write(b2);
                           ch2.close();
                           System.exit(0);
                       }
                       k.interestOps(SelectionKey.OP_READ);
                       continue;
                       
                   } 
                   else if(k.isReadable()){
                       SocketChannel ch2 = (SocketChannel)k.channel();
                       ByteBuffer b = ByteBuffer.allocate(2000);
                       b.clear();
                       int n = ch2.read(b);
                       b.flip();
                       ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(b.array()));
                       
                       if(bandera == 0){
                            vidas = 4;
                            System.out.println("Tu palabra ha sido seleccionada. Tienes " + vidas + " vidas para adivinarla");
                            Objeto ejercicio = (Objeto)ois.readObject();
                            palabra = ejercicio.getPalabra();
                            indice = ejercicio.getIndice();
                            imprimir(palabra);
                            bandera = 1;
                       } else{
                           System.out.println("\nPalabra Actualizada: ");
                            Resultado resultado = (Resultado)ois.readObject();
                           //Evaluacion del resultado al intentar con cierta letra
                           imprimir(resultado.getPalabra());
                           palabra = resultado.getPalabra();
                            if(resultado.getCompletado() == 1){
                                System.out.println("Felicidades! Ganaste el juego con " + vidas + " vidas");
                                bandera = 2;
                            }
                            else if(resultado.getError() == 1 && vidas-1 == 0){
                                System.out.println("HAS PERDIDO EL JUEGO");
                                bandera = 2;
                            }
                            else if(resultado.getError()==1){
                                vidas--;
                                System.out.println("Vidas = " + vidas);
                                System.out.println("Ups! Letra incorrecta");                                
                            }
                            else{
                                System.out.println("Vidas  = " + vidas);
                                System.out.println("Bien! Letra correcta");
                            }
                       }
                       k.interestOps(SelectionKey.OP_WRITE);
                       continue;
                   }//if
               }//while
           }//while
       }catch(Exception e){
           e.printStackTrace();
       } 
    }//main
}