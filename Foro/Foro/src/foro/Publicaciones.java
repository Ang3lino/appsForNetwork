/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foro;

import foro.networking.MyState;
import foro.networking.Pack;
import foro.networking.tcp.TcpClient;
import foro.networking.tcp.TcpServer;
import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Publicaciones extends javax.swing.JFrame implements Observer{
private String FechaBusqueda; 
private ArrayList<JButton> publications;
private ArrayList<Boolean> BotonOprimido;
private JTextArea comentarios;
private ArrayList<JTextArea> cuerpo;
private String description;
private int indice,indice2;
private String Usuario;
private Login login;
private String Titulo;
private boolean LabelPrincipal;
private TcpClient cliente;
private ArrayList <Pack> posts;
private Pack post_actual;

    public void setLogin(Login login) {
        this.login = login;
       
    }

    public Login getLogin() {
        return login;
    }
    /**
     * Creates new form Publicaciones
     */
    public Publicaciones(String Usuario) {
        initComponents();
        login=new Login();
        publications=new ArrayList<>();
        BotonOprimido=new ArrayList<>();
        cuerpo=new ArrayList <>();
        comentarios=new JTextArea();
        LabelPrincipal=false;
        this.Usuario=Usuario;
        jLabelUsuario.setText("Usuario: @"+Usuario);
        post_actual=new Pack();
        
        cliente=new TcpClient();
        cliente.addObserver(this);
        posts=cliente.getListPost();
        
  
         
    }
    
    public void addBotton(String contenido, int idPost, Pack post){
       JButton button=new JButton("JButton"+indice);
       button.setText(contenido);
       button.setForeground(Color.red);
       Font fuente=new Font("Monospaced", Font.BOLD, 12);
       button.setFont(fuente);
       button.setBorderPainted( false );
       button.setBackground(new Color(255,255,204));
      
       
       jPanelPublicaciones.add(button);
       publications.add(button);  
     //  BotonOprimido.add(false);
       jPanelPublicaciones.updateUI();
    
       ///Mostramos el cuerpo del Post
       button.addActionListener((ActionEvent e) -> {
           jPanelArticulo.removeAll();   
        
           Font fuente2=new Font("Monospaced", Font.BOLD, 18);
           JLabel label=new JLabel("Jlabel");
           label.setText(contenido);
           label.setForeground(Color.blue);
           label.setFont(fuente2);
           label.setBackground(new Color(255,255,204));
           jPanelArticulo.add(label);
           
           JTextArea descripcion_post=new JTextArea("Descripcion");
           Font fuente3=new Font("Monospaced", Font.BOLD, 16);
           descripcion_post.setFont(fuente3);
           
          
           try {
               TcpClient cliente=new TcpClient();
               TcpClient cliente2=new TcpClient();
               post_actual=cliente.downloadPack(idPost);
               post_actual.setPostId(idPost);
               System.out.println("poust actual: "+post_actual.toString());
               descripcion_post.setText(post_actual.getDescription());
               System.out.println("Descripcion :"+ post_actual.getDescription());
               jPanelArticulo.add(descripcion_post);
               
               if(post_actual.getFileUrl()!=null){
               JLabel imagen=new JLabel();
               String path=post_actual.getImage().getAbsolutePath();
               imagen.setIcon(new ImageIcon(path));
               jPanelArticulo.add(imagen);
               }
               
               
               ArrayList<Pack> comentarios=cliente2.getComments(idPost);
               System.out.println("get_comments("+idPost+")"+ "SIZE comentarios pack "+comentarios.size());
               JTextArea comments=new JTextArea();
               for(int i=0;i<comentarios.size();i++){
                   
                comments.append("\n\n"+comentarios.get(i).getmComment());
                   System.out.println(idPost+" "+comentarios.get(i).getmComment());
               }
               
              jPanelArticulo.add(comments);
              
               
           } catch (IOException ex) {
               Logger.getLogger(Publicaciones.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(Publicaciones.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           jPanelArticulo.updateUI();
                    
       });
       
       indice++;
     
    }
    

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }



public void setFecha() {
   FechaBusqueda=(String)jComboBoxyear.getSelectedItem()+"-"+(String)jComboBoxMes.getSelectedItem()+"-"+(String)jComboBoxDia.getSelectedItem();
   
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        jButtonAddComentario = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabelNameUsuario = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanelPublicaciones = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanelArticulo = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jComboBoxDia = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxMes = new javax.swing.JComboBox<>();
        jComboBoxyear = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButtonFecha = new javax.swing.JButton();
        jTextFieldTitulo = new javax.swing.JTextField();
        jLabelUsuario = new javax.swing.JLabel();
        jButtonTitulo = new javax.swing.JButton();
        jButtonAddPub = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollBar2 = new javax.swing.JScrollBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 78, 50));

        jButtonAddComentario.setText("Agregar Comentario");
        jButtonAddComentario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddComentarioActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel4.setBackground(new java.awt.Color(255, 255, 153));
        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("PUBLICACIONES:");

        jPanelPublicaciones.setBackground(new java.awt.Color(255, 255, 204));
        jPanelPublicaciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelPublicaciones.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane2.setViewportView(jPanelPublicaciones);

        jPanelArticulo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelArticulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelArticulo.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane3.setViewportView(jPanelArticulo);

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jComboBoxDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"01","02","03","04",
            "05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22"
            ,"23","24","25","26","27","28","29","30","31"

        }));

        jLabel3.setText("Dia");

        jComboBoxMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04","05","06","07"
            ,"08","09","10","11","12"}));
