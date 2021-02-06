/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wivi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.CannotRealizeException;
import javax.media.DataSink;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.Processor;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import javax.media.rtp.InvalidSessionAddressException;
import javax.media.rtp.RTPManager;
import javax.media.rtp.ReceiveStream;
import javax.media.rtp.ReceiveStreamListener;
import javax.media.rtp.SessionAddress;
import javax.media.rtp.event.NewReceiveStreamEvent;
import javax.media.rtp.event.ReceiveStreamEvent;
import javax.media.util.BufferToImage;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author BENAMAR
 */
public class ClientIHM2 extends javax.swing.JFrame {
  //  MotionDetectionEffect mdd = MotionDetectionEffect();
    
   
     
    public String adressserver  , adressserver2 ;
    public URL fileurl;
 public Player p , p2 ;
 public Processor processor , processor2 ;
   
    Image Img1, Img2 ;
    Component videoScreen , localvideo , videoScreen2 ;

    /**
     * Creates new form ClientIHM
     */
    public ClientIHM2() {
        initComponents();
    
        affichage.setEditable(false);
        jToolBar1.setFloatable(false);
        
       //new ClientIHM().setVisible(true);
      
        
    }
    
    

    
    public void getadress(){
        
      if (ipsrv1.getText().length()== 0 ||
          ipsrv2.getText().length()== 0 ||
          ipsrv3.getText().length()== 0 ||
          ipsrv4.getText().length() == 0  ) {
          
          ecritureAffichage(" Aucune adresse n'a été saisie");   
      } else if (ipsrv1.getText().length() > 4 ||
          ipsrv2.getText().length() > 4 ||
          ipsrv3.getText().length() > 4 ||
          ipsrv4.getText().length() > 4 ) {
          
          ecritureAffichage(" Verifier la longueur des champs !! ");   
      } 
      
      
      else { 
          adressserver = new String (
                          ipsrv1.getText()
                          + "." + ipsrv2.getText() 
                          + "." + ipsrv3.getText()
                          + "." + ipsrv4.getText()
                                     );
          
          
          
    
        
         ecritureAffichage("En attente du flux video ... ");
         
          RTPManager VideoManager = RTPManager.newInstance();
          VideoManager.addFormat(new VideoFormat(
                     VideoFormat.H263_RTP), 18);
          
          try {
              
              //create local session address
              SessionAddress add = new SessionAddress (InetAddress.getLocalHost(),5060);
              
              //Initialize the RTPManager usiing the local sessionaddress
              VideoManager.initialize(add);
              
              //create SessionAddress for source
             SessionAddress add2 = new SessionAddress(InetAddress.getByName(adressserver),5060);
           
             //add this SessionAddress to the RTPMAnaer
           VideoManager.addTarget(add2);
             
          
           
        try
        {
            Socket sock = new Socket(adressserver,9999);
            
            PrintStream pr = new PrintStream(sock.getOutputStream());
            ecritureAffichage("Connexion avec le serveur : "+adressserver);
            String temp = (sock.getLocalAddress().getHostAddress());
            pr.println(temp);
            BufferedReader gt = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String tm = gt.readLine();
          
            
          VideoManager.addReceiveStreamListener(new ReceiveStreamListener(){ 
              
              public void update (ReceiveStreamEvent event) {
                  
                  if (event instanceof NewReceiveStreamEvent) {
                      ecritureAffichage("Reception d'un flux");
                    //new flux received 
                      ReceiveStream rs = event.getReceiveStream();
                     
                      try {
                          p = Manager.createRealizedPlayer(rs.getDataSource());
                          
                        // DataSource dss  = Manager.createCloneableDataSource(rs.getDataSource());
                       //  processor = Manager.createProcessor(rs.getDataSource());
                          //if the palyer has a visual component , then create new jframe 
                      
                          if(p.getVisualComponent()!= null) {
                         /*     JFrame fenetre = new JFrame ("flux Video");
                              fenetre.setSize(320,280);
                              fenetre.getContentPane().add(p.getVisualComponent());
                              fenetre.setVisible(true);
                              fenetre.setLocation(100,0);*/
               
                              
                       //
                              localplayer f = new localplayer(); 
                             f.setVisible(true);
                               videoScreen = p.getVisualComponent();
                            videoScreen.setSize(f.jPanel2.getSize());
                               f.jPanel2.removeAll();
                               f.jPanel2.add(videoScreen,BorderLayout.CENTER);
                               f.jPanel2.repaint();
                               f.jPanel2.revalidate();
                           

                        
                        
                        
                          }
                              p.start();
                              ecritureAffichage("Flux Video Recu");
                          
                          
                          
                          
                      } catch (IOException ex) {
                          Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
                      } catch (NoPlayerException ex) {
                          Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
                      } catch (CannotRealizeException ex) {
                          Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
                      } //catch (CannotRealizeException ex) {
                        //  Logger.getLogger(ClientIHM.class.getName()).log(Level.SEVERE, null, ex);
                     // }
                      
                  }
              }
              
          });
        }
        catch(Exception ex)
        {
            
        }
      
          } catch (UnknownHostException ex) {
              Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
          } catch (InvalidSessionAddressException ex) {
              Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IOException ex) {
              Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
          }
          
               adressserver2 = ("169.254.17.2");
                 RTPManager VideoManager2 = RTPManager.newInstance();
          VideoManager2.addFormat(new VideoFormat(
                     VideoFormat.H263_RTP), 18);
           try {
              
              //create local session address
              SessionAddress add = new SessionAddress (InetAddress.getLocalHost(),5060);
              
              //Initialize the RTPManager usiing the local sessionaddress
              VideoManager.initialize(add);
              
              //create SessionAddress for source
             SessionAddress add3 = new SessionAddress(InetAddress.getByName(adressserver2),5060);
           
             //add this SessionAddress to the RTPMAnaer
           VideoManager.addTarget(add3);
             
          
           
        try
        {
            Socket sock2 = new Socket(adressserver2,9999);
            
            PrintStream pr2 = new PrintStream(sock2.getOutputStream());
            ecritureAffichage("Connexion avec le serveur : "+adressserver);
            String temp2 = (sock2.getLocalAddress().getHostAddress());
            pr2.println(temp2);
            BufferedReader gt2 = new BufferedReader(new InputStreamReader(sock2.getInputStream()));
            String tm2 = gt2.readLine();
          
            
          VideoManager2.addReceiveStreamListener(new ReceiveStreamListener(){ 
              
              public void update (ReceiveStreamEvent event) {
                  
                  if (event instanceof NewReceiveStreamEvent) {
                      ecritureAffichage("Reception d'un flux");
                    //new flux received 
                      ReceiveStream rs2 = event.getReceiveStream();
                     
                      try {
                          p2 = Manager.createRealizedPlayer(rs2.getDataSource());
                          
                        // DataSource dss  = Manager.createCloneableDataSource(rs.getDataSource());
                       //  processor = Manager.createProcessor(rs.getDataSource());
                          //if the palyer has a visual component , then create new jframe 
                      
                          if(p2.getVisualComponent()!= null) {
                         /*     JFrame fenetre = new JFrame ("flux Video");
                              fenetre.setSize(320,280);
                              fenetre.getContentPane().add(p.getVisualComponent());
                              fenetre.setVisible(true);
                              fenetre.setLocation(100,0);*/
               
                              
                       //
                              localplayer f = new localplayer(); 
                             f.setVisible(true);
                               videoScreen2= p2.getVisualComponent();
                            videoScreen2.setSize(f.jPanel4.getSize());
                               f.jPanel4.removeAll();
                               f.jPanel4.add(videoScreen2,BorderLayout.CENTER);
                               f.jPanel4.repaint();
                               f.jPanel4.revalidate();
                           

                        
                        
                        
                          }
                              p2.start();
                              ecritureAffichage("Flux Video 2  Recu");
                          
                          
                          
                          
                      } catch (IOException ex) {
                          Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
                      } catch (NoPlayerException ex) {
                          Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
                      } catch (CannotRealizeException ex) {
                          Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
                      } //catch (CannotRealizeException ex) {
                        //  Logger.getLogger(ClientIHM.class.getName()).log(Level.SEVERE, null, ex);
                     // }
                      
                  }
              }
              
          });
        }
        catch(Exception ex)
        {
            
        }
      
          } catch (InvalidSessionAddressException ex) {
              Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IOException ex) {
              Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
          }
        
}
          
}   
              
    
        

