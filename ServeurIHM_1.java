/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wivi;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoProcessorException;
import javax.media.Processor;
import javax.media.control.TrackControl;
import javax.media.format.UnsupportedFormatException;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.rtp.InvalidSessionAddressException;
import javax.media.rtp.RTPManager;
import javax.media.rtp.SendStream;
import javax.media.rtp.SessionAddress;
/**
 *
 * @author BENAMAR
 * Server used for two clients with sockets for 
 * getting ip adress and rtp for sending webcam video
 */
public class ServeurIHM_1 extends javax.swing.JFrame {

    public String adressclient , adressclient2 ; 
    private MediaLocator videoLocator ;
    private Processor WebcamCessor = null ;
   public boolean accept ;
   public int port ;
  
    /**
     * Creates new form ClientIHM
     */
    public ServeurIHM_1() {
        initComponents();
        
    
        afficher.setEditable(false);
        jPanel1.setVisible(false);
       
        
        // new ClientIHM().setVisible(true);
      
        
    }

    
    public void getadress(){
        
     /* if (ipcli1.getText().length()== 0 ||
          ipcli2.getText().length()== 0 ||
          ipcli3.getText().length()== 0 ||
          ipcli4.getText().length() == 0  ) {
          
          ecritureAffichage(" Aucune adresse n'a été saisie");  
      } else if (ipcli1.getText().length() > 3 ||
          ipcli2.getText().length() > 4 ||
          ipcli3.getText().length() > 4  ||
          ipcli4.getText().length() > 4 ) {
          
          ecritureAffichage(" Verifier la longueur des champs !! ");   
      } 
      
      
      else { 
          adressclient = new String (
                          ipcli1.getText()
                          + "." + ipcli2.getText() 
                          + "." + ipcli3.getText()
                          + "." + ipcli4.getText()
                                     );*/
  
    
    
    
    
        
        //configure the source webcam
      ecritureAffichage("Configuration de la source (webcam)");
        videoLocator = new MediaLocator("vfw://0");
      
   
        try {
                    ecritureAffichage("Server waiting ...");
        
            //create a processor using the medialocator
            ecritureAffichage("Creation du processor");
            
            WebcamCessor = Manager.createProcessor(videoLocator);
            
            
        }  catch (NoProcessorException ex) {
            
        } catch (IOException ex) {
              Logger.getLogger(ServeurIHM_1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //call methods to send the flux video
       ecritureAffichage("Configuration du processor");
        configure(WebcamCessor);
        ecritureAffichage("Mise au bon format du flux");
        SetSupportedFormat(WebcamCessor);
        ecritureAffichage("Processor prêt ! ");
        WebcamCessor = realize(WebcamCessor);
        ecritureAffichage("Demarrage du processor");
        Demarre(WebcamCessor);
        ecritureAffichage("Creation du RTPManager");
        createRTPManager(WebcamCessor);
        
      
  
    }
    
    
     public Processor configure(Processor p) {
          
          while (p.getState() < Processor.Configured) {
              
              p.configure();    
          }
          return p ;
      }
      
      public void SetSupportedFormat(Processor p) {
          
          ContentDescriptor cd = new ContentDescriptor(ContentDescriptor.RAW_RTP);
          p.setContentDescriptor(cd);
          
          TrackControl track[] = p.getTrackControls();
          for(int i = 0 ; i< track.length; i++) {
              
              Format suppFormats[] = track[i].getSupportedFormats();
              
              if (suppFormats.length > 0 ) {
                  track[i].setFormat(suppFormats[0]);
              } else {
                  track[i].setEnabled(false);
              }
          }
      }
      
      public Processor realize(Processor p) {
          
          while (p.getState()< Processor.Realized) {
              p.realize();
          
      }
      return p ; 
      
}
             
          public void Demarre(Processor p) {
              
              p.start();
          }
          
          public void createRTPManager(Processor p)
                  
          {
              
              DataSource OutputSource = p.getDataOutput();
              
              RTPManager rtpm = RTPManager.newInstance();
              
              
          if (adressclient != null || adressclient2 != null )   {     try {
                  
             
          jLabel3.setText("");
         jPanel1.setVisible(true);
                  SessionAddress localaddr = new SessionAddress(InetAddress.getLocalHost(),5060);
                  rtpm.initialize(localaddr);
                 
             SessionAddress client1 = new SessionAddress(InetAddress.getByName(adressclient),5060);
                rtpm.addTarget(client1);
                  
                SessionAddress client2 = new SessionAddress(InetAddress.getByName(adressclient2),5060);
                rtpm.addTarget(client2);
                  
                  
                  SendStream ss2 = rtpm.createSendStream(OutputSource,0);
                         
                  ss2.start();
                  ecritureAffichage("Started");
          }
                 
              catch (UnknownHostException ex) {
            Logger.getLogger(ServeurIHM_1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidSessionAddressException ex) {
            Logger.getLogger(ServeurIHM_1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServeurIHM_1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedFormatException ex) {
            Logger.getLogger(ServeurIHM_1.class.getName()).log(Level.SEVERE, null, ex);
        }
          }
          
    
      
          }


    public void ecritureAffichage(String s) {
        afficher.append(s+"\n");
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        afficher = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Wivid - Serveur");
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(500, 480));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wivi/CCTV-Camera-icon.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wivi/wiviserv.jpg"))); // NOI18N

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setText("Démarrer vidéo");
        jButton2.setToolTipText("Commencer le flux vidéo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setText("Connecter");
        jButton1.setToolTipText("Obtenir les adresses client");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("En Attente des clients ... ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(67, 67, 67)
                        .addComponent(jButton2))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel4)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusCycleRoot(true);

        jLabel7.setBackground(new java.awt.Color(204, 204, 204));
        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("Client 1 : ");

        jLabel2.setBackground(new java.awt.Color(153, 153, 153));

        jLabel8.setBackground(new java.awt.Color(204, 204, 204));
        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Client 2 : ");

        jLabel6.setBackground(new java.awt.Color(153, 153, 153));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        afficher.setColumns(20);
        afficher.setRows(5);
        jScrollPane2.setViewportView(afficher);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
       

             try
        {
          ServerSocket ser = new ServerSocket(9999);
            Socket sock = ser.accept();
          
            BufferedReader ed = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            adressclient = ed.readLine();  
            jPanel1.setVisible(true);
               
            jLabel2.setText("Adresse IP : "+ adressclient); 
               
           if (adressclient != null) {
               jLabel3.setText("");
             ser.close();
             sock.close();
           
             }
             
            
        ServerSocket ser2 = new ServerSocket(9998);
           // ser2.setSoTimeout(10000);
            Socket sock2 = ser2.accept();
             BufferedReader ed2 = new BufferedReader(new InputStreamReader(sock2.getInputStream()));
            adressclient2 = ed2.readLine();
            jPanel1.setVisible(true);
             jLabel6.setText("Adresse IP: "+ adressclient2); 
             
             if (adressclient2 != null) {
             ser2.close();
             sock2.close();
             }
             
           
              
        }
        
  
      catch(Exception ex){}
      
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        getadress();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServeurIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServeurIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServeurIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServeurIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServeurIHM_1().setVisible(true);
              
                
          
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea afficher;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
