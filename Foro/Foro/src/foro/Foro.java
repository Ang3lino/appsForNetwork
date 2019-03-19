/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foro;

/**
 *
 * @author wwwda
 */
public class Foro {
public String Usuario;
   
public Foro(){
}
public void setUsuario(){
  Login login=new Login();
        while(!login.getREGISTRADO()){
         login.setVisible(true);
            System.out.println("dentro del ciclo");
        }
        
        Usuario=login.getUsuario();   
        if(login.getREGISTRADO()){
         System.out.println("El usuario es: "+Usuario);
         login.setVisible(false);
         login.dispose();
        }
}

public void PantallaPrincipal(){
 Publicaciones pub=new Publicaciones();
 pub.setVisible(true);

}



    public static void main(String[] args) {
      Foro foro=new Foro();
      foro.setUsuario();
      foro.PantallaPrincipal();
      
            
    }
    
}