    public void ecritureAffichage(String s) {
        affichage.append(s+"\n");
    }
     
        
      public void capture () { 
           try {
 
            Thread.sleep(100);//wait 10 seconds before capturing photo
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
        }
 
           
            FrameGrabbingControl fgc = (FrameGrabbingControl) p.getControl("javax.media.control.FrameGrabbingControl");
 
            Buffer buf = fgc.grabFrame();//grab the current frame on video screen
 
            BufferToImage btoi = new BufferToImage((VideoFormat) buf.getFormat());
            
            Buffer buf2 = fgc.grabFrame();
            
            
            
            
             Img1 = btoi.createImage(buf);
          //    Img2 = btoi.createImage(buf);
           
             ImageIcon icon1 = new ImageIcon(Img1);
             
           //  flux fl = new flux();
             
           // f.lblcapture.setIcon((icon1));
         
            
                 
            // save image to file
              Calendar cal = new GregorianCalendar();
              int month = cal.get(Calendar.MONTH) +1;
              int day = cal.get(Calendar.DAY_OF_MONTH);
              int year = cal.get(Calendar.YEAR);
              int milisecond = cal.get(Calendar.MILLISECOND);
              int second = cal.get(Calendar.SECOND);
              int minut = cal.get(Calendar.MINUTE);
              int hour = cal.get(Calendar.HOUR);
           
          String n = ("D:\\MyPhoto♦Date_ "+day+"-"+month + "-" + year + " ♦Time_ "+hour+" h"+minut+" min"+second
                  + milisecond +" milisec.jpg" );
                      
             saveImagetoFile(Img1,n);
       
            
    }
      
    
     public void saveImagetoFile(Image img, String string) {
 
           int w = img.getWidth(null);
           int h = img.getHeight(null);
           BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
           Graphics2D g2 = bi.createGraphics();
 
           g2.drawImage(img, 0, 0, null);
 
           g2.dispose();
 
           String fileType = string.substring(string.indexOf('.') + 1);
       try {
           ImageIO.write(bi, fileType, new File(string));
       } catch (IOException ex) {
           Logger.getLogger(ClientIHM2.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
    
    

    
   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jFileChooser2 = new javax.swing.JFileChooser();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        ipsrv2 = new javax.swing.JTextField();
        ipsrv4 = new javax.swing.JTextField();
        ipsrv3 = new javax.swing.JTextField();
        ipsrv1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        affichage = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btnpic = new javax.swing.JButton();
        btnx = new javax.swing.JButton();

        jDialog1.setTitle("Choisir un ficier vidéo/son");
        jDialog1.setMinimumSize(new java.awt.Dimension(601, 397));

        jFileChooser2.setDragEnabled(true);
        jFileChooser2.setMinimumSize(new java.awt.Dimension(601, 397));
        jFileChooser2.setPreferredSize(new java.awt.Dimension(528, 326));
        jFileChooser2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Wivid - Client");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Adresse IP du Serveur", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N

        ipsrv2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipsrv2ActionPerformed(evt);
            }
        });

