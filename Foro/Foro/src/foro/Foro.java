/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foro;

import foro.networking.tcp.TcpClient;
import foro.networking.tcp.TcpServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author wwwda
 */
public class Foro {
public String Usuario;
public Login login;  
public Publicaciones pub;


public Foro(){
    login=new Login();
    
}
public void setUsuario(){

       while(!login.getREGISTRADO()){
         login.setVisible(true);
        }
        
        Usuario=login.getUsuario(); 
        login.setVisible(false);
        pub=new Publicaciones(Usuario);
       
         System.out.println("El usuario es: "+Usuario);
      
      
}


public void PantallaPrincipal(){
 pub.setVisible(true);
}


    public static void main(String[] args) {
     Foro foro=new Foro();
     foro.setUsuario();    
     foro.PantallaPrincipal();
     
      
            
    }
    
}