jComboBoxMes.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        jComboBoxMesActionPerformed(evt);
    }
    });

    jComboBoxyear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"2019", "2018", "2017", "2016", "2015" }));
    jComboBoxyear.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBoxyearActionPerformed(evt);
        }
    });

    jLabel2.setText("Mes");

    jLabel1.setText("Año");

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
            .addGap(19, 19, 19)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jComboBoxyear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1))
            .addGap(18, 18, 18)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jComboBoxMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel3)
                .addComponent(jComboBoxDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(jLabel2)
                .addComponent(jLabel1))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jComboBoxDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jComboBoxMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jComboBoxyear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
    );

    jButtonFecha.setBackground(new java.awt.Color(51, 51, 51));
    jButtonFecha.setForeground(new java.awt.Color(255, 255, 255));
    jButtonFecha.setText("Buscar por Fecha");
    jButtonFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    jButtonFecha.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonFechaActionPerformed(evt);
        }
    });

    jTextFieldTitulo.setText("palaba clave");
    jTextFieldTitulo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextFieldTituloActionPerformed(evt);
        }
    });

    jLabelUsuario.setBackground(new java.awt.Color(255, 255, 255));
    jLabelUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabelUsuario.setForeground(new java.awt.Color(255, 255, 255));
    jLabelUsuario.setText("Usuario:");

    jButtonTitulo.setBackground(new java.awt.Color(51, 51, 51));
    jButtonTitulo.setForeground(new java.awt.Color(255, 255, 255));
    jButtonTitulo.setText("Buscar");
    jButtonTitulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    jButtonTitulo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonTituloActionPerformed(evt);
        }
    });

    jButtonAddPub.setBackground(new java.awt.Color(51, 51, 51));
    jButtonAddPub.setForeground(new java.awt.Color(255, 255, 255));
    jButtonAddPub.setText("Crear Publicacion");
    jButtonAddPub.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    jButtonAddPub.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonAddPubActionPerformed(evt);
        }
    });

    jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(255, 255, 255));
    jLabel5.setText("WHY NOT?FORO DE DISCUCION");

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButtonFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(30, 30, 30)
            .addComponent(jTextFieldTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jButtonTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jButtonAddPub, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(245, 245, 245)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUsuario)
                    .addComponent(jButtonAddPub))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonFecha)
                        .addComponent(jTextFieldTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonTitulo)))))
    );

    jButton1.setText("actualizar");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton1))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(22, 22, 22)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jButtonAddComentario)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addContainerGap())
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(jLabelNameUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(378, 378, 378))
        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabelNameUsuario)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(1, 1, 1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButtonAddComentario))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(246, 246, 246))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(2, 2, 2)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(18, 18, 18)
            .addComponent(jScrollBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 748, Short.MAX_VALUE)
            .addContainerGap())
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxyearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxyearActionPerformed

    }//GEN-LAST:event_jComboBoxyearActionPerformed

    private void jButtonFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFechaActionPerformed
      setFecha();
        
        if (FechaBusqueda==null){
          JOptionPane.showMessageDialog(null,"Introduce una Fecha de Busqueda");
        }
         
        else{
      
        TcpClient cliente=new TcpClient();
        cliente.addObserver(this);
        posts=cliente.getPostsByKeyword(FechaBusqueda); 
       
        
        }
          
       
   
    
          
           
 
    }//GEN-LAST:event_jButtonFechaActionPerformed

    private void jButtonTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTituloActionPerformed
    
    String keyword=jTextFieldTitulo.getText();
    TcpClient cliente=new TcpClient();
    cliente.addObserver(this);
    posts=cliente.getPostsByKeyword(keyword);
    
    }//GEN-LAST:event_jButtonTituloActionPerformed

    private void jComboBoxMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxMesActionPerformed

    private void jTextFieldTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTituloActionPerformed

    private void jButtonAddPubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddPubActionPerformed
      
       Publicacion pub=new Publicacion(new javax.swing.JFrame(), true);
       pub.setVisible(true);     
      // addBotton(pub.toString());  
        Pack post=new Pack(MyState.UPLOAD);
 
        post.addPost(Usuario,pub.getmCategory(),pub.getmTitile(), pub.getmDescription(),pub.getImg());
      
        TcpClient cliente=new TcpClient();
        cliente.uploadPack(post);
        
        
    }//GEN-LAST:event_jButtonAddPubActionPerformed

    
    
    private void jButtonAddComentarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddComentarioActionPerformed
       JTextArea text=new JTextArea("text"+indice);
       Font fuente=new Font("Monospaced", Font.BOLD, 12);
       text.setFont(fuente);
       text.setText(jTextArea1.getText()+"\n @"+Usuario);
     //  comentarios.setText("\n\n"+jTextArea1.getText()+"\n @"+Usuario);
      
       TcpClient cliente=new TcpClient();
       cliente.sendComment(post_actual.getPostId(),Usuario,jTextArea1.getText()+"   @"+Usuario);
       System.out.println("id_postactual: "+post_actual.getPostId());
       text.setSize(5, 10);
       jPanelArticulo.add(text);
       indice2++;
       cuerpo.add(text);
       jTextArea1.setText("");
       jPanelArticulo.updateUI();  
    }//GEN-LAST:event_jButtonAddComentarioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        TcpClient cl=new TcpClient();
        cl.addObserver(this);
        posts=cl.getListPost();
    
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAddComentario;
    private javax.swing.JButton jButtonAddPub;
    private javax.swing.JButton jButtonFecha;
    private javax.swing.JButton jButtonTitulo;
    private javax.swing.JComboBox<String> jComboBoxDia;
    private javax.swing.JComboBox<String> jComboBoxMes;
    private javax.swing.JComboBox<String> jComboBoxyear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelNameUsuario;
    public static javax.swing.JLabel jLabelUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelArticulo;
    private javax.swing.JPanel jPanelPublicaciones;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldTitulo;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
         ArrayList <Pack> posts=(ArrayList <Pack>)arg;
         jPanelPublicaciones.removeAll();
        for(int i=0;i<posts.size();i++){
            Pack post=new Pack();
            post=posts.get(i);
            String contenido=post.listaPost();       
            System.out.println(post.getPostId());   
            addBotton(contenido,post.getPostId(),post);
        }
        jPanelPublicaciones.updateUI();
    }
}