        ipsrv4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipsrv4ActionPerformed(evt);
            }
        });

        ipsrv3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipsrv3ActionPerformed(evt);
            }
        });

        ipsrv1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipsrv1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("•");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("•");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("•");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(ipsrv1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ipsrv2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ipsrv3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ipsrv4, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ipsrv3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ipsrv4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ipsrv1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ipsrv2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)))
        );

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setText("Connect");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wivi/CCTV-Camera-icon.png"))); // NOI18N

        affichage.setColumns(20);
        affichage.setRows(5);
        jScrollPane2.setViewportView(affichage);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\BENAMAR\\Desktop\\wi.jpg")); // NOI18N

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        btnpic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wivi/2savepicture.png"))); // NOI18N
        btnpic.setToolTipText("Enregistrer une photo");
        btnpic.setFocusable(false);
        btnpic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnpic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnpic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpicActionPerformed(evt);
            }
        });
        jToolBar1.add(btnpic);

        btnx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wivi/x.png"))); // NOI18N
        btnx.setToolTipText("Exit");
        btnx.setFocusable(false);
        btnx.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnx.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxActionPerformed(evt);
            }
        });
        jToolBar1.add(btnx);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ipsrv1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipsrv1ActionPerformed
        // TODO add your handling code here:
        
        
        
        
    }//GEN-LAST:event_ipsrv1ActionPerformed

    private void ipsrv3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipsrv3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipsrv3ActionPerformed

    private void ipsrv4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipsrv4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipsrv4ActionPerformed

    private void ipsrv2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipsrv2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipsrv2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        getadress();
       
               
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxActionPerformed
        p.stop();
        //new flux().dispose();
    }//GEN-LAST:event_btnxActionPerformed

    private void btnpicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpicActionPerformed
        // TODO add your handling code here:
        capture();
    }//GEN-LAST:event_btnpicActionPerformed

    private void jFileChooser2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFileChooser2ActionPerformed

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
            java.util.logging.Logger.getLogger(ClientIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientIHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                
                new ClientIHM().setVisible(true);
            new ClientIHM().setResizable(false);
                     
                     
                     
                 
                
              
                
          
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea affichage;
    private javax.swing.JButton btnpic;
    private javax.swing.JButton btnx;
    private javax.swing.JTextField ipsrv1;
    private javax.swing.JTextField ipsrv2;
    private javax.swing.JTextField ipsrv3;
    private javax.swing.JTextField ipsrv4;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JFileChooser jFileChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
